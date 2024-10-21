package config;

import org.tomlj.TomlParseResult;
import java.util.Objects;

public record Configuration(MazeConfig maze, VisualsConfig visuals, GeneratorConfig generator, SurfaceConfig surface,
                            SolverConfig solver, CostFuncConfig costFunc, PerlinConfig perlin) {

    public static Configuration fromToml(TomlParseResult toml) {
        // Parse each section
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
