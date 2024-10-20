package maze.solver.algorithms;

import maze.Node;
import maze.solver.AbstractMazeSolver;
import maze.solver.functions.CostFunc;
import maze.solver.structs.Graph;
import maze.solver.structs.MazeSolution;
import java.util.Arrays;
import java.util.List;

/**
 * Implements the Bellman-Ford algorithm to solve a maze.
 */
public class BellmanFordSolver extends AbstractMazeSolver {

    /**
     * Constructs a BellmanFordSolver with the specified CostFunc.
     *
     * @param costFunc the cost function to calculate movement costs between nodes
     */
    public BellmanFordSolver(CostFunc costFunc) {
        super(costFunc);
    }

    /**
     * Solves the maze using the Bellman-Ford algorithm.
     *
     * @param grid the maze grid represented as a 2D array of Nodes
     * @return a MazeSolution representing the path from start to goal, or null if no path is found
     */
    @Override
    public MazeSolution solve(Node[][] grid) {
        initialize(grid);

        // Initialize distance and predecessor arrays
        double[] dist = new double[numNodes];
        int[] pred = new int[numNodes];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(pred, -1);

        dist[startIdx] = 0.0;
        pred[startIdx] = startIdx;

        // Relax edges repeatedly
        for (int i = 1; i < numNodes; i++) {
            boolean updated = false;
            for (Graph.Edge edge : graph.edgeList()) {
                if (dist[edge.from] + edge.weight < dist[edge.to]) {
                    dist[edge.to] = dist[edge.from] + edge.weight;
                    pred[edge.to] = edge.from;
                    updated = true;
                }
            }
            if (!updated) {
                break; // No updates in this iteration, so exit early
            }
        }

        // Check for negative-weight cycles
        for (Graph.Edge edge : graph.edgeList()) {
            if (dist[edge.from] + edge.weight < dist[edge.to]) {
                throw new IllegalStateException("Graph contains a negative-weight cycle.");
            }
        }

        // Check if a path exists
        if (dist[goalIdx] == Double.POSITIVE_INFINITY) {
            return null; // No path found
        }

        // Reconstruct the path from start to goal
        List<Node> path = reconstructPath(startIdx, goalIdx, pred);
        double totalCost = dist[goalIdx];

        return new MazeSolution(path, totalCost);
    }
}
