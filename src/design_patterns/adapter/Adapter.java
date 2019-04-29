package design_patterns.adapter;

/**
 * @author SpencerCJH
 * @date 2019/4/29 14:03
 */
public class Adapter extends Adaptee implements Target{
    @Override
    public void request() {
        doSomething();
    }
}
