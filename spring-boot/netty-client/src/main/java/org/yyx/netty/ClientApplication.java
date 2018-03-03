package org.yyx.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yyx.netty.rpc.entity.User;
import org.yyx.netty.rpc.service.DemoService;

import javax.annotation.Resource;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

    /**
     * ClientApplication 日志控制器
     * Create by 叶云轩 at 2018/3/3 下午2:24
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientApplication.class);
    /**
     * 测试业务
     */
    @Resource
    private DemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("\n----------------[rpc测试]----------------\n");
        int sum = demoService.sum(5, 8);
        LOGGER.info("\n\n----------------[5+8的和为]----------------\n {}\n", sum);
        String print = demoService.print();
        LOGGER.info("\n\n----------------[服务器返回的消息是]----------------\n {}\n", print);
        User userInfo = demoService.getUserInfo();
        LOGGER.info("\n\n----------------[用户信息是]----------------\n {}\n", userInfo);
    }
}