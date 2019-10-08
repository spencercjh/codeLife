package factory_method;

/**
 * @author SpencerCJH
 * @date 2019/4/28 19:32
 */
public abstract class AbstractCreator {
    /**
     * 根据传来的Class或者String、Enum来生产一个产品
     *
     * @param c   class
     * @param <T> Product Type
     * @return
     */
    public abstract <T extends AbstractProduct> T createProduct(Class<T> c);
}
