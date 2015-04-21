package com.lvmama.distribution.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.distribution.OrdOrderDistribution;
import com.lvmama.comm.pet.vo.Page;
/**
 * 
 * @author lipengcheng
 *
 */
public class OrdOrderDistributionDAO extends BaseIbatisDAO{
	
	/**
	 * 通过流水号和商户ID查
	 * @param serialNo
	 * @return
	 */
	public OrdOrderDistribution selectByOrderIdAndDistributionInfoId(Long orderId, Long distributionInfoId) {
		Map <String,Object> params= new HashMap<String,Object>();
		params.put("orderId", orderId);
		params.put("distributionInfoId", distributionInfoId);
		return (OrdOrderDistribution) super.queryForObject("ORD_ORDER_DISTRIBUTION.selectByOrderIdAndDistributionInfoId", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<OrdOrderDistribution> selectByParams(Map<String,Object> params){
		return super.queryForList("ORD_ORDER_DISTRIBUTION.selectByParams",params);
	}
	
	public Long selectByParamsCount(Map<String,Object> params){
		return (Long)super.queryForObject("ORD_ORDER_DISTRIBUTION.selectByParamsCount",params);
	}

	/**
	 * 插入一条数据
	 * @param ordOrderDitribution
	 */
	public void insert(OrdOrderDistribution ordOrderDitribution) {
		super.insert("ORD_ORDER_DISTRIBUTION.insert", ordOrderDitribution);
	}
	
	public Long getSerialNo() {
		return (Long)super.queryForObject("ORD_ORDER_DISTRIBUTION.getSerialNo");
	}
	
	/**
	 * 订单是否发过传真
	 * @param orderId
	 * @return
	 */
	public boolean isSentFax(Long orderId) {
		boolean flag = false;
		Long result = (Long) super.queryForObject("ORD_ORDER_DISTRIBUTION.isSentFax", orderId);
		if (orderId != null && result > 0) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 查询分销订单资源审核状态、支付等待时间
	 * @param distributorId
	 * @param orderIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OrdOrderDistribution>selectDistributionOrderApproveStatus(Long distributorId,String orderIds){
		Map <String,Object> params = new HashMap<String,Object>(); 
		params.put("distributorId", distributorId);
		params.put("orderIds", orderIds);
		return (List<OrdOrderDistribution>)super.queryForList("ORD_ORDER_DISTRIBUTION.selectDistributionOrderApproveStatus",params);
	}
	
	/**
	 * 更新分销订单的资源审核状态
	 * 此审核状态为分销商的订单审核状态
	 * @param orderIds
	 * @param distributorId
	 */
	public void updateOrdOrderDistributionResourceStatus(String orderIds,Long distributorId){
		Map <String,Object> params = new HashMap<String,Object>(); 
		params.put("distributorId", distributorId);
		params.put("orderIds", orderIds);
		super.update("ORD_ORDER_DISTRIBUTION.updateResourceStatus", params);
	}
	/**
	 * 更新分销订单的退款状态
	 * 退款成功状态改为true
	 * @param orderIds
	 */
	public void updateRefundStatusByPartnerOrdid(String partnerOrderId){
		Map <String,Object> params = new HashMap<String,Object>(); 
		params.put("orderId", partnerOrderId);
		super.update("ORD_ORDER_DISTRIBUTION.updateRefundStatusByPartnerOrdid", params);
	}
}
