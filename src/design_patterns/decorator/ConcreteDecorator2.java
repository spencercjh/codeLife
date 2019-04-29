package design_patterns.decorator;

/**
 * @author SpencerCJH
 * @date 2019/4/29 13:48
 */
public class ConcreteDecorator2 extends AbstractDecorator{
    public ConcreteDecorator2(AbstractComponent component) {
        super(component);
    }
    private void method(){
        System.out.println("Decorator Method 2");
    }

    @Override
    public void operate() {
        super.operate();
        method();
    }
}
