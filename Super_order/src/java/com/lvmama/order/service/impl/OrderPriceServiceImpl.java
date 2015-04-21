package com.lvmama.order.service.impl;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdJourneyProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.bee.vo.ord.PriceInfo;
import com.lvmama.comm.pet.vo.favor.FavorResult;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.service.OrderPriceService;
import com.lvmama.prd.dao.ProdJourneyProductDAO;
import com.lvmama.prd.logic.ProductTimePriceLogic;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Map;


/**
 * 前台页面价格计算.
 * @author 
 *
 */
public class OrderPriceServiceImpl extends OrderCheckService implements
		OrderPriceService {

	private static Logger logger = Logger.getLogger(OrderPriceServiceImpl.class);

	private ProductTimePriceLogic productTimePriceLogic;
	
	private ProdJourneyProductDAO prodJourneyProductDAO;
	

	private OrderDAO orderDAO;


	public PriceInfo countPrice(BuyInfo buyInfo)
	{
		PriceInfo priceInfo = new PriceInfo();
		try{
			OrdOrder order = new OrdOrder();
			order.setVisitTime(null);
			Map<Long, OrdOrderItemProd> orderItemProdMap = initialOrdItemProdList(buyInfo, order);
			TotalProdPriceVo totalProdPriceVo=countOrderItemProdPrice(order, orderItemProdMap,buyInfo.hasSelfPack(),buyInfo.isTodayOrder());
			Long orderMarketPrice =totalProdPriceVo.getTotalOrderItemProdMarketPrice();
            Long orderDiscountPrice=totalProdPriceVo.getTotalOrderDiscountPrice();
			Long orderPrice = order.getOughtPay();
			Long bonus = order.getCashRefund();

            if(Constant.initCouponEnabled(order.getMainProduct().getProductId())) {

                if (!buyInfo.hasSelfPack() && null != buyInfo.getFavorResult()) {
                    countFavor(order, buyInfo.getFavorResult());
                    priceInfo.setValidateBusinessCouponInfoList(buyInfo.getFavorResult().getValidateBusinessCouponInfoList());
                    priceInfo.setInfo(buyInfo.getFavorResult().getValidateCodeInfo());
                }
            }

            //使用现金账户的金额
            if(buyInfo.getCashValue()!=null && buyInfo.getCashValue()>0){
                order.setOughtPay(order.getOughtPay() - PriceUtil.convertToFen(buyInfo.getCashValue()));
                priceInfo.setCashValue(buyInfo.getCashValue());
            }
			Long oughtPay = order.getOughtPay();
			
			Long coupon = oughtPay - orderPrice;
			
			priceInfo.setBonus(bonus);
			priceInfo.setCoupon(Math.abs(coupon));
			priceInfo.setMarketPrice(orderMarketPrice);
			priceInfo.setOughtPay((oughtPay<0?0:oughtPay));
			priceInfo.setPrice(orderPrice);
			/**
			 * 设定订单的全部优惠金额
			 */
			priceInfo.setOrderDiscountPrice(orderDiscountPrice);
			/**
			 * 前台下单计算的订单总金额将去掉附加产品的金额
			 */
			/*
			Long couponPay = OrderUitl.getEnjoyCouponamount(orderItemProdMap.values());
			priceInfo.setCouponPay(couponPay);
			*/
			priceInfo.setOrderQuantity(order.calcTotalPersonQuantity());
		}catch(OrderPriceException ex){
			priceInfo.sendError(ex.getMessage());//错误信息记录在此，做为给用户提示使用
		}
		return priceInfo;
	}
	
	
	
	

	/**
	 * 根据产品的价格计算订单的价格.
	 *  以前的逻辑:判断产品是不是附加产品 "true".equals(product.getAdditional(),true的话取采购产品的价格,false则取采购产品的时间价格表的价格。
     *  现在的逻辑:所有的产品都是取时间价格表的价格
	 * @param order
	 *         订单信息
	 * @param orderItemProdMap
	 *         产品列表 
	 * @return 
	 */
	private TotalProdPriceVo countOrderItemProdPrice(OrdOrder order, Map<Long, OrdOrderItemProd> orderItemProdMap,boolean selfPack,boolean isTodayOrder) {
		Long totalOrderItemProdMarketPrice = 0L;
		Long totalOrderItemProdPrice = 0L;
//		Long totalOrderCashRefund = 0L;
//		Long totalOrderMetaSettlement = 0L;
		Long totalOrderDiscountPrice=0L;
		
		for (Long key:orderItemProdMap.keySet()) 
		{
			OrdOrderItemProd item = orderItemProdMap.get(key);
			if(item.getQuantity() > 0) 
			{
				ProdProduct product = prodProductDAO.selectByPrimaryKey(item.getProductId());
				if(product == null)
				{
					logger.info("countPrice fail: ProdProduct is null by ProductId: " + product.getProductId());
					throw new OrderPriceException("产品ID:"+product.getProductId()+" 已经下线");
				}
				if(selfPack && item.hasDefault()){
					continue;
				}
				item.setProductName(product.getProductName());
				item.setProductType(product.getProductType());
				item.setMarketPrice(product.getMarketPrice());
				item.setWrapPage(product.getWrapPage());
				item.setSendSms(product.getSendSms());
				
				
				//如果需要计算
				long discount=0L;
				if(selfPack&&item.getJourneyProductId()!=null){
					ProdJourneyProduct pjp=prodJourneyProductDAO.selectByPrimaryKey(item.getJourneyProductId());
					discount=pjp.getDiscount();
				}
				TimePrice timePrice = null;
				
				if(isTodayOrder){
					timePrice = productTimePriceLogic.calcCurrentProdTimePrice(item.getProdBranchId(), DateUtil.getDayStart(new Date()));
				} else {
					timePrice = productTimePriceLogic.calcProdTimePrice(item.getProdBranchId(), item.getVisitTime());
				}
				
				if(timePrice != null){
					item.setPrice(timePrice.getPrice()-discount);//减去优惠
				}else{
					logger.info("countPrice fail: ProdProduct's TimePrice is null by ProductId: "
									+ product.getProductId() + " and VisitTime: " + item.getVisitTime());
					throw new OrderPriceException("产品 “"+product.getProductName()+"” 类别:"+item.getProdBranchId()+" "+DateFormatUtils.format(item.getVisitTime(), "yyyy-MM-dd")+" 已经售空");
				}
				
				//非超级自由行的主产品才计算
				if(!(selfPack&&item.hasDefault())){
//					if("true".equals(product.getPayToLvmama())) {
//						if(CollectionUtils.isNotEmpty(item.getOrdOrderItemMetas())) {
//							for (OrdOrderItemMeta timeInfo : item.getOrdOrderItemMetas()) {
//									totalOrderMetaSettlement += timeInfo.getQuantity() * timeInfo.getSettlementPrice();
//							}
//						}
//					}
					
					// 酒店
					if(CollectionUtils.isNotEmpty(item.getTimeInfoList())) {
						for (OrdTimeInfo timeInfo : item.getTimeInfoList()) {
							final TimePrice priceInfo=productTimePriceLogic.calcProdTimePrice(item.getProdBranchId(), timeInfo.getVisitTime());
							long price=priceInfo.getPrice()-discount;//减去优惠
							totalOrderItemProdPrice += timeInfo.getQuantity() * price;
//							if(!"true".equals(product.getPayToLvmama())) {
//								totalOrderMetaSettlement += timeInfo.getQuantity() * price;
//							}
							totalOrderItemProdMarketPrice += timeInfo.getQuantity() * priceInfo.getMarketPrice();
							totalOrderDiscountPrice+=timeInfo.getQuantity()*discount;
						}
					}else{
//						if(!"true".equals(product.getPayToLvmama())) {
//							totalOrderMetaSettlement += item.getQuantity() * item.getPrice();
//						}
						totalOrderItemProdPrice = totalOrderItemProdPrice + item.getQuantity() * item.getPrice();
						totalOrderItemProdMarketPrice = totalOrderItemProdMarketPrice + item.getQuantity() * item.getMarketPrice();
						totalOrderDiscountPrice+=item.getQuantity()*discount;
					}
				}
			}
		}
//		totalOrderCashRefund = BonusConfigData.getBonusByProfit(totalOrderItemProdPrice-totalOrderMetaSettlement,BonusConfigData.ORDER_PROFIT_ISSUE);
//		order.setCashRefund(totalOrderCashRefund);
		order.setOughtPay(totalOrderItemProdPrice);
		TotalProdPriceVo totalProdPrice=new TotalProdPriceVo();
		totalProdPrice.setTotalOrderItemProdMarketPrice(totalOrderItemProdMarketPrice);
		totalProdPrice.setTotalOrderDiscountPrice(totalOrderDiscountPrice);
		return totalProdPrice;
	}
	
	private void countFavor(OrdOrder order, FavorResult favorResult) {
		try{
			order.setOughtPay(order.getOughtPay() - favorResult.sumAllFavorDiscountAmount(order));	
		}catch(Exception ex){
			logger.error(ex, ex);
		}
	}

//	private void countCoupon(OrdOrder order, Coupon coupon, Long totalOrderItemProdPrice, Map<Long, OrdOrderItemProd> orderItemProdMap) 
//	{
//		Long quantity = 0L;
//		long orderAmount = totalOrderItemProdPrice.longValue();
//		
//		if (coupon != null && coupon.getCouponId() != null
//				&& "true".equals(coupon.getChecked())) {
//			/**
//			 * 优惠对象
//			 */
//			MarkCoupon mc = markCouponDAO.selectByPrimaryKey(coupon
//					.getCouponId());
//			/**
//			 * 订单包含的人数
//			 */
//			quantity = order.calcTotalPersonQuantity();
//			/**
//			 * 获得当前订单主产品与优惠关联的优惠金额 有金额使用关联产品的金额，没有使用优惠活动的默认金额
//			 */
//			Long productAmount = couponLogic.getMarkCouponProductPrice(order,
//					mc);
//			if (productAmount != 0) {
//				/**
//				 * 更新优惠金额
//				 */
//				mc.setAmount(productAmount);
//			}
//
//			/**
//			 * 处理优惠金额
//			 */
//			Pair<Long, MarkCoupon> pair = couponLogic.countOrderCouponAmount(
//					orderAmount, quantity, mc);
//			orderAmount = pair.getFirst();
//		}
//		// 订单应付金额
//		order.setOughtPay(orderAmount);
//	}


	/**
	 * 查询订单总金额.
	 *
	 * @param params
	 *            用户ID，订单状态
	 * @return 总金额
	 * 
	 */
	public float queryOrdersAmountByParams(Map<String, Object> params) {
		return orderDAO.queryOrdersAmountByParams(params);
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}


	/**
	 * @param prodJourneyProductDAO the prodJourneyProductDAO to set
	 */
	public void setProdJourneyProductDAO(ProdJourneyProductDAO prodJourneyProductDAO) {
		this.prodJourneyProductDAO = prodJourneyProductDAO;
	}
	/**
	 * 订单的市场价，优惠价格
	 */
	public  class TotalProdPriceVo{
		//订单的市场价
		private Long totalOrderItemProdMarketPrice ;
		//优惠价格
		private Long totalOrderDiscountPrice ;
		public Long getTotalOrderItemProdMarketPrice() {
			return totalOrderItemProdMarketPrice;
		}
		public void setTotalOrderItemProdMarketPrice(Long totalOrderItemProdMarketPrice) {
			this.totalOrderItemProdMarketPrice = totalOrderItemProdMarketPrice;
		}
		public Long getTotalOrderDiscountPrice() {
			return totalOrderDiscountPrice;
		}
		public void setTotalOrderDiscountPrice(Long totalOrderDiscountPrice) {
			this.totalOrderDiscountPrice = totalOrderDiscountPrice;
		}
	}
	public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}
}
