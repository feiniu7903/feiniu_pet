package com.lvmama.jinjiang.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaTravelCode;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderSHHoliday;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.bee.service.meta.MetaTravelCodeService;
import com.lvmama.comm.bee.service.ord.OrdOrderSHHolidayService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.OrderUitl;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderSenderBiz;
import com.lvmama.jinjiang.JinjiangClient;
import com.lvmama.jinjiang.model.Contact;
import com.lvmama.jinjiang.model.Guest;
import com.lvmama.jinjiang.model.Order;
import com.lvmama.jinjiang.model.OrderDetail;
import com.lvmama.jinjiang.model.Receivable;
import com.lvmama.jinjiang.model.request.AddOrderRequest;
import com.lvmama.jinjiang.model.request.CancelOrderRequest;
import com.lvmama.jinjiang.model.request.GetOrderRequest;
import com.lvmama.jinjiang.model.request.NotifyCancelOrderRequest;
import com.lvmama.jinjiang.model.request.PayedRequest;
import com.lvmama.jinjiang.model.response.AddOrderResponse;
import com.lvmama.jinjiang.model.response.CancelOrderResponse;
import com.lvmama.jinjiang.model.response.GetOrderResponse;
import com.lvmama.jinjiang.model.response.PayedResponse;
import com.lvmama.jinjiang.service.JinjiangOrderService;
import com.lvmama.jinjiang.vo.order.OrderMessage;
import com.lvmama.passport.utils.WebServiceConstant;
import com.opensymphony.oscache.util.StringUtil;

public class JinjiangOrderServiceImpl implements JinjiangOrderService {

	private static final Log logger =LogFactory.getLog(JinjiangOrderServiceImpl.class);
	
	private OrderService orderServiceProxy;
	private OrdRefundMentService ordRefundMentService;
	private OrdSaleServiceService ordSaleServiceService;
	private MetaTravelCodeService metaTravelCodeService;
	private OrdOrderSHHolidayService ordOrderSHHolidayService;
	private ComLogService comLogService;
	private WorkOrderSenderBiz workOrderProxy;
	
	public String submitOrder(Long orderId) {
		String message="";
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if (isJinJiangOrder(order)) {
			//第一次下单系统领单
			orderServiceProxy.makeOrdOrderItemMetaListToAudit("system",getOrderItemMetaIds(order));
			try{
				Order jjOrder=initOrder(order);
				AddOrderRequest corderReq = new AddOrderRequest(jjOrder);
				AddOrderResponse response = new JinjiangClient().execute(corderReq);
				if(response.isSuccess() && "BOOKING_CONFIRMED".equalsIgnoreCase(response.getOutOrder().getOrderStatus())){
					saveExternalOrderInfo(order,response.getOutOrder().getOrderNo());
					message= "锦江下单成功";
				}else{
					if(needCancelOrder(response.getErrorcode())){
						ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG_CANCEL.getCode(),"CANCEL"));
						orderServiceProxy.cancelOrder(orderId, "锦江电商通知取消订单", "SYSTEM");
						message= "锦江下单失败"+ response.getErrormessage();
					}else{
						message = booking(order, response.getErrormessage());
					}
				}
			}catch(Exception e ){
				logger.error("锦江下单异常 订单号：" + orderId +e);
				message =  booking(order, "锦江下单异常 ");
			}
			addComLog(message,orderId);
		}
		return message;
	}

	private List<Long> getOrderItemMetaIds(OrdOrder order) {
		List<Long> orderItemMetaIdList = new ArrayList<Long>();
		for(OrdOrderItemMeta item:order.getAllOrdOrderItemMetas()){
			orderItemMetaIdList.add(item.getOrderItemMetaId());
		}
		return orderItemMetaIdList;
	}

	public String reSubmitOrder(Long orderId) {
		String message= "锦江下单成功";
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if (isJinJiangOrder(order) && canBookingToSupplier(order)) {
			try{
				Order jjOrder=initOrder(order);
				AddOrderRequest corderReq = new AddOrderRequest(jjOrder);
				AddOrderResponse response = new JinjiangClient().execute(corderReq);
				if(response.isSuccess() && "BOOKING_CONFIRMED".equalsIgnoreCase(response.getOutOrder().getOrderStatus())){
					saveExternalOrderInfo(order,response.getOutOrder().getOrderNo());
					message= "锦江下单成功";
				}else{
					if(needCancelOrder(response.getErrorcode())){
						ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG_CANCEL.getCode(),"CANCEL"));
						orderServiceProxy.cancelOrder(orderId, "锦江电商通知取消订单", "SYSTEM");
						message= "锦江下单失败"+ response.getErrormessage();
					}else{
						message = booking(order, response.getErrormessage());
					}
				}
			}catch(Exception e ){
				logger.error("锦江下单异常 订单号：" + orderId +e);
				message =  booking(order, "锦江下单异常 ");
			}
			addComLog(message,orderId);
		}
		return message;
	}
	
	@Override
	public String cancelOrder(Long orderId) {
		String message="";
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if(isJinJiangOrder(order)){
			try{
				if(!order.isPaymentSucc()){
					OrdOrderSHHoliday sh = new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG.getCode(),null);
					OrdOrderSHHoliday sh1 = ordOrderSHHolidayService.selectByObjectIdAndObjectType(sh);
					if(sh1!=null){
						CancelOrderRequest rquest = new CancelOrderRequest(sh1.getContent(),""+orderId);
						CancelOrderResponse response = new JinjiangClient().execute(rquest);
						if(response.isSuccess() && "CANCELED".equalsIgnoreCase(response.getOrderStatus())){
							message= "锦江取消订单成功";
							ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG_CANCEL.getCode(),"CANCEL"));
						}else{
							OrderDetail detail = getThirdOrder(orderId);
							if("CANCELED".equals(detail.getOrderStatus())){
								message= "锦江取消订单成功";
								ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG_CANCEL.getCode(),"CANCEL"));
							}else{
								message = " 锦江取消订单失败 ";
								if(response.getErrormessage()!=null){
									message+=response.getErrormessage();
								}
								ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG_CANCEL.getCode(),"ERROR"));
								sendWorkOrder(order,Constant.WORK_ORDER_TYPE_AND_SENDGROUP.GYSQDQXDSBCX.getWorkOrderTypeCode());
							}
						}
					}
				}
			}catch(Exception e ){
				message = "锦江取消订单异常  ";
				ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG_CANCEL.getCode(),"ERROR"));
				sendWorkOrder(order,Constant.WORK_ORDER_TYPE_AND_SENDGROUP.GYSQDQXDSBCX.getWorkOrderTypeCode());
				logger.error("锦江取消订单异常 订单号：" + orderId +e);
			}
			addComLog(message,orderId);
		}
		return message;
	}

	@Override
	public String payOrder(Long orderId) {
		String message="";
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if(isJinJiangOrder(order)){
			try{
				PayedRequest rquest = InitPayedRequest(order);
				PayedResponse response = new JinjiangClient().execute(rquest);
				if(response.isSuccess() && "PAYED".equalsIgnoreCase(response.getPayStatus())){
					message= "锦江支付订单成功";
					ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG_PAY.getCode(),"PAYED"));
				}else{
					OrderDetail detail = getThirdOrder(orderId);
					if("PAYED".equals(detail.getPayStatus())){
						message= "锦江支付订单成功";
						ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG_PAY.getCode(),"PAYED"));
					}else{
						message = "锦江支付订单失败";
						if(response.getErrormessage()!=null){
							message+=response.getErrormessage();
						}
						ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG_PAY.getCode(),"ERROR"));
						sendWorkOrder(order,Constant.WORK_ORDER_TYPE_AND_SENDGROUP.GYSQDZPDSBCX.getWorkOrderTypeCode());
					}
					
				}
			}catch(Exception e ){
				ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG_PAY.getCode(),"ERROR"));
				sendWorkOrder(order,Constant.WORK_ORDER_TYPE_AND_SENDGROUP.GYSQDZPDSBCX.getWorkOrderTypeCode());
				message = "锦江支付订单异常";
				logger.error("锦江支付订单异常 订单号：" + orderId +e);
			}
			addComLog(message,orderId);
		}
		return message;
	}
	public OrderMessage notifyCancelOrder(NotifyCancelOrderRequest cancelOrder){
		if(StringUtil.isEmpty(cancelOrder.getThirdOrderNo())){
			return new OrderMessage(OrderMessage.MessageCode.M_0001);
		}
		try{
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(Long.parseLong(cancelOrder.getThirdOrderNo()));
			if(order == null){
				return new OrderMessage(OrderMessage.MessageCode.M_0002);
			}
			if(isJinJiangOrder(order)){
				
				if(order.isPaymentSucc()){
					refunded(order,cancelOrder.getLossAmount().longValue());
				}else{
					if(order!=null && !Constant.ORDER_STATUS.CANCEL.getCode().equals(order.getOrderStatus())){
						orderServiceProxy.cancelOrder(Long.parseLong(cancelOrder.getThirdOrderNo()), "锦江电商通知取消订单", "SYSTEM");
						addComLog("锦江通知取消订单",Long.parseLong(cancelOrder.getThirdOrderNo()));
					}
				}
			}else{
				return new OrderMessage(OrderMessage.MessageCode.M_0002);
			}
		}catch(Exception e ){
			logger.error("锦江通知订单取消异常 " +e);
			addComLog("锦江通知订单取消异常",Long.parseLong(cancelOrder.getThirdOrderNo()));
			return new OrderMessage(OrderMessage.MessageCode.M_0003);
		}
		return new OrderMessage(OrderMessage.MessageCode.M_0000);
	}
	private String booking( OrdOrder order,String errormessage) {
		String message;
		Long orderId = order.getOrderId();
		OrderDetail detail = getThirdOrder(orderId);
		if(detail!=null && "BOOKING_CONFIRMED".equalsIgnoreCase(detail.getOrderStatus())){
			saveExternalOrderInfo(order,detail.getOrderNo());
			message= "锦江下单成功";
		}else if(detail!=null&&!StringUtil.isEmpty(detail.getOrderNo())){
			ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG_CANCEL.getCode(),"CANCEL"));
			orderServiceProxy.cancelOrder(orderId, "锦江取消订单", "SYSTEM");
			message= "下单失败  锦江系统下单失败";
		}else{
			message = "锦江下单失败";
			if(!StringUtil.isEmpty(errormessage)){
				message += errormessage;
			}
			//需要人工进行审核
			ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_APPROVE.getCode(),"TRUE"));
			sendWorkOrder(order,Constant.WORK_ORDER_TYPE_AND_SENDGROUP.GYSQDXDSBCX.getWorkOrderTypeCode());
		}
		return message;
	}
	private boolean canBookingToSupplier(OrdOrder order) {
		OrderDetail detail = getThirdOrder(order.getOrderId());
		if(detail==null || StringUtil.isEmpty(detail.getOrderNo())){
			return true;
		}
		if(detail!=null && ("BOOKING_FAILED".equalsIgnoreCase(detail.getOrderStatus())||"CANCELED".equalsIgnoreCase(detail.getOrderStatus()))){
			ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(order.getOrderId(),Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG_CANCEL.getCode(),"CANCEL"));
			if(order!=null && !Constant.ORDER_STATUS.CANCEL.getCode().equals(order.getOrderStatus())){
				orderServiceProxy.cancelOrder(order.getOrderId(), "锦江电商通知取消订单", "SYSTEM");
			}
		}
		return false;
	}
	private void sendWorkOrder(OrdOrder order,String workOrderType) {
		if(Constant.ORDER_TYPE.GROUP_FOREIGN.getCode().equalsIgnoreCase(order.getOrderType()) || 
				Constant.ORDER_TYPE.FREENESS_FOREIGN.getCode().equalsIgnoreCase(order.getOrderType())){
			workOrderType = workOrderType.replace("CX", "CJ");
		}
		workOrderProxy.sendWorkOrder(order,workOrderType ,
				"/super_back/ord/doGetOrderByOrderId.do?orderType="
						+ order.getOrderType() + "&orderId="
						+ order.getOrderId(), Boolean.TRUE,
				Boolean.FALSE, null, null, null, null, null, false);
	}
	public OrderDetail getThirdOrder(Long orderId){
		OrderDetail orderDetail = new OrderDetail();
		OrdOrderSHHoliday sh = new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG.getCode(),null);
		OrdOrderSHHoliday sh1 = ordOrderSHHolidayService.selectByObjectIdAndObjectType(sh);
		if(sh1!=null){
			GetOrderRequest req = new GetOrderRequest(sh1.getContent(),""+orderId);
			GetOrderResponse res = new JinjiangClient().execute(req); 
			if(res.isSuccess()){
				return res.getOrder();
			}
		}
		return orderDetail;
	}
	
	
	/**
	 * 
	 * @param orderId 订单ID
	 * @param lossAmount 损失金额
	 */
	private void refunded(OrdOrder order, Long lossAmount) {
		Long orderId = order.getOrderId();
		try{
			lossAmount = lossAmount*100 ;
			Long refAmount = order.getActualPay()-lossAmount;
			orderServiceProxy.updateNeedSaleService("true", orderId, "SYSTEM");
			
			for(OrdOrderItemMeta item:order.getAllOrdOrderItemMetas()){
				if(item.getSupplierId().toString().equals(WebServiceConstant.getProperties("jinjiang.supplierId"))){
					item.setAmountValue(0L);
					item.setActualLoss(0L);
					if(lossAmount<=0){
						break;
					}
					if(item.getTotalSettlementPrice()>=lossAmount){
						item.setAmountValue(lossAmount);
						item.setActualLoss(lossAmount);
					}else{
						item.setAmountValue(item.getTotalSettlementPrice());
						item.setActualLoss(item.getTotalSettlementPrice());
					}
					lossAmount = lossAmount - item.getTotalSettlementPrice();
				}
			}
			ordRefundMentService.applyRefund(orderId,saveSaleService(orderId), order.getAllOrdOrderItemMetas(), 
					 refAmount, Constant.REFUNDMENT_TYPE.ORDER_REFUNDED.toString(), "UNVERIFIED", "申请退款", "STSTEM", 0L);				 
			if(order!=null && order.isCancelAble()){
				orderServiceProxy.cancelOrder(orderId, "锦江电商通知废单", "SYSTEM");
			}
			 
		}catch(Exception e){
			logger.error("锦江通知订单废单 操作异常 orderId=" + orderId);
		}
	}

	private Long saveSaleService(Long orderId){
		OrdSaleService ordSevice=new OrdSaleService();
		ordSevice.setCreateTime(new Date());
		ordSevice.setOperatorName("SYSTEM");
		ordSevice.setOrderId(orderId);
		ordSevice.setApplyContent("申请退款");
		ordSevice.setServiceType("NORMAL");
		ordSevice.setStatus("NORMAL");
		return ordSaleServiceService.addOrdSaleService(ordSevice);
	}
	
	private Order initOrder(OrdOrder order) {
		Order jjOrder = new Order();
		Long adultNum =0L;
		Long childNum =0L;
		if(!CollectionUtils.isEmpty(order.getAllOrdOrderItemMetas())){
			OrdOrderItemMeta itemMeta = order.getAllOrdOrderItemMetas().get(0);
			for(OrdOrderItemMeta i:order.getAllOrdOrderItemMetas()){
				if("ROUTE".equals(i.getProductType()) 
						&& StringUtils.isNotEmpty(i.getProductIdSupplier()) 
							&&! Constant.ROUTE_BRANCH.VIRTUAL.getCode().equalsIgnoreCase(i.getProductTypeSupplier())){
					itemMeta = i;
					if(Constant.ROUTE_BRANCH.ADULT.getCode().equals(itemMeta.getProductTypeSupplier())){
						adultNum = adultNum + itemMeta.getQuantity();
					}
					if(Constant.ROUTE_BRANCH.CHILD.getCode().equals(itemMeta.getProductTypeSupplier())){
						childNum = childNum + itemMeta.getQuantity();
					}
				}
			}
		}
		jjOrder.setAdultNum(adultNum.intValue());
		jjOrder.setChildNum(childNum.intValue());
		jjOrder.setContact(initContact(order));
		jjOrder.setExtension(null);
		
		jjOrder.setGuests(initOrderGuests(order));
		jjOrder.setReceivables(initOrderReceivables(jjOrder,order));
		jjOrder.setRemark(order.getUserMemo());
		jjOrder.setThirdOrderNo(""+order.getOrderId());
		jjOrder.setTotalAmount(getOrderAmount(order));
		jjOrder.setTotalPersonsNum(adultNum.intValue() + childNum.intValue());
		return jjOrder;
	}
	

	private List<Receivable> initOrderReceivables(Order jjOrder,OrdOrder order) {
		List<Receivable> res = new ArrayList<Receivable>();
		if(!CollectionUtils.isEmpty(order.getAllOrdOrderItemMetas())){
			for(OrdOrderItemMeta i:order.getAllOrdOrderItemMetas()){
				if("ROUTE".equals(i.getProductType()) 
						&& StringUtils.isNotEmpty(i.getProductIdSupplier()) 
							&& !Constant.ROUTE_BRANCH.VIRTUAL.getCode().equalsIgnoreCase(i.getProductTypeSupplier())){
					Receivable re = new Receivable();
					re.setCopiesByLong(i.getQuantity());
					re.setReservationPriceByLong(i.getSellPrice());
					MetaTravelCode metaTravelCode = metaTravelCodeService.selectBySuppAndDateAndChannelAndBranch(i.getProductIdSupplier(), DateUtil.accurateToDay(i.getVisitTime())
							,Constant.SUPPLIER_CHANNEL.JINJIANG.getCode(),i.getProductTypeSupplier());
					if(metaTravelCode!=null){
						jjOrder.setGroupCode(metaTravelCode.getTravelCodeId());
						re.setPriceCode(metaTravelCode.getTravelCode());
					}
					res.add(re);
				}
			}
		}
		return res;
	}

	private boolean isJinJiangOrder(OrdOrder order) {
		return OrderUitl.isjinjiangOrder(order);
	}
	
	private void saveExternalOrderInfo(OrdOrder order,String thirdOrderNo) {
		OrdOrderSHHoliday sh = new OrdOrderSHHoliday(order.getOrderId(),Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG.getCode(),thirdOrderNo);
		ordOrderSHHolidayService.insertOrUpdate(sh);
		
		for(OrdOrderItemMeta item:order.getAllOrdOrderItemMetas()){
			Date date = order.getVisitTime();
			if (null != order.getCreateTime() && null != order.getWaitPayment()) {
	    		Calendar cal = Calendar.getInstance();
	    		cal.setTime(order.getCreateTime());
	    		cal.add(Calendar.MINUTE, new Long(order.getWaitPayment()).intValue());
	    		date = cal.getTime();
	    	}
			orderServiceProxy.resourceAmple(item.getOrderItemMetaId(),"system","",date);
		}
	}
	
	private Contact initContact(OrdOrder order) {
		Contact contact = new Contact();
		if(order.getContact()!=null){
			contact.setAddress(order.getContact().getAddress());
			contact.setEmail(order.getContact().getEmail());
			contact.setFax(order.getContact().getFax());
			contact.setMobile(order.getContact().getMobile());
			contact.setName(order.getContact().getName());
			contact.setPostCode(order.getContact().getPostcode());
			contact.setTelephone(order.getContact().getTel());
		}
		return contact;
	}

	private List<Guest> initOrderGuests(OrdOrder order) {
		List<Guest> orderPassengers = new ArrayList<Guest>();
		for(OrdPerson per: order.getTravellerList()){
			if(StringUtils.equals(per.getPersonType(),Constant.ORD_PERSON_TYPE.TRAVELLER.name())){
				Guest orderPrassenger = new Guest();
				orderPrassenger.setBirthday(per.getBrithday());
				orderPrassenger.setCertificationNumber(per.getCertNo());
				orderPrassenger.setCertificationType(per.getCertType());
				orderPrassenger.setMobile(per.getMobile());
				orderPrassenger.setName(per.getName());
				orderPrassenger.setSex(per.getGender());
				orderPrassenger.setPriceCategory("");
				orderPrassenger.setOtherContactInfo("");
				orderPassengers.add(orderPrassenger);
			}
		}
		return orderPassengers;
	}

	private PayedRequest InitPayedRequest(OrdOrder order) {
		PayedRequest payedRequest = new PayedRequest();
		BigDecimal actualAmount = getOrderAmount(order);
		payedRequest.setActualAmount(actualAmount);
		OrdOrderSHHoliday sh = new OrdOrderSHHoliday(order.getOrderId(),Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG.getCode(),null);
		OrdOrderSHHoliday sh1 = ordOrderSHHolidayService.selectByObjectIdAndObjectType(sh);
		payedRequest.setOrderNo(sh1.getContent());
		payedRequest.setThirdOrderNo(""+order.getOrderId());
		return payedRequest;
	}

	private BigDecimal getOrderAmount(OrdOrder order) {
		Long payAmount =0l;
		for(OrdOrderItemMeta itemMeta:order.getAllOrdOrderItemMetas()){
			if(itemMeta.getProductTypeSupplier()!=null){
				if(Constant.ROUTE_BRANCH.ADULT.getCode().equals(itemMeta.getProductTypeSupplier())){
					payAmount = payAmount + itemMeta.getSellPrice()*itemMeta.getQuantity();
				}
				if(Constant.ROUTE_BRANCH.CHILD.getCode().equals(itemMeta.getProductTypeSupplier())){
					payAmount = payAmount + itemMeta.getSellPrice()*itemMeta.getQuantity();
				}
				if(Constant.ROUTE_BRANCH.FANGCHA.getCode().equals(itemMeta.getProductTypeSupplier())){
					payAmount = payAmount + itemMeta.getSellPrice()*itemMeta.getQuantity();
				}
			}
		}
		BigDecimal actualAmount = new BigDecimal(payAmount).divide(new BigDecimal(100)).setScale(2);
		return actualAmount;
	}

	
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setOrdOrderSHHolidayService(
			OrdOrderSHHolidayService ordOrderSHHolidayService) {
		this.ordOrderSHHolidayService = ordOrderSHHolidayService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	
	public void setMetaTravelCodeService(MetaTravelCodeService metaTravelCodeService) {
		this.metaTravelCodeService = metaTravelCodeService;
	}

	public void setWorkOrderProxy(WorkOrderSenderBiz workOrderProxy) {
		this.workOrderProxy = workOrderProxy;
	}

	
	public void setOrdRefundMentService(OrdRefundMentService ordRefundMentService) {
		this.ordRefundMentService = ordRefundMentService;
	}

	public void setOrdSaleServiceService(OrdSaleServiceService ordSaleServiceService) {
		this.ordSaleServiceService = ordSaleServiceService;
	}

	private boolean needCancelOrder(String errorcode) {
		if("3035".equalsIgnoreCase(errorcode) || "3036".equalsIgnoreCase(errorcode)){
			return true;
		}
		return false;
	}
	/**
	 * 订单日志
	 */
	public void addComLog(String content, Long orderId) {
		ComLog log = new ComLog();
		log.setObjectId(orderId);
		log.setObjectType("ORD_ORDER");
		log.setOperatorName("SYSTEM");
		log.setLogName("订单操作");
		log.setLogType("OrderStatus");
		log.setContent(content);
		log.setParentType("ORD_ORDER");
		comLogService.addComLog(log);
	}
}
