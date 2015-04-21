package com.lvmama.passport.processor.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
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
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.impl.client.xilingsm.XilingSnowMountainServiceClient;
import com.lvmama.passport.processor.impl.client.xilingsm.model.Request;
import com.lvmama.passport.processor.impl.client.xilingsm.model.RequestDetail;
import com.lvmama.passport.processor.impl.client.xilingsm.model.Response;
import com.lvmama.passport.processor.impl.util.OrderUtil;

/**
 * Xiling snow mountain web service processor implementation.
 *
 * @author zuoxiaoshuai
 */
public class XilingSnowMountainProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, OrderPerformProcessor {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(XilingSnowMountainProcessorImpl.class);

	/** The order service. */
	private OrderService orderService = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");

	/* (non-Javadoc)
	 * @see com.lvmama.passport.processor.ApplyCodeProcessor#apply(com.lvmama.comm.bee.po.pass.PassCode)
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("Xiling Snow Mountain Apply Code Request :" + passCode.getSerialNo());
		//Create request object by passCode
		Request request = createApplyRequest(passCode);
		//Initial passport
		Passport passport = new Passport();
		//Get start time
		long startTime = System.currentTimeMillis();
		try{
			//Call xiling moutain web service to apply
			doApply(request, passport, passCode, startTime);
		} catch (Exception e) {
			log.info("Xiling Snow Mountain Apply serialNo Error:" +
								passCode.getSerialNo() + " UseTime:" +
								(System.currentTimeMillis() - startTime) / 1000);
			log.error("Xiling Snow Mountain ApplyCode Exception:", e);
			String error = e.getMessage();
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			passport.setComLogContent(error);
			this.reapplySet(passport, passCode.getReapplyCount(), error);
		}

		return passport;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.passport.processor.DestroyCodeProcessor#destroy(com.lvmama.comm.bee.po.pass.PassCode)
	 */
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("Xiling Snow Mountain Destroy Code Request :" + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		passport.setComLogContent("供应商不提供废码接口");
		passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
		passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
		return passport;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.passport.processor.OrderPerformProcessor#perform(com.lvmama.comm.bee.po.pass.PassCode)
	 */
	@Override
	public Passport perform(PassCode passCode) {
		XilingSnowMountainServiceClient client = XilingSnowMountainServiceClient.getInstance();
		Request request = createQueryRequest(passCode);
		try {
			String result = client.execute(request.getQueryRequestXML(), "queryIndent");
			log.info("Xiling snow mountain rspBody: " + result);
			String resultCode = TemplateUtils.getElementValue(result, "//response/result");
			if ("-1".equals(resultCode)) {//Error
				String error = TemplateUtils.getElementValue(result, "//response/error");
				log.info("Xiling snow mountain perform fail message: " + resultCode + " " + error);
			} else {
				String orderStatus = TemplateUtils.getElementValue(result, "//response/wsPojos/pojo/status");
				if ("05".equals(orderStatus)) {//Already spending
					Passport passport = new Passport();
					passport.setChild("0");
					passport.setAdult("0");
					passport.setUsedDate(new Date());
					passport.setDeviceId("XilingSnowMountain");
					return passport;
				}
			}
		} catch (Exception e) {
			log.error("Xiling snow mountain perform error message", e);
		}
		return null;
	}

	/**
	 * Do apply.
	 *
	 * @param request requst object
	 * @param passport the passport
	 * @param passCode the pass code
	 * @param startTime the start time
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws Exception the exception
	 */
	private void doApply(Request request, Passport passport,
								PassCode passCode, long startTime) 
								throws UnsupportedEncodingException, Exception {
		XilingSnowMountainServiceClient client = XilingSnowMountainServiceClient.getInstance();
		String result = client.execute(request.getBuyTicketRequestXML(), "buyTicket");
		log.info("Xiling Snow Mountain ApplyCode Reqeust Result:" + result);
		//String result=StringUtil.getTestData();
		//Process response XML string
		Response response = Response.createInstanceByXML(StringUtils.trim(result));
		log.info("Xiling Snow Mountain Apply serialNo :" + passCode.getSerialNo() +
							" UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
		passport.setSerialno(passCode.getSerialNo());
		passport.setCode(response.getCheckCode());
//		passport.setAddCode(response.getIndentCode());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());

		String returnCode = StringUtils.trim(response.getResult());

		if ("0".equals(returnCode)) {
			String status=response.getStatus();
			if(StringUtils.equals(status,"03")){
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			}else{
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常：预存款账号余额不足");
				this.reapplySet(passport, passCode.getReapplyCount(),"FAILED");
				log.info("Xiling Snow Mountain Apply Error message:" + status);
			}
			
		} else {
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			log.info("Xiling Snow Mountain Applay Result status: " + returnCode);
			passport.setComLogContent("XilingSnowMountainProcessorImpl_apply_Received" +
										" an abnormal status code " + returnCode +
											", " + response.getError());
			this.reapplySet(passport, passCode.getReapplyCount(), passport.getStatus());
		}
	}

	/**
	 * Create apply request object.
	 *
	 * @param passCode the pass code
	 * @return the request
	 */
	private Request createApplyRequest(PassCode passCode) {
		Request request = new Request();
		//Query order info by passCode.
		OrdOrder ordorder = orderService.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson person = OrderUtil.init().getContract(ordorder);

		request.setPhoneNumber(person.getMobile());
		request.setLinkman(person.getName());
		request.setIdCardNumber(person.getCertNo());

		request.setBatchNo(passCode.getSerialNo());
		request.setUseDate(ordorder.getVisitTime());
		request.setOutNo(passCode.getSerialNo());

		Float price = 0.0f;
		OrdOrderItemMeta item = OrderUtil.init().getItemMeta(ordorder, passCode);
		for (OrdOrderItemProd itemProd : ordorder.getOrdOrderItemProds()) {
			if (itemProd.getOrderItemProdId().equals(item.getOrderItemId())) {
				price = itemProd.getPriceYuan();
				break;
			}
		}
		//total count
		Long num = item.getProductQuantity() * item.getQuantity();
		//total price
		Float totalPrice = price * num;

		request.setTotalAmount(num.toString());
		request.setTotalPrice(totalPrice.toString());

		request.setDetails(new ArrayList<RequestDetail>());

		RequestDetail detail = new RequestDetail();
		detail.setAmount(num.toString());
		detail.setTicketPriceId(item.getProductIdSupplier());

		request.getDetails().add(detail);

		return request;
	}
	
	/**
	 * Create query request object.
	 *
	 * @param passCode the pass code
	 * @return the request
	 */
	private Request createQueryRequest(PassCode passCode) {
		Request request = new Request();
		request.setBatchNo(passCode.getSerialNo());
		request.setIndentCode(passCode.getAddCode());
		return request;
	}

	/**
	 * Redo.
	 *
	 * @param passport the passport
	 * @param times the times
	 * @param error the error
	 */
	private void reapplySet(Passport passport, long times, String error) {
		OrderUtil.init().reapplySet(passport, times);
	}
}
