package com.lvmama.pet.web.shop.shopProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.shop.ShopLog;
import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.po.shop.ShopProductCoupon;
import com.lvmama.comm.pet.service.shop.ShopCooperationCouponService;
import com.lvmama.comm.pet.service.shop.ShopLogService;
import com.lvmama.comm.pet.service.shop.ShopProductService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.utils.ZkMessage;


/**
 * 新增产品
 * @author Brian
 *
 */
public class NewProductAction extends com.lvmama.pet.web.BaseAction {
	/**
	 *  序列值
	 */
	private static final long serialVersionUID = -1156767087154768981L;
	/**
	 * 产品的逻辑层
	 */
	private ShopProductService shopProductService;
	/**
	 * 日志逻辑层
	 */
	private ShopLogService shopLogService;
	
	/**
	 * 合作网站优惠券接口
	 */
	private ShopCooperationCouponService shopCooperationCouponService;
	
	/**
	 * 页面操作的产品
	 */
	private ShopProduct product;
	/**
	 * 优惠券标识
	 */
	private Long couponId;
	/**
	 * 是否复制
	 */
	private String isClone;
	/**
	 * 页面对图片处理
	 */
	private List<ShopProduct> pictureList = new ArrayList<ShopProduct>();
	/**
	 * 产品标识
	 */
	private Long productId;
	/**
	 * 产品日志列表
	 */
	private List<ShopLog> logList;

	@Override
	public void doBefore() {
		if (null == productId) {
			product = new ShopProduct();
		} else {
			product = shopProductService.queryByPk(productId);
			if (Constant.SHOP_PRODUCT_TYPE.COUPON.name().equals(product.getProductType())) {
				this.couponId = ((ShopProductCoupon) product).getCouponId();
			}

			Map<String, Object> parametes = new HashMap<String, Object>();
			parametes.put("objectId", productId);
			parametes.put("objectType", "SHOP_PRODUCT");
			logList = shopLogService.query(parametes);
		}
		pictureList.add(product);
		if (null != this.isClone) {
			product.setProductId(null);
		}
		session.setAttribute("SHOP_PRODUCT", product);
	}
	/**
	 * 上传图片
	 * @param imgsrc 图片地址
	 */
	public void addImage(final String imgsrc) {
		if (imgsrc == null || imgsrc.equals("")) {
			alert("请选择要上传的图片");
			return;
		}
		product.setPictures(imgsrc);
	}

	/**
	 * 产品是否为热推产品
	 * @param value 是否热推
	 */
	public void changeHotProduct(boolean value) {
		product.setIsHotProduct(value ? "Y" : "N");
	}

	/**
	 * 判断产品的内容是否为Null
	 * @return 产品内容是否为Null
	 */
	public boolean isNullOfProductContent() {
		return StringUtils.isEmpty(product.getContent());
	}

	/**
	 * 产品是否为首页推荐产品
	 * @param value 是否首页推荐产品
	 */
	public void changeRecommend(boolean value) {
		product.setIsRecommend(value ? "Y" : "N");
	}

	/**
	 * 新增/更新产品
	 * @param value 是否上线
	 */
	public void save(final boolean value) {
		
		String currentUser = getSessionUserName();
		if(StringUtils.isEmpty(currentUser)){
			ZkMessage.showInfo("请先登陆.");
			return;
		}
		product.setIsValid(value ? "Y" : "N");
		String msg = "";
		if(value) {
			msg = " 并已经上线!";
		}
		if (Constant.SHOP_PRODUCT_TYPE.COUPON.name().equals(product.getProductType())) {
			ShopProductCoupon cp = new ShopProductCoupon();
			org.springframework.beans.BeanUtils.copyProperties(product, cp);
			cp.setCouponId(this.couponId);
			shopProductService.save(cp, getSessionUserName());
		} else {
			shopProductService.save(product, getSessionUserName());
		}
		ZkMessage.showInfo("产品\"" + product.getProductName() + "\"已经保存成功! " + msg);
		refreshParent("search");
		getComponent().detach();
	}

	public void getCooperateStock(final String productType){
		if(productId == null && !Constant.SHOP_PRODUCT_TYPE.COOPERATION_COUPON.name().equals(productType)){
			return;
		}else if(productId == null && Constant.SHOP_PRODUCT_TYPE.COOPERATION_COUPON.name().equals(productType)){
			product.setStocks(0L);
			return;
		}
		Long stocks = null;
		if(Constant.SHOP_PRODUCT_TYPE.COOPERATION_COUPON.name().equals(productType)){
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("productId", productId);
			stocks = shopCooperationCouponService.count(parameters);
		}else{
			ShopProduct product = shopProductService.queryByPk(productId);
			if(productType.equals(product.getProductType())){
				stocks = product.getStocks();
			}
		}
		product.setStocks(stocks);
	}
	
	public boolean getStocksDisabled(){
		if(Constant.SHOP_PRODUCT_TYPE.COOPERATION_COUPON.name().equals(product.getProductType())){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	public ShopProduct getProduct() {
		return product;
	}


	public void setShopProductService(final ShopProductService shopProductService) {
		this.shopProductService = shopProductService;
	}
	public void setProductId(final Long productId) {
		this.productId = productId;
	}
	public List<ShopProduct> getPictureList() {
		return pictureList;
	}

	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(final Long couponId) {
		this.couponId = couponId;
	}
	public void setIsClone(final String isClone) {
		this.isClone = isClone;
	}
	public List<ShopLog> getLogList() {
		return logList;
	}
	public void setShopLogService(final ShopLogService shopLogService) {
		this.shopLogService = shopLogService;
	}
	public void setShopCooperationCouponService(
			ShopCooperationCouponService shopCooperationCouponService) {
		this.shopCooperationCouponService = shopCooperationCouponService;
	}
}
