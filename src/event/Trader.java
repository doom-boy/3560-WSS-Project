package event;

import player.Player;

/**
 * Kinda thinking like this flow:
 * - Trader offers resource, like we can see their stock. 
 * - Player makes offer. 
 * - Trader could accept offer or counteroffer. 
 * - Player could accept or not. 
 * - If Player doesn't accept, trader patience level decrease. 
 * - If trader has no more patience, they won't trade anymore.
 * 
 * Generous trader will offer more for less and have higher patience. 
 * Greedy trader is opposite. 
 */

public abstract class Trader extends Event {

    protected int[] stock;   // resource #'s [food, water, gold]
    protected int patience;  // decrease everytime  player rejects trader's counter offer

    public Trader(int[] stock, int patience) {
        super(true); // traders are repeatable
        this.stock = stock.clone();
        this.patience = patience;
    }

    /**
     * func for
     * - show trades
     * - player makes offer
     * - confirm / apply stat mod if trade accepted (need to check patience before confirm)
     * -
     * 
     */


    public void decrementPatience() {
        patience--;
        System.out.println("[Trader] Patience remaining: " + patience);
    }

    public int getPatience() {
        return patience;
    }
    public int[] getStock() {
        return stock;
    }

    public void trigger(Player player) {}
}

// has to be abstract for the generous and greedy trader implementation