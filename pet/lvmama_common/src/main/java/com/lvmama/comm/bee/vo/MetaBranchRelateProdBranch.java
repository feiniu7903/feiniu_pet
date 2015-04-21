package com.lvmama.comm.bee.vo;

import java.io.Serializable;

/**
 * 根据采购类别查看关联的销售类别结果集类
 * @author zhangxin
 *
 */
public class MetaBranchRelateProdBranch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**销售产品的产品ID*/
	private Long prodProductId;

	/**销售产品的产品名称*/
	private String prodProductName;
	
	/**关联的销售类别ID*/
	private Long prodBranchId;
	
	/**关联的销售类别名称*/
	private String prodBranchName;
	
	/**关联的销售产品的产品经理*/
	private String prodManagerName;

	/**关联的销售类别的状态 根据类别的ON_LINE判断*/
	private String prodBranchState;
	
	/**关联的销售产品的状态 根据产品的ON_LINE判断*/
	private String prodProductState;

	public Long getProdProductId() {
		return prodProductId;
	}

	public void setProdProductId(Long prodProductId) {
		this.prodProductId = prodProductId;
	}

	public String getProdProductName() {
		return prodProductName;
	}

	public void setProdProductName(String prodProductName) {
		this.prodProductName = prodProductName;
	}

	public Long getProdBranchId() {
		return prodBranchId;
	}

	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

	public String getProdBranchName() {
		return prodBranchName;
	}

	public void setProdBranchName(String prodBranchName) {
		this.prodBranchName = prodBranchName;
	}

	public String getProdManagerName() {
		return prodManagerName;
	}

	public void setProdManagerName(String prodManagerName) {
		this.prodManagerName = prodManagerName;
	}

	public String getProdBranchState() {
		return prodBranchState;
	}

	public void setProdBranchState(String prodBranchState) {
		this.prodBranchState = prodBranchState;
	}

	public String getStatus() {
		return "true".equals(prodBranchState)?"上线":"下线";
	}

	public String getProdProductState() {
		return prodProductState;
	}

	public void setProdProductState(String prodProductState) {
		this.prodProductState = prodProductState;
	}
	
}