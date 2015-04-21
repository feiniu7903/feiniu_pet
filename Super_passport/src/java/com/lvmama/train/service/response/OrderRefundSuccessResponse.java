package com.lvmama.train.service.response;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.train.order.OrderRefundSuccessRspVo;

public class OrderRefundSuccessResponse extends AbstractTrainResponse {

	@Override
	public void parse(String response) throws RuntimeException {
		// TODO Auto-generated method stub
		OrderRefundSuccessRspVo vo = (OrderRefundSuccessRspVo)JsonUtil.getObject4JsonString(response, 
				OrderRefundSuccessRspVo.class, null);
		this.getRsp().setVo(vo);
	}

}
