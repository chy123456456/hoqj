package com.keng.main.mapper;

import com.keng.main.entity.BudgetItemYear;

public interface BudgetItemYearMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BudgetItemYear record);

    int insertSelective(BudgetItemYear record);

    BudgetItemYear selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BudgetItemYear record);

    int updateByPrimaryKey(BudgetItemYear record);
}