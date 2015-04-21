package com.lvmama.comm.vst.vo;

import java.io.Serializable;
/**
 * 商品信息（类别）
 * @author ranlongfei 2013-12-20
 * @version
 */
public class VstProdGoodsVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8871785078202966934L;
	/**
	 * 商品ID
	 */
	private Long goodsId;
	/**
	 * 商品名
	 */
	private String goodsName;
	
	/**
	 * 产品经理id
	 */
	private Long managerId;
	
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Long getManagerId() {
		return managerId;
	}
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	
}
