/**
 * 
 */
package com.lvmama.comm.utils.homePage;

 import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


/**
 * @author nixianjun
 * 
 */
public final class ManageMemMapUtils {

	private static ManageMemMapUtils uniqueInstance = null;
	private static Object LOCK = new Object();
	public final static int ONE_HOUR=3600;
	public final static int TWO_HOUR=7200;

	/**
	 * 单例
	 */
	private ManageMemMapUtils() {
 	}
	
	public static ManageMemMapUtils getInstance() {
		if (uniqueInstance == null) {
			synchronized (LOCK) {
				uniqueInstance = new ManageMemMapUtils();
			}
		}
		return uniqueInstance;
	}
	private  Map map=new HashMap();

	public void set(String key, int seconds, Object obj) {
		
		if(map.get(key)==null){
			map.put(key, obj);
			map.put(key+"_time", getDateAfter(seconds));
		}else if(null!=map.get(key+"_time")){
			long lv=((Date) map.get(key+"_time")).getTime();
			if(lv<System.currentTimeMillis()){
				putData(key,seconds,obj);
			}
		}
	}
	
	public  Object get(String key){
		if(StringUtils.isBlank(key)){
			return null;
		}
		if(StringUtils.isBlank(key+"_time")){
			return null;
		}else if(null!=map.get(key+"_time")){
			long lv=((Date) map.get(key+"_time")).getTime();
			if(lv<System.currentTimeMillis()){
				removeData(key);
				return null;
			}
		}
		return map.get(key);
		
	}
	private void removeData(String key){
		map.remove(key);
		map.remove(key+"_time");
	}
	
	private  void putData(String key, int seconds, Object obj){
		
		map.put(key, obj);
		map.put(key+"_time",getDateAfter(seconds));
	}
	
	 /**
     * 获得当前开始活参数秒的时间日期
    * @Title: getDateAfter
    * @Description:
    * @param
    * @return Date    返回类型
    * @throws
     */
    private Date getDateAfter(int second) {
        Calendar cal = Calendar.getInstance();
 		cal.add(Calendar.SECOND, second);
 		return cal.getTime();
 	}

}
