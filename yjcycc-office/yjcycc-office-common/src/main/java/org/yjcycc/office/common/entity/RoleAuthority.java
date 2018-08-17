package org.yjcycc.office.common.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("roleAuthority")
public class RoleAuthority implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5410925473989008960L;

	private Long id;
	
	private Long roleId; // 权限id
	
	private String authority; // 权限列表

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}
