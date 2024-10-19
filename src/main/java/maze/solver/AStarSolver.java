package maze.solver;

import maze.Node;
import maze.Maze;
import maze.solver.functions.CostFunc;
import util.Pair;

import java.util.*;

/**
 * Implements the A* search algorithm to solve a maze.
 *
 * <p>
 * The A* algorithm is an informed search algorithm that uses heuristics to efficiently find the shortest path
 * from the start node to the goal node. This implementation assumes that all movement costs are non-negative.
 * </p>
 *
 * <p>
 * Beware: If the CostFunc can produce negative weights, the A* algorithm may not function correctly.
 * </p>
 */
public class AStarSolver implements MazeSolver {
    private final CostFunc costFunc;

    /**
     * Constructs an AStarSolver with the specified CostFunc.
     *
     * @param costFunc the cost function to calculate movement costs between nodes
     */
    public AStarSolver(CostFunc costFunc) {
        this.costFunc = costFunc;
    }

    /**
     * Solves the maze using the A* algorithm.
     *
     * @param grid the maze grid represented as a 2D array of Nodes
     * @return a MazeSolution representing the path from start to goal, or null if no path is found
     * @throws IllegalArgumentException if the grid is null or empty
     */
    @Override
    public MazeSolution solve(Node[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            throw new IllegalArgumentException("Grid must be a non-empty 2D array of Nodes.");
        }

        int height = grid.length;
        int width = grid[0].length;

        // Define start and goal nodes
        Node start = grid[0][0];
        Node goal = grid[height - 1][width - 1];

        // Priority queue ordered by f = g + h
        PriorityQueue<PathNode> openSet = new PriorityQueue<>();
        openSet.add(new PathNode(start, 0.0, heuristic(start, goal), null));

        // Maps to keep track of the best g scores and parent nodes
        Map<Node, Double> gScores = new HashMap<>();
        gScores.put(start, 0.0);

        // Set to keep track of visited nodes
        Set<Node> closedSet = new HashSet<>();

        while (!openSet.isEmpty()) {
            PathNode currentPathNode = openSet.poll();
            Node current = currentPathNode.node;

            // If the goal is reached, reconstruct and return the path
            if (current.equals(goal)) {
                return reconstructPath(currentPathNode);
            }

            if (closedSet.contains(current)) {
                continue; // Skip already processed nodes
            }

            closedSet.add(current);
            current.visited(true); // Mark as visited

            // Explore neighbors
            for (Node.Direction direction : Node.Direction.values()) {
                if (!current.walls().hasWall(direction)) { // Check if movement in this direction is possible
                    Pair<Integer, Integer>
                        neighborCoords = Maze.getNeighborCoordinates(current, direction, width, height);
                    if (neighborCoords == null) {
                        continue; // Neighbor is out of bounds
                    }

                    Node neighbor = grid[neighborCoords.value()][neighborCoords.key()];

                    if (closedSet.contains(neighbor)) {
                        continue; // Ignore already processed neighbors
                    }

                    // Calculate tentative g score
                    double tentativeG = currentPathNode.g + costFunc.calculateCost(current.height(), neighbor.height());

                    // If neighbor not in gScores or tentativeG is better
                    if (!gScores.containsKey(neighbor) || tentativeG < gScores.get(neighbor)) {
                        gScores.put(neighbor, tentativeG);
                        double fScore = tentativeG + heuristic(neighbor, goal);
                        PathNode neighborPathNode = new PathNode(neighbor, tentativeG, fScore, currentPathNode);
                        openSet.add(neighborPathNode);
                    }
                }
            }
        }

        // No path found
        return null;
    }

    /**
     * Reconstructs the path from the goal node to the start node.
     *
     * @param currentPathNode the current PathNode (goal node)
     * @return a MazeSolution containing the path from start to goal and the total cost
     */
    private MazeSolution reconstructPath(PathNode currentPathNode) {
        List<Node> path = new ArrayList<>();
        PathNode pathNode = currentPathNode;
        while (pathNode != null) {
            path.add(pathNode.node);
            pathNode = pathNode.parent;
        }
        Collections.reverse(path); // Reverse to get path from start to goal
        double totalCost = currentPathNode.g; // Total cost is the g score of the goal node
        return new MazeSolution(path, totalCost);
    }

    /**
     * Heuristic function estimating the cost from the current node to the goal node.
     * Uses Manhattan distance multiplied by the minimal possible cost per move.
     *
     * @param current the current node
     * @param goal    the goal node
     * @return the heuristic cost
     */
    private double heuristic(Node current, Node goal) {
        // Manhattan distance
        // int dx = Math.abs(current.x() - goal.x());
        // int dy = Math.abs(current.y() - goal.y());
        //return dx + dy;
        return 0;
    }

    /**
     * Represents a node in the path with associated costs and a parent node.
     */
    private static class PathNode implements Comparable<PathNode> {
        private final Node node;
        private final double g; // Cost from start to this node
        private final double f; // Estimated total cost (g + h)
        private final PathNode parent;

        /**
         * Constructs a PathNode.
         *
         * @param node   the current node
         * @param g      the cost from start to this node
         * @param f      the estimated total cost (g + h)
         * @param parent the parent PathNode
         */
        public PathNode(Node node, double g, double f, PathNode parent) {
            this.node = node;
            this.g = g;
            this.f = f;
            this.parent = parent;
        }

        /**
         * Compares this PathNode with another based on the f score.
         *
         * @param other the other PathNode to compare with
         * @return negative if this.f < other.f, positive if this.f > other.f, zero otherwise
         */
        @Override
        public int compareTo(PathNode other) {
            return Double.compare(this.f, other.f);
        }
    }
}
