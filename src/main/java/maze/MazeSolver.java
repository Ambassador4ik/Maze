package maze;

import java.util.function.Function;

public interface MazeSolver {
    double climbCost(double heightDiff);
    double dropCost(double heightDiff);
}
