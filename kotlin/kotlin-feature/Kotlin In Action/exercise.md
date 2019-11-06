## 练习

这些实践是我在看书学习的过程中产生的一些思考的验证

### 1.实践 Lambda 和 集合、拓展、匿名内部类
源代码位置：`top.spencercjh.exercise.traverse`
filter容器，使用不同的写法。
```kotlin
// function needs to be manually imported
import java.util.function.Function
import java.util.stream.Collectors

data class Node(val name: String, val age: Int)

fun main() {
    val peoples = listOf(Node("Alice", 11),
            Node("Peter", 15),
            Node("Jack", 8))
    // Lambda expression equals kotlin.Function
    println(peoples.filter { person -> person.age > 10 })
    // use kotlin.function to replace Lambda
    val function = object : Function1<Node, Boolean> {
        override fun invoke(person: Node): Boolean {
            return person.age > 10
        }
    }
    println(peoples.filter(function))
    // In Kotlin, use java.util.Function instead
    fun <T> Iterable<T>.myFilter(predicate: Function<T, Boolean>): List<T> {
        val newArray = ArrayList<T>()
        for (t in this) {
            if (predicate.apply(t)) {
                newArray.add(t)
            }
        }
        return newArray
    }
    println(peoples.myFilter(Function { node: Node -> node.age > 10 }))
    // use Anonymous inner class to replace Lambda
    println(peoples.myFilter(object : Function<Node, Boolean> {
        override fun apply(t: Node): Boolean {
            return t.age > 10
        }
    }))
    println(peoples.filter { it.age > 10 })
    println(peoples.maxBy { it.age })
    println(peoples.stream().filter { it.age > 10 }.collect(Collectors.toList()).toString())
}
```
Java做一个对照：
```java
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Difference {
    private static List<String> myFilter(List<String> list, Function<String, Boolean> predicate) {
        final List<String> newList = new ArrayList<>();
        for (String s : list) {
            if (predicate.apply(s)) {
                newList.add(s);
            }
        }
        return newList;
    }

    public static void main(String[] args) {
        List<String> list = List.of("Alice", "Peter", "1", "2", "3");
        Function<String, Boolean> lambda = x -> x.length() > 1;
        System.out.println(myFilter(list, lambda));
        System.out.println(myFilter(list, new Function<String, Boolean>() {
            @Override
            public Boolean apply(String s) {
                return s.length() > 1;
            }
        }));
    }
}
```

事实证明：
* Lambda都可以和各自Function接口替换。
* 拓展函数非常方便和实用。不过要注意可维护问题。

***

### 2.Java Function VS Kotlin Function

源代码位置：`top.spencercjh.exercise.function`
根据第一节的实践，我们发现Lambda都可以和各自Function接口替换。所以我们深入一点点。

这方面Kotlin不愧是"函数式编程友善的"语言，"函数是头等公民"的语言：
```kotlin
val lambdaWithMultipleParameters = { x: Int, y: Int, z: Int -> x + y + z }
val same = object : Function3<Int, Int, Int, Int> {
    override fun invoke(p1: Int, p2: Int, p3: Int): Int {
        return p1 + p2 + p3
    }
}

fun main() {
    println(lambdaWithMultipleParameters(1, 2, 3))
    println(same(1, 2, 3))
}
```

Java里要写一个传多参的Function必须以这种繁琐的方法，写的人头都晕了。还好我之前看过Java Functional Programming，知道这些尖括号都什么意思……
```java
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Spencer
 */
public class Difference {
    public static void main(String[] args) {
        Function<Integer, BiFunction<Integer, Integer, Integer>> fun = new Function<Integer, BiFunction<Integer, Integer, Integer>>() {
            @Override
            public BiFunction<Integer, Integer, Integer> apply(Integer x) {
                return new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer y, Integer z) {
                        return x + y + z;
                    }
                };
            }
        };
        Function<Integer, BiFunction<Integer, Integer, Integer>> same = x -> (y, z) -> x + y + z;
        System.out.println(fun.apply(1).apply(2, 3));
        System.out.println(same.apply(1).apply(2, 3));
    }
}

```

仔细看源码：
在`kotlin.jvm.functions.Functions.kt`中，有以下代码（这个类不是我们直接call的，他们已经是关键字了）：
```kotlin
/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

// Auto-generated file. DO NOT EDIT!

package kotlin.jvm.functions

/** A function that takes 0 arguments. */
interface Function0<out R> : Function<R> {
    /** Invokes the function. */
    operator fun invoke(): R
}
/** A function that takes 1 argument. */
interface Function1<in P1, out R> : Function<R> {
    /** Invokes the function with the specified argument. */
    operator fun invoke(p1: P1): R
}
/** A function that takes 2 arguments. */
interface Function2<in P1, in P2, out R> : Function<R> {
    /** Invokes the function with the specified arguments. */
    operator fun invoke(p1: P1, p2: P2): R
}
/** A function that takes 3 arguments. */
interface Function3<in P1, in P2, in P3, out R> : Function<R> {
    /** Invokes the function with the specified arguments. */
    operator fun invoke(p1: P1, p2: P2, p3: P3): R
}
/** A function that takes 4 arguments. */
interface Function4<in P1, in P2, in P3, in P4, out R> : Function<R> {
    /** Invokes the function with the specified arguments. */
    operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4): R
}
// 后面还有.
```

经过简单的测试你就会发现，这个其实是无限的，所以是auto-generated file。

你可以在代码里写出`object : Function100<100个类型>`，只要你写出来了，就不会报错！

***

### 3. 在拓展函数里使用with/apply

IDE 帮助解决了我的疑惑
```kotlin
class WithAndApply

fun WithAndApply.extendsAlphabet() = with(receiver = StringBuilder()) {
    //  you can get reference to the extended class: alphabet(this@extendsAlphabet)
    alphabet(this)
    toString()
}

fun main() {
    println(WithAndApply().extendsAlphabet())
}
```
