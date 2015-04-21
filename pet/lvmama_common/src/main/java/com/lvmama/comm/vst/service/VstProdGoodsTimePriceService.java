package com.lvmama.comm.vst.service;

import java.util.Date;

import com.lvmama.comm.utils.json.ResultHandle;

/**
 * 商品（采购类别）的库存价格接口
 * @author ranlongfei 2013-12-20
 * @version
 */
public interface VstProdGoodsTimePriceService {

	/**
	 * 下单的库存扣减
	 * 
	 * @author: ranlongfei 2013-12-20 下午6:16:48
	 * @param orderId 订单id
	 * @param goodsId 采购产品id
	 * @param stock 大于0
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	ResultHandle updateStockByCreateOrder(Long orderId, Long goodsId, Long stock, Date start, Date end);
	/**
	 * 取消单的返回
	 * 
	 * @author: ranlongfei 2013-12-20 下午6:16:48
	 * @param orderId 订单id
	 * @param goodsId 采购产品id
	 * @param stock 大于0
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	ResultHandle updateStockByCancelOrder(Long orderId, Long goodsId, Long stock, Date start, Date end);
	
}