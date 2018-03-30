package org.yjcycc.office.common.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("role")
public class Role implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6032099912592822427L;

	/**
	 * roleId
	 */
	private Integer id;
	
	/**
	 * 角色名称
	 */
	private String roleName;
	
	/**
	 * 角色描述
	 */
	private String roleDesc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	
}
