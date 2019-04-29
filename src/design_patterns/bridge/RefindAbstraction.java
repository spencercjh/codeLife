package design_patterns.bridge;

/**
 * @author SpencerCJH
 * @date 2019/4/29 17:08
 */
public class RefindAbstraction extends Abstraction{
    public RefindAbstraction(Implementor implementor) {
        super(implementor);
    }

    @Override
    public void request() {
        super.request();
        super.getImplementor().doAnything();
    }
}
