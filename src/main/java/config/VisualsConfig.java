package config;

import lombok.Getter;
import org.tomlj.TomlTable;
import java.util.Map;
import java.util.Objects;

public record VisualsConfig(boolean image, boolean console, int cellSize, String filename) {
    public VisualsConfig(boolean image, boolean console, int cellSize, String filename) {
        this.image = image;
        this.console = console;
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty.");
        }
        this.filename = filename;
        if (cellSize < 10) {
            throw new IllegalArgumentException("Cell Size must be greater or equal to 10.");
        }
        this.cellSize = cellSize;
    }

    public static VisualsConfig fromToml(TomlTable toml) {
        boolean image = Objects.requireNonNull(toml.getBoolean("image"));
        boolean console = Objects.requireNonNull(toml.getBoolean("console"));
        String filename = Objects.requireNonNull(toml.getString("filename"));
        int cellSize = ((Number) Objects.requireNonNull(toml.get("cell_size"))).intValue();
        return new VisualsConfig(image, console, cellSize, filename);
    }
}
