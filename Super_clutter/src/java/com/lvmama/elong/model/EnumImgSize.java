package com.lvmama.elong.model;


public enum EnumImgSize {
	LARGE(1), 
	MIDDLE(3), 
	SMALL(2);

	private final int value;

	EnumImgSize(int v) {
		value = v;
	}

	public int value() {
		return value;
	}

	public static EnumImgSize fromValue(int v) {
		for (EnumImgSize c : EnumImgSize.values()) {
			if (c.value == v) {
				return c;
			}
		}
		throw new IllegalArgumentException(String.valueOf(v));
	}
	
	public static void main(String[] args) {
		System.out.println(EnumImgSize.LARGE.value);
		System.out.println(EnumImgSize.LARGE.toString() instanceof String);
		System.out.println(EnumImgSize.valueOf("LARGE").value());
	}
}
