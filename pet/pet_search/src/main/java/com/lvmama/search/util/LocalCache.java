package com.lvmama.search.util;

/**
 * 本地缓存信息
 * @author yanggan
 *
 */
public class LocalCache {  
	//缓存ID  
    private String key;
    //缓存数据  
    private Object value;
    //超时时间（MS)
    private long timeOut;
    //是否终止  
    private boolean expired; 
    
    public LocalCache() {  
            super();  
    }  

    public LocalCache(String key, Object value, long timeOut, boolean expired) {  
            this.key = key;  
            this.value = value;  
            this.timeOut = timeOut;  
            this.expired = expired;  
    }  

    public String getKey() {  
            return key;  
    }  

    public long getTimeOut() {  
            return timeOut;  
    }  

    public Object getValue() {  
            return value;  
    }  

    public void setKey(String string) {  
            key = string;  
    }  

    public void setTimeOut(long l) {  
            timeOut = l;  
    }  

    public void setValue(Object object) {  
            value = object;  
    }  

    public boolean isExpired() {  
            return expired;  
    }  

    public void setExpired(boolean b) {  
            expired = b;  
    }  
}  
