package maze.solver.functions;

public class LinearCostFunc extends CostFunc {
    @Override
    double calculateClimbCost(int heightDiff) {
        return heightDiff;
    }

    @Override
    double calculateFallCost(int heightDiff) {
        return -heightDiff;
    }
}
