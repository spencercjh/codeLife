### 5.Lambda 编程

> Lambda 表达式，或简称lambda，本质上就是可以传递给其他函数的一小段代码。

#### 语法

`{ ParameterList -> FunctionBody}`

参数列表并不像Java一样用括号包裹起来。

相比Java，Kotlin对Lambda有更多的简化：

* 如果Lambda 表达式是函数调用的最后一个实参，它可以放到右括号的后边;
* 如果Lambda 表达式是函数调用的唯一实参，可以去掉函数参数列表的圆括号.

比如这样：
```kotlin
data class Node(val name: String, val age: Int)

val peoples = listOf(Node("Alice", 11),
            Node("Peter", 15),
            Node("Jack", 8))

println(peoples.filter { person -> person.age > 10 })
println(peoples.filter ({ person -> person.age > 10 }))
```

将Lambda 表达式声明为参数：
```kotlin
fun <A> functionAsParameters(value: A, predicate: Function1<A, Boolean>) = predicate(value)
fun <A> lambdaAsParameters(value: A, predicate: (A) -> Boolean) = predicate(value)
val x = 123
println(functionAsParameters(x) { num: Int -> num > 0 })
println(lambdaAsParameters(x) { num: Int -> num > 0 })
```

这就引申出了另一个知识点： `Function`.请看[这里](https://github.com/spencercjh/codeLife/wiki/Exercise#2java-function-vs-kotlin-function)

#### 集合操作

这里我推荐一本书，[_Java Functional Programming_](https://book.douban.com/subject/27594722/)。

里面教你用Java 8实现自己的函数式编程组件：Function,Supply,Predicate,Optional,Tuple,Result,List,Stream等等等等。我本来也记不清`map`,`flatmap`,`filter`,搞不懂`Function<A ,Function<A, Function<B, C>>>`是什么意思。看了这本书，并亲自写下大量的实践代码后，对函数式编程，对`Lambda`，`java.util.function`,`Stream`都有了自己的认识和了解。

#### 序列和流 及其操作
 
 流是Kotlin基于JDK 6实现的对标Stream流的东西。它们的基本使用只要照着Java/KtDoc就不是问题。有一些进阶的技巧和经验需要看[_Effective Java 第三版_](https://book.douban.com/subject/30412517/)。我后面会更新的！
 
 先要搞清楚操作的事情。我觉得操作很重要，不恰当的流操作会让我们的编码效率大大降低，看似优雅的实现性能却大打折扣，这是我们不愿意看到的。让我们翻译一下JDK Stream的Doc：(谷歌机翻。我是看完英文再把机翻放上来的！)

流：

> 流操作和管道

> 流操作分为中间操作和终端操作，并合并以形成流管道。流管道由一个源（例如Collection，数组，生成器函数或I / O通道）组成；随后是零个或多个中间操作，例如Stream.filter或Stream.map；以及诸如Stream.forEach或Stream.reduce之类的终端操作。

> 中间操作返回一个新的流。他们总是很懒惰。执行诸如filter（）之类的中间操作实际上并不执行任何过滤，而是创建一个新的流，该新流在遍历时将包含与给定谓词匹配的初始流的元素。在执行管道的终端操作之前，不会开始遍历管道源。

> 诸如Stream.forEach或IntStream.sum之类的终端操作可能会遍历该流以产生结果或副作用。执行终端操作后，流管道被视为已消耗，无法再使用；如果需要再次遍历相同的数据源，则必须返回到数据源以获取新的流。在几乎所有情况下，终端操作人员都很渴望在返回之前完成对数据源的遍历和对管道的处理。只有终端操作iterator（）和spliterator（）不在；在现有操作不足以完成任务的情况下，这些命令将作为“转义阴影线”提供，以实现任意客户端控制的管道遍历。

> 延迟处理流可显着提高效率；在上面的filter-map-sum示例的管道中，可以将过滤，映射和求和合并到数据的一次传递中，并且中间状态最少。懒惰还可以避免在不必要时检查所有数据。对于诸如“查找长度超过1000个字符的第一个字符串”之类的操作，只需要检查足够多的字符串以查找具有所需特征的字符串，而无需检查可从源中获得的所有字符串。 （当输入流无限且不仅很大时，此行为就变得更加重要。）

> 中间操作进一步分为无状态操作和有状态操作。处理新元素时，无状态操作（例如过滤器和映射）不会保留先前看到的元素的状态-每个元素都可以独立于其他元素上的操作进行处理。在处理新元素时，诸如不同和已排序的有状态操作可能会合并先前看到的元素的状态。

> 有状态操作可能需要在产生结果之前处理整个输入。例如，在查看流的所有元素之前，不能对流进行排序产生任何结果。结果，在并行计算下，某些包含有状态中间操作的管道可能需要对数据进行多次遍历，或者可能需要缓冲大量数据。包含排他性无状态中间操作的管道可以通过一次处理（顺序或并行）进行处理，而数据缓冲最少。

> 此外，一些操作被认为是短路操作。如果出现无限输入时，中间操作可能会短路，这可能会导致产生有限流。如果出现无限输入时，端子操作可能会在有限时间内终止，则该端子操作会发生短路。在管道中进行短路操作是使无限流的处理在有限时间内正常终止的必要条件，但还不够。

序列：

> A sequence that returns values through its iterator. The values are evaluated lazily, and the sequence is potentially infinite.

> Sequence operations, like Sequence.map, Sequence.filter etc, generally preserve that property of a sequence, and again it's documented for an operation if it doesn't.

总结：Intermediate operations中间操作都能惰性地执行到序列上，Terminal operations会触发执行所有的延期计算。

#### with 和 apply

一个书上的例子（改过一点）：
```kotlin
val alphabet = { sb: StringBuilder ->
    for (letter in 'A'..'Z') {
        sb.append(letter)
    }
}

fun alphabetWith() = with(
    receiver = StringBuilder(),
    block = {
        alphabet(this)
        toString()
    }
)


fun alphabetApply() = StringBuilder().apply { alphabet(this) }.toString()

fun main() {
    println(alphabetWith())
    println(alphabetApply())
}
```

#### 内联函数 inline

简单说内联就是标注内联的函数会被编译器直接把代码编织到调用处而不走函数调用，上下文切换的过程。大多数简短的库函数API，它们都被标注为了`inline`。

> 对于普通的函数调用，JVM已经提供了强大的内联支持。它会分析代码的执行，并在任何通过内联能够带来好处的适合将函数调用内联。这是在将字节码转换成机器代码的时候自动完成的。在字节码中，每一个函数的实现只会出现一次，并不需要跟Kotlin的内联函数一样，每个调用的地方都拷贝一次。再说，如果函数被直接调用，调用栈会更加清晰。

#### Kotlin里的try-resource语句
try-resource在JDK里其实也是个语法糖？在Kotlin里成了一个拓展方法`use`.
```kotlin
fun readFirstLineFromFile(path: String): String? = BufferedReader(FileReader(path)).use { return it.readLine() }
```
看use的源码我就知道，它应该是`Closeable`的拓展函数，用来关资源用的。这里问题就来了，这个return可以写在lambda 外，也可以写在lambda里，这里面一定是有区别的，区别在哪里呢？下节就介绍了。

#### Lambda Return
1. 从lambda 返回

这个`return`其实暗藏玄机：如果`foreEach`不是内联函数的话，传进去的这个lambda 是不能return的。

```kotlin
fun returnInLambda() {
    listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).forEach {
        if (it == 5) {
            return
        }
    }
    // this line can't be reached
    println("lambda out")
}

fun main() {
    returnInLambda()
}
```

> 在一个非内联函数的lambda 中使用return表达式是不允许的。一个非内联函数可以把传给它的lambda 保存在变量中以便在函数返回时可以继续使用，这个时候lambda 想要去影响函数的返回已经太晚了。

2. 使用标签返回
我仿佛看到了`goto`……`@`在后的叫**lambda标签**，`@`在前的叫**返回表达式标签**。

```kotlin
fun returnByLabel() {
    list.forEach label@{
        if (it == 5) {
            return@label
        }
    }
    println("lambda out 1")
}

fun main() {
    returnByLabel()
}
```
3. 使用匿名函数返回（可以转换到使用标签返回）

它可以转化成去掉匿名函数加一个返回表达式标签`@forEach`
```kotlin
fun returnByAnonymousFunction() {
    list.forEach(fun(number) {
        if (number == 5) {
            return
        }
        println("not 5:${number}")
    })
    println("lambda out 2")
}

fun main() {
    returnByAnonymousFunction()
}
```
> 注意，尽管匿名函数看起来跟普通函数很相似，但它其实是lambda 表达式的另一种语法形式而已。关于lambda 表达式如何实现，以及内联函数中如何被内联的讨论同样适用于匿名函数。