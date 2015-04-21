package com.lvmama.back.sweb.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.prod.LimitSaleTime;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.LimitSaleTimeService;
import com.lvmama.comm.bee.service.prod.ProdCouponIntervalService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductRoyaltyService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

public class CalendarAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6930924994123896245L;
	private Long id;
	private TimePrice[][] calendar;
	private List<CalendarModel> calendarList;
	private List<CalendarModel> calendarList2;
	private ProdProductBranchService prodProductBranchService;
	private List<ProdProductBranch> productBranchList;
	private String orderChannel;
	private String visitDate;
	private String leaveDate;
	private Long mainProdBranchId;
	private ProdProduct product;
	private ProdProductService prodProductService;
	@Autowired
	private ProductHeadQueryService productServiceProxy;
	private LimitSaleTimeService limitSaleTimeService;
	private String paramsStr;
	private String justShow;
	private boolean testOrder = false;
	private Long orderId;
	private String resource = "否";// 资源是否需要审核
	private boolean noNext = false;
	@Autowired
	private FavorService favorService;
	private ProdCouponIntervalService prodCouponIntervalService;
	private ProdProductRoyaltyService prodProductRoyaltyService;

	/**
	 * 下单时间价格查询
	 * 
	 * @return
	 */
	@Action(value = "/common/timePrice", results = @Result(name = "calendar", location = "/WEB-INF/pages/back/timeprice/time_price.jsp"))
	public String timePrice() {
		product = this.prodProductService.getProdProductById(id);
		if(product != null) {
			//期票产品
			if(product.IsAperiodic()) {
				Date currDate = DateUtil.getDayStart(new Date());
				boolean isMainExisted = false;
				//在上下线时间之间
				if(!currDate.before(product.getOnlineTime()) && !currDate.after(product.getOfflineTime())) {
					List<ProdProductBranch> branchList = prodProductBranchService.getProductBranchByProductId(id, "false", "true");
					productBranchList = new ArrayList<ProdProductBranch>();
					for (ProdProductBranch branch : branchList) {
						Date validEndTime = branch.getValidEndTime();
						//未过有效期
						if(branch.getValidBeginTime() != null && validEndTime != null && !currDate.after(DateUtil.getDayStart(validEndTime))) {
							ProdProductBranch prodProductBranch = prodProductBranchService.getProductBranchDetailByBranchId(branch.getProdBranchId(), validEndTime, !testOrder);
							if(prodProductBranch != null) {
								if(branch.getProdBranchId().equals(mainProdBranchId)) {
									isMainExisted = true;
								}
								productBranchList.add(prodProductBranch);
							}
						}
					}
				}
				if(!productBranchList.isEmpty()) {
					//如果入口类别id可售,则默认该类别的时间价格表,否则查第一个可售类别的时间价格表
					if(isMainExisted) {
						calendarList = productServiceProxy.getProductCalendarByBranchId(mainProdBranchId);
					} else {
						calendarList = productServiceProxy.getProductCalendarByBranchId(productBranchList.get(0).getProdBranchId());
					}
				}
			} else {
				// 线路，直接取默认类别价格
				if (product.getProductType().equals(Constant.PRODUCT_TYPE.ROUTE.name())) {
					// 后台下单查看时间价格表
					if (justShow == null) {
						productBranchList = prodProductBranchService.getProductBranchByProductId(id, null);
					}
					calendarList = productServiceProxy.getProductCalendarByProductId(id);
				} else {
					calendarList = productServiceProxy.getProductCalendarByBranchId(mainProdBranchId);
					// 后台下单查看时间价格表
					if (justShow == null) {
						if (!noNext) {
							// 单房型
							if (product.getSubProductType() != null && product.getSubProductType().equals(Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name())) {
								productBranchList = new ArrayList<ProdProductBranch>();
								ProdProductBranch branch = null;
								// 单房型显示入住到离店的平均价
								Long totalPrice = 0L;
								// 入住天数
								int days = getDays(DateUtil.toDate(visitDate, "yyyy-MM-dd"), DateUtil.toDate(leaveDate, "yyyy-MM-dd"));
								long stock = 0;
								// 计算出每天的价格、库存信息
								for (int i = 0; i < days; i++) {
									Date d = DateUtils.addDays(DateUtil.toDate(visitDate, "yyyy-MM-dd"), i);
									ProdProductBranch b = prodProductBranchService.getProductBranchDetailByBranchId(mainProdBranchId, d, !testOrder);
									if (b != null) {
										// 取入住当天的类别信息
										if (i == 0) {
											branch = b;
											stock = b.getStock();
										} else {
											// 取入住期间最小的库存
											if (b.getStock() < stock) {
												stock = b.getStock();
											}
										}
										// 入住期间的销售总价和市场总价
										totalPrice += b.getSellPrice();
									} else {// 避免日期跨度间某些天无法订购
										branch = null;
										stock = 0;
										totalPrice = 0L;
									}
									if(checkResourceNeedConfirm(branch.getProdBranchId(), d)) {
										resource = "是";
									}
								}
								if (branch != null) {
									// 入住期间最小的库存
									branch.setStock(stock);
									// 入住期间的平均价
									branch.setSellPrice(totalPrice / days);
									productBranchList.add(branch);
								}
							} else {
								// 门票或套房
								productBranchList = prodProductBranchService.getProductBranchByProductId(id, null);
							}
						}
					}
				}
			}
			if(calendarList != null) {
				calendarList = favorService.fillFavorParamsInfoForCalendar(id,null, calendarList);
				calendarList = fillCuCouponFlagForCalendar(id, null, calendarList);
			}
		}
		
		return "calendar";
	}

	
	/**
	 * 酒店，计算出入住天数
	 * */
	private int getDays(Date visitDate, Date leaveDate) {
		return (int) ((leaveDate.getTime() - visitDate.getTime()) / 24 / 60 / 60 / 1000);
	}

	/**
	 * 校验当天的价格，库存
	 * */
	@Action(value = "/common/checkDayInfo")
	public void checkDayInfo() {
		JSONResult result = new JSONResult();
		try {
			Date visitTime = DateUtil.toDate(visitDate, "yyyy-MM-dd");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("productId", id);
			params.put("visitTime", visitTime);
			params.put("onLine", !testOrder);
			List<ProdProductBranch> branchList = prodProductBranchService.getProductBranchDetailByProductId(params);
			JSONArray array = new JSONArray();
			for (ProdProductBranch branch : branchList) {
				JSONObject object = new JSONObject();
				object.put("branchId", branch.getProdBranchId());
				object.put("sellPrice", branch.getSellPriceYuan());
				object.put("stock", branch.getStock());
				object.put("isGugongProduct",false);
				// 判断是否时间限制，当前是是否可售
				LimitSaleTime limitSaleTime = limitSaleTimeService.getLimitSaleTime(Long.valueOf(id), visitTime);
				if (limitSaleTime != null) {
					if (StringUtils.equals(limitSaleTime.getLimitType(),Constant.LIMIT_SALE_TYPE.HOURRANGE.getCode())) {
						String now =  DateUtil.formatDate(new Date(),"HH:mm");
						object.put("canSale",DateUtil.toDate(now, "HH:mm").after(DateUtil.toDate(limitSaleTime.getLimitHourStart(), "HH:mm"))
									&& DateUtil.toDate(now, "HH:mm").before(DateUtil.toDate(limitSaleTime.getLimitHourEnd(), "HH:mm")));
						if(prodProductRoyaltyService.getRoyaltyProductIds().contains(id)){
							object.put("isGugongProduct",true);
						}
						object.put("hourRangeMsg","可售时间为每天的"+limitSaleTime.getLimitHourStart()+"至"+limitSaleTime.getLimitHourEnd());
					} else {
						object.put("canSale", new Date().after(limitSaleTime.getLimitSaleTime()));
					}
				}
				boolean flag = checkResourceNeedConfirm(branch.getProdBranchId(), visitTime);
				object.put("resource", flag ? "是" : "否");
				array.add(object);
				
				
				
			}
			result.put("array", array);
		} catch (Exception e) {
			result.raise(e);
		}
		result.output(getResponse());
	}
	
	/**
	 * 校验期票产品是否可卖
	 * */
	@Action(value = "/common/checkIsValid")
	public void checkIsValid() {
		JSONResult result = new JSONResult();
		try {
			Date visitTime = DateUtil.toDate(visitDate, "yyyy-MM-dd");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("productId", id);
			params.put("visitTime", visitTime);
			params.put("onLine", !testOrder);
			List<ProdProductBranch> branchList = prodProductBranchService.getProductBranchDetailByProductId(params);
			JSONArray array = new JSONArray();
			for (ProdProductBranch branch : branchList) {
				JSONObject object = new JSONObject();
				object.put("branchId", branch.getProdBranchId());
				object.put("sellPrice", branch.getSellPriceYuan());
				object.put("stock", branch.getStock());
				// 判断是否时间限制，当前是是否可售
				LimitSaleTime limitSaleTime=limitSaleTimeService.getLimitSaleTime(Long.valueOf(id), visitTime);
				if (limitSaleTime != null && limitSaleTime.getLimitSaleTime()!=null) {
					object.put("canSale", new Date().after(limitSaleTime.getLimitSaleTime()));
				}
				boolean flag = checkResourceNeedConfirm(branch.getProdBranchId(), visitTime);
				object.put("resource", flag ? "是" : "否");
				array.add(object);
			}
			result.put("array", array);
		} catch (Exception e) {
			result.raise(e);
		}
		result.output(getResponse());
	}

	// 判断是否需要资源审核
	private boolean checkResourceNeedConfirm(Long branchId, Date visitDate) {
		// 资源需确认
		if (this.prodProductService.checkResourceNeedConfirm(branchId, visitDate)) {
			return true;
		}
		return false;
	}

	@Action(value = "/common/calendar", results = @Result(name = "calendar", location = "/WEB-INF/pages/back/common/calendar.jsp"))
	public String loadBackPriceCalendar() {
		calendarList = productServiceProxy.getProductCalendarByProductId(id);
		return "calendar";

	}

	@Action(value = "/common/calendar2", results = @Result(name = "calendar", location = "/WEB-INF/pages/back/common/calendar.jsp"))
	public String loadLeaveCalendar() {
		Date lastDate = new Date();
		calendarList2 = productServiceProxy.getProductCalendarByProductId(id);
		calendarList = new ArrayList<CalendarModel>();

		for (CalendarModel cm : calendarList2) {
			for (int i = 0; i < cm.getCalendar().length; i++) {
				for (int j = 0; j <= 6; j++) {
					if (cm.getCalendar()[i][j].isSellable(0) && DateUtil.getDaysBetween(new Date(), cm.getCalendar()[i][j].getSpecDate()) <= 28) {
						lastDate = DateUtil.getDateAfterDays(cm.getCalendar()[i][j].getSpecDate(), 2);
					}
				}
			}
		}

		for (CalendarModel cm : calendarList2) {
			for (int i = 0; i < cm.getCalendar().length; i++) {
				for (int j = 0; j <= 6; j++) {
					if (cm.getCalendar()[i][j].getSpecDate().before(lastDate) && cm.getCalendar()[i][j].getSpecDate().after(new Date())) {
						if ((j != 0 && cm.getCalendar()[i][j - 1].isSellable(0)) || (j == 0 && i != 0 && cm.getCalendar()[i - 1][6].isSellable(0))) {
							cm.getCalendar()[i][j].setOnlyForLeave(true);
							cm.setCalendar(cm.getCalendar());
						}
					}
				}
			}
			calendarList.add(cm);
		}

		return "calendar";

	}

	/**
	 * 获取该产品当前7个月范围促优惠时间标示
	 * @param productId
	 * @param branchId
	 * @param List<CalendarModel> list
	 * @return
	 */
	private List<CalendarModel> fillCuCouponFlagForCalendar(Long productId, Long branchId, List<CalendarModel> cmList){
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
		cmList = prodCouponIntervalService.fillCuCouponFlagForCalendar(productId, branchId, cmList, dateParam);
		return cmList;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TimePrice[][] getCalendar() {
		return calendar;
	}

	public void setCalendar(TimePrice[][] calendar) {
		this.calendar = calendar;
	}

	public List<CalendarModel> getCalendarList() {
		return calendarList;
	}

	public void setCalendarList(List<CalendarModel> calendarList) {
		this.calendarList = calendarList;
	}

	public List<ProdProductBranch> getProductBranchList() {
		return productBranchList;
	}

	public void setProdProductBranchService(ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public Long getMainProdBranchId() {
		return mainProdBranchId;
	}

	public void setMainProdBranchId(Long mainProdBranchId) {
		this.mainProdBranchId = mainProdBranchId;
	}

	public ProdProduct getProduct() {
		return product;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public String getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public void setLimitSaleTimeService(LimitSaleTimeService limitSaleTimeService) {
		this.limitSaleTimeService = limitSaleTimeService;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getParamsStr() {
		return paramsStr;
	}

	public void setParamsStr(String paramsStr) {
		this.paramsStr = paramsStr;
	}

	public void setJustShow(String justShow) {
		this.justShow = justShow;
	}

	public String getJustShow() {
		return justShow;
	}

	public boolean isTestOrder() {
		return testOrder;
	}

	public void setTestOrder(boolean testOrder) {
		this.testOrder = testOrder;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getResource() {
		return resource;
	}

	public boolean isNoNext() {
		return noNext;
	}

	public void setNoNext(boolean noNext) {
		this.noNext = noNext;
	}

	public void setProductServiceProxy(ProductHeadQueryService productServiceProxy) {
		this.productServiceProxy = productServiceProxy;
	}

	public void setProdCouponIntervalService(
			ProdCouponIntervalService prodCouponIntervalService) {
		this.prodCouponIntervalService = prodCouponIntervalService;
	}
	
	public void setProdProductRoyaltyService(ProdProductRoyaltyService prodProductRoyaltyService) {
		this.prodProductRoyaltyService = prodProductRoyaltyService;
	}

}