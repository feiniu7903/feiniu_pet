package com.lvmama.passport.processor.impl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.impl.client.hangzhouzoom.HangzhouZoomClient;
import com.lvmama.passport.processor.impl.client.hangzhouzoom.model.OrderInfo;
import com.lvmama.passport.processor.impl.client.hangzhouzoom.model.OrderResponse;
import com.lvmama.passport.processor.impl.client.hangzhouzoom.model.Product;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;

/**
 * 杭州野生动物园服务商接口
 * 
 * @author tangJing
 * 
 */
public class HangZhouZooProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor {

	// 日志工具对象
	private static final Log log = LogFactory.getLog(HangZhouZooProcessorImpl.class);
	// 订单服务接口
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");

	/**
	 * 创建订单---申请二维码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("HangZhouZoom Apply Code:" + passCode.getSerialNo());
		// 通关系统数据参数对象
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		// 取出信息并保存
		Long startTime = 0L;
		try {
			OrderInfo orderInfo = this.buildOrderInfo(passCode);
			// 发送申码请求
			startTime = System.currentTimeMillis();
			String result = HangzhouZoomClient.applyCodeRequest(orderInfo);
			log.info("HangZhouZoom Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("HangZhouZoom Apply Code Response:"+result);
			if (result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常：" + result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount(), "FAILED");
			} else {
				OrderResponse message = HangzhouZoomClient.parseApplyCodeResponse(result);
				if (StringUtils.equals(message.getOrderStatus(), "1")) {
					passport.setExtId(message.getOrderId());// 放置订单号
					passport.setCode(message.getOrderId());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					passport.setComLogContent("供应商返回异常：" + message.getErrorInfo());
					this.reapplySet(passport, passCode.getReapplyCount(), "FAILED");
					log.info("HangZhouZoom Apply returned Status Error message:" + message.getErrorInfo());
				}
			}
		} catch (Exception e) {
			log.error("HangZhouZoom Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			passport.setComLogContent(e.toString());
			this.reapplySet(passport, passCode.getReapplyCount(), "FAILED");
			log.error("HangZhouZoom Apply Error message:", e);
		}
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		passport.setSendOrderid(true);
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		return passport;
	}

	/**
	 * 退订单---废除二维码
	 */
	@Override
	public Passport destroy(PassCode passCode) {

		log.info("HangZhouZoom Destoy Code :" + passCode.getSerialNo());
		// 通关系统数据参数对象(保存传回来的数据并记录)
		Passport passport = new Passport();
		// 发送请求信息类
		Long startTime = 0L;
		try {
			// 发送废单请求
			startTime = System.currentTimeMillis();
			String result = HangzhouZoomClient.destroyRequest(passCode.getExtId());
			log.info("HangZhouZoom destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("HangZhouZoom Destoy Code Response:"+result);
			if (result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());// 设置状态（失败）
				passport.setComLogContent("供应商返回异常：" + result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			} else {
				// 解析废单请求返回的信息 0 失败 1 成功 orderId 订单号
				OrderResponse message = HangzhouZoomClient.parseDestoryCodeResponse(result);
				if (StringUtils.equals(message.getOrderStatus(), "1")) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());// 设置状态（成功）
					log.info("HangZhouZoom Destoy SUCCESS, message:" + message.getOrderId());
				} else {
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());// 设置状态（失败）
					passport.setComLogContent("供应商返回异常：" + message.getErrorInfo());
					log.info("HangZhouZoom Destoy Error, message:" + message.getErrorInfo());
				}
			}
		} catch (Exception e) {
			log.error("HangZhouZoom destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());// 设置状态（失败）
			passport.setComLogContent(e.toString());
			log.error("HangZhouZoom Destoy Error, message:", e);
		}
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		return passport;
	}

	/**
	 * 重新申请二维码
	 */
	public void reapplySet(Passport passport, long times, String error) {
		OrderUtil.init().reapplySet(passport, times);
	}

	/**
	 * 获取申码相关信息
	 * 
	 * @param passCode
	 * @return
	 */
	public OrderInfo buildOrderInfo(PassCode passCode) throws Exception {
		List<Product> productlist = new ArrayList<Product>();
		// 以下为组合查询
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(1000000000);
		List<String> targetIdlist = this.buildTargetId(passCode.getPassPortList());
		String targetIds = this.join(",", targetIdlist.iterator());
		compositeQuery.getMetaPerformRelate().setTargetId(targetIds);
		compositeQuery.getMetaPerformRelate().setOrderId(ordorder.getOrderId());
		List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCustomerOrderId(String.valueOf(ordorder.getOrderId()));
		orderInfo.setUserName(ordorder.getContact().getName());
		orderInfo.setIdentityCard(ordorder.getContact().getCertNo() == null ? "422202" : ordorder.getContact().getCertNo());
		orderInfo.setEmail(ordorder.getContact().getEmail() == null ? "abc@163.com" : ordorder.getContact().getEmail());
		orderInfo.setMobile(ordorder.getContact().getMobile());
		orderInfo.setEnterTime(String.valueOf(ordorder.getVisitTime().getTime()));
		float totalPrice=0.0f;
		for(OrdOrderItemMeta ordOrderItemMeta : orderItemMetas) {
			totalPrice+=ordOrderItemMeta.getSettlementPriceYuan()*ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity();
		}
		log.info("订单总金额："+totalPrice);
		orderInfo.setAmount(PriceUtil.formatDecimal(totalPrice));
		for (OrdOrderItemMeta ordOrderItemMeta : orderItemMetas) {
			if (StringUtils.isBlank(ordOrderItemMeta.getProductIdSupplier())) {
				throw new IllegalArgumentException("代理产品编号不能空");
			}
			if (null != ordOrderItemMeta && ordOrderItemMeta.getProductTypeSupplier() != null && !"".equals(ordOrderItemMeta.getProductTypeSupplier())) {
				String ticketId=ordOrderItemMeta.getProductIdSupplier();//票种类型Id
				log.info("票种类型ID："+ticketId);
				Long quantity =ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity();
				log.info("购买数："+quantity);
				float price=ordOrderItemMeta.getSettlementPriceYuan();//单张票价
				log.info("票价："+price);
				Product product = new Product();
				product.setProductId(ticketId);
				product.setCustomerUnitPrice(PriceUtil.formatDecimal(price));
				product.setQuantity(String.valueOf(quantity));
				product.setCustomerProductName(ordOrderItemMeta.getProductName());
				productlist.add(product);
			}
		}
		orderInfo.setProductList(productlist);
		return orderInfo;
	}

	private List<String> buildTargetId(List<PassPortCode> passPortCodeList) {
		List<String> targetIdlist = new ArrayList<String>();
		for (PassPortCode passPortCode : passPortCodeList) {
			targetIdlist.add(passPortCode.getTargetId().toString());
		}
		return targetIdlist;
	}

	@SuppressWarnings("rawtypes")
	private String join(String seperator, Iterator objects) {
		StringBuffer buf = new StringBuffer();
		if (objects.hasNext()) {
			buf.append(objects.next());
		}
		while (objects.hasNext()) {
			buf.append(seperator).append(objects.next());
		}
		return buf.toString();
	}
}
