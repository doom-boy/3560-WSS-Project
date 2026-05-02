package map;

import java.util.Random;

import event.Event;
import event.GoldBonus;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private int width;
    private int height;
    private Tile[][] grid;
    private int difficulty;

    private Random rng; // for generation randomness (terrain, event, etc)

    // Event tuning by difficulty [easy, normal, hard]
    private static final int[] MIN_EVENTS     = {2, 3, 4};
    private static final int[] MAX_EVENTS     = {4, 6, 10};
    // Occurrence rate; chance per tile that event spawns, we can inverse this so less events, but doesn't seem fair for bigger map
    private static final double[] EVENT_RATES = {0.15, 0.25, 0.40};



    public Map(int width, int height, int difficulty) {
        this.width = width;
        this.height = height;
        this.difficulty = difficulty;
        this.grid = new Tile[height][width];
        this.rng = new Random();
    }

    //map generation
    public void generate() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Terrain terrain = randomTerrain();
                grid[y][x] = new Tile(new Position(x, y), terrain);
            }
        }
        placeEvents();
    }

    private Terrain randomTerrain() {
        // Only Plain implemented; other terrain subclasses to b made
        // TODO: add Mountain, Desert, Swamp, Forest and randomize it
        return new Plain();
    }

    //how to acc set the events to each 
    private void placeEvents() {
    double rate = EVENT_RATES[difficulty];
    int minE = MIN_EVENTS[difficulty];
    int maxE = MAX_EVENTS[difficulty];

    //collect eligible tiles (skip start column)
    List<Tile> eligible = new ArrayList<>();
    for (int y = 0; y < height; y++) {
        for (int x = 1; x < width; x++) {
            eligible.add(grid[y][x]);
        }
    }

    // Shuffle so placement isn't biased toward top-left
    java.util.Collections.shuffle(eligible, rng);

    // Desired count from rate, between min Event n max Event (for overall)
    int desired = (int)(eligible.size() * rate);
    int count = Math.max(minE, Math.min(maxE, desired));

    for (int i = 0; i < count; i++) {
        eligible.get(i).setEvent(randomEvent());
    }





    //event picked randomly, 1 of 4 //TODO: need to add in food bonus
    private Event randomEvent() {
        int roll = rng.nextInt(3);
        switch (roll) {
            case 0:
                // GoldBonus ; 1-time
                double goldMult = 0.8 + rng.nextDouble() * 0.8; // 0.8 - 1.6
                return new GoldBonus(goldMult, 10);
            case 1:
                // Trader; repeatable
                // GenerousTrader/GreedyTrader ; pick randomly
                boolean generous = rng.nextBoolean();
                return generous ? new GenerousTrader() : new GreedyTrader();
            default:
                // WaterBonus placeholder ; TODO, repeatable
                ////////////////////////////////////////////////////////////////////////
                // implement water bonus
                double goldMult2 = 1.0;
                return new GoldBonus(goldMult2, 5); // temp
        }
    }




    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    //getTile
    public Tile getTile(Position pos) {
        if (pos.getX() < 0 || pos.getX() >= width) return null;
        if (pos.getY() < 0 || pos.getY() >= height) return null;
        return grid[pos.getY()][pos.getX()];
    }


    // Native indexing: grid[3][2] = position (2,3); for debug
    public Tile getTileRaw(int row, int col) {
        return grid[row][col];
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