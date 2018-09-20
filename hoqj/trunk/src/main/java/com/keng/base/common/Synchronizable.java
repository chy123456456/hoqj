package com.keng.base.common;
public interface Synchronizable {
    public <T> T getSyncObj(String key, T field);
}
