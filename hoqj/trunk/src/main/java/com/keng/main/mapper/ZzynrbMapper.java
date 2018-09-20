package com.keng.main.mapper;

import java.util.List;
import java.util.Map;

import com.keng.main.entity.Zzynrb;

public interface ZzynrbMapper {
    int deleteByPrimaryKey(String zyh);

    int insert(Zzynrb record);

    int insertSelective(Zzynrb record);

    Zzynrb selectByPrimaryKey(String zyh);

    int updateByPrimaryKeySelective(Zzynrb record);

    int updateByPrimaryKey(Zzynrb record);

	List<?> selectPager(Map<String, Object> paramsMap);

	int selectPagerCount(Map<String, Object> paramsMap);
}