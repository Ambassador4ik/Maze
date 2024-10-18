package heightmap;

import heightmap.params.PerlinNoiseParams;
import heightmap.params.ProviderParams;
import heightmap.params.RandomParams;
import heightmap.providers.HeightMapProvider;
import heightmap.providers.PerlinNoiseHMProvider;
import heightmap.providers.ProviderType;
import heightmap.providers.RandomHMProvider;

public class HeightMapProviderFactory {
    public static HeightMapProvider createProvider(
        ProviderType type,
        ProviderParams params
    ) {
        return switch (type) {
            case RANDOM -> {
                if (!(params instanceof RandomParams randomParams)) {
                    throw new IllegalArgumentException("Invalid parameters for RandomHMProvider.");
                }
                yield new RandomHMProvider(randomParams.heightRange());
            }
            case PERLIN_NOISE -> {
                if (!(params instanceof PerlinNoiseParams perlinNoiseParams)) {
                    throw new IllegalArgumentException("Invalid parameters for PerlinNoiseHMProvider.");
                }
                yield new PerlinNoiseHMProvider(
                    perlinNoiseParams.scale(),
                    perlinNoiseParams.octaves(),
                    perlinNoiseParams.persistence(),
                    perlinNoiseParams.lacunarity(),
                    perlinNoiseParams.heightRange()
                );
            }
            default -> throw new IllegalArgumentException("Unsupported ProviderType: " + type);
        };
    }
}
