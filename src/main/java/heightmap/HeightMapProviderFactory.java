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
        int heightRange
    ) {
        return switch (type) {
            case RANDOM -> new RandomHMProvider(heightRange);
            case PERLIN_NOISE -> {
                PerlinNoiseParams params = new PerlinNoiseParams(heightRange);
                yield new PerlinNoiseHMProvider(
                    params.scale(),
                    params.octaves(),
                    params.persistence(),
                    params.lacunarity(),
                    params.heightRange()
                );
            }
        };
    }
}
