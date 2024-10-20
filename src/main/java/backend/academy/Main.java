package backend.academy;

import heightmap.HeightMapProviderFactory;
import heightmap.params.PerlinNoiseParams;
import heightmap.providers.HeightMapProvider;
import heightmap.providers.PerlinNoiseHMProvider;
import heightmap.providers.ProviderType;
import heightmap.providers.RandomHMProvider;
import lombok.experimental.UtilityClass;
import maze.Maze;
import maze.MazeFactory;
import maze.Node;
import maze.generator.DFSMazeGenerator;
import maze.generator.GeneratorType;
import maze.solver.AStarSolver;
import maze.solver.SolverType;
import maze.solver.functions.ConstantCostFunc;
import maze.solver.functions.CostFuncType;
import maze.solver.functions.NonLinearCostFunc;
import visuals.MazeVisualizer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@UtilityClass
public class Main {
    public static void main(String[] args) {

        HeightMapProvider heightMapProvider = HeightMapProviderFactory.createProvider(
            ProviderType.PERLIN_NOISE,
            new PerlinNoiseParams(20, 10.0, 6, 0.5, 2.0)
        );

        Maze maze = MazeFactory.createSolvedMaze(
            100,
            100,
            heightMapProvider,
            GeneratorType.DFS,
            SolverType.ASTAR,
            CostFuncType.NON_LINEAR
        );

        maze.solution().printSolutionToFile("solution1.txt");
        System.out.println(maze.solution().totalCost());
        //maze.solveWith(new AStarSolver(new ConstantCostFunc()));
        //System.out.println(maze.solution().costWith(new NonLinearCostFunc()));
        //maze.solution().printSolutionToFile("solution2.txt");

        // Define output file path
        String outputFilePath = "heights_and_maze.txt"; // Updated file name for clarity

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    Node node = maze.grid()[i][j];
                    int height = node.height(); // Assuming height() is a getter method

                    // Retrieve wall information: 1 for present, 0 for absent
                    int north = node.walls().hasWall(Node.Direction.NORTH) ? 1 : 0;
                    int south = node.walls().hasWall(Node.Direction.SOUTH) ? 1 : 0;
                    int east  = node.walls().hasWall(Node.Direction.EAST)  ? 1 : 0;
                    int west  = node.walls().hasWall(Node.Direction.WEST)  ? 1 : 0;

                    // Format: height,N,S,E,W
                    String cellData = String.format("%d,%d,%d,%d,%d", height, north, south, east, west);
                    writer.write(cellData + " ");
                }
                writer.write("\n");
                // Each row in the file corresponds to a row in the grid
            }
            System.out.println("Height map and maze structure successfully written to " + outputFilePath);
        } catch (IOException e) {
            System.err.println("An error occurred while writing the data to the file:");
            e.printStackTrace();
        }

        MazeVisualizer.generateMazeImage(maze, 10, "img.png");
    }
}
