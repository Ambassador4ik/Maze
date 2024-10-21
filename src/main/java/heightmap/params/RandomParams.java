package heightmap.params;

/**
 * RandomParams is a class that defines parameters for generating
 * a heightmap using random noise. It extends the ProviderParams class.
 */
public class RandomParams extends ProviderParams {

    /**
     * Constructor to initialize RandomParams with a specified height range.
     *
     * @param heightRange Range of heights for the heightmap
     */
    public RandomParams(int heightRange) {
        super(heightRange);
    }
}
