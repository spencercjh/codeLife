package design_patterns.factory_method;

/**
 * @author SpencerCJH
 * @date 2019/4/28 19:28
 */
public abstract class AbstractProduct {
    public void method1() {
        System.out.println("Method 1");
    }

    /**
     * 子产品继承实现
     */
    public abstract void method2();
}
