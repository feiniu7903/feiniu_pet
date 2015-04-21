package com.lvmama.pet.recon.utils;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.pay.FinReconBankStatement;
import com.lvmama.comm.pet.po.pay.FinReconResult;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentAndRefundment;
import com.lvmama.comm.vo.Constant;

public class ReconCommonUtil {
	/**
	 * 封装BankStatement公共参数
	 * @author ZHANG Nan
	 * @param finReconResult
	 * @param finReconBankStatement
	 * @return
	 */
	public static FinReconResult setBankStatementCommonParam(FinReconResult finReconResult,FinReconBankStatement finReconBankStatement){
		finReconResult.setBankPaymentTradeNo(finReconBankStatement.getBankPaymentTradeNo());
		finReconResult.setBankGatewayTradeNo(finReconBankStatement.getBankGatewayTradeNo());
		//是否为收入类型的交易
		if(finReconBankStatement.isIncome()){
			finReconResult.setBankAmount(finReconBankStatement.getAmount());
		}
		else{
			finReconResult.setBankAmount(finReconBankStatement.getOutAmount());
		}
		finReconResult.setTransactionTime(finReconBankStatement.getTransactionTime());
		finReconResult.setTransactionType(finReconBankStatement.getTransactionType());
		finReconResult.setTransactionSource(Constant.TRANSACTION_SOURCE.NORMAL.name());
		if(StringUtils.isBlank(finReconResult.getGateway())){
			finReconResult.setGateway(finReconBankStatement.getGateway());	
		}
		finReconResult.setBankReconTime(finReconBankStatement.getDownloadTime());
		finReconResult.setCreateTime(new Date());
		return finReconResult;
	}
	/**
	 * 封装PayPayment公共参数
	 * @author ZHANG Nan
	 * @param finReconResult
	 * @param finReconBankStatement
	 * @return
	 */
	public static FinReconResult setPayPaymentCommonParam(FinReconResult finReconResult,PayPayment payPayment,Date reconDate){
		finReconResult.setPaymentTradeNo(payPayment.getPaymentTradeNo());
		finReconResult.setGatewayTradeNo(payPayment.getGatewayTradeNo());
		finReconResult.setAmount(payPayment.getAmount());
		finReconResult.setCallbackTime(payPayment.getCallbackTime());
		finReconResult.setGateway(payPayment.getPaymentGateway());
		finReconResult.setOrderId(payPayment.getObjectId());
		finReconResult.setPaymentId(payPayment.getPaymentId());
		if(reconDate!=null){
			finReconResult.setBankReconTime(reconDate);	
		}
		finReconResult.setCreateTime(new Date());
		return finReconResult;
	}
	/**
	 * 封装PayPaymentRefundment公共参数
	 * @author ZHANG Nan
	 * @param finReconResult
	 * @param finReconBankStatement
	 * @return
	 */
	public static FinReconResult setPayPaymentRefundCommonParam(FinReconResult finReconResult,PayPaymentAndRefundment payPaymentAndRefundment,Date reconDate){
		finReconResult.setPaymentTradeNo(payPaymentAndRefundment.getSerial());
		finReconResult.setGatewayTradeNo(payPaymentAndRefundment.getPayGatewayTradeNo());
		finReconResult.setAmount(payPaymentAndRefundment.getAmount());
		finReconResult.setCallbackTime(payPaymentAndRefundment.getCallbackTime());
		finReconResult.setGateway(payPaymentAndRefundment.getRefundGateway());
		finReconResult.setBankReconTime(reconDate);
		finReconResult.setCreateTime(new Date());
		finReconResult.setOrderId(payPaymentAndRefundment.getOrderId());
		return finReconResult;
	}
	/**
	 * 抽取orderId
	 * @author ZHANG Nan
	 * @param payPaymentList
	 * @return
	 */
	public static String getOrderIds(List<PayPayment> payPaymentList){
		StringBuffer orderIds=new StringBuffer();
		for (PayPayment payPayment : payPaymentList) {
			orderIds.append(payPayment.getObjectId()+",");
		}
		return removeTailcomma(orderIds);
	}
	
	/**
	 * 删除最后一位逗号
	 * @author ZHANG Nan
	 * @param str
	 * @return
	 */
	public static String removeTailcomma(StringBuffer strBuffer){
		return removeTailcomma(strBuffer.toString());
	}
	public static String removeTailcomma(String str){
		if(StringUtils.isNotBlank(str) && str.length()>=1){
			return str.substring(0, str.length()-1);
		}
		return str;
	}
}
