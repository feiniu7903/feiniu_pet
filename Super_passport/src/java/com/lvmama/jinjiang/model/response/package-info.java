/**
 * 
 */
/**
 * @author yangbin
 *
 */
package com.lvmama.jinjiang.model.response;




enum ERRORCODE{
	ERR_0000("0000"),//成功
	ERR_0001("0001"),//服务器出现不可预期的异常!
	ERR_0002("0002"),//数据校验不通过！
	ERR_2000("2000"),//获取团信息必选字段不能为空！
	ERR_2001("2001"),//获取团信息团代码不能为空！
	ERR_3000("3000");//订单请求必选字段不能为空!
	
	private String code;
	ERRORCODE(String code) {
		this.code = code;
	}
	public String getCode(){
		return this.code;
	}
	
}