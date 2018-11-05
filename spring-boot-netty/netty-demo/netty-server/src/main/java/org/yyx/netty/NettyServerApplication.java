package org.yyx.netty;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yyx.netty.server.listener.NettyServerListener;

import javax.annotation.Resource;

/**
 * Netty服务器启动类
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/7/9 - 上午9:33
 */
@SpringBootApplication
public class NettyServerApplication implements CommandLineRunner {

    @Resource
    private NettyServerListener nettyServerListener;

    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        nettyServerListener.start();
    }
}