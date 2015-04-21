package com.lvmama.train.service.request;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.order.TicketDrawbackReqVo;
import com.lvmama.train.service.TrainResponse;
import com.lvmama.train.service.response.TicketDrawbackResponse;

/**
 * 2.5.	退票请求接口
 * @author XuSemon
 * @date 2013-8-12
 */
public class TicketDrawbackRequest extends AbstractTrainRequest {
	
	public TicketDrawbackRequest(ReqVo vo){
		super(vo);
		if(!(vo instanceof TicketDrawbackReqVo))
			throw new RuntimeException("未接受到正确的处理数据!");
		TicketDrawbackReqVo tdvo = (TicketDrawbackReqVo)vo;
		put("user_ip", tdvo.getUserIp());
		put("order_id", tdvo.getOrderId());
		put("refund_type", String.valueOf(tdvo.getRefundType()));
		put("ticket_num", String.valueOf(tdvo.getTicketNum()));
		put("json_param", JsonUtil.getJsonString4List(tdvo.getTicketDrawbackInfos()));
	}

	@Override
	public Class<? extends TrainResponse> getClazz() {
		// TODO Auto-generated method stub
		return TicketDrawbackResponse.class;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "http://" + hostaddress + Constant.TRAIN_INTERFACE.TICKET_ORDER_DRAWBACK.getUrl();
	}

	@Override
	public String getBaseUrl() {
		// TODO Auto-generated method stub
		return Constant.TRAIN_INTERFACE.TICKET_ORDER_DRAWBACK.getUrl();
	}

}
