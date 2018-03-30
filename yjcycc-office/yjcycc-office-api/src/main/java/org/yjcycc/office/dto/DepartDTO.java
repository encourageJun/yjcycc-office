package org.yjcycc.office.dto;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.yjcycc.office.common.entity.Depart;

@Alias("departDTO")
public class DepartDTO extends Depart implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8096443132354416174L;

	/**
	 * 机构id
	 */
	private Integer branchId;
	
	/**
	 * 机构名称
	 */
	private String branchName;

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
	
}
