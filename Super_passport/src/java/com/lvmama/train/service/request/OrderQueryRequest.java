package com.lvmama.train.service.request;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.order.OrderQueryReqVo;
import com.lvmama.train.service.TrainResponse;
import com.lvmama.train.service.response.OrderQueryResponse;

/**
 * 2.4.	订单状态查询接口
 * @author XuSemon
 * @date 2013-8-12
 */
public class OrderQueryRequest extends AbstractTrainRequest {
	
	public OrderQueryRequest(ReqVo vo){
		super(vo);
		if(!(vo instanceof OrderQueryReqVo))
			throw new RuntimeException("未接受到正确的处理数据!");
		OrderQueryReqVo oqvo = (OrderQueryReqVo)vo;
		put("user_ip", oqvo.getUserIp());
		put("order_id", oqvo.getOrderId());
	}

	@Override
	public Class<? extends TrainResponse> getClazz() {
		// TODO Auto-generated method stub
		return OrderQueryResponse.class;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "http://" + hostaddress + Constant.TRAIN_INTERFACE.TICKET_ORDER_QUERY.getUrl();
	}

	@Override
	public String getBaseUrl() {
		// TODO Auto-generated method stub
		return Constant.TRAIN_INTERFACE.TICKET_ORDER_QUERY.getUrl();
	}

}
