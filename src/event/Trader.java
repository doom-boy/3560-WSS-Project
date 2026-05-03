package event;

import java.util.Random;

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
    private int turnsSincePatience = 0;
    protected Random rng = new Random();

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
    // Trade pairs: {give_type, get_type) ;  0=food, 1=water, 2=gold
    // Base rates: 1 food for 2 gold, 1 water for 1 gold, 1 food for 2 water (and inverses)
    protected static final double[][] BASE_RATES = {
        // [give_type][get_type] = how much getter receives per 1 unit given
        //         food   water  gold
        /* food*/ {0,    2.0,   2.0},
        /*water*/ {0.5,  0,     1.0},
        /* gold*/ {0.5,  1.0,   0  }
    };

    public int[] showTradeOption() {
        // Pick random valid trade pair (give != get)
        int giveType, getType;
        do {
            giveType = rng.nextInt(3);
            getType  = rng.nextInt(3);
        } while (giveType == getType);

        int giveAmt = 1 + rng.nextInt(5);
        int getAmt  = (int) Math.max(1, BASE_RATES[giveType][getType] * giveAmt * getOfferMultiplier());
        getAmt      = Math.min(getAmt, stock[getType]); // can't offer more than stock

        int[] offer = new int[6]; // [give_food, give_water, give_gold, get_food, get_water, get_gold]
        offer[giveType]     = giveAmt; // what player gives
        offer[3 + getType]  = getAmt;  // what player gets
        return offer;
    }

    // Subclasses define their multiplier affecting offer generosity
    protected abstract double getOfferMultiplier();
    protected abstract double getDemandMultiplier();



    // returns true if player's offer meets/exceeds trade value after multipliers
    protected abstract boolean offerAcceptable(int[] playerOffer);

    // for adjusting player's offer to min acceptable value
    protected abstract int[] counterOffer(int[] playerOffer);

    // Player give their offer/counteroffer; returns trader's response
    // response: 0=accept, 1=counteroffer, 2=reject
    public int[] makeOffer(int[] playerOffer) {
        // Auto-reject if player asks for more than stock
        if (playerOffer[3] > stock[0] || playerOffer[4] > stock[1] || playerOffer[5] > stock[2]) {
            System.out.println("Trader Rejects: insufficient stock.");
            return new int[]{2};
        }
        if (offerAcceptable(playerOffer)) return new int[]{0};
        if (patience <= 0) return new int[]{2};
        return counterOffer(playerOffer);
    }

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

    // Call once per game turn while trader is active
    // for incrementing patience after 5 turns
    public void onTurnPassed() {
        turnsSincePatience++;
        if (turnsSincePatience >= 5) {
            patience++;
            turnsSincePatience = 0;
            System.out.println("[Trader] Patience recovered: " + patience);
        }
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
