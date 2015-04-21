package com.lvmama.jinjiang.vo.order;


public class OrderMessage {

	private String code;
	private String message;
	
	public OrderMessage() {
	}
	
	public OrderMessage(MessageCode code) {
		this.code = code.getM_code();
		this.message = code.message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	public enum MessageCode{
		
		M_0001("0001","第三方订单号为Null"),
		M_0002("0002","订单不存在"),
		M_0003("0003","取消订单异常"),
		M_0000("0000","成功");
		
		private String m_code;
		private String message;
		
		MessageCode(String m_code,String message){
			this.m_code = m_code;
			this.message = message;
		}
		
		public String getM_code() {
			return m_code;
		}
		public void setM_code(String m_code) {
			this.m_code = m_code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
		
		
	}
}
