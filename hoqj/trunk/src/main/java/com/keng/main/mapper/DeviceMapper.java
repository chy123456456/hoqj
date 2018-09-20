package com.keng.main.mapper;

import java.util.List;
import java.util.Map;

import com.keng.main.entity.Device;

public interface DeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);

	List<?> selectPager(Map<String, Object> paramsMap);

	int selectPagerCount(Map<String, Object> paramsMap);

	List<Device> selectBySelective(Device device);

	List<Device> selectByDeviceNo(Device device);
}