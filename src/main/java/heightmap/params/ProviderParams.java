package heightmap.params;

import lombok.Getter;

@Getter
public abstract class ProviderParams {
    private final int heightRange;

    public ProviderParams(int heightRange) {
        this.heightRange = heightRange;
    }
}
