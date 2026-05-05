package logic.brain;

import map.Map;
import map.Tile;
import player.Player;
import logic.Path;
import logic.vision.Vision;
import event.Trader;

// Prioritizes east. resources-seeking only at 1/3 max; movement at 1/8 max
public class Determined extends Brain {

    public Determined(Vision vision) { super(vision); }

    @Override
    public Tile decideMove(Player player, Map map) {
        boolean foodCritical  = player.getCurrFood()     < player.getMaxFood()     / 3.0;
        boolean waterCritical = player.getCurrWater()    < player.getMaxWater()    / 3.0;
        boolean moveCritical  = player.getCurrMovement() < player.getMaxMovement() / 8.0;

        if (moveCritical) return null; // rest

        Path path = null;
        if (foodCritical) {
            path = vision.closestFood(map, player.getPosition());
        } else if (waterCritical) {
            path = vision.closestWater(map, player.getPosition());
        }

        // Default push east
        if (path == null) {
            Tile east = stepEast(player, map);
            if (east != null) return east;
            path = vision.easiestPath(map, player.getPosition());
        }

        return stepToward(path, player, map);
    }

    @Override
    public boolean considerEvent(Player player, Tile tile) {
        // Only interact if we're critically low
        boolean foodLow  = player.getCurrFood()  < player.getMaxFood()  / 3.0;
        boolean waterLow = player.getCurrWater() < player.getMaxWater() / 3.0;
        if (tile.getEvent() instanceof event.FoodBonus && foodLow)  return true;
        if (tile.getEvent() instanceof event.WaterBonus && waterLow) return true;
        if (tile.getEvent() instanceof event.GoldBonus) return true; // free gold always
        return false;
    }

    @Override
    public int[] decideTrade(Player player, Trader trader, int[] traderOffer) {
        // Only trade if critically low on food or water
        boolean foodCritical  = player.getCurrFood()  < player.getMaxFood()  / 3.0;
        boolean waterCritical = player.getCurrWater() < player.getMaxWater() / 3.0;
        if ((traderOffer[3] > 0 && foodCritical) || (traderOffer[4] > 0 && waterCritical))
            return traderOffer;
        return null;
    }
}