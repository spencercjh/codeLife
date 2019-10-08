package template_method;

/**
 * @author SpencerCJH
 * @date 2019/4/28 21:05
 */
public abstract class AbstractClass {
    /**
     * 业务方法1
     */
    protected abstract void doSomething();

    /**
     * 业务方法2
     */
    protected abstract void doAnyThing();

    public void templateMethod() {
        doAnyThing();
        doSomething();
    }
}
