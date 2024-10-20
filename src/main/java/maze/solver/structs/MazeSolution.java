package maze.solver.structs;

import lombok.Getter;
import maze.Node;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import maze.solver.functions.CostFunc;

/**
 * Represents the solution to a maze, containing the path from start to goal and the total cost.
 */
public class MazeSolution {
    @Getter private final List<Node> path;
    @Getter private final double totalCost; // New field to store total cost

    /**
     * Constructs a MazeSolution with the specified path and total cost.
     *
     * @param path the list of nodes representing the path from start to goal
     * @param totalCost the total cost of the path
     */
    public MazeSolution(List<Node> path, double totalCost) {
        this.path = path;
        this.totalCost = totalCost;
    }

    /**
     * Prints the solution path and total cost to the console.
     */
    public void printSolution() {
        for (Node node : path) {
            System.out.println("(" + node.x() + ", " + node.y() + ")");
        }
        System.out.println("Total Cost: " + totalCost); // Print total cost
    }

    /**
     * Prints the solution path and total cost to a file.
     *
     * @param filename the name of the file to write the solution to
     */
    public void printSolutionToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Node node : path) {
                writer.write("(" + node.x() + ", " + node.y() + ")");
                writer.newLine();  // Adds a new line after each node
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recalculates the total cost of the path using the provided CostFunc.
     *
     * <p>
     * This method iterates through the path and computes the sum of costs between consecutive nodes
     * based on the given CostFunc.
     * </p>
     *
     * @param costFunc the cost function to use for recalculating the path cost
     * @return the total cost of the path using the provided CostFunc
     * @throws IllegalArgumentException if the costFunc is null
     */
    public double costWith(CostFunc costFunc) {
        if (costFunc == null) {
            throw new IllegalArgumentException("CostFunc cannot be null.");
        }

        if (path == null || path.size() < 2) {
            return 0.0; // No movement, hence no cost
        }

        double total = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            Node current = path.get(i);
            Node next = path.get(i + 1);
            total += costFunc.calculateCost(current.height(), next.height());
        }
        return total;
    }
}
