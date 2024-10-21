package config;

import java.io.IOException;
import lombok.experimental.UtilityClass;

/**
 * Utility class for loading and providing access to the global {@link Configuration} instance.
 */
@UtilityClass
public class Config {
    private static final Configuration INSTANCE;

    static {
        try {
            INSTANCE = ConfigLoader.loadConfig("config.toml");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    /**
     * Provides the singleton instance of {@link Configuration}.
     *
     * @return the global configuration instance
     */
    public static Configuration getInstance() {
        return INSTANCE;
    }
}
