package com.lvmama.comm.bee.service.distribution;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.OrdOrderDistribution;
import com.lvmama.comm.pet.vo.Page;

public interface DistributionOrderService {

	OrdOrderDistribution selectByOrderIdAndDistributionInfoId(Long orderId, Long distributionInfoId);
	
	void insertOrdOrderDistribution(OrdOrderDistribution ordOrderDitribution);

	/**
	 * 通过条件查询分销订单信息
	 * 
	 * @return
	 */
	List<OrdOrderDistribution> selectDistributionOrderByParams(Map<String, Object> params);

	/**
	 * 通过条件查询分销订单信息总数
	 * 
	 * @return
	 */
	Long selectDistributionOrderByParamsCount(Map<String, Object> params);
	/**
	 * 查询系统自动分配的序列号
	 * 
	 * @return
	 */
	Long getSerialNo();
	/**
	 * 查询分销订单资源审核状态、支付等待时间
	 * 
	 * @param distributorId
	 * @param orderIds
	 * @return
	 */
	List<OrdOrderDistribution> selectDistributionOrderApproveStatus(Long distributorId, String orderIds);
	/**
	 * 订单是否发过传真
	 * 
	 * @param orderId
	 * @return
	 */
	boolean isSentFax(Long orderId);
	/**
	 * 更新分销订单的退款状态
	 * 
	 * @param partnerOrderId
	 */
	void updateRefundStatusByPartnerOrdid(String partnerOrderId,Long distributionOrderRefundId);
	/**
	 * 更新分销订单的资源审核状态
	 * 此审核状态为分销商的订单审核状态
	 * 
	 * @param orderIds
	 * @param distributorId
	 */
	void updateOrdOrderDistributionResourceStatus(String orderIds, Long distributorId);
}
