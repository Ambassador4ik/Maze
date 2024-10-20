package maze.generator.algorithms;

import maze.Node;
import maze.Node.Direction;
import maze.generator.AbstractMazeGenerator;
import util.Pair;

import java.util.Comparator;
import java.util.PriorityQueue;
import static maze.Maze.getNeighborCoordinates;
import static maze.Maze.removeWall;

public class PrimMazeGenerator extends AbstractMazeGenerator {
    public PrimMazeGenerator() {
        super();
    }

    @Override
    protected void generateMaze(Node[][] grid, int height, int width) {
        // Priority queue to store walls with random priorities
        PriorityQueue<Wall> walls = new PriorityQueue<>(Comparator.comparingDouble(w -> w.priority));

        // Start from a random cell
        int startY = random.nextInt(height);
        int startX = random.nextInt(width);
        Node start = grid[startY][startX];
        start.visited(true);

        // Add all walls of the starting cell to the wall list
        for (Direction dir : Direction.values()) {
            Pair<Integer, Integer> neighborCoords = getNeighborCoordinates(start, dir, width, height);
            if (neighborCoords != null) {
                walls.add(new Wall(start, dir, random.nextDouble()));
            }
        }

        while (!walls.isEmpty()) {
            Wall currentWall = walls.poll();
            Node current = currentWall.node;
            Direction direction = currentWall.direction;
            Pair<Integer, Integer> neighborCoords = getNeighborCoordinates(current, direction, width, height);

            if (neighborCoords == null) continue;

            Node neighbor = grid[neighborCoords.value()][neighborCoords.key()];

            if (!neighbor.visited()) {
                // Remove walls between current and neighbor
                removeWall(current, neighbor, direction);
                neighbor.visited(true);

                // Add the neighbor's walls to the wall list
                for (Direction dir : Direction.values()) {
                    Pair<Integer, Integer> nextCoords = getNeighborCoordinates(neighbor, dir, width, height);
                    if (nextCoords != null) {
                        Node nextNeighbor = grid[nextCoords.value()][nextCoords.key()];
                        if (!nextNeighbor.visited()) {
                            walls.add(new Wall(neighbor, dir, random.nextDouble()));
                        }
                    }
                }
            }
        }
    }

    // Helper class to represent walls with a random priority
    private static class Wall {
        Node node;
        Direction direction;
        double priority;

        Wall(Node node, Direction direction, double priority) {
            this.node = node;
            this.direction = direction;
            this.priority = priority;
        }
    }
}
