package lazyload;

import interceptor.TargetObject;
import net.sf.cglib.proxy.Dispatcher;

/**
 * @author SpencerCJH
 * @date 2019/7/23 15:27
 */
public class ConcreteClassDispatcher implements Dispatcher {
    @Override
    public Object loadObject() throws Exception {
        System.out.println("调度器前");
        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setKey("Dispatcher").setValue(new TargetObject());
        System.out.println("调度器后");
        return propertyBean;
    }
}
