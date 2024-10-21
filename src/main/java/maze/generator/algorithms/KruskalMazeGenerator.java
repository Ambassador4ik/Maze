package maze.generator.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import maze.Node;
import maze.Node.Direction;
import maze.generator.AbstractMazeGenerator;
import util.Pair;
import static maze.Maze.getNeighborCoordinates;
import static maze.Maze.removeWall;

public class KruskalMazeGenerator extends AbstractMazeGenerator {
    public KruskalMazeGenerator() {
    }

    @Override
    protected void generateMaze(Node[][] grid, int height, int width) {
        // Initialize disjoint sets for each cell
        DisjointSet ds = new DisjointSet(width * height);

        // List of all walls between cells
        List<Wall> walls = new ArrayList<>();

        // Populate the walls list
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Node current = grid[y][x];
                for (Direction dir : Direction.values()) {
                    Pair<Integer, Integer> neighborCoords = getNeighborCoordinates(current, dir, width, height);
                    if (neighborCoords != null) {
                        int nx = neighborCoords.key();
                        int ny = neighborCoords.value();
                        // To avoid duplicate walls, only add walls where neighbor is to the west or south
                        if (dir == Direction.WEST || dir == Direction.SOUTH) {
                            walls.add(new Wall(x, y, dir));
                        }
                    }
                }
            }
        }

        // Shuffle the walls randomly
        Collections.shuffle(walls, random);

        // Process each wall in random order
        for (Wall wall : walls) {
            int cell1 = wall.y * width + wall.x;
            Pair<Integer, Integer> neighborCoords = getNeighborCoordinates(grid[wall.y][wall.x], wall.direction,
                width, height);
            if (neighborCoords == null) {
                continue;
            }
            int nx = neighborCoords.key();
            int ny = neighborCoords.value();
            int cell2 = ny * width + nx;

            // If cells are in different sets, remove the wall and union the sets
            if (ds.find(cell1) != ds.find(cell2)) {
                Node node1 = grid[wall.y][wall.x];
                Node node2 = grid[ny][nx];
                removeWall(node1, node2, wall.direction);
                ds.union(cell1, cell2);
                node1.visited(true);
                node2.visited(true);
            }
        }
    }

    // Helper class to represent walls between cells
    private static class Wall {
        int x;
        int y;
        Direction direction;

        Wall(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }

    // Disjoint Set (Union-Find) data structure for Kruskal's algorithm
    private static class DisjointSet {
        private final int[] parent;
        private final int[] rank;

        DisjointSet(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }

        void union(int x, int y) {
            int xRoot = find(x);
            int yRoot = find(y);
            if (xRoot == yRoot) {
                return;
            }

            // Union by rank
            if (rank[xRoot] < rank[yRoot]) {
                parent[xRoot] = yRoot;
            } else if (rank[xRoot] > rank[yRoot]) {
                parent[yRoot] = xRoot;
            } else {
                parent[yRoot] = xRoot;
                rank[xRoot]++;
            }
        }
    }
}
