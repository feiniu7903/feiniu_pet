/**
 * 
 */
package com.lvmama.train.service;

import com.lvmama.comm.vo.train.Rsp;

/**
 * @author yangbin
 *
 */
public interface TrainResponse {
	/**
	 * 解析Http请求后的返回内容
	 * @param response
	 * @throws RuntimeException
	 */
	void parse(String response)throws RuntimeException;
	/**
	 * @param rsp
	 * @throws RuntimeException
	 */
	void parseError(String rsp) throws RuntimeException;
	/**
	 * 获取返回内容类
	 * @return
	 * @throws RuntimeException
	 */
	Rsp getRsp();
	/**
	 * 设置是否解析成功
	 * @param isSuccess
	 * @throws RuntimeException
	 */
	void setSuccess(boolean isSuccess);
	
	/**
	 * 设置是否取消订单(比如创建请求供应商创建订单时，如果发生连接超时异常，不取消订单，持续用job发请求)
	 * */
	void setCancelOrder(boolean isCancelOrder);
}
