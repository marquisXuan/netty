# spring boot netty

### 资源库说明

首先,不是一个项目.而是一个DEMO性质的代码分享.

**当前资源库中包含了两大部分**

1. netty-demo下的可以在项目中使用的netty与SpringBoot集成的demo
2. 在study-netty下的netty相关的基础学习demo

## netty-demo

#### 简述
这是我之前博客中的集成代码,当然,现今的代码已经不完全是博文中所述那样.这里做了相应的优化与升级.主要包含以下几点:

1. 性能优化,不再循环中与服务端创建连接.
2. 代码优化,客户端代码添加了更多的注释,以便理解这样的做法.
3. 功能优化,添加了保持长连接的心跳检测功能,并添加了测试类模拟测试.

原博文地址: [开源中国](https://my.oschina.net/yzwjyw/blog/1614889) [CSDN](http://blog.csdn.net/yuanzhenwei521/article/details/79194275) [博客园](http://www.cnblogs.com/tdg-yyx/p/8376842.html)

#### PS

具体项目说明详见内部项目的说明文档

## study-netty

#### 简述

当前目录下的项目为netty基础学习demo.

#### PS

具体项目说明详见内部项目的说明文档


### 更新说明：

------

##### 2018-11-01：

1. 修改异常传输机制，使用异常类进行服务端信息传递，摒弃与HTTP类似的响应码结构体传递信息。添加了异常测试类。主项目仍为[netty-server](./spring-boot-netty/netty-server/README.md)和[netty-client](./spring-boot-netty/netty-client/README.md)。**study-netty**仅仅是学习项目。
2. 添加客户端阅读说明，说明我的思路。

------

### 使用说明：

### Netty + WebSocket - Server端

#### PS:已单独出另外一个项目进行细写[netty-websocket](https://github.com/marquisXuan/xpush)

此次更新主要添加了一个基于netty的websocket服务，可以做到群发消息，也可以做到点对点发送消息。代码在**stude-netty/study-server/src/main/java/org/yyx/netty/study/websocket**这里只给出了服务器代码，客户端代码采用的是**Chrome浏览器上的Simple Web Socket Client插件，省去了自己写页面的麻烦**。

#### WebSocketServer类

该类是服务端代码，将ChildHandler抽取出来放在了WebSocketChildHandler类中。

#### WebSocketServerHandler类

该类是主要的业务处理逻辑，在该类中进行了相关的处理，（具体说明，后续博文补上）

#### WebSocketUsers类

该类主要是为了保证通道唯一建立的类，因个人业务逻辑，提供了sendMessageToUser，sendMessageToUsers等方法，以用来进行群发和针对某用户发送某消息。

### 使用方法

服务器启动依旧和以前一样。不过此次启动方法为startWebSocketServer()方法。

客户端连接的地址为:ws://127.0.0.1:9999/websocket/用户名

用户名字段必须添加，否则会直接关闭连接，因为服务器没有做异常处理，这点根据个人需要进行修改。

### Netty + WebSocket - Client端

此次更新主要添加了一个基于Netty的WebSocket客户端。可以点对点发送消息，可以群发。已经过测试。开启Server端，启动Client的测试1，再启动Client的测试2.便可以测试2给测试1发送消息了。**当然中间要注释一些代码**，即，启动测试1时注释(**WebSocketClientHandler类128-133行**)，启动测试2时，打开。

Client一般为浏览器，所以也就不多说什么了。这个Client端没有自定义协议，使用了FastJson进行序列化。以后有时间了再把项目代码扔出来。这个作为Demo已经够用啦。

### Server端改进

Server端已添加MessagePack解码二进制数据支持。自己写的Client走这个。使用ByteBuf

浏览器客户端走TextWebSocketFrame.没有做过多的业务逻辑。

##### DEMO使用：

1. ###### 不考虑粘包/拆包问题的相关测试

   1. 打开study-netty项目下study-server项目中的StudyServerApplicationTests类
   2. 运行Junit测试startNettyServer1
   3. 打开study-netty项目下study-client项目中的StudyClientApplicationTests类
   4. 运行Junit测试startNettyClient1

2. ###### 模拟粘包/拆包问题的相关测试

   1. 打开study-netty项目下study-server项目中的StudyServerApplicationTests类
   2. 运行Junit测试startNettyServer2
   3. 打开study-netty项目下study-client项目中的StudyClientApplicationTests类
   4. 运行Junit测试startNettyClient2

3. ###### 解决粘包/拆包问题的相关测试LineBasedFrameDecoder实现

   1. 打开study-netty项目下study-server项目中的StudyServerApplicationTests类
   2. 运行Junit测试startNettyServer3
   3. 打开study-netty项目下study-client项目中的StudyClientApplicationTests类
   4. 运行Junit测试startNettyClient3

4. ###### 解决粘包/拆包问题的相关测试DelimiterBasedFrameDecoder实现

   1. 打开study-netty项目下study-server项目中的StudyServerApplicationTests类
   2. 运行Junit测试startNettyServer4
   3. 打开study-netty项目下study-client项目中的StudyClientApplicationTests类
   4. 运行Junit测试startNettyClient4

5. ###### 解决粘包/拆包问题的相关测试FixedLengthFrameDecoder实现

   1. 打开study-netty项目下study-server项目中的StudyServerApplicationTests类
   2. 运行Junit测试startNettyServer5
   3. 暂未写Client，可以使用telent进行测试，Client稍后提交



##### 项目使用：

1. 当前资源库中含有多个项目，位于spring-boot-netty目录下。IDE采用IDEA 2017.3.4
2. 依次检出项目netty-server与netty-client。
3. 先启动项目netty-server
4. 再启动项目netty-client.控制台将打印出对应信息
5. Study-netty项目是一个学习netty的相关例子与注释。整理了一下，不定期发布

#### 作者QQ: 562638362

#### 作者邮箱：marquis_xuan@163.com

