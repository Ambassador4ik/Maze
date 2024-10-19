package maze;

import lombok.Getter;
import util.Pair;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Maze {
    @Getter private Node[][] grid;
    private final SecureRandom random;

    private final MazeGenerator generator;
    private final MazeSolver solver;

    public Maze(MazeGenerator generator, MazeSolver solver) {
        this.random = new SecureRandom();
        this.generator = generator;
        this.solver = solver;

        this.generator.generate(grid);
    }

    public void init() {
        Stack<Node> stack = new Stack<>();
        Node curr = grid[0][0];
        curr.visited(true);
        stack.push(curr);

        while (!stack.empty()) {
            curr = stack.peek();
            List<Pair<Node.Direction, Node>> unvisited = getUnvisitedNeighbors(curr);

            if (unvisited.isEmpty()) {
                Pair<Node.Direction, Node> selected = unvisited.get(random.nextInt(unvisited.size()));
                Node.Direction direction = selected.key();
                Node next = selected.value();

                next.visited(true);
                stack.push(next);
            }
            else {
                stack.pop();
            }

        }
    }

    private List<Pair<Node.Direction, Node>> getUnvisitedNeighbors(Node curr) {
        List<Pair<Node.Direction, Node>> neighbors = new ArrayList<>();

        int x = curr.x();
        int y = curr.y();

        int width = grid.length;
        int height = grid[0].length;

        if (x > 0 && !grid[x-1][y].visited()) {
            neighbors.add(new Pair<>(Node.Direction.WEST, grid[x-1][y]));
        }
        if (x < width - 1 && !grid[x+1][y].visited()) {
            neighbors.add(new Pair<>(Node.Direction.WEST, grid[x-1][y]));
        }
        if (y > 0 && !grid[x][y-1].visited()) {
            neighbors.add(new Pair<>(Node.Direction.WEST, grid[x-1][y]));
        }
        if (y < height - 1 && !grid[x][y+1].visited()) {
            neighbors.add(new Pair<>(Node.Direction.WEST, grid[x-1][y]));
        }

        return neighbors;
    }

    private static Node.Direction getOpposite(Node.Direction direction) {
        switch (direction) {
            case WEST -> {
                return Node.Direction.EAST;
            }
            case EAST -> {
                return Node.Direction.WEST;
            }
            case NORTH -> {
                return Node.Direction.SOUTH;
            }
            case SOUTH -> {
                return Node.Direction.NORTH;
            }
            default -> {
                throw new IllegalArgumentException("Invalid Direction!");
            }
        }
    }
}
