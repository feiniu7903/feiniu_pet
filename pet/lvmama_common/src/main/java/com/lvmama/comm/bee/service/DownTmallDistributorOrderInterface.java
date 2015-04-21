package com.lvmama.comm.bee.service;



/***
 * 查询库里(ord_tmall_distributor_map)订单状态为create的订单,将其状态设为processing调用淘宝详情接口进行后台下单
 * 
 * @author YuanXueBo
 *
 */
public interface DownTmallDistributorOrderInterface {
	void backDownDistributorOrder(Long fenxiaoId) throws Exception;

}
