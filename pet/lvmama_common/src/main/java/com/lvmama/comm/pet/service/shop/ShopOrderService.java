package com.lvmama.comm.pet.service.shop;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.shop.ShopOrder;
import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.vo.ShopOrderVO;
import com.lvmama.comm.pet.vo.ShopRemoteCallResult;

/**
 * 积分商城订单服务接口
 * @author ganyingwen
 *
 */
public interface ShopOrderService {
	/**
	 * 插入订单
	 * @param shopOrder 订单
	 */
	Long insert(final ShopOrder shopOrder);

	/**
	 * 根据给定参数查询订单
	 * @param parameters 查询参数
	 * @return 订单列表
	 */
	List<ShopOrder> queryShopOrder(Map<String, Object> parameters);

	/**
	 * 根据订单主键查询订单详情
	 * @param orderId 订单ID
	 * @return 订单
	 */
	ShopOrder queryShopOrderByKey(final Long orderId);

	/**
	 * 更新
	 * @param shopOrder 订单
	 * @return 更新条数
	 */
	int updata(final ShopOrder shopOrder);

	/**
	 * 订单总数
	 * @param parameters 查询参数
	 * @return 总数
	 */
	Long orderCount(final Map<String, Object> parameters);
	
	/**
	 * 检查用户是否可以兑换
	 * @param userId 用户标识
	 * @param productId 积分产品标识
	 * @param quantity 数量
	 * @return 返回结果
	 */
	ShopRemoteCallResult checkPointToChangeProduct(Long userId, Long productId, int quantity);
	
	/**
	 * 创建积分订单
	 * @param userId 用户标识
	 * @param productId 积分产品标识
	 * @param quantity 数量
	 * @param info 兑奖用户信息
	 * @return
	 */
	//ShopRemoteCallResult createShopOrder(Long userId, Long productId, int quantity, ShopUserInfo info);
	
	/**
	 * 积分抽奖
	 * @param userId 用户标识
	 * @param productId 积分产品标识
	 * @return
	 */
	String luckyDraw(Long userId, Long productId);

	List<ShopOrderVO> queryShopOrderVO(Map<String, Object> parameters);	
	
	long getPayPoint(final Long userId, final ShopProduct product, final int quantity);
	 
	void deleteShopProductZhuanti(final Long userId, final ShopProduct product, final int quantity);
	
}
