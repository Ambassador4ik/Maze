package heightmap.providers;

import lombok.Getter;
import maze.Node;
import java.security.SecureRandom;

public class RandomHMProvider implements HeightMapProvider {
    @Getter private final int heightRange;

    private final SecureRandom random;

    public RandomHMProvider(
        int heightRange
    ) {
        this.heightRange = heightRange;

        this.random = new SecureRandom();
    }

    @Override
    public void fillMap(Node[][] nodes) {
        int height = nodes.length;
        if (height == 0) return;
        int width = nodes[0].length;

        for (Node[] node : nodes) {
            for (int x = 0; x < width; x++) {
                double normalized = random.nextDouble(-1, 1) * heightRange;
                node[x].height((int) Math.round(normalized));
            }
        }
    }
}
