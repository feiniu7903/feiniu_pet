/**
 * 
 */
package com.lvmama.train.service.request;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.order.OrderCancelReqVo;
import com.lvmama.train.service.TrainResponse;
import com.lvmama.train.service.response.CancelOrderResponse;

/**
 * 2.2.	取消订单接口
 * @author yangbin
 *
 */
public class CancelOrderRequest extends AbstractTrainRequest {

	public CancelOrderRequest(ReqVo vo) {
		super(vo);
		if(!(vo instanceof OrderCancelReqVo))
			throw new RuntimeException("未接受到正确的处理数据!");
		OrderCancelReqVo ocvo = (OrderCancelReqVo)vo;
		put("user_ip", ocvo.getUserIp());
		put("order_id", ocvo.getOrderId());
	}

	@Override
	public Class<? extends TrainResponse> getClazz() {
		return CancelOrderResponse.class;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "http://" + hostaddress + Constant.TRAIN_INTERFACE.TICKET_ORDER_CANCEL.getUrl();
	}

	@Override
	public String getBaseUrl() {
		// TODO Auto-generated method stub
		return Constant.TRAIN_INTERFACE.TICKET_ORDER_CANCEL.getUrl();
	}

}
