package maze;

import heightmap.providers.HeightMapProvider;
import lombok.Getter;
import maze.generator.MazeGenerator;
import maze.solver.MazeSolution;
import maze.solver.MazeSolver;
import util.Pair;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Maze {
    @Getter private Node[][] grid;
    @Getter private MazeSolution solution;

    private final int width;
    private final int height;

    @Getter private boolean isInitialized;
    @Getter private boolean isSolved;

    private static final SecureRandom random = new SecureRandom();

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;

        init();

        this.isInitialized = false;
        this.isSolved = false;
    }

    public void clear() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Node(j, i, 0);
            }
        }
    }

    private void init() {
        this.grid = new Node[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Node(j, i, 0);
            }
        }
    }

    public void setupSurfaceWith(HeightMapProvider provider) {
        provider.fillMap(grid);
    }

    public void initWith(MazeGenerator generator) {
        generator.generate(grid);
    }

    public void solveWith(MazeSolver solver) {
        this.solution = solver.solve(grid);
    }

    /**
     * Retrieves a list of unvisited neighboring directions for the given cell.
     *
     * @param current the current cell
     * @param grid    the maze grid
     * @param width   the width of the grid
     * @param height  the height of the grid
     * @return a list of Directions representing unvisited neighbors
     */
    public static List<Node.Direction> getUnvisitedNeighbors(Node current, Node[][] grid, int width, int height) {
        List<Node.Direction> neighbors = new ArrayList<>();
        for (Node.Direction direction : Node.Direction.values()) {
            Pair<Integer, Integer> coords = getNeighborCoordinates(current, direction, width, height);
            if (coords != null) {
                Node neighbor = grid[coords.value()][coords.key()];
                if (!neighbor.visited()) {
                    neighbors.add(direction);
                }
            }
        }
        return neighbors;
    }

    /**
     * Calculates the coordinates of the neighbor in the specified direction.
     *
     * @param current   the current cell
     * @param direction the direction to move
     * @param width     the width of the grid
     * @param height    the height of the grid
     * @return a Pair representing the neighbor's (x, y) coordinates or null if out of bounds
     */
    public static Pair<Integer, Integer> getNeighborCoordinates(Node current, Node.Direction direction, int width, int height) {
        int x = current.x();
        int y = current.y();
        switch (direction) {
            case NORTH:
                y -= 1;
                break;
            case SOUTH:
                y += 1;
                break;
            case EAST:
                x += 1;
                break;
            case WEST:
                x -= 1;
                break;
        }
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return new Pair<>(x, y);
        }
        return null;
    }

    /**
     * Removes the walls between two adjacent cells in the specified direction.
     *
     * @param current   the current cell
     * @param neighbor  the neighboring cell
     * @param direction the direction from current to neighbor
     */
    public static void removeWall(Node current, Node neighbor, Node.Direction direction) {
        current.walls().setWall(direction, false);
        // Remove the opposite wall in the neighbor cell
        Node.Direction opposite = getOppositeDirection(direction);
        neighbor.walls().setWall(opposite, false);
    }

    /**
     * Determines the opposite direction.
     *
     * @param direction the original direction
     * @return the opposite Direction
     */
    public static Node.Direction getOppositeDirection(Node.Direction direction) {
        return switch (direction) {
            case NORTH -> Node.Direction.SOUTH;
            case SOUTH -> Node.Direction.NORTH;
            case EAST -> Node.Direction.WEST;
            case WEST -> Node.Direction.EAST;
            default -> throw new IllegalArgumentException("Invalid direction: " + direction);
        };
    }

    /**
     * Adds loops to the maze by randomly removing additional walls based on the loop probability.
     *
     * @param grid            the maze grid
     * @param width           the width of the grid
     * @param height          the height of the grid
     * @param loopProbability the probability of removing an additional wall to create a loop
     */
    public static void addLoops(Node[][] grid, int width, int height, double loopProbability) {
        List<Node.Direction> directions = Arrays.asList(Node.Direction.EAST, Node.Direction.SOUTH);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Node current = grid[y][x];

                directions.forEach(direction -> {
                    Pair<Integer, Integer> neighborCoords = getNeighborCoordinates(current, direction, width, height);

                    // Proceed only if neighbor exists, there is a wall, and probability condition is met
                    if (neighborCoords != null &&
                        current.walls().hasWall(direction) &&
                        random.nextDouble() < loopProbability) {

                        Node neighbor = grid[neighborCoords.value()][neighborCoords.key()];
                        removeWall(current, neighbor, direction);
                    }
                });
            }
        }
    }

    /**
     * Creates entrance and exit points in the maze.
     *
     * @param grid   the maze grid
     * @param width  the width of the grid
     * @param height the height of the grid
     */
    public static void setupExits(Node[][] grid, int width, int height) {
        // Create entrance at (0,0) by removing the NORTH wall
        grid[0][0].walls().setWall(Node.Direction.NORTH, false);

        // Create exit at (height-1, width-1) by removing the SOUTH wall
        grid[height - 1][width - 1].walls().setWall(Node.Direction.SOUTH, false);
    }

    /**
     * Prints the maze to the console for visualization.
     *
     * @param grid the maze grid
     */
    public static void printMaze(Node[][] grid) {
        int height = grid.length;
        int width = grid[0].length;

        // Print the top boundary
        for (int x = 0; x < width; x++) {
            System.out.print("+---");
        }
        System.out.println("+");

        for (int y = 0; y < height; y++) {
            StringBuilder top = new StringBuilder("|");
            StringBuilder bottom = new StringBuilder("+");

            for (int x = 0; x < width; x++) {
                Node current = grid[y][x];
                top.append("   "); // Space for the cell
                if (current.walls().hasWall(Node.Direction.EAST)) {
                    top.append("|");
                } else {
                    top.append(" ");
                }

                if (current.walls().hasWall(Node.Direction.SOUTH)) {
                    bottom.append("---+");
                } else {
                    bottom.append("   +");
                }
            }
            System.out.println(top.toString());
            System.out.println(bottom.toString());
        }
    }
}
