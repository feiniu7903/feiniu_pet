package com.lvmama.comm.bee.service.pass;


public interface UpdateOrderStatusService {

	/**
	 * 针对提交废码申请后，对方非实时返回结果，需审核时
	 * 对方不推送审核结果，需定时去查找审核结果
	 * @throws Exception
	 */
	public void updateOrderStatus();
}
