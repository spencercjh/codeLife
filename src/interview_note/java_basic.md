# JavaBasic

这个部分参考了**极客时间**中的《Java核心技术36讲》，作者杨晓峰，前Oracle首席工程师，
其他参考书目如下：

1. 《码出高效：Java开发手册》 ISBN: 9787121349096
2. 《Effective Java中文版（第3版）》 ISBN: 9787111612728
3. 《设计模式之禅（第2版）》 ISBN: 9787111437871
4. 《Java核心技术·卷 II（原书第10版）》 ISBN: 9787111573319
5. 《阿里巴巴Java开发手册》 ISBN: 9787121332319

**这里不会包括高级Java内容：JVM、多线程等。相关内容请看java_advanced.md![java_advanced.md](java_advanced.md)**
***
## Java语言概况

![Java](./image/Java语言蓝图.png)

***
### Java语言是解释执行还是编译执行？描述一下Java代码的的执行流程

我们开发的 Java 的源代码，首先通过 Javac 编译成字节码（byteCode，.Class文件中的内容），然后在运行时，通过JVM内嵌的解释器（Interpreter）将字节码转换为最终的机器码。
这种情况下这些代码就是解释执行的。常见的JVM，比如我们大多数情况下使用的Oracle JDK提供的Hotspot JVM，都提供了JIT（Just In Time）编译器（compiler），也就是常说的动态编译器，
JIT能够在运行时将热点代码编译成机器码，这种情况下这些代码就是编译执行的。

Java通过字节码和JVM这种跨平台的抽象，屏蔽了操作系统和硬件的细节，这是“一次编译，到处执行”的基础。

我们通常把Java分为编译期和运行时。在运行时，JVM会通过类加载器（Class-Loader）加载字节码，解释或者编译执行。通常运行在server模式的JVM，会进行上万次调用以收集足够的信息进行高效的编译，
client模式这个门限是1500次。Oracle Hotspot JVM内置了两个不同的JIT Compiler：C1和C2。
C1对应client模式，适用于对于启动速度敏感的应用，比如默认Java桌面应用；C2对应server模式，它的优化是为了长时间运行的服务器端应用设计的。
默认采用分层编译（TieredCompilation)，这里不再赘述JIT的细节。

“一次编译、到处运行”说的是Java语言跨平台的特性，Java的跨平台特性与Java虚拟机的存在密不可分，可在不同的环境中运行。
比如说Windows平台和Linux平台都有相应的JDK，安装好JDK后也就有了Java语言的运行环境。其实Java语言本身与其他的编程语言没有特别大的差异，并不是说Java语言可以跨平台，而是在不同的平台都有可以让Java语言运行的环境而已，所以才有了Java一次编译，到处运行这样的效果。


对比：

C/C++编程是面向操作系统的，需要开发者极大地关心不同操作系统之间的差异性；

Python是一门解释执行的动态脚本语言。
***
### Exception和Error有什么区别？

![Java Exception&Error](./image/Java异常与错误.png)

#### 完整内容

Exception 和 Error 都是继承了 Throwable 类，在 Java 中只有 Throwable
类型的 实例才可以被抛出（throw）或者捕获（catch），它是异常处理机制的基本组成类型。

Exception 和 Error 体现了 Java 平台设计者对不同异常情况的分类。Exception 
是程序正常运行中，可以预料的意外情况，可能并且应该被捕获，进行相应处理。

Error 是指在正常情况下，不大可能出现的情况，绝大部分的 Error 都会导致程序
（比如 JVM 自身）处于非正常的、不可恢复状态。既然是非正常情况，所以不便于也不需要捕获，
常见的比如 OutOfMemoryError 之类，都是 Error 的子类。

Exception 又分为可检查（checked）异常和不检查（unchecked）异常，
可检查异常在源代码里必须显式地进行捕获处理，这是编译期检查的一部分。
前面我介绍的不可查的 Error，是 Throwable 不是 Exception。

不检查异常就是所谓的运行时异常，类似 NullPointerException、
ArrayIndexOutOfBoundsException 之类，通常是可以编码避免的逻辑错误，
具体根据需要来判断是否需要捕获，并不会在编译期强制要求。

编程基本原则： 
    
    1. 不要捕获Exception而是特定异常
    
    2. 不要生吞异常，即捕获到异常以后什么都不做
    
    3. 由于会产生额外的开销，影响JVM对代码进行优化，不要一个巨大的try-catch包住整段代码，
    仅捕获必要的代码段。利用异常控制代码流程
    比条件语句低效
***
### 接口和抽象类有什么区别？

#### 完整内容

接口和抽象类是 Java 面向对象设计的两个基础机制。

接口是对行为的抽象，它是抽象方法的集合，利用接口可以达到 API 定义和实现分离的目的。
接口，不能实例化；不能包含任何非常量成员，任何 field 都是隐含着 public static final 
的意义；同时，没有非静态方法实现，也就是说要么是抽象方法，要么是静态方法。
Java 标准类库中，定义了非常多的接口，比如 java.util.List。

抽象类是不能实例化的类，用 abstract 关键字修饰 class，其目的主要是代码重用。
除了不能实例化，形式上和一般的 Java 类并没有太大区别，可以有一个或者多个抽象方法，
也可以没有抽象方法。抽象类大多用于抽取相关 Java 类的共用方法实现或者是共同成员变量，
然后通过继承的方式达到代码复用的目的。Java 标准库中，
比如 collection 框架，很多通用部分就被抽取成为抽象类，
例如 java.util.AbstractList。

Java 类实现 interface 使用 implements 关键词，继承 abstract class 则是使用 
extends 关键词，我们可以参考 Java 标准库中的 ArrayList。  
    
```java
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable{
    // do something
}
```

Java8及Java8以后，interface增加了对default
method的支持。Java9以后甚至可以定义private default method。比如：

```java
public interface Collection<E> extends Iterable<E> {
     /**
      * Returns a sequential {@code Stream} with this collection as its source.
      *
      * <p>This method should be overridden when the {@link #spliterator()}
      * method cannot return a spliterator that is {@code IMMUTABLE},
      * {@code CONCURRENT}, or <em>late-binding</em>. (See {@link #spliterator()}
      * for details.)
      *
      * @implSpec
      * The default implementation creates a sequential {@code Stream} from the
      * collection's {@code Spliterator}.
      *
      * @return a sequential {@code Stream} over the elements in this collection
      * @since 1.8
      */
     default Stream<E> stream() {
         return StreamSupport.stream(spliterator(), false);
     }
  }
```
***
### 谈谈你对Java集合的理解？

#### 完整内容
Java狭义的容器集合即Collection类及其实现，不包括Map。类图如下：

![Collection](./image/JavaCollectionUML.png)

List，也就是我们前面介绍最多的有序集合，它提供了方便的访问、插入、删除等操作。

Vector，ArrayList和LinkedList这三者都是实现集合框架中的
List，也就是所谓的有序集合，因此具体功能也比较近似，比如都提供按照位置进行定位、添加或者删除的操作，都提供迭代器以遍历其内容等。但因为具体的设计区别，在行为、性能、线程安全等方面，表现又有很大不同。

Vector 是 Java 早期提供的线程安全的动态数组，如果不需要线程安全，并不建议选择，毕竟同步是有额外开销的。Vector 内部是使用对象数组来保存数据，可以根据需要自动的增加容量，当数组已满时，会创建新的数组，并拷贝原有数组数据。

ArrayList 是应用更加广泛的动态数组实现，它本身不是线程安全的，所以性能要好很多。与 Vector 近似，ArrayList 也是可以根据需要调整容量，不过两者的调整逻辑有所区别，Vector 在扩容时会提高 1 倍，而 ArrayList 则是增加 50%。

LinkedList 顾名思义是 Java 提供的双向链表，所以它不需要像上面两种那样调整容量，它也不是线程安全的。

Vector 和 ArrayList 作为动态数组，其内部元素以数组形式顺序存储的，所以非常适合随机访问的场合。除了尾部插入和删除元素，往往性能会相对较差，比如我们在中间位置插入一个元素，需要移动后续所有元素。而 LinkedList 进行节点插入、删除却要高效得多，但是随机访问性能则要比动态数组慢。

HashSet,TreeSet,LinkedHashSet实现了Set接口，Set是不允许重复元素的，这是和 List
最明显的区别，也就是不存在两个对象 equals 返回
true。我们在日常开发中有很多需要保证元素唯一性的场合。

TreeSet 支持自然顺序访问，但是添加、删除、包含等操作要相对低效（log(n) 时间）。

LinkedHashSet，内部构建了一个记录插入顺序的双向链表，因此提供了按照插入顺序遍历的能力，与此同时，也保证了常数时间的添加、删除、包含等操作，这些操作性能略低于 HashSet，因为需要维护链表的开销。

在遍历元素时，HashSet 性能受自身容量影响，所以初始化时，除非有必要，不然不要将其背后的 HashMap 容量设置过大。而对于 LinkedHashSet，由于其内部链表提供的方便，遍历性能只和元素多少有关系。
***
### 谈谈你对Java Map的理解？

#### 完整内容

HashTable是过时的，线程安全的，拓展了Dictionary类；
HashMap拓展了AbstractMap，实现了Map接口，底层使用数组，数组元素是链表或是红黑树，
线程不安全，不记录插入顺序，使用懒加载技术，第一次put的时候才会分配内存；
LinkedHashMap额外为Entry维护一个双向链表，保存下插入顺序；
TreeMap底层使用红黑树实现。

红黑树，一种平衡二叉树的实现，相较于AVL树对左右子树高度差的严格限制（不大于1），红黑树不作这样的限制，具体实现暂时了解的不多，但有一句口诀：
有红必有黑，红红不相连。

Java Map类图如下：

![Map](./image/JavaMapUML.png)

Hashtable 比较特别，作为类似 Vector、Stack 的早期集合相关类型，它是扩展了 Dictionary 类的，类结构上与 HashMap 之类明显不同。

HashMap 等其他 Map 实现则是都扩展了 AbstractMap，里面包含了通用方法抽象。不同 Map 的用途，从类图结构就能体现出来，设计目的已经体现在不同接口上。

大部分使用 Map 的场景，通常就是放入、访问或者删除，而对顺序没有特别要求，HashMap
在这种情况下基本是最好的选择。HashMap 的性能表现非常依赖于哈希码的有效性，请务必掌握
hashCode 和 equals 的一些基本约定。比如：

    equals 相等，hashCode 一定要相等。
    
    重写了 hashCode 也要重写 equals。
    
    hashCode 需要保持一致性，状态改变返回的哈希值仍然要一致。    
    
    equals 的对称、反射、传递等特性。
    
    使用TreeMap时compareTo的返回值和equals要一致。
    
虽然 LinkedHashMap 和 TreeMap
都可以保证某种顺序，但二者还是非常不同的。LinkedHashMap
通常提供的是遍历顺序符合插入顺序，它的实现是通过为条目（键值对）维护一个双向链表。注意，通过特定构造函数，我们可以创建反映访问顺序的实例，所谓的
put、get、compute
等，都算作“访问”。这种行为适用于一些特定应用场景，例如，我们构建一个空间占用敏感的资源池，希望可以自动将最不常被访问的对象释放掉，这就可以利用
LinkedHashMap 提供的机制来实现。

对于TreeMap，它的整体顺序是由键的顺序关系决定的，通过Comparator或Comparable（自然顺序）来决定。
***
### 谈谈HashMap的实现？

#### 简要回答

HashMap实现了Map接口，继承了AbstractMap，是Java语言中的哈希表实现。
底层使用数组table存放每一个key的链表头（红黑树树根），使用value的hashCode和内部的hash方法计算哈希值与数组的下标对应。
当不存在哈希冲突时候，查找元素的时间复杂度是O(n)；存在哈希冲突后，如果table的大小大于64，且某一个key下的链表节点数超过8个时，链表会转会为红黑树，当红黑树下的节点小于6个时，重新转化为链表。
HashMap是非线程安全的，在多线程情况下可能会导致元素丢失和链表死链的问题。
HashMap是懒加载的，在初始化的过程中只会根据initialCapacity和loadFactor去计算实际的capacity，并不会分配table数组空间。
实际capacity是接近initialCapacity的一个2的整数幂，如果initialCapacity是15，那么实际capacity是16，最后map扩容前最大的size等于capacity*loadFactor。
***

#### 谈谈你了解的Java IO？

#### 简要回答

有BIO，NIO和AIO。BIO是Java.io中的阻塞(Blocked)IO，NIO是Java.nio中的非阻塞(non-blocked)IO，AIO是异步(Asynchronous)IO。
***