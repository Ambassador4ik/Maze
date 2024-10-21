package config;

import heightmap.providers.ProviderType;
import java.util.Objects;
import org.tomlj.TomlTable;

/**
 * Configuration class for surface generation settings.
 */
public record SurfaceConfig(ProviderType type, int heightRange) {

    /**
     * Validates and initializes the surface generation settings.
     *
     * @param heightRange the height range for the surface
     */
    public SurfaceConfig {
        if (heightRange <= 0) {
            throw new IllegalArgumentException("Height range must be a positive integer");
        }
        Objects.requireNonNull(type, "Provider type cannot be null");
    }

    /**
     * Parses the TOML configuration into a {@link SurfaceConfig}.
     *
     * @param toml the TOML table containing surface settings
     * @return the populated {@link SurfaceConfig}
     */
    public static SurfaceConfig fromToml(TomlTable toml) {
        String typeStr = Objects.requireNonNull(toml.getString("type"));
        ProviderType type = ProviderType.fromString(typeStr);
        int heightRange = ((Number) Objects.requireNonNull(toml.get("height_range"))).intValue();
        return new SurfaceConfig(type, heightRange);
    }
}
