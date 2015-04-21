package com.lvmama.shholiday.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaTravelCode;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderSHHoliday;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.service.favor.FavorOrderService;
import com.lvmama.comm.bee.service.meta.MetaTravelCodeService;
import com.lvmama.comm.bee.service.ord.OrdOrderSHHolidayService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponUsage;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.businessCoupon.BusinessCouponService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.OrderUitl;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderSenderBiz;
import com.lvmama.shholiday.ShholidayClient;
import com.lvmama.shholiday.request.CancelOrderRequest;
import com.lvmama.shholiday.request.CreateOrderRequest;
import com.lvmama.shholiday.request.ModifyOrderRequest;
import com.lvmama.shholiday.request.OrderDetailRequest;
import com.lvmama.shholiday.request.PayOrderRequest;
import com.lvmama.shholiday.request.UpdateOrderRequest;
import com.lvmama.shholiday.response.CancelOrderResponse;
import com.lvmama.shholiday.response.CreateOrderResponse;
import com.lvmama.shholiday.response.ModifyOrderResponse;
import com.lvmama.shholiday.response.OrderDetailResponse;
import com.lvmama.shholiday.response.PayOrderResponse;
import com.lvmama.shholiday.response.UpdateOrderResponse;
import com.lvmama.shholiday.service.SHHolidayOrderService;
import com.lvmama.shholiday.vo.order.Contact;
import com.lvmama.shholiday.vo.order.OrderDetailInfo;
import com.lvmama.shholiday.vo.order.OrderModifyInfo;
import com.lvmama.shholiday.vo.order.OrderPassenger;
import com.lvmama.shholiday.vo.order.OrderPayInfo;
import com.opensymphony.oscache.util.StringUtil;

public class SHHolidayOrderServiceImpl implements SHHolidayOrderService {

	private static final Log logger = LogFactory.getLog(SHHolidayOrderServiceImpl.class);
	private OrderService orderServiceProxy;
	private FavorOrderService favorOrderService;
	private BusinessCouponService businessCouponService;
	private MetaTravelCodeService metaTravelCodeService;
	private OrdOrderSHHolidayService ordOrderSHHolidayService;
	private ComLogService comLogService;
	private ComMessageService comMessageService;
	ShholidayClient shholidayClient;
	private WorkOrderSenderBiz workOrderProxy;
	
	@Override
	public String submitOrder(Long orderId) {
		String message="";
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		try{
			if (isSHHolidayOrder(order)) {
				//第一次下单系统领单
				orderServiceProxy.makeOrdOrderItemMetaListToAudit("system",getOrderItemMetaIds(order));
				
				Long favorAmount = calculateFavorAmount(order);
				List<BusinessCoupon> businessCoupons = querybusinessCoupons(order);
				MetaTravelCode metaTravelCode=queryTeamUniqueId(order);
				CreateOrderRequest corderReq = new CreateOrderRequest(order,businessCoupons,favorAmount,metaTravelCode);
				CreateOrderResponse response = shholidayClient.execute(corderReq);
				if(response.isSuccess()){
					saveExternalOrderInfo(order,response);
					message= "上航下单成功";
				}else{
					if(needCancelOrder(response.getHeader().getCode())){
						ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG_CANCEL.getCode(),"CANCEL"));
						orderServiceProxy.cancelOrder(orderId, "通知上航取消订单", "SYSTEM");
						message= "上航下单失败"+ response.getHeader().getException();
					}else{
						message = booking(order, response.getHeader().getException());
					}
				}
				addComLog(message,orderId);
			}
		}catch(Exception e ){
			message = booking(order, "上航假期下单异常");
			logger.error("上航假期下单异常 " + e);
		}
		return message;
	}
	public String reSubmitOrder(Long orderId){
		String message="";
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		try{
			if (isSHHolidayOrder(order)&& canBookingToSupplier(order)) {
				Long favorAmount = calculateFavorAmount(order);
				List<BusinessCoupon> businessCoupons = querybusinessCoupons(order);
				MetaTravelCode metaTravelCode=queryTeamUniqueId(order);
				CreateOrderRequest corderReq = new CreateOrderRequest(order,businessCoupons,favorAmount,metaTravelCode);
				CreateOrderResponse response = shholidayClient.execute(corderReq);
				if(response.isSuccess()){
					saveExternalOrderInfo(order,response);
					message= "上航下单成功";
				}else{
					if(needCancelOrder(response.getHeader().getCode())){
						ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_JINJIANG_CANCEL.getCode(),"CANCEL"));
						orderServiceProxy.cancelOrder(orderId, "通知上航取消订单", "SYSTEM");
						message= "上航下单失败"+ response.getHeader().getException();
					}else{
						message = booking(order, response.getHeader().getException());
					}
					
				}
				addComLog(message,orderId);
			}
		}catch(Exception e ){
			message = booking(order, "上航假期下单异常");
			message = "上航假期下单异常 " + e.getCause();
		}
	
		return message;
	}
	public OrderDetailInfo findOrder(Long orderId){
		try{
			OrderDetailRequest corderReq = new OrderDetailRequest(""+orderId);
			OrderDetailResponse response = shholidayClient.execute(corderReq);
			if(response!=null){
				return response.getOrderDetailInfo();
			}
		}catch(Exception e){
			logger.error("上航查询订单异常：" + e);
		}
		return null;
	}
	@Override
	public String updateOrder(Long orderId) {
		String message="";
		try{
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if(isSHHolidayOrder(order)){
				List<OrderPassenger> orderPassengers = initOrderPassengers(order);
				Contact contact = initContact(order);
				String uniqueID = getUniqueIdByOrderId(order.getOrderId());
				UpdateOrderRequest rquest = new UpdateOrderRequest(orderPassengers,contact,uniqueID);
				UpdateOrderResponse response = shholidayClient.execute(rquest);
				if(response.isSuccess()){
					message= "更新订单成功";
				}else{
					message = "上航假期更新订单失败";
					if(response.getHeader()!=null && response.getHeader().getException()!=null){
						message+=response.getHeader().getException();
					}else if(response.getHeader()!=null && response.getHeader()!=null){
						message+=response.getHeader().getException();
					}
				}
				addComLog(message,orderId);
			}
		}catch(Exception e ){
			message = "上航假期更新订单异常 ";
		}
		return message;
	}
	
	@Override
	public String cancelOrder(Long orderId) {
		String message="";
		try{
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if(isSHHolidayOrder(order)){
				if(order.isPaymentSucc()){
					return modifyOrder(order);
				}else{
					OrdOrderSHHoliday sh = new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER.getCode(),null);
					OrdOrderSHHoliday sh1 = ordOrderSHHolidayService.selectByObjectIdAndObjectType(sh);
					if(sh1!=null){
						CancelOrderRequest rquest = new CancelOrderRequest(sh1.getContent());
						CancelOrderResponse response = shholidayClient.execute(rquest);
						if(response.isSuccess()){
							message= "通知上航取消订单成功";
						}else{
							message = "上航假期订单取消订单失败 ";
							if(response.getHeader()!=null && response.getHeader().getException()!=null){
								message+=response.getHeader().getException();
							}else if(response.getHeader()!=null && response.getHeader()!=null){
								message+=response.getHeader().getException();
							}
						}
					}
					addComLog(message,orderId);
				}
			}
		}catch(Exception e ){
			message = "上航假期订单取消订单异常 ";
		}
		return message;
	}

	@Override
	public String payOrder(Long orderId) {
		String message="";
		try{
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if(isSHHolidayOrder(order)){
				OrderPayInfo orderPayInfo = initOrderPayInfo(order);
				PayOrderRequest rquest = new PayOrderRequest(orderPayInfo);
				PayOrderResponse response = shholidayClient.execute(rquest);
				if(response.isSuccess()){
					message= "支付请求成功";
				}else{
					message = "上航假期订单支付请求失败";
					if(response.getHeader()!=null && response.getHeader().getException()!=null){
						message+=response.getHeader().getException();
					}else if(response.getHeader()!=null && response.getHeader()!=null){
						message+=response.getHeader().getException();
					}
					comMessageService.addSystemComMessage(Constant.EVENT_TYPE.SHHOLIDAY_ORDER_NOTIFY.name(), message, Constant.SYSTEM_USER);
				}
				addComLog(message,orderId);
			}
		}catch(Exception e ){
			message = "上航假期订单支付请求异常";
		}
		return message;
	}	

	private String modifyOrder(OrdOrder order){
		String message="";
		try{
			List<OrderPassenger> orderPassengers = initOrderPassengers(order);
			OrderModifyInfo modifyInfo = initmodifyInfo(order);
			initPassengerRefundAmount(order,orderPassengers);
			ModifyOrderRequest rquest = new ModifyOrderRequest(orderPassengers,modifyInfo);
			ModifyOrderResponse response = shholidayClient.execute(rquest);
			if(response.isSuccess()){
				saveBookModifyNo(order,response);
				message= "废单请求成功";
			}else{
				message = "上航假期订单废单请求失败";
				if(response.getHeader()!=null && response.getHeader().getException()!=null){
					message+=response.getHeader().getException();
				}else if(response.getHeader()!=null && response.getHeader()!=null){
					message+=response.getHeader().getException();
				}
			}
		}catch(Exception e ){
			message = "上航假期订单废单请求异常";
		}
		addComLog(message,order.getOrderId());
		return message;
	}
	
	
	private void saveBookModifyNo(OrdOrder order,ModifyOrderResponse response) {
		if(response.getBookModifyNo()!=null){
			OrdOrderSHHoliday sh = new OrdOrderSHHoliday(order.getOrderId(),Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_BOOK_MODIFY_NO.getCode(),response.getBookModifyNo());
			ordOrderSHHolidayService.insert(sh);
		}
	}
	
	private List<Long> getOrderItemMetaIds(OrdOrder order) {
		List<Long> orderItemMetaIdList = new ArrayList<Long>();
		for(OrdOrderItemMeta item:order.getAllOrdOrderItemMetas()){
			orderItemMetaIdList.add(item.getOrderItemMetaId());
		}
		return orderItemMetaIdList;
	}
	
	private String booking( OrdOrder order,String errormessage) {
		String message;
		Long orderId = order.getOrderId();
		OrderDetailInfo detail = findOrder(orderId);
		if(detail==null){
			message = "上航下单失败";
			if(!StringUtil.isEmpty(errormessage)){
				message += errormessage;
			}
			//需要人工进行审核
			ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_APPROVE.getCode(),"TRUE"));
			sendWorkOrder(order,Constant.WORK_ORDER_TYPE_AND_SENDGROUP.GYSQDXDSBGD.getWorkOrderTypeCode());
		}else if(detail.isBooking(orderId)){
			saveExternalOrderInfoByDetail(order,detail);
			message= "上航下单成功";
		}else{
			ordOrderSHHolidayService.insertOrUpdate(new OrdOrderSHHoliday(orderId,Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER_SHHOLIDAY_CANCEL.getCode(),"CANCEL"));
			orderServiceProxy.cancelOrder(orderId, "上航订单取消", "SYSTEM");
			message= "上航下单失败";
		}
		return message;
	}
	private boolean canBookingToSupplier(OrdOrder order) {
		OrderDetailInfo detail = findOrder(order.getOrderId());
		if(detail==null || StringUtil.isEmpty(detail.getExternalOrderNo())){
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
	private OrderModifyInfo initmodifyInfo(OrdOrder order) {
		OrderModifyInfo modifyInfo = new OrderModifyInfo();
		OrdOrderSHHoliday sh = new OrdOrderSHHoliday(order.getOrderId(),Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER.getCode(),null);
		OrdOrderSHHoliday sh1 = ordOrderSHHolidayService.selectByObjectIdAndObjectType(sh);
		Long factRefundAmount =0l;
		for(OrdOrderItemMeta itemMeta : order.getAllOrdOrderItemMetas()){
			factRefundAmount = factRefundAmount + itemMeta.getTotalSettlementPrice();
		}
		modifyInfo = modifyInfo.getModifyInfo(sh1.getContent(),factRefundAmount);
		return modifyInfo;
	}
	
	private void initPassengerRefundAmount(OrdOrder order,List<OrderPassenger> orderPassengers) {
		Long factRefundAmount =0l;
		for(OrdOrderItemMeta itemMeta : order.getAllOrdOrderItemMetas()){
			factRefundAmount = factRefundAmount + itemMeta.getTotalSettlementPrice();
		}
		for(OrderPassenger pa:orderPassengers){
			pa.setRefundAmountType("R");
			pa.setFactRefundAmount(factRefundAmount);
		}
	}
	
	private boolean isSHHolidayOrder(OrdOrder order) {
		return OrderUitl.hasShholidayOrder(order);
	}
	
	private void saveExternalOrderInfoByDetail(OrdOrder order,OrderDetailInfo detail) {
		OrdOrderSHHoliday sh = new OrdOrderSHHoliday(order.getOrderId(),Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER.getCode(),detail.getExternalOrderNo());
		ordOrderSHHolidayService.insert(sh);
		if(detail.getContact()!=null && detail.getContact().getUniqueId()!=null){
			OrdOrderSHHoliday sh1 = new OrdOrderSHHoliday(order.getContact().getPersonId(),Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_CONTACT.getCode(),detail.getContact().getUniqueId());
			ordOrderSHHolidayService.insert(sh1);
		}
		if(detail.getPassengers()!=null){
			for(OrderPassenger pass:detail.getPassengers()){
				for(OrdPerson per: order.getTravellerList()){
					if(pass.isMe(per)){
						OrdOrderSHHoliday sh2 = new OrdOrderSHHoliday(per.getPersonId(),Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_PASSENGER.getCode(),pass.getUniqueId());
						ordOrderSHHolidayService.insert(sh2);
					}
				}
			}
		}
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
	private void saveExternalOrderInfo(OrdOrder order,CreateOrderResponse response) {
		if(response.getSupplierOrderId()!=null){
			OrdOrderSHHoliday sh = new OrdOrderSHHoliday(order.getOrderId(),Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER.getCode(),response.getSupplierOrderId());
			ordOrderSHHolidayService.insertOrUpdate(sh);
		}
		if(response.getContact()!=null && response.getContact().getUniqueId()!=null){
			OrdOrderSHHoliday sh = new OrdOrderSHHoliday(order.getContact().getPersonId(),Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_CONTACT.getCode(),response.getContact().getUniqueId());
			ordOrderSHHolidayService.insertOrUpdate(sh);
		}
		if(response.getPassengers()!=null){
			for(OrderPassenger pass:response.getPassengers()){
				for(OrdPerson per: order.getTravellerList()){
					if(pass.isMe(per)){
						OrdOrderSHHoliday sh = new OrdOrderSHHoliday(per.getPersonId(),Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_PASSENGER.getCode(),pass.getUniqueId());
						ordOrderSHHolidayService.insertOrUpdate(sh);
					}
				}
			}
		}
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
	private boolean needCancelOrder(String errorcode) {
		if("00000".equalsIgnoreCase(errorcode)){
			return false;
		}if("60013".equalsIgnoreCase(errorcode)){
			return false;
		}
		if("60015".equalsIgnoreCase(errorcode)){
			return false;
		}
		if("60010".equalsIgnoreCase(errorcode)){
			return false;
		}
		if("60012".equalsIgnoreCase(errorcode)){
			return false;
		}
		if("60014".equalsIgnoreCase(errorcode)){
			return false;
		}
		return true;
	}
	private MetaTravelCode queryTeamUniqueId(OrdOrder order) {
		if(!CollectionUtils.isEmpty(order.getAllOrdOrderItemMetas())){
			OrdOrderItemMeta itemMeta = order.getAllOrdOrderItemMetas().get(0);
			for(OrdOrderItemMeta i:order.getAllOrdOrderItemMetas()){
				if("ROUTE".equals(i.getProductType()) && StringUtils.isNotEmpty(i.getProductIdSupplier())){
					itemMeta = i;
				}
			}
			
			if(itemMeta.getProductIdSupplier()!=null){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("supplierProductId", itemMeta.getProductIdSupplier());
				params.put("specDate", DateUtil.accurateToDay(itemMeta.getVisitTime()));
				params.put("supplierChannel", Constant.SUPPLIER_CHANNEL.SH_HOLIDAY.name());
				List<MetaTravelCode> list = metaTravelCodeService.selectByCondition(params );
				if(list!=null && list.size()>0){
					MetaTravelCode metaTravelCode = list.get(0);
					if(metaTravelCode!=null){
						return metaTravelCode;
					}
				}
			}
		}
		return null;
	}
	private List<BusinessCoupon> querybusinessCoupons(OrdOrder order) {
		List<MarkCouponUsage> markUsages=  favorOrderService.getMarkCouponUsageByObjectIdAndType(order.getOrderId(),"ORD_ORDER_ITEM_META");
		if(CollectionUtils.isEmpty(markUsages)){
			return null;
		}
		List<BusinessCoupon> businessCoupons = new ArrayList<BusinessCoupon>();
		for(MarkCouponUsage mark:markUsages){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("favorType", mark.getStrategy());
			param.put("productId", mark.getSubObjectIdA());
			param.put("branchId", mark.getSubObjectIdB());
			List<BusinessCoupon> bs= businessCouponService.selectByIDs(param);
			if(bs!=null){
				businessCoupons.addAll(bs);
			}
		}
		return businessCoupons;
	}
	private Long calculateFavorAmount(OrdOrder order) {
		List<MarkCouponUsage> markUsages=  favorOrderService.getMarkCouponUsageByObjectIdAndType(order.getOrderId(),"ORD_ORDER_ITEM_PROD");
		if(CollectionUtils.isEmpty(markUsages)){
			return 0l;
		}
		Long favorAmount=0l;
		for(MarkCouponUsage mark:markUsages){
			favorAmount = favorAmount + mark.getAmount();
		}
		return favorAmount;
	}

	

	private String getUniqueIdByOrderId(Long orderId) {
		OrdOrderSHHoliday sh = new OrdOrderSHHoliday();
		sh.setObjectId(orderId);
		sh.setObjectType(Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER.getCode());
		OrdOrderSHHoliday sh1 = ordOrderSHHolidayService.selectByObjectIdAndObjectType(sh);
		if(sh1!=null && sh1.getOrderSHHolidayId()>0){
			return sh1.getContent();
		}
		return "";
	}

	private Contact initContact(OrdOrder order) {
		Contact contact = new Contact();
		contact.getContact(order.getContact());
		
		OrdOrderSHHoliday sh = new OrdOrderSHHoliday();
		sh.setObjectId(order.getContact().getPersonId());
		sh.setObjectType(Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_CONTACT.getCode());
		OrdOrderSHHoliday sh1 = ordOrderSHHolidayService.selectByObjectIdAndObjectType(sh);
		if(sh1!=null && sh1.getOrderSHHolidayId()>0){
			contact.setUniqueId(sh1.getContent());
		}
		return contact;
	}

	private List<OrderPassenger> initOrderPassengers(OrdOrder order) {
		List<OrderPassenger> orderPassengers = new ArrayList<OrderPassenger>();
		for(OrdPerson per: order.getTravellerList()){
			OrderPassenger orderPrassenger = new OrderPassenger();
			OrdOrderSHHoliday sh = new OrdOrderSHHoliday(per.getPersonId(),Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_PASSENGER.getCode(),null);
			OrdOrderSHHoliday sh1 = ordOrderSHHolidayService.selectByObjectIdAndObjectType(sh);
			if(sh1!=null && sh1.getOrderSHHolidayId()>0){
				orderPrassenger.setUniqueId(sh1.getContent());
			}
			
			orderPrassenger.setAddress(per.getAddress());
			orderPrassenger.setBrithday(per.getBrithday());
			orderPrassenger.setCertNo(per.getCertNo());
			orderPrassenger.setCertType(per.getCertType());
			orderPrassenger.setEmail(per.getEmail());
			orderPrassenger.setMobile(per.getMobile());
			orderPrassenger.setName(per.getName());
			orderPrassenger.setGender(per.getGender());
			orderPrassenger.setFax(per.getFax());
			orderPassengers.add(orderPrassenger);
		}
		return orderPassengers;
	}

	private OrderPayInfo initOrderPayInfo(OrdOrder order) {
		OrderPayInfo orderPayInfo = new OrderPayInfo();
		Long payAmount =0l;
		for(OrdOrderItemMeta itemMeta:order.getAllOrdOrderItemMetas()){
			if(itemMeta.getProductTypeSupplier()!=null){
				if(Constant.SH_HOLIDAY_BRANCH_TYPE.ADULT.getCode().equals(itemMeta.getProductTypeSupplier())){
					payAmount = payAmount + itemMeta.getSettlementPriceLong();
				}
				if(Constant.SH_HOLIDAY_BRANCH_TYPE.CHILDREN.getCode().equals(itemMeta.getProductTypeSupplier())){
					payAmount = payAmount + itemMeta.getSettlementPriceLong();
				}
				if(Constant.SH_HOLIDAY_BRANCH_TYPE.INFANT.getCode().equals(itemMeta.getProductTypeSupplier())){
					payAmount = payAmount + itemMeta.getSettlementPriceLong();
				}
				if(Constant.SH_HOLIDAY_BRANCH_TYPE.ACCOMPANY.getCode().equals(itemMeta.getProductTypeSupplier())){
					payAmount = payAmount + itemMeta.getSettlementPriceLong();
				}if(Constant.SH_HOLIDAY_BRANCH_TYPE.ROOMDIFFER.getCode().equals(itemMeta.getProductTypeSupplier())){
					payAmount = payAmount + itemMeta.getSettlementPriceLong();
				}
			}
		}
		Long favorAmount = calculateFavorAmount(order);
		orderPayInfo.setPayAmount(payAmount-favorAmount);
		OrdOrderSHHoliday sh = new OrdOrderSHHoliday(order.getOrderId(),Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_ORDER.getCode(),null);
		OrdOrderSHHoliday sh1 = ordOrderSHHolidayService.selectByObjectIdAndObjectType(sh);
		orderPayInfo.setExternalOrderNo(sh1.getContent());
		return orderPayInfo;
	}
	
	
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setFavorOrderService(FavorOrderService favorOrderService) {
		this.favorOrderService = favorOrderService;
	}

	public void setBusinessCouponService(BusinessCouponService businessCouponService) {
		this.businessCouponService = businessCouponService;
	}

	public void setMetaTravelCodeService(MetaTravelCodeService metaTravelCodeService) {
		this.metaTravelCodeService = metaTravelCodeService;
	}

	public void setShholidayClient(ShholidayClient shholidayClient) {
		this.shholidayClient = shholidayClient;
	}
	
	public void setOrdOrderSHHolidayService(
			OrdOrderSHHolidayService ordOrderSHHolidayService) {
		this.ordOrderSHHolidayService = ordOrderSHHolidayService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	
	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}

	public void setWorkOrderProxy(WorkOrderSenderBiz workOrderProxy) {
		this.workOrderProxy = workOrderProxy;
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
