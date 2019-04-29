package design_patterns.adapter;

/**
 * @author SpencerCJH
 * @date 2019/4/29 14:01
 */
public class ConcreteTarget implements Target{
    @Override
    public void request() {
        System.out.println("Concrete Target Service");
    }
}
