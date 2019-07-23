package interceptor;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author SpencerCJH
 * @date 2019/7/23 14:29
 */
public class TestCglib {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        enhancer.setCallback(new TargetInterceptor());
        TargetObject newTargetObject = (TargetObject) enhancer.create();
        System.out.println(newTargetObject.toString());
        System.out.println(newTargetObject.method1("test"));
        System.out.println(newTargetObject.method2(123));
        System.out.println(newTargetObject.method3(123F));
    }
}
