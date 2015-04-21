/**
 * 
 */
package com.lvmama.train.service.response;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.train.order.OrderCancelRspVo;


/**
 * @author yangbin
 *
 */
public class CancelOrderResponse extends AbstractTrainResponse {

	@Override
	public void parse(String response) throws RuntimeException {
		// TODO Auto-generated method stub
		OrderCancelRspVo vo = (OrderCancelRspVo)JsonUtil.getObject4JsonString(response, 
				OrderCancelRspVo.class, null);
		this.getRsp().setVo(vo);
	}
	
}
