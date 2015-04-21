package com.lvmama.ckdevice.service;

import java.util.List;
import java.util.Map;

import com.lvmama.ckdevice.dao.DeviceInfoDAO;
import com.lvmama.comm.bee.po.ckdevice.CkDeviceInfo;
import com.lvmama.comm.bee.service.ckdevice.CkDeviceService;
/**
 * 立式设备
 * @author gaoxin
 *
 */
public class CkDeviceServiceImpl implements CkDeviceService{
	private DeviceInfoDAO deviceInfoDAO;

	/**
	 * 通过设备号查设备信息
	 * @param serialNo
	 * @return
	 */
	public CkDeviceInfo selectByDeviceCode(String deviceCode){
		return deviceInfoDAO.selectByDeviceCode(deviceCode);
	}
	/**
	 * 通过设备Id查设备信息
	 * @param serialNo
	 * @return
	 */
	public CkDeviceInfo selectByDeviceId(Long deviceId) {
		return deviceInfoDAO.selectByDeviceId(deviceId);
	}
	
	/**
	 * 条件查询分销商列表
	 */
	public List<CkDeviceInfo> selectDeviceByParams(Map<String, Object> parameterObject){
		return deviceInfoDAO.selectDeviceByParams(parameterObject);
	}
	
	/**
	 * 
	 * @param deviceInfo
	 */
	@Override
	public void updateDevice(CkDeviceInfo deviceInfo){
		deviceInfoDAO.update(deviceInfo);
	}
	
	@Override
	public void addDevice(CkDeviceInfo deviceInfo){
		deviceInfoDAO.insert(deviceInfo);
	}
	
	/**
	 * 条件查询分销商总数
	 */
	public Long queryDeviceInfoCount(Map<String, Object> parameterObject) {
		return deviceInfoDAO.queryDeviceInfoCount(parameterObject);
	}
	public DeviceInfoDAO getDeviceInfoDAO() {
		return deviceInfoDAO;
	}
	public void setDeviceInfoDAO(DeviceInfoDAO deviceInfoDAO) {
		this.deviceInfoDAO = deviceInfoDAO;
	}
	
}
