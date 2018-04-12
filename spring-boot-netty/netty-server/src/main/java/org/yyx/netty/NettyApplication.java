package org.yyx.netty;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yyx.netty.server.listener.NettyServerListener;

import javax.annotation.Resource;

/**
 * Netty服务器启动类
 */
@SpringBootApplication
public class NettyApplication implements CommandLineRunner {

    @Resource
    private NettyServerListener nettyServerListener;

    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
    }

    @Override
    public void run(String... args) {
        nettyServerListener.start();
    }
}