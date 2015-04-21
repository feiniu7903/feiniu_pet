package com.lvmama.back.sweb.ord;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.StringUtil;
import com.lvmama.comm.bee.po.ord.*;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.service.ord.OrdOrderSHHolidayService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.*;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.pub.ComSmsTemplate;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.OrderUitl;
import com.lvmama.comm.utils.PaymentUrl;
import com.lvmama.comm.utils.PersonUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.ord.RouteUtil;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PayAndPrePaymentVO;
import com.lvmama.comm.work.builder.WorkOrderFinishedBiz;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@ParentPackage("json-default")
@Results( {
		@Result(name = "order_list", location = "/WEB-INF/pages/back/ord/ord_list.jsp"),
		@Result(name = "ord_approve", location = "/WEB-INF/pages/back/ord/ord_approve.jsp"),
 		@Result(name = "ord_pay", location = "/WEB-INF/pages/back/ord/ord_pay.jsp"),
 		@Result(name = "ord_otherMergePay", location = "/WEB-INF/pages/back/ord/ord_otherMergePay.jsp"),
 		@Result(name = "createNewOrder", location = "/phoneOrder/index.do?orderId=${orderId}", type = "redirect")
		})
/**
 * 订单审核操作类
 * 
 * @author shihui,luoyinqi
 */
public class OrderApproveAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private PassCodeService passCodeService;
	private TopicMessageProducer passportMessageProducer;
	private UserUserProxy userUserProxy;
	/**
	 * 电子合同.
	 */
	private OrdEContractService ordEContractService;
	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy;
	/**
	 * 短信模板接口.
	 */
	private ComSmsTemplateService comSmsTemplateService;
	/**
	 * 短信发送接口.
	 */
	private SmsRemoteService smsRemoteService;
	/**
	 * 我的审核任务订单集合列表
	 */
	private List<OrdOrder> ordersList;
	/**
	 * 我的审核任务订单集合总数
	 */
	private Long totalRecords;
	/**
	 * 我的历史订单集合列表
	 */
	private List<OrdOrder> historyOrdersList;
	/**
	 * 我的审核任务和历史订单详细信息
	 */
	private OrdOrder orderDetail;
	private CashAccountVO moneyAccount;
	private CashAccountService cashAccountService;
	private List<OrdOrderAmountItem> listAmountItem;
	private List<OrdRefundment> listOrdRefundment;
	private PayPaymentService payPaymentService;
	private OrdOrderSHHolidayService ordOrderSHHolidayService;

	/**
	 * 订单id
	 */
	private Long orderId;
	
	private Long invoiceId;
	/**
	 * 联系人或地址id
	 */
	private Long personId;
	/**
	 * 废单原因
	 */
	private List<CodeItem> cancelReasons;
	/**
	 * 游客或联系人id
	 */
	private String receiverId;
	/**
	 * 用户服务接口
	 */
	private IReceiverUserService receiverUserService;
	private ProdProductService prodProductService;
	/**
	 * 退款服务接口
	 */
	private OrdRefundMentService ordRefundMentService;
	/**
	 * 发票列表
	 */
	private List<OrdInvoice> invoiceList;
	/**
	 * tab下标
	 */
	private int tab;
	/**
	 * 操作日志
	 */

	private String accountpay;
	private boolean canAccountPay;
	private List<ComLog> comLogs;
 
	private CompositeQuery compositeQuery; // 综合查询类
	private OrderIdentity orderIdentity; // 根据id查询相关参数
	private OrderTimeRange orderTimeRange; // 根据时间范围查询相关参数
	private OrderStatus orderStatus; // 根据状态查询相关参数
	private OrderContent orderContent; // 根据订单内容查询相关参数
	private PageIndex pageIndex; // 分页相关参数
	private String orderType;
	private List<SortTypeEnum> typeList;
	private List<CodeItem> payWaitItemList;
	private UsrReceivers usrReceivers;
	private String operatFrom;
	private String type;//加地址时使用，为空时是给订单加实体票地址，为invoice时给发票加地址
	private boolean haveMoblie;
	//是否设置过支付密码
	private boolean havePaymentPassword;
	/**
	 * 是否有显示团相关的信息
	 */
	private boolean useTravelGroup=false;
	
	/**
	 * 是否显示供应商渠道重新推订单按钮
	 */
	private boolean showSupplierChannelFlag = false;
	/**
	 * 是否显示供应商渠道重新取消订单按钮
	 */
	private boolean showSupplierChannelRecancelFlag = false;
	
	/**
	 * 是否显示供应商渠道重新支付订单按钮
	 */
	private boolean showSupplierChannelRepayFlag = false;
	
	private ComLogService comLogService;
	/**
	 * 订单服务
	 */
	// private OrderService orderService;
	private OrdOrder ordOrder;
	private List<PayAndPrePaymentVO> prePaymentList;
	private String permId;
	/**
	 * 拉卡拉URL.
	 */
	private String lakalaURL;
	/**
	 * paymentTarget.
	 */
	private String paymentTarget;
	/**
	 * 一城卡支付标识.
	 */
	private boolean oneCityOneCardFlag;
	/**
	 * 现金账户手机号码服务接口.
	 */
	
	/**
	 * 订单是否能进行资金转移
	 */
	private boolean canTransfer;
	
	protected TopicMessageProducer resourceMessageProducer;
	private Long paymentId;
	
	private WorkOrderFinishedBiz workOrderFinishedProxy;
    /**
     * 废单重下原因
     */
    private String cancelReorderReason;

	/**
	 * 临时关闭存款账户支付
	 */
	private Boolean tempCloseCashAccountPay=false;
	
	@Action("/ord/laterProcess")
	public void laterProcess(){
		String mobile=this.getRequest().getParameter("mobile");
		ComSmsTemplate template = comSmsTemplateService.selectSmsTemplateByPrimaryKey("ORDER_LATER_PROCESS");
		try {
			smsRemoteService.sendSms(template.getContent(), mobile);
			this.sendAjaxMsg("success");
		} catch (Exception e) {
			e.printStackTrace();
			this.sendAjaxMsg("error");
		}
	}
	
	@Action("/ord/reSendSupplierOrder")
	public void reSendSupplierOrder(){
		JSONResult result = new JSONResult();
		try{
			if(orderId==null){
				throw new Exception("参数不正确");
			}
			this.orderDetail = orderServiceProxy
					.queryOrdOrderByOrderId(new Long(orderId));
			if(!orderDetail.getIsShHolidayOrder() && !orderDetail.getIsJinjiangOrder()){
				throw new Exception("订单不支持当前操作");
			}
		
			orderMessageProducer.sendMsg(MessageFactory.newSupplierChannelReSubmit(orderId));
			
			//add by zhushuying 信息审核通过，结束工单
			if (null != getSession().getAttribute("workTaskId")) {
				String paramId = getSession().getAttribute("workTaskId")
						.toString();
				workOrderFinishedProxy.finishWorkOrder(Long.valueOf(paramId),"", this.getSessionUser().getUserName());
			}
			//end by zhushuying
			
			comLogService.insert("ORD_ORDER", null, orderId,
					getOperatorName(), "ORD_ORDER", "订单修改", "重新生成供应商订单",
					null);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	@Action("/ord/reSendCancelSupplierOrder")
	public void reSendCancelSupplierOrder(){
		JSONResult result = new JSONResult();
		try{
			if(orderId==null){
				throw new Exception("参数不正确");
			}
			this.orderDetail = orderServiceProxy
					.queryOrdOrderByOrderId(new Long(orderId));
			if(!orderDetail.getIsJinjiangOrder()){
				throw new Exception("订单不支持当前操作");
			}
		
			orderMessageProducer.sendMsg(MessageFactory.newSupplierChannelReCancel(orderId));
			
			//add by zhushuying 信息审核通过，结束工单
			if (null != getSession().getAttribute("workTaskId")) {
				String paramId = getSession().getAttribute("workTaskId")
						.toString();
				workOrderFinishedProxy.finishWorkOrder(Long.valueOf(paramId),"", this.getSessionUser().getUserName());
			}
			//end by zhushuying
			
			comLogService.insert("ORD_ORDER", null, orderId,
					getOperatorName(), "ORD_ORDER", "订单修改", "重新通知供应商取消订单",
					null);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	@Action("/ord/reSendPaySupplierOrder")
	public void reSendPaySupplierOrder(){
		JSONResult result = new JSONResult();
		try{
			if(orderId==null){
				throw new Exception("参数不正确");
			}
			this.orderDetail = orderServiceProxy
					.queryOrdOrderByOrderId(new Long(orderId));
			if(!orderDetail.getIsJinjiangOrder()){
				throw new Exception("订单不支持当前操作");
			}
		
			orderMessageProducer.sendMsg(MessageFactory.newSupplierChannelRePay(orderId));
			
			//add by zhushuying 信息审核通过，结束工单
			if (null != getSession().getAttribute("workTaskId")) {
				String paramId = getSession().getAttribute("workTaskId")
						.toString();
				workOrderFinishedProxy.finishWorkOrder(Long.valueOf(paramId),"", this.getSessionUser().getUserName());
			}
			//end by zhushuying
			
			comLogService.insert("ORD_ORDER", null, orderId,
					getOperatorName(), "ORD_ORDER", "订单修改", "重新通知供应商支付订单",
					null);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	/**
	 * 进入页面初始化时执行 默认为"我的审核任务"
	 */
	@Action("/ord/order_list")
	public String execute() {
		compositeQuery = new CompositeQuery();
		orderStatus = new OrderStatus();
		orderContent = new OrderContent();
		typeList = new ArrayList<SortTypeEnum>();
		pageIndex = new PageIndex();
		//ExcludedContent excludeContent = new ExcludedContent();
		//excludeContent.setOrderApproveStatus(Constant.ORDER_APPROVE_STATUS.VERIFIED.name(),ExcludedContent.NEED_CLEAR_LIST_FALSE);
		//excludeContent.setOrderApproveStatus(Constant.ORDER_APPROVE_STATUS.BEFOLLOWUP.name(),ExcludedContent.NEED_CLEAR_LIST_FALSE);
		orderStatus.setAuditTakenStatus(Constant.AUDIT_TAKEN_STATUS.TAKEN.name());
		orderStatus.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
		orderContent.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
		orderContent.setOrderType(orderType);
		orderContent.setTakenOperator(getOperatorName());
		typeList.add(CompositeQuery.SortTypeEnum.ORDER_ID_ASC);
		// orderIdentity.setOperatorId(getOperatorName());
		pageIndex.setEndIndex(20);
		// compositeQuery.setOrderIdentity(orderIdentity);
		orderStatus.setInfoApproveStatus(Constant.ORDER_APPROVE_STATUS.UNVERIFIED.name());
		compositeQuery.setStatus(orderStatus);
		compositeQuery.setContent(orderContent);
		compositeQuery.setTypeList(typeList);
		compositeQuery.setPageIndex(pageIndex);
		//compositeQuery.setExcludedContent(excludeContent);
		
		ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		compositeQuery.setPageIndex(new PageIndex());
		compositeQuery.setExcludedContent(null);
		totalRecords = orderServiceProxy
				.compositeQueryOrdOrderCount(compositeQuery);

		return "order_list";
	}

	/**
	 * 我的历史订单查询
	 */
	public String doHistoryQuery() {

		compositeQuery = new CompositeQuery();
		orderIdentity = new OrderIdentity();
		orderTimeRange = new OrderTimeRange();
		orderContent = new OrderContent();
		orderStatus = new OrderStatus();
		pageIndex = new PageIndex();
		typeList = new ArrayList<SortTypeEnum>();

		String ordId = "";
		String person = "";
		String visitTimeStart = "";
		String visitTimeEnd = "";
		String createTimeStart = "";
		String createTimeEnd = "";
		String mail = "";
		String mobile = "";
		String tempString = "";
		String travelGroupCode = "";
		String approveStatus = "";
		String paymentStatus = "";
		String trafficTicketStatus = "";
		String order_status = "";
		
		ordId = this.getRequest().getParameter("ordId");
		person = this.getRequest().getParameter("person");
		mail = this.getRequest().getParameter("email");
		mobile = this.getRequest().getParameter("mobile");
		visitTimeStart = this.getRequest().getParameter("visitTimeStart");
		visitTimeEnd = this.getRequest().getParameter("visitTimeEnd");
		createTimeStart = this.getRequest().getParameter("createTimeStart");
		createTimeEnd = this.getRequest().getParameter("createTimeEnd");
		travelGroupCode = this.getRequest().getParameter("travelCode");
		approveStatus = this.getRequest().getParameter("approveStatus");
		paymentStatus = this.getRequest().getParameter("paymentStatus");
		trafficTicketStatus = this.getRequest().getParameter("trafficTicketStatus");
		order_status = this.getRequest().getParameter("order_Status");
		
		typeList.add(CompositeQuery.SortTypeEnum.ORDER_ID_DESC);
		typeList.add(CompositeQuery.SortTypeEnum.REDAIL_ASC);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();

		if (!StringUtil.isEmptyString(ordId)) {
			orderIdentity.setOrderId(Long.parseLong(ordId.trim()));
			tempString += ("ordId=" + ordId + "&");
		}

		if (!StringUtil.isEmptyString(person)) {
			orderContent.setUserName(person.trim());
			tempString += ("person=" + person + "&");
		}

		if (!StringUtil.isEmptyString(mail)) {
			orderContent.setEmail(mail.trim());
			tempString += ("email=" + mail + "&");
		}

		if (!StringUtil.isEmptyString(mobile)) {
			orderContent.setMobile(mobile.trim());
			tempString += ("mobile=" + mobile + "&");
		}

		if (!StringUtil.isEmptyString(visitTimeStart)
				&& !StringUtil.isEmptyString(visitTimeEnd)) {
			tempString += ("visitTimeStart=" + visitTimeStart + "&");
			tempString += ("visitTimeEnd=" + visitTimeEnd + "&");
			try {
				orderTimeRange
						.setOrdOrderItemProdVisitTimeStart(simpleDateFormat
								.parse(visitTimeStart));

				c.setTime(simpleDateFormat.parse(visitTimeEnd)); // 因取到的结束日期为当天的0:00:00，故查询时需往后延一天，下同
				c.add(Calendar.DATE, 1);
				orderTimeRange.setOrdOrderItemProdVisitTimeEnd(c.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (!StringUtil.isEmptyString(createTimeStart)
				&& !StringUtil.isEmptyString(createTimeEnd)) {
			tempString += ("createTimeStart=" + createTimeStart + "&");
			tempString += ("createTimeEnd=" + createTimeEnd + "&");
			try {
				orderTimeRange.setCreateTimeStart(simpleDateFormat
						.parse(createTimeStart));

				c.setTime(simpleDateFormat.parse(createTimeEnd));
				c.add(Calendar.DATE, 1);
				orderTimeRange.setCreateTimeEnd(c.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(travelGroupCode)){
			tempString += ("travelGroupCode=" + travelGroupCode + "&");
			orderContent.setTravelCode(travelGroupCode);
		}
		if(StringUtils.isNotEmpty(approveStatus)){
			tempString += ("approveStatus=" + approveStatus +"&");
			orderStatus.setOrderApproveStatus(approveStatus);
		}
		if(StringUtils.isNotEmpty(order_status)){
			tempString += ("order_status=" + order_status +"&");
			orderStatus.setOrderStatus(order_status);			
		}
		if(StringUtils.isNotEmpty(paymentStatus)){
			tempString += ("paymentStatus=" + paymentStatus +"&");
			orderStatus.setPaymentStatus(paymentStatus);
		}
		if(StringUtils.isNotEmpty(trafficTicketStatus)){
			tempString += ("trafficTicketStatus" + trafficTicketStatus +"&");
			orderStatus.setTicketStatus(trafficTicketStatus);
		}
		
		orderContent.setOrderType(orderType);
		orderContent.setTakenOperator(getOperatorName());
		pageIndex.setEndIndex(50);

		compositeQuery.setOrderIdentity(orderIdentity);
		compositeQuery.setStatus(orderStatus);
		compositeQuery.setOrderTimeRange(orderTimeRange);
		//将用户名Email等转为IdList形式查询
		Map<String, Object> param = orderContent.getUserParam(orderContent);
		if(param != null) {
			orderContent.setUserIdList(orderContent.getUserList(userUserProxy.getUsers(param)));
		}
		
		compositeQuery.setContent(orderContent);
		compositeQuery.setTypeList(typeList);

		Long totalRecords = orderServiceProxy
				.compositeQueryOrdOrderCount(compositeQuery);
		pagination = initPagination();
		pagination.setTotalRecords(totalRecords);
		pageIndex.setBeginIndex(pagination.getFirstRow());
		pageIndex.setEndIndex(pagination.getLastRow());
		pagination.setActionUrl("ord/order_list!doHistoryQuery.do?orderType="
				+ orderType + "&tab=" + tab + "&" + tempString+"&permId="+permId);

		compositeQuery.setPageIndex(pageIndex);
		historyOrdersList = orderServiceProxy
				.compositeQueryOrdOrder(compositeQuery);
		return "order_list";
	}

	/**
	 * 领单，查询所有该取单人下订单未审核完的订单列表 并根据订单时间倒序排列
	 * 
	 */
	@Action("/ord/doGetOrder")
	public String doGetOrder() {
		orderServiceProxy.makeOrdOrderAudit(getOperatorName(), orderType);
		return execute();
	}
	

	/**
	 * 领单，工单  任务处理  信息审核工单
	 * @return
	 * @author zhushuying
	 */
	@Action("/ord/doGetOrderByOrderId")
	public String doGetOrderByOrderId() {
		orderServiceProxy.makeOrdOrderAuditById(getOperatorName(), orderId);
		String workTaskId = getRequest().getParameter("workTaskId");
		getRequest().getSession().setAttribute("workTaskId", workTaskId);
		return showApproveOrderDetail();
	}

	/**
	 * 需重播，直接记录到数据库
	 */
	@Action("/ord/doNeedReplay")
	public void doNeedReplay() {
		try {
			boolean flag = orderServiceProxy.updateRedail(orderDetail
					.getRedail(), orderId, getOperatorName());
			returnMessage(flag);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}

	/**
	 * 查看，显示该该订单的详细内容，利用异步返回数据
	 */
	@Action("/ord/showApproveOrderDetail")
	public String showApproveOrderDetail() {
		if (orderId != null) {
			this.orderDetail = orderServiceProxy
					.queryOrdOrderByOrderId(new Long(orderId));
			fillOrder(orderDetail);
			this.cancelReasons = CodeSet.getInstance().getCachedCodeList(
					Constant.CODE_TYPE.ORD_CANCEL_REASON.name());
		}
		payWaitItemList = CodeSet.getInstance().getCachedCodeList(
				Constant.CODE_TYPE.WAIT_PAYMENT.name());
		initSupplierButton();
		return "ord_approve";
	}
	
	private void initSupplierButton(){
		if(OrderUitl.hasShholidayOrder(orderDetail)){
			OrdOrderSHHoliday shHoliday = new OrdOrderSHHoliday();
			shHoliday.setObjectId(orderDetail.getOrderId());
			shHoliday.setObjectType(Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER.getCode());
			shHoliday = ordOrderSHHolidayService.selectByObjectIdAndObjectType(shHoliday);
			showSupplierChannelFlag = shHoliday==null;
		}
		if(OrderUitl.isjinjiangOrder(orderDetail)){
			OrdOrderSHHoliday psh = new OrdOrderSHHoliday();
			psh.setObjectId(orderDetail.getOrderId());
			psh.setObjectType(Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG.getCode());
			OrdOrderSHHoliday shHoliday = ordOrderSHHolidayService.selectByObjectIdAndObjectType(psh);
			showSupplierChannelFlag = shHoliday==null;
			
			psh.setObjectId(orderDetail.getOrderId());
			psh.setObjectType(Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG_CANCEL.getCode());
			shHoliday = ordOrderSHHolidayService.selectByObjectIdAndObjectType(psh);
			if(shHoliday!=null && !"CANCEL".equalsIgnoreCase(shHoliday.getContent())){
				showSupplierChannelRecancelFlag = true;
			}
			
			psh.setObjectId(orderDetail.getOrderId());
			psh.setObjectType(Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG_PAY.getCode());
			shHoliday = ordOrderSHHolidayService.selectByObjectIdAndObjectType(psh);
			if(shHoliday!=null && !"PAYED".equalsIgnoreCase(shHoliday.getContent())){
				showSupplierChannelRepayFlag = true;
			}
			
		}
	}
	 

	private void fillOrder(OrdOrder order) {
		for (OrdOrderItemProd item : order.getOrdOrderItemProds()) {
			item.setProduct(prodProductService.getProdProduct(item.getProductId()));
		}
	}

	@Action("/ord/showOrderPay")
	public String showOrderPay() {
		this.ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		
		//由于驴妈妈账户被盗严重  对于广州长隆供应商的产品临时关闭存款账户支付功能(485=广州长隆供应商ID)
		List<OrdOrderItemMeta> ordOrderItemMetaList=ordOrder.getAllOrdOrderItemMetas();
		if(ordOrderItemMetaList!=null && ordOrderItemMetaList.size()>0){
			for (OrdOrderItemMeta ordOrderItemMeta : ordOrderItemMetaList) {
				if(ordOrderItemMeta.getSupplierId()!=null && 
						(485==ordOrderItemMeta.getSupplierId().longValue()
						||2261==ordOrderItemMeta.getSupplierId().longValue()
						||4462==ordOrderItemMeta.getSupplierId().longValue()
						||4367==ordOrderItemMeta.getSupplierId().longValue()
						||1340==ordOrderItemMeta.getSupplierId().longValue()
						||6134==ordOrderItemMeta.getSupplierId().longValue()
						)){
					tempCloseCashAccountPay=true;
				}
			}
		}
		
		
		
		this.canTransfer = orderServiceProxy.canTransferPayment(orderId);
		this.moneyAccount = cashAccountService.queryMoneyAccountByUserNo(ordOrder.getUserId());
		this.canAccountPay = orderServiceProxy.canAccountPay(ordOrder,moneyAccount);
		
		if(moneyAccount!=null){
			this.haveMoblie=StringUtils.isNotBlank(moneyAccount.getMobileNumber());
			this.havePaymentPassword=StringUtils.isNotBlank(moneyAccount.getPaymentPassword());
		}
		
		try {
			// 加载订单信息
			//ordPaymentList = payPaymentService.selectByObjectIdAndBizType(orderId, com.lvmama.comm.vo.Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
			prePaymentList  = payPaymentService.selectPayAndPreByObjectIdAndBizType(orderId, com.lvmama.comm.vo.Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
			this.getRequest().setAttribute("orderId", orderId);
			this.getRequest().setAttribute("payTotal",
			this.getRequest().getParameter("payTotal"));
			this.getRequest().setAttribute("paymentStatus",
			this.getRequest().getParameter("paymentStatus"));
			this.getRequest().setAttribute("userId", ordOrder.getUserId());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		setPaymentTarget(ordOrder.getPaymentTarget());
		PaymentUrl paymentUrl = new PaymentUrl(ordOrder.getOrderId(), "ORD_ORDER", Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name(), ordOrder.getOughtPayFenLong(), Constant.PAYMENT_OPERATE_TYPE.PAY.name());
		lakalaURL = paymentUrl.getPaymentUrl(Constant.getInstance().getPaymentUrl()+"pay/lakala.do");
		return "ord_pay";
	}
	/**
	 * 展示合并支付的订单支付信息
	 * @author ZHANG Nan
	 * @return
	 */
	@Action("/ord/showOtherMergePay")
	public String showOtherMergePay() {
		try {
			Long objectId=Long.valueOf(getRequest().getParameter("orderId"));
			String paymentTradeNo=getRequest().getParameter("paymentTradeNo");
			String gatewayTradeNo=getRequest().getParameter("gatewayTradeNo");
			String paymentGateway=getRequest().getParameter("paymentGateway");
			prePaymentList  = payPaymentService.getOtherMergePayListByPayment(objectId, paymentTradeNo, gatewayTradeNo, paymentGateway);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return "ord_otherMergePay";
	}
	
	/**
	 * 支付信息页面-手工重新发送支付成功消息
	 * @author ZHANG Nan
	 * @return
	 */
	@Action("/ord/reSendPaymentSuccessCallMessage")
	public void reSendPaymentSuccessCallMessage(){
		try {
			PayPayment payPayment=payPaymentService.selectByPaymentId(paymentId);
			LOG.info("payPayment:"+com.lvmama.comm.utils.StringUtil.printParam(payPayment));
			if (payPayment!=null && !payPayment.isNotified() && Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name().equals(payPayment.getStatus())) {
				LOG.info("paymentSuccessCallMessage already send, operatorName:"+getOperatorName());
				resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(payPayment.getPaymentId()));
			}
			returnMessage(true);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}
	
	
	/**
	 * 修改支付等待时间
	 */
	@Action("/ord/transfer_payment")
	public void doTransferpayment() {
		try {
			boolean flag=false;
			this.ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if (null != ordOrder && null != ordOrder.getOriginalOrderId() && orderServiceProxy.canTransferPayment(orderId)) {
				orderMessageProducer.sendMsg(MessageFactory.newOrderTransferPaymentMessage(orderId, ordOrder.getOriginalOrderId(), Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.getCode(), Constant.OBJECT_TYPE.ORD_ORDER.getCode()));
				flag = true;
			}
			returnMessage(flag);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}
	

	/**
	 * 修改支付等待时间
	 */
	@Action("/ord/doModifyWaitPayment")
	public void doModifyWaitPayment() {
		try {
			boolean flag=false;
			//添加判断，如果前台传入的值为空时不让操作保存
			if(orderDetail!=null||orderDetail.getWaitPayment()!=null){
				flag = orderServiceProxy.updateWaitPayment(orderDetail
					.getWaitPayment(), orderId, getOperatorName());
			}
			returnMessage(flag);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}

	/**
	 * 删除联系人或旅客
	 */
	@Action("/ord/doDeletePerson")
	public void doDeletePerson() {
		try {
			boolean flag = orderServiceProxy.removePersonFromOrdOrder(personId,
					orderId, getOperatorName());
			returnMessage(flag);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}

	/**
	 * 检查是否可以退单
	 * @throws IOException 
	 */
	@Action("/ord/checkGoingBack")
	public void checkGoingBack() throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("objectId", orderId.toString());
		if(orderServiceProxy.canGoingBack(params)){
			this.returnMessage(true);
		}else{
			this.returnMessage(false);
		}
 	}
	
	/**
	 * 检查是否可以审核（订单有可能被回收）
	 */
	@Action("/ord/checkApprove")
	public void checkApprove()throws IOException{
		OrdOrder oo = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if(Constant.AUDIT_TAKEN_STATUS.UNTAKEN.name().equals(oo.getTaken())){
			this.returnMessage(true);
		}else{
			this.returnMessage(false);
		}
	}
	
	/**
	 * 退单
	 */
	@Action("/ord/doCancelOrder")
	public String doCancelOrder() {
		String id = this.getRequest().getParameter("id");
		long longId = Long.parseLong(id);
		orderServiceProxy.cancelOrdOrderAudit(getOperatorName(), longId);
		return this.execute();
	}

	/**
	 * 信息审核通过
	 */
	@Action("/ord/doInfoApprovePass")
	public void doInfoApprovePass() {
		try {
			boolean flag = orderServiceProxy.approveInfoPass(orderId,
					getOperatorName());
			returnMessage(flag);
			//add by zhushuying 信息审核通过，结束工单
			if (null != getSession().getAttribute("workTaskId")) {
				String paramId = getSession().getAttribute("workTaskId")
						.toString();
				workOrderFinishedProxy.finishWorkOrder(Long.valueOf(paramId),"", this.getSessionUser().getUserName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}

	/**
	 * 订单审核通过
	 */
	@Action("/ord/doOrderApprovePass")
	public void doOrderApprovePass() {
		try {
			boolean flag = orderServiceProxy.approveVerified(orderId,
					getOperatorName());
			returnMessage(flag);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}

	/**
	 * 保存配送地址
	 */
	@Action("/ord/doSaveExpressAdd")
	public void doSaveExpressAdd() {
		try {
			UsrReceivers usrReceiver = this.receiverUserService
					.getRecieverByPk(receiverId);
			if(usrReceiver != null){
				Person person = PersonUtil.converReceiver(usrReceiver, Constant.ORD_PERSON_TYPE.ADDRESS);
				
				boolean flag = false;
				if(StringUtils.equals(type, "INVOICE")){//是发票地址时
					OrdInvoice invoice=orderServiceProxy.selectOrdInvoiceByPrimaryKey(invoiceId);
					if(invoice==null){
						throw new Exception("发票不存在");
					}
					if(!Constant.INVOICE_STATUS.UNBILL.name().equals(invoice.getStatus())){
						throw new Exception("当前发票状态不能修改地址");
					}
					flag=orderServiceProxy.addPerson2Invoice(person,invoiceId,getOperatorName());
				}else{
					if (personId == null || personId == 0) {
						flag = orderServiceProxy.addPerson2OrdOrder(person, orderId,
								getOperatorName());
					} else {
						person.setPersonId(personId);
						flag = orderServiceProxy.updatePerson(person, orderId,
								getOperatorName());
					}
				}
				returnMessage(flag);
			} else {
				returnMessage(false);
			}
		} catch (Exception e) {
			returnMessage(false);
		}
	}

	/**
	 * 废单
	 */
	@Action("/ord/doOrderCancel")
	public void doOrderCancel() {
		try {
			OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(orderId);
			//该位置做废单判断，如果已经过了最晚时间不做处理，只许超级废单直接处理
			if(order==null||!order.isCancelAble()){
				returnMessage(false);
			}else{
				if(order.getIsShHolidayOrder()){
					orderMessageProducer.sendMsg(MessageFactory.newSupplierOrderCancelMessage(orderId));
					returnMessage(true);
				}else{
					boolean flag = orderServiceProxy.cancelOrder(orderId, orderDetail
							.getCancelReason(), getOperatorName());
					
					LOG.info("flag:"+flag+",Resources not audit,automatic refundment");
					if(flag){
				         orderServiceProxy.autoCreateOrderFullRefund(order,super.getOperatorName(), "资源未审核废单，自动退款");
				    }
					returnMessage(flag);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}
	
	/**
	 * 废单重下的处理
	 */
	@Action("/ord/cancelAndCreateNewOrder")
	public String doCancelAndCreateNewOrder() {
		OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if (null == order 
				|| !order.isCancelAble()
				|| !Constant.SETTLEMENT_STATUS.UNSETTLEMENTED.name().equals(order.getSettlementStatus())
				|| !orderServiceProxy.findValidOrdRefundmentByOrderId(orderId).isEmpty()) {
			//订单不能取消，或者订单已经进入结算，或者订单具有退款单，不能进行废单重下
			return ERROR;
		} else {
			//为了与普通的废单重下在日志上做区分,故名叫 "废单 * 重下"
			orderServiceProxy.cancelOrder(orderId, "废单 * 重下", getOperatorName(),Constant.CANCEL_REORDER_REASON.getCnName(cancelReorderReason));
			return "createNewOrder";
		}
	}

	/**
	 * 新增用户保存为联系人
	 */
	@Action("/ord/doAddContactor")
	public void addContactor() {
		try {
			UsrReceivers usr = this.receiverUserService
					.getRecieverByPk(receiverId);			
			Person per = new Person();
			per.setPersonId(personId == null ? 0 : personId);
			per.setName(usr.getReceiverName());
			per.setTel(usr.getPhone());
			per.setMobile(usr.getMobileNumber());
			per.setEmail(usr.getEmail());
			per.setAddress(usr.getAddress());
			per.setPostcode(usr.getPostCode());
			per.setFax(usr.getFax());
			per.setFaxTo(usr.getFaxContactor());
			per.setCertNo(usr.getCardNum());
			per.setCertType(usr.getCardType());
			per.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.name());
			per.setReceiverId(usr.getReceiverId());
			boolean flag = orderServiceProxy.addPerson2OrdOrder(per, orderId,
					getOperatorName());
			returnMessage(flag);
			// 二维码修改联系人
			if ("monitor".equalsIgnoreCase(operatFrom)) {
				OrdOrder order = orderServiceProxy
						.queryOrdOrderByOrderId(orderId);
				List<PassEvent> list = passCodeService.updateContact(order);
				// 发送申码请求处理消息
				for (PassEvent passEvent : list) {
					passportMessageProducer.sendMsg(MessageFactory
							.newPasscodeEventMessage(passEvent.getEventId()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}

	/**
	 * 保存为游客
	 */
	@Action("/ord/doAddVisitor")
	public void doAddVisitor() {
		try {
			UsrReceivers usr = receiverUserService.getRecieverByPk(receiverId);
			Person per = new Person();
			per.setPersonId(0);
			per.setName(usr.getReceiverName());
			per.setTel(usr.getPhone());
			per.setMobile(usr.getMobileNumber());
			per.setEmail(usr.getEmail());
			per.setAddress(usr.getAddress());
			per.setPostcode(usr.getPostCode());
			per.setFax(usr.getFax());
			per.setFaxTo(usr.getFaxContactor());
			per.setCertNo(usr.getCardNum());
			per.setCertType(usr.getCardType());
			per.setPersonType(Constant.ORD_PERSON_TYPE.TRAVELLER.name());
			per.setReceiverId(usr.getReceiverId());
			boolean flag = orderServiceProxy.addPerson2OrdOrder(per, orderId,
					getOperatorName());
			returnMessage(flag);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}

	/**
	 * 是否需要实体票
	 */
	@Action("/ord/doNeedInvoice")
	public void doNeedInvoice() {
		try {
			boolean flag = orderServiceProxy.updateNeedInvoice(orderDetail
					.getNeedInvoice(), orderId, getOperatorName());
			returnMessage(flag);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}

	private String creatorComplete="";//接收返回值
	
	public String getCreatorComplete() {
		return creatorComplete;
	}

	public void setCreatorComplete(String creatorComplete) {
		this.creatorComplete = creatorComplete;
	}
	/**
	 * 返回操作成功信息
	 */
	private void returnMessage(boolean flag) {
		try {
 			if (flag) {
 				if (null != getSession().getAttribute("workTaskId")) {
 					String paramId = getSession().getAttribute("workTaskId")
 							.toString();
 					creatorComplete=workOrderFinishedProxy.finishWorkOrder(Long.parseLong(paramId), getSessionUser().getUserName());
 					getSession().removeAttribute("workTaskId");
 				}
 				this.getResponse().getWriter().write("{result:true,creatorComplete:'"+creatorComplete+"'}");
			} else {
				this.getResponse().getWriter().write("{result:false}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<OrdOrder> getOrdersList() {
		return ordersList;
	}

	public List<OrdOrder> getHistoryOrdersList() {
		return historyOrdersList;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public OrderTimeRange getOrderTimeRange() {
		return orderTimeRange;
	}

	public void setOrderTimeRange(OrderTimeRange orderTimeRange) {
		this.orderTimeRange = orderTimeRange;
	}

	public OrderContent getOrderContent() {
		return orderContent;
	}

	public List<CodeItem> getCancelReasons() {
		return cancelReasons;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public CompositeQuery getCompositeQuery() {
		return compositeQuery;
	}

	public void setCompositeQuery(CompositeQuery compositeQuery) {
		this.compositeQuery = compositeQuery;
	}

	public OrderIdentity getOrderIdentity() {
		return orderIdentity;
	}

	public void setOrderIdentity(OrderIdentity orderIdentity) {
		this.orderIdentity = orderIdentity;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;		
		useTravelGroup=RouteUtil.hasTravelGroupProduct(orderType);
	}

	public int getTab() {
		return tab;
	}

	public void setTab(int tab) {
		this.tab = tab;
	}

	public List<OrdInvoice> getInvoiceList() {
		return invoiceList;
	}

	public OrdOrder getOrdOrder() {
		return ordOrder;
	}

	public void setOrdOrder(OrdOrder ordOrder) {
		this.ordOrder = ordOrder;
	}

	public List<PayAndPrePaymentVO> getPrePaymentList() {
		return prePaymentList;
	}
	public OrdOrder getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrdOrder orderDetail) {
		this.orderDetail = orderDetail;
	}

	public List<CodeItem> getPayWaitItemList() {
		return payWaitItemList;
	}

	public void setPayWaitItemList(List<CodeItem> payWaitItemList) {
		this.payWaitItemList = payWaitItemList;
	}

	public List<ComLog> getComLogs() {
		return comLogs;
	}

	public UsrReceivers getUsrReceivers() {
		return usrReceivers;
	}

	public void setUsrReceivers(UsrReceivers usrReceivers) {
		this.usrReceivers = usrReceivers;
	}

	public String getOperatFrom() {
		return operatFrom;
	}

	public void setOperatFrom(String operatFrom) {
		this.operatFrom = operatFrom;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setPassportMessageProducer(
			TopicMessageProducer passportMessageProducer) {
		this.passportMessageProducer = passportMessageProducer;
	}

	public void setMoneyAccount(CashAccountVO moneyAccount) {
		this.moneyAccount = moneyAccount;
	}

	public CashAccountVO getMoneyAccount() {
		return moneyAccount;
	}

	public void setOrdEContractService(OrdEContractService ordEContractService) {
		this.ordEContractService = ordEContractService;
	}
	public OrdEContractService getOrdEContractService() {
		return ordEContractService;
	}
	public String getAccountpay() {
		return accountpay;
	}

	public void setAccountpay(String accountpay) {
		this.accountpay = accountpay;
	}

	public boolean isCanAccountPay() {
		return canAccountPay;
	}

	public void setCanAccountPay(boolean canAccountPay) {
		this.canAccountPay = canAccountPay;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public OrdRefundMentService getOrdRefundMentService() {
		return ordRefundMentService;
	}

	public void setOrdRefundMentService(
			OrdRefundMentService ordRefundMentService) {
		this.ordRefundMentService = ordRefundMentService;
	}

	public boolean isHaveMoblie() {
		return haveMoblie;
	}

	public void setHaveMoblie(boolean haveMoblie) {
		this.haveMoblie = haveMoblie;
	}

	/**
	 * orderMessageProducer.
	 */
	private transient TopicMessageProducer orderMessageProducer;

	/**
	 * setOrderMessageProducer.
	 * 
	 * @param orderMessageProducer
	 *            orderMessageProducer
	 */
	public void setOrderMessageProducer(
			final TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	/**
	 * getLakalaURL.
	 * 
	 * @return 拉卡拉URL
	 */
	public String getLakalaURL() {
		return lakalaURL;
	}

	/**
	 * setLakalaURL.
	 * 
	 * @param lakalaURL
	 *            拉卡拉URL
	 */
	public void setLakalaURL(final String lakalaURL) {
		this.lakalaURL = lakalaURL;
	}

	/**
	 * getPaymentTarget.
	 * 
	 * @return paymentTarget
	 */
	public String getPaymentTarget() {
		return paymentTarget;
	}

	/**
	 * setPaymentTarget.
	 * 
	 * @param paymentTarget
	 *            paymentTarget
	 */
	public void setPaymentTarget(final String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}

	/**
	 * 获取优惠总额
	 */
	public Long getSumYouHuiAmount() {
		Long _sum = 0L;
		if (null != this.listAmountItem && !listAmountItem.isEmpty()) {
			for (OrdOrderAmountItem item : listAmountItem) {
				if (item.isCouponItem()) {
					_sum += item.getItemAmount() / 100;
				}
			}
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
					_rtn.append(item.getItemAmount() / 100 + ",");
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
	public Long getSumRefundmentAmount() {
		Long _sum = 0L;
		if (null != this.listOrdRefundment && !listOrdRefundment.isEmpty()) {
			for (OrdRefundment refundment : listOrdRefundment) {
				if (Constant.REFUND_TYPE.ORDER_REFUNDED.name()
						.equalsIgnoreCase(refundment.getRefundType())) {
					_sum += refundment.getAmount() / 100;
				}
			}
		}
		return _sum;
	}

	/**
	 * 获取补偿总额
	 * 
	 * @return
	 */
	public Long getSumCompensationAmount() {
		Long _sum = 0L;
		if (null != this.listOrdRefundment && !listOrdRefundment.isEmpty()) {
			for (OrdRefundment refundment : listOrdRefundment) {
				if (Constant.REFUND_TYPE.COMPENSATION.name()
						.equalsIgnoreCase(refundment.getRefundType())) {
					_sum += refundment.getAmount() / 100;
				}
			}
		}
		return _sum;
	}

	public String getSettlementStatus() {
		// 是否所有对象都结算
		boolean settlementStatus = true;
		// 是否存在已结算的对象
		boolean onceSettlementStatus = false;
		if (null != orderDetail
				&& null != orderDetail.getAllOrdOrderItemMetas()
				&& !orderDetail.getAllOrdOrderItemMetas().isEmpty()) {
			for (OrdOrderItemMeta orderItemMeta : orderDetail
					.getAllOrdOrderItemMetas()) {
				onceSettlementStatus = onceSettlementStatus
						|| Constant.SETTLEMENT_STATUS.SETTLEMENTED.name()
								.equalsIgnoreCase(
										orderItemMeta.getSettlementStatus());
				settlementStatus = settlementStatus
						&& Constant.SETTLEMENT_STATUS.SETTLEMENTED.name()
								.equalsIgnoreCase(
										orderItemMeta.getSettlementStatus());
			}
		}
		if (settlementStatus) {
			return "结算";
		} else {
			if (onceSettlementStatus) {
				return "部分结算";
			} else {
				return "未结算";
			}
		}
	}

	/**
	 * isOneCityOneCardFlag.
	 * 
	 * @return 一城卡支付标识
	 */
	public boolean isOneCityOneCardFlag() {
		return oneCityOneCardFlag;
	}

	/**
	 * setOneCityOneCardFlag.
	 * 
	 * @param oneCityOneCardFlag
	 *            一城卡支付标识
	 */
	public void setOneCityOneCardFlag(final boolean oneCityOneCardFlag) {
		this.oneCityOneCardFlag = oneCityOneCardFlag;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getPermId() {
		return permId;
	}
	public void setPermId(String permId) {
		this.permId = permId;
	}

	/**
	 * @return the useTravelGroup
	 */
	public boolean isUseTravelGroup() {
		return useTravelGroup;
	}
	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public void setComSmsTemplateService(ComSmsTemplateService comSmsTemplateService) {
		this.comSmsTemplateService = comSmsTemplateService;
	}
	public void setSmsRemoteService(final SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}
	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}
	public boolean isCanTransfer() {
		return canTransfer;
	}
	public boolean isHavePaymentPassword() {
		return havePaymentPassword;
	}
	public void setHavePaymentPassword(boolean havePaymentPassword) {
		this.havePaymentPassword = havePaymentPassword;
	}
	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}
	public List<OrdOrderAmountItem> getListAmountItem() {
		return listAmountItem;
	}
	public List<OrdRefundment> getListOrdRefundment() {
		return listOrdRefundment;
	}
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public TopicMessageProducer getResourceMessageProducer() {
		return resourceMessageProducer;
	}
	public void setResourceMessageProducer(TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public PageIndex getPageIndex() {
		return pageIndex;
	}
	public List<SortTypeEnum> getTypeList() {
		return typeList;
	}
	public void setWorkOrderFinishedProxy(
			WorkOrderFinishedBiz workOrderFinishedProxy) {
		this.workOrderFinishedProxy = workOrderFinishedProxy;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public Boolean getTempCloseCashAccountPay() {
		return tempCloseCashAccountPay;
	}

	public void setTempCloseCashAccountPay(Boolean tempCloseCashAccountPay) {
		this.tempCloseCashAccountPay = tempCloseCashAccountPay;
	}

	public boolean isShowSupplierChannelFlag() {
		return showSupplierChannelFlag;
	}

	public void setShowSupplierChannelFlag(boolean showSupplierChannelFlag) {
		this.showSupplierChannelFlag = showSupplierChannelFlag;
	}

	public boolean isShowSupplierChannelRecancelFlag() {
		return showSupplierChannelRecancelFlag;
	}

	public void setShowSupplierChannelRecancelFlag(
			boolean showSupplierChannelRecancelFlag) {
		this.showSupplierChannelRecancelFlag = showSupplierChannelRecancelFlag;
	}

	public boolean isShowSupplierChannelRepayFlag() {
		return showSupplierChannelRepayFlag;
	}

	public void setShowSupplierChannelRepayFlag(boolean showSupplierChannelRepayFlag) {
		this.showSupplierChannelRepayFlag = showSupplierChannelRepayFlag;
	}

	public void setOrdOrderSHHolidayService(
			OrdOrderSHHolidayService ordOrderSHHolidayService) {
		this.ordOrderSHHolidayService = ordOrderSHHolidayService;
	}

    public String getCancelReorderReason() {
        return cancelReorderReason;
    }

    public void setCancelReorderReason(String cancelReorderReason) {
        this.cancelReorderReason = cancelReorderReason;
    }
}
