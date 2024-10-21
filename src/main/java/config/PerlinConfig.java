package config;

import java.util.Objects;
import org.tomlj.TomlTable;

/**
 * Configuration class for Perlin noise settings.
 */
public record PerlinConfig(double scale, int octaves, double persistence, double lacunarity) {

    /**
     * Validates and initializes the Perlin noise settings.
     *
     * @param scale the scale of the noise
     * @param octaves the number of octaves for the noise
     * @param persistence the persistence factor for the noise
     * @param lacunarity the lacunarity factor for the noise
     */
    public PerlinConfig {
        if (scale <= 0) {
            throw new IllegalArgumentException("Scale must be positive");
        }
        if (octaves <= 0) {
            throw new IllegalArgumentException("Octaves must be greater than zero");
        }
        if (persistence <= 0 || persistence > 1) {
            throw new IllegalArgumentException("Persistence must be in the range (0, 1]");
        }
        if (lacunarity <= 1) {
            throw new IllegalArgumentException("Lacunarity must be greater than 1");
        }
    }

    /**
     * Parses the TOML configuration into a {@link PerlinConfig}.
     *
     * @param toml the TOML table containing Perlin noise settings
     * @return the populated {@link PerlinConfig}
     */
    public static PerlinConfig fromToml(TomlTable toml) {
        double scale = Objects.requireNonNull(toml.getDouble("scale"));
        int octaves = ((Number) Objects.requireNonNull(toml.get("octaves"))).intValue();
        double persistence = Objects.requireNonNull(toml.getDouble("persistence"));
        double lacunarity = Objects.requireNonNull(toml.getDouble("lacunarity"));

        return new PerlinConfig(scale, octaves, persistence, lacunarity);
    }
}
