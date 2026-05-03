package event;

import player.Player;

public class WaterBonus extends Event {
    private double multiplier;
    private int baseAmount;

    public WaterBonus(double multiplier, int baseAmount) {
        super(true); //repeatable
        this.multiplier = multiplier;
        this.baseAmount = baseAmount;
    }

    @Override
    public void trigger(Player player) {
        if (!isAvailable()) return;
        int reward = (int)(baseAmount * multiplier);
        player.addWater(reward);
        System.out.println("Water Bonus! +" + reward + " water");
    }

    public double getMultiplier() {
        return multiplier;
    }
}