package com.keng.main.mapper;
import java.util.List;

import com.keng.main.entity.SysRoles;
import com.keng.base.common.Pager;

public interface SysRolesMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(SysRoles record);

    int insertSelective(SysRoles record);

    SysRoles selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(SysRoles record);

    int updateByPrimaryKey(SysRoles record);

    List<SysRoles> getRoles(Pager pager);

    List<SysRoles> getAllRoles();

    String getRoleTree(Integer roleId);

    String getRoleChild(Integer roleId);

    String getRoleParent(Integer roleId);

    List<SysRoles> selectRoleByParent(String childRole);

    Integer selectIdByName();

    Integer getRoleIdByRoleName(String roleName);   // 根据角色名称查询角色id
}
