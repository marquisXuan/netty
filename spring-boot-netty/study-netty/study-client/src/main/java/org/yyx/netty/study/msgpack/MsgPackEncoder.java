package org.yyx.netty.study.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * MsgPack编码器
 * <p>
 * create by 叶云轩 at 2018/4/12-下午7:29
 * contact by tdg_yyx@foxmail.com
 */
public class MsgPackEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        MessagePack messagePack = new MessagePack();
        // 序列化
        byte[] write = messagePack.write(msg);
        out.writeBytes(write);
    }
}
