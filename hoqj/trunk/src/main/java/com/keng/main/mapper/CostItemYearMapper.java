package com.keng.main.mapper;

import com.keng.main.entity.CostItemYear;

public interface CostItemYearMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CostItemYear record);

    int insertSelective(CostItemYear record);

    CostItemYear selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CostItemYear record);

    int updateByPrimaryKey(CostItemYear record);
}