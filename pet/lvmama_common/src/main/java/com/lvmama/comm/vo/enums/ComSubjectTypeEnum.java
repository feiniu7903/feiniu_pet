/**
 * 
 */
package com.lvmama.comm.vo.enums;

/**
 * @author zhangzhenhua
 * 
 */
public enum ComSubjectTypeEnum {
	PLACE("PLACE", "景点"), ROUTE("ROUTE", "线路"), HOTEL("HOTEL", "酒店");
	private String code;
	private String name;

	ComSubjectTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
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

	public static String getName(String code) {
		for (ComSubjectTypeEnum item : ComSubjectTypeEnum.values()) {
			if (item.getCode().equals(code)) {
				return item.getName();
			}
		}
		return code;
	}
}
