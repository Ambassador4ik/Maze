package maze.generator;

import maze.Node;
import maze.Node.Direction;
import util.Pair;

import java.security.SecureRandom;
import java.util.*;
import static maze.Maze.*;

public class DFSMazeGenerator implements MazeGenerator {
    // Probability to remove an additional wall to create loops
    private static final double LOOP_PROBABILITY = 0.05;
    private final SecureRandom random;

    public DFSMazeGenerator() {
        this.random = new SecureRandom();
    }

    @Override
    public void generate(Node[][] grid) {
        int height = grid.length;
        if (height == 0) return;
        int width = grid[0].length;

        // Initialize all cells as unvisited
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x].visited(false);
            }
        }

        // Stack for DFS
        Deque<Node> stack = new ArrayDeque<>();

        // Start from the top-left corner
        Node start = grid[0][0];
        start.visited(true);
        stack.push(start);

        while (!stack.isEmpty()) {
            Node current = stack.peek();
            List<Direction> unvisitedNeighbors = getUnvisitedNeighbors(current, grid, width, height);

            if (!unvisitedNeighbors.isEmpty()) {
                // Randomly select an unvisited neighbor
                Direction direction = unvisitedNeighbors.get(random.nextInt(unvisitedNeighbors.size()));
                Pair<Integer, Integer> neighborCoords = getNeighborCoordinates(current, direction, width, height);
                if (neighborCoords != null) {
                    // Corrected the order of indices here
                    Node neighbor = grid[neighborCoords.value()][neighborCoords.key()];
                    if (!neighbor.visited()) {
                        // Remove walls between current and neighbor
                        removeWall(current, neighbor, direction);
                        neighbor.visited(true);
                        stack.push(neighbor);
                    }
                }
            } else {
                // Backtrack
                stack.pop();
            }
        }

        addLoops(grid, width, height, LOOP_PROBABILITY);

        setupExits(grid, width, height);
    }
}
