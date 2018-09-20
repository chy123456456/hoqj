package com.keng.base.shiro;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.keng.main.entity.SysMenus;
import com.keng.main.entity.SysUsers;
import com.keng.main.service.UserService;

/**
 * 登录验证（shiro框架）
 * 
 * 
 */
@Component
public class LoginAuthRealm extends AuthorizingRealm {
  @Autowired
  private UserService      userSv;


  public LoginAuthRealm() {
    super();
    // 设置认证token的实现类
    setAuthenticationTokenClass(UsernamePasswordToken.class);
  }

  // 授权信息
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    SimpleAuthorizationInfo result = new SimpleAuthorizationInfo();
    // 获取当前登录的用户
    SysUsers user = (SysUsers) super.getAvailablePrincipal(principals);
    List<SysMenus> menus = user.getMenus();
    for (SysMenus SysMenus : menus) {
      if (SysMenus.getParentId() != 0 && !StringUtils.isBlank(SysMenus.getUrl()) && SysMenus.getStatus() == 1) {
        result.addStringPermission("/" + SysMenus.getUrl());
      }
    }
    return result;
  }

  // 认证信息
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
    try {
    	UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();
        SysUsers user = userSv.getByCode(token.getUsername());
        if (!StringUtils.isBlank(username)) {
            if (user != null ) {
                return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            }
        }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  } 
}
