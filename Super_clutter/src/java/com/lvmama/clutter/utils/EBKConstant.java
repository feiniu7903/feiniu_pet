package com.lvmama.clutter.utils;


public class EBKConstant {

	public static enum MSG_LEVEL {
		/**
		 * EBK操作成功，并有数据返回
		 */
		SUCCESS("0"),
		/**
		 * 业务逻辑异常
		 */
		LOGIC_ERROR("1"),
		/**
		 * 系统异常
		 */
		EXCEPTION("-1"),
		/**
		 * 参数传输错误
		 */
		PARAM_ERROR("-2"),
		/**
		 * 客户端签名和服务端签名不匹配
		 */
		SIGN_ERROR("-3");
		
		private String value;
		MSG_LEVEL(String value){
			this.value=value;
		}
		public String getName(){
			return this.name();
		}
		public String getValue(){
			return this.value;
		}
	} 
}
