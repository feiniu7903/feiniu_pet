package com.lvmama.back.sweb.ord;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrderInfoDTO;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Coupon;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.vo.favor.FavorResult;
import com.lvmama.comm.pet.vo.favor.OrderFavorStrategy;
import com.lvmama.comm.pet.vo.mark.ProductCoupon;
import com.lvmama.comm.pet.vo.mark.ValidateCodeInfo;
import com.lvmama.comm.utils.Pair;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.Constant;
//import com.lvmama.comm.bee.service.ICouponService;

/**
 * 生成订单后,使用优惠券的处理.
 * @author kevin
 * @version  1.0  2011-10-19
 *  
 */
@ParentPackage("json-default")
@Results( { 	
	@Result(type="json",name="checkOrderCode",params={"includeProperties","info.*"})
})
public class OrderCouponAction  extends BaseAction{
	
	private static final String SUCCESS = "success";
	/**
	 * 订单ID.
	 */
	private Long orderId;
	/**
	 * 订单服务接口.
	 */
	private OrderService orderServiceProxy;
	/**
	 * 销售产品优惠券列表.
	 */
	private List<ProductCoupon> productCouponList;
	private List<MarkCoupon> partyCouponList;
	
	private String productId;
	
	private String couponId;
	
	private String code;
	
	private ValidateCodeInfo info;
	/**
	 * 销售产品DAO.
	 */
	protected ProdProductBranchService prodProductBranchService;
	private MetaProductBranchService metaProductBranchService;
	private FavorService favorService;
	protected TopicMessageProducer orderMessageProducer;
	/**
	 * 改订单是否可以
	 */
	private boolean showYouHui = true;
	
	private MarkCouponService markCouponService;
	
	/**
	 * 判断订单是否可以使用此优惠券.
	 * @return
	 */
	@Action(value="/orderCoupon/choseCoupon")
	public String choseCoupon(){
		info=new ValidateCodeInfo();
		if(orderId!=null&&orderId>0){
			OrdOrder  order=orderServiceProxy.queryOrdOrderByOrderId(orderId);
			this.isOrderCoupon(order, couponId, code);
		}
		return "checkOrderCode";
	}
	/**
	 * 判断某个优惠券在这个订单中是否可以使用.
	 * @param order 
	 *          订单.
	 * @param code
	 *          优惠券号码.
	 * @return
	 */
	private boolean isOrderCoupon(final OrdOrder order, final String couponId, final String code) {
		boolean iskey=false;
		
		OrderFavorStrategy strategy = this.favorService.validateCoupon(StringUtils.isNotEmpty(couponId) ? Long.parseLong(couponId) : null, code, order.getMainProduct().getProductId(), order.getMainProduct().getSubProductType(), info);
		if (strategy != null && strategy.isApply(order, 0L)) {
			Long discount = strategy.getDiscountAmount(order, 0L);
			info.setCouponId(strategy.getMarkCoupon().getCouponId());
			info.setValid(true);
			info.setKey(Constant.COUPON_INFO.OK.name());	
			info.setYouhuiAmount(discount);
			info.setPaymentChannel(strategy.getMarkCoupon().getPaymentChannel());
			iskey=true;
			String message = "验证成功可以使用";
			info.setValue(message);
		} else {
			info.setCouponId(StringUtils.isNotEmpty(couponId) ? Long.parseLong(couponId) : null);
			info.setValid(false);
			info.setKey("ERROR_INFO");
			info.setYouhuiAmount(0l);
			info.setPaymentChannel("");
			iskey=false;
			String message = "验证不可以使用";
			info.setValue(message);
		}
		return iskey;
	}
	
//	private boolean isOrderCoupon(final OrdOrder order,final String code){
//		boolean iskey=false;
//		Long orderQuantity = 0L;
//		
//		for (OrdOrderItemProd ordOrderItemProd : order.getOrdOrderItemProds()) {
//			//判断优惠券是否在可用时间里面.
//				//
//				Long mainPorductId;
//				if(!ordOrderItemProd.isOtherProductType() && !ordOrderItemProd.isAdditionalProduct()){
//					info = this.couponService.checkCodeValid(order.getUserId(), code);
//					mainPorductId = ordOrderItemProd.getProductId();
//					List<ProdProductBranchItem> branchItems = this.prodProductBranchService.selectBranchItemByBranchId(ordOrderItemProd.getProdBranchId());
//					for (ProdProductBranchItem ppbi : branchItems) {
//						MetaProductBranch meta = this.metaProductBranchService.getMetaBranch(ppbi.getMetaBranchId());
//						if (!meta.isAdditional()){
//							orderQuantity += ordOrderItemProd.getQuantity()*ppbi.getQuantity()*(meta.getAdultQuantity()+meta.getChildQuantity());
//						}
//					}
// 
//					this.info = this.couponService.validateCoupon(mainPorductId, code, order.getUserId(), order.getOughtPay(), orderQuantity, ordOrderItemProd.getSubProductType());
//				}
//			}
//		if(Constant.COUPON_INFO.OK.name().equals(info.getKey())){
//			iskey=true;
//			String message = "验证成功可以使用";
//			info.setValue(message);
//		}
//		return iskey;
//	}
	
	/**
	 * 用优惠券修改价格.
	 * @return
	 */
	@Action(value="/orderCoupon/saveCoupon")
	public void updateOrderPrice(){
		info=new ValidateCodeInfo();
		JSONResult result=new JSONResult();
		try{
			OrdOrder  order=orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if(!StringUtils.isEmpty(code)){
				MarkCoupon markCoupon = markCouponService.selectMarkCouponByCouponCode(code, true);
				if(markCoupon == null){
					result.raise("订单不能使用优惠券!!");
					result.output(getResponse());
					return;
				}else{
					couponId = String.valueOf(markCoupon.getCouponId());
				}
			}
			this.isOrderCoupon(order, couponId, code);
			if("OK".equals(info.getKey()) && !order.isPaymentSucc()){
				OrderInfoDTO orderInfo=new OrderInfoDTO();
				BeanUtils.copyProperties(order,orderInfo);
				
				OrderFavorStrategy strategy = this.favorService.validateCoupon(StringUtils.isNotBlank(couponId) ? Long.parseLong(couponId) : null, code, order.getMainProduct().getProductId(), order.getMainProduct().getSubProductType(), info);
				if (strategy != null && strategy.isApply(order, 0L)) {
					Long discount = strategy.getDiscountAmount(order, 0L);
					// 构建订单优惠金额明细
					OrdOrderAmountItem couponAmountItem = markCouponAmountItem(order.getOrderId(), strategy.getMarkCoupon(), discount);
					orderInfo.setMarkCoupon(strategy.getMarkCoupon());
					orderInfo.setMarkCouponCode(strategy.getMarkCouponCode());
					orderInfo.setCouponAmountItem(couponAmountItem);
					//orderInfo.setOughtPay(order.getOughtPay() - discount); 
					orderInfo.setPaymentChannel(strategy.getMarkCoupon().getPaymentChannel());
					// 保存优惠券
					BuyInfo buyInfo = new BuyInfo();
					FavorResult favorResult = new FavorResult();
					favorResult.addOrderFavorStrategy(strategy);
					buyInfo.setFavorResult(favorResult);
					orderInfo.setBuyInfo(buyInfo);
					if(orderServiceProxy.updateOrderPriceByCoupon(orderInfo,getOperatorName())){
						OrdOrder newOrder=orderServiceProxy.queryOrdOrderByOrderId(orderId);
						orderMessageProducer.sendMsg(MessageFactory.newCouponUsedMessage(newOrder.getOrderId()));
						if(newOrder.getOughtPay() <= newOrder.getActualPay()){
							orderMessageProducer.sendMsg(MessageFactory.newOrderPay0YuanMessage(newOrder.getOrderId()));
						}
					}					
				}else{
					result.raise("订单不能使用该优惠券!!");
				}
				// 处理优惠券
//		        Coupon coupon = new Coupon();
//		        coupon.setChecked(Boolean.TRUE.toString());
//		        coupon.setCode(info.getCode());
//		        coupon.setCouponId(info.getCouponId());
//	        
//				final MarkCoupon markCoupon = this.couponService.loadMarkCouponByPk(coupon.getCouponId());
//				final Long productAmount = this.couponService.getMarkCouponProductPrice(order, markCoupon);
//				if (productAmount != 0) {
//					markCoupon.setAmount(productAmount);
//				}				
//				final Pair<Long, MarkCoupon> pair= this.couponService.countOrderCouponAmount(order.getOughtPay(),order.calcTotalPersonQuantity(), markCoupon);
//				if(pair.isFail()){
//					throw new IllegalArgumentException(pair.getMsg());
//				}
//				final MarkCouponCode markCouponCode = this.couponService.getMarkCouponCodeByCouponIdAndCode(coupon.getCouponId(),coupon.getCode());
				
	       }else{
				result.raise("订单不能使用优惠券!!");
	       }
		}catch(Exception ex){
			result.raise(new JSONResultException(ex));
		}
		result.output(getResponse());
	}

	/**
	 * 构建订单优惠金额明细.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param markCoupon
	 *            优惠券
	 * @return 订单优惠金额明细{@link OrdOrderAmountItem}
	 */
	static OrdOrderAmountItem markCouponAmountItem(final Long orderId,
			final MarkCoupon markCoupon, final Long actualDiscount) {
		final OrdOrderAmountItem couponAmountItem = new OrdOrderAmountItem();
		couponAmountItem.setItemAmount(actualDiscount);
		couponAmountItem.setOrderId(orderId);
		couponAmountItem.setItemName(markCoupon.getCouponName());
		couponAmountItem
				.setOderAmountType(Constant.ORDER_AMOUNT_TYPE.ORDER_COUPON_AMOUNT
						.name());
		return couponAmountItem;
	}

	
	/**
	 * 获取订单ID.
	 * @return
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * 设置订单ID.
	 * @param orderId
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * 获取订单服务接口.
	 * @return
	 */
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	/**
	 * 设置订单服务接口.
	 * @param orderServiceProxy
	 */
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	/**
	 * 获取销售产品优惠券列表.
	 * @return
	 */
	public List<ProductCoupon> getProductCouponList() {
		return productCouponList;
	}

	/**
	 * 设置销售产品优惠券列表.
	 * @param productCouponList
	 */
	public void setProductCouponList(List<ProductCoupon> productCouponList) {
		this.productCouponList = productCouponList;
	}
	
	public List<MarkCoupon> getPartyCouponList() {
		return partyCouponList;
	}

	public void setPartyCouponList(List<MarkCoupon> partyCouponList) {
		this.partyCouponList = partyCouponList;
	}
	
	public void setFavorService(FavorService favorService) {
		this.favorService = favorService;
	}
	public boolean isShowYouHui() {
		return showYouHui;
	}

	public void setShowYouHui(boolean showYouHui) {
		this.showYouHui = showYouHui;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
 
	public ValidateCodeInfo getInfo() {
		return info;
	}

	public void setInfo(ValidateCodeInfo info) {
		this.info = info;
	}
 
	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}
	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}
	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}
	
}
