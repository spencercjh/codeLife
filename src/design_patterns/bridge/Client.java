package design_patterns.bridge;

/**
 * @author SpencerCJH
 * @date 2019/4/29 17:09
 */
public class Client {
    public static void main(String[] args) {
        Implementor implementor=new ConcreteImplementor1();
        Abstraction abstraction=new RefindAbstraction(implementor);
        abstraction.request();
    }
}
