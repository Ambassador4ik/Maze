package config;

import lombok.Getter;
import maze.solver.SolverType;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;
import java.util.Map;
import java.util.Objects;

public record SolverConfig(SolverType type) {

    public static SolverConfig fromToml(TomlTable toml) {
        String typeStr = Objects.requireNonNull(toml.getString("type"));
        SolverType type = SolverType.fromString(typeStr);
        return new SolverConfig(type);
    }
}
