package config;

import java.util.Objects;
import org.tomlj.TomlTable;

/**
 * Configuration class for visual output settings.
 */
public record VisualsConfig(boolean image, boolean console, int cellSize, String filename) {

    /**
     * Validates and initializes the visual output settings.
     *
     * @param filename the output filename
     * @param cellSize the size of the cell in the visual output
     */
    @SuppressWarnings("MagicNumber")
    public VisualsConfig {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty.");
        }
        if (cellSize < 10) {
            throw new IllegalArgumentException("Cell Size must be greater or equal to 10.");
        }
    }

    /**
     * Parses the TOML configuration into a {@link VisualsConfig}.
     *
     * @param toml the TOML table containing visual settings
     * @return the populated {@link VisualsConfig}
     */
    public static VisualsConfig fromToml(TomlTable toml) {
        boolean image = Objects.requireNonNull(toml.getBoolean("image"));
        boolean console = Objects.requireNonNull(toml.getBoolean("console"));
        String filename = Objects.requireNonNull(toml.getString("filename"));
        int cellSize = ((Number) Objects.requireNonNull(toml.get("cell_size"))).intValue();
        return new VisualsConfig(image, console, cellSize, filename);
    }
}
