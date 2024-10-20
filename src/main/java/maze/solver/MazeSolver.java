package maze.solver;

import maze.Node;
import maze.solver.structs.MazeSolution;

public interface MazeSolver {
    MazeSolution solve(Node[][] grid);
}
