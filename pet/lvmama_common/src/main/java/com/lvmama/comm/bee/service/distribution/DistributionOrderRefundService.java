package com.lvmama.comm.bee.service.distribution;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionOrderRefund;

public interface DistributionOrderRefundService {

	/**
	 * 条件查询分销订单退款历史列表
	 */
	public List<DistributionOrderRefund> queryDistributionOrderRefundByParams(Map<String, Object> params);
	
	public Long queryDistributionOrderRefundByParamsCount(Map<String, Object> params);
	/**
	 * 分销重新退款状态查询
	 * 
	 * @param distributionOrderRefundId
	 * @return
	 */
	public String selectRefundStatusByOrderRefundId(Long distributionOrderRefundId);
	
	public DistributionOrderRefund selectDistributionOrderRefundById(Long refundId);

	/**
	 * 新增分销退款历史 
	 */
	public void insertDistributionRefund(DistributionOrderRefund refund);
}
