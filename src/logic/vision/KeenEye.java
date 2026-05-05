package logic.vision;

import java.util.Arrays;
import java.util.List;

// Sees: N, NE, current, E, E2, S, SE
public class KeenEye extends Vision {
    public KeenEye() { this.range = 1; }

    @Override
    public List<int[]> getVisibleOffsets() {
        return Arrays.asList(
            new int[]{ 0, -1},  // N
            new int[]{ 1, -1},  // NE
            new int[]{ 0,  0},  // current
            new int[]{ 1,  0},  // E
            new int[]{ 2,  0},  // E2
            new int[]{ 0,  1},  // S
            new int[]{ 1,  1}   // SE
        );
    }
}