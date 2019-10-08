package lazyload;

import interceptor.TargetObject;
import net.sf.cglib.proxy.LazyLoader;

/**
 * @author SpencerCJH
 * @date 2019/7/23 15:23
 */
public class ConcreteClassLazyLoader implements LazyLoader {
    @Override
    public Object loadObject() throws Exception {
        System.out.println("懒加载器前");
        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setKey("LazyLoader").setValue(new TargetObject());
        System.out.println("懒加载器后");
        return propertyBean;
    }
}
