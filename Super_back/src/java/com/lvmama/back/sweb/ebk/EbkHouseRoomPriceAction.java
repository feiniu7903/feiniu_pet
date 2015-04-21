package com.lvmama.back.sweb.ebk;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkHousePrice;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.ebooking.EbkHousePriceService;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProductService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.service.EbkSuperClientService;
@Results(value={
		@Result(name="ebkHousePriceApplyList",location="/WEB-INF/pages/back/ebk/ebkHousePriceApplyList.jsp"),
		@Result(name="ebkHousePriceList",location="/WEB-INF/pages/back/ebk/ebkHousePriceList.jsp"),
		@Result(name="ebkHousePriceApplyDetail",location="/WEB-INF/pages/back/ebk/ebkHousePriceApplyDetail.jsp"),
		@Result(name="ebkRoutePriceApplyList",location="/WEB-INF/pages/back/ebk/ebkRoutePriceApplyList.jsp"),
		@Result(name="ebkRoutePriceList",location="/WEB-INF/pages/back/ebk/ebkRoutePriceList.jsp")
})				  
@Namespace("/ebooking/roomprice")
public class EbkHouseRoomPriceAction extends BackBaseAction {
	private static final long serialVersionUID = -3926743297676568212L;
	private EbkHousePriceService ebkHousePriceService;
	private EbkSuperClientService ebkSuperClientService;
	
	

	private EbkHousePrice ebkHousePrice = new EbkHousePrice();
	//分页默认为第一页.
	private Long page = 1L;
	//分页对象.
	private Page<EbkHousePrice> ebkHousePricePage = new Page<EbkHousePrice>();
	 
	//
	private List<EbkHousePrice> ebkHousePriceList = new ArrayList<EbkHousePrice>();

	private ProductService productProxy;
	//查询条件-提交的开始时间.
	private String createTimeBegin;
	//查询条件-提交的结束时间.
	private String createTimeEnd;
	//查询条件-审核的开始时间.
	private String confirmTimeBegin;
	//查询条件-审核的开始时间.
	private String confirmTimeEnd;
	
	//EBooking房价变价申请的审核状态查询条件列表. 引用Constant.EBK_SUGGEST_AUDIT_STATUS.
	private List<CodeItem> suggestAuditStatusList = new ArrayList<CodeItem>();
	
	private CalendarModel calendarModel;
	
	private MetaProductService metaProductService;
	
	private MetaProductBranchService metaProductBranchService;
	
	private SupplierService supplierService;
	
	private TimePrice timePriceBean;

	/**
	 * 待审核房价变价列表页面.
	 * @return
	 */
	@Action("ebkHousePriceApplyList")
	public String ebkHousePriceApplyList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status",
				Constant.EBK_SUGGEST_AUDIT_STATUS.WAITING_FOR_AUDIT.name());
		if ( this.ebkHousePrice.getHousePriceId()!=null) {
			params.put("housePriceId", this.ebkHousePrice.getHousePriceId());
		}
		// 提交时间.
		if (this.createTimeBegin!=null&&!StringUtils.isEmpty(this.createTimeBegin)) {
			params.put("createTimeBegin", this.createTimeBegin);
		}
		if (this.createTimeEnd!=null&&!StringUtils.isEmpty(this.createTimeEnd)) {
			params.put("createTimeEnd", this.createTimeEnd);
		}
		// 申请主题.
		if (this.ebkHousePrice.getSubject()!=null&&!StringUtils.isEmpty(this.ebkHousePrice.getSubject())) {
			params.put("subject", this.ebkHousePrice.getSubject().trim());
		}
		// 提交人.
		if (this.ebkHousePrice.getSubmitUser()!=null&&!StringUtils.isEmpty(this.ebkHousePrice.getSubmitUser())) {
			params.put("submitUser", this.ebkHousePrice.getSubmitUser().trim());
		}
		// 酒店名称.
		if (this.ebkHousePrice.getSupplierId()!=null ) {
			params.put("supplierId", this.ebkHousePrice.getSupplierId());
		}
		params.put("productType", Constant.PRODUCT_TYPE.HOTEL.name());
		params.put("applyType", Constant.EBK_HOUSE_PRICE_APPLY_TYPE.PRICE_APPLY.name());
		this.ebkHousePricePage.setTotalResultSize(this.ebkHousePriceService.countEbkHousePriceListByExample(params));
		ebkHousePricePage.buildUrl(getRequest());
		ebkHousePricePage.setCurrentPage(this.page);
		params.put("start", ebkHousePricePage.getStartRows());
		params.put("end", ebkHousePricePage.getEndRows());
		params.put("orderby", "CREATE_TIME");
		params.put("order", "DESC");
		this.ebkHousePriceList = this.ebkHousePriceService.findEbkHousePriceListByExample(params);
		this.ebkHousePricePage.setItems(this.ebkHousePriceList);
		return "ebkHousePriceApplyList";
	}

	/**
	 * 待审核线路变价列表页面.
	 * @return
	 */
	@Action("ebkRoutePriceApplyList")
	public String ebkRoutePriceApplyList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status",Constant.EBK_SUGGEST_AUDIT_STATUS.WAITING_FOR_AUDIT.name());
		if ( this.ebkHousePrice.getHousePriceId()!=null) {
			params.put("housePriceId", this.ebkHousePrice.getHousePriceId());
		}
		// 提交时间.
		if (this.createTimeBegin!=null&&!StringUtils.isEmpty(this.createTimeBegin)) {
			params.put("createTimeBegin", this.createTimeBegin);
		}
		if (this.createTimeEnd!=null&&!StringUtils.isEmpty(this.createTimeEnd)) {
			params.put("createTimeEnd", this.createTimeEnd);
		}
		// 申请主题.
		if (this.ebkHousePrice.getSubject()!=null&&!StringUtils.isEmpty(this.ebkHousePrice.getSubject())) {
			params.put("subject", this.ebkHousePrice.getSubject().trim());
		}
		// 提交人.
		if (this.ebkHousePrice.getSubmitUser()!=null&&!StringUtils.isEmpty(this.ebkHousePrice.getSubmitUser())) {
			params.put("submitUser", this.ebkHousePrice.getSubmitUser().trim());
		}
		// 酒店名称.
		if (this.ebkHousePrice.getSupplierId()!=null ) {
			params.put("supplierId", this.ebkHousePrice.getSupplierId());
		}
		// 采购产品名称
		if (this.ebkHousePrice.getProductName()!=null&&!StringUtils.isEmpty(this.ebkHousePrice.getProductName())) {
			params.put("productName", this.ebkHousePrice.getProductName().trim());
		}
		//采购产品类型为线路
		params.put("productType", Constant.PRODUCT_TYPE.ROUTE.name());
		params.put("applyType", Constant.EBK_HOUSE_PRICE_APPLY_TYPE.PRICE_APPLY.name());
		this.ebkHousePricePage.setTotalResultSize(this.ebkHousePriceService.countEbkHousePriceListByExample(params));
		ebkHousePricePage.buildUrl(getRequest());
		ebkHousePricePage.setCurrentPage(this.page);
		params.put("start", ebkHousePricePage.getStartRows());
		params.put("end", ebkHousePricePage.getEndRows());
		params.put("orderby", "CREATE_TIME");
		params.put("order", "DESC");
		this.ebkHousePriceList = this.ebkHousePriceService.findEbkHousePriceListByExample(params);
		this.ebkHousePricePage.setItems(this.ebkHousePriceList);
		return "ebkRoutePriceApplyList";
	}
	/**
	 * 已审核(审核通过、审核不通过)房价变价列表页面.
	 * @return
	 */
	@Action("ebkHousePriceList")
	public String ebkHousePriceList() {
		this.initSuggestAuditStatusList(true);
		Map<String,Object> params = new HashMap<String,Object>();
		//申请状态查询条件:为空或全部时查询"审核通过"、"审核不通过".
		params.put("housePriceId", this.ebkHousePrice.getHousePriceId());
		if (StringUtils.isEmpty(this.ebkHousePrice.getStatus()) || "ALL".equals(this.ebkHousePrice.getStatus())) {
			params.put("includeAduitStatus", new String[]{Constant.EBK_SUGGEST_AUDIT_STATUS.PASSED_AUDIT.name(),Constant.EBK_SUGGEST_AUDIT_STATUS.UNPASSED_AUDIT.name()});
		} else {
			params.put("status", this.ebkHousePrice.getStatus());
		}
		// 酒店名称.
		if (this.ebkHousePrice.getSupplierId()!=null ) {
			params.put("supplierId", this.ebkHousePrice.getSupplierId());
		}
		//申请主题.
		params.put("subject", this.ebkHousePrice.getSubject() == null ? "" : this.ebkHousePrice.getSubject().trim());
		//审核人.
		params.put("confirmUser", this.ebkHousePrice.getConfirmUser() == null ? "" : this.ebkHousePrice.getConfirmUser().trim());
		//审核时间.
		params.put("confirmTimeBegin", this.confirmTimeBegin);
		params.put("confirmTimeEnd", this.confirmTimeEnd);
		//提交人.
		params.put("submitUser", this.ebkHousePrice.getSubmitUser() == null ? "" : this.ebkHousePrice.getSubmitUser().trim());
		//提交时间.
		params.put("createTimeBegin", this.createTimeBegin);
		params.put("createTimeEnd", this.createTimeEnd);
		params.put("productType", Constant.PRODUCT_TYPE.HOTEL.name());
		params.put("applyType", Constant.EBK_HOUSE_PRICE_APPLY_TYPE.PRICE_APPLY.name());
		this.ebkHousePricePage.setTotalResultSize(this.ebkHousePriceService.countEbkHousePriceListByExample(params));
		ebkHousePricePage.buildUrl(getRequest());
		ebkHousePricePage.setCurrentPage(this.page);
		params.put("start", ebkHousePricePage.getStartRows());
		params.put("end", ebkHousePricePage.getEndRows());
		params.put("orderby", "CREATE_TIME");
		params.put("order", "DESC");
		this.ebkHousePriceList = this.ebkHousePriceService.findEbkHousePriceListByExample(params);
		this.ebkHousePricePage.setItems(this.ebkHousePriceList);
		return "ebkHousePriceList";
	}
	/**
	 * 已审核(审核通过、审核不通过)房价变价列表页面.
	 * @return
	 */
	@Action("ebkRoutePriceList")
	public String ebkRoutePriceList() {
		this.initSuggestAuditStatusList(true);
		Map<String,Object> params = new HashMap<String,Object>();
		//申请状态查询条件:为空或全部时查询"审核通过"、"审核不通过".
		params.put("housePriceId", this.ebkHousePrice.getHousePriceId());
		if (StringUtils.isEmpty(this.ebkHousePrice.getStatus()) || "ALL".equals(this.ebkHousePrice.getStatus())) {
			params.put("includeAduitStatus", new String[]{Constant.EBK_SUGGEST_AUDIT_STATUS.PASSED_AUDIT.name(),Constant.EBK_SUGGEST_AUDIT_STATUS.UNPASSED_AUDIT.name()});
		} else {
			params.put("status", this.ebkHousePrice.getStatus());
		}
		// 酒店名称.
		if (this.ebkHousePrice.getSupplierId()!=null ) {
			params.put("supplierId", this.ebkHousePrice.getSupplierId());
		}
		//申请主题.
		params.put("subject", this.ebkHousePrice.getSubject() == null ? "" : this.ebkHousePrice.getSubject().trim());
		//审核人.
		params.put("confirmUser", this.ebkHousePrice.getConfirmUser() == null ? "" : this.ebkHousePrice.getConfirmUser().trim());
		//审核时间.
		params.put("confirmTimeBegin", this.confirmTimeBegin);
		params.put("confirmTimeEnd", this.confirmTimeEnd);
		//提交人.
		params.put("submitUser", this.ebkHousePrice.getSubmitUser() == null ? "" : this.ebkHousePrice.getSubmitUser().trim());
		//提交时间.
		params.put("createTimeBegin", this.createTimeBegin);
		params.put("createTimeEnd", this.createTimeEnd);
		//采购产品类型为线路
		// 采购产品名称
		if (this.ebkHousePrice.getProductName()!=null&&!StringUtils.isEmpty(this.ebkHousePrice.getProductName())) {
			params.put("productName", this.ebkHousePrice.getProductName().trim());
		}
		params.put("productType", Constant.PRODUCT_TYPE.ROUTE.name());
		params.put("applyType", Constant.EBK_HOUSE_PRICE_APPLY_TYPE.PRICE_APPLY.name());
		this.ebkHousePricePage.setTotalResultSize(this.ebkHousePriceService.countEbkHousePriceListByExample(params));
		ebkHousePricePage.buildUrl(getRequest());
		ebkHousePricePage.setCurrentPage(this.page);
		params.put("start", ebkHousePricePage.getStartRows());
		params.put("end", ebkHousePricePage.getEndRows());
		params.put("orderby", "CREATE_TIME");
		params.put("order", "DESC");
		this.ebkHousePriceList = this.ebkHousePriceService.findEbkHousePriceListByExample(params);
		this.ebkHousePricePage.setItems(this.ebkHousePriceList);
		return "ebkRoutePriceList";
	}
	/**
	 * 
	 * @param containAll 是否包含"全部"条目项.
	 */
	private void initSuggestAuditStatusList(boolean containAll) {
		this.suggestAuditStatusList.clear();
		if (containAll) {
			CodeItem all = new CodeItem("","全部");
			this.suggestAuditStatusList.add(all);
		}
 		this.suggestAuditStatusList.add( new CodeItem(Constant.EBK_SUGGEST_AUDIT_STATUS.PASSED_AUDIT.name(),Constant.EBK_SUGGEST_AUDIT_STATUS.PASSED_AUDIT.getCnName()));
 		this.suggestAuditStatusList.add(new CodeItem(Constant.EBK_SUGGEST_AUDIT_STATUS.UNPASSED_AUDIT.name(),Constant.EBK_SUGGEST_AUDIT_STATUS.UNPASSED_AUDIT.getCnName()));
	}
	
	/**
	 * 房价变价详情页面,查看房价变价的详细信息,对于待审核状态的数据可以进行审核操作,对于已审核的信息则仅查看信息.
	 * @return
	 */
	@Action("ebkHousePriceApplyDetail")
	public String ebkHousePriceApplyDetail() {
		this.initSuggestAuditStatusList(false);
		this.ebkHousePrice = this.ebkHousePriceService.selectByPrimaryKey(this.ebkHousePrice.getHousePriceId());
		SupSupplier supSupplier = this.supplierService.getSupplier(this.ebkHousePrice.getSupplierId());
		this.ebkHousePrice.setSupSupplier(supSupplier);
		String metaProductBranchName = this.metaProductBranchService.getMetaBranch(this.ebkHousePrice.getMetaBranchId()).getBranchName();
		this.ebkHousePrice.setMetaProductBranchName(metaProductBranchName);
		String metaProductName = this.metaProductService.getMetaProduct(this.ebkHousePrice.getMetaProductId()).getProductName();
		this.ebkHousePrice.setProductName(metaProductName);
		return "ebkHousePriceApplyDetail";
	}
	
	/**
	 * 进行审核动作,确认审核通过或审核不通过.
	 */
	@Action("confirmAuditStatus")
	public void confirmAuditStatus() {
		Map<String, Object> param = new HashMap<String, Object>();
		Long housePriceId=Long.valueOf(super.getRequestParameter("housePriceId"));
		String auditedStatus=super.getRequestParameter("auditedStatus");
		EbkHousePrice ehp = ebkHousePriceService.selectByPrimaryKey(housePriceId);
		if (ehp == null) {
			param.put("success", false);
			param.put("errorMessage", "更新失败!");
			this.sendAjaxResultByJson(JSONObject.fromObject(param).toString());
			return;
		}
		if(!ehp.getStatus().equals(Constant.EBK_SUGGEST_AUDIT_STATUS.WAITING_FOR_AUDIT.name())) {
			param.put("success", false);
			param.put("errorMessage", "更新失败!");
			this.sendAjaxResultByJson(JSONObject.fromObject(param).toString());
			return;
		}
		if(Constant.EBK_SUGGEST_AUDIT_STATUS.PASSED_AUDIT.name().equalsIgnoreCase(auditedStatus)) {
			if(timePriceBean == null) {
				param.put("success", false);
				param.put("errorMessage", "提前预定小时数、退改策略不能为空!");
				this.sendAjaxResultByJson(JSONObject.fromObject(param).toString());
				return;
			}
		}
		//ehp.setHousePriceId(housePriceId);
		ehp.setStatus(auditedStatus);
		ehp.setConfirmTime(new Date());
		ehp.setConfirmUser(super.getSessionUserName());
		try {
			if (ehp.getStatus().equalsIgnoreCase(Constant.EBK_SUGGEST_AUDIT_STATUS.PASSED_AUDIT.name())) {
				this.updateTimePrice(ehp.getHousePriceId(), timePriceBean);
			}
			this.ebkHousePriceService.updateByPrimaryKey(ehp);
			if (isNotSwitch()) {
				log.info("update vst updateEbkSuperApply , applyId:"+ehp.getHousePriceId()+",auditStatus: "+auditedStatus);
				Integer resultHandle=ebkSuperClientService.updateEbkSuperApply(ehp).getReturnContent();
				if (resultHandle==0) {
					log.error("Call vst RPC service 'updateEbkSuperApply' error:  操作失败！");
				}else if(resultHandle==1){
					log.info("Call vst RPC service 'updateEbkSuperApply'  success.");
				}
				/**
				if(resultHandle.isSuccess()){
				log.info("Call vst RPC service 'updateEbkSuperApply' success.");z
				}else{
					log.error("Call vst RPC service 'updateEbkSuperApply' error:"+resultHandle.getMsg());
				}*/
			}
			param.put("success", true);
			param.put("errorMessage", "无");
		} catch (Exception ioe) {
			param.put("success", false);
			param.put("errorMessage", "更新失败!" + ioe.getMessage());
 		}finally{
			this.sendAjaxResultByJson(JSONObject.fromObject(param).toString());
		}
	}
	
	
	public boolean isNotSwitch(){
		Boolean isCallPRC=false;
		String control = Constant.getInstance().getProperty("vst.ebkSuperClientService");
		log.info("vst RPC 'ebkSuperClientService' service call cfg:"+control);
		if (StringUtils.isNotBlank(control)) {
			isCallPRC=Boolean.valueOf(control);
		}
		return isCallPRC;
	}
	
	/**
	 * 更新时间价格表.
	 * @param housePriceId
	 */
	private void updateTimePrice(Long housePriceId, TimePrice timePriceBean) {
		EbkHousePrice ehp = this.ebkHousePriceService.selectByPrimaryKey(housePriceId);
		TimePrice tp = new TimePrice();
 		tp.setMetaBranchId(ehp.getMetaBranchId());
		tp.setBeginDate(ehp.getStartDate());
		tp.setEndDate(ehp.getEndDate());
		if (StringUtils.isNotEmpty(ehp.getApplyWeek())) {
			tp.setWeekOpen("true");
			String[] applyWeekArray = ehp.getApplyWeek().split(",");
			for (String s : applyWeekArray) {
				if ("一".equals(s.trim())) {
					tp.setMonday("true");
				} else if ("二".equals(s.trim())) {
					tp.setTuesday("true");
				} else if ("三".equals(s.trim())) {
					tp.setWednesday("true");
				} else if ("四".equals(s.trim())) {
					tp.setThursday("true");
				} else if ("五".equals(s.trim())) {
					tp.setFriday("true");
				} else if ("六".equals(s.trim())) {
					tp.setSaturday("true");
				} else if ("七".equals(s.trim())) {
					tp.setSunday("true");
				}
			}
		}
		tp.setBreakfastCount(ehp.getBreakfastCount());
		tp.setSettlementPrice(ehp.getSettlementPrice());
		tp.setMarketPrice(ehp.getMarketPrice());
		tp.setSuggestPrice(ehp.getSuggestPrice());
		//不修改资源状态
		tp.setResourceConfirm(null);
		tp.setDayStock(-2);
		tp.setOverSale(null);
		tp.setClose("false");
		tp.setZeroStockHour(null);
		tp.setAheadHourFloat(timePriceBean.getAheadHourFloat());
		String cancelStrategy = timePriceBean.getCancelStrategy();
		tp.setCancelStrategy(cancelStrategy);
		tp.setCancelHourFloat(timePriceBean.getCancelHourFloat());
		if(StringUtils.isNotEmpty(cancelStrategy) && !timePriceBean.isAble()) {
			tp.setCancelHour(7*31*24*60l);
		}
		String resultStr = productProxy.saveOrUpdateMetaTimePrice("op3", tp, this.getSessionUserName());
		if(resultStr != null) {
			throw new RuntimeException(resultStr);
		}
	}
	
	public EbkHousePrice getEbkHousePrice() {
		return ebkHousePrice;
	}

	public void setEbkHousePrice(EbkHousePrice ebkHousePrice) {
		this.ebkHousePrice = ebkHousePrice;
	}
	public void setEbkHousePriceService(EbkHousePriceService ebkHousePriceService) {
		this.ebkHousePriceService = ebkHousePriceService;
	}

	public Long getPage() {
			return page;
		}

		public void setPage(Long page) {
			this.page = page;
		}

		public Page<EbkHousePrice> getEbkHousePricePage() {
			return ebkHousePricePage;
		}

		public void setEbkHousePricePage(Page<EbkHousePrice> ebkHousePricePage) {
			this.ebkHousePricePage = ebkHousePricePage;
		}

		public List<EbkHousePrice> getEbkHousePriceList() {
			return ebkHousePriceList;
		}

		public void setEbkHousePriceList(List<EbkHousePrice> ebkHousePriceList) {
			this.ebkHousePriceList = ebkHousePriceList;
		}

		public List<CodeItem> getSuggestAuditStatusList() {
			return suggestAuditStatusList;
		}

		public void setSuggestAuditStatusList(List<CodeItem> suggestAuditStatusList) {
			this.suggestAuditStatusList = suggestAuditStatusList;
		}

		 
		public void setSupplierService(SupplierService supplierService) {
			this.supplierService = supplierService;
		}

		public CalendarModel getCalendarModel() {
			return calendarModel;
		}

		public void setCalendarModel(CalendarModel calendarModel) {
			this.calendarModel = calendarModel;
		}

		public MetaProductService getMetaProductService() {
			return metaProductService;
		}

		public void setMetaProductService(MetaProductService metaProductService) {
			this.metaProductService = metaProductService;
		}

		public void setMetaProductBranchService(
				MetaProductBranchService metaProductBranchService) {
			this.metaProductBranchService = metaProductBranchService;
		}

		public void setProductProxy(ProductService productProxy) {
			this.productProxy = productProxy;
		}

		public String getCreateTimeBegin() {
			return createTimeBegin;
		}

		public void setCreateTimeBegin(String createTimeBegin) {
			this.createTimeBegin = createTimeBegin;
		}

		public String getCreateTimeEnd() {
			return createTimeEnd;
		}

		public void setCreateTimeEnd(String createTimeEnd) {
			this.createTimeEnd = createTimeEnd;
		}

		public String getConfirmTimeBegin() {
			return confirmTimeBegin;
		}

		public void setConfirmTimeBegin(String confirmTimeBegin) {
			this.confirmTimeBegin = confirmTimeBegin;
		}

		public String getConfirmTimeEnd() {
			return confirmTimeEnd;
		}

		public void setConfirmTimeEnd(String confirmTimeEnd) {
			this.confirmTimeEnd = confirmTimeEnd;
		}

		public TimePrice getTimePriceBean() {
			return timePriceBean;
		}

		public void setTimePriceBean(TimePrice timePriceBean) {
			this.timePriceBean = timePriceBean;
		}
		
		public void setEbkSuperClientService(EbkSuperClientService ebkSuperClientService) {
			this.ebkSuperClientService = ebkSuperClientService;
		}
}
