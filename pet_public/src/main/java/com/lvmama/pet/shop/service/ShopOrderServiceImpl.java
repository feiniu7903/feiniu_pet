package com.lvmama.pet.shop.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.shop.ShopOrder;
import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.po.shop.ShopProductZhuanti;
import com.lvmama.comm.pet.service.shop.ShopOrderService;
import com.lvmama.comm.pet.service.shop.ShopProductService;
import com.lvmama.comm.pet.service.shop.ShopProductZhuantiService;
import com.lvmama.comm.pet.service.shop.ShopUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.ShopOrderVO;
import com.lvmama.comm.pet.vo.ShopRemoteCallResult;
import com.lvmama.comm.pet.vo.ShopUser;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.shop.dao.ShopOrderDAO;

/**
 * 积分商城订单服务接口实现类
 * @author ganyingwen
 *
 */
public class ShopOrderServiceImpl implements ShopOrderService {
	/**
	 * 抽奖码字长
	 */
	private static final int LUCKY_CODE_LENGTH = 10;

    /**
     * 最大随机数
     */
    private static final int MAX_RAMVAL = 10;
    /**
     * 最小值
     */
    private static final int MIN_VAL = 48;    
	/**
	 * 日志输入器
	 */
	private static final Log LOG = LogFactory.getLog(ShopOrderServiceImpl.class);
	/**
	 * 商城订单数据库操作接口
	 */
	@Autowired
	private ShopOrderDAO shopOrderDAO;
	/**
	 * 积分商城产品服务
	 */
	@Autowired
	private ShopProductService shopProductService;
	@Autowired
	private ShopProductZhuantiService shopProductZhuantiService;
	
	/**
	 * 积分用户服务
	 */
	@Autowired
	private ShopUserService shopUserService;
	@Autowired	
	private UserUserProxy userUserProxy;


	@Override
	public Long insert(final ShopOrder shopOrder) {
		return shopOrderDAO.insert(shopOrder);
	}

	@Override
	public List<ShopOrder> queryShopOrder(final Map<String, Object> parameters) {
		return shopOrderDAO.queryShopOrder(parameters);
	}

	@Override
	public List<ShopOrderVO> queryShopOrderVO(final Map<String, Object> parameters) {
		return (List<ShopOrderVO>)shopOrderDAO.queryShopOrderVO(parameters);
	}
	
	@Override
	public ShopOrder queryShopOrderByKey(final Long orderId) {
		return (ShopOrder) shopOrderDAO.queryShopOrderByKey(orderId);
	}

	@Override
	public int updata(final ShopOrder shopOrder) {
		return shopOrderDAO.updata(shopOrder);
	}

	@Override
	public Long orderCount(final Map<String, Object> parameters) {
		return shopOrderDAO.orderCount(parameters);
	}
	
//	@Override
//	public ShopRemoteCallResult createShopOrder(final Long userId, final Long productId,
//			final int quantity, final ShopUserInfo info) {
//		ShopRemoteCallResult callResult = this.checkPointToChangeProduct(userId, productId, quantity);
//		if (callResult.isResult()) {
//			ShopOrder order = new ShopOrder();
//			ShopProduct product = shopProductService.queryByPk(productId);
//
//			//创建订单信息
//			order.setActualPay(product.getPointChange() * quantity);
//			order.setOughtPay(product.getPointChange() * quantity);
//			order.setUserId(userId);
//			order.setProductId(product.getProductId());
//			order.setProductName(product.getProductName());
//			order.setProductType(product.getProductType());
//			order.setQuantity(quantity);
//			order.setAddress(info.getAddress());
//			order.setMobile(info.getMobile());
//			order.setName(info.getName());
//			order.setZip(info.getZip());
//			//订单状态处理
//			if (Constant.SHOP_PRODUCT_TYPE.PRODUCT.name().equals(product.getProductType())) {
//				order.setOrderStatus(Constant.ORDER_STATUS.UNCONFIRM.name());
//			} else if (Constant.SHOP_PRODUCT_TYPE.COUPON.name().equals(product.getProductType())) {
//				order.setOrderStatus(Constant.ORDER_STATUS.FINISHED.name());
//			} else {
//				order.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
//			}
//
//			shopProductService.reduceStocks(productId, quantity);
//			this.insert(order);
//			shopUserService.reducePoint(userId, "POINT_FOR_CHANGE_PRODUCT",
//					Long.valueOf(0 - (product.getPointChange() * quantity)), String.valueOf((order.getOrderId())));
//
//			callResult.setObject(order.getOrderId());
//
//			if (Constant.SHOP_PRODUCT_TYPE.COUPON.name().equals(product.getProductType())) {
//				bindingCouponAndUser(userId, order, ((ShopProductCoupon) product).getCouponId());
//			}
//		}
//		return callResult;
//	}
	
	@Override
	public ShopRemoteCallResult checkPointToChangeProduct(final Long userId,
			final Long productId, final int quantity) {
		if (null == productId || null == userId) {
			LOG.error("Either product identifity or user identifity is empty, cann't verify!");
			return new ShopRemoteCallResult(false, ShopProductService.ABSENT_NECESSARY_DATA, "产品标识或者用户标识为空，无法进行校验");
		}
		//产品有关条件
		ShopProduct product = shopProductService.queryByPk(productId);
		if (null == product) {
			LOG.error("Cann't find product, change fail!!");
			return new ShopRemoteCallResult(false, ShopProductService.PRODUCT_CANNOT__BEFOUND, "无法找到需要兑换的产品，兑换校验失败");
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("find product:" + product);
			}
		}
		if (!"Y".equals(product.getIsCanSell())) {
			LOG.error("Product has offlined, change fail!");
			return new ShopRemoteCallResult(false, ShopProductService.PRODUCT_HAS_OFFLIINED, "产品已经下线，无法进行兑换");
		}
		if (null == product.getStocks() || product.getStocks().longValue() < quantity) {
			LOG.error("Absent for product's stock, change fail。stock：" + product.getStocks() + ", want change number:" + quantity);
			return new ShopRemoteCallResult(false, ShopProductService.PRODUCT_LESS_STOCK, "产品已经下线，无法进行兑换");
		}
		
		//用户有关条件
		ShopUser shopUser = shopUserService.getUserByPK(userId);
		if (null == shopUser) {
			LOG.error("Cann't find user");
			return new ShopRemoteCallResult(false, ShopProductService.USER_CANNOT_BE_FOUND, "无法找到用户，无法进行兑换");
		}
		
		//处理用户有专题优惠的情况
		long userPoint = getPayPoint(userId, product, quantity);
		
		if (shopUser.getPoint().longValue() >= userPoint) {
			LOG.error("User's point:" + shopUser.getPoint().longValue() + "; need point:" + userPoint);
			return new ShopRemoteCallResult(true, ShopProductService.SUCCESS, null);
			
		} else {
			LOG.error("User has not enough point to change，user's point:"
					+ shopUser.getPoint().longValue() + "; need point:" + userPoint);
			return new ShopRemoteCallResult(false, ShopProductService.USER_LESS_POINT, "用户积分不够");
		}
	}
	
	public long getPayPoint(final Long userId, final ShopProduct product, final int quantity){
		
		long payPoint = product.getPointChange().longValue() * quantity;
		long couponPoint = 0;
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productId", product.getProductId());
		parameters.put("userId", userId);
		List<ShopProductZhuanti> list = shopProductZhuantiService.queryList(parameters);
		if(list != null && list.size() > 0){
			int couponNum = 0;
			int noCouponNum = 0;
			if(quantity > list.size()){
				couponNum = list.size();
				noCouponNum = quantity - couponNum;
			}else{
				couponNum = quantity;
			}
			for(int i = 0; i < couponNum; i++){
				couponPoint = couponPoint + product.getPointChange().longValue() * list.get(i).getChangeRate()/100;
			}
			payPoint = couponPoint + product.getPointChange().longValue() * noCouponNum;
		}
		return payPoint;
	}
	
	public void deleteShopProductZhuanti(final Long userId, final ShopProduct product, final int quantity){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productId", product.getProductId());
		parameters.put("userId", userId);
		List<ShopProductZhuanti> list = shopProductZhuantiService.queryList(parameters);
		
		if(list != null && list.size() > 0){
			int couponNum = 0;
			if(quantity > list.size()){
				couponNum = list.size();
			}else{
				couponNum = quantity;
			}
			for(int i = 0; i < couponNum; i++){
				shopProductZhuantiService.deleteByKey(list.get(i).getShopProductZhuantiId());
			}
		}
	}
	
	@Override
	public String luckyDraw(final Long userId, final Long productId) {
		ShopRemoteCallResult callResult = checkPointToChangeProduct(userId, productId, 1);
		
		if (callResult.isResult()) {
			ShopProduct product = shopProductService.queryByPk(productId);
			if (Constant.SHOP_CHANGE_TYPE.RAFFLE.name().equals(product.getChangeType())) {
				Integer rate =  new Integer(100000);
				int winningRate=1;
				if(product.getWinningRate() != null){
					winningRate=(int) (product.getWinningRate()*rate);
				}
				int random=new Random().nextInt(rate);
				if (winningRate>random) {
					return getRandomString(LUCKY_CODE_LENGTH);
				} else {
					//用户未能中奖，直接扣积分
					shopUserService.reducePoint(userId, "POINT_FOR_RAFFLE_PRODUCT",
							Long.valueOf(0 - (product.getPointChange())), null);
					return null;
				}
			} else {
				LOG.debug("兑换的物品根本不是抽奖类产品，直接返回");
			}
		}
		return null;
	}
	
    /**
     * 产生随即数
     * @param random random
     * @param len len
     * @return 随即数
     */
    private static String getRandomString(final int len) {
	    java.util.Random rd = new java.util.Random();
	    StringBuffer sb = new StringBuffer();
	    int rdGet; //取得随机数
	    char ch;

	    for (int i = 0; i < len; i++) {
	        rdGet = Math.abs(rd.nextInt(MAX_RAMVAL)) % MAX_RAMVAL + MIN_VAL; // 产生48到57的随机数(0-9的键位值)
	        ch = (char) rdGet;
	        sb.append(ch);
	    }
	    return sb.toString();
	}
    
	/**
	 * 创建优惠券类产品
	 * @param userId 用户标识
	 * @param order 订单
	 * @param couponId 优惠券号
	 */
//	private void bindingCouponAndUser(final Long userId, final ShopOrder order, final Long couponId) {
//		if (null == userId || null == order || null == couponId) {
//			return;
//		}
//		for (int i = 0; i < order.getQuantity(); i++) {
//			String code = tempBackendManagerRemoteService.generateNewMarkCouponCode(couponId);
//			if (LOG.isDebugEnabled()) {
//				LOG.debug("生成优惠券号码:" + code);
//			}
//			UserUser user = userUserProxy.getUserUserByPk(userId);
//			if (null != user) {
//				tempBackendManagerRemoteService.bindingUserAndCouponCode(user.getUserId(), code);
//			}
//		}
//		order.setOrderStatus(Constant.ORDER_STATUS.FINISHED.name());
//		updata(order);
//	}    
}
