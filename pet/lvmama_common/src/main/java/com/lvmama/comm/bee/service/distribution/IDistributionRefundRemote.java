package com.lvmama.comm.bee.service.distribution;

import com.lvmama.comm.bee.po.distribution.DistributionOrderRefund;


/**
 * 分销订单退款服务
 * @author lipengcheng
 *
 */
public interface IDistributionRefundRemote {
	/**
	 * 分销订单退款
	 * @return
	 */
	boolean refund(DistributionOrderRefund refund);
	
}
