package com.lvmama.distribution.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.distribution.DistributorTuanInfo;
/**
 * 团购分销商
 * @author gaoxin
 *
 */
@Repository
public class DistributorTuanInfoDao extends BaseIbatisDAO{
	
	/**
	 * 通过商户号查商户信息
	 * @param serialNo
	 * @return
	 */
	public DistributorTuanInfo selectByDistributorCode(String distributorCode) {
		return (DistributorTuanInfo) super.queryForObject("DISTRIBUTOR_TUAN_INFO.getByDistributorCode", distributorCode);
	}
	
	/**
	 * 通过商户Id查商户信息
	 * @param serialNo
	 * @return
	 */
	public DistributorTuanInfo selectByDistributorId(Long distributorId) {
		return (DistributorTuanInfo) super.queryForObject("DISTRIBUTOR_TUAN_INFO.selectByDistributorId", distributorId);
	}
	
	/**
	 * 通过渠道类型查商户信息
	 * @param serialNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DistributorTuanInfo> selectByDistributorChannelType(String channelType) {
		return (List<DistributorTuanInfo>) super.queryForList("DISTRIBUTOR_TUAN_INFO.queryByType", channelType);
	}


	/**
	 * 插入一条数据
	 * @param ordOrderDitribution
	 */
	public void insert(DistributorTuanInfo distributorInfo) {
		super.insert("DISTRIBUTOR_TUAN_INFO.insert", distributorInfo);
	}
	/**
	 * 更新一条数据
	 * @param distributorInfo
	 */
	public void update(DistributorTuanInfo distributorInfo){
		super.update("DISTRIBUTOR_TUAN_INFO.update",distributorInfo);
	}
	
	/**
	 * 条件查询分销商列表
	 */
	@SuppressWarnings("unchecked")
	public List<DistributorTuanInfo> selectDistributorByParams(Map<String, Object> parameterObject){
		return this.queryForList("DISTRIBUTOR_TUAN_INFO.findPage", parameterObject);
	}
	
	/**
	 * 条件查询分销商总数
	 */
	public Long queryDistributorInfoCount(Map<String, Object> parameterObject) {
		return (Long) super.queryForObject("DISTRIBUTOR_TUAN_INFO.findPageCount",parameterObject);
	}

	/**
	 * 查询所有分销商
	 */
	@SuppressWarnings("unchecked")
	public List<DistributorTuanInfo> getAllDistributors() {
		return (List<DistributorTuanInfo>)queryForList("DISTRIBUTOR_TUAN_INFO.getAllDistributors");
	}
	
}
