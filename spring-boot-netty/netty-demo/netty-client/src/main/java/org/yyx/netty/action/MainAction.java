package org.yyx.netty.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yyx.netty.entity.User;
import org.yyx.netty.rpc.service.DemoService;

import javax.annotation.Resource;

/**
 * 主要用来进行模拟测试的类.就不用写接口来进行测试了
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/11/1-17:05
 */
@Component
public class MainAction {

    /**
     * MainAction 日志输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MainAction.class);

    /**
     * 测试业务
     */
    @Resource
    private DemoService demoService;

    /**
     * 真正远程调用的方法
     * @throws InterruptedException interruptedException
     */
    public void call() throws InterruptedException {
        // 用于模拟服务器正常启动后，人工调用远程服务代码
        Thread.sleep(10 * 1000);
        LOGGER.warn("[准备进行业务测试]");
        LOGGER.warn("[rpc测试] ");
        int sum = demoService.sum(5, 8);
        LOGGER.warn("[rpc测试结果] {}", sum);
        LOGGER.warn("[字符串测试] ");
        String print = demoService.print();
        LOGGER.warn("[字符串测试结果] {}", print);
        LOGGER.warn("[对象测试] ");
        User userInfo = demoService.getUserInfo();
        LOGGER.warn("[对象测试结果] {}", userInfo);
        LOGGER.warn("[异常测试]");
        try {
            double division = demoService.division(3, 0);
            LOGGER.warn("[异常测试结果] {}", division);
        } catch (Exception e) {
            LOGGER.error("[服务器异常] {}", e.getMessage());
        }
    }
}
