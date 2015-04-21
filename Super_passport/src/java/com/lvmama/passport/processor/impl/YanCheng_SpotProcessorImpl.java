package com.lvmama.passport.processor.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.RandomFactory;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.impl.client.yanchen.Reserve2;
import com.lvmama.passport.processor.impl.client.yanchen.Service1Client;
import com.lvmama.passport.processor.impl.client.yanchen.Service1Soap;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 淹城服务商实现
 * 
 * 【景区支付产品对接淹城旧系统】
 */
public class YanCheng_SpotProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor {
	private static final Log log = LogFactory.getLog(YanCheng_SpotProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private PassCodeService passCodeService = (PassCodeService) SpringBeanProxy.getBean("passCodeService");

	private static Map<Integer, String> errMessageMap;
	// 淹城WebService返回的错误信息常量
	private String getMessageStatus(int code) {
		if (errMessageMap == null) {
			errMessageMap = new HashMap<Integer,String>();
			errMessageMap.put(0,"执行失败");
			errMessageMap.put(-1,"无此类型预订商品");
			errMessageMap.put(-2,"非法代理商");
			errMessageMap.put(-3,"无效代理商");
		}
		return errMessageMap.get(code);
	}


	@Override
	public Passport apply(PassCode passCode) {
		log.info("YanCheng Apply Code Request :" + passCode.getSerialNo());
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrdOrderItemMetaId(passCode.getObjectId());
		Passport passport = new Passport();
		queryAndSendOrder(ordOrder, passCode, passport);
		passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
		while (true) {
			String ssistNo = RandomFactory.generate(10);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("code", ssistNo);
			PassCode temp = passCodeService.getPassCodeByParams(data);
			if (temp == null) {
				passport.setCode(ssistNo);
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				break;
			}
		}
		passport.setSendOrderid(false);
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		log.info("orderId ：" + passport.getSerialno());
		log.info("Code :" + passport.getCode());
		return passport;
	}

	@Override
	public Passport destroy(PassCode passCode) {
		log.info("YanCheng DestroyCode Request :" + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
		passport.setSerialno(passCode.getSerialNo());
		passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		return passport;
	}

	/**
	 * 查询出驴妈妈订单数据，然后发送到对方系统的订单里
	 * 
	 * @param ordOrder
	 * @param passCode
	 * @param passport
	 */
	private void queryAndSendOrder(OrdOrder ordOrder, PassCode passCode, Passport passport) {
		if (ordOrder.getAllOrdOrderItemMetas() != null) {
			for (OrdOrderItemMeta ordOrderItemMeta : ordOrder.getAllOrdOrderItemMetas()) {
				if (passCode.getObjectId().equals(ordOrderItemMeta.getOrderItemMetaId())) {
					sendOrder(ordOrder, passCode, ordOrderItemMeta, passport);
					break;
				}
			}
		}
	}

	private void sendOrder(OrdOrder ordOrder, PassCode passCode, OrdOrderItemMeta ordOrderItemMeta, Passport passport) {
		//避免因产品设置有误导致在线支付产品走到这里，而引起游客要支付两次的造成投诉，故加此逻辑
		String paymentTarget = ordOrder.getPaymentTarget();
		if (Constant.PAYMENT_TARGET.TOLVMAMA.name().equals(paymentTarget)) {
			throw new IllegalArgumentException("产品绑定有误，请核实！");
		}
		String CRLF = System.getProperty("line.separator");
		float price=ordOrderItemMeta.getSettlementPriceYuan();
		StringBuffer buf = new StringBuffer();
		buf.append(CRLF + "djno=" + passCode.getSerialNo());
		buf.append(CRLF + "linkman=" + ordOrder.getContact().getName());
		buf.append(CRLF + "telphone=" + ordOrder.getContact().getMobile());
		buf.append(CRLF + "content=" + ordOrder.getContact().getMemo());
		buf.append(CRLF + "cdate=" + DateFormatUtils.format(ordOrder.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
		buf.append(CRLF + "paymode=01");
		buf.append(CRLF + "payflag=false");
		buf.append(CRLF + "ipaddress=127.0.0.1");
		buf.append(CRLF + "agentno=" + WebServiceConstant.getProperties("yancheng.agentno"));
		buf.append(CRLF + "rtypeid=" + ordOrderItemMeta.getProductTypeSupplier());
		buf.append(CRLF + "rnum=" + (ordOrderItemMeta.getProductQuantity() * ordOrderItemMeta.getQuantity()));
		buf.append(CRLF + "rprice=" + price);
		buf.append(CRLF + "rdate=" + DateFormatUtils.format(ordOrderItemMeta.getVisitTime(),"yyyy-MM-dd"));
		buf.append(CRLF + "agentkey=" + WebServiceConstant.getProperties("yancheng.agentkey"));
		log.info(buf.toString());

		Reserve2 reserve = new Reserve2();
		reserve.setDjno(passCode.getSerialNo());// 单据编号(30字节)
		reserve.setLinkman(ordOrder.getContact().getName());// 预订人姓名
		reserve.setTelphone(ordOrder.getContact().getMobile());// 预订手机号
		reserve.setContent(ordOrder.getContact().getMemo());// 预订备注
		reserve.setCdate(DateFormatUtils.format((ordOrder.getCreateTime()),"yyyy-MM-dd HH:mm:ss"));// 预订时间(非游玩时间,数据创建时间)
		reserve.setPaymode("01");// 支付模式 现在系统强制有默认为
									// 现场支付：“01”，可以不填，今后可能会考虑在线支付等情况。
		reserve.setPayflag(true);// 支付标识 是否已经支付，默认为没有支付：“false”，针对在线支付等预付的情况。
		reserve.setIpaddress("127.0.0.1");// 预订客户的IP地址即网页的访问的IP地址 (主要用于分析客户归属地)
		reserve.setAgentno(WebServiceConstant.getProperties("yancheng.agentno"));// 预订代理商编码
		reserve.setRtypeid(ordOrderItemMeta.getProductTypeSupplier());// 门票类型代码(如果有预订产品有价格和代码变更,淹城会有对应通知的)
		long num = ordOrderItemMeta.getProductQuantity() * ordOrderItemMeta.getQuantity();
		reserve.setRnum((int) num);// 预订数量
		reserve.setRprice(new BigDecimal(price));// 预订价格
		reserve.setRdate(DateFormatUtils.format(ordOrderItemMeta.getVisitTime(),"yyyy-MM-dd"));// 游玩时间
		reserve.setAgentkey(WebServiceConstant.getProperties("yancheng.agentkey"));// 上传代理商密钥
		long startTime=System.currentTimeMillis();
		// 创建WebService客户端调用
		Service1Client serviceClient = new Service1Client();
		Service1Soap servicePort = serviceClient.getService1Soap();
		log.info("YanCheng Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);

		try {
			// 发送订单
			int ret = servicePort.addData2(reserve);
			if (ret > 0) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				log.info("YanCheng Apply error: " + getMessageStatus(ret));
				passport.setComLogContent("供应商返回异常：" + getMessageStatus(ret));
				this.reapplySet(passport, passCode.getReapplyCount());
			}
		} catch (Exception e) {
			log.error("yancheng Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.error("YanCheng Apply Exception: ",e);
			passport.setComLogContent(e.getMessage());
			this.reapplySet(passport, passCode.getReapplyCount());
		}
	}

	
	/**
	 * 重新申请码处理
	 * @param passport
	 * @param error
	 */
	public void reapplySet(Passport passport,long times){
		OrderUtil.init().reapplySet(passport, times);
	}
}