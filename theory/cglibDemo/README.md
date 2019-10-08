reference: https://www.runoob.com/w3cnote/cglibcode-generation-library-intro.html

# CGLIB(Code Generation Library) 介绍与原理
 
## 一、什么是 CGLIB?

CGLIB是一个功能强大，高性能的代码生成包。它为没有实现接口的类提供代理，为JDK的动态代理提供了很好的补充。通常可以使用Java的动态代理创建代理，但当要代理的类没有实现接口或者为了更好的性能，CGLIB是一个好的选择。


CGLIB作为一个开源项目，其代码托管在github，地址为：https://github.com/cglib/cglib

## 二、CGLIB 原理
**CGLIB 原理：** 动态生成一个要代理类的子类，子类重写要代理的类的所有不是final的方法。在子类中采用方法拦截的技术拦截所有父类方法的调用，顺势织入横切逻辑。它比使用Java反射的JDK动态代理要快。

**CGLIB 底层：** 使用字节码处理框架ASM，来转换字节码并生成新的类。不鼓励直接使用ASM，因为它要求你必须对JVM内部结构包括class文件的格式和指令集都很熟悉。

**CGLIB缺点：** 对于final方法，无法进行代理。

## 三、CGLIB 的应用
广泛的被许多AOP的框架使用，例如Spring AOP和dynaop。Hibernate使用CGLIB来代理单端single-ended(多对一和一对一)关联。

## 四、为什么使用 CGLIB?
CGLIB代理主要通过对字节码的操作，为对象引入间接级别，以控制对象的访问。我们知道Java中有一个动态代理也是做这个事情的，那我们为什么不直接使用Java动态代理，而要使用CGLIB呢？答案是CGLIB相比于JDK动态代理更加强大，JDK动态代理虽然简单易用，但是其有一个致命缺陷是，只能对接口进行代理。如果要代理的类为一个普通类、没有接口，那么Java动态代理就没法使用了。

## 五、CGLIB组成结构

![cglib component](https://www.runoob.com/wp-content/uploads/2018/11/jnbNov2005-1.png)

CGLIB底层使用了ASM（一个短小精悍的字节码操作框架）来操作字节码生成新的类。除了CGLIB库外，脚本语言（如Groovy何BeanShell）也使用ASM生成字节码。ASM使用类似SAX的解析器来实现高性能。我们不鼓励直接使用ASM，因为它需要对Java字节码的格式足够的了解。

## 六、CGLIB的API

### 1、Jar包：

    cglib-nodep-2.2.jar：使用nodep包不需要关联asm的jar包,jar包内部包含asm的类.
    
    cglib-2.2.jar：使用此jar包需要关联asm的jar包,否则运行时报错.

### 2、CGLIB类库：

由于基本代码很少，学起来有一定的困难，主要是缺少文档和示例，这也是CGLIB的一个不足之处。


本系列使用的CGLIB版本是2.2。

    net.sf.cglib.core: 底层字节码处理类，他们大部分与ASM有关系。
    net.sf.cglib.transform: 编译期或运行期类和类文件的转换
    net.sf.cglib.proxy: 实现创建代理和方法拦截器的类
    net.sf.cglib.reflect: 实现快速反射和C#风格代理的类
    net.sf.cglib.util: 集合排序等工具类
    net.sf.cglib.beans: JavaBean相关的工具类

## 七、本篇介绍通过MethodInterceptor和Enhancer实现一个动态代理。

### 一、首先说一下JDK中的动态代理：

JDK中的动态代理是通过反射类Proxy以及InvocationHandler回调接口实现的，但是，JDK中所要进行动态代理的类必须要实现一个接口，也就是说只能对该类所实现接口中定义的方法进行代理，这在实际编程中具有一定的局限性，而且使用反射的效率也并不是很高。

### 二、使用CGLib实现：

使用CGLib实现动态代理，完全不受代理类必须实现接口的限制，而且CGLib底层采用ASM字节码生成框架，使用字节码技术生成代理类，比使用Java反射效率要高。唯一需要注意的是，CGLib不能对声明为final的方法进行代理，因为CGLib原理是动态生成被代理类的子类。

### 下面，将通过一个实例介绍使用CGLib实现动态代理。

#### 1、被代理类：

首先，定义一个类，该类没有实现任何接口。

```java
package com.zghw.cglib;
 
/**
 * 没有实现接口，需要CGlib动态代理的目标类
 * 
 * @author zghw
 *
 */
public class TargetObject {
    public String method1(String paramName) {
        return paramName;
    }
 
    public int method2(int count) {
        return count;
    }
 
    public int method3(int count) {
        return count;
    }
 
    @Override
    public String toString() {
        return "TargetObject []"+ getClass();
    }
}
```

#### 2、拦截器：

定义一个拦截器。在调用目标方法时，CGLib会回调MethodInterceptor接口方法拦截，来实现你自己的代理逻辑，类似于JDK中的InvocationHandler接口。

```java
package com.zghw.cglib;
 
import java.lang.reflect.Method;
 
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
/**
 * 目标对象拦截器，实现MethodInterceptor
 * @author zghw
 *
 */
public class TargetInterceptor implements MethodInterceptor{
 
    /**
     * 重写方法拦截在方法前和方法后加入业务
     * Object obj为目标对象
     * Method method为目标方法
     * Object[] params 为参数，
     * MethodProxy proxy CGlib方法代理对象
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] params,
            MethodProxy proxy) throws Throwable {
        System.out.println("调用前");
        Object result = proxy.invokeSuper(obj, params);
        System.out.println(" 调用后"+result);
        return result;
    }
}
```

参数：Object为由CGLib动态生成的代理类实例，Method为上文中实体类所调用的被代理的方法引用，Object[]为参数值列表，MethodProxy为生成的代理类对方法的代理引用。

返回：从代理实例的方法调用返回的值。

其中，proxy.invokeSuper(obj,arg) 调用代理类实例上的proxy方法的父类方法（即实体类TargetObject中对应的方法）

在这个示例中，只在调用被代理类方法前后各打印了一句话，当然实际编程中可以是其它复杂逻辑。

#### 3、生成动态代理类：

```java
package com.zghw.cglib;
 
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
 
public class TestCglib {
    public static void main(String args[]) {
        Enhancer enhancer =new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        enhancer.setCallback(new TargetInterceptor());
        TargetObject targetObject2=(TargetObject)enhancer.create();
        System.out.println(targetObject2);
        System.out.println(targetObject2.method1("mmm1"));
        System.out.println(targetObject2.method2(100));
        System.out.println(targetObject2.method3(200));
    }
}
```

这里Enhancer类是CGLib中的一个字节码增强器，它可以方便的对你想要处理的类进行扩展，以后会经常看到它。

首先将被代理类TargetObject设置成父类，然后设置拦截器TargetInterceptor，最后执行enhancer.create()动态生成一个代理类，并从Object强制转型成父类型TargetObject。

最后，在代理类上调用方法。

#### 4、回调过滤器CallbackFilter

##### 一、作用

在CGLib回调时可以设置对不同方法执行不同的回调逻辑，或者根本不执行回调。

在JDK动态代理中并没有类似的功能，对InvocationHandler接口方法的调用对代理类内的所以方法都有效。

定义实现过滤器CallbackFilter接口的类：

```java
package com.zghw.cglib;
 
import java.lang.reflect.Method;
 
import net.sf.cglib.proxy.CallbackFilter;
/**
 * 回调方法过滤
 * @author zghw
 *
 */
public class TargetMethodCallbackFilter implements CallbackFilter {
 
    /**
     * 过滤方法
     * 返回的值为数字，代表了Callback数组中的索引位置，要到用的Callback
     */
    @Override
    public int accept(Method method) {
        if(method.getName().equals("method1")){
            System.out.println("filter method1 ==0");
            return 0;
        }
        if(method.getName().equals("method2")){
            System.out.println("filter method2 ==1");
            return 1;
        }
        if(method.getName().equals("method3")){
            System.out.println("filter method3 ==2");
            return 2;
        }
        return 0;
    }
 
}
```

其中return值为被代理类的各个方法在回调数组Callback[]中的位置索引（见下文）。

```java
package com.zghw.cglib;
 
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
 
public class TestCglib {
    public static void main(String args[]) {
        Enhancer enhancer =new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        CallbackFilter callbackFilter = new TargetMethodCallbackFilter();
        
        /**
         * (1)callback1：方法拦截器
           (2)NoOp.INSTANCE：这个NoOp表示no operator，即什么操作也不做，代理类直接调用被代理的方法不进行拦截。
           (3)FixedValue：表示锁定方法返回值，无论被代理类的方法返回什么值，回调方法都返回固定值。
         */
        Callback noopCb=NoOp.INSTANCE;
        Callback callback1=new TargetInterceptor();
        Callback fixedValue=new TargetResultFixed();
        Callback[] cbarray=new Callback[]{callback1,noopCb,fixedValue};
        //enhancer.setCallback(new TargetInterceptor());
        enhancer.setCallbacks(cbarray);
        enhancer.setCallbackFilter(callbackFilter);
        TargetObject targetObject2=(TargetObject)enhancer.create();
        System.out.println(targetObject2);
        System.out.println(targetObject2.method1("mmm1"));
        System.out.println(targetObject2.method2(100));
        System.out.println(targetObject2.method3(100));
        System.out.println(targetObject2.method3(200));
    }
}
```

```java
package com.zghw.cglib;
 
import net.sf.cglib.proxy.FixedValue;
/**
 * 表示锁定方法返回值，无论被代理类的方法返回什么值，回调方法都返回固定值。
 * @author zghw
 *
 */
public class TargetResultFixed implements FixedValue{
 
    /**
     * 该类实现FixedValue接口，同时锁定回调值为999
     * (整型，CallbackFilter中定义的使用FixedValue型回调的方法为getConcreteMethodFixedValue，该方法返回值为整型)。
     */
    @Override
    public Object loadObject() throws Exception {
        System.out.println("锁定结果");
        Object obj = 999;
        return obj;
    }
 
}
```

#### 5.延迟加载对象

##### 一、作用：

说到延迟加载，应该经常接触到，尤其是使用Hibernate的时候，本篇将通过一个实例分析延迟加载的实现方式。 LazyLoader接口继承了Callback，因此也算是CGLib中的一种Callback类型。

另一种延迟加载接口Dispatcher。

Dispatcher接口同样继承于Callback，也是一种回调类型。

但是Dispatcher和LazyLoader的区别在于：LazyLoader只在第一次访问延迟加载属性时触发代理类回调方法，而Dispatcher在每次访问延迟加载属性时都会触发代理类回调方法。

##### 二、示例：

首先定义一个实体类LoaderBean，该Bean内有一个需要延迟加载的属性PropertyBean。

```java
package com.zghw.cglib;
 
import net.sf.cglib.proxy.Enhancer;
 
public class LazyBean {
    private String name;
    private int age;
    private PropertyBean propertyBean;
    private PropertyBean propertyBeanDispatcher;
 
    public LazyBean(String name, int age) {
        System.out.println("lazy bean init");
        this.name = name;
        this.age = age;
        this.propertyBean = createPropertyBean();
        this.propertyBeanDispatcher = createPropertyBeanDispatcher();
    }
 
    
 
    /**
     * 只第一次懒加载
     * @return
     */
    private PropertyBean createPropertyBean() {
        /**
         * 使用cglib进行懒加载 对需要延迟加载的对象添加代理，在获取该对象属性时先通过代理类回调方法进行对象初始化。
         * 在不需要加载该对象时，只要不去获取该对象内属性，该对象就不会被初始化了（在CGLib的实现中只要去访问该对象内属性的getter方法，
         * 就会自动触发代理类回调）。
         */
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PropertyBean.class);
        PropertyBean pb = (PropertyBean) enhancer.create(PropertyBean.class,
                new ConcreteClassLazyLoader());
        return pb;
    }
    /**
     * 每次都懒加载
     * @return
     */
    private PropertyBean createPropertyBeanDispatcher() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PropertyBean.class);
        PropertyBean pb = (PropertyBean) enhancer.create(PropertyBean.class,
                new ConcreteClassDispatcher());
        return pb;
    }
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public int getAge() {
        return age;
    }
 
    public void setAge(int age) {
        this.age = age;
    }
 
    public PropertyBean getPropertyBean() {
        return propertyBean;
    }
 
    public void setPropertyBean(PropertyBean propertyBean) {
        this.propertyBean = propertyBean;
    }
 
    public PropertyBean getPropertyBeanDispatcher() {
        return propertyBeanDispatcher;
    }
 
    public void setPropertyBeanDispatcher(PropertyBean propertyBeanDispatcher) {
        this.propertyBeanDispatcher = propertyBeanDispatcher;
    }
 
    @Override
    public String toString() {
        return "LazyBean [name=" + name + ", age=" + age + ", propertyBean="
                + propertyBean + "]";
    }
}
```

```java
package com.zghw.cglib;
 
public class PropertyBean {
    private String key;
    private Object value;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return "PropertyBean [key=" + key + ", value=" + value + "]" +getClass();
    }
    
}
```

```java
package com.zghw.cglib;
 
import net.sf.cglib.proxy.LazyLoader;
 
public class ConcreteClassLazyLoader implements LazyLoader {
    /**
     * 对需要延迟加载的对象添加代理，在获取该对象属性时先通过代理类回调方法进行对象初始化。
     * 在不需要加载该对象时，只要不去获取该对象内属性，该对象就不会被初始化了（在CGLib的实现中只要去访问该对象内属性的getter方法，
     * 就会自动触发代理类回调）。
     */
    @Override
    public Object loadObject() throws Exception {
        System.out.println("before lazyLoader...");
        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setKey("zghw");
        propertyBean.setValue(new TargetObject());
        System.out.println("after lazyLoader...");
        return propertyBean;
    }
 
}
```

```java
package com.zghw.cglib;
 
import net.sf.cglib.proxy.Dispatcher;
 
public class ConcreteClassDispatcher implements Dispatcher{
 
    @Override
    public Object loadObject() throws Exception {
        System.out.println("before Dispatcher...");
        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setKey("xxx");
        propertyBean.setValue(new TargetObject());
        System.out.println("after Dispatcher...");
        return propertyBean;
    }
 
}
```

#### 6.接口生成器InterfaceMaker

##### 一、作用：

InterfaceMaker会动态生成一个接口，该接口包含指定类定义的所有方法。

##### 二、示例：

```java
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
        Object object = Enhancer.create(Object.class, new Class[]{targetInterface}, (MethodInterceptor) (obj, method, parameter, proxy) -> {
            System.out.println("show args into filter");
            for (Object object1 : parameter) {
                System.out.println(object1);
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
        });
        Method targetMethod1 = object.getClass().getMethod("method3", float.class);
        System.out.println((float) targetMethod1.invoke(object, new Object[]{33F}));
        Method targetMethod2 = object.getClass().getMethod("method1", String.class);
        System.out.println(targetMethod2.invoke(object, "spencer"));
    }
}
```