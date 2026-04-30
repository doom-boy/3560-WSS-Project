package event;

public abstract class Event {
    protected boolean isRepeating;
    protected boolean encountered;

    public Event() {
        
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