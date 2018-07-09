package org.yyx.netty.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yyx.netty.client.NettyClient;
import org.yyx.netty.rpc.util.NettyBeanScanner;

/**
 * Netty初始化
 * <p>
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/7/9 - 上午9:32
 */
@Configuration
public class NettyConfiguration {

    @Bean
    public NettyBeanScanner initNettyBeanScanner(@Value("${netty.basePackage}") String basePackage,
                                                 @Value("${netty.clientName}") String clientName) {
        return new NettyBeanScanner(basePackage, clientName);
    }

    @Bean("nettyClient")
    public NettyClient initNettyClient(@Value("${netty.url}") String url, @Value("${netty.port}") int port) {
        return new NettyClient(url, port);
    }
}
