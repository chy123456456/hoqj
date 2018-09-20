package com.keng.main.entity;
import java.io.Serializable;

public class SysUserRoleKey implements Serializable {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;
    private String           userId;
    private Integer           roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String string) {
        this.userId = string;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}