package com.lvmama.pet.sweb.shop;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.service.shop.ShopProductService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 产品详情编辑页
 * @author Brian
 */
@Results({
	@Result(name = "edit", location = "/WEB-INF/pages/shop/product/editor.jsp")
	})
public class ProductDetailEditAction extends ActionSupport {
	/**
	 *  序列值
	 */
	private static final long serialVersionUID = 6350768370957139460L;
	/**
	 * 产品标识
	 */
	private Long productId;
	/**
	 * 产品详细内容
	 */
	private String content;
	/**
	 * 产品逻辑接口
	 */
	private ShopProductService shopProductService;

	/**
	 * 产品详情编辑页
	 * @return 产品详情编辑页
	 */
	@Action("/view/toedit")
	public String toViewContentEdit() {
		if (null != productId) {
			ShopProduct product = shopProductService.queryByPk(productId);
			if (null != product) {
				content = product.getContent();
			}
		}
		return "edit";
	}

	public String getContent() {
		return content;
	}

	public void setProductId(final Long productId) {
		this.productId = productId;
	}

	public void setShopProductService(ShopProductService shopProductService) {
		this.shopProductService = shopProductService;
	}


}