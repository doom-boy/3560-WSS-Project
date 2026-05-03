package player;

import map.Position;

// starts with less max water
public class ThirstyPlayer extends Player {
    private String type = "ThirstyPlayer";

    public ThirstyPlayer(Position startPos, Object vision, Object brain) {
        super(startPos, vision, brain);
        this.maxWater  = 70;  //lower water ceiling
        this.currWater = 70;
    }

    public String getType() {
        return type;
    }
}