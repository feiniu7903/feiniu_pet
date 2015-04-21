package com.lvmama.comm.bee.service;

import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.taobao.api.response.TradeFullinfoGetResponse;


/***
 * 查询库里(ord_tmall_map)订单状态为create的订单,将其状态设为processing调用淘宝详情接口进行后台下单
 * @author dingming
 *
 */
public interface DownTmallOrderInterface {
	void backDownOrder(String  tmallOrderNo,TradeFullinfoGetResponse response) throws Exception;
	void downEticketOrder(Map<String,String> data,TradeFullinfoGetResponse response) throws Exception;

}
