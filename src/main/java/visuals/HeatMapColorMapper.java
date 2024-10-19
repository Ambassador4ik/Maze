package visuals;

import java.awt.Color;

/**
 * Utility class for mapping height values to colors using a terrain-like colormap.
 */
public class HeatMapColorMapper {

    /**
     * Represents a color stop in the colormap.
     */
    private static class ColorStop {
        double position; // Normalized position (0 to 1)
        Color color;

        ColorStop(double position, Color color) {
            this.position = position;
            this.color = color;
        }
    }

    // Define the color stops based on Matplotlib's cm.terrain
    private static final ColorStop[] COLOR_STOPS = new ColorStop[] {
        new ColorStop(0.0, new Color(0, 0, 128)),       // Deep Water - Dark Blue
        new ColorStop(0.2, new Color(0, 0, 255)),       // Shallow Water - Blue
        new ColorStop(0.25, new Color(240, 230, 140)),  // Beach - Khaki
        new ColorStop(0.4, new Color(34, 139, 34)),     // Lowlands - Forest Green
        new ColorStop(0.6, new Color(139, 69, 19)),     // Highlands - Saddle Brown
        new ColorStop(0.8, new Color(255, 250, 250)),   // Mountains - Snow
        new ColorStop(1.0, new Color(255, 250, 250))    // Peaks - Snow
    };

    /**
     * Maps a height value to a Color object using the terrain-like colormap.
     *
     * @param height     The height value of the node.
     * @param minHeight  The minimum height in the maze.
     * @param maxHeight  The maximum height in the maze.
     * @return The Color corresponding to the height.
     */
    public static Color getColorForHeight(int height, int minHeight, int maxHeight) {
        if (maxHeight == minHeight) {
            // Avoid division by zero; return middle color
            return new Color(34, 139, 34); // Forest Green
        }

        // Normalize the height to a value between 0 and 1
        double ratio = (double) (height - minHeight) / (maxHeight - minHeight);
        ratio = Math.max(0.0, Math.min(1.0, ratio)); // Clamp between 0 and 1

        // Find the two color stops between which the ratio falls
        ColorStop lower = COLOR_STOPS[0];
        ColorStop upper = COLOR_STOPS[COLOR_STOPS.length - 1];

        for (int i = 0; i < COLOR_STOPS.length - 1; i++) {
            if (ratio >= COLOR_STOPS[i].position && ratio <= COLOR_STOPS[i + 1].position) {
                lower = COLOR_STOPS[i];
                upper = COLOR_STOPS[i + 1];
                break;
            }
        }

        // Calculate the relative position between the two stops
        double range = upper.position - lower.position;
        double relativePos = (ratio - lower.position) / range;

        // Interpolate the color components
        int red = (int) (lower.color.getRed() + relativePos * (upper.color.getRed() - lower.color.getRed()));
        int green = (int) (lower.color.getGreen() + relativePos * (upper.color.getGreen() - lower.color.getGreen()));
        int blue = (int) (lower.color.getBlue() + relativePos * (upper.color.getBlue() - lower.color.getBlue()));

        return new Color(red, green, blue);
    }
}


