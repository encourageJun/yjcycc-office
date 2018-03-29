package org.yjcycc.office.common.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * 便签
 * @author Yangjun
 *
 */

@Alias("myNote")
public class MyNote implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4471948588557563310L;

	/**
	 * 便签id
	 */
	private Integer noteId;
	
	/**
	 * 便签标题
	 */
	private String noteTitle;
	
	/**
	 * 便签内容
	 */
	private String noteContent;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 创建人
	 */
	private String createUser;

	public Integer getNoteId() {
		return noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}

	public String getNoteTitle() {
		return noteTitle;
	}

	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	public String getNoteContent() {
		return noteContent;
	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
}
