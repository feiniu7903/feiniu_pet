package com.lvmama.train.service.request;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.order.OrderRefundSuccessReqVo;
import com.lvmama.train.service.TrainResponse;
import com.lvmama.train.service.response.OrderRefundSuccessResponse;

/**
 * 2.6.	退款成功请求接口
 * @author XuSemon
 * @date 2013-8-12
 */
public class OrderRefundSuccessRequest extends AbstractTrainRequest {
	
	public OrderRefundSuccessRequest(ReqVo vo){
		super(vo);
		if(!(vo instanceof OrderRefundSuccessReqVo))
			throw new RuntimeException("未接受到正确的处理数据!");
		OrderRefundSuccessReqVo orsvo = (OrderRefundSuccessReqVo)vo;
		put("user_ip", orsvo.getUserIp());
		put("refund_id", orsvo.getRefundId());
		put("order_id", orsvo.getOrderId());
		put("ticket_fee", String.valueOf(orsvo.getTicketFee()));
		put("ticket_charge", String.valueOf(orsvo.getTicketCharge()));
		put("refund_type", String.valueOf(orsvo.getRefundType()));
	}

	@Override
	public Class<? extends TrainResponse> getClazz() {
		// TODO Auto-generated method stub
		return OrderRefundSuccessResponse.class;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "http://" + hostaddress + Constant.TRAIN_INTERFACE.TICKET_ORDER_REFUND.getUrl();
	}

	@Override
	public String getBaseUrl() {
		// TODO Auto-generated method stub
		return Constant.TRAIN_INTERFACE.TICKET_ORDER_REFUND.getUrl();
	}

}
