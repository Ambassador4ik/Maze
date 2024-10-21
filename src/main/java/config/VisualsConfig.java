package config;

import lombok.Getter;
import org.tomlj.TomlTable;
import java.util.Map;
import java.util.Objects;

public record VisualsConfig(boolean image, boolean console, String filename) {
    public VisualsConfig(boolean image, boolean console, String filename) {
        this.image = image;
        this.console = console;
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty.");
        }
        this.filename = filename;
    }

    public static VisualsConfig fromToml(TomlTable toml) {
        boolean image = Objects.requireNonNull(toml.getBoolean("image"));
        boolean console = Objects.requireNonNull(toml.getBoolean("console"));
        String filename = Objects.requireNonNull(toml.getString("filename"));
        return new VisualsConfig(image, console, filename);
    }
}
