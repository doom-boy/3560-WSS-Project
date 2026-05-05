package logic;

import java.util.List;
import java.util.ArrayList;

public class Path {
    public enum Move {
        NORTH, SOUTH, EAST, WEST,
        NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST,
        STAY
    }

    private List<Move> moves;
    private int totalMovementCost;
    private int totalFoodCost;
    private int totalWaterCost;
    private int destX;
    private int destY;

    public Path() {
        this.moves = new ArrayList<>();
    }

    public void addMove(Move m) {
        moves.add(m);
    }


    // getts n setters
    public List<Move> getMoves() {
        return moves;
    }
    public int getTotalMovementCost() {
        return totalMovementCost; 
    }
    public int getTotalFoodCost() { 
        return totalFoodCost; 
    }
    public int getTotalWaterCost() { 
        return totalWaterCost; 
    }
    public int getDestX() { 
        return destX; 
    }
    public int getDestY() { 
        return destY; 
    }

    public void setTotalMovementCost(int v) { 
        totalMovementCost = v; 
    }
    public void setTotalFoodCost(int v) { 
        totalFoodCost = v; 
    }
    public void setTotalWaterCost(int v) { 
        totalWaterCost = v; 
    }
    public void setDestX(int x) { 
        destX = x; 
    }
    public void setDestY(int y) { 
        destY = y; 
    }


    public boolean isEmpty() { return moves.isEmpty(); }

    // First move in path, Brain to use this & step player 1 tile
    public Move nextMove() {
        return moves.isEmpty() ? Move.STAY : moves.get(0);
    }

    @Override
    public String toString() {
        return moves + " | mv=" + totalMovementCost
             + " food=" + totalFoodCost + " water=" + totalWaterCost;
    }
}