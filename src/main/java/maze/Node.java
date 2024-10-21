package maze;

import java.util.EnumMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a node (cell) in a maze with coordinates, height, walls, and a visited flag.
 */
@Getter
@Setter
public class Node {
    private final int x;
    private final int y;
    private int height;
    private final Walls walls;
    private boolean visited;

    /**
     * Constructs a Node with specified coordinates and height.
     *
     * @param x the x-coordinate of the node
     * @param y the y-coordinate of the node
     * @param height the height of the node
     */
    public Node(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.walls = new Walls();
        this.visited = false;
    }

    /**
     * Constructs a Node with specified coordinates, height, and walls.
     *
     * @param x the x-coordinate of the node
     * @param y the y-coordinate of the node
     * @param height the height of the node
     * @param walls the walls of the node
     */
    public Node(int x, int y, int height, Walls walls) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.walls = walls;
        this.visited = false;
    }

    /**
     * Enum representing possible wall directions.
     */
    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    /**
     * Represents the walls of a node using an EnumMap for efficiency.
     */
    @Getter
    @Setter
    public static class Walls {
        private final Map<Direction, Boolean> walls;

        /**
         * Initializes all walls to be present.
         */
        public Walls() {
            walls = new EnumMap<>(Direction.class);
            for (Direction direction : Direction.values()) {
                walls.put(direction, Boolean.TRUE);
            }
        }

        /**
         * Checks if a wall exists in the given direction.
         *
         * @param direction the direction to check
         * @return true if the wall exists, false otherwise
         */
        public boolean hasWall(Direction direction) {
            return walls.getOrDefault(direction, Boolean.FALSE);
        }

        /**
         * Sets the presence of a wall in the given direction.
         *
         * @param direction the direction to set
         * @param exists whether the wall exists
         */
        public void setWall(Direction direction, boolean exists) {
            walls.put(direction, exists);
        }
    }
}
