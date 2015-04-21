/**
 * 
 */
package com.lvmama.train.service.response;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.train.order.OrderCreateRspVo;


/**
 * @author yangbin
 *
 */
public class CreateOrderResponse extends AbstractTrainResponse {

	@Override
	public void parse(String response) throws RuntimeException {
		// TODO Auto-generated method stub
		OrderCreateRspVo vo = (OrderCreateRspVo)JsonUtil.getObject4JsonString(response, 
				OrderCreateRspVo.class, null);
		this.getRsp().setVo(vo);
	}

}
