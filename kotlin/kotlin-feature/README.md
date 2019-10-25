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

innerValue和readOnlyInnerValue将不会有包含他们的`component()`函数，不会在`toString()`,`equals()`,`hashCode()`,`copy()`中出现.

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

get和set可以写在var变量的下面，自定义其逻辑.

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

* 延迟属性 lazy
* 可观察属性 Delegates.observable() 
* 把属性储存在映射中

