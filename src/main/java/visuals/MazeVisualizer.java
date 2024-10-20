package visuals;

import maze.Maze;
import maze.Node;
import maze.solver.structs.MazeSolution;
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
    private final BufferedImage img;
    private final Graphics2D graphics;

    private final int cellSize;

    public MazeVisualizer(Maze maze, int cellSize) {
        Node[][] grid = maze.grid();
        this.cellSize = cellSize;

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

        int imgWidth = width * cellSize;
        int imgHeight = height * cellSize;

        this.img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
        this.graphics = img.createGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, imgWidth, imgHeight);

        // Draw heatmap
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Node node = grid[y][x];
                Color color = HeatMapColorMapper.getColorForHeight(node.height(), minHeight, maxHeight);
                graphics.setColor(color);
                graphics.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }

        // Draw walls
        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke(2));
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Node node = grid[y][x];
                int px = x * cellSize;
                int py = y * cellSize;

                // Draw north wall
                if (node.walls().hasWall(Node.Direction.NORTH)) {
                    graphics.drawLine(px, py, px + cellSize, py);
                }
                // Draw south wall
                if (node.walls().hasWall(Node.Direction.SOUTH)) {
                    graphics.drawLine(px, py + cellSize, px + cellSize, py + cellSize);
                }
                // Draw west wall
                if (node.walls().hasWall(Node.Direction.WEST)) {
                    graphics.drawLine(px, py, px, py + cellSize);
                }
                // Draw east wall
                if (node.walls().hasWall(Node.Direction.EAST)) {
                    graphics.drawLine(px + cellSize, py, px + cellSize, py + cellSize);
                }
            }
        }
    }

    public void drawSolution(MazeSolution solution, Color color) {
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke((float) cellSize / 3));

        List<Node> path = solution.path();
        for (int i = 0; i < path.size() - 1; i++) {
            Node current = path.get(i);
            Node next = path.get(i + 1);
            int x1 = current.x() * cellSize + cellSize / 2;
            int y1 = current.y() * cellSize + cellSize / 2;
            int x2 = next.x() * cellSize + cellSize / 2;
            int y2 = next.y() * cellSize + cellSize / 2;
            graphics.drawLine(x1, y1, x2, y2);
        }
    }

    public void saveImage(String path) {
        graphics.dispose();

        try {
            ImageIO.write(img, "PNG", new File(path));
        } catch (Exception e) {
            System.out.println("dskjfksajhfkds");
        }
    }
}

