package design_patterns.decorator;

/**
 * @author SpencerCJH
 * @date 2019/4/29 13:45
 */
public class AbstractDecorator extends AbstractComponent{
    private AbstractComponent component=null;

    public AbstractDecorator(AbstractComponent component) {
        this.component = component;
    }

    @Override
    public void operate() {
        component.operate();
    }
}
