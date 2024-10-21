package config;

import java.io.IOException;

public class Config {
    // Private static final Configuration instance
    private static final Configuration INSTANCE;

    // Static block to initialize the Configuration
    static {
        try {
            INSTANCE = ConfigLoader.loadConfig("config.toml");
        } catch (IOException e) {
            // Handle the exception as needed
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    // Private constructor to prevent instantiation
    private Config() {
        // Prevent instantiation
    }

    // Public method to provide access to the Configuration instance
    public static Configuration getInstance() {
        return INSTANCE;
    }
}
