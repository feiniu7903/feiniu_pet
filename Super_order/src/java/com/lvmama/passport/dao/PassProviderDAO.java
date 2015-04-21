package com.lvmama.passport.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pass.PassProvider;

/**
 * @author ShiHui
 */
public class PassProviderDAO extends BaseIbatisDAO {
	
	public PassProvider selectByPerformTargetId(Long performTargetId) {
		List<PassProvider> list = super.queryForList(
				"PASS_PROVIDER.selectByPerformTargetId", performTargetId);
		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 按条件查询
	 * 
	 * @param 查询参数
	 */
	public List<PassProvider> selectByParams(Map<String, Object> params) {
		return super.queryForList(
				"PASS_PROVIDER.selectByParams", params);
	}

	/**
	 * 新增
	 * 
	 * @param PassProvider对象
	 */
	public void addPassProvider(PassProvider passProvider) {
		super.insert(
				"PASS_PROVIDER.insertPassProvider", passProvider);
	}

	/**
	 * 修改
	 */
	public void updatePassProvider(PassProvider passProvider) {
		super.update(
				"PASS_PROVIDER.updatePassProvider", passProvider);
	}

	/**
	 * 删除
	 */
	public void deletePassProvider(PassProvider passProvider) {
		super.delete(
				"PASS_PROVIDER.deletePassProvider", passProvider);
	}
	public List<PassProvider> selectByOrderId(Long orderId){
		return this.queryForList("PASS_PROVIDER.selectByOrderId",orderId);
	}
	
	public List<PassProvider> selectByDeviceNo(String deviceNo){
		return this.queryForList("PASS_PROVIDER.selectByDeviceNo",deviceNo);
	}
}
