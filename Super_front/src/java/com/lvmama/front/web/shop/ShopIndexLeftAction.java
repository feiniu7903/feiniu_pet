package com.lvmama.front.web.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.service.shop.ShopProductService;
import com.lvmama.comm.pet.service.shop.ShopUserService;
import com.lvmama.comm.pet.vo.ShopUser;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.front.web.BaseAction;


/**
 * 积分商城首页左边栏的Action
 * 
 * @author yuzhizeng
 * 
 */
@Results({ @Result(name = "toHelpCenter", location = "/WEB-INF/pages/shop/help.ftl", type = "freemarker") })
public class ShopIndexLeftAction extends BaseAction {

	private static final long serialVersionUID = 4979724770074459380L;
	/**
	 * 日志输入器
	 */
	private static final Log LOG = LogFactory.getLog(ShopIndexLeftAction.class);
	/**
	 * 放在MemCache中的对象的默认过期秒数
	 */
	public static final int EXPIRY_MINUTES = 60;

	/**
	 * 查询热门推荐产品个数
	 */
	public static final String TOP_PRODUCT_COUNT = "5";
	/**
	 * 积分商城的服务接口
	 */
	protected ShopProductService shopProductService;
	/**
	 * 积分用户的服务接口
	 */
	protected ShopUserService shopUserService;

	/**
	 * 热门推荐产品列表
	 */
	protected List<ShopProduct> topProductList;
	/**
	 * 用户
	 */
	protected ShopUser shopUser;

	/**
	 * 积分商城首页左边栏初始化
	 * 
	 * @return
	 */
	public void initIndexLeft() {
		if (isLogin()) {
			shopUser = shopUserService.getUserByPK(this.getUser().getId());
		}
		topProductList = getTopProduct();
	}

	/**
	 * 获取积分商城首页的热卖产品列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<ShopProduct> getTopProduct() {
		Object cache = MemcachedUtil.getInstance().get("SHOP_INDEX_PAGE_OF_TOP_PRODUCT_LIST");
		if (null != cache) {
			LOG.debug("从MemCache中获取积分商城首页的热卖产品列表");
		} else {
			LOG.debug("MemCache中积分商城首页的热卖产品列表不存在或已经过期，需要重新获取");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isHotProduct", "Y");
			parameters.put("isValid", "Y");
			parameters.put("_startRow", "1");
			parameters.put("_endRow", TOP_PRODUCT_COUNT);
			cache = shopProductService.query(parameters);
			if (null != cache) {
				MemcachedUtil.getInstance().set("SHOP_INDEX_PAGE_OF_TOP_PRODUCT_LIST", EXPIRY_MINUTES, cache);
			}
		}
		return (List<ShopProduct>) cache;
	}

	/**
	 * 去帮助中心
	 * 
	 * @return
	 */
	@Action("/shop/toHelpCenter")
	public String toHelpCenter() {
		return "toHelpCenter";
	}
	

	public void setShopProductService(ShopProductService shopProductService) {
		this.shopProductService = shopProductService;
	}

	public List<ShopProduct> getTopProductList() {
		return topProductList;
	}

	public ShopUser getShopUser() {
		return shopUser;
	}

	public void setShopUserService(ShopUserService shopUserService) {
		this.shopUserService = shopUserService;
	}

}
