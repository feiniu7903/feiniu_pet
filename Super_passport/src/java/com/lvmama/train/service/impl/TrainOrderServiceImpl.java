package com.lvmama.train.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficRefund;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficTicketInfo;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.prod.LineStationStation;
import com.lvmama.comm.bee.po.pub.ComJobContent;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.ord.OrderTrafficService;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.IdentityCardUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.order.OrderCancelReqVo;
import com.lvmama.comm.vo.train.order.OrderCreateReqVo;
import com.lvmama.comm.vo.train.order.OrderCreateRspVo;
import com.lvmama.comm.vo.train.order.OrderPaidReqVo;
import com.lvmama.comm.vo.train.order.OrderPaidRspVo;
import com.lvmama.comm.vo.train.order.OrderPassengerInfo;
import com.lvmama.comm.vo.train.order.OrderQueryReqVo;
import com.lvmama.comm.vo.train.order.OrderQueryRspVo;
import com.lvmama.comm.vo.train.order.OrderRefundSuccessReqVo;
import com.lvmama.comm.vo.train.order.TicketDrawbackInfo;
import com.lvmama.comm.vo.train.order.TicketDrawbackReqVo;
import com.lvmama.comm.vo.train.product.TicketBookInfo;
import com.lvmama.comm.vo.train.product.TicketBookReqVo;
import com.lvmama.comm.vo.train.product.TicketBookRspVo;
import com.lvmama.train.service.TrainClient;
import com.lvmama.train.service.TrainOrderService;
import com.lvmama.train.service.request.CancelOrderRequest;
import com.lvmama.train.service.request.CreateOrderRequest;
import com.lvmama.train.service.request.OrderPaidRequest;
import com.lvmama.train.service.request.OrderQueryRequest;
import com.lvmama.train.service.request.OrderRefundSuccessRequest;
import com.lvmama.train.service.request.TicketBookInfoQueryRequest;
import com.lvmama.train.service.request.TicketDrawbackRequest;
import com.lvmama.train.service.response.CancelOrderResponse;
import com.lvmama.train.service.response.CreateOrderResponse;
import com.lvmama.train.service.response.OrderPaidResponse;
import com.lvmama.train.service.response.OrderQueryResponse;
import com.lvmama.train.service.response.OrderRefundSuccessResponse;
import com.lvmama.train.service.response.TicketBookInfoQueryResponse;
import com.lvmama.train.service.response.TicketDrawbackResponse;

public class TrainOrderServiceImpl implements TrainOrderService {
	private static final org.apache.commons.logging.Log logger=LogFactory.getLog(TrainOrderServiceImpl.class);
	private TrainClient client = new TrainClient();	
	private OrderService orderServiceProxy;
	private ProdTrainService prodTrainService;
	private MetaProductBranchService metaProductBranchService;
	private OrderTrafficService orderTrafficService;
	private ComLogService comLogService;
	
	private static final String TRAFFIC = "traffic";
	private static final String ADULT = "adult";
	private static final String CHILD = "child";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void createTrainOrder(Long orderId) {
		// TODO Auto-generated method stub
		try {
			logger.info("Train Order pay successed, and now to create supplier order. OrderId: " + orderId);
			if(orderId == null || orderId == 0L){
				logger.info("火车票订单创建请求失败, 订单Id错误");
				return;
			}
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if(order == null){
				logger.info("火车票订单创建请求失败,根据订单Id未能获取订单信息.  OrderId: " + orderId);
				return;
			}
			//第一层key为票类型+游玩时间，同一种票类型在供应商处下一个单
			//第二层Map存放OrdOrderTraffic、OrdOrderItemMeta
			//目前只接收成人票、儿童票两种
			Map<String, Map<String, Object>> map = getTrafficFromOrder(order);
			if(!map.isEmpty()){
				Iterator it = map.entrySet().iterator();
				while(it.hasNext()){
					Map.Entry<String, Map<String, Object>> entry = (Map.Entry<String, Map<String, Object>>)it.next();
					String key = entry.getKey();
					Map<String, Object> _map = entry.getValue();
					//OrdOrderTraffic类
					OrdOrderTraffic traffic = (OrdOrderTraffic)_map.get(TRAFFIC);
					//成人票订单子子项
					OrdOrderItemMeta adult = (OrdOrderItemMeta)_map.get(ADULT);
					//儿童票订单子子项
					OrdOrderItemMeta child = (OrdOrderItemMeta)_map.get(CHILD);
					//保存OrdOrderTraffic
					traffic = orderTrafficService.makeTrafficOrder(traffic);
					String visitTime = key.split("\\|")[1];
					//发送下单请求到供应商
					boolean isSendSuccess = sendTrafficOrderToSupplier(traffic, order, adult, child, visitTime);
					//发送订单支付成功请求到供应商
					if(isSendSuccess)
						sendTrafficOrderPaySuccessToSupplier(traffic, order);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, Map<String, Object>> getTrafficFromOrder(OrdOrder order) throws Exception{
		Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();
		for(OrdOrderItemMeta meta:order.getAllOrdOrderItemMetas()){
			if (meta.isTrafficProductType() && 
					StringUtils.equals(meta.getSubProductType(), Constant.SUB_PRODUCT_TYPE.TRAIN.name())) {
				String visitTime = DateUtil.formatDate(meta.getVisitTime(), "yyyy-MM-dd");
				MetaProductBranch mpb = metaProductBranchService.getMetaBranch(meta.getMetaBranchId());
				LineStationStation stationStation = prodTrainService.getStationStationDetailById(mpb.getStationStationId());
				if(result.containsKey(mpb.getBerth() + "|" + visitTime)){
					Map<String, Object> _map = result.get(mpb.getBerth() + "|" + visitTime);
					OrdOrderTraffic traffic = (OrdOrderTraffic)_map.get(TRAFFIC);
					if(StringUtils.equals(mpb.getBranchType(), String.valueOf(Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_302.getCode()))){
						traffic.setOrderItemMetaChildId(meta.getOrderItemMetaId());
						_map.put(CHILD, meta);
					}else if(StringUtils.equals(mpb.getBranchType(), String.valueOf(Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_301.getCode()))){
						traffic.setOrderItemMetaId(meta.getOrderItemMetaId());
						_map.put(ADULT, meta);
					}
				}else {
					Map<String, Object> _map = new HashMap<String, Object>();
					OrdOrderTraffic traffic = new OrdOrderTraffic();
					traffic.setDepartureStationName(stationStation.getDepartureStation().getStationName());
					traffic.setArrivalStationName(stationStation.getArrivalStation().getStationName());
					traffic.setSeat(mpb.getBerth());
					if(StringUtils.equals(mpb.getBranchType(), String.valueOf(Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_302.getCode()))){
						traffic.setOrderItemMetaChildId(meta.getOrderItemMetaId());
						_map.put(CHILD, meta);
					}else if(StringUtils.equals(mpb.getBranchType(), String.valueOf(Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_301.getCode()))){
						traffic.setOrderItemMetaId(meta.getOrderItemMetaId());
						_map.put(ADULT, meta);
					}
					traffic.setTrainName(stationStation.getLineName());
					_map.put(TRAFFIC, traffic);
					result.put(mpb.getBerth() + "|" + visitTime, _map);
				}
			}
		}
		return result;
	}
	
	public boolean sendTrafficOrderToSupplier(OrdOrderTraffic traffic, OrdOrder order, OrdOrderItemMeta adult, 
			OrdOrderItemMeta child, String visitTime) throws Exception{
		boolean isSendSuccess = false;
		// TODO Auto-generated method stub
		OrderCreateReqVo vo = new OrderCreateReqVo();
		vo.setDepartStation(traffic.getDepartureStationName());
		vo.setArriveStation(traffic.getArrivalStationName());
		vo.setDepartDate(visitTime);
		vo.setTrainId(traffic.getTrainName());
		Long orderItemMetaId = traffic.getOrderItemMetaId();
		if(orderItemMetaId == null) {
			orderItemMetaId = traffic.getOrderItemMetaChildId();
		}
		if(orderItemMetaId != null) {
			vo.setOrderItemMetaId(orderItemMetaId.toString());
		}
		//向请求VO中添加乘客信息
		vo = getTravellerInfo(vo, traffic.getSeat(), adult, child, order);
		/***add by shihui,当出现连接错误时，记录在表中，job处理****/
		vo.setObjectId(traffic.getOrderTrafficId());
		vo.setRequestType(ComJobContent.JOB_TYPE.TRAIN_CREATE_ORDER.name());
		/********************************************************/
		CreateOrderRequest request = new CreateOrderRequest(vo);
		//发送下单请求
		CreateOrderResponse response = client.execute(request);
		OrderCreateRspVo rspVo=null;
		if(response.isSuccess()){
			rspVo = (OrderCreateRspVo)response.getRsp().getVo();
			if(response.getRsp().getStatus().getRet_code() == Constant.HTTP_SUCCESS && 
					rspVo.getOrder_status() == Constant.TRAIN_ORDER_STATUS.ORDER_STATUS_801.getCode()){
				traffic.setSupplierOrderId(rspVo.getOrder_id());
				orderTrafficService.updateSupplierOrderId(traffic.getOrderTrafficId(), rspVo.getOrder_id());
				comLogService.insert("ORD_ORDER", order.getOrderId(), null, "System", "TRAIN_BOOK", "火车票供应商下单", "火车票生成订单，供应商订单号："+rspVo.getOrder_id(), "ORD_ORDER");
				isSendSuccess = true;
			}
		}
		if(!isSendSuccess && response.isCancelOrder()){
			ResultHandleT<OrdOrderTraffic> result= orderTrafficService.updateFailStatus(traffic.getOrderTrafficId(),rspVo!=null?rspVo.getOrder_msg():"生成订单失败");
			if(result.isSuccess()){
				if(result.getReturnContent().hasFailStatus()){
					//orderMessageProducer.sendMsg(MessageFactory.newTrafficRefumentMessage(traffic.getOrderItemMetaId()));
					boolean flag = orderServiceProxy.cancelOrder(order.getOrderId(), "火车票订单失败", "SYSTEM");
					if(flag){
						orderServiceProxy.autoCreateOrderFullRefund(order,  "SYSTEM", "火车票订单失败");
					}
				}
			}else{
				logger.warn("train failt update,order_traffic_id:"+traffic.getOrderTrafficId());
			}
		}
		return isSendSuccess;
	}
	
	private OrderCreateReqVo getTravellerInfo(OrderCreateReqVo vo,String ticketClass, OrdOrderItemMeta adult, 
			OrdOrderItemMeta child, OrdOrder order) throws Exception{
		// TODO Auto-generated method stub
		for(OrdPerson op : order.getTravellerList()){
			OrderPassengerInfo opi = new OrderPassengerInfo();
			opi.setPassenger_name(op.getName());
			opi.setTicket_class(Integer.parseInt(ticketClass));
			opi.setId_num(op.getCertNo());
			
			if(op.getCertType().equals(Constant.CERT_TYPE.ID_CARD.getCode())){
				opi.setId_type(Constant.TRAIN_ID_TYPE.ID_TYPE_401.getCode());
				if(Integer.parseInt(getYear(op.getCertNo())) <= 12){
					opi.setTicket_type(Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_302.getCode());
					if(child != null)
						opi.setTicket_price(child.getActualSettlementPriceYuan());
				}else{
					opi.setTicket_type(Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_301.getCode());
					if(adult != null)
						opi.setTicket_price(adult.getActualSettlementPriceYuan());
				}
				opi.setPhone_num(op.getMobile());
				vo.getPassengers().add(opi);
				continue;
			}else if(op.getCertType().equals(Constant.CERT_TYPE.HUZHAO.getCode()))
				opi.setId_type(Constant.TRAIN_ID_TYPE.ID_TYPE_402.getCode());
			else if(op.getCertType().equals(Constant.CERT_TYPE.GANGAO.getCode()))
				opi.setId_type(Constant.TRAIN_ID_TYPE.ID_TYPE_403.getCode());
			else if(op.getCertType().equals(Constant.CERT_TYPE.TAIBAO.getCode()))
				opi.setId_type(Constant.TRAIN_ID_TYPE.ID_TYPE_404.getCode());
			
			Date birthTime = op.getBrithday();
			//小于12岁，则放置儿童票
			if(DateUtil.getAge(birthTime) <= 12){
				opi.setTicket_type(Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_302.getCode());
				if(child != null)
					opi.setTicket_price(child.getTotalSettlementPriceYuan());
			}else{
				opi.setTicket_type(Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_301.getCode());
				if(adult != null)
					opi.setTicket_price(adult.getTotalSettlementPriceYuan());
			}
			opi.setPhone_num(op.getMobile());
			vo.getPassengers().add(opi);
		}
		return vo;
	}
	
	private String getYear(String certNo){
		  if(null==certNo)
			  return Constant.E_CONTRACT_DEFAULT_FILL;
		  java.util.Date birthDay=null;
		  birthDay= IdentityCardUtil.getDate(certNo);
		  if(null!=birthDay){
			  Integer age=DateUtil.getAge(birthDay);
			  return age.toString();
		  }else{
			  return Constant.E_CONTRACT_DEFAULT_FILL;
		  }
	}
	
	public void sendTrafficOrderPaySuccessToSupplier(OrdOrderTraffic traffic, OrdOrder order) throws Exception{
		if(traffic == null) throw new Exception("Traffic订单为空");
		// TODO Auto-generated method stub
		OrderPaidReqVo vo = new OrderPaidReqVo();
		vo.setOrderId(traffic.getSupplierOrderId());
		vo.setPaidResult(0);
		
		OrderPaidRequest request = new OrderPaidRequest(vo);
		OrderPaidResponse response = client.execute(request);
		if(response.isSuccess()){
			OrderPaidRspVo rspVo = (OrderPaidRspVo)response.getRsp().getVo();
			if(response.getRsp().getStatus().getRet_code() != Constant.HTTP_SUCCESS || 
					rspVo.getOrder_status() != Constant.TRAIN_ORDER_STATUS.ORDER_STATUS_802.getCode()){
				ResultHandleT<OrdOrderTraffic> result= orderTrafficService.updateFailStatus(traffic.getOrderTrafficId(),rspVo.getOrder_msg());
				if(result.isSuccess()){
					if(result.getReturnContent().hasFailStatus()){
						//orderMessageProducer.sendMsg(MessageFactory.newTrafficRefumentMessage(traffic.getOrderItemMetaId()));
						boolean flag = orderServiceProxy.cancelOrder(order.getOrderId(), "火车票订单失败", "SYSTEM");
						if(flag){
							orderServiceProxy.autoCreateOrderFullRefund(order,  "SYSTEM", "火车票订单失败");
						}
					}
				}else{
					logger.warn("train failt update,order_traffic_id:"+traffic.getOrderTrafficId());
				}
			}
		}
	}
	
	@Override
	public void cancelTrainOrder(Long orderId) {
		if(orderId == null || orderId == 0L){
			logger.info("火车票订单取消请求失败, 订单Id错误");
			return;
		}
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if(order == null){
			logger.info("火车票订单取消请求失败,根据订单Id未能获取订单信息.  OrderId: " + orderId);
		}
		
		if(Constant.ORDER_TYPE.TRAIN.name().equalsIgnoreCase(order.getOrderType())){
			if(!order.isPaymentSucc()){
				orderServiceProxy.cancelOrder(order.getOrderId(), "火车票订单失败", "SYSTEM");
			}else{
				for(OrdOrderItemMeta meta:order.getAllOrdOrderItemMetas()){
					if(Constant.SUB_PRODUCT_TYPE.TRAIN.name().equalsIgnoreCase(meta.getSubProductType())){
						OrdOrderTraffic traffic = orderTrafficService.getTrafficByOrderItemMetaId(meta.getOrderItemMetaId());
						
						OrderCancelReqVo vo = new OrderCancelReqVo();
						vo.setOrderId(traffic.getSupplierOrderId());
						
						 CancelOrderRequest request = new CancelOrderRequest(vo);
						 CancelOrderResponse response = client.execute(request);
						 if(response.isSuccess()){
							 orderServiceProxy.cancelOrder(order.getOrderId(), "火车票订单失败", "SYSTEM");
						 }
					}
				}
			}
		}
	}

	@Override
	public void drawbackTrainTicket(Long ticketId) {
		// TODO Auto-generated method stub
		if(ticketId == null || ticketId == 0L){
			logger.info("火车票退票请求失败, 退票Id错误");
			return;
		}
		OrdOrderTrafficTicketInfo ticket = orderTrafficService.getTicketInfoById(ticketId);
		if(ticket == null){
			logger.info("火车票退票请求失败, 无法根据退票Id获取退票信息. TicketId: " + ticketId);
			return;
		} 
		OrdOrderTraffic traffic = orderTrafficService.gettrafficById(ticket.getOrderTrafficId());
		if(traffic == null){
			logger.info("火车票退票请求失败, 无法取得火车票订票信息.  OrderTrafficId: " + ticket.getOrderTrafficId());
			return;
		}
		
		TicketDrawbackReqVo vo = new TicketDrawbackReqVo();
		vo.setOrderId(traffic.getSupplierOrderId());
		vo.setRefundType(Integer.parseInt(ticket.getTicketCategory()));
		vo.setTicketNum(1);
		TicketDrawbackInfo info = new TicketDrawbackInfo(ticketId.intValue(), ticket.getPrice());
		vo.getTicketDrawbackInfos().add(info);
		
		TicketDrawbackRequest request = new TicketDrawbackRequest(vo);
		TicketDrawbackResponse response = client.execute(request);
		if(!response.isSuccess()){
			logger.info("火车票退票请求失败, 供应商退票请求后返回失败");
		}
	}

	@Override
	public void refundTrainTicketSuccess(Long orderTrafficRefundId) {
		// TODO Auto-generated method stub
		if(orderTrafficRefundId == null || orderTrafficRefundId == 0L){
			logger.info("火车票退款成功请求失败, 退款流水号错误");
			return;
		}
		OrdOrderTrafficRefund refund = orderTrafficService.getTrafficRefundByRefundId(orderTrafficRefundId);
		if(refund == null){
			logger.info("火车票退款成功请求失败, 无法取得火车票退款信息.  OrderTrafficRefundId: " + orderTrafficRefundId);
			return;
		}
		
		OrdOrderTraffic traffic = orderTrafficService.gettrafficById(refund.getOrderTrafficId());
		
		OrderRefundSuccessReqVo vo = new OrderRefundSuccessReqVo();
		vo.setRefundId(refund.getBillNo());
		vo.setOrderId(traffic.getSupplierOrderId());
		if(refund.getAmount()!=null){
			vo.setTicketFee(PriceUtil.convertToYuan(refund.getAmount()));
		}
		if(refund != null && refund.getTicketCharge() != null)
			vo.setTicketCharge(PriceUtil.convertToYuan(refund.getTicketCharge()));
		vo.setRefundType(Constant.TRAIN_REFUND_TYPE.REFUND_TYPE_506.getCode());
		
		OrderRefundSuccessRequest request = new OrderRefundSuccessRequest(vo);
		OrderRefundSuccessResponse response = client.execute(request);
		if(!response.isSuccess()){
			logger.info("火车票退款成功请求失败, 供应商退款成功请求后返回失败");
		}
	}

	@Override
	public int queryTrainTicketStatus(Long orderId) throws Exception{
		// TODO Auto-generated method stub
		if(orderId == null || orderId == 0L){
			throw new Exception("火车票订单查询请求失败, 订单Id错误");
		}
		
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if(order == null){
			throw new Exception("火车票订单查询请求失败,根据订单Id未能获取订单信息.  OrderId: " + orderId);
		}
		
		OrdOrderItemMeta meta = order.getAllOrdOrderItemMetas().get(0);
		OrdOrderTraffic traffic = orderTrafficService.getTrafficByOrderItemMetaId(meta.getOrderItemMetaId());
		
		OrderQueryReqVo vo = new OrderQueryReqVo();
		vo.setOrderId(traffic.getSupplierOrderId());
		OrderQueryRequest request = new OrderQueryRequest(vo);
		OrderQueryResponse response = client.execute(request);
		if(response.isSuccess()){
			return ((OrderQueryRspVo)response.getRsp().getVo()).getOrder_status();
		}else{
			throw new Exception("火车票订单查询请求失败, 供应商订单查询请求后返回失败");
		}
	}
	
	@Override
	public List<TicketBookInfo> queryTicketBookInfo(String departStation,
			String arriveStation, String departDate, String trainId)
			throws Exception {
		TicketBookReqVo vo = new TicketBookReqVo();
		vo.setDepartStation(departStation);
		vo.setArriveStation(arriveStation);
		vo.setDepartDate(departDate);
		vo.setTrainId(trainId);
		
		TicketBookInfoQueryRequest request = new TicketBookInfoQueryRequest(vo);
		TicketBookInfoQueryResponse response = client.execute(request);
		if(response.isSuccess()){
			return ((TicketBookRspVo)response.getRsp().getVo()).getTicketBookInfos();
		}else
			throw new Exception("火车票订票信息查询请求失败, 供应商订票信息查询请求后返回失败");
	}


	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public ProdTrainService getProdTrainService() {
		return prodTrainService;
	}
	public void setProdTrainService(ProdTrainService prodTrainService) {
		this.prodTrainService = prodTrainService;
	}
	public MetaProductBranchService getMetaProductBranchService() {
		return metaProductBranchService;
	}
	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}
	public OrderTrafficService getOrderTrafficService() {
		return orderTrafficService;
	}
	public void setOrderTrafficService(OrderTrafficService orderTrafficService) {
		this.orderTrafficService = orderTrafficService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

}
