package org.yjcycc.office.common.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("depart")
public class Depart implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3157054601651318217L;

	/**
	 * 部门id
	 */
	private Integer departId;
	
	/**
	 * 部门名称
	 */
	private String departName;
	
	/**
	 * 部门负责人
	 */
	private String principalUser;
	
	/**
	 * 联系电话
	 */
	private String connectTelNo;
	
	/**
	 * 手机号码
	 */
	private String connectMobileTelNo;
	
	/**
	 * 传真
	 */
	private String faxes;
	
	/**
	 * 所属机构id
	 */
	private Integer branchId;

	public Integer getDepartId() {
		return departId;
	}

	public void setDepartId(Integer departId) {
		this.departId = departId;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getConnectTelNo() {
		return connectTelNo;
	}

	public void setConnectTelNo(String connectTelNo) {
		this.connectTelNo = connectTelNo;
	}

	public String getConnectMobileTelNo() {
		return connectMobileTelNo;
	}

	public void setConnectMobileTelNo(String connectMobileTelNo) {
		this.connectMobileTelNo = connectMobileTelNo;
	}

	public String getFaxes() {
		return faxes;
	}

	public void setFaxes(String faxes) {
		this.faxes = faxes;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public String getPrincipalUser() {
		return principalUser;
	}

	public void setPrincipalUser(String principalUser) {
		this.principalUser = principalUser;
	}
	
}
