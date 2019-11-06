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

***