package com.ejingtong.common;

import com.ejingtong.model.User;
public class Constans {

	//用户信息保存文件
	public static final String FILE_NAME_USER = "com.ejingdong.user";
	
	//设置信息保存文件
	public static final String FILE_NAME_SETTING = "com.ejingdong.setting";
	
	public static User userInfo;
	
	public static String IMEI = "";
	
	public static String IMSI = "";
	
	public static String sdPath = "";
	
	public static String logPath = "/ejingtong/log/";
	
	public static final String key = "6srGIiQLO8KSMF8IsFwgOaNgjMvetmBZAoPtSUxKWdoveBl1b3VEgUvJqDnz0uWvaO8KWMkxPi8sokEpjEi0fmFinYQv5l69kyMblFsrOL4z42rVRVCrzqal1KGgiRNZ";
	
	public static final String KEY_INTENT_ORDER = "order";
	public static final String KEY_INTENT_ORDER_ID = "order_id";
	public static final String KEY_PULL_TYPE = "key_pull_type";
	public static final String KEY_ORDER_STATU = "key_order_statu";
	public static final String KEY_ADDR_LOGIC_VALUE = "addr_logic_value";
	public static final String KEY_ADDR_PUSH_SERVER_VALUE = "addr_push_server_value";
	public static final String KEY_ADDR_PUSH_CALLBACK_VALUE = "addr_push_callback_value";
	public static final String KEY_PORT_PUSH_SERVER_VALUE = "addr_port_push_server_value";
	
	public static final int PAGE_SIZE = 10; //默认的
	public static final String FILE_URL_DOWNLOAD_UPDATE = "ebook/download/update/";
	//action.用于通知有新的订单
	public static final String ACTION_NEW_ORDER = "com.ejingtong.action.new.order";

	public static final String UPDATE_URL = "http://www.lvmama.com/client/ebk/config.json";
	
	//业务拉数据地址
	//public static String ADDR_LOGIC = "http://10.2.1.30:8080/clutter/";
	//public static String ADDR_LOGIC = "http://180.169.51.83:8035/clutter/";
	public static String ADDR_LOGIC = "http://192.168.0.248/clutter/";
	
	
	//推送服务回调地址
	//public static String ADDR_PUSH_BACK = "http://180.169.51.83:8035/push/";
	public static String ADDR_PUSH_BACK = "http://192.168.0.248:8035/push/";
	
	//推送服务器地址
	//public static String ADDR_PUSH_SERVER = "180.169.51.83";
	
	 public static String ADDR_PUSH_SERVER = "192.168.0.248";
	//public static String ADDR_PUSH_SERVER = "10.2.1.30";
	//推送服务器端口
	public static int PORT_PUSH_SERVER = 1225;
	
	public static String CONNECT_LOCK = "CONNECT_LOCK";
	
	
	public static enum CLIENT_COMMAND_TYPE {
		
		REG("注册"),
		SYNC_DATA("同步第一次推送失败的数据"),
		SYNC_DATA_NEW_DEVICE("新设备同步数据");
		
		private String cnName;
		
		CLIENT_COMMAND_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		
		public static String getCnName(String code){
			for(CLIENT_COMMAND_TYPE item:CLIENT_COMMAND_TYPE.values()){
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
