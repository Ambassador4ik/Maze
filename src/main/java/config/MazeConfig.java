package config;

import java.util.Objects;
import org.tomlj.TomlTable;

/**
 * Configuration class for maze dimensions.
 */
public record MazeConfig(int height, int width) {

    /**
     * Validates and initializes the maze dimensions.
     *
     * @param height the height of the maze
     * @param width the width of the maze
     */
    public MazeConfig {
        if (height <= 0) {
            throw new IllegalArgumentException("Maze height must be positive.");
        }
        if (width <= 0) {
            throw new IllegalArgumentException("Maze width must be positive.");
        }
    }

    /**
     * Parses the TOML configuration into a {@link MazeConfig}.
     *
     * @param toml the TOML table containing maze dimension settings
     * @return the populated {@link MazeConfig}
     */
    public static MazeConfig fromToml(TomlTable toml) {
        int height = ((Number) Objects.requireNonNull(toml.get("height"))).intValue();
        int width = ((Number) Objects.requireNonNull(toml.get("width"))).intValue();
        return new MazeConfig(height, width);
    }
}
