package com.lvmama.passport.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;

/**
 * 设备编号管理
 * 
 * @author chenlinjun
 * 
 */
@SuppressWarnings("unchecked")
public class PassDeviceDAO extends BaseIbatisDAO {
	/**
	 * 添加设备编号信息
	 * 
	 * @param passDevice
	 * @return
	 */
	public Long addPaasDevice(PassDevice passDevice) {
		return (Long) super.insert("PASS_DEVICE.insertSelective", passDevice);
	}

	/**
	 * 修改设备编号信息
	 * 
	 * @param passDevice
	 * @return
	 */
	public int updatePaasDevice(PassDevice passDevice) {
		return super.update("PASS_DEVICE.updateByPrimaryKeySelective", passDevice);
	}

	/**
	 * 通过设备编号和服务商编号查询设备编号信息
	 * 
	 * @param deviceNo
	 * @param providerId
	 * @return
	 */
	public PassDevice getPaasDeviceByDeviceNoAndProviderId(String deviceNo, Long providerId) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("deviceNo", deviceNo);
		data.put("providerId", providerId);
		return (PassDevice) super.queryForObject(
				"PASS_DEVICE.getPaasDeviceByDeviceNoAndProviderId", data);
	}

	/**
	 * 通过设备编号查询设备编号信息
	 * 
	 * @param deviceId
	 * @return
	 */
	public PassDevice getPaasDeviceByDeviceId(Long deviceId) {
		return (PassDevice) super.queryForObject("PASS_DEVICE.getPaasDeviceByDeviceId",
				deviceId);
	}

	/**
	 * 通过设备编号删除
	 * 
	 * @param deviceId
	 * @return
	 */
	public int delDeviceByDeviceId(Long deviceId) {
		return super.delete("PASS_DEVICE.delDevice",
				deviceId);
	}
	/**
	 * 查询设备编号信息
	 * 
	 * @param param
	 * @return
	 */
	public List<PassDevice> searchPassDevice(Map<String, Object> param) {
		if(!param.containsKey("_startRow")){
			param.put("_startRow", 0L);
		}
		if(!param.containsKey("_endRow")){
			param.put("_endRow", 100000L);
		}
		return super.queryForList("PASS_DEVICE.searchPassDevice", param);
	}
	
	public int searchPassDeviceCount(Map<String,Object> param){
		return (Integer)super.queryForObject("PASS_DEVICE.searchPassDeviceCount",param);
	}

	/**
	 * 通过服务商和通关编号查询设备信息
	 * 
	 * @param codeId
	 * @param providerId
	 * @return
	 */
	public List<PassDevice> selectPassDeviceByProviderIdAndCodeId(long codeId, long providerId) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("codeId", codeId);
		data.put("providerId", providerId);
		return super.queryForList("PASS_DEVICE.selectPassDeviceByProviderIdAndCodeId", data);
	}
	
	public SupPerformTarget getPerformTargetListByEBK(Map params) {
		return (SupPerformTarget)super.queryForObject("PASS_DEVICE.getPerformTargetListByEBK", params);
	}
	
	public List<PassDevice> getPassDeviceListByCode(String addCode,String currentUdid) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("code", addCode);
		if(StringUtils.isNotEmpty(currentUdid)){
			params.put("excludeDevices", currentUdid);
		}
		return super.queryForList("PASS_DEVICE.getDeviceListByCode", params);
	}
	
	public List<PassDevice> getPassDeviceListByOrderId(Long orderId) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("orderId", orderId);
		return super.queryForList("PASS_DEVICE.getDeviceListByOrderId", params);
	}
	
	public List<PassDevice> getDeviceListByProviderId(Long providerId) {
		return super.queryForList("PASS_DEVICE.getDeviceListByProviderId", providerId);
	}
	
}
