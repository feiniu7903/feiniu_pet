package com.lvmama.train.service.request;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.product.TicketBookReqVo;
import com.lvmama.train.service.TrainResponse;
import com.lvmama.train.service.response.TicketBookInfoQueryResponse;

/**
 * 1.6.	订票信息查询接口
 * @author XuSemon
 * @date 2013-8-12
 */
public class TicketBookInfoQueryRequest extends AbstractTrainRequest {
	public TicketBookInfoQueryRequest(ReqVo vo){
		super(vo);
		if(!(vo instanceof TicketBookReqVo))
			throw new RuntimeException("未接受到正确的处理数据!");
		TicketBookReqVo tbvo = (TicketBookReqVo)vo;
		put("user_ip", tbvo.getUserIp());
		put("depart_station", tbvo.getDepartStation());
		put("arrive_station", tbvo.getArriveStation());
		put("depart_date", tbvo.getDepartDate());
		put("train_id", tbvo.getTrainId());
	}

	@Override
	public Class<? extends TrainResponse> getClazz() {
		// TODO Auto-generated method stub
		return TicketBookInfoQueryResponse.class;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "http://" + hostaddress + Constant.TRAIN_INTERFACE.TRAIN_TICKET_QUERY.getUrl();
	}

	@Override
	public String getBaseUrl() {
		// TODO Auto-generated method stub
		return Constant.TRAIN_INTERFACE.TRAIN_TICKET_QUERY.getUrl();
	}

}
