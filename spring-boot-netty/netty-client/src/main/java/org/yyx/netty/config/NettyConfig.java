package org.yyx.netty.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * netty客户端配置
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/11/1-17:13
 */
@Component
@ConfigurationProperties(prefix = "netty")
@Data
public class NettyConfig {

    private String url;

    private int port;
}
