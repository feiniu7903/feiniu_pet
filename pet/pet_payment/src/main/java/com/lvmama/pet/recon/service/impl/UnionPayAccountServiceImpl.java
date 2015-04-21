package com.lvmama.pet.recon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pay.FinReconBankStatement;
import com.lvmama.comm.pet.po.pay.FinReconResult;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentAndRefundment;
import com.lvmama.comm.pet.service.pay.FinReconBankStatementService;
import com.lvmama.comm.pet.service.pay.FinReconResultService;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.service.GatewayAccountService;
import com.lvmama.pet.recon.utils.ReconCommonUtil;
import com.lvmama.pet.recon.utils.UnionpayParsePayData;
import com.lvmama.pet.recon.utils.UnionpayReconUtil;
import com.lvmama.pet.vo.UnionPayResponseBean;

public class UnionPayAccountServiceImpl implements GatewayAccountService{
	
	protected final Log log =LogFactory.getLog(this.getClass().getName());
	//银行交易明细接口
	private FinReconBankStatementService finReconBankStatementService;
	//对账结果接口
	private FinReconResultService finReconResultService;
	//支付接口
	private PayPaymentService payPaymentService;
	//退款接口
	private PayPaymentRefundmentService payPaymentRefundmentService; 
	//下载对账开始日期
	private Date startDate=null;
	//下载对账结束日期
	private Date endDate=null;
	//下载后默认使用的网关CODE
	private static String GATEWAY = Constant.RECON_GW_TYPE.UNIONPAY.name();
	//处理我方网关时包括的网关CODE
	private static String GATEWAY_IN = "'" + Constant.RECON_GW_TYPE.UNIONPAY.name() + 
			"','" + Constant.RECON_GW_TYPE.CHINAPAY_PRE.name()+ "'";
	//成功标志
	private static String SUCCESS="SUCCESS";
	
	/**
	 * 定时任务自动处理账务
	 */
	@Override
	public String processAccount() {
		Date date = new Date();
		//date=DateUtil.toDate("2013-05-10", "yyyy-MM-dd");
		//默认取前天所有数据(例:2013-03-24 00:00:00 ~ 2013-03-25 00:00:00)
		startDate = DateUtil.accurateToDay(DateUtil.getDateAfterDays(date, -2));
		endDate = DateUtil.accurateToDay(DateUtil.getDateAfterDays(date, -1));
		return processAccount(startDate, endDate);
	}
	
	private String processAccount(Date startDate, Date endDate) {
		return processAccount(startDate, endDate, "");
	}
	/**
	 * 根据日期处理账务
	 */
	@Override
	public String processAccount(Date startDate, Date endDate,String reconStatus) {
		this.startDate=startDate;
		this.endDate=endDate;
		//先将网关+当天日期的老数据删除
		int rows=finReconBankStatementService.deleteOldData(GATEWAY_IN,startDate);
		log.info("delete bank_statement old data,rows:"+rows+",GATEWAY_IN:"+GATEWAY_IN+",date:"+DateUtil.formatDate(startDate, "yyyy-MM-dd"));
		//下载、解析、插入银行端源数据
		String result=parseData();
		log.info("parseData complete, result:"+result);
		if(SUCCESS.equals(result)){
			//对账
			log.info("start recon, reconStatus:"+reconStatus);
			return recon(reconStatus);
		}
		return result;
	}
	
	/**
	 * 下载、解析、插入银行端源数据
	 * @author ZHANG Nan
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param pageNo 页面数
	 * @return 是否成功
	 */
	private String parseData(){
		// 请求数据
		String datas = UnionpayReconUtil.queryReconData(startDate);
		if(StringUtils.isBlank(datas)){
			log.error("download date fail for unionPay");
			return "Download Reconciliation file failed , please re- download! gateway:"+GATEWAY;
		}
		//解析数据
		List<UnionPayResponseBean> unionPayResponseBeanList=UnionpayParsePayData.parseData(datas);		
		//将解析完成的数据封装对象
		List<FinReconBankStatement> finReconBankStatementList=convertFinReconBankStatement(unionPayResponseBeanList,startDate);
		//再将最新下载的数据保存
		finReconBankStatementService.insert(finReconBankStatementList);
		return SUCCESS;
	}
	/**
	 * 封装对象以方便将银行端数据插入
	 * @author ZHANG Nan
	 * @param alipayAccountLogVOList 解析后对象
	 * @param downloadTime 下载日期
	 * @return 封装后可插入数据库的对象
	 */
	private List<FinReconBankStatement> convertFinReconBankStatement(List<UnionPayResponseBean> unionPayResponseBeanList,Date downloadTime){
		List<FinReconBankStatement> finReconBankStatementList=new ArrayList<FinReconBankStatement>(); 
		for (UnionPayResponseBean unionPayResponseBean : unionPayResponseBeanList) {
			if(unionPayResponseBean.isNeedRecon()){
				FinReconBankStatement finReconBankStatement=new FinReconBankStatement();
				
				finReconBankStatement.setGateway(GATEWAY);
				finReconBankStatement.setBankPaymentTradeNo(unionPayResponseBean.getBankPaymentTradeNo());
				//收入
				if(Constant.TRANSACTION_TYPE.PAYMENT.name().equals(unionPayResponseBean.transCodeMsgToCode())){
					finReconBankStatement.setAmount(unionPayResponseBean.getAmount());
					//银联交易流水号只在支付类型时判断
					finReconBankStatement.setBankGatewayTradeNo(unionPayResponseBean.getBankGatewayTradeNo());
				}
				//支出
				else if(Constant.TRANSACTION_TYPE.REFUNDMENT.name().equals(unionPayResponseBean.transCodeMsgToCode())){
					finReconBankStatement.setOutAmount(unionPayResponseBean.getAmount());	
				}
				finReconBankStatement.setTransactionTime(unionPayResponseBean.getTransactionTime());
				finReconBankStatement.setTransactionType(unionPayResponseBean.transCodeMsgToCode());
				
				finReconBankStatement.setDownloadTime(downloadTime);
				finReconBankStatement.setCreateTime(new Date());
				finReconBankStatementList.add(finReconBankStatement);
			}
		}
		return finReconBankStatementList;
	}
	
	
	
	
	
	/**
	 * 银联对账
	 * @author ZHANG Nan
	 * @param reconDate  对帐日期(对哪天的帐)
	 * @param reconStatus 重新对账时根据状态来删除结果表老数据
	 * @return 是否成功
	 */
	private String recon(String reconStatus){
		//网关+对账日期(对哪天的帐)删除老数据
		int rows=finReconResultService.deleteOldData(GATEWAY_IN, startDate,reconStatus);
		log.info("delete recon_result old data,rows:"+rows+",GATEWAY_IN:"+GATEWAY_IN+",date:"+DateUtil.formatDate(startDate, "yyyy-MM-dd")+",reconStatus:"+reconStatus);
		
		//以当前网关+对账日期为条件,将 我方成功的交易数据插入到对账结果表中
		boolean result=finReconResultService.copyTransactionDataToReconResult(GATEWAY_IN, startDate);
		log.info("copyTransactionDataToReconResult complete, result:"+result);
		if(result){
			//抓银行源数据开始对账
			Map<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put("gateway", GATEWAY);
			paramMap.put("downloadTimeShort", startDate);
			List<FinReconBankStatement> finReconBankStatementList=finReconBankStatementService.selectByParamMap(paramMap);
			for (FinReconBankStatement finReconBankStatement : finReconBankStatementList) {
				try {
					//银联对账
					log.debug("start processRecon, finReconBankStatement:"+StringUtil.printParam(finReconBankStatement));
					processRecon(finReconBankStatement,StringUtils.isBlank(reconStatus));
				} catch (Exception e) {
					e.printStackTrace();
					log.error("Single data recon fail,finReconBankStatement:"+StringUtil.printParam(finReconBankStatement));
				}
			}
			//银联对账后 如还有未对账状态的数据,则直接更新为我方有,银联无匹配
			int noMatchingRows=finReconResultService.updateNoMatchingReconResult(GATEWAY_IN, startDate,GATEWAY);
			log.info("update no matching data ,rows:"+noMatchingRows+",GATEWAY_IN:"+GATEWAY_IN+",date:"+DateUtil.formatDate(startDate, "yyyy-MM-dd"));
		}
		return SUCCESS;
	}
	/**
	 * 对账逻辑
	 * @author ZHANG Nan
	 * @param finReconBankStatement 银行源数据对象
	 * @param processUnneedRecon 是否处理免对账数据(对于手工重新对账而言不需要重新处理免对账数据)
	 * @return 是否成功
	 */
	public void processRecon(FinReconBankStatement finReconBankStatement,boolean processUnneedRecon){
		//免对账数据(支付手续费、退款手续费、普通提现、对外付款 无法对账)
		if(finReconBankStatement.isUnneedRecon()){
			if(processUnneedRecon){
				processUnneedRecon(finReconBankStatement);	
			}
		}
		//支付对账
		else if(Constant.TRANSACTION_TYPE.PAYMENT.name().equals(finReconBankStatement.getTransactionType())){
			processPaymentRecon(finReconBankStatement);
		}
		//退款对账
		else if(Constant.TRANSACTION_TYPE.REFUNDMENT.name().equals(finReconBankStatement.getTransactionType())){
			processPaymentRefundRecon(finReconBankStatement);
		}
		else{
			log.error("There is no matching of transaction_type,finReconBankStatement:"+StringUtil.printParam(finReconBankStatement));
		}
	}
	/**
	 * 免对账
	 * @author ZHANG Nan
	 * @param finReconBankStatement
	 * @return
	 */
	private void processUnneedRecon(FinReconBankStatement finReconBankStatement){
		FinReconResult finReconResult = new FinReconResult ();
		finReconResult=ReconCommonUtil.setBankStatementCommonParam(finReconResult, finReconBankStatement);
		finReconResult.setReconStatus(Constant.RECON_STATUS.SUCCESS.name());
		finReconResult.setReconResult("银行免对账交易");
		finReconResultService.insert(finReconResult);
	}
	/**
	 * 支付对账
	 * @author ZHANG Nan
	 * @param finReconBankStatement
	 */
	private void processPaymentRecon(FinReconBankStatement finReconBankStatement){
		//获取非资金转移的原始交易
		List<PayPayment> payPaymentList=payPaymentService.selectByPaymentTradeNoAndGatewayPure(finReconBankStatement.getBankPaymentTradeNo(),GATEWAY_IN,true);
		if(payPaymentList.size()==0){
			//处理预授权的交易.  如果在原始交易中为找到对应的交易记录,则在资金转移的交易中寻找(预授权有可能在资金转移后才做预授权扣款) 
			List<PayPayment> tempPayPaymentList=payPaymentService.selectByPaymentTradeNoAndGatewayPure(finReconBankStatement.getBankPaymentTradeNo(),"'" + Constant.RECON_GW_TYPE.CHINAPAY_PRE.name()+ "'",false);
			//筛选出第一次被资金转移的交易(根据资金转移规则:资金转移后自动做预授权扣款)
			PayPayment prePayPayment=selectOriTransactionPayment(tempPayPaymentList);
			if(prePayPayment!=null){
				payPaymentList.add(prePayPayment);	
			}
		}
		
		
		if(payPaymentList.size()==0){
			recordNoMatchProcess(finReconBankStatement);
			return ;
		}
		else if(payPaymentList.size()==1){
			//TODO 根据payment_trade_no找到一条交易记录
			PayPayment payPayment=payPaymentList.get(0);
			
			FinReconResult finReconResult = finReconResultService.selectReconResultListByParas(payPayment.getPaymentTradeNo(),
					payPayment.getGatewayTradeNo(), Constant.TRANSACTION_TYPE.PAYMENT.name(), GATEWAY_IN,null,payPayment.getPaymentId()+"");
			
			//如果这笔交易已经成功则跳过，不做任何处理
			if(reconStatusIsSuccess(finReconResult)){
				return ;
			}
			
			finReconResult=ReconCommonUtil.setBankStatementCommonParam(finReconResult, finReconBankStatement);
			finReconResult=ReconCommonUtil.setPayPaymentCommonParam(finReconResult, payPayment,startDate);
			
			
			//处理网关交易号无法匹配的情况
			boolean gatewayTradeNoMatchFlag=gatewayTradeNoMatch(finReconBankStatement.getBankGatewayTradeNo(), payPayment.getGatewayTradeNo(), finReconResult, "");
			if(!gatewayTradeNoMatchFlag){
				return ;
			}
			
			//处理交易金额无法匹配的情况
			boolean amountMatchFlag=amountMatch(finReconBankStatement.getCompareAmount(), payPayment.getAmount(), finReconResult,"");
			if(!amountMatchFlag){
				return ;
			}
			
			//处理交易记录状态
			boolean statusMatchFlag=statusMatch(payPayment.getStatus(), finReconResult, "");
			if(!statusMatchFlag){
				return ;
			}
			
			finReconResult.setReconStatus(Constant.RECON_STATUS.SUCCESS.name());
			finReconResult.setReconResult("对账成功");
			//处理交易日期不在同一天的情况
			finReconResult=transactionTimeMatch(finReconBankStatement.getTransactionTime(), payPayment.getCallbackTime(), finReconResult,"");
			finReconResultService.saveOrUpdate(finReconResult);
			return ;
		}
		else{
			String prefix="该交易为合并支付,订单号包括:"+ReconCommonUtil.getOrderIds(payPaymentList)+",";
			Long sumAomunt=finReconBankStatement.getCompareAmount();
			StringBuffer notInReconResultIds=new StringBuffer();
			for (PayPayment payPayment : payPaymentList) {
				FinReconResult finReconResult= finReconResultService.selectReconResultListByParas(payPayment.getPaymentTradeNo(),
						payPayment.getGatewayTradeNo(), Constant.TRANSACTION_TYPE.PAYMENT.name(), GATEWAY_IN,ReconCommonUtil.removeTailcomma(notInReconResultIds),payPayment.getPaymentId()+"");
				
				//如果这笔交易已经成功则跳过，不做任何处理
				if(reconStatusIsSuccess(finReconResult)){
					continue;
				}
				
				if(finReconResult.getReconResultId()!=null && finReconResult.getReconResultId()>0){
					notInReconResultIds.append("'"+finReconResult.getReconResultId()+"',");
				}
				
				finReconResult=ReconCommonUtil.setBankStatementCommonParam(finReconResult, finReconBankStatement);
				finReconResult=ReconCommonUtil.setPayPaymentCommonParam(finReconResult, payPayment,startDate);
				
				//处理网关交易号无法匹配的情况
				boolean gatewayTradeNoMatchFlag=gatewayTradeNoMatch(finReconBankStatement.getBankGatewayTradeNo(),payPayment.getGatewayTradeNo(),finReconResult,prefix);
				if(!gatewayTradeNoMatchFlag){
					continue;
				}
				
				//处理交易记录状态
				boolean statusMatchFlag=statusMatch(payPayment.getStatus(), finReconResult, prefix);
				if(!statusMatchFlag){
					continue;
				}
				
				//处理交易日期不在同一天的情况
				finReconResult=transactionTimeMatch(finReconBankStatement.getTransactionTime(), payPayment.getCallbackTime(), finReconResult, prefix);
				
				if(sumAomunt-payPayment.getAmount()>=0){
					finReconResult.setBankAmount(payPayment.getAmount());
					sumAomunt=sumAomunt-payPayment.getAmount();
					finReconResult.setReconStatus(Constant.RECON_STATUS.SUCCESS.name());
					finReconResult.setReconResult(ReconCommonUtil.removeTailcomma(prefix));
					finReconResultService.saveOrUpdate(finReconResult);
					continue;
				}
				else{
					finReconResult.setBankAmount(sumAomunt);
					finReconResult.setReconStatus(Constant.RECON_STATUS.FAIL.name());
					finReconResult.setReconResult(prefix+"交易总金额不匹配");
					finReconResultService.saveOrUpdate(finReconResult);
					continue;
				}
			}
		}
	}
	
	/**
	 * 退款对账
	 * @author ZHANG Nan
	 * @param finReconBankStatement
	 */
	private void processPaymentRefundRecon(FinReconBankStatement finReconBankStatement){
		//在类型为退款时 BankPaymentTradeNo记的值为我方pay_payment_refundment表的serial
		PayPaymentAndRefundment payPaymentAndRefundment=payPaymentRefundmentService.selectPaymentAndRefundBySerialAndGateway(finReconBankStatement.getBankPaymentTradeNo(),GATEWAY_IN);
		if(payPaymentAndRefundment.getPayRefundmentId()==null){
			recordNoMatchProcess(finReconBankStatement);
			return ;
		}
		FinReconResult finReconResult = finReconResultService.selectReconResultListByParas(payPaymentAndRefundment.getSerial(),
				payPaymentAndRefundment.getPayGatewayTradeNo(), Constant.TRANSACTION_TYPE.REFUNDMENT.name(), GATEWAY_IN);
		
		//如果这笔交易已经成功则跳过，不做任何处理
		if(reconStatusIsSuccess(finReconResult)){
			return ;
		}
		
		finReconResult=ReconCommonUtil.setBankStatementCommonParam(finReconResult, finReconBankStatement);
		finReconResult=ReconCommonUtil.setPayPaymentRefundCommonParam(finReconResult, payPaymentAndRefundment,startDate);
		
		//处理交易金额无法匹配的情况
		boolean amountMatchFlag=amountMatch(finReconBankStatement.getCompareAmount(), payPaymentAndRefundment.getAmount(), finReconResult,"");
		if(!amountMatchFlag){
			return ;
		}
		//处理交易记录状态
		boolean statusMatchFlag=statusMatch(payPaymentAndRefundment.getStatus(), finReconResult, "");
		if(!statusMatchFlag){
			return ;
		}
		
		finReconResult.setReconStatus(Constant.RECON_STATUS.SUCCESS.name());
		finReconResult.setReconResult("对账成功");
		//处理交易日期不在同一天的情况
		finReconResult=transactionTimeMatch(finReconBankStatement.getTransactionTime(), payPaymentAndRefundment.getCallbackTime(), finReconResult,"");
		finReconResultService.saveOrUpdate(finReconResult);
		return ;
	}

	
	/**
	 * 判断这笔交易的勾兑结果是否已经为成功
	 * @author ZHANG Nan
	 * @param finReconResult 勾兑结果交易
	 * @return 是否已经为勾兑成功
	 */
	private boolean reconStatusIsSuccess(FinReconResult finReconResult){
		if(finReconResult!=null && Constant.RECON_STATUS.SUCCESS.name().equals(finReconResult.getReconStatus())){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 处理银联有,我方未找到交易记录
	 * @author ZHANG Nan
	 * @param finReconBankStatement
	 */
	private void recordNoMatchProcess(FinReconBankStatement finReconBankStatement){
		FinReconResult finReconResult=new FinReconResult();
		finReconResult=ReconCommonUtil.setBankStatementCommonParam(finReconResult, finReconBankStatement);
		finReconResult.setReconStatus(Constant.RECON_STATUS.FAIL.name());
		finReconResult.setReconResult(Constant.RECON_GW_TYPE.getCnName(GATEWAY)+"有,我方未找到交易记录");
		finReconResultService.insert(finReconResult);
	}
	
	
	/**
	 * 处理网关交易号无法匹配的情况
	 * @author ZHANG Nan
	 */
	private boolean gatewayTradeNoMatch(String bankGatewayTradeNo,String gatewayTradeNo,FinReconResult finReconResult,String resultPrefix){
		if(!bankGatewayTradeNo.equals(gatewayTradeNo)){
			finReconResult.setReconStatus(Constant.RECON_STATUS.FAIL.name());
			finReconResult.setReconResult(resultPrefix+"网关交易号不匹配");
			finReconResultService.saveOrUpdate(finReconResult);
			return false;
		}
		else{
			return true;
		}
	}
	/**
	 * 处理交易金额无法匹配的情况
	 * @author ZHANG Nan
	 */
	private boolean amountMatch(Long bankAmount,Long amount,FinReconResult finReconResult,String resultPrefix){
		if(!bankAmount.equals(amount)){
			finReconResult.setReconStatus(Constant.RECON_STATUS.FAIL.name());
			finReconResult.setReconResult(resultPrefix+"交易金额不匹配");
			finReconResultService.saveOrUpdate(finReconResult);
			return false;
		}
		else{
			return true;
		}
	}
	/**
	 * 处理交易记录状态
	 * @author ZHANG Nan
	 */
	private boolean statusMatch(String status,FinReconResult finReconResult,String resultPrefix){
		//支付状态不等于成功 并且不等于已转移的都为失败
		if(!Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name().equals(status) && !Constant.PAYMENT_SERIAL_STATUS.TRANSFERRED.name().equals(status)){
			finReconResult.setReconStatus(Constant.RECON_STATUS.FAIL.name());
			finReconResult.setReconResult(resultPrefix+"交易记录状态非成功,为:"+status);
			finReconResultService.saveOrUpdate(finReconResult);
			return false;
		}
		else{
			return true;
		}
	}
	/**
	 * 处理交易日期不在同一天的情况
	 * @author ZHANG Nan
	 */
	private FinReconResult transactionTimeMatch(Date transactionTime,Date callbackTime,FinReconResult finReconResult,String resultPrefix){
		if(transactionTime==null || callbackTime==null || DateUtil.accurateToDay(transactionTime).compareTo(DateUtil.accurateToDay(callbackTime))!=0){
			finReconResult.setReconResult(resultPrefix+"对账成功,注意:交易日期不在同一天");
		}
		return finReconResult;
	}
	
	/**
	 * 筛选出第一次资金转移的交易
	 * @author ZHANG Nan
	 * @param payPaymentList 支付交易集合
	 * @return 第一次资金转移的交易
	 */
	private PayPayment selectOriTransactionPayment(List<PayPayment> payPaymentList){
		if(payPaymentList!=null && payPaymentList.size()>0){
			Map<Long,PayPayment> payPaymentMap=new HashMap<Long,PayPayment>();
			//根据对象ID保存集合
			for (PayPayment payPayment : payPaymentList) {
				payPaymentMap.put(payPayment.getObjectId(), payPayment);
			}
			//遍历支付集合 通过oriObjectId寻找老交易记录,如果能找到则说明并不是第一次资金转移的支付。(直到通过老对象Id找不到交易记录时返回)
			for (PayPayment payPayment : payPaymentList) {
				PayPayment ordPayPayment=payPaymentMap.get(payPayment.getOriObjectId());
				if(ordPayPayment==null){
					return payPayment;
				}
			}
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public PayPaymentService getPayPaymentService() {
		return payPaymentService;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public FinReconResultService getFinReconResultService() {
		return finReconResultService;
	}

	public void setFinReconResultService(FinReconResultService finReconResultService) {
		this.finReconResultService = finReconResultService;
	}

	public FinReconBankStatementService getFinReconBankStatementService() {
		return finReconBankStatementService;
	}

	public void setFinReconBankStatementService(FinReconBankStatementService finReconBankStatementService) {
		this.finReconBankStatementService = finReconBankStatementService;
	}

	public PayPaymentRefundmentService getPayPaymentRefundmentService() {
		return payPaymentRefundmentService;
	}

	public void setPayPaymentRefundmentService(PayPaymentRefundmentService payPaymentRefundmentService) {
		this.payPaymentRefundmentService = payPaymentRefundmentService;
	}
}
