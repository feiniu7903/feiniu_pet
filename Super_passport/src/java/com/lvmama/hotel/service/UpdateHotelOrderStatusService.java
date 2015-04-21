package com.lvmama.hotel.service;

public interface UpdateHotelOrderStatusService {
	/**
	 * 更新订单状态.
	 * 
	 * 通过供应商和订单号查询对方订单状态，对于处理成功的不做查询，也不做更新
	 */
	public void updateOrderStatus() throws Exception;
}
