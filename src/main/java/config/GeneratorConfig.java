package config;

import maze.generator.GeneratorType;
import org.tomlj.TomlTable;
import java.util.Objects;

public record GeneratorConfig(GeneratorType type, double loopProbability) {
    public GeneratorConfig(GeneratorType type, double loopProbability) {
        if (loopProbability < 0.0 || loopProbability > 1.0) {
            throw new IllegalArgumentException("Loop probability must be in [0, 1].");
        }
        this.loopProbability = loopProbability;

        this.type = type;
    }

    public static GeneratorConfig fromToml(TomlTable toml) {
        String typeStr = Objects.requireNonNull(toml.getString("type"));
        GeneratorType type = GeneratorType.fromString(typeStr);
        double loopProbability = Objects.requireNonNull(toml.getDouble("loop_probability"));
        return new GeneratorConfig(type, loopProbability);
    }
}
