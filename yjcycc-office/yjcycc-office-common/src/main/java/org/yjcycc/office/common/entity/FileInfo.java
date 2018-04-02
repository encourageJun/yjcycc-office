package org.yjcycc.office.common.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("fileInfo")
public class FileInfo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9050712265354588846L;

	/**
	 * fileId
	 */
	private Integer id;
	
	/**
	 * 父节点 Id
	 */
	private Integer parentId;
	
	/**
	 * 文件类型id
	 */
	private Integer fileType;
	
	/**
	 * 文件名称
	 */
	private String fileName;
	
	/**
	 * 文件路径
	 */
	private String filePath;
	
	/**
	 * 是否已删除, 默认0, 0否1是
	 */
	private Integer ifDelete;
	
	/**
	 * 文件备注
	 */
	private String remark;
	
	/**
	 * 创建者
	 */
	private String fileOwner;
	
	/**
	 * 创建时间
	 */
	private Date createDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileOwner() {
		return fileOwner;
	}

	public void setFileOwner(String fileOwner) {
		this.fileOwner = fileOwner;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getIfDelete() {
		return ifDelete;
	}

	public void setIfDelete(Integer ifDelete) {
		this.ifDelete = ifDelete;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}
	
}
