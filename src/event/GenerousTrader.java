package event;

public class GenerousTrader extends Trader {

    //offer more, asks less, higher patience; these can all be modded later
    private static final int BASE_PATIENCE = 5;
    private static final double OFFER_BONUS = 1.4;   //gives 40% more
    private static final double ASK_DISCOUNT = 0.7;  //asks 30% less

    public GenerousTrader() {
        super(new int[]{20, 20, 20}, BASE_PATIENCE); // can mod stock levels; should make random
        /////////////////
    }


    
    @Override
    protected double getOfferMultiplier()  {
        return OFFER_BONUS; 
    }

    @Override
    protected double getDemandMultiplier() { 
        return ASK_DISCOUNT; 
    }

    @Override
    protected boolean offerAcceptable(int[] o) {
        if (patience <= 0) return false;
        // Check each give/get pair against base rate * discount
        for (int g = 0; g < 3; g++) {
            for (int r = 0; r < 3; r++) {
                if (g == r || o[g] == 0) continue;
                double expected = BASE_RATES[g][r] * o[g] * ASK_DISCOUNT;
                if (o[3 + r] >= expected) return true;
            }
        }
        return false;
    }

    @Override
    protected int[] counterOffer(int[] o) {
        int[] counter = o.clone();
        for (int g = 0; g < 3; g++) {
            for (int r = 0; r < 3; r++) {
                if (g == r || o[g] == 0) continue;
                int needed = (int) Math.ceil(BASE_RATES[g][r] * o[g] * ASK_DISCOUNT);
                counter[3 + r] = Math.max(o[3 + r], needed);
            }
        }
        return new int[]{1, counter[0], counter[1], counter[2], counter[3], counter[4], counter[5]};
    }

    @Override
    public String toString() {
        return "GenerousTrader(patience=" + patience + ")";
    }
    //

    // needs to modify cost so more offered and less cost


    
}
