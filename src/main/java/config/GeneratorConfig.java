package config;

import java.util.Objects;
import maze.generator.GeneratorType;
import org.tomlj.TomlTable;

/**
 * Configuration class for maze generator settings.
 */
public record GeneratorConfig(GeneratorType type, double loopProbability) {

    /**
     * Validates and initializes the configuration.
     *
     * @param loopProbability the probability of loops in the maze
     */
    public GeneratorConfig {
        if (loopProbability < 0.0 || loopProbability > 1.0) {
            throw new IllegalArgumentException("Loop probability must be in [0, 1].");
        }
    }

    /**
     * Parses the TOML configuration into a {@link GeneratorConfig}.
     *
     * @param toml the TOML table containing generator settings
     * @return the populated {@link GeneratorConfig}
     */
    public static GeneratorConfig fromToml(TomlTable toml) {
        String typeStr = Objects.requireNonNull(toml.getString("type"));
        GeneratorType type = GeneratorType.fromString(typeStr);
        double loopProbability = Objects.requireNonNull(toml.getDouble("loop_probability"));
        return new GeneratorConfig(type, loopProbability);
    }
}
