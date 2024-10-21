package config;

import org.tomlj.TomlTable;
import java.util.Objects;

public record PerlinConfig(double scale, int octaves, double persistence, double lacunarity) {
    public static PerlinConfig fromToml(TomlTable toml) {
        double scale = Objects.requireNonNull(toml.getDouble("scale"));
        int octaves = ((Number) Objects.requireNonNull(toml.get("octaves"))).intValue();
        double persistence = Objects.requireNonNull(toml.getDouble("persistence"));
        double lacunarity = Objects.requireNonNull(toml.getDouble("lacunarity"));

        return new PerlinConfig(scale, octaves, persistence, lacunarity);
    }
}
