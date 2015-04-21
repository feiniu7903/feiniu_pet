package com.lvmama.front.web.buy;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.bee.po.prod.LimitSaleTime;
import com.lvmama.comm.bee.po.prod.ProdHotel;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductRoyaltyService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.bee.vo.Prod;
import com.lvmama.comm.bee.vo.view.ViewProdProductJourneyDetail;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;

/**
 * 
 * @author songlianjun
 * 获取产品时间价格表
 *
 */
@ParentPackage("json-default")
@SuppressWarnings("unused")
public class AjaxQueryProductCalendarAction extends BaseAction {
	
	private Long productId;
	private Long branchId;
	private  Map<String,Object> prodCalendarMap;
	private Map<String , Object> jsonMap = new HashMap<String, Object>();
	private PageService pageService;
	private ProductHeadQueryService productServiceProxy;
 	private String choseDate;
	private String endDate;
	private String formartStr = "yyyy-MM-dd";
	private String error = "error";
	private Map<String , String> ordNum = new HashMap<String, String>();
	private String submitOrder;//提交订单
	private ProdProductRoyaltyService prodProductRoyaltyService;
	/**
	 * 获取某一产品的时间价格表
	 * 目前有上海欢乐谷调用，地址http://sh.happyvalley.cn/ticket/ticket.aspx
	 * @return
	 */
	@Action(value="/buy/ajaxQueryProductCalendar",results=@Result(type="json",name="prodCalendars",params={"includeProperties","prodCalendarMap.*","callbackParameter","callback"}))
	public String ajaxQueryProductCalendar(){
		List<CalendarModel>  calendarModelList =null;
		prodCalendarMap = new HashMap<String,Object>();
		try{
			calendarModelList = productServiceProxy.getProductCalendarByProductId(productId);
			prodCalendarMap.put("cmList", calendarModelList);
			prodCalendarMap.put("flag", "Y");
		}catch(Exception e){
			prodCalendarMap.put("cmList", new ArrayList());
			prodCalendarMap.put("flag", "N");
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
		}

		return "prodCalendars";
	}
	
 	@Action(value="/happy/product/price",results=@Result(type="json",name="checkAll",params={"includeProperties","jsonMap.*","callbackParameter","callback"}))
	public String  validateAmtInput() throws NumberFormatException, ParseException{
 		Long prdId = Long.valueOf(productId);
 		ProdProduct product = pageService.getProdProductByProductId(productId);
 		Date visitDate = null;
 		if(product != null && !product.IsAperiodic()) {
			visitDate = DateUtil.toDate(this.choseDate, "yyyy-MM-dd");
			ProdProductBranch mainBranch = productServiceProxy.getProdBranchDetailByProdBranchId(prdId, visitDate);
 		}
 		this.ordNum.put("param"+prdId, "1");
		List<ProdProductBranch> list = productServiceProxy.getProdBranchList(prdId, null, visitDate);
		checkProduct(visitDate, list);
		return "checkAll";

	}
 	/**
 	 * 检查产品或指定的类别的时间价格库存.
 	 * 包括最小起订量等.
 	 * 
 	 * @return
 	 * @throws NumberFormatException
 	 * @throws ParseException
 	 */
 	@Action(value="/product/price",results=@Result(type="json",name="checkAllAsync",params={"includeProperties","jsonMap.*"}))
	public String  validateAmtInputAsync() throws NumberFormatException, ParseException{
 		this.jsonMap.put("flag", "N");
		//branchId优先
		if (productId != null || branchId != null) {
			ProdProduct prodProduct = pageService.getProdProductByProductId(productId);
			if(prodProduct == null) {
				jsonMap.put(error,"当前商品不可售");			
				return "checkAllAsync";
			}
			List<ProdProductBranch> list = new ArrayList<ProdProductBranch>();
			Date visitDate = null;
			//非不定期才校验主类别
			if(!prodProduct.IsAperiodic()) {
				visitDate = DateUtil.toDate(this.choseDate, this.formartStr);
				if(branchId!=null){
					ProdProductBranch branch = this.productServiceProxy.getProdBranchDetailByProdBranchId(branchId, visitDate,!isProductPreview());
					if(branch==null){
						jsonMap.put(error, "该商品"+ DateUtil.getFormatDate(visitDate, "yyyy-MM-dd")+ "不可售");
					}else{
						//为非酒店单房型时只能展示该类别
						ProdProduct product = pageService.getProdProductByProductId(branch.getProductId());
						if (product.getOnlineTime().getTime()>System.currentTimeMillis()) { 
							jsonMap.put(error, "此产品未到销售时间");
						}else{
							if(product.isSingleRoom()){
								this.productId = branch.getProductId();
								list.add(branch);
							}
						}
					}
				}
			}
			//非不定期,visitDate为null无影响,会取branch的最晚有效期来做校验
			if(list.isEmpty()) {//为非酒店单房型时需要展示所有的可售类别
				list = this.productServiceProxy.getProdBranchListAndOnline(productId, null,
						visitDate,!isProductPreview());
			}
			if(CollectionUtils.isNotEmpty(list)){
				ProdProductBranch productBranch=list.get(0);
				ProdProduct product = productBranch.getProdProduct();
				//产品类型
				this.jsonMap.put("productType", product.getProductType());
				//自主打包
				this.jsonMap.put("selfPack", productBranch.getProdProduct().hasSelfPack());
				
				LimitSaleTime canSaleDateTime = pageService.getDateCanSale(productBranch.getProductId(), visitDate);
				if(!pageService.checkDateCanSale(productBranch.getProductId(),visitDate)){ 
					if(prodProductRoyaltyService.getRoyaltyProductIds().contains(productBranch.getProductId())){
						jsonMap.put(error,"该产品"+productBranch.getProductId()+"可预订范围为每天的"+canSaleDateTime.getLimitHourStart()+"至"+canSaleDateTime.getLimitHourEnd());
					}else{
						if(StringUtils.equals(canSaleDateTime.getLimitType(),Constant.LIMIT_SALE_TYPE.HOURRANGE.getCode())){
							jsonMap.put(error, "此产品当前游玩日期可预订范围为每天的"+canSaleDateTime.getLimitHourStart()+"至"+canSaleDateTime.getLimitHourEnd());
						}else{
							jsonMap.put(error, "此产品当前游玩日期需要"+DateUtil.getDateTime("yyyy-MM-dd HH:mm", canSaleDateTime.getLimitSaleTime())+"后下单");
						}
						
					}
				}else{
					// 酒店单房型检查
					if (branchId!=null&&product.isHotel()&&product.isSingleRoom() && !product.IsAperiodic()) {
						Date date = new Date();
						if (StringUtils.isNotEmpty(this.endDate)) {
							date = DateUtil.toDate(this.endDate, formartStr);
						} else {
							date = DateUtil.dsDay_Date(visitDate, 1);
						}
						this.checkHotle(productBranch, visitDate, date);
					} else {
						// 非酒店单房型检查(不定期产品暂时不做附加产品)
						if(StringUtils.equals("true", submitOrder) && !product.IsAperiodic()){
							List<ProdProductRelation> relatList=this.productServiceProxy.getRelatProduct(productBranch.getProductId(), visitDate);
							if(CollectionUtils.isNotEmpty(relatList)){
								for(ProdProductRelation relation:relatList){
									list.add(relation.getBranch());
								}
							}
						}
						checkProduct(visitDate, list);
					}
				}
			} else {
				jsonMap.put(error,"当前商品不可售");		
				return "checkAllAsync";
			}
		}
		
		//订单提交，如果jsonMap flag==N并且没有error的情况直接把error定义为无库存;
		if("N".equals(jsonMap.get("flag"))&&!jsonMap.containsKey(error)){ 
			jsonMap.put(error,"预订的产品库存为空");			
		}
		return "checkAllAsync";
	}
 	
 	private void checkHotle(ProdProductBranch mainBranch,Date visitDate,Date visitEndDate){
 		
 		if(visitDate.compareTo(visitEndDate) == 0){
 			jsonMap.put(error,"离店日期必须大于入住日期");
 			return;
 		}
 		
		int days = 1;
		//检查入住时间和离店时间不能是同一天。
		days =  DateUtil.getDaysBetween(visitDate,  visitEndDate);
		if(days>28){
			 jsonMap.put(error,"入住时间不能超过28晚");
			 return;
	    }
		String ordNumber =  this.ordNum.get("param"+mainBranch.getProdBranchId());	    
		for (int i = 0; i < days; i++) {
			Date date = DateUtil.dsDay_Date(visitDate, i);
			check(mainBranch,date,ordNumber);
			if(this.jsonMap.containsKey(error)){
				return;
			}
		}
		//当天销售产品的价格
		List<Map<String,Object>> prdTimePriceList = new ArrayList<Map<String,Object>>();
		if(mainBranch != null){
 			Map<String,Object> priceMap  = new HashMap<String,Object>();
 			priceMap.put("branchName", mainBranch.getBranchName());
 			priceMap.put("branchId", mainBranch.getProdBranchId());
 			priceMap.put("price", mainBranch.getSellPriceYuan()+"");
 			priceMap.put("branchDefault", mainBranch.hasDefault());
 			priceMap.put("minimum", mainBranch.getMinimum());
 			priceMap.put("maximum", mainBranch.getMaximum());
 			priceMap.put("priceUtil", mainBranch.getPriceUnit());
 			prdTimePriceList.add(priceMap);
 			
		}
		this.jsonMap.put("price", prdTimePriceList);
		jsonMap.put("days",days);
		this.jsonMap.put("flag", "Y");
 	}
 	
 	private void checkProduct(Date visitDate,List<ProdProductBranch> prodBranchList){
		if(CollectionUtils.isNotEmpty(prodBranchList)){
			for(ProdProductBranch branch:prodBranchList){
				String ordNumber = ordNum.get("param"+branch.getProdBranchId());				
				if(branch.getProdProduct().isAdditional()){
					ordNumber=ordNum.get("addition"+branch.getProdBranchId());
				}
				if(StringUtils.isNotEmpty(ordNumber)){
					//不定期取类别的最后有效期做校验
					if(branch.getProdProduct().IsAperiodic()) {
						visitDate = branch.getValidEndTime();
					}
					check(branch,visitDate,ordNumber);
				}
				
				if(this.jsonMap.containsKey(error)){
					return;
				}
			}
		}

		List<Map<String,Object>> prdTimePriceList = new ArrayList<Map<String,Object>>();		
 		for(ProdProductBranch branch:prodBranchList){
 			Map<String,Object> priceMap  = new HashMap<String,Object>();
 			priceMap.put("branchName", branch.getBranchName());
 			priceMap.put("branchId", branch.getProdBranchId());
 			priceMap.put("price",  branch.getSellPriceYuan()+"");
 			priceMap.put("branchDefault", branch.hasDefault());
 			priceMap.put("minimum", branch.getMinimum());
 			priceMap.put("maximum", branch.getMaximum());
 			priceMap.put("priceUtil", branch.getPriceUnit());
 			prdTimePriceList.add(priceMap);
 		}

 		this.jsonMap.put("price", prdTimePriceList);
 		this.jsonMap.put("flag", "Y");
 	}
 	
 	/**
 	 * @author yangbin
 	 * change:2012-2-23 上午10:38:51
 	 * @param prdId 
 	 * @param visitDate
 	 * @param pp
 	 * @param ordNumber
 	 */
 	private void check(ProdProductBranch prodBranch, Date visitDate, String ordNumber) {
 		
 		if (visitDate.getTime()<DateUtil.getClearCalendar().getTime().getTime()) {
 			jsonMap.put(error, "该商品"+DateUtil.getFormatDate(visitDate, "yyyy-MM-dd")+"不可售");
			return;
		}

		if(!pageService.isVisitDateProduct(prodBranch.getProductId(),prodBranch.getProdBranchId(), visitDate)){
			jsonMap.put(error, "该商品"+DateUtil.getFormatDate(visitDate, "yyyy-MM-dd")+"不可售");
			return;
		}
		
		//开始时间限制检查

		LimitSaleTime limitSaleTime=pageService.getDateCanSale(prodBranch.getProductId(), visitDate);
		if(limitSaleTime!=null){
			if(!StringUtils.equals(limitSaleTime.getLimitType(),Constant.LIMIT_SALE_TYPE.HOURRANGE.getCode())){
				String resTime = DateUtil.getFormatDate(limitSaleTime.getLimitSaleTime(), "yyyy-MM-dd HH:mm");
				jsonMap.put(error, "该商品此游玩日期的销售从"+resTime+"开始");
				return;
			}else{
				jsonMap.put(error, "该商品此游玩日期的销售时间为每天的"+limitSaleTime.getLimitHourStart()+"至"+limitSaleTime.getLimitHourEnd());
				return;
			}
		}
		if(StringUtils.isEmpty(ordNumber)){
			return;
		}
		long number=NumberUtils.toLong(ordNumber);
 		if(number<1){
 			return;
 		}
 		
 		if(StringUtils.equals("true", submitOrder)){
			if(!prodBranch.hasOnline()||!prodBranch.getProdProduct().isOnLine()){
				jsonMap.put(error, "该商品"+DateUtil.getFormatDate(visitDate, "yyyy-MM-dd")+"不可售");
				return;
			}
		}
		
 		//最小订购数
		if(number<prodBranch.getMinimum()){
			jsonMap.put(error, "产品【"+prodBranch.getFullName()+"】最小预订量为："+prodBranch.getMinimum());
			return;
		}
		 
		//最大订购数
		if(number>prodBranch.getMaximum()){
			jsonMap.put(error, "产品【"+prodBranch.getFullName()+"】最多可售数量为"+prodBranch.getMaximum());
			return;
		} 
		//ViewProdProduct vpp = productRemoteService.getProductByProductId(productId, visitDate);
 		//检查库存数，是否可超卖
		//当ordNumber为0时表示没有订购此产品，不需要检查库存
		if (number>0 && !this.productServiceProxy.isSellable(prodBranch.getProdBranchId(),number, visitDate)) {
			String value = null;
			if(prodBranch.getStock() >0){
				value = "产品【"+prodBranch.getFullName()+"】库存不足";
			}else{
				value = "产品【"+prodBranch.getFullName()+"】,"+DateUtil.getDateTime(formartStr, visitDate)+"库存不足,请选择其他日期";
			}
			jsonMap.put(error, value);
			return;
		}
	}
 	/**
	 * 取销售产品下类别最近可售日期
	 * @return
	 */
	@Action(value="/product/queryNearDayOfCanCell",results=@Result(type="json",name="checkAll",params={"includeProperties","jsonMap.*","callbackParameter","callback"}))
	public String queryNearDayOfCanCell()throws IOException {
		if (branchId!=null&&branchId > 0) {
			Date day=DateUtils.addDays(DateUtil.getDayStart(new Date()), 2);
			day = productServiceProxy.selectNearBranchTimePriceByBranchId(branchId,day);
			if(day!=null){
				ViewProdProductJourneyDetail prodProductJourneyDetail = pageService.getProductJourneyFromProductId(productId, day, 1l, 0l);
				if (prodProductJourneyDetail.isSuccess()){
					jsonMap.put("success", true);
					jsonMap.put("day", DateUtil.formatDate(day, "yyyy-MM-dd"));
				}
			}
		}
		return "checkAll";

	}
 	private boolean isProductPreview(){
 		return getSession("PRODUCT_PREVIEW_KEY")!=null;
 	}
 	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Map<String, Object> getProdCalendarMap() {
		return prodCalendarMap;
	}
	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public void setChoseDate(String choseDate) {
		this.choseDate = choseDate;
	}

	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}
 
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Map<String, String> getOrdNum() {
		return ordNum;
	}

	public void setOrdNum(Map<String, String> ordNum) {
		this.ordNum = ordNum;
	}

	/**
	 * @param submitOrder the submitOrder to set
	 */
	public void setSubmitOrder(String submitOrder) {
		this.submitOrder = submitOrder;
	}

	public void setProductServiceProxy(ProductHeadQueryService productServiceProxy) {
		this.productServiceProxy = productServiceProxy;
	}
	
	public void setProdProductRoyaltyService(
			ProdProductRoyaltyService prodProductRoyaltyService) {
		this.prodProductRoyaltyService = prodProductRoyaltyService;
	}
	
	
}
