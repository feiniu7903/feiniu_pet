package com.lvmama.back.web.ord.refundMent;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.A;
import org.zkoss.zul.Label;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.comm.vst.service.VstDistributorService;
import com.lvmama.comm.vst.service.VstOrdOrderService;
import com.lvmama.comm.vst.vo.VstOrdOrderVo;
/**
 * 查询待退款任务
 * @author songlianjun
 *
 */
public class ListWaitingRefundmentTaskAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 退款服务对像.
	 */
	private OrdRefundMentService ordRefundMentService;

	private PayPaymentRefundmentService payPaymentRefundmentService;
	/**
	 * 新系统订单服务
	 */
	private VstOrdOrderService vstOrdOrderService;
	private VstDistributorService vstDistributorService;
	/**
	 * 退款查询集合.
	 */
	private List<OrdRefundment> ordRefundmentList;
	
	private Map<String, Object> searchRefundMentMap = new HashMap<String, Object>();
		
	private TopicMessageProducer resourceMessageProducer;
	
	private OrderService orderServiceProxy;
	
	private UserUserProxy userUserProxy;
	
	private ComLogService comLogService;
	
	private Long refundmentId;
	private String refundBank;
	
	
	public VstDistributorService getVstDistributorService() {
		return vstDistributorService;
	}
	public void setVstDistributorService(VstDistributorService vstDistributorService) {
		this.vstDistributorService = vstDistributorService;
	}
	public void ordRefundMentQuery(){
		searchRefundMentMap.put("status", Constant.REFUNDMENT_STATUS.VERIFIED.name());
		searchRefundMentMap = formatMap(searchRefundMentMap);
		Map<String, Object> map=initialPageInfoByMap(ordRefundMentService.findOrdRefundByParamCount(searchRefundMentMap),searchRefundMentMap);
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
		ordRefundmentList=ordRefundMentService.findOrdRefundByParam(searchRefundMentMap,skipResults,maxResults);
		((Label)this.getComponent().getFellow("_totalRowCountLabel")).setValue(String.valueOf(ordRefundmentList.size()));
	}
	/**
	 * 退款到现金帐户.
	 * @param a
	 */
	public void refund(final A a){
		ZkMessage.showQuestion("您确定需要退款吗?", 
				new ZkMsgCallBack()	{
					public void execute() {
						Long  refundmentId= (Long)a.getAttribute("refundmentId");
						OrdRefundment orf = ordRefundMentService.findOrdRefundmentById(refundmentId);
						if (orf.isCanToDoRefund()) {
							String sync_key="ord_refundment_"+orf.getRefundmentId();
							try{
								//查询memcache，检查是否有其他进程在做同笔退款
								if(SynchronizedLock.isOnDoingMemCached(sync_key)) {
									alert("退款处理中，请勿重复提交！");
									return;
								}
								
								OrdOrder order = null;
								if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(orf.getSysCode())) {
									VstOrdOrderVo vstOrdOrderVo = vstOrdOrderService.getVstOrdOrderVo(orf.getOrderId());
									UserUser userUser=userUserProxy.getUserUserByUserNo(vstOrdOrderVo.getUserId());
									long refundedAmount = orderServiceProxy.getRefundAmountByOrderId(orf.getOrderId(), orf.getSysCode());
									order = new OrdOrder().setProp(vstOrdOrderVo, userUser, refundedAmount);
								}else {
									order = orderServiceProxy.queryOrdOrderByOrderId(orf.getOrderId());
								}
								UserUser user = userUserProxy.getUserUserByUserNo(order.getUserId());
								
								//分销渠道退款，发消息给分销渠道做退款，退款单直接成功状态；
								if(Constant.CHANNEL.DISTRIBUTOR_B2B.name().equalsIgnoreCase(order.getChannel())){
									
									//更新 ord_refundment状态为REFUNDED
									orf.setRefundTime(Calendar.getInstance().getTime());
									orf.setStatus(Constant.REFUNDMENT_STATUS.REFUNDED.name());
									orf.setOperatorName(getSessionUserName());
									ordRefundMentService.refundApproveSuccess(orf);
									
									if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(orf.getSysCode())) {
										comLogService.insert("ORD_REFUNDMENT", orf.getOrderId(), refundmentId, 
												getSessionUserName(), Constant.COM_LOG_CASH_EVENT.updateOrderRefundment.name(), 
												"退款单退款", "退款单退款", Constant.PAYMENT_BIZ_TYPE.VST_ORDER.name());
									}else{
										comLogService.insert("ORD_REFUNDMENT", orf.getOrderId(), refundmentId, 
												getSessionUserName(), Constant.COM_LOG_CASH_EVENT.updateOrderRefundment.name(), 
												"退款单退款", "退款单退款", Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
									}
									
									Message msg = MessageFactory.newTntOrderRefundMessage(orf.getRefundmentId());
									if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(orf.getSysCode())) {
										msg.setAddition(Constant.PAYMENT_BIZ_TYPE.VST_ORDER.name());
									}else{
										msg.setAddition(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
									}
									resourceMessageProducer.sendMsg(msg);
									
									alert("已退款");
									refreshComponent("search");
									
								}else{
									RefundmentToBankInfo refundInfo = new RefundmentToBankInfo();
									refundInfo.setRefundAmount(orf.getAmount());
									if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(orf.getSysCode())) {
										refundInfo.setBizType(Constant.PAYMENT_BIZ_TYPE.VST_ORDER.name());
									}else{
										refundInfo.setBizType(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
									}
									refundInfo.setObjectId(orf.getRefundmentId());
									refundInfo.setObjectType(Constant.PAYMENT_OBJECT_TYPE.ORD_REFUNDMENT.name());
									refundInfo.setRefundType(orf.getRefundType());
									refundInfo.setUserId(user.getId());
									refundInfo.setOperator(getOperatorName());
									//强制指定退款网关
									refundInfo.setRefundGateway(orf.getRefundBank());
									boolean success = payPaymentRefundmentService.createRefundment(order.getOrderId(), refundInfo);
									if (success) {
										//更新 ord_refundment状态为PROCESSING
										orf.setRefundTime(Calendar.getInstance().getTime());
										orf.setStatus(Constant.REFUNDMENT_STATUS.PROCESSING.name());
										orf.setOperatorName(getSessionUserName());
										ordRefundMentService.refundApproveSuccess(orf);
										if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(orf.getSysCode())) {
											comLogService.insert("ORD_REFUNDMENT", orf.getOrderId(), refundmentId, 
													getSessionUserName(), Constant.COM_LOG_CASH_EVENT.updateOrderRefundment.name(), 
													"退款单退款", "退款单退款", Constant.PAYMENT_BIZ_TYPE.VST_ORDER.name());
										}else{
											comLogService.insert("ORD_REFUNDMENT", orf.getOrderId(), refundmentId, 
													getSessionUserName(), Constant.COM_LOG_CASH_EVENT.updateOrderRefundment.name(), 
													"退款单退款", "退款单退款", Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
										}
										
										
										Message msg = MessageFactory.newPaymentRefundmentMessage(orf.getRefundmentId());
										if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(orf.getSysCode())) {
											msg.setAddition(Constant.PAYMENT_BIZ_TYPE.VST_ORDER.name());
										}else{
											msg.setAddition(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
										}
										resourceMessageProducer.sendMsg(msg);
										
										alert("退款处理中");
										refreshComponent("search");
									}
								}
							}finally {
								//释放锁
								SynchronizedLock.releaseMemCached(sync_key);
							}
						}else{
							alert("退款失败，请检查退款金额是否不是0，是否已经做过了退款等！");
						}
					}
				}, new ZkMsgCallBack() {
					public void execute() {}
				}
		);
	}
	public void doBefore() {
		if(refundmentId!=null){
			OrdRefundment orf = ordRefundMentService.findOrdRefundmentById(refundmentId);
			this.refundBank=orf.getRefundBank();	
		}
		searchRefundMentMap.put("status", Constant.REFUNDMENT_STATUS.VERIFIED.name());
		searchRefundMentMap.put("sysCode", Constant.COMPLAINT_SYS_CODE.SUPER.name());
		searchRefundMentMap = formatMap(searchRefundMentMap);
	}
	/**
	 * 修改退款网关.
	 * @param a
	 */
	public void modifyRefundGateway(){
		OrdRefundment ordRefundment =ordRefundMentService.findOrdRefundmentById(refundmentId);
		ordRefundment.setRefundBank(refundBank);
		ordRefundMentService.updateOrdRefundmentByPK(ordRefundment);
		alert("更改完毕!");
		refreshComponent("win_modify_refund_gateway_colse");
	}
	public List<OrdRefundment> getOrdRefundmentList() {
		return ordRefundmentList;
	}
	
	public Map<String, Object> getSearchRefundMentMap() {
		return searchRefundMentMap;
	}

	public void setSearchRefundMentMap(Map<String, Object> searchRefundMentMap) {
		this.searchRefundMentMap = searchRefundMentMap;
	}

	public void setOrdRefundMentService(OrdRefundMentService ordRefundMentService) {
		this.ordRefundMentService = ordRefundMentService;
	}

	public void setPayPaymentRefundmentService(
			PayPaymentRefundmentService payPaymentRefundmentService) {
		this.payPaymentRefundmentService = payPaymentRefundmentService;
	}
	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	public Long getRefundmentId() {
		return refundmentId;
	}
	public void setRefundmentId(Long refundmentId) {
		this.refundmentId = refundmentId;
	}
	public String getRefundBank() {
		return refundBank;
	}
	public void setRefundBank(String refundBank) {
		this.refundBank = refundBank;
	}
	public void setVstOrdOrderService(VstOrdOrderService vstOrdOrderService) {
		this.vstOrdOrderService = vstOrdOrderService;
	}
}
