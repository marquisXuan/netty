package org.yyx.netty.study.codec.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MsgPack编码器
 * <p>
 * create by 叶云轩 at 2018/4/12-下午7:29
 * contact by tdg_yyx@foxmail.com
 */
public class MsgPackEncoder extends MessageToByteEncoder<Object> {
    /**
     * MsgPackEncoder 日志控制器
     * Create by 叶云轩 at 2018/5/3 下午3:15
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgPackEncoder.class);

    /**
     * 编码 Object -> byte[]
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf out) throws Exception {
        LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                "\t├ [编码]: {}\n" +
                "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓", msg);
        MessagePack messagePack = new MessagePack();
        // 序列化
        byte[] write = messagePack.write(msg);
        out.writeBytes(write);
    }
}
