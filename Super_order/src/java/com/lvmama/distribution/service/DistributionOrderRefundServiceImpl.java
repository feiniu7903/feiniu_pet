package com.lvmama.distribution.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionOrderRefund;
import com.lvmama.comm.bee.service.distribution.DistributionOrderRefundService;
import com.lvmama.distribution.dao.DistributionOrderRefundDAO;
/**
 * 分销定单退款服务实现
 * @author yanzhirong
 */
public class DistributionOrderRefundServiceImpl implements DistributionOrderRefundService{
	
	private DistributionOrderRefundDAO distributionOrderRefundDAO;
	
	/**
	 * 条件查询分销订单退款历史列表
	 */
	public List<DistributionOrderRefund> queryDistributionOrderRefundByParams(Map<String, Object> params) {
		return distributionOrderRefundDAO.queryDistributionOrderRefundByParams(params);
	}
	
	public Long queryDistributionOrderRefundByParamsCount(Map<String, Object> params) {
		return distributionOrderRefundDAO.queryDistributionOrderRefundByParamsCount(params);
	}
	
	public String selectRefundStatusByOrderRefundId(Long distributionOrderRefundId) {
		return this.distributionOrderRefundDAO.selectRefundStatusByOrderRefundId(distributionOrderRefundId);
	}
	
	public DistributionOrderRefund selectDistributionOrderRefundById(Long refundId){
		return this.distributionOrderRefundDAO.selectDistributionOrderRefundById(refundId);
	}

	/**
	 * 新增分销退款历史 
	 */
	public void insertDistributionRefund(DistributionOrderRefund refund) {
		distributionOrderRefundDAO.insert(refund);
	}

	public void setDistributionOrderRefundDAO(
			DistributionOrderRefundDAO distributionOrderRefundDAO) {
		this.distributionOrderRefundDAO = distributionOrderRefundDAO;
	}
	
}
