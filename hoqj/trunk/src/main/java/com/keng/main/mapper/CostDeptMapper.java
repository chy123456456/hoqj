package com.keng.main.mapper;

import com.keng.main.entity.CostDept;

public interface CostDeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CostDept record);

    int insertSelective(CostDept record);

    CostDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CostDept record);

    int updateByPrimaryKey(CostDept record);
}