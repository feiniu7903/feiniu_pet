package com.lvmama.sso.vo;

import hao3604j.Hao360;

import com.lvmama.sso.utils.UnionLoginUtil;

/**
 * 好360
 *
 */
public class Hao360V extends Hao360 {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7117451497016487925L;
	/**
	 * KEY_NAME
	 */
	private static final String KEY_NAME = "hao360.consumer.key";
	/**
	 * SECRET_NAME
	 */
	private static final String SECRET_NAME = "hao360.consumer.secret";

	/**
	 * 无参构造函数
	 */
	public Hao360V() {
		super();
		UnionLoginUtil util = UnionLoginUtil.getInstance();
		this.setOAuthConsumer(util.getValue(KEY_NAME), util.getValue(SECRET_NAME));
	}
}
