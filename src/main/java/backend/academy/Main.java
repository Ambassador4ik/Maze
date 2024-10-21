package backend.academy;

import config.Config;
import config.Configuration;
import heightmap.HeightMapProviderFactory;
import heightmap.providers.HeightMapProvider;
import java.awt.Color;
import lombok.experimental.UtilityClass;
import maze.Maze;
import maze.MazeFactory;
import util.OutputHandler;
import visuals.MazeVisualizer;

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

        OutputHandler.println("Solution cost with " + config.costFunc().type() + ": " + maze.solution().totalCost());
        OutputHandler.println("Solution length: " + maze.solution().path().size());

        if (config.visuals().console()) {
            OutputHandler.println(maze.getMazeAsString());
        }

        if (config.visuals().image()) {
            MazeVisualizer visualizer = new MazeVisualizer(maze, config.visuals().cellSize());
            visualizer.drawSolution(maze.solution(), Color.BLUE);
            visualizer.saveImage(config.visuals().filename());
        }
    }
}
