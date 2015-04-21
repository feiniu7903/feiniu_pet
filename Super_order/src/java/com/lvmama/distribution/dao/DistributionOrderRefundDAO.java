package com.lvmama.distribution.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.distribution.DistributionOrderRefund;
/**
 * 分销订单退款--DAO
 * @author lipengcheng
 *
 */
public class DistributionOrderRefundDAO extends BaseIbatisDAO{
	
	public DistributionOrderRefund selectDistributionOrderRefundById(Long refundId){
		return (DistributionOrderRefund)super.queryForObject("DISTRIBUTOR_ORDER_REFUND.selectDistributionOrderRefundById",refundId);
	}
	
	@SuppressWarnings("unchecked")
	public List<DistributionOrderRefund> queryDistributionOrderRefundByParams(Map<String,Object> params){
		return (List<DistributionOrderRefund>)super.queryForList("DISTRIBUTOR_ORDER_REFUND.queryDistributionOrderRefundByParams", params);
	}
	public Long queryDistributionOrderRefundByParamsCount(Map<String,Object> params){
		return (Long)super.queryForObject("DISTRIBUTOR_ORDER_REFUND.queryDistributionOrderRefundByParamsCount", params);
	}
	public String selectRefundStatusByOrderRefundId(Long distributionOrderRefundId){
		return (String)super.queryForObject("DISTRIBUTOR_ORDER_REFUND.selectRefundByOrderRefundId", distributionOrderRefundId);
	}
	public void updateRefundStatusByRefundId(Long distributionOrderRefundId){
		super.update("DISTRIBUTOR_ORDER_REFUND.updateRefundStatusByRefundId",distributionOrderRefundId);
	}
	
	public void insert(DistributionOrderRefund refund){
		super.insert("DISTRIBUTOR_ORDER_REFUND.insert", refund);
	}
	
	public void update(DistributionOrderRefund refund){
		super.update("DISTRIBUTOR_ORDER_REFUND.update", refund);
	}

}
