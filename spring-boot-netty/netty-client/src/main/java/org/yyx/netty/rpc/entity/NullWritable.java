package org.yyx.netty.rpc.entity;

import java.io.Serializable;

/**
 * 服务器可能返回空的处理
 * <p>
 * create by 叶云轩 at 2018/3/3-下午1:39
 * contact by tdg_yyx@foxmail.com
 */
public class NullWritable implements Serializable {
    private static final long serialVersionUID = 2123827169429254101L;

    private static NullWritable instance = new NullWritable();

    private NullWritable() {
    }

    public static NullWritable nullWritable() {
        return instance;
    }
}
