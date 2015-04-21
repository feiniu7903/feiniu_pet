package com.lvmama.distribution.model.lv;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;

/**
 * 分销--产品类别列表对象
 * @author lipengcheng
 *
 */
public class ProductBranch {
	
	/** 产品单个类别对象*/
	private List<Branch> branchList;//request
	private List<ProdProductBranch> prodProductBranchList;
	
	private boolean isQuanr = false;
	private boolean isCashZero = false;
	public ProductBranch(){
	}
	
	/**
	 * 获得签证信息
	 * @return
	 */
	public String getLocalSigned(){
		StringBuilder localSigned = new StringBuilder();
		for(Branch branch : branchList){
			localSigned.append(branch.getLocalSigned());
		}
		return localSigned.toString();
	}
	
	/**
	 * 关于分销产品列表的构造函数
	 * @param prodProductBranchList
	 */
	public ProductBranch (DistributionProduct distributionProduct){
		this.prodProductBranchList = distributionProduct.getProdProduct().getProdBranchList();
		if(prodProductBranchList==null){
			this.prodProductBranchList = new ArrayList<ProdProductBranch>();
		}
	}
	
	/**
	 * 构造查询产品列表报文--类别列表节点
	 * @return
	 */
	public String buildForProductInfoList(){
		StringBuilder str = new StringBuilder();
		for(ProdProductBranch prodProductBranch : prodProductBranchList){
			Branch brach = new Branch(prodProductBranch);
			str.append(brach.buildForProductInfoList(isQuanr,isCashZero));
		}
		return str.toString();
	}
	
	/**
	 * 构造单个产品时间价格报文--类别列表节点
	 * @return
	 */
	public String buildForGetProductPrice() {
		StringBuilder str = new StringBuilder();
		for (ProdProductBranch prodProductBranch : prodProductBranchList) {
			Branch brach = new Branch(prodProductBranch);
			str.append(brach.buildForGetProductPrice());
		}
		return str.toString();
	}	
	
	/**
	 * 构造推送单个产品时间价格报文--类别列表节点
	 * @return
	 */
	public String buildForPushProductPrice() {
		StringBuilder str = new StringBuilder();
		for (ProdProductBranch prodProductBranch : prodProductBranchList) {
			Branch brach = new Branch(prodProductBranch);
			str.append(brach.buildForPushProductPrice(isQuanr,isCashZero));
		}
		return str.toString();
	}
	
	/**
	 * 构造查询产品产品信息--类别列表节点
	 * @return
	 */
	public String buildForGetProductInfo() {
		StringBuilder str = new StringBuilder();
		for (ProdProductBranch prodProductBranch : prodProductBranchList) {
			Branch brach = new Branch(prodProductBranch);
			str.append(brach.buildForGetProductInfo(isQuanr,isCashZero));
		}
		return str.toString();
	}
	
	
	/**
	 * 构造时间价格表报文--类别列表节点
	 * @return
	 */
	public String buildForProductPriceList() {
		StringBuilder str = new StringBuilder();
		for (ProdProductBranch prodProductBranch : prodProductBranchList) {
			Branch brach = new Branch(prodProductBranch);
			str.append(brach.buildForProductPriceList());
		}
		return str.toString();
	}
	
	
	/**
	 * 构造产品上下线信息报文
	 * @return
	 */
	public String buildForGetProductOnLine(){
		StringBuilder str = new StringBuilder();
		for(ProdProductBranch prodProductBranch : prodProductBranchList){
			Branch brach = new Branch(prodProductBranch);
			str.append(brach.buildForGetProductOnLine());
		}
		return str.toString();
	}
	
	public List<Branch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<Branch> branchList) {
		this.branchList = branchList;
	}

	public void setQuanr(boolean isQuanr) {
		this.isQuanr = isQuanr;
	}

	public void setCashZero(boolean isCashZero) {
		this.isCashZero = isCashZero;
	}

	
}
