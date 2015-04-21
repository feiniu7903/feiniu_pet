package com.lvmama.passport.processor.impl;
import java.util.List;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.impl.client.chimelong.TicketServiceClient;
import com.lvmama.passport.processor.impl.client.chimelong.model.ArrayOfMaXiActTime;
import com.lvmama.passport.processor.impl.client.chimelong.model.CancelResult;
import com.lvmama.passport.processor.impl.client.chimelong.model.MaXiActTime;
import com.lvmama.passport.processor.impl.client.chimelong.model.OrderResult;
import com.lvmama.passport.processor.impl.client.chimelong.util.BuyTicket;
import com.lvmama.passport.processor.impl.client.chimelong.util.CancelOrder;
import com.lvmama.passport.processor.impl.client.chimelong.util.ChimelongUtils;
import com.lvmama.passport.processor.impl.client.chimelong.util.FaultException;
import com.lvmama.passport.processor.impl.client.chimelong.util.Order;
import com.lvmama.passport.processor.impl.util.OrderUtil;

/**
 * 长隆第三方接口实现
 * 
 * @author chenlinjun
 * 
 */
public class ChimelongProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor  {

	private static final Log log = LogFactory.getLog(ChimelongProcessorImpl.class);
	private static final String SUCCESS = "00";

	@Override
	public Passport apply(PassCode passCode) {
		log.info("Chimelong Apply Code Request :"+passCode.getSerialNo());
		Passport passport = new Passport();
		BuyTicket buy = new BuyTicket();
		PassPortCode passPort=passCode.getPassPortList().get(0);
		try {
			Order order  = buy.getOrderInfo(passCode.getOrderId(),String.valueOf(passPort.getTargetId()), passCode.getObjectId());
			if(order.isMaxi()){
				this.applyMaXiTicket(passport, order, passCode);
			}else{
				this.applyTicket(passport, order, passCode);
			}
		} catch(Exception ce) {
			log.error("Chimelong ApplyCode Exception, serialno: "+passCode.getSerialNo(), ce);
			String error=ce.getMessage();
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			passport.setComLogContent(error);
			this.reapplySet(passport, passCode.getReapplyCount(), error);
		}
		passport.setSendOrderid(true);
		// 回传我方交易流水号
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		log.info("serialno ："+passport.getSerialno()+" Code :"+passport.getCode());
		return passport;
	}
	/**
	 * 长隆普通产品申请
	 * @param passport
	 * @param order
	 * @param passCode
	 * @return
	 */
	public Passport applyTicket(Passport passport,Order order,PassCode passCode){
		BuyTicket buy = new BuyTicket();
		String md5key = ChimelongUtils.getMd5Key();
		TicketServiceClient client = new TicketServiceClient();
		OrderResult orderresult = new OrderResult();
		String[] responseInfoArray = null;
		Long startTime = 0L;
			try {
				order = buy.getOrder(passCode,order); // 提供给长隆的信
				String orderInfo = order.getOrderInfo();
				log.info("Chimelong not Maxi Apply Code :" + orderInfo);
				orderInfo = ChimelongUtils.enCode(md5key,orderInfo);
				String sign = ChimelongUtils.getSign(order.getVer_no() + order.getMer_no() + orderInfo + md5key);
				startTime = System.currentTimeMillis();
				orderresult = client.getTicketServiceHttpPort().buyTicket(order.getVer_no(), order.getMer_no(),
						orderInfo, sign);
				log.info("ChimLong Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
				String responseInfo = ChimelongUtils.deCode(md5key, orderresult.getOrderInfo().getValue());
				responseInfoArray = responseInfo.split("\\|");
				log.info("Chimelong Apply Code Result:" + responseInfo);
				if (null != responseInfoArray && responseInfoArray.length > 0 && SUCCESS.equals(responseInfoArray[13])) {
					// 长隆返回的取票凭证码
					passport.setCode(responseInfoArray[10]);
					// 申请成功
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
				}else{
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					passport.setComLogContent("ChimelongProcessorImpl_applyTicket_Received an abnormal status code");
					this.reapplySet(passport, passCode.getReapplyCount(), "11");
				}
			} catch(Exception ce) {
				log.error("Chimelong Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
				log.error("Chimelong ApplyCode Exception, serialno: "+passCode.getSerialNo(), ce);
				String error=ce.getMessage();
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent(error);
				this.reapplySet(passport, passCode.getReapplyCount(), error);
			}
		return passport;
	}
	/**
	 * 大马戏产品申请码
	 * @param passport
	 * @param order
	 * @param passCode
	 * @return
	 */
	public Passport applyMaXiTicket(Passport passport,Order order,PassCode passCode){
		long startTime = 0L;
		BuyTicket buy = new BuyTicket();
		TicketServiceClient client = new TicketServiceClient();
		OrderResult orderresult = new OrderResult();
		String md5key = ChimelongUtils.getMd5Key();
		String[] responseInfoArray = null;
		List<MaXiActTime> maXiTimes=null;
		try{
			ArrayOfMaXiActTime maXiActTime = client.getTicketServiceHttpPort().findMaXiActTime(order.getValidDate());
			maXiTimes = maXiActTime.getMaXiActTime();
		}catch(Exception ce) {
			log.error("Chimelong ApplyCode Exception, serialno: "+passCode.getSerialNo(), ce);
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			passport.setComLogContent(ce.getMessage());
			this.reapplySet(passport, passCode.getReapplyCount(), ce.getMessage());
			return passport;
		}
		for (MaXiActTime maXiTime : maXiTimes) {
			try {
				String start = DateFormatUtils.format(maXiTime.getActTimeBegin().toGregorianCalendar().getTime(), "yyyy-MM-dd HH:mm");
				String end = DateFormatUtils.format(maXiTime.getActTimeEnd().toGregorianCalendar().getTime(), "yyyy-MM-dd HH:mm");
				String id = maXiTime.getActId().getValue();
				String maxiTime = id + "," + start + "," + end;
				String dateTemp = "场次:" + id + ",场次开演时间:" + start + ",场次结束时间:" + end;
				order = buy.getOrder(passCode,order); // 提供给长隆的信
				String orderInfo = order.getOrderInfo();
				orderInfo=orderInfo.replaceAll("maxiTime", maxiTime);
				log.info("Chimelong Apply Code Request Param :" + orderInfo);
				orderInfo = ChimelongUtils.enCode(md5key,orderInfo);
				String sign = ChimelongUtils.getSign(order.getVer_no() + order.getMer_no() + orderInfo + md5key);
				startTime = System.currentTimeMillis();
				orderresult = client.getTicketServiceHttpPort().buyTicket(order.getVer_no(), order.getMer_no(), orderInfo, sign);
				log.info("ChimLong Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
				String responseInfo = ChimelongUtils.deCode(md5key, orderresult.getOrderInfo().getValue());
				responseInfoArray = responseInfo.split("\\|");
				log.info("Chimelong Apply Code Result:" + responseInfo);
				if (null != responseInfoArray && responseInfoArray.length > 0 && SUCCESS.equals(responseInfoArray[13])) {
					passport.setCode(responseInfoArray[10]+","+dateTemp);
					passport.setExtId(responseInfoArray[10]);
					// 申请成功
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					passport.setErrorNO(null);
					passport.setReapplyTime(null);
					passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
					break;
				}else{
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					passport.setComLogContent("ChimelongProcessorImpl_applyMaXiTicket_Received an abnormal status code");
					this.reapplySet(passport, passCode.getReapplyCount(), "11");
				}
			} catch(Exception ce) {
				log.info("ChimLong Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
				log.error("Chimelong ApplyCode Exception, serialno: "+passCode.getSerialNo(), ce);
				String error=ce.getMessage();
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent(error);
				this.reapplySet(passport, passCode.getReapplyCount(), error);
			}
		}
		return passport;
	}
	/**
	 * 重新申请码处理
	 * @param passport
	 * @param error
	 */
	public void reapplySet(Passport passport,long times,String error ){
		OrderUtil.init().reapplySet(passport, times);
	}
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("Chimelong DestoyCode Request :"+passCode.getSerialNo());
		Passport passport = new Passport();
		Order order = new Order();
		CancelOrder cancel = new CancelOrder();
		order = cancel.getOrder(passCode);
		TicketServiceClient client = new TicketServiceClient();
		long startTime = 0L;
		try {
			startTime = System.currentTimeMillis();
			doDestroy(client, order, passport);
			log.info("ChimLong Destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
		} catch (Exception e) {
			log.info("ChimLong Destroy　Error serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.error("Chimelong DestoyCode Exception1", e);
			retry(client, order, passport);
		}
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		return passport;
	}
	
	private void doDestroy(TicketServiceClient client, Order order, Passport passport) throws FaultException {
		CancelResult cancelResult = client.getTicketServiceHttpPort().cancelOrder(order.getVer_no(), order.getMer_no(), order.getOrderInfo(), order.getSign());// 向长隆方向提交请求
		if (cancelResult!=null && cancelResult.getResult() !=null && SUCCESS.equals(cancelResult.getResult().getValue())) {
			passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
		}//这里没有else是因为远程调用出错不会返回状态码，而是抛异常
	}
	
	private void retry(TicketServiceClient client, Order order, Passport passport) {
		log.info("chimelong destroycode retry");
		try {
			Thread.sleep(1000);
			doDestroy(client, order, passport);
		} catch (Exception e) {
			log.error("Chimelong DestoyCode Exception2", e);
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			passport.setComLogContent(e.getMessage());
		}
	}
}