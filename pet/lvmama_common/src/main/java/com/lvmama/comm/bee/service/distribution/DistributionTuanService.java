package com.lvmama.comm.bee.service.distribution;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionTuanCouponBatch;
import com.lvmama.comm.bee.po.distribution.DistributorTuanInfo;

/**
 * 分销团购预约平台
 * 团购预约劵Service
 * @author gaoxin
 *
 */
public interface DistributionTuanService {
	/**
	 * 根据劵号查询预约劵以及产品订单渠道
	 * @param tuanCouponCode
	 * @return
	 */
	public DistributionTuanCouponBatch getTuanCouponByCode(String tuanCouponCode);
	
	/**
	 * 根据分销商id获取
	 * @param id
	 * @return
	 */
	public DistributorTuanInfo getDistributorTuanById(Long id);
	
	/**
	 * 通过商户号查商户信息
	 * @param serialNo
	 * @return
	 */
	public DistributorTuanInfo selectByDistributorCode(String distributorCode);
	
	
	public void insert(DistributorTuanInfo distributorInfo);
	public void update(DistributorTuanInfo distributorInfo);
	/**
	 * 条件查询分销商列表
	 */
	public List<DistributorTuanInfo> selectDistributorByParams(Map<String, Object> parameterObject);
	
	/**
	 * 条件查询分销商总数
	 */
	public Long queryDistributorInfoCount(Map<String, Object> parameterObject);
	
	/**
	 * 通过渠道类型查商户信息
	 * @param serialNo
	 * @return
	 */
	public List<DistributorTuanInfo> selectByDistributorChannelType(String channelType);
	
	/**
	 * 新建支付网关
	 * @param code
	 * @param name
	 */
	public void insertPamentGetWay(String code,String name);
	
	/**
	 * 查询所有分销商
	 * @return
	 */
	public List<DistributorTuanInfo> getAllDistributors();

}
