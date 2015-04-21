package com.lvmama.distribution.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.distribution.DistributorIp;

public class DistributorIpDAO extends BaseIbatisDAO {
	/**
	 * 通过商户ID查Ip
	 * @param serialNo
	 * @return Ip列表
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectByDistributorInfoId(Long distributorInfoId) {
		return (List<String>) super.queryForList("DISTRIBUTOR_IP.selectByDistributorInfoId", distributorInfoId);
	}
	
	/**
	 * 通过商户ID查Ip信息
	 * @param serialNo
	 * @return IP信息列表
	 */
	@SuppressWarnings("unchecked")
	public List<DistributorIp> selectByDistributorId(Long distributorInfoId) {
		return (List<DistributorIp>) super.queryForList("DISTRIBUTOR_IP.selectByDistributorId", distributorInfoId);
	}

	/**
	 * 插入一条数据
	 * @param ordOrderDitribution
	 */
	public void insert(DistributorIp distributorIp) {
		super.insert("DISTRIBUTOR_IP.insert", distributorIp);
	}
	
	/**
	 * 通过主键更新更新IP
	 */
	public void updateByDistributorIpId(DistributorIp distributorIp) {
		super.update("DISTRIBUTOR_IP.updateByDistributorIpId", distributorIp);
	}
	
	/**
	 * 通过主键删除IP
	 */
	public void deleteByDistributorIpId(Long distributorIpId) {
		super.delete("DISTRIBUTOR_IP.deleteByDistributorIpId", distributorIpId);
	}
	
	/**
	 * 通过主键查询ip
	 */
	public DistributorIp getDistributorIpByDistributorIpId(Long distributorIpId){
		 return (DistributorIp)super.queryForObject("DISTRIBUTOR_IP.getDistributorIpBydistributorIpId",distributorIpId);
		
	}
}
