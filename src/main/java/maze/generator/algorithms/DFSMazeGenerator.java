package maze.generator.algorithms;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import maze.Node;
import maze.Node.Direction;
import maze.generator.AbstractMazeGenerator;
import util.Pair;
import static maze.Maze.getNeighborCoordinates;
import static maze.Maze.getUnvisitedNeighbors;
import static maze.Maze.removeWall;

public class DFSMazeGenerator extends AbstractMazeGenerator {
    public DFSMazeGenerator() {
    }

    @Override
    protected void generateMaze(Node[][] grid, int height, int width) {
        // Stack for DFS
        Deque<Node> nodes = new ArrayDeque<>();

        // Start from the top-left corner
        Node start = grid[0][0];
        start.visited(true);
        nodes.push(start);

        while (!nodes.isEmpty()) {
            Node current = nodes.peek();
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
                        nodes.push(neighbor);
                    }
                }
            } else {
                // Backtrack
                nodes.pop();
            }
        }
    }
}
