package map;

import java.util.Random;

public class Map {
    private int width;
    private int height;
    private Tile[][] grid;
    private int difficulty;

    private Random rng; // for generation randomness (terrain, event, etc)







    public Map(int width, int height, int difficulty) {
        this.width = width;
        this.height = height;
        this.difficulty = difficulty;
        this.grid = new Tile[height][width];
        this.rng = new Random();
    }



    public int getWidth()  {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
}

/**
 * width, height, grid, difficulty variables
 * 
 * - need a min and max event and occurance rate
 * - need generate function
 * - randomly assign event and terrains 
 * - get width and height publics
 * 
 */