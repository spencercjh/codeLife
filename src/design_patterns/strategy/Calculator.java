package design_patterns.strategy;

/**
 * @author SpencerCJH
 * @date 2019/4/28 22:08
 */
public enum Calculator {
    /**
     * a+b
     */
    ADD("+") {
        @Override
        public int execute(int a, int b) {
            return a + b;
        }
    },
    /**
     * a-b
     */
    SUB("-") {
        @Override
        public int execute(int a, int b) {
            return a - b;
        }
    };
    String value;

    Calculator(String value) {
        this.value = value;
    }

    public abstract int execute(int a, int b);
}
