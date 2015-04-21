package com.lvmama.comm.pet.service.pub;

import java.util.List;

import com.lvmama.comm.pet.po.pub.ComIps;

public interface ComIpsService {

	/**
	 * 根据ip地址返回所在地
	 * @param ipAddress  ip地址
	 * @return
	 */
	ComIps query(String ipAddress);
	
	/**
	 * 根据省份,城市名称查询ip信息
	 * @param province 省份名称
	 * @param city 城市
	 * @param ipAddress ip地址
	 */
	ComIps selectComIpsByCityProvince(String province,String city,String ipAddress);
	/**
	 * 查询所有的
	 * IP库数据量过大,慎用全部查询逻辑
	 * @return
	 */
	@Deprecated
	List<ComIps> selectComIpsAll();
	/**
	 * 根据省份ID查询IP地址
	 * @param provinceIds
	 * @return
	 */
	public  List<ComIps> selectComIpsByProvinceIds(List<String> provinceIds);
	
}
