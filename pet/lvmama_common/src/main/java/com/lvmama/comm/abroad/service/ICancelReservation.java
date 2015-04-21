package com.lvmama.comm.abroad.service;

import com.lvmama.comm.abroad.vo.request.CancelReservationReq;
import com.lvmama.comm.abroad.vo.response.CancelReservationRes;

public interface ICancelReservation {
	/**
	 * 预订订单取消
	 * @param cancelReservationReq
	 * @return
	 */
	public CancelReservationRes cancelReservation(CancelReservationReq cancelReservationReq,String sessionId);
	
	/**
	 * 预订订单取消
	 * @param orderNo
	 * @param userId
	 * @param reason
	 * @return
	 */
	public CancelReservationRes cancelReservation(String orderNo,String userId,String reason);
}
