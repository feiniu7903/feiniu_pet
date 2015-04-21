/**
 * 
 */
package com.lvmama.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderForPaymentSms;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderForPaymentSmsDAO;
import com.lvmama.order.logic.SmsSendLogic;
import com.lvmama.order.service.OrderForPaymentService;
import com.lvmama.order.service.OrderUpdateService;
import com.lvmama.order.service.Query;

/**
 * 
 * @author yangbin
 *
 */
public class OrderForPaymentServiceImpl extends OrderServiceImpl implements OrderForPaymentService {
	
	private SmsSendLogic smsSendLogic;
	private Query queryService;
	private OrderForPaymentSmsDAO orderForPaymentSmsDAO;
	private OrderUpdateService orderUpdateService;
	private final static Log logger = LogFactory.getLog(OrderForPaymentServiceImpl.class);

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.OrderSmsSendService#sendForPayment(com.lvmama.comm.bee.po.ord.OrdOrder)
	 */
	@Override
	public void sendForPayment(OrdOrder order) {
		OrdOrderForPaymentSms record = buildForPaymentSms(order);
		orderForPaymentSmsDAO.insert(record);
		smsSendLogic.sendForPayment(record.getOrderId(),record.getMobile(),record.getSmsCode());
	}
	
	private Date prolongTime(OrdOrder order){
		Date lastDate=order.getLastCancelTime();
		Date fourDate=DateUtils.addHours(new Date(), 4);//默认是4小时
		if(lastDate.before(fourDate)){
			return lastDate;
		}else{
			return fourDate;
		}
			
	}
	
	@Override
	public void receiverForPayment(String mobile, String code) {
		boolean hasInfoLevel=logger.isInfoEnabled();
		if(hasInfoLevel){
			logger.info("reciver for payment mobile:"+mobile+" code:"+code);
		}
		OrdOrderForPaymentSms record = orderForPaymentSmsDAO.selectByMobileAndCode(mobile, code);
		if(record!=null){
			if(record.isNew()){
				OrdOrder order = queryService.queryOrdOrder(record.getOrderId());
				if(order.getLastCancelTime()!=null&&order.isNormal()&&order.isApprovePass()){
					
					Date date = DateUtils.addMinutes(order.getApproveTime(), order.getWaitPayment().intValue());
					Date canDateAble=prolongTime(order);
					if(date.before(canDateAble)&&canDateAble.after(new Date())){
						long waitPayment = DateUtil.getMinBetween(order.getApproveTime(),canDateAble);
						
						orderUpdateService.updateWaitPayment(waitPayment, order.getOrderId(), order.getUserId());
						if(hasInfoLevel){
							logger.info("change waitPayment orderId:"+order.getOrderId());
						}
					}
				}
				if(hasInfoLevel){
					logger.info("update for payment status");
				}
				record.setStatus(Constant.FOR_PAYMENT_STATUS.REPLY.name());
				orderForPaymentSmsDAO.updateByPrimaryKey(record);
			}else{
				if(hasInfoLevel){
					logger.info("for payment id:"+record.getForPaymentSmsId()+" repeat handle");
				}
			}
		}
	}

	public void setSmsSendLogic(SmsSendLogic smsSendLogic) {
		this.smsSendLogic = smsSendLogic;
	}

	
	private OrdOrderForPaymentSms buildForPaymentSms(final OrdOrder order){
		OrdOrderForPaymentSms oofp = new OrdOrderForPaymentSms();
		oofp.setEndTime(order.getLastCancelTime());
		oofp.setStatus(Constant.FOR_PAYMENT_STATUS.CREATE.name());
		oofp.setMobile(smsSendLogic.getBookerMobil(order.getOrderId()));
		oofp.setSmsCode(StringUtil.getRandomLetterString(3));		
		oofp.setOrderId(order.getOrderId());		
		return oofp;
	}
	
	

	public void setOrderForPaymentSmsDAO(OrderForPaymentSmsDAO orderForPaymentSmsDAO) {
		this.orderForPaymentSmsDAO = orderForPaymentSmsDAO;
	}

	public void setOrderUpdateService(OrderUpdateService orderUpdateService) {
		this.orderUpdateService = orderUpdateService;
	}

	public void setQueryService(Query queryService) {
		this.queryService = queryService;
	}

	
}
