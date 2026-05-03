package event;

public class GreedyTrader extends Trader {

    // Offers less, demands more, low patience
    private static final int BASE_PATIENCE = 2;
    private static final double OFFER_CUT    = 0.6;  // gives 40% less
    private static final double DEMAND_HIKE  = 1.5;  // asks 50% more

    public GreedyTrader() {
        super(new int[]{20, 20, 20}, BASE_PATIENCE);
    }

    

    @Override
    protected double getOfferMultiplier()  {
        return OFFER_CUT;
    }

    @Override
    protected double getDemandMultiplier() {
        return DEMAND_HIKE;
    }


    @Override
    protected boolean offerAcceptable(int[] o) {
        if (patience <= 0) return false;
        for (int g = 0; g < 3; g++) {
            for (int r = 0; r < 3; r++) {
                if (g == r || o[g] == 0) continue;
                double expected = BASE_RATES[g][r] * o[g] * DEMAND_HIKE;
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
                int needed = (int) Math.ceil(BASE_RATES[g][r] * o[g] * DEMAND_HIKE);
                counter[3 + r] = Math.max(o[3 + r], needed);
            }
        }
        return new int[]{1, counter[0], counter[1], counter[2], counter[3], counter[4], counter[5]};
    }

    

    @Override
    public String toString() {
        return "GreedyTrader(patience=" + patience + ")";
    }

    // mod patience, mod their offers n trade options
    
}
