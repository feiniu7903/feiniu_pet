package com.lvmama.bee.web.ebooking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkHousePrice;
import com.lvmama.comm.bee.po.ebooking.EbkHouseStatus;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.ebooking.EbkHousePriceService;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProductService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.bee.vo.EbkDayStockDetail;
import com.lvmama.comm.bee.vo.MetaBranchRelateProdBranch;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.work.WorkGroup;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * EBooking线路库存维护.
 * 
 */
@Results(value = {
		@Result(name = "maintainRouteStockStatus", location = "/WEB-INF/pages/ebooking/routeStock/maintainRouteStockStatus.jsp"),
		@Result(name = "searchRouteStockTimePriceTable", location = "/WEB-INF/pages/ebooking/routeStock/routeStockTimePriceTable.jsp"),
		@Result(name = "submitedRouteStockApply", location = "/WEB-INF/pages/ebooking/routeStock/submitedRouteStockApply.jsp"),
		@Result(name = "toSubmitedRouteStockApply", location = "submitedRouteStockApply.do", type="redirect")
})
public class EbkRouteStockStatusAction extends EbkBaseAction {

	private static final long serialVersionUID = -3926743297676568212L;
	/** 
	 * 产品的时间价格表
	 * <br/>每个产品存放2月的时间价格表
	 * <br/>List<CalendarModel>中，0位为第一月，1位为第二月
	 */
	private List<Map<MetaProductBranch, List<CalendarModel>>> branchCalendarModel;
	private EbkDayStockDetail ebkDayStockDetail = new EbkDayStockDetail();
	private MetaProductService metaProductService;
	private MetaProductBranchService metaProductBranchService;
	private EbkHousePrice ebkHousePrice = new EbkHousePrice();
	private List<EbkHousePrice> ebkHousePriceList = new ArrayList<EbkHousePrice>();
	private EbkHousePriceService ebkHousePriceService;
	private BCertificateTargetService bCertificateTargetService;
	private String metaProductName;
	private Date startDate;
	private String addResult = "";
	private Date createTimeBegin;
	private Date createTimeEnd;
	private Date beginDate;
	private Date endDate;
	private ProductService productProxy;
	private List<MetaProductBranch> productBranchList;
	private List<EbkDayStockDetail> ebkDayStockDetailList;
	private Page<EbkHousePrice> ebkHousePricePage = new Page<EbkHousePrice>();
	private Long[] metaProductBranchId;
	private boolean isSearch=false;
	private List<CodeItem> suggestAuditStatusList = new ArrayList<CodeItem>();
	private Long housePriceId;
	private SupBCertificateTarget supBCertificateTarget;
	private ComLogService comLogService;


	/**
	 * 打开线路库存查询页面.
	 * 
	 * @return
	 */
	@Action("/ebooking/routeStock/maintainRouteStockStatus")
	public String maintainRouteStockStatus() {
		if(isSearch){
			Page<EbkDayStockDetail> ebkDayStockDetailPage = new Page<EbkDayStockDetail>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("supplierId", this.getCurrentSupplierId());
			params.put("productType", Constant.PRODUCT_TYPE.ROUTE.name());
			if(!StringUtil.isEmptyString(ebkDayStockDetail.getMetaProductName())){
				params.put("metaProductName", ebkDayStockDetail.getMetaProductName());
			}
			if(!StringUtil.isEmptyString(DateUtil.formatDate(ebkDayStockDetail.getSpecDate(), "yyyy-MM-dd"))){
				params.put("specDate", DateUtil.formatDate(ebkDayStockDetail.getSpecDate(), "yyyy-MM-dd"));
			}
			ebkDayStockDetailPage.buildUrl(getRequest());
			ebkDayStockDetailPage.setTotalResultSize(metaProductBranchService.getEbkDayStockDetailPageCount(params));
			ebkDayStockDetailPage.setCurrentPage(super.page);
			params.put("beginIndex", (int)ebkDayStockDetailPage.getStartRows());
			params.put("endIndex", (int)ebkDayStockDetailPage.getEndRows());
			//不定期产品不允许在EBK中做库存修改
			params.put("isNotAperiodic", Constant.TRUE_FALSE.TRUE.getAttr1());
			this.ebkDayStockDetailList = metaProductBranchService.getEbkDayStockDetail(params);
			ebkDayStockDetailPage.setItems(ebkDayStockDetailList);
			setRequestAttribute("ebkDayStockDetailPage",ebkDayStockDetailPage );
		}
		return "maintainRouteStockStatus";
	}
	// 查询时间价格表.
	private List<CalendarModel> searchTimePrice(Long metaProductBranchId,Date beginDate) {
		List<CalendarModel> mList = new ArrayList<CalendarModel>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),1);
		mList.add(this.createCalendarModel(metaProductBranchId, calendar));
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
		mList.add(this.createCalendarModel(metaProductBranchId,	calendar));
		return mList;
	}

	// 获取指定的采购产品类别和月份的时间价格表.
	private CalendarModel createCalendarModel(Long metaBranchId,
			Calendar calendar) {
		// 指定月份的开始日期,当月的第一天.
		Date beginDate = calendar.getTime();
		// 指定月份的结束日期,当月的最后一天.
		Date endDate = DateUtil.dsDay_Date(beginDate,
				DateUtil.getDaysOfmonth(calendar.get(Calendar.MONTH)));
		Date fristBeginDate = this.firstBeginDate(beginDate);
		CalendarModel calendarModel = this.metaProductService
				.getMetaTimePriceByMetaProductId(metaBranchId,
						DateUtil.getDayStart(fristBeginDate), endDate);
		calendarModel.setYear(calendar.get(Calendar.YEAR));
		calendarModel.setMonth(calendar.get(Calendar.MONTH));
		return calendarModel;
	}

	// 指定日期向前推的星期日的日期. 如beginDate为2012-12-1日,则返回2012-11-25.
	private Date firstBeginDate(Date beginDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
		int day = -cal.get(Calendar.DAY_OF_WEEK) + 1;
		return DateUtils.addDays(cal.getTime(), day);
	}
	/**
	 * 提交线路库存时间价格表修改页面.
	 * 
	 * @return
	 */
	@Action("/ebooking/routeStock/submitRouteStockTimePriceStatus")
	public void submitRouteStockTimePriceStatus() {
		try {
			this.updateRouteStockTimePrice(this.ebkDayStockDetail,this.beginDate,this.endDate);
		} catch (RuntimeException e) {
			this.addResult = e.getMessage();
			e.printStackTrace();
		}
		if ("".equals(this.addResult)) {
			this.addResult = "success";
		}
		 this.sendAjaxMsg(this.addResult);
 	}
	/**
	 * 线路库存时间价格表修改页面.
	 * 
	 * @return
	 */
	@Action("/ebooking/routeStock/searchRouteStockTimePriceTable")
	public String searchRouteStockTimePriceTable() {
		//重新查询页面
		if(ebkDayStockDetail.getMetaBranchId() != null) {
			branchCalendarModel = new ArrayList<Map<MetaProductBranch,List<CalendarModel>>>();
			Map<MetaProductBranch,List<CalendarModel>> metaM = new HashMap<MetaProductBranch, List<CalendarModel>>();
			MetaProductBranch mb = metaProductBranchService.getMetaBranch(ebkDayStockDetail.getMetaBranchId());
			metaM.put(mb, searchTimePrice(ebkDayStockDetail.getMetaBranchId(),ebkDayStockDetail.getSpecDate()));
			branchCalendarModel.add(metaM);
			MetaProductBranch metaProductBranch = this.metaProductBranchService.getMetaBranch(this.ebkDayStockDetail.getMetaBranchId());
			supBCertificateTarget = this.bCertificateTargetService.
					selectSuperMetaBCertificateByMetaProductId(metaProductBranch.getMetaProductId()).get(0);
		}
        return  "searchRouteStockTimePriceTable";
 	}
	/**
	 * 添加待审核状态的减少库存申请信息.
	 * 
	 * @return
	 */
	@Action("/ebooking/routeStock/reduceRouteStockApprove")
	public void reduceRouteStockApprove() {
		if(this.checkIfCanReduceDayStock()){
			this.ebkHousePrice.setSubmitUser(super.getSessionUserName());
			this.ebkHousePrice.setSupplierId(super.getSessionUser().getSupplierId());
			this.ebkHousePrice.setCreateTime(new Date());
			this.ebkHousePrice.setStartDate(beginDate);
			this.ebkHousePrice.setEndDate(endDate);
			this.ebkHousePrice.setIsStockZero(this.ebkDayStockDetail.getIsStockZero());
			this.ebkHousePrice.setIsOverSale(this.ebkDayStockDetail.getIsOverSale());
			this.ebkHousePrice.setStockAddOrMinus(this.ebkDayStockDetail.getAddOrMinusStock());
			this.ebkHousePrice.setIsAddDayStock("false");
			this.ebkHousePrice.setMetaBranchId(this.ebkDayStockDetail.getMetaBranchId());
			this.ebkHousePrice.setStatus(Constant.EBK_SUGGEST_AUDIT_STATUS.WAITING_FOR_AUDIT.name());
			this.ebkHousePrice.setProductType(Constant.PRODUCT_TYPE.ROUTE.name());
			this.ebkHousePrice.setApplyType(Constant.EBK_HOUSE_PRICE_APPLY_TYPE.STOCK_APPLY.name());
			// 初始化库存减少信息的采购产品ID属性值.
			List<MetaProduct> embList = this.metaProductService.getEbkMetaProductByBranchIds(
					Arrays.asList(this.ebkHousePrice.getMetaBranchId()));
			if (embList != null && !embList.isEmpty()) {
				this.ebkHousePrice.setMetaProductId(embList.iterator().next().getMetaProductId());
			}
			try {
				this.ebkHousePriceService.insert(this.ebkHousePrice);
				this.addResult = "SUCCESS";
				String con="["+new SimpleDateFormat("yyyy年MM月dd日").format(new Date())+
						"]您有1条待处理的线路库存修改申请单     查看(点击后快速到线路待审核库存修改里面)";
		 		metaProductBranchService.changeHousepriceSendMessage(ebkHousePrice.getMetaProductId(),con,super.getSessionUserName());
			} catch (Exception e) {
				this.addResult = "操作异常";
				e.printStackTrace();
			}
			this.sendAjaxMsg(addResult);
		}
	}
	/**
	 * 校验是否可以减少某一天的库存
	 * @author: FangWeiQuan 2013-1-7 下午1:45:19
	 * @param oldTime
	 * @param isAddDayStock 增减或原值
	 * @param stock
	 */
	private boolean checkIfCanReduceDayStock() {
		Long theZero =beginDate.getTime();
		Calendar cal = Calendar.getInstance();
		while (theZero <= endDate.getTime()) {
			cal.setTimeInMillis(theZero);
			Date specDate = new Date(theZero);		
			TimePrice timePrice = this.metaProductService.getMetaTimePriceByIdAndDate(this.ebkDayStockDetail.getMetaBranchId(), specDate);
			if (timePrice != null) {
				long stockReduce = 0;
				//减少库存
				if("false".equalsIgnoreCase(this.ebkDayStockDetail.getIsAddDayStock())) {
					stockReduce = timePrice.getDayStock() - this.ebkDayStockDetail.getAddOrMinusStock();
					if(stockReduce < 0) {
						this.sendAjaxMsg(new SimpleDateFormat("yyyy年MM月dd日").format(specDate)+"库存不足，无法减少");
						return false;
					}
				}
			}
			theZero += 86400000;
		}
		return true;
	}
	/**
	 * 已提交的变价申请页面.
	 * 
	 * @return
	 */
	@Action("/ebooking/routeStock/submitedRouteStockApply")
	public String submitedRouteStockApply() {
		this.initSuggestAuditStatusList();
		Map<String, Object> params = new HashMap<String, Object>();
		// //申请状态查询条件:为空或全部时查询"待审核"、"审核通过"、"审核不通过".
		if (StringUtils.isEmpty(this.ebkHousePrice.getStatus())
				|| "ALL".equals(this.ebkHousePrice.getStatus())) {
			params.put("includeAduitStatus", new String[] {
					Constant.EBK_SUGGEST_AUDIT_STATUS.WAITING_FOR_AUDIT.name(),
					Constant.EBK_SUGGEST_AUDIT_STATUS.PASSED_AUDIT.name(),
					Constant.EBK_SUGGEST_AUDIT_STATUS.UNPASSED_AUDIT.name() });
			} else {
			params.put("status", this.ebkHousePrice.getStatus());
		}
		if ( this.ebkHousePrice.getHousePriceId()!=null) {
			params.put("housePriceId", this.ebkHousePrice.getHousePriceId());
		}
		// 提交时间.
		if (this.createTimeBegin!=null) {
			params.put("createTimeBegin", DateUtil.formatDate(this.createTimeBegin, "yyyy-MM-dd"));
		}
		if (this.createTimeEnd!=null) {
			params.put("createTimeEnd", DateUtil.formatDate(this.createTimeEnd, "yyyy-MM-dd"));
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
		return "submitedRouteStockApply";
	}
	@Action("/ebooking/routeStock/routeStockApplyDetail")
	public String routeStockApplyDetail(){
		this.ebkHousePrice = ebkHousePriceService.selectByPrimaryKey(housePriceId);
		List<Long> metaBranchIdList = new ArrayList<Long>();
		metaBranchIdList.add(this.ebkHousePrice.getMetaBranchId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("metaBranchIds", metaBranchIdList);
		List<MetaProductBranch> embList = metaProductBranchService.getEbkMetaBranchParam(params);
		if(embList != null && embList.size() > 0){
			this.ebkHousePrice.setMetaProductBranchName(embList.get(0).getBranchName());
		}
		String metaProductName = this.metaProductService.getMetaProduct(this.ebkHousePrice.getMetaProductId()).getProductName();
		this.ebkHousePrice.setProductName(metaProductName);
		return "routeStockApplyDetail";
	}
	/**
	 * 
	 * @param containAll 是否包含"全部"条目项.
	 */
	private void initSuggestAuditStatusList( ) {
		this.suggestAuditStatusList.clear();
		CodeItem all = new CodeItem("","全部");
		this.suggestAuditStatusList.add(all);
 		this.suggestAuditStatusList.add( new CodeItem(Constant.EBK_SUGGEST_AUDIT_STATUS.WAITING_FOR_AUDIT.name(),Constant.EBK_SUGGEST_AUDIT_STATUS.WAITING_FOR_AUDIT.getCnName()));
 		this.suggestAuditStatusList.add( new CodeItem(Constant.EBK_SUGGEST_AUDIT_STATUS.PASSED_AUDIT.name(),Constant.EBK_SUGGEST_AUDIT_STATUS.PASSED_AUDIT.getCnName()));
 		this.suggestAuditStatusList.add(new CodeItem(Constant.EBK_SUGGEST_AUDIT_STATUS.UNPASSED_AUDIT.name(),Constant.EBK_SUGGEST_AUDIT_STATUS.UNPASSED_AUDIT.getCnName()));
	}
	/**
	 * 线路库存维护除减少库存外更新时间价格表.
	 * @param housePriceId
	 */
	private void updateRouteStockTimePrice(EbkDayStockDetail eds,Date beginDate,Date endDate) {
		TimePrice tp = new TimePrice();
 		tp.setMetaBranchId(eds.getMetaBranchId());
		tp.setBeginDate(beginDate);
		tp.setEndDate(endDate);
		//是否关班
		//若关班则库存清零，不可超卖;否则正常流程
		if ("true".equals(eds.getIsStockZero())) {
			Map<String,Object> param = new HashMap<String,Object>();
			List<MetaBranchRelateProdBranch> relativeProdProductAndBranchList;
			//获取该采购类别关联的销售产品，若数量大于1，则不可禁售
			relativeProdProductAndBranchList = metaProductBranchService.selectProdProductAndProdBranchByMetaBranchId(tp.getMetaBranchId());
			long num = relativeProdProductAndBranchList.size();
			if (num > 1) {
				throw new RuntimeException("该产品由于绑定多个销售产品,不可禁售,请联系驴妈妈客服修改产品设置");
			}
			param.put("metaBranchId", tp.getMetaBranchId());
			param.put("beginDate", tp.getBeginDate());
			param.put("endDate", tp.getEndDate());
			metaProductService.deleteByBeginDateAndEndDate(param);
			String content=	this.getSessionUserName() + "将" + DateUtil.getDateTime("yyyy-MM-dd", tp.getBeginDate()) +
					"与" + DateUtil.getDateTime("yyyy-MM-dd", tp.getEndDate())+ "时间内采购产品设置为关班";
			comLogService.insert(null, tp.getMetaBranchId(), tp.getTimePriceId(), this.getSessionUserName(), 
					Constant.COM_LOG_ORDER_EVENT.updateMetaTimePrice.name(), "编辑采购时间价格表",
					content, "META_TIME_PRICE");
 		}else {
 			//超卖
 			tp.setOverSale(eds.getIsOverSale());
 			//设定日库存
 			if(eds.getAddOrMinusStock()<0){
 				throw new RuntimeException("增减库存不能为负数");
 			}
 			if(!(eds.getAddOrMinusStock().equals("输入增加的数量")||eds.getAddOrMinusStock().equals("输入减少的数量"))){
 				tp.setDayStock(eds.getAddOrMinusStock());
 			}
 			tp.setIsAddDayStock(eds.getIsAddDayStock());
 			//不修改资源状态
 			if(tp.getDayStock() >= 0) {
 				tp.setResourceConfirm("false");
 			} else {
 				tp.setResourceConfirm(null);
 			}
 			tp.setClose("false");
 			String result = productProxy.saveOrUpdateMetaTimePrice("op2", tp, this.getSessionUserName());
 			if(result != null) {
 				throw new RuntimeException(result);
 			}
 		}
	}
	/**
	 * 撤消待审核的房价变更信息.
	 * 
	 * @return
	 */
	@Action("/ebooking/routeStock/cancelRouteStockApply")
	public String cancelRouteStockApply() {
		try {
			this.ebkHousePriceService.deleteEbkHousePriceByPrimaryKey(
					this.ebkHousePrice.getHousePriceId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toSubmitedRouteStockApply";
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public List<Map<MetaProductBranch, List<CalendarModel>>> getBranchCalendarModel() {
		return branchCalendarModel;
	}

	public void setBranchCalendarModel(List<Map<MetaProductBranch, List<CalendarModel>>> branchCalendarModel) {
		this.branchCalendarModel = branchCalendarModel;
	}

	public String getAddResult() {
		return addResult;
	}

	public void setAddResult(String addResult) {
		this.addResult = addResult;
	}

	public Date getCreateTimeBegin() {
		return createTimeBegin;
	}

	public void setCreateTimeBegin(Date createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public void setProductProxy(ProductService productProxy) {
		this.productProxy = productProxy;
	}

	public List<MetaProductBranch> getProductBranchList() {
		return productBranchList;
	}

	public void setProductBranchList(List<MetaProductBranch> productBranchList) {
		this.productBranchList = productBranchList;
	}

	public Long[] getMetaProductBranchId() {
		return metaProductBranchId;
	}

	public String getMetaProductName() {
		return metaProductName;
	}
	public void setMetaProductName(String metaProductName) {
		this.metaProductName = metaProductName;
	}
	public void setMetaProductBranchId(Long[] metaProductBranchId) {
		this.metaProductBranchId = metaProductBranchId;
	}
	public List<EbkDayStockDetail> getEbkDayStockDetailList() {
		return ebkDayStockDetailList;
	}
	public void setEbkDayStockDetailList(
			List<EbkDayStockDetail> ebkDayStockDetailList) {
		this.ebkDayStockDetailList = ebkDayStockDetailList;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public EbkDayStockDetail getEbkDayStockDetail() {
		return ebkDayStockDetail;
	}
	public void setEbkDayStockDetail(EbkDayStockDetail ebkDayStockDetail) {
		this.ebkDayStockDetail = ebkDayStockDetail;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean getIsSearch() {
		return isSearch;
	}

	public void setIsSearch(boolean isSearch) {
		this.isSearch = isSearch;
	}

	public EbkHousePriceService getEbkHousePriceService() {
		return ebkHousePriceService;
	}

	public void setEbkHousePriceService(EbkHousePriceService ebkHousePriceService) {
		this.ebkHousePriceService = ebkHousePriceService;
	}

	public List<CodeItem> getSuggestAuditStatusList() {
		return suggestAuditStatusList;
	}

	public void setSuggestAuditStatusList(List<CodeItem> suggestAuditStatusList) {
		this.suggestAuditStatusList = suggestAuditStatusList;
	}

	public EbkHousePrice getEbkHousePrice() {
		return ebkHousePrice;
	}

	public void setEbkHousePrice(EbkHousePrice ebkHousePrice) {
		this.ebkHousePrice = ebkHousePrice;
	}

	public List<EbkHousePrice> getEbkHousePriceList() {
		return ebkHousePriceList;
	}

	public void setEbkHousePriceList(List<EbkHousePrice> ebkHousePriceList) {
		this.ebkHousePriceList = ebkHousePriceList;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Page<EbkHousePrice> getEbkHousePricePage() {
		return ebkHousePricePage;
	}

	public void setEbkHousePricePage(Page<EbkHousePrice> ebkHousePricePage) {
		this.ebkHousePricePage = ebkHousePricePage;
	}

	public MetaProductService getMetaProductService() {
		return metaProductService;
	}

	public MetaProductBranchService getMetaProductBranchService() {
		return metaProductBranchService;
	}

	public ProductService getProductProxy() {
		return productProxy;
	}

	public Long getHousePriceId() {
		return housePriceId;
	}

	public void setHousePriceId(Long housePriceId) {
		this.housePriceId = housePriceId;
	}
	public BCertificateTargetService getbCertificateTargetService() {
		return bCertificateTargetService;
	}
	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}
	public SupBCertificateTarget getSupBCertificateTarget() {
		return supBCertificateTarget;
	}
	public void setSupBCertificateTarget(SupBCertificateTarget supBCertificateTarget) {
		this.supBCertificateTarget = supBCertificateTarget;
	}
	public ComLogService getComLogService() {
		return comLogService;
	}
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}



}
