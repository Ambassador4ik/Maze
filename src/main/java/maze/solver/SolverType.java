package maze.solver;

import maze.solver.functions.CostFunc;

public enum SolverType {
    ASTAR {
        @Override
        public MazeSolver createSolver(CostFunc costFunc) {
            return new AStarSolver(costFunc);
        }
    };

    public abstract MazeSolver createSolver(CostFunc costFunc);
}
