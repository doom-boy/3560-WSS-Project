package map;

import event.Event;

public class Tile {

    private Position position;
    private Terrain terrain;
    private Event event; //0 to 1 multiplicy on diagram

    public Tile(Position position, Terrain terrain) { // eventless tile (will b determined on map chance)
        //
    }

    public Tile (Position position, Terrain terrain, Event event) {
        //
    }

    //getEvent, get food n water cost
}


/**
 * 
 * need to get the position, terrain, cost, events, of each tile
 */