package maze.solver;

import maze.Node;

public interface MazeSolver {
    MazeSolution solve(Node[][] grid);
}
