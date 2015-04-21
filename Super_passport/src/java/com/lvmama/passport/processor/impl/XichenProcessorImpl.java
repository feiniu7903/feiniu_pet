package com.lvmama.passport.processor.impl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.impl.client.xichen.XiChenClient;
import com.lvmama.passport.processor.impl.client.xichen.model.OrderResponse;
import com.lvmama.passport.processor.impl.client.xichen.model.SubmitOrderBean;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;
/**
 * 西晨
 * @author tangJing
 */
public class XichenProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor{
	private static final Log log = LogFactory.getLog(XichenProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("XiChen apply serialNo: " + passCode.getSerialNo());
		long startTime=0L;
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		try {
			SubmitOrderBean bean=buildOrderInfo(passCode);
			startTime = System.currentTimeMillis();
			String result=XiChenClient.applyCodeRequest(bean);
			log.info("XiChen applay serialNo :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			if (result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常："+ result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount());
			} else {
				log.info("XiChen applay resStr: " + result);
				result=result.substring(result.indexOf("{"));
				OrderResponse order=XiChenClient.parseOrderResponse(result);
				String status =order.getErrorNo();
				if (StringUtils.equals(status,"0")){
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					//0代表预售票，其他代表电子票
					if (StringUtils.equals(bean.getTickType(), "0")) {
						String extId=order.getOrderId();
						passport.setExtId(extId);
						passport.setCode(extId);
					} else {
						String code=order.getCode();
						passport.setCode(code);
					}
				}else {
					String errorn=order.getErrorMsg();
					passport.setComLogContent("供应商返回异常："+ errorn);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					log.info("XiChen apply fail message: "+ errorn);
				}
			}
		} catch (Exception e) {
			log.error("XiChen applay serialNo Error :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("XiChen applay message", e);
		}
		return passport;
	}
	
	/**
	 * 重新申码
	 */
	public void reapplySet(Passport passport, long times) {
		OrderUtil.init().reapplySet(passport, times);
	}
	
	/**
	 * 废码
	 */
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("XiChen destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		passport.setComLogContent("供应商不提供废码接口");
		passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
		passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
		return passport;
	}
	
	private SubmitOrderBean buildOrderInfo(PassCode passCode)throws Exception{
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordPerson = OrderUtil.init().getContract(ordOrder);
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
		Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
		String productIdSupplier = itemMeta.getProductIdSupplier();
		String productTypeSupplier = itemMeta.getProductTypeSupplier();
		if (StringUtils.isBlank(productIdSupplier)) {
			throw new IllegalArgumentException("代理产品编号不能空");
		}
		if (StringUtils.isBlank(productTypeSupplier)) {
			throw new IllegalArgumentException("代理产品类型不能空");
		}
		String username=WebServiceConstant.getProperties("xichen.username");
		SubmitOrderBean order=new SubmitOrderBean();
		order.setUsername(username);
		order.setIdcard(ordPerson.getCertNo());
		order.setPhone(ordPerson.getMobile());
		order.setNum(String.valueOf(count));
		order.setName(ordPerson.getName());
		order.setTid(productIdSupplier);
		order.setTickType(productTypeSupplier);
		return order;
	}	
}