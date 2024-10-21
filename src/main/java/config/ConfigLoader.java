package config;

import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ConfigLoader {
    public static Configuration loadConfig(String filepath) throws IOException {
        Path source = Paths.get(filepath);

        TomlParseResult toml = Toml.parse(source);
        toml.errors().forEach(error -> {
            throw error;
        });


        return Configuration.fromToml(toml);
    }
}
