package com.keng.main.mapper;

import java.util.List;
import java.util.Map;

import com.keng.main.entity.Ylaqrb;

public interface YlaqrbMapper {
    int insert(Ylaqrb record);

    int insertSelective(Ylaqrb record);

	List<?> selectPager(Map<String, Object> paramsMap);

	int selectPagerCount(Map<String, Object> paramsMap);
}