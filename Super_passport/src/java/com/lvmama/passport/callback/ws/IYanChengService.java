package com.lvmama.passport.callback.ws;

/**
 * 淹城WebService服务接口
 * 
 * @author qiuguobin
 */
public interface IYanChengService {
	/**
	 * 对方在淹城系统履行完成后调用驴妈妈WebService方法
	 * @param djno 单据编号
	 * @param childQuantity 儿童数
	 * @param adultQuantity 成人数
	 */
	public int doAfterPerformance(String djno, int childQuantity, int adultQuantity);
}
