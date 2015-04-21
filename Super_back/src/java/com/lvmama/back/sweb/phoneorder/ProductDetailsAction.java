package com.lvmama.back.sweb.phoneorder;


import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.prod.LimitSaleTime;
import com.lvmama.comm.bee.po.prod.ProdHotel;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ProdTicket;
import com.lvmama.comm.bee.service.LimitSaleTimeService;
import com.lvmama.comm.bee.service.com.ConditionService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.info.InfoProductInfo;
import com.lvmama.comm.pet.po.prod.ProdProductChannel;
import com.lvmama.comm.pet.po.pub.ComCondition;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.JsonMsg;
import com.lvmama.comm.vo.Constant;

@ParentPackage("json-default")
public class ProductDetailsAction extends  BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7270754924895773705L;
	private ProdProductService prodProductService;
	private ProdProductBranchService prodProductBranchService;
	private ProdProduct prodProduct;
	private String id;
	private String orderChannel;
	private List<SupPerformTarget> productPerformList;
	private List<SupSettlementTarget> productSettlementList;
	private List<SupBCertificateTarget> bcertificateTargetList;
	private LimitSaleTimeService limitSaleTimeService;
	private ProdRoute prodRoute;
	private ProdTicket prodTicket;
	private ProdHotel prodHotel;
	private InfoProductInfo ipi;
	protected ConditionService conditionService;
	private List<ComCondition> metaConditionList;
	List<ProdProductChannel> listProdChannel;
	private String choseDate;
	private String endDate;
	private JsonMsg jsonMsg;
	private List<JsonMsg> jsonMsgList;
	private Long quantity = 0L;
	private String formatStr = "yyyy-MM-dd";
	
	 
	
	@Action(value="/productDetail/checkDate",results=@Result(type="json",name="checkDate",params={"includeProperties","jsonMsg.*"}))
	public String checkDate() throws ParseException, IOException{
		Date visitDate = DateUtil.toDate(choseDate, "yyyy-MM-dd");
		Long productId = Long.valueOf(id);
		LimitSaleTime limitSaleTime = limitSaleTimeService.getLimitSaleTime(productId, visitDate);
		if(!StringUtils.equals(limitSaleTime.getLimitType(),Constant.LIMIT_SALE_TYPE.HOURRANGE.getCode())){
			this.jsonMsg = new JsonMsg(); 
			if(limitSaleTime != null){
				String resTime = DateUtil.getFormatDate(limitSaleTime.getLimitSaleTime(), "yyyy-MM-dd HH:mm");
				if(!"".equals(resTime)){
					jsonMsg.setKey("time");
					jsonMsg.setValue(resTime);
				}
			}
		}
		return "checkDate";
	}
	
	/**
	 * @author yangbin
	 * change:2012-2-23 上午11:24:14
	 * 调用方需要传入的参数要修改成传入类别ID
	 * 检查时间价格表，是否可售，
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@Action(value="/productDetail/checkStock",results=@Result(type="json",name="checkStock",params={"includeProperties","jsonMsg.*"}))
	public String checkStock() throws ParseException, IOException{
		//检查游玩时间不能为空
		if(StringUtils.isEmpty(choseDate)){
			jsonMsg =  new JsonMsg();
 			jsonMsg.setKey("stock");
			jsonMsg.setValue("请选择游玩日期");
			return "checkStock";
 		}
		
		Long prodBranchId = Long.valueOf(id);
		Date visitDate = DateUtil.toDate(choseDate, formatStr);
		Date visitEndDate = null;
		if(StringUtils.isNotEmpty(endDate)){
			visitEndDate = DateUtil.toDate(this.endDate, formatStr);
		}
		this.chcekProduct(visitDate,visitEndDate,prodBranchId);
 		return "checkStock";
	}
 
	public void chcekProduct(Date visitDate,Date endDate,Long prodBranchId){
		ProdProductBranch branch = prodProductBranchService.selectProdProductBranchByPK(prodBranchId); 
		if(quantity>branch.getMaximum()){ 
			jsonMsg = new JsonMsg(); 
			jsonMsg.setKey("stock"); 
			jsonMsg.setValue("订购数量超过最大可售数"); 
			return ; 
		}
		if(quantity<branch.getMinimum()){ 
			jsonMsg = new JsonMsg(); 
			jsonMsg.setKey("stock"); 
			jsonMsg.setValue("订购数量小于最小订购数"); 
			return ; 
		}

		int days = 1;
		if(endDate != null && (visitDate.compareTo(endDate) == 0 || visitDate.after(endDate))){
			jsonMsg =  new JsonMsg();
 			jsonMsg.setKey("stock");
			jsonMsg.setValue("离店日期必须大于入住日期");
			//检查入住时间和离店时间不能是同一天。
			days =  DateUtil.getDaysBetween(visitDate,  endDate);
			return;
		}
		
		for (int i = 0; i < days; i++) {
			Date date = DateUtil.dsDay_Date(visitDate, i);
	 		boolean productSellable = prodProductService.isProductSellable(prodBranchId, this.quantity, date);
	 		jsonMsg =  new JsonMsg();
	 		if(!productSellable){
	 			jsonMsg.setKey("stock");
				jsonMsg.setValue(DateUtil.getFormatDate(date, formatStr)+"当前库存不足，请选择其他日期");
				return;
	 		}
		}
	}

	public ProdProduct getProdProduct() {
		return prodProduct;
	}

	public void setProdProduct(ProdProduct prodProduct) {
		this.prodProduct = prodProduct;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public List<SupBCertificateTarget> getBcertificateTargetList() {
		return bcertificateTargetList;
	}

	public void setBcertificateTargetList(
			List<SupBCertificateTarget> bcertificateTargetList) {
		this.bcertificateTargetList = bcertificateTargetList;
	}

	public List<SupPerformTarget> getProductPerformList() {
		return productPerformList;
	}

	public void setProductPerformList(List<SupPerformTarget> productPerformList) {
		this.productPerformList = productPerformList;
	}

	public List<SupSettlementTarget> getProductSettlementList() {
		return productSettlementList;
	}

	public void setProductSettlementList(
			List<SupSettlementTarget> productSettlementList) {
		this.productSettlementList = productSettlementList;
	}
 
	public ProdRoute getProdRoute() {
		return prodRoute;
	}

	public void setProdRoute(ProdRoute prodRoute) {
		this.prodRoute = prodRoute;
	}

	public ProdTicket getProdTicket() {
		return prodTicket;
	}

	public void setProdTicket(ProdTicket prodTicket) {
		this.prodTicket = prodTicket;
	}

	public ProdHotel getProdHotel() {
		return prodHotel;
	}

	public void setProdHotel(ProdHotel prodHotel) {
		this.prodHotel = prodHotel;
	}
 
	public InfoProductInfo getIpi() {
		return ipi;
	}

	public void setIpi(InfoProductInfo ipi) {
		this.ipi = ipi;
	}

	public void setConditionService(ConditionService conditionService) {
		this.conditionService = conditionService;
	}

	public List<ComCondition> getMetaConditionList() {
		return metaConditionList;
	}

	public void setMetaConditionList(List<ComCondition> metaConditionList) {
		this.metaConditionList = metaConditionList;
	}

	public List<ProdProductChannel> getListProdChannel() {
		return listProdChannel;
	}

	public void setListProdChannel(List<ProdProductChannel> listProdChannel) {
		this.listProdChannel = listProdChannel;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public JsonMsg getJsonMsg() {
		return jsonMsg;
	}

	public void setJsonMsg(JsonMsg jsonMsg) {
		this.jsonMsg = jsonMsg;
	}

	public String getChoseDate() {
		return choseDate;
	}

	public void setChoseDate(String choseDate) {
		this.choseDate = choseDate;
	}

	public LimitSaleTimeService getLimitSaleTimeService() {
		return limitSaleTimeService;
	}

	public void setLimitSaleTimeService(LimitSaleTimeService limitSaleTimeService) {
		this.limitSaleTimeService = limitSaleTimeService;
	}

	public List<JsonMsg> getJsonMsgList() {
		return jsonMsgList;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @param prodProductBranchService the prodProductBranchService to set
	 */
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

}
