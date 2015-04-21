package com.lvmama.pet.pub.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.pub.ComIps;
import com.lvmama.comm.pet.service.pub.ComIpsService;
import com.lvmama.comm.utils.IPMap;
import com.lvmama.pet.pub.dao.ComIpsDAO;

class ComIpsServiceImpl implements ComIpsService {
	private static final Log log = LogFactory.getLog(ComIpsServiceImpl.class);
	/**
	 * IP地址格式段位置, 24、16、8、0
	 */
	private static final int[] IP_SECTIONS = {24, 16, 8, 4};
	@Autowired
	private ComIpsDAO comIpsDAO;
	
	/**
	 * 根据省份,城市名称查询ip信息
	 * @param province 省份名称
	 * @param city 城市
	 */
	public ComIps selectComIpsByCityProvince(String province,String city,String ipAddress){
		ComIps ci= comIpsDAO.selectComIpsByCity(province,city);
		if(ci != null ){
			ci.setIpStart(IPMap.stringToLong(ipAddress));
    		ci.setIpEnd(IPMap.stringToLong(ipAddress));
		}
		return ci;
	}
	
	public  List<ComIps> selectComIpsByProvinceIds(List<String> provinceIds){
		return this.comIpsDAO.selectComIpsByProvinceIds(provinceIds);
	}
	
	@Override
	public ComIps query(final String ipAddress) {
		if (StringUtils.isBlank(ipAddress)) {
			return null;
		}
		return  comIpsDAO.selectComIpsByIp(getLong(ipAddress));		
	}
	
	
	private long getLong(final String ipAddress) {
		String[] arr = ipAddress.split("\\.");
		if (arr.length != 4) {
			return 0;
		}
		return (Long.parseLong(arr[0]) << IP_SECTIONS[0]) + (Long.parseLong(arr[1]) << IP_SECTIONS[1])
				+ (Long.parseLong(arr[2]) << IP_SECTIONS[2]) + Long.parseLong(arr[3]);
	}

	@Override
	public List<ComIps> selectComIpsAll() {
		return comIpsDAO.selectComIpsAll();
	}

}
