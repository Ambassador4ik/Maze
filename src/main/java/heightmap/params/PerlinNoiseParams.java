package heightmap.params;

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
}
