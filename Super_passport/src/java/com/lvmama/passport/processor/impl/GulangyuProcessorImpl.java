package com.lvmama.passport.processor.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.impl.client.gulangyu.GulangyuUtil;
import com.lvmama.passport.processor.impl.client.gulangyu.GulangyuWebServiceClient;
import com.lvmama.passport.processor.impl.client.gulangyu.model.OrderCancelResponse;
import com.lvmama.passport.processor.impl.client.gulangyu.model.OrderSubmitRequest;
import com.lvmama.passport.processor.impl.client.gulangyu.model.OrderSubmitResponse;
import com.lvmama.passport.processor.impl.client.gulangyu.model.ScenicSpot;
import com.lvmama.passport.processor.impl.client.gulangyu.model.ScenicSpotResponse;
import com.lvmama.passport.processor.impl.client.gulangyu.model.SendMsgResponse;
import com.lvmama.passport.processor.impl.client.gulangyu.model.Ticket;
import com.lvmama.passport.processor.impl.client.gulangyu.model.TicketResponse;
import com.lvmama.passport.processor.impl.util.OrderUtil;

/**
 * 鼓浪屿对接实现
 * @author lipengcheng
 *
 */
public class GulangyuProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor ,ResendCodeProcessor{
	/** 日志对象 */
	private static final Log log = LogFactory.getLog(GulangyuProcessorImpl.class);
	/** 错误信息MAP */
	private static Map<String, String> errorMap = new HashMap<String, String>();
	
	/** 订单查询接口*/
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	
	public GulangyuProcessorImpl() {
		if (errorMap.isEmpty()) {
			errorMap.put("101", "无授权，拒绝连接");
			errorMap.put("102", "传输数据类型出错");
			errorMap.put("103", "传输数据格式出错");
			errorMap.put("104", "数据无法传输");
			errorMap.put("105", "数据为空或不存在或重叠");
			errorMap.put("106", "数据服务出错，无法提交订单");
			errorMap.put("107", "返回数据非XML格式");
			errorMap.put("108", "网络中断");
			errorMap.put("109", "重复查询");
			errorMap.put("110", "重复提交");
			errorMap.put("111", "数据含有非法字符");
			errorMap.put("112", "手机格式错误");
			errorMap.put("113", "错误的联票方式");
			errorMap.put("114", "编码出错，无法继续");
			errorMap.put("115", "网站服务出错，请联系技术开发人员");
			errorMap.put("116", "短信重发次数过多");
			errorMap.put("117", "在线支付未支付成功无法发短信");
			errorMap.put("118", "终端信息出错，请联系技术人员");
			errorMap.put("119", "订单状态出错，请联系技术人员");
			errorMap.put("120", "订单号已超出系统承载,无法生成");
			errorMap.put("121", "状态参数出错");
			errorMap.put("122", "订单号已存在,无法提交");
		}
	}
	
	/**
	 * 申码
	 */
	public Passport apply(PassCode passCode) {
		log.info("Gulangyu Apply Code: "+passCode.getSerialNo());
		Passport passport = new Passport();
		Long startTime = 0L;
		try {
			OrderSubmitRequest orderSubmitRequest = this.applyCode(passCode);
			this.printRequestData(orderSubmitRequest);
			startTime = System.currentTimeMillis();
			String result = GulangyuWebServiceClient.getOrderSubmitResult(orderSubmitRequest);
			log.info("Gulangyu Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info(result);
			if (!errorMap.containsKey(result)) {
				OrderSubmitResponse orderSubmitResponse = GulangyuUtil.getOrderSubmitResponse(result);
				passport.setCode(orderSubmitResponse.getOrderInfo().getUuCertnum());
				passport.setExtId(orderSubmitResponse.getOrderInfo().getUu16uOrder());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				String error = errorMap.get(result);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常："+error);
				log.info("Gulangyu Apply Error message:" + error);
				this.reapplySet(passport, passCode.getReapplyCount());
			}
		} catch (Exception e) {
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			passport.setComLogContent(e.getMessage());
			log.error("Gulangyu Apply Error message:" , e);
			this.reapplySet(passport, passCode.getReapplyCount());
		}
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		passport.setSendOrderid(true);
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		return passport;
	}
	
	/**
	 * 打印申码信息
	 * @param orderSubmitRequest
	 */
	private void printRequestData(OrderSubmitRequest orderSubmitRequest){
		log.info("uuLid: " + orderSubmitRequest.getUuLid());
		log.info("uuTid: " + orderSubmitRequest.getUuTid());
		log.info("orderId: " + orderSubmitRequest.getOrderId());
		log.info("visitTime: " + orderSubmitRequest.getVisitTime());
		log.info("ticketNum: " + orderSubmitRequest.getTicketNum());
		log.info("ticketPrice: " + orderSubmitRequest.getTicketPrice());
		log.info("customerName: " + orderSubmitRequest.getCustomerName());
		log.info("mobile: " + orderSubmitRequest.getMobile());
		log.info("createTime: " + orderSubmitRequest.getCreateTime());
		log.info("paymentType: " + orderSubmitRequest.getPaymentType());
		log.info("paymentStatus: " + orderSubmitRequest.getPaymentStatus());
		log.info("totalAmount: " + orderSubmitRequest.getTotalAmount());
		log.info("unionType: " + orderSubmitRequest.getUnionType());
		log.info("unionId: " + orderSubmitRequest.getUnionId());
		log.info("memberId: " + orderSubmitRequest.getMemberId());
	}
	
	
	/**
	 * 重发短信
	 */
	public Passport resend(PassCode passCode) {
		log.info("Gulangyu Resend SMS: "+passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		passport.setSerialno(passCode.getSerialNo());
		try {
			String result = GulangyuWebServiceClient.getReSendMsgResult(passCode.getExtId());
			log.info(result);
			if(!errorMap.containsKey(result)){
				SendMsgResponse sendMsgResponse = GulangyuUtil.getSendMsgResponse(result);
				String uu16uorder = sendMsgResponse.getSendMsg().getUu16uorder();
				if (passCode.getExtId().equals(uu16uorder)) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					String errorStr = errorMap.get(result);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
					passport.setComLogContent("供应商返回异常："+errorStr);
					log.info("Gulangyu Resend Exception: "+errorStr);
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				}
			}else{
				String errorStr = errorMap.get(result);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
				passport.setComLogContent("供应商返回异常："+errorStr);
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				
			}
			
		} catch (Exception e) {
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("Gulangyu Resend Exception", e);
		}
		return passport;
	}
	
	/**
	 * 重新申请码处理
	 * @param passport
	 * @param error
	 */
	public void reapplySet(Passport passport,long times){
		OrderUtil.init().reapplySet(passport, times);
	}

	/**
	 * 废码
	 */
	public Passport destroy(PassCode passCode) {
		log.info("Gulangyu Destoy Code :"+passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		passport.setSerialno(passCode.getSerialNo());
		Long startTime = 0L;
		try {
			startTime = System.currentTimeMillis();
			String result = GulangyuWebServiceClient.getOrderCancelResult(passCode.getExtId());
			log.info("Gulangyu destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info(result);
			if (!errorMap.containsKey(result) ) {
				OrderCancelResponse orderCancelResponse = GulangyuUtil.getOrderCancelResponse(result);
				String uuOrdernum = orderCancelResponse.getOrderCancel().getUuOrdernum();//返回的16u订单号
				if(passCode.getExtId().equals(uuOrdernum)){
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());//设置状态（成功）
					log.info("Gulangyu Destoy SUCCESS, message:" + orderCancelResponse.getOrderCancel().getUuStatus());
				}else{
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());//设置状态（失败）
					passport.setComLogContent("供应商返回异常："+uuOrdernum);
					log.info("Gulangyu Destoy Error, uuOrdernum:" + uuOrdernum);
				}
			}else{
				String errorStr = errorMap.get(result);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());//设置状态（失败）
				passport.setComLogContent("供应商返回异常："+errorStr);
				log.error("Gulangyu Destory Error, message:" + errorStr);
			}
		}catch(Exception e){
			log.error("Gulangyu destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());// 设置状态（失败）
			passport.setComLogContent(e.getMessage());
			log.error("Gulangyu Destory Error, message:", e);
		}
		return passport;
	}
	
	
	public static void getAllScenicSpotAndTicekt(){
		try {
			String result = GulangyuWebServiceClient.getAllScenicSpotResult();
			log.info(result);
			ScenicSpotResponse scenicSpotData = GulangyuUtil.getScenicSpotResponse(result);
			for(ScenicSpot  scenicSpot : scenicSpotData.getScenicSpotList()){
				System.out.println("UuId："+scenicSpot.getUuId());
				System.out.println("getUuTitle: " + scenicSpot.getUuTitle());
				System.out.println("UuAddtime："+scenicSpot.getUuAddtime());
				System.out.println("UuImgPath："+scenicSpot.getUuImgPath());
				System.out.println("UuArea："+scenicSpot.getUuArea());
				result = GulangyuWebServiceClient.getTicketResult(scenicSpot.getUuId());
				if(!errorMap.containsKey(result)){
					TicketResponse ticketData = GulangyuUtil.getTicketResponse(result);
					for(Ticket ticket : ticketData.getTicketList()){
//						result = GulangyuWebServiceClient.getSpecialTicket(ticket.getUuLandid(),ticket.getUuId());
						System.out.println("UuId :"+ticket.getUuId());
						System.out.println("UuTitle :"+ticket.getUuTitle());
						System.out.println("UuTprice："+ticket.getUuTprice());
						System.out.println("UuUprice："+ticket.getUuUprice());
						System.out.println("UuGprice："+ticket.getUuGprice());
						System.out.println("UuDelaydays："+ticket.getUuDelaydays());
						System.out.println("UuUniont："+ticket.getUuUniont());
						System.out.println("UuPay："+ticket.getUuPay());
						System.out.println("UuNotes："+ticket.getUuNotes());
						System.out.println("UuWprice："+ticket.getUuWprice());
						System.out.println("UuIfs："+ticket.getUuIfs());
					}
				}else{
					System.out.println(errorMap.get(result));
				}
				/*log.info("*******特价门票");
				result = GulangyuWebServiceClient.getSpecialTicket(scenicSpot.getUuId());
				log.info(result);
				SpecialTicketData specialTicketData = GulangyuUtil.getSpecialTicketData(result);
				for(SpecialTicket specialTicket : specialTicketData.getSpecialTicketList()){
					System.out.println("门票ID :"+specialTicket.getUuId());
					System.out.println("特价说明 :"+specialTicket.getUuPtime());
				}*/
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private OrderSubmitRequest applyCode(PassCode passCode){
		OrderSubmitRequest orderSubmitRequest = new OrderSubmitRequest();
		//以下为组合查询
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(1000000000);
		List<String> targetIdlist = this.buildTargetId(passCode.getPassPortList());
		String targetIds = this.join(",", targetIdlist.iterator());
		compositeQuery.getMetaPerformRelate().setTargetId(targetIds);
		compositeQuery.getMetaPerformRelate().setOrderId(ordOrder.getOrderId());
		List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
		OrdPerson contact = this.getContact(ordOrder);//取得联系人
		for (OrdOrderItemMeta ordOrderItemMeta : orderItemMetas) {
			if (null != ordOrderItemMeta && ordOrderItemMeta.getProductTypeSupplier() != null && 
					!"".equals(ordOrderItemMeta.getProductTypeSupplier())&&
					passCode.getObjectId().intValue()==ordOrderItemMeta.getOrderItemMetaId().intValue()) {
				orderSubmitRequest.setUuLid(ordOrderItemMeta.getProductTypeSupplier());//景区ID
				orderSubmitRequest.setUuTid(ordOrderItemMeta.getProductIdSupplier());//门票ID
				orderSubmitRequest.setOrderId(passCode.getSerialNo());//传入订单号(流水号)
				orderSubmitRequest.setVisitTime(DateFormatUtils.format(ordOrderItemMeta.getVisitTime(), "yyyy-MM-dd"));//游玩时间
				orderSubmitRequest.setTicketNum(String.valueOf(ordOrderItemMeta.getQuantity()*ordOrderItemMeta.getProductQuantity()));//门票数量
				orderSubmitRequest.setTicketPrice(String.valueOf(ordOrderItemMeta.getSettlementPriceYuan()));//单张门票价格
				orderSubmitRequest.setCustomerName(contact.getName());//取票人姓名
				orderSubmitRequest.setMobile(contact.getMobile());//取票人电话
				orderSubmitRequest.setCreateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));//下单时间
				if(ordOrder.isPayToLvmama()){
					orderSubmitRequest.setPaymentType("1");//支付方式：0、景区现付 1、已在线支付
					orderSubmitRequest.setPaymentStatus("1");
				}else{
					orderSubmitRequest.setPaymentType("0");//支付方式：0、景区现付 1、已在线支付
					orderSubmitRequest.setPaymentStatus("0");
				}
				orderSubmitRequest.setTotalAmount(String.valueOf(ordOrderItemMeta.getSettlementPriceToYuan()*ordOrderItemMeta.getQuantity()));//支付总金额
				orderSubmitRequest.setUnionType("0");//联票方式，暂不支持联票
				orderSubmitRequest.setUnionId("0");//联票ID,以逗号隔开，暂不生效
				orderSubmitRequest.setMemberId("");//会员ID
				break;
			}
		}

		return orderSubmitRequest;
	}
	
	/**
	 * 取得联系人
	 * @param order
	 * @return
	 */
	private OrdPerson getContact(OrdOrder order){
		OrdPerson contact = null;
		if (!order.getTravellerList().isEmpty() && StringUtils.isNotBlank(order.getTravellerList().get(0).getMobile())) {
			contact = order.getTravellerList().get(0);
		} else {
			contact = order.getContact();
		}
		return contact;
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
		if (objects.hasNext()){
			buf.append( objects.next() );
		}
		while (objects.hasNext()) {
			buf.append(seperator).append(objects.next());
		}
		return buf.toString();
	}
	
}
