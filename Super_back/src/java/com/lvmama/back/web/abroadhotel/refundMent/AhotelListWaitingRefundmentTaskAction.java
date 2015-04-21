package com.lvmama.back.web.abroadhotel.refundMent;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.A;
import org.zkoss.zul.Label;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.abroad.po.AhotelOrdRefundment;
import com.lvmama.comm.abroad.service.AbroadhotelOrderService;
import com.lvmama.comm.abroad.vo.request.ReservationsOrderReq;
import com.lvmama.comm.abroad.vo.response.ReservationsOrder;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sale.OrderRefundService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
/**
 * 查询待退款任务
 */
public class AhotelListWaitingRefundmentTaskAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 海外酒店订单的service.
	 */
	private AbroadhotelOrderService abroadhotelOrderService;

	/**
	 * 退款查询集合.
	 */
	private List<AhotelOrdRefundment> ordRefundmentList;
	
	private Map<String, Object> searchRefundMentMap = new HashMap<String, Object>();
	
	private OrderRefundService orderRefundService; 
	
	private TopicMessageProducer orderMessageProducer;
	
	private UserUserProxy userUserProxy;
	
	private PayPaymentRefundmentService payPaymentRefundmentService;
	
	private TopicMessageProducer resourceMessageProducer;
	private ComLogService comLogService;
	public void ordRefundMentQuery(){
		searchRefundMentMap.put("status", Constant.REFUNDMENT_STATUS.VERIFIED.toString());
		Map map=initialPageInfoByMap(abroadhotelOrderService.findAhotelOrdRefundByParamCount(searchRefundMentMap),searchRefundMentMap);
		int skipResults=0;
		int maxResults=10;
		if(map.get("skipResults")!=null){
			skipResults=Integer.parseInt(map.get("skipResults").toString());
		}
		if(map.get("maxResults")!=null){
			maxResults=Integer.parseInt(map.get("maxResults").toString());
		}
		searchRefundMentMap.put("skipResults",skipResults);
		searchRefundMentMap.put("maxResults",maxResults);
		ordRefundmentList=abroadhotelOrderService.findAhotelOrdRefundByParam(searchRefundMentMap,skipResults,maxResults);
		((Label)this.getComponent().getFellow("_totalRowCountLabel")).setValue(String.valueOf(ordRefundmentList.size()));
	}

	/**
	 * 退款
	 * @param a
	 */
	public void refund(final A a){
		ZkMessage.showQuestion("您确定需要退款吗?", 
				new ZkMsgCallBack()	{
					public void execute() {
						Long   refundmentId= (Long)a.getAttribute("refundmentId");
						Long orderId= (Long) a.getAttribute("orderId");
						
						ReservationsOrderReq reservationsOrderReq = new ReservationsOrderReq();
						reservationsOrderReq.setOrderNo(orderId.toString());
						ReservationsOrder abroadOrder=abroadhotelOrderService.queryAbroadHotelOrderByOrderId(orderId.toString());
						UserUser uu=userUserProxy.getUserUserByUserNo(abroadOrder.getUserId());

						AhotelOrdRefundment orf=abroadhotelOrderService.findAhotelOrdRefundByRefundmentId(refundmentId);
						boolean succ = false;
						if (orf == null || !Constant.REFUNDMENT_STATUS.VERIFIED.name().equals(orf.getStatus())||orf.isRefunded()) {
							alert("退款失败");
							return;
						}
						//退款单金额为0时，系统自动拒绝退款
						if(orf.getAmount().longValue() == 0){
						    succ=orderRefundService.rejectCashRefundment(refundmentId, "退款单金额为零,系统拒绝退款", "SYSTEM");
						}
						if(orf.getAmount().longValue() > 0){
							RefundmentToBankInfo refundInfo = new RefundmentToBankInfo();
							refundInfo.setRefundAmount(orf.getAmount());
							refundInfo.setBizType(Constant.PAYMENT_BIZ_TYPE.TRANSHOTEL_ORDER.name());
							refundInfo.setObjectId(orf.getRefundmentId());
							refundInfo.setRefundType(orf.getRefundType());
							refundInfo.setObjectType(Constant.PAYMENT_OBJECT_TYPE.ORD_REFUNDMENT.name());
							refundInfo.setUserId(uu.getId());
							refundInfo.setOperator(getOperatorName());
							succ = payPaymentRefundmentService.createRefundment(orf.getOrderId(), refundInfo);
							if(succ){
								//更新 ord_refundment 状态为PROCESSING
								orf.setRefundTime(Calendar.getInstance().getTime());
								orf.setStatus(Constant.REFUNDMENT_STATUS.PROCESSING.name());
								orf.setOperatorName(getOperatorName());
								abroadhotelOrderService.updateAhotelOrdRefundment(orf);
								
								Message msg = MessageFactory.newPaymentRefundmentMessage(orf.getRefundmentId());
								msg.setAddition(Constant.PAYMENT_BIZ_TYPE.TRANSHOTEL_ORDER.name());
								resourceMessageProducer.sendMsg(msg);
							}
							comLogService.insert("ABROADHOTEL_ORD_REFUNDMENT", orf.getOrderId(), refundmentId, getOperatorName(), 
							Constant.COM_LOG_CASH_EVENT.insertOrderRefundment.name(), "创建银行退款单", "创建海外酒店银行退款单", Constant.PAYMENT_BIZ_TYPE.TRANSHOTEL_ORDER.name());
						}else{
							succ = abroadhotelOrderService.rejectAbroadHotelCashRefundment(refundmentId, "退款单金额为零,系统拒绝退款", "SYSTEM");
						}
						if (succ){
							alert("退款成功");
							refreshComponent("search");
						}else{
							alert("退款失败");
						}
					}
				}, new ZkMsgCallBack() {
					public void execute() {}
				}
		);
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	
	public List<AhotelOrdRefundment> getOrdRefundmentList() {
		return ordRefundmentList;
	}
	
	public Map<String, Object> getSearchRefundMentMap() {
		return searchRefundMentMap;
	}

	public void setSearchRefundMentMap(Map<String, Object> searchRefundMentMap) {
		this.searchRefundMentMap = searchRefundMentMap;
	}

	public void setAbroadhotelOrderService(
			AbroadhotelOrderService abroadhotelOrderService) {
		this.abroadhotelOrderService = abroadhotelOrderService;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public OrderRefundService getOrderRefundService() {
		return orderRefundService;
	}

	public void setOrderRefundService(OrderRefundService orderRefundService) {
		this.orderRefundService = orderRefundService;
	}

	public AbroadhotelOrderService getAbroadhotelOrderService() {
		return abroadhotelOrderService;
	}

	public TopicMessageProducer getOrderMessageProducer() {
		return orderMessageProducer;
	}

	public void setOrdRefundmentList(List<AhotelOrdRefundment> ordRefundmentList) {
		this.ordRefundmentList = ordRefundmentList;
	}

	public void setPayPaymentRefundmentService(
			PayPaymentRefundmentService payPaymentRefundmentService) {
		this.payPaymentRefundmentService = payPaymentRefundmentService;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	
	
	
	
}
