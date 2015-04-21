package com.lvmama.comm.vo.train;


/**
 * 通知接口中用于回复的VO
 * @author XuSemon
 * @date 2013-8-6
 */
public class BaseVo extends RspVo{
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
	public BaseVo(){}
	/**
	 * 带参数构造函数
	 * @param ret_code
	 * @param ret_msg
	 */
	public BaseVo(int ret_code, String ret_msg){
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
