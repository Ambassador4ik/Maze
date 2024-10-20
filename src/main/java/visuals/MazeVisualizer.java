package visuals;

import maze.Maze;
import maze.Node;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Utility class for visualizing the maze.
 */
public class MazeVisualizer {

    /**
     * Generates an image of the maze with walls, a heatmap based on heights, and the solution path.
     *
     * @param maze      The Maze object containing the grid and solution.
     * @param cellSize  The size of each cell in pixels.
     * @param filename  The filename to save the image (e.g., "maze.png").
     */
    public static void generateMazeImage(Maze maze, int cellSize, String filename) {
        Node[][] grid = maze.grid();
        int height = grid.length;
        int width = grid[0].length;

        // Determine min and max heights for color mapping
        int minHeight = Integer.MAX_VALUE;
        int maxHeight = Integer.MIN_VALUE;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int nodeHeight = grid[y][x].height();
                if (nodeHeight < minHeight) minHeight = nodeHeight;
                if (nodeHeight > maxHeight) maxHeight = nodeHeight;
            }
        }

        // Create a buffered image
        int imgWidth = width * cellSize;
        int imgHeight = height * cellSize;
        BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        // Fill background with white
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imgWidth, imgHeight);

        // Draw heatmap
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Node node = grid[y][x];
                Color color = HeatMapColorMapper.getColorForHeight(node.height(), minHeight, maxHeight);
                g.setColor(color);
                g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }

        // Draw walls
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Node node = grid[y][x];
                int px = x * cellSize;
                int py = y * cellSize;

                // Draw north wall
                if (node.walls().hasWall(Node.Direction.NORTH)) {
                    g.drawLine(px, py, px + cellSize, py);
                }
                // Draw south wall
                if (node.walls().hasWall(Node.Direction.SOUTH)) {
                    g.drawLine(px, py + cellSize, px + cellSize, py + cellSize);
                }
                // Draw west wall
                if (node.walls().hasWall(Node.Direction.WEST)) {
                    g.drawLine(px, py, px, py + cellSize);
                }
                // Draw east wall
                if (node.walls().hasWall(Node.Direction.EAST)) {
                    g.drawLine(px + cellSize, py, px + cellSize, py + cellSize);
                }
            }
        }

        // Highlight solution path if available
        if (maze.solution() != null) {
            g.setColor(Color.BLUE); // Choose a contrasting color
            g.setStroke(new BasicStroke(cellSize / 3)); // Thicker line for visibility

            List<Node> path = maze.solution().path();
            for (int i = 0; i < path.size() - 1; i++) {
                Node current = path.get(i);
                Node next = path.get(i + 1);
                int x1 = current.x() * cellSize + cellSize / 2;
                int y1 = current.y() * cellSize + cellSize / 2;
                int x2 = next.x() * cellSize + cellSize / 2;
                int y2 = next.y() * cellSize + cellSize / 2;
                g.drawLine(x1, y1, x2, y2);
            }
        }

        // Dispose graphics
        g.dispose();

        // Save the image to a file
        try {
            ImageIO.write(image, "PNG", new File(filename));
            System.out.println("Maze image saved as " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

