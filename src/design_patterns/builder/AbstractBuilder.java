package design_patterns.builder;

/**
 * @author SpencerCJH
 * @date 2019/4/28 21:26
 */
public abstract class AbstractBuilder {

    public abstract Product setPart(int part);

    public abstract Product setModel(String model);

    public abstract Product buildProduct();
}
