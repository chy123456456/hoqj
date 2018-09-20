package com.keng.main.mapper;

import com.keng.main.entity.DeviceRepair;

public interface DeviceRepairMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceRepair record);

    int insertSelective(DeviceRepair record);

    DeviceRepair selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceRepair record);

    int updateByPrimaryKey(DeviceRepair record);
}