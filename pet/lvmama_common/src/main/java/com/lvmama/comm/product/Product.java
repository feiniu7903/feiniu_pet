package com.lvmama.comm.product;

public interface Product {
	
	/**
	 * 是否SUPER产品
	 * @return
	 */
	boolean isSuperProduct();
	
	/**
	 * 是否BEE PRODUCT
	 * @return
	 */
	boolean isBeeProduct();
	
	/**
	 * 是否代售系统产品
	 * @return
	 */
	boolean isAntProduct();
	
	/**
	 * 是否海外酒店产品
	 * @return
	 */
	boolean isAbroadHotelProduct();
	
	/**
	 * 获取产品业务类型
	 * @return
	 */
	String getBizType();
}
