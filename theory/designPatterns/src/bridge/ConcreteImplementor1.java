package bridge;

/**
 * @author SpencerCJH
 * @date 2019/4/29 16:58
 */
public class ConcreteImplementor1 implements Implementor{
    @Override
    public void doSomething() {
        System.out.println("Concrete Implementor 1 do something");
    }

    @Override
    public void doAnything() {
        System.out.println("Concrete Implementor 1 do anything");
    }
}
