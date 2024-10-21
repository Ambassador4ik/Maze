package heightmap.providers;

public enum ProviderType {
    RANDOM,
    PERLIN_NOISE;

    public static ProviderType fromString(String typeStr) {
        try {
            return ProviderType.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid solver type: " + typeStr);
        }
    }
}
