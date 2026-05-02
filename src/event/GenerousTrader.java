package event;

public class GenerousTrader extends Trader {

    //offer more, asks less, higher patience; these can all be modded later
    private static final int BASE_PATIENCE = 5;
    private static final double OFFER_BONUS = 1.4;   //gives 40% more
    private static final double ASK_DISCOUNT = 0.7;  //asks 30% less

    public GenerousTrader() {
        super(new int[]{20, 20, 20}, BASE_PATIENCE); // can mod stock levels
    }

    

    // needs to modify cost so more offered and less cost


    
}
