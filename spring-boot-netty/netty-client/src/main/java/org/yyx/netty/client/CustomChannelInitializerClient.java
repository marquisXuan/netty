package org.yyx.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.netty.entity.MethodInvokeMeta;
import org.yyx.netty.entity.NullWritable;
import org.yyx.netty.util.ObjectCodec;

/**
 * <p>
 * create by 叶云轩 at 2018/3/3-下午2:09
 * contact by tdg_yyx@foxmail.com
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/8/15 - 12:30
 */
public class CustomChannelInitializerClient extends ChannelInitializer<SocketChannel> {

    private Logger logger = LoggerFactory.getLogger(CustomChannelInitializerClient.class);

    private MethodInvokeMeta methodInvokeMeta;

    private Object response;

    public CustomChannelInitializerClient(MethodInvokeMeta methodInvokeMeta) {
        if (!"toString".equals(methodInvokeMeta.getMethodName())) {
            logger.info("[CustomChannelInitializerClient] 调用方法名：{}，入参：{},参数类型：{}，返回值类型{}"
                    , methodInvokeMeta.getMethodName()
                    , methodInvokeMeta.getArgs()
                    , methodInvokeMeta.getParameterTypes()
                    , methodInvokeMeta.getReturnType());
        }
        this.methodInvokeMeta = methodInvokeMeta;
    }

    public Object getResponse() {
        if (response instanceof NullWritable) {
            return null;
        }
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LengthFieldPrepender(2));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 2, 0, 2));
        pipeline.addLast(new ObjectCodec());
        pipeline.addLast(new ClientChannelHandlerAdapter(methodInvokeMeta, this));
    }
}
