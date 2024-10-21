package maze.solver.functions;

import config.Config;

public class NonLinearCostFunc extends CostFunc {
    private static final double PENALTY_TRESHOLD = Config.getInstance().costFunc().penaltyTreshold();

    @Override
    @SuppressWarnings("MagicNumber")
    double calculateClimbCost(int heightDiff) {
        return Math.pow(heightDiff, 1.5);
    }

    @Override
    double calculateFallCost(int heightDiff) {
        return Math.tanh(PENALTY_TRESHOLD - heightDiff);
    }
}
