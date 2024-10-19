package maze.solver.functions;

public abstract class CostFunc {
    public double calculateCost(int currHeight, int nextHeight) {
        if (currHeight <= nextHeight) {
            return calculateClimbCost(nextHeight - currHeight);
        }
        return calculateFallCost(currHeight - nextHeight);
    }

    abstract double calculateClimbCost(int heightDiff);
    abstract double calculateFallCost(int heightDiff);
}
