package maze.generator;

import maze.Node;

import java.security.SecureRandom;

import static maze.Maze.*; // Ensure Maze class has the necessary static methods

public abstract class AbstractMazeGenerator implements MazeGenerator {
    // Probability to remove an additional wall to create loops
    protected static final double LOOP_PROBABILITY = 0.05;
    protected final SecureRandom random;

    public AbstractMazeGenerator() {
        this.random = new SecureRandom();
    }

    @Override
    public void generate(Node[][] grid) {
        int height = grid.length;
        if (height == 0) return;
        int width = grid[0].length;

        initializeGrid(grid, height, width);

        generateMaze(grid, height, width);

        addLoops(grid, width, height, LOOP_PROBABILITY);

        setupExits(grid, width, height);
    }

    // Initialize all cells as unvisited
    private void initializeGrid(Node[][] grid, int height, int width) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x].visited(false);
            }
        }
    }

    // Abstract method to be implemented by specific algorithms
    protected abstract void generateMaze(Node[][] grid, int height, int width);
}
