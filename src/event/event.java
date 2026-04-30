package event;
import player.Player;

public abstract class Event {
    protected boolean isRepeating;
    protected boolean encountered;

    public Event(boolean isRepeating) {
        this.isRepeating = isRepeating;
        this.encountered = false;
    }


    // Returns true if event is available to trigger
    public boolean isAvailable() {
        if (encountered && !isRepeating) return false;
        return true;
    }

    //subclasses implement acc effect on player
    public abstract void trigger(Player player);


    
    public boolean isRepeating() {
        return isRepeating;
    }
    public boolean isEncountered() {
        return encountered;
    }

    // needed to mark events
    public void setEncountered(boolean encountered) {
        this.encountered = encountered;
    }






}

/**
 * need indicator for encountered and event_is_repeating
 * 
 * - summarize all non-encountered event thru check
 * 
 * - event trigger; need to define what happens in sub event
 * 
 */