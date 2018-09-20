package com.keng.main.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.keng.main.entity.SysMenus;

public interface SysMenusMapper {
    int deleteByPrimaryKey(Integer menuId);

    int deleteByMenuId(@Param("menuId") Integer menuId, @Param("parentId") Integer parentId);

    int insert(SysMenus record);

    int insertSelective(SysMenus record);

    SysMenus selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(SysMenus record);

    int updateByPrimaryKey(SysMenus record);

    List<SysMenus> getAllMenus();
}
