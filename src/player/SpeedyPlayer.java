package player;

import map.Position;
import logic.vision.Vision;
import logic.brain.Brain;

// More movement per turn; tradeoff lower food n water max
public class SpeedyPlayer extends Player {
    private String type = "SpeedyPlayer";

    public SpeedyPlayer(Position startPos, Brain brain, Vision vision) {
        super(startPos, brain, vision);
        this.maxMovement  = 15;  // extra movement
        this.currMovement = 15;
        this.maxFood = 70;
        this.currFood = 70;
        this.maxWater = 70;
        this.currWater = 70;
    }

    public String getType() {
        return type;
    }
}