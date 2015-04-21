package com.lvmama.comm.bee.service.pass;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.pass.PassProvider;

/**
 * @author ShiHui
 */
public interface PassProviderService {
	/**
	 * 按条件查询
	 * 
	 * @param 查询参数
	 */

	List<PassProvider> queryPassProviders(Map<String, Object> params);

	/**
	 * 新增PassProvider对象
	 * 
	 * @param
	 */
	void addPassProvider(PassProvider passProvider);

	/**
	 * 修改
	 */
	void updatePassProvider(PassProvider passProvider);

	/**
	 * 删除
	 */
	void deletePassProvider(PassProvider passProvider);
	/**
	 * 通过ORDERID取得PassProvider数据
	 * @param orderId
	 * @return
	 */
	List<PassProvider> selectPassProviderByOrderId(Long orderId);
	
	/**
	 * 根据设备编号取得设备所属的服务商列表
	 * @param deviceNo
	 * @return
	 */
	List<PassProvider> selectByDeviceNo(String deviceNo);
}
