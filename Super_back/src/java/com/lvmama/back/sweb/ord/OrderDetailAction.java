package com.lvmama.back.sweb.ord;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.op.OpTravelGroup;
import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountApply;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.po.ord.OrdOrderRoute;
import com.lvmama.comm.bee.po.ord.OrdOrderSHHoliday;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.favor.FavorOrderService;
import com.lvmama.comm.bee.service.op.IOpTravelGroupService;
import com.lvmama.comm.bee.service.ord.OrdOrderSHHolidayService;
import com.lvmama.comm.bee.service.ord.OrderItemMetaAperiodicService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.fin.SetSettlementItem;
import com.lvmama.comm.pet.po.mark.MarkCouponUsage;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.pub.ComAffix;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.businessCoupon.BusinessCouponService;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.pet.service.fin.SetSettlementItemService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComAffixService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.OrderUitl;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;
@ParentPackage("json-default")
@Results( {
		@Result(name = "ord_history", location = "/WEB-INF/pages/back/ord/ord_history.jsp"),
		@Result(name = "ord_detail", location = "/WEB-INF/pages/back/ord/ord_detail.jsp"),
		@Result(name = "ord_detail_byId", location = "/WEB-INF/pages/back/ord/ord_detail_byId.jsp"),
		@Result(name = "ord_detail_error", location = "/WEB-INF/pages/back/ord/ord_detail_error.jsp"),
		@Result(name = "ord_settlementStatus", location = "/WEB-INF/pages/back/ord/ord_settlement_status.jsp")
		
})
public class OrderDetailAction  extends BaseAction {

	private ProdProductService prodProductService;
	private ComLogService comLogService;
	/**
	 * 团接口
	 */
	private IOpTravelGroupService opTravelGroupService;
	/**
	 * 附件接口
	 */
	private ComAffixService comAffixService;
	/**
	 * 退款服务接口
	 */
	private OrdRefundMentService ordRefundMentService;
	/**
	 * 电子合同.
	 */
	private OrdEContractService ordEContractService;
	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy;
	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 我的审核任务和历史订单详细信息
	 */
	private OrdOrder orderDetail;
	/**
	 * 订单价格修改记录列表.
	 */
	private List<OrdOrderAmountApply> ordOrderAmountApplyList;
	private List<OrdOrderAmountItem> listAmountItem;
	private List<OrdRefundment> listOrdRefundment;
	private List<ComLog> comLogs;
	/**
	 * 一城卡支付标识.
	 */
	private boolean oneCityOneCardFlag;
	/**
	 * 废单原因
	 */
	private List<CodeItem> cancelReasons;
	/**
	 * 发票列表
	 */
	private List<OrdInvoice> invoiceList;
	/**
	 * 当前订单附件列表
	 */
	private List<ComAffix> orderComAffixList;
	/**
	 * 团上附件列表
	 */
	private List<ComAffix> groupComAffixList;
	/**
	 * 订单备注
	 */
	private List<OrdOrderMemo> orderMemos;
	
	public boolean isCardPay = false;
	
	private ProdProductBranchService prodProductBranchService;
	private OrdOrderSHHolidayService ordOrderSHHolidayService;
	
	private ViewPageService viewPageService;
	
	private ViewPage viewPage;
	
	private boolean showDelayWaitPaymentFlag = false;
	
	private int delayWaitPayment = 0;
	
	private boolean hasValidRefundment = false;
	
	private transient SetSettlementItemService setSettlementItemService;
	
	private List<SetSettlementItem> settlementItemList;
	/**
	 * 优惠金额
	 */
	private FavorOrderService favorOrderService;
	/**
	 * 优惠策略
	 */
	private BusinessCouponService businessCouponService;
	/**
	 * 早定早惠优惠金额
	 */
	private float earlyCouponAmount;
	/**
	 * 早定早惠优惠名称
	 */
	private String earlyCouponName;
	/**
	 * 多订多惠优惠金额
	 */
	private float moreCouponAmount;
	/**
	 * 多订多惠优惠名称
	 */
	private String moreCouponName;
	
	/**
	 * 已使用的产品优惠活动 
	 */
	private String earlyDesc;
	private String moreDesc;
	private String jsonString;
	
	private OrderItemMetaAperiodicService orderItemMetaAperiodicService;
	
	private boolean aperiodicCanOperate = true;
	private Map<Long, String> aperiodicStatusMap;
	/**
	 * 是否显示供应商渠道重新推订单按钮
	 */
	private boolean showSupplierChannelFlag = false;
	
	private PermUser managerUser;
	
	private PermUserService permUserService;
	/**
	 * 查看，显示该该订单的详细内容,没有操作.
	 */
	@Action("/ord/orderDetail")
	public String orderDetail() {
		queryOrderDetail();
		Map<String,Object> parameter=new HashMap<String, Object>();
		if(orderId!=null&&orderId>0){
			parameter.put("orderId", orderId);
		}
		this.orderMemos = orderServiceProxy.queryMemoByOrderId(new Long(
				orderId));
	    ordOrderAmountApplyList=orderServiceProxy.queryOrdOrderAmountApply(parameter);
	    
	    
		return "ord_detail";
	}
	/**
	 * 查看，显示该该订单的详细内容，利用异步返回数据
	 */
	@Action("/ord/showHistoryOrderDetail")
	public String showHistoryOrderDetail() {
		queryOrderDetail();
		
		return "ord_history";
	}
	
	/**
	 * 查看，直接通过id查看
	 */
	@Action("/ord/showOrderDetailById")
	public String showOrderDetail() {
		if (orderId == null || orderServiceProxy.queryOrdOrderByOrderId(new Long(orderId))==null) {
			this.jsonString="订单不存在，订单号："+orderId;
			return "ord_detail_error";
		}
		queryOrderDetail();
		return "ord_detail_byId";
	}
	private void queryOrderDetail() {
		try{
			if (orderId != null) {
				this.orderDetail = orderServiceProxy
						.queryOrdOrderByOrderId(new Long(orderId));
				
				Long countCardPay =  orderServiceProxy.selectCardPaymentSuccessSumAmount(new Long(orderId));
				if(countCardPay > 0){
					isCardPay=true;
				}
				OrdOrderRoute orderRoute = orderServiceProxy
						.queryOrdOrderRouteByOrderId(new Long(orderId));
				if(orderRoute!=null){
					orderDetail.setOrderRoute(orderRoute);
				}
				OrdEContract ordEContract=this.ordEContractService.queryByOrderId(new Long(orderId));
				if(ordEContract!=null){
					orderDetail.setContractStatus(ordEContract.getEcontractStatus());	
				}
				this.fillOrder(orderDetail);
				//优惠券列表
				listAmountItem=orderServiceProxy.queryOrdOrderAmountItem(new Long(orderId), "ALL");
				//优惠策略(业务端)
				Map<String,Object> businessCouponparam = new HashMap<String,Object>();
				businessCouponparam.put("objectId", orderId);
				businessCouponparam.put("objectType", "ORD_ORDER_ITEM_PROD");
				List<MarkCouponUsage> markCouponUsageList=favorOrderService.selectByParam(businessCouponparam);
				
				businessCouponparam.clear();
				
				if(markCouponUsageList!=null && markCouponUsageList.size()>0){
					List<Long> businessCouponIds =new ArrayList<Long>();
					for(MarkCouponUsage markCouponUsage:markCouponUsageList){
						businessCouponIds.add(markCouponUsage.getCouponCodeId());
					}
					businessCouponparam.put("businessCouponIds", businessCouponIds);
					List<BusinessCoupon> businessCouponList=businessCouponService.selectByIDs(businessCouponparam);
					Long tempEarlyAmount=0l;
					Long tempMoreAmount=0l;
					StringBuffer sbEarly=new StringBuffer();
					StringBuffer sbMore=new StringBuffer();
					StringBuffer sbEarlyDesc=new StringBuffer();
					StringBuffer sbMoreDesc=new StringBuffer();
					if(businessCouponList != null && businessCouponList.size() > 0){
						for(BusinessCoupon businessCoupon :businessCouponList){
							for(MarkCouponUsage markCouponUsage:markCouponUsageList){
								if(businessCoupon.getBusinessCouponId().compareTo(markCouponUsage.getCouponCodeId())==0){
									if("EARLY".equals(businessCoupon.getCouponType())){
										//1.早订早惠
										tempEarlyAmount+=markCouponUsage.getAmount();
										sbEarly.append(","+businessCoupon.getCouponName());
										//已使用的产品优惠活动 
										sbEarlyDesc.append("<p>"+businessCoupon.getCouponName()+" 优惠金额： <font color='red' style='font-weight: bold;'>"+PriceUtil.convertToYuan(markCouponUsage.getAmount())+"</font></p>");
									}else if ("MORE".equals(businessCoupon.getCouponType())){
										//2.多买多惠
										tempMoreAmount+=markCouponUsage.getAmount();
										sbMore.append(","+businessCoupon.getCouponName());
										//已使用的产品优惠活动 
										sbMoreDesc.append("<p>"+businessCoupon.getCouponName()+" 优惠金额： <font color='red' style='font-weight: bold;'>"+PriceUtil.convertToYuan(markCouponUsage.getAmount())+"</font></p>");
									}
								}
							}
						}
					}
					earlyCouponAmount+=PriceUtil.convertToYuan(tempEarlyAmount);
					String early=sbEarly.toString();
					if(early!=null&&!"".equals(early)){
						earlyCouponName=early.substring(1);
					}
					moreCouponAmount+=PriceUtil.convertToYuan(tempMoreAmount);
					String more=sbMore.toString();
					if(more!=null&&!"".equals(more)){
						moreCouponName=more.substring(1);
					}
					earlyDesc=sbEarlyDesc.toString();
					moreDesc=sbMoreDesc.toString();
				}
				
				
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("orderId", orderId);
//				this.getRequest().setAttribute("orderDetail", orderDetail);
				param.put("status", Constant.REFUNDMENT_STATUS.REFUNDED.name());
				listOrdRefundment = ordRefundMentService.findOrdRefundByParam(param,0,ordRefundMentService.findOrdRefundByParamCount(param).intValue());

				hasValidRefundment =  (orderServiceProxy.findValidOrdRefundmentByOrderId(orderId).size() != 0);
							
				//取出订单相关附件列表
				param = new HashMap<String, Object>();
				param.put("objectId", orderDetail.getOrderId());
				param.put("objectType", "ORD_ORDER");
				orderComAffixList=comAffixService.selectListByParam(param);
				
				if(StringUtils.isNotEmpty(orderDetail.getTravelGroupCode())){
					OpTravelGroup group=opTravelGroupService.getOptravelGroup(orderDetail.getTravelGroupCode());
					if(group!=null){
						param.clear();
						param.put("objectId", group.getTravelGroupId());
						param.put("objectType", "OP_TRAVEL_GROUP");
						groupComAffixList=comAffixService.selectListByParam(param);
					}
				}
				// 如果需要发票
				if (orderDetail.getNeedInvoice() != null) {
						if (orderDetail.getNeedInvoice().equals("true")) {
							this.invoiceList = orderServiceProxy
									.queryInvoiceByOrderId(orderId);
						}
					}
					this.comLogs = comLogService.queryByParentId(Constant.COM_LOG_OBJECT_TYPE.ORD_ORDER.name(), orderId);
					this.cancelReasons = CodeSet.getInstance().getCachedCodeList(
							Constant.CODE_TYPE.ORD_CANCEL_REASON.name());
					
				}
			OrdOrderItemProd mainProduct = orderDetail.getMainProduct();
			if(mainProduct!=null&&mainProduct.getProductId()!=null){
				viewPage = viewPageService.getViewPage(mainProduct.getProductId());
			}else{
				viewPage = new ViewPage();
			}
			//判断是否显示“延长支付等待时间”控件
			showDelayWaitPayment(orderDetail);
			
			//不定期订单,判断该订单是否已激活
			if(orderDetail.IsAperiodic()) {
				//已激活,不能操作
				aperiodicCanOperate = !orderItemMetaAperiodicService.isOrderActivated(orderDetail.getOrderId());
				aperiodicStatusMap = orderItemMetaAperiodicService.getAperiodicStatusByOrderId(orderDetail.getOrderId());
			}
			
			if(OrderUitl.hasShholidayOrder(orderDetail)){
				OrdOrderSHHoliday shHoliday = new OrdOrderSHHoliday();
				shHoliday.setObjectId(orderDetail.getOrderId());
				shHoliday.setObjectType(Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER.getCode());
				shHoliday = ordOrderSHHolidayService.selectByObjectIdAndObjectType(shHoliday);
				showSupplierChannelFlag = shHoliday==null;
			}
			if(OrderUitl.isjinjiangOrder(orderDetail)){
				OrdOrderSHHoliday shHoliday = new OrdOrderSHHoliday();
				shHoliday.setObjectId(orderDetail.getOrderId());
				shHoliday.setObjectType(Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG.getCode());
				shHoliday = ordOrderSHHolidayService.selectByObjectIdAndObjectType(shHoliday);
				showSupplierChannelFlag = shHoliday==null;
			}
			ProdProduct mainProdProduct = orderDetail.getMainProduct().getProduct();
			managerUser = permUserService.getPermUserByUserId(mainProdProduct.getManagerId());

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 支付等待时间设置的时间列表
	 */
	private Map<String,Integer> waitPaymentMap;
	
	/**
	 * 判断是否显示“延长支付等待时间”控件
	 * */
	private void showDelayWaitPayment(OrdOrder orderDetail) {
		//订单状态正常,订单未支付,下单时间在必须使用预授权之间之前，资源审核通过
		if(OrderUitl.hasWaitpaymentChange(orderDetail)) {
			showDelayWaitPaymentFlag = true;
			createWaitPayment(orderDetail);
		}
	}
	
	private static final int wait_payment_list[]={4,6,8,12};
	
	private void createWaitPayment(OrdOrder order){		
		Date maxTime=order.getLastCancelTime();
		Date currWaitPayment=DateUtils.addMinutes(order.getApproveTime(), order.getWaitPayment().intValue());		
		if(maxTime.before(new Date())){
			return;
		}	
		Date lastTime = null;
		
		waitPaymentMap = new LinkedHashMap<String, Integer>();
		for(int x:wait_payment_list){
			int m=x*60;//分钟
			Date tmp = DateUtils.addMinutes(currWaitPayment, m);			
			if(tmp.before(maxTime)){
				waitPaymentMap.put(x+"小时", m);
				lastTime = new Date(tmp.getTime());
			}else{
				break;
			}
		}
		
		if(lastTime==null||lastTime.before(maxTime)){
			waitPaymentMap.put("最大值", -1);
		}
	}
	
	
	/**
	 * 延迟支付等待时间
	 */
	@Action("/ord/delayWaitPayment")
	public void delayWaitPayment() {
		JSONResult result=new JSONResult();
		try{
			if (orderId != null) {
				this.orderDetail = orderServiceProxy.queryOrdOrderByOrderId(new Long(orderId));
				Date lastCancelTime = orderDetail.getLastCancelTime();
				
				if(delayWaitPayment != 0) {
					Date newWaitPayment = null;
					if(delayWaitPayment != -1) {
						//取原系统默认的支付等待时间
						Date waitPayDate = null;
						waitPayDate = DateUtils.addMinutes(orderDetail.getApproveTime(), orderDetail.getWaitPayment().intValue());
						if (lastCancelTime != null && waitPayDate.after(lastCancelTime)) {
							waitPayDate = lastCancelTime;
						}
						if(waitPayDate != null) {
							newWaitPayment = DateUtils.addMinutes(waitPayDate, delayWaitPayment);
							if(newWaitPayment.after(lastCancelTime)){
								newWaitPayment = lastCancelTime;
							}
						}
					} else {
						//当不限时						
						newWaitPayment = lastCancelTime;
					}
					Long min = DateUtil.getMinBetween(orderDetail.getApproveTime(), newWaitPayment);
					orderServiceProxy.updateWaitPayment(min, orderId, getOperatorNameAndCheck());
					
					String newWaitPaymentStr = DateUtil.getDateTime("yyyy-MM-dd HH:mm", newWaitPayment);
					result.put("newWaitPaymentStr", newWaitPaymentStr);
				} else {
					throw new Exception("请选择延长支付等待时间！");
				}
			} else {
				throw new Exception("订单编号为空！");
			}
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * 查看订单子子项列表的结算详细情况.
	 * @return
	 */
	@Action("/ord/showSettlementStatusDetail")
	public String showSettlementStatusDetail() {
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		boolean searchSettlement = false;
		if(StringUtils.isEmpty(order.getTravelGroupCode())||order.getIsShHolidayOrder()||order.getIsJinjiangOrder()){
			searchSettlement= true;
		}else{
			OpTravelGroup group = opTravelGroupService.getOptravelGroup(order.getTravelGroupCode());
			ProdRoute product = prodProductService.getProdRouteById(group.getProductId());
			if(Constant.GROUP_TYPE.AGENCY.name().equals(product.getGroupType())){
				searchSettlement = true;
			}else{
				searchSettlement = false;
			}
		}
		if(searchSettlement){
			settlementItemList = this.setSettlementItemService.searchSettlementItemByOrderId(orderId);
		}else{
			settlementItemList = this.orderServiceProxy.searchOrderItemMetaListByOrderId(orderId);
		}
		return "ord_settlementStatus";
	}
	
	private void fillOrder(OrdOrder order) {
		for (OrdOrderItemProd item : order.getOrdOrderItemProds()) {
			item.setProduct(prodProductService.getProdProduct(item.getProductId()));
		}
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public OrdOrder getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(OrdOrder orderDetail) {
		this.orderDetail = orderDetail;
	}
	public List<OrdOrderAmountApply> getOrdOrderAmountApplyList() {
		return ordOrderAmountApplyList;
	}
	public void setOrdOrderAmountApplyList(
			List<OrdOrderAmountApply> ordOrderAmountApplyList) {
		this.ordOrderAmountApplyList = ordOrderAmountApplyList;
	}
	public List<OrdOrderAmountItem> getListAmountItem() {
		return listAmountItem;
	}
	public void setListAmountItem(List<OrdOrderAmountItem> listAmountItem) {
		this.listAmountItem = listAmountItem;
	}
	public List<OrdRefundment> getListOrdRefundment() {
		return listOrdRefundment;
	}
	public void setListOrdRefundment(List<OrdRefundment> listOrdRefundment) {
		this.listOrdRefundment = listOrdRefundment;
	}
	public List<ComLog> getComLogs() {
		return comLogs;
	}
	public void setComLogs(List<ComLog> comLogs) {
		this.comLogs = comLogs;
	}
	public boolean isOneCityOneCardFlag() {
		return oneCityOneCardFlag;
	}
	public void setOneCityOneCardFlag(boolean oneCityOneCardFlag) {
		this.oneCityOneCardFlag = oneCityOneCardFlag;
	}
	public List<CodeItem> getCancelReasons() {
		return cancelReasons;
	}
	public void setCancelReasons(List<CodeItem> cancelReasons) {
		this.cancelReasons = cancelReasons;
	}
	public List<OrdInvoice> getInvoiceList() {
		return invoiceList;
	}
	public void setInvoiceList(List<OrdInvoice> invoiceList) {
		this.invoiceList = invoiceList;
	}
	public List<ComAffix> getOrderComAffixList() {
		return orderComAffixList;
	}
	public void setOrderComAffixList(List<ComAffix> orderComAffixList) {
		this.orderComAffixList = orderComAffixList;
	}
	public List<ComAffix> getGroupComAffixList() {
		return groupComAffixList;
	}
	public void setGroupComAffixList(List<ComAffix> groupComAffixList) {
		this.groupComAffixList = groupComAffixList;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	public void setOpTravelGroupService(IOpTravelGroupService opTravelGroupService) {
		this.opTravelGroupService = opTravelGroupService;
	}
	
	public void setComAffixService(ComAffixService comAffixService) {
		this.comAffixService = comAffixService;
	}
	public void setOrdRefundMentService(OrdRefundMentService ordRefundMentService) {
		this.ordRefundMentService = ordRefundMentService;
	}
	public void setOrdEContractService(OrdEContractService ordEContractService) {
		this.ordEContractService = ordEContractService;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public List<OrdOrderMemo> getOrderMemos() {
		return orderMemos;
	}
	
	
	/**
	 * 获取优惠总额
	 */
	public Float getSumYouHuiAmount() {
		float _sum = 0.0f;
		if (null != this.listAmountItem && !listAmountItem.isEmpty()) {
			for (OrdOrderAmountItem item : listAmountItem) {
				if (item.isCouponItem()) {
					BigDecimal b1 = new BigDecimal(Float.toString(_sum));
					BigDecimal b2 = new BigDecimal(Float.toString(PriceUtil.convertToYuan(item.getItemAmount())));
					_sum = b1.add(b2).floatValue();
					
				}
			}
		}
		
		if(earlyCouponAmount != 0){
			_sum += earlyCouponAmount;
		}
		
		if(moreCouponAmount != 0){
			_sum += moreCouponAmount;
		}
		
		return _sum;
	}

	/**
	 * 获取优惠券金额
	 */
	public String getYouHuiAmountList() {
		StringBuilder _rtn = new StringBuilder();
		if (null != this.listAmountItem && !listAmountItem.isEmpty()) {
			for (OrdOrderAmountItem item : listAmountItem) {
				if (item.isCouponItem()) {
					_rtn.append(PriceUtil.convertToYuan(item.getItemAmount()) + ",");
				}
			}
		}
		if (_rtn.length() > 0) {
			_rtn.setLength(_rtn.length() - 1);
		}
		return _rtn.toString();
	}

	/**
	 * 获取优惠券名称
	 */
	public String getYouHuiNameList() {
		StringBuilder _rtn = new StringBuilder();
		if (null != this.listAmountItem && !listAmountItem.isEmpty()) {
			for (OrdOrderAmountItem item : listAmountItem) {
				if (item.isCouponItem()) {
					_rtn.append(item.getItemName() + ",");
				}
			}
		}
		if (_rtn.length() > 0) {
			_rtn.setLength(_rtn.length() - 1);
		}
		return _rtn.toString();
	}
	
	public OrdOrderAmountItem getOrderAmountItem() {
		if (null != this.listAmountItem && !listAmountItem.isEmpty()) {
			for (OrdOrderAmountItem item : listAmountItem) {
				if (item.isOrderItem()) {
					return item;
				}
			}
		}
		return null;
	}

	/**
	 * 获取退款总额
	 */
	public float getSumRefundmentAmount() {
		Long _sum = 0L;
		if (null != this.listOrdRefundment && !listOrdRefundment.isEmpty()) {
			for (OrdRefundment refundment : listOrdRefundment) {
				if (Constant.REFUND_TYPE.ORDER_REFUNDED.name()
						.equalsIgnoreCase(refundment.getRefundType())) {
					_sum += refundment.getAmount();
				}
			}
		}
		return PriceUtil.convertToYuan(_sum);
	}

	/**
	 * 获取补偿总额
	 * 
	 * @return
	 */
	public float getSumCompensationAmount() {
		Long _sum = 0L;
		if (null != this.listOrdRefundment && !listOrdRefundment.isEmpty()) {
			for (OrdRefundment refundment : listOrdRefundment) {
				if (Constant.REFUND_TYPE.COMPENSATION.name()
						.equalsIgnoreCase(refundment.getRefundType())) {
					_sum += refundment.getAmount();
				}
			}
		}
		return PriceUtil.convertToYuan(_sum);
	}

	public String getSettlementStatus() {
		String settlementStatus ="未结算";
		if(null != orderDetail){
			settlementStatus = Constant.SETTLEMENT_STATUS.getCnName(this.orderDetail.getSettlementStatus());
		}
		return settlementStatus;
	}

	public boolean isCardPay() {
		return isCardPay;
	}
	public void setCardPay(boolean isCardPay) {
		this.isCardPay = isCardPay;
	}
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}
 
	public void setViewPageService(ViewPageService viewPageService) {
		this.viewPageService = viewPageService;
	}
	public ViewPage getViewPage() {
		return viewPage;
	}

	public boolean isShowDelayWaitPaymentFlag() {
		return showDelayWaitPaymentFlag;
	}
	public void setDelayWaitPayment(int delayWaitPayment) {
		this.delayWaitPayment = delayWaitPayment;
	}

	public boolean isHasValidRefundment() {
		return hasValidRefundment;
	}
	public void setSetSettlementItemService(SetSettlementItemService setSettlementItemService) {
		this.setSettlementItemService = setSettlementItemService;
	}
	 
	public Map<String, Integer> getWaitPaymentMap() {
		return waitPaymentMap;
	}
	public List<SetSettlementItem> getSettlementItemList() {
		return settlementItemList;
	}
	public float getEarlyCouponAmount() {
		return earlyCouponAmount;
	}
	public String getEarlyCouponName() {
		return earlyCouponName;
	}
	public float getMoreCouponAmount() {
		return moreCouponAmount;
	}
	public String getMoreCouponName() {
		return moreCouponName;
	}
	public void setFavorOrderService(FavorOrderService favorOrderService) {
		this.favorOrderService = favorOrderService;
	}
	public void setBusinessCouponService(BusinessCouponService businessCouponService) {
		this.businessCouponService = businessCouponService;
	}
	public String getEarlyDesc() {
		return earlyDesc;
	}
	public String getMoreDesc() {
		return moreDesc;
	}
	public String getJsonString() {
		return jsonString;
	}
	public void setOrderItemMetaAperiodicService(
			OrderItemMetaAperiodicService orderItemMetaAperiodicService) {
		this.orderItemMetaAperiodicService = orderItemMetaAperiodicService;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public boolean isAperiodicCanOperate() {
		return aperiodicCanOperate;
	}
	public Map<Long, String> getAperiodicStatusMap() {
		return aperiodicStatusMap;
	}
	public void setAperiodicStatusMap(Map<Long, String> aperiodicStatusMap) {
		this.aperiodicStatusMap = aperiodicStatusMap;
	}
	
	public void setOrdOrderSHHolidayService(
			OrdOrderSHHolidayService ordOrderSHHolidayService) {
		this.ordOrderSHHolidayService = ordOrderSHHolidayService;
	}
	public boolean isShowSupplierChannelFlag() {
		return showSupplierChannelFlag;
	}
	public PermUser getManagerUser() {
		return managerUser;
	}
	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}
	
}
