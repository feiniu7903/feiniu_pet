package com.lvmama.back.sweb.timeprice;
import java.beans.PropertyEditor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.comm.bee.po.prod.ProdOther;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.TimeRange;
import com.lvmama.comm.bee.po.prod.TimeRange.TimeRangePropertEditor;
import com.lvmama.comm.bee.po.prod.ViewMultiJourney;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdCouponIntervalService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.bee.service.view.ViewMultiJourneyService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.pet.po.prod.ProdCouponInterval;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;

@ParentPackage("json-default")
@Results( { @Result(name = "success", location = "/WEB-INF/pages/back/calendar/prod_time_price.jsp"), @Result(name = "prodTime", location = "/WEB-INF/pages/back/calendar/prod_time.jsp"),
		@Result(type = "json", name = "saveTimePrice", params = { "includeProperties", "success" }) })
public class ProdTimePriceAction extends AbstractTimePriceAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6930924994123896245L;
	private Long productId;
	private Long prodBranchId;
	private TimePrice[][] calendar;
	private CalendarModel calendarModel;
	private List<TimePrice> timePriceList;
	private ProdProductService prodProductService;
	private boolean success;
	private ProdProductBranchService prodProductBranchService;
	private ProdProduct product;
	private String visaSelfSign;
	private String todayOrderAble = "false";
	private MetaProductBranchService metaProductBranchService;
	private MetaProductService metaProductService;
	private ProdCouponIntervalService prodCouponIntervalService;
	private ProdProductTagService prodProductTagService;
	private String promotions;
	private String promotionsFlag;
	private String isCanSave = "true";
	private String invalidDateMemo;
	private boolean isMultiJourney = false;
	private ViewMultiJourneyService viewMultiJourneyService;
	private List<ViewMultiJourney> viewMultiJourneyList;
	
	@Action("/prod/toProdTimePrice")
	public String execute() {
		time();
		return this.SUCCESS;
	}

	/**
	 * 销售产品时间价格表查询
	 * 
	 * @return
	 */
	@Action("/prod/prodTime")
	public String time() {
		Map<String, Object> map;
		if ("UP".equals(monthType)) {
			map = DateUtil.getBeginAndEndDateByDate(DateUtil.getTheMiddle(currPageDate, -1));
		} else if ("DOWN".equals(monthType)) {
			map = DateUtil.getBeginAndEndDateByDate(DateUtil.getTheMiddle(currPageDate, 1));
		} else {
			if (currPageDate == null) {
				currPageDate = new Date();
			}
			map = DateUtil.getBeginAndEndDateByDate(currPageDate);
		}
		ProdProductBranch branch = prodProductBranchService.selectProdProductBranchByPK(prodBranchId);
		if (branch != null) {
			product = prodProductService.getProdProduct(branch.getProductId());
			if (product != null) {
				long beginTime = ((Date) (map.get("beginDate"))).getTime();
				long endTime = ((Date) (map.get("endDate"))).getTime();
				currPageDate = (Date) (map.get("currPageDate"));
				//如果是子类型为签证则获取是否自备签标识
				if(StringUtils.equals(product.getSubProductType(),Constant.SUB_PRODUCT_TYPE.VISA.name())){
				ProdOther prodOther=(ProdOther)product;
				visaSelfSign=prodOther.getVisaSelfSign();
				}
				// 加载时间价格,促标示
				calendarModel = prodProductService.selectProdTimePriceByProdBranchId(productId, prodBranchId, beginTime, endTime, product.hasSelfPack());
				calendarModel = fillCuCouponFlagForCalendar(product.getProductId(), prodBranchId);
				
				//取出是否支持手机当天预定
				if(product.isTicket()) {
					todayOrderAble = branch.getTodayOrderAble();
				}
				//期票产品，上线状态并且未过期，不能编辑
				if(product.IsAperiodic()) {
					Date validEndTime = branch.getValidEndTime();
					Date currentDate = DateUtil.getDayStart(new Date());
					if(product.isOnLine() && (validEndTime != null && !currentDate.after(validEndTime))) {
						isCanSave = "false";
					}
					invalidDateMemo = branch.getInvalidDateMemo();
				}
				if(product.isRoute()){
					isMultiJourney = ((ProdRoute)product).hasMultiJourney();
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("productId", product.getProductId());
					params.put("valid", 'Y');
					viewMultiJourneyList = viewMultiJourneyService.queryMultiJourneyByParams(params);
				}
			}
		}
		return "prodTime";
	}

	/**
	 * 获取该产品当前7个月范围促优惠时间标示
	 * @param productId
	 * @param branchId
	 * @return
	 */
	private CalendarModel fillCuCouponFlagForCalendar(Long productId, Long branchId){

		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DATE, -1);
		Date startDate = cal.getTime();
		cal.add(Calendar.MONTH, 7);
		Date endDate = cal.getTime();
		Map<String,Object> dateParam = new HashMap<String,Object>();
		dateParam.put("startDate", startDate);
		dateParam.put("endDate", endDate);
		
		List<CalendarModel> list = new ArrayList<CalendarModel>();
		list.add(calendarModel);
		list = prodCouponIntervalService.fillCuCouponFlagForCalendar(product.getProductId(), prodBranchId, list, dateParam);
		if(list != null && list.size() > 0){
			calendarModel = list.get(0);
		}
		return calendarModel;
	}
	
	/**
	 * 
	 * @return
	 */
	@Action("/prod/saveTimePrice")
	public void saveTimePrice() {
		JSONResult result = new JSONResult();
		try {
			Assert.notNull(timePriceBean);
			Assert.notNull(timePriceBean.getProdBranchId(), "类别不存在");
			if (!timePriceBean.isSpecifiedDateDuration()) {
				throw new Exception("时间区间不可以为空");
			}
			if (timePriceBean.getBeginDate().after(timePriceBean.getEndDate())) {
				throw new Exception("时间区间不正确");
			}
			if(timePriceBean.getBeginDate().before(DateUtil.getDayStart(new Date()))){
				throw new Exception("修改时间范围最早开始时间为当前日期");
			}
			// 针对产品的上下线时间判断
			ProdProductBranch branch = prodProductBranchService.selectProdProductBranchByPK(timePriceBean.getProdBranchId());
			Assert.notNull(branch);
			product = prodProductService.getProdProduct(branch.getProductId());
			Assert.notNull(product);
			//多行程判断
			if(product.isRoute()) {
				ProdRoute pr = (ProdRoute) product;
				if(pr.hasMultiJourney()) {
					if(timePriceBean.getMultiJourneyId() == null || timePriceBean.getMultiJourneyId() < 0l) {
						throw new Exception("行程必选");
					}
				}
			}
			if (!timePriceBean.isClosed()) {
				if(!product.hasSelfPack()){
				}else{
					timePriceBean.setAheadHour(0L);
				}
				
				if (!product.hasSelfPack()) {
					if(StringUtils.isEmpty(timePriceBean.getPriceType())){
						throw new IllegalArgumentException("价格类型不可以为空");
					}
					if(Constant.PRICE_TYPE.FIXED_PRICE.name().equals(timePriceBean.getPriceType())){
						if(StringUtils.equals(product.getSubProductType(),Constant.SUB_PRODUCT_TYPE.VISA.name())){
							ProdOther prodOther=(ProdOther)product;
							visaSelfSign=prodOther.getVisaSelfSign();
					     }
						if (hasSkipSetPrice()) {
							this.timePriceBean.setPriceF("0");
						}else if(StringUtils.equals(visaSelfSign,"true")){
						//如果是签证自备签，在判断价格>-1时提示
						 if(timePriceBean.getPrice() > -1){
							throw new Exception("签证销售产品自备签时驴妈妈价不可以为正数"); 
						 }
						}else if (timePriceBean.getPrice() < 1) {
							throw new Exception("驴妈妈价不可以为0");
						}
					}else if(Constant.PRICE_TYPE.RATE_PRICE.name().equals(timePriceBean.getPriceType())){
						if (timePriceBean.getRatePrice()==null||timePriceBean.getRatePrice()<1){
							throw new Exception("比例加价值不可以小于1");
						}
					}else if(Constant.PRICE_TYPE.FIXED_ADD_PRICE.name().equals(timePriceBean.getPriceType())){
						if (timePriceBean.getFixedAddPrice()==null||timePriceBean.getFixedAddPrice()<0){
							throw new Exception("固定加价不可以小于0");
						}
					}
				}
				if (product.getOnlineTime() == null || product.getOfflineTime() == null) {
					throw new Exception("产品的上下线时间为空，不可以操作时间价格表");
				}

				if (product.getOnlineTime().after(timePriceBean.getBeginDate())) {
					throw new Exception("时间价格表修改区间不可以早于上线时间");
				}
				if (product.getOfflineTime().before(timePriceBean.getEndDate())) {
					throw new Exception("时间价格表修改区间不可以晚于下线时间");
				}
			}
			if(product.IsAperiodic()) {
				//校验不定期时间价格表
				ResultHandle handle = prodProductService.aperiodicTimePriceValidation(timePriceBean, timePriceBean.getProdBranchId());
				if(handle.isFail()) {
					throw new Exception(handle.getMsg());
				}
			}
			
			if (!timePriceBean.isClosed() && product != null && product.hasSelfPack()) {
				List<Date> dates = prodProductService.saveSelfPackTimePrice(timePriceBean, product.getProductId(), getOperatorNameAndCheck());
				if (!dates.isEmpty()) {
					StringBuffer sb = new StringBuffer();
					for (Date d : dates) {
						sb.append(DateUtil.getFormatDate(d, "MM-dd"));
						sb.append(",");
					}
					sb.setLength(sb.length() - 1);
					result.put("code", -1000);// 把没有添加成功的日期发送给客户端
					result.put("msg", sb.toString());
				}
			} else {
				prodProductService.saveTimePrice(timePriceBean, this.productId, this.getOperatorNameAndCheck());	
			}
			
			productMessageProducer.sendMsg(MessageFactory.newProductSellPriceMessage(timePriceBean.getProdBranchId()));
			//期票产品，修改期票有效期
			if(product.IsAperiodic()) {
				branch.setInvalidDateMemo(invalidDateMemo);
				prodProductBranchService.updateByPrimaryKeySelective(branch);
				// 现在的消息直接向类别发送.
				TimeRange range=new TimeRange(timePriceBean.getBeginDate(),timePriceBean.getEndDate());
				PropertyEditor editor = new TimeRangePropertEditor();
				editor.setValue(range);		
				productMessageProducer.sendMsg(MessageFactory.updateProdBranchValidTimeMessage(timePriceBean.getProdBranchId(),editor.getAsText()));
			}
			
			//页面选了促checkkbox ---保存促时间记录和标签
			if(promotionsFlag !=null && promotionsFlag.equalsIgnoreCase("on")){
				if(promotions.equalsIgnoreCase("1")){
					addCuCouponTag(timePriceBean, branch.getProductId(), timePriceBean.getProdBranchId());
				}else if(promotions.equalsIgnoreCase("0")){
					delCuCouponTag(timePriceBean, branch.getProductId(), timePriceBean.getProdBranchId());
				}
			}
		} catch (JSONResultException ex) {
			result.raise(ex);
		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(getResponse());
	}
		
	//保存促优惠时间(可存N天多笔记录)和标签
	private void addCuCouponTag(TimePrice timePriceBean, Long currentProductId, Long currentProdBranchId){
		long between =(timePriceBean.getEndDate().getTime()- timePriceBean.getBeginDate().getTime())/1000;
		long day = between/(24*3600) + 1; //包含当天
		List<ProdCouponInterval> prodCouponIntervalList = new ArrayList<ProdCouponInterval>();
		Calendar specDate = Calendar.getInstance();
	    specDate.setTime(timePriceBean.getBeginDate());
		for(int i = 0; i < day; i++){
			ProdCouponInterval prodCI = new ProdCouponInterval();
			if(i > 0){
				 specDate.add(Calendar.DATE, 1);//第一笔记录外
			}
			prodCI.setSpecDate(specDate.getTime());
			prodCI.setCouponType(Constant.PROD_TAG_NAME.SALES_PROMOTION.getCode());
			prodCI.setBranchId(currentProdBranchId);
			prodCI.setProductId(currentProductId);
			prodCouponIntervalList.add(prodCI);
		}
		//插入N天的记录
		prodCouponIntervalService.batchInsert(prodCouponIntervalList);
		
		//重构产品与标签关联
		List<ProdProductTag> prodProductTags = null;
		List<Long> productIds = new ArrayList<Long>();
		productIds.add(currentProductId);
		ProdProductTag prodProductTag = prodCouponIntervalService
				.checkProductTag(Constant.PROD_TAG_NAME.SALES_PROMOTION.getCode(), currentProductId);
		
		if (prodProductTag != null) {
			prodProductTags = new ArrayList<ProdProductTag>();
			prodProductTags.add(prodProductTag);
			//标签保存(1,有记录---先删后插      2,没记录---新插)
			prodProductTagService.addSystemProgProductTags(prodProductTags, productIds, 
					Constant.PROD_TAG_NAME.SALES_PROMOTION.getCnName());
		}
	}
	
	//删除促优惠时间和重构产品标签关联
	private void delCuCouponTag(TimePrice timePriceBean, Long currentProductId, Long currentProdBranchId){
		Map<String, Object> delMap = new HashMap<String, Object>();
		delMap.put("startDate", timePriceBean.getBeginDate());
		delMap.put("endDate", timePriceBean.getEndDate());
		delMap.put("productId", currentProductId);
		delMap.put("branchId", currentProdBranchId);
		delMap.put("couponType", Constant.PROD_TAG_NAME.SALES_PROMOTION.getCode());
		prodCouponIntervalService.deleteByParams(delMap);

		List<ProdProductTag> prodProductTags = null;
		List<Long> productIds = new ArrayList<Long>();
		productIds.add(currentProductId);
		// 重构产品与标签关联(若没记录获取prodProductTag=null)
		ProdProductTag prodProductTag = prodCouponIntervalService
				.checkProductTag(Constant.PROD_TAG_NAME.SALES_PROMOTION.getCode(), currentProductId);
		if (prodProductTag != null) {
			prodProductTags = new ArrayList<ProdProductTag>();
			prodProductTags.add(prodProductTag);
		}
		// 标签保存(1,有记录---先删后插 2,没记录---新插)
		prodProductTagService.addSystemProgProductTags(prodProductTags, productIds, 
			Constant.PROD_TAG_NAME.SALES_PROMOTION.getCnName());
	}
		
	/**
	 * @param prodProductBranchService
	 *            the prodProductBranchService to set
	 */
	public void setProdProductBranchService(ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public TimePrice[][] getCalendar() {
		return calendar;
	}

	public void setCalendar(TimePrice[][] calendar) {
		this.calendar = calendar;
	}

	public void setCalendarList(List<CalendarModel> calendarList) {
		this.calendarList = calendarList;
	}

	public List<TimePrice> getTimePriceList() {
		return timePriceList;
	}

	public void setTimePriceList(List<TimePrice> timePriceList) {
		this.timePriceList = timePriceList;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public CalendarModel getCalendarModel() {
		return calendarModel;
	}

	public void setCurrPageDate(Date currPageDate) {
		this.currPageDate = currPageDate;
	}

	public void setMonthType(String monthType) {
		this.monthType = monthType;
	}

	public void setTimePriceBean(TimePrice timePriceBean) {
		this.timePriceBean = timePriceBean;
	}

	public boolean isSuccess() {
		return success;
	}

	public Long getProdBranchId() {
		return prodBranchId;
	}

	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

	/**
	 * @return the product
	 */
	public ProdProduct getProduct() {
		return product;
	}

	public String getVisaSelfSign() {
		return visaSelfSign;
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public String getTodayOrderAble() {
		return todayOrderAble;
	}

	public void setProdCouponIntervalService(
			ProdCouponIntervalService prodCouponIntervalService) {
		this.prodCouponIntervalService = prodCouponIntervalService;
	}

	public void setProdProductTagService(ProdProductTagService prodProductTagService) {
		this.prodProductTagService = prodProductTagService;
	}

	public String getPromotions() {
		return promotions;
	}

	public void setPromotions(String promotions) {
		this.promotions = promotions;
	}

	public String getPromotionsFlag() {
		return promotionsFlag;
	}

	public void setPromotionsFlag(String promotionsFlag) {
		this.promotionsFlag = promotionsFlag;
	}

	public String getIsCanSave() {
		return isCanSave;
	}

	public String getInvalidDateMemo() {
		return invalidDateMemo;
	}

	public void setInvalidDateMemo(String invalidDateMemo) {
		this.invalidDateMemo = invalidDateMemo;
	}

	public void setViewMultiJourneyService(
			ViewMultiJourneyService viewMultiJourneyService) {
		this.viewMultiJourneyService = viewMultiJourneyService;
	}

	public List<ViewMultiJourney> getViewMultiJourneyList() {
		return viewMultiJourneyList;
	}

	public boolean hasMultiJourney() {
		return isMultiJourney;
	}
	
}