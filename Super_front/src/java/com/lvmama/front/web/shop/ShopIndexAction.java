package com.lvmama.front.web.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 积分商城首页的Action
 * @author Brian
 *
 */
@Results({
	@Result(name="index",location="/WEB-INF/pages/shop/index.ftl",type="freemarker")
})
public class ShopIndexAction extends ShopIndexLeftAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 1961332012353382801L;
	/**
	 * 日志输入器
	 */
	private static final Log LOG = LogFactory.getLog(ShopIndexAction.class);
	
	@SuppressWarnings("rawtypes")
	private Page page;
	private long currentPage = 1;
	private long pageSize = 12;
	
	/**
	 * 合作网站优惠券产品列表
	 */
	private List<ShopProduct> cooperationCouponList;
	/**
	 * 优惠券产品列表
	 */
	private List<ShopProduct> couponList;
	/**
	 * 实物产品列表
	 */
	private List<ShopProduct> productList;
	/**
	 * 抽奖产品列表
	 */
	private List<ShopProduct> raffleList;
	/**
	 * 首页每行显示产品个数
	 */
	private static final String INDEX_PAGE_COUNT = "8";
	/**
	 * 产品类型
	 */
	private String productType;
	
	private int pointsRegion = 0;
	/**
	 * 产品类型
	 */
	private String purchasingPower;
	/**
	 * 首页推荐商品
	 */
	private Map<String, List<RecommendInfo>> shopIndexPageMap;
	/**
	 * SUPER后台推送
	 */
	protected RecommendInfoService recommendInfoService;
	// pcadmin后台推荐块：热门景点排行
	private long blockId = 16127; // 主块ID

	private String station = "LP"; // 站点
	
	/**
	 * 积分商城首页
	 * @return
	 * @throws Exception
	 */
	@Action("/shop/index")
	public String index()  throws Exception {
		
		initIndexLeft();
		couponList = getIndexPageOfCouponList();
		productList = getIndexPageOfProductList();
		raffleList = getIndexPageOfRaffleList();
		cooperationCouponList = getIndexPageOfCooperationCouponList();
		shopIndexPageMap = getRecProductByBlockIdAndStationFromCache();
		
		return "index";
	}
	
	/**
	 * 获取首页的其它网站优惠券产品列表
	 * @return 其它网站优惠券产品列表
	 */
	@SuppressWarnings("unchecked")
	private List<ShopProduct> getIndexPageOfCooperationCouponList() {
		Object cache = MemcachedUtil.getInstance().get("SHOP_INDEX_PAGE_OF_COOPERATION_COUPON_LIST");
		if (null != cache) {
			LOG.debug("从MemCache中获取积分商城首页的其它网站优惠券类产品列表");
		} else {
			LOG.debug("MemCache中积分商城首页的其它网站优惠券产品列表不存在或已经过期，需要重新获取");
			Map<String,Object> coopCouponParameters = new HashMap<String, Object>();
			coopCouponParameters.put("_startRow", "1");
			coopCouponParameters.put("_endRow", INDEX_PAGE_COUNT);
			coopCouponParameters.put("changeType", Constant.SHOP_CHANGE_TYPE.POINT_CHANGE.name());
			coopCouponParameters.put("productType", Constant.SHOP_PRODUCT_TYPE.COOPERATION_COUPON.name());
			coopCouponParameters.put("isRecommend", "Y");
			coopCouponParameters.put("isValid", "Y");
			cache = shopProductService.query(coopCouponParameters);
			if (null != cache) {
				MemcachedUtil.getInstance().set("SHOP_INDEX_PAGE_OF_COOPERATION_COUPON_LIST", EXPIRY_MINUTES, cache);
			}
		}
		return (List<ShopProduct>) cache;
	}
	
	/**
	 * 获取首页的优惠券类产品列表
	 * @return 优惠券类产品列表
	 */
	@SuppressWarnings("unchecked")
	private List<ShopProduct> getIndexPageOfCouponList() {
		Object cache = MemcachedUtil.getInstance().get("SHOP_INDEX_PAGE_OF_COUPON_LIST");
		if (null != cache) {
			LOG.debug("从MemCache中获取积分商城首页的优惠券类产品列表");
		} else {
			LOG.debug("MemCache中积分商城首页的优惠券类产品列表不存在或已经过期，需要重新获取");
			Map<String,Object> CouponParameters = new HashMap<String, Object>();
			CouponParameters.put("_startRow", "1");
			CouponParameters.put("_endRow", INDEX_PAGE_COUNT);
			CouponParameters.put("changeType", Constant.SHOP_CHANGE_TYPE.POINT_CHANGE.name());
			CouponParameters.put("productType", Constant.SHOP_PRODUCT_TYPE.COUPON.name());
			CouponParameters.put("isRecommend", "Y");
			CouponParameters.put("isValid", "Y");
			cache = shopProductService.query(CouponParameters);
			if (null != cache) {
				MemcachedUtil.getInstance().set("SHOP_INDEX_PAGE_OF_COUPON_LIST", EXPIRY_MINUTES, cache);
			}
		}
		return (List<ShopProduct>) cache;
	}
	
	/**
	 * 获取首页的实物类产品列表
	 * @return 实物类产品列表
	 */
	@SuppressWarnings("unchecked")
	private List<ShopProduct> getIndexPageOfProductList() {
		Object cache = MemcachedUtil.getInstance().get("SHOP_INDEX_PAGE_OF_PRODUCT_LIST");
		if (null != cache) {
			LOG.debug("从MemCache中获取积分商城首页的实物类产品列表");
		} else {
			LOG.debug("MemCache中积分商城首页的实物类产品列表不存在或已经过期，需要重新获取");
			Map<String, Object> productParameters = new HashMap<String, Object>();
			productParameters.put("_startRow", "1");
			productParameters.put("_endRow", INDEX_PAGE_COUNT);
			productParameters.put("isRecommend", "Y");
			productParameters.put("changeType", Constant.SHOP_CHANGE_TYPE.POINT_CHANGE.name());
			productParameters.put("productType", Constant.SHOP_PRODUCT_TYPE.PRODUCT.name());
			productParameters.put("isValid", "Y");
			cache = shopProductService.query(productParameters);
			if (null != cache) {
				MemcachedUtil.getInstance().set("SHOP_INDEX_PAGE_OF_PRODUCT_LIST", EXPIRY_MINUTES, cache);
			}
		}
		return (List<ShopProduct>) cache;
	}
	
	/**
	 * 获取首页的抽奖产品列表
	 * @return 抽奖产品列表
	 */
	@SuppressWarnings("unchecked")
	private List<ShopProduct> getIndexPageOfRaffleList() {
		Object cache = MemcachedUtil.getInstance().get("SHOP_INDEX_PAGE_OF_RAFFLE_LIST");
		if (null != cache) {
			LOG.debug("从MemCache中获取积分商城首页的抽奖产品列表");
		} else {
			LOG.debug("MemCache中积分商城首页的抽奖产品列表不存在或已经过期，需要重新获取");
			Map<String, Object> productParameters = new HashMap<String, Object>();
			productParameters.put("_startRow", "1");
			productParameters.put("_endRow", INDEX_PAGE_COUNT);
			productParameters.put("isRecommend", "Y");
			productParameters.put("changeType", Constant.SHOP_CHANGE_TYPE.RAFFLE.name());
			productParameters.put("isValid", "Y");
			cache = shopProductService.query(productParameters);
			if (null != cache) {
				MemcachedUtil.getInstance().set("SHOP_INDEX_PAGE_OF_RAFFLE_LIST", EXPIRY_MINUTES, cache);
			}
		}
		return (List<ShopProduct>) cache;
	}
	
	/**
	 * 从cache(1天)获取积分商城的推荐信息
	 */
	@SuppressWarnings("unchecked")
	private Map<String, List<RecommendInfo>> getRecProductByBlockIdAndStationFromCache() {
		Object cache = MemcachedUtil.getInstance().get("SHOP_RECOMMEND_INFO");
		if (null != cache) {
			LOG.debug("从MemCache中获取积分商城的推荐信息列表");
		} else {
			LOG.debug("MemCache中获取积分商城的推荐信息列表不存在或已经过期，需要重新获取");
			cache = recommendInfoService.getRecommendInfoByParentBlockIdAndPageChannel(blockId, station);
			if(cache != null)
			{
				MemcachedUtil.getInstance().set("SHOP_RECOMMEND_INFO", 4 * 60 * 60, cache);
			}
		}
		return (Map<String, List<RecommendInfo>>) cache;
	}
	
	/**
	 * * * * * get and set properties
	 */
	public List<ShopProduct> getCouponList() {
		return couponList;
	}
	public List<ShopProduct> getProductList() {
		return productList;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	@SuppressWarnings("rawtypes")
	public Page getPage() {
		return page;
	}
	public long getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	public int getPointsRegion() {
		return pointsRegion;
	}
	public void setPointsRegion(int pointsRegion) {
		this.pointsRegion = pointsRegion;
	}
	public String getPurchasingPower() {
		return purchasingPower;
	}
	public void setPurchasingPower(String purchasingPower) {
		this.purchasingPower = purchasingPower;
	}
	public List<ShopProduct> getRaffleList() {
		return raffleList;
	}

	public List<ShopProduct> getCooperationCouponList() {
		return cooperationCouponList;
	}

	public void setRecommendInfoService(RecommendInfoService recommendInfoService) {
		this.recommendInfoService = recommendInfoService;
	}

	public Map<String, List<RecommendInfo>> getShopIndexPageMap() {
		return shopIndexPageMap;
	}

	public void setShopIndexPageMap(
			Map<String, List<RecommendInfo>> shopIndexPageMap) {
		this.shopIndexPageMap = shopIndexPageMap;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}
 
}
