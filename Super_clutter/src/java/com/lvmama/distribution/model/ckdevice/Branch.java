package com.lvmama.distribution.model.ckdevice;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.utils.StringUtil;

/**
 * 产品类别
 * @author gaoxin
 *
 */
@XmlRootElement
public class Branch {
	
	/** 类别Id*/
	private String branchId;//request
	/** 类别名称*/
	private String branchName;
	/** 订购份数*/
	private String quantity;//request
	/** 游玩日期*/
	private String visitDate;//request
	/** 离店日期*/
	private String leaveDate;
	/** 驴妈妈售价*/
	private String sellPrice;
	private String branchOnLine;
	private Long minimum;
	private Long maximum;
	private Long adultQuantity;
	private Long childQuantity;
	private String branchDescription;
	private List<Price> priceList;
	public Branch(){
	}
	
	public Branch(ProdProductBranch prodProductBranch){
		List<TimePrice> timePriceList = prodProductBranch.getTimePriceList();
		this.branchId = String.valueOf(prodProductBranch.getProdBranchId());
		this.branchName = prodProductBranch.getBranchName();
		this.branchOnLine = this.getOnline(prodProductBranch);
		this.minimum = prodProductBranch.getMinimum();
		this.maximum = prodProductBranch.getMaximum();
		this.adultQuantity = prodProductBranch.getAdultQuantity();
		this.childQuantity = prodProductBranch.getChildQuantity();
		this.branchDescription = prodProductBranch.getDescription();
		if(timePriceList != null && !timePriceList.isEmpty()){
			priceList = new ArrayList<Price>();
			for (TimePrice timePrice : timePriceList) {
				Price price = new Price(timePrice);
				priceList.add(price);
			}
		}
	}

	private String getOnline(ProdProductBranch prodProductBranch){
		return ("true".equals(prodProductBranch.getOnline()) && "Y".equals(prodProductBranch.getValid())) ? "true" : "false";
	}
	@XmlElement
	public String getBranchId() {
		return StringUtil.replaceNullStr(branchId);
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	@XmlElement
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	@XmlElement
	public String getQuantity() {
		return StringUtil.replaceNullStr(quantity);
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	@XmlElement
	public String getVisitDate() {
		return StringUtil.replaceNullStr(visitDate);
	}
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	
	@XmlElement
	public String getLeaveDate() {
		return StringUtil.replaceNullStr(leaveDate);
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}
	@XmlElement
	public String getSellPrice() {
		return StringUtil.replaceNullStr(sellPrice);
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}

	@XmlElement
	public String getBranchOnLine() {
		return branchOnLine;
	}


	public void setBranchOnLine(String branchOnLine) {
		this.branchOnLine = branchOnLine;
	}


	public Long getMinimum() {
		return minimum;
	}


	public void setMinimum(Long minimum) {
		this.minimum = minimum;
	}


	public Long getMaximum() {
		return maximum;
	}


	public void setMaximum(Long maximum) {
		this.maximum = maximum;
	}


	public Long getAdultQuantity() {
		return adultQuantity;
	}


	public void setAdultQuantity(Long adultQuantity) {
		this.adultQuantity = adultQuantity;
	}


	public Long getChildQuantity() {
		return childQuantity;
	}


	public void setChildQuantity(Long childQuantity) {
		this.childQuantity = childQuantity;
	}


	public String getBranchDescription() {
		return branchDescription;
	}


	public void setBranchDescription(String branchDescription) {
		this.branchDescription = branchDescription;
	}

	public List<Price> getPriceList() {
		return priceList;
	}


	public void setPriceList(List<Price> priceList) {
		this.priceList = priceList;
	}

}
