package com.lvmama.pet.pay.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.lvmama.comm.pet.po.fin.FinBizItem;
import com.lvmama.comm.pet.po.pay.FinReconResult;
import com.lvmama.comm.pet.po.pay.PayPaymentAndRefundment;
import com.lvmama.comm.pet.po.pay.PayPaymentDetail;
import com.lvmama.comm.pet.service.fin.FinBizItemService;
import com.lvmama.comm.pet.service.pay.FinReconResultService;
import com.lvmama.comm.pet.service.pay.PayPaymentDetailService;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.FinConvertUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PayAndPrePaymentVO;
import com.lvmama.pet.pay.dao.FinReconResultDAO;

@Service
public class FinReconResultServiceImpl implements FinReconResultService {
	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private FinReconResultDAO finReconResultDAO;
	
	private PayPaymentService payPaymentService;
	
	private PayPaymentDetailService payPaymentDetailService;
	
	private PayPaymentRefundmentService payPaymentRefundmentService;
	
	private FinBizItemService finBizItemService;

	@Override
	public Long insert(FinReconResult finReconResult) {

		Long count = finReconResultDAO.insert(finReconResult);
		
		//勾兑对象转换为流水对象
		FinBizItem finBizItem = FinConvertUtil.changeReconToBizItem(finReconResult,null);
		//插入流水记录
		finBizItemService.insertFinBizItem(finBizItem);
		
		//流水表中当月的记录如果未入账或入账失败，则流水状态改为取消
		if((Constant.GL_STATUS.INIT.equals(finBizItem.getGlStatus())) || (Constant.GL_STATUS.FAILED.equals(finBizItem.getGlStatus()))){
			if(finBizItem.getGlTime()!=null){
				if(currentMonthFinBizItem(finBizItem.getGlTime())){
					finBizItem.setCancelStatus("Y");
					finBizItemService.updateFinBizItem(finBizItem);
				}
			}
		}
		
		return count;
	}
	
	/**
	 * 判断流水是否为当月的记录
	 * @param glTime
	 * @return
	 */
	private boolean currentMonthFinBizItem(Date glTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String glTimeString = sdf.format(glTime);
		int glTimeYear = Integer.valueOf(glTimeString.split("-")[0]);
		int glTimeMonth = Integer.valueOf(glTimeString.split("-")[1]);
		
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR); 
		int currentMonth = cal.get(Calendar.MONTH) + 1;
		
		if( currentYear==glTimeYear && currentMonth==glTimeMonth){
			return true;
		}else{
			return false;
		}
	}
	
	public int update(FinReconResult finReconResult){
		
		Map<String,String> paramMap=new HashMap<String,String>(); 
		paramMap.put("reconResultId", finReconResult.getReconResultId().toString()); 
		
		List<FinBizItem> finBizItemList = finBizItemService.selectFinBizItemListByParas(paramMap); 
		if(finBizItemList.size()>0){ 
			FinBizItem finBizItem = finBizItemList.get(0); 
			finBizItem = FinConvertUtil.changeReconToBizItem(finReconResult,finBizItem); 
			finBizItemService.updateFinBizItem(finBizItem); 
		} 
		
		/*
		//勾兑对象转换为流水对象
		FinBizItem finBizItem = FinConvertUtil.changeReconToBizItem(finReconResult,null);
		//修改流水记录
		finBizItemService.updateFinBizItem(finBizItem);
		*/		
		return finReconResultDAO.update(finReconResult);
	}
	
	public int updateGLStatus(FinReconResult finReconResult){
		return finReconResultDAO.updateGLStatus(finReconResult);
	}
	
	private void saveOrUpdate(List<FinReconResult> finReconResultList){
		for (FinReconResult finReconResult : finReconResultList) {
			saveOrUpdate(finReconResult);
		}
	}
	
	public void saveOrUpdate(FinReconResult finReconResult){
		if(finReconResult.getReconResultId()!=null && finReconResult.getReconResultId()>0){
			 update(finReconResult);
		}
		else{
			insert(finReconResult);
		}
	}
	
	public List<Long> insert(List<FinReconResult> finReconResultList) {
		List<Long> reconResultIdList=new ArrayList<Long>();
		for (FinReconResult finReconResult : finReconResultList) {
			Long reconResultId=insert(finReconResult);
			reconResultIdList.add(reconResultId);
		}
		return reconResultIdList;
	}
	public FinReconResult selectByReconResultId(Long reconResultId){
		return finReconResultDAO.selectByReconResultId(reconResultId);
	}
	/**
	 * 根据条件将当前网关+当天日期我方成功的交易数据插入到对账结果表中
	 * @author ZHANG Nan
	 * @param gateway 网关
	 * @param reconDate 对账日期(对哪天的帐)
	 * @return 是否成功
	 */
	public boolean copyTransactionDataToReconResult(String gatewayIN,Date reconDate){
		return copyTransactionDataToReconResult(gatewayIN, reconDate, Constant.RECON_STATUS.UN_RECON.name());
	}
	/**
	 * 根据条件将当前网关+当天日期我方成功的交易数据插入到对账结果表中
	 * @author ZHANG Nan
	 * @param gateway 网关
	 * @param reconDate 对账日期(对哪天的帐)
	 * @param defaultReconStatus 默认使用的对账状态(对于存款账户来说默认不需要勾兑直接成功)
	 * @return 是否成功
	 */
	public boolean copyTransactionDataToReconResult(String gatewayIN,Date reconDate,String defaultReconStatus){
		try {
			//复制成功的支付及充值数据到对账结果表中
			copyPayPaymentToReconResult(gatewayIN, reconDate,defaultReconStatus);
			//复制成功的退款及提现数据到对账结果表中
			copyPayPaymentRefundToReconResult(gatewayIN, reconDate,defaultReconStatus);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	private void copyPayPaymentToReconResult(String gatewayIN,Date reconDate,String defaultReconStatus){
		//根据条件查询出支付及充值记录
		List<PayAndPrePaymentVO> payPaymentList=payPaymentService.selectPaymentListByParasToRecon(gatewayIN,reconDate);
		//转换对象将数据查询到对账结果表中
		List<FinReconResult> finReconResultList=convertFinReconResult(gatewayIN,payPaymentList, reconDate,defaultReconStatus);
		saveOrUpdate(finReconResultList);
	}
	
	private List<FinReconResult> convertFinReconResult(String gatewayIN,List<PayAndPrePaymentVO> payPaymentList,Date bankReconTime,String defaultReconStatus){
		List<FinReconResult> finReconResultList=new ArrayList<FinReconResult>(); 
		for (PayAndPrePaymentVO payPayment : payPaymentList) {
			//如果交易为预授权并且状态不为预授权完成或退货,则不需要处理
			if(payPayment.isPrePayment()){
				if(!Constant.PAYMENT_PRE_STATUS.PRE_SUCC.name().equals(payPayment.getPreStatus())
						&&!Constant.PAYMENT_PRE_STATUS.PRE_REFUND.name().equals(payPayment.getPreStatus())){
					continue;
				}
			}
			
			String transactionTypeTemp=null;
			String defaultReconStatusTemp=defaultReconStatus;
			//判断是否为资金转移交易
			if(payPayment.getOriObjectId()!=null && payPayment.getOriObjectId()>0){
				transactionTypeTemp=Constant.TRANSACTION_TYPE.CANCEL_TO_CREATE_NEW.name();
				//如果交易为资金转移,则默认勾兑状态为成功
				defaultReconStatusTemp=Constant.RECON_STATUS.SUCCESS.name();
			}
			//判断是否为充值交易
			else if(Constant.PAYMENT_OBJECT_TYPE.CASH_RECHARGE.name().equals(payPayment.getObjectType())){
				transactionTypeTemp=Constant.TRANSACTION_TYPE.CASH_RECHARGE.name();
			}
			else{
				transactionTypeTemp=Constant.TRANSACTION_TYPE.PAYMENT.name();
			}
			FinReconResult finReconResult=null;
			try{
				finReconResult=selectReconResultListByParas(payPayment.getPaymentTradeNo(), payPayment.getGatewayTradeNo(), transactionTypeTemp, gatewayIN,null,payPayment.getPaymentId()+"");
			}catch(Exception e){
				log.info("convertFinReconResult select data paymentId="
						+ payPayment.getPaymentId() + " transactionTypeTemp="
						+ transactionTypeTemp + " GatewayTradeNo="
						+ payPayment.getGatewayTradeNo() + " PaymentTradeNo="
						+ payPayment.getPaymentTradeNo());
			}
			if(null==finReconResult && (Constant.RECON_GW_TYPE.STORED_CARD.getCode().equals(payPayment.getPaymentGateway()) ||Constant.RECON_GW_TYPE.DAI_JIN_QUAN.getCode().equals(payPayment.getPaymentGateway()))){
				Map<String,String> paramMap=new HashMap<String,String>();
				paramMap.put("transactionType", transactionTypeTemp);
				paramMap.put("paymentId", Long.toString(payPayment.getPaymentId()));
				List<FinReconResult> finReconResultListno=finReconResultDAO.selectReconResultListByParas(paramMap);
				if(null!=finReconResultListno && !finReconResultListno.isEmpty()){
					finReconResult = finReconResultListno.get(0);
				}else{
					finReconResult = new FinReconResult();
				}
			}else if(null==finReconResult){
				continue;
			}
			//如果这条勾兑结果已经为成功则直接跳过
			if(finReconResult!=null && Constant.RECON_STATUS.SUCCESS.name().equals(finReconResult.getReconStatus())){
				continue;
			}
			finReconResult.setPaymentTradeNo(payPayment.getPaymentTradeNo());
			finReconResult.setGatewayTradeNo(payPayment.getGatewayTradeNo());
			finReconResult.setAmount(payPayment.getAmount());
			finReconResult.setBankAmount(payPayment.getAmount());
			finReconResult.setCallbackTime(payPayment.getCallbackTime());
			finReconResult.setTransactionType(transactionTypeTemp);
			finReconResult.setTransactionSource(Constant.TRANSACTION_SOURCE.NORMAL.name());
			finReconResult.setOrderId(payPayment.getObjectId());
			finReconResult.setGateway(payPayment.getPaymentGateway());
			
			//取消支付网关为“杉德POS机现金支付”或“交行POS机现金支付”的免勾兑状态
			if(("SAND_POS_CASH".equals(payPayment.getPaymentGateway())) || ("COMM_POS_CASH".equals(payPayment.getPaymentGateway()))){
				
				PayPaymentDetail payPaymentDetail=payPaymentDetailService.selectPaymentDetailByPaymentId(String.valueOf(payPayment.getPaymentId()));
				
				if(payPaymentDetail!=null){
					if(Constant.PAYMENT_DETAIL_CASH_AUDIT_STATUS.UNLIBERATED.name().equals(payPaymentDetail.getCashAuditStatus())){
						finReconResult.setReconStatus(Constant.RECON_STATUS.FAIL.name());
						finReconResult.setReconResult("现金收款监控中该订单未解款");
					}else if(Constant.PAYMENT_DETAIL_CASH_AUDIT_STATUS.LIBERATE.name().equals(payPaymentDetail.getCashAuditStatus())){
						finReconResult.setReconStatus(Constant.RECON_STATUS.FAIL.name());
						finReconResult.setReconResult("现金收款监控中该订单已解款但未审核");
					}else if(Constant.PAYMENT_DETAIL_CASH_AUDIT_STATUS.VERIFIED.name().equals(payPaymentDetail.getCashAuditStatus())){
						finReconResult.setReconStatus(Constant.RECON_STATUS.SUCCESS.name());
						finReconResult.setReconResult("对账成功");
					}
				}
				
			}else{
				finReconResult.setReconStatus(defaultReconStatusTemp);
				
				//如果初始化交易时 默认状态为SUCCESS则认为是 我方免对账交易
				if(Constant.RECON_STATUS.SUCCESS.name().equals(finReconResult.getReconStatus())){
					finReconResult.setReconResult("我方免对账交易");	
				}
			}
			
			if(finReconResult.getTransactionTime() == null){
				finReconResult.setTransactionTime(payPayment.getCallbackTime());
			}
			//如果初始化交易时 默认状态为SUCCESS则认为是 我方免对账交易
			if(Constant.RECON_STATUS.SUCCESS.name().equals(finReconResult.getReconStatus())){
				finReconResult.setReconResult("我方免对账交易");	
			}
			finReconResult.setBankReconTime(bankReconTime);
			finReconResult.setCreateTime(new Date());
			finReconResult.setPaymentId(payPayment.getPaymentId());
			finReconResultList.add(finReconResult);
		}
		return finReconResultList;
	}
	
	private void copyPayPaymentRefundToReconResult(String refundGatewayIN,Date reconDate,String defaultReconStatus){
		//根据条件查询出退款及提现记录
		List<PayPaymentAndRefundment> payPaymentAndRefundmentList=payPaymentRefundmentService.selectPaymentAndRefundByPreReconRefundData(refundGatewayIN,reconDate);
		//转换对象将数据查询到对账结果表中
		List<FinReconResult> finReconResultList=convertRefundToFinReconResult(refundGatewayIN,payPaymentAndRefundmentList,reconDate,defaultReconStatus);
		saveOrUpdate(finReconResultList);
	}
	private List<FinReconResult> convertRefundToFinReconResult(String refundGatewayIN,List<PayPaymentAndRefundment> payPaymentAndRefundmentList,Date bankReconTime,String defaultReconStatus){
		List<FinReconResult> finReconResultList=new ArrayList<FinReconResult>(); 
		for (PayPaymentAndRefundment payPaymentAndRefundment : payPaymentAndRefundmentList) {
			//如果交易类型是预授权,并且预授权状态不为退款。则跳过
			if(payPaymentAndRefundment.isPrePayment()){
				if(!Constant.PAYMENT_PRE_STATUS.PRE_REFUND.name().equals(payPaymentAndRefundment.getPreStatus())){
					continue;
				}
			}
			
			String transactionTypeTemp=null;
			if(Constant.PAYMENT_OBJECT_TYPE.CASH_MONEY_DRAW.name().equals(payPaymentAndRefundment.getObjectType())){
				transactionTypeTemp=Constant.TRANSACTION_TYPE.CASH_MONEY_DRAW.name();
			}
			else{
				transactionTypeTemp=Constant.TRANSACTION_TYPE.REFUNDMENT.name();
			}
			FinReconResult finReconResult=null;
			try{
				finReconResult=selectReconResultListByParas(payPaymentAndRefundment.getSerial(), payPaymentAndRefundment.getPayGatewayTradeNo(), transactionTypeTemp, refundGatewayIN);
			}catch(Exception e){
				log.info("convertFinReconResult select data paymentId="
						+ payPaymentAndRefundment.getPaymentId() + " getSerial="
						+ payPaymentAndRefundment.getSerial() + " GatewayTradeNo="
						+ payPaymentAndRefundment.getPayGatewayTradeNo()+ " transactionTypeTemp="
						+ transactionTypeTemp);
			}
			if(null==finReconResult){
				Map<String,String> paramMap=new HashMap<String,String>();
				paramMap.put("transactionType", transactionTypeTemp);
				paramMap.put("paymentId", Long.toString(payPaymentAndRefundment.getPayRefundmentId()));
				List<FinReconResult> finReconResultListno=finReconResultDAO.selectReconResultListByParas(paramMap);
				if(null!=finReconResultListno && !finReconResultListno.isEmpty()){
					finReconResult = finReconResultListno.get(0);
				}else{
					finReconResult = new FinReconResult();
				}
			}
			//如果这条勾兑结果已经为成功则直接跳过
			if(finReconResult!=null && Constant.RECON_STATUS.SUCCESS.name().equals(finReconResult.getReconStatus())){
				continue;
			}
			
			finReconResult.setPaymentTradeNo(payPaymentAndRefundment.getSerial());
			finReconResult.setGatewayTradeNo(payPaymentAndRefundment.getPayGatewayTradeNo());
			finReconResult.setAmount(payPaymentAndRefundment.getAmount());
			finReconResult.setBankAmount(payPaymentAndRefundment.getAmount());
			finReconResult.setCallbackTime(payPaymentAndRefundment.getCallbackTime());
			finReconResult.setTransactionType(transactionTypeTemp);
			finReconResult.setTransactionSource(Constant.TRANSACTION_SOURCE.NORMAL.name());
			finReconResult.setOrderId(payPaymentAndRefundment.getOrderId());
			finReconResult.setGateway(payPaymentAndRefundment.getRefundGateway());
			finReconResult.setReconStatus(defaultReconStatus);
			if(finReconResult.getTransactionTime() == null){
				finReconResult.setTransactionTime(payPaymentAndRefundment.getCallbackTime());
			}
			//如果初始化交易时 默认状态为SUCCESS则认为是 我方免对账交易
			if(Constant.RECON_STATUS.SUCCESS.name().equals(finReconResult.getReconStatus())){
				finReconResult.setReconResult("我方免对账交易");	
			}
			finReconResult.setBankReconTime(bankReconTime);
			finReconResult.setCreateTime(new Date());
			finReconResult.setPaymentId(payPaymentAndRefundment.getPayRefundmentId());
			finReconResultList.add(finReconResult);
		}
		return finReconResultList;
	}
	
	/**
	 * 根据网关+对账日期(对哪天的帐)删除数据
	 * @author ZHANG Nan
	 * @param gateway 网关
	 * @param bankReconTime 对账日期(对哪天的帐)
	 * @param reconStatus 重新对账时根据状态来删除结果表老数据
	 * @return 删除行数
	 */
	@Override
	public int deleteOldData(String gatewayIN, Date bankReconTime,String reconStatus) {
		Assert.notNull(gatewayIN, "gatewayIN can't be empty");
		Assert.notNull(bankReconTime, "bankReconTime can't be empty");
		return finReconResultDAO.deleteOldData(gatewayIN, bankReconTime,reconStatus);
	}

	/**
	 * 统一更新网关+对账日期 未对账的数据
	 * @author ZHANG Nan
	 * @param gateway
	 * @param bankReconTimeShort
	 * @return
	 */
    public int updateNoMatchingReconResult(String gatewayIN,Date bankReconTimeShort,String gateway){
    	Map<String,Object> paramMap=new HashMap<String,Object>();
    	paramMap.put("setReconStatus", Constant.RECON_STATUS.FAIL.name());
    	paramMap.put("setReconResult", "我方有,"+Constant.RECON_GW_TYPE.getCnName(gateway)+"无匹配");
    	paramMap.put("gatewayIN", gatewayIN);
    	paramMap.put("bankReconTimeShort", bankReconTimeShort);
    	paramMap.put("reconStatus", Constant.RECON_STATUS.UN_RECON.name());
    	return finReconResultDAO.updateNoMatchingReconResult(paramMap);
	}
    /**
     * 根据对账流水号+网关交易号+交易类型+网关集合 查询对账结果对象
     * @author ZHANG Nan
     * @param paymentTradeNo 对账流水号
     * @param gatawayTradeNo 网关交易号
     * @param transactionType 交易类型
     * @param gatewayIN 网关集合
     * @return 对账结果对象
     */
    public FinReconResult selectReconResultListByParas(String paymentTradeNo,String gatawayTradeNo,String transactionType,String gatewayIN){
    	Assert.notNull(paymentTradeNo,"paymentTradeNo can't be empty!");
    	Assert.notNull(transactionType,"transactionType can't be empty!");
    	Assert.notNull(gatewayIN,"gatewayIN can't be empty!");
    	return selectReconResultListByParas(paymentTradeNo, gatawayTradeNo, transactionType, gatewayIN, null);
    }
    /**
     * 根据对账流水号+网关交易号+交易类型+网关集合 并且不包含某些主键 查询对账结果对象
     * @author ZHANG Nan
     * @param paymentTradeNo 对账流水号
     * @param gatawayTradeNo 网关交易号
     * @param transactionType 交易类型
     * @param gatewayIN 网关集合
     * @param notInReconResultIds 不包含的主键
     * @return 对账结果对象
     */
    public FinReconResult selectReconResultListByParas(String paymentTradeNo,String gatawayTradeNo,String transactionType,String gatewayIN,String notInReconResultIds){
    	Assert.notNull(paymentTradeNo,"paymentTradeNo can't be empty!");
    	Assert.notNull(transactionType,"transactionType can't be empty!");
    	Assert.notNull(gatewayIN,"gatewayIN can't be empty!");
    	return selectReconResultListByParas(paymentTradeNo, gatawayTradeNo, transactionType, gatewayIN, notInReconResultIds, null);
    }
    /**
     * 根据对账流水号+网关交易号+交易类型+网关集合 并且不包含某些主键 查询对账结果对象
     * @author ZHANG Nan
     * @param paymentTradeNo 对账流水号
     * @param gatawayTradeNo 网关交易号
     * @param transactionType 交易类型
     * @param gatewayIN 网关集合
     * @param notInReconResultIds 不包含的主键
     * @return 对账结果对象
     */
    public FinReconResult selectReconResultListByParas(String paymentTradeNo,String gatawayTradeNo,String transactionType,String gatewayIN,String notInReconResultIds,String paymentId){
    	Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("paymentTradeNo", paymentTradeNo);
		paramMap.put("gatewayTradeNo", gatawayTradeNo);
		paramMap.put("transactionType", transactionType);
		paramMap.put("gatewayIN", gatewayIN);
		paramMap.put("notInReconResultIds", notInReconResultIds);
		paramMap.put("paymentId", paymentId);
		List<FinReconResult> finReconResultList=finReconResultDAO.selectReconResultListByParas(paramMap);
		if(finReconResultList!=null && finReconResultList.size()>0){
			return finReconResultList.get(0);
		}
    	return new FinReconResult();
    }
    /**
     * 根据查询参数计算总行数
     * @author ZHANG Nan
     * @param paramMap 查询参数
     * @return 总行数
     */
    public Long selectReconResultListByParasCount(Map<String,String> paramMap){
    	return finReconResultDAO.selectReconResultListByParasCount(paramMap);
    }
    /**
     * 根据查询条件获取对账结果集合
     * @author ZHANG Nan
     * @param paramMap 查询条件
     * @return 对账结果集合
     */
	public List<FinReconResult> selectReconResultListByParas(Map<String,String> paramMap){
		return finReconResultDAO.selectReconResultListByParas(paramMap);
	}
	
	public List<FinReconResult> selectReconResultListForBatch(){
		return finReconResultDAO.selectReconResultListForBatch();
	}
	
    /**
     * 根据查询条件计算  我方交易总额及银行交易总额
     * @author ZHANG Nan
     * @param paramMap 查询条件
     * @return 我方交易总额及银行交易总额
     */
	public Map<String,String> selectTransactionAmountByParamMap(Map<String, String> paramMap){
		Map<String,BigDecimal> resultMap=finReconResultDAO.selectTransactionAmountByParamMap(paramMap);
		Map<String,String> map=new HashMap<String,String>();
		if(resultMap!=null){
			DecimalFormat df =new DecimalFormat("0.00");
			if(resultMap.get("TRANSACTIONAMOUNTSUM")!=null){
				map.put("TRANSACTIONAMOUNTSUM", df.format(resultMap.get("TRANSACTIONAMOUNTSUM").doubleValue()/100));	
			}
			else{
				map.put("TRANSACTIONAMOUNTSUM", "0");
			}
			if(resultMap.get("TRANSACTIONBANKAMOUNTSUM")!=null){
				map.put("TRANSACTIONBANKAMOUNTSUM",df.format(resultMap.get("TRANSACTIONBANKAMOUNTSUM").doubleValue()/100));	
			}
			else{
				map.put("TRANSACTIONBANKAMOUNTSUM", "0");
			}
		}
		return map;
	}
	
	
	public PayPaymentRefundmentService getPayPaymentRefundmentService() {
		return payPaymentRefundmentService;
	}

	public void setPayPaymentRefundmentService(PayPaymentRefundmentService payPaymentRefundmentService) {
		this.payPaymentRefundmentService = payPaymentRefundmentService;
	}

	public PayPaymentService getPayPaymentService() {
		return payPaymentService;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public FinReconResultDAO getFinReconResultDAO() {
		return finReconResultDAO;
	}

	public void setFinReconResultDAO(FinReconResultDAO finReconResultDAO) {
		this.finReconResultDAO = finReconResultDAO;
	}
	public int updateFailedReconResultGLStatus() {
		return finReconResultDAO.updateFailedReconResultGLStatus();
	}
	public FinBizItemService getFinBizItemService() {
		return finBizItemService;
	}
	public void setFinBizItemService(FinBizItemService finBizItemService) {
		this.finBizItemService = finBizItemService;
	}

	public PayPaymentDetailService getPayPaymentDetailService() {
		return payPaymentDetailService;
	}

	public void setPayPaymentDetailService(
			PayPaymentDetailService payPaymentDetailService) {
		this.payPaymentDetailService = payPaymentDetailService;
	}
	
	
}
