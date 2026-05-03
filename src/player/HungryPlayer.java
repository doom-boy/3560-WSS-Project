package player;

import map.Position;

// Higher food drain — starts with less max food
public class HungryPlayer extends Player {
    private String type = "HungryPlayer";

    public HungryPlayer(Position startPos, Object vision, Object brain) {
        super(startPos, vision, brain);
        this.maxFood  = 70;   // lower food ceiling
        this.currFood = 70;
        // water and movement unchanged
    }

    public String getType() { return type; }
}