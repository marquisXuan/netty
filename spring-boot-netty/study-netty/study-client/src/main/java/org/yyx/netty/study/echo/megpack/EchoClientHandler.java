package org.yyx.netty.study.echo.megpack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.netty.study.entity.User;

/**
 * <p>
 * create by 叶云轩 at 2018/4/12-下午4:57
 * contact by tdg_yyx@foxmail.com
 */
public class EchoClientHandler extends ChannelHandlerAdapter {

    /**
     * EchoClientHandler 日志控制器
     * Create by 叶云轩 at 2018/4/12 下午4:57
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EchoClientHandler.class);
    private final int sendNumber;
    /**
     * 计数器
     */
    private int counter;

    public EchoClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("--- [异常] {}", cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        User[] infos = UserInfo();
        for (User info : infos) {
            ctx.write(info);
        }
        ctx.flush();

    }

    private User[] UserInfo() {
        User[] users = new User[sendNumber];
        User user = null;
        for (int i = 0; i < sendNumber; i++) {
            user = new User();
            user.setUserGender(i);
            user.setUserName("YYX --->" + i);
            users[i] = user;
        }
        return users;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("--- [第{}次接收到服务器的消息] {} | [消息] {}", ++counter, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
