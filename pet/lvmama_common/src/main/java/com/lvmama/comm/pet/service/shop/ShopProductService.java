package com.lvmama.comm.pet.service.shop;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.shop.ShopProduct;
/**
 * 积分商城产品的逻辑接口层
 * @author Brian
 *
 */
public interface ShopProductService {
	int SUCCESS = 0;  //正常
	int ABSENT_NECESSARY_DATA = 11; //缺少必要数据
	int USER_CANNOT_BE_FOUND = 21;  //用户找不到
	int PRODUCT_CANNOT__BEFOUND = 22;  //产品找不到
	int PRODUCT_HAS_OFFLIINED = 31;  //产品已经下线
	int PRODUCT_LESS_STOCK = 32;  //产品缺少库存
	int USER_LESS_POINT = 33;  //缺少积分
	int PRODUCT_TOO_MUCH = 34;  //一次兑换数量太多
	/**
	 * 查询产品
	 * @param parameters 参数列表
	 * @return 产品列表
	 */
	List<ShopProduct> query(Map<String, Object> parameters);

	/**
	 * 根据主键查询产品
	 * @param productId 标识
	 * @return 产品
	 */
	ShopProduct queryByPk(Long productId);

	/**
	 * 查询产品数
	 * @param parameters 参数列表
	 * @return 产品数
	 */
	Long count(Map<String, Object> parameters);

	/**
	 * 保存/更新产品信息
	 * @param product 产品
	 * @param operatorId 操作人
	 * @return 产品标识
	 */
	Long save(ShopProduct product, String operatorId);

	/**
	 * 减少库存
	 * @param productId 产品标识
	 * @param quantity 数量
	 */
	void reduceStocks(Long productId, int quantity);
	/**
	 * 保存产品信息
	 * @param product 产品
	 * @param operatorId 操作人
	 * @return 产品标识
	 */
	Long insertOrUpdateShopProduct(ShopProduct product,String operatorId);
	/**
	 * 根据条件查询用户是否下过单
	 * @param parameters
	 * @return
	 */
	Long isOrderChecked(Map<String, Object> parameters);
}
