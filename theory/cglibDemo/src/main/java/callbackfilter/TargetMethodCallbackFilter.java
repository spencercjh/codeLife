package callbackfilter;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * @author SpencerCJH
 * @date 2019/7/23 14:39
 */
public class TargetMethodCallbackFilter implements CallbackFilter {
    /**
     * @param method invoked method
     * @return index 对应callbacks[]的下标
     */
    @Override
    public int accept(Method method) {
        if ("method1".equals(method.getName())) {
            System.out.println("filter method1 ==0");
            return 0;
        } else if ("method2".equals(method.getName())) {
            System.out.println("filter method2 ==1");
            return 1;
        } else if ("method3".equals(method.getName())) {
            System.out.println("filter method3 ==2");
            return 2;
        }
        return 0;
    }

}
