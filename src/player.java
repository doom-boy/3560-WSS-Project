public class Player {
    private int maxMovement;
    private int maxFood;
    private int maxWater;
    private int currMovement;
    private int currFood;
    private int currWater;
    private int currGold;
    private Position position;

    // Vision/Brain placeholders
    private Object vision; 
    private Object brain;  

    public Player(Position startPos, Object vision, Object brain) {
        this.position = startPos;
        this.vision   = vision;
        this.brain    = brain;

        // default stats; start @ max, gold @ 0
        this.maxMovement  = 3;
        this.maxFood      = 100;
        this.maxWater     = 100;
        this.currMovement = maxMovement;
        this.currFood     = maxFood;
        this.currWater    = maxWater;
        this.currGold     = 0;
    }

    // Called at top of each turn iteration
    public void regenerateMovement() {
        currMovement = maxMovement;
    }

    public void makeMove(Tile target) {
        if (currMovement <= 0) return;
        position = target.getPosition().copy();
        currMovement -= target.getMovementCost();
        if (currMovement < 0) currMovement = 0;
    }

    // Full tile stat deduction on move
    public void applyTileCost(Tile tile) {
        currFood  -= tile.getFoodCost();
        currWater -= tile.getWaterCost();
        if (currFood  < 0) currFood  = 0;
        if (currWater < 0) currWater = 0;
    }

    //half cost when staying on tile ; "resting"
    public void applyHalfTileCost(Tile tile) {
        currFood  -= tile.getFoodCost()  / 2;
        currWater -= tile.getWaterCost() / 2;
        if (currFood  < 0) currFood  = 0;
        if (currWater < 0) currWater = 0;
    }

    public void addFood(int amount) {
        currFood = Math.min(maxFood, Math.max(0, currFood + amount));
    }

    public void addWater(int amount) {
        currWater = Math.min(maxWater, Math.max(0, currWater + amount));
    }

    public void addGold(int amount) {
        currGold = Math.max(0, currGold + amount);
    }

    public boolean isAlive() {
        return currFood > 0 && currWater > 0;
    }

    // getters
    public Position getPosition()  { return position; }
    public int getMaxMovement()    { return maxMovement; }
    public int getMaxFood()        { return maxFood; }
    public int getMaxWater()       { return maxWater; }
    public int getCurrMovement()   { return currMovement; }
    public int getCurrFood()       { return currFood; }
    public int getCurrWater()      { return currWater; }
    public int getCurrGold()       { return currGold; }

    // Setters for Brain tuning; if needed, can delete lateron
    public void setPosition(Position p)  { this.position = p; }
    public void setCurrMovement(int v)   { this.currMovement = v; }
}