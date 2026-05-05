package map;

/**
 * WeatherSystem tracks weather state via isHot and isDry flags.
 * progressWeather() cycles through 4 states, each boosting a terrain type's cost.
 *
 * State -> Terrain cost penalty:
 *   hot  + !dry  -> Plains cost ++
 *   hot  + dry   -> Desert cost ++
 *   !hot + dry   -> Mountain cost ++
 *   !hot + !dry  -> Swamp cost ++
 */
public class WeatherSystem {

    private boolean isHot;
    private boolean isDry;
    private int duration;

    private static final int SEASON_LENGTH = 10;

    public WeatherSystem() {
        // Start: hot, not dry (Plains state)
        this.isHot = true;
        this.isDry = false;
        this.duration = 0;
    }

    /** Called once per turn. Advances weather after SEASON_LENGTH turns. */
    public void tick() {
        duration++;
        if (duration >= SEASON_LENGTH) {
            duration = 0;
            progressWeather();
        }
    }

    /**
     * Cycles weather through the 4 states per the diagram:
     * hot+!dry -> hot+dry -> !hot+dry -> !hot+!dry -> hot+!dry ...
     */
    public void progressWeather() {
        if (isHot && !isDry) {
            isDry = true;          // hot+!dry  ->  hot+dry
        } else if (isHot && isDry) {
            isHot = false;         // hot+dry   ->  !hot+dry
        } else if (!isHot && isDry) {
            isDry = false;         // !hot+dry  ->  !hot+!dry
        } else {
            isHot = true;          // !hot+!dry ->  hot+!dry
        }
    }

    /**
     * Returns the movement cost modifier for the current weather.
     * The affected terrain type gets +1 cost, others stay normal.
     */
    public String getAffectedTerrain() {
        if ( isHot && !isDry) return "Spring"; // cost on plains
        if ( isHot &&  isDry) return "Summer"; // const on deserts
        if (!isHot &&  isDry) return "Winter"; // cost on mountain
        return "Fall"; // !hot && !dry // cost on swamp
    }

    /** Water drain goes up when hot. */
    public double getWaterMod() {
        return isHot ? 1.3 : 1.0;
    }

    /** Food drain goes up when cold. */
    public double getFoodMod() {
        return !isHot ? 1.3 : 1.0;
    }

    // Getters
    public boolean isHot() { 
        return isHot; 
    }
    public boolean isDry() { 
        return isDry; 
    }
    public int getDuration() { 
        return duration; 
    }

    @Override
    public String toString() {
        return String.format("Weather: %s | Hot: %b | Dry: %b | Turn: %d",
            getAffectedTerrain() + " weather", isHot, isDry, duration);
    }
}