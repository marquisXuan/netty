package org.yyx.netty.rpc.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * 用户实体
 * <p>
 * create by 叶云轩 at 2018/3/3-下午1:48
 * contact by tdg_yyx@foxmail.com
 */
public class User implements Serializable {
    private static final long serialVersionUID = -5462474276911290451L;

    /**
     * 编号
     */
    private int id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 分数
     */
    private double source;
    /**
     * 领导
     */
    private User leader;

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", source=" + source +
                ", leader=" + leader +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Double.compare(user.source, source) == 0 &&
                Objects.equals(name, user.name) &&
                Objects.equals(leader, user.leader);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, source, leader);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSource() {
        return source;
    }

    public void setSource(double source) {
        this.source = source;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }
}
