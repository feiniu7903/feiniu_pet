package com.lvmama.order.service.proxy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.ebooking.OrdFaxTask;
import com.lvmama.comm.bee.po.ord.OrdEplaceOrderQuantity;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdInvoiceRelation;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountApply;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.po.ord.OrdOrderRoute;
import com.lvmama.comm.bee.po.ord.OrdOrderSum;
import com.lvmama.comm.bee.po.ord.OrdPerform;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.bee.po.ord.OrderInfoDTO;
import com.lvmama.comm.bee.po.pass.PassPortDetail;
import com.lvmama.comm.bee.po.pass.PassPortSummary;
import com.lvmama.comm.bee.po.prod.ProdProductItem;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.pub.ComAudit;
import com.lvmama.comm.bee.service.SupplierStockCheckService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.ord.OrderPerformService;
import com.lvmama.comm.bee.service.ord.OrderPersonService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.InvoiceResult;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.InvoiceRelate;
import com.lvmama.comm.bee.vo.ord.Invoice;
import com.lvmama.comm.bee.vo.ord.OrderPersonCount;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.bee.vo.ord.PriceInfo;
import com.lvmama.comm.bee.vo.ord.ResponseMessage;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.fin.SetSettlementItem;
import com.lvmama.comm.pet.po.money.CashDraw;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentRefundment;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.fin.SetTransferTaskService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.pet.service.sale.OrderRefundService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.favor.FavorProductResult;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.Pair;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.comm.vo.SupplierProductInfo;
import com.lvmama.comm.vst.service.VstOrdOrderService;
import com.lvmama.comm.vst.vo.VstOrdOrderVo;
import com.lvmama.order.aop.ValidityCheck;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.order.dao.OrderPersonDAO;
import com.lvmama.order.logic.BonusReturnLogic;
import com.lvmama.order.service.IOrderCancleService;
import com.lvmama.order.service.ModifyOrderAmountService;
import com.lvmama.order.service.OrderAuditService;
import com.lvmama.order.service.OrderCreateService;
import com.lvmama.order.service.OrderInvoiceService;
import com.lvmama.order.service.OrderItemMetaSaleAmountServie;
import com.lvmama.order.service.OrderMemoService;
import com.lvmama.order.service.OrderPaymentService;
import com.lvmama.order.service.OrderPriceService;
import com.lvmama.order.service.OrderRefundmentService;
import com.lvmama.order.service.OrderResourceService;
import com.lvmama.order.service.OrderRouteService;
import com.lvmama.order.service.OrderSockCheckService;
import com.lvmama.order.service.OrderUpdateService;
import com.lvmama.order.service.Query;
import com.lvmama.order.util.GeneralSequenceNo;
import com.lvmama.prd.dao.MetaTimePriceDAO;
import com.lvmama.prd.dao.ProdProductItemDAO;
import com.lvmama.prd.dao.ProdTimePriceDAO;
import com.lvmama.prd.logic.ProductTimePriceLogic;

/**
 * 订单服务代理.
 *
 * <pre>
 * <b>此类中所有方法输入参数都要经过{@link ValidityCheck}的非空校验</b>
 * </pre>
 */
public final class OrderServiceProxy implements OrderService {

	private Logger LOG = Logger.getLogger(this.getClass());
	/**
	 * 查询服务.
	 */
	private transient Query queryService;

	/**
	 * setQueryService.
	 * 
	 * @param queryService
	 *            查询服务
	 */
	public void setQueryService(final Query queryService) {
		this.queryService = queryService;
	}

	/**
	 * 领单服务.
	 */
	private transient OrderPriceService orderPriceService;
	/**
	 * 领单服务.
	 */
	private transient OrderAuditService orderAuditService;
	/**
	 * 订单创建服务.
	 */
	private transient OrderCreateService orderBuildServiceProxy;
	/**
	 * 订单更改服务.
	 */
	private transient OrderUpdateService orderUpdateService;
	/**
	 * 订单联系人服务.
	 */
	private transient OrderPersonService orderPersonService;
	/**
	 * 订单备注服务.
	 */
	private transient OrderMemoService orderMemoService;
	/**
	 * 订单资源服务.
	 */
	private transient OrderResourceService orderResourceService;
	/**
	 * 订单履行服务.
	 */
	private transient OrderPerformService orderPerformService;
	/**
	 * 订单发票服务.
	 */
	private transient OrderInvoiceService orderInvoiceService;
	/**
	 * 订单支付服务
	 */
	private transient OrderPaymentService orderPaymentService;
	/**
	 * OrdRefundment服务.
	 */
	private transient OrderRefundmentService orderRefundmentService;

	/**
	 * 修改订单关于团的服务.
	 */
	private transient OrderRouteService orderRouteService;
	/**
	 * FincCashService服务.
	 */
	private transient CashAccountService cashAccountService;
	
	/**
	 * 第三方库存检查
	 */
	private transient SupplierStockCheckService supplierStockCheckService;
	

	/**
	 * 订单电子合同信息
	 */
	private transient OrdEContractService ordEContractService;
	public void setOrdEContractService(OrdEContractService ordEContractService) {
		this.ordEContractService = ordEContractService;
	}

	/**
	 * 支付退款服务对象
	 */
	private PayPaymentRefundmentService payPaymentRefundmentService;
	/**
	 * PET公共资源消息发送器
	 */
	private TopicMessageProducer resourceMessageProducer;
	/**
	 * 
	 */
	private transient ModifyOrderAmountService modifyOrderAmountService;
	/**
	 * 消息发送接口
	 */
	private TopicMessageProducer orderMessageProducer;
	/**
	 * 订单二次跟踪处理,取特定取消的订单.
	 */
	private IOrderCancleService orderCancleService;

	private SettlementTargetService settlementTargetService; 
	
	private transient UserUserProxy userUserProxy;

	private transient OrderSockCheckService orderSockCheckService;

	private transient SupplierService supplierService;

	private transient OrderRefundService orderRefundService;
	
	private ProdProductItemDAO prodProductItemDAO;
	
	private ProdTimePriceDAO prodTimePriceDAO;
	
	private ProductTimePriceLogic productTimePriceLogic;
	
	private BonusReturnLogic bonusReturnLogic;

	private OrderItemMetaDAO orderItemMetaDAO;
	
	private OrderItemProdDAO orderItemProdDAO;
	
	private OrderDAO orderDAO;
	
	private SetTransferTaskService setTransferTaskService;
	
	private OrderItemMetaSaleAmountServie orderItemMetaSaleAmountServie;

	private MetaProductService metaProductService;
	
	private ProdProductService prodProductService;
	
	private SmsRemoteService smsRemoteService;

	private ComSmsTemplateService comSmsTemplateService;
	
	private OrderPersonDAO orderPersonDAO;
	
	private MetaTimePriceDAO metaTimePriceDAO;
	
	private FavorService favorService;
	
	private VstOrdOrderService vstOrdOrderService;
	
	public MetaProductService getMetaProductService() {
		return metaProductService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	/**
	 * 设置取特定取消的订单SERVICE.
	 * 
	 * @param orderCancleService
	 */
	public void setOrderCancleService(IOrderCancleService orderCancleService) {
		this.orderCancleService = orderCancleService;
	}

	/**
	 * setOrderMessageProducer
	 * 
	 * @param orderMessageProducer
	 *            消息发送接口
	 */
	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	/**
	 * setOrderRefundmentService.
	 * 
	 * @param orderRefundmentService
	 *            OrdRefundment服务
	 */
	public void setOrderRefundmentService(final OrderRefundmentService orderRefundmentService) {
		this.orderRefundmentService = orderRefundmentService;
	}
 
	/**
	 * setOrderInvoiceService.
	 * 
	 * @param orderInvoiceService
	 *            订单发票服务
	 */
	public void setOrderInvoiceService(final OrderInvoiceService orderInvoiceService) {
		this.orderInvoiceService = orderInvoiceService;
	}

	/**
	 * setOrderPerformService.
	 * 
	 * @param orderPerformService
	 *            订单履行服务
	 */
	public void setOrderPerformService(final OrderPerformService orderPerformService) {
		this.orderPerformService = orderPerformService;
	}

	/**
	 * setOrderResourceService.
	 * 
	 * @param orderResourceService
	 *            订单资源服务
	 */
	public void setOrderResourceService(final OrderResourceService orderResourceService) {
		this.orderResourceService = orderResourceService;
	}

	/**
	 * setOrderPriceService.
	 * 
	 * @param orderPriceService
	 *            订单价格计算服务
	 */
	public void setOrderPriceService(OrderPriceService orderPriceService) {
		this.orderPriceService = orderPriceService;
	}

	public void setModifyOrderAmountService(ModifyOrderAmountService modifyOrderAmountService) {
		this.modifyOrderAmountService = modifyOrderAmountService;
	}

	/**
	 * setOrderAuditService.
	 * 
	 * @param orderAuditServiceImpl
	 *            领单服务
	 */
	public void setOrderAuditService(final OrderAuditService orderAuditServiceImpl) {
		this.orderAuditService = orderAuditServiceImpl;
	}

	/**
	 * setOrderCreateService.
	 * 
	 * @param orderCreateServiceImpl
	 *            订单创建服务
	 */
	public void setOrderBuildServiceProxy(final OrderCreateService orderBuildServiceProxy) {
		this.orderBuildServiceProxy = orderBuildServiceProxy;
	}

	/**
	 * setOrderUpdateService.
	 * 
	 * @param orderUpdateServiceImpl
	 *            订单更改服务
	 */
	public void setOrderUpdateService(final OrderUpdateService orderUpdateServiceImpl) {
		this.orderUpdateService = orderUpdateServiceImpl;
	}

	
	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}

	
	public void setComSmsTemplateService(ComSmsTemplateService comSmsTemplateService) {
		this.comSmsTemplateService = comSmsTemplateService;
	}

	public void setOrderPersonDAO(OrderPersonDAO orderPersonDAO) {
		this.orderPersonDAO = orderPersonDAO;
	}

	/**
	 * setOrderPersonService.
	 * 
	 * @param orderPersonServiceImpl
	 *            订单联系人服务
	 */
	public void setOrderPersonService(final OrderPersonService orderPersonServiceImpl) {
		this.orderPersonService = orderPersonServiceImpl;
	}

	/**
	 * setOrderMemoService.
	 * 
	 * @param orderMemoServiceImpl
	 *            订单备注服务
	 */
	public void setOrderMemoService(final OrderMemoService orderMemoServiceImpl) {
		this.orderMemoService = orderMemoServiceImpl;
	}

	/**
	 * 订单关于团的附加表服务.
	 * 
	 * @param orderRouteService
	 */
	public void setOrderRouteService(OrderRouteService orderRouteService) {
		this.orderRouteService = orderRouteService;
	}

	public void setProdProductItemDAO(ProdProductItemDAO prodProductItemDAO) {
		this.prodProductItemDAO = prodProductItemDAO;
	}

	public void setProdTimePriceDAO(ProdTimePriceDAO prodTimePriceDAO) {
		this.prodTimePriceDAO = prodTimePriceDAO;
	}

	public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}

	/**
	 * 订单价格计算.
	 * 
	 * <pre>
	 * 前后台下单时使用
	 * </pre>
	 * 
	 * @param buyInfo
	 *            购买信息
	 * 
	 * @return PriceInfo
	 */
	@Override
	public PriceInfo countPrice(BuyInfo buyInfo) {
		return orderPriceService.countPrice(buyInfo);
	}

	@Override
	public ResultHandle checkOrderStock(BuyInfo info) {
		ResultHandleT<SupplierProductInfo> handle = orderSockCheckService.calcProductSell(info);
		if(handle.isFail()||handle.getReturnContent()==null||handle.getReturnContent().isEmpty()){
			return handle;
		}
		
		handle = supplierStockCheckService.checkStock(info,handle.getReturnContent());
		return handle;
	}

	/**
	 * 生成唯一序列号.
	 * 
	 * @return 唯一序列号
	 */
	@Override
	public String generateSerialNo() {
		return GeneralSequenceNo.generateSerialNo();
	}

	/**
	 * 生成流水号.
	 * 
	 * @return 唯一序列号
	 */
	@Override
	public String generateSequence() {
		return GeneralSequenceNo.generate();
	}

	/**
	 * 创建订单.
	 * 
	 * <pre>
	 * 前台下单时使用
	 * </pre>
	 * 
	 * @param buyInfo
	 *            购买信息
	 * 
	 * @return 创建的订单
	 */
	@Override
	public OrdOrder createOrder(final BuyInfo buyInfo) {
		initBuyInfo(buyInfo);
		OrdOrder order = orderBuildServiceProxy.createOrder(buyInfo);
		if (order != null) {
			orderMessageProducer.sendMsg(MessageFactory.newOrderCreateMessage(order.getOrderId()));
			return order;
		}
		
		return null;
	}
	private void initBuyInfo(BuyInfo buyInfo){
		if(StringUtils.isNotEmpty(buyInfo.getUserId())&&buyInfo.getUserNo()==null){
			UserUser user = userUserProxy.getUserUserByUserNo(buyInfo.getUserId());
			if(user!=null){
				buyInfo.setUserNo(user.getId());
			}
		}
	}

	/**
	 * 创建订单.
	 * 
	 * <pre>
	 * 后台下单时使用
	 * </pre>
	 * 
	 * @param buyInfo
	 *            购买信息
	 * @param operatorId
	 *            操作人ID
	 * 
	 * @return 创建的订单
	 */
	@Override
	public OrdOrder createOrderWithOperatorId(final BuyInfo buyInfo, final String operatorId) {
		initBuyInfo(buyInfo);
		OrdOrder order = orderBuildServiceProxy.createOrder(buyInfo, operatorId);
		if (order != null) {
			saveOrderMemo(buyInfo.getMemoList(), order.getOrderId(), operatorId);
			orderMessageProducer.sendMsg(MessageFactory.newOrderCreateMessage(order.getOrderId()));
			return order;
		} else
			return null;
	}

	/**
	 * 保存订单备注
	 * 
	 * @param orderMemo
	 */
	private void saveOrderMemo(List<OrdOrderMemo> orderMemo, Long orderId, String userId) {
		List<OrdOrderMemo> oomemoList = orderMemo;
		if (oomemoList != null && oomemoList.size() != 0) {
			for (OrdOrderMemo ordOrderMemo : oomemoList) {
				ordOrderMemo.setOrderId(orderId);
				ordOrderMemo.setOperatorName(userId);
				saveMemo(ordOrderMemo, userId);
			}
		}
	}
	/**
	 * 创建订单.
	 * 
	 * <pre>
	 * 后台废单重下时使用
	 * </pre>
	 * 
	 * @param buyInfo
	 *            购买信息
	 * @param oriOrderId
	 *            原订单ID
	 * @param operatorId
	 *            操作人ID
	 * 
	 * @return 创建的订单
	 */
	@Override
	public OrdOrder createOrderWithOrderId(final BuyInfo buyInfo, final Long oriOrderId, final String operatorId) {
		initBuyInfo(buyInfo);
		OrdOrder order = orderBuildServiceProxy.createOrder(buyInfo, oriOrderId, operatorId);
		if (order != null) {
			saveOrderMemo(buyInfo.getMemoList(), order.getOrderId(), operatorId);
			orderMessageProducer.sendMsg(MessageFactory.newOrderCreateMessage(order.getOrderId()));
			return order;
		} else
			return null;
	}

	/**
	 * 更改订单需重播.
	 * 
	 * @param redail
	 *            要更改的订单需重播
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改订单需重播成功，<code>false</code>代表更改订单需重播失败
	 * </pre>
	 */
	@Override
	public boolean updateRedail(final String redail, final Long orderId, final String operatorId) {
		return orderUpdateService.updateRedail(redail, orderId, operatorId);
	}

	/**
	 * 更改采购产品订单子项的传真备注.
	 * 
	 * @param memo
	 *            更改后的采购产品订单子项的传真备注
	 * @param ordOrderItemMetaId
	 *            采购产品订单子项ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改采购产品订单子项的传真备注成功，<code>false</code>
	 *         代表更改采购产品订单子项的传真备注失败
	 * </pre>
	 */
	@Override
	public boolean updateOrderItemMetaFaxMemo(final String memo, final Long ordOrderItemMetaId, final String operatorId) {
		return orderUpdateService.updateOrderItemMetaFaxMemo(memo, ordOrderItemMetaId, operatorId);
	}

	/**
	 * 更改订单支付等待时间.
	 * 
	 * @param waitPayment
	 *            更改后的订单支付等待时间，以分为单位
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改订单支付等待时间成功，<code>false</code>代表更改订单支付等待时间失败
	 * </pre>
	 */
	@Override
	public boolean updateWaitPayment(final Long waitPayment, final Long orderId, final String operatorId) {
		return orderUpdateService.updateWaitPayment(waitPayment, orderId, operatorId);
	}

	/**
	 * 更改订单售后服务标志.
	 * 
	 * @param needSaleService
	 *            订单售后服务标志
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改订单售后服务标志成功，<code>false</code>代表更改订单售后服务标志失败
	 * </pre>
	 */
	@Override
	public boolean updateNeedSaleService(final String needSaleService, final Long orderId, final String operatorId) {
		return orderUpdateService.updateNeedSaleService(needSaleService, orderId, operatorId);
	}

	/**
	 * 综合订单查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * 指定查询条件的订单列表，如果指定查询条件没有对应订单，则返回元素数为0的订单列表，
	 * 使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code> <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	@Override
	public List<OrdOrder> compositeQueryOrdOrder(final CompositeQuery compositeQuery) {
		List<OrdOrder> ordOrderList = queryService.compositeQueryOrdOrder(compositeQuery);
		if(compositeQuery.getQueryFlag().isQuerySupplier()){
			this.initOrdOrderItemMetaSupSupplier(ordOrderList);
		}
		if(compositeQuery.getQueryFlag().isQueryUser()){
			initOrdOrderUser(ordOrderList);
		}
		return ordOrderList;
	}

	private List<String> initUserNo(List<OrdOrder> ordOrderList) {
		List<String> userIdList = new ArrayList<String>();
		OrdOrder ordOrder = null;
		for (int i = 0; i < ordOrderList.size(); i++) {
			ordOrder = ordOrderList.get(i);
			userIdList.add(ordOrder.getUserId());
		}
		return userIdList;
	}

	/**
	 * 初始化订单user数据
	 * 
	 * @param order
	 * @return
	 */
	private void initOrdOrderUser(List<OrdOrder> ordOrderList) {
		// 初始user
		List<String> userNoList = initUserNo(ordOrderList);
		List<UserUser> userUserList = userUserProxy.getUsersListByUserNoList(userNoList);

		// 设置订单user
		final Map<String, UserUser> map = new Hashtable<String, UserUser>();
		for (UserUser item : userUserList) {
			map.put(item.getUserNo(), item);
		}
		OrdOrder order = null;
		for (int i = 0; i < ordOrderList.size(); i++) {
			order = ordOrderList.get(i);
			final UserUser user = map.get(order.getUserId());
			order.setUser(user);
			if (UtilityTool.isValid(user)) {
				order.setUserName(user.getUserName());
				order.setRealName(user.getRealName());
				order.setMobileNumber(user.getMobileNumber());
				order.setGender(user.getGender());
			}
		}
	}

	/**
	 * 轻量级综合订单查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * 与综合订单查询相同，但只会返回订单基本数据，而不填充任何附加数据。此方法是一种
	 * 性能优先的查询。
	 * </pre>
	 */
	@Override
	public List<OrdOrder> lightedCompositeQueryOrdOrder(final CompositeQuery compositeQuery) {
		return queryService.lightedCompositeQueryOrdOrder(compositeQuery);
	}

	public OrdOrderSum compositeQueryOrdOrderSum(final CompositeQuery compositeQuery) {
		return queryService.compositeQueryOrdOrderSum(compositeQuery);
	}

	/**
	 * 根据订单ID查询订单.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的订单，包含相关销售产品，相关采购产品，相关供应商等信息，如果指定ID的订单不存在则返回<code>null</code>
	 * </pre>
	 */
	@Override
	public OrdOrder queryOrdOrderByOrderId(final Long orderId) {
		OrdOrder order = queryService.queryOrdOrder(orderId);
		if (order == null) {
			return null;
		}

		List<OrdOrder> orderList = new ArrayList<OrdOrder>();
		orderList.add(order);
		this.initOrdOrderItemMetaSupSupplier(orderList);
		this.initOrdOrderUser(orderList);
		return orderList.get(0);

	}
	
	
	@Override
	public OrdOrder queryUserFirstOrder(String userId) {
		Long orderId=this.orderDAO.queryUserFirstOrderId(userId);
		if(null==orderId){
			return null;
		}
		return this.queryOrdOrderByOrderId(orderId);
	}
	
	@Override
	public Long queryUserOrderVisitTimeGreaterCounts(String userId) {
		return orderDAO.queryUserOrderVisitTimeGreaterCounts(userId);
	}
	

	private void initOrdOrderItemMetaSupSupplier(List<OrdOrder> ordOrderList) {
		Set<Long> ids=new HashSet<Long>();
		for(OrdOrder order:ordOrderList){
			for(OrdOrderItemMeta ooim:order.getAllOrdOrderItemMetas()){
				ids.add(ooim.getSupplierId());
			}
		}
		List<Long> list = new ArrayList<Long>(ids);
		Map<Long, SupSupplier> supSupplierMap = supplierService.getSupSupplierBySupplierId(list);
		for(OrdOrder order:ordOrderList){
			for(OrdOrderItemMeta ooim:order.getAllOrdOrderItemMetas()){
				ooim.setSupplier(supSupplierMap.get(ooim.getSupplierId()));
			}
		}
	}

	@Override
	public OrdOrder queryOrdOrderByOrderIdRefund(final Long refundmentId, final Long orderId) {
		OrdOrder ordOrder = queryService.queryOrdOrderItemMetaListRefund(refundmentId, orderId);
		List<OrdOrder> orderList = new ArrayList<OrdOrder>();
		orderList.add(ordOrder);
		initOrdOrderUser(orderList);
		return orderList.get(0);
	}

	/**
	 * 根据通关码申请流水号查询订单.
	 * 
	 * @param serialNo
	 *            通关码申请流水号
	 * @return <pre>
	 * 指定通关码申请流水号的订单，包含相关销售产品，相关采购产品，相关供应商等信息，
	 * 如果指定通关码申请流水号的订单不存在则返回<code>null</code>
	 * </pre>
	 */
	@Override
	public OrdOrder queryOrdOrderBySerialNo(final String serialNo) {
		final Long orderId = queryService.queryOrdOrderIdBySerialNo(serialNo);
		return queryOrdOrderByOrderId(orderId);
	}

	/**
	 * 更新订单信息.
	 * 
	 * @param order
	 */
	@Override
	public void updateOrdOrderByPrimaryKey(final OrdOrder order) {
		orderUpdateService.updateOrdOrderByPrimaryKey(order);
	}
	
	@Override
	public boolean updateCashRefund(Long orderId, Long cashRefund) {
		return orderUpdateService.updateCashRefund(orderId, cashRefund);
	}

	/**
	 * 向指定ID订单添加{@link Person}.
	 * 
	 * @param person
	 *            {@link Person}
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表向指定ID订单添加{@link Person}成功，
	 * <code>false</code>代表向指定ID订单添加{@link Person}失败
	 * </pre>
	 */
	@Override
	public boolean addPerson2OrdOrder(final Person person, final Long orderId, final String operatorId) {
		return orderPersonService.addPerson2OrdOrder(person, orderId, operatorId);
	}

	@Override
	public boolean addPerson2Invoice(Person person, Long invoiceId, String operatorId) {
		return orderPersonService.insertInvoicePerson(person, invoiceId, operatorId);
	}

	/**
	 * 根据订单ID查询{@link Person}.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的{@link Person}，如果指定订单ID的{@link Person}不存在，
	 * 则返回元素数为0的{@link Person}列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	@Override
	public List<Person> queryPersonByOrderId(final Long orderId) {
		return orderPersonService.queryPersonByOrderId(orderId);
	}

	/**
	 * 移除指定ID订单的{@link Person}.
	 * 
	 * @param personId
	 *            {@link Person}的ID
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表移除指定ID订单的{@link Person}成功，
	 * <code>false</code>代表移除指定ID订单的{@link Person}失败
	 * </pre>
	 */
	@Override
	public boolean removePersonFromOrdOrder(final Long personId, final Long orderId, final String operatorId) {
		return orderPersonService.removePersonFromOrdOrder(personId, orderId, operatorId);
	}

	/**
	 * 更改指定ID订单的{@link Person}.
	 * 
	 * @param person
	 *            {@link Person}
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改指定ID订单的{@link Person}成功，
	 * <code>false</code>代表更改指定ID订单的{@link Person}失败
	 * </pre>
	 */
	@Override
	public boolean updatePerson(final Person person, final Long orderId, final String operatorId) {
		return orderPersonService.updatePerson(person, orderId, operatorId);
	}

	/**
	 * 根据订单ID查询订单备注.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的订单备注列表，如果指定订单ID没有对应订单备注， 则返回元素数为0的订单备注列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	@Override
	public List<OrdOrderMemo> queryMemoByOrderId(final Long orderId) {
		return orderMemoService.queryMemoByOrderId(orderId);
	}

	/**
	 * 取消订单领单.
	 * 
	 * @param operatorId
	 *            操作人ID
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * <code>true</code>代表取消订单领单成功，<code>false</code>代表取消订单领单失败
	 * </pre>
	 */
	@Override
	public boolean cancelOrdOrderAudit(final String operatorId, final Long orderId) {
		return orderAuditService.cancelOrdOrderAudit(operatorId, orderId);
	}

	public boolean cancelOrdOrderAuditByOrderId(String operator, Long orderId) {
		return orderAuditService.cancelOrdOrderAuditByOrderId(operator, orderId);
	}

	public List<ComAudit> selectComAuditList(final Map<String, String> params) {
		return orderAuditService.selectComAuditByParam(params);
	}

	@Override
	public Long selectComAuditCountByParams(Map<String, Object> params){
		return  orderAuditService.selectComAuditCountByParams(params);
	}
	/**
	 * 取消采购产品订单子项领单.
	 * 
	 * @param operatorId
	 *            操作人ID
	 * @param ordOrderItemMetaId
	 *            采购产品订单子项ID
	 * @return <pre>
	 * <code>true</code>代表取消采购产品订单子项领单成功，<code>false</code>代表取消采购产品订单子项领单失败
	 * </pre>
	 */
	@Override
	public boolean cancelOrdOrderItemMetaAudit(final String operatorId, final Long ordOrderItemMetaId) {
		return orderAuditService.cancelOrdOrderItemMetaAudit(operatorId, ordOrderItemMetaId);
	}

	/**
	 * 根据订单类型领单.
	 * 
	 * @param operatorId
	 *            领单人ID
	 * @param orderType
	 *            订单类型
	 * @return 订单
	 */
	@Override
	public OrdOrder makeOrdOrderAudit(final String operatorId, final String orderType) {
		return orderAuditService.makeOrdOrderAudit(operatorId, orderType);
	}
	
	/**
	 * 根据订单id领单.
	 * 
	 * @param operatorId
	 *            领单人ID
	 * @param orderId
	 *            订单Id
	 * @return 订单
	 */
	@Override
	public OrdOrder makeOrdOrderAuditById(final String operatorId, final Long orderId) {
		return orderAuditService.makeOrdOrderAuditById(operatorId, orderId);
	}

	/**
	 * 根据订单编号分配无需审核的订单
	 * 
	 * @param operator
	 * @param orderId
	 * @param assignUser
	 * @return
	 */
	@Override
	public boolean makeOrdOrderConfirmAuditByOrderId(String operator, Long orderId, String assignUser) {
		return orderAuditService.makeOrdOrderConfirmAuditByOrderId(operator, orderId, assignUser);
	}

	/**
	 * 根据销售产品类型领单.
	 * 
	 * @param operatorId
	 *            领单人ID
	 * @param productType
	 *            销售产品类型
	 * @return 采购产品订单子项
	 */
	@Override
	public OrdOrderItemMeta makeOrdOrderItemMetaAudit(final String operatorId, final String productType) {
		return orderAuditService.makeOrdOrderItemMetaAudit(operatorId, productType);
	}

	/**
	 * 根据订单编号OrdOrder领单（分单）.
	 * 
	 * @author luoyinqi
	 * 
	 * @param operatorId
	 *            领单人ID
	 * @param orderId
	 *            订单编号
	 * @return row
	 */
	public boolean makeOrdOrderAuditByOrderId(final String operatorId, final Long orderId, String assignUser) {
		return orderAuditService.makeOrdOrderAuditByOrderId(operatorId, orderId, assignUser);
	}

	/**
	 * 批量采购产品订单子项领单.
	 * 
	 * @param operatorId
	 *            领单人ID
	 * @param orderItemMetaIdList
	 *            批量采购产品订单子项ID
	 * @return <pre>
	 * <code>true</code>代表批量采购产品订单子项领单成功，<code>false</code>代表批量采购产品订单子项领单失败
	 * </pre>
	 */
	@Override
	public List<OrdOrderItemMeta> makeOrdOrderItemMetaListToAudit(final String operatorId, final List<Long> orderItemMetaIdList) {
		return orderAuditService.makeOrdOrderItemMetaListToAudit(operatorId, orderItemMetaIdList);
	}

	/**
	 * 指定销售产品OrdOrderItemMeta领单批量.
	 *
	 * @param operatorId
	 *            领单人ID
	 * @param itemMetaIdList
	 *            销售产品类型
	 * @return <pre>
	 * <code>true</code>代表领单成功，<code>false</code>代表领单失败
	 * </pre>
	 */
	@Override
	public boolean makeOrdOrderItemMetaToAuditByAssignUser(String operatorId,String assignUserId,OrdOrderItemMeta item){
		return orderAuditService.makeOrdOrderItemMetaToAuditByAssignUser(operatorId, assignUserId,item);
	}
	/**
	 * 修改订单下单人userId.
	 * 
	 * @param orderId
	 *            订单ID
	 * 
	 * @param userId
	 *            下单人ID
	 * 
	 * @param operatorName
	 *            操作人登录名
	 * @return <pre>
	 * <code>true</code>代表修改成功，<code>false</code>代表修改失败
	 * </pre>
	 */
	public boolean updateUserIdForOrder(Long orderId, String userId, String operatorName) {
		return orderUpdateService.updateUserIdForOrder(orderId, userId, operatorName);
	}

	/**
	 * 取消订单.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param reason
	 *            取消原因
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表取消订单成功，<code>false</code>代表取消订单失败
	 * </pre>
	 */
	@Override
	public boolean cancelOrder(final Long orderId, final String reason, final String operatorId) {
		boolean successFlag = orderUpdateService.cancelOrder(orderId, reason, operatorId, null);
		if (successFlag) {
			orderMessageProducer.sendMsg(MessageFactory.newOrderCancelMessage(orderId));
			return true;
		} else
			return false;
	}

    public boolean cancelOrder(Long orderId, String reason, String operatorId, String cancelReorderReason) {
        boolean successFlag = orderUpdateService.cancelOrder(orderId, reason, operatorId,cancelReorderReason);
        if (successFlag) {
            orderMessageProducer.sendMsg(MessageFactory.newOrderCancelMessage(orderId));
            return true;
        } else
            return false;
    }

    /**
	 * 取消订单.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param reason
	 *            取消原因
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表取消订单成功，<code>false</code>代表取消订单失败
	 * </pre>
	 */
	@Override
	public boolean cancelVstOrder(final Long orderId, final String reason, final String operatorId) {
		boolean successFlag = vstOrdOrderService.cancelOrder(orderId, null, reason, operatorId, null);
		return successFlag;
	}

	/**
	 * 批量取消订单.
	 * 
	 * @param orderIdArray
	 *            订单ID数组
	 * @param reason
	 *            取消原因
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表批量取消订单成功，<code>false</code>代表批量取消订单失败
	 * </pre>
	 */
	@Override
	public boolean cancelOrderWithArray(final Long[] orderIdArray, final String reason, final String operatorId) {
		for (Long orderId : orderIdArray) {
			orderUpdateService.cancelOrder(orderId, reason, operatorId, null);
		}
		return true;
	}

	/**
	 * 恢复订单.
	 * 
	 * @param orderId
	 *            订单ID
	 * 
	 * @param operatorName
	 *            操作人登录用户名
	 * 
	 * @return <pre>
	 * <code>true</code>代表恢复订单成功，<code>false</code>代表恢复订单失败
	 * </pre>
	 */
	@Override
	public ResponseMessage restoreOrder(Long orderId, String operatorName) {
		ResponseMessage responseMessage = orderUpdateService.restoreOrder(orderId, operatorName);
		if (responseMessage.getSuccessFlag()) {
			orderMessageProducer.sendMsg(MessageFactory.newOrderRestoreMessage(orderId));
		}

		return responseMessage;
	}

	/**
	 * 批量保存订单备注.
	 * 
	 * @param memoList
	 *            批量订单备注
	 * @param operatorId
	 *            操作人ID
	 * @return 批量保存的订单备注
	 */
	@Override
	public List<OrdOrderMemo> saveMemoList(final List<OrdOrderMemo> memoList, final String operatorId) {
		return orderMemoService.saveMemoList(memoList, operatorId);
	}

	/**
	 * 保存订单备注.
	 * 
	 * @param memo
	 *            订单备注
	 * @param operatorId
	 *            操作人ID
	 * @return 保存的订单备注
	 */
	@Override
	public OrdOrderMemo saveMemo(final OrdOrderMemo memo, final String operatorId) {
		return orderMemoService.saveMemo(memo, operatorId);
	}

	/**
	 * 删除订单备注.
	 * 
	 * @param memoId
	 *            订单备注ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表删除订单备注成功，<code>false</code>代表删除订单备注失败
	 * </pre>
	 */
	@Override
	public boolean deleteMemo(final Long memoId, final String operatorId) {
		return orderMemoService.deleteMemo(memoId, operatorId);
	}

	/**
	 * 通过主键来选择订单备注.
	 * 
	 * @param memoId
	 *            订单备注ID
	 * 
	 * @return OrdOrderMemo 订单备注
	 */
	@Override
	public OrdOrderMemo selectMemo(Long memoId) {
		return orderMemoService.selectMemo(memoId);
	}

	/**
	 * 修改订单备注.
	 * 
	 * @param memo
	 *            订单备注
	 * 
	 * @param operatorId
	 *            操作人ID
	 * 
	 * @return 修改订单备注是否成功
	 */
	@Override
	public boolean updateMemo(OrdOrderMemo memo, String operatorId) {
		return orderMemoService.updateMemo(memo, operatorId);
	}

	/**
	 * 综合订单查询计数.
	 * 
	 * <pre>
	 * 分页查询使用
	 * </pre>
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 满足综合查询条件的订单总数
	 */
	@Override
	public Long compositeQueryOrdOrderCount(final CompositeQuery compositeQuery) {
		return queryService.compositeQueryOrdOrderCount(compositeQuery);
	}

	/**
	 * 订单信息审核通过.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表订单信息审核通过成功，<code>false</code>代表订单信息审核通过失败
	 * </pre>
	 */
	@Override
	public boolean approveInfoPass(final Long orderId, final String operatorId) {
		boolean successFlag = orderUpdateService.updateOrdOrderApproveStatus(orderId, Constant.INFO_APPROVE_STATUS.INFOPASS.name(), operatorId);
		return successFlag;
	}

	/**
	 * 批量订单信息审核通过.
	 * 
	 * @param orderIdArray
	 *            订单ID数组
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表批量订单信息审核通过成功，<code>false</code>代表批量订单信息审核通过失败
	 * </pre>
	 */
	@Override
	public boolean approveInfoPassWithArray(final Long[] orderIdArray, final String operatorId) {
		for (Long orderId : orderIdArray) {
			approveInfoPass(orderId, operatorId);
		}
		return true;
	}

	/**
	 * 订单审核通过.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表订单审核通过成功，<code>false</code>代表订单审核通过失败
	 * </pre>
	 */
	@Override
	public boolean approveVerified(final Long orderId, final String operatorId) {
		boolean successFlag = orderUpdateService.updateOrdOrderApproveStatus(orderId, Constant.ORDER_APPROVE_STATUS.VERIFIED.name(), operatorId);
		if (successFlag) {
			orderMessageProducer.sendMsg(MessageFactory.newOrderApproveMessage(orderId));
			return true;
		} else
			return false;
	}

	/**
	 * 批量订单审核通过.
	 * 
	 * @param orderIdArray
	 *            订单ID数组
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表批量订单审核通过成功，<code>false</code>代表批量订单审核通过失败
	 * </pre>
	 */
	@Override
	public boolean approveVerifiedWithArray(final Long[] orderIdArray, final String operatorId) {
		for (Long orderId : orderIdArray) {
			approveVerified(orderId, operatorId);
		}

		return true;
	}

	/**
	 * 根据采购产品订单子项ID取相应的订单.
	 * 
	 * @param orderItemId
	 *            采购产品订单子项ID
	 * @return
	 */
	@Override
	public OrdOrder findOrderByOrderItemMetaId(final Long orderItemId) {
		return orderAuditService.getOrderByOrderItemMetaId(orderItemId);
	}

	/**
	 * 采购产品订单子项资源不满足(订单未付的资源不满足).
	 * 
	 * @param orderItemId
	 *            采购产品订单子项ID
	 * @param operatorId
	 *            操作人ID
	 * 
	 *            <pre>
	 * 当所有采购产品订单子项资源都为不满足时，订单资源也变为不满足
	 * </pre>
	 * @return <pre>
	 * <code>true</code>代表采购产品订单子项资源不满足成功，<code>false</code>代表采购产品订单子项资源不满足失败
	 * </pre>
	 */
	@Override
	public boolean resourceLack(final Long orderItemId, final String operatorId, final String resourceLackReason) {
		boolean result = orderResourceService.updateOrderItemResource(orderItemId, Constant.ORDER_RESOURCE_STATUS.LACK.name(), operatorId, null, resourceLackReason);
		return result;
	}

	/**
	 * 采购产品订单子项资源满足.
	 * 
	 * @param orderItemId
	 *            采购产品订单子项ID
	 * @param operatorId
	 *            操作人ID
	 * 
	 *            <pre>
	 * 当所有采购产品订单子项资源都为不满足时，订单资源也变为不满足
	 * </pre>
	 * @return <pre>
	 * <code>true</code>代表采购产品订单子项资源不满足成功，<code>false</code>代表采购产品订单子项资源不满足失败
	 * </pre>
	 */
	@Override
	public boolean resourceAmple(final Long orderItemId, final String operatorId, final String resourceLackReason, Date retentionTime) {
		boolean result = orderResourceService.updateOrderItemResource(orderItemId, Constant.ORDER_RESOURCE_STATUS.AMPLE.name(), operatorId, retentionTime, resourceLackReason);
		OrdOrder order = this.queryOrdOrderByOrderItemMetaId(orderItemId);
		LOG.info("updateOrderItemResource orderItemId: " + orderItemId + " result: " + result);
		if (result && order.isApprovePass()) {
			Message msg = MessageFactory.newOrderApproveMessage(order.getOrderId());
			msg.setAddition(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
			orderMessageProducer.sendMsg(msg);
		}
		if (order.getOughtPay() == 0) {
			orderMessageProducer.sendMsg(MessageFactory.newOrderPay0YuanMessage(order.getOrderId()));
		}
		//资源满足JMS
		orderMessageProducer.sendMsg(MessageFactory.newOrderResourceMessage(order.getOrderId()));
		return result;
	}
	
	/**
	 * VST采购产品订单子项资源满足.
	 * 
	 * @param orderId
	 *            采购产品订单ID
	 * 
	 *            <pre>
	 * 当所有采购产品订单子项资源都为不满足时，订单资源也变为不满足
	 * </pre>
	 * @return <pre>
	 * <code>true</code>代表采购产品订单子项资源不满足成功，<code>false</code>代表采购产品订单子项资源不满足失败
	 * </pre>
	 */
	@Override
	public void resourceAmpleVst(final Long orderId) {
		LOG.info("resourceAmpleVst start "+orderId);
		//VST处理（更改采购产品订单子项资源状态.）
		VstOrdOrderVo vstOrdOrderVo = vstOrdOrderService.getVstOrdOrderVo(orderId);
		OrdOrder order = new OrdOrder();//approveStatus oughtPay
		order.setOughtPay(vstOrdOrderVo.getOughtAmount());
		order.setApproveStatus(vstOrdOrderVo.getResourceStatus());
		order.setOrderId(vstOrdOrderVo.getOrderId());
		//
		if ("AMPLE".equalsIgnoreCase(order.getApproveStatus())) {	//
			Message msg = MessageFactory.newOrderApproveMessage(order.getOrderId());
			msg.setAddition(Constant.PAYMENT_BIZ_TYPE.VST_ORDER.name());
			orderMessageProducer.sendMsg(msg);
		}
		if (order.getOughtPay() == 0) {
			Message msg = MessageFactory.newOrderPay0YuanMessage(order.getOrderId());
			msg.setAddition(Constant.PAYMENT_BIZ_TYPE.VST_ORDER.name());
			orderMessageProducer.sendMsg(msg);
		}
		LOG.info("resourceAmpleVst end "+orderId);
		return;
	}

	/**
	 * 资源调整为待跟进
	 * 
	 * @param orderItemId
	 *            采购产品订单子项ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表成功，<code>false</code>代表失败
	 * </pre>
	 */
	@Override
	public boolean resourceBefollowup(final Long orderItemId, final String operatorId) {
		boolean result = orderResourceService.updateOrderItemResource(orderItemId, Constant.ORDER_RESOURCE_STATUS.BEFOLLOWUP.name(), operatorId, null, Constant.NULL);
		// TODO liuboen 待跟进不需要 OrdOrder order =
		// orderAuditService.getOrderByOrderItemMetaId(orderItemId);
		return result;
	}

	/**
	 * 批量采购产品订单子项资源不满足.
	 * 
	 * @param orderItemIdArray
	 *            采购产品订单子项ID数组
	 * @param operatorId
	 *            操作人ID
	 * 
	 *            <pre>
	 * 当所有采购产品订单子项资源都为不满足时，订单资源也变为不满足
	 * </pre>
	 * @return <pre>
	 * <code>true</code>代表批量采购产品订单子项资源不满足成功，
	 * <code>false</code>代表批量采购产品订单子项资源不满足失败
	 * </pre>
	 */
	@Override
	public boolean resourceLackWithArray(final Long[] orderItemIdArray, final String operatorId) {
		for (Long orderItemId : orderItemIdArray) {
			orderResourceService.updateOrderItemResource(orderItemId, Constant.ORDER_RESOURCE_STATUS.LACK.name(), operatorId, null, Constant.NULL);
			OrdOrder order = orderAuditService.getOrderByOrderItemMetaId(orderItemId);
			if (order.isNormal()) {
				orderUpdateService.cancelOrder(order.getOrderId(), "资源审核不通过直接废单", "SYSTEM", null);
				orderMessageProducer.sendMsg(MessageFactory.newOrderCancelMessage(order.getOrderId()));
			}
		}
		return true;
	}

	/**
	 * 根据采购产品订单子项ID更新传真备注.
	 * 
	 * @param ordOrderItemMetaId
	 *            采购产品订单子项ID
	 * @param faxMemo
	 *            更新后的传真备注
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表根据采购产品订单子项ID更新传真备注成功，<code>false</code>
	 *         代表根据采购产品订单子项ID更新传真备注失败
	 * </pre>
	 */
	@Override
	public boolean updateFaxMemo(final Long ordOrderItemMetaId, final String faxMemo, final String operatorId) {
		return orderUpdateService.updateFaxMemo(ordOrderItemMetaId, faxMemo, operatorId);
	}

	/**
	 * 根据采购产品订单子项ID更新传真备注.
	 * 
	 * @param ordOrderItemMetaIdList
	 *            采购产品订单子项ID列表
	 * @param faxMemo
	 *            更新后的传真备注
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表根据采购产品订单子项ID更新传真备注成功，<code>false</code>
	 *         代表根据采购产品订单子项ID更新传真备注失败
	 * </pre>
	 */
	@Override
	public boolean updateFaxMemoByorderItemMetaIdList(final List<Long> ordOrderItemMetaIdList, final String faxMemo, final String operatorId) {
		return orderUpdateService.updateFaxMemoByorderItemMetaIdList(ordOrderItemMetaIdList, faxMemo, operatorId);
	}

	/**
	 * 订单完成.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表订单完成成功，<code>false</code>
	 *         代表订单完成失败
	 * </pre>
	 */
	@Override
	public boolean finishOrder(final Long orderId, final String operatorId) {
		return false;
	}

	/**
	 * 新增履行信息.
	 * 
	 * @param performTargetId
	 *            履行对象ID
	 * @param objectId
	 *            订单ID或订单子子项ID
	 * @param objectType
	 *            objectId指向的类型
	 * @param adultQuantity
	 *            该采购产品的成人数量
	 * @param childQuantity
	 *            该采购产品的儿童数量
	 * 
	 * @return insert履行信息是否成功
	 */
	@Override
	public boolean insertOrdPerform(Long performTargetId, Long objectId, String objectType, Long adultQuantity, Long childQuantity) {
		return insertOrdPerform(performTargetId, objectId,
				 objectType,  adultQuantity, childQuantity,null);

	}
	@Override
	public boolean insertOrdPerform(Long performTargetId, Long objectId,
			String objectType, Long adultQuantity, Long childQuantity,String memo) {
		String key = "ORDER_SERVICE_ORD_PERFORM_" + performTargetId + "_" + objectId + "_" + objectType;
		if (SynchronizedLock.isOnDoingMemCached(key)) {
			return false;
		}
		boolean result = true;
		try {
			Long orderId = orderPerformService.insertOrdPerform(
					performTargetId, objectId, objectType, adultQuantity,
					childQuantity,memo);
			boolean allPerformed = orderPerformService
					.checkAllPerformed(orderId);
			if (allPerformed) {
				orderMessageProducer.sendMsg(MessageFactory.newOrderPerformMessage(orderId));
			}
		} catch (Exception e) {
			// result = false;
			e.printStackTrace();
		} finally {
			SynchronizedLock.releaseMemCached(key);
		}
		return result;
	}
	/**
	 * 查询{@link OrderAndComment}.
	 * 
	 * <pre>
	 * 返现使用(用户游玩后4月内点评互动，且订单履行时间在4个月内的)
	 * </pre>
	 * 
	 * @return <pre>
	 * 如果没有{@link OrderAndComment}， 则返回元素数为0的{@link OrderAndComment}列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	@Override
	public List<OrderAndComment> queryOrderAndCommentOnPeriod(final Map<String, Object> map ) {
		List<OrderAndComment> list = queryService.queryOrderAndCommentOnPeriod(map);
        return list;
        //不需要设置用户名
		//return initUsername(list);
	}

	/**
	 * 根据订单ID查询{@link OrderAndComment}.
	 * 
	 * <pre>
	 *  当前时间,该订单履行状态是否能返现,没状态时间限制.
	 * </pre>
	 * 
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的{@link OrderAndComment}，
	 * 如果指定订单ID没有对应{@link OrderAndComment}，则返回<code>null</code>
	 * </pre>
	 */
	@Override
	public List<OrderAndComment> selectCanRefundOrderByOrderId(final Long orderId) {
		List<OrderAndComment> list = queryService.selectCanRefundOrderByOrderId(orderId);
		return list;
	}

	/**
	 * 根据订单ID查询订单是否待返现
	 * 
	 * @param orderId
	 *            订单ID
	 * @return {@link OrdOrder}
	 */
	@Override
	public OrderAndComment queryCanRefundOrderByOrderId(Long orderId) {
		OrderAndComment oa = queryService.queryCanRefundOrderByOrderId(orderId);
		if (oa == null) {
			return oa;
		} else {
			List<OrderAndComment> list = new ArrayList<OrderAndComment>();
			list.add(oa);
			return list.get(0);
		}

	}
	
	/**
	 * 获取可点评的订单产品信息
	 * @param userNo
	 * @return
	 */
	@Override
	public List<OrderAndComment> selectCanCommentOrderProductByUserNo(final String userNo)
	{
		return queryService.selectCanCommentOrderProductByUserNo(userNo);
	}
	
	/**
	 * 获取可点评的订单产品信息，用于发送站内信
	 * @return List<OrderAndComment>
	 * @return
	 */
	@Override
	public List<OrderAndComment> selectCanCommentOrderProductByDate()
	{
		return queryService.selectCanCommentOrderProductByDate();
	}


	/**
	 * 新增OrdInvoice
	 * 
	 * @param invoice
	 * @param orderIds
	 *            订单ID列表
	 * @param operatorId
	 *            操作人ID
	 * @return
	 */
	public ResultHandle insertInvoiceByOrders(final List<Pair<Invoice,Person>> invoices, final List<Long> orderIds, final String operatorId) {
		ResultHandle handle = new ResultHandle();
		if (CollectionUtils.isEmpty(orderIds)) {
			handle.setMsg("订单号为空");
		}else{
			try {
				if(orderIds.size()==1){
					orderInvoiceService.insert(invoices, orderIds.get(0), operatorId);
				}else if(invoices.size()>1){
					throw new Exception("多个订单号不可开多张发票");
				}else{
					orderInvoiceService.insert(invoices.get(0), orderIds, operatorId);
				}
			} catch (Exception ex) {
				handle.setMsg(ex.getMessage());
			}
		}
		return handle;
	}

	/**
	 * 删除OrdInvoice.
	 * 
	 * @param invoiceId
	 *            发票ID
	 * @param operatorId
	 *            操作人ID
	 * @return 删除发票是否成功
	 */
	@Override
	public boolean delete(final Long invoiceId, final String operatorId) {
		return orderInvoiceService.delete(invoiceId, operatorId);
	}

	/**
	 * 修改OrdInvoice.
	 * 
	 * @param ordInvoice
	 *            发票对象
	 * @param operatorId
	 *            操作人ID
	 * @return 修改发票是否成功
	 */
	@Override
	public boolean updateOrdInvoice(final OrdInvoice ordInvoice, final String operatorId) {
		return orderInvoiceService.update(ordInvoice, operatorId);
	}

	/**
	 * 查询OrdInvoice.
	 * 
	 * @param orderId
	 *            订单ID
	 * 
	 * @return <pre>
	 * 指定订单ID的OrdInvoice列表，如果指定订单ID没有对应OrdInvoice， 则返回元素数为0的OrdInvoice列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回
	 * <code>null</code>
	 * </pre>
	 * 
	 */
	@Override
	public List<OrdInvoice> queryInvoiceByOrderId(final Long orderId) {
		List<OrdInvoice> list = orderInvoiceService.queryInvoiceByOrderId(orderId);
		InvoiceRelate invoiceRelate = new InvoiceRelate();
		invoiceRelate.setFindAddress(true);
		invoiceRelate.setFindOrderRelation(true);
		queryService.fillOrdInvoice(list, invoiceRelate);
		return list;
	}

	/**
	 * 查询OrdInvoice.
	 * 
	 * @param status
	 *            发票状态
	 * 
	 * @return <pre>
	 * 指定订单ID的OrdInvoice列表，如果指定订单ID没有对应OrdInvoice， 则返回元素数为0的OrdInvoice列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回
	 * <code>null</code>
	 * </pre>
	 * 
	 */
	@Override
	public List<OrdInvoice> queryInvoiceByStatus(final String status) {
		return orderInvoiceService.queryInvoiceByStatus(status);
	}

	/**
	 * 修改invoiceNo和billDate字段.
	 * 
	 * @param invoiceNo
	 *            发票号
	 * @param billDate
	 *            开票日期
	 * @param invoiceId
	 *            发票ID
	 * @param operatorId
	 *            操作人ID
	 * 
	 * @return 修改是否成功
	 * 
	 */
	@Override
	public boolean updateWithDate(final String invoiceNo, final Date billDate, final Long invoiceId, final String operatorId) {
		return orderInvoiceService.update(invoiceNo, billDate, invoiceId, operatorId);
	}

	/**
	 * 修改发票的发票号
	 * 
	 * @param invoiceId
	 *            发票序号
	 * @param invoiceNo
	 *            发票号
	 * @param operatorId
	 *            操作人ID
	 * @return
	 */
	@Override
	public boolean updateInvoiceNo(Long invoiceId, String invoiceNo, String operatorId) {
		if (StringUtils.isEmpty(invoiceNo))
			return false;
		return orderInvoiceService.update(invoiceNo, new Date(), invoiceId, operatorId);
	}

	@Override
	public boolean updateInvoiceExpressNo(Long invoiceId, String expressNo, String operatorId) {
		boolean isOk = orderInvoiceService.updateExpressNo(invoiceId, expressNo, operatorId);
		if(isOk){
			//如果更新快递单号成功 发送短信
			sendSms(invoiceId,expressNo);
		}
		return isOk;
	}
	
	public void sendSms(Long invoiceId,String expressNo){
		String content = "";
		try{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("express_no", expressNo);
			content = comSmsTemplateService.getSmsContent(Constant.SMS_TEMPLATE.ORDER_FOR_BILL_INVOICE.getCode(), parameters);
			String mobile = getInvoicePersonMobile(invoiceId);
			smsRemoteService.sendSms(content, mobile,1);
		} catch(Exception e){
			LOG.error("发票出票发短信失败  发票ID=" + invoiceId +" 发票号 ="+expressNo + " 发送内容=" + content,e);
		}
	}
	private String getInvoicePersonMobile(Long invoiceId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("objectType", Constant.OBJECT_TYPE.ORD_INVOICE.name());
		map.put("objectId", ""+invoiceId);
		map.put("personType", Constant.ORD_PERSON_TYPE.ADDRESS.name());
		List<OrdPerson> list = orderPersonDAO.queryOrdPersonByParams(map);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return list.get(0).getMobile();
		}

	}
	/**
	 * 修改status字段.
	 * 
	 * @param status
	 *            发票状态（未开票、已开票、作废）
	 * @param invoiceId
	 *            发票ID
	 * @param operatorId
	 *            操作人ID
	 * 
	 * @return 修改是否成功
	 * 
	 */
	@Override
	public InvoiceResult update(final String change_status, final Long invoiceId, final String operatorId) {
		String status = change_status;// 需要操作的状态
		String operator = operatorId;
		InvoiceResult result = new InvoiceResult();

		// 如果发票是变更为取消或审核通过状态
		if (status.equals(Constant.INVOICE_STATUS.CANCEL.name()) || status.equals(Constant.INVOICE_STATUS.APPROVE.name())
				||status.equals(Constant.INVOICE_STATUS.RED.name())) {
			List<OrdInvoiceRelation> list = orderInvoiceService.selectInvoiceRelationListByInvoiceId(invoiceId);
			if (CollectionUtils.isNotEmpty(list)) {
				// 如果是取消，需要修改订单相关的标记
				OrdInvoice ordInvoice = orderInvoiceService.selectByPrimaryKey(invoiceId);
				boolean needUpdateInvoice=true;
				if (status.equals(Constant.INVOICE_STATUS.APPROVE.name())) {// 审核通过的状态
					try {
						long amount = 0;
						boolean changeAmount=false;//只有在发票金额全部为true才更新发票金额
						for (OrdInvoiceRelation relation : list) {
							OrdOrder order = queryOrdOrderByOrderId(relation.getOrderId());
							if("true".equals(order.getNeedInvoice())){
								long orderAmount = order.getActualPay() - orderInvoiceService.getSumCompensationAndRefundment(relation.getOrderId());
								long otherOrderAmount = orderInvoiceService.getOrderInvoiceAmountNotInvoiceId(relation.getOrderId(),ordInvoice.getInvoiceId());
								boolean updateNeedInvoice=orderAmount<=otherOrderAmount;
								orderAmount-=otherOrderAmount;
								if (orderAmount < 1) {
									throw new InvoiceResult.CancelException(relation.getOrderId(),updateNeedInvoice);
								}
								amount += orderAmount;
								changeAmount = true;
							}else if("part".equals(order.getNeedInvoice())){
								changeAmount=false;
							}
						}
						// 金额不一样，需要修改发票金额
						if (!ordInvoice.getAmount().equals(amount)&&changeAmount) {
							long oldAmount = ordInvoice.getAmount();
							ordInvoice.setAmount(amount);
							orderInvoiceService.update(ordInvoice, "SYSTEM", "修改发票原金额:" + PriceUtil.convertToYuan(oldAmount) + "至" + PriceUtil.convertToYuan(amount));
						}
					} catch (InvoiceResult.CancelException ex) {
						result.rasieCancel(ex);
						// 直接废掉,并且将订单当中的开票状态变成false;
						status = Constant.INVOICE_STATUS.CANCEL.name();
						operator = "SYSTEM";
						if(ex.isUpdateNeedInvoice()){
							needUpdateInvoice=false;
						}
					}
				}

				if (status.equals(Constant.INVOICE_STATUS.CANCEL.name())||status.equals(Constant.INVOICE_STATUS.RED.name())) {
					if(list.size()==1){//有可能存在多张分开开票
						if(needUpdateInvoice){//如果其他的发票金额已经多余总金额，不变更状态。
							Long orderId= list.get(0).getOrderId();
							String needInvoice="false";
							if(orderInvoiceService.selectInvoiceCountByOrderId(orderId)>1){
								needInvoice="part";
							}
							orderUpdateService.updateNeedInvoice(orderId, needInvoice);
						}
					}else{
						for (OrdInvoiceRelation relation : list) {
							orderUpdateService.updateNeedInvoice(relation.getOrderId(), "false");
						}
					}
				}
			}
		}
		boolean f = orderInvoiceService.update(status, invoiceId, operator);
		if (!f) {
			result.rasieError(new Exception("更新到" + status + " 失败"));
		}
		return result;
	}
	@Override
	public long getOrderInvoiceAmountNotInvoiceId(final Long orderId){
		return orderInvoiceService.getOrderInvoiceAmountNotInvoiceId(orderId, null);
	}
	
	/**
	 * 更新红冲状态
	 */
	public InvoiceResult updateRedFlag(Long invoiceId,String redFlag,String operatorId){
		InvoiceResult result = new InvoiceResult();
		OrdInvoice ordInvoice = orderInvoiceService.selectByPrimaryKey(invoiceId);
		if(null==ordInvoice){
			result.rasieError(new Exception("发票不存在"));
			return result;
		}
		
		ordInvoice.setRedFlag(redFlag);
		ordInvoice.setRedReqTime(new Date());
		//LOG.info("用户进行了红冲操作,发票id为" + ordInvoice.getInvoiceId() + " 操作的时间为： " + new Date());
		boolean f=orderInvoiceService.updateRedFlag(ordInvoice,operatorId);
		if (!f) {
			result.rasieError(new Exception("更新到发票id：" + ordInvoice.getInvoiceId() + " 失败"));
		}
		return result;
	}
	/**
	 * 查询OrdInvoice.
	 * 
	 * @param invoiceId
	 *            主键
	 * 
	 * @return OrdInvoice
	 * 
	 */
	@Override
	public OrdInvoice selectOrdInvoiceByPrimaryKey(final Long invoiceId) {
		OrdInvoice ordInvoice = orderInvoiceService.selectByPrimaryKey(invoiceId);
		if (ordInvoice != null) {
			List<OrdInvoiceRelation> list = orderInvoiceService.selectInvoiceRelationListByInvoiceId(invoiceId);
			if (CollectionUtils.isNotEmpty(list)) {
				for (OrdInvoiceRelation relation : list) {
					relation.setOrder(queryOrdOrderByOrderId(relation.getOrderId()));
				}
				ordInvoice.setInvoiceRelationList(list);
			}
			ordInvoice.setDeliveryAddress(orderPersonService.selectInvoicePersonByInvoiceId(invoiceId));
		}
		return ordInvoice;
	}

	/**
	 * 根据Person ID查询{OrdPerson}.
	 * 
	 * @param personId
	 *            主键
	 * @return OrdPerson
	 * 
	 */
	@Override
	public OrdPerson selectOrdPersonByPrimaryKey(final Long personId) {
		return orderPersonService.selectByPrimaryKey(personId);
	}

	/**
	 * 订单返现.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param commentId
	 *            点评ID
	 * @param cash
	 *            返现金额，以分为单位，100代表1元
	 * @return <pre>
	 * <code>true</code>代表订单返现成功，<code>false</code>
	 *         代表订单返现失败
	 * </pre>
	 */
	@Override
	public boolean cashOrder(final Long orderId, final Long cash) {
		return orderUpdateService.cashOrder(orderId, cash);
	}

	/**
	 * 根据采购产品订单子项ID查询订单.
	 * 
	 * @param ordOrderItemMetaId
	 *            采购产品订单子项ID
	 * @return <pre>
	 * 指定采购产品订单子项ID的订单，包含相关销售产品，相关采购产品，相关供应商等信息，
	 * 如果指定采购产品订单子项ID的订单不存在则返回<code>null</code>
	 * </pre>
	 */
	@Override
	public OrdOrder queryOrdOrderByOrdOrderItemMetaId(final Long ordOrderItemMetaId) {
		final Long orderId = queryService.queryOrdOrderId(ordOrderItemMetaId);
		return queryOrdOrderByOrderId(orderId);
	}

	/**
	 * 更改订单子子项的退款标志.
	 * 
	 * @param refund
	 *            要更改的订单子子项的退款标志
	 * @param ordOrderItemMetaId
	 *            订单子子项ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改订单子子项的退款标志成功，<code>false</code>代表更改订单子子项的退款标志失败
	 * </pre>
	 */
	@Override
	public boolean updateOrderItemMetaRefund(final String refund, final Long ordOrderItemMetaId, final String operatorId) {
		return orderUpdateService.updateOrderItemMetaRefund(refund, ordOrderItemMetaId, operatorId);
	}

	/**
	 * 更改订单子子项的实际结算价.
	 * 
	 * @param actualSettlementPrice
	 *            要更改的订单子子项的实际结算价
	 * @param ordOrderItemMetaId
	 *            订单子子项ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改订单子子项的实际结算价成功，<code>false</code>代表更改订单子子项的实际结算价失败
	 * </pre>
	 */
	@Override
	public boolean updateActualSettlementPrice(final Long actualSettlementPrice, final Long ordOrderItemMetaId, final String operatorId) {
		return orderUpdateService.updateActualSettlementPrice(actualSettlementPrice, ordOrderItemMetaId, operatorId);
	}

	/**
	 * 查询{@link OrdFaxTask}计数.
	 * 
	 * <pre>
	 * 分页查询使用
	 * </pre>
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 满足综合查询条件的{@link OrdFaxTask}总数
	 */
	@Override
	public Long queryOrdFaxTaskCount(final CompositeQuery compositeQuery) {
		return queryService.queryOrdFaxTaskCount(compositeQuery);
	}

	/**
	 * 查询{@link OrdSaleService}.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * 如果没有{@link OrdSaleService}， 则返回元素数为0的{@link OrdSaleService}列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	@Override
	public List<OrdSaleService> queryOrdSaleService(final CompositeQuery compositeQuery) {
		return queryService.queryOrdSaleService(compositeQuery);
	}

	/**
	 * 查询{@link OrdSaleService}计数.
	 * 
	 * <pre>
	 * 分页查询使用
	 * </pre>
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 满足综合查询条件的{@link OrdSaleService}总数
	 */
	@Override
	public Long queryOrdSaleServiceCount(final CompositeQuery compositeQuery) {
		return queryService.queryOrdSaleServiceCount(compositeQuery);
	}

	/**
	 * 查询 {@link OrdInvoice}.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * 如果没有{@link OrdInvoice}， 则返回元素数为0的{@link OrdInvoice}列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	@Override
	public List<OrdInvoice> queryOrdInvoice(final CompositeQuery compositeQuery) {
		List<OrdInvoice> list = queryService.queryOrdInvoice(compositeQuery);
		if(compositeQuery.getInvoiceRelate().isFindOrder()&&compositeQuery.getInvoiceRelate().isFindOrderInvoiceAmount()){
			Map<Long,Long> map = new HashMap<Long,Long>();
			for(OrdInvoice oi:list){
				for(OrdOrder order:oi.getOrderList()){
					long refundment=0;
					if(!map.containsKey(order.getOrderId())){
						refundment = orderInvoiceService.getSumCompensationAndRefundment(order.getOrderId());
						map.put(order.getOrderId(), refundment);
					}else{
						refundment = map.get(order.getOrderId());
					}
					order.setOrderPay(order.getActualPay()-refundment);//该值表示能开票的金额
				}
			}
		}
		return list;
	}

	/**
	 * 查询 {@link OrdInvoice}计数.
	 * 
	 * <pre>
	 * 分页查询使用
	 * </pre>
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 满足综合查询条件的 {@link OrdInvoice}总数
	 */
	@Override
	public Long queryOrdInvoiceCount(final CompositeQuery compositeQuery) {
		return queryService.queryOrdInvoiceCount(compositeQuery);
	}

	/**
	 * 修改是否需要发票NeedInvoice字段.
	 * 
	 * @param needInvoice
	 *            是否需要发票
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * 
	 * @return 修改是否成功
	 * 
	 */
	@Override
	public boolean updateNeedInvoice(final String needInvoice, final Long orderId, final String operatorId) {
		return orderInvoiceService.updateNeedInvoice(needInvoice, orderId, operatorId);
	}

	/**
	 * 根据ID查询{@link OrdOrderItemProd}.
	 * 
	 * @param orderItemProdId
	 *            销售产品订单子项ID
	 * @return <pre>
	 * 指定ID的{@link OrdOrderItemProd}，
	 * 如果指定ID没有对应{@link OrdOrderItemProd}，则返回<code>null</code>
	 * </pre>
	 */
	@Override
	public OrdOrderItemProd queryOrdOrderItemProdById(final Long orderItemProdId) {
		return queryService.queryOrdOrderItemProdById(orderItemProdId);
	}

	/**
	 * 根据订单号查询订单.
	 * 
	 * @param orderNo
	 *            订单号
	 * @return <pre>
	 * 指定订单号的订单，包含相关销售产品，相关采购产品，相关供应商等信息，如果指定订单号的订单不存在则返回<code>null</code>
	 * </pre>
	 */
	@Override
	public OrdOrder queryOrdOrderByOrderNo(final String orderNo) {
		OrdOrder ordOrder = queryService.queryOrdOrderByOrderNo(orderNo);

		List<OrdOrder> ordOrderList = new ArrayList<OrdOrder>();
		ordOrderList.add(ordOrder);
		this.initOrdOrderItemMetaSupSupplier(ordOrderList);
		this.initOrdOrderUser(ordOrderList);
		return ordOrderList.get(0);

	}


	/**
	 * 根据采购产品订单子项ID查询{@link OrdPerform}.
	 * 
	 * @param orderItemMetaId
	 *            采购产品订单子项ID
	 * @return <pre>
	 * 指定采购产品订单子项ID的{@link OrdPerform}，
	 * 如果指定采购产品订单子项ID的{@link OrdPerform}不存在则返回<code>null</code>
	 * </pre>
	 */
	@Override
	public OrdPerform queryOrdPerformByOrderItemMetaId(final Long orderItemMetaId) {
		return queryService.queryOrdPerformByOrderItemMetaId(orderItemMetaId);
	}

	/**
	 * 综合{@link OrdOrderItemMeta}查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * 以{@link OrdOrder}形式包装{@link OrdOrderItemMeta}的列表，
	 * 每个{@link OrdOrder}包含一个{@link OrdOrderItemMeta},
	 * 使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	@Override
	public List<OrdOrder> compositeQueryOrdOrderItemMeta(final CompositeQuery compositeQuery) {
		List<OrdOrder> ordOrderList = queryService.compositeQueryOrdOrderItemMeta(compositeQuery);
		if(compositeQuery.getQueryFlag().isQueryUser()){
			this.initOrdOrderUser(ordOrderList);
		}
		return ordOrderList;
	}

	/**
	 * 查询 {@link OrdOrderItemMeta}计数.
	 * 
	 * <pre>
	 * 分页查询使用
	 * </pre>
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 满足综合查询条件的 {@link OrdOrderItemMeta}总数
	 */
	@Override
	public Long compositeQueryOrdOrderItemMetaCount(final CompositeQuery compositeQuery) {
		return queryService.compositeQueryOrdOrderItemMetaCount(compositeQuery);
	}

	/**
	 * 查找联系人
	 */
	public List<OrdPerson> getOrdPersons(OrdPerson pars) {
		return this.orderPaymentService.getOrdPersons(pars);
	}

	@Override
	public boolean paymentSuccess(PayPayment payPayment) {
		String key = "PAYMENT_CALL_BACK_ACTION_" + payPayment.getPaymentId();
		try {
			if (SynchronizedLock.isOnDoingMemCached(key)) {
				LOG.error("ERROR: key is exists! key="+key);
				return false;
			}
			OrdOrder ordOrder = orderPaymentService.newPaymentSuccess(payPayment);
			LOG.info("ordOrder:"+StringUtil.printParam(ordOrder));
			// 只有这一次进行了订单支付状态变更，才发消息
			if (ordOrder.isUpdateInCurrent()) {
				if (payPayment.isPrePayment() && ordOrder.isApprovePass()) {
					Message msg = MessageFactory.newOrderApproveBeforePrepayMessage(ordOrder.getOrderId());
					msg.setAddition(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
					orderMessageProducer.sendMsg(msg);
				} else if (payPayment.isPrePayment() && ordOrder.isApproveFail()) {
					this.autoCreateOrderFullRefund(ordOrder, "SYSTEM", "资源审核不通过");
				}
				if (ordOrder.isPartPay()) {
					orderMessageProducer.sendMsg(MessageFactory.newOrderPartpayPaymentMessage(ordOrder.getOrderId()));
				} else if (ordOrder.isPaymentSucc()) {
					orderMessageProducer.sendMsg(MessageFactory.newOrderPaymentMessage(ordOrder.getOrderId()));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			SynchronizedLock.releaseMemCached(key);
		}
		return true;
	}

	@Override
	public boolean refundSuccess(PayPaymentRefundment payRefundment) {
		Long refundmentId = payRefundment.getObjectId();
		// 根据退款单的退款金额修改订单退款金额
		if (Constant.REFUND_TYPE.ORDER_REFUNDED.name().equals(payRefundment.getRefundType())) {
			// 根据退款单ID查询退款单信息
			OrdRefundment ordRefundment = orderRefundmentService.queryOrdRefundmentById(refundmentId);
			if(Constant.REFUNDMENT_STATUS.REFUNDED.name().equals(ordRefundment.getStatus())){
				LOG.warn("ordrefundment: "+refundmentId+" status is refunded!!!!!!!!!!can't refundSuccess twice!");
				return false;
			}
			orderUpdateService.updateRefundedAmount(payRefundment.getOrderId(), ordRefundment.getRefundmentId(),ordRefundment.getAmount());
		}
		// 修改退款单的退款状态
		boolean res = orderRefundmentService.updateOrdRefundmentStatusById(refundmentId, Constant.REFUNDMENT_STATUS.REFUNDED.name());
		if (res) {
			// 退款成功之后修改订单的结算数据
			PermUser user = new PermUser();
			user.setUserName("admin");
			user.setUserId(0L);
			orderRefundmentService.ordRefundment2UpdateSettlement(refundmentId , user);
		}
		return res;
	}


	@Override
	public boolean transferPaymentSuccess(final List<PayPayment> payPayments) {
		OrdOrder ordOrder = orderPaymentService.newTransferPaymentSuccess(payPayments);
		if (ordOrder.isPartPay()) {
			orderMessageProducer.sendMsg(MessageFactory.newOrderPartpayPaymentMessage(ordOrder.getOrderId()));
		} else if (ordOrder.isPaymentSucc()) {
			orderMessageProducer.sendMsg(MessageFactory.newOrderPaymentMessage(ordOrder.getOrderId()));
		}
		// 进行支付金额拆分,如果支付成功或实付大于应付
		if (ordOrder.isPaymentSucc()|| ordOrder.getActualPay() > ordOrder.getOughtPay()) {
			orderItemMetaSaleAmountServie.updateOrderItemMetaSaleAmount(ordOrder.getOrderId());
		} else {
			orderItemMetaSaleAmountServie.updateOrderItemSaleAmount(ordOrder.getOrderId());
		}
		if (ordOrder.getActualPay() > ordOrder.getOughtPay()) {
			setTransferTaskService.insert(ordOrder.getOrderId());
			//autoCreateOrdRefundment(false, ordOrder, ordOrder.getActualPay() - ordOrder.getOughtPay(), "SYSTEM", "支付转移自动产生退款");
		}
		return true;
	}
	/**
	 * 自动创建全额退款的退款单并进入实际退款中
	 * @param order  需要退款的订单
	 * @param operatorName 操作人的姓名
	 * @param memo  退款理由
	 * <p>此方法不仅会自动创建退款单，而且还会不需人工审核就进入实际退款中</p>
	 */
	@Override
	public  boolean autoCreateOrderFullRefund( final OrdOrder order, final String operatorName, final String memo){
		Long refundedAmount = this.orderRefundService.queryRefundAmountSumByOrderIdAndSysCode(order.getOrderId(), Constant.COMPLAINT_SYS_CODE.SUPER.name());
		return this.autoCreateOrdRefundment(true, order, order.getActualPay() - refundedAmount, operatorName, memo);
	}

	@Override
	public boolean autoCreateOrdRefundment(boolean isFullRefund, final OrdOrder order, final Long refundAmount, final String operatorName, final String memo){
		return autoCreateOrderRefundment(isFullRefund,order,refundAmount,operatorName,memo)!=null;
	}
	
	@Override
	public Long autoCreateOrdRefundmentBySupplier(final OrdOrder order,final Long orderItemMetaId,final Long refundAmount,final String operatorName,final String memo){
		LOG.info("autoCreateOrderRefundment orderId: " + order.getOrderId());
		Long refundmentId=null;
		if (order.isPaySucc() || order.isPartPay()) {
			LOG.info("order.isPaySucc(): " + order.isPaySucc() + " order.isPartPay() " + order.isPartPay());
			refundmentId = orderRefundmentService.markOrdRefunmentBySupplierBear(order.getOrderId(), orderItemMetaId, operatorName, refundAmount, memo);
			
			OrdRefundment orf = queryOrdRefundmentById(refundmentId);
			
			if (orf.isCanToDoRefund()) {
				LOG.info("orf.isCanToDoRefund(): " + orf.isCanToDoRefund());
				UserUser user = userUserProxy.getUserUserByUserNo(order.getUserId());

				RefundmentToBankInfo refundInfo = new RefundmentToBankInfo();
				refundInfo.setRefundAmount(orf.getAmount());
				refundInfo.setBizType(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
				refundInfo.setObjectId(orf.getRefundmentId());
				refundInfo.setObjectType(Constant.PAYMENT_OBJECT_TYPE.ORD_REFUNDMENT.name());
				refundInfo.setRefundType(orf.getRefundType());
				refundInfo.setUserId(user.getId());
				refundInfo.setOperator(operatorName);

				boolean success = payPaymentRefundmentService.createRefundment(order.getOrderId(), refundInfo);
				LOG.info("success " + success);
				if (success) {
					// 3.更新 ord_refundment 状态为REFUNDED，已退款
					orf.setRefundTime(Calendar.getInstance().getTime());
					orf.setStatus(Constant.REFUNDMENT_STATUS.PROCESSING.name());
					orf.setOperatorName(operatorName);
					updateOrdRefundment(orf);
					Message msg = MessageFactory.newPaymentRefundmentMessage(orf.getRefundmentId());
					msg.setAddition(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
					resourceMessageProducer.sendMsg(msg);
				}else{
					refundmentId = null;
				}
			}
		}
		return refundmentId;
	}
	/**
	 * 自动创建退款单并进入实际退款中
	 * @param isFullRefund 是否是全额退款
	 * @param order 需要退款的订单
	 * @param refundAmount 退款金额
	 * @param operatorName 操作人的姓名
	 * @param memo 退款理由
	 * <p>此方法不仅会自动创建退款单，而且还会不需人工审核就进入实际退款中</p>
	 */
	public Long autoCreateOrderRefundment(boolean isFullRefund, final OrdOrder order, final Long refundAmount, final String operatorName, final String memo) {
		LOG.info("autoCreateOrderRefundment orderId: " + order.getOrderId());
		Long refundmentId=null;
		if (order.isPaySucc() || order.isPartPay()) {
			LOG.info("order.isPaySucc(): " + order.isPaySucc() + " order.isPartPay() " + order.isPartPay());
			refundmentId = saveOrdRefundmentByOrderId(isFullRefund, order.getOrderId(), "SYSTEM", refundAmount , memo, true, Constant.COMPLAINT_SYS_CODE.SUPER.name());
			
			OrdRefundment orf = queryOrdRefundmentById(refundmentId);
			
			if (orf.isCanToDoRefund()) {
				LOG.info("orf.isCanToDoRefund(): " + orf.isCanToDoRefund());
				UserUser user = userUserProxy.getUserUserByUserNo(order.getUserId());

				RefundmentToBankInfo refundInfo = new RefundmentToBankInfo();
				refundInfo.setRefundAmount(orf.getAmount());
				refundInfo.setBizType(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
				refundInfo.setObjectId(orf.getRefundmentId());
				refundInfo.setObjectType(Constant.PAYMENT_OBJECT_TYPE.ORD_REFUNDMENT.name());
				refundInfo.setRefundType(orf.getRefundType());
				refundInfo.setUserId(user.getId());
				refundInfo.setOperator(operatorName);

				boolean success = payPaymentRefundmentService.createRefundment(order.getOrderId(), refundInfo);
				LOG.info("success " + success);
				if (success) {
					// 3.更新 ord_refundment 状态为REFUNDED，已退款
					orf.setRefundTime(Calendar.getInstance().getTime());
					orf.setStatus(Constant.REFUNDMENT_STATUS.PROCESSING.name());
					orf.setOperatorName(operatorName);
					updateOrdRefundment(orf);
					Message msg = MessageFactory.newPaymentRefundmentMessage(orf.getRefundmentId());
					msg.setAddition(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
					resourceMessageProducer.sendMsg(msg);
				}else{
					refundmentId = null;
				}
			}
		}
		return refundmentId;
	}
	
	
	/**
	 * 自动创建全额退款的退款单并进入实际退款中
	 * @param orderId  需要退款的订单id
	 * @param operatorName 操作人的姓名
	 * @param memo  退款理由
	 * <p>此方法不仅会自动创建退款单，而且还会不需人工审核就进入实际退款中</p>
	 */
	@Override
	public  boolean autoCreateOrderFullRefundVst(final Long orderId, final String operatorName, final String memo){
		LOG.info("autoCreateOrderFullRefundVst start "+orderId);
		VstOrdOrderVo vstOrdOrderVo = vstOrdOrderService.getVstOrdOrderVo(orderId);
		if(vstOrdOrderVo!=null) {
			Long refundedAmount = this.orderRefundService.queryRefundAmountSumByOrderIdAndSysCode(vstOrdOrderVo.getOrderId(), Constant.COMPLAINT_SYS_CODE.VST.name());
			return this.autoCreateOrdRefundmentVst(true, orderId, vstOrdOrderVo.getActualAmount() - refundedAmount, operatorName, memo);
		}else {
			return false;
		}
	}
	/**
	 * 自动创建退款单并进入实际退款中
	 * @param isFullRefund 是否是全额退款
	 * @param orderId 需要退款的订单
	 * @param refundAmount 退款金额
	 * @param operatorName 操作人的姓名
	 * @param memo 退款理由
	 * <p>此方法不仅会自动创建退款单，而且还会不需人工审核就进入实际退款中</p>
	 */
	@Override
	public boolean autoCreateOrdRefundmentVst(boolean isFullRefund, final Long orderId,final Long refundAmount, final String operatorName, final String memo) {
		return autoCreateOrderRefundmentVst(isFullRefund,orderId,refundAmount,operatorName,memo)!=null;
	}
	/**
	 * 自动创建退款单并进入实际退款中
	 * @param isFullRefund 是否是全额退款
	 * @param order 需要退款的订单
	 * @param refundAmount 退款金额
	 * @param operatorName 操作人的姓名
	 * @param memo 退款理由
	 * <p>此方法不仅会自动创建退款单，而且还会不需人工审核就进入实际退款中</p>
	 */
	@Override
	public Long autoCreateOrderRefundmentVst(boolean isFullRefund, final Long orderId, final Long refundAmount, final String operatorName, final String memo) {
		LOG.info("autoCreateOrderRefundment orderId: " + orderId);
		Long refundmentId=null;
		VstOrdOrderVo vstOrdOrderVo = vstOrdOrderService.getVstOrdOrderVo(orderId);
		OrdOrder order = new OrdOrder();
		order.setUserId(vstOrdOrderVo.getUserId());
		order.setOrderId(vstOrdOrderVo.getOrderId());
		order.setPaymentStatus(vstOrdOrderVo.getPaymentStatus());
		if (order.isPaySucc() || order.isPartPay()) {
			LOG.info("order.isPaySucc(): " + order.isPaySucc() + " order.isPartPay() " + order.isPartPay());
			refundmentId = saveOrdRefundmentByOrderId(isFullRefund, order.getOrderId(), "SYSTEM", refundAmount , memo, true, Constant.COMPLAINT_SYS_CODE.VST.name());
			OrdRefundment orf = queryOrdRefundmentById(refundmentId);
			
			if (orf.isCanToDoRefund()) {
				LOG.info("orf.isCanToDoRefund(): " + orf.isCanToDoRefund());
				UserUser user = userUserProxy.getUserUserByUserNo(order.getUserId());

				RefundmentToBankInfo refundInfo = new RefundmentToBankInfo();
				refundInfo.setRefundAmount(orf.getAmount());
				refundInfo.setBizType(Constant.PAYMENT_BIZ_TYPE.VST_ORDER.name());
				refundInfo.setObjectId(orf.getRefundmentId());
				refundInfo.setObjectType(Constant.PAYMENT_OBJECT_TYPE.ORD_REFUNDMENT.name());
				refundInfo.setRefundType(orf.getRefundType());
				refundInfo.setUserId(user.getId());
				refundInfo.setOperator(operatorName);

				boolean success = payPaymentRefundmentService.createRefundment(order.getOrderId(), refundInfo);
				LOG.info("success " + success);
				if (success) {
					// 3.更新 ord_refundment 状态为REFUNDED，已退款
					orf.setRefundTime(Calendar.getInstance().getTime());
					orf.setStatus(Constant.REFUNDMENT_STATUS.PROCESSING.name());
					orf.setOperatorName(operatorName);
					updateOrdRefundment(orf);
					Message msg = MessageFactory.newPaymentRefundmentMessage(orf.getRefundmentId());
					msg.setAddition(Constant.PAYMENT_BIZ_TYPE.VST_ORDER.name());
					resourceMessageProducer.sendMsg(msg);
				}else{
					refundmentId = null;
				}
			}
		}
		return refundmentId;
	}

	/**
	 * 查询是储值卡支付的金额.
	 * 
	 * @param orderId
	 * @return
	 */
	public Long selectCardPaymentSuccessSumAmount(Long orderId) {
		return this.orderPaymentService.selectCardPaymentSuccessSumAmount(orderId);
	}

	/**
	 * 综合{@link OrdOrderItemMeta}查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return <pre>
	 * {@link OrdOrderItemMeta}的列表，使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	@Override
	public List<OrdOrderItemMeta> compositeQueryOrdOrderItemMetaByMetaPerformRelate(final CompositeQuery compositeQuery) {
		return queryService.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
	}

	/**
	 * 查询{@link OrdOrderAmountItem}.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param oderAmountType
	 *            类型
	 * @return {@link OrdOrderAmountItem}
	 */
	@Override
	public List<OrdOrderAmountItem> queryOrdOrderAmountItem(final Long orderId, final String oderAmountType) {
		return queryService.queryOrdOrderAmountItem(orderId, oderAmountType);
	}

	/**
	 * 履行明细查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 履行明细列表
	 */
	@Override
	public List<PerformDetail> queryPerformDetail(final CompositeQuery compositeQuery) {
		return queryService.queryPerformDetail(compositeQuery);
	}

	/**
	 * 履行明细查询计数.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 履行明细查询计数
	 */
	@Override
	public Long queryPerformDetailCount(final CompositeQuery compositeQuery) {
		return queryService.queryPerformDetailCount(compositeQuery);
	}

	@Override
	public List<PerformDetail> queryPerformDetailForEplaceList(
			final CompositeQuery compositeQuery) {
		return queryService.queryPerformDetailForEplaceList(compositeQuery);
	}
	
	@Override
	public List<PerformDetail> queryPerformDetailForEplacePageList(
			final CompositeQuery compositeQuery) {
		return queryService.queryPerformDetailForEplacePageList(compositeQuery);
	}
	@Override
	public Long queryPerformDetailForEplaceCount(final CompositeQuery compositeQuery) {
		return queryService.queryPerformDetailForEplaceCount(compositeQuery);
	}
	@Override
	public List<String> queryPerformDetailForEplaceTongjiPageList(
			final CompositeQuery compositeQuery) {
		return queryService.queryPerformDetailForEplaceTongjiPageList(compositeQuery);
	}
	@Override
	public Long queryPerformDetailForEplaceTongjiCount(final CompositeQuery compositeQuery) {
		return queryService.queryPerformDetailForEplaceTongjiCount(compositeQuery);
	}
	@Override
	public List<OrdEplaceOrderQuantity> queryEbkOrderForEplaceTotalQuantity(final CompositeQuery compositeQuery,boolean isTotal) {
		return queryService.queryEbkOrderForEplaceTotalQuantity(compositeQuery, isTotal);
	}
	/**
	 * 通关汇总查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 通关汇总列表
	 */
	@Override
	public List<PassPortSummary> queryPassPortSummary(final CompositeQuery compositeQuery) {
		return queryService.queryPassPortSummary(compositeQuery);
	}

	/**
	 * 通关汇总查询计数.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 通关汇总查询计数
	 */
	@Override
	public Long queryPassPortSummaryCount(final CompositeQuery compositeQuery) {
		return queryService.queryPassPortSummaryCount(compositeQuery);
	}

	/**
	 * 通关明细查询.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 通关明细列表
	 */
	@Override
	public List<PassPortDetail> queryPassPortDetail(final CompositeQuery compositeQuery) {
		return queryService.queryPassPortDetail(compositeQuery);
	}

	/**
	 * 通关明细查询计数.
	 * 
	 * @param compositeQuery
	 *            综合查询
	 * @return 通关明细查询计数
	 */
	@Override
	public Long queryPassPortDetailCount(final CompositeQuery compositeQuery) {
		return queryService.queryPassPortDetailCount(compositeQuery);
	}

	/**
	 * 根据订单ID查询 {@link OrdPerform}.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return {@link OrdPerform}列表
	 */
	@Override
	public List<OrdPerform> queryOrdPerformByOrderId(final Long orderId) {
		return queryService.queryOrdPerformByOrderId(orderId);
	}

	/**
	 * 根据订单ID和状态查询{@link OrdRefundment}.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param status
	 *            状态
	 * @param gatewayTradeNo
	 *            网关的交易号
	 * @return {@link OrdRefundment}列表
	 */
	@Override
	public List<OrdRefundment> queryOrdRefundmentByOrderIdAndStatus(final Long orderId, final String status, final String gatewayTradeNo) {
		return orderRefundmentService.queryOrdRefundmentByOrderIdAndStatus(orderId, status, gatewayTradeNo);
	}

	/**
	 * 根据refundmentId更新status.
	 * 
	 * @param refundmentId
	 *            ID
	 * @param status
	 *            status
	 * @return <code>true</code>代表更新成功，<code>false</code>代表更新失败
	 */
	@Override
	public boolean updateOrdRefundmentStatusById(final Long refundmentId, final String status) {
		return orderRefundmentService.updateOrdRefundmentStatusById(refundmentId, status);
	}

	/**
	 * 生成退款单.
	 * @deprecated
	 * @param isFullrefund 是否是全额退款
	 * @param orderId
	 * @param operatorName
	 * @param amount
	 * @param mome
	 * @param isKey
	 * @return
	 */
	@Override
	public Long saveOrdRefundmentByOrderId(boolean isFullrefund,final Long orderId, final String operatorName, final Long amount, final String mome, final boolean isKey) {
		return orderRefundmentService.markOrdRefunment(isFullrefund,orderId, operatorName, amount, mome, isKey);
	}
	
	/**
	 * 生成退款单.
	 * 
	 * @param isFullrefund 是否是全额退款
	 * @param orderId
	 * @param operatorName
	 * @param amount
	 * @param mome
	 * @param isKey
	 * @param sysCode
	 * @return
	 */
	@Override
	public Long saveOrdRefundmentByOrderId(boolean isFullrefund,final Long orderId, final String operatorName, final Long amount, final String mome, final boolean isKey, String sysCode) {
		return orderRefundmentService.markOrdRefunment(isFullrefund,orderId, operatorName, amount, mome, isKey, sysCode);
	}

	/**
	 * 更加refundmentId更新status和refundTime.
	 * 
	 * @param refundmentId
	 *            ID
	 * @param status
	 *            新status
	 * @param refundTime
	 *            新refundTime
	 * @return <code>true</code>代表更新成功，<code>false</code>代表更新失败
	 */
	@Override
	public boolean updateOrdRefundmentStatusAndRefundTimeById(final Long refundmentId, final String status, final Date refundTime) {
		return orderRefundmentService.updateOrdRefundmentStatusAndRefundTimeById(refundmentId, status, refundTime);
	}

	/**
	 * 根据ID查询{@link OrdRefundment}.
	 * 
	 * @param refundmentId
	 *            ID
	 * @return {@link OrdRefundment}
	 */
	@Override
	public OrdRefundment queryOrdRefundmentById(final Long refundmentId) {
		return orderRefundmentService.queryOrdRefundmentById(refundmentId);
	}

	/**
	 * 
	 * @param ordRefundment
	 * @return
	 */
	@Override
	public boolean updateOrdRefundment(OrdRefundment ordRefundment) {
		return orderRefundmentService.updateOrdRefundment(ordRefundment);
	}

	// 支付需要的接口-------------------------------------------------------
	/**
	 * 保存新的{@link OrdRefundment}.
	 * 
	 * @param ordRefundment
	 *            ordRefundment
	 * @return 新{@link OrdRefundment}的ID
	 */
	@Override
	public Long saveOrdRefundment(final OrdRefundment ordRefundment) {
		return orderRefundmentService.saveOrdRefundment(ordRefundment);
	}

	/**
	 * 根据orderId查询{@link OrdRefundment}.
	 * 
	 * @param orderId
	 * 
	 * @return {@link List<OrdRefundment>}
	 */
	@Override
	public List<OrdRefundment> findValidOrdRefundmentByOrderId(Long orderId) {
		return orderRefundmentService.findValidOrdRefundmentByOrderId(orderId);
	}

	/**
	 * 根据refundmentEventId查询{@link OrdRefundment}.
	 * 
	 * @param refundmentEventId
	 * 
	 * @return {@link OrdRefundment}
	 * @deprecated
	 * @Override public OrdRefundment
	 *           findOrdRefundmentByOrdRefundmentEventId(Long
	 *           refundmentEventId){ return
	 *           orderRefundmentService.findOrdRefundmentByOrdRefundmentEventId
	 *           (refundmentEventId); }
	 */
	/**
	 * 更新退款任务.
	 * 
	 * @param ordRefundmentEvent
	 *            退款任务
	 * 
	 * @return 更新退款任务是否成功
	 * @Override public boolean updateOrdRefundmentEvent(OrdRefundmentEvent
	 *           ordRefundmentEvent){ return
	 *           orderRefundmentService.updateOrdRefundmentEvent
	 *           (ordRefundmentEvent); }
	 */

	/**
	 * 退款回调接口.
	 * 
	 * @param fincTransaction
	 *            交易对象
	 * 
	 * @param ordRefundment
	 *            退款单
	 * 
	 * @param ordRefundmentEvent
	 *            退款任务
	 * 
	 * @return {@link OrdRefundment}
	 * @Override public boolean callbackForRefund(PayTransaction
	 *           fincTransaction, OrdRefundment ordRefundment,
	 *           OrdRefundmentEvent ordRefundmentEvent){ return
	 *           orderRefundmentService.callbackForRefund(fincTransaction,
	 *           ordRefundment, ordRefundmentEvent); }
	 */
	/**
	 * 退款应答事务处理接口.
	 * 
	 * @param ordRefundment
	 *            退款单
	 * 
	 * @param ordRefundmentEvent
	 *            退款任务
	 * 
	 * @return 退款应答事务处理是否成功
	 * @Override public boolean refundFastPay(OrdRefundment ordRefundment,
	 *           OrdRefundmentEvent ordRefundmentEvent){ //return
	 *           orderRefundmentService.refundFastPay(ordRefundment,
	 *           ordRefundmentEvent); }
	 */
	// FincCashService-------------------------------------------------------
	/**
	 * 新建打款单.
	 * 
	 * @param cashMoneyDraw
	 *            打款单
	 * 
	 * @return 打款单ID
	 */
	@Override
	public Long insertCashMoneyDraw(CashMoneyDraw cashMoneyDraw) {
		return cashAccountService.insertCashMoneyDraw(cashMoneyDraw);
	}

	/**
	 * 查询打款单.
	 * 
	 * @param moneyDrawId
	 *            打款单ID
	 * 
	 * @return 打款单
	 */
	@Override
	public CashMoneyDraw findCashMoneyDraw(Long moneyDrawId) {
		return cashAccountService.queryCashMoneyDraw(moneyDrawId);
	}

	/**
	 * 更新打款任务.
	 * 
	 * @param cashDraw
	 *            打款任务
	 * 
	 * @return 更新打款任务是否成功
	 */
	@Override
	public boolean updateCashDrawByPrimaryKey(CashDraw cashDraw) {
		return cashAccountService.updateCashDrawByPrimaryKey(cashDraw);
	}

	/**
	 * 根据alipay2bankFile查询{@link cashDraw}.
	 * 
	 * @param alipay2bankFile
	 *            上传给支付宝的打款文件名
	 * 
	 * @return {@link fincCashDraw}
	 */
	@Override
	public CashDraw findCashDrawByAlipay2bankFile(String alipay2bankFile) {
		return cashAccountService.findCashDrawByAlipay2bankFile(alipay2bankFile);
	}

	/**
	 * 根据流水号查询{@link cashDraw}.
	 * 
	 * @param serial
	 *            流水号
	 * 
	 * @return {@link cashDraw}
	 */
	@Override
	public CashDraw findCashDrawBySerial(String serial) {
		return cashAccountService.findCashDrawBySerial(serial);
	}

	/**
	 * 接收到打款应答后更新打款单、新建现金账户打款记录、现金账户支付记录.
	 * 
	 * @param fincMoneyDraw
	 *            打款单
	 * 
	 * @param fincCashDraw
	 *            现金账户打款记录
	 * 
	 * @param fincCashPay
	 *            现金账户支付记录
	 * 
	 * @return 操作是否成功
	 */
	@Override
	public boolean drawMoney(CashMoneyDraw cashMoneyDraw, CashDraw cashDraw) {
		return cashAccountService.withDrawMoney(cashMoneyDraw, cashDraw);
	}

	/**
	 * 根据提现单ID查询{@link fincCashDraw}.
	 * 
	 * @param moneyDrawId
	 *            提现单ID
	 * 
	 * @return {@link fincCashDraw}
	 */
	@Override
	public CashDraw findCashDrawByMoneyDrawId(final Long moneyDrawId) {
		return cashAccountService.findCashDrawByMoneyDrawId(moneyDrawId);
	}

	public boolean canGoingBack(Map<String, String> params) {
		return orderAuditService.canGoingBack(params);
	}

	public boolean canRecycle(Map<String, String> params) {
		return orderAuditService.canRecycle(params);
	}

	/**
	 * 统计订单人数.
	 * 
	 * @param productid
	 *            销售产品ID
	 * @param ordOrderVisitTimeStart
	 *            订单起始游玩时间（包括）
	 * @param ordOrderVisitTimeEnd
	 *            订单结束游玩时间（不包括）
	 * @param paymentStatus
	 *            订单支付状态
	 * @param orderStatus
	 *            订单状态
	 * @return {@link OrderPersonCount}
	 */
	@Override
	public OrderPersonCount queryOrderPersonCount(final Long productid, final Date ordOrderVisitTimeStart, final Date ordOrderVisitTimeEnd, final String paymentStatus, final String orderStatus) {
		return queryService.queryOrderPersonCount(productid, ordOrderVisitTimeStart, ordOrderVisitTimeEnd, paymentStatus, orderStatus);
	}

	/**
	 * 修改订单关于团的附加表.
	 * 
	 * @param OrdOrderRoute
	 *            orderRoute
	 * @return 更改结果
	 */
	@Override
	public boolean updateOrderRoute(final OrdOrderRoute orderRoute) {
		return this.orderRouteService.updateOrderRoute(orderRoute);
	}

	/**
	 * 根据订单ID查询订单附加团信息.
	 * 
	 * @param orderId
	 *            订单ID
	 * @return
	 */
	@Override
	public OrdOrderRoute queryOrdOrderRouteByOrderId(final Long orderId) {
		return orderRouteService.queryOrdOrderRouteByOrderId(orderId);
	}

	/**
	 * 订单电子合同的状态更改为已确认
	 * 
	 * @param orderId
	 *            订单号
	 * @return 更改结果
	 */
	@Override
	public boolean updateOrdEContractStatusToConfirmed(final Long orderId) {
		return orderUpdateService.updateOrdEContractStatusToConfirmed(orderId);
	}

	/**
	 * 更新订单金额 和 优惠券的处理 .
	 * 
	 * @param orderInfo
	 * @return
	 */
	public boolean updateOrderPriceByCoupon(OrderInfoDTO orderInfo, String operatorId) {
		return orderUpdateService.updateOrderPriceByCoupon(orderInfo, operatorId);
	}

	/**
	 * 查询订单总金额.
	 * 
	 * @param params
	 *            用户ID，订单状态
	 * @return 总金额
	 * 
	 */
	public float queryOrdersAmountByParams(Map<String, Object> params) {
		return orderPriceService.queryOrdersAmountByParams(params);
	}

	public OrdOrderAmountApply findOrderAmountApplyById(final Long ApplyId) {
		return modifyOrderAmountService.selectByPrimaryKey(ApplyId);
	}

	/**
	 * 保存订单金额申请.
	 * 
	 * @param ordOrderAmountApply
	 */
	@Override
	public void insertModifyOrderAmountApply(OrdOrderAmountApply ordOrderAmountApply) {
		this.modifyOrderAmountService.saveModifyOrderAmountApply(ordOrderAmountApply);
	}

	/**
	 * 根据订单查询修改订单金额申请的记录.
	 * 
	 * @param ordOrderAmountApply
	 * @return 申请记录
	 */
	@Override
	public List<OrdOrderAmountApply> queryOrdOrderAmountApply(Map<String, Object> parameter) {
		return this.modifyOrderAmountService.queryOrderAmountApply(parameter);
	}

	/**
	 * 根据订单查询修改订单金额申请的记录数.
	 * 
	 * @param ordOrderAmountApply
	 * @return 申请记录
	 */
	@Override
	public Long queryOrdOrderAmountApplyCount(Map<String, Object> parameter) {
		return this.modifyOrderAmountService.queryOrderAmountApplyCount(parameter);
	}

	/**
	 * 修改订单金额申请.
	 */
	@Override
	public int updateOrderModifyAmountApply(OrdOrderAmountApply ordOrderAmountApply) {
		return this.modifyOrderAmountService.updateOrderModifyAmountApply(ordOrderAmountApply);
	}

	/**
	 * 修改订单金额申请和订单金额 订单金额纪录.
	 */
	@Override
	public int updateOrderModifyAmountApplyOrder(OrdOrderAmountApply ordOrderAmountApply) {
		return this.modifyOrderAmountService.updateOrderModifyAmountApplyOrder(ordOrderAmountApply);
	}

	/**
	 * 订单二次跟踪处理,取特定取消的订单.
	 * 
	 * @return
	 */
	public List<OrdOrder> queryOrderNotTrack(final Long RowNum) {
		return orderCancleService.queryOrderNotTrack(RowNum);
	}

	/**
	 * 生成二次跟踪处理记录.
	 * 
	 * @param number
	 * @param userName
	 */
	public void saveOrdertrack(final Long number, final String userName) {
		orderCancleService.saveOrdertrack(number, userName);
	}

	@Override
	public boolean existsEContract(Long orderId) {
		return ordEContractService.getOrdEContractByOrderId(orderId) != null;
	}
 

	@Override
	public float sumActualSettlementPriceYuan(CompositeQuery compositeQuery) {
		return this.queryService.sumActualSettlementPriceYuan(compositeQuery);
	}

	@Override
	public boolean editOrder(final Long orderId, final Long adult, final Long child) {
		return this.orderUpdateService.editOrder(orderId, adult, child);
	}

	/**
	 * 不能开发票的订单金额
	 * 
	 * @param orderId
	 * @return
	 */
	public long unableInvoiceAmountByOrderId(Long orderId) {
		return orderInvoiceService.getSumCompensationAndRefundment(orderId);
	}

	/**
	 * 获取订单退款金额
	 * 
	 * @param orderId
	 * @return
	 */
	public long getRefundAmountByOrderId(Long orderId, String sysCode) {
		return orderInvoiceService.getRefundAmountByOrderId(orderId, sysCode);
	}
	
	/**
	 * 根据采购产品订单子子项ID更新实际结算价格.
	 * 
	 * @param ordItemId
	 *            采购产品订单子子项ID
	 * @param price
	 *            实际结算价格
	 * @return <pre>
	 * <code>true</code>代表根据采购产品订单子项ID更新实际结算价格成功，<code>false</code>
	 *         代表根据采购产品订单子项ID更新实际结算价格失败
	 * </pre>
	 */
	@Override
	public boolean updateSettPrice(Long ordItemId, Long price, String operatorId) {
		boolean flag = orderUpdateService.updateSettPrice(ordItemId, price, operatorId);
		return flag;
	}

	/**
	 * 根据采购产品订单子子项ID更新实际结算价格.
	 * 
	 * @param ordItemId
	 *            采购产品订单子子项ID
	 * @param price
	 *            实际结算价格
	 * @return <pre>
	 * <code>true</code>代表根据采购产品订单子项ID更新实际结算价格成功，<code>false</code>
	 *         代表根据采购产品订单子项ID更新实际结算价格失败
	 * </pre>
	 */
	@Override
	public boolean updateBatchPrice(String ordItemIds, Long price, String operatorId) {
		boolean flag = orderUpdateService.updateBatchPrice(ordItemIds, price, operatorId);
		return flag;
	}

	// 订单预授权支付的处理----------------------------------------------------

	/**
	 * 根据订单子子项ID查询订单.
	 * 
	 * @param orderItemMetaId
	 * @return
	 */
	@Override
	public OrdOrder queryOrdOrderByOrderItemMetaId(Long orderItemMetaId) {
		return orderAuditService.getOrderByOrderItemMetaId(orderItemMetaId);
	}

	@Override
	public ResultHandle updateOrderItemMetaBranchId(Long orderItemMetaId, Long metaBranchId, SupBCertificateTarget bCertificateTarget, String operatorName) {
		ResultHandle handle = orderUpdateService.updateOrderItemMeta(orderItemMetaId, metaBranchId, bCertificateTarget, operatorName);
		if (handle.isSuccess()) {
			// 检查资源是否已经标注为资源满足
			OrdOrderItemMeta orderItemMeta = queryService.queryOrdOrderItemMetaById(orderItemMetaId);
			if (orderItemMeta.isApproveResourceAmple()) {
				resourceAmple(orderItemMetaId, "SYSTEM", operatorName, null);
			}
		}
		return handle;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setOrderSockCheckService(OrderSockCheckService orderSockCheckService) {
		this.orderSockCheckService = orderSockCheckService;
	}

	@Override
	public List<OrdOrderItemMeta> queryOrdOrderItemMetaByOrderId(Long orderId) {
		return this.queryService.queryOrdOrderItemMetaByOrderId(orderId);
	}

	@Override
	public OrdOrderItemMeta queryOrdOrderItemMetaBy(Long orderItemMetaId) {
		return this.queryService.queryOrdOrderItemMetaById(orderItemMetaId);
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public void setOrderPaymentService(OrderPaymentService orderPaymentService) {
		this.orderPaymentService = orderPaymentService;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

	@Override
	public boolean canAccountPay(OrdOrder order, CashAccountVO moneyAccount) {
		/**
		 * 仅当支付给lvmama 已审核; 存款账户可支付余额>0 未支付或部分支付; 订单状态为：正常 才可以使用现金帐户支付
		 */
		if (order.getPaymentTarget().equals(Constant.PAYMENT_TARGET.TOLVMAMA.name()) && moneyAccount != null && moneyAccount.getMaxPayMoney() > 0 && !order.getPaymentStatus().equals(Constant.PAYMENT_STATUS.PAYED.name()) && order.getOrderStatus().equals(Constant.ORDER_STATUS.NORMAL.name()) && order.getApproveStatus().equals(Constant.ORDER_APPROVE_STATUS.VERIFIED.name())) {
			return true;
		}

		return false;
	}


	@Override
	public boolean canTransferPayment(final Long orderId) {
		OrdOrder order = this.queryOrdOrderByOrderId(orderId);
		if (null == order || null == order.getOriginalOrderId()) {
			LOG.debug("Either order or original order's id cann't be found!");
			return false;
		}
		OrdOrder oriOrder = queryOrdOrderByOrderId(order.getOriginalOrderId());
		if (null == oriOrder) {
			LOG.debug("Original order cann't be found!");
			return false;
		}
		List<OrdRefundment> orderRefundmentList = orderRefundmentService.findValidOrdRefundmentByOrderId(oriOrder.getOrderId());
		if (null != oriOrder 
				&& Constant.ORDER_STATUS.CANCEL.name().equals(oriOrder.getOrderStatus()) 
				&& (Constant.PAYMENT_STATUS.PAYED.name().equals(oriOrder.getPaymentStatus()) || Constant.PAYMENT_STATUS.PARTPAY.name().equals(oriOrder.getPaymentStatus())) 
				&& Constant.SETTLEMENT_STATUS.UNSETTLEMENTED.name().equals(oriOrder.getSettlementStatus()) 
				&& (null == orderRefundmentList || orderRefundmentList.size() == 0)
				&& Constant.ORDER_STATUS.NORMAL.name().equals(order.getOrderStatus())
				&& Constant.PAYMENT_STATUS.UNPAY.name().equals(order.getPaymentStatus())) {
			return true;
		} else {
			return false;
		}
	}
	
	public Long getOrderQuantity(BuyInfo createOrderBuyInfo){
		List<Item> itemList = createOrderBuyInfo.getItemList();
		Long quantity = 0L;
		for (Item item : itemList) {
			if (item.getTimeInfoList() != null && !item.getTimeInfoList().isEmpty()) {
				quantity += item.getAdultQuantity() + item.getChildQuantity();
			} else {
				List<ProdProductItem> productItems = prodProductItemDAO.selectProductItems(item
						.getProductId());
				for (ProdProductItem prodProductItem : productItems) {
					quantity += item.getQuantity() * prodProductItem.getQuantity();
				}
			}
		}
		return quantity;
	}

	public void setPayPaymentRefundmentService(PayPaymentRefundmentService payPaymentRefundmentService) {
		this.payPaymentRefundmentService = payPaymentRefundmentService;
	}

	public void setResourceMessageProducer(TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	@Override
	public Long getOrderAllPrice(BuyInfo createOrderBuyInfo) {
		Long totalOrderItemProdPrice = 0L;
		List<Item> itemList = createOrderBuyInfo.getItemList();
		for (Item item : itemList) {
			if (item.getTimeInfoList() != null && !item.getTimeInfoList().isEmpty()) {
				for (OrdTimeInfo timeInfo : item.getTimeInfoList()) {
					TimePrice timePrice = prodTimePriceDAO.getProdTimePrice(item.getProductId(),
							item.getProductBranchId(), timeInfo.getVisitTime());
					if (timePrice != null) {
						totalOrderItemProdPrice = totalOrderItemProdPrice + item.getQuantity()
								* timePrice.getPrice();
					}
				}

			} else {
				TimePrice timePrice = productTimePriceLogic.loadTimePriceByPidAndData(
						item.getProductId(), item.getVisitTime());
				if (timePrice != null) {
					totalOrderItemProdPrice = totalOrderItemProdPrice + item.getQuantity()
							* timePrice.getPrice();
				}
			}

		}
		return totalOrderItemProdPrice;
	}

	public List<PerformDetail> getOrderPerformDetail(List<Long> orderItemMetaIds){
		return orderPerformService.getOrderPerformDetail(orderItemMetaIds);
	}
 
	public void setOrderRefundService(OrderRefundService orderRefundService) {
		this.orderRefundService = orderRefundService;
	}
	@Override
	public ResultHandle checkTrainOrderLimit(Map<String,Object> params) {
		ResultHandle handle = new ResultHandle();
		List<Long> products = queryService.queryOrderLimitByParam(params);
		if(!products.isEmpty()){
			handle.setMsg("当前的游玩人当中有游客已经下过单");
		}
		return handle;
	}
  
	@SuppressWarnings("unchecked")
	@Override
	public ResultHandle checkCreateOrderLimitIsExisted(Map<String, Object> params) {
		ResultHandle handle = new ResultHandle();
		String productLimit = params.get("productLimit")+"";
		if("true".equals(productLimit)){
			Long count = queryService.queryOrderLimitByUserId(params);
			if(count >=3){
				handle.setMsg("本月你已不能下单。");
				return handle;
			}
			count = queryService.queryOrderLimitByMobile(params);
			if(count >=3){
				handle.setMsg("你的手机号,本月你不能下单。");
				return handle;
			}
		}
		//故宫产品同一个身份证号同一游玩日期只能下一笔订单
		String gugongproductLimit=String.valueOf(params.get("gugongproductLimit"));
		if("true".equals(gugongproductLimit)){
			Long count = queryService.queryOrderLimitByCertNo(params);
			if(count>=1){
				handle.setMsg("同一游玩时间同一证件号只能下一笔订单。");
				return handle;
			}
		}
		 
		Object subProductTypeO = params.get("subProductType");
		if(subProductTypeO != null) {
			
			String subProductType = (String)subProductTypeO;
			Object travellerInfoOptions = params.get("travellerInfoOptions");
			String personType = Constant.ORD_PERSON_TYPE.CONTACT.name();
			//酒店有必填信息，需填写游客或目的地自由行
			if((travellerInfoOptions != null && !((List<String>) travellerInfoOptions).isEmpty())|| Constant.ROUTE_SUB_PRODUCT_TYPE.FREENESS.name().equals(subProductType)) {
				personType = Constant.ORD_PERSON_TYPE.TRAVELLER.name();
			}
			params.put("personType", personType);
			//酒店套餐/目的地自由行
			if(Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name().equals(subProductType) || Constant.ROUTE_SUB_PRODUCT_TYPE.FREENESS.name().equals(subProductType)) {
				Long count = queryService.queryOrderByParams1(params);
				if(count > 0){
					handle.setMsg("您已在相同日期内预订过该产品，为避免您后续的入住困难，请更换游玩人姓名！");
				}
				return handle;
			} else {//单房型
				List<Map<String, Object>> resultList = queryService.queryOrderByParams2(params);
				for (int i = 0; i < resultList.size(); i++) {
					 Map<String, Object> resultMap = resultList.get(i);
					 Object vTime = resultMap.get("VISITTIME");
					 Object lTime = resultMap.get("LEAVETIME");
					 if(vTime != null && lTime != null) {
						 if(DateUtil.formatDate((Date)vTime, "yyyy-MM-dd").equals(DateUtil.formatDate((Date)params.get("visitTime"), "yyyy-MM-dd")) 
								 && DateUtil.formatDate((Date)lTime, "yyyy-MM-dd").equals(DateUtil.formatDate(DateUtil.dsDay_Date((Date)params.get("leaveTime"), -1), "yyyy-MM-dd"))) {
							 handle.setMsg("您已在相同日期内预订过该产品，为避免您后续的入住困难，请更换游玩人姓名！");
							 return handle;
						 }
					 }
				}
			}
		}
		return handle;
	}
	
	/**
	 * 根据订单好查询订单子子项
	 * @param orderId
	 * @return
	 */
	public List<SetSettlementItem> searchOrderItemMetaListByOrderId(Long orderId){
		List<SetSettlementItem> returnList = new ArrayList<SetSettlementItem>();
		List<OrdOrderItemMeta> list = orderItemMetaDAO.selectByOrderId(orderId);
		for(OrdOrderItemMeta im : list){
			String period = "";
			List<SupSettlementTarget>  targetList = settlementTargetService.getSuperSupSettlementTargetByMetaProductId(im.getMetaProductId());
			if(targetList.size() > 0){
				period = targetList.get(0).getSettlementPeriod();
			}
			SupSupplier sup = supplierService.getSupplier(im.getSupplierId());
			SetSettlementItem item = new SetSettlementItem();
			item.setSupplierName(sup.getSupplierName());
			item.setMetaProductName(im.getProductName());
			item.setSettlementStatus(im.getSettlementStatus());
			item.setSettlementPeriod(period);
			returnList.add(item);
		}
		return returnList;
	}

	public void setOrderItemMetaDAO(OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}

	public void setSettlementTargetService(
			SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}

	@Override
	public Long queryOrderProfitByOrderId(Long orderId) {
		return queryService.queryOrderProfitByOrderId(orderId);
	}

	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	@Override
	public void updateCertificateStatusAndTypeOrConfirmChannel(
			Long ordItemMetaId, String certificateStatus,
			String ebkCertificateType, String confirmChannel) {
		 this.orderUpdateService.updateCertificateStatusAndTypeOrConfirmChannel(
				 ordItemMetaId, certificateStatus,ebkCertificateType,confirmChannel);;	
	}
	
	@Override
	public boolean updateIsCashRefundByOrderId(OrdOrder order) {
		return orderUpdateService.updateIsCashRefundByOrderId(order);
	}

	public void setSetTransferTaskService(
			SetTransferTaskService setTransferTaskService) {
		this.setTransferTaskService = setTransferTaskService;
	}
	
	/**
	 * 修改订单、订单子项及子子项的游玩日期游玩时间
	 * @return
	 */
	@Override
	public ResultHandle updateOrderVisitTime(Long orderId, String visitTimeStart,String userId,List<Long> orderItemMetaIds){
		ResultHandle handle=new ResultHandle();
		try{
			Date visitTime = null;
			if (!StringUtil.isEmptyString(visitTimeStart)) {
				visitTime = DateUtil.stringToDate(visitTimeStart,"yyyy-MM-dd");
			}
			OrdOrder ordOrder = this.queryOrdOrderByOrderId(orderId);
			if(ordOrder.getVisitTime() != null) {} else {
				ordOrder.setVisitTime(visitTime);
				this.updateOrdOrderByPrimaryKey(ordOrder);
			}
			//更新产品优惠后的采购产品子项
			List<FavorProductResult> favorProductResultList = favorService.getFavorMetaProductResultByOrderInfo(ordOrder);
			for (Long orderItemMetaId : orderItemMetaIds) {
				OrdOrderItemMeta itemMeta = orderItemMetaDAO.selectByPrimaryKey(orderItemMetaId);
				TimePrice tp = metaTimePriceDAO.getMetaTimePriceByIdAndDate(itemMeta.getMetaBranchId(), DateUtil.getDayStart(visitTime));
				if(tp != null) {
					itemMeta.setSettlementPrice(tp.getSettlementPrice());
					itemMeta.setActualSettlementPrice(itemMeta.getSettlementPrice());
					itemMeta.setTotalSettlementPrice(itemMeta.getActualSettlementPrice()*itemMeta.getQuantity()*itemMeta.getProductQuantity());
				}
				itemMeta.setVisitTime(visitTime);
				
				OrdOrderItemProd itemProd = orderItemProdDAO.selectByPrimaryKey(itemMeta.getOrderItemId());
				if(itemProd.getVisitTime()!= null) {} else{
					itemProd.setVisitTime(visitTime);
					orderUpdateService.updateOrderItemProdByPrimaryKey(itemProd,userId);
				}
				
				if(CollectionUtils.isNotEmpty(favorProductResultList)) {
					//计算优惠后的结算价
					for(int i = 0; i < favorProductResultList.size(); i++){//遍历，获取对应的优惠策略结果，然后先计算出优惠后的结算价，总结算价
						FavorProductResult favorProductResult = favorProductResultList.get(i);
						if(itemMeta.getMetaProductId().equals(favorProductResult.getProductId()) 
								&& itemMeta.getMetaBranchId().equals(favorProductResult.getProductBranchId())){
							Long discountAmount = favorProductResult.getDiscountAmount(itemMeta, 0, true);
							//设置优惠后的采购销售价
							itemMeta.setActualSettlementPrice(itemMeta.getSettlementPrice() -  discountAmount/ (itemMeta.getQuantity() * itemMeta.getProductQuantity()));
							//设置优惠后的采购总销售价
							itemMeta.setTotalSettlementPrice(itemMeta.getActualSettlementPrice()*itemMeta.getQuantity()*itemMeta.getProductQuantity());
						}
					}
				}
				orderUpdateService.updateOrderItemMetaByPrimaryKey(itemMeta,userId);
			}
			//游玩时间为空则发送取消预订消息，否则发送激活消息
			if (StringUtil.isEmptyString(visitTimeStart)) {
				orderMessageProducer.sendMsg(MessageFactory.orderCancelActivatedOrdMessage(orderId, orderItemMetaIds));
			}else{
				orderMessageProducer.sendMsg(MessageFactory.orderActivatedOrdMessage(orderId, orderItemMetaIds));
			}
		}catch(Exception e) {
			handle.setMsg("修改订单游玩日期出错");
		}
		return handle;
	}
	
	public void setOrderItemProdDAO(OrderItemProdDAO orderItemProdDAO) {
		this.orderItemProdDAO = orderItemProdDAO;
	}


	public void setSupplierStockCheckService(
			SupplierStockCheckService supplierStockCheckService) {
		this.supplierStockCheckService = supplierStockCheckService;
	}
	public ProdProductService getProdProductService() {
		return prodProductService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	@Override
	public Long queryOrdOrderCount(Map<String, Object> params) {
		return queryService.queryOrdOrderCount(params);
	}
	public void setOrderItemMetaSaleAmountServie(
			OrderItemMetaSaleAmountServie orderItemMetaSaleAmountServie) {
		this.orderItemMetaSaleAmountServie = orderItemMetaSaleAmountServie;
	}
	/**
	 * 根据订单子项id查询
	 * @param ordOrderItemMetaId
	 * @return
	 */
	public OrdOrderItemMeta getOrdOrderItemMeta(Long ordOrderItemMetaId){
		return this.orderItemMetaDAO.selectByPrimaryKey(ordOrderItemMetaId);
	}

	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}

	public void setFavorService(FavorService favorService) {
		this.favorService = favorService;
	}

	@Override
	public List<OrdOrder> queryOrderByThreeMonthsAgoWeek(Date time) {
	    return orderDAO.queryOrderByThreeMonthsAgoWeek(time);
	}

	public void setVstOrdOrderService(VstOrdOrderService vstOrdOrderService) {
		this.vstOrdOrderService = vstOrdOrderService;
	}

	@Override
	public List<OrdOrder> queryOrderBySeckill(Map<String, Object> paramMap) {
		return orderDAO.queryOrderBySeckill(paramMap);
	}
	/**根据批量productId查询订单总数*/ 
	@Override
	public Long getOrderCountByProductIds(String[] productIds, Date startTime,
			Date endTime) {
		return orderItemProdDAO.getOrderCountByProductIds(productIds,startTime,endTime);
	}

	@Override
	public long getOrderBonusReturnAmount(OrdOrder ordOrder) {
		return bonusReturnLogic.getOrderBonusReturnAmount(ordOrder);
	}

	public void setBonusReturnLogic(BonusReturnLogic bonusReturnLogic) {
		this.bonusReturnLogic = bonusReturnLogic;
	}
	
	
	
}
