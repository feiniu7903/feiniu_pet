package com.lvmama.passport.processor.impl;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.impl.client.yzspringmoonlit.YzSpringMoonlitHelp;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;
/**
 * 《春江花月夜-唯美扬州》实景演出
 * @author tangJing
 *
 */
public class YzSpringMoonlitProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor,ResendCodeProcessor{
	private YzSpringMoonlitHelp help = new YzSpringMoonlitHelp();
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private static final Log log = LogFactory.getLog(LishuiAdventureIslandProcessorImpl.class);
	@Override
	public Passport apply(PassCode passCode) {
		log.info("YzSpringMoonlitProcessorImpl Apply Code: " + passCode.getSerialNo());
		Passport passport =new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		try{
			// 获取春江花月夜-唯美扬州的URL地址
			String url = WebServiceConstant.getProperties("yzspringMoonlit.order");
			// 构建申请订单参数
			Map<String,String> requestParas = buildOrderParam(passCode);
			log.info("YzSpringMoonlitProcessorImpl apply req:"+requestParas.toString());
			// 提交订单，并获取返回结果
			String resXml = HttpsUtil.requestPostForm(url, requestParas);
			log.info("YzSpringMoonlitProcessorImpl apply res: " + resXml);
			if (resXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常：" + resXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				reapplySet(passport, passCode.getReapplyCount());
			} else {
				String rspCode = TemplateUtils.getElementValue(resXml, "//result/code");
				String rspDesc = TemplateUtils.getElementValue(resXml, "//result/message");
				// 返回 0 表示提交订单成功
				if(rspCode.equals("0")){
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					passport.setCode(passCode.getSerialNo());
				}else{
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					passport.setComLogContent("供应商返回异常：" + rspDesc);
					reapplySet(passport, passCode.getReapplyCount());
				}
			}
			
		}catch (Exception e) {
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("YzSpringMoonlitProcessorImpl apply error message", e);
		}
		return passport;
	}
	
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("YzSpringMoonlitProcessorImpl Destroy Code: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		passport.setComLogContent("供应商不提供废码接口");
		passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
		passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
		return passport;
	}
	
	/**
	 * 重新申请码处理
	 * 
	 * @param passport
	 * @param error
	 */
	public void reapplySet(Passport passport, long times) {
		int reapplyMinute = 5;
		if (times < 3) {
			passport.setReapplyTime(DateUtils.addMinutes(new Date(),reapplyMinute));
			passport.setStatus(PassportConstant.PASSCODE_STATUS.TEMP_FAILED.name());
		} else {
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
		}
	}
	
	public Map<String, String> buildOrderParam(PassCode passCode) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		// 获取订单对象
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		// 获取联系人
		OrdPerson contact = OrderUtil.init().getContract(ordOrder);
		// 获取采购项目列表中的订单子项
		OrdOrderItemMeta itemMeta =OrderUtil.init().getItemMeta(ordOrder, passCode);
		// 获取采购数量
		Long quantity = itemMeta.getProductQuantity() * itemMeta.getQuantity();
		
		String userName=WebServiceConstant.getProperties("yzspringMoonlit.username");
		String password=WebServiceConstant.getProperties("yzspringMoonlit.password");
		// 代理产品编号（票编号）
		String productIdSupplier = itemMeta.getProductIdSupplier();
		// 代理产品类型（区域编号）
		String productTypeSupplier = itemMeta.getProductTypeSupplier();
		if (StringUtils.isBlank(productIdSupplier)) {
			throw new IllegalArgumentException("代理产品编号不能空");
		}
		if (StringUtils.isBlank(productTypeSupplier)) {
			throw new IllegalArgumentException("代理产品类型不能空");
		}
		productIdSupplier = productIdSupplier.trim();
		productTypeSupplier = productTypeSupplier.trim();
		// 游玩日期
		String visitTime = DateFormatUtils.format(itemMeta.getVisitTime(), "yyyy-MM-dd");
		// 票务查询接口，对比 票编号 和  区域编号，然后取出对应的programTimeNo（场次编号），leftSeat（余票数量）
		Map<String, String> returnMap = help.queryTicket(visitTime, productIdSupplier, productTypeSupplier);
		// 获取余票数
		long leftSeat = Long.parseLong(returnMap.get("leftSeat"));
		// 检验票的数量是否够
		if (quantity > leftSeat) {
			throw new IllegalArgumentException("余票数量不足");
		}
		// 场次编号
		String programTimeNo = returnMap.get("programTimeNo");
		
		// 入场券信息。示例（12-1-2）12表示票编号，1表示区域编号，2表示票数量；
		String ticketInfo = productIdSupplier + "-" + productTypeSupplier + "-" + quantity;
		// 游玩时间
		params.put("playDate", visitTime);
		params.put("programTimeNo", programTimeNo);
		params.put("ticketInfo", ticketInfo);
		params.put("touristName", contact.getName());
		params.put("touristCellPhone", contact.getMobile());
		params.put("touristIdNo", contact.getCertNo());
		params.put("orderNo", passCode.getSerialNo());
		params.put("userName", userName);
		params.put("password", password);
		params.put("paymentType", "1");// 1预存 2签单
		// 生成签名信息
		String checkValue = help.makeSign(params);
		params.put("checkValue",checkValue);

		return params;
	}

	public Map<String, String> buildParams(PassCode passcode){
		Map<String,String> requestParas=new LinkedHashMap<String, String>();
		try{
			String userName=WebServiceConstant.getProperties("yzspringMoonlit.username");
			String password=WebServiceConstant.getProperties("yzspringMoonlit.password");
			requestParas.put("orderNo",passcode.getSerialNo());
			requestParas.put("userName", userName);
			requestParas.put("password", password);
			String checkValue = help.makeSign(requestParas);
			requestParas.put("checkValue", checkValue);
		}catch(Exception e){
		 e.printStackTrace();
		}
		return requestParas;
	}

	@Override
	public Passport resend(PassCode passCode) {
		log.info("YzSpringMoonlitProcessorImpl resend serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		passport.setSerialno(passCode.getSerialNo());
		try {
			String url=WebServiceConstant.getProperties("yzspringMoonlit.order.resendSMS");
			Map<String,String> requestParas=buildParams(passCode);
			log.info("YzSpringMoonlitProcessorImpl resend req:"+requestParas.toString());
			String resXml=HttpsUtil.requestPostForm(url, requestParas);
			log.info("YzSpringMoonlitProcessorImpl resend res: " + resXml);
			if (resXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setComLogContent("供应商返回异常：" + resXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount());
			} else {
				String rspCode = TemplateUtils.getElementValue(resXml, "//result/code");
				String rspDesc = TemplateUtils.getElementValue(resXml, "//result/message");
				if(rspCode.equals("0")){
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				}else{
					passport.setComLogContent("供应商返回异常：" + rspDesc);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("OverseasChinatown resend fail message: " + rspCode + " " + rspDesc);
				}
			}
		}catch (Exception e) {
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("OverseasChinatown resend error message", e);
		}
		return passport;
	}

	public static void main(String[] args) {
		try{
			//下单测试
//			Map<String, String> params = new LinkedHashMap<String, String>();
//			System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
//			params.put("playDate",DateFormatUtils.format(new Date(), "yyyy-MM-dd"));//游玩日期
//			params.put("programTimeNo","187");//场次编号
//			params.put("ticketInfo","1-3-1");//订票信息：票编号-区域编号-票数量
//			params.put("touristName","小汤测试单");//游客姓名
//			params.put("touristCellPhone","15026847838");//游客手机号码
//			params.put("touristIdNo","420621198810141243");//游客身份证号码
//			params.put("orderNo","20131219151502");//订单编号
//			params.put("userName","shlmm");//用户名
//			params.put("password","111111");//密码
//			params.put("paymentType","1");//支付方式：1预存 2签单
//			String checkValue = makeSign(params);
//			params.put("checkValue",checkValue);
//			String resXml=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("yzspringMoonlit.order"), params);
//			System.out.println(resXml);
			
//			//参数构建
//			Map<String,String> requestParas=new LinkedHashMap<String, String>();
//			String userName="shlmm";//WebServiceConstant.getProperties("yzspringMoonlit.username");
//			String password="111111";//WebServiceConstant.getProperties("yzspringMoonlit.password");
//			requestParas.put("orderNo", "20131219151502");
//			requestParas.put("userName", userName);
//			requestParas.put("password", password);
//			String check=makeSign(requestParas);
//			requestParas.put("checkValue", check);		
//						
////			//重发凭证短信
//			String url=WebServiceConstant.getProperties("yzspringMoonlit.order.resendSMS");
//			String resXml2=HttpsUtil.requestPostForm(url, requestParas);
//			log.info("YzSpringMoonlit rensend res: " + resXml2);
			
			
			// 查询
//			Map<String,String> requestParas=new LinkedHashMap<String, String>();
//			String userName="shlmm";//WebServiceConstant.getProperties("yzspringMoonlit.username");
//			String password="111111";//WebServiceConstant.getProperties("yzspringMoonlit.password");
//			requestParas.put("playDate", "2014-02-26");
//			requestParas.put("userName", userName);
//			requestParas.put("password", password);
//			String check=makeSign(requestParas);
//			requestParas.put("checkValue", check);		
//						
//			String url=WebServiceConstant.getProperties("yzspringMoonlit.order.query");
//			String resXml2=HttpsUtil.requestPostForm(url, requestParas);
//			log.info("YzSpringMoonlit rensend res: " + resXml2);
			
			YzSpringMoonlitHelp help = new YzSpringMoonlitHelp();
			Map<String, String> map = help.queryTicket("2014-03-05", "1", "1");
			System.out.println(map.toString());
	        
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
}
