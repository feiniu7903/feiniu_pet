package com.lvmama.train.service.request;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.order.OrderPaidReqVo;
import com.lvmama.train.service.TrainResponse;
import com.lvmama.train.service.response.OrderPaidResponse;

/**
 * 2.3.	订单支付成功接口
 * @author XuSemon
 * @date 2013-8-12
 */
public class OrderPaidRequest extends AbstractTrainRequest {
	
	public OrderPaidRequest(ReqVo vo){
		super(vo);
		if(!(vo instanceof OrderPaidReqVo))
			throw new RuntimeException("未接受到正确的处理数据!");
		OrderPaidReqVo opvo = (OrderPaidReqVo)vo;
		put("user_ip", opvo.getUserIp());
		put("order_id", opvo.getOrderId());
		put("paid_result", String.valueOf(opvo.getPaidResult()));
	}

	@Override
	public Class<? extends TrainResponse> getClazz() {
		// TODO Auto-generated method stub
		return OrderPaidResponse.class;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "http://" + hostaddress + Constant.TRAIN_INTERFACE.TICKET_ORDER_PAID.getUrl();
	}

	@Override
	public String getBaseUrl() {
		// TODO Auto-generated method stub
		return Constant.TRAIN_INTERFACE.TICKET_ORDER_PAID.getUrl();
	}

}
