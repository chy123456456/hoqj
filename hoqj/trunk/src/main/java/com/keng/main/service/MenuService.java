package com.keng.main.service;
import java.util.List;

import com.keng.main.entity.SysMenus;

public interface MenuService {
    /**
     * 查询所有的菜单
     * @date 2014-3-3 下午4:20:20
     * @return
     */
    public List<SysMenus> getAllMenus();

    /**
     * 修改菜单
     * @date 2014-3-3 下午4:20:29
     * @param menu
     * @return
     */
    public int updateMenu(SysMenus menu);

    /**
     * 添加菜单
     * @date 2014-3-3 下午4:20:37
     * @param menu
     * @return
     */
    public int insertMenu(SysMenus menu);

    /**
     * 删除菜单
     * @date 2014-3-3 下午4:20:43
     * @param menuId
     * @return
     */
    public int deleteMenu(int menuId);
}
