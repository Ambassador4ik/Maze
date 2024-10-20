package maze.solver;

import maze.Maze;
import maze.Node;
import maze.solver.functions.CostFunc;
import maze.solver.structs.Graph;
import maze.solver.structs.MazeSolution;
import util.Pair;

import java.util.*;

/**
 * An abstract base class for MazeSolver implementations.
 * It encapsulates common functionalities shared across different maze-solving algorithms.
 */
public abstract class AbstractMazeSolver implements MazeSolver {
    protected final CostFunc costFunc;
    protected Map<Node, Integer> nodeToIndex;
    protected Map<Integer, Node> indexToNode;
    protected Node start;
    protected Node goal;
    protected int startIdx;
    protected int goalIdx;
    protected int height;
    protected int width;
    protected int numNodes;
    protected Graph graph;

    /**
     * Constructs an AbstractMazeSolver with the specified CostFunc.
     *
     * @param costFunc the cost function to calculate movement costs between nodes
     */
    public AbstractMazeSolver(CostFunc costFunc) {
        this.costFunc = costFunc;
    }

    /**
     * Validates the grid and initializes common data structures.
     *
     * @param grid the maze grid represented as a 2D array of Nodes
     * @throws IllegalArgumentException if the grid is null or empty
     */
    protected void initialize(Node[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            throw new IllegalArgumentException("Grid must be a non-empty 2D array of Nodes.");
        }

        this.height = grid.length;
        this.width = grid[0].length;
        this.numNodes = height * width;

        // Assign unique indices to each node
        nodeToIndex = new HashMap<>();
        indexToNode = new HashMap<>();
        int index = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Node node = grid[row][col];
                nodeToIndex.put(node, index);
                indexToNode.put(index, node);
                index++;
            }
        }

        // Define start and goal nodes
        start = grid[0][0];
        goal = grid[height - 1][width - 1];
        startIdx = nodeToIndex.get(start);
        goalIdx = nodeToIndex.get(goal);

        // Build the graph
        buildGraph(grid);
    }

    /**
     * Builds the graph representation of the maze.
     *
     * @param grid the maze grid
     */
    protected void buildGraph(Node[][] grid) {
        graph = new Graph(numNodes);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Node current = grid[row][col];
                int u = nodeToIndex.get(current);

                for (Node.Direction direction : Node.Direction.values()) {
                    if (!current.walls().hasWall(direction)) { // Check if movement in this direction is possible
                        Pair<Integer, Integer>
                            neighborCoords = Maze.getNeighborCoordinates(current, direction, width, height);
                        if (neighborCoords == null) {
                            continue; // Neighbor is out of bounds
                        }

                        int neighborRow = neighborCoords.value();
                        int neighborCol = neighborCoords.key();
                        Node neighbor = grid[neighborRow][neighborCol];
                        int v = nodeToIndex.get(neighbor);

                        // Calculate movement cost
                        double cost = costFunc.calculateCost(current.height(), neighbor.height());

                        graph.addEdge(u, v, cost);
                    }
                }
            }
        }
    }

    /**
     * Reconstructs the path from start to goal using the predecessor array.
     *
     * @param startIdx the index of the start node
     * @param goalIdx  the index of the goal node
     * @param pred     the predecessor array
     * @return a list of Nodes representing the path from start to goal
     */
    protected List<Node> reconstructPath(int startIdx, int goalIdx, int[] pred) {
        LinkedList<Node> path = new LinkedList<>();
        int current = goalIdx;

        while (current != startIdx) {
            if (current == -1) {
                return Collections.emptyList(); // No path exists
            }
            path.addFirst(indexToNode.get(current));
            current = pred[current];
        }

        path.addFirst(indexToNode.get(startIdx));
        return path;
    }

    /**
     * Abstract method to be implemented by concrete solvers.
     *
     * @param grid the maze grid represented as a 2D array of Nodes
     * @return a MazeSolution representing the path from start to goal, or null if no path is found
     */
    @Override
    public abstract MazeSolution solve(Node[][] grid);
}
