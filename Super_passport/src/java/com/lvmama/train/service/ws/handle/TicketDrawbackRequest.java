package com.lvmama.train.service.ws.handle;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficTicketInfo;
import com.lvmama.comm.bee.service.ord.OrderTrafficService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.BaseVo;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.Rsp;
import com.lvmama.comm.vo.train.notify.TicketDrawbackInfo;
import com.lvmama.comm.vo.train.notify.TicketDrawbackNotifyVo;

/**
 * 退票结果通知
 * @author XuSemon
 * @date 2013-8-7
 */
public class TicketDrawbackRequest extends AbstractNotifyRequest{
	private OrderTrafficService orderTrafficService;
	
	public TicketDrawbackRequest(){
		orderTrafficService = SpringBeanProxy.getBean(OrderTrafficService.class, "orderTrafficService");
	}

	@Override
	public Rsp handle(ReqVo vo) throws RuntimeException {
		if(!(vo instanceof TicketDrawbackNotifyVo))
			throw new RuntimeException("未接受到正确的处理数据!");
		TicketDrawbackNotifyVo tdnvo = (TicketDrawbackNotifyVo)vo;
		
		Rsp rsp = check(tdnvo);
		if(rsp != null)
			return rsp;
		
		String supplierOrderId = tdnvo.getOrderId();
		//是否下单、出票判断
		OrdOrderTraffic traffic = orderTrafficService.getTrafficBySupplierOrderId(supplierOrderId);
		if(traffic == null){
			return new Rsp(Constant.HTTP_SERVER_ERROR, 
					Constant.HTTP_SERVER_ERROR_MSG, 
					new BaseVo(Constant.REPLY_CODE.ORDER_NOTEXIST.getRetCode(), 
									  Constant.REPLY_CODE.ORDER_NOTEXIST.getRetMsg()));
		}
		if(!Constant.ORDER_TRAFFIC_STATUS.ISSUE.getCode().equals(traffic.getStatus())){
			return new Rsp(Constant.HTTP_SERVER_ERROR, 
					Constant.HTTP_SERVER_ERROR_MSG, 
					new BaseVo(Constant.REPLY_CODE.HAS_NOT_ISSUE.getRetCode(), 
									  Constant.REPLY_CODE.HAS_NOT_ISSUE.getRetMsg()));
		}
			
		StringBuilder sb = null;
		Map<String, OrdOrderTrafficTicketInfo> tickets = orderTrafficService.getAllTicketsByOrderId(supplierOrderId);
		for(TicketDrawbackInfo info : tdnvo.getInfos()){
			OrdOrderTrafficTicketInfo ticket = tickets.get(String.valueOf(info.getTicket_id()));
			//车票编号不存在则返回
			if(ticket == null){
				return new Rsp(Constant.HTTP_SERVER_ERROR, 
								Constant.HTTP_SERVER_ERROR_MSG, 
								new BaseVo(Constant.REPLY_CODE.TICKET_NOEXIST.getRetCode(), 
												  Constant.REPLY_CODE.TICKET_NOEXIST.getRetMsg()));
			}
			//当退票状态为成功时，才需要更新数据库
			if(info.getTicket_status() == 0){
				if(sb == null)
					sb = new StringBuilder(String.valueOf(info.getTicket_id()));
				else
					sb.append(",").append(String.valueOf(info.getTicket_id()));
			}
		}
		orderTrafficService.updateDrawbackTicketById(sb.toString(), tdnvo.getRefundId());
		return new Rsp(new BaseVo(Constant.REPLY_CODE.SUCCESS.getRetCode(), 
										 Constant.REPLY_CODE.SUCCESS.getRetMsg()));
	}
	
	/**
	 * 检查请求数据是否正确
	 * @param vo
	 * @return
	 */
	private Rsp check(TicketDrawbackNotifyVo vo){
		Rsp rsp = super.check(vo);
		if(rsp != null)
			return rsp;
		
		if(StringUtils.isEmpty(vo.getOrderId())){
			return new Rsp(Constant.HTTP_SERVER_ERROR, 
					Constant.HTTP_SERVER_ERROR_MSG, 
					new BaseVo(Constant.REPLY_CODE.ORDER_NOTEXIST.getRetCode(), 
							Constant.REPLY_CODE.ORDER_NOTEXIST.getRetMsg()));
		}else if(StringUtils.isEmpty(vo.getRefundId())){
			return new Rsp(Constant.HTTP_SERVER_ERROR, 
					Constant.HTTP_SERVER_ERROR_MSG, 
					new BaseVo(Constant.REPLY_CODE.MISS_REFUND_ID.getRetCode(), 
							Constant.REPLY_CODE.MISS_REFUND_ID.getRetMsg()));
		}else if(vo.getInfos() == null || vo.getInfos().size() == 0){
			return new Rsp(Constant.HTTP_SERVER_ERROR, 
					Constant.HTTP_SERVER_ERROR_MSG, 
					new BaseVo(Constant.REPLY_CODE.MISS_JSON_PARAM.getRetCode(), 
							Constant.REPLY_CODE.MISS_JSON_PARAM.getRetMsg()));
		}else return null;
	}
}
