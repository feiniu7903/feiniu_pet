/**
 * 
 */
package com.lvmama.comm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.vo.Constant;


/**
 * @author liuyi
 *
 */
public class ActivityUtil {
	
	private static final Log LOG = LogFactory.getLog(ActivityUtil.class);
	private static ActivityUtil instance;
	private static Object LOCK = new Object();
	private Map<String, Date> activityBeginTimeMap;
	private Map<String, Date> activityEndTimeMap;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static List<String> ipList=new ArrayList<String>();
	
	static{
		String[] innerIp={"180.169.51.82","180.169.51.83","180.169.51.84","180.169.51.85","180.169.51.86","180.169.51.87","180.169.51.88","180.169.51.89","180.169.51.90","180.169.51.91","180.169.51.92","180.169.51.93","180.169.51.94","222.66.131.98","10.2.1.76"};
		ipList=Arrays.asList(innerIp);
		
	}
	public static ActivityUtil getInstance() {
		if(instance == null){
			synchronized(LOCK) {
				if (instance==null) {
					instance=new ActivityUtil();
				}
			}
		}
		return instance;
	}
	
	
	private ActivityUtil(){
		try{
			activityBeginTimeMap = new HashMap<String, Date>();
			activityEndTimeMap = new HashMap<String, Date>();
		}catch(Exception e){
			LOG.error(e, e);
		}
	}
	
	public boolean checkActivityIsValid(String key, Date activityDate){
		Date activityBeginTime = null;
		Date activityEndTime = null;
		Date currentDate = activityDate;
		String value;
		if(activityBeginTimeMap.get(key) != null){
			activityBeginTime = activityBeginTimeMap.get(key);
		}else{
			try {
				value = Constant.getInstance().getProperty(key+".activity.begin.time");
				if(StringUtils.isNotEmpty(value)){
					activityBeginTime = dateFormat.parse(value);
					activityBeginTimeMap.put(key, activityBeginTime);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(activityEndTimeMap.get(key) != null){
			activityEndTime = activityEndTimeMap.get(key);
		}else{
			try {
				value = Constant.getInstance().getProperty(key+".activity.end.time");
				if(StringUtils.isNotEmpty(value)){
					activityEndTime = dateFormat.parse(value);
					activityEndTimeMap.put(key, activityEndTime);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(activityBeginTime == null || activityEndTime == null){
			return false;
		}
		if(currentDate.compareTo(activityBeginTime) >=0 && currentDate.compareTo(activityEndTime) <= 0){
			return true;
		}else{
			return false;
		}
	}
	
	
	public boolean checkActivityIsValid(String key){
		return checkActivityIsValid(key, new Date());
	}
	
	public static boolean isCompanyInnerIp(String ip){
		return ipList.contains(ip);
	}

}
