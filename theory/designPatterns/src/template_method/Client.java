package template_method;

/**
 * @author SpencerCJH
 * @date 2019/4/28 21:09
 */
public class Client {
    public static void main(String[] args) {
        AbstractClass class1 = new ConcreteClass1();
        AbstractClass class2 = new ConcreteClass2();
        class1.templateMethod();
        class2.templateMethod();
    }
}
