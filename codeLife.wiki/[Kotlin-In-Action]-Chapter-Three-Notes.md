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

***