package config;

import lombok.Getter;
import org.tomlj.TomlTable;
import java.util.Map;
import java.util.Objects;

public record MazeConfig(int height, int width) {
    public MazeConfig {
        if (height <= 0) {
            throw new IllegalArgumentException("Maze height must be positive.");
        }
        if (width <= 0) {
            throw new IllegalArgumentException("Maze width must be positive.");
        }
    }

    public static MazeConfig fromToml(TomlTable toml) {
        int height = ((Number) Objects.requireNonNull(toml.get("height"))).intValue();
        int width = ((Number) Objects.requireNonNull(toml.get("width"))).intValue();
        return new MazeConfig(height, width);
    }
}
