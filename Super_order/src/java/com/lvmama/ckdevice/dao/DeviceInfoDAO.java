package com.lvmama.ckdevice.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ckdevice.CkDeviceInfo;
/**
 * @author gaoxin
 *
 */
@Repository
public class DeviceInfoDAO extends BaseIbatisDAO{
	
	/**
	 * 通过设备号查设备信息
	 * @param serialNo
	 * @return
	 */
	public CkDeviceInfo selectByDeviceCode(String deviceCode) {
		return (CkDeviceInfo) super.queryForObject("CK_DEVICE_INFO.getByDeviceCode", deviceCode);
	}
	
	/**
	 * 通过设备Id查设备信息
	 * @param serialNo
	 * @return
	 */
	public CkDeviceInfo selectByDeviceId(Long deviceId) {
		return (CkDeviceInfo) super.queryForObject("CK_DEVICE_INFO.selectByDeviceId", deviceId);
	}
	
	/**
	 * 通过渠道类型查设备信息
	 * @param serialNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CkDeviceInfo> selectByDeviceChannelType(String channelType) {
		return (List<CkDeviceInfo>) super.queryForList("CK_DEVICE_INFO.queryByType", channelType);
	}


	/**
	 * 插入一条数据
	 * @param ordOrderDitribution
	 */
	public void insert(CkDeviceInfo deviceInfo) {
		super.insert("CK_DEVICE_INFO.insert", deviceInfo);
	}
	/**
	 * 更新一条数据
	 * @param deviceInfo
	 */
	public void update(CkDeviceInfo deviceInfo){
		super.update("CK_DEVICE_INFO.update",deviceInfo);
	}
	
	/**
	 * 条件查询分销商列表
	 */
	@SuppressWarnings("unchecked")
	public List<CkDeviceInfo> selectDeviceByParams(Map<String, Object> parameterObject){
		return this.queryForList("CK_DEVICE_INFO.findPage", parameterObject);
	}
	
	/**
	 * 条件查询分销商总数
	 */
	public Long queryDeviceInfoCount(Map<String, Object> parameterObject) {
		return (Long) super.queryForObject("CK_DEVICE_INFO.findPageCount",parameterObject);
	}

}
