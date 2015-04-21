package com.lvmama.comm.pet.vo.product;

/**
 * 关联产品，所有实现这个类的产品代表着可以被当做关联产品附件销售。
 * 类名使用名词，实在是找不到一个更好的形容词来描述此特性，有待改良
 * @author Administrator
 *
 */
public interface RelatedProduct extends java.io.Serializable {
	/**
	 * 获取产品标识
	 * @return
	 */
	Long getProductId();
	/**
	 * 获取产品名词
	 * @return
	 */
	String getProductName();
	/**
	 * 获取中文的产品类型名词
	 * @return
	 */
	String getZhProductType();
	/**
	 * 获取中文的产品子类型名词
	 * @return
	 */
	String getZhSubProductType();
	/**
	 * 获取市场价(单位：分)
	 * @return
	 */
	Long getMarketPrice();
	/**
	 * 获取市场价(单位：元)
	 * @return
	 */
	float getMarketPriceYuan();
	/**
	 * 获取销售价(单位:分)
	 * @return
	 */
	Long getSellPrice();
	/**
	 * 获取销售价(单位:元)
	 * @return
	 */
	float getSellPriceYuan();
}
