package com.lvmama.distribution.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.pet.vo.Page;
/**
 * 
 * @author lipengcheng
 *
 */
public class DistributorInfoDAO extends BaseIbatisDAO{
	
	/**
	 * 通过商户号查商户信息
	 * @param serialNo
	 * @return
	 */
	public DistributorInfo selectByDistributorCode(String distributorCode) {
		return (DistributorInfo) super.queryForObject("DISTRIBUTOR_INFO.selectByDistributorCode", distributorCode);
	}
	
	/**
	 * 通过商户Id查商户信息
	 * @param serialNo
	 * @return
	 */
	public DistributorInfo selectByDistributorId(Long distributorId) {
		return (DistributorInfo) super.queryForObject("DISTRIBUTOR_INFO.selectByDistributorId", distributorId);
	}
	
	/**
	 * 通过渠道查商户信息
	 * @param serialNo
	 * @return
	 */
	public DistributorInfo selectByDistributorChannel(String distributorChannel) {
		return (DistributorInfo) super.queryForObject("DISTRIBUTOR_INFO.selectByDistributorChannel", distributorChannel);
	}
	
	/**
	 * 通过产品ID和类别ID查询白名单分销商
	 * @param productId
	 * @param productBranchId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DistributorInfo> selectWhiteListByProductIdAndProductBranchId(Long productId,Long productBranchId){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("productId", productId);
		params.put("productBranchId", productBranchId);
		params.put("volid", "true");
		return (List<DistributorInfo>)super.queryForList("DISTRIBUTOR_INFO.selectByProductIdAndProductBranchId",params);
	}
	/**
	 * 通过产品ID和类别ID及类型（黑名单、白名单）查询分销商
	 * @param productId
	 * @param productBranchId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DistributorInfo> selectByProductIdAndProductBranchIdAndVolid(Long productBranchId, String volid){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("volid", volid);
		params.put("productBranchId", productBranchId);
		return (List<DistributorInfo>)super.queryForList("DISTRIBUTOR_INFO.selectByProductIdAndProductBranchId",params);
	}
	
	/**
	 * 取出全部的分销商
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DistributorInfo> getAllDistributors(){
		return (List<DistributorInfo>)super.queryForList("DISTRIBUTOR_INFO.getAllDistributors");
	}

	/**
	 * 插入一条数据
	 * @param ordOrderDitribution
	 */
	public void insert(DistributorInfo distributorInfo) {
		super.insert("DISTRIBUTOR_INFO.insert", distributorInfo);
	}
	/**
	 * 更新一条数据
	 * @param distributorInfo
	 */
	public void update(DistributorInfo distributorInfo){
		super.update("DISTRIBUTOR_INFO.update",distributorInfo);
	}
	
	/**
	 * 条件查询分销商列表
	 */
	@SuppressWarnings("unchecked")
	public List<DistributorInfo> selectDistributorByParams(Map<String, Object> parameterObject){
		return this.queryForList("DISTRIBUTOR_INFO.queryDistributorInfoPageListOrderBy", parameterObject);
	}
	
	/**
	 * 条件查询分销商总数
	 */
	public Integer queryDistributorInfoCount(Map<String, Object> parameterObject) {
		return (Integer) super.queryForObject("DISTRIBUTOR_INFO.queryDistributorInfoCount",parameterObject);
	}
	
	/**
	 * 根据订单号查商户信息
	 * @param orderId
	 * @return
	 */
	public DistributorInfo selectByOrderId(Long orderId) {
		return (DistributorInfo) super.queryForObject("DISTRIBUTOR_INFO.selectByOrderId", orderId);
	}
	
}
