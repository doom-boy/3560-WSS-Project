package player;

import map.Position;

// More movement per turn; tradeoff lower food n water max
public class SpeedyPlayer extends Player {
    private String type = "SpeedyPlayer";

    public SpeedyPlayer(Position startPos, Object vision, Object brain) {
        super(startPos, vision, brain);
        this.maxMovement  = 5;  // extra movement
        this.currMovement = 5;
        this.maxFood = 80;
        this.currFood = 80;
        this.maxWater = 80;
        this.currWater = 80;
    }

    public String getType() {
        return type;
    }
}