package com.lvmama.comm.bee.service.prod;

import com.lvmama.comm.bee.po.prod.TimePrice;

public interface ProductService {

	/**
	 * 修改采购产品的时间价格表的<b>代理</b>方法
	 * <br>修改价格表，发送Jms消息
	 * <br><b style="color:red">返回null则操作成功，否则返回错误提示</b>
	 * <br><b>advancedOpt:</b>
	 * <br><b>op1:</b>修改价格
	 * <br><b>op2:</b>修改库存
	 * <br><b>op3:</b>修改全部属性
	 * <br><b>op4:</b>修改自动清库存小时数
	 * @author: ranlongfei 2013-1-7 上午9:52:39
	 * @param advancedOpt 
	 * @param timePriceBean
	 * @param operatorName
	 * @return
	 */
	String saveOrUpdateMetaTimePrice(String advancedOpt, TimePrice timePriceBean, String operatorName);
}
