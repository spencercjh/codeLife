package interfacemaker;

import interceptor.TargetObject;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author SpencerCJH
 * @date 2019/7/23 15:35
 */
public class TestInterfaceMaker {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        InterfaceMaker interfaceMaker = new InterfaceMaker();
        interfaceMaker.add(TargetObject.class);
        Class<?> targetInterface = interfaceMaker.create();
        for (Method method : targetInterface.getMethods()) {
            System.out.println(method.getName());
        }
        Object object = Enhancer.create(Object.class, new Class[]{targetInterface}, new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("show args into filter");
                for (Object object : args) {
                    System.out.println(object);
                }
                if ("method1".equals(method.getName())) {
                    System.out.println("filter method1");
                    return "test method1";
                } else if ("method2".equals(method.getName())) {
                    System.out.println("filter method2");
                    return "test method2";
                } else if ("method3".equals(method.getName())) {
                    System.out.println("filter method3");
                    return 789F;
                }
                return "deault method";
            }
        });
        Method targetMethod1 = object.getClass().getMethod("method3", float.class);
        System.out.println((float) targetMethod1.invoke(object, new Object[]{33F}));
        Method targetMethod2 = object.getClass().getMethod("method1", String.class);
        System.out.println(targetMethod2.invoke(object, "spencer"));
    }
}