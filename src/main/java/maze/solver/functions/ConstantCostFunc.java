package maze.solver.functions;

public class ConstantCostFunc extends CostFunc {

    @Override
    double calculateClimbCost(int heightDiff) {
        return 1;
    }

    @Override
    double calculateFallCost(int heightDiff) {
        return 1;
    }
}
