package com.lvmama.back.message;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.service.favor.FavorOrderService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mark.MarkCouponUsage;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.vo.favor.FavorProductResult;
import com.lvmama.comm.vo.Constant;

public class CouponSetProcesser implements MessageProcesser{
	
	private FavorOrderService favorOrderService;
	private OrderService orderServiceProxy;
	private MarkCouponService markCouponService;
	private TopicMessageProducer orderMessageProducer;
	private Logger log = Logger.getLogger(this.getClass());
	private FavorService favorService;
	@Override
	public void process(Message message) {
		if(message.isOrderCancelMsg()){//订单取消，回滚优惠券
			log.info("get order cancel message, start reset coupon");
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			if(order.isPayToLvmama()){
				Long orderId = order.getOrderId();
				log.info("is pay to lvmama");
				log.info("begin reset coupn "+orderId);
				try{
					List<MarkCouponUsage> mcusageList = favorOrderService.getMarkCouponUsageByObjectIdAndType(orderId, 
							Constant.OBJECT_TYPE.ORD_ORDER.name());
					if(mcusageList != null && mcusageList.size() > 0){
						for(int i = 0; i < mcusageList.size(); i++){
							MarkCouponUsage mcusage = mcusageList.get(i);
							MarkCouponCode mcc =  markCouponService.selectMarkCouponCodeByPk(mcusage.getCouponCodeId());
							MarkCoupon mc =  markCouponService.selectMarkCouponByPk(mcc.getCouponId());
							if(mc.isBCoupon() && mcc.getUsed().equals("true")){//B类才需要回滚
								mcc.setUsed("false");
								markCouponService.updateMarkCouponCode(mcc, false);
								log.info("reset order:"+orderId+", coupon："+mcc.getCouponCode()+",ID:"+mcc.getCouponCodeId());
							}
							//回滚已经使用了的优惠金额--只针对优惠活动
							if(mc.getMaxCoupon()!=-1 && !mc.isBCoupon() && "false".equals(mc.getWithCode()) && mcusage!=null && mcusage.getAmount()>0){
								if(mc.getUsedCoupon()!=null && mc.getUsedCoupon()>0 && mc.getUsedCoupon()>=mcusage.getAmount()){
									Long currentUsedCoupon=mcusage.getAmount();
									Map<String,Object> conditionMap=new HashMap<String,Object>();
									conditionMap.put("couponId",mc.getCouponId());//主键，根据主键做更新
									conditionMap.put("flag","subtract");//标识做回滚还是相加动作
									conditionMap.put("currentUsedCoupon", currentUsedCoupon);//当前优惠金额
									//更新已经使用了的优惠金额
									markCouponService.updateUsedCouponByMarkCoupon(conditionMap);
									log.info("reset markCouponId:"+mc.getCouponId()+",currentUsedCoupon:"+currentUsedCoupon);
								}else{
									log.info("usedCoupon:"+mc.getUsedCoupon()+",currentUsedCoupon:"+mcusage.getAmount()+"markCouponId:"+mc.getCouponId());
								}
							}
						}
					}
				}catch(Exception ex){
					ex.printStackTrace();
					log.error("reset coupon for order ："+orderId+" error:"+ex.getMessage());
				}
			}
		}else if(message.isOrderCreateMsg() || message.isCouponUsedMsg()){//订单创建，设置优惠券/产品优惠采购价明细
			log.info("get order create message, start set coupon");
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			if(order.isPayToLvmama()){
				Long orderId = order.getOrderId();
				log.info("is pay to lvmama");
				log.info("begin set coupn used"+orderId);
				try{
					//设置优惠券
					List<MarkCouponUsage> mcusageList = favorOrderService.getMarkCouponUsageByObjectIdAndType(orderId, 
							Constant.OBJECT_TYPE.ORD_ORDER.name());
					if(mcusageList != null && mcusageList.size() > 0){
						for(int i = 0; i < mcusageList.size(); i++){
							MarkCouponUsage mcusage = mcusageList.get(i);
							MarkCouponCode mcc =  markCouponService.selectMarkCouponCodeByPk(mcusage.getCouponCodeId());
							MarkCoupon mc =  markCouponService.selectMarkCouponByPk(mcc.getCouponId());
							if(mc.isBCoupon() && mcc.getUsed().equals("false")){//B类才需要设置已使用
								mcc.setUsed("true");
								markCouponService.updateMarkCouponCode(mcc, false);
								log.info("set order: "+orderId+", coupon:"+mcc.getCouponCode()+",ID:"+mcc.getCouponCodeId());
							}
							//设置已经使用了的优惠金额--只针对优惠活动
							if(mc.getMaxCoupon()!=-1 && !mc.isBCoupon() && "false".equals(mc.getWithCode())&& mcusage!=null && mcusage.getAmount()>0){
								Long currentUsedCoupon=mcusage.getAmount();
								Map<String,Object> conditionMap=new HashMap<String,Object>();
								conditionMap.put("couponId",mc.getCouponId());//主键，根据主键做更新
								conditionMap.put("flag","add");//标识做回滚还是相加动作
								conditionMap.put("currentUsedCoupon", currentUsedCoupon);//当前优惠金额
								//更新已经使用了的优惠金额
								markCouponService.updateUsedCouponByMarkCoupon(conditionMap);
								log.info("reset mark_coupon:"+mc.getCouponId()+",currentUsedCoupon:"+currentUsedCoupon);
							}
						}
					}
					
					//更新产品优惠后的采购产品子项
					List<FavorProductResult> favorProductResultList = favorService.getFavorMetaProductResultByOrderInfo(order);
					if(favorProductResultList != null && favorProductResultList.size() > 0){
						log.info("set order meta: "+orderId);
						favorOrderService.updateOrderItemMetaPriceByCoupon(order.getOrderId(), favorProductResultList);
					}
					
					List<Long> orderItemMetaIds = new ArrayList<Long>();
					//为了发送修改结算价的消息
					for(OrdOrderItemProd ordOrderItemProd: order.getOrdOrderItemProds()) {
						for(OrdOrderItemMeta ordOrderItemMeta : ordOrderItemProd.getOrdOrderItemMetas()) {
							for (FavorProductResult result : favorProductResultList) {
								if(ordOrderItemMeta.getMetaProductId().equals(result.getProductId()) 
										&& ordOrderItemMeta.getMetaBranchId().equals(result.getProductBranchId())) {
									orderItemMetaIds.add(ordOrderItemMeta.getOrderItemMetaId());
									break;
								}
							}
						}
					}
					if (!orderItemMetaIds.isEmpty()) {
						orderMessageProducer.sendMsg(MessageFactory.newModifySettlementPricesMessage(orderItemMetaIds, "System"));
					}
					
				}catch(Exception ex){
					ex.printStackTrace();
					log.error("set coupon for order ："+orderId+" error:"+ex.getMessage());
				}
			}
		}
	}

	
	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setFavorOrderService(FavorOrderService favorOrderService) {
		this.favorOrderService = favorOrderService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setFavorService(FavorService favorService) {
		this.favorService = favorService;
	}


	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

}
