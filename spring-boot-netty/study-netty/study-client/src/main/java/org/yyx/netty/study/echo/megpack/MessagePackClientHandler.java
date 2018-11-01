package org.yyx.netty.study.echo.megpack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.netty.entity.User;

/**
 * <p>
 * create by 叶云轩 at 2018/4/12-下午4:57
 * contact by tdg_yyx@foxmail.com
 */
public class MessagePackClientHandler extends ChannelHandlerAdapter {

    /**
     * MessagePackClientHandler 日志控制器
     * Create by 叶云轩 at 2018/4/12 下午4:57
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagePackClientHandler.class);

    private final int sendNumber;

    public MessagePackClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("--- [异常] {}", cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        User[] userInfo = UserInfo();
        ctx.writeAndFlush(userInfo);
        for (User info : userInfo) {
            ctx.write(info);
        }
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        LOGGER.info("--- [收到服务器的消息] {}", msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    private User[] UserInfo() {
        User[] users = new User[sendNumber];
        User user;
        for (int i = 0; i < sendNumber; i++) {
            user = new User();
            user.setId(i);
            user.setName("YYX --->" + i);
            users[i] = user;
        }
        return users;
    }
}
