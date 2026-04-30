package map;

// Abstract, b/c subclasses define diff terrain typs
public abstract class Terrain {
    protected int movementCost;
    protected int foodCost;
    protected int waterCost;

    public Terrain(int movementCost, int foodCost, int waterCost) {
        this.movementCost = movementCost;
        this.foodCost = foodCost;
        this.waterCost = waterCost;
    }

    public int getMovementCost() { return movementCost; }
    public int getFoodCost()     { return foodCost; }
    public int getWaterCost()    { return waterCost; }

    public abstract String getType();
}