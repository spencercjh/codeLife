package decorator;

/**
 * java.io中很典型的装饰器模式使用方法
 * @author SpencerCJH
 * @date 2019/4/29 13:49
 */
public class Client {
    public static void main(String[] args) {
        AbstractComponent component=new ConcreteDecorator2(new ConcreteDecorator1(new ConcreteComponent()));
        component.operate();
    }
}
