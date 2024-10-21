package backend.academy;

import config.Config;
import config.ConfigLoader;
import config.Configuration;
import heightmap.HeightMapProviderFactory;
import heightmap.params.PerlinNoiseParams;
import heightmap.params.ProviderParams;
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
    public static void main(String[] args) {
        Configuration config = Config.getInstance();

        HeightMapProvider heightMapProvider = HeightMapProviderFactory.createProvider(
            config.surface().type(),
            config.surface().heightRange()
        );

        Maze maze = MazeFactory.createSolvedMaze(
            config.maze().width(),
            config.maze().height(),
            heightMapProvider,
            config.generator().type(),
            config.solver().type(),
            config.costFunc().type()
        );

        System.out.println("Solution cost with " + config.costFunc().type() + ": " + maze.solution().totalCost());
        System.out.println("Solution length: " + maze.solution().path().size());

        if (config.visuals().console()) {
            System.out.println(maze.getMazeAsString());
        }

        if (config.visuals().image()) {
            MazeVisualizer visualizer = new MazeVisualizer(maze, config.visuals().cellSize());
            visualizer.drawSolution(maze.solution(), Color.BLUE);
            visualizer.saveImage(config.visuals().filename());
        }
    }
}
