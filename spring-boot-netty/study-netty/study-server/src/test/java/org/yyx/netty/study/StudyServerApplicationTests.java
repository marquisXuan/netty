package org.yyx.netty.study;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudyServerApplicationTests {

    private final int port = 8080;

    // region 启动不考虑粘包/拆包问题的netty服务
    @Test
    public void startNettyServer1() throws Exception {
        new org.yyx.netty.study.time.demo1.TimeServer().bind(port);
    }
    // endregion

    // region 启动模拟粘包/拆包问题的netty服务
    @Test
    public void startNettyServer2() throws Exception {
        new org.yyx.netty.study.time.demo2.TimeServer().bind(port);
    }
    // endregion

    // region 启动已解决粘包/拆包问题的netty服务 - LineBasedFrameDecoder 实现
    @Test
    public void startNettyServer3() throws Exception {
        new org.yyx.netty.study.time.demo3.TimeServer().bind(port);
    }
    // endregion

    // region 启动已解决粘包/拆包问题的netty服务 - DelimiterBasedFrameDecoder 实现
    @Test
    public void startNettyServer4() throws Exception {
        new org.yyx.netty.study.echo.delimiter.EchoServer().bind(port);
    }
    // endregion
}