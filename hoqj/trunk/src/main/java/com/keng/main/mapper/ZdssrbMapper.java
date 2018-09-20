package com.keng.main.mapper;

import java.util.List;
import java.util.Map;

import com.keng.main.entity.Zdssrb;

public interface ZdssrbMapper {
    int deleteByPrimaryKey(String zyh);

    int insert(Zdssrb record);

    int insertSelective(Zdssrb record);

    Zdssrb selectByPrimaryKey(String zyh);

    int updateByPrimaryKeySelective(Zdssrb record);

    int updateByPrimaryKey(Zdssrb record);

	List<?> selectPager(Map<String, Object> paramsMap);

	int selectPagerCount(Map<String, Object> paramsMap);
}