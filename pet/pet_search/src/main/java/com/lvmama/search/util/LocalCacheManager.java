package com.lvmama.search.util;

import java.util.HashMap;

/**
 * 本地缓存管理
 * @author yanggan
 *
 */
public class LocalCacheManager {  
    private static HashMap<String,LocalCache> cacheMap = new HashMap<String,LocalCache>();  
  
    private LocalCacheManager() {  
        super();  
    }  
    /**
     * 获取缓存数据
     * 如果超时则返回空
     * @param key 缓存key
     * @return
     */
    public synchronized static Object get(String key) {  
        LocalCache cache = getCacheInfo(key);
        return cache == null ? null : cache.isExpired() ? null : cache.getValue();
    }  
  
    /**
     * 判断是否存在一个缓存  
     * @param key 缓存key
     * @return
     */
    public synchronized static boolean hasCache(String key) {  
        return cacheMap.containsKey(key);  
    }  
  
    /**
     * 清除所有缓存  
     */
    public synchronized static void clearAll() {  
        cacheMap.clear();  
    }  
  
    /**
     * 清除指定的缓存  
     * @param key 缓存key
     */
    public synchronized static void clear(String key) {  
        cacheMap.remove(key);  
    }  
    /**
     * 
     * @param key
     * @param obj
     */
    public synchronized static void put(String key, Object obj) {  
        putCacheInfo(key, obj, 2*60*60*1000);//默认保存两小时
    }  
    /**
     * 
     * @param key
     * @param obj
     */
    public synchronized static void put(String key, Object obj,long timeOut) {  
        putCacheInfo(key, obj, timeOut);
    }
    /**
     * 获取缓存数据
     * @param key 缓存KEY
     * @return 缓存数据
     */
    public static LocalCache getCacheInfo(String key) {  
        if (hasCache(key)) {  
            LocalCache cache = (LocalCache) cacheMap.get(key);  
            if (cacheExpired(cache)) { //调用判断是否终止方法  
                cache.setExpired(true);  
            }  
            return cache;  
        }else  
            return null;  
    }  
    /**
     * 把数据放入缓存中
     * @param key 缓存KEY
     * @param obj 缓存内容
     * @param dt 过期时间（毫秒）
     * @param expired 是否已过期
     */
    public static void putCacheInfo(String key, Object obj, long dt,boolean expired) {  
        LocalCache cache = new LocalCache();  
        cache.setKey(key);  
        cache.setTimeOut(dt + System.currentTimeMillis()); //设置多久后更新缓存  
        cache.setValue(obj);  
        cache.setExpired(expired); //缓存默认载入时，终止状态为FALSE  
        cacheMap.put(key, cache);  
    }  
    /**
     * 把对象放入缓存中
     * @param key 缓存KEY
     * @param obj 缓存内容
     * @param dt 过期时间（毫秒）
     */
    public static void putCacheInfo(String key,Object obj,long dt){  
        LocalCache cache = new LocalCache();  
        cache.setKey(key);  
        if(dt <= 0l){
        	cache.setTimeOut(0);
        }else{
        	cache.setTimeOut(dt+System.currentTimeMillis());
        }
        cache.setValue(obj);  
        cache.setExpired(false);  
        cacheMap.put(key,cache);  
    }  
  
    /**
     * 判断缓存是否终止  
     * @param cache 缓存
     * @return
     */
    public static boolean cacheExpired(LocalCache cache) {  
        if (null == cache) { //传入的缓存不存在  
            return false;  
        }  
        long nowDt = System.currentTimeMillis(); //系统当前的毫秒数  
        long cacheDt = cache.getTimeOut(); //缓存内的过期毫秒数  
        if (cacheDt <= 0 ||cacheDt > nowDt) { //过期时间小于等于零时,或者过期时间大于当前时间时，则为FALSE  
            return false;  
        } else { //大于过期时间 即过期  
            return true;  
        }  
    }  
  
    /**
     * 获取缓存的大小
     * @return
     */
    public static int getCacheSize() {  
        return cacheMap.size();  
    }  
} 