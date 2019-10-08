package decorator;

/**
 * @author SpencerCJH
 * @date 2019/4/29 13:46
 */
public class ConcreteDecorator1 extends AbstractDecorator{
    public ConcreteDecorator1(AbstractComponent component) {
        super(component);
    }
    private void method1(){
        System.out.println("Decorator Method 1");
    }

    @Override
    public void operate() {
        method1();
        super.operate();
    }
}
