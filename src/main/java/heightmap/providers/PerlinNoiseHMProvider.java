package heightmap.providers;

import java.security.SecureRandom;
import lombok.Getter;
import maze.Node;

/**
 * PerlinNoiseHMProvider is a class that generates a heightmap using Perlin Noise.
 * It implements the HeightMapProvider interface.
 */
@SuppressWarnings("MagicNumber")
public class PerlinNoiseHMProvider implements HeightMapProvider {
    @Getter private final double scale;        // Scale of the noise
    @Getter private final int octaves;         // Number of octaves in the noise
    @Getter private final double persistence;  // Persistence value for the noise
    @Getter private final double lacunarity;   // Lacunarity value for the noise
    @Getter private final int heightRange;     // Range of heights for the heightmap

    private int[] permutation;                 // Permutation array for the noise
    private final SecureRandom random;         // Secure random number generator

    /**
     * Constructor to initialize PerlinNoiseHMProvider with custom noise parameters.
     *
     * @param scale        Scale of the Perlin noise
     * @param octaves      Number of octaves used in the noise
     * @param persistence  Persistence value for the noise
     * @param lacunarity   Lacunarity value for the noise
     * @param heightRange  Range of heights for the heightmap
     */
    public PerlinNoiseHMProvider(
        double scale,
        int octaves,
        double persistence,
        double lacunarity,
        int heightRange
    ) {
        this.scale = scale;
        this.octaves = octaves;
        this.persistence = persistence;
        this.lacunarity = lacunarity;
        this.heightRange = heightRange;

        random = new SecureRandom();

        initPermutation();
    }

    /**
     * Initializes the permutation array used for the Perlin noise algorithm.
     */
    private void initPermutation() {
        // Initialize the permutation array with values from 0 to 255
        permutation = new int[512];
        int[] p = new int[256];
        for (int i = 0; i < 256; i++) {
            p[i] = i;
        }

        // Shuffle the array using the seeded random
        for (int i = 255; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Swap
            int temp = p[i];
            p[i] = p[index];
            p[index] = temp;
        }

        // Duplicate the permutation array
        for (int i = 0; i < 512; i++) {
            permutation[i] = p[i & 255];
        }
    }

    /**
     * Fade function used in the Perlin noise algorithm, as defined by Ken Perlin.
     *
     * @param t Input value for the fade function
     * @return Smoothed value
     */
    private double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    /**
     * Performs linear interpolation between two values.
     *
     * @param a First value
     * @param b Second value
     * @param t Interpolation factor
     * @return Interpolated value
     */
    private double lerp(double a, double b, double t) {
        return a + t * (b - a);
    }

    /**
     * Gradient function used in the Perlin noise algorithm.
     *
     * @param hash Hash value
     * @param x    X-coordinate
     * @param y    Y-coordinate
     * @return Gradient value
     */
    private double grad(int hash, double x, double y) {
        int h = hash & 7;      // Convert low 3 bits of hash code
        double u = h < 4 ? x : y;
        double v = h < 4 ? y : x;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    /**
     * Perlin noise function for 2D coordinates.
     *
     * @param x X-coordinate
     * @param y Y-coordinate
     * @return Noise value at the given coordinates
     */
    private double perlin(double x, double y) {
        int xi = (int) Math.floor(x) & 255;
        int yi = (int) Math.floor(y) & 255;

        double xf = x - Math.floor(x);
        double yf = y - Math.floor(y);

        double u = fade(xf);
        double v = fade(yf);

        int aa = permutation[permutation[xi] + yi];
        int ab = permutation[permutation[xi] + yi + 1];
        int ba = permutation[permutation[xi + 1] + yi];
        int bb = permutation[permutation[xi + 1] + yi + 1];

        double x1;
        double x2;
        x1 = lerp(grad(aa, xf, yf), grad(ba, xf - 1, yf), u);
        x2 = lerp(grad(ab, xf, yf - 1), grad(bb, xf - 1, yf - 1), u);
        return lerp(x1, x2, v);
    }

    /**
     * Fills the 2D array of nodes with height values generated using Perlin noise.
     *
     * @param nodes 2D array of Node objects representing the heightmap
     */
    @Override
    public void fillMap(Node[][] nodes) {
        int height = nodes.length;
        if (height == 0) {
            return;
        }
        int width = nodes[0].length;

        // Calculate total amplitude for normalization
        double maxAmplitude = 0.0;
        double amplitude = 1.0;
        for (int o = 0; o < octaves; o++) {
            maxAmplitude += amplitude;
            amplitude *= persistence;
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                amplitude = 1.0;
                double frequency = 1.0;
                double noiseHeight = 0.0;

                for (int o = 0; o < octaves; o++) {
                    double sampleX = (x / scale) * frequency;
                    double sampleY = (y / scale) * frequency;

                    double perlinValue = perlin(sampleX, sampleY);
                    noiseHeight += perlinValue * amplitude;

                    amplitude *= persistence;
                    frequency *= lacunarity;
                }

                // Normalize the noise value
                double normalized = (noiseHeight / maxAmplitude) * heightRange;
                nodes[y][x].height((int) Math.round(normalized));
            }
        }
    }
}
