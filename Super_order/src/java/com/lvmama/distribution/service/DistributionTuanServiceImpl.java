package com.lvmama.distribution.service;

import java.util.List;
import java.util.Map;

import com.lvmama.com.dao.ComCodesetDAO;
import com.lvmama.comm.bee.po.distribution.DistributionTuanCouponBatch;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.bee.po.distribution.DistributorTuanInfo;
import com.lvmama.comm.bee.service.distribution.DistributionTuanService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.distribution.dao.DistributionTuanCouponBatchDAO;
import com.lvmama.distribution.dao.DistributionTuanCouponDAO;
import com.lvmama.distribution.dao.DistributorTuanInfoDao;

/**
 * 分销团购预约平台
 * 团购预约劵Service
 * @author gaoxin
 *
 */
public class DistributionTuanServiceImpl implements DistributionTuanService{
	private DistributorTuanInfoDao distributorTuanInfoDao;
	private DistributionTuanCouponBatchDAO distributionTuanCouponBatchDAO;
	private DistributionTuanCouponDAO distributionTuanCouponDAO;
	private ComCodesetDAO comCodesetDAO;
	/**
	 * 根据劵号查询预约劵以及产品订单渠道
	 * @param tuanCouponCode
	 * @return
	 */
	public DistributionTuanCouponBatch getTuanCouponByCode(String tuanCouponCode){
		DistributionTuanCouponBatch batch = distributionTuanCouponBatchDAO.getTuanCouponByCode(tuanCouponCode);
		batch.setDistributionTuanCoupon(distributionTuanCouponDAO.queryByCode(batch.getTuanCode()));
		return batch;
	}
	
	/**
	 * 根据分销商id获取渠道
	 * @param id
	 * @return
	 */
	public DistributorTuanInfo getDistributorTuanById(Long id){
		return distributorTuanInfoDao.selectByDistributorId(id);
	}
	
	/**
	 * 通过商户号查商户信息
	 * @param serialNo
	 * @return
	 */
	public DistributorTuanInfo selectByDistributorCode(String distributorCode){
		return distributorTuanInfoDao.selectByDistributorCode(distributorCode);
	}
	
	
	public void insert(DistributorTuanInfo distributorInfo){
		distributorTuanInfoDao.insert(distributorInfo);
	}
	public void update(DistributorTuanInfo distributorInfo){
		distributorTuanInfoDao.update(distributorInfo);
	}
	/**
	 * 条件查询分销商列表
	 */
	public List<DistributorTuanInfo> selectDistributorByParams(Map<String, Object> parameterObject){
		return distributorTuanInfoDao.selectDistributorByParams(parameterObject);
	}
	
	/**
	 * 条件查询分销商总数
	 */
	public Long queryDistributorInfoCount(Map<String, Object> parameterObject){
		return distributorTuanInfoDao.queryDistributorInfoCount(parameterObject);
	}
	
	/**
	 * 通过渠道类型查商户信息
	 * @param serialNo
	 * @return
	 */
	public List<DistributorTuanInfo> selectByDistributorChannelType(String channelType){
		return distributorTuanInfoDao.selectByDistributorChannelType(channelType);
	}

	public void setDistributorTuanInfoDao(
			DistributorTuanInfoDao distributorTuanInfoDao) {
		this.distributorTuanInfoDao = distributorTuanInfoDao;
	}
	
	/**
	 * 新建支付网关
	 * @param code
	 * @param name
	 */
	public void insertPamentGetWay(String code,String name){
		CodeItem codeItem = new CodeItem(code, name);
		codeItem.setAttr01("PAYMENT_GATEWAY");
		codeItem.setAttr02("Y");
		codeItem.setAttr03("20");
		comCodesetDAO.insertPamentGetWay(codeItem);
	}

	public void setComCodesetDAO(ComCodesetDAO comCodesetDAO) {
		this.comCodesetDAO = comCodesetDAO;
	}

	public void setDistributionTuanCouponBatchDAO(
			DistributionTuanCouponBatchDAO distributionTuanCouponBatchDAO) {
		this.distributionTuanCouponBatchDAO = distributionTuanCouponBatchDAO;
	}

	public void setDistributionTuanCouponDAO(
			DistributionTuanCouponDAO distributionTuanCouponDAO) {
		this.distributionTuanCouponDAO = distributionTuanCouponDAO;
	}

	/**
	 * 取出全部的分销商
	 */
	public List<DistributorTuanInfo> getAllDistributors() {
		return distributorTuanInfoDao.getAllDistributors();
	}

	
	
}
