package com.keng.main.action.system;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.keng.base.common.BaseAction;
import com.keng.base.utils.JacksonBuilder;
import com.keng.main.entity.SysMenus;
import com.keng.main.service.MenuService;
/**
 * ParamAction Desc：菜单管理
 * @date 2016年4月11日 上午10:44:37
 */
@Controller
public class MenuAction extends BaseAction {
    private @Autowired
    MenuService menuService;

    /**
     * 初始化页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/menus/menu_mgr.html", method = RequestMethod.GET)
    public String index(HttpServletRequest request, ModelMap model) {
        model.put("menus", menuService.getAllMenus());
        try {
            model.put("menusJson", JacksonBuilder.mapper().writeValueAsString(model.get("menus")));
        } catch (JsonProcessingException e) {
            getLog(this).error(e.getMessage(), e);
        }
        return AD_HTML + "system/menu_mgr";
    }

    /**
     * 新增菜单
     * @param menu
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/menu/add.html", method = RequestMethod.POST)
    public Object addMenu(SysMenus menu, HttpServletRequest request, ModelMap model) {
        Map<String, Object> result = getResultMap();
        try {
            if (menuService.insertMenu(menu) > 0) {
                result.put(RESULT, true);
                result.put(MESSAGE, "新增成功！");
                result.put("menuId", menu.getMenuId());
            } else {
                result.put(RESULT, false);
                result.put(MESSAGE, "新增失败！");
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常处理失败！");
            getLog(this).error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 修改菜单
     * @param menu
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/menu/upd.html", method = RequestMethod.POST)
    public Object updMenu(SysMenus menu, HttpServletRequest request, ModelMap model) {
        Map<String, Object> result = getResultMap();
        try {
            if (menu.getMenuId() != null && menuService.updateMenu(menu) > 0) {
                result.put(RESULT, true);
                result.put(MESSAGE, "更新成功！");
            } else {
                result.put(RESULT, false);
                result.put(MESSAGE, "更新失败！");
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常处理失败！");
            getLog(this).error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 删除菜单
     * @param menuId
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/menu/del.html", method = RequestMethod.POST)
    public Object delMenu(Integer menuId, HttpServletRequest request, ModelMap model) {
        Map<String, Object> result = getResultMap();
        try {
            if (menuId != null && menuService.deleteMenu(menuId) > 0) {
                result.put(RESULT, true);
                result.put(MESSAGE, "删除成功！");
            } else {
                result.put(RESULT, false);
                result.put(MESSAGE, "删除失败！");
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常处理失败！");
            getLog(this).error(e.getMessage(), e);
        }
        return result;
    }
}
