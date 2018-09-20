package com.keng.main.mapper;
import java.util.List;

import com.keng.main.entity.SysUserRoleKey;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(SysUserRoleKey key);

    int insert(SysUserRoleKey record);

    int insertSelective(SysUserRoleKey record);

    List<SysUserRoleKey> selectByUserId(int userId);

    int deleteByUserId(String userId);
}
