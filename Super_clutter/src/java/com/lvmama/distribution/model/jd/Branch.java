package com.lvmama.distribution.model.jd;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.distribution.util.JdUtil;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
/**
 * 产品类别
 * @author gaoxin
 *
 */
public class Branch {
	private String branchId;//供应商的产品类别ID
   	private String branchName;//类别名称
   	private String numberLimit;
   	private List<Price> priceList=new ArrayList<Price>(); //价格
   	private ProdProductBranch prdBranch;
	private String branchDescription;
   	public Branch(){}
   	public Branch(ProdProductBranch prdBranch){
   		this.prdBranch=prdBranch;
   	}
   	
   	/**
   	 * 构建类别报文
   	 * @return
   	 */
   	public String buildBranchToXml(){
   		StringBuilder sb=new StringBuilder();
   		String numLimit=prdBranch.getMinimum()+"-"+prdBranch.getMaximum();
   		sb.append("<branch>")
   		.append(JdUtil.buildXmlElement("branchId", prdBranch.getProdBranchId()))
   		.append(JdUtil.buildXmlElement("branchName", prdBranch.getBranchName()))
   		.append(JdUtil.buildXmlElement("numberLimit", numLimit))
   		.append(JdUtil.buildXmlElement("branchDescription", prdBranch.getDescription()));
   		if(priceList.size()>0){
   			sb.append("<prices>");
   			for(Price price:priceList){
   				sb.append(price.buildPriceToXml());
   			}
   			sb.append("</prices>");
   		}
   		sb.append("</branch>");
   		return sb.toString();
   	}
   	
   	
   	/**
   	 * 构建每日价格类别报文
   	 * @return
   	 */
   	public String buildBranchToXmlInDailyPrice(){
   		StringBuilder sb=new StringBuilder();
   		sb.append("<branch>")
   		.append(JdUtil.buildXmlElement("branchId", prdBranch.getProdBranchId()))
   		.append(JdUtil.buildXmlElement("branchName", prdBranch.getBranchName()));
   		if(priceList.size()>0){
   			sb.append("<prices>");
   			for(Price price:priceList){
   				sb.append(price.buildPriceToXml());
   			}
   			sb.append("</prices>");
   		}
   		sb.append("</branch>");
   		return sb.toString();
   	}
   	/**
   	 * 构建上下线相关报文
   	 * @return
   	 */
   	public String buildOnOffLineBranchToXml() {
   		StringBuilder sb=new StringBuilder();
   		sb.append(JdUtil.buildXmlElement("branch", JdUtil.buildXmlElement("branchId", prdBranch.getProdBranchId())));
   		return sb.toString();
	}
   	
	public String getBranchId() {
		return branchId;
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
	public List<Price> getPriceList() {
		return priceList;
	}
	public void setPriceList(List<Price> priceList) {
		this.priceList = priceList;
	}
	public ProdProductBranch getPrdBranch() {
		return prdBranch;
	}
	public void setPrdBranch(ProdProductBranch prdBranch) {
		this.prdBranch = prdBranch;
	}
	public String getNumberLimit() {
		return numberLimit;
	}
	public void setNumberLimit(String numberLimit) {
		this.numberLimit = numberLimit;
	}
	public String getBranchDescription() {
		return branchDescription;
	}
	public void setBranchDescription(String branchDescription) {
		this.branchDescription = branchDescription;
	}

}
