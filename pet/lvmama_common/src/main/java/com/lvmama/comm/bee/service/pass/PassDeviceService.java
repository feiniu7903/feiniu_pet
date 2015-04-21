package com.lvmama.comm.bee.service.pass;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
/**
 * 设备编号管理服务接口
 * @author chenlinjun
 *
 */
public interface PassDeviceService {
	/**
	 * 添加设备编号信息
	 * 
	 * @param passDevice
	 * @return
	 */
	public Long addPaasDevice(PassDevice passDevice);

	/**
	 * 修改设备编号信息
	 * 
	 * @param passDevice
	 * @return
	 */
	public int updatePaasDevice(PassDevice passDevice);

	/**
	 * 通过设备编号和服务商编号查询设备编号信息
	 * 
	 * @param deviceNo
	 * @param providerId
	 * @return
	 */
	public PassDevice getPaasDeviceByDeviceNoAndProviderId(String deviceNo,
			Long providerId);
	/**
	 * 查询设备编号信息
	 * @param param
	 * @return
	 */
	public List<PassDevice> searchPassDevice(Map<String,Object> param);
	
	/**
	 * 查询设备编号数量
	 * @param param
	 * @return
	 */
	public int searchPassDeviceCount(Map<String,Object> param);
	/**
	 * 通过设备编号查询设备编号信息
	 * @param deviceId
	 * @return
	 */
	public PassDevice getPaasDeviceByDeviceId(Long deviceId);
	
	/**
	 * 通过设备编号删除
	 * 
	 * @param deviceId
	 * @return
	 */
	public int delDeviceByDeviceId(Long deviceId);

	/**
	 * 查询履行对象，当前条件只有设备编号
	 * @param params
	 * @return
	 */
	SupPerformTarget getPerformTargetByEBK(Map params);
	
	/**
	 * 根据供应商，查询所有的设备，当前需要主要由于LVMAMA自己申码的业务需求产生，
	 * 因为PUSH服务需要检测有哪些设备当前在线。
	 * @param providerId
	 * @return
	 */
	List<PassDevice> getDeviceListByProviderId(Long providerId);
}
