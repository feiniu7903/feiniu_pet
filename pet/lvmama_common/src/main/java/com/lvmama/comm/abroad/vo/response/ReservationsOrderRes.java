package com.lvmama.comm.abroad.vo.response;

import java.util.List;

public class ReservationsOrderRes extends ErrorRes{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ReservationsOrder> ReservationsOrders;
	/**
	 * 返回查询结果
	 * @return
	 */
	public List<ReservationsOrder> getReservationsOrders() {
		return ReservationsOrders;
	}
	public void setReservationsOrders(List<ReservationsOrder> reservationsOrders) {
		ReservationsOrders = reservationsOrders;
	}
}
