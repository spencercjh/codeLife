# Q:谈谈JVM内存区域的划分

## 简要回答

每个线程都有的程序计数器PC，Java栈，本地方法栈；
JVM共用的堆，堆里有分老年代和新生代；
JVM共有的方法区，方法区里有运行时常量池；
Buffer类中操作的DirectBuffer，也叫堆外内存。
还有一些JVM程序本身需要的内存，以及JIT编译时消耗的cache。

## 完整内容

这里描述的都是JDK8以后的Java。JVM内存结构图如下所示：

![JVM Memory](./image/JVM内存模型.png) 

程序计数器（PC Program Counter Register）

每个线程 都有它自己的PC，并且任何时间一个线程都只有一个方法在执行，也就是所谓的当前方法。
PC会存储当前线程正在执行的Java方法的JVM指令地址；或者如果是在执行本地方法，则是未指定值。

Java虚拟机栈（JVM Stack）

每个线程在创建时都会创建一个虚拟机栈，其内部保存一个个的栈帧（Stack Frame），对应着一次次的Java方法调用。
在同一个时间点，对应的只会有一个活动的栈帧，通常叫做当前帧，方法所在的类叫当前类。JVM对它的操作有压栈和出栈。
栈帧中存放着局部变量表、操作数栈、动态链接、方法正常退出或者异常退出的定义等。

堆（Heap）

它是Java内存管理的核心区域，用来放置Java对象的实例，几乎所有创建的Java对象实例都是被直接分配在堆上。
堆被所有的线程共享，在JVM启动时，我们制定的“Xmx”之类的参数就是用来制定最大堆空间等指标。堆内空间还会被不同的垃圾收集器进行进一步的划分，最有名的就是新生代、老年代的划分。

方法区（Method Area）

用来存储所谓的元数据（Meta Data），例如类结构信息，以及对应的运行时常量池、字段、方法代码等。由于早期的Hotspot JVM实现，很多人习惯于将方法去称为永久代（Permanent Generation)。
Oracle JDK 8中将它移除了，同时增加了元数据区（Metaspace）。

运行时常量池（Run-Time Constant Pool)

这是方法区的一部分。其中存放各种常量信息，不管是编译期生成的各种字面量，还是需要在运行时决定的符号引用。所以它比一般语言的符号表存储的符号表存储的信息更加宽泛。

本地方法栈（Native Method Stack）

它和 Java 虚拟机栈是非常相似的，支持对本地方法的调用也是每个线程都会创建一个。在Oracle Hotspot JVM中，本地方法栈和JVM栈在同一块区域，这完全取决于实现，并未在JVM规范中强制。

直接内存（Direct Memory)

Direct Buffer所直接分配的内存。在 JVM 工程师的眼中，并不认为它是 JVM 内部内存的一部分，也并未体现 JVM 内存模型中。

Direct Buffer

Direct Buffer：如果我们看 Buffer 的方法定义，你会发现它定义了 isDirect() 方法，返回当前 Buffer 是否是 Direct 类型。这是因为 Java 提供了堆内和堆外（Direct）Buffer，
我们可以以它的 allocate 或者 allocateDirect 方法直接创建。

在实际使用中，Java 会尽量对 Direct Buffer 仅做本地 IO 操作，对于很多大数据量的 IO 密集操作，可能会带来非常大的性能优势，因为：

    1. Direct Buffer 生命周期内内存地址都不会再发生更改，进而内核可以安全地对其进行访问，很多 IO 操作会很高效。

    2. 减少了堆内对象存储的可能额外维护工作，所以访问效率可能有所提高。
    
但是请注意，Direct Buffer 创建和销毁过程中，都会比一般的堆内 Buffer 增加部分开销，所以通常都建议用于长期使用、数据较大的场景。

其他

JVM本身是个本地程序，还需要其他的内存去完成各种基本任务，比如，JIT Compiler 在运行时对热点方法进行编译，就会将编译后的方法储存在 Code Cache 里面；GC 等功能需要运行在本地线程之中，
类似部分都需要占用内存空间。这些是实现 JVM JIT 等功能的需要，但规范中并不涉及。
***
### 线程的状态有哪些，他们是如何相互转化的？

#### 简要回答

一个线程从创建完对象实例开始状态为NEW；
Thread.start()后变为RUNNABLE；
获得CPU执行时间片后会变成RUNNING；
时间片用完后变回RUNNABLE；
碰到I/O释放，同步块释放，锁阻塞时变成BLOCKED；
碰到I/O释放，同步块释放，获得锁后变回RUNNABLE；
碰到Object.wait()释放锁，状态变成WAIT；
碰到Object.notify()或者Object.notifyAll()后重新回到RUNNABLE；
碰到Object.wait(timeout)释放锁，状态变成TIME_WAIT；
碰到Object.notify()或者Object.notifyAll()或者线程等待超时后重新回到RUNNABLE；
线程运行结束或者抛出异常退出后状态变成TERMINATED
***

### Object.wait()和Thread.sleep()有什么区别？

#### 简要回答

wait和sleep的主要区别是调用wait方法时，线程在等待的时候会释放掉它所获得的monitor，但是调用Thread.sleep()方法时，线程在等待的时候仍然会持有monitor或者锁。
另外，Java中的wait方法应在同步代码块中调用，但是sleep方法不需要。
另一个区别是Thread.sleep()方法是一个静态方法，作用在当前线程上；
但是wait方法是一个实例方法，并且只能在其他线程调用本实例的notify()方法时被唤醒。
另外，使用sleep方法时，被暂停的线程在被唤醒之后会立即进入就绪态（Runnable state)，但是使用wait方法的时候，被暂停的线程会首先获得锁（进入阻塞态），然后再进入就绪态。
***

### ConcurrentHashMap是如何实现线程安全的？

#### 简要回答

在JDK8之前，使用分段锁，将table分成多个Segment，对Segment加锁，以实现map中的一个部分可以进行线程安全地修改。

在JDK8以后，取消了这样的设计。put方法中使用ReservationNode在并发场景下对某一个slot(也就是table[i])加锁；
涉及到元素总数的相关更新和计算时，彻底避免使用锁，取而代之的是更多的CAS操作。
在竞争激烈的情况下使用内部类CounterCell作为一个计数器单元，当竞争进一步加剧时会通过扩容减少竞争。
***
### violated 和 synchronize 的区别？

#### 简要回答

***

### Executor,ExecutorService,Executors的关系和区别？

#### 简要回答

***

### ThreadPoolExecutor的参数都是什么含义？

#### 简要回答

***

### Executors中有哪些线程池？

#### 简要回答

***

