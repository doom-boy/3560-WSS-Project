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
 * 
 * 
 */

/**
 * Player can't know what type of trader it is
 * might want to have patience slowly regenerate after amt of turns have passed, maybe stock too but at a slow rate
 * 
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

    // gives trader's opening offer [resourceType, amount]
    // resourceType: 0=food, 1=water, 2=gold
    public abstract int[] showTradeOption();

    // Player give their offer/counteroffer; returns trader's response
    // response: 0=accept, 1=counteroffer, 2=reject
    public abstract int[] makeOffer(int[] playerOffer);

    //finalize trade if player accepts; give "true" if trade went through
    public boolean confirmTrade(Player player, int[] agreedOffer) {
        if (patience <= 0) {
            System.out.println("[Trader] Trader has lost patience and left.");
            return false;
        }
        applyTrade(player, agreedOffer);
        return true;
    }



    protected void applyTrade(Player player, int[] offer) {
        // format for offer: [give_food, give_water, give_gold, get_food, get_water, get_gold]
        player.addFood(-offer[0]); //taken from plyer
        player.addWater(-offer[1]); // ^^
        player.addGold(-offer[2]);  // ^^
        player.addFood(offer[3]);
        player.addWater(offer[4]);
        player.addGold(offer[5]);
    }


    public void decrementPatience() {
        patience--;
        // JUst for debug, can remove later if wanted
        System.out.println("[Trader] Patience remaining: " + patience);
    }

    public int getPatience() {
        return patience;
    }
    public int[] getStock() {
        return stock;
    }



    //needs brain to do interact here
    @Override
    public void trigger(Player player) {
        if (patience <= 0) {
            System.out.println("Trader refuses to deal.");
            return;
        }

        // Full trade dialogue driven externally by Brain (placeholder)
        // Brain.conductTrade(player, this) ///////////////////
        int[] offer = showTradeOption();
        System.out.printf("Trader Offers: food=%d water=%d gold=%d%n", offer[0], offer[1], offer[2]);

        // Brain response placeholder (auto-no for now) //////////
        System.out.println("[Trader] No negotiation. Trade skipped.");


    }







}
