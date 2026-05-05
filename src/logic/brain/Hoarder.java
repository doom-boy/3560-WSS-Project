package logic.brain;

import map.Map;
import map.Tile;
import player.Player;
import logic.Path;
import logic.vision.Vision;
import event.Trader;

// Minimizes travel cost; keep food/water near max; focuses on gold
public class Hoarder extends Brain {

    public Hoarder(Vision vision) { super(vision); }

    @Override
    public Tile decideMove(Player player, Map map) {
        if (player.getCurrMovement() <= 0) return null;

        boolean foodLow  = player.getCurrFood()  < player.getMaxFood()  * 0.8;
        boolean waterLow = player.getCurrWater() < player.getMaxWater() * 0.8;

        Path path = null;
        if (foodLow) {
            path = vision.closestFood(map, player.getPosition());
        } else if (waterLow) {
            path = vision.closestWater(map, player.getPosition());
        } else {
            // Seek gold; if none visible, easiest (cheapest) path
            path = vision.closestGold(map, player.getPosition());
            if (path == null) path = vision.closestTrader(map, player.getPosition());
        }

        if (path == null) path = vision.easiestPath(map, player.getPosition());

        // Hoarder no move if cost too high
        if (path != null && path.getTotalMovementCost() > player.getCurrMovement() / 2) {
            return null; // rest instead
        }

        return stepToward(path, player, map);
    }

    @Override
    public boolean considerEvent(Player player, Tile tile) {
        // Interacts with everything; maximizes collection
        return tile.getEvent() != null && tile.getEvent().isAvailable();
    }

    @Override
    public int[] decideTrade(Player player, Trader trader, int[] traderOffer) {
        // Trade gold for food/water only if near max already (hoarder keeps reserves)
        boolean hasGold  = player.getCurrGold() > 10;
        boolean wantFood = player.getCurrFood()  < player.getMaxFood()  * 0.9;
        boolean wantWater= player.getCurrWater() < player.getMaxWater() * 0.9;
        if (hasGold && (wantFood || wantWater)) return traderOffer;
        return null;
    }
}