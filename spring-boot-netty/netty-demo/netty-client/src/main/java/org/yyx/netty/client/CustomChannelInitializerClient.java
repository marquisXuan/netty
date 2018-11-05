package org.yyx.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.yyx.netty.entity.MethodInvokeMeta;
import org.yyx.netty.entity.NullWritable;
import org.yyx.netty.util.ObjectCodec;

/**
 * <p>
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/8/15 - 12:30
 */
public class CustomChannelInitializerClient extends ChannelInitializer<SocketChannel> {

    /**
     * 远程服务元信息
     */
    private MethodInvokeMeta methodInvokeMeta;
    /**
     * 远程服务响应结果
     */
    private Object response;

    public CustomChannelInitializerClient(MethodInvokeMeta methodInvokeMeta) {
        this.methodInvokeMeta = methodInvokeMeta;
    }

    /**
     * 获取远程服务结果响应的方法
     *
     * @return {@link Object}响应结果
     * @throws Exception
     */
    public Object getResponse() throws Exception {
        if (response instanceof NullWritable) {
            // 空值返回
            return null;
        } else if (response instanceof Exception) {
            // 异常类
            Exception exception = (Exception) response;
            throw exception;
        }
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    /**
     * 初始化通道信息
     *
     * @param ch
     */
    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        // 基于定长的方式解决粘包/拆包问题
        pipeline.addLast(new LengthFieldPrepender(2));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 2, 0, 2));
        // 序列化方式 可使用 MsgPack 或 Protobuf 进行序列化扩展 具体可参考study-netty项目下的相关使用例子
        pipeline.addLast(new ObjectCodec());
        // 实际做处理的适配器
        pipeline.addLast(new ClientChannelHandlerAdapter(methodInvokeMeta, this));
    }
}
