package com.lvmama.comm.utils;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SynchronizedLock {
	protected static Log logger = LogFactory.getLog(SynchronizedLock.class);
  
	public static Object LOCK = new Object();
	
	public static Object LOCKMEMCACHED = new Object();
	
	private static Set<Object> set = new HashSet<Object>();
	
	public static boolean isOnDoing(Object obj) {
		synchronized(LOCK){
			if (set.contains(obj)) {
				return true;
			}else{
				set.add(obj);
				return false;
			}
		}
	}
	
	public static void release(Object obj) {
		synchronized(LOCK){
			set.remove(obj);
		}
	}
	
	public static boolean isOnDoingMemCached(String key) {
		synchronized(LOCKMEMCACHED){
			if(MemcachedUtil.getInstance().get(key) !=null){
				return true;			
			}else{
				//缓存设置有效时间为5分钟
				if(!MemcachedUtil.getInstance().set(key, 5*60, key)){
					logger.error("请检查MemCached服务器或相应的配置文件！");
					return isOnDoing(key);
				}
				return false;
			}
		}
	}
	
	public static void releaseMemCached(String key) {
		synchronized(LOCKMEMCACHED){
			if(!MemcachedUtil.getInstance().remove(key)){
				logger.error("请检查MemCached服务器或相应的配置文件！");
				release(key);
			}
		}
	}
	
	/*public static boolean isOnDoingMemCached(String key) {
		synchronized(LOCK){
			if (set.contains(key)) {
				return true;
			}else{
				set.add(key);
				return false;
			}
		}
	}
	
	public static void releaseMemCached(String key) {
		synchronized(LOCK){
			set.remove(key);
		}
	}*/
}
