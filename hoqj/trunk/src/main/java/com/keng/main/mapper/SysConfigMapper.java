package com.keng.main.mapper;
import java.util.List;
import java.util.Map;

import com.keng.main.entity.SysConfig;
import com.keng.main.entity.SysConfigKey;

public interface SysConfigMapper {
    int deleteByPrimaryKey(SysConfigKey key);

    int insert(SysConfig record);

    int insertSelective(SysConfig record);

    int updateByPrimaryKeySelective(SysConfig record);

    int updateByPrimaryKey(SysConfig record);

    List<SysConfig> selectByParams(Map<String, Object> map);

    SysConfig selectByParam(Map<String, Object> map);

    int selectByParamsCount(Map<String, Object> map);

    List<SysConfig> selectPager(Map<String, Object> paramsMap);

    int selectPagerCount(Map<String, Object> paramsMap);
}