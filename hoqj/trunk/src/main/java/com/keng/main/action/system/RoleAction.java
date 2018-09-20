package com.keng.main.action.system;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.keng.base.common.BaseAction;
import com.keng.base.utils.JacksonBuilder;
import com.keng.main.entity.SysRoleMenuKey;
import com.keng.main.entity.SysRoles;
import com.keng.main.service.MenuService;
import com.keng.main.service.RoleService;
/**
 * ParamAction Desc：角色管理
 * @date 2016年4月11日 上午10:44:37
 */
@Controller
public class RoleAction extends BaseAction {
    private @Autowired
    MenuService menuService;
    private @Autowired
    RoleService roleService;

    /**
     * 初始化页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/menus/role_mgr.html", method = RequestMethod.GET)
    public String index(HttpServletRequest request, ModelMap model) {
        try {
            model.put("menusJson", JacksonBuilder.mapper().writeValueAsString(menuService.getAllMenus()));
            model.put("rolesJson", JacksonBuilder.mapper().writeValueAsString(roleService.getAllRoles()));
        } catch (JsonProcessingException e) {
            getLog(this).error(e.getMessage(), e);
        }
        return AD_HTML + "system/role_mgr";
    }

    /**
     * 获取所有角色
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/get_roles.html", method = RequestMethod.POST)
    public Object list(HttpServletRequest request, ModelMap model) {
        Map<String, Object> result = getResultMap();
        try {
            result.put(RESULT, true);
            result.put(DATA, roleService.getRoleByParent(getUser().getRoleId()));
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常，获取数据失败！");
            getLog(this).error("系统异常，获取数据失败！", e);
        }
        return result;
    }

    /**
     * 新增角色
     * @param role
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/add.html", method = RequestMethod.POST)
    public Object add(SysRoles role, HttpServletRequest request, ModelMap model) {
        Map<String, Object> result = getResultMap();
        try {
            if (role.getParentRole() == null || StringUtils.isBlank(role.getRoleName())) {
                result.put(RESULT, false);
                result.put(MESSAGE, "提交数据无效！");
            } else {
                if (roleService.addRole(role) > 0) {
                    result.put(RESULT, true);
                    result.put("roleId", role.getRoleId());
                    result.put(MESSAGE, "新增成功！");
                } else {
                    result.put(RESULT, false);
                    result.put(MESSAGE, "新增失败！");
                }
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常，操作失败！");
            getLog(this).error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 修改角色
     * @param role
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/upd.html", method = RequestMethod.POST)
    public Object upd(SysRoles role, HttpServletRequest request, ModelMap model) {
        Map<String, Object> result = getResultMap();
        try {
            if (role.getRoleId() == null || StringUtils.isBlank(role.getRoleName())) {
                result.put(RESULT, false);
                result.put(MESSAGE, "修改失败！");
            } else {
                if (roleService.updRole(role) > 0) {
                    result.put(RESULT, true);
                    result.put(MESSAGE, "修改成功！");
                } else {
                    result.put(RESULT, false);
                    result.put(MESSAGE, "修改失败！");
                }
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常，操作失败！");
            getLog(this).error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 删除角色
     * @param roleId
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/del.html", method = RequestMethod.POST)
    public Object del(Integer roleId, HttpServletRequest request, ModelMap model) {
        Map<String, Object> result = getResultMap();
        try {
            if (roleId != null && roleService.delRole(roleId) > 0) {
                result.put(RESULT, true);
                result.put(MESSAGE, "删除成功！");
            } else {
                result.put(RESULT, false);
                result.put(MESSAGE, "删除失败！");
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常，操作失败！");
            getLog(this).error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 角色菜单
     * @param roleId
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/role_menus.html", method = RequestMethod.POST)
    public Object roleMenus(Integer roleId, HttpServletRequest request, ModelMap model) {
        Map<String, Object> result = getResultMap();
        try {
            if (roleId != null) {
                result.put(RESULT, true);
                result.put(DATA, roleService.getRoleMenus(roleId));
            } else {
                result.put(RESULT, false);
                result.put(MESSAGE, "参数错误，获取数据失败！");
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常，获取数据失败！");
            getLog(this).error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 验证角色菜单
     * @param roleMenu
     * @param checked
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/menu_checked.html", method = RequestMethod.POST)
    public Object roleMenus(SysRoleMenuKey roleMenu, Boolean checked, HttpServletRequest request, ModelMap model) {
        Map<String, Object> result = getResultMap();
        try {
            if (checked != null && roleMenu.getRoleId() != null && roleMenu.getMenuId() != null) {
                result.put(RESULT, true);
                roleService.changeRoleMenu(roleMenu, checked);
            } else {
                result.put(RESULT, false);
                result.put(MESSAGE, "参数错误，获取数据失败！");
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常，获取数据失败！");
        }
        return result;
    }
}
