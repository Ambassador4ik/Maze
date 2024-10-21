package heightmap.params;

import config.Config;
import config.Configuration;
import lombok.Getter;

/**
 * PerlinNoiseParams is a class that defines parameters used for generating
 * a heightmap using Perlin Noise. It extends the ProviderParams class.
 * The parameters include scale, octaves, persistence, and lacunarity,
 * which are important for the Perlin Noise algorithm.
 */
@Getter
public class PerlinNoiseParams extends ProviderParams {
    private final double scale;        // Scale of the noise
    private final int octaves;         // Number of octaves in the noise
    private final double persistence;  // Controls the amplitude of octaves
    private final double lacunarity;   // Controls the frequency of octaves

    /**
     * Constructor to initialize the PerlinNoiseParams with custom values.
     *
     * @param heightRange  Range of heights for the heightmap
     * @param scale        Scale of the Perlin noise
     * @param octaves      Number of octaves used in the noise
     * @param persistence  Persistence value for the noise
     * @param lacunarity   Lacunarity value for the noise
     */
    public PerlinNoiseParams(int heightRange, double scale, int octaves, double persistence, double lacunarity) {
        super(heightRange);
        this.scale = scale;
        this.octaves = octaves;
        this.persistence = persistence;
        this.lacunarity = lacunarity;
    }

    /**
     * Constructor to initialize the PerlinNoiseParams with default values
     * from the configuration.
     *
     * @param heightRange Range of heights for the heightmap
     */
    public PerlinNoiseParams(int heightRange) {
        super(heightRange);
        Configuration config = Config.getInstance();
        this.scale = config.perlin().scale();
        this.octaves = config.perlin().octaves();
        this.persistence = config.perlin().persistence();
        this.lacunarity = config.perlin().lacunarity();
    }
}
