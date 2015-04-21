package com.lvmama.bee.web.ebooking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkHousePrice;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.ebooking.EbkHousePriceService;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProductService;
import com.lvmama.comm.bee.vo.BranchNameCalendarModel;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * EBooking房价维护(商户版).
 * 
 */
@Results(value = {
		@Result(name = "changePriceSuggest", location = "/WEB-INF/pages/ebooking/houseprice/changePriceSuggest.jsp"),
		@Result(name = "searchHousePrice", location = "/WEB-INF/pages/ebooking/houseprice/searchHousePrice.jsp"),
		@Result(name = "submitedApply", location = "/WEB-INF/pages/ebooking/houseprice/submitedApply.jsp"),
		@Result(name = "toChangePriceSuggest", location = "changePriceSuggest.do", type = "redirect"),
		@Result(name = "toSubmitedApply", location = "submitedApply.do", type = "redirect"),
		@Result(name = "housePriceApplyDetail", location = "housePriceApplyDetail.jsp")
})
@Namespace("/ebooking/houseprice")
public class EbkHouseRoomPriceAction extends EbkBaseAction {
	private static final long serialVersionUID = -3926743297676568212L;
	private EbkHousePriceService ebkHousePriceService;

	private EbkHousePrice ebkHousePrice = new EbkHousePrice();
	// 分页默认为第一页.
	private Long page = 1L;
	// 分页对象.
	private Page<EbkHousePrice> ebkHousePricePage = new Page<EbkHousePrice>();
	// 待申请状态的房价变更信息数量.
	private int appliedSuggestCount;
	//
	private List<EbkHousePrice> ebkHousePriceList = new ArrayList<EbkHousePrice>();
	// 房价变更信息的id字符串表示,以","分隔.
	private String housePriceIds;
	//
	private Date createTimeBegin;
	//
	private Date createTimeEnd;
	// EBooking房价变价申请的审核状态查询条件列表. 引用Constant.EBK_SUGGEST_AUDIT_STATUS.
	private List<CodeItem> suggestAuditStatusList = new ArrayList<CodeItem>();
	// 操作结果标识:操作成功:success,操作失败:failed.
	private String addResult;

	private MetaProductBranchService metaProductBranchService;

	private MetaProductService metaProductService;

	private int yearNow = Calendar.getInstance().get(Calendar.YEAR);
	// 房价查询页面中的年份查询条件.
	private int year = Calendar.getInstance().get(Calendar.YEAR);
	// 房价查询页面中的月份查询条件.
	private int month = Calendar.getInstance().get(Calendar.MONTH);
	private List<BranchNameCalendarModel> branchNameCalendarModelList=new ArrayList<BranchNameCalendarModel>();
	private Float settlementPriceFen;
	private List<MetaProductBranch> productBranchList;
	
	private BCertificateTargetService bCertificateTargetService;
	private SupBCertificateTarget supBCertificateTarget;
	private ProductService productProxy;
	/**
	 * 变价申请页面.
	 * 
	 * @return
	 */
	@Action("changePriceSuggest")
	public String changePriceSuggest() {
		this.ebkHousePrice.setSupplierId(super.getCurrentSupplierId());
		Map<String, Object> params = new HashMap<String, Object>();
		// 审核状态--待申请状态.
		params.put("status",
				Constant.EBK_SUGGEST_AUDIT_STATUS.WAITING_FOR_APPLY.name());
		params.put("submitUser", super.getSessionUserName());
		params.put("applyType", Constant.EBK_HOUSE_PRICE_APPLY_TYPE.PRICE_APPLY.name());
		params.put("productType", Constant.PRODUCT_TYPE.ROUTE.name());
		this.appliedSuggestCount = this.ebkHousePriceService
				.countEbkHousePriceListByExample(params);
		this.ebkHousePricePage.setTotalResultSize(this.appliedSuggestCount);
		this.ebkHousePricePage.buildUrl(getRequest());
		this.ebkHousePricePage.setCurrentPage(this.page);
		ebkHousePricePage.setPageSize(this.appliedSuggestCount);
		params.put("start", ebkHousePricePage.getStartRows());
		params.put("end", ebkHousePricePage.getEndRows());
		params.put("orderby", "CREATE_TIME");
		params.put("order", "DESC");
		createTimeBegin = DateUtils.addDays(new Date(), 1);
		createTimeEnd = createTimeBegin;
		this.ebkHousePriceList = this.ebkHousePriceService.findEbkHousePriceListByExample(params);
		this.ebkHousePricePage.setItems(this.ebkHousePriceList);
		this.housePriceIds = this.housePriceIds();
		this.initMetaProductBranchName();
		productBranchList = getProductBranch();
		return "changePriceSuggest";
	}

	// 查询当前用户绑定的采购产品类别信息列表.
	private List<MetaProductBranch> getProductBranch() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
		params.put("isSearchEbookingProduct","true");
		//不定期产品不允许在EBK中做变价
		params.put("isNotAperiodic", Constant.TRUE_FALSE.TRUE.getAttr1());
		List<MetaProductBranch> result = metaProductBranchService.getEbkMetaBranch(params);
		if (result == null || result.isEmpty()) {
			return new ArrayList<MetaProductBranch>();
		}
		return result;
	}

	// 获取housePriceId的字符串格式,以","分隔.
	private String housePriceIds() {
		if ((this.ebkHousePriceList == null)
				|| (this.ebkHousePriceList.isEmpty())) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (EbkHousePrice ehp : this.ebkHousePriceList) {
			sb.append(ehp.getHousePriceId()).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 添加待申请状态的变价申请信息.
	 * 
	 * @return
	 */
	@Action("addChangePriceSuggest")
	public String addChangePriceSuggest() {
		this.ebkHousePrice.setSubmitUser(super.getSessionUserName());
		this.ebkHousePrice.setCreateTime(new Date());
		this.ebkHousePrice.setSupplierId(super.getSessionUser().getSupplierId());
		this.ebkHousePrice.setMarketPrice(PriceUtil.convertToFen(this.ebkHousePrice.getMarketPrice()));
		if(this.ebkHousePrice.getSuggestPrice()!=null){
			this.ebkHousePrice.setSuggestPrice(PriceUtil.convertToFen(this.ebkHousePrice.getSuggestPrice()));
		}
		this.ebkHousePrice.setSettlementPrice(PriceUtil.convertToFen(settlementPriceFen));
		if(StringUtils.equals(ebkHousePrice.getMemo(), "费用包含内容如有变更，请在此描述")){
			ebkHousePrice.setMemo("");
		}
		this.initMetaProductId();
		this.ebkHousePrice.setStatus(Constant.EBK_SUGGEST_AUDIT_STATUS.WAITING_FOR_APPLY.name());
		this.ebkHousePrice.setApplyType(Constant.EBK_HOUSE_PRICE_APPLY_TYPE.PRICE_APPLY.name());
		this.addResult = "success";
		try {
			this.ebkHousePriceService.insert(this.ebkHousePrice);
		} catch (Exception e) {
			this.addResult = "failed";
			e.printStackTrace();
		}

		return "toChangePriceSuggest";
	}

	/**
	 * 初始化房价变更信息的采购产品ID属性值.
	 */
	private void initMetaProductId() {
		List<MetaProduct> embList = this.metaProductService.getEbkMetaProductByBranchIds(
				Arrays.asList(this.ebkHousePrice.getMetaBranchId()));
		if (embList != null && !embList.isEmpty()) {
			this.ebkHousePrice.setMetaProductId(embList.iterator().next().getMetaProductId());
		}
	}

	/**
	 * 删除待申请的房价变更信息.
	 * 
	 * @return
	 */
	@Action("deleteChangePriceSuggest")
	public String deleteChangePriceSuggest() {
		try {
			this.ebkHousePriceService
					.deleteEbkHousePriceByPrimaryKey(this.ebkHousePrice
							.getHousePriceId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toChangePriceSuggest";
	}

	/**
	 * 若凭证式上勾选供应商变价/库存修改不审核，则可以直接修改时间价格
	 * 否则需要提交待申请状态的房价变更信息，由后台客服审核通过才行
	 * @return
	 */
	@Action("submitChangePriceSuggest")
	public void submitChangePriceSuggest() {
		String[] ids = this.housePriceIds.split(",");
		Long metaProductId=0L;
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			for (String id : ids) {
				EbkHousePrice ehp =this.ebkHousePriceService.selectByPrimaryKey(Long.valueOf(id));
				if(ehp!=null){
					supBCertificateTarget = this.bCertificateTargetService.
							getSuperMetaBCertificateByMetaProductId(ehp.getMetaProductId());
					if("false".equals(supBCertificateTarget.getPriceStockVerifyFalg())){
						TimePrice timePriceBean = new TimePrice();
						ehp.setStatus(Constant.EBK_SUGGEST_AUDIT_STATUS.PASSED_AUDIT.name());
						ehp.setConfirmTime(new Date());
						ehp.setConfirmUser(super.getSessionUserName());
						this.updateTimePrice(ehp, timePriceBean);
					}else{
						//变价单
						ehp.setHousePriceId(Long.valueOf(id));
						ehp.setStatus(Constant.EBK_SUGGEST_AUDIT_STATUS.WAITING_FOR_AUDIT
								.name());
						metaProductId=ehp.getMetaProductId();
						if(Constant.PRODUCT_TYPE.HOTEL.name().equals(ehp.getProductType())){
							String con="["+new SimpleDateFormat("yyyy年MM月dd日").format(new Date())+"]您有1条待处理的酒店" +
									"变价申请单     查看（点击后快速到待审核变价单里面）";
							metaProductBranchService.changeHousepriceSendMessage(metaProductId,con,super.getSessionUserName());
						}else if(Constant.PRODUCT_TYPE.ROUTE.name().equals(ehp.getProductType())){
							String con="["+new SimpleDateFormat("yyyy年MM月dd日").format(new Date())+"]您有1条待处理的线路" +
									"变价申请单     查看（点击后快速到待审核变价单里面）";
							metaProductBranchService.changeHousepriceSendMessage(metaProductId,con,super.getSessionUserName());
						}
					}
					this.ebkHousePriceService.updateByPrimaryKey(ehp);
				}
			}				
			param.put("success", true);
		} catch (Exception e) {
			param.put("success", false);
			param.put("errorMessage", "更新失败!" + e.getMessage());
		}finally{
			this.sendAjaxResultByJson(JSONObject.fromObject(param).toString());
		}
	}
	/**
	 * 凭证对象勾选 供应商变价/库存修改是否审核为"是",则不需要后台进行审核，前台提交直接通过就可以
	 */
	@Action("confirmAuditStatusFrontEnd")
	public void confirmAuditStatusFrontEnd() {
		TimePrice timePriceBean = new TimePrice();
		Map<String, Object> param = new HashMap<String, Object>();
		String[] ids = this.housePriceIds.split(",");
		try {
			for (String id : ids) {
				EbkHousePrice ehp =this.ebkHousePriceService.selectByPrimaryKey(Long.valueOf(id));
				ehp.setStatus(Constant.EBK_SUGGEST_AUDIT_STATUS.PASSED_AUDIT.name());
				ehp.setConfirmTime(new Date());
				ehp.setConfirmUser(super.getSessionUserName());
				this.updateTimePrice(ehp, timePriceBean);
				this.ebkHousePriceService.updateByPrimaryKey(ehp);
			}
			param.put("success", true);
			param.put("errorMessage", "无");
		} catch (Exception e) {
			param.put("success", false);
			param.put("errorMessage", "更新失败!" + e.getMessage());
		}finally{
			this.sendAjaxResultByJson(JSONObject.fromObject(param).toString());
		}
	}
	
	/**
	 * 前台更新时间价格表.
	 * @param housePriceId
	 */
	private void updateTimePrice(EbkHousePrice ehp, TimePrice timePriceBean) {
 		timePriceBean.setMetaBranchId(ehp.getMetaBranchId());
		timePriceBean.setBeginDate(ehp.getStartDate());
		timePriceBean.setEndDate(ehp.getEndDate());
		if (StringUtils.isNotEmpty(ehp.getApplyWeek())) {
			timePriceBean.setWeekOpen("true");
			String[] applyWeekArray = ehp.getApplyWeek().split(",");
			for (String s : applyWeekArray) {
				if ("一".equals(s.trim())) {
					timePriceBean.setMonday("true");
				} else if ("二".equals(s.trim())) {
					timePriceBean.setTuesday("true");
				} else if ("三".equals(s.trim())) {
					timePriceBean.setWednesday("true");
				} else if ("四".equals(s.trim())) {
					timePriceBean.setThursday("true");
				} else if ("五".equals(s.trim())) {
					timePriceBean.setFriday("true");
				} else if ("六".equals(s.trim())) {
					timePriceBean.setSaturday("true");
				} else if ("七".equals(s.trim())) {
					timePriceBean.setSunday("true");
				}
			}
		}
		timePriceBean.setBreakfastCount(ehp.getBreakfastCount());
		timePriceBean.setSettlementPrice(ehp.getSettlementPrice());
		timePriceBean.setMarketPrice(ehp.getMarketPrice());
		timePriceBean.setSuggestPrice(ehp.getSuggestPrice());
		//不修改资源状态
		timePriceBean.setResourceConfirm(null);
		timePriceBean.setDayStock(-2);
		timePriceBean.setOverSale(null);
		timePriceBean.setClose("false");
		timePriceBean.setZeroStockHour(null);
		String resultStr = productProxy.saveOrUpdateMetaTimePrice("op3", timePriceBean, this.getSessionUserName());
		if(resultStr != null) {
			throw new RuntimeException(resultStr);
		}
	}
	/**
	 * 房价查询页面.
	 * 
	 * @return
	 */
	@Action("searchHousePrice")
	public String searchHousePrice() {
		searchAllHousePrice();

		return "searchHousePrice";
	}

	// 查询所有房型
	private void searchAllHousePrice() {
		productBranchList = this.getProductBranch();
		if (this.ebkHousePrice.getMetaBranchId() == null) {
			if (productBranchList != null && !productBranchList.isEmpty()) {
				for (MetaProductBranch e : productBranchList) {
					if (e.getMetaBranchId() != null) {
						BranchNameCalendarModel branchNameCalendarModel = new BranchNameCalendarModel();
						EbkHousePrice ehPrice = new EbkHousePrice();
						ehPrice.setMetaBranchId(e.getMetaBranchId());
						ehPrice.setMetaProductBranchName(e.getBranchName());

						branchNameCalendarModel.setEbkHousePrice(ehPrice);

						Calendar calendar = Calendar.getInstance();
						calendar.set(this.year, this.month, 1);

						branchNameCalendarModel.setCalendarModel(this
								.createCalendarModel(e.getMetaBranchId(),
										calendar));
						calendar.set(Calendar.MONTH,
								calendar.get(Calendar.MONTH) + 1);
						branchNameCalendarModel.setNextCalendarModel(this
								.createCalendarModel(e.getMetaBranchId(),
										calendar));
						this.branchNameCalendarModelList
								.add(branchNameCalendarModel);
					}

				}
			}
		}else {
			//被选中的metaBranchId
			Long metaBranchId = this.ebkHousePrice.getMetaBranchId();
			if (productBranchList != null && !productBranchList.isEmpty()) {
				for (MetaProductBranch e : productBranchList) {
					if (e.getMetaBranchId() != null&&metaBranchId == null
							|| e.getMetaBranchId().equals(metaBranchId)) {
						BranchNameCalendarModel branchNameCalendarModel = new BranchNameCalendarModel();
						EbkHousePrice ehPrice = new EbkHousePrice();
						ehPrice.setMetaBranchId(e.getMetaBranchId());
						ehPrice.setMetaProductBranchName(e.getBranchName());

						branchNameCalendarModel.setEbkHousePrice(ehPrice);

						Calendar calendar = Calendar.getInstance();
						calendar.set(this.year, this.month, 1);

						branchNameCalendarModel.setCalendarModel(this
								.createCalendarModel(e.getMetaBranchId(),
										calendar));
						calendar.set(Calendar.MONTH,
								calendar.get(Calendar.MONTH) + 1);
						branchNameCalendarModel.setNextCalendarModel(this
								.createCalendarModel(e.getMetaBranchId(),
										calendar));
						this.branchNameCalendarModelList
								.add(branchNameCalendarModel);
						break;
					}

				}
			}
		}
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

	// 获取指定日期所在星期的第一天(一个星期的第一天为星期日)的日期.
	// 如beginDate为2012-12-1日星期六,则返回2012-11-25日(星期日).
	private Date firstBeginDate(Date beginDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
		int day = -cal.get(Calendar.DAY_OF_WEEK) + 1;
		return DateUtils.addDays(cal.getTime(), day);
	}
	
	/**
	 * 已提交的变价申请页面.
	 * 
	 * @return
	 */
	@Action("submitedApply")
	public String submitedApply() {
		this.initSuggestAuditList();
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
		// 提交时间.
		params.put("createTimeBegin", DateUtil.formatDate(this.createTimeBegin, "yyyy-MM-dd"));
		params.put("createTimeEnd", DateUtil.formatDate(this.createTimeEnd, "yyyy-MM-dd"));
		params.put("submitUser", super.getSessionUserName());
		params.put("applyType", Constant.EBK_HOUSE_PRICE_APPLY_TYPE.PRICE_APPLY.name());
		this.ebkHousePricePage.setTotalResultSize(this.ebkHousePriceService
				.countEbkHousePriceListByExample(params));
		this.ebkHousePricePage.buildUrl(getRequest());
		this.ebkHousePricePage.setCurrentPage(this.page);
		params.put("start", this.ebkHousePricePage.getStartRows());
		params.put("end", this.ebkHousePricePage.getEndRows());
		params.put("orderby", "CREATE_TIME");
		params.put("order", "DESC");
		this.ebkHousePriceList = this.ebkHousePriceService
				.findEbkHousePriceListByExample(params);
		this.initMetaProductBranchName();
		this.ebkHousePricePage.setItems(this.ebkHousePriceList);
		return "submitedApply";
	}
	
	@Action("housePriceApplyDetail")
	public String housePriceApplyDetail(){
		Long housePriceId = Long.parseLong(getRequestParameter("housePriceId"));
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
		return "housePriceApplyDetail";
	}

	// 初始化采购产品类别名称.
	private void initMetaProductBranchName() {
		List<Long> metaBranchIdList = new ArrayList<Long>(
				this.ebkHousePriceList.size());
		for (EbkHousePrice e : this.ebkHousePriceList) {
			metaBranchIdList.add(e.getMetaBranchId());
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("metaBranchIds", metaBranchIdList);
		List<MetaProductBranch> embList = this.metaProductBranchService
				.getEbkMetaBranchParam(params);
		if (embList != null && !embList.isEmpty()) {
			for (MetaProductBranch emb : embList) {
				for (EbkHousePrice ehp : this.ebkHousePriceList) {
					if (emb.getMetaBranchId().equals(ehp.getMetaBranchId())) {
						ehp.setMetaProductBranchName(emb.getBranchName());
					}
				}
			}
		}
	}

	// 初始化EBooking房价变价申请的审核状态.
	private void initSuggestAuditList() {
		this.suggestAuditStatusList.clear();
		Constant.EBK_SUGGEST_AUDIT_STATUS[] ebkSuggestAuditStatusArray = Constant.EBK_SUGGEST_AUDIT_STATUS
				.values();
		CodeItem all = new CodeItem("", "全部");
		this.suggestAuditStatusList.add(all);
		for (Constant.EBK_SUGGEST_AUDIT_STATUS e : ebkSuggestAuditStatusArray) {
			if (e.isVisible()) {
				CodeItem codeItem = new CodeItem(e.name(), e.getCnName());
				this.suggestAuditStatusList.add(codeItem);
			}
		}
	}

	/**
	 * 撤消待审核的房价变更信息.
	 * 
	 * @return
	 */
	@Action("cancelChangePriceSuggest")
	public String cancelChangePriceSuggest() {
		try {
			this.ebkHousePriceService
					.deleteEbkHousePriceByPrimaryKey(this.ebkHousePrice
							.getHousePriceId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toSubmitedApply";
	}

	public EbkHousePrice getEbkHousePrice() {
		return ebkHousePrice;
	}

	public void setEbkHousePrice(EbkHousePrice ebkHousePrice) {
		this.ebkHousePrice = ebkHousePrice;
	}

	public void setEbkHousePriceService(
			EbkHousePriceService ebkHousePriceService) {
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

	public int getAppliedSuggestCount() {
		return appliedSuggestCount;
	}

	public void setAppliedSuggestCount(int appliedSuggestCount) {
		this.appliedSuggestCount = appliedSuggestCount;
	}

	public List<CodeItem> getSuggestAuditStatusList() {
		return suggestAuditStatusList;
	}

	public void setSuggestAuditStatusList(List<CodeItem> suggestAuditStatusList) {
		this.suggestAuditStatusList = suggestAuditStatusList;
	}

	public String getHousePriceIds() {
		return housePriceIds;
	}

	public void setHousePriceIds(String housePriceIds) {
		this.housePriceIds = housePriceIds;
	}

	public Date getCreateTimeBegin() {
		return createTimeBegin;
	}

	public void setCreateTimeBegin(Date createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getAddResult() {
		return addResult;
	}

	public void setAddResult(String addResult) {
		this.addResult = addResult;
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public MetaProductService getMetaProductService() {
		return metaProductService;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYearNow() {
		return yearNow;
	}

	public void setYearNow(int yearNow) {
		this.yearNow = yearNow;
	}

	public List<BranchNameCalendarModel> getBranchNameCalendarModelList() {
		return branchNameCalendarModelList;
	}

	public Float getSettlementPriceFen() {
		return settlementPriceFen;
	}

	public void setSettlementPriceFen(Float settlementPriceFen) {
		this.settlementPriceFen = settlementPriceFen;
	}

	public List<MetaProductBranch> getProductBranchList() {
		return productBranchList;
	}

	public void setProductBranchList(List<MetaProductBranch> productBranchList) {
		this.productBranchList = productBranchList;
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

	public ProductService getProductProxy() {
		return productProxy;
	}

	public void setProductProxy(ProductService productProxy) {
		this.productProxy = productProxy;
	}

}
