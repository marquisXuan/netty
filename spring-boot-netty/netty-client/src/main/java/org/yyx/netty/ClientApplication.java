package org.yyx.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yyx.netty.entity.User;
import org.yyx.netty.rpc.service.DemoService;

import javax.annotation.Resource;

/**
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/8/15 - 12:29
 */
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
        LOGGER.info("{} -> [rpc测试] ", this.getClass().getName());
        int sum = demoService.sum(5, 8);
        LOGGER.info("{} -> [rpc测试结果] {}", this.getClass().getName(), sum);
        LOGGER.info("{} -> [字符串测试] ", this.getClass().getName());
        String print = demoService.print();
        LOGGER.info("{} -> [字符串测试结果] {}", this.getClass().getName(), print);
        LOGGER.info("{} -> [对象测试] ", this.getClass().getName());
        User userInfo = demoService.getUserInfo();
        LOGGER.info("{} -> [对象测试结果] {}", this.getClass().getName(), userInfo);
        LOGGER.info("{} -> [异常测试]", this.getClass().getName());
        try {
            double division = demoService.division(3, 0);
            LOGGER.info("{} -> [异常测试结果] {}", this.getClass().getName(), division);
        } catch (Exception e) {
            LOGGER.error("{} -> [服务器异常] {}", this.getClass().getName(), e.getMessage());
        }
    }
}