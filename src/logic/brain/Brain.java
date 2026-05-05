package logic.brain;

import map.Map;
import map.Position;
import map.Tile;
import player.Player;
import logic.vision.Vision;
import logic.Path;
import event.Trader;

public abstract class Brain {
    protected Vision vision;

    public Brain(Vision vision) {
        this.vision = vision;
    }

    // Core, returns target Tile or null to rest
    public abstract Tile decideMove(Player player, Map map);

    // event interaction decision; true = interact
    public abstract boolean considerEvent(Player player, Tile tile);

    // trade decision; returns player's offer int[6] or null to decline
    public abstract int[] decideTrade(Player player, Trader trader, int[] traderOffer);

    // resolve path's next step to an actual tile
    protected Tile stepToward(Path path, Player player, Map map) {
        if (path == null || path.isEmpty()) return null;
        Position cur = player.getPosition();
        int dx = 0, dy = 0;
        switch (path.nextMove()) {
            case NORTH: dy = -1; break;
            case SOUTH: dy =  1; break;
            case EAST: dx =  1; break;
            case WEST: dx = -1; break;
            case NORTHEAST: dx =  1; dy = -1; break;
            case NORTHWEST: dx = -1; dy = -1; break;
            case SOUTHEAST: dx =  1; dy =  1; break;
            case SOUTHWEST: dx = -1; dy =  1; break;
            default: return null; // STAY
        }
        return map.getTile(new Position(cur.getX() + dx, cur.getY() + dy));
    }

    // always prefer moving east if movement available
    protected Tile stepEast(Player player, Map map) {
        if (player.getCurrMovement() <= 0) return null;
        Position cur = player.getPosition();
        Tile east = map.getTile(new Position(cur.getX() + 1, cur.getY()));
        if (east == null) return null;
        if (east.getMovementCost() <= player.getCurrMovement()) return east;
        return null;
    }

    public Vision getVision() { 
        return vision; 
    }
}


/**
 * 
 * NEED:
 * 
 * Brain.conductTrade(player, this) // for trader trigger Trader.java
 * 
 * Brain.decideMove() // in WSS
 * 
 * Brain.considerEvent() // in WSS
 * 
 * 
 * 
 */