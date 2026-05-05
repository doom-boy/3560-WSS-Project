package player;

import map.Position;
import logic.vision.Vision;
import logic.brain.Brain;

// starts with less max water
public class ThirstyPlayer extends Player {
    private String type = "ThirstyPlayer";

    public ThirstyPlayer(Position startPos, Brain brain, Vision vision) {
        super(startPos, brain, vision);
        this.maxWater  = 80;  //lower water ceiling
        this.currWater = 80;
        this.maxMovement  = 10;
        this.currMovement = 10;
    }

    public String getType() {
        return type;
    }
}