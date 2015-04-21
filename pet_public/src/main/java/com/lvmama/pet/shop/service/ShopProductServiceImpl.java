package com.lvmama.pet.shop.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.po.shop.ShopProductCondition;
import com.lvmama.comm.pet.po.shop.ShopProductCoupon;
import com.lvmama.comm.pet.service.shop.ShopLogService;
import com.lvmama.comm.pet.service.shop.ShopProductService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.shop.dao.ShopProductDAO;
import com.lvmama.pet.user.dao.UserPointLogDAO;

/**
 * 积分商城产品的逻辑实现层
 * @author Brian
 *
 */
class ShopProductServiceImpl implements ShopProductService {
	/**
	 * 积分商城产品数据库操作层
	 */
	private ShopProductDAO shopProductDAO;
	/**
	 * 积分商城产品日志逻辑层
	 */
	private ShopLogService shopLogService;
	/**
	 * 用户积分逻辑层
	 */
	private UserPointLogDAO userPointLogDAO;

	@Override
	public List<ShopProduct> query(final Map<String, Object> parameters) {
		return shopProductDAO.query(parameters);
	}

	@Override
	public ShopProduct queryByPk(final Long productId) {
		ShopProduct shopProduct=shopProductDAO.queryDetailByPk(productId);
		if(shopProduct!=null){
			List<ShopProductCondition> shopProductConditions = shopProductDAO.getShopProductConditionByShopProductId(productId);
			shopProduct.setShopProductConditions(shopProductConditions);
		}
		return shopProduct;
	}

	@Override
	public 	Long count(final Map<String, Object> parameters) {
		return shopProductDAO.count(parameters);
	}

	@Override
	public 	Long save(final ShopProduct product, final String operatorId) {
		Long productId = null;
		if (null == product.getProductId()) {
			if (Constant.SHOP_PRODUCT_TYPE.COUPON.name().equals(product.getProductType())
					&& product instanceof ShopProductCoupon) {
				productId = shopProductDAO.insert((ShopProductCoupon) product);
			} else {
				productId = shopProductDAO.insert(product);
			}
			shopLogService.insert(null, productId, "SHOP_PRODUCT", "PRODUCT_CREATE", operatorId);
		} else {
			String content = getModifiedContent(product);
			if (Constant.SHOP_PRODUCT_TYPE.COUPON.name().equals(product.getProductType())
					&& product instanceof ShopProductCoupon) {
				productId = shopProductDAO.update((ShopProductCoupon) product);
			} else {
				productId = shopProductDAO.update(product);
			}
			shopLogService.insert(content, productId, "SHOP_PRODUCT", "PRODUCT_UPDATE", operatorId);

		}
		return productId;
	}

	@Override
	public void reduceStocks(final Long productId, final int quantity) {
		if (null == productId) {
			return;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productId", productId);
		parameters.put("quantity", quantity);
		shopProductDAO.reduceStocks(parameters);
	}

	/**
	 * 获取变更内容
	 * @param product 更新过的产品
	 * @return 更新内容
	 */
	private String getModifiedContent(final ShopProduct shopProduct) {
		StringBuilder sb = new StringBuilder();
		ShopProduct p = shopProductDAO.queryDetailByPk(shopProduct.getProductId());
		if (null == p.getContent()) {
			p.setContent("");
		}
		if (null == shopProduct.getContent()) {
			shopProduct.setContent("");
		}
		if (p.getProductCode() != null && !p.getProductCode().equals(shopProduct.getProductCode())) {
			sb.append("产品编号:").append(p.getProductCode()).append("->").append(shopProduct.getProductCode()).append(";");
		}
		if (p.getProductName() != null && !p.getProductName().equals(shopProduct.getProductName())) {
			sb.append("产品名称:").append(p.getProductName()).append("->").append(shopProduct.getProductName()).append(";");
		}
		if (p.getPointChange() != null && p.getPointChange().longValue() != shopProduct.getPointChange().longValue()) {
			sb.append("兑换积分:").append(p.getPointChange()).append("->").append(shopProduct.getPointChange()).append(";");
		}
		if (p.getMarketPrice() != null && p.getMarketPrice() .longValue() != shopProduct.getMarketPrice().intValue()) {
			sb.append("市场价:").append(p.getMarketPrice()).append("->").append(shopProduct.getMarketPrice()).append(";");
		}
		if (p.getStocks() != null && shopProduct.getStocks()!=null && p.getStocks() .longValue() != shopProduct.getStocks().intValue()) {
			sb.append("库存:").append(p.getStocks()).append("->").append(shopProduct.getStocks()).append(";");
		}
		if (p.getIsValid() != null && !p.getIsValid().equals(shopProduct.getIsValid())) {
			sb.append("上下线:").append(p.getIsValid()).append("->").append(shopProduct.getIsValid()).append(";");
		}
		if (p.getProductType() != null && !p.getProductType().equals(shopProduct.getProductType())) {
			sb.append("产品类型:").append(p.getProductType()).append("->").append(shopProduct.getProductType()).append(";");
		}
		if (p.getIsHotProduct() != null && !p.getIsHotProduct().equals(shopProduct.getIsHotProduct())) {
			sb.append("是否热门商品:").append(p.getIsHotProduct()).append("->").append(shopProduct.getIsHotProduct()).append(";");
		}
		if (p.getIsRecommend() != null && !p.getIsRecommend().equals(shopProduct.getIsRecommend())) {
			sb.append("是否推荐:").append(p.getIsRecommend()).append("->").append(shopProduct.getIsRecommend()).append(";");
		}
		if(p.getChangeType()!=null && !p.getChangeType().equals(shopProduct.getChangeType())) {
			sb.append("兑换类别:").append(p.getChChangeType()).append("->").append(shopProduct.getChChangeType()).append(";");
		}
		if(p.getChangeType()!=null && p.getChangeType().equals(shopProduct.getChangeType()) && "RAFFLE".equals(p.getChangeType())){
			if(p.getWinningRate()!=null && shopProduct.getWinningRate()!=null && p.getWinningRate() != shopProduct.getWinningRate()){
				sb.append("抽奖概率:").append(p.getWinningRateForString()).append("->").append(shopProduct.getWinningRateForString()).append(";");
			}
		}
		if(p.getIsValidate()!=null && !p.getIsValidate().equals(shopProduct.getIsValidate())) {
			sb.append("是否需要手机验证:").append(p.getIsValidate()).append("->").append(shopProduct.getIsValidate()).append(";");
		}
		if (p.getPictures() != null && !p.getPictures().equals(shopProduct.getPictures())) {
			sb.append("图片:").append(p.getPictures()).append("->").append(shopProduct.getPictures()).append(";");
		}
		if (p.getContent() != null && !p.getContent().equals(shopProduct.getContent())) {
			sb.append("内容:").append(p.getContent()).append("->").append(shopProduct.getContent()).append(";");
		}
		if (shopProduct instanceof ShopProductCoupon && p instanceof  ShopProductCoupon) {
			if (((ShopProductCoupon) shopProduct).getCouponId().longValue() != ((ShopProductCoupon) p).getCouponId().longValue()) {
				sb.append("优惠券编号:").append(((ShopProductCoupon) p).getCouponId())
					.append("->").append(((ShopProductCoupon) shopProduct).getCouponId()).append(";");
			}
		}
		if (shopProduct instanceof ShopProductCoupon && !(p instanceof  ShopProductCoupon)) {
			sb.append("优惠券编号:").append(((ShopProductCoupon) shopProduct).getCouponId());
		}
		return sb.toString();
	}

	public void setShopProductDAO(ShopProductDAO shopProductDAO) {
		this.shopProductDAO = shopProductDAO;
	}

	public void setShopLogService(ShopLogService shopLogService) {
		this.shopLogService = shopLogService;
	}

	@Override
	public Long insertOrUpdateShopProduct(ShopProduct product,String operatorId) {
		Long productId = null;
		if (null == product.getProductId()) {
			if (Constant.SHOP_PRODUCT_TYPE.COUPON.name().equals(product.getProductType())
					&& product instanceof ShopProductCoupon) {
				productId = shopProductDAO.insert((ShopProductCoupon) product);
			} else {
				productId = shopProductDAO.insert(product);
			}
			//保存限制条件取限制条件
			List<ShopProductCondition> shopProductConditions=product.getShopProductConditions();
			if(shopProductConditions!=null && shopProductConditions.size()>0){
				for(ShopProductCondition shopProductCondition:shopProductConditions){
					if(shopProductCondition!=null && shopProductCondition.getConditionX()!=null && !"".equals(shopProductCondition.getConditionX())){
						shopProductCondition.setProductId(productId);
						shopProductDAO.insertShopProductCondition(shopProductCondition);
					}
				}
			}
			shopLogService.insert(null, productId, "SHOP_PRODUCT", "PRODUCT_CREATE", operatorId);
		} else {
			String content = getModifiedContent(product);
			if (Constant.SHOP_PRODUCT_TYPE.COUPON.name().equals(product.getProductType())
					&& product instanceof ShopProductCoupon) {
				productId = shopProductDAO.update((ShopProductCoupon) product);
			} else {
				productId = shopProductDAO.update(product);
			}
			//更新限制条件取限制条件
			//1.1先删掉所有限制条件
			shopProductDAO.deleteShopProductConditionByProductId(productId);
			//1.2再保存所有限制条件
			List<ShopProductCondition> shopProductConditions=product.getShopProductConditions();
			if(shopProductConditions!=null && shopProductConditions.size()>0){
				for(ShopProductCondition shopProductCondition:shopProductConditions){
					if(shopProductCondition!=null && shopProductCondition.getConditionX()!=null && !"".equals(shopProductCondition.getConditionX())){
						shopProductCondition.setProductId(productId);
						shopProductDAO.insertShopProductCondition(shopProductCondition);
					}
				}
			}
			shopLogService.insert(content, productId, "SHOP_PRODUCT", "PRODUCT_UPDATE", operatorId);

		}
		return productId;
	}
	
	/**
	 * 根据条件查询用户是否下过单
	 * @param parameters
	 * @return
	 */
	@Override
	public Long isOrderChecked(Map<String, Object> parameters) {
		return userPointLogDAO.getCountUserPointLog(parameters);
	}

	public void setUserPointLogDAO(UserPointLogDAO userPointLogDAO) {
		this.userPointLogDAO = userPointLogDAO;
	}

}
