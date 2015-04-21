package com.lvmama.distribution.model.ckdevice;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author gaoxin
 *
 */
@XmlRootElement
public class OrderInfo {
	
	/** 产品id*/
	private String productId;
	/** 产品类别列表*/
	private ProductBranch productBranch = new ProductBranch();
	/** 第一游玩人*/
	private FirstCustomer firstVisitCustomer = new FirstCustomer();
	/** 其他游玩人列表*/
	private OtherCustomer otherVisitCustomer = new OtherCustomer();
	/** 备注信息*/
	private String orderMemo = "";
	
	@XmlElement
	public ProductBranch getProductBranch() {
		return productBranch;
	}
	public void setProductBranch(ProductBranch productBranch) {
		this.productBranch = productBranch;
	}
	@XmlElement
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}


	@XmlElement
	public FirstCustomer getFirstVisitCustomer() {
		return firstVisitCustomer;
	}

	public void setFirstVisitCustomer(FirstCustomer firstVisitCustomer) {
		this.firstVisitCustomer = firstVisitCustomer;
	}
	
	@XmlElement
	public OtherCustomer getOtherVisitCustomer() {
		return otherVisitCustomer;
	}

	public void setOtherVisitCustomer(OtherCustomer otherVisitCustomer) {
		this.otherVisitCustomer = otherVisitCustomer;
	}

	public String getOrderMemo() {
		return orderMemo;
	}

	public void setOrderMemo(String orderMemo) {
		this.orderMemo = orderMemo;
	}

}
