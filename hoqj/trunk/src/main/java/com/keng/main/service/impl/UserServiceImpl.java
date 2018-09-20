package com.keng.main.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.keng.base.common.Constants;
import com.keng.base.common.Pager;
import com.keng.base.utils.MD5Encrypt;
import com.keng.main.entity.SysUserRoleKey;
import com.keng.main.entity.SysUsers;
import com.keng.main.mapper.SysUserRoleMapper;
import com.keng.main.mapper.SysUsersMapper;
import com.keng.main.service.UserService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class UserServiceImpl implements UserService {
  @Autowired
  private SysUsersMapper    userDao;
  @Autowired
  private SysUserRoleMapper userRoleMapper;
  @Autowired
  private SysUsersMapper    sysUsersMapper;

  public int addUser(SysUsers user, String roleId) {
    // 设置默认密码
    user.setPassword(MD5Encrypt.MD5(Constants.DEFAULT_PASSWORD));
//    user.setCreateDate(new Date());
    userDao.insert(user);
    String[] strs = roleId.split(",");
    for (String rid : strs) {
      // 分配角色
      SysUserRoleKey userRole = new SysUserRoleKey();
      userRole.setRoleId(Integer.parseInt(rid));
//      userRole.setUserId(user.getUserId());
      userRoleMapper.insert(userRole);
    }
    return 1;
  }

  public int addNewUser(SysUsers user, Integer roleId, String pwkey) {
    // 设置默认密码
    user.setPassword(MD5Encrypt.MD5(pwkey));
//    user.setCreateDate(new Date());
    userDao.insert(user);
    // 分配角色
    SysUserRoleKey userRole = new SysUserRoleKey();
    userRole.setRoleId(roleId);
//    userRole.setUserId(user.getUserId());
    userRoleMapper.insert(userRole);
    return 1;
  }

  public SysUsers getByCode(String username) {
//    return userDao.selectByUsername(username,
//        "中文".equals(BaseAction.getSession().getAttribute("language").toString()) ? "chinese" : "english");
	  return userDao.selectByUsername(username);
  }

  public SysUsers getByUserId(Integer userId) {
    return userDao.selectByPrimaryKey(userId);
  }

  public boolean updateUser(SysUsers user) {
    if (user.getPassword() == null) {
//      user.setUpdateDate(new Date());
    }
    int r = userDao.updateByPrimaryKeySelective(user);
    return r > 0;
  }

  public boolean updateUser(SysUsers user, String roleId) {
    userRoleMapper.deleteByUserId(user.getId());
    // 分配角色
    String[] strs = roleId.split(",");
    for (String rid : strs) {
      // 分配角色
      SysUserRoleKey userRole = new SysUserRoleKey();
      userRole.setRoleId(Integer.parseInt(rid));
      userRole.setUserId(user.getId());
      userRoleMapper.insert(userRole);
    }
    int r = userDao.updateByPrimaryKeySelective(user);
    return r > 0;
  }

  public void getAllUser(Pager pager) {
    pager.setResult(userDao.selectPager(pager.getParamsMap()));
    pager.setTotalRecord(userDao.selectPagerCount(pager.getParamsMap()));
  }

  public int deleteUser(Integer userId) {
    return userDao.deleteByPrimaryKey(userId);
  }

  public int batchDeleteUser(String userIds) {
    List<String> ids = Arrays.asList(userIds.split("\\|"));
    return userDao.batchDeleteUser(ids);
  }

  public int changeUserRole(SysUserRoleKey userRole, Boolean checked) {
    if (checked) {
      return userRoleMapper.insert(userRole);
    } else {
      return userRoleMapper.deleteByPrimaryKey(userRole);
    }
  }

  public List<SysUserRoleKey> getAllRole(Integer userId) {
    return userRoleMapper.selectByUserId(userId);
  }

  @Override
  public boolean resetPassword(String userId) {
    SysUsers user = new SysUsers();
    user.setId(userId);
    user.setPassword(MD5Encrypt.MD5(Constants.DEFAULT_PASSWORD));
    userDao.updateByPrimaryKeySelective(user);
    return true;
  }

  @Override
  public List<SysUsers> selectRoleChild(Integer roleId) {
    return null;
  }

  @Override
  public List<SysUsers> selectAllVer() {
    return sysUsersMapper.selectAllVer();
  }

  @Override
  public List<SysUsers> allNameList(Map<String, Object> map) {
    return sysUsersMapper.allNameList(map);
  }
}
