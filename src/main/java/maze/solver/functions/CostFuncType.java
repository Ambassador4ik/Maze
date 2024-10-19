package maze.solver.functions;

public enum CostFuncType {
    NON_LINEAR {
        @Override
        public CostFunc createCostFunc() {
            return new NonLinearCostFunc();
        }
    },
    CONST {
        @Override
        public CostFunc createCostFunc() {
            return new ConstantCostFunc();
        }
    },
    TANH {
        @Override
        public CostFunc createCostFunc() {
            return new TanhCostFunc();
        }
    };

    public abstract CostFunc createCostFunc();
}
