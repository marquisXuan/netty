package org.yyx.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.yyx.netty.action.MainAction;
import org.yyx.netty.config.NettyConfig;

import javax.annotation.Resource;

/**
 * netty客户端监听器
 * <p>
 * 主要用于延迟测试RPC和启动NettyClient
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/11/1-17:03
 */
@Order(0)
@Component
public class NettyClientListener implements CommandLineRunner {
    /**
     * NettyClientListener 日志输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientListener.class);

    /**
     * netty客户端配置
     */
    @Resource
    private NettyConfig nettyConfig;
    /**
     * 主要用于测试RPC场景的类。集成到自己的业务中就不需要此依赖
     */
    @Resource
    private MainAction mainAction;

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("{} -> [准备进行与服务端通信]", this.getClass().getName());
        // region 模拟RPC场景
        Thread t1 = new Thread(() -> {
            try {
                mainAction.call();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 使用一个线程模拟Client启动完毕后RPC的场景
        t1.start();
        // endregion
        // 获取服务器监听的端口
        int port = nettyConfig.getPort();
        // 获取服务器IP地址
        String url = nettyConfig.getUrl();
        // 启动NettyClient
        new NettyClient(port, url).start();
    }
}
