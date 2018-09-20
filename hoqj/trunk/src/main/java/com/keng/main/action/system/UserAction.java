package com.keng.main.action.system;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.keng.base.common.BaseAction;
import com.keng.base.common.Constants;
import com.keng.base.common.Pager;
import com.keng.base.utils.MD5Encrypt;
import com.keng.main.entity.SysUserRoleKey;
import com.keng.main.entity.SysUsers;
import com.keng.main.service.RoleService;
import com.keng.main.service.UserService;

/**
 * ParamAction Desc：用户管理
 * 
 * @date 2016年4月11日 上午10:44:37
 */
@Controller
public class UserAction extends BaseAction {
  @Autowired
  private UserService userService;
  @Autowired
  private RoleService roleService;

  /**
   * 初始化页面
   * 
   * @param model
   * @return
   */
  @RequestMapping(value = "/menus/user_mgr.html", method = RequestMethod.GET)
  public String index(ModelMap model) {
    model.put("allRole", roleService.getAllRoles());
    return AD_HTML + "system/user_mgr";
  }

  /**
   * 用户列表
   * 
   * @param request
   * @param model
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/user/list.html")
  public Object list(HttpServletRequest request, ModelMap model) {
    SysUsers user = getUser();
    Pager pager = createPager(request);
    pager.addParam("childRole", roleService.getRoleChild(user.getRoleId()));
    pager.addParam("username", getRequestParams(String.class, request, "username"));
    pager.addParam("status", getRequestParams(String.class, request, "status"));
    pager.addParam("roleId", getRequestParams(Integer.class, request, "roleId"));
    pager.addParam("code", getRequestParams(String.class, request, "code"));
    pager.addParam("role", user.getRoleId());
    userService.getAllUser(pager);
    return getGridData(pager);
  }

  /**
   * 用户信息
   * 
   * @param model
   * @return
   */
  @RequestMapping(value = "/user/user_message.html", method = RequestMethod.GET)
  public String _index(ModelMap model) {
    model.put("user", userService.getByUserId(Integer.valueOf(getUser().getId())));
    return AD_HTML + "system/user_message";
  }

  /**
   * 创建用户
   * 
   * @param model
   * @return
   */
  @RequestMapping(value = "/menus/create_new_user.html", method = RequestMethod.GET)
  public String index_user(ModelMap model) {
    model.put("superPost", Constants.POST_SUPER_ADMIN);// 超级管理员
    return AD_HTML + "system/create_new_user";
  }

  /**
   * 修改密码
   * 
   * @param oldPassword
   * @param newPassword
   * @param entPassword
   * @param request
   * @param model
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/updpass.html", method = RequestMethod.POST)
  public Object alterPassword(String oldPassword, String newPassword, String entPassword, HttpServletRequest request,
      ModelMap model) {
    Map<String, Object> result = new HashMap<String, Object>();
    if (StringUtils.isBlank(oldPassword)) {
      result.put(ERROR, "旧密码不能为空！");
      return result;
    }
    if (StringUtils.isBlank(newPassword)) {
      result.put(ERROR, "新密码不能为空！");
      return result;
    }
    if (StringUtils.isBlank(entPassword)) {
      result.put(ERROR, "确认密码不能为空！");
      return result;
    }
    if (!newPassword.equals(entPassword)) {
      result.put(ERROR, "两次密码不相同！");
      return result;
    }
    SysUsers user = getUser();
    try {
      String oldPass = MD5Encrypt.MD5(oldPassword);
      if (!user.getPassword().equals(oldPass)) {
        result.put(ERROR, "旧密码错误！");
        return result;
      } else {
        user.setPassword(MD5Encrypt.MD5(newPassword));
        if (userService.updateUser(user)) {
          result.put(RESULT, true);
          result.put(MESSAGE, "密码修改成功！");
        } else {
          result.put(RESULT, false);
          result.put(ERROR, "密码修改失败！");
          return result;
        }
      }
    } catch (Exception e) {
      result.put(RESULT, false);
      result.put(ERROR, "系统异常，修改密码失败！");
      getLog(this).error(e.getMessage(), e);
    }
    return result;
  }

  /**
   * 新增用户
   * 
   * @param user
   * @param roleId
   * @param request
   * @param model
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/user/add.html", method = RequestMethod.POST)
  public Object add(SysUsers user, String roleId, HttpServletRequest request, ModelMap model) {
    Map<String, Object> result = getResultMap();
    try {
      if (roleId == null || StringUtils.isBlank(user.getName())) {
        result.put(RESULT, false);
        result.put(MESSAGE, "提交信息不完整！");
      } else if (userService.getByCode(user.getName()) != null) {
        result.put(RESULT, false);
        result.put(MESSAGE, "用户已存在！");
      } else if (userService.addUser(user, roleId) > 0) {
        result.put(RESULT, true);
        result.put(MESSAGE, "新增成功！");
      } else {
        result.put(RESULT, false);
        result.put(MESSAGE, "新增失败！");
      }
    } catch (Exception e) {
      result.put(RESULT, false);
      result.put(MESSAGE, "系统异常，操作失败！");
      getLog(this).error(e.getMessage(), e);
    }
    return result;
  }

  /**
   * 修改用户
   * 
   * @param user
   * @param roleId
   * @param request
   * @param model
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/user/upd.html", method = RequestMethod.POST)
  public Object upd(SysUsers user, String roleId, HttpServletRequest request, ModelMap model) {
    Map<String, Object> result = getResultMap();
    try {
      if (user.getId() == null || roleId == null) {
        result.put(RESULT, false);
        result.put(MESSAGE, "提交数据不完整！");
      } else if (userService.updateUser(user, roleId)) {
        result.put(RESULT, true);
        result.put(MESSAGE, "修改成功！");
      } else {
        result.put(RESULT, false);
        result.put(MESSAGE, "修改失败！");
      }
    } catch (Exception e) {
      result.put(RESULT, false);
      result.put(MESSAGE, "系统异常，操作失败！");
      getLog(this).error(e.getMessage(), e);
    }
    return result;
  }

  /**
   * 修改用户信息
   * 
   * @param user
   * @param request
   * @param model
   * @return
   */
  @SuppressWarnings("unused")
@ResponseBody
  @RequestMapping(value = "/user/upd_msg.html", method = RequestMethod.POST)
  public Object updMsg(SysUsers user, HttpServletRequest request, ModelMap model) {
    Map<String, Object> result = getResultMap();
    try {
      if (user.getId() == null) {
        result.put(RESULT, false);
        result.put(MESSAGE, "提交数据不完整！");
      } else if (userService.updateUser(user)) {
        SysUsers _user = getUser();
        result.put(RESULT, true);
        result.put(MESSAGE, "修改成功！");
      } else {
        result.put(RESULT, false);
        result.put(MESSAGE, "修改失败！");
      }
    } catch (Exception e) {
      result.put(RESULT, false);
      result.put(MESSAGE, "系统异常，操作失败！");
      getLog(this).error(e.getMessage(), e);
    }
    return result;
  }

  /**
   * 删除用户
   * 
   * @param userId
   * @param request
   * @param model
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/user/del.html", method = RequestMethod.POST)
  public Object del(Integer userId, HttpServletRequest request, ModelMap model) {
    Map<String, Object> result = getResultMap();
    try {
      if (userId != null && userService.deleteUser(userId) > 0) {
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
   * 修改用户角色
   * 
   * @param userRole
   * @param checked
   * @param request
   * @param model
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/user/role_checked.html", method = RequestMethod.POST)
  public Object userRoles(SysUserRoleKey userRole, Boolean checked, HttpServletRequest request, ModelMap model) {
    Map<String, Object> result = getResultMap();
    try {
      if (checked != null && userRole.getRoleId() != null && userRole.getUserId() != null) {
        result.put(RESULT, true);
        userService.changeUserRole(userRole, checked);
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

  /**
   * 查询角色
   * 
   * @param userId
   * @param request
   * @param model
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/user/user_roles.html", method = RequestMethod.POST)
  public Object searchUserRoles(Integer userId, HttpServletRequest request, ModelMap model) {
    Map<String, Object> result = getResultMap();
    try {
      if (userId != null) {
        result.put(RESULT, true);
        result.put(DATA, userService.getAllRole(userId));
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
   * 批量删除
   * 
   * @param userIds
   * @param request
   * @param model
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/user/batch_del.html", method = RequestMethod.POST)
  public Object batchDel(String userIds, HttpServletRequest request, ModelMap model) {
    Map<String, Object> result = getResultMap();
    try {
      if (!StringUtils.isBlank(userIds) && userService.batchDeleteUser(userIds) > 0) {
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
   * 密码重置
   * 
   * @param userId
   * @param request
   * @param model
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/user/resetPassword.html", method = RequestMethod.POST)
  public Object resetPassword(String id, HttpServletRequest request, ModelMap model) {
    Map<String, Object> result = getResultMap();
    try {
      if (!StringUtils.isEmpty(id) && userService.resetPassword(id)) {
        result.put(RESULT, true);
        result.put(MESSAGE, "密码重置成功！");
      } else {
        result.put(RESULT, false);
        result.put(MESSAGE, "密码重置失败！");
      }
    } catch (Exception e) {
      result.put(RESULT, false);
      result.put(MESSAGE, "系统异常，操作失败！");
      getLog(this).error(e.getMessage(), e);
    }
    return result;
  }
}
