package adapter;

/**
 * @author SpencerCJH
 * @date 2019/4/29 14:05
 */
public class Client {
    public static void main(String[] args) {
        Target target=new ConcreteTarget();
        target.request();
        Target target2=new Adapter();
        target2.request();
    }
}
