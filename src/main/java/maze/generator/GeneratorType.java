package maze.generator;

import maze.generator.algorithms.DFSMazeGenerator;
import maze.generator.algorithms.KruskalMazeGenerator;
import maze.generator.algorithms.PrimMazeGenerator;

public enum GeneratorType {
    DFS {
        @Override
        public MazeGenerator createGenerator() {
            return new DFSMazeGenerator();
        }
    },
    KRUSKAL {
        @Override
        public MazeGenerator createGenerator() {
            return new KruskalMazeGenerator();
        }
    },
    PRIM {
        @Override
        public MazeGenerator createGenerator() {
            return new PrimMazeGenerator();
        }
    };

    public abstract MazeGenerator createGenerator();

    public static GeneratorType fromString(String typeStr) {
        try {
            return GeneratorType.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid solver type: " + typeStr, e);
        }
    }
}
