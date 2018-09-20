package com.keng.main.mapper;

import com.keng.main.entity.CostItem;

public interface CostItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CostItem record);

    int insertSelective(CostItem record);

    CostItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CostItem record);

    int updateByPrimaryKey(CostItem record);
}