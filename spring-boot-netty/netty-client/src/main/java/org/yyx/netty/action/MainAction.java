package org.yyx.netty.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.yyx.netty.rpc.service.DemoService;

import javax.annotation.Resource;

/**
 * 程序主入口
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/11/1-17:05
 */
@Order(1)
@Component
public class MainAction implements CommandLineRunner {

    /**
     * MainAction 日志输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MainAction.class);

    /**
     * 测试业务
     */
    @Resource
    private DemoService demoService;

    @Override
    public void run(String... args) throws Exception {
//        LOGGER.info("{} -> [准备进行业务测试]", this.getClass().getName());
//        LOGGER.info("{} -> [rpc测试] ", this.getClass().getName());
//        int sum = demoService.sum(5, 8);
//        LOGGER.info("{} -> [rpc测试结果] {}", this.getClass().getName(), sum);
//        LOGGER.info("{} -> [字符串测试] ", this.getClass().getName());
//        String print = demoService.print();
//        LOGGER.info("{} -> [字符串测试结果] {}", this.getClass().getName(), print);
//        LOGGER.info("{} -> [对象测试] ", this.getClass().getName());
//        User userInfo = demoService.getUserInfo();
//        LOGGER.info("{} -> [对象测试结果] {}", this.getClass().getName(), userInfo);
//        LOGGER.info("{} -> [异常测试]", this.getClass().getName());
//        try {
//            double division = demoService.division(3, 0);
//            LOGGER.info("{} -> [异常测试结果] {}", this.getClass().getName(), division);
//        } catch (Exception e) {
//            LOGGER.error("{} -> [服务器异常] {}", this.getClass().getName(), e.getMessage());
//        }
    }
}
