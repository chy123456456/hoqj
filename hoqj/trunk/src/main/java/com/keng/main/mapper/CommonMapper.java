package com.keng.main.mapper;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CommonMapper {
    Date selectDbCurrentTimestamp(Map<String, Object> params);

    Date selectDbCurrentDate(Map<String, Object> params);

    List<Map<String, Object>> selectByDynamicSql(@Param("sql") String sql);
}
