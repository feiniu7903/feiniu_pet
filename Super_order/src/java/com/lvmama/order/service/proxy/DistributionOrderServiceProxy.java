package com.lvmama.order.service.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.distribution.DistributionTuanCoupon;
import com.lvmama.comm.bee.po.distribution.DistributionTuanCouponBatch;
import com.lvmama.comm.bee.po.distribution.DistributorTuanInfo;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.distribution.DistributionTuanCouponService;
import com.lvmama.comm.bee.service.distribution.DistributionTuanService;
import com.lvmama.comm.bee.service.ord.DistributionOrderService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.vo.Constant;

public class DistributionOrderServiceProxy implements DistributionOrderService {

	private static final Log log = LogFactory.getLog(DistributionOrderServiceProxy.class);
	
	private DistributionTuanService distributionTuanService;
	private OrderService orderServiceProxy;
	private UserUserProxy userUserProxy;
	private PayPaymentService payPaymentService;
	private TopicMessageProducer resourceMessageProducer;
	private DistributionTuanCouponService distributionTuanCouponService;
	private final String BOOKER="银联旅游卡分销专用";//银联旅游卡分销专用
	private final String channel=Constant.CHANNEL.DIST_YUYUE.name();
	
	public OrdOrder createOrderByCouponCode(final BuyInfo buyInfo,List<String> couponCodes){
		if(buyInfo==null){
			log.info("分销券码下单失败 ,购买信息为 null");
			return null;
		}
		if(CollectionUtils.isEmpty(couponCodes)){
			log.info("分销券码下单失败 ,券码为 null");
			return null;
		}
		List<DistributionTuanCouponBatch> distCBs = getDistributionTuanCouponInfo(couponCodes);
		if(distCBs==null){
			return null;
		}
		UserUser user = userUserProxy.getUsersByIdentity(BOOKER,UserUserProxy.USER_IDENTITY_TYPE.USER_NAME);
		buyInfo.setUserId(user.getUserNo());
		buyInfo.setChannel(channel);
		buyInfo.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
		OrdOrder order = create(buyInfo,distCBs);
		return order;
	}

	private synchronized List<DistributionTuanCouponBatch> getDistributionTuanCouponInfo(
			List<String> couponCodes) {
		List<DistributionTuanCouponBatch> list = new ArrayList<DistributionTuanCouponBatch>();
		for(String code:couponCodes){
			DistributionTuanCouponBatch dtb = distributionTuanService.getTuanCouponByCode(code);
			if(dtb==null || dtb.getDistributionTuanCoupon()==null){
				log.info("分销券码查询无效 ,对象为 null");
				return null;
			}
			if(!Constant.DISTRIBUTION_TUAN_COUPON_STATUS.NORMAL.name().equalsIgnoreCase(dtb.getDistributionTuanCoupon().getStatus())){
				log.info("分销券码非正常状态 ,状态为：" + dtb.getDistributionTuanCoupon().getStatus());
				return null;
			}
			if(new Date().after(DateUtil.dsDay_Date(dtb.getValidEndTime(), 1))){
				log.info("分销券码已过期 ,最晚时间" + DateUtil.formatDate(dtb.getEndTime(), "yyyy-MM-dd"));
				return null;
			}
			list.add(dtb);
		}
		DistributionTuanCouponBatch batch = list.get(0);
		for(DistributionTuanCouponBatch b:list){
			if(b.getBranchId().equals(batch.equals(batch.getBranchId()))){
				log.info("验证失败 下单券码不是同一个类别 ,券码1：" + batch.getTuanCode() + "===" + batch.getBranchId() + "  券码2=" + b.getTuanCode()+"===" + b.getBranchId() );
				return null;
			}
		}
		for(DistributionTuanCouponBatch b:list){
			DistributionTuanCoupon coupon = b.getDistributionTuanCoupon();
			coupon.setStatus(Constant.DISTRIBUTION_TUAN_COUPON_STATUS.USED.name());
			distributionTuanCouponService.update(coupon);
		}
		return list;
	}

	
	private OrdOrder create(BuyInfo buyInfo,List<DistributionTuanCouponBatch> dtbs){
		DistributionTuanCouponBatch  dtb = dtbs.get(0);
		OrdOrder order = orderServiceProxy.createOrderWithOperatorId(buyInfo, dtb.getOperatorName());
		if(order!=null && order.isNormal()){
			for(DistributionTuanCouponBatch b : dtbs){
				DistributionTuanCoupon coupon = b.getDistributionTuanCoupon();
				coupon.setOrderId(order.getOrderId());
				distributionTuanCouponService.update(coupon);
			}
			if(order.isUnpay()&&order.isPayToLvmama()){
				DistributorTuanInfo distributor= this.distributionTuanService.getDistributorTuanById(dtb.getDistributorTuanInfoId());
				paymentOrder(order,dtb.getOperatorName(),distributor.getPaymentCode());
			}
		}else{
			log.info("分销预约下单失败" + dtbs.get(0).getTuanCode());
			for(DistributionTuanCouponBatch b:dtbs){
				DistributionTuanCoupon coupon = b.getDistributionTuanCoupon();
				coupon.setStatus(Constant.DISTRIBUTION_TUAN_COUPON_STATUS.NORMAL.name());
				distributionTuanCouponService.update(coupon);
			}
		}
		
		return order;
	}
	
	
	private boolean paymentOrder(OrdOrder order,String operatorName,String paymentCode) {
		PayPayment payPayment = new PayPayment();
		payPayment.setObjectId(order.getOrderId());
		payPayment.setSerial(payPayment.geneSerialNo());
		String key = "PAYMENT_DISTRIBUTION_ACTION" + payPayment.getSerial();
		if (SynchronizedLock.isOnDoingMemCached(key)) {
			return false;
		}
		try {
			Date callbackTime = new Date();
			payPayment.setCallbackInfo("分销支付");
			payPayment.setGatewayTradeNo(DateUtil.formatDate(callbackTime, "yyyyMMddHHmmssSSS")+order.getOrderId());
			payPayment.setPaymentTradeNo(payPayment.getGatewayTradeNo());
			payPayment.setCallbackTime(callbackTime);
			payPayment.setCreateTime(callbackTime);
			payPayment.setPaymentGateway(paymentCode);
			payPayment.setAmount(order.getOughtPay());
			payPayment.setOperator(operatorName);
			payPayment.setBizType(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.getCode());
			payPayment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
			payPayment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
			payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			Long paymentId = payPaymentService.savePayment(payPayment);
			resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
		} finally {
			SynchronizedLock.releaseMemCached(key);
		}
		return true;
	}

	public void setDistributionTuanService(
			DistributionTuanService distributionTuanService) {
		this.distributionTuanService = distributionTuanService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	public void setDistributionTuanCouponService(
			DistributionTuanCouponService distributionTuanCouponService) {
		this.distributionTuanCouponService = distributionTuanCouponService;
	}
	
}
