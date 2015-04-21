package com.lvmama.pet.pub.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComIps;

public class ComIpsDAO extends BaseIbatisDAO {
	/**
	 * 根据IP获得这个IP相关的信息
	 */
	public ComIps selectComIpsByIp(Long ip) {
		return (ComIps)super.queryForObject("COM_IPS.selectComIpsByIp", ip);
	}
	
	/**
	 * 根据省份名称,城市名称获取ComIps对象(不包含ip_start,ip_end)
	 * @param provinceName
	 * @param cityName
	 * @return
	 */
	public ComIps selectComIpsByCity(String provinceName,String cityName){
		Map<String,String> map = new HashMap<String,String>();
		map.put("provinceName",provinceName);
		map.put("cityName",cityName);
		return (ComIps)super.queryForObject("COM_IPS.selectComIpsByCityProvince", map);
	}
	/**
	 * 根据省份ID查询IP
	 * @return
	 */
	public  List<ComIps> selectComIpsByProvinceIds(List<String> provinceIds){
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		map.put("provinceIds",provinceIds);
		return queryForListForReport("COM_IPS.selectComIpsByProvinceIds",map);
	}
	/**
	 * 查询所有IP相关的信息
	 */
	@SuppressWarnings("unchecked")
	public List<ComIps> selectComIpsAll() {
		return execute(new SqlMapClientCallback<List>() {
			public List doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				return executor.queryForList("COM_IPS.selectComIpsAll", null);
			}
		});
	}

	public void saveComIps(ComIps ComIps) {
		 super.insert("COM_IPS.saveComIps", ComIps);
	}

	public void deleteComIps(ComIps ComIps) {
		super.delete("COM_IPS.deleteComIps", ComIps);
	}
}