package logic.vision;

import java.util.Arrays;
import java.util.List;

// Sees extended forward arc like N, N2, S, S2, E, E2, NE, SE, E of N2, E of S2, N of E2, S of E2
public class Farsight extends Vision {
    public Farsight() { this.range = 2; }

    @Override
    public List<int[]> getVisibleOffsets() {
        return Arrays.asList(
            new int[]{0, -1},  // N
            new int[]{0, -2},  // N2
            new int[]{0,  1},  // S
            new int[]{0,  2},  // S2
            new int[]{1,  0},  // E
            new int[]{2,  0},  // E2
            new int[]{1, -1},  // NE
            new int[]{1,  1},  // SE
            new int[]{1, -2},  // E of N2
            new int[]{1,  2},  // E of S2
            new int[]{2, -1},  // N of E2
            new int[]{2,  1}   // S of E2
        );
    }
}