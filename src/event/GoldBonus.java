package event;

import player.Player;

public class GoldBonus extends Event {

    private double multiplier;
    private int baseAmount;

    public GoldBonus(double multiplier, int baseAmount) {
        super(false); // not repeating, 1 time event
        this.multiplier = multiplier;
        this.baseAmount = baseAmount;
    }

    @Override
    public void trigger(Player player) {
        if (!isAvailable()) return;
        int reward = (int)(baseAmount * multiplier);
        player.addGold(reward);
        System.out.println("Gold Bonus! +" + reward + " gold");
        setEncountered(true);
    }


    public double getMultiplier() {
        return multiplier;
    }


    
}

/**
 * have a handle for event trigger
 * 
 *  need handles for call:
 * -  GoldBonus(goldMult, 10); ////multiplier n amt
 */