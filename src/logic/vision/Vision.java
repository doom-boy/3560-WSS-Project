package logic.vision;

import logic.Path;
import map.Map;
import map.Position;
import map.Tile;
import event.FoodBonus;
import event.WaterBonus;
import event.GoldBonus;
import event.Trader;

import java.util.*;

public abstract class Vision {
    protected int range; // subclasses define shape via getVisibleOffsets()

    //returns list of (dx,dy) offsets this vision type can see
    public abstract List<int[]> getVisibleOffsets();

    public List<Tile> getVisibleTiles(Map map, Position pos) {
        List<Tile> result = new ArrayList<>();
        for (int[] off : getVisibleOffsets()) {
            Tile t = map.getTile(new Position(pos.getX() + off[0], pos.getY() + off[1]));
            if (t != null) result.add(t);
        }
        return result;
    }

    // path-finding stuff; on slides

    public Path closestFood(Map map, Position pos) {
        return findClosest(map, pos, FoodBonus.class, false, 1);
    }
    public Path secondClosestFood(Map map, Position pos) {
        return findClosest(map, pos, FoodBonus.class, false, 2);
    }
    public Path closestWater(Map map, Position pos) {
        return findClosest(map, pos, WaterBonus.class, false, 1);
    }
    public Path secondClosestWater(Map map, Position pos) {
        return findClosest(map, pos, WaterBonus.class, false, 2);
    }
    public Path closestGold(Map map, Position pos) {
        return findClosest(map, pos, GoldBonus.class, false, 1);
    }
    public Path secondClosestGold(Map map, Position pos) {
        return findClosest(map, pos, GoldBonus.class, false, 2);
    }
    public Path closestTrader(Map map, Position pos) {
        return findClosest(map, pos, Trader.class, false, 1);
    }
    public Path secondClosestTrader(Map map, Position pos) {
        return findClosest(map, pos, Trader.class, false, 2);
    }

    public Path easiestPath(Map map, Position pos) {
        // lowest movement cost tile in vision
        List<Tile> visible = getVisibleTiles(map, pos);
        Tile best = null;
        for (Tile t : visible) {
            if (best == null || t.getMovementCost() < best.getMovementCost()) best = t;
        }
        if (best == null) return null;
        return buildSingleStepPath(pos, best);
    }

    //finds nth closest tile whose event is an instance of eventClass
    protected Path findClosest(Map map, Position pos, Class<?> eventClass, boolean unused, int nth) {
        List<Tile> visible = getVisibleTiles(map, pos);
        List<Tile> candidates = new ArrayList<>();
        for (Tile t : visible) {
            if (t.getEvent() != null && eventClass.isInstance(t.getEvent())
                    && t.getEvent().isAvailable()) {
                candidates.add(t);
            }
        }
        if (candidates.isEmpty()) return null;

        // sort; distance ASC, then movementCost ASC, then x DESC (prefer east)
        candidates.sort((a, b) -> {
            int da = dist(pos, a.getPosition());
            int db = dist(pos, b.getPosition());
            if (da != db) return da - db;
            if (a.getMovementCost() != b.getMovementCost())
                return a.getMovementCost() - b.getMovementCost();
            return b.getPosition().getX() - a.getPosition().getX();
        });

        Tile target = candidates.size() >= nth ? candidates.get(nth - 1) : null;
        if (target == null) return null;
        return buildSingleStepPath(pos, target);
    }

    private int dist(Position a, Position b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    protected Path buildSingleStepPath(Position from, Tile to) {
        Path p = new Path();
        int dx = to.getPosition().getX() - from.getX();
        int dy = to.getPosition().getY() - from.getY();
        p.addMove(directionMove(dx, dy));
        p.setTotalMovementCost(to.getMovementCost());
        p.setTotalFoodCost(to.getTerrain().getFoodCost());
        p.setTotalWaterCost(to.getTerrain().getWaterCost());
        p.setDestX(to.getPosition().getX());
        p.setDestY(to.getPosition().getY());
        return p;
    }

    private Path.Move directionMove(int dx, int dy) {
        if (dx > 0 && dy < 0) return Path.Move.NORTHEAST;
        if (dx > 0 && dy > 0) return Path.Move.SOUTHEAST;
        if (dx < 0 && dy < 0) return Path.Move.NORTHWEST;
        if (dx < 0 && dy > 0) return Path.Move.SOUTHWEST;

        if (dx > 0) return Path.Move.EAST;
        if (dx < 0) return Path.Move.WEST;
        if (dy < 0) return Path.Move.NORTH;
        return Path.Move.SOUTH; // gotta b last option
    }

    public int getRange() { 
        return range; 
    }
}