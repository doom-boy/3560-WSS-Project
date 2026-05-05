package logic.brain;

import map.Map;
import map.Tile;
import player.Player;
import logic.Path;
import logic.vision.Vision;
import event.Trader;

// Prioritizes lowest stat; seeks food/water before gold or east
public class Careful extends Brain {

    public Careful(Vision vision) { super(vision); }

    @Override
    public Tile decideMove(Player player, Map map) {
        if (player.getCurrMovement() <= 0) return null;

        boolean foodLow  = player.getCurrFood()  < player.getMaxFood()  * 0.4;
        boolean waterLow = player.getCurrWater() < player.getMaxWater() * 0.4;

        Path path = null;

        if (foodLow && waterLow) {
            // whichever is lower takes priority
            path = player.getCurrFood() <= player.getCurrWater()
                ? vision.closestFood(map, player.getPosition())
                : vision.closestWater(map, player.getPosition());
        } else if (foodLow) {
            path = vision.closestFood(map, player.getPosition());
            if (path == null) path = vision.secondClosestFood(map, player.getPosition());
        } else if (waterLow) {
            path = vision.closestWater(map, player.getPosition());
            if (path == null) path = vision.secondClosestWater(map, player.getPosition());
        }

        // If no resource found or stats ok, take easiest path east
        if (path == null) path = vision.easiestPath(map, player.getPosition());

        Tile target = stepToward(path, player, map);
        // fallback: step east if nothing useful visible
        return target != null ? target : stepEast(player, map);
    }

    @Override
    public boolean considerEvent(Player player, Tile tile) {
        // Always interact; careful player takes any resource
        return tile.getEvent() != null && tile.getEvent().isAvailable();
    }

    @Override
    public int[] decideTrade(Player player, Trader trader, int[] traderOffer) {
        // Accept if we're getting food/water we need
        boolean wantFood  = player.getCurrFood()  < player.getMaxFood()  * 0.6;
        boolean wantWater = player.getCurrWater() < player.getMaxWater() * 0.6;
        boolean gettingFood  = traderOffer[3] > 0;
        boolean gettingWater = traderOffer[4] > 0;
        if ((wantFood && gettingFood) || (wantWater && gettingWater)) return traderOffer;
        return null; // decline
    }
}