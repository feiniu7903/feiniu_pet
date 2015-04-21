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
import com.lvmama.comm.bee.vo.EbkDayStockDetail;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
@Results(value={
		@Result(name="ebkRouteStockApplyList",location="/WEB-INF/pages/back/ebk/ebkRouteStockApplyList.jsp"),
		@Result(name="ebkRouteStockApplyDetail",location="/WEB-INF/pages/back/ebk/ebkRouteStockApplyDetail.jsp"),
		@Result(name="ebkRouteStockList",location="/WEB-INF/pages/back/ebk/ebkRouteStockList.jsp")
})				  
@Namespace("/ebooking/routestock")
public class EbkRouteStockAction extends BackBaseAction {
	private static final long serialVersionUID = -3926743297676568212L;
	private EbkHousePriceService ebkHousePriceService;
	
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
	private Long housePriceId;

	/**
	 * 待审核线路库存变更列表页面.
	 * @return
	 */
	@Action("ebkRouteStockApplyList")
	public String ebkRouteStockApplyList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status",
				Constant.EBK_SUGGEST_AUDIT_STATUS.WAITING_FOR_AUDIT.name());
		if ( this.ebkHousePrice.getHousePriceId()!=null) {
			params.put("housePriceId", this.ebkHousePrice.getHousePriceId());
		}
		// 提交时间开始.
		if (this.createTimeBegin!=null&&!StringUtils.isEmpty(this.createTimeBegin)) {
			params.put("createTimeBegin", this.createTimeBegin);
		}
		//提交时间结束
		if (this.createTimeEnd!=null&&!StringUtils.isEmpty(this.createTimeEnd)) {
			params.put("createTimeEnd", this.createTimeEnd);
		}
		// 提交人.
		if (this.ebkHousePrice.getSubmitUser()!=null&&!StringUtils.isEmpty(this.ebkHousePrice.getSubmitUser())) {
			params.put("submitUser", this.ebkHousePrice.getSubmitUser().trim());
		}
		// 供应商名称.
		if (this.ebkHousePrice.getSupplierId()!=null ) {
			params.put("supplierId", this.ebkHousePrice.getSupplierId());
		}
		params.put("productType", Constant.PRODUCT_TYPE.ROUTE.name());
		params.put("applyType", Constant.EBK_HOUSE_PRICE_APPLY_TYPE.STOCK_APPLY.name());
		this.ebkHousePricePage.setTotalResultSize(this.ebkHousePriceService.countEbkHousePriceListByExample(params));
		ebkHousePricePage.buildUrl(getRequest());
		ebkHousePricePage.setCurrentPage(this.page);
		params.put("start", ebkHousePricePage.getStartRows());
		params.put("end", ebkHousePricePage.getEndRows());
		params.put("orderby", "CREATE_TIME");
		params.put("order", "DESC");
		this.ebkHousePriceList = this.ebkHousePriceService.findEbkHousePriceListByExample(params);
		this.ebkHousePricePage.setItems(this.ebkHousePriceList);
		return "ebkRouteStockApplyList";
	}
	/**
	 * 房价变价详情页面,查看房价变价的详细信息,对于待审核状态的数据可以进行审核操作,对于已审核的信息则仅查看信息.
	 * @return
	 */
	@Action("ebkRouteStockApplyDetail")
	public String ebkRouteStockApplyDetail() {
		this.initSuggestAuditStatusList(false);
		this.ebkHousePrice = this.ebkHousePriceService.selectByPrimaryKey(this.ebkHousePrice.getHousePriceId());
		SupSupplier supSupplier = this.supplierService.getSupplier(this.ebkHousePrice.getSupplierId());
		this.ebkHousePrice.setSupSupplier(supSupplier);
		String metaProductBranchName = this.metaProductBranchService.getMetaBranch(this.ebkHousePrice.getMetaBranchId()).getBranchName();
		this.ebkHousePrice.setMetaProductBranchName(metaProductBranchName);
		String metaProductName = this.metaProductService.getMetaProduct(this.ebkHousePrice.getMetaProductId()).getProductName();
		this.ebkHousePrice.setProductName(metaProductName);
		return "ebkRouteStockApplyDetail";
	}
	/**
	 * 已审核(审核通过、审核不通过)房价变价列表页面.
	 * @return
	 */
	@Action("ebkRouteStockList")
	public String ebkRouteStockList() {
		this.initSuggestAuditStatusList(true);
		Map<String,Object> params = new HashMap<String,Object>();
		//申请状态查询条件:为空或全部时查询"审核通过"、"审核不通过".
		if (StringUtils.isEmpty(this.ebkHousePrice.getStatus()) || "ALL".equals(this.ebkHousePrice.getStatus())) {
			params.put("includeAduitStatus", new String[]{Constant.EBK_SUGGEST_AUDIT_STATUS.PASSED_AUDIT.name(),Constant.EBK_SUGGEST_AUDIT_STATUS.UNPASSED_AUDIT.name()});
		} else {
			params.put("status", this.ebkHousePrice.getStatus());
		}
		//申请单号
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
		// 提交人.
		if (this.ebkHousePrice.getSubmitUser()!=null&&!StringUtils.isEmpty(this.ebkHousePrice.getSubmitUser())) {
			params.put("submitUser", this.ebkHousePrice.getSubmitUser().trim());
		}
		// 供应商名称.
		if (this.ebkHousePrice.getSupplierId()!=null ) {
			params.put("supplierId", this.ebkHousePrice.getSupplierId());
		}
		params.put("productType", Constant.PRODUCT_TYPE.ROUTE.name());
		params.put("applyType", Constant.EBK_HOUSE_PRICE_APPLY_TYPE.STOCK_APPLY.name());
		this.ebkHousePricePage.setTotalResultSize(this.ebkHousePriceService.countEbkHousePriceListByExample(params));
		ebkHousePricePage.buildUrl(getRequest());
		ebkHousePricePage.setCurrentPage(this.page);
		params.put("start", ebkHousePricePage.getStartRows());
		params.put("end", ebkHousePricePage.getEndRows());
		params.put("orderby", "CREATE_TIME");
		params.put("order", "DESC");
		this.ebkHousePriceList = this.ebkHousePriceService.findEbkHousePriceListByExample(params);
		this.ebkHousePricePage.setItems(this.ebkHousePriceList);
		return "ebkRouteStockList";
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
	 * 进行审核动作,确认审核通过或审核不通过.
	 */
	@Action("confirmAuditStatus")
	public void confirmAuditStatus() {
		Map<String, Object> param = new HashMap<String, Object>();
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
		ehp.setStatus(auditedStatus);
		ehp.setConfirmTime(new Date());
		ehp.setConfirmUser(super.getSessionUserName());
		try {
			if (ehp.getStatus().equalsIgnoreCase(Constant.EBK_SUGGEST_AUDIT_STATUS.PASSED_AUDIT.name())) {
				this.updateRouteStockTimePrice(ehp);
			}
			this.ebkHousePriceService.updateByPrimaryKey(ehp);
			param.put("success", true);
			param.put("errorMessage", "无");
		} catch (Exception ioe) {
			param.put("success", false);
			param.put("errorMessage", "更新失败!" + ioe.getMessage());
 		}finally{
			this.sendAjaxResultByJson(JSONObject.fromObject(param).toString());
		}
	}
	/**
	 * 更新时间价格表.
	 * 
	 * @param housePriceId
	 */
	private void updateRouteStockTimePrice(EbkHousePrice ehp) {
		TimePrice tp = new TimePrice();
 		tp.setMetaBranchId(ehp.getMetaBranchId());
		tp.setBeginDate(ehp.getStartDate());
		tp.setEndDate(ehp.getEndDate());
		//是否关班
		//若关班则库存清零，不可超卖;否则正常流程
		if ("true".equalsIgnoreCase(ehp.getIsStockZero())) {
			tp.setOverSale("false");
			tp.setDayStock(0);
 		}else {//库存不清零
 			tp.setOverSale(ehp.getIsOverSale());
 			//设定日库存
 			if(ehp.getStockAddOrMinus()<0){
 				throw new RuntimeException("增减库存不能为负数");
 			}
			tp.setDayStock(ehp.getStockAddOrMinus());
 			tp.setIsAddDayStock(ehp.getIsAddDayStock());
 		}
		//不修改资源状态
		if(tp.getDayStock() >= 0) {
			tp.setResourceConfirm("false");
		} else {
			tp.setResourceConfirm(null);
		}
		tp.setClose("false");
		String result = productProxy.saveOrUpdateMetaTimePrice("op2", tp, ehp.getSubmitUser());
		if(result != null) {
			throw new RuntimeException(result);
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
		public Long getHousePriceId() {
			return housePriceId;
		}
		public void setHousePriceId(Long housePriceId) {
			this.housePriceId = housePriceId;
		}
		
}
