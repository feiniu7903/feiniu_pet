package com.lvmama.order.logic;

import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.pass.PassProvider;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderPersonDAO;
import com.lvmama.order.sms.AperiodicActivateSmsCreator;
import com.lvmama.order.sms.AperiodicCancelActivateSmsCreator;
import com.lvmama.order.sms.AperiodicPaySuccSmsCreator;
import com.lvmama.order.sms.ChimelongSmsCreator;
import com.lvmama.order.sms.DiemPaySuccSMMSCreator;
import com.lvmama.order.sms.DiemPaySuccSmsCreator;
import com.lvmama.order.sms.MergedSmsContentCreator;
import com.lvmama.order.sms.MultiSmsCreator;
import com.lvmama.order.sms.NoWorkSmsCreator;
import com.lvmama.order.sms.NormalPaySuccSmsCreator;
import com.lvmama.order.sms.NormalPayToSupSmsCreator;
import com.lvmama.order.sms.OnWorkSmsCreator;
import com.lvmama.order.sms.OrderCancelSmsCreator;
import com.lvmama.order.sms.OrderForGugongSmsCreator;
import com.lvmama.order.sms.OrderForPaymentSmsCreator;
import com.lvmama.order.sms.OrderPrePaySmsCreator;
import com.lvmama.order.sms.PassportPayToLvmamaPaySmsCreator;
import com.lvmama.order.sms.PassportPayToSupOrderSmsCreator;
import com.lvmama.order.sms.PassportVisitRemindSmsCreator;
import com.lvmama.order.sms.PaymentRemindSmsCreator;
import com.lvmama.order.sms.SingleSmsCreator;
import com.lvmama.order.sms.TrainIssueSmsCreator;
import com.lvmama.passport.dao.PassCodeDAO;
import com.lvmama.sms.SmsSender;
import com.lvmama.sms.dao.ComSmsDAO;

public class SmsSendLogic {
	private SmsSender smsSender;
	private OrderPersonDAO orderPersonDAO;
	private PassCodeDAO passCodeDAO;
	private OrderDAO orderDAO ;
	private ComSmsDAO comSmsDAO;
	private PassCodeService passCodeService;
	private OrderService orderServiceImpl;
	private PerformTargetService performTargetService;
	
	/**
	 * 海外酒店订单的service.
	 */
	//private AbroadhotelOrderService abroadhotelOrderService;

	public void sendCreatePayToLvmamaOrderSucc(Message message) {
		SingleSmsCreator creator = null;
		String mobile = getBookerMobil(message.getObjectId());
		if (isWorkTime()) {
			creator = new OnWorkSmsCreator(message.getObjectId(),mobile);
		} else {
			creator = new NoWorkSmsCreator(message.getObjectId(),mobile);
		}
		ComSms sms = creator.createSingleSms();
		if (sms!=null){
			smsSender.sendSms(sms);
		}
	}

	public void sendCreatePayToSupplierOrderSucc(Message message) {
		SingleSmsCreator creator = null;
		String mobile = getContactMobile(message);
		creator = new NormalPayToSupSmsCreator(message.getObjectId(),mobile);
		ComSms sms = creator.createSingleSms();
		if (sms!=null) {
			smsSender.sendSms(sms);
		}
	}

	public void sendCancelOrder(Message message) {
		String mobile = getBookerMobil(message.getObjectId());
		SingleSmsCreator creator =new OrderCancelSmsCreator(message.getObjectId(),mobile);
		ComSms sms = creator.createSingleSms();
		if (sms!=null){
			smsSender.sendSms(sms);
		}
	}

	public void sendApprovePassPayToLvmama(Message message){
		SingleSmsCreator creator = null;
		String mobile = getContactMobile(message);
		creator = new PaymentRemindSmsCreator(message.getObjectId(),mobile);
		ComSms sms = creator.createSingleSms();
		if (sms!=null){
			smsSender.sendSms(sms);
		}
	}

	public void sendPayToSupNormalCert(Message message) {
		SingleSmsCreator creator = null;
		String mobile = getNormalCertReceiverMobile(message);
		creator = new NormalPayToSupSmsCreator(message.getObjectId(),mobile);
		ComSms sms = creator.createSingleSms();
		if (sms!=null){
			smsSender.sendSms(sms);
		}
	}

	public void sendPayToLvmamaNormalCert(Message message) {
		String mobile=getNormalCertReceiverMobile(message);
		MultiSmsCreator creator = new NormalPaySuccSmsCreator(message.getObjectId(), mobile);
		List<ComSms> smsList = creator.createSmsList();
		if ("CTRIP".equals(orderDAO.selectByPrimaryKey(message.getObjectId()).getChannel())) {
			for (ComSms comSms : smsList) {
				smsSender.sendSms(comSms, "QUNFA");
			}			
		} else {
			for (ComSms comSms : smsList) {
				smsSender.sendSms(comSms);
			}
		}
	}
    /**
     * 发送二维码短信
     * @param codeId
     * @param mobile
     * @param timingFlag 是否定时发送
     */
	public 	void sendSingleDiemCert(Long codeId, String mobile,boolean timingFlag) {
		SingleSmsCreator creator = new DiemPaySuccSmsCreator(codeId, mobile,timingFlag);
		ComSms sms = creator.createSingleSms();
		if(sms!=null){
			if(timingFlag){
				comSmsDAO.insert(sms);
			}else{
				OrdOrder order = orderDAO.selectByPrimaryKey(sms.getObjectId());
				if(!order.hasSelfPack()){
					smsSender.sendSms(sms,codeId);
				}else{//超级自由行订单
					comSmsDAO.insert(sms);
				}
			}
		}
	}
	
	/**
	 * 方特+智友宝 彩信构建短信list
	 * @param codeId
	 * @param mobile
	 */
	public void sendDiemPaySuccSMMSProductSms(Long codeId,String mobile) {
		MultiSmsCreator creator = new DiemPaySuccSMMSCreator(codeId, mobile);
		List<ComSms> smsList = creator.createSmsList();
			for (ComSms comSms : smsList) {
				smsSender.sendSms(comSms,codeId);
			}
	}
	
	public void sendTrainIssueSms(OrdOrder order,Long orderItemMetaId){
		String mobile=getContactMobile(order.getOrderId());
		SingleSmsCreator creator = new TrainIssueSmsCreator(order.getOrderId(),mobile,orderItemMetaId);
		ComSms sms = creator.createSingleSms();
		if(sms!=null){
			smsSender.sendSms(sms);
		}
	}
	
	public void sendOrderPrePay(Message message){
		String mobile=getNormalCertReceiverMobile(message);
		SingleSmsCreator creator = new OrderPrePaySmsCreator(message.getObjectId(), mobile);
		ComSms sms = creator.createSingleSms();
		smsSender.sendSms(sms);
	}
	
	public void sendForPayment(final Long orderId,final String mobile,final String code){
		SingleSmsCreator creator = new OrderForPaymentSmsCreator(orderId, mobile, code);
		ComSms sms=creator.createSingleSms();
		if(sms!=null){
			smsSender.sendSms(sms);
		}
	}
	

	public void sendForGugong(Long orderId,String ylOrderId,String mobile){
		OrderForGugongSmsCreator creator=new OrderForGugongSmsCreator(orderId,ylOrderId,mobile);
		ComSms sms=creator.createSingleSms();
		if(sms!=null){
			smsSender.sendSms(sms);
		}
	}
	
	public void sendForChimelong(Long codeId,String mobile){
		ChimelongSmsCreator creator=new ChimelongSmsCreator(codeId,mobile);
		ComSms sms=creator.createSingleSms();
		if(sms!=null){
			smsSender.sendSms(sms);
		}
	}


	public void sendMultiDiemCert(Message message) {
		List<PassCode> codes = passCodeDAO.getPassCodeByOrderId(message.getObjectId());
		
		boolean isMergeCertificateSMS = false;
		for (int i = 0; i < codes.size(); i++) {
			PassCode passCode = codes.get(i);
			List<PassPortCode> passport= passCodeService.searchPassPortByCodeId(passCode.getCodeId());
			isMergeCertificateSMS = passport.get(0).isMergeCertificateSMS();
			if(isMergeCertificateSMS){
				break;
			}
		}
		
		//凭证短信合并发送
		if(isMergeCertificateSMS){
			this.sendMergedSmsContent(codes.get(0),message.getAddition(), true, false);
		}else{
			for (int i = 0; i < codes.size(); i++) {
				PassCode passCode = codes.get(i);
				if (passCode.isApplySuccess()) {
					sendSingleDiemCert(passCode.getCodeId(), message.getAddition(),false);
				}
			}
		}
	}
	/**
	 * 发送 二维码订单，二维码在线支付支付成功短信
	 * @param message
	 */
	public void sendPassportPayToLvmamaPaySuccess(Message message){
		String mobile=getNormalCertReceiverMobile(message);
		SingleSmsCreator creator = new PassportPayToLvmamaPaySmsCreator(message.getObjectId(), mobile);
		ComSms sms = creator.createSingleSms();
		smsSender.sendSms(sms);
	}
	/**
	 * 发送二维码订单，景区支付并下单成功短信
	 * @param message
	 */
	public void sendPassportPayToSupplierOrderSuccess(Message message){
		String mobile=getNormalCertReceiverMobile(message);
		SingleSmsCreator creator = new PassportPayToSupOrderSmsCreator(message.getObjectId(), mobile);
		ComSms sms = creator.createSingleSms();
		smsSender.sendSms(sms);
	}
	/**
	 * 定时发送二维码订单，二维码游玩提醒
	 * @param message
	 */
	public void sendPassportVisitRemindByTiming(Message message,boolean timingFlag){
		String mobile=getNormalCertReceiverMobile(message);
		SingleSmsCreator creator = new PassportVisitRemindSmsCreator(message.getObjectId(), mobile,timingFlag);
		ComSms sms = creator.createSingleSms();
		if(sms!=null){
			if(timingFlag){
				comSmsDAO.insert(sms);
			}else{
				smsSender.sendSms(sms);
			}
		}
	}

	private String getContactMobile(Message message) {
		String mobile=null;
		if (StringUtil.validMobileNumber(message.getAddition())) {
			mobile=message.getAddition().trim();
		}else{
			mobile=orderPersonDAO.getOrdPersonMobile(message.getObjectId(), Constant.ORD_PERSON_TYPE.CONTACT.name());
		}
		return mobile;
	}
	
	private String getContactMobile(Long orderId) {
		String mobile=orderPersonDAO.getOrdPersonMobile(orderId, Constant.ORD_PERSON_TYPE.CONTACT.name());
		return mobile;
	}
	

	public String getBookerMobil(Long orderId){
		String booker = orderPersonDAO.getOrdPersonMobile(orderId, Constant.ORD_PERSON_TYPE.BOOKER.name());
		if(booker!=null){
			return booker;
		}else {
			return orderPersonDAO.getOrdPersonMobile(orderId, Constant.ORD_PERSON_TYPE.CONTACT.name());
		}
	}
	
	/**
	 * 获取普通凭证短信的短信接收人
	 * 默认为第一游玩人，当游玩人为空或者无手机，则为联系人
	 * 
	 * @param message
	 * @return
	 * 
	 * @author Brian
	 */
	private String getNormalCertReceiverMobile(Message message) {
		String mobile=null;
		if (StringUtil.validMobileNumber(message.getAddition())) {
			mobile=message.getAddition().trim();
		}else{
			mobile = orderPersonDAO.getOrdPersonMobile(message.getObjectId(), Constant.ORD_PERSON_TYPE.TRAVELLER.name());
			if (null == mobile) {
				mobile =  getContactMobile(message);
			}
		}
		return mobile;		
	}

	/**
	 * 下单人电话号码BOOKER
	 * @param orderId
	 * @return
	 */
	public String getBookerMobile(Long orderId) {
		OrdPerson pparam = new OrdPerson();
		pparam.setObjectId(orderId);
		pparam.setPersonType("BOOKER");
		List<OrdPerson> opList = orderPersonDAO.getOrdPersons(pparam);//查询订单联系人
		OrdPerson contact = opList.size()!=0?opList.get(0):null;
		return contact.getMobile();
	}

	public static boolean isWorkTime() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.HOUR_OF_DAY)>=8&&cal.get(Calendar.HOUR_OF_DAY)<=23;
	}

	/**
	 * 是否走定时逻辑--如果设备室银河和驴妈妈自有设备时
	 * @param orderId
	 * @return
	 */
	public boolean isTimingLogic(Long orderId){
		List<PassProvider> passProviders = passCodeService.selectPassProviderByOrderId(orderId);
		boolean isTiming = true;
		for (PassProvider passProvider : passProviders) {
			if(!("false".equals(passProvider.getSendSms()) && ("银河".equals(passProvider.getName()) || passProvider.getName().startsWith("LVMAMA")) )){
				isTiming =false;
				break;
			}
		}
		return isTiming;
	}
	/**
	 * 是否立即发送二维码
	 * @return
	 */
	public boolean isNowSendPasscode(Long orderId){
		return true;
		/*OrdOrder order = orderServiceImpl.queryOrdOrderByOrderId(orderId);
		//手机当天订时 立即发送
		if(order.hasTodayOrder()){
			return true;
		}
		//游玩时间不为空时，并当前日期是游玩前一天或当前日期就是游玩日期 就立即发送
		if(order.getVisitTime()!=null 
				&& (DateUtil.formatDate(DateUtil.dsDay_Date(order.getVisitTime(), -1), "yyyyMMdd")
						.equals(DateUtil.formatDate(new Date(), "yyyyMMdd"))
					|| DateUtil.formatDate(order.getVisitTime(), "yyyyMMdd")
						.equals(DateUtil.formatDate(new Date(), "yyyyMMdd"))
				)){
			return true;
		}
		return false;*/
	}
	/**
	 * 是否只有故宫或方特的产品
	 * @param order
	 * @return
	 */
	public boolean isOnlyGuGongOrFangteSupplier(Long orderId) {
		OrdOrder order = orderServiceImpl.queryOrdOrderByOrderId(orderId);
		List<OrdOrderItemMeta> ordOrderItemMetaList = order.getAllOrdOrderItemMetas();
		int guGongOrFangteSupCount = 0;
		int otherSupCount = 0;
		for (OrdOrderItemMeta ordOrderItemMeta : ordOrderItemMetaList) {
			List<SupPerformTarget> supPerformTargetList = performTargetService.findSuperSupPerformTargetByMetaProductId(ordOrderItemMeta.getMetaProductId());
			for (SupPerformTarget supPerformTarget : supPerformTargetList) {
				if(supPerformTarget.getSupplierId().equals(Constant.getInstance().getGugongSupplierId())
						||supPerformTarget.getSupplierId().equals(Constant.getInstance().getFangteSupplierId())){
					guGongOrFangteSupCount++;
				}else{
					otherSupCount++;
				}
			}
		}
		if(otherSupCount==0 && guGongOrFangteSupCount>0){
			return true;
		}
		return false;
	}
	public void setSmsSender(SmsSender smsSender) {
		this.smsSender = smsSender;
	}

	public void setOrderPersonDAO(OrderPersonDAO orderPersonDAO) {
		this.orderPersonDAO = orderPersonDAO;
	}

	public void setPassCodeDAO(PassCodeDAO passCodeDAO) {
		this.passCodeDAO = passCodeDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
		
	public void setComSmsDAO(ComSmsDAO comSmsDAO) {
		this.comSmsDAO = comSmsDAO;
	}
	
//	public void setAbroadhotelOrderService(
//			AbroadhotelOrderService abroadhotelOrderService) {
//		this.abroadhotelOrderService = abroadhotelOrderService;
//	}
	public void sendAperiodicPaySuccCert(Message message) {
		String mobile=getNormalCertReceiverMobile(message);
		SingleSmsCreator creator = new AperiodicPaySuccSmsCreator(message.getObjectId(), mobile);
		ComSms sms = creator.createSingleSms();
		if(sms != null) {
			smsSender.sendSms(sms);
		}
	}
	
	public void sendAperiodicActivateSms(Message message) {
		String mobile=getNormalCertReceiverMobile(message);
		String add = message.getAddition();
		if(StringUtils.isNotEmpty(add)) {
			String[] orderItemMetaIds = add.split(",");
			if(orderItemMetaIds != null && orderItemMetaIds.length > 0) {
				SingleSmsCreator creator = new AperiodicActivateSmsCreator(message.getObjectId(), mobile, orderItemMetaIds);
				ComSms sms = creator.createSingleSms();
				if(sms != null) {
					smsSender.sendSms(sms);
				}
			}
		}
	}
	
	public void sendAperiodicCancelActivateSms(Message message) {
		String mobile=getNormalCertReceiverMobile(message);
		String add = message.getAddition();
		if(StringUtils.isNotEmpty(add)) {
			String[] orderItemMetaIds = add.split(",");
			if(orderItemMetaIds != null && orderItemMetaIds.length > 0) {
				SingleSmsCreator creator = new AperiodicCancelActivateSmsCreator(message.getObjectId(), mobile, orderItemMetaIds);
				ComSms sms = creator.createSingleSms();
				if(sms != null) {
					smsSender.sendSms(sms);
				}
			}
		}
	}


	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setOrderServiceImpl(OrderService orderServiceImpl) {
		this.orderServiceImpl = orderServiceImpl;
	}
	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}

	public void sendMergedSmsContent(PassCode passCode, String mobile, boolean mergeCertificateSMS, boolean generateSMS) {
		MergedSmsContentCreator creator = new MergedSmsContentCreator(passCode,mobile, mergeCertificateSMS,generateSMS);
		List<ComSms> smsList=creator.createSmsList();
		
		if(smsList == null) return;
		
		for(ComSms sms : smsList){
			smsSender.sendSms(sms);
		}
	}
}
