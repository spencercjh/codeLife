package factory_method;

import java.lang.reflect.InvocationTargetException;

/**
 * @author SpencerCJH
 * @date 2019/4/28 19:35
 */
public class ConcreteCreator extends AbstractCreator {
    @Override
    public <T extends AbstractProduct> T createProduct(Class<T> c) {
        T product = null;
        try {
            product = (T) Class.forName(c.getName()).getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return product;
    }
}
