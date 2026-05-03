package map.terrain;

public class Desert extends Terrain {
    public static final String field = "Desert";

    public Desert() {
        super(2, 3, 5); // easy to move, but high water cost
    }

    @Override
    public String getType() {
        return field;
    }
}