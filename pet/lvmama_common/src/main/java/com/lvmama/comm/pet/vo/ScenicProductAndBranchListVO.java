package com.lvmama.comm.pet.vo;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;

/**
 * 景点dest页面，根据产品展现产品的类别的类。只用在景点dest页面
 * @author Administrator
 *
 */
public class ScenicProductAndBranchListVO implements java.io.Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 3712989532571776128L;
	
	/**
	 * 产品名字
	 */
	private String productName;
	
	/**
	 * 产品
	 */
	private ProductSearchInfo productSearchInfo;
	/**
	 * 类别产品列表
	 */
	private List<ProdBranchSearchInfo> branchSearchInfo;
	
	public ScenicProductAndBranchListVO() {
		branchSearchInfo = new ArrayList<ProdBranchSearchInfo>();
	}
	
	public ScenicProductAndBranchListVO(String productName, List<ProdBranchSearchInfo> branchSearchInfo) {
		this.productName = productName;
		this.branchSearchInfo = branchSearchInfo;
	}
	
	public void addBranchSearchInfo(ProdBranchSearchInfo info) {
		branchSearchInfo.add(info);
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<ProdBranchSearchInfo> getBranchSearchInfo() {
		return branchSearchInfo;
	}

	public void setBranchSearchInfo(List<ProdBranchSearchInfo> branchSearchInfo) {
		this.branchSearchInfo = branchSearchInfo;
	}

	public ProductSearchInfo getProductSearchInfo() {
		return productSearchInfo;
	}

	public void setProductSearchInfo(ProductSearchInfo productSearchInfo) {
		this.productSearchInfo = productSearchInfo;
	}	
}
