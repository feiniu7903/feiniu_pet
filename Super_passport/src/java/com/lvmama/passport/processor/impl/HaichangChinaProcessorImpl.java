package com.lvmama.passport.processor.impl;
import java.util.HashMap;
import java.util.Map;
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
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.impl.client.haichang.BookingTicket;
import com.lvmama.passport.processor.impl.client.haichang.BookingTicketImplServiceLocator;
import com.lvmama.passport.processor.impl.client.haichang.CancelTicket;
import com.lvmama.passport.processor.impl.client.haichang.CancelTicketImplServiceLocator;
import com.lvmama.passport.processor.impl.client.haichang.Resend;
import com.lvmama.passport.processor.impl.client.haichang.ResendImplServiceLocator;
import com.lvmama.passport.processor.impl.client.haichang.ResultBookingTicket;
import com.lvmama.passport.processor.impl.client.haichang.ResultCancelTicket;
import com.lvmama.passport.processor.impl.client.haichang.ResultResend;
import com.lvmama.passport.processor.impl.client.haichang.model.HaichangOrder;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.WebServiceConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HaichangChinaProcessorImpl implements ApplyCodeProcessor,DestroyCodeProcessor,
		ResendCodeProcessor {
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy
			.getBean("orderServiceProxy");
	private static final Log log = LogFactory
			.getLog(HaichangChinaProcessorImpl.class);

	@Override
	public Passport apply(PassCode passCode) {
		log.info("HaichangChinaProcessorImpl apply code:"
				+ passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		HaichangOrder ord= this.buildOrderInfo(passCode);
		try {
			BookingTicket bookticket = new BookingTicketImplServiceLocator().getBookingTicketImplPort();
			ResultBookingTicket result = bookticket.bookingTicket(
					ord.getAgentOrderId(), ord.getAgentId(),
					ord.getAgentPassword(), ord.getCompanyId(),
					ord.getVisitorName(), ord.getVisitorPhoneNumber(),"","",
					"", "", "", "", "", "", "", ord.getTicketType(),
					ord.getTicketNumber(), ord.getTicketPrice(),
					ord.getTimeStart(), ord.getTimeEnd(),
					ord.getFlagPayOffline(), ord.getFlagPayOnline(), "");
				String code=result.getCode();
				String status=result.getStatus();
				log.info("HaichangChina apply code:" + code);
				log.info("HaichangChina apply status:" +status);
				if(StringUtils.equals(status,"1")){
					if(StringUtils.equals(code,"0x00")){
						passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					}else{
						passport.setComLogContent("供应商返回异常：" +convertErrorCode_order(code));
						passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
						passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
						log.info("HaichangChina apply fail message: " + convertErrorCode_order(code));
					}
				}else{
					passport.setComLogContent("供应商返回异常：" +convertErrorCode_order(code) );
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				}
		} catch (Exception e) {
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("HaichangChinaProcessorImpl apply error message", e);
		}
		return passport;
	}
	
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("HaichangChina DestoyCode code :"+passCode.getSerialNo());
		long startTime=0L;
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		try {
			startTime=System.currentTimeMillis();
			String agentId = WebServiceConstant.getProperties("haichangchina.agentId");
			String agentPassword = WebServiceConstant.getProperties("haichangchina.agentPassword");
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdPerson contact =OrderUtil.init().getContract(ordOrder);
			OrdOrderItemMeta itemMeta =OrderUtil.init().getItemMeta(ordOrder, passCode);
			String productTypeSupplier = itemMeta.getProductTypeSupplier();
			String payType="";
			if(ordOrder.isPayToLvmama()){
				payType="2";
			}
			if(ordOrder.isPayToSupplier()){
				payType="1";
			}
			CancelTicket cancelTicket= new CancelTicketImplServiceLocator().getCancelTicketImplPort();
			ResultCancelTicket cancelResult = cancelTicket.cancelTicket(
					passCode.getSerialNo(), agentId, agentPassword, productTypeSupplier,
					contact.getName(),passCode.getMobile(),payType);
			String code=cancelResult.getCode();
			String status=cancelResult.getStatus();
			if(StringUtils.equals(status,"1")){
				if(StringUtils.equals(code,"0x00")){
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				}else{
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setComLogContent("供应商返回异常："+covertErrorCode_cancel(code));
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("HaichangChina destroy fail message: " + covertErrorCode_cancel(code));
				}
			}else{
				passport.setComLogContent("供应商返回异常：" +covertErrorCode_cancel(code) );
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			}
			
		}catch (Exception e) {
			log.error("HaichangChina destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setComLogContent(e.getMessage());
			log.error("HaichangChina Destoy Exception:", e);
		}
		return passport;
	}

	public HaichangOrder buildOrderInfo(PassCode passCode) {
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode
				.getOrderId());
		OrdPerson contact =OrderUtil.init().getContract(ordOrder);
		OrdOrderItemMeta itemMeta =OrderUtil.init().getItemMeta(ordOrder, passCode);
		String productIdSupplier = itemMeta.getProductIdSupplier();
		String productTypeSupplier = itemMeta.getProductTypeSupplier();
		String visiteTime = DateFormatUtils.format(ordOrder.getVisitTime(),
				"yyyy-MM-dd");
		Long quantity = itemMeta.getQuantity() * itemMeta.getProductQuantity();
		if (StringUtils.isBlank(productIdSupplier)) {
			throw new IllegalArgumentException("代理产品编号不能空");
		}
		if (StringUtils.isBlank(productTypeSupplier)) {
			throw new IllegalArgumentException("代理产品类型不能空");
		}
		HaichangOrder ord=new HaichangOrder();
		String agentId = WebServiceConstant
				.getProperties("haichangchina.agentId");
		String agentPassword = WebServiceConstant
				.getProperties("haichangchina.agentPassword");
		float sellPrice = itemMeta.getSellPriceToYuan();
		ord.setAgentOrderId(passCode.getSerialNo());// 代理商订单号
		ord.setAgentId(agentId);// 代理商编号
		ord.setAgentPassword(agentPassword);// 代理商密码
		ord.setCompanyId(productTypeSupplier.trim());// 公司代码
		ord.setVisitorName(contact.getName());// 游客姓名
		ord.setVisitorPhoneNumber(contact.getMobile());// 游客手机号
		ord.setTicketType(productIdSupplier.trim());// 票种ID
		ord.setTicketNumber(String.valueOf(quantity));// 数量
		ord.setTicketPrice(String.valueOf(sellPrice));// 票价
		ord.setTimeStart(visiteTime + " 00:00:00");// 有效时间开始
		ord.setTimeEnd(visiteTime + " 23:59:59");// 有效时间结束
		if (ordOrder.isPayToLvmama()) {
			ord.setFlagPayOffline("0");// 0:非到付、1:到付
			ord.setFlagPayOnline("1");// 0:非在线支付、1:在线支付
		}else {
			ord.setFlagPayOffline("1");// 0:非到付、1:到付
			ord.setFlagPayOnline("0");// 0:非在线支付、1:在线支付
		}
		return ord;
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

	@Override
	public Passport resend(PassCode passCode) {
		log.info("HaichangChina resend serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		passport.setSerialno(passCode.getSerialNo());
		try {
			String agentId = WebServiceConstant.getProperties("haichangchina.agentId");
			String agentPassword = WebServiceConstant.getProperties("haichangchina.agentPassword");
			Resend resend=new ResendImplServiceLocator().getResendImplPort();
			ResultResend resultresend=resend.resend(passCode.getSerialNo(),agentId, agentPassword,passCode.getMobile());
			String code=resultresend.getCode();
			String status= resultresend.getStatus();
			log.info("HaichangChina resend code:" + code);
			log.info("HaichangChina resend status:" +status);
			if(StringUtils.equals(status,"1")){
				if(StringUtils.equals(code,"0x00")){
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				}else{
					passport.setComLogContent("供应商返回异常：" +code+convertErrorCode_resend(code));
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("HaichangChina resend fail message: " + convertErrorCode_resend(code));
				}
			}else{
				passport.setComLogContent("供应商返回异常：" +convertErrorCode_resend(code) );
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			}
		} catch (Exception e) {
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("HaichangChina resend error message", e);
		}
		return passport;
	}
	
	public static String convertErrorCode_order(String code){
		Map<String,String> codeMap=new HashMap<String, String>();
		codeMap.put("0x00", "处理成功");
		codeMap.put("0x01", "AgentID(代理商编号)为空或格式不正确");
		codeMap.put("0x02", "CompanyID(公司ID)为空或格式不正确");
		codeMap.put("0x03", "VisitorPhoneNumber(游客手机号)为空或格式不正确");
		codeMap.put("0x05", "TicketType(票种ID)为空或格式不正确");
		codeMap.put("0x06", "TicketNumber(票种数量)为空或格式不正确");
		codeMap.put("0x07", "TimeStart(有效时间开始)为空或格式不正确");
		codeMap.put("0x08", "TimeStart(有效时间开始)晚于TimeEnd(有效时间结束)");
		codeMap.put("0x09", "TimeEnd(有效时间结束)为空或格式不正确");
		codeMap.put("0x0A", "FlagPayOffline(到付标识)为空或格式不正确");
		codeMap.put("0x0B", "FlagPayOnline(支付标识)为空或格式不正确");
		codeMap.put("0x0C", "AgentOrderID(代理商订单号)为空或格式不正确");
		codeMap.put("0x0D", "AgentPassword(代理商密码)为空或格式不正确");
		codeMap.put("0x0E", "TicketType(票种ID)、TicketNumber(数量)和 TicketPrice(票价)数量不同");
		codeMap.put("0x0F", "代理商不存在或密码错误");
		codeMap.put("0x10", "代理商过期、不能购票或停用");
		codeMap.put("0x11", "含有不存在的票种或非该公司票种");
		codeMap.put("0x12", "含有过期或未通过的票种");
		codeMap.put("0x13", "含有代理商不可用的票种");
		codeMap.put("0x14", "TicketPrice(票价)为空或格式不正确");
		codeMap.put("0x15", "代理商订单号重复");
		codeMap.put("0x97", "序列配置错误");
		codeMap.put("0x98", "数据字典配置错误");
		codeMap.put("0x99", "其他错误");
		return codeMap.get(code);
	}

	
	public static String convertErrorCode_resend(String code){
		Map<String,String> codeMap=new HashMap<String, String>();
		codeMap.put("0x00", "处理成功");
		codeMap.put("0x01", "AgentOrderID(代理商订单号)为空或格式不正确");
		codeMap.put("0x02", "AgentID(代理商编号)为空或格式不正确");
		codeMap.put("0x03", "AgentPassword(代理商密码)为空或格式不正确");
		codeMap.put("0x04", "VisitorPhoneNumber(游客手机号)为空或格式不正确");
		codeMap.put("0x05", "订单不存在");
		codeMap.put("0x06", "该订单不属于该代理商");
		codeMap.put("0x07", "代理商不存在或密码错误");
		codeMap.put("0x08", "代理商过期、不能购票或停用");
		codeMap.put("0x09", "订单过期");
		codeMap.put("0x0A", " 该订单重发次数超过2次");
		return codeMap.get(code);
	}
	
	public static String covertErrorCode_cancel(String code){
		Map<String,String> codeMap=new HashMap<String, String>();
		codeMap.put("0x01", "AgentOrderID(代理商订单号)为空或格式不正确");
		codeMap.put("0x02", "AgentID(代理商编号)为空或格式不正确");
		codeMap.put("0x03", "AgentPassword(代理商密码)为空或格式不正确");
		codeMap.put("0x04", "VisitorPhoneNumber(游客手机号)为空或格式不正确");
		codeMap.put("0x05", "订单不存在");
		codeMap.put("0x06", "该订单不属于该代理商");
		codeMap.put("0x07", " 该订单下有票已被使用");
		codeMap.put("0x08", "该订单已退订");
		codeMap.put("0x16", "代理商订单号不存在");
		codeMap.put("0x17", " 传入标识位不正确、到付出1、在线支付传入2");
		codeMap.put("0x18", " 该订单下存在无法已兑换的票或退票、请联系客服人员进行人工核对");
		codeMap.put("0x19", " 传入的标识位与订票时的标识位不符");
		codeMap.put("0x21", " 订单过期、需人工退票");
		return codeMap.get(code);
	}
	public static void main(String[] args) {
		try {
			String agentId = WebServiceConstant
					.getProperties("haichangchina.agentId");
			String agentPassword = WebServiceConstant
					.getProperties("haichangchina.agentPassword");
//			BookingTicket bookticket = new BookingTicketImplServiceLocator()
//					.getBookingTicketImplPort();
//			ResultBookingTicket result=bookticket.bookingTicket("20140606135709", agentId, agentPassword,
//					"1001", "小汤测试单", "15026847838",
//					"", "", "", "", "", "",
//					"", "", "", "1311211001", "1",
//					"145", "2014-06-12 00:00:00", "2014-06-12 23:59:59", "0",
//					"1", "");
//			 log.info("HaichangChina apply Code:"+result.getCode());
//			 log.info("HaichangChina apply Status:"+result.getStatus());
//			 log.info("HaichangChina apply TimeStamp:"+result.getTimeStamp());
			 
			 
			 CancelTicket cancelTicket= new CancelTicketImplServiceLocator().getCancelTicketImplPort();
			ResultCancelTicket cancelResult = cancelTicket.cancelTicket(
					"20140606135709", agentId, agentPassword, "1001",
					"小汤测试单", "15121052428", "2");
			System.out.println(cancelResult.getStatus());
			System.out.println(cancelResult.getCode());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
