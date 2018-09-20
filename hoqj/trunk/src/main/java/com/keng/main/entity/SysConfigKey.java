package com.keng.main.entity;
import java.io.Serializable;

public class SysConfigKey implements Serializable {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;
    private String            cfgId;
    private String            cfgKey;

    public String getCfgId() {
        return cfgId;
    }

    public void setCfgId(String cfgId) {
        this.cfgId = cfgId == null ? null : cfgId.trim();
    }

    public String getCfgKey() {
        return cfgKey;
    }

    public void setCfgKey(String cfgKey) {
        this.cfgKey = cfgKey == null ? null : cfgKey.trim();
    }
}