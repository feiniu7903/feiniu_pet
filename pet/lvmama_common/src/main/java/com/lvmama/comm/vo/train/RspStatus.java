package com.lvmama.comm.vo.train;

public class RspStatus {
	/**
	 * 返回码，0-确认成功
	 */
	private int ret_code;
	/**
	 * 返回信息
	 */
	private String ret_msg;
	/**
	 * 默认构造函数
	 */
	public RspStatus(){}
	/**
	 * 带参数构造函数
	 * @param ret_code
	 * @param ret_msg
	 */
	public RspStatus(int ret_code, String ret_msg){
		this.ret_code = ret_code;
		this.ret_msg = ret_msg;
	}
	public int getRet_code() {
		return ret_code;
	}
	public void setRet_code(int ret_code) {
		this.ret_code = ret_code;
	}
	public String getRet_msg() {
		return ret_msg;
	}
	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}
}
