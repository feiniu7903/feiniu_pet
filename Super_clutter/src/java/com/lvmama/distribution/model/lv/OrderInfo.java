package com.lvmama.distribution.model.lv;


/**
 * 分销创建订单请求报文体
 * @author lipengcheng
 *
 */
public class OrderInfo {
	
	/** 分销商订单号*/
	private String partnerOrderId="";
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
	/**
	 * 获得签证信息
	 * @return
	 */
	public String getLocalSigned(){
		return this.getPartnerOrderId() + this.getProductId() + this.getProductBranch().getLocalSigned() +this.getFirstVisitCustomer().getLocalSigned() + this.getOtherVisitCustomer().getLocalSigned();
	}
	
	//setter and getter
	
	public ProductBranch getProductBranch() {
		return productBranch;
	}
	public void setProductBranch(ProductBranch productBranch) {
		this.productBranch = productBranch;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public FirstCustomer getFirstVisitCustomer() {
		return firstVisitCustomer;
	}

	public void setFirstVisitCustomer(FirstCustomer firstVisitCustomer) {
		this.firstVisitCustomer = firstVisitCustomer;
	}

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
