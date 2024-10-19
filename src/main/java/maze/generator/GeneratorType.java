package maze.generator;

public enum GeneratorType {
    DFS {
        @Override
        public MazeGenerator createGenerator() {
            return new DFSMazeGenerator();
        }
    };

    public abstract MazeGenerator createGenerator();
}
