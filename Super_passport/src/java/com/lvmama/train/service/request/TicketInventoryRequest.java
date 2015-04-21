/**
 * 
 */
package com.lvmama.train.service.request;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.product.TicketInventoryReqVo;
import com.lvmama.train.service.TrainResponse;
import com.lvmama.train.service.response.TicketInventoryResponse;

/**
 * 1.7.	车票库存验证接口
 * @author yangbin
 *
 */
public class TicketInventoryRequest extends AbstractTrainRequest {

	public TicketInventoryRequest(ReqVo vo) {
		super(vo);
		if(!(vo instanceof TicketInventoryReqVo))
			throw new RuntimeException("未接受到正确的处理数据!");
		TicketInventoryReqVo tivo = (TicketInventoryReqVo)vo;
		put("user_ip", tivo.getUserIp());
		put("depart_station", tivo.getDepartStation());
		put("arrive_station", tivo.getArriveStation());
		put("depart_date", tivo.getDepartDate());
		put("train_id", tivo.getTrainId());
		put("ticket_class", tivo.getTicketClass());
	}

	/* (non-Javadoc)
	 * @see com.lvmama.train.service.TrainRequest#getClazz()
	 */
	@Override
	public Class<? extends TrainResponse> getClazz() {
		return TicketInventoryResponse.class;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "http://" + hostaddress + Constant.TRAIN_INTERFACE.TRAIN_TICKET_VERIFY.getUrl();
	}

	@Override
	public String getBaseUrl() {
		// TODO Auto-generated method stub
		return Constant.TRAIN_INTERFACE.TRAIN_TICKET_VERIFY.getUrl();
	}

}
