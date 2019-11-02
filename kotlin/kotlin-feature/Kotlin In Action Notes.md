# Kotlin In Action Notes

《Kotlin实战》即《Kotlin In Action》由来自JetBrains的Kotlin核心开发者 Dmitry Jemerov和Svetlana Isakova所著。（他们应该是JetBrains圣彼得堡分部的开发人员哈哈哈）

我将按照页码顺序，从一个Java程序员的角度，记录有价值的笔记。

这个书是根据Kotlin 1.0 编写的，我目前正在学习的是 1.2 到 1.3 。1.3 最简单的一个feature就是`main`函数不用`array`的参数了。

## 正文

### 1.Kotlin简介

第一章就在安利Kotlin。我想在这里总结一下我被Kotlin深深吸引的原因——

* 安全
> `var?` and `var`  消除NPE；
> `is` and `as`  智能转换
> ……
* 简洁
> data class；
> 没有分号
> ……
* 现代
> 函数式编程；
> 命名参数；
> 默认参数值；
> 安全
> ……

总的来说，Kotlin集百家之长，让你在享受其他各种语言的feature同时写着Java。

### 2.Kotlin基础

#### 语句和表达式

在Java中，所有的控制语句都是语句；在Kotlin中除了循环，大多数如`if`,`try-catch`,`when`,`throw`等（控制）语句（还有lambda）是表达式，是右值，而不是语句。

在这些表达式中，代码块中最后的表达式就是结果（返回值）。

**很重要的一点：** Java中的赋值操作`=`是表达式，在Kotlin中反而是语句。

所以以下Java代码是很难直接转化成Kotlin代码的：

```java
String line;
while((line=reader.readLine())!=null){
	// do something
}
```

凑合一下，改成这样：

```kotlin
var line:String=reader.readLine()
while(line!=null){
	// do something
	line=read.readLine()
}
```
#### 变量

Kotlin的`val`和`var`帮助程序员转向函数式风格。

#### 包

Kotlin中可以把多个类放在同一个文件中，文件的名字还可以随意选择。它并不会对磁盘上源文件的布局强加任何限制。
不像Java中包和物理目录严格对应。

#### 用`in`,`when`

这个`in`和Python的应该差不多，等我写点测试确认一下。

`when`可以代替一些小型的`if-else`，分支较多的时候仍然应该使用*设计模式*。

#### 异常

Kotlin不区分_受检异常_和_未受检异常_，和Python一样。

> 经验显示这些Java规则常常导致许多毫无意义的重新抛出或忽略异常的代码。

深有体会，当下层的API乱抛异常的时候，上层调用真的很痛苦。

### 3.函数的定义与调用

#### 拓展

拓展函数语法：`fun [TargetClass].[ExtendsFunctionName](ParameterList):[ReturnType][FunctionBody|ExpressionBody]`

拓展属性语法：`var|val [TargetClass].[ValueName]:[ValueType] (GetterBody|SetterBody)`

> 实质上，拓展函数就是静态函数的一个高效的语法糖。它把调用对象作为了它的第一个参数。调用拓展函数，不会创建适配的对象或者任何运行时的额外消耗。

Kotlin拓展了java.util.collection：

|Kotlin|==>|Java|
|:---:|:---:|:---:|
|set|==>|HashSet|
|list|==>|ArrayList|
|map|==>|HashMap|

根据我的印象和理解，Kotlin拓展了这些collection的函数式API。

#### 命名参数

和Python一样。

> 不幸的是，当你调用Java的函数时，不能采用命名参数，不管是JDK中的参数，或者是Android框架的函数，都不行。把参数名称存到`.class`文件是Java 8 及其更高版本的一个可选功能，而Kotlin需要保持和Java 6 的兼容性。

比如我在写RESTful Demo时就碰到`javax.servlet.ResponseEntity`（包名可以记不清了）不能命名参数。

#### 默认参数

和Python一样。

> 参数的默认值是被编码到被调用的函数中，而不是调用的地方。

> 考虑到Java没有参数默认值的概念，当你从Java中调用Kotlin函数的时候，必须显示地指定所有参数值。如果需要从Java代码中频繁地调用，而且希望它对Java的调用者更简便，可以用`@JvmOverloads`注解它。这个指示编译器生成Java重载函数，从最后一个开始省略每个参数。

#### 顶层函数和属性

Kotlin推荐你把东西写到类外面去，那边叫“顶层”。用它来代替静态工具类。

#### 可变参数

用`vararg`代替了Java的`...`

#### 中缀调用和解构声明

`mapOf`里用的`to`，是`infix`关键字声明的库函数。它能返回一个`Pair`对象。

```kotlin
for( (k,v) in mapOf()){}

val (first,second) = 1 to "one"
```
这样的语法其实里面涉及到的就是解构声明。

#### 局部函数

函数内部的函数。局部函数可以访问外部元素，但相对函数外是私有的域，有点闭包的味道。

### 4.类，对象和接口

#### 修饰符

Java中的类默认是`open`的，Kotlin中默认是`final`的。

类的访问修饰符：

|修饰符|相关成员|评注|
|:---:|:---:|:---:|
|final(默认)|不能被重写|类中成员默认使用|
|open|可以被重写|需要明确地声明|
|abstract|必须被重写|只能在抽象类中使用；抽象成员不能有实现|
|override|重写父类或者接口中的成员|如果没有使用`final`声明，重写的成员默认是开放的|

internal是Kotlin特有的，表示在同一个Module中可见。

可见性修饰符：

|修饰符|类成员|顶层声明|
|:---:|:---:|:---:|
|public(默认)|所有地方可见|所有地方可见|
|internal|模块中可见|模块中可见|
|protected|子类中可见|文件中可见|
|private|类中可见|文件中可见|

#### 内部类和嵌套类

_说实话在看着块内容之前，Java的这部分基础知识我都掌握的模棱两可。现在全懂了。_

在Java中，一个嵌套类加不加`static`的区别是，静态嵌套类会从这个类中删除包围它的类的隐式引用。

比如你在一个单元测试里为了测试需要写了一个嵌套类，如果不加`static`，将无法正常反序列化它（至少Jackson是这样的）。

> Kotlin中没有显示修饰符的嵌套类与Java中的`static`嵌套类是一样的。如果要把它变成一个内部类来持有一个外部类的引用的话需要使用`inner`修饰符。

对应关系：

|类A在另一个类B中声明|Java|Kotlin|
|:---:|:---:|:---:|
|嵌套类（不存储外部类的引用）|static class A|class A|
|内部类（存储外部类的引用）|class A|inner class A|

#### 密封类

不允许在类外部拥有子类

#### 构造函数、属性

Kotlin区分主构造函数和从构造函数。写法很自由，但都殊途同归。

#### 判等

判等是每个语言里我最先关注的内容：

|运算符|Java|Kotlin|评注|
|:---:|:---:|:---:|:---:|
|==|值比较or引用比较|相当于`equals`|如果`equals`被重写了就能直接用`==`进行引用比较|
|equals|值比较|值比较|一样|
|===|无|引用比较|现代编程语言很多都有三等|