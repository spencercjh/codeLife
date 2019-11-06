### 4.类，对象和接口

#### 修饰符

Java中的类默认是`open`的，Kotlin中默认是`final`的。

类的访问修饰符：

|   修饰符    |         相关成员         |                      评注                       |
| :---------: | :----------------------: | :---------------------------------------------: |
| final(默认) |        不能被重写        |                类中成员默认使用                 |
|    open     |        可以被重写        |                 需要明确地声明                  |
|  abstract   |        必须被重写        |     只能在抽象类中使用；抽象成员不能有实现      |
|  override   | 重写父类或者接口中的成员 | 如果没有使用`final`声明，重写的成员默认是开放的 |

internal是Kotlin特有的，表示在同一个Module中可见。

可见性修饰符：

|    修饰符    |    类成员    |   顶层声明   |
| :----------: | :----------: | :----------: |
| public(默认) | 所有地方可见 | 所有地方可见 |
|   internal   |  模块中可见  |  模块中可见  |
|  protected   |  子类中可见  |  文件中可见  |
|   private    |   类中可见   |  文件中可见  |

#### 内部类和嵌套类

_说实话在看着块内容之前，Java的这部分基础知识我都掌握的模棱两可。现在全懂了。_

在Java中，一个嵌套类加不加`static`的区别是，静态嵌套类会从这个类中删除包围它的类的隐式引用。

比如你在一个单元测试里为了测试需要写了一个嵌套类，如果不加`static`，将无法正常反序列化它（至少Jackson是这样的）。

> Kotlin中没有显示修饰符的嵌套类与Java中的`static`嵌套类是一样的。如果要把它变成一个内部类来持有一个外部类的引用的话需要使用`inner`修饰符。

对应关系：

|     类A在另一个类B中声明     |      Java      |    Kotlin     |
| :--------------------------: | :------------: | :-----------: |
| 嵌套类（不存储外部类的引用） | static class A |    class A    |
|  内部类（存储外部类的引用）  |    class A     | inner class A |

#### 密封类

不允许在类外部拥有子类。限制类的非预期继承。

#### 构造函数、属性

Kotlin区分主构造函数和从构造函数。写法很自由，但都殊途同归。

#### 判等

判等是每个语言里我最先关注的内容：

| 运算符 |       Java       |     Kotlin     |                      评注                      |
| :----: | :--------------: | :------------: | :--------------------------------------------: |
|   ==   | 值比较or引用比较 | 相当于`equals` | 如果`equals`被重写了就能直接用`==`进行引用比较 |
| equals |      值比较      |     值比较     |                      一样                      |
|  ===   |        无        |    引用比较    |            现代编程语言很多都有三等            |

#### 委托

委托在Java里需要用设计模式去实现，有很多冗余的模板代码。委托是Kotlin的语言特性，在看书之前我就根据official doc进行过一些学习。

[委托属性](https://www.kotlincn.net/docs/reference/delegated-properties.html) 简单说就是类中的一个属性是委托被另一个类处理的(get and set).在这个简单的游戏规则之上,Kotlin为我们搞了三种已经实现好的属性委托模式.

它有固定的语法结构: `val/var <属性名>: <类型> by <表达式>`

* 延迟属性 `lazy()`

`lazy()`是接受一个 lambda 并返回一个 Lazy <T> 实例的函数,返回的实例可以作为实现延迟属性的委托： 第一次调用 get() 会执行已传递给 lazy() 的 lambda 表达式并记录结果, 后续调用 get() 只是返回记录的结果.我马上想到,当需要写一些方法的初始化代码时,可以用lazy.

这个东西默认加锁.如果初始化委托的同步锁不是必需的,这样多个线程可以同时执行,那么将`LazyThreadSafetyMode.PUBLICATION`作为参数传递给 lazy() 函数.而如果你确定初始化将总是发生在与属性使用位于相同的线程,那么可以使用`LazyThreadSafetyMode.NONE`模式:它不会有任何线程安全的保证以及相关的开销.

例子:

```kotlin
val testLazy:String by lazy{
    println("first")
    "hello"
}

fun main(){
    println(testLazy)
    println(testLazy)
}
```

方法源码:

```kotlin
// org/jetbrains/kotlin/kotlin-stdlib/1.3.50/kotlin-stdlib-1.3.50-sources.jar!/kotlin/util/LazyJVM.kt

actual fun <T> lazy(mode: LazyThreadSafetyMode, initializer: () -> T): Lazy<T> =
    when (mode) {
        LazyThreadSafetyMode.SYNCHRONIZED -> SynchronizedLazyImpl(initializer)
        LazyThreadSafetyMode.PUBLICATION -> SafePublicationLazyImpl(initializer)
        LazyThreadSafetyMode.NONE -> UnsafeLazyImpl(initializer)
    }

actual fun <T> lazy(initializer: () -> T): Lazy<T> = SynchronizedLazyImpl(initializer)
```

* 可观察代理属性 `Delegates.observable()`

`Delegates.observable()`接受两个参数：初始值与修改时处理程序（handler）. 每当我们给属性赋值时会调用该处理程序（在赋值后执行）.这个handler有三个参数：被赋值的属性、旧值与新值.handler的签名是` (property: KProperty<*>, oldValue: T,newValue: T) -> Unit`.

这玩意就是观察者模式的官方实现呗.

在JDK里,`java.util.Observer`已经从JDK9开始`@Deprecated`了,所以Kotlin的这个`observable`挺方便的一种简单实现.

在日常业务中,什么时候需要用到观察者模式呢? :当数据之间有耦合地关联关系时,A发生变化B也要跟着变,B跟着变的代码不应该耦合在A类里面,这时候可以使用观察者模式.Kotlin的这个很方便,以后有实战使用再总结.

例子:
```kotlin
var testObservable: String by Delegates.observable(
    initialValue = "initialValue",
    onChange = { property, oldValue, newValue ->
        println("property:$property \t oldValue:$oldValue \t newValue:$newValue")
    })

fun main() {
    println(testObservable)
    testObservable = "first update"
    println(testObservable)
}
```
方法源码:
```kotlin
 inline fun <T> observable(initialValue: T, crossinline onChange: (property: KProperty<*>, oldValue: T,newValue: T) -> Unit):ReadWriteProperty<Any?, T> =
        object : ObservableProperty<T>(initialValue) {
            override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) = onChange(property, oldValue, newValue)
}
```

* 否决代理属性 `Delegates.vetoable()`

在`Delegates.observable()`的基础之上,如果你想截获赋值并“否决”它们,那么使用`vetoable()`取代`observable()`. 在属性被赋新值生效之前会调用传递给`vetoable`的处理程序.我觉得这个可以用在某些属性的特殊校验上.handler的签名是`(property: KProperty<*>, oldValue: T, newValue: T) -> Boolean`.

和`observable()`的`onChange`不同的是,这里传的lambda需要返回一个布尔值决定接不接受新值.

例子:
```kotlin
var testVetoable: String by Delegates.vetoable(initialValue = "initialValue",
    onChange = { property, oldValue, newValue ->
        println("property:$property")
        newValue.length > oldValue.length
    })

fun main() {
    println(testVetoable)
    testVetoable = "first"
    println(testVetoable)
    testVetoable = "LongLongLongLong"
    println(testVetoable)
}
```

方法源码:
```kotlin
    inline fun <T> vetoable(initialValue: T, crossinline onChange: (property: KProperty<*>, oldValue: T, newValue: T) -> Boolean):ReadWriteProperty<Any?, T> =
        object : ObservableProperty<T>(initialValue) {
        override fun beforeChange(property: KProperty<*>, oldValue: T, newValue: T): Boolean = onChange(property, oldValue, newValue)
    }
```

* 非空代理属性

返回具有非空值的读/写属性的属性委托，该值不是在对象构造期间而是在以后的时间初始化。尝试在分配初始值之前读取属性会导致异常。

说人话就是，当你声明了一个变量，声明的时候不想或者不知道怎么初始化它，就用`Delegates.notNull()`来代替无意义的初始化吧。在未初始化之前取值会抛出异常。

我写的这个例子就是,如果你把字符串初始化成了`""`的话(在Kotlin里又不能写成`=null`,我也不建议这样做,应该区分`var?`和`var`),后面不小心取了这样的empty value,没有及时抛出异常,就不好.

例子:

```kotlin
var testNotNull: String by Delegates.notNull()

fun main() {
//   assert exception:Property testNotNull should be initialized before get.
//   println(testNotNull)
    testNotNull = "123"
    println(testNotNull)
}
```
源码:

```kotlin
 fun <T : Any> notNull(): ReadWriteProperty<Any?, T> = NotNullVar()

private class NotNullVar<T : Any> : ReadWriteProperty<Any?, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("Property ${property.name} should be initialized before get.")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }
}
```

* 把属性储存在映射中

最直白的话说就是一个map观察着被委托的对象。对象的变化能够通知到map中对应value的变化,map中的变化也能通知到被委托对象的变化。比如这样：
```kotlin
package top.spencercjh.exercise

/**
 * @author SpencerCJH
 * @date 2019/11/6 0:14
 */
class Person {
    private val _attributes = hashMapOf<String, String>()

    fun setAttribute(key: String, value: String) {
        _attributes[key] = value
    }

    fun getAttribute(key: String) = _attributes[key]

    var name: String by _attributes
}

fun main() {
    val spencer = Person()
    spencer.setAttribute("name", "spencer")
//    spencer.setAttribute("name1", "spencer") assert exception
    println(spencer.name)
    spencer.name = "updated"
    println(spencer.getAttribute("name"))
}
```

7.5 节委托属性一节的内容我就在这里提前详细地介绍完了。

#### `object`关键字

使用场景：

* Singleton（只用`object`声明的单例是线程不安全的）；
* `companion object`声明工厂方法、静态属性；
* 对象表达式 `object: [Class Constructor]{[override methods]}`

对象表达式、Lambda表达式、匿名内部类能够相在一定程度上互转化。

***