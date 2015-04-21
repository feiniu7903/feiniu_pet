package com.lvmama.comm.bee.service.ckdevice;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ckdevice.CkDeviceInfo;

public interface CkDeviceService {

	/**
	 * 通过设备号查设备信息
	 * @param serialNo
	 * @return
	 */
	public abstract CkDeviceInfo selectByDeviceCode(String deviceCode);

	/**
	 * 通过设备Id查设备信息
	 * @param serialNo
	 * @return
	 */
	public abstract CkDeviceInfo selectByDeviceId(Long deviceId);

	/**
	 * 条件查询分销商列表
	 */
	public abstract List<CkDeviceInfo> selectDeviceByParams(Map<String, Object> parameterObject);

	/**
	 * 条件查询分销商总数
	 */
	public abstract Long queryDeviceInfoCount(Map<String, Object> parameterObject);

	void updateDevice(CkDeviceInfo deviceInfo);
	
	void addDevice(CkDeviceInfo deviceInfo);

}