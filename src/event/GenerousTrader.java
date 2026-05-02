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


    ///////////////////////////////////////////////////////////
    //initial trade option, need to change si offers other stuff
    @Override
    public int[] showTradeOption() {
        // Offers food; wants gold
        int foodOffer = (int)(10 * OFFER_BONUS);
        int goldAsk   = (int)(5  * ASK_DISCOUNT);
        // [give_food, give_water, give_gold] from trader's side
        return new int[]{foodOffer, 0, -goldAsk};
    }


    // prob need to remake ts
    @Override
    public int[] makeOffer(int[] playerOffer) {
        // Generous trader accepts most reasonable offers
        int playerGold = playerOffer[2];
        if (playerGold >= 3) {
            // Accept
            return new int[]{0}; // 0 = accept
        }
        // counteroffer: ask for slightly more
        int[] counter = playerOffer.clone();
        counter[2] = playerGold + 1;
        return new int[]{1, counter[0], counter[1], counter[2]}; // 1 = counteroffer

        //patience trigger
        
    }

    @Override
    public String toString() {
        return "GenerousTrader(patience=" + patience + ")";
    }
    //

    // needs to modify cost so more offered and less cost


    
}
