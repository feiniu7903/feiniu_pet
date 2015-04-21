package com.lvmama.pet.pay.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentAndRefundment;
import com.lvmama.comm.pet.po.pay.PayPaymentGateway;
import com.lvmama.comm.pet.po.pay.PayPaymentRefundment;
import com.lvmama.comm.pet.po.pay.PayPrePayment;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.PayRefundDetail;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.pay.dao.PayPaymentDAO;
import com.lvmama.pet.pay.dao.PayPaymentGatewayDAO;
import com.lvmama.pet.pay.dao.PayPaymentRefundmentDAO;
import com.lvmama.pet.pay.dao.PayPrePaymentDAO;

public class PayPaymentRefundmentServiceImpl implements PayPaymentRefundmentService {
	
	private PayPaymentDAO payPaymentDAO;
	private PayPaymentRefundmentDAO payPaymentRefundmentDAO;
	private PayPrePaymentDAO payPrePaymentDAO;
	private PayPaymentGatewayDAO payPaymentGatewayDAO;
	private ComLogService comLogService;
	private Logger log = Logger.getLogger(this.getClass());
	@Override
	/**
	 * 订单组装生成退款明细
	 */
	public boolean createRefundment(Long orderId, RefundmentToBankInfo refundInfo) {
		log.info("start PayPaymentRefundmentServiceImpl.createRefundment("+orderId+", "+refundInfo.getObjectId()+") refundInfo="+StringUtil.printParam(refundInfo));
		if(StringUtils.isNotBlank(refundInfo.getRefundType()) && Constant.REFUND_TYPE.COMPENSATION.name().equalsIgnoreCase(refundInfo.getRefundType())){
			compensationProcess(orderId, refundInfo);
		}
		else if(StringUtils.isNotBlank(refundInfo.getRefundType()) && Constant.REFUND_TYPE.ORDER_REFUNDED.name().equalsIgnoreCase(refundInfo.getRefundType())){
			refundedPorcess(orderId, refundInfo);	
		}else if(StringUtils.isNotBlank(refundInfo.getRefundType()) && Constant.REFUND_TYPE.CASH_ACCOUNT_WITHDRAW.name().equalsIgnoreCase(refundInfo.getRefundType())){
			this.cashAccountWithDrawProcess(orderId, refundInfo);
		}
		log.info("end PayPaymentRefundmentServiceImpl.createRefundment("+orderId+", "+refundInfo.getObjectId()+") refundInfo="+StringUtil.printParam(refundInfo));
		
		return true;
	}
	/**
	 *提现
	 * @param orderId
	 * @param refundInfo
	 */
	private void cashAccountWithDrawProcess(Long orderId, RefundmentToBankInfo refundInfo){
		if(refundInfo.getRefundAmount()>0){
			PayPaymentRefundment payRefundment = new PayPaymentRefundment();
			payRefundment.setAmount(refundInfo.getRefundAmount());
			payRefundment.setObjectId(refundInfo.getObjectId());
			payRefundment.setObjectType(refundInfo.getObjectType());
			payRefundment.setCreateTime(new Date());
			payRefundment.setStatus(Constant.PAYMENT_SERIAL_STATUS.CREATE.name());
			
			PayPaymentGateway payPaymentGateway=payPaymentGatewayDAO.selectPaymentGatewayByGateway(refundInfo.getPaymentGateway());
			if(payPaymentGateway!=null){
				payRefundment.setIsAllowRefund(payPaymentGateway.getIsAllowRefund());
				payRefundment.setRefundGateway(payPaymentGateway.getRefundGateway());
			}
			payRefundment.setUserId(refundInfo.getUserId());
			payRefundment.setBizType(refundInfo.getBizType());
			payRefundment.setOperator(refundInfo.getOperator());
			payRefundment.setOrderId(orderId);
			payRefundment.setRefundType(refundInfo.getRefundType());
			Long paymentRefundmentId=payPaymentRefundmentDAO.savePayPaymentRefundment(payRefundment);
			//记录日志
			payRefundment.setPayRefundmentId(paymentRefundmentId);
			createRefundComLog(payRefundment, refundInfo);
		}
	}
	
	/**
	 * 补偿单退款-直接退款至现金账户
	 * @author ZHANG Nan
	 * @param orderId 订单号
	 * @param refundInfo 退款对象
	 */
	private void compensationProcess(Long orderId, RefundmentToBankInfo refundInfo){
		PayPaymentRefundment payRefundment = new PayPaymentRefundment();
		payRefundment.setAmount(refundInfo.getRefundAmount());
		payRefundment.setObjectId(refundInfo.getObjectId());
		payRefundment.setObjectType(refundInfo.getObjectType());
		payRefundment.setCreateTime(new Date());
		payRefundment.setStatus(Constant.PAYMENT_SERIAL_STATUS.CREATE.name());
		payRefundment.setIsAllowRefund(Boolean.TRUE.toString().toUpperCase());
		payRefundment.setRefundGateway(Constant.PAYMENT_GATEWAY.CASH_ACCOUNT.name());
		payRefundment.setUserId(refundInfo.getUserId());
		payRefundment.setBizType(refundInfo.getBizType());
		payRefundment.setOperator(refundInfo.getOperator());
		payRefundment.setOrderId(orderId);
		payRefundment.setRefundType(refundInfo.getRefundType());
		Long paymentRefundmentId=payPaymentRefundmentDAO.savePayPaymentRefundment(payRefundment);
		//记录日志
		payRefundment.setPayRefundmentId(paymentRefundmentId);
		createRefundComLog(payRefundment, refundInfo);
	}
	/**
	 * 退款单退款-根据退款网关退款顺序及优先原路退回原则进行退款
	 * @author ZHANG Nan
	 * @param orderId 订单号
	 * @param refundInfo 退款对象
	 */
	private void refundedPorcess(Long orderId, RefundmentToBankInfo refundInfo){
		Long refundmentAmount = refundInfo.getRefundAmount();
		Date date = new Date();
		
		List<PayPayment> paymentList=new ArrayList<PayPayment>();
		log.info("orderId:"+orderId +"refundInfo:"+StringUtil.printParam(refundInfo));
		if(orderId!=null && refundInfo.getBizType()!=null){
			paymentList = payPaymentDAO.selectPaymentListByParas(orderId, refundInfo.getBizType());	
		}
		if(paymentList!=null && paymentList.size()>0){
			Map<String,String> paramMap=new HashMap<String,String>();
			paramMap.put("orderby", "REFUND_ORDER");
			List<PayPaymentGateway> payPaymentGatewayList=payPaymentGatewayDAO.selectPayPaymentGatewayByParamMap(paramMap);
			for (PayPaymentGateway payPaymentGateway: payPaymentGatewayList) {
				//循环可原路退回的支付记录 默认以支付金额正序(支付金额越小越先退)
				for (PayPayment pay : paymentList) {
					if (refundmentAmount > 0) {
						if (pay.getPaymentGateway().equalsIgnoreCase(payPaymentGateway.getGateway())) {
							PayPaymentRefundment payRefundment = new PayPaymentRefundment();
							//获取当前这笔支付的已退款总金额
							Long oldRefunmentTotalAmount=getRefunmentAmountByPaymentId(String.valueOf(pay.getPaymentId()));
							//当前这笔支付可退款金额
							Long canRefunmentAmount=pay.getAmount()-oldRefunmentTotalAmount;
							if(canRefunmentAmount>0){
								if (refundmentAmount > canRefunmentAmount) {
									payRefundment.setAmount(canRefunmentAmount);
									refundmentAmount = refundmentAmount - canRefunmentAmount;
								} else {
									payRefundment.setAmount(refundmentAmount);
									refundmentAmount = 0L;
								}
								payRefundment.setObjectId(refundInfo.getObjectId());
								payRefundment.setObjectType(refundInfo.getObjectType());
								payRefundment.setCreateTime(date);
								payRefundment.setPaymentId(pay.getPaymentId());
								payRefundment.setStatus(Constant.PAYMENT_SERIAL_STATUS.CREATE.name());						
								payRefundment.setIsAllowRefund(payPaymentGateway.getIsAllowRefund());
								//如果强制指定退款网关则使用强制指定的，否则默认原路退回
								if(StringUtils.isNotBlank(refundInfo.getRefundGateway())){
									payRefundment.setRefundGateway(refundInfo.getRefundGateway());
								}
								else{
									payRefundment.setRefundGateway(payPaymentGateway.getRefundGateway());	
								}
								payRefundment.setUserId(refundInfo.getUserId());
								payRefundment.setBizType(refundInfo.getBizType());
								payRefundment.setOperator(refundInfo.getOperator());
								payRefundment.setOrderId(orderId);
								payRefundment.setRefundType(refundInfo.getRefundType());
								Long paymentRefundmentId=payPaymentRefundmentDAO.savePayPaymentRefundment(payRefundment);
								//记录日志
								payRefundment.setPayRefundmentId(paymentRefundmentId);
								createRefundComLog(payRefundment, refundInfo);
							}
						}
					}
				}
			}
		}
		if(refundmentAmount>0){
			log.error("Still the amount can not refundment, orderId="+orderId);
		}
		
	}
	private void createRefundComLog(PayPaymentRefundment payRefundment,RefundmentToBankInfo refundInfo){
		ComLog log = new ComLog();
		log.setObjectId(payRefundment.getPayRefundmentId());
		log.setObjectType("PAY_PAYMENT_REFUNDMENT");
		log.setParentId(payRefundment.getObjectId());
		log.setParentType("ORD_REFUNDMENT");
		log.setLogType("PAY_PAYMENT_REFUNDMENT_CREATE");
		log.setLogName("");
		log.setOperatorName(refundInfo.getOperator());
		String content="创建退款明细成功";
		if(StringUtils.isNotBlank(refundInfo.getRefundGateway())){
			content+=",强制指定退款网关:"+refundInfo.getRefundGateway();
		}
		log.setContent(content);
		log.setCreateTime(new Date());
		comLogService.addComLog(log);
	}
	
   /**
    * 更新支付退款明细.
    * @param refundment
    * @return
    */
   @Override
   public boolean  updatePyamentRefundment(PayPaymentRefundment refundment){
	   payPaymentRefundmentDAO.updatePayPaymentRefundment(refundment);
	   return true;
   }
   /**
	 * 更新退款网关.
	 * @param payment
	 * @return
	 */
	public void updatePayPaymentRefundmentRefundGateway(PayPaymentRefundment payRefundment){
		payPaymentRefundmentDAO.updatePayPaymentRefundmentRefundGateway(payRefundment);
	}
   
   
   /**
    * 预授权退款成功后保存退款明细表和预授权表.
    * @param refundment
    * @param prePayment
    * @return
    */
   @Override
   public boolean updatePyamentRefundmentAndPayPayPayment(PayPaymentRefundment refundment,PayPrePayment prePayment){
	   payPaymentRefundmentDAO.updatePayPaymentRefundment(refundment);
	   if (prePayment!=null) {
		   payPrePaymentDAO.updatePrePayment(prePayment);
	   }
	   return true;
   }
   
   
	
   /**
	 * 根据订单ID和退款状态查询退款明细记录.
	 * @param objectId
	 * @param stauts
	 * @return
	 */
	@Override
	public List<PayPaymentRefundment> selectPaymentRefundmentListByObject(final Long objectId,final String stauts){
		return payPaymentRefundmentDAO.selectPayRefundmentListByObjectIdAndStatus(objectId,stauts);
	}
	
	/**
	 *  根据订单ID查询处有效和需要自动退款的退款明细记录.
	 * @param objectId
	 * @return
	 */
	@Override
	public List<PayPaymentRefundment> selectRefundListByObjectId(Long objectId,String objectType){
		return payPaymentRefundmentDAO.selectValidPayRefundmentListByObjectIdAndObjectType(objectId,objectType);
	}
	
	/**
	 *  根据订单ID查询和业务类型查询已经成功退款的记录.
	 * @param objectId
	 * @return
	 */
	@Override
	public List<PayPaymentRefundment> selectRefundListByOrderIdAndBizType(Long orderId, String bizType){
		return payPaymentRefundmentDAO.selectRefundListByOrderIdAndBizType(orderId,bizType);
	}
	
	/**
	 *  根据订单ID查询处有效和需要自动退款的退款明细记录.
	 * @param objectId
	 * @return
	 */
	@Override
									  
	public List<PayPaymentRefundment> selectRefundListByObjectIdAndBizType(Long objectId,String bizType,String status){
		return payPaymentRefundmentDAO.selectValidPayRefundmentListByObjectIdAndBizType(objectId,bizType,status);
	}

	/**
	 * 根据订单objectId,objectType,bizType查询退款明细记录.
	 * @param objectId
	 * @param objectType
	 * @param bizType
	 * @return
	 */
	public List<PayPaymentRefundment> selectPayRefundmentListByObjectIdAndObjectTypeAndBizType(Long objectId,String objectType, String bizType){
		return payPaymentRefundmentDAO.selectPayRefundmentListByObjectIdAndObjectTypeAndBizType(objectId, objectType, bizType);
	}
	
	/**
	 *  查询出有效和需要自动退款的退款明细记录.
	 * @return
	 */
	@Override
	public List<PayPaymentRefundment> selectValidPayRefundmentListBy(){
		return payPaymentRefundmentDAO.selectValidPayRefundmentListBy();
	}
	
	/**
	 *  查询出有效和需要自动退款的退款明细记录.
	 * @return
	 */
	@Override
	public PayPaymentRefundment selectValidPayRefundmentBy() {
		List<PayPaymentRefundment> refundList = selectValidPayRefundmentListBy();
		PayPaymentRefundment payPaymentRefundment = new PayPaymentRefundment();
		if (refundList != null && refundList.size() > 0) {
			payPaymentRefundment = refundList.get(0);
		}
		return payPaymentRefundment;
	}
	
	/**
	 * 根据退款的流水查询出来 需要退款的PayPaymentRefundment
	 * @param objectId
	 * @param refumentAmount
	 * @return
	 */
	@Override
	public PayPaymentRefundment selectPaymentRefundmentBySerial(final String serial) {
		return payPaymentRefundmentDAO.selectPayPaymentRefundmentListBy(serial);
	}
	
	@Override
	public PayPaymentRefundment selectPaymentRefundmentBySerial(final String serial,final String status) {
		return payPaymentRefundmentDAO.selectPayPaymentRefundmentListBySerial(serial, status);
	}
	
	@Override
	public PayPaymentRefundment selectPaymentRefundmentByPK(Long payRefundmentId) {
		return payPaymentRefundmentDAO.selectPaymentRefundmentByPK(payRefundmentId);
	}
	
	@Override
	public PayPaymentRefundment callBackPayPaymentRefundment(PayPaymentRefundment payPaymentRefundment, boolean isSuccess) {
		PayPaymentRefundment payPaymentRefundmentDB = selectPaymentRefundmentBySerial(payPaymentRefundment.getSerial());
		if (!payPaymentRefundmentDB.isSuccess()) {
			if (isSuccess) {
				payPaymentRefundmentDB.setStatus(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());
			} else {
				payPaymentRefundmentDB.setStatus(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
			}
			payPaymentRefundmentDB.setCallbackInfo(payPaymentRefundment.getCallbackInfo());
			payPaymentRefundmentDB.setCallbackTime(new Date());
			
			
			PayPrePayment payPrePayment=payPrePaymentDAO.selectPrePaymentByPaymentId(payPaymentRefundmentDB.getPaymentId());
			//如果退款成功 处理预授权状态
			if(payPrePayment!=null && Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name().equalsIgnoreCase(payPaymentRefundmentDB.getStatus())){
				if (Constant.PAYMENT_PRE_STATUS.PRE_PAY.name().equalsIgnoreCase(payPrePayment.getStatus())) {
					payPrePayment.setStatus(Constant.PAYMENT_PRE_STATUS.PRE_CANCEL.name());
					payPrePayment.setCancelTime(new Date());
				}
				else if (Constant.PAYMENT_PRE_STATUS.PRE_SUCC.name().equalsIgnoreCase(payPrePayment.getStatus())) {
					payPrePayment.setStatus(Constant.PAYMENT_PRE_STATUS.PRE_REFUND.name());
				}
			}
			updatePyamentRefundmentAndPayPayPayment(payPaymentRefundmentDB, payPrePayment);
		}
		return payPaymentRefundmentDB;
	}
	
	
	/**
	 * 根据订单ID查询退款及预授权明细记录.
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<PayRefundDetail> selectPayRefundDetailList(Map param,int skipResults,int maxResults) {
		return payPaymentRefundmentDAO.selectPayRefundDetailList(param, skipResults, maxResults);
	}

	@SuppressWarnings("rawtypes")
	@Override
	/**
	 * 查询退款及预授权明细记录数量.
	 * @return
	 */
	public Long selectPayRefundDetailCount(Map param) {
		return payPaymentRefundmentDAO.selectPayRefundDetailCount(param);
	}

	/**
	 *  根据订单ID查询退款及预授权明细记录数量.
	 * @return
	 */
	@Override
	public PayRefundDetail selectPayRefundDetailByPaymentRefundmentId(Long paymentRefundmentId) {
		return payPaymentRefundmentDAO.selectPayRefundDetailByPaymentRefundmentId(paymentRefundmentId);
	}
	
	/**
	 * 通过支付Id获取当前这笔支付退款金额
	 * @author ZHANG Nan
	 * @param paymentId
	 * @return
	 */
	public Long getRefunmentAmountByPaymentId(String paymentId){
		Long oldRefunmentTotalAmount=payPaymentRefundmentDAO.getRefunmentAmountByPaymentId(paymentId);
		if(oldRefunmentTotalAmount!=null){
			return oldRefunmentTotalAmount;
		}
		else{
			return 0L;
		}
	}
	/**
	 * 查询出有效的支付的明细退款单纪录.
	 * @param map
	 * @return
	 */
	public List<PayPaymentRefundment> selectUnRefundedPaymentByGateWay(String paymentGateway){
		return payPaymentRefundmentDAO.selectUnRefundedPaymentByGateWay(paymentGateway);
	}
	
	public List<PayPaymentAndRefundment> selectPaymentAndRefundByParams(Map<String, Object> map){
		return payPaymentRefundmentDAO.selectPaymentAndRefundByParams(map);
	}
	
	
	/**
	 * 根据退款网关+对账日期+状态 获取成功退款的记录用于预先插入到对账结果表中
	 * @author ZHANG Nan
	 */
	public List<PayPaymentAndRefundment> selectPaymentAndRefundByPreReconRefundData(String refundGatewayIN,Date reconDate){
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("refundGatewayIN", refundGatewayIN);
		paramMap.put("createTimeShort", reconDate);
		paramMap.put("status", Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
		return payPaymentRefundmentDAO.selectPaymentAndRefundByParams(paramMap);
	}
	/**
	 * 根据退款发起的流水号+退款网关 获取退款记录
	 * @author ZHANG Nan
	 * @param serial  退款发起的流水号
	 * @param refundGateway 退款网关
	 * @return 退款对象
	 */
	public PayPaymentAndRefundment selectPaymentAndRefundBySerialAndGateway(String serial,String refundGatewayIN){
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("serial", serial);
		map.put("refundGatewayIN", refundGatewayIN);
		List<PayPaymentAndRefundment> payPaymentAndRefundmentList=payPaymentRefundmentDAO.selectPaymentAndRefundByParams(map);
		if(payPaymentAndRefundmentList!=null && payPaymentAndRefundmentList.size()>0){
			return payPaymentAndRefundmentList.get(0);
		}
		return new PayPaymentAndRefundment();
	}
	
	public void setPayPaymentDAO(PayPaymentDAO payPaymentDAO) {
		this.payPaymentDAO = payPaymentDAO;
	}

	public void setPayPaymentRefundmentDAO(
			PayPaymentRefundmentDAO payPaymentRefundmentDAO) {
		this.payPaymentRefundmentDAO = payPaymentRefundmentDAO;
	}

	public void setPayPrePaymentDAO(PayPrePaymentDAO payPrePaymentDAO) {
		this.payPrePaymentDAO = payPrePaymentDAO;
	}

	public void setPayPaymentGatewayDAO(PayPaymentGatewayDAO payPaymentGatewayDAO) {
		this.payPaymentGatewayDAO = payPaymentGatewayDAO;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
}
