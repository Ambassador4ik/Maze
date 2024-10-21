package heightmap.params;

import lombok.Getter;

/**
 * ProviderParams is an abstract class that holds common parameters for
 * different providers that generate heightmaps. It defines the height range
 * for the generated terrain.
 */
@Getter
public abstract class ProviderParams {
    private final int heightRange; // Range of heights for the heightmap

    /**
     * Constructor to initialize the ProviderParams with the height range.
     *
     * @param heightRange Range of heights for the heightmap
     */
    public ProviderParams(int heightRange) {
        this.heightRange = heightRange;
    }
}
