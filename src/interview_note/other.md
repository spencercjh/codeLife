### TCP建立链接为什么需要三次握手，而不是两次？

解释1.三次握手可以确保发送端和接收端来去之间的链路都畅通：如A向B建立链接，发送SYN到B，B收到SYN，这时候A没法知道A到B是否是畅通的，B知道A到B是畅通的但不知道B到A是否是畅通的，接着B发回ACK，SYN，A收到后知道了A到B，B到A的链路都是畅通的，但对B来说，B仍然只知道A到B是畅通的，不知道B到A发出去的消息有没有被收到，最后A再向B发出ACK，这时候AB两端都知道A到B，B到A互相是畅通的。

解释2.三次握手可以防止建立错误的连接：如果A有旧链接的建立连接请求因为网络原因刚到达B，对B来说，B没法分辨这是不是一个正确的请求，只会机械地回复ACK，SYN。如果只有两次握手，到这B就会一直等待A发送对应的数据，导致出错，浪费系统资源。有了第三次握手，A就能察觉到这个错误，并设置RESET发回告诉B这是一个错误的发起连接请求，B重新变回LISTEN正常接收连接。
***
### TCP关闭连接为什么需要四次挥手？


因为TCP是双全工通信的，A给B发FIN仅代表A不会给B继续发有效数据了，而B可以继续给A发送有效数据并得到ACK。
***
### 什么是TIME_WAIT，为什么需要TIME_WAIT,TIME_WAIT可能会造成什么问题？


以A为主动关闭方,B为被动关闭方,最后一次挥手后，即A向B发出最后一个ACK后，A就进入了TIME_WAIT状态，这是因为A向B发出最后一个ACK后B并不会向A回应，而是直接关闭，因此A没法知道B是否收到了它发出的ACK请求，如果直接关闭而最后一个ACK丢失的话，B会向A重传FIN，而A会给B回RST，导致B不能被正确地关闭，因此A会等待2MSL（2\*Maximum Segment Lifetime）来尽量防止这种情况发生，注意这里并不是保证，假如A发送的ACK丢失，B超时重传FIN并一直丢失，2MSL并不能处理这种情况，因此MSL在各个系统的定义不同，仅是表示了系统对这种情况的忍耐程度。

若客户端为主动关闭方（只有主动关闭方才会进入TIME_WAIT状态），大量的socket处于TIME_WAIT状态可能会导致客户端的port用尽，对服务端来说，可能会使kernel memory耗尽， 也可能会导致file descriptor耗尽（fd只有在TIME_WAIT结束后才会被释放）。
***


### 你知道OOP中的SOLID原则吗？

#### 完整内容

##### Single Responsibility Principle 单一职责原则

There should never be more than one reason for a class to
change.单一职责适用于接口、类，同时也适用于方法。一个方法尽可能做一件事情。
比较好的实践是，接口一定要做到单一职责，而类的设计尽量做到只有一个原因引起变化。

##### Liskov Substitution Principle 里氏替换原则

Functions that use pointers or references to base classes must be able
to use objects of derived classes without knowing
it.（所有引用基类的复方必须能透明地使用其子类的对象。）通俗地说，
只要父类能出现的地方子类就可以出现，而且替换为子类也不会产生任何错误或异常，使用者可能根本就
不需要知道是父类还是子类。但是反之就不正确了，有子类出现的地方，父类未必就能适应。

定义包含了以下四层含义：
    
    1. 子类必须完全实现父类的方法。
    
    2. 子类可以有自己的方法和属性。
    
    3. 覆盖或实现父类的方法时输入参数可以被放大。
    
    4. 覆盖或实现父类的方法适输出结果可以被缩小。

##### Dependency Inversion Principle 依赖导致原则

High level modules should not depend upon low level modules. Both should
depend upon abstractions. Abstractions should not depend upon
details.Details should depend upon abstractions.

翻译过来包含三层含义：

    1. 高层模块不应该依赖底层模块，两者都应该依赖其抽象。
    
    2. 抽象不应该依赖细节。
    
    3. 细节应该依赖抽象。

依赖倒置原则的本质就是通过抽象（接口或者抽象类）使各个类或者模块的实现彼此独立，不互相影响，实现模块间的松耦合，需要遵循以下规则：
    
    1. 每个类尽量都有接口或者抽象类，或者都具备。
    
    2. 变量的表面类型尽量是接口或者抽象类。
    
    3. 任何类都不应该从具体类派生。
    
    4. 尽量不要覆写基类的方法。
    
    5. 结合里式替换原则使用。
        接口负责定义public实行和方法，并且声明与其他对象的依赖关系，抽象类负责公共构造部分的实现，实现类准备地实现业务逻辑，同时在适当的时候对父类进行细化。


##### Interface Segregation Principle 接口隔离原则

Clients should not be forced to depend upon interfaces that they don't
use.The dependency of one class to another one should depend on the
smallest possible
interface.（客户端不应该依赖它不需要的接口。类间的依赖关系应该建立在最小的接口上。）

接口隔离原则与单一职责原则的审视角度是不相同的，单一职责要求的是类和接口职责单一，
注重的是职责，这是业务逻辑上的划分，而接口隔离原则要求接口的方法尽量少，避免庞大臃肿的接口的出现。
原则总结为以下含义：

    1. 接口要尽量小
        根据接口隔离原则拆分接口时，首先必须满足单一职责原则。
    2. 接口要高内聚
        尽量少公布public方法。
    3. 定制服务
        单独为一个个体提供优良的服务。
    4. 接口的设计是有限度的
        一个接口只服务于一个子模块或业务逻辑。

##### Least Knowledge Principle(Law of Demeter) 最少知识原则（迪米特法则）

一个对象应该对其他对象有最少的了解。通俗地说，一个类应该对自己需要耦合或者调用的类知道的越少越好，
被耦合或者被调用的类的内容是如何复杂的都和这个类没有关系。包含以下含义：
    
    1. 只和朋友交流
        朋友指出现在成员变量，方法的输入输出参数中的类称为成员朋友类，而出现在方法体内部的类不属于朋友类。
    2. 朋友间是有距离的
        反复衡量是否还可以再减少publish方法和属性，是否可以修改为private,package-private,protected等访问权限，是否可以加上final关键字。

##### Open/Close Principle 开闭原则

Software entities like classes,modules and fuctions should be open for
extension but closed for
modifications.一个软件实体如类、模块和函数应该对拓展开放，对修改关闭。
软件实体应该通过拓展来实现变化，而不是通过修改已有的代码来实现变化。