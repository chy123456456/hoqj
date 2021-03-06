package com.keng.main.mapper;
import java.util.List;

import com.keng.main.entity.SysRoleMenuKey;

public interface SysRoleMenuMapper {
    int deleteByPrimaryKey(SysRoleMenuKey key);

    int insert(SysRoleMenuKey record);

    int insertSelective(SysRoleMenuKey record);

    List<SysRoleMenuKey> selectByRoleId(Integer roleId);
}
