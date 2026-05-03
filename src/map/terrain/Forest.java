package map.terrain;

public class Forest extends Terrain {
    public static final String field = "Forest";

    public Forest() {
        super(2, 2, 1); // moderate costs, easier terrain
    }

    @Override
    public String getType() {
        return field;
    }
}