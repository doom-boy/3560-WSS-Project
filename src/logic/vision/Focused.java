package logic.vision;

import java.util.Arrays;
import java.util.List;

// Sees: NE, E, SE, current
public class Focused extends Vision {
    public Focused() { this.range = 1; }

    @Override
    public List<int[]> getVisibleOffsets() {
        return Arrays.asList(
            new int[]{1, -1},  // NE
            new int[]{1,  0},  // E
            new int[]{1,  1},  // SE
            new int[]{0,  0}   // current
        );
    }
}