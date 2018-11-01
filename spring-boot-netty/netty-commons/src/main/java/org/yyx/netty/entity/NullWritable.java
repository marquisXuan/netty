package org.yyx.netty.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 服务器可能返回空的处理
 * <p>
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/8/15 - 11:57
 */
@Data
public class NullWritable implements Serializable {
    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 2123827169429254101L;
    /**
     * 单例
     */
    private static NullWritable instance = new NullWritable();

    /**
     * 私有构造器
     */
    private NullWritable() {
    }

    /**
     * 返回代表Null的对象
     *
     * @return {@link NullWritable} 当方法返回值为void时或返回值为null时返回此对象
     */
    public static NullWritable nullWritable() {
        return instance;
    }
}
