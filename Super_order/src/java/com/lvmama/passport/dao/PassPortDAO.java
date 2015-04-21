package com.lvmama.passport.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pass.PassPort;

/**
 * @author ShiHui
 */
@SuppressWarnings("unchecked")
public class PassPortDAO extends BaseIbatisDAO {
	/**
	 * 按条件查询
	 * 
	 * @param 查询参数
	 */

	public List<PassPort> selectByParams(Map<String, Object> params) {
		if(!params.containsKey("_startRow")){
			params.put("_startRow", 0);			
		}
		if(!params.containsKey("_endRow")){
			params.put("_endRow", 1000);
		}
		return super.queryForList(
				"PASS_PORT.PassPort_selectByParams", params);
	}

	/**
	 * 新增
	 * 
	 * @param PassPort对象
	 */
	public void addPassPort(PassPort passPort) {
		super.insert(
				"PASS_PORT.PassPort_insertPassPort", passPort);
	}

	/**
	 * 修改
	 */
	public void updatePassPort(PassPort passPort) {
		super.update(
				"PASS_PORT.PassPort_updatePassPort", passPort);
	}

	/**
	 * 删除
	 */
	public void deletePassPort(Long portId) {
		super.delete(
				"PASS_PORT.PassPort_deletePassPort", portId);
	}
	/**
	 * 通过业务系统编号查询通关点信息
	 * @param outPortId
	 * @return
	 */
	public PassPort getPassPortByOutPortId(Long outPortId){
		return (PassPort)super.queryForObject(
				"PASS_PORT.getPassPortByOutPortId", outPortId);
	}
	/**
	 * 通过名称查询通关点信息
	 * @param params
	 * @return
	 */
	public List<PassPort> getPassportByName(Map<String, String> params) {
		return super.queryForList(
				"PASS_PORT.getPassPortByName", params);
	}
	
	/**
	 * 通过编号查询通关点信息
	 * @param portId
	 * @return
	 */
	public PassPort getPassportByPortId(Long portId) {
		return (PassPort)super.queryForObject(
				"PASS_PORT.getPassPort", portId);
	}
	
	public int queryPassPortCount(Map<String,Object> params){
		return (Integer)super.queryForObject("PASS_PORT.queryPassPortCount",params);
	}
}
