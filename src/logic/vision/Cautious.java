package logic.vision;

import java.util.Arrays;
import java.util.List;

// Sees N, current, S, E (T-shape pointing east)
public class Cautious extends Vision {
    public Cautious() { this.range = 1; }

    @Override
    public List<int[]> getVisibleOffsets() {
        return Arrays.asList(
            new int[]{0, -1},  // N
            new int[]{0,  0},  // current
            new int[]{0,  1},  // S
            new int[]{1,  0}   // E
        );
    }
}