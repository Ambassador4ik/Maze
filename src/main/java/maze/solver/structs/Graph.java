package maze.solver.structs;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a graph structure for the maze, supporting both edge list and adjacency list representations.
 */
public class Graph {
    private final int numNodes;
    @Getter private final List<Edge> edgeList;
    @Getter private final List<List<Edge>> adjacencyList;

    public Graph(int numNodes) {
        this.numNodes = numNodes;
        this.edgeList = new ArrayList<>();
        this.adjacencyList = new ArrayList<>(numNodes);
        for (int i = 0; i < numNodes; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, double weight) {
        Edge edge = new Edge(from, to, weight);
        edgeList.add(edge);
        adjacencyList.get(from).add(new Edge(to, weight));
    }

    /**
     * Represents an edge in the graph.
     */
    public static class Edge {
        public final int from;
        public final int to;
        public final double weight;

        public Edge(int from, int to, double weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        // Constructor for adjacency list where 'from' is implicit
        public Edge(int to, double weight) {
            this.from = -1; // Not used in adjacency list
            this.to = to;
            this.weight = weight;
        }
    }
}
