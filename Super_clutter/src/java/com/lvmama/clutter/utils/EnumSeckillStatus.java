package com.lvmama.clutter.utils;


/**
 * @Title: EnumSeckillStatus.java
 * @Package com.lvmama.clutter.utils
 * @Description: TODO
 * @author jiangzhihu
 * @date 2014-4-14 下午4:16:22
 * @version V1.0.0
 */
public enum EnumSeckillStatus {
	SECKILL_BEFORE("即将开抢"),
	SECKILL_BEING("立即抢"),
	SECKILL_AFTER("抢购已结束"),
	SECKILL_FINISHED("已售罄");

	private String cnName;

	EnumSeckillStatus(String name) {
		this.cnName = name;
	}

	public String getCode() {
		return this.name();
	}

	public String getCnName() {
		return this.cnName;
	}

	public static String getCnName(String code) {
		for (EnumSeckillStatus item : EnumSeckillStatus.values()) {
			if (item.getCode().equals(code)) {
				return item.getCnName();
			}
		}
		return code;
	}

	public String toString() {
		return this.name();
	}
}
