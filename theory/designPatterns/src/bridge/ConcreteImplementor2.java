package bridge;

/**
 * @author SpencerCJH
 * @date 2019/4/29 16:58
 */
public class ConcreteImplementor2 implements Implementor{
    @Override
    public void doSomething() {
        System.out.println("Concrete Implementor 2 do something");
    }

    @Override
    public void doAnything() {
        System.out.println("Concrete Implementor 2 do anything");
    }
}
