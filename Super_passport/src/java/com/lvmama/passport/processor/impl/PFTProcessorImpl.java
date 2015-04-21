package com.lvmama.passport.processor.impl;

import java.util.HashMap;
import java.util.Map;

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
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.impl.client.pft.PFTMXLocator;
import com.lvmama.passport.processor.impl.client.pft.PFTMXPort;
import com.lvmama.passport.processor.impl.client.pft.model.PFTOrder;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.WebServiceConstant;



public class PFTProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor{
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy
			.getBean("orderServiceProxy");
	private static final Log log = LogFactory
			.getLog(PFTProcessorImpl.class);

	@Override
	public Passport apply(PassCode passCode) {
		log.info("PFTProcessorImpl apply code:"
				+ passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		PFTOrder ord= this.buildOrderInfo(passCode);
		log.info("The value of ScenceFlag is:"+ord.getScenceFlag());
		try {
			PFTMXPort pftMXPort = new PFTMXLocator(ord.getScenceFlag()).getPFTMXPort();
			String result = pftMXPort.PFT_Order_Submit(ord.getAgentAcc(), ord.getAgentPwd(), 
					ord.getScenicSpotId(), ord.getTicketId(), ord.getOrderId(), ord.getTicketPrice(), 
					ord.getTicketNum(), ord.getVisitDate(), ord.getUserNameWhoGetTicket(), ord.getUserMobilePhoneWhoGetTicket(), 
					ord.getContactorMobilePhone(), ord.getIsNeedSendSMS(), ord.getPayType(), ord.getOrderSubmitType(),
					ord.getAssemblyArea(), ord.getTeamNo(), ord.getUnionTicketId(), ord.getPackageTicketId(), ord.getSupplierId());
            
			String uuErrorcode = TemplateUtils.getElementValue(result, "//Data/Rec/UUerrorcode");
			String uuCode = TemplateUtils.getElementValue(result, "//Data/Rec/UUcode");
			String uuOrdernum = TemplateUtils.getElementValue(result, "//Data/Rec/UUordernum");//票付通订单号
			String uuQrcodeIMG = TemplateUtils.getElementValue(result, "//Data/Rec/UUqrcodeIMG");//二维码地址
			//申码成功
			if (null==uuErrorcode) {
				log.info("PFT apply code Success,UUCode is:" + uuCode);
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				//保存二维码地址
				passport.setCode(uuQrcodeIMG);
				//保存票付通唯一的订单号
				passport.setExtId(uuOrdernum);
				//保存凭证号
				passport.setAddCode(uuCode);
			}else{
				System.out.println("apply code failed....Errcode is:"+uuErrorcode);	
				passport.setComLogContent("供应商返回异常：" +convertErrorCode_order(uuErrorcode));
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.info("PFT apply fail message: " + convertErrorCode_order(uuErrorcode));
			}
		} catch (Exception e) {
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("PFTProcessorImpl apply error message", e);
		}
		return passport;
	}
	
	public PFTOrder buildOrderInfo(PassCode passCode) {
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode
				.getOrderId());
		OrdPerson contact =OrderUtil.init().getContract(ordOrder);
		OrdOrderItemMeta itemMeta =OrderUtil.init().getItemMeta(ordOrder, passCode);
		String[] supplierIdAndScenicSpotId = itemMeta.getProductIdSupplier().split("\\|");//这里放景区标识+'|'+供应商ID+'|'+景区ID+'|'+门票ID
		String scenceFlag = supplierIdAndScenicSpotId[0];//这里为景区标识 如"jxlhs","yjxssj"
		String supplierId = supplierIdAndScenicSpotId[1];//这里为供应商ID
		String scenicSpotId = supplierIdAndScenicSpotId[2];//这里为景区ID
		String ticketId = supplierIdAndScenicSpotId[3];//这里放门票ID
		String visitDate = DateFormatUtils.format(ordOrder.getVisitTime(),
				"yyyy-MM-dd");
		Long ticketNum = itemMeta.getQuantity() * itemMeta.getProductQuantity();
		if (StringUtils.isBlank(scenicSpotId)) {
			throw new IllegalArgumentException("代理产品编号(景区ID)不能空");
		}
		if (StringUtils.isBlank(ticketId)) {
			throw new IllegalArgumentException("代理产品类型(门票ID)不能空");
		}
		PFTOrder ord=new PFTOrder();
		//根据传入参数不同返回不同的账户密码
		String agentAcc = getPFTAgentAcc(scenceFlag);
		String agentPwd = getPFTAgentPwd(scenceFlag);
		float ticketPrice = itemMeta.getSettlementPriceToYuan()*100;//票价以分为单位
		ord.setAgentAcc(agentAcc);
		ord.setAgentPwd(agentPwd);
		ord.setScenceFlag(scenceFlag);
		ord.setScenicSpotId(scenicSpotId);
		ord.setTicketId(ticketId);
		ord.setOrderId(passCode.getSerialNo());// 代理商订单号
		ord.setTicketPrice(String.valueOf(ticketPrice));//门票价格(驴妈妈结算价)
		ord.setTicketNum(String.valueOf(ticketNum));//门票数量
		ord.setVisitDate(visitDate);//游玩日期
		ord.setUserNameWhoGetTicket(contact.getName());//取票人姓名
		ord.setUserMobilePhoneWhoGetTicket(contact.getMobile());//取票人手机
		ord.setContactorMobilePhone(contact.getMobile());//联系人手机
		ord.setIsNeedSendSMS("1");//短信由驴妈妈发送，所以这里填"1"
		if (ordOrder.isPayToLvmama()){
			ord.setPayType("2");//0:使用账户余额   2:使用供应商处余额4.现场支付
		}else if (ordOrder.isPayToSupplier()){
			ord.setPayType("4");
		}
		ord.setOrderSubmitType("0");//下单方式
		ord.setAssemblyArea("");//集合地点 <置空>
		ord.setTeamNo("");//团号 <置空>
		ord.setUnionTicketId("0");//联票ID
		ord.setPackageTicketId("0");//套票ID
		ord.setSupplierId(supplierId);//供应商ID
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
	
    public String getPFTAgentAcc(String sceneFlag){
    	//拼接景区类型名
        String sceneTypeName = "pft"+sceneFlag+".agentId"; 
    	return WebServiceConstant.getProperties(sceneTypeName);
    }
    
    public String getPFTAgentPwd(String sceneFlag){
    	//拼接景区类型名
        String sceneTypeName = "pft"+sceneFlag+".agentPassword"; 
    	return WebServiceConstant.getProperties(sceneTypeName);
    }
	
	public static String convertErrorCode_order(String code){
		Map<String,String> codeMap=new HashMap<String, String>();
		codeMap.put("101", "无授权，拒绝连接");
		codeMap.put("102", "传输数据类型出错");
		codeMap.put("103", "传输数据格式出错");
		codeMap.put("104", "数据无法传输");
		codeMap.put("105", "数据为空或重叠");
		codeMap.put("106", "数据服务出错，无法提交订单");
		codeMap.put("107", "返回数据非XML 格式");
		codeMap.put("108", "网络中断");
		codeMap.put("109", "重复查询");
		codeMap.put("110", "重复提交");
		codeMap.put("111", "数据含有非法字符");
		codeMap.put("112", "手机格式错误");
		codeMap.put("113", "错误的联票方式");
		codeMap.put("114", "编码出错，无法继续");
		codeMap.put("115", "网站服务出错，请联系技术开发人员");
		codeMap.put("116", "短信重发次数过多");
		codeMap.put("117", "在线支付未支付成功无法发短信");
		codeMap.put("118", "终端信息出错，请联系技术人员");
		codeMap.put("119", "订单状态出错，请联系技术人员");
		codeMap.put("120", "订单号已超出系统承载，无法生成");
		codeMap.put("121", "状态参数出错");
		codeMap.put("122", "资金账户余额不足");
		codeMap.put("123", "未知的订单号");
		codeMap.put("124", "权限不够");
		
		codeMap.put("130", "邮件格式错误");
		codeMap.put("131", "手机格式错误");
		codeMap.put("132", "短信插入失败");
		codeMap.put("133", "时间未到");
		codeMap.put("134", "时间超过");
		codeMap.put("141", "无修改返回");
		codeMap.put("142", "存在时间交集");
		codeMap.put("143", "未按照预期更新数据");
		codeMap.put("144", "参加优惠计划的订单不支持修改订单");
		codeMap.put("145", "修改订单内容不能全部为空");
		
		codeMap.put("1061", "无此日期的价格");
		codeMap.put("1062", "库存已售罄");
		codeMap.put("1063", "总库存已售罄");
		codeMap.put("1064", "错误的分销差价设置");
		codeMap.put("1065", "无此商品的分销价格");
		codeMap.put("1066", "商品票类数据出错");
		codeMap.put("1067", "商品数据出错");
		codeMap.put("1068", "商品需提前预定");
		codeMap.put("1069", "已超过当日购买的时间");
		codeMap.put("1070", "支付方式错误");
		codeMap.put("1071", "游玩时间必填");
		codeMap.put("1072", "取票人姓名、手机不能为空");
		codeMap.put("1073", "错误的供应链");
		codeMap.put("1074", "短信发送失败");
		codeMap.put("1075", "远端订单号不能重复");
		codeMap.put("1076", "过期的订单无法修改");
		codeMap.put("1077", "订单不是未使用状态，无法取消或修改");
		codeMap.put("1078", "订单修改数量只能减少，不能增加");
		return codeMap.get(code);
	}
	
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("PFTProcessorImpl destory serialNo:"
				+ passCode.getSerialNo());
		
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode
				.getOrderId());
		OrdOrderItemMeta itemMeta =OrderUtil.init().getItemMeta(ordOrder, passCode);
		String[] supplierIdAndScenicSpotId = itemMeta.getProductIdSupplier().split("\\|");//这里放景区标识+'|'+供应商ID+'|'+景区ID+'|'+门票ID
		String scenceFlag = supplierIdAndScenicSpotId[0];//这里为景区标识串如"jxlhs","mrssj"		
		
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		try {
			PFTMXPort pftMXPort = new PFTMXLocator(scenceFlag).getPFTMXPort();
			String agentAcc = this.getPFTAgentAcc(scenceFlag);
			String agentPwd = this.getPFTAgentPwd(scenceFlag);
			String result = pftMXPort.order_Change_Pro(agentAcc, agentPwd, passCode.getExtId(), "0", "", "");//这里取票人手机号可以为空
			String uuErrorcode = TemplateUtils.getElementValue(result, "//Data/Rec/UUerrorcode");
			String uuDone = TemplateUtils.getElementValue(result, "//Data/Rec/UUdone");//返回的取消订单状态,100为成功
			
			String logstr = "ac = "+ agentAcc
					+"  pw = "+agentPwd
					+"  ExtId = "+passCode.getExtId()+ "[end]";
			log.info("PFTProcessorImpl destroy params:"
					+ logstr);
			
			//取消订单成功
			if (StringUtils.equals(uuDone, "100")) {
				log.info("PFT destroy order Success,uuOrdernum is:" + passCode.getExtId());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());//设置状态（成功）
			}else{
				System.out.println("PFT destroy order failed....Errcode is:"+uuErrorcode);	
				passport.setComLogContent("供应商返回异常：" +convertErrorCode_order(uuErrorcode));
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());//设置状态（失败）
				log.info("PFT destroy order failed message: " + convertErrorCode_order(uuErrorcode)+"uuOrdernum is:"+passCode.getExtId());
			}
		} catch (Exception e) {
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			log.error("PFTProcessorImpl destroy order error message", e);
		}
		return passport;
	}

}