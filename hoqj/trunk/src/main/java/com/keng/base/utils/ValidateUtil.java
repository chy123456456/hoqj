package com.keng.base.utils;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

@SuppressWarnings("rawtypes")
public class ValidateUtil {
    public static boolean isEmpty(Object o) {
        if (o == null) return true;
        if (o instanceof String) {
            return StringUtils.isEmpty((String) o);
        } else if (o instanceof Collection) {
            return CollectionUtils.isEmpty((Collection) o);
        } else if (o instanceof Map) {
            return MapUtils.isEmpty((Map) o);
        }
        return false;
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }
}