package config;

import maze.generator.GeneratorType;
import org.tomlj.TomlTable;
import java.util.Objects;

public record GeneratorConfig(GeneratorType type, double loopProbability) {
    public static GeneratorConfig fromToml(TomlTable toml) {
        String typeStr = Objects.requireNonNull(toml.getString("type"));
        GeneratorType type = GeneratorType.fromString(typeStr);
        double loopProbability = Objects.requireNonNull(toml.getDouble("loop_probability"));
        return new GeneratorConfig(type, loopProbability);
    }
}
