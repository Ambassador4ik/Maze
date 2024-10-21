package heightmap.providers;

/**
 * ProviderType is an enum that defines different types of heightmap providers.
 * Currently, it supports RANDOM and PERLIN_NOISE providers.
 */
public enum ProviderType {
    RANDOM,       // Random heightmap provider
    PERLIN_NOISE; // Perlin Noise heightmap provider

    /**
     * Converts a string representation of the provider type to a ProviderType enum.
     *
     * @param typeStr String representation of the provider type
     * @return ProviderType corresponding to the input string
     * @throws IllegalArgumentException if the string does not match a valid type
     */
    public static ProviderType fromString(String typeStr) {
        try {
            return ProviderType.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid solver type: " + typeStr, e);
        }
    }
}
