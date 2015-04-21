package com.lvmama.pet.pay.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentDetail;
import com.lvmama.comm.pet.po.pay.PayPrePayment;
import com.lvmama.comm.pet.po.pay.PayTransaction;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pay.PayPaymentDetailService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.CashPaymentComboVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.PAYMENT_SERIAL_STATUS;
import com.lvmama.comm.vo.PayAndPrePaymentVO;
import com.lvmama.pet.pay.dao.PayPaymentDAO;
import com.lvmama.pet.pay.dao.PayPrePaymentDAO;
import com.lvmama.pet.pay.dao.PayTransactionDAO;
import com.lvmama.pet.pub.dao.ComLogDAO;

/**
 * 支付和数据库交互的处理.
 * @author liwenzhan
 * 
 */
public class PayPaymentServiceImpl implements PayPaymentService {
	
	private Logger LOG = Logger.getLogger(this.getClass());

	/**
	 * 支付记录DAO.
	 */
	private PayPaymentDAO payPaymentDAO;
	/**
	 * 支付记录(预授权)DAO.
	 */
	private PayPrePaymentDAO payPrePaymentDAO;
	/**
	 * 支付流水DAO.
	 */
	private PayTransactionDAO payTransactionDAO;
	/**
	 * 系统日志DAO
	 */
	private ComLogDAO comLogDAO;
	
	/**
	 * 支付信息-扩展
	 */
	private PayPaymentDetailService payPaymentDetailService;

	/**
	 * 支付成功的回调处理.
	 * 
	 * @param payment
	 * @param isKey
	 *            验证数据.
	 * @return  PayPayment
	 */
	@Override
	public List<PayPayment> callBackPayPayment(PayPayment payment,boolean success){
		List<PayPayment> paymentList = payPaymentDAO.selectByPaymentTradeNo(payment.getPaymentTradeNo());
		List<PayPayment> paymentDBList =new ArrayList<PayPayment>(); 
		for (PayPayment paymentDB: paymentList) {
			if(paymentDB!=null && !paymentDB.isSuccess() && !paymentDB.isTransferred()){
				if (success) {
					paymentDB.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
				}
				else{
					paymentDB.setStatus(Constant.PAYMENT_SERIAL_STATUS.FAIL.name());
				}
				paymentDB.setCallbackInfo(payment.getCallbackInfo());
				paymentDB.setGatewayTradeNo(payment.getGatewayTradeNo());
				paymentDB.setRefundSerial(payment.getRefundSerial());
				paymentDB.setCallbackTime(payment.getCallbackTime());
				if(paymentDB.isPrePayment()){
					//更新预授权支付数据
					updatePrePayment(paymentDB);
				}
				updatePayment(paymentDB);
				paymentDBList.add(paymentDB);
			}
		}
		return paymentDBList;
	}
	
	/**
	 * 更新预授权支付数据
	 * @author ZHANG Nan
	 * @param paymentDB
	 */
	public void updatePrePayment(PayPayment paymentDB){
		PayPrePayment prePayment = payPrePaymentDAO.selectPrePaymentByPaymentId(paymentDB.getPaymentId());
		if (prePayment != null) {
			prePayment.setStatus(Constant.PAYMENT_PRE_STATUS.PRE_PAY.name());
			prePayment.setStartTime(paymentDB.getCallbackTime());
			//业务订单的最晚取消时间.
			Date payEndTime = prePayment.getEndTime();
			//预授权最晚取消时间.
			Date preEndTime = DateUtil.dsDay_Date(paymentDB.getCallbackTime(),25);
			if((payEndTime!=null && preEndTime!=null) && DateUtil.isCompareTime(payEndTime, preEndTime)){
				prePayment.setEndTime(payEndTime);
			}else{
				prePayment.setEndTime(preEndTime);
			}
			payPrePaymentDAO.updatePrePayment(prePayment);
		}
	}

	
	/**
	 * 保存支付记录 .
	 * 
	 * @param payment
	 * @return
	 */
	@Override
	public Long savePayment(final PayPayment payment) {
		Long paymentId = this.payPaymentDAO.savePayment(payment);
		
		if (paymentId!=null && payment.isSuccess()) {
			savePayTransactionByPay(payment);
		}
		return paymentId;
	}
	/**
	 * 保存支付信息及支付扩展信息
	 * @author ZHANG Nan
	 * @param payment 支付信息
	 * @param payPaymentDetail 支付扩展信息
	 * @return 支付信息主键
	 */
	public Long savePaymentAndDetail(PayPayment payment,PayPaymentDetail payPaymentDetail) {
		Long paymentId =  savePayment(payment);
		if(paymentId!=null && payPaymentDetail!=null){
			payPaymentDetail.setPaymentId(paymentId);
			payPaymentDetailService.savePayPaymentDetail(payPaymentDetail);
		}
		return paymentId; 
	}
	/**
	 * 保存支付记录和预授权支付记录 .
	 * 
	 * @param payment
	 * @return
	 */
	@Override
	public boolean savePaymentAndPrePayment(final PayPayment payment,
			final PayPrePayment prePayment) {
		boolean isSucc = false;
		Long paymentId = this.payPaymentDAO.savePayment(payment);
		prePayment.setPaymentId(paymentId);
		Long prePaymentId = payPrePaymentDAO.savePrePayment(prePayment);
		if (paymentId > 0 && prePaymentId > 0) {
			isSucc = true;
		}
		return isSucc;
	}

	/**
	 * 更新支付记录.
	 * 
	 * @param payment
	 * @return
	 */
	@Override
	public boolean updatePayment(PayPayment payment) {
		LOG.error("payment infor: " + StringUtil.printParam(payment));
		PayPayment oldPayPayment=payPaymentDAO.selectByPaymentId(payment.getPaymentId());
		if(oldPayPayment.isNotified()){
			payment.setNotified(oldPayPayment.getNotified());	
		}
		payPaymentDAO.updatePayment(payment);
		if (payment.isSuccess()) {
			savePayTransactionByPay(payment);
		}
		return true;
	}
	/**
	 * 更新支付信息及扩展信息
	 * @author ZHANG Nan
	 * @param payment 支付信息
	 * @param payPaymentDetail 支付扩展信息
	 * @return 成功或失败
	 */
	public boolean updatePaymentAndDetail(PayPayment payment,PayPaymentDetail payPaymentDetail) {
		boolean result=updatePayment(payment);
		if(result){
			payPaymentDetailService.updatePayPaymentDetail(payPaymentDetail);
		}
		return result;
	}
	
	private Long savePayTransactionByPay(final PayPayment payment){
		Date now = new Date();
		PayTransaction payTransaction = new PayTransaction();
		payTransaction.setSerial(payment.getSerial());
		payTransaction.setAmount(payment.getAmount());
		payTransaction.setGatewayTradeNo(payment.getGatewayTradeNo());
		payTransaction.setObjectId(payment.getObjectId());
		payTransaction.setObjectType(payment.getObjectType());
		payTransaction.setPayee("LVMAMA");
		payTransaction.setPayer("");
		payTransaction.setPaymentGateway(payment.getPaymentGateway());
		payTransaction.setPaymentType("ONLINE");
		payTransaction.setTransactionType(Constant.TRANSCATION_TYPE.PAYMENT.name());
		payTransaction.setTransTime(now);
		payTransaction.setCreateTime(now);
		return payTransactionDAO.insert(payTransaction);
	}

	/**
	 * 更新支付记录和预授权支付记录.
	 * 
	 * @param payment
	 * @param prePayment
	 * @return
	 */
	@Override
	public boolean updatePayPrePayment(final PayPrePayment prePayment) {
		payPrePaymentDAO.updatePrePayment(prePayment);
		return true;
	}
	/**
	 * 资金转移 预授权支付信息
	 * @author ZHANG Nan
	 * @param oldPaymentId
	 * @param newPaymentId
	 */
	public void transferPrePayment(Long oldPaymentId,Long newPaymentId){
		if(oldPaymentId!=null && newPaymentId!=null){
			PayPrePayment payPrePayment=payPrePaymentDAO.selectPrePaymentByPaymentId(oldPaymentId);
			if(payPrePayment!=null){
				payPrePayment.setPaymentId(newPaymentId);
				payPrePaymentDAO.savePrePayment(payPrePayment);
			}
		}
	}

	/**
	 * 按PK取PAYMENT
	 * 
	 * @param paymentId
	 * @return
	 */
	public PayPayment selectByPaymentId(Long paymentId) {
		return payPaymentDAO.selectByPaymentId(paymentId);
	}

	/**
	 * 查询出来需要通知而未通知的PAYMENT
	 * 
	 * @return
	 */
	public List<PayPayment> selectUnNotifiedPayment() {
		return payPaymentDAO.selectUnNotifiedPayment();
	}
	/**
	 * 通过交易流水号+对象ID获取支付记录
	 * @author ZHANG Nan
	 * @param paymentTradeNo 交易流水号
	 * @param objectId 对象ID
	 * @return 支付记录
	 */
	public PayPayment selectByPaymentTradeNoAndObjectId(String paymentTradeNo,String objectId){
		return payPaymentDAO.selectByPaymentTradeNoAndObjectId(paymentTradeNo, objectId);
	}
	/**
	 * 通过支付流ID查询一个支付流水记录.
	 * 
	 * @param paymentTradeNo
	 * @return
	 */
	public List<PayPayment> selectByPaymentTradeNo(String paymentTradeNo) {
		return payPaymentDAO.selectByPaymentTradeNo(paymentTradeNo);
	}
	/**
	 * 通过对账流水号和支付网关查询多个支付记录.
	 * 
	 * @param paymentTradeNo
	 * @return
	 */
	public List<PayPayment> selectByPaymentTradeNoAndGatewayPure(String paymentTradeNo,String gatewayIN,Boolean oriObjectIdIsNull) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("paymentTradeNo", paymentTradeNo);
		paras.put("paymentGatewayIN", gatewayIN);
		if(oriObjectIdIsNull!=null){
			if(oriObjectIdIsNull){
				paras.put("oriObjectIdIsNull", "is null");	
			}
			else{
				paras.put("oriObjectIdIsNull", "is not null");
			}
		}
		
		return this.payPaymentDAO.selectPaymentListByMap(paras);
	}
	/**
	 * 通过支付流ID和支付网关查询一个支付流水记录.
	 * 
	 * @param paymentTradeNo
	 * @return
	 */
	public List<PayPayment> selectByPaymentTradeNoAndGateway(String paymentTradeNo,String gateway) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("paymentTradeNo", paymentTradeNo);
		paras.put("paymentGateway", gateway);
		paras.put("status", Constant.PAYMENT_SERIAL_STATUS.CREATE.name()); 
		return this.payPaymentDAO.selectPaymentListByMap(paras);
	}
	
	/**
	 * 通过对账流水号和多个支付网关查询多个支付记录
	 * @author ZHANG Nan
	 * @param paymentTradeNo
	 * @param gateways
	 * @return
	 */
	public List<PayPayment> selectByPaymentTradeNoAndGateways(String paymentTradeNo,String gateways) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("paymentTradeNo", paymentTradeNo);
		paras.put("paymentGatewayIN", gateways);
		paras.put("status", Constant.PAYMENT_SERIAL_STATUS.CREATE.name()); 
		return this.payPaymentDAO.selectPaymentListByMap(paras);
	}

	/**
	 * 根据支付网关+对账日期+状态 获取成功支付的记录用于预先插入到对账结果表中
	 * @author ZHANG Nan
	 */
	public List<PayAndPrePaymentVO> selectPaymentListByParasToRecon(String paymentGatewayIN,Date reconDate){
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("paymentGatewayIN", paymentGatewayIN);
		paramMap.put("createTimeShort", reconDate);
		paramMap.put("statusIN", "'"+Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name()+"','"+Constant.PAYMENT_SERIAL_STATUS.TRANSFERRED.name()+"'");
		return payPaymentDAO.selectPaymentListByParasToRecon(paramMap);
	}
	
	/**
	 * 通过支付Payment的ID查询预授权的支付流水记录.
	 * 
	 * @param serial
	 * @return
	 */
	public PayPrePayment selectPrePaymentByPaymentId(Long paymentId) {
		return payPrePaymentDAO.selectPrePaymentByPaymentId(paymentId);
	}

	/**
	 * 根据订单ID查询PayPayment的记录数.
	 * 
	 * @param orderId
	 * @return
	 */
	public Long selectPaymentCount(Long orderId) {
		return payPaymentDAO.selectPaymentCount(orderId);
	}

	/**
	 * 根据Serial查询PayPayment对象
	 * @param serial
	 * @return
	 */
	public PayPayment selectBySerial(String serial) {
		return payPaymentDAO.selectBySerial(serial);
	}
	/**
	 * 根据objectId统计支付成功的支付金额总和.
	 * @param objectId
	 * @return
	 */
	@Override
	public Long sumPayedPayPaymentAmountByObjectId(Long objectId) {
		return payPaymentDAO.sumPayedOrdPaymentAmountByObjectId(objectId);
	}

	@Override
	public List<PayPayment> selectByObjectIdAndBizType(Long objectId, String bizType) {
		return payPaymentDAO.selectByObjectIdAndBizType(objectId, bizType);
	}

	/**
	 * 新订单监控-支付信息 数据查询
	 */
	@Override
	public List<PayAndPrePaymentVO> selectPayAndPreByObjectIdAndBizType(Long objectId, String bizType){
		List<PayAndPrePaymentVO> payAndPrePaymentVOList=payPaymentDAO.selectPayAndPreByObjectIdAndBizType(objectId, bizType);
		for (PayAndPrePaymentVO payAndPrePaymentVO : payAndPrePaymentVOList) {
			//只对最原始的交易订单 显示合并支付的信息，废单重下而来的订单不显示
			if(payAndPrePaymentVO.getOriObjectId()==null){
				List<PayAndPrePaymentVO> otherMergePayList=getOtherMergePayListByPayment(payAndPrePaymentVO.getObjectId(),payAndPrePaymentVO.getPaymentTradeNo(),payAndPrePaymentVO.getGatewayTradeNo(),payAndPrePaymentVO.getPaymentGateway());
				payAndPrePaymentVO.setMergePayPaymentList(otherMergePayList);	
			}
		}
		return payAndPrePaymentVOList;
	}
	/**
	 * 获取与objectId订单一起合并支付的订单的支付信息
	 * @author ZHANG Nan
	 * @param objectId 订单号
	 * @param paymentTradeNo 对账流水号
	 * @param gatewayTradeNo 网关交易号
	 * @param paymentGateway 支付网关
	 * @return 与objectId订单一起合并支付的订单的支付信息
	 */
	public List<PayAndPrePaymentVO> getOtherMergePayListByPayment(Long objectId,String paymentTradeNo,String gatewayTradeNo,String paymentGateway){
		return payPaymentDAO.getOtherMergePayListByPayment(objectId, paymentTradeNo, gatewayTradeNo, paymentGateway);
	}
	
	/**
	 * 根据objectId查询成功的支付记录数.
	 * @param objectId
	 * @return
	 */
	@Override
	public Long selectPaymentSuccessCount(Long objectId) {
		return payPaymentDAO.selectPaymentSuccessCount(objectId);
	}

	 /**
	  * 查询是储值卡支付的金额.
	  * @param orderId
	  * @return
	  */
	public Long selectCardPaymentSuccessSumAmount(Long objectId) {
		return payPaymentDAO.selectCardPaymentSuccessSumAmount(objectId);
	}

	 /**
	  * 查询某支付网关当天所有支付记录数.
	  * @param params
	  * @return
	  */
	public Long selectPaymentCountByGateway(String gateway) {
		return payPaymentDAO.selectPaymentCountByGateway(gateway);
	}

	/**
	 * 根据订单号、支付网关、支付记录状态查询支付记录.
	 * 
	 * @param objectId
	 *            订单号.
	 * @param paymentGateway
	 *            支付网关.
	 * @param status
	 *            支付状态.
	 * @return List&lt;OrdPayment&gt;.
	 */
	public List<PayPayment> selectPayPaymentByObjectIdAndPaymentGateway(
			Long objectId, String paymentGateway, String status) {
		return payPaymentDAO.selectPayPaymentByObjectIdAndPaymentGateway(objectId,
				paymentGateway, status);
	}
	 /**
	  * 根据订单号、支付网关、支付记录状态(多个状态)查询支付记录.
	  * @param orderId 订单号.
	  * @param paymentGateway 支付网关.
	  * @param status 支付状态.
	  * @return List&lt;OrdPayment&gt;.
	  */
	public List<PayPayment> selectPayPaymentByObjectIdAndPaymentGatewayAndStatuss(Long objectId, String paymentGateway, String statuss) {
		 return payPaymentDAO.selectPayPaymentByObjectIdAndPaymentGatewayAndStatuss(objectId, paymentGateway, statuss);
	 }
	/**
     * 根据驴妈妈与银行或支付平台的交易流水号查询驴妈妈与客户的支付流水号.
     * @param paymentTradeNo 
     * @return 
     */
	public String selectSerialByPaymentTradeNo(String paymentTradeNo) {
		return payPaymentDAO.selectSerialByPaymentTradeNo(paymentTradeNo);
	}

	/**
	 * 根据订单号查询储值卡成功支付成功的所有记录.
	 * @param objectId
	 * @return
	 */
	public List<PayPayment> selectCardPayedPayPaymentListByObjectId(Long objectId) {
		return payPaymentDAO.selectCardPayedPayPaymentListByObjectId(objectId);
	}

	@Override
	public Long savePayTransaction(PayTransaction payTransaction) {
		return payTransactionDAO.insert(payTransaction);
	}

	/**
	 * 根据订单ID获取{@link FincTransaction}.
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的{@link FincTransaction}，
	 * 如果指定订单ID没有对应{@link FincTransaction}，则返回<code>null</code>
	 * </pre>
	 */
	@Override
	public List<PayTransaction> selectPayTransactionByObjectId(Long orderId){
		return payTransactionDAO.selectPayTransactionByObjectId(orderId);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<PayTransaction> selectByParams(Map params) {
		return payTransactionDAO.selectByParams(params);
	}


	/**
	 * 订单审核已通过的预授权的处理
	 * 
	 * @param objectId
	 * @return
	 */
	@Override
	public List<PayPayment> createPrePaymentCompleteData(final Long objectId,final String bizType) {
		List<PayPayment> list = new ArrayList<PayPayment>();
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("objectId", objectId);
		paras.put("bizType", bizType);
		paras.put("paymentType", Constant.PAYMENT_OPERATE_TYPE.PRE_PAY.name());
		paras.put("status", Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());// Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
		
		List<PayPayment> paymentList = this.payPaymentDAO.selectPaymentListByMap(paras);
		if (paymentList != null && paymentList.size() > 0) {
			for (PayPayment payment : paymentList) {
				PayPrePayment prePayment = this.payPrePaymentDAO.selectPrePaymentByPaymentId(payment.getPaymentId());
				payment.setPayPrePayment(prePayment);
				list.add(payment);
			}
		}
		return list;
	}
	
	/**
	 * 预授权扣款请求补偿查询
	 * 
	 * @param objectId
	 * @return
	 */
	@Override
	public List<PayPayment> selectNewPrePayData(final Long objectId,final String bizType) {
		List<PayPayment> list = new ArrayList<PayPayment>();
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("objectId", objectId);
		paras.put("bizType", bizType);
		paras.put("paymentType", Constant.PAYMENT_OPERATE_TYPE.PRE_PAY.name());
		paras.put("status", Constant.PAYMENT_SERIAL_STATUS.CREATE.name());// Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
		
		List<PayPayment> paymentList = this.payPaymentDAO.selectNewPrePayData(paras);
		if (paymentList != null && paymentList.size() > 0) {
			for (PayPayment payment : paymentList) {
				PayPrePayment prePayment = this.payPrePaymentDAO.selectPrePaymentByPaymentId(payment.getPaymentId());
				payment.setPayPrePayment(prePayment);
				list.add(payment);
			}
		}
		return list;
	}
	
	@Override
	public List<PayPayment> transferPayment(final Long orgObjectId, final Long newObjectId, final String bizType, final String objectType) {
		if (null != orgObjectId && null != newObjectId) {
			Map<String, Object> paras = new HashMap<String, Object>();
			paras.put("objectId", orgObjectId);
			paras.put("bizType", bizType);
			paras.put("objectType", objectType);
			paras.put("status", Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			List<PayPayment> payments = payPaymentDAO.selectPaymentListByMap(paras);
			List<PayPayment> newPayPaymentList=new ArrayList<PayPayment>();
			for (PayPayment payPayment : payments) {
				//创建新订单支付记录
				PayPayment newPayPayment=new PayPayment();
				//BeanUtils.copyProperties复制对于既有is又有get方法的字段反射有问题-暂时弃用
				//BeanUtils.copyProperties(payPayment, newPayPayment);
				newPayPayment.setPaymentId(payPayment.getPaymentId());
				newPayPayment.setSerial(SerialUtil.generate24ByteSerialAttaObjectId(newObjectId));
				newPayPayment.setBizType(payPayment.getBizType());
				newPayPayment.setNotified("true"); 
				newPayPayment.setNotifyTime(new Date());
				newPayPayment.setObjectId(newObjectId);
				newPayPayment.setObjectType(payPayment.getObjectType());
				newPayPayment.setPaymentType(payPayment.getPaymentType());
				newPayPayment.setPaymentGateway(payPayment.getPaymentGateway());
				newPayPayment.setAmount(payPayment.getAmount());
				newPayPayment.setStatus(payPayment.getStatus());
				newPayPayment.setCreateTime(new Date());
				newPayPayment.setCallbackInfo(payPayment.getCallbackInfo());
				newPayPayment.setCallbackTime(payPayment.getCallbackTime());
				newPayPayment.setGatewayTradeNo(payPayment.getGatewayTradeNo());
				newPayPayment.setGateId(payPayment.getGateId());
				newPayPayment.setPaymentTradeNo(payPayment.getPaymentTradeNo());
				newPayPayment.setRefundSerial(payPayment.getRefundSerial());
				newPayPayment.setNotified("true");
				newPayPayment.setOperator(payPayment.getOperator());
				newPayPayment.setNotifyTime(new Date());
				newPayPayment.setOriObjectId(payPayment.getObjectId());
				newPayPayment.setPayMobileNum(payPayment.getPayMobileNum());
				
				Long newPaymentId=savePayment(newPayPayment);
				newPayPayment.setPaymentId(newPaymentId);
				newPayPaymentList.add(newPayPayment);
				
				//修改老订单支付记录
				payPayment.setStatus(PAYMENT_SERIAL_STATUS.TRANSFERRED.name());
				payPayment.setCallbackInfo("资金已转移到新订单:"+newObjectId+",转移时间:"+DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				updatePayment(payPayment);
				
				//资金转移支付扩展信息表
				payPaymentDetailService.transferPaymentDetail(payPayment.getPaymentId(), newPaymentId);
				
				//资金转移 预授权支付信息
				transferPrePayment(payPayment.getPaymentId(), newPaymentId);
			}
			
			ComLog log = new ComLog();
			log.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
			log.setObjectId(orgObjectId);
			log.setOperatorName("SYSTEM");
			log.setLogType("TRANSFER_PAYMENT");
			log.setLogName("支付记录转出");
			log.setContent("支付记录从订单号:" + orgObjectId + " 转移到订单号:" + newObjectId);
			comLogDAO.insert(log);
			
			log = new ComLog();
			log.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
			log.setObjectId(newObjectId);
			log.setOperatorName("SYSTEM");
			log.setLogType("TRANSFER_PAYMENT");
			log.setLogName("支付记录转入");
			log.setContent("支付记录从订单号:" + orgObjectId + " 转移到订单号:" + newObjectId);
			comLogDAO.insert(log);
			
			return newPayPaymentList;
		} else {
			return new ArrayList<PayPayment>(0);
		}
	}
	/**
	 * 通过PaymentTradeNo判断支付记录是否已存在
	 * @author ZHANG Nan
	 * @param PaymentTradeNo
	 * @return
	 */
	public boolean isExistsByPaymentTradeNo(String PaymentTradeNo){
		return payPaymentDAO.isExistsByPaymentTradeNo(PaymentTradeNo);
	}
	/**
	 * 现金收款监控
	 * @author ZHANG Nan
	 * @param paramMap 参数
	 * @return 现金收款监控集合
	 */
	public List<CashPaymentComboVO> selectPayPaymentAndDetailByParamMap(Map<String, String> paramMap){
		return payPaymentDAO.selectPayPaymentAndDetailByParamMap(paramMap);
	}
	/**
	 * 现金收款监控-总数
	 * @author ZHANG Nan
	 * @param paramMap 参数
	 * @return 现金收款监控集合-总数
	 */
	public Long selectPayPaymentAndDetailByParamMapCount(Map<String, String> paramMap){
		return payPaymentDAO.selectPayPaymentAndDetailByParamMapCount(paramMap);
	}
	public Map<String,Long> selectPayPaymentAuditAmountByParamMap(Map<String, String> paramMap){
		Map<String,BigDecimal> resultMap=payPaymentDAO.selectPayPaymentAuditAmountByParamMap(paramMap);
		Map<String,Long> map=new HashMap<String,Long>();
		if(resultMap!=null){
			map.put("PAYMENTAMOUNTSUM", Long.valueOf(resultMap.get("PAYMENTAMOUNTSUM")==null?"0":resultMap.get("PAYMENTAMOUNTSUM")+""));
			map.put("AUDITPASSAMOUNTSUM", Long.valueOf(resultMap.get("AUDITPASSAMOUNTSUM")==null?"0":resultMap.get("AUDITPASSAMOUNTSUM")+""));
			map.put("LIBERATEAMOUNTSUM", Long.valueOf(resultMap.get("LIBERATEAMOUNTSUM")==null?"0":resultMap.get("LIBERATEAMOUNTSUM")+""));
		}
		return map;
	}
	
	/**
	 * 其它收款监控
	 * @author ZHANG Nan
	 * @param paramMap 参数
	 * @return 现金收款监控集合
	 */
	public List<CashPaymentComboVO> selectOtherPayPaymentAndDetailByParamMap(Map<String, String> paramMap){
		return payPaymentDAO.selectOtherPayPaymentAndDetailByParamMap(paramMap);
	}
	/**
	 * 其它收款监控-总数
	 * @author ZHANG Nan
	 * @param paramMap 参数
	 * @return 现金收款监控集合-总数
	 */
	public Long selectOtherPayPaymentAndDetailByParamMapCount(Map<String, String> paramMap){
		return payPaymentDAO.selectOtherPayPaymentAndDetailByParamMapCount(paramMap);
	}
	
	

	public void setPayPaymentDAO(PayPaymentDAO payPaymentDAO) {
		this.payPaymentDAO = payPaymentDAO;
	}

	public void setPayPrePaymentDAO(PayPrePaymentDAO payPrePaymentDAO) {
		this.payPrePaymentDAO = payPrePaymentDAO;
	}

	public void setPayTransactionDAO(PayTransactionDAO payTransactionDAO) {
		this.payTransactionDAO = payTransactionDAO;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	public void setPayPaymentDetailService(PayPaymentDetailService payPaymentDetailService) {
		this.payPaymentDetailService = payPaymentDetailService;
	}
    
	/**
     * 根据电话支付订单号和网关支付类型查询电话支付来电电话号码
     * 电话支付号码去掉前面的0
     * @author CAOKUN
     * @param orderId 
     * @return payMobileNum
    */
	public String selectPayMobileNumByPaymentOrderNoAndPaymentGateway(Long orderId, String paymentGateway) {
		String payMobileNum = payPaymentDAO.selectPayMobileNumByPaymentOrderNoAndPaymentGateway(orderId, paymentGateway);
		if (null != payMobileNum && payMobileNum.length()> 11) {
			payMobileNum = payMobileNum.substring(payMobileNum.length()-11, payMobileNum.length());
		}
		return payMobileNum;
	}
	
	
}
