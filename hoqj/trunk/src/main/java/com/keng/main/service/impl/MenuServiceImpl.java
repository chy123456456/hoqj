package com.keng.main.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.keng.main.entity.SysMenus;
import com.keng.main.mapper.SysMenusMapper;
import com.keng.main.service.MenuService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class MenuServiceImpl implements MenuService {
    private @Autowired
    SysMenusMapper menuMapper;

    public List<SysMenus> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    public int updateMenu(SysMenus menu) {
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    public int insertMenu(SysMenus menu) {
        return menuMapper.insertSelective(menu);
    }

    public int deleteMenu(int menuId) {
        return menuMapper.deleteByMenuId(menuId, menuId);
    }
}
