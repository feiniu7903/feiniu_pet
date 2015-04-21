package com.lvmama.distribution.model.lv;

import java.util.List;

import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.distribution.util.DistributionUtil;

/**
 * 分销--产品类别对象
 * @author lipengcheng
 *
 */
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
	private ProdProductBranch prodProductBranch;
	private List<TimePrice> timePriceList;
	
	public Branch(){
	}
	
	/**
	 * 获得签证信息
	 * @return
	 */
	public String getLocalSigned() {
		return this.getBranchId() + this.getQuantity() + this.getVisitDate() + this.getLeaveDate()+this.getSellPrice();
	}
	
	public Branch(ProdProductBranch prodProductBranch){
		this.prodProductBranch = prodProductBranch;
		this.timePriceList = prodProductBranch.getTimePriceList();
	}
	
	/**
	 * 构造查询产品列表报文--单个类别信息节点
	 * @return
	 */
	public String buildForProductInfoList(boolean isQuanr,boolean isCashZero){
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<branch>");
		xmlStr.append(DistributionUtil.buildXmlElement("branchId", prodProductBranch.getProdBranchId()));
		xmlStr.append(DistributionUtil.buildXmlElement("branchName", prodProductBranch.getBranchName()));
		xmlStr.append(DistributionUtil.buildXmlElement("branchOnLine", this.getOnline(prodProductBranch)));
		xmlStr.append(DistributionUtil.buildXmlElement("minimum", prodProductBranch.getMinimum()));
		xmlStr.append(DistributionUtil.buildXmlElement("maximum", prodProductBranch.getMaximum()));
		xmlStr.append(DistributionUtil.buildXmlElement("adultQuantity", prodProductBranch.getAdultQuantity()));
		xmlStr.append(DistributionUtil.buildXmlElement("childQuantity", prodProductBranch.getChildQuantity()));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("branchDescription", prodProductBranch.getDescription()));
		if(isQuanr){
			Long cashBack = isCashZero ? 0L : prodProductBranch.getSellPrice() == null ? 0L : prodProductBranch.getSellPrice();

			xmlStr.append(DistributionUtil.buildXmlElement("commentsCashback", cashBack));
		}
		xmlStr.append("</branch>");
		return xmlStr.toString();
	}
	
	/**
	 * 构造单个产品时间价格报文--类别信息节点
	 * @return
	 */
	public String buildForGetProductPrice() {
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<branch>");
		xmlStr.append(DistributionUtil.buildXmlElement("branchId", prodProductBranch.getProdBranchId()));
		xmlStr.append(DistributionUtil.buildXmlElement("branchName", prodProductBranch.getBranchName()));
		xmlStr.append(DistributionUtil.buildXmlElement("branchOnLine", this.getOnline(prodProductBranch)));
		for (TimePrice timePrice : timePriceList) {
			Price price = new Price(timePrice);
			xmlStr.append(price.buildPriceStr());
		}
		xmlStr.append("</branch>");
		return xmlStr.toString();
	}
	
	/**
	 * 构造推送单个产品时间价格报文--类别信息节点
	 * @return
	 */
	public String buildForPushProductPrice(boolean isQuanr , boolean isCashZero) {
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<branch>");
		xmlStr.append(DistributionUtil.buildXmlElement("branchId", prodProductBranch.getProdBranchId()))
		.append(DistributionUtil.buildXmlElement("branchName", prodProductBranch.getBranchName()))
		.append(DistributionUtil.buildXmlElement("branchOnLine", this.getOnline(prodProductBranch)))
		.append(DistributionUtil.buildXmlElement("minimum", prodProductBranch.getMinimum()))
		.append(DistributionUtil.buildXmlElement("maximum", prodProductBranch.getMaximum()))
		.append(DistributionUtil.buildXmlElementInCDATA("branchDescription", prodProductBranch.getDescription()));
		if(isQuanr){
			Long cashBack = isCashZero ? 0L : prodProductBranch.getSellPrice() == null ? 0L : prodProductBranch.getSellPrice();

			xmlStr.append(DistributionUtil.buildXmlElement("commentsCashback", cashBack));
		}
		for (TimePrice timePrice : timePriceList) {
			Price price = new Price(timePrice);
			xmlStr.append(price.buildPriceStr());
		}
		xmlStr.append("</branch>");
		return xmlStr.toString();
	}
	
	/**
	 * 构造查询产品产品信息--单个类别信息节点
	 * @return
	 */
	public String buildForGetProductInfo(boolean isQuanr, boolean isCashZero) {
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<branch>");
		xmlStr.append(DistributionUtil.buildXmlElement("branchId", prodProductBranch.getProdBranchId()));
		xmlStr.append(DistributionUtil.buildXmlElement("branchName", prodProductBranch.getBranchName()));
		xmlStr.append(DistributionUtil.buildXmlElement("branchOnLine", this.getOnline(prodProductBranch)));
		xmlStr.append(DistributionUtil.buildXmlElement("minimum", prodProductBranch.getMinimum()));
		xmlStr.append(DistributionUtil.buildXmlElement("maximum", prodProductBranch.getMaximum()));
		xmlStr.append(DistributionUtil.buildXmlElement("adultQuantity", prodProductBranch.getAdultQuantity()));
		xmlStr.append(DistributionUtil.buildXmlElement("childQuantity", prodProductBranch.getChildQuantity()));
		xmlStr.append(DistributionUtil.buildXmlElementInCDATA("branchDescription", prodProductBranch.getDescription()));
		if(isQuanr){
			Long cashBack = isCashZero ? 0L : prodProductBranch.getSellPrice() == null ? 0L : prodProductBranch.getSellPrice();

			xmlStr.append(DistributionUtil.buildXmlElement("commentsCashback", cashBack));
		}
		for (TimePrice timePrice : timePriceList) {
			Price price = new Price(timePrice);
			xmlStr.append(price.buildPriceStr());
		}
		xmlStr.append("</branch>");
		return xmlStr.toString();
	}
	
	/**
	 * 构造时间价格表报文--类别信息表节点
	 * @return
	 */
	public String buildForProductPriceList() {
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<branch>");
		xmlStr.append(DistributionUtil.buildXmlElement("branchId", prodProductBranch.getProdBranchId()));
		xmlStr.append(DistributionUtil.buildXmlElement("branchName", prodProductBranch.getBranchName()));
		xmlStr.append(DistributionUtil.buildXmlElement("branchOnLine", this.getOnline(prodProductBranch)));
		for (TimePrice timePrice : timePriceList) {
			Price price = new Price(timePrice);
			xmlStr.append(price.buildPriceStr());
		}
		xmlStr.append("</branch>");
		return xmlStr.toString();
	}
	
	/**
	 * 构造产品上下线信息报文
	 * @return
	 */
	public String buildForGetProductOnLine(){
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<branch>");
		xmlStr.append(DistributionUtil.buildXmlElement("branchId", prodProductBranch.getProdBranchId()));
		xmlStr.append(DistributionUtil.buildXmlElement("branchOnLine", this.getOnline(prodProductBranch)));
		xmlStr.append("</branch>");
		return xmlStr.toString();
	}
	
	private String getOnline(ProdProductBranch prodProductBranch){
		return ("true".equals(prodProductBranch.getOnline()) && "Y".equals(prodProductBranch.getValid())) ? "true" : "false";
	}
	
	public String getBranchId() {
		return StringUtil.replaceNullStr(branchId);
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getQuantity() {
		return StringUtil.replaceNullStr(quantity);
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getVisitDate() {
		return StringUtil.replaceNullStr(visitDate);
	}
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	public ProdProductBranch getProdProductBranch() {
		return prodProductBranch;
	}

	public void setProdProductBranch(ProdProductBranch prodProductBranch) {
		this.prodProductBranch = prodProductBranch;
	}

	public String getLeaveDate() {
		return StringUtil.replaceNullStr(leaveDate);
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getSellPrice() {
		return StringUtil.replaceNullStr(sellPrice);
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}

}
