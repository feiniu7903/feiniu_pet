package com.lvmama.passport.callback.ws;

/**
 * 第三方回调驴妈妈接口
 * @author lipengcheng
 *
 */
public interface IPassportPerformCallbackService {
	
	/**
	 * 通关后第三方通知驴妈妈更改状态接口
	 * @param orderId
	 * @return
	 */
	public String performCallback(String orderId,String date,String adultQuantity , String childQuantity) ;	

}
