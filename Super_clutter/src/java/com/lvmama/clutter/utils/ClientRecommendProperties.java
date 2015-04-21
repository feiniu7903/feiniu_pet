package com.lvmama.clutter.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.StringUtil;

public class ClientRecommendProperties {
	private static final Log log = LogFactory.getLog(ClientRecommendProperties.class);
	private static Properties prop = null;


	static {
		synchronized (ClientRecommendProperties.class) {
			if (prop == null) {
				prop = new Properties();
				try {
					InputStream is = ClientRecommendProperties.class.getResourceAsStream("/recommend_block.properties");
					prop.load(is);
				} catch (IOException e) {
					log.error("read pepsi.properties error", e);
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 获取blockId.
	 * @param type
	 * @return
	 */
	public static String getBlockId(String type){
		if (StringUtil.isEmptyString(type)) {
			log.error("Key is empty. You'd better give an existing key.");
			return null;
		}
		return prop.getProperty("blockId_"+type);
	}
	
	/**
	 * 获取blockId.
	 * @param type
	 * @return
	 */
	public static String getClientRecommendBlockId(){
		return prop.getProperty("clientRecommendBlockId");
	}
	
	/**
	 * get value by key.
	 * 
	 * @param key
	 * @return key value of selected key
	 */
	public static String getProperty(String key) {
		if (StringUtil.isEmptyString(key)) {
			log.error("Key is empty. You'd better give an existing key.");
			return null;
		}
		return prop.getProperty(key);
	}
	
	
	public static List<Long> getRecommendGroupBlockId(){
		List<Long> blockIds = new ArrayList<Long>();
		String[] keys = {"sh","bj","cd","gz","hz","nj","sz","cq"};
		for (String string : keys) {
			String p = getProperty("group."+string+".blockId");
			String[] datas = p.split(",");
			blockIds.add(Long.valueOf(datas[0]));
		}
		
		return blockIds;
	}
	
	
	public static List<Map<String,Object>> getRecommendGroupBlockInfo(){
		List<Map<String,Object>> blockInfos = new ArrayList<Map<String,Object>>();
		String[] keys = {"sh","bj","cd","gz","hz","nj","sz","cq"};
		for (String string : keys) {
			String p = getProperty("group."+string+".blockId");
			String[] datas = p.split(",");
			Map<String,Object>  objMap = new HashMap<String, Object>();
			objMap.put("blockId", datas[0]);
			objMap.put("cityName", datas[1]);
			blockInfos.add(objMap);
		}
		return blockInfos;
	}
	

}
