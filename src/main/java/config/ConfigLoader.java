package config;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.experimental.UtilityClass;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

/**
 * Loader class for reading and parsing the TOML configuration file.
 */
@UtilityClass
public class ConfigLoader {

    /**
     * Loads the configuration from the specified TOML file.
     *
     * @param filepath the path to the configuration file
     * @return the parsed {@link Configuration}
     * @throws IOException if an error occurs during file access
     */
    public static Configuration loadConfig(String filepath) throws IOException {
        Path source = Paths.get(filepath);
        TomlParseResult toml = Toml.parse(source);

        toml.errors().forEach(error -> {
            throw error;
        });

        return Configuration.fromToml(toml);
    }
}
