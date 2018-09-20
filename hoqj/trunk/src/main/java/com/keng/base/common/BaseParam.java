package com.keng.base.common;
import java.util.Map;

import com.keng.base.utils.ObjectUtil;

public class BaseParam<K, V> extends MapWrapper<K, V> {
    public BaseParam() {
    }

    public BaseParam(Map<K, V> map) {
        this.map = map;
    }

    public Integer getAsInteger(K key) {
        return ObjectUtil.getInteger(map.get(key));
    }

    public Long getAsLong(K key) {
        return ObjectUtil.getLong(map.get(key));
    }

    public String getAsString(K key) {
        Object o = map.get(key);
        if (o == null) return null;
        return o.toString();
    }
}
