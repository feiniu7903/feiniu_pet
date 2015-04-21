package com.lvmama.tnt.search.vo;

import java.io.Serializable;

import com.lvmama.comm.search.vo.ProductBranchBean;



/**
 * 分销搜索产品类别
 * @author gaoxin
 *
 */
public class ProductBranch extends ProductBranchBean  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 486743013760164553L;
	/**
	 * 是否在B2B分销渠道销售
	 */
	private String valid;
	
	/**
	 * 销售价
	 */
	private Float tntSellPrice;
	/**
	 * 市场价
	 */
	private Float tntMarketPrice;
	
	/**
	 * 分销价
	 */
	private String tntPrice ="登录有优惠";

	
	
	public String getValid() {
		return valid;
	}


	public Float getTntSellPrice() {
		return tntSellPrice;
	}


	public void setTntSellPrice(Float tntSellPrice) {
		this.tntSellPrice = tntSellPrice;
	}


	public Float getTntMarketPrice() {
		return tntMarketPrice;
	}


	public void setTntMarketPrice(Float tntMarketPrice) {
		this.tntMarketPrice = tntMarketPrice;
	}


	public String getTntPrice() {
		return tntPrice;
	}

	public void setTntPrice(String tntPrice) {
		this.tntPrice = tntPrice;
	}


	public void setValid(String valid) {
		this.valid = valid;
	}
	
}
