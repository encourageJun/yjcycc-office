package org.yjcycc.office.common.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("user")
public class User implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4803664756209898185L;

	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 部门id
	 */
	private Integer departId;
	
	/**
	 * 角色id
	 */
	private Integer roleId;
	
	/**
	 * 性别, 0男 1女
	 */
	private Integer gender;
	
	/**
	 * 用户状态, 0正常 1禁用
	 */
	private Integer userState;
	
	/**
	 * 用户头像
	 */
	private String picUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getDepartId() {
		return departId;
	}

	public void setDepartId(Integer departId) {
		this.departId = departId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
}
