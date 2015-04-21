package com.lvmama.passport.processor.impl;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.hengdianmc.client.AgentInterfaceLocator;
import com.lvmama.passport.hengdianmc.client.AgentInterfaceSoap;
import com.lvmama.passport.hengdianmc.client.OrderInfo;
import com.lvmama.passport.hengdianmc.client.OrderRep;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceClient;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 *  横店影视城服务商接口
 *  
 * @author haofeifei
 *
 */
public class HengDianMovieCityProcessorImpl implements ApplyCodeProcessor{

	// 日志工具对象
	private static final Log log = LogFactory.getLog(HengDianMovieCityProcessorImpl.class);
	// 订单服务接口
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	
	/**
	 * 创建订单---申请二维码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		Long startTime = 0L;
		try {
			AgentInterfaceSoap s = new AgentInterfaceLocator().getAgentInterfaceSoap();
			OrderInfo orderInfo = this.fillSubmitOrderBean(passCode);
			OrderRep result = s.orderReq(orderInfo);
			boolean isSuccess = result.isResult();
			String errorCode = result.getDealTime();
			String errorMsg = result.getErrorMsg();
			
			if (isSuccess) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				passport.setComLogContent("供应商返回异常："+errorMsg);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				this.reapplySet(passport, passCode.getReapplyCount());
				log.info("HengDianMovieCity apply fail message: " + errorCode + " ," + errorMsg);
			}
		} catch (Exception e) {
			log.error("HengDianMovieCity Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("HengDianMovieCity apply error message", e);
			e.printStackTrace();
		}
		return passport;
	}


	/**
	 * 重新申码
	 */
	public void reapplySet(Passport passport, long times) {
		OrderUtil.init().reapplySet(passport, times);
	}
	
	private OrderInfo fillSubmitOrderBean(PassCode passCode) throws NoSuchAlgorithmException {
		String serialNo = passCode.getSerialNo();
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordperson =OrderUtil.init().getContract(ordorder);
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordorder, passCode);
		Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
		String visiteTime = DateFormatUtils.format(itemMeta.getVisitTime(),"yyyy-MM-dd");//游玩时间
		String productIdSupplier = itemMeta.getProductIdSupplier(); // 采购产品 ——类别——代理产品编号
		if (StringUtils.isBlank(productIdSupplier)) {
			throw new IllegalArgumentException("代理产品编号不能空");
		}
		String productTypeSupplier = itemMeta.getProductTypeSupplier();//代理产品类型

		
		String products="<product><viewid>"+productIdSupplier+"</viewid><viewname>"
		+productIdSupplier+"</viewname><Type>"+productTypeSupplier+"</Type><number>"+count+"</number></product>";
		
		OrderInfo submitOrderBean = new OrderInfo();
		submitOrderBean.setTimeStamp(String.valueOf(new Date()));
		submitOrderBean.setCompanyCode(WebServiceConstant.getProperties("hengdianMovieCity.companyCode"));//商家编码，由横店影视城分配
		submitOrderBean.setCompanyName(WebServiceConstant.getProperties("hengdianMovieCity.companyName"));//商家名称
		submitOrderBean.setCompanyOrderID(serialNo);//商家自己的订单流水号
		submitOrderBean.setArrivalDate(visiteTime);//游玩时间 
		submitOrderBean.setOrderTime(DateUtil.formatDate(ordorder.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));//订单时间
		submitOrderBean.setVisitorName(ordperson.getName());//游客姓名 
		submitOrderBean.setVisitorMobile(ordperson.getMobile());//游客手机号 
		submitOrderBean.setIdCardNeed(1);//是否需要身份证号
		submitOrderBean.setIdCard(ordperson.getCertNo());//身份证号码 
		submitOrderBean.setPayType(String.valueOf(1));
		submitOrderBean.setProducts(products);
		return submitOrderBean;
	}
	
	
	
	
}
