package config;

import maze.solver.functions.CostFuncType;
import org.tomlj.TomlTable;
import java.util.Objects;

public record CostFuncConfig(CostFuncType type) {
    public static CostFuncConfig fromToml(TomlTable toml) {
        String typeStr = Objects.requireNonNull(toml.getString("type"));
        CostFuncType type = CostFuncType.fromString(typeStr);
        return new CostFuncConfig(type);
    }
}
