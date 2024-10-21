package maze.solver.structs;

import java.util.List;
import maze.Node;
import maze.solver.functions.CostFunc;

/**
 * Represents the solution to a maze, containing the path from start to goal and the total cost.
 *
 * @param totalCost New field to store total cost
 */
public record MazeSolution(List<Node> path, double totalCost) {
    /**
     * Constructs a MazeSolution with the specified path and total cost.
     *
     * @param path      the list of nodes representing the path from start to goal
     * @param totalCost the total cost of the path
     */
    public MazeSolution {
    }

    /**
     * Recalculates the total cost of the path using the provided CostFunc.
     *
     * <p>
     * This method iterates through the path and computes the sum of costs between consecutive nodes
     * based on the given CostFunc.
     * </p>
     *
     * @param costFunc the cost function to use for recalculating the path cost
     * @return the total cost of the path using the provided CostFunc
     * @throws IllegalArgumentException if the costFunc is null
     */
    public double costWith(CostFunc costFunc) {
        if (costFunc == null) {
            throw new IllegalArgumentException("CostFunc cannot be null.");
        }

        if (path == null || path.size() < 2) {
            return 0.0; // No movement, hence no cost
        }

        double total = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            Node current = path.get(i);
            Node next = path.get(i + 1);
            total += costFunc.calculateCost(current.height(), next.height());
        }
        return total;
    }
}
