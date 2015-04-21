package com.lvmama.train.service.response;

import java.util.List;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.train.product.TicketBookInfo;
import com.lvmama.comm.vo.train.product.TicketBookRspVo;

public class TicketBookInfoQueryResponse extends AbstractTrainResponse {

	@SuppressWarnings("unchecked")
	@Override
	public void parse(String response) throws RuntimeException {
		List<TicketBookInfo> ticketBookInfos = 
				JsonUtil.getList4Json(response, TicketBookInfo.class, null);
		this.getRsp().setVo(new TicketBookRspVo(ticketBookInfos));
	}

}
