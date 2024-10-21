package backend.academy;

import config.Config;
import config.ConfigLoader;
import config.Configuration;
import heightmap.HeightMapProviderFactory;
import heightmap.params.PerlinNoiseParams;
import heightmap.providers.HeightMapProvider;
import heightmap.providers.ProviderType;
import lombok.experimental.UtilityClass;
import maze.Maze;
import maze.MazeFactory;
import maze.generator.GeneratorType;
import maze.solver.SolverType;
import maze.solver.algorithms.JohnsonsSolver;
import maze.solver.functions.ConstantCostFunc;
import maze.solver.functions.CostFuncType;
import maze.solver.functions.TanhCostFunc;
import visuals.MazeVisualizer;

import java.awt.Color;
import java.io.IOException;

@UtilityClass
public class Main {
    public static void main(String[] args) throws IOException {
        Configuration config = Config.getInstance();

        HeightMapProvider heightMapProvider = HeightMapProviderFactory.createProvider(
            ProviderType.PERLIN_NOISE,
            new PerlinNoiseParams(
                config.surface().heightRange()
            )
        );

        Maze maze = MazeFactory.createSolvedMaze(
            config.maze().width(),
            config.maze().height(),
            heightMapProvider,
            config.generator().type(),
            config.solver().type(),
            config.costFunc().type()
        );


        System.out.println(maze.solution().totalCost() + " " + maze.solution().path().size());

        MazeVisualizer visualizer = new MazeVisualizer(maze, 20);
        visualizer.drawSolution(maze.solution(), Color.BLUE);

        maze.solveWith(new JohnsonsSolver(new ConstantCostFunc()));
        visualizer.drawSolution(maze.solution(), Color.MAGENTA);


        //maze.solution().printSolutionToFile("solution1.txt");

        //maze.solveWith(new AStarSolver(new ConstantCostFunc()));
        //System.out.println(maze.solution().costWith(new NonLinearCostFunc()));
        //maze.solution().printSolutionToFile("solution2.txt");


        System.out.println("Cost of const as tangent: " + maze.solution().costWith(new TanhCostFunc()) + " " + maze.solution().path().size());
        visualizer.saveImage("img.png");
    }
}
