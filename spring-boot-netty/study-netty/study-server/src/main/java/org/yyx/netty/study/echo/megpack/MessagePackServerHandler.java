package org.yyx.netty.study.echo.megpack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.netty.entity.User;

import java.util.List;

/**
 * <p>
 * create by 叶云轩 at 2018/4/12-下午4:12
 * contact by tdg_yyx@foxmail.com
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/8/15 - 12:32
 */
public class MessagePackServerHandler extends ChannelHandlerAdapter {

    /**
     * MessagePackServerHandler 日志控制器
     * Create by 叶云轩 at 2018/4/12 下午4:20
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagePackServerHandler.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.info("--- [发生异常] 释放资源: {}", cause.getMessage());
        // todo
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("Server connect success");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        List<User> userInfo = (List<User>) msg;
        LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                "\t├ [接收 ]: {}\n" +
                "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓", userInfo);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
