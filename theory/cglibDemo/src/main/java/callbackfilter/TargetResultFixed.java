package callbackfilter;

import net.sf.cglib.proxy.FixedValue;

/**
 * @author SpencerCJH
 * @date 2019/7/23 14:54
 */
public class TargetResultFixed implements FixedValue {
    @Override
    public Object loadObject() throws Exception {
        System.out.println("锁定结果");
        return 999;
    }
}
