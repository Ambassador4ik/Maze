package maze.solver;

import maze.solver.algorithms.BellmanFordSolver;
import maze.solver.algorithms.JohnsonsSolver;
import maze.solver.algorithms.SPFASolver;
import maze.solver.functions.CostFunc;

public enum SolverType {
    JOHNSONS {
        @Override
        public MazeSolver createSolver(CostFunc costFunc) {
            return new JohnsonsSolver(costFunc);
        }
    },
    SPFA {
        @Override
        public MazeSolver createSolver(CostFunc costFunc) {
            return new SPFASolver(costFunc);
        }
    },
    BELLMAN_FORD{
        @Override
        public MazeSolver createSolver(CostFunc costFunc) {
            return new BellmanFordSolver(costFunc);
        }
    };

    public abstract MazeSolver createSolver(CostFunc costFunc);
}
