package maze.generator.algorithms;

import maze.Node;
import maze.Node.Direction;
import maze.generator.AbstractMazeGenerator;
import util.Pair;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import static maze.Maze.getNeighborCoordinates;
import static maze.Maze.removeWall;

public class DFSMazeGenerator extends AbstractMazeGenerator {
    public DFSMazeGenerator() {
        super();
    }

    @Override
    protected void generateMaze(Node[][] grid, int height, int width) {
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
    }

    // Helper method to get unvisited neighbors
    private List<Direction> getUnvisitedNeighbors(Node current, Node[][] grid, int width, int height) {
        List<Direction> directions = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            Pair<Integer, Integer> neighborCoords = getNeighborCoordinates(current, dir, width, height);
            if (neighborCoords != null) {
                Node neighbor = grid[neighborCoords.value()][neighborCoords.key()];
                if (!neighbor.visited()) {
                    directions.add(dir);
                }
            }
        }
        return directions;
    }
}
