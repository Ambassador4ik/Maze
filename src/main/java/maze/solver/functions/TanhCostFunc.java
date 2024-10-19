package maze.solver.functions;

public class TanhCostFunc extends CostFunc {
    @Override
    double calculateClimbCost(int heightDiff) {
        return Math.tanh(heightDiff);
    }

    @Override
    double calculateFallCost(int heightDiff) {
        return Math.tanh(heightDiff);
    }
}
