package factory_method;

/**
 * @author SpencerCJH
 * @date 2019/4/28 19:43
 */
public class Client {
    public static void main(String[] args) {
        AbstractCreator creator = new ConcreteCreator();
        AbstractProduct product1 = creator.createProduct(ConcreteProduct1.class);
        product1.method2();
        AbstractProduct product2 = creator.createProduct(ConcreteProduct2.class);
        product2.method2();
    }
}
