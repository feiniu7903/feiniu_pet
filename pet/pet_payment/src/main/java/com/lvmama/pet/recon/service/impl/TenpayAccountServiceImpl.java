package com.lvmama.pet.recon.service.impl;

import java.io.IOException;
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
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.recon.service.GatewayAccountService;
import com.lvmama.pet.recon.utils.ReconCommonUtil;
import com.lvmama.pet.utils.TenpayUtil;
import com.lvmama.pet.vo.TenpayAccountResponseBean;
import com.lvmama.pet.vo.TenpayAccountVO;

public class TenpayAccountServiceImpl implements GatewayAccountService{
	
	protected final Log log =LogFactory.getLog(this.getClass().getName());
	//银行交易明细接口
	private FinReconBankStatementService finReconBankStatementService;
	//对账结果接口
	private FinReconResultService finReconResultService;
	//支付接口
	private PayPaymentService payPaymentService;
	//退款接口
	private PayPaymentRefundmentService payPaymentRefundmentService; 
	//下载需对账的日期
	private Date accountDate=null;
	//下载后默认使用的网关CODE
	private static String GATEWAY = Constant.RECON_GW_TYPE.TENPAY.name();
	//处理我方网关时包括的网关CODE
	private static String GATEWAY_IN = "'" + Constant.RECON_GW_TYPE.TENPAY.name() + "'";
	//成功标志
	private static String SUCCESS="SUCCESS";
	
	/**
	 * 定时任务自动处理财付通账务
	 */
	@Override
	public String processAccount() {
		//默认取前天所有数据(例:2013-03-24)
		Date date = new Date();
		this.accountDate = DateUtil.toYMDDate(DateUtil.getDateAfterDays(date, -2));
		Date endDate = DateUtil.accurateToDay(DateUtil.getDateAfterDays(date, -1));	
		
		return processAccount(accountDate,endDate,"");
	}
	

	@Override
	public String processAccount(Date startDate, Date endDate,String reconStatus) {
		this.accountDate = startDate;
		//先将网关+当天日期的老数据删除
		int rows=finReconBankStatementService.deleteOldData(GATEWAY_IN,accountDate);
		log.info("delete bank_statement old data,rows:"+rows+",GATEWAY_IN:"+GATEWAY_IN+",date:"+DateUtil.formatDate(accountDate, "yyyy-MM-dd"));
		//下载、解析、插入银行端源数据
		String result=parseData();
		log.info("parseData complete, result:"+result);
		if(SUCCESS.equals(result)){
			//对账
			log.info("start recon, reconStatus:"+reconStatus);
			return recon(reconStatus);
		}
		return reconStatus;
	}
	
	/**
	 * 下载、解析、插入银行端源数据
	 * @author zhangjie
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param pageNo 页面数
	 * @return 是否成功
	 */
	private String parseData(){
		//下载数据
		try {
			String data = TenpayUtil.cllTenpayHttpAccount(accountDate);
			if(StringUtils.isBlank(data)){
				log.error("download date fail for tenpay");
				return "Download Reconciliation file failed , please re- download! gateway:"+GATEWAY;
			}
			//解析数据
			TenpayAccountResponseBean tenpayAccountResponseBean = TenpayAccountResponseBean.parseResponseBean(data);
			if(tenpayAccountResponseBean.isSuccessAccount()) {
				//将解析完成的数据封装对象
				List<FinReconBankStatement> finReconBankStatementList=convertFinReconBankStatement(tenpayAccountResponseBean,accountDate);
				//再将最新下载的数据保存
				finReconBankStatementService.insert(finReconBankStatementList);
			}else{
				log.error("download date fail for tenpay , response date :"+data);
				return "Download Reconciliation file failed , please re- download! gateway:"+GATEWAY;
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error("download date fail for tenpay");
		}
		return SUCCESS;
	}
	
	/**
	 * 封装对象以方便将银行端数据插入
	 * @author zhangjie
	 * @param tenpayAccountResponseBean 解析后对象
	 * @param downloadTime 下载日期
	 * @return 封装后可插入数据库的对象
	 */
	private List<FinReconBankStatement> convertFinReconBankStatement(TenpayAccountResponseBean tenpayAccountResponseBean,Date downloadTime){
		List<FinReconBankStatement> finReconBankStatementList=new ArrayList<FinReconBankStatement>(); 
		for (TenpayAccountVO tenpayAccountVO : tenpayAccountResponseBean.getTenpayAccountVOs()) {
			if(tenpayAccountVO!=null){
				FinReconBankStatement finReconBankStatement=new FinReconBankStatement();
				finReconBankStatement.setGateway(GATEWAY);
				finReconBankStatement.setTransactionType(tenpayAccountVO.getTransactionType());
				finReconBankStatement.setBankPaymentTradeNo(tenpayAccountVO.getMerchTradeNo());	
				finReconBankStatement.setBankGatewayTradeNo(tenpayAccountVO.getTenpayTradeNo());
				finReconBankStatement.setAmount(PriceUtil.convertToFen(tenpayAccountVO.getTradeAmount()));
				finReconBankStatement.setOutAmount(PriceUtil.convertToFen(tenpayAccountVO.getRefundAmount()));	
				finReconBankStatement.setTransactionTime(DateUtil.toDate(tenpayAccountVO.getTransTime(), "yyyy-MM-dd HH:mm:ss"));
				finReconBankStatement.setDownloadTime(downloadTime);
				finReconBankStatement.setCreateTime(new Date());
				finReconBankStatement.setMemo(tenpayAccountVO.getTransShow());
				finReconBankStatementList.add(finReconBankStatement);
			}
		}
		return finReconBankStatementList;
	}
	
	
	/**
	 * 财付通对账
	 * @author zhangjie
	 * @param reconDate  对帐日期(对哪天的帐)
	 * @param reconStatus 重新对账时根据状态来删除结果表老数据
	 * @return 是否成功
	 */
	private String recon(String reconStatus){
		//网关+对账日期(对哪天的帐)删除老数据
		int rows=finReconResultService.deleteOldData(GATEWAY_IN, accountDate,reconStatus);
		log.info("delete recon_result old data,rows:"+rows+",GATEWAY_IN:"+GATEWAY_IN+",date:"+DateUtil.formatDate(accountDate, "yyyy-MM-dd")+",reconStatus:"+reconStatus);
		
		//以当前网关+对账日期为条件,将 我方成功的交易数据插入到对账结果表中
		boolean result=finReconResultService.copyTransactionDataToReconResult(GATEWAY_IN, accountDate);
		log.info("copyTransactionDataToReconResult complete, result:"+result);
		if(result){
			//抓银行源数据开始对账
			Map<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put("gateway", GATEWAY);
			paramMap.put("downloadTimeShort", accountDate);
			List<FinReconBankStatement> finReconBankStatementList=finReconBankStatementService.selectByParamMap(paramMap);
			for (FinReconBankStatement finReconBankStatement : finReconBankStatementList) {
				try {
					//财付通对账(如果reconStatus为空则处理免对账交易,否则不执行)
					log.debug("start processRecon, finReconBankStatement:"+StringUtil.printParam(finReconBankStatement));
					processRecon(finReconBankStatement,StringUtils.isBlank(reconStatus));
				} catch (Exception e) {
					e.printStackTrace();
					log.error("Single data recon fail,finReconBankStatement:"+StringUtil.printParam(finReconBankStatement));
				}
			}
			//财付通对账后 如还有未对账状态的数据,则直接更新为我方有,财付通无匹配
			int noMatchingRows=finReconResultService.updateNoMatchingReconResult(GATEWAY_IN, accountDate,GATEWAY);
			log.info("update no matching data ,rows:"+noMatchingRows+",GATEWAY_IN:"+GATEWAY_IN+",date:"+DateUtil.formatDate(accountDate, "yyyy-MM-dd"));
		}
		return SUCCESS;
	}
	/**
	 * 对账逻辑
	 * @author zhangjie
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
	 * @author zhangjie
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
	 * @author zhangjie
	 * @param finReconBankStatement
	 */
	private void processPaymentRecon(FinReconBankStatement finReconBankStatement){
		List<PayPayment> payPaymentList=payPaymentService.selectByPaymentTradeNoAndGatewayPure(finReconBankStatement.getBankPaymentTradeNo(),GATEWAY_IN,true);
		if(payPaymentList.size()==0){
			recordNoMatchProcess(finReconBankStatement);
			return ;
		}
		else if(payPaymentList.size()==1){
			//根据payment_trade_no找到一条交易记录
			PayPayment payPayment=payPaymentList.get(0);
			
			FinReconResult finReconResult = finReconResultService.selectReconResultListByParas(payPayment.getPaymentTradeNo(),
					payPayment.getGatewayTradeNo(), Constant.TRANSACTION_TYPE.PAYMENT.name(), GATEWAY_IN,null,payPayment.getPaymentId()+"");
			
			//如果这笔交易已经成功则跳过，不做任何处理
			if(reconStatusIsSuccess(finReconResult)){
				return ;
			}
			
			finReconResult=ReconCommonUtil.setBankStatementCommonParam(finReconResult, finReconBankStatement);
			finReconResult=ReconCommonUtil.setPayPaymentCommonParam(finReconResult, payPayment,accountDate);
			
			
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
				finReconResult=ReconCommonUtil.setPayPaymentCommonParam(finReconResult, payPayment,accountDate);
				
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
	 * @author zhangjie
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
		finReconResult=ReconCommonUtil.setPayPaymentRefundCommonParam(finReconResult, payPaymentAndRefundment,accountDate);
		
		//处理网关交易号无法匹配的情况
		boolean gatewayTradeNoMatchFlag=gatewayTradeNoMatch(finReconBankStatement.getBankGatewayTradeNo(),payPaymentAndRefundment.getPayGatewayTradeNo(),finReconResult,"");
		if(!gatewayTradeNoMatchFlag){
			return ;
		}
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
	 * 处理财付通有,我方未找到交易记录
	 * @author zhangjie
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
	 * 判断这笔交易的勾兑结果是否已经为成功
	 * @author zhangjie
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
	 * 处理网关交易号无法匹配的情况
	 * @author zhangjie
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
	 * @author zhangjie
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
	 * @author zhangjie
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
	 * @author zhangjie
	 */
	private FinReconResult transactionTimeMatch(Date transactionTime,Date callbackTime,FinReconResult finReconResult,String resultPrefix){
		if(transactionTime==null || callbackTime==null || DateUtil.accurateToDay(transactionTime).compareTo(DateUtil.accurateToDay(callbackTime))!=0){
			finReconResult.setReconResult(resultPrefix+"对账成功,注意:交易日期不在同一天");
		}
		return finReconResult;
	}
	
	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
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

	public static void main(String[] ags){
		String data="spid=1216574701&trans_time=2013-07-15&stamp="+Long.toString(System.currentTimeMillis()/1000)+"&cft_signtype=0&mchtype=0&key=5977d7ae78e897aa2d34cec70ccf0215";
		
		String sign=MD5.md5(data);
		
		data+="&sign="+sign;
		

		try {
			String result = HttpsUtil.requestPostData("http://mch.tenpay.com/cgi-bin/mchdown_real_new.cgi", data, "application/x-www-form-urlencoded", "GBK").getResponseString("GBK");
			TenpayAccountResponseBean tenpayAccountResponseBean = TenpayAccountResponseBean.parseResponseBean(result);
			System.out.println(tenpayAccountResponseBean.isSuccessAccount()+"--------------------");
			System.out.println(tenpayAccountResponseBean.getRefundAmount()+"--------------------");
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
}
