package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdPerson;

public class PassPortInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5403816957039635638L;
	private Long orderId;
	private String name;
	private String mobile;
	private Long adult;
	private Long child;
	private Long totalMan;
	private String payChannel;
	private float price;
	private Long targetId;
	private String serialNo;
	private Long codeId;
	private String priceYuan;
	private List<OrdPerson> personList = new ArrayList<OrdPerson>();
	private int performPassedQuantity;
	private Long totalChildProductQuantity;
	private Long totalAdultProductQuantity;
	private boolean isPayToSupplier;
	private String visitTime;
	private Long sellPrice;
	private String productName;
	private Long quantity;
	
	private String branchName;
	
	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getAdult() {
		return adult;
	}

	public void setAdult(Long adult) {
		this.adult = adult;
	}

	public Long getChild() {
		return child;
	}

	public void setChild(Long child) {
		this.child = child;
	}

	public Long getTotalMan() {
		return totalMan;
	}

	public void setTotalMan(Long totalMan) {
		this.totalMan = totalMan;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getPriceYuan() {
		return priceYuan;
	}

	public void setPriceYuan(String priceYuan) {
		this.priceYuan = priceYuan;
	}

	public List<OrdPerson> getPersonList() {
		return personList;
	}

	public void setPersonList(List<OrdPerson> personList) {
		this.personList = personList;
	}

	public int getPerformPassedQuantity() {
		return performPassedQuantity;
	}

	public void setPerformPassedQuantity(int performPassedQuantity) {
		this.performPassedQuantity = performPassedQuantity;
	}

	public Long getTotalChildProductQuantity() {
		return totalChildProductQuantity;
	}

	public void setTotalChildProductQuantity(Long totalChildProductQuantity) {
		this.totalChildProductQuantity = totalChildProductQuantity;
	}

	public Long getTotalAdultProductQuantity() {
		return totalAdultProductQuantity;
	}

	public void setTotalAdultProductQuantity(Long totalAdultProductQuantity) {
		this.totalAdultProductQuantity = totalAdultProductQuantity;
	}
	public boolean isPayToSupplier() {
		return isPayToSupplier;
	}

	public void setPayToSupplier(boolean isPayToSupplier) {
		this.isPayToSupplier = isPayToSupplier;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public Long getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
}
