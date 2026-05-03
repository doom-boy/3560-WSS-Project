package logic.brain;

import java.util.List;

import map.Tile;

public class Brain {

    private List<Tile> path;
    private Tile target;


    public Brain() {}

    public Tile decideMove() {
        return null;
    }

    

    
}


/**
 * 
 * NEED:
 * 
 * Brain.conductTrade(player, this) // for trader trigger Trader.java
 * 
 * Brain.decideMove() // in WSS
 * 
 * Brain.considerEvent() // in WSS
 * 
 * 
 * 
 */