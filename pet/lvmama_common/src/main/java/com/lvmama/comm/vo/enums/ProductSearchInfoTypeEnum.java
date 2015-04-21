package com.lvmama.comm.vo.enums;

public enum ProductSearchInfoTypeEnum {
	TICKET("TICKET", "门票"), HOTEL("HOTEL", "酒店"), ROUTE("ROUTE", "线路"),OTHER("OTHER","其他");
	private String code;
	private String name;

	ProductSearchInfoTypeEnum(String code, String name) {
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
		for (ProductSearchInfoTypeEnum item : ProductSearchInfoTypeEnum.values()) {
			if (item.getCode().equals(code)) {
				return item.getName();
			}
		}
		return code;
	}
}
