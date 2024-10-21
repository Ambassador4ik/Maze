package maze.solver.functions;

import maze.generator.GeneratorType;

public enum CostFuncType {
    LINEAR {
        @Override
        public CostFunc createCostFunc() {
            return new LinearCostFunc();
        }
    },
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

    public static CostFuncType fromString(String typeStr) {
        try {
            return CostFuncType.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid solver type: " + typeStr);
        }
    }
}
