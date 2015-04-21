package com.lvmama.push.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.StringUtil;





public class ConstantPush{
	private static final Log log = LogFactory.getLog(ConstantPush.class);
	private static Properties prop = null;
	
	static {
		synchronized (ConstantPush.class) {
			if (prop == null) {
				prop = new Properties();
				try {
					InputStream is = ConstantPush.class.getResourceAsStream("/const.properties");
					prop.load(is);
				} catch (IOException e) {
					log.error("read pepsi.properties error", e);
					e.printStackTrace();
				}
			}
		}
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
	
	public static String getPushLogDir(){
		return getProperty("push.log.dir");
	}
	
	
	public static enum PUSH_MSG_TYPE {
		REG("注册到服务"),
		PULL("拉取数据"),
		DELET_HISTORY_ORDER("清除客户端历史数据"),
		UPLOAD_LOG("上传日志"),
		RESTART_DEVICE("重启设备"),
		NOTIFICATION("弹通知栏"),
		SYNC_DATAS("同步数据");
		
		private String cnName;
		PUSH_MSG_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PUSH_MSG_TYPE item:PUSH_MSG_TYPE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return this.name();
		}
	}
	
	public static enum CLIENT_SESSION_STATUS {
		
		ONLINE("在线"),
		BUSY("读写繁忙"),
		OFFLINE("离线"),
		FREE("读写空闲");
		
		private String cnName;
		
		CLIENT_SESSION_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		
		public static String getCnName(String code){
			for(CLIENT_SESSION_STATUS item:CLIENT_SESSION_STATUS.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return this.name();
		}
	}
}
