package org.yyx.netty.study.codec.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * MsgPack编码器
 * <p>
 * create by 叶云轩 at 2018/4/12-下午7:29
 * contact by tdg_yyx@foxmail.com
 */
public class MsgPackEncoder extends MessageToByteEncoder<Object> {

    /**
     * MsgPackEncoder 日志控制器
     * Create by 叶云轩 at 2018/5/3 下午3:18
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgPackEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        MessagePack messagePack = new MessagePack();
        // 序列化
        byte[] write = new byte[0];
        try {
            write = messagePack.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.writeBytes(write);
    }
}
