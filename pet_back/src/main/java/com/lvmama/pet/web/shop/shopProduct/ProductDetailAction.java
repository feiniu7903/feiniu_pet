package com.lvmama.pet.web.shop.shopProduct;

import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.service.shop.ShopProductService;


/**
 * 产品详情Action
 *
 */
public class ProductDetailAction  extends com.lvmama.pet.web.BaseAction {
    /**
     * serialVersionUID
     */
	private static final long serialVersionUID = -2240990352751933080L;
    /**
     * 产品服务接口
     */
	private ShopProductService shopProductService;
	/**
	 * 产品
	 */
	private ShopProduct product;
	/**
	 * 产品ID
	 */
	private Long productId;


	@Override
	public void doBefore() {
		if (null == productId) {
			product = new ShopProduct();
		} else {
			product = shopProductService.queryByPk(productId);
		}
	}

	public ShopProduct getProduct() {
		return product;
	}

	public void setProductId(final Long productId) {
		this.productId = productId;
	}

	public void setShopProductService(ShopProductService shopProductService) {
		this.shopProductService = shopProductService;
	}


}

