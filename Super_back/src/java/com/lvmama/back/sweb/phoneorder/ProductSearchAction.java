package com.lvmama.back.sweb.phoneorder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.StringUtils;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProdTime;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewMultiJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.TrainDataSyncService;
import com.lvmama.comm.bee.service.com.ConditionService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.view.ViewMultiJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.bee.vo.ProdRouteDate;
import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductChannel;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.pub.ComCondition;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.service.businessCoupon.BusinessCouponService;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;
/**
 * @author shihui
 * 
 *         门票、酒店、线路产品查询
 * */
@ParentPackage("json-default")
@Results( { @Result(name = "product_search", location = "/WEB-INF/pages/back/phoneorder/product_search.jsp"),
		@Result(name = "product_list", location = "/WEB-INF/pages/back/phoneorder/product_list.jsp"),
		@Result(name = "important_tips", location = "/WEB-INF/pages/back/phoneorder/important_tips.jsp"),
		@Result(name = "important_tip_tabs", location = "/WEB-INF/pages/back/phoneorder/important_tip_tabs.jsp"),
		@Result(name = "important_tips_prod_detail", location = "/WEB-INF/pages/back/phoneorder/important_tips_prod_detail.jsp"),
		@Result(name = "channel_register", location = "/WEB-INF/pages/back/phoneorder/channel_register.jsp"),
		@Result(name = "coupon_list", location = "/WEB-INF/pages/back/phoneorder/coupon_list.jsp"),
		@Result(name = "business_coupon_list", location = "/WEB-INF/pages/back/phoneorder/business_coupon_list.jsp")
		})
public class ProductSearchAction extends BaseAction {
	private static final long serialVersionUID = -2673918918285886537L;

	private String productName;

	private String fromPlaceName;

	private String placeName;

	private String productId;

	private String sellPriceMin;

	private String sellPriceMax;

	private String filialeName;

	private String productType;

	private String beginVisitDate;

	private String endVisitDate;
	
	private Date trainVisitTime;

	private String visitDay;

	private String orderChannel;

	private String[] subProdTypes;
	
	private String departureStationPinyin;
	
	private String arrivalStationPinyin;
	
	private String lineName;

	private MarkCouponService markCouponService;

	private ProdProductService prodProductService;

	private List<ProdProduct> productList;

	private ViewPageService viewPageService;
	
	private ViewPage viewPage;

	private List<ViewMultiJourney> viewMultiJourneylist;

	private Long pageId;

	private String code;

	private boolean justCanSale = true;

	private String paramsStr = "";

	private List<CodeItem> channelList;

	private ProdProductBranchService prodProductBranchService;

	private Map<ProdRoute, List<String>> routeProductListMap;

	private ProdProduct product;

	private ProdRoute prodRoute;

	private ProdProductPlaceService prodProductPlaceService;

	private List<ProdProductPlace> placeList;

	private PerformTargetService performTargetService;

	private SettlementTargetService settlementTargetService;

	private BCertificateTargetService bCertificateTargetService;

	private List<ProdProductBranchTarget> targetList;

	private List<ComCondition> conditionList = new ArrayList<ComCondition>();

	protected ConditionService conditionService;

	private String channels;

	private boolean needJquery;

	private List<MarkCoupon> couponList;

	private Long prodBranchId;

	private boolean testOrder = false;

	private Long orderId = 0l;

	private OrderService orderServiceProxy;
	
	private String mangerName;
	
	private PermUserService permUserService;
	
	private MetaProductService metaProductService;
	
	private String  cancelType;
	
	private ComLogService comLogService;

	private BusinessCouponService businessCouponService;
	
	private List<BusinessCoupon> businessCouponList;
	
	private String isAperiodic = "false";
	
	private String validBeginTime;
	
	private String validEndTime;
	
	private TrainDataSyncService trainDataSyncServiceProxy;
	
	private ViewMultiJourneyService viewMultiJourneyService;
	
	private ViewPageJourneyService viewPageJourneyService;
	
	private List<ViewJourney> viewJourneylist;
	
	private PageService pageService;
	
	/**
	 * 跳转到查询界面
	 */
	@Action(value = "/phoneOrder/index")
	public String toSearchIndex() {
		// 订单重下
		if (orderId != null && orderId != 0l) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			isAperiodic = order.getIsAperiodic();
			if(Constant.COM_LOG_ORDER_EVENT.toCreateOrderNew_new.name().equalsIgnoreCase(cancelType)){
				buildComLog("ORD_ORDER", order.getOrderId(),  getOperatorName(), Constant.COM_LOG_ORDER_EVENT.toCreateOrderNew_new.name(),
						"后台取消", "订单重下，操作者"+ getOperatorName() + " "+"订单重下");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<OrdOrderItemProd> list = order.getOrdOrderItemProds();
			for (int i = 0; i < list.size(); i++) {
				OrdOrderItemProd itemProd = list.get(i);
				if ("true".equals(itemProd.getIsDefault())) {
					productId = itemProd.getProductId().toString();
					productType = itemProd.getProductType();
					if(!order.IsAperiodic()) {
						//套房和线路的游玩日期
						beginVisitDate = sdf.format(itemProd.getVisitTime());
						endVisitDate = sdf.format(itemProd.getVisitTime());
						// 酒店单房型取区间
						if (Constant.PRODUCT_TYPE.HOTEL.name().equals(order.getOrderType())) {
							if (Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(itemProd.getSubProductType())) {
								beginVisitDate = sdf.format(itemProd.getVisitTime());
								//取区间日期，求离店日期
								List<Date> dateList = new ArrayList<Date>();
								for (OrdOrderItemProdTime itemTime : itemProd.getAllOrdOrderItemProdTime()) {
									dateList.add(itemTime.getVisitTime());
								}
								if (!dateList.isEmpty()) {
									java.util.Collections.sort(dateList);
									Calendar c = Calendar.getInstance();
									c.setTime((Date) dateList.get(dateList.size() - 1));
									c.add(Calendar.DATE, 1);
									endVisitDate = sdf.format(c.getTime());
								}
							}
						}
					}
				}
			}
		}
		return "product_search";
	}

	private void buildComLog(String objectType, Long objectId,
			String operatorName, String logType, String logName, String content) {
		ComLog log = new ComLog();
		log.setObjectType(objectType);
		log.setObjectId(objectId);
		log.setOperatorName(operatorName);
		log.setLogType(logType);
		log.setLogName(logName);
		log.setContent(content);
		comLogService.addComLog(log);
	}
	
	/**
	 * 渠道下单
	 * */
	@Action(value = "/ordChannel/index")
	public String toChannelSearchIndex() {
		channelList = CodeSet.getInstance().getCachedCodeList(Constant.CODE_TYPE.CHANNEL.name());
		return "product_search";
	}

	/**
	 * 下测试单
	 * */
	@Action(value = "/testOrder/index")
	public String toTestSearchIndex() {
		testOrder = true;
		return "product_search";
	}

	/**
	 * 下拉框分公司列表
	 */
	public List<CodeItem> getFilialeNameList() {
		List<CodeItem> list = CodeSet.getInstance().getCachedCodeList("FILIALE_NAME");
		list.add(0, new CodeItem("", "全部"));
		return list;
	}

	/**
	 * 门票、酒店、线路产品查询
	 * */
	@Action(value = "/phoneOrder/doSearchList")
	public String doSearchList() {
		pagination = super.initPagination();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productName", StringUtils.trimAllWhitespace(productName));
		params.put("productId", StringUtils.trimAllWhitespace(productId));
		params.put("sellPriceMin", org.apache.commons.lang3.StringUtils.isEmpty(sellPriceMin) ? null : Long.valueOf(sellPriceMin) * 100);
		params.put("sellPriceMax", org.apache.commons.lang3.StringUtils.isEmpty(sellPriceMax) ? null : Long.valueOf(sellPriceMax) * 100);
		params.put("filialeName", org.apache.commons.lang3.StringUtils.isEmpty(filialeName) ? null : filialeName);
		params.put("productType", productType);
		params.put("subProdTypes", subProdTypes);
		params.put("fromPlaceName", fromPlaceName);
		params.put("placeName", placeName);
		params.put("onLine", !testOrder);
		params.put("testOrder", testOrder);
		params.put("isAperiodic", isAperiodic);
		//不定期产品不走时间价格表
		if("true".equalsIgnoreCase(isAperiodic)) {
			params.put("validBeginTime", validBeginTime);
			params.put("validEndTime", validEndTime);
			Long size = prodProductService.selectCountQiPiaoProductListByParams(params);
			pagination.setTotalRecords(size);
			if (size > 0) {
				params.put("_startRow", pagination.getFirstRow());
				params.put("_endRow", pagination.getLastRow());
				productList = prodProductService.selectQiPiaoProductListByParams(params);
				if (CollectionUtils.isNotEmpty(productList)) {
					List<Long> prodList = new ArrayList<Long>();
					for (int i = 0; i < productList.size(); i++) {
						Long prodId = productList.get(i).getProductId();
						if (!prodList.contains(prodId)) {
							prodList.add(prodId);
						}
					}
					params.put("prodIds", prodList);
					List<ProdProductBranch> branchProductList = prodProductService.selectQiPiaoBranchListByParams(params);
					Map<Long, List<ProdProductBranch>> map = new HashMap<Long, List<ProdProductBranch>>();
					for (ProdProductBranch ppb : branchProductList) {
						List<ProdProductBranch> ppbList = null;
						if (map.containsKey(ppb.getProductId())) {
							ppbList = map.get(ppb.getProductId());
						} else {
							ppbList = new ArrayList<ProdProductBranch>();
						}
						ppbList.add(ppb);
						map.put(ppb.getProductId(), ppbList);
					}
					for(int p = 0; p < productList.size(); p++) {
						ProdProduct prod = productList.get(p);
						List<ProdProductBranch> findBranchList = map.get(prod.getProductId());
						if (CollectionUtils.isEmpty(findBranchList)) {
							continue;
						}
						for (ProdProductBranch branchPrice : findBranchList) {
							//设置ProdProductBranch当前是否享有产品优惠
							List<BusinessCoupon> list = getBusinessCouponByBranchId(prod.getProductId(), branchPrice.getProdBranchId());
							if(list != null && list.size() > 0){
								branchPrice.setHasBusinessCoupon(Boolean.TRUE);
							}
						}
						//内部提示信息是否存在
						prod.setInteriorExist(pageService.isInteriorExist(prod.getProductId()));
						prod.setProdBranchList(findBranchList);
					}
				}
			}
		}else if(Constant.PRODUCT_TYPE.TRAFFIC.name().equals(productType)){
			//强制只能购买火车票
			String stationKey="";
			stationKey=departureStationPinyin;
			stationKey+="-";
			stationKey+=arrivalStationPinyin;
			params.put("stationKey",stationKey);
			params.put("visitTime", trainVisitTime);
			params.put("trainVisitTime", trainVisitTime);
			params.put("departureStation", getRequest().getParameter("departureStation"));
			params.put("arrivalStation", getRequest().getParameter("arrivalStation"));
			params.put("lineName", lineName);
			Long size = null;
			if (org.apache.commons.lang3.StringUtils
					.isNotEmpty(departureStationPinyin)
					&& org.apache.commons.lang3.StringUtils
							.isNotEmpty(arrivalStationPinyin)
					&& trainVisitTime != null
					&& !trainVisitTime.before(DateUtil.getDayStart(new Date()))) {
				size = prodProductService
						.selectCountProductListByParams(params);
				if (size == null || size == 0L) {
					// 不存在产品时做一次数据重新计算
					size = prodProductService
							.selectCountProductListByParams(params);
				}
			}
			if(size!=null&&size>0L){
				pagination.setTotalRecords(size);
				params.put("_startRow", pagination.getFirstRow());
				params.put("_endRow", pagination.getLastRow());
				productList = prodProductService.selectProductListByParams(params);
				List<ProdProductBranch> list = prodProductBranchService.selectProdTrainBranchsByParams(params);
				compositeProduct(productList,list);
			}
		} else {
			params.put("beginVisitDate", beginVisitDate);
			params.put("endVisitDate", endVisitDate);
			params.put("visitDay", visitDay);
			if (Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)) {
				Long size = prodProductService.selectCountRouteListByParams(params);
				pagination.setTotalRecords(size);
				if (size > 0) {
					params.put("_startRow", pagination.getFirstRow());
					params.put("_endRow", pagination.getLastRow());
					// 符合条件的产品id列表
					List<Long> productIds = prodProductService.selectRouteProductIdsByParams(params);
					params.put("prodIds", productIds);
					// 符合条件的产品信息
					List<ProdRouteDate> list = prodProductService.selectRouteProductListByParams(params);
					Map<Long, List<String>> map = new HashMap<Long, List<String>>();
					for (int i = 0; i < list.size(); i++) {
						ProdRouteDate prod = list.get(i);
						Long prodId = prod.getProductId();
						List<String> dates = null;
						if (map.containsKey(prodId)) {
							dates = map.get(prodId);
						} else {
							dates = new ArrayList<String>();
						}
						dates.add(DateUtil.getFormatDate(prod.getSpecDate(), "MM-dd"));
						map.put(prodId, dates);
					}
					routeProductListMap = new HashMap<ProdRoute, List<String>>();
					for (int i = 0; i < list.size(); i++) {
						ProdRoute route = list.get(i);
						//设置ProdProductBranch当前是否享有产品优惠
						List<BusinessCoupon> businessCouponList = getBusinessCouponByBranchId(route.getProductId(), null);
						if(businessCouponList != null && businessCouponList.size() > 0){
							route.setHasBusinessCoupon(Boolean.TRUE);
						}
						//内部提示信息是否存在
						route.setInteriorExist(pageService.isInteriorExist(route.getProductId()));
						// 将产品信息和日期绑定
						routeProductListMap.put(route, map.get(route.getProductId()));
					}
				}
			} else {
				Long size = prodProductService.selectCountProductListByParams(params);
				pagination.setTotalRecords(size);
				if (size > 0) {
					params.put("_startRow", pagination.getFirstRow());
					params.put("_endRow", pagination.getLastRow());
					productList = prodProductService.selectProductListByParams(params);
					if (CollectionUtils.isNotEmpty(productList)) {
						List<Long> prodList = new ArrayList<Long>();
						for (int i = 0; i < productList.size(); i++) {
							Long prodId = productList.get(i).getProductId();
							if (!prodList.contains(prodId)) {
								prodList.add(prodId);
							}
						}
						params.put("prodIds", prodList);
						// 查询酒店、门票、其他产品类别列表信息
						List<ProdProductBranch> branchProductList = prodProductService.selectProductBranchListByParams(params);
						Map<Long, List<ProdProductBranch>> map = new HashMap<Long, List<ProdProductBranch>>();
						for (ProdProductBranch ppb : branchProductList) {
							List<ProdProductBranch> ppbList = null;
							if (map.containsKey(ppb.getProductId())) {
								ppbList = map.get(ppb.getProductId());
							} else {
								ppbList = new ArrayList<ProdProductBranch>();
							}
	
							ppbList.add(ppb);
							map.put(ppb.getProductId(), ppbList);
						}
						int days = visitDay != null ? Integer.parseInt(visitDay) : 0;
						for(int p = 0; p < productList.size(); p++) {
							ProdProduct prod = productList.get(p);
							List<ProdProductBranch> findBranchList = map.get(prod.getProductId());
							if (CollectionUtils.isEmpty(findBranchList)) {
								continue;
							}
							if (Constant.PRODUCT_TYPE.HOTEL.name().equals(prod.getProductType()) && (Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(prod.getSubProductType()))) {
								List<ProdProductBranch> branchPriceList = new ArrayList<ProdProductBranch>();
								for (ProdProductBranch branchPrice : findBranchList) {
									branchPrice.setProdProduct(prod);
									// 酒店单房间，如果其中某天无库存，则标志出该房型不可售，仅显示，供看时间价格表
									// 计算出每天的价格、库存信息
									for (int z = 0; z < days; z++) {
										Date d = DateUtils.addDays(DateUtil.toDate(beginVisitDate, "yyyy-MM-dd"), z);
										ProdProductBranch b = prodProductBranchService.getProductBranchDetailByBranchId(branchPrice, d);
										if (b == null) {
											branchPrice.setStock(-9999);
											break;
										}
									}
									
									//设置ProdProductBranch当前是否享有产品优惠
									List<BusinessCoupon> list = getBusinessCouponByBranchId(prod.getProductId(), branchPrice.getProdBranchId());
									if(list != null && list.size() > 0){
										branchPrice.setHasBusinessCoupon(Boolean.TRUE);
									}
									
									// 仅显示可售
									if (justCanSale) {
										// 单房型有时间跨度，且其中某天不可售
										if (branchPrice.getStock() == -9999) {
										} else {// 满足要求的单房型或套房
											branchPriceList.add(branchPrice);
										}
									} else {
										branchPriceList.add(branchPrice);
									}
								}
								prod.setProdBranchList(branchPriceList);
							} else {
								for (ProdProductBranch branchPrice : findBranchList) {
									//设置ProdProductBranch当前是否享有产品优惠
									List<BusinessCoupon> list = getBusinessCouponByBranchId(prod.getProductId(), branchPrice.getProdBranchId());
									if(list != null && list.size() > 0){
										branchPrice.setHasBusinessCoupon(Boolean.TRUE);
									}
								}
								prod.setProdBranchList(findBranchList);
							}
							//内部提示信息是否存在
							prod.setInteriorExist(pageService.isInteriorExist(prod.getProductId()));
						}
					}
				}
			}
		}
		// 保存查询条件
		if (routeProductListMap == null && productList == null) {
		} else {
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				if (!key.equals("prodIds")) {
					Object value = params.get(key);
					if (value != null && (value instanceof String[])) {
						String[] vals = (String[]) value;
						for (String val : vals) {
							paramsStr += key + "=" + val + "&";
						}
					}else if(value!=null&&value instanceof Date){
						paramsStr += key + "=" + (DateUtil.formatDate((Date)value, "yyyy-MM-dd"))+"&";
					} else {
						paramsStr += key + "=" + (value == null ? "" : value) + "&";
					}
				}
			}
			paramsStr += "justCanSale=" + justCanSale;
		}
		pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		return "product_list";
	}

	@Action(value = "/phoneOrder/showImportantTips")
	public String showProductDeatil() {
		if (this.pageId != null) {
			if (this.pageId > 0) {
				viewPage = viewPageService.getViewPage(pageId);
				if (viewPage != null) {
					ProdProduct prod = prodProductService.getProdProduct(pageId);
					if(prod != null) {
						if(prod.isRoute()) {
							ProdRoute pr = (ProdRoute) prod;
							if(pr.hasMultiJourney()) {
								viewMultiJourneylist = viewMultiJourneyService.getAllMultiJourneyDetailByProductId(pageId);
							} else {
								viewJourneylist = viewPageJourneyService.getViewJourneysByProductId(pageId);
							}
						}
					}
				}
			}
		}
		return "important_tips";
	}

	@Action(value = "/phoneOrder/showImportantTipTabs")
	public String showImportantTips() {
		return "important_tip_tabs";
	}

	@Action(value = "/phoneOrder/showImportantTipsProdDetail")
	public String showImportantTipsProdDetail() {
		if (this.pageId != null && this.pageId > 0) {
			product = prodProductService.getProdProductById(pageId);
			// product = productService.getProdProduct(pageId);
			if (product != null) {
				String type = product.getProductType();
				if (Constant.PRODUCT_TYPE.ROUTE.name().equals(type)) {
					prodRoute = (ProdRoute) prodProductService.getProdRouteById(product.getProductId());
				}

				placeList = prodProductPlaceService.selectByProductId(product.getProductId());
				List<ProdProductBranch> ids = new ArrayList<ProdProductBranch>();
				if (prodBranchId != null && prodBranchId > 0) {// 大于0的情况下直接取当前类别的对应的对象列表
					ProdProductBranch branch = prodProductBranchService.selectProdProductBranchByPK(prodBranchId);
					if (branch != null) {
						ids.add(branch);
					}
				}

				if (ids.isEmpty()) {
					ids = prodProductBranchService.getProductBranchByProductId(product.getProductId(), null);
				}

				if (CollectionUtils.isNotEmpty(ids)) {
					targetList = new ArrayList<ProdProductBranchTarget>();
					for (ProdProductBranch branch : ids) {
						ProdProductBranchTarget target = new ProdProductBranchTarget(branch);
						target = this.getProdProductBranchTarget(branch.getProdBranchId(), target);
						targetList.add(target);
					}
				}
				
				List<ProdProductChannel> prodProductChannelList = prodProductService.selectChannelByProductId(product.getProductId());
				StringBuffer sb = new StringBuffer("");
				for (ProdProductChannel iter : prodProductChannelList) {
					sb.append(iter.getChannelName()).append(",");
				}
				channels = sb.toString();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("objectId", product.getProductId());
				params.put("objectType", "PROD_PRODUCT");
				conditionList = conditionService.getConditionByObjectId(params);
				
				PermUser user = permUserService.getPermUserByUserId(product.getManagerId());
				if(user != null) {
					mangerName = user.getRealName();
				}
			}
		}
		return "important_tips_prod_detail";
	}

	private ProdProductBranchTarget getProdProductBranchTarget(
			Long prodBranchId, ProdProductBranchTarget target) {
		List<ProdProductBranchItem> items = this.prodProductBranchService
				.selectBranchItemByBranchId(prodBranchId);
		for (ProdProductBranchItem item : items) {
			MetaProduct metaProduct = metaProductService.getMetaProduct(item
					.getMetaProductId());
			ProdProductBranchTarget.PerformTarget performTarget = new ProdProductBranchTarget.PerformTarget();
			performTarget.setMetaProduct(metaProduct);
			List<SupPerformTarget> list = this.performTargetService
					.findSuperSupPerformTargetByMetaProductId(item
							.getMetaProductId());
			performTarget.setTargetList(list);
			target.getPerformList().add(performTarget);

			ProdProductBranchTarget.SettlementTarget settlementTarget = new ProdProductBranchTarget.SettlementTarget();
			settlementTarget.setMetaProduct(metaProduct);
			settlementTarget.setTargetList(this.settlementTargetService
					.getSuperSupSettlementTargetByMetaProductId(item
							.getMetaProductId()));
			target.getSettlementList().add(settlementTarget);

			ProdProductBranchTarget.BCertificateTarget bCertificateTarget = new ProdProductBranchTarget.BCertificateTarget();
			bCertificateTarget.setMetaProduct(metaProduct);
			bCertificateTarget.setTargetList(this.bCertificateTargetService
					.selectSuperMetaBCertificateByMetaProductId(item
							.getMetaProductId()));
			target.getBcertificateList().add(bCertificateTarget);
		}

		return target;
	}
	
	 
	
	@Action(value = "/phoneOrder/doShowChannelPage")
	public String doShowChannelPage() {
		return "channel_register";
	}

	/**
	 * 查询目的地信息
	 * */
	@Action(value = "/phoneOrder/showPlaceInfo")
	public void showPlaceInfo() {
		JSONResult result = new JSONResult();
		if (productId != null) {
			Place place = prodProductPlaceService.getToDestByProductId(Long.parseLong(productId));
			if (place != null) {
				result.put("hotelStar", place.getHotelStar());
				result.put("address", place.getAddress());
				result.put("phone", place.getPhone());
			} else {
				result.put("msg", "无目的地信息");
			}
		}
		result.output(getResponse());
	}

	
	/**
	 * 查询优惠活动信息
	 * */
	@Action(value = "/phoneOrder/showCouponInfo")
	public String showCouponInfo() {
		if (productId != null) {
			// mod by ljp 20120518
			String withCode = (String)this.getRequest().getParameter("withCode");
			String subProductType = this.prodProductService.getProdProductById(Long.valueOf(productId)).getSubProductType();
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("productId", productId);
	    	map.put("subProductType", subProductType);
	    	map.put("withCode", withCode);
	    	
			couponList = markCouponService.selectProductCanUseMarkCoupon(map);
		}
		return "coupon_list";
	}

/*	*
	 * 查询产品类别的优惠策略信息
	 * *//*
	@Action(value = "/phoneOrder/showBusinessCouponInfo")
	public String showBusinessCouponInfo() {
		
		if (productId != null && prodBranchId != null) {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("productId", productId);
			if(prodBranchId > 0){
				param.put("branchId", prodBranchId);
			}
			param.put("process", "true");
			param.put("valid", "true");
			param.put("metaType", "SALES");
			param.put("beginTime321", "true");
			param.put("_startRow", "0");
			param.put("_endRow", "1");
			businessCouponList = businessCouponService.selectWithProdInfo(param);
		}
		return "business_coupon_list";
	}*/
	
	private List<BusinessCoupon> getBusinessCouponByBranchId(Long productId, Long prodBranchId) {
		
		if (productId != null) {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("productId", productId);
			if(prodBranchId != null){
				param.put("branchId", prodBranchId);
			}
			param.put("process", "true");
			param.put("valid", "true");
			param.put("metaType", "SALES");
			param.put("_startRow", "0");
			param.put("_endRow", "1");
			List<BusinessCoupon> list = businessCouponService.selectWithProdInfo(param);
			return list;
		}else{
			return null;
		}
	}
	
	private void compositeProduct(List<ProdProduct> list,List<ProdProductBranch> branchList){
		Map<Long,List<ProdProductBranch>> map = new HashMap<Long, List<ProdProductBranch>>();
		for(ProdProductBranch branch:branchList){
			List<ProdProductBranch> bList=null;
			if(map.containsKey(branch.getProductId())){
				bList = map.get(branch.getProductId());
			}else{
				bList = new ArrayList<ProdProductBranch>();
				map.put(branch.getProductId(), bList);
			}
			bList.add(branch);
		}
		
		for(ProdProduct pp:list){
			//内部提示信息是否存在
			pp.setInteriorExist(pageService.isInteriorExist(pp.getProductId()));
			pp.setProdBranchList(map.get(pp.getProductId()));
		}
	}

	public void setViewPageService(ViewPageService viewPageService) {
		this.viewPageService = viewPageService;
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = NumberUtils.toLong(pageId);
	}

	public ViewPage getViewPage() {
		return viewPage;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setSellPriceMin(String sellPriceMin) {
		this.sellPriceMin = sellPriceMin;
	}

	public void setSellPriceMax(String sellPriceMax) {
		this.sellPriceMax = sellPriceMax;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public void setVisitDay(String visitDay) {
		this.visitDay = visitDay;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public List<ProdProduct> getProductList() {
		return productList;
	}

	public void setBeginVisitDate(String beginVisitDate) {
		this.beginVisitDate = beginVisitDate;
	}

	public void setEndVisitDate(String endVisitDate) {
		this.endVisitDate = endVisitDate;
	}

	public String getProductType() {
		return productType;
	}

	public String[] getSubProdTypes() {
		return subProdTypes;
	}

	public void setSubProdTypes(String[] subProdTypes) {
		this.subProdTypes = subProdTypes;
	}

	public Map<ProdRoute, List<String>> getRouteProductListMap() {
		return routeProductListMap;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setProdProductBranchService(ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public void setFromPlaceName(String fromPlaceName) {
		this.fromPlaceName = fromPlaceName;
	}

	public boolean isJustCanSale() {
		return justCanSale;
	}

	public void setJustCanSale(boolean justCanSale) {
		this.justCanSale = justCanSale;
	}

	public List<CodeItem> getChannelList() {
		return channelList;
	}

	public String getParamsStr() {
		return paramsStr;
	}

	public String getProductName() {
		return productName;
	}

	public String getFromPlaceName() {
		return fromPlaceName;
	}

	public String getPlaceName() {
		return placeName;
	}

	public String getProductId() {
		return productId;
	}

	public String getSellPriceMin() {
		return sellPriceMin;
	}

	public String getSellPriceMax() {
		return sellPriceMax;
	}

	public String getFilialeName() {
		return filialeName;
	}

	public String getBeginVisitDate() {
		return beginVisitDate;
	}

	public String getEndVisitDate() {
		return endVisitDate;
	}

	public String getVisitDay() {
		return visitDay;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public ProdProduct getProduct() {
		return product;
	}

	public void setProduct(ProdProduct product) {
		this.product = product;
	}

	public ProdProductPlaceService getProdProductPlaceService() {
		return prodProductPlaceService;
	}

	public void setProdProductPlaceService(ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public List<ProdProductPlace> getPlaceList() {
		return placeList;
	}

	public void setPlaceList(List<ProdProductPlace> placeList) {
		this.placeList = placeList;
	}

	public PerformTargetService getPerformTargetService() {
		return performTargetService;
	}

	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}

	public SettlementTargetService getSettlementTargetService() {
		return settlementTargetService;
	}

	public void setSettlementTargetService(SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}

	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}

	/**
	 * @return the targetList
	 */
	public List<ProdProductBranchTarget> getTargetList() {
		return targetList;
	}

	public List<ComCondition> getConditionList() {
		return conditionList;
	}

	public void setConditionList(List<ComCondition> conditionList) {
		this.conditionList = conditionList;
	}

	public ConditionService getConditionService() {
		return conditionService;
	}

	public void setConditionService(ConditionService conditionService) {
		this.conditionService = conditionService;
	}

	public String getChannels() {
		return channels;
	}

	public void setChannels(String channels) {
		this.channels = channels;
	}

	public ProdRoute getProdRoute() {
		return prodRoute;
	}

	public void setProdRoute(ProdRoute prodRoute) {
		this.prodRoute = prodRoute;
	}

	public boolean isNeedJquery() {
		return needJquery;
	}

	public void setNeedJquery(boolean needJquery) {
		this.needJquery = needJquery;
	}
 

	public List<MarkCoupon> getCouponList() {
		return couponList;
	}

	public List<CodeItem> getSubProductTypeList() {
		return ProductUtil.getRouteSubTypeList();
	}

	/**
	 * @return the prodBranchId
	 */
	public Long getProdBranchId() {
		return prodBranchId;
	}

	/**
	 * @param prodBranchId
	 *            the prodBranchId to set
	 */
	public void setProdBranchId(String prodBranchId) {
		this.prodBranchId = NumberUtils.toLong(prodBranchId);
	}

	public boolean isTestOrder() {
		return testOrder;
	}

	public void setTestOrder(boolean testOrder) {
		this.testOrder = testOrder;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setParamsStr(String paramsStr) {
		this.paramsStr = paramsStr;
	}

	public String getMangerName() {
		return mangerName;
	}

	public void setMangerName(String mangerName) {
		this.mangerName = mangerName;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public String getCancelType() {
		return cancelType;
	}

	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}

	public void setBusinessCouponService(BusinessCouponService businessCouponService) {
		this.businessCouponService = businessCouponService;
	}

	public List<BusinessCoupon> getBusinessCouponList() {
		return businessCouponList;
	}

	public void setBusinessCouponList(List<BusinessCoupon> businessCouponList) {
		this.businessCouponList = businessCouponList;
	}

	public String getIsAperiodic() {
		return isAperiodic;
	}

	public String getValidBeginTime() {
		return validBeginTime;
	}

	public void setValidBeginTime(String validBeginTime) {
		this.validBeginTime = validBeginTime;
	}

	public String getValidEndTime() {
		return validEndTime;
	}

	public void setValidEndTime(String validEndTime) {
		this.validEndTime = validEndTime;
	}

	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}

	public void setTrainDataSyncServiceProxy(
			TrainDataSyncService trainDataSyncServiceProxy) {
		this.trainDataSyncServiceProxy = trainDataSyncServiceProxy;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getArrivalStationPinyin() {
		return arrivalStationPinyin;
	}

	public void setArrivalStationPinyin(String arrivalStationPinyin) {
		this.arrivalStationPinyin = arrivalStationPinyin;
	}

	public String getDepartureStationPinyin() {
		return departureStationPinyin;
	}

	public void setDepartureStationPinyin(String departureStationPinyin) {
		this.departureStationPinyin = departureStationPinyin;
	}

	public Date getTrainVisitTime() {
		return trainVisitTime;
	}

	public void setTrainVisitTime(Date trainVisitTime) {
		this.trainVisitTime = trainVisitTime;
	}

	public List<ViewMultiJourney> getViewMultiJourneylist() {
		return viewMultiJourneylist;
	}

	public void setViewMultiJourneylist(List<ViewMultiJourney> viewMultiJourneylist) {
		this.viewMultiJourneylist = viewMultiJourneylist;
	}

	public void setViewMultiJourneyService(
			ViewMultiJourneyService viewMultiJourneyService) {
		this.viewMultiJourneyService = viewMultiJourneyService;
	}

	public void setViewPageJourneyService(
			ViewPageJourneyService viewPageJourneyService) {
		this.viewPageJourneyService = viewPageJourneyService;
	}

	public List<ViewJourney> getViewJourneylist() {
		return viewJourneylist;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

}
