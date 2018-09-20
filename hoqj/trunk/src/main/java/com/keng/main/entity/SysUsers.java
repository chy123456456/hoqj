package com.keng.main.entity;

import java.io.Serializable;
import java.util.List;

public class SysUsers implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String code;
	private String name;
	private String sex;
	private String password;
	private SysDept dept;
	private Integer status;
	private List<SysMenus> menus;
	private List<SysRoles> roles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<SysMenus> getMenus() {
		return menus;
	}

	public void setMenus(List<SysMenus> menus) {
		this.menus = menus;
	}

	public List<SysRoles> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRoles> roles) {
		this.roles = roles;
	}
	
	public Integer getRoleId() {
        if (roles != null && roles.size() > 0) {
            return roles.get(0).getRoleId();
        }
        return null;
    }

	public SysDept getDept() {
		return dept;
	}

	public void setDept(SysDept dept) {
		this.dept = dept;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SysUsers [id=" + id + ", code=" + code + ", name=" + name + ", sex=" + sex + ", password=" + password
				+ ", dept=" + dept + ", status=" + status+"]";
	}

}
