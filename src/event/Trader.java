package event;

import player.Player;

public abstract class Trader extends Event {
    public Trader() {};

    public void trigger(Player player) {}
}

// has to be abstract for the generous and greedy trader implementation