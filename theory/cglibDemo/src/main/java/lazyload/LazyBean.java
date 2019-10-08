package lazyload;

import net.sf.cglib.proxy.Enhancer;

import java.util.Objects;

/**
 * @author SpencerCJH
 * @date 2019/7/23 15:19
 */
public class LazyBean {
    private String name;
    private int age;
    private PropertyBean propertyBean;
    private PropertyBean propertyBeanDispatcher;

    public LazyBean(String name, int age) {
        this.name = name;
        this.age = age;
        this.propertyBean = createPropertyBean();
        this.propertyBeanDispatcher = createPropertyBeanDispatcher();
    }

    public String getName() {
        return name;
    }

    public LazyBean setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public LazyBean setAge(int age) {
        this.age = age;
        return this;
    }

    public PropertyBean getPropertyBean() {
        return propertyBean;
    }

    public LazyBean setPropertyBean(PropertyBean propertyBean) {
        this.propertyBean = propertyBean;
        return this;
    }

    public PropertyBean getPropertyBeanDispatcher() {
        return propertyBeanDispatcher;
    }

    public LazyBean setPropertyBeanDispatcher(PropertyBean propertyBeanDispatcher) {
        this.propertyBeanDispatcher = propertyBeanDispatcher;
        return this;
    }

    private PropertyBean createPropertyBean() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PropertyBean.class);
        return (PropertyBean) Enhancer.create(PropertyBean.class, new ConcreteClassLazyLoader());
    }

    private PropertyBean createPropertyBeanDispatcher() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PropertyBean.class);
        return (PropertyBean) Enhancer.create(PropertyBean.class, new ConcreteClassDispatcher());
    }

    @Override
    public String toString() {
        return "LazyBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", propertyBean=" + propertyBean +
                ", propertyBeanDispatcher=" + propertyBeanDispatcher +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LazyBean lazyBean = (LazyBean) o;
        return age == lazyBean.age &&
                Objects.equals(name, lazyBean.name) &&
                Objects.equals(propertyBean, lazyBean.propertyBean) &&
                Objects.equals(propertyBeanDispatcher, lazyBean.propertyBeanDispatcher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, propertyBean, propertyBeanDispatcher);
    }
}
