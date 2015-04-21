package com.lvmama.passport.processor.impl;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;
/**
 * 嘉兴海盐南北湖景区对接
 * @author tangJing
 *
 */
public class NanbeihuProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor{
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private static final Log log = LogFactory.getLog(LishuiAdventureIslandProcessorImpl.class);
	@Override
	public Passport apply(PassCode passCode) {
		log.info("NanbeihuProcessorImpl Apply Code: " + passCode.getSerialNo());
		Passport passport =new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		try{
			String url = WebServiceConstant.getProperties("nanbeihu.order");
			Map<String,String> requestParas=this.buildOrderParam(passCode,"applay");
			log.info("NanbeihuProcessorImpl apply req:"+requestParas.toString());
			String resXml=HttpsUtil.requestPostForm(url, requestParas);
			log.info("NanbeihuProcessorImpl apply res: " + resXml);
			if (resXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常：" + resXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount());
			} else {
				resXml=resXml.substring(resXml.indexOf("<"));
				String rspCode = TemplateUtils.getElementValue(resXml, "//mpinfo/code");
				String rspDesc = TemplateUtils.getElementValue(resXml, "//mpinfo/message");
				if(rspCode.equals("8")){
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					passport.setCode(passCode.getSerialNo());
				}else{
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					passport.setComLogContent("供应商返回异常：" + rspDesc);
					this.reapplySet(passport, passCode.getReapplyCount());
				}
			}
			
		}catch (Exception e) {
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("NanbeihuProcessorImpl apply error message", e);
		}
		return passport;
	}
	
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("NanbeihuProcessorImpl Destroy Code: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		try {
			String url=WebServiceConstant.getProperties("nanbeihu.ordercannel");
			Map<String,String> requestParas=this.buildOrderParam(passCode,"destroy");
			log.info("NanbeihuProcessorImpl Destroy req:"+requestParas.toString());
			String resXml=HttpsUtil.requestPostForm(url, requestParas);
			log.info("NanbeihuProcessorImpl Destroy res: " + resXml);
			if (resXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setComLogContent("供应商返回异常：" + resXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount());
			} else {
				resXml=resXml.substring(resXml.indexOf("<"));
				String rspCode = TemplateUtils.getElementValue(resXml, "//mpinfo/code");
				String rspDesc = TemplateUtils.getElementValue(resXml, "//mpinfo/message");
				if(rspCode.equals("8")){
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				}else{
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setComLogContent("供应商返回异常：" + rspDesc);
					this.reapplySet(passport, passCode.getReapplyCount());
				}
			}
		}catch (Exception e) {
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setComLogContent(e.getMessage());
			log.error("NanbeihuProcessorImpl Destroy Exception:", e);
			
		}
		return passport;
	}
	
	/**
	 * 重新申请码处理
	 * 
	 * @param passport
	 * @param error
	 */
	public void reapplySet(Passport passport, long times) {
		OrderUtil.init().reapplySet(passport, times);
	}
	
	public Map<String, String> buildOrderParam(PassCode passCode,String actionType){
		Map<String, String> ordParam=new LinkedHashMap<String, String>();
		String keyCode=WebServiceConstant.getProperties("nanbeihu.keycode");
		try {
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdPerson contact=ordOrder.getContact();
			OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
			String productIdSupplier = itemMeta.getProductIdSupplier();
			if (StringUtils.isBlank(productIdSupplier)) {
				throw new IllegalArgumentException("代理产品编号不能空");
			}
			Long quantity =itemMeta.getQuantity() * itemMeta.getProductQuantity();
			float price = 0.0f;
			for (OrdOrderItemProd itemProd : ordOrder.getOrdOrderItemProds()) {
				if (itemProd.getOrderItemProdId().longValue()==itemMeta.getOrderItemId()) {
					price=itemProd.getPriceYuan();
					break;
				}
			}
			float totalPrice = price * quantity;
			float settlementPrice=itemMeta.getSettlementPriceToYuan();
			String payType =""; // 1在线支付 2景区支付 
			String payStatus=""; //0：未付款1：付款中2：已付款 
			String orderStatus="";//订单状态 1 ：已确认2： 已取消3：无效4：退货5 ：已分单6： 部分分单  7：退款 
			if (ordOrder.isPayToLvmama()) {
				payType = "1";
				payStatus="2";
			}
			if(ordOrder.isPayToSupplier()){
				payType="2";
				payStatus="0";
			}
			if(actionType.equals("applay")){
				orderStatus="1";
			}else if(actionType.equals("destroy")){
				if (ordOrder.isPayToLvmama()) {
					orderStatus="7";
				}else{
					orderStatus="2";
				}
			}
			ordParam.put("key_code",MD5.encode(keyCode));
			ordParam.put("order_sn",passCode.getSerialNo());
			ordParam.put("order_status",orderStatus);
			ordParam.put("pay_status",payStatus);
			ordParam.put("pay_id",payType);
			ordParam.put("consignee",contact.getName());
			ordParam.put("mobile",contact.getMobile());
			ordParam.put("play_date",DateFormatUtils.format(ordOrder.getVisitTime(), "yyyy-MM-dd"));
			/*南北湖景区对接系统没有自己专有的产品ID，产品Id以我们传的信息为主，故我们自己组合的套餐独立申码
			时在每个类别的价格如果传售价会导致一个类别的价格对应了我们组合后的销售价，故如果是在线支付价格用每个采购产品
			的结算价，如果是景区支付则取产品的售价
			*/
			if (ordOrder.isPayToLvmama()) {
				ordParam.put("price",PriceUtil.formatDecimal(settlementPrice));
				ordParam.put("total",PriceUtil.formatDecimal(settlementPrice*quantity));
			}else{
				ordParam.put("price",PriceUtil.formatDecimal(price));
				ordParam.put("total",PriceUtil.formatDecimal(totalPrice));
			}
			ordParam.put("product", String.valueOf(itemMeta.getProductName())+"("+quantity+"份)");//采购产品名称+订购张数
			ordParam.put("product_id",String.valueOf(productIdSupplier.trim()));
			ordParam.put("number",String.valueOf(quantity));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return ordParam;
	}
}
