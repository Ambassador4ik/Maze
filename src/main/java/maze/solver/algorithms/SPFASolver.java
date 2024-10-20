package maze.solver.algorithms;

import maze.Node;
import maze.solver.AbstractMazeSolver;
import maze.solver.functions.CostFunc;
import maze.solver.structs.Graph;
import maze.solver.structs.MazeSolution;
import java.util.Arrays;
import java.util.List;
import java.util.*;

/**
 * Implements the Shortest Path Faster Algorithm (SPFA) to solve a maze.
 */
public class SPFASolver extends AbstractMazeSolver {
    /**
     * Constructs a SPFASolver with the specified CostFunc.
     *
     * @param costFunc the cost function to calculate movement costs between nodes
     */
    public SPFASolver(CostFunc costFunc) {
        super(costFunc);
    }

    /**
     * Solves the maze using the SPFA algorithm.
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

        // Initialize queue and in-queue flags
        Queue<Integer> queue = new LinkedList<>();
        boolean[] inQueue = new boolean[numNodes];

        // Enqueue the start node
        queue.add(startIdx);
        inQueue[startIdx] = true;

        // Relax edges using SPFA
        while (!queue.isEmpty()) {
            int u = queue.poll();
            inQueue[u] = false;

            for (Graph.Edge edge : graph.adjacencyList().get(u)) {
                int v = edge.to;
                double newDist = dist[u] + edge.weight;

                if (newDist < dist[v]) {
                    dist[v] = newDist;
                    pred[v] = u;

                    if (!inQueue[v]) {
                        queue.add(v);
                        inQueue[v] = true;
                    }
                }
            }
        }

        // Optional: Detect negative cycles by performing one more relaxation
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
