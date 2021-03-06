[![TeamCity (simple build status)](https://img.shields.io/teamcity/http/teamcity.jetbrains.com/s/Kotlin_dev_Compiler.svg)](https://teamcity.jetbrains.com/viewType.html?buildTypeId=Kotlin_dev_Compiler&branch_Kotlin_dev=%3Cdefault%3E&tab=buildTypeStatusDiv)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

clone from: https://github.com/beyondfengyu/DistributedID

# 雪花算法分布式ID生成器
这个项目的目的是提供一个轻量级、高并发、高可用的生成唯一ID的服务，生成的ID是一个64位的
长整型，全局唯一，保持递增，相对有序。基于twitter的雪花算法来生成ID,用于取代UUID那种无序、128位的字符串形式的ID，提供
一种更加高效、人性化的全局唯一ID的生成方式，目前单机CPU4核、内存8G压测的并发数可以达到
**250万/每秒**，即每秒最多可以生成250万个唯一的ID，当然如果部署集群的话，这个数据可以
更高。
<br><br>
具体的教程可以参考我写的一篇文章：[基于twitter雪花算法的分布式ID —— 服务器篇](http://www.wolfbe.com/detail/201701/386.html)
<br><br>
**温馨提示：** 各位同学在fork之前，希望能够给个star，尊重一下作者的努力。
<br><br><br>
## 特点
* 基于twitter的雪花算法生成ID;
* 基于netty框架提供通信层接入；
* 提供HTTP和SDK两种方式接入；
* 轻量级、高并发、易伸缩；
* 部署简单，支持分布式部署；
<br>

## 接入
服务器支持两种方式接入：**HTTP**和**SDK**，无论哪一种方式接入，对于同一台服务器来说，调用的是同
一个ID生成器，所以得到的ID都是递增、有序的。
<br><br>

### HTTP接入
HTTP的接入方式很简单，直接访问IP+端口即可，或者域名+端口，端口号固定为**16830**。如果你不喜欢这种带有端口号的方式，可以考虑配置Nginx来做代理转发，配置Nginx对于部署分布式ID集群也有好处，可以通过Nginx来做负载均衡。
<br><br>

### SDK接入
SDK接入前需要在自己的项目中加入SDK的jar包，SDK可以参照我的另外一个项目[DistributedID-SDK](https://github.com/beyondfengyu/DistributedID-SDK)，或者自己写一个SDK来接入，语言不限。**DistributedID-SDK**提供了同步和异步两种请求方式，如果有高并发的要求，建议使用异步请求的方式，相同的环境下异步请求的性能会比同步请求的性能更高。
<br><br>

## 部署
部署之前需要把项目源码打包成jar包，或者使用项目打包好的jar包，把jar包上传到服务器，执行如下命令：
<br>
·java -jar distributedid.jar 1 2·
执行上面命令指定了两个参数1和2，前面的1代表数据中心标识，后面的2代表的是机器或进程标识.
<br><br>

如果不指定这两个参数，那么会使用默认的值1。如果只考虑部署单机服务器，那么可以不考虑这两个参数，**如果需要分布式集群来生成ID时，需要指定数据中心标识ID和机器进程标识ID，并且每一个服务器的数据中心标识ID和机器进程标识ID作为联合键全局唯一，这样才能保证集群生成的ID都是唯一的。**
<br>
<br>

## 交流
如果有兴趣交流Netty相关知识，可以加入**Netty联盟：379119816**
<br>
<br>
## 赞助
如果觉得项目还不错，想要表达些什么的话，可以上[爱淘汇：http://itao.wolfbe.com](http://itao.wolfbe.com) 领淘宝天猫的优惠券，领取优惠券再下单可以省不少钱喔。你们使用这些优惠券购买东西时，我也可以得到一些佣金的，多谢支持！！！
<br>
<br>
