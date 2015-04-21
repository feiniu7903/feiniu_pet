package com.lvmama.comm.vo.enums;

public enum PlacePhotoTypeEnum {
	LARGE("LARGE", "大图"), MIDDLE("MIDDLE", "中图"), SMAIL("SMALL", "小图");
	private String code;
	private String name;

	PlacePhotoTypeEnum(String code, String name) {
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
		for (PlacePhotoTypeEnum item : PlacePhotoTypeEnum.values()) {
			if (item.getCode().equals(code)) {
				return item.getName();
			}
		}
		return code;
	}
}
