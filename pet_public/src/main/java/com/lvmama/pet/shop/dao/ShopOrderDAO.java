package com.lvmama.pet.shop.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.shop.ShopOrder;
import com.lvmama.comm.pet.vo.ShopOrderVO;

/**
 * 积分商城订单数据库操作接口实现
 * @author ganyingwen
 *
 */
public class ShopOrderDAO extends BaseIbatisDAO {

	/**
	 * 插入订单
	 * @param shopOrder 订单
	 * @return 订单号
	 */
	public Long insert(final ShopOrder shopOrder) {
		return (Long) super.insert("SHOP_ORDER.insert", shopOrder);
	}

	/**
	 * 根据给定参数查询订单
	 * @param parameters 查询参数
	 * @return 订单列表
	 */
	@SuppressWarnings("unchecked")
	public List<ShopOrder> queryShopOrder(final Map<String, Object> parameters) {
		return super.queryForList("SHOP_ORDER.queryShopOrder", parameters);
	}

	/**
	 * 根据给定参数查询订单
	 * @param parameters 查询参数
	 * @return 订单列表
	 */
	@SuppressWarnings("unchecked")
	public List<ShopOrderVO> queryShopOrderVO(final Map<String, Object> parameters) {
		return super.queryForList("SHOP_ORDER.queryShopOrder", parameters);
	}
	
	/**
	 * 根据订单主键查询订单详情
	 * @param orderId 订单ID
	 * @return 订单
	 */
	public ShopOrder queryShopOrderByKey(final Long orderId) {
		return (ShopOrder) super.
				queryForObject("SHOP_ORDER.queryShopOrderByKey", orderId);
	}

	/**
	 * 更新
	 * @param shopOrder 订单
	 * @return 更新条数
	 */
	public int updata(final ShopOrder shopOrder) {
		return super.update("SHOP_ORDER.update", shopOrder);
	}

	/**
	 * 订单总数
	 * @param parameters 查询参数
	 * @return 总数
	 */
	public Long orderCount(final Map<String, Object> parameters) {
		return (Long) super.queryForObject("SHOP_ORDER.orderCount", parameters);
	}

}
