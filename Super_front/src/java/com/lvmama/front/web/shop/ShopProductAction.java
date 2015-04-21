package com.lvmama.front.web.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.shop.ShopOrder;
import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.po.shop.ShopProductCondition;
import com.lvmama.comm.pet.service.shop.ShopOrderService;
import com.lvmama.comm.vo.Constant;

@Results({
		@Result(name = "index", location = "/shop/index.do", type = "redirect"),
		@Result(name = "pointChangeProductDetail", location = "/WEB-INF/pages/shop/pointChangeProductDetail.ftl", type = "freemarker"),
		@Result(name = "raffleChangeProductDetail", location = "/WEB-INF/pages/shop/raffleChangeProductDetail.ftl", type = "freemarker") })
public class ShopProductAction extends ShopIndexLeftAction {
	
	private static final long serialVersionUID = -357187452688791012L;
	/**
	 * 日志输入器
	 */
	private static final Log LOG = LogFactory.getLog(ShopProductAction.class);
	/**
	 * 产品
	 */
	private ShopProduct product;

	/**
	 * 产品表识
	 */
	private Long productId;

	/**
	 * 错误的信息
	 */
	private String errorText;
	/**
	 * 错误的信息Code
	 */
	private int errorCode;
	/**
	 * 兑换订单Service
	 */
	private ShopOrderService shopOrderService;
	/**
	 * 兑换描述
	 */
	private String checkDes;
	/**
	 * 兑换限制描述
	 */
	private String checkShopProduct;
	
	/**
	 * 显示产品详细页面
	 * @returnuo
	 */
	@Action("/shop/showProductDetail")
	public String showProductDetail() {
		//下一步会用token，现在我们没用httpSession,token有时不会有session会报错 
		this.getRequest().getSession().getId();
		initIndexLeft();
		
		if (null != productId) {
			product = shopProductService.queryByPk(productId);
			if( product == null){
				LOG.info("showProductDetail productID:" + productId);
				return "index";
			}
			List<ShopProductCondition> shopProductConditions = product.getShopProductConditions();
			if(shopProductConditions!=null && shopProductConditions.size()>0 ){
				checkShopProduct(shopProductConditions);
			}
		} else {
			return "index"; // 返回到产品列表页面
		}
		
		if (Constant.SHOP_CHANGE_TYPE.RAFFLE.name().equals(product.getChangeType())) {
			/* 如果是抽奖产品，则进入抽奖的页面 */
			return "raffleChangeProductDetail";
		} else {
			/* 进入积分兑换的页面 */
			return "pointChangeProductDetail";
		}
	}
	
	/**
	 * 检查兑换条件 
	 * @param shopProductConditions
	 */
	private void checkShopProduct(List<ShopProductCondition> shopProductConditions){
		Integer i=1;
		StringBuffer sbCheckDesc=new StringBuffer();
		StringBuffer sbCheckShopProduct=new StringBuffer();
		
		for(ShopProductCondition shopProductCondition:shopProductConditions){
			
			if(Constant.SHOP_PRODUCT_CONDITION.CHECK_EXCHANGE_EMAIL.getCode().equals(shopProductCondition.getConditionX())){
				sbCheckDesc.append(i+".仅限邮箱验证会员兑换;<br/>");
				i++;
				//未验证邮箱
				if(isLogin()&&!"Y".equals(this.getUser().getIsEmailChecked())){
					sbCheckShopProduct.append("仅限邮箱验证会员兑换，您的邮箱还未验证！&nbsp;&nbsp;<a href='http://www.lvmama.com/myspace/userinfo.do' target='_blank'>立即验证</a><br/>");
				}
			}else if(Constant.SHOP_PRODUCT_CONDITION.CHECK_EXCHANGE_ORDER.getCode().equals(shopProductCondition.getConditionX())){
				sbCheckDesc.append(i+".仅限购买过旅游产品并因此获得积分的会员兑换;<br/>");
				i++;
				if(isLogin()){
					Map<String,Object> parameters=new HashMap<String,Object>();
					parameters.put("userId", this.getUser().getId());
					parameters.put("ruleId", "POINT_FOR_ORDER_VISITED");
					Long num=shopProductService.isOrderChecked(parameters);
					//未下过单
					if(num<=0){
						sbCheckShopProduct.append("仅限购买过旅游产品并因此获得积分的会员兑换，您还未下过订单！&nbsp;&nbsp;<a href='http://www.lvmama.com' target='_blank'>现在去看看</a><br/>");
					}
				}
			}else if(Constant.SHOP_PRODUCT_CONDITION.CHECK_EXCHANGE_NUM.getCode().equals(shopProductCondition.getConditionX())){
				sbCheckDesc.append(i+".每个会员最多兑换"+shopProductCondition.getConditionY()+"个;<br/>");
				i++;
				if(isLogin()){
					Map<String,Object> parameters=new HashMap<String,Object>();
					parameters.put("userId", this.getUser().getId());
					parameters.put("productId", productId);
					List<ShopOrder> shopOrders=shopOrderService.queryShopOrder(parameters);
					int num=0;
					if(shopOrders!=null && shopOrders.size()>0){
						for(ShopOrder shopOrder:shopOrders){
							num+=shopOrder.getQuantity();
						}
					}
					//超过最大下单数
					if(num>=Long.valueOf(shopProductCondition.getConditionY())){
						sbCheckShopProduct.append("每个会员最多兑换"+shopProductCondition.getConditionY()+"个，不要太贪心哦！&nbsp;&nbsp;<a id='nowClose' href='#' >知道了</a>");
					}
				}
			}
		}
		checkDes=sbCheckDesc.toString();
		checkShopProduct=sbCheckShopProduct.toString();
	}

	public ShopProduct getProduct() {
		return product;
	}

	public void setProduct(ShopProduct product) {
		this.product = product;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getCheckDes() {
		return checkDes;
	}

	public void setCheckDes(String checkDes) {
		this.checkDes = checkDes;
	}

	public String getCheckShopProduct() {
		return checkShopProduct;
	}

	public void setCheckShopProduct(String checkShopProduct) {
		this.checkShopProduct = checkShopProduct;
	}

	public void setShopOrderService(ShopOrderService shopOrderService) {
		this.shopOrderService = shopOrderService;
	}
	


}
