package heightmap.params;

import config.Config;
import config.Configuration;
import lombok.Getter;

@Getter
public class PerlinNoiseParams extends ProviderParams {
    private final double scale;
    private final int octaves;
    private final double persistence;
    private final double lacunarity;

    public PerlinNoiseParams(int heightRange, double scale, int octaves, double persistence, double lacunarity) {
        super(heightRange);
        this.scale = scale;
        this.octaves = octaves;
        this.persistence = persistence;
        this.lacunarity = lacunarity;
    }

    public PerlinNoiseParams(int heightRange) {
        super(heightRange);
        Configuration config = Config.getInstance();
        this.scale = config.perlin().scale();
        this.octaves = config.perlin().octaves();
        this.persistence = config.perlin().persistence();
        this.lacunarity = config.perlin().lacunarity();
    }
}
