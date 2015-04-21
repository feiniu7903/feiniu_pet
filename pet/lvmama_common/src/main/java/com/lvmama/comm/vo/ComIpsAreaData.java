/**
 * 
 */
package com.lvmama.comm.vo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.pub.ComIps;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComIpsService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.IPMap;

/**
 * 
 * IP库所有数据
 * 
 * @author 张振华
 * 
 */
public class ComIpsAreaData implements Serializable {
	private static Object LOCK = new Object();
	private static final Log log = LogFactory.getLog(ComIpsAreaData.class);
	private static final long serialVersionUID = 5958631532070231335L;
	private static Map<Long, String> placeNamesMap = new HashMap<Long, String>();
	private TreeMap<IPEntry, ComIps> comIpsCache = new  TreeMap<IPEntry, ComIps>();
	
	private static ComIpsAreaData instance;
	
	private ComIpsAreaData(){
	};
	public static ComIpsAreaData getInstance(){
		if(instance == null ){
			instance = new ComIpsAreaData();
			instance.init();
		}
		return instance;
	}
	private void init(){
		synchronized (LOCK) {
			if(comIpsCache.size() == 0){
				ComIpsService ipsService = (ComIpsService)SpringBeanProxy.getBean("comIpsRemoteBean");
				log.info("===begin init comips===");
				long begin = System.currentTimeMillis();
				List<String> provinceIds = new ArrayList<String>();
				provinceIds.add("310000");
				provinceIds.add("320000");
				provinceIds.add("330000");
				provinceIds.add("110000");
				provinceIds.add("440000");
				List<ComIps>	listComIpsArea  = ipsService.selectComIpsByProvinceIds(provinceIds);
				long end = System.currentTimeMillis();
				log.info("init comips success. times(ms)"+(end-begin));
				for (ComIps ci : listComIpsArea) {
					comIpsCache.put(new IPEntry(ci.getIpStart(), ci.getIpEnd()), ci);
				}
			}
		}
	}
	
	/**
	 * 从淘宝IP库获取所在地
	 * @param ipAddress
	 * @return
	 */
	public ComIps queryFromTaobaoIp(String ipAddress){
		ComIps ci = null;
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(3000);
		httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		String content = null;
		try {
			GetMethod getMethod = new GetMethod("http://ip.taobao.com/service/getIpInfo.php?ip="+ipAddress);
			int statusCode =httpClient.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				InputStream inputStream = getMethod.getResponseBodyAsStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
				BufferedReader bufferReader = new BufferedReader(inputStreamReader);
				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = bufferReader.readLine()) != null) {
					sb.append(line.trim());
				}
				content = sb.toString();
				bufferReader.close();
				inputStreamReader.close();
				inputStream.close();
				JSONObject jsonObject = JSONObject.fromObject(content); 
				
				Integer code = (Integer) jsonObject.get("code");
		        if(code != null && code == 0){
		        	JSONObject data =  (JSONObject) jsonObject.get("data");
		        	String city = (String) data.get("city");
		        	String region = (String) data.get("region");
		        	if(StringUtils.isNotEmpty(city)){
		        		String regex = "(.*)市$";
		        		Pattern p = Pattern.compile(regex);
		        		Matcher m=p.matcher(city); 
		        		if(m.find()){
		        			city = m.group(1);
		        		}
		        		ComIpsService ipsService = (ComIpsService) SpringBeanProxy.getBean("comIpsRemoteBean");
		        		ci= ipsService.selectComIpsByCityProvince(region, city, ipAddress);
		        	}
		        }else{
		        	log.warn("get ip fail, taobao code is: "+ipAddress);
		        }
			}else{
				log.warn("get taobao ip error http_status: "+statusCode +" ip:"+ipAddress);
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return ci;
	}
	
	final int MAX_ENTRIES = 10000;
	Map<String,ComIps> unknownIpscache = new LinkedHashMap<String,ComIps>(MAX_ENTRIES + 1, .75F, true) {
		// This method is called just after a new entry has been added
		public boolean removeEldestEntry(Map.Entry eldest) {
			return size() > MAX_ENTRIES;
		}
	};
	
	public ComIps selectComIpsAreaByIp(String ip){
		if (unknownIpscache.containsKey(ip)) {
			return unknownIpscache.get(ip);
		}
		
		Long ipLong = IPMap.stringToLong(ip);
		ComIps cip = null;
		cip = comIpsCache.get(new IPEntry(ipLong));
		if (cip == null) {
			ComIpsService ipsService = (ComIpsService) SpringBeanProxy.getBean("comIpsRemoteBean");
			cip = ipsService.query(ip);
			if(cip == null ){//IP库中不存在当前IP
				cip = this.queryFromTaobaoIp(ip);
				
				log.info("Unkonwn IP: " + ip);
				unknownIpscache.put(ip, cip);
			}else{
				comIpsCache.put(new IPEntry(cip.getIpStart(), cip.getIpEnd()), cip);
			}
		}
		
		return cip;
	}
	@Deprecated
	public static ComIps selectComIpsAreaByIp(Long ip) {
		return ComIpsAreaData.getInstance().selectComIpsAreaByIp(IPMap.longToString(ip));
	}
	
	public static String getPlaceName(Long placeId) {
	    String placeName = placeNamesMap.get(placeId);
	    if (placeName == null) {
	        synchronized (ComIpsAreaData.class) {
	        	PlaceService placeService = (PlaceService)SpringBeanProxy.getBean("placeService");
    	        Place Place = placeService.queryPlaceByPlaceId(placeId);
    	        placeName = Place.getName();
    	        placeNamesMap.put(placeId, placeName);
	        }
	    }
	    return placeName;
	}
}

