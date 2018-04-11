package org.yyx.netty;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Netty服务器启动类
 */
@SpringBootApplication
public class NettyApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
    }

    @Override
    public void run(String... args) {

    }
}