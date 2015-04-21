package com.lvmama.order.trigger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.service.ebooking.CertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkTaskService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
/**
 * 供应商凭证生成
 * @author yuzhibing
 *
 */
public class SupplierCertificateProcesser implements MessageProcesser {
	private static final Log log = LogFactory.getLog(SupplierCertificateProcesser.class);
	private CertificateService certificateServiceProxy;
	private OrderService orderServiceProxy;
	private EbkCertificateService ebkCertificateService;
	private EbkTaskService ebkTaskService;
	
	public void process(Message message) {
		OrdOrder ordOrder = null;
		String userMemoStatus = null;
		if(message.isOrderCreateMsg()){
			infoLog(message.toString());
			 ordOrder = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
//			 if(!"true".equals(ordOrder.getResourceConfirm()) || ordOrder.isApproveResourceAmple()) {
				 if(hasOrderMemo(ordOrder)){
					 userMemoStatus = "false";
				 }
//			 }
			 certificateServiceProxy.createSupplierCertificate(ordOrder,CertificateService.ORDER_CREATE,userMemoStatus, null);
		}else if(message.isOrderCancelMsg()){
			infoLog(message.toString());
			 ordOrder = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			certificateServiceProxy.createSupplierCertificate(ordOrder,CertificateService.ORDER_CANCEL,null, null);
		}else if(message.isOrderApproveMsg()){
			infoLog(message.toString());
			 ordOrder = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			certificateServiceProxy.createSupplierCertificate(ordOrder,CertificateService.ORDER_APPROVE,null, null);
		}else if(message.isOrderModifyPerson()){
			infoLog(message.toString());
			ordOrder = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			if(ordOrder.isNormal()){
				certificateServiceProxy.createSupplierCertificate(ordOrder,CertificateService.ORDER_MODIFY_PERSON,null, null);
			}
		}else if(message.isOrderPaymentMsg()){
			infoLog(message.toString());
			 ordOrder = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			certificateServiceProxy.createSupplierCertificate(ordOrder,CertificateService.ORDER_PAYMENT,null, null);
		}else if(message.isOrderModifySettlementPrice()){
			infoLog(message.toString());
			// 查找需要处理的订单正常只有个订单，结算价修改会有多个。
			List<OrdOrder> ordList = fillOrderList(message);
			//凭证生成 
			for (OrdOrder ord : ordList) {
				log.info("ebkCertificate Order:" + ord.getOrderId());
				 if(hasOrderMemo(ord)){
					 userMemoStatus = "false";
				 }
				certificateServiceProxy.createSupplierCertificate(ord,CertificateService.ORDER_MODIFY_SETTLEMENT_PRICE,userMemoStatus, getMessageOrderItemMetaIds(message));
			}
		}
		if (message.isOrderPaymentMsg() || message.isOrderCancelMsg()) {
			ebkTaskService.updateEbkTaskSynOrder(ordOrder.getOrderId(), ordOrder.getPaymentStatus(), ordOrder.getOrderStatus());
		}
	}
	private void infoLog(String contnet){
		log.info("ebk "+contnet);
	}
	/**
	 * 因订单结算价格修改消息传的是订单子子项ID列表，所以要从中取出对应的订单列表来
	 * <br>如果存在订单特殊备注，不进入
	 * @author: ranlongfei 2013-4-9 下午6:01:13
	 * @param message
	 * @return
	 */
	private List<OrdOrder> fillOrderList(Message message) {
		//结算价格的消息只有附加信息中的子子项ID
		List<OrdOrder> ordList = new ArrayList<OrdOrder>();
		if(message.isOrderModifySettlementPrice()) {
			//结算价修改消息
			String ids = getMessageOrderItemMetaIds(message);
			if("".equals(ids)){
				return ordList;
			}
			Set<Long> orderIds = new HashSet<Long>();
			for(String id : ids.split(",")) {
				OrdOrderItemMeta item = orderServiceProxy.queryOrdOrderItemMetaBy(Long.valueOf(id));
				orderIds.add(item.getOrderId());
			}
			for(Long oId : orderIds) {
				OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(oId);
				ordList.add(ordOrder);
			}
		} 
		return ordList;
	}
	/**
	 * 
	 * @author: ranlongfei 2013-5-10 下午5:48:02
	 * @param message
	 * @return
	 */
	public String getMessageOrderItemMetaIds(Message message) {
		if(message.getAddition() != null && message.getAddition().contains("|")) {
			String ids = message.getAddition().substring(0, message.getAddition().indexOf("|"));
			return ids;
		}
		return "";
	}
	
	/**
	 * 创建订单时是否有用户备注
	 * 
	 * @author: ranlongfei 2013-1-29 上午11:52:16
	 * @param order
	 * @return
	 */
	private boolean hasOrderMemo(OrdOrder order) {
		List<OrdOrderMemo> memoList = orderServiceProxy.queryMemoByOrderId(order.getOrderId());
		if(memoList != null && memoList.size() > 0) {
			for(OrdOrderMemo m : memoList) {
				if(m.hasUserMemo() && m.hasUserMemoApprove()) {
					log.info("hasOrderMemo order_id:"+order.getOrderId());
					return true;
				}
			}
		}
		return false;
	}
	
	public CertificateService getCertificateServiceProxy() {
		return certificateServiceProxy;
	}

	public void setCertificateServiceProxy(CertificateService certificateServiceProxy) {
		this.certificateServiceProxy = certificateServiceProxy;
	}


	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}


	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public EbkTaskService getEbkTaskService() {
		return ebkTaskService;
	}

	public void setEbkTaskService(EbkTaskService ebkTaskService) {
		this.ebkTaskService = ebkTaskService;
	}
	public EbkCertificateService getEbkCertificateService() {
		return ebkCertificateService;
	}
	public void setEbkCertificateService(EbkCertificateService ebkCertificateService) {
		this.ebkCertificateService = ebkCertificateService;
	}
	
}
