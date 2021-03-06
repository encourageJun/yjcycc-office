package org.yjcycc.office.common.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 机构
 * @author Yangjun
 *
 */

@Alias("branch")
public class Branch implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 827412533575936725L;

	/**
	 * 机构id
	 */
	private Integer branchId;
	
	/**
	 * 机构名称
	 */
	private String branchName;
	
	/**
	 * 机构简称
	 */
	private String branchShortName;

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchShortName() {
		return branchShortName;
	}

	public void setBranchShortName(String branchShortName) {
		this.branchShortName = branchShortName;
	}
	
}
