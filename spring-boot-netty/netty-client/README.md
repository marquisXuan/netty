# Netty-client

> 该项目源自博主项目中实际应用场景。主要用作RPC通信中的客户端处理。

## 阅读说明

### 阅读顺序：

netty-client阅读顺序参考以下链条

```java
NettyConfiguration -> NettyBeanScanner -> PackageClassUtils(工具) -> RPCProxyFactoryBean -> WrapMethodUtils(工具) -> NettyClient -> CustomChannelInitializerClient -> ClientChannelHandlerAdapter
```

由NettyConfiguration入手，获取配置文件中的相关配置。

### 项目阅读

主要见博客：

[叶云轩的知识库](http://xuan.wp.happyqing.com/2018/09/17/netty%E4%B8%8Espring-boot%E7%9A%84%E6%95%B4%E5%90%88/)、[开源中国](https://my.oschina.net/yzwjyw/blog/1614889)、[CSDN](http://blog.csdn.net/yuanzhenwei521/article/details/79194275)、[博客园](http://www.cnblogs.com/tdg-yyx/p/8376842.html)

**更直接的参见源码注释。**

### 更新说明

------

#### 2018-11-01

1. 添加阅读思路，整理待优化细节

------

