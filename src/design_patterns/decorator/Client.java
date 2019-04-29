package design_patterns.decorator;

/**
 * @author SpencerCJH
 * @date 2019/4/29 13:49
 */
public class Client {
    public static void main(String[] args) {
        AbstractComponent component=new ConcreteComponent();
        component=new ConcreteDecorator1(component);
        component=new ConcreteDecorator2(component);
        component.operate();
    }
}
