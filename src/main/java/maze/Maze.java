package maze;

import heightmap.providers.HeightMapProvider;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import maze.generator.MazeGenerator;
import maze.solver.MazeSolver;
import maze.solver.structs.MazeSolution;
import util.Pair;

public class Maze {
    @Getter private Node[][] grid;
    @Getter private MazeSolution solution;

    private final int width;
    private final int height;

    private static final SecureRandom RANDOM = new SecureRandom();

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;

        init();
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
    public static Pair<Integer, Integer> getNeighborCoordinates(
        Node current, Node.Direction direction,
        int width, int height
    ) {
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
            default:
                throw new IllegalArgumentException("No such direction!");
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
                    if (neighborCoords != null
                        && current.walls().hasWall(direction)
                        && RANDOM.nextDouble() < loopProbability) {

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
     * Returns a string representation of the maze, highlighting the solution path if available.
     *
     * @return the string representation of the maze with ANSI color codes for the solution path
     */
    public String getMazeAsString() {
        StringBuilder sb = new StringBuilder();

        // ANSI escape codes for coloring
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BG_GREEN = "\u001B[45m"; // Background Green

        // Determine the fixed cell width based on the maximum height value
        int maxHeight = getMaxHeight();
        int cellWidth = String.valueOf(maxHeight).length() + 2; // Adding padding

        // Top boundary
        StringBuilder topBoundary = new StringBuilder();
        for (int x = 0; x < width; x++) {
            topBoundary.append('+');
            for (int i = 0; i < cellWidth; i++) {
                topBoundary.append('-');
            }
        }
        topBoundary.append("+\n");
        sb.append(topBoundary);

        // Prepare a set of nodes that are in the solution path for quick lookup
        Set<Node> solutionPath = new HashSet<>();
        if (solution != null && solution.path() != null) {
            solutionPath.addAll(solution.path());
        }

        for (int y = 0; y < height; y++) {
            StringBuilder rowTop = new StringBuilder("|");
            StringBuilder rowBottom = new StringBuilder("+");

            for (int x = 0; x < width; x++) {
                Node current = grid[y][x];
                String heightStr = String.valueOf(current.height());

                // Format height string to occupy (cellWidth) spaces, centered
                heightStr = centerString(heightStr, cellWidth);

                // Check if current node is part of the solution path
                if (solutionPath.contains(current)) {
                    // Highlight the cell with background color
                    rowTop.append(ANSI_BG_GREEN).append(heightStr).append(ANSI_RESET);
                } else {
                    // Regular cell with padding
                    rowTop.append(" ".repeat(cellWidth));
                }

                // East wall
                if (current.walls().hasWall(Node.Direction.EAST)) {
                    rowTop.append('|'); // Changed from "|" to '|'
                } else {
                    rowTop.append(' '); // Changed from " " to ' '
                }

                // South wall
                if (current.walls().hasWall(Node.Direction.SOUTH)) {
                    for (int i = 0; i < cellWidth; i++) {
                        rowBottom.append('-'); // Changed from "-" to '-'
                    }
                    rowBottom.append('+'); // Changed from "+" to '+'
                } else {
                    for (int i = 0; i < cellWidth; i++) {
                        rowBottom.append(' '); // Changed from " " to ' '
                    }
                    rowBottom.append('+'); // Changed from "+" to '+'
                }
            }
            rowTop.append('\n'); // Changed from "\n" to '\n'
            rowBottom.append('\n'); // Changed from "\n" to '\n'
            sb.append(rowTop);
            sb.append(rowBottom);
        }

        return sb.toString();
    }

    /**
     * Centers a string within a given width by padding with spaces.
     *
     * @param text  the string to center
     * @param width the total width of the resulting string
     * @return the centered string
     */
    private String centerString(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        int totalPadding = width - text.length();
        int paddingStart = totalPadding / 2;
        int paddingEnd = totalPadding - paddingStart;
        return " ".repeat(paddingStart) + text + " ".repeat(paddingEnd);
    }

    /**
     * Finds the maximum height value in the maze grid.
     *
     * @return the maximum height value
     */
    private int getMaxHeight() {
        int max = Integer.MIN_VALUE;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x].height() > max) {
                    max = grid[y][x].height();
                }
            }
        }
        return max;
    }
}
