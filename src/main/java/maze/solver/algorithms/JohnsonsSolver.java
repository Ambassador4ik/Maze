package maze.solver.algorithms;

import maze.Node;
import maze.solver.AbstractMazeSolver;
import maze.solver.functions.CostFunc;
import maze.solver.structs.Graph;
import maze.solver.structs.MazeSolution;
import util.Pair;
import java.util.Arrays;
import java.util.List;
import java.util.*;

/**
 * Implements Johnson's algorithm to solve a maze with potential negative edge weights.
 */
public class JohnsonsSolver extends AbstractMazeSolver {
    /**
     * Constructs a JohnsonsSolver with the specified CostFunc.
     *
     * @param costFunc the cost function to calculate movement costs between nodes
     */
    public JohnsonsSolver(CostFunc costFunc) {
        super(costFunc);
    }

    /**
     * Solves the maze using Johnson's algorithm.
     *
     * @param grid the maze grid represented as a 2D array of Nodes
     * @return a MazeSolution representing the path from start to goal, or null if no path is found
     */
    @Override
    public MazeSolution solve(Node[][] grid) {
        initialize(grid);

        // Step 1: Add a new node q and connect it to all nodes with edge weight 0
        int qIdx = numNodes; // Index for the new node q
        Graph augmentedGraph = buildAugmentedGraphWithQ();

        // Step 2: Run Bellman-Ford from q to compute h(v)
        double[] h = bellmanFord(augmentedGraph, qIdx, numNodes + 1);

        // Step 3: Check for negative-weight cycles
        if (h == null) {
            throw new IllegalStateException("Graph contains a negative-weight cycle.");
        }

        // Step 4: Reweight all edges to eliminate negative weights
        Graph reweightedGraph = reweightEdges(h);

        // Step 5: Run Dijkstra's algorithm from start on reweighted graph
        DijkstraResult dijkstraResult = dijkstra(reweightedGraph, startIdx);

        // Check if the goal is reachable
        if (dijkstraResult.dist[goalIdx] == Double.POSITIVE_INFINITY) {
            return null; // No path found
        }

        // Step 6: Reconstruct the path from start to goal
        List<Node> path = reconstructPath(startIdx, goalIdx, dijkstraResult.pred);
        double totalCost = dijkstraResult.dist[goalIdx] - h[startIdx] + h[goalIdx];

        return new MazeSolution(path, totalCost);
    }

    /**
     * Builds the augmented graph by adding a new node q connected to all nodes with edge weight 0.
     *
     * @return the augmented graph
     */
    private Graph buildAugmentedGraphWithQ() {
        Graph augmentedGraph = new Graph(numNodes + 1);

        // Copy existing edges
        for (Graph.Edge edge : graph.edgeList()) {
            augmentedGraph.addEdge(edge.from, edge.to, edge.weight);
        }

        // Connect q to all nodes with edge weight 0
        int qIdx = numNodes;
        for (int i = 0; i < numNodes; i++) {
            augmentedGraph.addEdge(qIdx, i, 0.0);
        }

        return augmentedGraph;
    }

    /**
     * Runs the Bellman-Ford algorithm to compute shortest paths from the source.
     *
     * @param graph      the graph
     * @param source     the source node index
     * @param totalNodes the total number of nodes in the graph
     * @return the array of shortest distances or null if a negative cycle is detected
     */
    private double[] bellmanFord(Graph graph, int source, int totalNodes) {
        double[] dist = new double[totalNodes];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[source] = 0.0;

        // Relax edges repeatedly
        for (int i = 1; i < totalNodes; i++) {
            boolean updated = false;
            for (Graph.Edge edge : graph.edgeList()) {
                if (dist[edge.from] + edge.weight < dist[edge.to]) {
                    dist[edge.to] = dist[edge.from] + edge.weight;
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
                return null; // Negative cycle detected
            }
        }

        return dist;
    }

    /**
     * Reweights the edges to eliminate negative weights using the potential h.
     *
     * @param h the potential array from Bellman-Ford
     * @return the reweighted graph
     */
    private Graph reweightEdges(double[] h) {
        Graph reweightedGraph = new Graph(numNodes);

        for (int u = 0; u < numNodes; u++) {
            for (Graph.Edge edge : graph.adjacencyList().get(u)) {
                double newWeight = edge.weight + h[u] - h[edge.to];
                reweightedGraph.addEdge(u, edge.to, newWeight);
            }
        }

        return reweightedGraph;
    }

    /**
     * Runs Dijkstra's algorithm to compute shortest paths from the source.
     *
     * @param graph  the reweighted graph
     * @param source the source node index
     * @return the result containing distances and predecessors
     */
    private DijkstraResult dijkstra(Graph graph, int source) {
        double[] dist = new double[numNodes];
        int[] pred = new int[numNodes];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(pred, -1);
        dist[source] = 0.0;
        pred[source] = source;

        // Priority queue to select the node with the smallest distance
        PriorityQueue<Pair<Double, Integer>> pq = new PriorityQueue<>(Comparator.comparingDouble(Pair::key));
        pq.add(new Pair<>(0.0, source));

        while (!pq.isEmpty()) {
            Pair<Double, Integer> currentPair = pq.poll();
            double currentDist = currentPair.key();
            int u = currentPair.value();

            if (currentDist > dist[u]) {
                continue; // A shorter path to u has already been found
            }

            for (Graph.Edge edge : graph.adjacencyList().get(u)) {
                int v = edge.to;
                double newDist = dist[u] + edge.weight;
                if (newDist < dist[v]) {
                    dist[v] = newDist;
                    pred[v] = u;
                    pq.add(new Pair<>(newDist, v));
                }
            }
        }

        return new DijkstraResult(dist, pred);
    }

    /**
     * Helper class to store Dijkstra's results.
     */
    private static class DijkstraResult {
        double[] dist;
        int[] pred;

        DijkstraResult(double[] dist, int[] pred) {
            this.dist = dist;
            this.pred = pred;
        }
    }
}
