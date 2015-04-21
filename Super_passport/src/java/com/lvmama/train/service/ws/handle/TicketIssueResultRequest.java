/**
 * 
 */
package com.lvmama.train.service.ws.handle;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficTicketInfo;
import com.lvmama.comm.bee.po.pub.ComJobContent;
import com.lvmama.comm.bee.service.com.ComJobContentService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.ord.OrderTrafficService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.BaseVo;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.Rsp;
import com.lvmama.comm.vo.train.notify.TicketIssueInfo;
import com.lvmama.comm.vo.train.notify.TicketIssueNotifyVo;

/**
 * 出票结果通知
 * @author yangbin
 *
 */
public class TicketIssueResultRequest extends AbstractNotifyRequest {
	private static final Log logger = LogFactory.getLog(TicketIssueResultRequest.class);
	private OrderTrafficService orderTrafficService;
	private TopicMessageProducer orderMessageProducer;
	private ComJobContentService comJobContentService;
	private OrderService orderServiceProxy;
	private int[] status = new int[]{Constant.TRAIN_ORDER_STATUS.ORDER_STATUS_806.getCode(), 
			Constant.TRAIN_ORDER_STATUS.ORDER_STATUS_807.getCode(),
			Constant.TRAIN_ORDER_STATUS.ORDER_STATUS_808.getCode(),
			Constant.TRAIN_ORDER_STATUS.ORDER_STATUS_809.getCode(),
			Constant.TRAIN_ORDER_STATUS.ORDER_STATUS_810.getCode()};
	
	public TicketIssueResultRequest() {
		orderMessageProducer = SpringBeanProxy.getBean(TopicMessageProducer.class, "orderMessageProducer");
		orderTrafficService = SpringBeanProxy.getBean(OrderTrafficService.class, "orderTrafficService");
		orderServiceProxy = SpringBeanProxy.getBean(OrderService.class,"orderServiceProxy");
		comJobContentService = SpringBeanProxy.getBean(ComJobContentService.class,"comJobContentService");
	}
	
	@Override
	public Rsp handle(ReqVo vo) throws RuntimeException{
		if(!(vo instanceof TicketIssueNotifyVo))
			throw new RuntimeException("未接受到正确的处理数据!");
		TicketIssueNotifyVo tinvo = (TicketIssueNotifyVo)vo;
		
		Rsp rsp = check(tinvo);
		if(rsp != null) 
			return rsp;
		
		String supplierOrderId = tinvo.getOrderId();
		boolean isIssueSuccess = (Constant.TRAIN_ORDER_STATUS.ORDER_STATUS_806.getCode() == NumberUtils.toInt(tinvo.getOrderStatus()))
				|| (Constant.TRAIN_ORDER_STATUS.ORDER_STATUS_807.getCode() == NumberUtils.toInt(tinvo.getOrderStatus()))
				|| (Constant.TRAIN_ORDER_STATUS.ORDER_STATUS_808.getCode() == NumberUtils.toInt(tinvo.getOrderStatus()));
		List<OrdOrderTrafficTicketInfo> list = new ArrayList<OrdOrderTrafficTicketInfo>();
		if(isIssueSuccess){
			for(TicketIssueInfo tii : tinvo.getIssueInfos()){
				list.add(getTicket(tii, tinvo.getOrderStatus())); 
			}
		}
//		else{
//			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderItemMetaId(traffic.getOrderItemMetaId());
//			boolean flag = orderServiceProxy.cancelOrder(order.getOrderId(), "火车票订单失败", "SYSTEM");
//			if(flag){
//				orderServiceProxy.autoCreateOrderFullRefund(order,  "SYSTEM", "火车票订单失败");
//			}
//			return null;
//		}
		
//		logger.info("获取的乘客信息数量:" + list.size());
		ResultHandleT<BaseVo> handle = orderTrafficService.ticketIssueResult(supplierOrderId, list);
		
		if(handle.isFail()){
			return new Rsp(Constant.HTTP_SERVER_ERROR, Constant.HTTP_SERVER_ERROR_MSG, handle.getReturnContent());
		}
		OrdOrderTraffic traffic = orderTrafficService.getTrafficBySupplierOrderId(supplierOrderId);
		logger.info("出票："+traffic.getOrderItemMetaId()+"   "+handle.isSuccess()+"    "+handle.getReturnContent().getRet_code()+"    "+handle.getReturnContent().getRet_msg());
		if(handle.getReturnContent().getRet_code()!=Constant.REPLY_CODE.REPEAT.getRetCode()){
			if(traffic.hasFailStatus()){//出票失败时废单
				OrdOrder order = orderServiceProxy.queryOrdOrderByOrderItemMetaId(traffic.getOrderItemMetaId());
				ComJobContent comJobContent = new ComJobContent();
				comJobContent.setJobType(ComJobContent.JOB_TYPE.TRAIN_FAIL_CANCEL_ORDER.name());
				comJobContent.setObjectId(order.getOrderId());
				comJobContent.setObjectType("ORD_ORDER");
				comJobContent.setPlanTime(DateUtils.addMinutes(comJobContent.getCreateTime(), 5));
				comJobContentService.add(comJobContent);
			}else if(isIssueSuccess&&traffic.hasIssueStatus()){//出票成功后的处理
				logger.info("出票成功："+traffic.getOrderItemMetaId());
				orderMessageProducer.sendMsg(MessageFactory.newTrafficIssueRefundMessage(traffic.getOrderItemMetaId()));
			}
		}
		return new Rsp(handle.getReturnContent());
	}
	
	/**
	 * 将VO车票信息转换为PO
	 * @param tii
	 * @return OrdOrderTrafficTicketInfo
	 */
	private OrdOrderTrafficTicketInfo getTicket(TicketIssueInfo tii, String status){
		OrdOrderTrafficTicketInfo info = new OrdOrderTrafficTicketInfo();
		info.setTicketId(String.valueOf(tii.getTicket_id()));
		info.setTicketStatus(status);
		info.setName(tii.getPassenger_name());
		info.setIdentity(String.valueOf(tii.getId_type()));
		info.setIdentityNo(tii.getId_num());
		info.setSeat(String.valueOf(tii.getTicket_class()));
		info.setTicketCategory(String.valueOf(tii.getTicket_type()));
		info.setPrice(tii.getPriceFen());
		info.setSeatNo(tii.getTicket_seat());
		return info;
	}
	
	/**
	 * 检查表单数据是否正确
	 * @param vo
	 * @return
	 */
	private Rsp check(TicketIssueNotifyVo vo){
		Rsp rsp = super.check(vo);
		if(rsp != null)
			return rsp;
		
		if(StringUtils.isEmpty(vo.getOrderId())){
			return new Rsp(Constant.HTTP_SERVER_ERROR, 
					Constant.HTTP_SERVER_ERROR_MSG, 
					new BaseVo(Constant.REPLY_CODE.ORDER_NOTEXIST.getRetCode(), 
							Constant.REPLY_CODE.ORDER_NOTEXIST.getRetMsg()));
		}else if(vo.getIssueInfos() == null || vo.getIssueInfos().size() == 0){
			return new Rsp(Constant.HTTP_SERVER_ERROR, 
					Constant.HTTP_SERVER_ERROR_MSG, 
					new BaseVo(Constant.REPLY_CODE.MISS_JSON_PARAM.getRetCode(), 
							Constant.REPLY_CODE.MISS_JSON_PARAM.getRetMsg()));
		}else if(!ArrayUtils.contains(status, NumberUtils.toInt(vo.getOrderStatus()))){
			return new Rsp(Constant.HTTP_SERVER_ERROR, 
					Constant.HTTP_SERVER_ERROR_MSG, 
					new BaseVo(Constant.REPLY_CODE.STATUS_CANNOT_HANDLE.getRetCode(), 
							Constant.REPLY_CODE.STATUS_CANNOT_HANDLE.getRetMsg()));
		}else return null;
	}

	public void setComJobContentService(ComJobContentService comJobContentService) {
		this.comJobContentService = comJobContentService;
	}

}
