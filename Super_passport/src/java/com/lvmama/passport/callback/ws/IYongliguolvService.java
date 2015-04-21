package com.lvmama.passport.callback.ws;

/**
 * 永利国旅回调驴妈妈接口
 * 
 * @author lipengcheng
 * @date 2011-11-3
 */
public interface IYongliguolvService {

	/**
	 * 对方在永利国旅系统履行完成后调用驴妈妈WebService方法
	 * @param djno 单据编号
	 * @param childQuantity 儿童数
	 * @param adultQuantity 成人数
	 */
	public int doAfterPerformance(String orderId, int childQuantity, int adultQuantity);
	
}
