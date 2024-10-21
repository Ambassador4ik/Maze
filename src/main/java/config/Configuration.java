package config;

import java.util.Objects;
import org.tomlj.TomlParseResult;

/**
 * Record that holds various configuration sections.
 */
public record Configuration(MazeConfig maze, VisualsConfig visuals, GeneratorConfig generator, SurfaceConfig surface,
                            SolverConfig solver, CostFuncConfig costFunc, PerlinConfig perlin) {

    /**
     * Parses the TOML configuration into a {@link Configuration} object.
     *
     * @param toml the parsed TOML content
     * @return the populated {@link Configuration} object
     */
    public static Configuration fromToml(TomlParseResult toml) {
        MazeConfig mazeConfig = MazeConfig.fromToml(Objects.requireNonNull(toml.getTable("maze")));
        VisualsConfig visualsConfig = VisualsConfig.fromToml(Objects.requireNonNull(toml.getTable("visuals")));
        GeneratorConfig generatorConfig = GeneratorConfig.fromToml(Objects.requireNonNull(toml.getTable("generator")));
        SurfaceConfig surfaceConfig = SurfaceConfig.fromToml(Objects.requireNonNull(toml.getTable("surface")));
        SolverConfig solverConfig = SolverConfig.fromToml(Objects.requireNonNull(toml.getTable("solver")));
        CostFuncConfig costFuncConfig = CostFuncConfig.fromToml(Objects.requireNonNull(toml.getTable("costfunc")));
        PerlinConfig perlinConfig = PerlinConfig.fromToml(Objects.requireNonNull(toml.getTable("perlin")));

        return new Configuration(
            mazeConfig,
            visualsConfig,
            generatorConfig,
            surfaceConfig,
            solverConfig,
            costFuncConfig,
            perlinConfig
        );
    }
}
