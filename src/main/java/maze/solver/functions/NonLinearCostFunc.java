package maze.solver.functions;

public class NonLinearCostFunc extends CostFunc {
    private final double PENALTY_TRESHOLD = 3;

    @Override
    double calculateClimbCost(int heightDiff) {
        return Math.pow(heightDiff, 1.5);
    }

    @Override
    double calculateFallCost(int heightDiff) {
        return Math.tanh(PENALTY_TRESHOLD - heightDiff);
    }
}
