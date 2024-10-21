package heightmap.providers;

import java.security.SecureRandom;
import lombok.Getter;
import maze.Node;

/**
 * RandomHMProvider is a class that generates a heightmap using random values.
 * It implements the HeightMapProvider interface.
 */
public class RandomHMProvider implements HeightMapProvider {
    @Getter private final int heightRange;   // Range of heights for the heightmap

    private final SecureRandom random;       // Secure random number generator

    /**
     * Constructor to initialize RandomHMProvider with a specified height range.
     *
     * @param heightRange Range of heights for the heightmap
     */
    public RandomHMProvider(int heightRange) {
        this.heightRange = heightRange;
        this.random = new SecureRandom();
    }

    /**
     * Fills the 2D array of nodes with random height values.
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

        for (Node[] node : nodes) {
            for (int x = 0; x < width; x++) {
                double normalized = random.nextDouble(-1, 1) * heightRange;
                node[x].height((int) Math.round(normalized));
            }
        }
    }
}
