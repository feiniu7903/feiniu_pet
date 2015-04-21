package com.lvmama.comm.vst.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 产品信息
 * @author ranlongfei 2013-12-20
 * @version
 */
public class VstProdProductVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3481467790163810738L;
	/**
	 * 产品ID
	 */
	private Long productId;
	/**
	 * 产品名
	 */
	private String productName;
	
	
	private List<VstProdGoodsVo> goodsList;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<VstProdGoodsVo> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<VstProdGoodsVo> goodsList) {
		this.goodsList = goodsList;
	}
	
	

}
