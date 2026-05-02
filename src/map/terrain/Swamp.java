package map.terrain;

public class Swamp extends Terrain {
    public static final String field = "Swamp";

    public Swamp() {
        super(5, 3, 2); // very hard to move, high food cost
    }

    @Override
    public String getType() {
        return field;
    }
}