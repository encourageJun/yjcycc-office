package org.yjcycc.office.common.constant;

public enum FileTypeEnum {

	Directory(0,"文件夹"),
	Word(1,"Word文档"),
	Excel(2,"Excel文档")
	;
	
	private int index;
	private String desc;
	
	private FileTypeEnum(int index, String desc) {
		this.index = index;
		this.desc = desc;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
