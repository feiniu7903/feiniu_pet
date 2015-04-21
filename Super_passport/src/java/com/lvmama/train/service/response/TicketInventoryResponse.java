/**
 * 
 */
package com.lvmama.train.service.response;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.train.product.TicketInventoryRspVo;


/**
 * @author yangbin
 *
 */
public class TicketInventoryResponse extends AbstractTrainResponse {

	@Override
	public void parse(String response) throws RuntimeException {
		// TODO Auto-generated method stub
		TicketInventoryRspVo vo;
		//若未返回任何东西，则按照无票情况处理
		if(response == null || "".equals(response) || "[]".equals(response) || "{}".equals(response)){
			vo = new TicketInventoryRspVo();
		}else{
			vo = (TicketInventoryRspVo)JsonUtil.getObject4JsonString(response, TicketInventoryRspVo.class, null);
		}
		this.getRsp().setVo(vo);
	}

}
