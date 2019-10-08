package callbackfilter;

import interceptor.TargetInterceptor;
import interceptor.TargetObject;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * @author SpencerCJH
 * @date 2019/7/23 14:49
 */
public class TestCglib {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        CallbackFilter callbackFilter = new TargetMethodCallbackFilter();
        Callback noOperationCallback = NoOp.INSTANCE;
        Callback targetInterceptor = new TargetInterceptor();
        Callback fixedValue = new TargetResultFixed();
        Callback[] callbacks = new Callback[]{noOperationCallback, fixedValue, targetInterceptor};
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackFilter(callbackFilter);
        TargetObject newTargetObject = (TargetObject) enhancer.create();
        System.out.println(newTargetObject);
        System.out.println(newTargetObject.method1("test"));
        System.out.println(newTargetObject.method2(123));
        System.out.println(newTargetObject.method3(123F));
        System.out.println(newTargetObject.method3(456F));
    }
}
