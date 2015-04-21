package com.lvmama.front.web.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.shop.ShopOrder;
import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.po.shop.ShopProductCondition;
import com.lvmama.comm.pet.po.shop.ShopProductZhuanti;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.shop.ShopOrderService;
import com.lvmama.comm.pet.service.shop.ShopProductService;
import com.lvmama.comm.pet.service.shop.ShopProductZhuantiService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.ShopRemoteCallResult;
import com.lvmama.comm.pet.vo.ShopUserInfo;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name = "invalid.token", location = "/WEB-INF/pages/shop/wrong.ftl", type = "freemarker"),
	@Result(name = "fillPointChangeOrder", location = "/WEB-INF/pages/shop/fillPointChangeOrder.ftl", type = "freemarker"),
	@Result(name = "fillRaffleChangeOrder", location = "/WEB-INF/pages/shop/fillRaffleChangeOrder.ftl", type = "freemarker"),
	@Result(name = "raffleChangeOrder", location = "/WEB-INF/pages/shop/raffleChangeProductDetail.ftl", type = "freemarker"),
	@Result(name = "orderSucc", location = "/WEB-INF/pages/shop/duihuan.ftl", type="freemarker"),
	@Result(name = "productDetail", location = "/shop/showProductDetail.do?productId=${productId}&errorText=${errorText}", type="redirect")
})
@InterceptorRefs( {
	@InterceptorRef("defaultStack"),
	@InterceptorRef(value = "token", params = { "includeMethods","createOrder"}),
	@InterceptorRef(value = "token", params = { "includeMethods", "initOrder" })
		 })
public class ShopOrderAction extends ShopIndexLeftAction {
	private static Map<String,Object> lockMap = new HashMap<String,Object>();
	private static Object lock_1 = new Object();
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 7841246275506897256L;
	/**
	 * 日志输入器
	 */
	private static final Log LOG = LogFactory.getLog(ShopOrderAction.class);
	/**
	 * SESSION中保存用户中奖号码的键
	 */
	private static final String SESSION_RAFFLE_NAME = "SESSION_RAFFLE_NAME";
	
	/**
	 * 积分订单服务
	 */
	private ShopOrderService shopOrderService;
	private UserClient userClient;
	private UserUserProxy userUserProxy;


	/**
	 * 产品标识
	 */
	private Long productId;
	/**
	 *订购产品数
	 */
	private Integer quantity;
	/**
	 * 用户信息
	 */
	private ShopUserInfo userInfo;
	/**
	 * 产品
	 */
	private ShopProduct product;
	/**
	 * 错误的信息
	 */
	private String errorText;
	/**
	 * 订单标识
	 */
	private Long orderId;
	/**
	 * 抽奖类产品如中奖的中奖码
	 */
	private String raffleCode;
	/**
	 * 是否进行了抽奖
	 */
	private boolean showRaffleResult;
	/**
	 * 用户
	 */
	private UserUser users;
	
	private String hasZhuantiCoupon = "false";
	
	private ShopProductZhuantiService shopProductZhuantiService;
	/**
	 * 已经兑换个数
	 */
	private Integer exchangeNum=0;
	/**
	 * 最多可兑换数量
	 */
	private Integer exchangeMaxNum=0;
	
	/**
	 * 初始化订单，即订单创建页面
	 * @return 订单创建页面
	 */
	@Action("/shop/initOrder")
	public String initOrder() {
	
		if (!isLogin()) {
			debug("用户尚未登录，无法进入兑换订单页面!");
			return "productDetail";
		}
		if (null == productId) {
			debug("缺少productId参数，无法进行兑换!");
			return "productDetail";			
		}
		product = shopProductService.queryByPk(productId);
		if( product == null){
			debug("product为空，无法进行兑换!");
			LOG.info("initOrder productID:" + productId);
			return "productDetail";
		}
		users = getUser();
		if (null == product) {
			debug("无法找到产品(productId:" + productId + ")，兑换失败");
			return "productDetail";			
		} else {
			debug("找到需要兑换的产品:" + product);
		}
		if (!"Y".equals(product.getIsValid())) {
			debug("产品已经下线，无法进行兑换");
			return "productDetail";				
		}
		
		//当前产品用户在专题是否有优惠
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("productId", productId);
		param.put("userId", users.getId());
		List<ShopProductZhuanti> zhuantiList = shopProductZhuantiService.queryList(param);
		if(zhuantiList != null && zhuantiList.size() > 0){
			hasZhuantiCoupon = "true";
		}
		initIndexLeft();
		
		checkExchangeNum();
		
		if (Constant.SHOP_CHANGE_TYPE.RAFFLE.name().equals(product.getChangeType())) {
			/* 进入积分抽奖的页面 */
			return raffle();
		} else {	
			/* 进入积分兑换的页面 */
			return "fillPointChangeOrder";
		}
	}
	
	private void checkExchangeNum(){
		//check一个会员最多只可兑换X个
		List<ShopProductCondition> shopProductConditions=product.getShopProductConditions();
		if(shopProductConditions!=null && shopProductConditions.size()>0){
			for(ShopProductCondition shopProductCondition :shopProductConditions){
				if(Constant.SHOP_PRODUCT_CONDITION.CHECK_EXCHANGE_NUM.getCode().equals(shopProductCondition.getConditionX())){
					//当前产品一个会员最多可兑换数量 
					if(shopProductCondition.getConditionY()!=null){
						exchangeMaxNum=Integer.valueOf(shopProductCondition.getConditionY());
					}
					
					//当前产品已被兑换个数
					Map<String,Object> parameters=new HashMap<String,Object>();
					parameters.put("userId", this.getUser().getId());
					parameters.put("productId", productId);
					List<ShopOrder> shopOrders=shopOrderService.queryShopOrder(parameters);
					if(shopOrders!=null && shopOrders.size()>0){
						exchangeNum = 0;
						for(ShopOrder shopOrder:shopOrders){
							exchangeNum+=shopOrder.getQuantity();
						}
					}
				}
			}
		}
	}
	
	/**
	 * 创建订单
	 * @return
	 */
	@Action("/shop/createOrder")
	public String createOrder() {
		ShopRemoteCallResult callBackResult = null;
		if (!isLogin()) {
			debug("用户尚未登录，无法进入兑换订单页面!");
			return "productDetail";
		}
		if (null == productId) {
			debug("缺少productId参数，无法进行兑换!");
			return "productDetail";			
		}
		
		product = shopProductService.queryByPk(productId);
		if( product == null){
			debug("product为空，无法进行兑换!");
			LOG.info("createOrder productID:" + productId);
			return "productDetail";
		}
		
		if (Constant.SHOP_CHANGE_TYPE.RAFFLE.name().equals(product.getChangeType()))
		{
			quantity = 1;//抽奖数量固定为1
		}
		else
		{
			if(quantity < 1)//保护产品兑换数量必须大于0
			{
				debug("product count < 1");
				initIndexLeft();
				return "productDetail";	
			}
		}
		
		if (Constant.SHOP_CHANGE_TYPE.RAFFLE.name().equals(product.getChangeType())) {
			if (null != raffleCode && raffleCode.equals(getSession(SESSION_RAFFLE_NAME))){
				callBackResult = userClient.createShopOrder(this.getUser().getId(), productId, quantity, userInfo);  
				if (callBackResult.isResult()) {
					removeSession(SESSION_RAFFLE_NAME);
					orderId = (Long) callBackResult.getObject();
					putSession(com.lvmama.comm.vo.Constant.SESSION_FRONT_USER, userUserProxy.getUserUserByPk(this.getUser().getId()));
					initIndexLeft();
					return "orderSucc";
				} 				
			} else {
				callBackResult = new ShopRemoteCallResult(false, ShopProductService.ABSENT_NECESSARY_DATA, "提交的抽奖号与实际不符");	
			}
		} else {
			Object lock = lockMap.get(getUserId());
			if( lock == null){
				synchronized (lock_1) {
					lock= new Object();
					lockMap.put(getUserId(), lock);
				}
			}
			synchronized (lock) {
				checkExchangeNum();
				if(exchangeNum+quantity > exchangeMaxNum && exchangeMaxNum > 0){
					LOG.error("userId:"+getUserId()+" productId : "+productId+"product's exchangeMaxNum:" + exchangeMaxNum + "; user's  exchangeNum:" + exchangeNum);
					callBackResult = new ShopRemoteCallResult(false, ShopProductService.PRODUCT_TOO_MUCH, "超过兑换数量限制");
				}else{
					callBackResult = shopOrderService.checkPointToChangeProduct(this.getUser().getId(), productId, quantity);
					if (callBackResult.isResult()) {
						callBackResult = userClient.createShopOrder(this.getUser().getId(), productId, quantity, userInfo);
						if (callBackResult.isResult()) {
							orderId = (Long) callBackResult.getObject();
							putSession(com.lvmama.comm.vo.Constant.SESSION_FRONT_USER, userUserProxy.getUserUserByPk(this.getUser().getId()));
							initIndexLeft();
							return "orderSucc";
						}
					}
				}
			}
			
		}
		
		//检查出不满足产品和积分条件
		debug("创建积分商城的订单失败!" + callBackResult);
		initIndexLeft();
		lockMap.remove(getUserId());
		return createOrderFail(callBackResult);
	}
	
	/**
	 * 抽奖页面的跳转
	 * @return
	 */
	private String raffle() {
		
		//1,代表用户是进行抽奖，而不是抽中奖的跳转
		if (StringUtils.isEmpty(raffleCode)) {
			//不满足产品和积分的条件
			if (!StringUtils.isEmpty(errorText)) {
				return "productDetail";
			}
			
			/* 进入抽奖结果页面 */
			ShopRemoteCallResult callBackResult = shopOrderService.checkPointToChangeProduct(this.getUser().getId(), productId, 1);
			if (callBackResult.isResult()) {
				showRaffleResult = true;
				raffleCode = shopOrderService.luckyDraw(this.getUser().getId(), productId);
				users = getUser();
				if (null == raffleCode) {
					debug("抱歉，没有抽到奖品!");
				} else {
					putSession(SESSION_RAFFLE_NAME, raffleCode);
					debug("恭喜，抽到奖品啦！中奖号:" + raffleCode);
				}
			} else {
				//检查出不满足产品和积分的条件
				debug("创建积分商城的订单失败!" + callBackResult);
				createOrderFail(callBackResult);
			}
			return "raffleChangeOrder";	
		} 
		
		//2,用户抽中奖后，去填写订单信息
		return "fillRaffleChangeOrder";
	}
	
	/**
	 * 创建订单失败的页面跳转
	 * @param callBackResult
	 * @return
	 */
	private String createOrderFail(ShopRemoteCallResult callBackResult) {
		if (null != callBackResult && !callBackResult.isResult()) {
			switch (callBackResult.getErrorCode()) {
				case ShopProductService.ABSENT_NECESSARY_DATA:
					errorText = "缺少必要的数据，创建订单失败"; 
					break;
				case ShopProductService.PRODUCT_CANNOT__BEFOUND:
					errorText = "所订购的产品不存在，创建订单失败"; 
					break;
				case ShopProductService.PRODUCT_HAS_OFFLIINED:
					errorText = "所订购产品已经下线，创建订单失败"; 
					break;
				case ShopProductService.PRODUCT_LESS_STOCK:
					errorText = "所订购的产品库存不足，请重新选择"; 
					break;
				case ShopProductService.USER_CANNOT_BE_FOUND:
					errorText = "无法找到用户信息，创建订单失败"; 
					break;
				case ShopProductService.USER_LESS_POINT:
					errorText = "用户积分不足，请重新选择"; 
					break;
				case ShopProductService.PRODUCT_TOO_MUCH:
					errorText = callBackResult.getErrorText(); 
					break;
			};
		}
		return initOrder();
	}
	
	/**
	 * 打印出调试信息
	 * @param message 调试信息
	 */
	private void debug(final String message) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(message);
		}
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(final Long productId) {
		this.productId = productId;
	}

	public ShopProduct getProduct() {
		return product;
	}

	public void setQuantity(final Integer quantity) {
		this.quantity = quantity;
	}

	public ShopUserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(ShopUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getErrorText() {
		return errorText;
	}

	public Long getOrderId() {
		return orderId;
	}

	public String getRaffleCode() {
		return raffleCode;
	}

	public void setRaffleCode(String raffleCode) {
		this.raffleCode = raffleCode;
	}

	public boolean isShowRaffleResult() {
		return showRaffleResult;
	}

	public UserUser getUsers() {
		return users;
	}

	public void setShopOrderService(ShopOrderService shopOrderService) {
		this.shopOrderService = shopOrderService;
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}
	
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public String isHasZhuantiCoupon() {
		return hasZhuantiCoupon;
	}

	public void setHasZhuantiCoupon(String hasZhuantiCoupon) {
		this.hasZhuantiCoupon = hasZhuantiCoupon;
	}

	public void setShopProductZhuantiService(
			ShopProductZhuantiService shopProductZhuantiService) {
		this.shopProductZhuantiService = shopProductZhuantiService;
	}

	public Integer getExchangeNum() {
		return exchangeNum;
	}

	public void setExchangeNum(Integer exchangeNum) {
		this.exchangeNum = exchangeNum;
	}

	public Integer getExchangeMaxNum() {
		return exchangeMaxNum;
	}

	public void setExchangeMaxNum(Integer exchangeMaxNum) {
		this.exchangeMaxNum = exchangeMaxNum;
	}
	
}
