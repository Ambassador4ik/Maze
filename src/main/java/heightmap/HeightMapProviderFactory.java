package heightmap;

import heightmap.params.PerlinNoiseParams;
import heightmap.providers.HeightMapProvider;
import heightmap.providers.PerlinNoiseHMProvider;
import heightmap.providers.ProviderType;
import heightmap.providers.RandomHMProvider;
import lombok.experimental.UtilityClass;

/**
 * HeightMapProviderFactory is a factory class that creates instances of
 * HeightMapProvider based on the provided type.
 */
@UtilityClass
public class HeightMapProviderFactory {

    /**
     * Creates and returns a HeightMapProvider instance based on the specified
     * provider type and height range.
     *
     * @param type        The type of heightmap provider
     * @param heightRange The range of heights for the heightmap
     * @return A HeightMapProvider instance corresponding to the specified type
     */
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
