package com.lvmama.comm.bee.service.pass;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPort;
import com.lvmama.comm.bee.po.pass.PassPortCode;

/**
 * @author ShiHui
 */
public interface PassPortService {
	/**
	 * 按条件查询
	 * 
	 * @param查询参数
	 */
	List<PassPort> queryPassPorts(Map<String, Object> params);
	int queryPassPortCount(Map<String,Object> params); 

	/**
	 * 新增
	 * 
	 * @param PassPort对象
	 */
	void addPassPort(PassPort passPort);

	/**
	 * 修改
	 */
	void updatePassPort(PassPort passPort);

	/**
	 * 删除
	 */
	void deletePassPort(Long portId);
	/**
	 * 通过业务系统编号查询通关点信息
	 * @param outPortId
	 * @return
	 */
	public PassPort getPassPortByOutPortId(Long outPortId);
	/**
	 * 通过名称查询通关点信息
	 * @param params
	 * @return
	 */
	public List<PassPort> getPassportByName(Map<String, String> params);
	/**
	 * 通过编号查询通关点信息
	 * @param portId
	 * @return
	 */
	public PassPort getPassportByPortId(Long portId);
	/**
	 * 查询通关点信息
	 * 
	 * @param params
	 * @return
	 */
	public List<PassCode> selectPassCodeByParams(Map<String, Object> params);
	/**
	 *  查询通关点信息记录数
	 * @param params
	 * @return
	 */
	public Integer selectPassCodeRowCount(Map<String,Object> params);
	/**
	 * 通过通关点编号查询通关点关联信息
	 * 
	 * @param CodeId
	 * @return
	 */
	public List<PassPortCode> searchPassPortByCodeId(Long CodeId);
	/**
	 * 按条件查询PassCode表
	 * @param params
	 * @return
	 */
	public List<PassCode> queryPassCodeByParam(Map<String,Object> params);
}
