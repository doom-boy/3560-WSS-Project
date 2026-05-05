package player;

import map.Position;
import logic.vision.Vision;
import logic.brain.Brain;

// Higher food drain — starts with less max food
public class HungryPlayer extends Player {
    private String type = "HungryPlayer";

    public HungryPlayer(Position startPos, Vision vision, Brain brain) {
        super(startPos, vision, brain);
        this.maxFood  = 70;   // lower food ceiling
        this.currFood = 70;
        // water and movement unchanged
    }

    public String getType() {
        return type;
    }
}