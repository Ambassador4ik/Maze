package config;

import java.util.Objects;
import maze.solver.SolverType;
import org.tomlj.TomlTable;

/**
 * Configuration class for solver settings.
 */
public record SolverConfig(SolverType type) {

    /**
     * Parses the TOML configuration into a {@link SolverConfig}.
     *
     * @param toml the TOML table containing solver settings
     * @return the populated {@link SolverConfig}
     */
    public static SolverConfig fromToml(TomlTable toml) {
        String typeStr = Objects.requireNonNull(toml.getString("type"));
        SolverType type = SolverType.fromString(typeStr);
        return new SolverConfig(type);
    }
}
