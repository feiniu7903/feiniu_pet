package com.lvmama.bee.web.ebooking;

import java.util.ArrayList;
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
import com.lvmama.comm.bee.po.ebooking.EbkHouseStatus;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProductService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.bee.vo.EbkDayStockDetail;
import com.lvmama.comm.pet.po.work.WorkGroup;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * EBooking库存维护.
 * 
 */
@Results(value = {
		@Result(name = "applyHouseRoomStatus", location = "/WEB-INF/pages/ebooking/housestatus/applyHouseRoomStatus.jsp"),
		@Result(name = "changeHouseRoomStatus", location = "/WEB-INF/pages/ebooking/housestatus/changeHouseRoomStatus.jsp"),
		@Result(name = "houseRoomTimePriceTable", location = "/WEB-INF/pages/ebooking/housestatus/houseRoomTimePriceTable.jsp")
})
public class EbkHouseRoomStatusAction extends EbkBaseAction {

	private static final long serialVersionUID = -3926743297676568212L;

	private EbkHouseStatus ebkHouseStatus = new EbkHouseStatus();
	private EbkDayStockDetail ebkDayStockDetail = new EbkDayStockDetail();


	private MetaProductService metaProductService;

	private MetaProductBranchService metaProductBranchService;

	private String metaProductName;
	private Date startDate;
	/** 
	 * 产品的时间价格表
	 * <br/>每个产品存放2月的时间价格表
	 * <br/>List<CalendarModel>中，0位为第一月，1位为第二月
	 */
	private List<Map<MetaProductBranch, List<CalendarModel>>> branchCalendarModel;

	private String addResult = "";
	
	private Date createTimeBegin;
	
	private Date beginDate;
	
	private Date endDate;
	private ProductService productProxy;
	
	private List<MetaProductBranch> productBranchList;
	
	private List<EbkDayStockDetail> ebkDayStockDetailList;
	
	private Long[] metaProductBranchId;
	/**
	 * 打开房态查询页面.
	 * 
	 * @return
	 */
	@Action("/ebooking/housestatus/applyHouseRoomStatus")
	public String applyHouseRoomStatus() {
		createTimeBegin = DateUtils.addDays(new Date(), 1);
		this.productBranchList = this.getProductBranch();
		return "applyHouseRoomStatus";
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
	 * 提交房态修改页面.
	 * 
	 * @return
	 */
	@Action("/ebooking/housestatus/submitChangeHouseRootStatus")
	public void submitChangeHouseRootStatus() {
		try {
			if(metaProductBranchId == null || metaProductBranchId.length <= 0){
				throw new RuntimeException("没有选择房型");
			}
			for(Long id : metaProductBranchId) {
				this.ebkHouseStatus.setMetaProductBranchId(id);
				this.updateTimePrice(this.ebkHouseStatus);
			}
		} catch (RuntimeException e) {
			this.addResult = e.getMessage();
		}
		if ("".equals(this.addResult)) {
			this.addResult = "success";
		}
		 this.sendAjaxMsg(this.addResult);
 	}
	/**
	 * 提交房态修改页面.
	 * 
	 * @return
	 */
	@Action("/ebooking/housestatus/houseRoomTimePriceTable")
	public String houseRoomTimePriceTable() {
		//重新查询页面
		if(metaProductBranchId != null && metaProductBranchId.length > 0) {
			this.branchCalendarModel = new ArrayList<Map<MetaProductBranch,List<CalendarModel>>>();
			for(Long id : metaProductBranchId) {
				Map<MetaProductBranch,List<CalendarModel>> metaM = new HashMap<MetaProductBranch, List<CalendarModel>>();
				MetaProductBranch mb = this.metaProductBranchService.getMetaBranch(id);
				metaM.put(mb, this.searchTimePrice(id, ebkHouseStatus.getBeginDate()));
				branchCalendarModel.add(metaM);
			}
		}
        return  "houseRoomTimePriceTable";
 	}
	/**
	 * 更新时间价格表.
	 * 
	 * @param housePriceId
	 */
	private void updateTimePrice(EbkHouseStatus eh) {
		TimePrice tp = new TimePrice();
 		tp.setMetaBranchId(eh.getMetaProductBranchId());
		tp.setBeginDate(eh.getBeginDate());
		tp.setEndDate(eh.getEndDate());
		if (StringUtils.isNotEmpty(eh.getApplyWeek())) {
			tp.setWeekOpen("true");
			String[] applyWeekArray = eh.getApplyWeek().split(",");
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
		// 是满房,日库存为0，不可超卖
		if (EbkHouseStatus.MAN_FANG_TRUE.equals(eh.getManfang())) {
			tp.setOverSale("false");
			tp.setDayStock(0);
 		}else {//不是满房
 			tp.setOverSale(eh.getChaomai());
 			//设定日库存
 			if(eh.getBaoliuQuantity()<0){
 				throw new RuntimeException("增减保留房不能为负数");
 			}
 			tp.setDayStock(eh.getBaoliuQuantity());
 			tp.setIsAddDayStock(eh.getBaoliu());
 		}
		//不修改资源状态
		if(tp.getDayStock() > 0) {
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
	// 查询当前用户绑定的采购产品类别信息列表.
	private List<MetaProductBranch> getProductBranch() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
		params.put("productType", Constant.PRODUCT_TYPE.HOTEL.name());
		//不定期产品不允许在EBK中做变价
		params.put("isNotAperiodic", Constant.TRUE_FALSE.TRUE.getAttr1());
		List<MetaProductBranch> result = metaProductBranchService
				.getEbkMetaBranch(params);

		if (result == null || result.isEmpty()) {
			return new ArrayList<MetaProductBranch>();
		}
		return result;
	}
	// 查询当前用户绑定的采购产品类别信息列表.
		private List<EbkDayStockDetail> getEbkDayStockDetail(Map<String, Object> params) {
			List<EbkDayStockDetail> result = metaProductBranchService
					.getEbkDayStockDetail(params);
			if (result == null || result.isEmpty()) {
				return new ArrayList<EbkDayStockDetail>();
			}
			return result;
		}
	public EbkHouseStatus getEbkHouseStatus() {
		return ebkHouseStatus;
	}

	public void setEbkHouseStatus(EbkHouseStatus ebkHouseStatus) {
		this.ebkHouseStatus = ebkHouseStatus;
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

}
