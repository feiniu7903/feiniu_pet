package com.lvmama.passport.processor.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.WebServiceConstant;
import com.lvmama.passport.wulong.model.OrderInfo;
import com.lvmama.passport.wulong.model.OrderLocator;
import com.lvmama.passport.wulong.model.OrderPortType;
import com.lvmama.passport.wulong.model.Product;
import com.lvmama.passport.wulong.model.Tourist;
/**
 * 武隆景区【游客凭身份证到景区前台或者自动出票机取票】
 *
 */
public class WuLongProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor,OrderPerformProcessor{
	private static final Log log = LogFactory.getLog(ManyouyouProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private ComLogService comLogService = (ComLogService) SpringBeanProxy.getBean("comLogService");
	private static String baseTemplateDir ="/com/lvmama/passport/wulong/template";
	@Override
	public Passport apply(PassCode passCode) {
		log.info("WuLong apply serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		Long startTime = 0L;
		try {
			OrderInfo buildOrder=buildOrder(passCode);
			String reqXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "orderInfo.xml", buildOrder);
			String pass=WebServiceConstant.getProperties("wulong.pass");
			String dis_code=WebServiceConstant.getProperties("wulong.dis_code");
			String checkcode=WebServiceConstant.getProperties("wulong.checkcode");
			String dispass=WebServiceConstant.getProperties("wulong.dispass");
			OrderPortType port=new OrderLocator().getorderHttpSoap11Endpoint();
			String resXml=port.orderSubmit(pass, dis_code, checkcode,dispass, reqXml);
			log.info("WuLong Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("WuLong apply resXml: " + resXml);
			String rspCode = TemplateUtils.getElementValue(resXml, "//result/code");
			String rspDesc = TemplateUtils.getElementValue(resXml, "//result/msg");
			if ("OS09999".equalsIgnoreCase(rspCode)) {
				passport.setCode(buildOrder.getIdcardCode());//身份证号
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				if(rspDesc.contains("[") && rspDesc.contains("]")){
					int start=rspDesc.indexOf("[")+1;
					int end=rspDesc.indexOf("]");
					if(start < end){
						String extId=rspDesc.substring(start,end);
						passport.setExtId(extId);
					}
				}
			}else{
				passport.setComLogContent("供应商返回异常："+rspDesc);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				this.reapplySet(passport, passCode.getReapplyCount());
				log.info("WuLong apply fail message: " + rspCode + " " + rspCode);	
			}
		}catch(Exception e){
			log.error("WuLong Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("WuLong apply error message", e);	
		}
		return passport;
	}
	
	/**
	 * 重新申码
	 */
	public void reapplySet(Passport passport, long times) {
		OrderUtil.init().reapplySet(passport, times);
	}
	
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("WuLong destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		Long startTime = 0L;
		try{
			String pass=WebServiceConstant.getProperties("wulong.pass");
			String dis_code=WebServiceConstant.getProperties("wulong.dis_code");
			String checkcode=WebServiceConstant.getProperties("wulong.checkcode");
			String dispass=WebServiceConstant.getProperties("wulong.dispass");
			OrderPortType port=new OrderLocator().getorderHttpSoap11Endpoint();
			String resXml=port.orderCancel(pass, dis_code, checkcode,dispass, passCode.getSerialNo(),"游客取消出游 ");
			log.info("wuLong destroy resXml: "+resXml);
			String rspCode = TemplateUtils.getElementValue(resXml, "//result/code");
			String rspDesc = TemplateUtils.getElementValue(resXml, "//result/msg");
			if("OS09999".equalsIgnoreCase(rspCode)) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			}else{
				passport.setComLogContent("供应商返回异常："+rspDesc);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.info("wuLong destroy fail message: " + rspCode + " " + rspDesc);
			}
		}catch(Exception e){
			log.info("WuLong destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("WuLong destroy error message",e);
		}
		return passport;
	}
	
	
	private static Set<PassCode> unusedList = new HashSet<PassCode>();
	private static Set<PassCode> usedList = new HashSet<PassCode>();
	/**
	 * 更新订单履行状态
	 */
	@Override
	public Passport perform(PassCode passCode) {
		log.info("WuLong perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		if (isNeedCheckout(passCode)) {
			try {
				String pass=WebServiceConstant.getProperties("wulong.pass");
				String dis_code=WebServiceConstant.getProperties("wulong.dis_code");
				String checkcode=WebServiceConstant.getProperties("wulong.checkcode");
				String dispass=WebServiceConstant.getProperties("wulong.dispass");
				OrderPortType port=new OrderLocator().getorderHttpSoap11Endpoint();
				String resXml=port.orderQuery(pass, dis_code, checkcode,dispass, passCode.getSerialNo());
				log.info("wuLong perform resXml: "+resXml);
				String rspCode = TemplateUtils.getElementValue(resXml, "//result/code");
				String rspDesc = TemplateUtils.getElementValue(resXml, "//result/msg");
				if("100".equalsIgnoreCase(rspCode)) {
					passport = new Passport();
					passport.setChild("0");
					passport.setAdult("0");
					passport.setUsedDate(new Date());
					passport.setDeviceId("WuLong");
					stopCheckout(passCode);
				}else{
					stopCheckout(passCode);
					this.addComLog(passCode, rspDesc, "该订单还未完成出票状态");
					log.info("wuLong perform fail message: " + rspCode + " " + rspDesc);
				}
			} catch(Exception e) {
				log.error("wuLong perform error message", e);
			}
		}
		return passport;
	}
	
	private void addComLog(PassCode passCode, String logContent, String logName) {
		ComLog log = new ComLog();
		log.setObjectType("PASS_CODE");
		log.setParentId(passCode.getOrderId());
		log.setObjectId(passCode.getCodeId());
		log.setOperatorName("SYSTEM");
		log.setLogType(Constant.COM_LOG_ORDER_EVENT.systemApprovePass.name());
		log.setLogName(logName);
		log.setContent(logContent);
		comLogService.addComLog(log);
	}
	
	private boolean isPassCodeUnused(PassCode passCode) {
		if (!usedList.contains(passCode)) {
			unusedList.add(passCode);
			return true;
		}
		return false;
	}
	
	private boolean isNeedCheckout(PassCode passCode) {
		return "SUCCESS".equals(passCode.getStatus()) && isPassCodeUnused(passCode);
	}
	
	private void stopCheckout(PassCode passCode) {
		unusedList.remove(passCode);
		usedList.add(passCode);
	}
	
	private OrderInfo buildOrder(PassCode passCode)throws Exception{
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdPerson ordPerson = OrderUtil.init().getContract(ordOrder);//游客信息
			OrdPerson contact=ordOrder.getContact();//联系人信息
			OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
			Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
			String productIdSupplier = itemMeta.getProductIdSupplier();
			String visiteTime = DateFormatUtils.format(ordOrder.getVisitTime(),"yyyy-MM-dd");
			String productId=itemMeta.getProductIdSupplier();
			float sellPrice=itemMeta.getSellPriceToYuan();
			float totalPrice=sellPrice*count;
			if (StringUtils.isBlank(productIdSupplier)) {
				throw new IllegalArgumentException("代理产品编号不能空");
			}
			log.info("realPrice:"+totalPrice);
			log.info("sellPrice:"+sellPrice);
			log.info("buyNum:"+count);
			OrderInfo order=new OrderInfo();
			//1是在线支付，2：是现场支付
			if (ordOrder.isPayToLvmama()) {
				order.setType("1");
			} else {
				order.setType("2");
			}
			order.setOrderId(passCode.getSerialNo());
			order.setRealPrice(String.valueOf(totalPrice));//实际成交价格
			order.setContactName(contact.getName());//联系人名称
			order.setContactPhone(contact.getMobile());//联系人电话
			order.setIdcardCode(ordPerson.getCertNo());
			order.setArriveDate(visiteTime);
			List<Product> productLists=new ArrayList<Product>();
			Product product=new Product();
			product.setProCode(productId.trim());
			product.setBuyPrice(String.valueOf(sellPrice));//销售价
			product.setBuyNum(String.valueOf(count));
			product.setBuyTotalPrice(String.valueOf(totalPrice));
			productLists.add(product);
			List<Tourist> touristLists=new ArrayList<Tourist>();
			Tourist tour=new Tourist();
			tour.setFullName(ordPerson.getName());
			tour.setAddress(ordPerson.getAddress());
			tour.setIdcardCode(ordPerson.getCertNo());
			touristLists.add(tour);
			order.setProductLists(productLists);
			order.setTouristLists(touristLists);
		return order;
	}
}
