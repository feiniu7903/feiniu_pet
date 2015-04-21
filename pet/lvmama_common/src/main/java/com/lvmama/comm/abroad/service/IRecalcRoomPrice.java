package com.lvmama.comm.abroad.service;

import com.lvmama.comm.abroad.vo.request.RecalcRoomPriceReq;
import com.lvmama.comm.abroad.vo.response.RecalcRoomPriceRes;

public interface IRecalcRoomPrice {
	/**
	 * 计算房间价格
	 * @param recalcRoomPriceReq
	 * @param sessionId
	 * @return
	 */
	public RecalcRoomPriceRes recalcRoomPrice(RecalcRoomPriceReq recalcRoomPriceReq,String sessionId);
}
