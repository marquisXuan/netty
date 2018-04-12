package org.yyx.netty.study.entity;

import lombok.Data;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * <p>
 * create by 叶云轩 at 2018/4/12-下午6:46
 * contact by tdg_yyx@foxmail.com
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 7217658384280375192L;

    private String userName;

    private int userGender;

    public byte[] codeC() {
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        byte[] bytes = this.userName.getBytes();
        allocate.putInt(bytes.length);
        allocate.put(bytes);
        allocate.putInt(this.userGender);
        allocate.flip();
        bytes = null;
        byte[] res = new byte[allocate.remaining()];
        allocate.get(res);
        return res;
    }

    public byte[] codeC(ByteBuffer buffer) {
        buffer.clear();
        byte[] bytes = this.userName.getBytes();
        buffer.putInt(bytes.length);
        buffer.put(bytes);
        buffer.putInt(this.userGender);
        buffer.flip();
        bytes = null;
        byte[] res = new byte[buffer.remaining()];
        buffer.get(res);
        return res;
    }
}
