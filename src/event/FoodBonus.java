package event;

import player.Player;

public class FoodBonus extends Event {
    private double multiplier;
    private int baseAmount;

    public FoodBonus(double multiplier, int baseAmount) {
        super(false); // 1-time
        this.multiplier = multiplier;
        this.baseAmount = baseAmount;
    }

    @Override
    public void trigger(Player player) {
        if (!isAvailable()) return;
        int reward = (int)(baseAmount * multiplier);
        player.addFood(reward);
        System.out.println("Food Bonus! +" + reward + " food");
        setEncountered(true);
    }

    public double getMultiplier() {
        return multiplier;
    }
}