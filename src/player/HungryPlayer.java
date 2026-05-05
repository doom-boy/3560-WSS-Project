package player;

import map.Position;
import logic.vision.Vision;
import logic.brain.Brain;

// Higher food drain — starts with less max food
public class HungryPlayer extends Player {
    private String type = "HungryPlayer";

    public HungryPlayer(Position startPos, Brain brain, Vision vision) {
        super(startPos, brain, vision);
        this.maxFood  = 80;   // lower food ceiling
        this.currFood = 80;
        this.maxMovement  = 10;
        this.currMovement = 10;
        // water and unchanged
    }

    public String getType() {
        return type;
    }
}