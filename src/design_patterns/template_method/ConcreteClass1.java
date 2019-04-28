package design_patterns.template_method;

/**
 * @author SpencerCJH
 * @date 2019/4/28 21:08
 */
public class ConcreteClass1 extends AbstractClass {
    @Override
    protected void doSomething() {
        System.out.println("do something 1");
    }

    @Override
    protected void doAnyThing() {
        System.out.println("do any thing 1");
    }
}
