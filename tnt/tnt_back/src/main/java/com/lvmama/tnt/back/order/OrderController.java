package com.lvmama.tnt.back.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lvmama.comm.bee.po.op.OpTravelGroup;
import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountApply;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderRoute;
import com.lvmama.comm.bee.po.ord.OrdOrderSHHoliday;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.op.IOpTravelGroupService;
import com.lvmama.comm.bee.service.ord.OrdOrderSHHolidayService;
import com.lvmama.comm.bee.service.ord.OrderItemMetaAperiodicService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.pub.ComAffix;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComAffixService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.utils.OrderUitl;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.tnt.comm.util.WebFetchPager;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.order.po.TntOrder;
import com.lvmama.tnt.order.service.TntOrderService;
import com.lvmama.tnt.user.po.TntChannel;
import com.lvmama.tnt.user.service.TntChannelService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderServiceProxy;
	
	@Autowired
	public TntOrderService tntOrderService;
	
	@Autowired
	private TntChannelService tntChannelService;
	
	@Autowired
	private ProdProductService prodProductService;
	
	@Autowired
	private ComLogService comLogService;
	/**
	 * 订单id
	 */
	private Long orderId;
	
	/**
	 * 我的审核任务和历史订单详细信息
	 */
	private OrdOrder orderDetail;
	
	@Autowired
	private IOpTravelGroupService opTravelGroupService;
	
	@Autowired
	private OrdEContractService ordEContractService;
	
	@Autowired
	private OrdRefundMentService ordRefundMentService;
	
	@Autowired
	private ComAffixService comAffixService;
	
	public boolean isCardPay = false;
	private boolean hasValidRefundment = false;
	
	@Autowired
	private ViewPageService viewPageService;
	
	private ViewPage viewPage;
	
	private boolean showDelayWaitPaymentFlag = false;
	/**
	 * 订单价格修改记录列表.
	 */
	private List<OrdOrderAmountApply> ordOrderAmountApplyList;
	private List<OrdOrderAmountItem> listAmountItem;
	private List<OrdRefundment> listOrdRefundment;
	private List<ComLog> comLogs;
	
	@Autowired
	private OrdOrderSHHolidayService ordOrderSHHolidayService;
	
	@Autowired
	private PermUserService permUserService;
	
	@Autowired
	private OrderItemMetaAperiodicService orderItemMetaAperiodicService;
	/**
	 * 当前订单附件列表
	 */
	private List<ComAffix> orderComAffixList;
	
	/**
	 * 团上附件列表
	 */
	private List<ComAffix> groupComAffixList;
	
	/**
	 * 废单原因
	 */
	private List<CodeItem> cancelReasons;
	
	/**
	 * 发票列表
	 */
	private List<OrdInvoice> invoiceList;
	
	/**
	 * 是否显示供应商渠道重新推订单按钮
	 */
	private boolean showSupplierChannelFlag = false;
	
	String zhVisitTime = "";
	
	String settlementStatus ="未结算";
	
	private PermUser managerUser;
	
	private boolean aperiodicCanOperate = true;
	private Map<Long, String> aperiodicStatusMap;
	
	@RequestMapping(value="/index.do",method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {
		TntOrder tntOrder= new TntOrder();
		model.addAttribute(tntOrder);
		model.addAttribute("settleStatusList",TntConstant.TNT_SETTLE_STATUS.values());
		model.addAttribute("orderApproveList",Constant.ORDER_APPROVE_STATUS.values());//资源状态
		model.addAttribute("orderStatusList",Constant.ORDER_STATUS.values());
		model.addAttribute("paymentStatusList",Constant.PAYMENT_STATUS.values());
		model.addAttribute("performStatusList",Constant.ORDER_PERFORM_STATUS.values());//履行状态
		model.addAttribute("resourceLackReasonList",Constant.ORDER_RESOURCE_LACK_REASON.values());
		Map<Long, String> channelMap = tntChannelService.getMap();
		model.addAttribute("channeList", channelMap);
		return "/order/index";
	}
	
	@RequestMapping(value="/query.do",method = {RequestMethod.POST,RequestMethod.GET})
	public String query(Model model, HttpServletRequest request,
			TntOrder tntOrder, Integer page) {
		int pageNo = page != null ? page : 1;
		Page<TntOrder> p = Page.page(pageNo, tntOrder);
		List<TntOrder> tntOrderList = query(p, request);
		List<TntOrder> list = new ArrayList<TntOrder>();
		for(TntOrder o : tntOrderList){
			TntOrder order = tntOrderService.getWithItems(o);
			if(order!=null){
				list.add(order);
			}
		}
		model.addAttribute("tntOrderList",list);
		model.addAttribute(Page.KEY, p);
		return "/order/list";
	}
	
	/**
	 * 查看，显示该该订单的详细内容，利用异步返回数据
	 */
	@RequestMapping(value="/showHistoryOrderDetail.do",method = {RequestMethod.POST,RequestMethod.GET})
	public String showHistoryOrderDetail(Model model, HttpServletRequest request, Long orderId) {
		this.orderId = orderId;
		TntOrder tntOrder= tntOrderService.getByOrderId(orderId);
		queryOrderDetail();
		//为了赋值给detail.jsp页面的变量
	    model.addAttribute("orderChannelType",this.getOrderChannelType(tntOrder.getChannelId()));
	    model.addAttribute(tntOrder);
	    model.addAttribute("orderDetail",this.orderDetail);
	    model.addAttribute("isHasDistribution",this.orderDetail.hasDistribution());
	    model.addAttribute("isCancelAble",this.orderDetail.isCancelAble());
	    model.addAttribute("isHasLastCancelTime",this.orderDetail.isHasLastCancelTime());
	    model.addAttribute("isRoute",this.orderDetail.isRoute());
	    model.addAttribute("isHotel",this.orderDetail.isHotel());
	    model.addAttribute("isTicket",this.orderDetail.isTicket());
	    model.addAttribute("isHasSelfPack",this.orderDetail.hasSelfPack());
	    model.addAttribute("isAble",this.orderDetail.isAble());
	    model.addAttribute("isAperiodic",this.orderDetail.IsAperiodic());
		model.addAttribute("isHasNeedPrePay",this.orderDetail.hasNeedPrePay());
		model.addAttribute("managerUser",this.managerUser);
		model.addAttribute("settlementStatus",this.settlementStatus);
		model.addAttribute("zhVisitTime",this.orderDetail.getLatestUseTime());
		model.addAttribute("orderId",orderId);
		model.addAttribute("comLogs",this.comLogs);
		model.addAttribute("cancelReasons",this.cancelReasons);
		model.addAttribute("cancelReorderReasons",Constant.CANCEL_REORDER_REASON.values());
		model.addAttribute("orderAmountItem",this.getOrderAmountItem());
		model.addAttribute("aperiodicCanOperate",this.isAperiodicCanOperate());
		return "/order/detail";
	}
	
	protected List<TntOrder> query(Page<TntOrder> page,
			HttpServletRequest request) {
		WebFetchPager<TntOrder> pager = new WebFetchPager<TntOrder>(
				page, request) {
			@Override
			protected long getTotalCount(TntOrder t) {
				return tntOrderService.count(t);
			}
			@Override
			protected List<TntOrder> fetchDetail(
					Page<TntOrder> page) {
					page = page.desc("TNT_ORDER_ID");
				return tntOrderService.findPage(page);
			}

		};
		return pager.fetch();
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
				
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("orderId", orderId);
				param.put("status", Constant.REFUNDMENT_STATUS.REFUNDED.name());
				listOrdRefundment = ordRefundMentService.findOrdRefundByParam(param,0,ordRefundMentService.findOrdRefundByParamCount(param).intValue());

				hasValidRefundment =  (orderServiceProxy.findValidOrdRefundmentByOrderId(orderId).size() != 0);
							
				//取出订单相关附件列表
				param = new HashMap<String, Object>();
				param.put("objectId", orderDetail.getOrderId());
				param.put("objectType", "ORD_ORDER");
				orderComAffixList=comAffixService.selectListByParam(param);
				
				if(null != orderDetail){
					this.settlementStatus = Constant.SETTLEMENT_STATUS.getCnName(this.orderDetail.getSettlementStatus());
				}
				
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
			//判断是否显示“延长支付等待时间”控件  TO DO
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
			if (null != mainProdProduct){
				managerUser = permUserService.getPermUserByUserId(mainProdProduct.getManagerId());
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void fillOrder(OrdOrder order) {
		for (OrdOrderItemProd item : order.getOrdOrderItemProds()) {
			item.setProduct(prodProductService.getProdProduct(item.getProductId()));
		}
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
	 * 获取订单渠道类型
	 * @param channelId
	 * @return
	 */
	public String getOrderChannelType(Long channelId){
		TntChannel tntChannel = tntChannelService.getByChannelId(channelId);
		return TntConstant.CHANNEL_CODE.getCnName(tntChannel.getChannelCode());
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
	
	public boolean isCardPay() {
		return isCardPay;
	}
	public void setCardPay(boolean isCardPay) {
		this.isCardPay = isCardPay;
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

	public boolean isHasValidRefundment() {
		return hasValidRefundment;
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
