package config;

import java.util.Objects;
import maze.solver.functions.CostFuncType;
import org.tomlj.TomlTable;

/**
 * Configuration class for cost function settings.
 */
public record CostFuncConfig(CostFuncType type, int penaltyTreshold) {
    public CostFuncConfig {
        if (penaltyTreshold <= 0) {
            throw new IllegalArgumentException("Penalty treshold must be positive.");
        }
    }

    /**
     * Parses the TOML configuration into a {@link CostFuncConfig}.
     *
     * @param toml the TOML table containing cost function settings
     * @return the populated {@link CostFuncConfig}
     */
    public static CostFuncConfig fromToml(TomlTable toml) {
        String typeStr = Objects.requireNonNull(toml.getString("type"));
        CostFuncType type = CostFuncType.fromString(typeStr);
        int penaltyTreshold = ((Number) Objects.requireNonNull(toml.get("penalty_treshold"))).intValue();
        return new CostFuncConfig(type, penaltyTreshold);
    }
}
