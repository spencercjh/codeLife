package design_patterns.builder;

/**
 * @author SpencerCJH
 * @date 2019/4/28 21:33
 */
public class Client {
    public static void main(String[] args) {
        Product product = new ConcreteProduct().buildProduct().setModel("123").setPart(1);
        product.doSomething();
    }
}
