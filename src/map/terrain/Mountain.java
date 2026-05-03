package map.terrain;

public class Mountain extends Terrain {
    private int visibilityBenefit;

    public Mountain() {
        super(4, 2, 3); // high movement cost, moderate food/water
        this.visibilityBenefit = 2;
    }

    //////////////////////////////////////////////////////////////////
    // need to implement this inside Vision 
    public int getVisibilityBenefit() {
        return visibilityBenefit;
    }

    @Override
    public String getType() {
        return "Mountain";
    }
}