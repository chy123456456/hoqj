package com.keng.main.service;

import java.util.List;
import java.util.Map;

import com.keng.base.common.Pager;
import com.keng.main.entity.SysUserRoleKey;
import com.keng.main.entity.SysUsers;

public interface UserService {
  /**
   * 新增用户，同时分配角色
   * 
   * @param user 用户
   * @param roleId 角色编号
   * @return
   */
  public int addUser(SysUsers user, String roleId);

  /**
   * 根据code查询用户
   * 
   * @param username 用户名称
   * @return
   */
  public SysUsers getByCode(String code);

  /**
   * 根据用户编号查询用户
   * 
   * @param userId 用户编号
   * @return
   */
  public SysUsers getByUserId(Integer userId);

  /**
   * 修改用户信息
   * 
   * @date 2014-3-3 下午4:10:55
   * @param user 用户
   * @return
   */
  public boolean updateUser(SysUsers user);

  /**
   * 修改用户角色和岗位
   * 
   * @date 2014-3-3 下午4:11:02
   * @param user 用户
   * @param roleId 角色编号
   * @return
   */
  public boolean updateUser(SysUsers user, String roleId);

  /**
   * 查询所有用户（分页）
   * 
   * @date 2014-3-3 下午4:14:19
   * @param pager
   */
  public void getAllUser(Pager pager);

  /**
   * 删除指定编号用户
   * 
   * @date 2014-3-3 下午4:14:33
   * @param userId
   * @return
   */
  public int deleteUser(Integer userId);

  /**
   * 分配用户角色
   * 
   * @date 2014-3-3 下午4:14:49
   * @param userRole
   * @param checked
   * @return
   */
  public int changeUserRole(SysUserRoleKey userRole, Boolean checked);

  /**
   * 查询用户所拥有的角色
   * 
   * @date 2014-3-3 下午4:15:19
   * @param userId
   * @return
   */
  public List<SysUserRoleKey> getAllRole(Integer userId);

  /**
   * 批量删除用户
   * 
   * @date 2014-3-3 下午4:17:22
   * @param userIds 用户编号，多个帐号用‘|’分割
   * @return
   */
  public int batchDeleteUser(String userIds);

  /**
   * 重置密码
   * 
   * @date 2014-3-3 下午2:49:53
   * @param userId 用户编号
   * @return
   */
  boolean resetPassword(String userId);

  /**
   * 查询指定角色下的所有用户
   * 
   * @date 2014-3-4 下午4:03:26
   * @param roleId
   * @return
   */
  public List<SysUsers> selectRoleChild(Integer roleId);

  /**
   * 新增新用户
   * 
   * @param user
   * @param roleId
   * @param pwkey
   * @return
   */
  public int addNewUser(SysUsers user, Integer roleId, String pwkey);

  /**
   * 查询所有的审核员
   * 
   * @return
   */
  public List<SysUsers> selectAllVer();

  /**
   * 授权人姓名加载出电话号码
   * 
   * @param map
   * @return
   */
  public List<SysUsers> allNameList(Map<String, Object> map);
}
