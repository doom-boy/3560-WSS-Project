package map;

public class Plain extends Terrain {
    // returns this if called
    private String type = "Plain";

    // Low cost traversal, sorta like base terrain
    public Plain() {
        super(1, 1, 1);
    }

    @Override
    public String getType() { 
        return type; 
    }
}