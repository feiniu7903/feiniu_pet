package com.lvmama.comm.bee.service;

import com.taobao.api.response.TradeFullinfoGetResponse;


/***
 * 查询库里(ord_tmall_map)订单状态为create的订单,将其状态设为processing调用淘宝详情接口进行后台下单
 * @author dingming
 *
 */
public interface DownTmallOrderService {
	void backDownOrder(String  tmallOrderNo,TradeFullinfoGetResponse response) throws Exception;

}
