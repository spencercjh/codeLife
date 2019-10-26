# Kotlin Study

我将从一个Java程序员要实现具体需求的角度,从实际的业务出发,扩散开来介绍其中涉及的所有的和Java不一样的Kotlin语言特性.参考：[kotlin 中国](https://www.kotlincn.net/docs/reference/)

## Chapter1:实现一个Data Class

reference: https://www.kotlincn.net/docs/reference/data-classes.html

在Java里我们有`Lombok`,但在Kotlin里呢?有data class,它和`Lombok`比有过之而无不及.

```kotlin
data class User(val name: String = "testName", val age: Int = 22) {
    var innerValue: String = "123"
    // val 变量是只读的
    val readOnlyInnerValue: String = "readOnly"
}

val user: User = User()

fun main(args: Array<String>) {
    println(user)
    user.innerValue = "456"
    println(user.innerValue)
    println(user.readOnlyInnerValue)
    println(user.hashCode())
}

```
在上面的代码段中:

生成了含有User类name和age字段的`toString()`,`hashCode()`,`equals()`,`copy()`,`component1()`,`component2()`

由于它们是val变量,所以只生成get方法而没有set方法.

由于我给予了它们默认值,所以我可以在构造函数里不传参数.这在Java里是不可以的,只能用重载或者工厂方法去实现.

innerValue和readOnlyInnerValue将不会有包含他们的`component()`函数,不会在`toString()`,`equals()`,`hashCode()`,`copy()`中出现.

Kotlin的可见性修饰符默认是`public`,而Java是"包可见".Kotlin没有"包可见"了,需要import.Kotlin特有`internal`可见性修饰符,用来描述模块内可见.详细的关于可见性修饰符的内容可以参考: https://www.kotlincn.net/docs/reference/visibility-modifiers.html

可以在类体中override这些方法(`toString()`,`hashCode()`,`equals()`)覆盖原本data class的实现.而`componentN()`以及`copy()`函数并没有显示的实现.

以下是data class的应用.这是一个简单的建造者模式,封装了api的返回格式:
```kotlin
data class Result<T>(val code: Int?,
                    val message: String?,
                    val status: Boolean?,
                    val body: T? = null,
                    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())) {

    data class Builder<T>(var code: Int? = null,
                          var message: String? = null,
                          var status: Boolean? = null,
                          var body: T? = null
    ) {
        fun code(code: Int) = apply { this.code = code }
        fun message(message: String) = apply { this.message = message }
        fun status(status: Boolean) = apply { this.status = status }
        fun body(body: T?) = apply { this.body = body }
        fun build() = Result(code, message, status, body)
    }
}
```

## Chapter2:实现单例Singleton

reference:  https://www.jianshu.com/p/5797b3d0ebd0

你可以在上述blog中学习到kotlin的对象`object`关键字,伴生对象`companion object`,`get()`的自定义,代码块加锁`@Synchronized`,变量线程间可见`@Volatile`和委托属性中的延迟属性`by lazy`

简单说`object`就是用来描述静态类的.`object class`里的所有函数和属性都相当于在`companion object`代码段中.

get和set可以写在var变量的下面,自定义其逻辑.

`@Synchronized`和`@Volatile`注解和Java的这两个同名关键字差不多.

### 委托和委托属性

关于委托,可以先看看什么叫委托模式:[wiki](https://zh.wikipedia.org/wiki/%E5%A7%94%E6%89%98%E6%A8%A1%E5%BC%8F)

其实Kotlin的[委托](https://www.kotlincn.net/docs/reference/delegation.html) 和Java里的代理非常像.不同的是Kotlin把它们提炼出来,搞了一些关键字.

一个简单的类委托的例子:
```kotlin
package chapter2.delegation

/**
 * @author SpencerCJH
 * @date 2019/10/18 20:32
 */
interface Base {
    val message: String
    fun print()
}

class BaseImpl(private val x: Int) : Base {
    override val message: String = "base implement message"

    override fun print() {
        print(x)
    }
}

class Derived(base: Base) : Base by base {
    override val message: String = "derived override message"

    override fun print() {
        println("derived print override base function")
    }
}

fun main(args: Array<String>) {
    val b = BaseImpl(10)
    val derived = Derived(b)
    derived.print()
    println(derived.message)
}
```

#### 委托属性

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

关于Kotlin的Lambda我后面马上接着一个chapter会讲,写了几下发现和Java有明显的语法不同.

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

