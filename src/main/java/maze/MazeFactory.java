package maze;

import heightmap.providers.HeightMapProvider;
import maze.generator.GeneratorType;
import maze.generator.MazeGenerator;
import maze.solver.MazeSolver;
import maze.solver.SolverType;
import maze.solver.functions.CostFunc;
import maze.solver.functions.CostFuncType;

public class MazeFactory {
    public static Maze createSolvedMaze(
        int width,
        int height,
        HeightMapProvider hmProvider,
        GeneratorType generatorType,
        SolverType solverType,
        CostFuncType costFuncType
    ) {
        MazeGenerator generator = generatorType.createGenerator();
        CostFunc costFunc = costFuncType.createCostFunc();
        MazeSolver solver = solverType.createSolver(costFunc);

        Maze maze = new Maze(width, height);

        maze.setupSurfaceWith(hmProvider);
        maze.initWith(generator);
        maze.solveWith(solver);

        return maze;
    }
}
