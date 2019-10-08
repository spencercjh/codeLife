package decorator;

/**
 * @author SpencerCJH
 * @date 2019/4/29 13:44
 */
public class ConcreteComponent extends AbstractComponent{
    @Override
    public void operate() {
        System.out.println("ConcreteComponent Operate");
    }
}
