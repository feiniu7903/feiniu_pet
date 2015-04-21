package com.lvmama.distribution.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.OrdOrderDistribution;
import com.lvmama.comm.bee.service.distribution.DistributionOrderService;
import com.lvmama.distribution.dao.DistributionOrderRefundDAO;
import com.lvmama.distribution.dao.OrdOrderDistributionDAO;

public class DistributionOrderServiceImpl implements DistributionOrderService{
	
	private OrdOrderDistributionDAO ordOrderDistributionDAO;
	
	private DistributionOrderRefundDAO distributionOrderRefundDAO;

	@Override
	public OrdOrderDistribution selectByOrderIdAndDistributionInfoId(
			Long orderId, Long distributionInfoId) {
		return ordOrderDistributionDAO.selectByOrderIdAndDistributionInfoId(orderId, distributionInfoId);
	}

	@Override
	public void insertOrdOrderDistribution(
			OrdOrderDistribution ordOrderDitribution) {
		ordOrderDistributionDAO.insert(ordOrderDitribution);
	}

	@Override
	public Long getSerialNo() {
		return ordOrderDistributionDAO.getSerialNo();
	}


	/**
	 * 通过条件查询分销订单信息
	 */
	public List<OrdOrderDistribution> selectDistributionOrderByParams(Map<String, Object> params) {
		return this.ordOrderDistributionDAO.selectByParams(params);
	}

	/**
	 * 通过条件查询分销订单信息总数
	 */
	public Long selectDistributionOrderByParamsCount(Map<String, Object> params) {
		return this.ordOrderDistributionDAO.selectByParamsCount(params);
	}

	@Override
	public List<OrdOrderDistribution> selectDistributionOrderApproveStatus(Long distributorId, String orderIds) {
		return ordOrderDistributionDAO.selectDistributionOrderApproveStatus(distributorId, orderIds);
	}

	@Override
	public boolean isSentFax(Long orderId) {
		return ordOrderDistributionDAO.isSentFax(orderId);
	}

	@Override
	public void updateRefundStatusByPartnerOrdid(String partnerOrderId,Long distributionOrderRefundId) {
		ordOrderDistributionDAO.updateRefundStatusByPartnerOrdid(partnerOrderId);
		if(distributionOrderRefundId != null){
			distributionOrderRefundDAO.updateRefundStatusByRefundId(distributionOrderRefundId);
		}
	}

	@Override
	public void updateOrdOrderDistributionResourceStatus(String orderIds, Long distributorId) {
		ordOrderDistributionDAO.updateOrdOrderDistributionResourceStatus(orderIds, distributorId);
	}
	
	public void setOrdOrderDistributionDAO(OrdOrderDistributionDAO ordOrderDistributionDAO) {
		this.ordOrderDistributionDAO = ordOrderDistributionDAO;
	}

	public void setDistributionOrderRefundDAO(
			DistributionOrderRefundDAO distributionOrderRefundDAO) {
		this.distributionOrderRefundDAO = distributionOrderRefundDAO;
	}
	
}
