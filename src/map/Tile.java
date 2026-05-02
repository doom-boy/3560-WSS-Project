package map;

import event.Event;
import map.terrain.Terrain;

public class Tile {

    private Position position;
    private Terrain terrain;
    private Event event; //0 to 1 multiplicity on diagram

    public Tile(Position position, Terrain terrain) { // eventless tile (will b determined on map chance)
        this.position = position;
        this.terrain = terrain;
        this.event = null;
    }

    public Tile (Position position, Terrain terrain, Event event) {
        this.position = position;
        this.terrain = terrain;
        this.event = event;
    }

    //getter n setters
    public Position getPosition() {
        return position;
    }
    public Terrain getTerrain() {
        return terrain;
    }
    public Event getEvent() { 
        return event; 
    }
    public void setEvent(Event e) { 
        this.event = e; 
    }

    // movement costs needed for later calcs
    public int getMovementCost() {
        return terrain.getMovementCost();
    }
    public int getFoodCost() {
        return terrain.getFoodCost();
    }
    public int getWaterCost() {
        return terrain.getWaterCost();
    }



}


/**
 * 
 * need to get the position, terrain, cost, events, of each tile
 */