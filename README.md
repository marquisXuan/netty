# Netty

> 有朋友看了博文想要一下源码，为此就整合了一下，现发布在这里.
>
> 当前项目对应的博文地址为：
>
> [开源中国](https://my.oschina.net/yzwjyw/blog/1614889)
>
> [CSDN](http://blog.csdn.net/yuanzhenwei521/article/details/79194275)
>
> [博客园](http://www.cnblogs.com/tdg-yyx/p/8376842.html)

### 使用说明：

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

