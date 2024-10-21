package config;

import heightmap.providers.ProviderType;
import org.tomlj.TomlTable;
import java.util.Objects;

public record SurfaceConfig(ProviderType type, int heightRange) {
    public static SurfaceConfig fromToml(TomlTable toml) {
        String typeStr = Objects.requireNonNull(toml.getString("type"));
        ProviderType type = ProviderType.fromString(typeStr);
        int heightRange = ((Number) Objects.requireNonNull(toml.get("height_range"))).intValue();
        return new SurfaceConfig(type, heightRange);
    }
}
