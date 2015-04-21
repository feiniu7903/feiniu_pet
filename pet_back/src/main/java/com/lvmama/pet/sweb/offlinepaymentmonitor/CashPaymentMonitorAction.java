package com.lvmama.pet.sweb.offlinepaymentmonitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pay.FinReconResult;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentDetail;
import com.lvmama.comm.pet.po.pay.PayPos;
import com.lvmama.comm.pet.po.pay.PayReceivingBank;
import com.lvmama.comm.pet.po.pay.PayReceivingCompany;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.pay.FinReconResultService;
import com.lvmama.comm.pet.service.pay.PayPaymentDetailService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.pay.PayPosService;
import com.lvmama.comm.pet.service.pay.PayReceivingBankService;
import com.lvmama.comm.pet.service.pay.PayReceivingCompanyService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.vo.CashPaymentComboVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.PAYMENT_DETAIL_CASH_AUDIT_STATUS;



public class CashPaymentMonitorAction extends BackBaseAction {
	
	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = -3501607363664006478L;
	/**
	 * 页面参数传递
	 */
	private Map<String,String> paramMap=new HashMap<String,String>();
	/**
	 * 现金审核状态对象
	 */
	private List<PAYMENT_DETAIL_CASH_AUDIT_STATUS> cashAuditStatusList=new ArrayList<PAYMENT_DETAIL_CASH_AUDIT_STATUS>();
	/**
	 * 收款公司对象
	 */
	private List<PayReceivingCompany> payReceivingCompanyList=new ArrayList<PayReceivingCompany>();
	/**
	 * 收款银行户名对象
	 */
	private List<String> receivingAccountList=new ArrayList<String>();
	/**
	 * 收款银行名称对象
	 */
	private List<String> bankNameList=new ArrayList<String>();
	/**
	 * 收款银行类型对象
	 */
	private List<PayReceivingBank> receivingAccountTypeList=new ArrayList<PayReceivingBank>();
	/**
	 * 收款银行
	 */
	private PayReceivingBank payReceivingBank;
	/**
	 * 银行卡号
	 */
	private String bankCardNo;
	/**
	 * 收款公司接口
	 */
	private PayReceivingCompanyService payReceivingCompanyService;
	/**
	 * 收款银行接口
	 */
	private PayReceivingBankService payReceivingBankService;
	/**
	 * 对账结果接口
	 */
	private FinReconResultService finReconResultService;
	/**
	 * super系统用户
	 */
	private PermUserService permUserService;
	/**
	 * POS支付接口
	 */
	private PayPosService payPosService;
	/**
	 * 自动完成联想搜索关键字
	 */
	private String search;
	/**
	 * 现金支付联合对象
	 */
	private List<CashPaymentComboVO> cashPaymentComboVOList;
	/**
	 * 现金支付联合对象
	 */
	private CashPaymentComboVO cashPaymentComboVO;
	/**
	 * 支付接口
	 */
	private PayPaymentService payPaymentService;
	/**
	 * 总支付金额
	 */
	private Long paymentAmountSum=0L;
	/**
	 * 已审核通过总金额
	 */
	private Long auditPassAmountSum=0L;
	
	/**
	 * 已解款总金额
	 */
	private Long liberateAmountSum=0L;
	
	/**
	 * 支付扩展接口
	 */
	private PayPaymentDetailService payPaymentDetailService;
	/**
	 * 日志类型
	 */
	private static String LOG_TYPE="CASH_PAYMENT_MONITOR";
	/**
	 * 日志接口
	 */
	protected ComLogService comLogRemoteService;
	/**
	 * 订单号-批量
	 */
	private String objectIds="";
	/**
	 * 支付ID-批量
	 */
	private String paymentIds;
	/**
	 * 支付总金额-批量
	 */
	private Long paymentsAmountSum=0L; 
	
	/**
	 * 初始化查询页面
	 * @author ZHANG Nan
	 * @return
	 */
	public String load(){
		initData();
		return SUCCESS;
	}
	/**
	 * super系统用户自动完成查询
	 * @author ZHANG Nan
	 */
	public void permUserAutoComplete(){
		JSONArray array=new JSONArray();
		List<PermUser> permUserList=permUserService.selectListByUserNameOrRealName(search);
		for (PermUser permUser : permUserList) {
			JSONObject obj=new JSONObject();
			obj.put("id", permUser.getUserName());
			obj.put("text", permUser.getRealName()+"("+permUser.getUserName()+")");
			array.add(obj);
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	/**
	 * POS机自动完成查询
	 * @author ZHANG Nan
	 */
	public void payPosAutoComplete(){
		JSONArray array=new JSONArray();
		List<PayPos> payPosList=payPosService.selectLikeTerminalNo(search);
		for (PayPos payPos : payPosList) {
			JSONObject obj=new JSONObject();
			obj.put("id", payPos.getTerminalNo());
			obj.put("text", payPos.getTerminalNo()+"("+payPos.getMemo()+")");
			array.add(obj);
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	/**
	 * 页面LIST查询
	 * @author ZHANG Nan
	 * @return
	 */
	public String query() {
		initData();
		pagination=initPage();
		paramMap.put("start", String.valueOf(pagination.getStartRows()));
		paramMap.put("end", String.valueOf(pagination.getEndRows()));
		Long cashPaymentComboVOListCount=payPaymentService.selectPayPaymentAndDetailByParamMapCount(paramMap);
		if(cashPaymentComboVOListCount!=null && cashPaymentComboVOListCount>0){
			cashPaymentComboVOList= payPaymentService.selectPayPaymentAndDetailByParamMap(paramMap);
			
			Map<String,Long> amountMap=payPaymentService.selectPayPaymentAuditAmountByParamMap(paramMap);
			if(amountMap!=null){
				paymentAmountSum=amountMap.get("PAYMENTAMOUNTSUM");
				auditPassAmountSum=amountMap.get("AUDITPASSAMOUNTSUM");
				liberateAmountSum=amountMap.get("LIBERATEAMOUNTSUM");
				
			}
		}
		pagination.setTotalResultSize(cashPaymentComboVOListCount);
		pagination.buildUrl(getRequest());
		return SUCCESS;
	}
	/**
	 * 初始化基本数据
	 * @author ZHANG Nan
	 */
	public void initData(){
		cashAuditStatusList.add(PAYMENT_DETAIL_CASH_AUDIT_STATUS.UNLIBERATED);
		cashAuditStatusList.add(PAYMENT_DETAIL_CASH_AUDIT_STATUS.LIBERATE);
		cashAuditStatusList.add(PAYMENT_DETAIL_CASH_AUDIT_STATUS.VERIFIED);
		
		Map<String,String> payReceivingCompanyParamMap=new HashMap<String,String>();
		payReceivingCompanyParamMap.put("status", "ENABLE");
		payReceivingCompanyParamMap.put("orderby", "CREATE_TIME");
		payReceivingCompanyList=payReceivingCompanyService.selectPayReceivingCompanyByParamMap(payReceivingCompanyParamMap);
	}
	/**
	 * 打开解款页面
	 * @author ZHANG Nan
	 * @return
	 */
	public String openLiberatePay(){
		cashPaymentComboVOList= payPaymentService.selectPayPaymentAndDetailByParamMap(paramMap);
		if(cashPaymentComboVOList!=null && cashPaymentComboVOList.size()>0){
			cashPaymentComboVO=cashPaymentComboVOList.get(0);
		}
		
		receivingAccountList=payReceivingBankService.selectEnableReceivingAccount();
		if(receivingAccountList!=null && receivingAccountList.size()>0){
			Map<String,String> paramMap=new HashMap<String,String>();
			paramMap.put("receivingAccount", receivingAccountProcess(cashPaymentComboVO, receivingAccountList));
			bankNameList=payReceivingBankService.selectBankName(paramMap.get("receivingAccount"));
			if(bankNameList!=null && bankNameList.size()>0){
				paramMap.put("bankName",bankNameProcess(cashPaymentComboVO, bankNameList));
				receivingAccountTypeList=payReceivingBankService.selectPayReceivingBankByParamMap(paramMap);
				if(receivingAccountTypeList!=null && receivingAccountTypeList.size()>0){
					bankCardNo=receivingAccountTypeProcess(cashPaymentComboVO, receivingAccountTypeList);
				}
			}
		}
		return "liberate";
	}
	public String view(){
		cashPaymentComboVOList= payPaymentService.selectPayPaymentAndDetailByParamMap(paramMap);
		if(cashPaymentComboVOList!=null && cashPaymentComboVOList.size()>0){
			cashPaymentComboVO=cashPaymentComboVOList.get(0);
		}
		return "view";
	}
	
	/**
	 * 打开批量解款页面
	 * @author ZHANG Nan
	 * @return
	 */
	public String openLiberatePayBatch(){
		paymentIds=paramMap.get("paymentIds");
		cashPaymentComboVOList= payPaymentService.selectPayPaymentAndDetailByParamMap(paramMap);
		for (CashPaymentComboVO cashPaymentComboVO : cashPaymentComboVOList) {
			objectIds+=cashPaymentComboVO.getObjectId()+" ";
			paymentsAmountSum+=cashPaymentComboVO.getAmount();
		}
		
		receivingAccountList=payReceivingBankService.selectEnableReceivingAccount();
		if(receivingAccountList!=null && receivingAccountList.size()>0){
			Map<String,String> paramMap=new HashMap<String,String>();
			paramMap.put("receivingAccount", receivingAccountProcess(cashPaymentComboVO, receivingAccountList));
			bankNameList=payReceivingBankService.selectBankName(paramMap.get("receivingAccount"));
			if(bankNameList!=null && bankNameList.size()>0){
				paramMap.put("bankName",bankNameProcess(cashPaymentComboVO, bankNameList));
				receivingAccountTypeList=payReceivingBankService.selectPayReceivingBankByParamMap(paramMap);
				if(receivingAccountTypeList!=null && receivingAccountTypeList.size()>0){
					bankCardNo=receivingAccountTypeProcess(cashPaymentComboVO, receivingAccountTypeList);
				}
			}
		}
		return "liberate_batch";
	}
	/**
	 * 收款户名处理
	 * @author ZHANG Nan
	 * @param cashPaymentComboVO
	 * @param receivingAccountList
	 * @return
	 */
	private String receivingAccountProcess(CashPaymentComboVO cashPaymentComboVO,List<String> receivingAccountList){	
		if(cashPaymentComboVO!=null && StringUtils.isNotBlank(cashPaymentComboVO.getReceivingAccount())){
			for (String receivingAccount : receivingAccountList) {
				if(receivingAccount.equals(cashPaymentComboVO.getReceivingAccount())){
					return receivingAccount;
				}
			}
		}
		return receivingAccountList.get(0);
	}
	/**
	 * 收款银行名称处理
	 * @author ZHANG Nan
	 * @param cashPaymentComboVO
	 * @param bankNameList
	 * @return
	 */
	private String bankNameProcess(CashPaymentComboVO cashPaymentComboVO,List<String> bankNameList){	
		if(cashPaymentComboVO!=null && StringUtils.isNotBlank(cashPaymentComboVO.getBankName())){
			for (String bankName: bankNameList) {
				if(bankName.equals(cashPaymentComboVO.getBankName())){
					return bankName;
				}
			}
		}
		return bankNameList.get(0);
	}
	/**
	 * 收款银行类型处理
	 * @author ZHANG Nan
	 * @param cashPaymentComboVO
	 * @param receivingAccountTypeList
	 * @return
	 */
	private String receivingAccountTypeProcess(CashPaymentComboVO cashPaymentComboVO,List<PayReceivingBank> receivingAccountTypeList){
		if(cashPaymentComboVO!=null && StringUtils.isNotBlank(cashPaymentComboVO.getBankCardNo())){
			for (PayReceivingBank payReceivingBanks : receivingAccountTypeList) {
				if(payReceivingBanks.getBankCardNo().equals(cashPaymentComboVO.getBankCardNo())){
					return payReceivingBanks.getBankCardNo();
				}
			}
		}
		return receivingAccountTypeList.get(0).getBankCardNo();
	}
	/**
	 * 打开现金交接页面
	 * @author ZHANG Nan
	 * @return
	 */
	public String openCashHandover(){
		cashPaymentComboVOList= payPaymentService.selectPayPaymentAndDetailByParamMap(paramMap);
		if(cashPaymentComboVOList!=null && cashPaymentComboVOList.size()>0){
			cashPaymentComboVO=cashPaymentComboVOList.get(0);
		}
		return "handover";
	}
	/**
	 * 载入银行名称
	 * @author ZHANG Nan
	 */
	public void loadBankNames(){
		bankNameList=payReceivingBankService.selectBankName(payReceivingBank.getReceivingAccount());
		JSONArray array=new JSONArray();
		for (String bankName: bankNameList) {
			JSONObject obj=new JSONObject();
			obj.put("value", bankName);
			array.add(obj);	
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	/**
	 * 载入银行类型
	 * @author ZHANG Nan
	 */
	public void receivingAccountTypes(){
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("receivingAccount", payReceivingBank.getReceivingAccount());
		paramMap.put("bankName", payReceivingBank.getBankName());
		
		receivingAccountTypeList=payReceivingBankService.selectPayReceivingBankByParamMap(paramMap);
		JSONArray array=new JSONArray();
		for (PayReceivingBank payReceivingBank : receivingAccountTypeList) {
			JSONObject obj=new JSONObject();
			obj.put("id", payReceivingBank.getBankCardNo());
			obj.put("value", payReceivingBank.getReceivingAccountType());
			array.add(obj);	
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	/**
	 * 解款
	 * @author ZHANG Nan
	 * @throws IOException
	 */
	public void liberate() throws IOException{
		boolean isModify=Constant.PAYMENT_DETAIL_CASH_AUDIT_STATUS.LIBERATE.name().equals(cashPaymentComboVO.getCashAuditStatus())?true:false;
		try {
			PayPayment payPayment=payPaymentService.selectByPaymentId(cashPaymentComboVO.getPaymentId());
			payPayment.setGatewayTradeNo(cashPaymentComboVO.getGatewayTradeNo());
			PayPaymentDetail payPaymentDetail=payPaymentDetailService.selectPaymentDetailByPaymentId(cashPaymentComboVO.getPaymentId()+"");
			payPaymentDetail.setCashLiberateMoneyPerson(getSessionUserName());
			payPaymentDetail.setCashLiberateMoneyDate(cashPaymentComboVO.getCashLiberateMoneyDate());
			payPaymentDetail.setCashAuditStatus(Constant.PAYMENT_DETAIL_CASH_AUDIT_STATUS.LIBERATE.name());
			payReceivingBank=payReceivingBankService.selectReceivingBankByBankCardNo(payReceivingBank.getBankCardNo());
			payPaymentDetail.setReceivingBankId(payReceivingBank.getReceivingBankId());
			payPaymentService.updatePaymentAndDetail(payPayment, payPaymentDetail);
			
			
			String successMessage=isModify?"修改解款单成功!":"解款成功!";
			comLogRemoteService.insert(LOG_TYPE, null, cashPaymentComboVO.getPaymentId(), getSessionUserName(), null, null, successMessage, null);
			this.getResponse().getWriter().write("{result:'"+successMessage+"!'}");
			
			/*
			 * 对相应的勾兑数据做处理
			 * 1，在现金收款监控中，当该笔订单是未解款状态时，订单状态为“已支付”。对账结果为“勾兑失败”，对账状态为“现金收款监控中该订单未解款”
			 * 2，在现金收款监控中，当该笔订单是已解款状态时，勾兑结果为“勾兑失败”，勾兑状态为“现金收款监控中该订单已解款但未审核”。
			 */
			if(("SAND_POS_CASH".equals(payPayment.getPaymentGateway())) || 
					("COMM_POS_CASH".equals(payPayment.getPaymentGateway()))){
				
				Map<String,String> paramMap=new HashMap<String,String>();
				paramMap.put("paymentTradeNo", payPayment.getPaymentTradeNo());
				paramMap.put("transactionType", Constant.TRANSACTION_TYPE.PAYMENT.name());
				paramMap.put("gateway", payPayment.getPaymentGateway());
				
				List<FinReconResult> finReconResultList=finReconResultService.selectReconResultListByParas(paramMap);
				  
				if(finReconResultList!=null && finReconResultList.size()>0){
					FinReconResult finReconResult = finReconResultList.get(0);
					finReconResult.setReconStatus(Constant.RECON_STATUS.FAIL.name());
					finReconResult.setReconResult("现金收款监控中该订单已解款但未审核");
					finReconResultService.update(finReconResult);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String errorMessage=isModify?"修改解款单失败!":"解款失败!";
			comLogRemoteService.insert(LOG_TYPE, null, cashPaymentComboVO.getPaymentId(), getSessionUserName(), null, null, errorMessage, null);
			this.getResponse().getWriter().write("{result:'"+errorMessage+"'}");
		}
	}
	/**
	 * 解款-批量
	 * @author ZHANG Nan
	 * @throws IOException
	 */
	public void liberateBatch() throws IOException{
		String tempPaymentId="";
		try {
			if(paymentIds!=null){
				String paymentIdArray[]=paymentIds.toString().split(",");
				for (String paymentId: paymentIdArray) {
					tempPaymentId=paymentId.replaceAll("'", "");
					PayPayment payPayment=payPaymentService.selectByPaymentId(Long.valueOf(tempPaymentId));
					payPayment.setGatewayTradeNo(cashPaymentComboVO.getGatewayTradeNo());
					PayPaymentDetail payPaymentDetail=payPaymentDetailService.selectPaymentDetailByPaymentId(tempPaymentId);
					payPaymentDetail.setCashLiberateMoneyPerson(getSessionUserName());
					payPaymentDetail.setCashLiberateMoneyDate(cashPaymentComboVO.getCashLiberateMoneyDate());
					payPaymentDetail.setCashAuditStatus(Constant.PAYMENT_DETAIL_CASH_AUDIT_STATUS.LIBERATE.name());
					payReceivingBank=payReceivingBankService.selectReceivingBankByBankCardNo(payReceivingBank.getBankCardNo());
					payPaymentDetail.setReceivingBankId(payReceivingBank.getReceivingBankId());
					payPaymentService.updatePaymentAndDetail(payPayment, payPaymentDetail);
					comLogRemoteService.insert(LOG_TYPE, null, Long.valueOf(tempPaymentId), getSessionUserName(), null, null, "批量解款成功", null);
					
					//批量勾兑
					if(("SAND_POS_CASH".equals(payPayment.getPaymentGateway())) || 
							("COMM_POS_CASH".equals(payPayment.getPaymentGateway()))){
						
						Map<String,String> paramMap=new HashMap<String,String>();
						paramMap.put("paymentTradeNo", payPayment.getPaymentTradeNo());
						paramMap.put("transactionType", Constant.TRANSACTION_TYPE.PAYMENT.name());
						paramMap.put("gateway", payPayment.getPaymentGateway());
						
						List<FinReconResult> finReconResultList=finReconResultService.selectReconResultListByParas(paramMap);
						  
						if(finReconResultList!=null && finReconResultList.size()>0){
							FinReconResult finReconResult = finReconResultList.get(0);
							finReconResult.setReconStatus(Constant.RECON_STATUS.FAIL.name());
							finReconResult.setReconResult("现金收款监控中该订单已解款但未审核");
							finReconResultService.update(finReconResult);
						}
					}
					
				}
			}
			this.getResponse().getWriter().write("{result:'批量解款成功!'}");
		} catch (Exception e) {
			e.printStackTrace();
			String errorMessage="批量解款失败";
			comLogRemoteService.insert(LOG_TYPE, null, Long.valueOf(tempPaymentId), getSessionUserName(), null, null, errorMessage, null);
			this.getResponse().getWriter().write("{result:'"+errorMessage+"'}");
		}
	}
	/**
	 * 现金交接
	 * @author ZHANG Nan
	 * @throws IOException
	 */
	public void handover() throws IOException{
		try {
			PayPaymentDetail payPaymentDetail=payPaymentDetailService.selectPaymentDetailByPK(cashPaymentComboVO.getPaymentDetailId());
			payPaymentDetail.setReceivingPerson(cashPaymentComboVO.getReceivingPerson());
			payPaymentDetailService.updatePayPaymentDetail(payPaymentDetail);
			comLogRemoteService.insert(LOG_TYPE, null, cashPaymentComboVO.getPaymentId(), getSessionUserName(), null, null, "现金交接成功,交接人:"+paramMap.get("oldReceivingPerson")+" 交接给:"+cashPaymentComboVO.getReceivingPerson(), null);
			this.getResponse().getWriter().write("{result:'现金交接成功!'}");
		} catch (Exception e) {
			e.printStackTrace();
			comLogRemoteService.insert(LOG_TYPE, null, cashPaymentComboVO.getPaymentId(), getSessionUserName(), null, null, "现金交接失败,交接人:"+paramMap.get("oldReceivingPerson")+" 交接给:"+cashPaymentComboVO.getReceivingPerson(), null);
			this.getResponse().getWriter().write("{result:'现金交接失败!'}");
		}
	}
	/**
	 * 打开审核页面
	 * @author ZHANG Nan
	 * @return
	 */
	public String openAudit(){
		cashPaymentComboVOList= payPaymentService.selectPayPaymentAndDetailByParamMap(paramMap);
		if(cashPaymentComboVOList!=null && cashPaymentComboVOList.size()>0){
			cashPaymentComboVO=cashPaymentComboVOList.get(0);
		}
		return "audit";
	}
	/**
	 * 审核
	 * @author ZHANG Nan
	 * @throws IOException
	 */
	public void audit() throws IOException{
		try {
			PayPaymentDetail payPaymentDetail=payPaymentDetailService.selectPaymentDetailByPK(cashPaymentComboVO.getPaymentDetailId());
			payPaymentDetail.setAuditPerson(getSessionUserName());
			payPaymentDetail.setCashAuditStatus(Constant.PAYMENT_DETAIL_CASH_AUDIT_STATUS.VERIFIED.name());
			payPaymentDetailService.updatePayPaymentDetail(payPaymentDetail);
			comLogRemoteService.insert(LOG_TYPE, null, cashPaymentComboVO.getPaymentId(), getSessionUserName(), null, null, "审核通过", null);
			this.getResponse().getWriter().write("{result:'审核成功!'}");
			
			/*
			 * 对相应的勾兑数据做处理
			 * 1，在现金收款监控中，当该笔订单是未解款状态时，订单状态为“已支付”。对账结果为“勾兑失败”，对账状态为“现金收款监控中该订单未解款”
			 * 2，在现金收款监控中，当该笔订单是已解款状态时，勾兑结果为“勾兑失败”，勾兑状态为“现金收款监控中该订单已解款但未审核”。
			 */
			PayPayment payPayment=payPaymentService.selectByPaymentId(cashPaymentComboVO.getPaymentId());
			
			if(("SAND_POS_CASH".equals(payPayment.getPaymentGateway())) || 
					("COMM_POS_CASH".equals(payPayment.getPaymentGateway()))){
				
				Map<String,String> paramMap=new HashMap<String,String>();
				paramMap.put("paymentTradeNo", payPayment.getPaymentTradeNo());
				paramMap.put("transactionType", Constant.TRANSACTION_TYPE.PAYMENT.name());
				paramMap.put("gateway", payPayment.getPaymentGateway());
				
				List<FinReconResult> finReconResultList=finReconResultService.selectReconResultListByParas(paramMap);
				  
				if(finReconResultList!=null && finReconResultList.size()>0){
					FinReconResult finReconResult = finReconResultList.get(0);
					finReconResult.setReconStatus(Constant.RECON_STATUS.SUCCESS.name());
					finReconResult.setReconResult("对账成功");
					finReconResultService.update(finReconResult);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			comLogRemoteService.insert(LOG_TYPE, null, cashPaymentComboVO.getPaymentId(), getSessionUserName(), null, null, "审核失败", null);
			this.getResponse().getWriter().write("{result:'审核失败!'}");
		}
	}
	
	/**
	 * 导出Excel
	 * @author ZHANG Nan
	 */
	public void exportExcel(){
		String templatePath = "/WEB-INF/resources/template/cash_payment_monitor.xls";
		String outputFile="cash_payment_monitor_"+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+".xls";
		cashPaymentComboVOList= payPaymentService.selectPayPaymentAndDetailByParamMap(paramMap);
		if(cashPaymentComboVOList!=null && cashPaymentComboVOList.size()>65535){
			//判断excel导出结果集是否超过65535行则提示错误
			HttpServletResponse response= getResponse();
			String fullContentType = "text/html;charset=UTF-8";
			response.setContentType(fullContentType);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			try {
				response.getWriter().write("导出的记录数超过了65535行,请缩小导出范围!");
				response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ;
		}
		Map<String,Object> beans = new HashMap<String,Object>();
		beans.put("list", cashPaymentComboVOList);
		exportXLS(beans, templatePath,outputFile);
	}
	/**
	 * xls文件下载
	 * @author ZHANG Nan
	 * @param map
	 * @param path
	 * @param fileName
	 */
	public void exportXLS(Map<String, Object> map, String path,String fileName){
		try {
			File templateResource = ResourceUtil.getResourceFile(path);
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName =System.getProperty("java.io.tmpdir") + "/" + fileName + ".xls";
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, map, destFileName);
			HttpServletResponse response = getResponse();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);   
			File file = new File(destFileName);
			InputStream inputStream=new FileInputStream(destFileName);  
			if (file != null && file.exists()) {
				OutputStream os=response.getOutputStream();  
	            byte[] b=new byte[1024];  
	            int length;  
	            while((length=inputStream.read(b))>0){  
	                os.write(b,0,length);  
	            }  
	            inputStream.close();  
			} else {
				throw new RuntimeException("Download failed!  path:"+path+" fileName:"+fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	public List<PAYMENT_DETAIL_CASH_AUDIT_STATUS> getCashAuditStatusList() {
		return cashAuditStatusList;
	}

	public void setCashAuditStatusList(List<PAYMENT_DETAIL_CASH_AUDIT_STATUS> cashAuditStatusList) {
		this.cashAuditStatusList = cashAuditStatusList;
	}

	public List<PayReceivingCompany> getPayReceivingCompanyList() {
		return payReceivingCompanyList;
	}

	public void setPayReceivingCompanyList(List<PayReceivingCompany> payReceivingCompanyList) {
		this.payReceivingCompanyList = payReceivingCompanyList;
	}

	public PayReceivingCompanyService getPayReceivingCompanyService() {
		return payReceivingCompanyService;
	}

	public void setPayReceivingCompanyService(PayReceivingCompanyService payReceivingCompanyService) {
		this.payReceivingCompanyService = payReceivingCompanyService;
	}
	public PermUserService getPermUserService() {
		return permUserService;
	}
	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public PayPosService getPayPosService() {
		return payPosService;
	}
	public void setPayPosService(PayPosService payPosService) {
		this.payPosService = payPosService;
	}
	public PayPaymentService getPayPaymentService() {
		return payPaymentService;
	}
	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}
	public List<CashPaymentComboVO> getCashPaymentComboVOList() {
		return cashPaymentComboVOList;
	}
	public void setCashPaymentComboVOList(List<CashPaymentComboVO> cashPaymentComboVOList) {
		this.cashPaymentComboVOList = cashPaymentComboVOList;
	}
	public Long getPaymentAmountSum() {
		return paymentAmountSum;
	}
	public void setPaymentAmountSum(Long paymentAmountSum) {
		this.paymentAmountSum = paymentAmountSum;
	}
	public Long getAuditPassAmountSum() {
		return auditPassAmountSum;
	}
	public void setAuditPassAmountSum(Long auditPassAmountSum) {
		this.auditPassAmountSum = auditPassAmountSum;
	}
	public CashPaymentComboVO getCashPaymentComboVO() {
		return cashPaymentComboVO;
	}
	public void setCashPaymentComboVO(CashPaymentComboVO cashPaymentComboVO) {
		this.cashPaymentComboVO = cashPaymentComboVO;
	}
	public PayReceivingBankService getPayReceivingBankService() {
		return payReceivingBankService;
	}
	public void setPayReceivingBankService(PayReceivingBankService payReceivingBankService) {
		this.payReceivingBankService = payReceivingBankService;
	}
	public FinReconResultService getFinReconResultService() {
		return finReconResultService;
	}
	public void setFinReconResultService(FinReconResultService finReconResultService) {
		this.finReconResultService = finReconResultService;
	}
	public List<String> getReceivingAccountList() {
		return receivingAccountList;
	}
	public void setReceivingAccountList(List<String> receivingAccountList) {
		this.receivingAccountList = receivingAccountList;
	}
	public List<String> getBankNameList() {
		return bankNameList;
	}
	public void setBankNameList(List<String> bankNameList) {
		this.bankNameList = bankNameList;
	}
	public List<PayReceivingBank> getReceivingAccountTypeList() {
		return receivingAccountTypeList;
	}
	public void setReceivingAccountTypeList(List<PayReceivingBank> receivingAccountTypeList) {
		this.receivingAccountTypeList = receivingAccountTypeList;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public PayReceivingBank getPayReceivingBank() {
		return payReceivingBank;
	}
	public void setPayReceivingBank(PayReceivingBank payReceivingBank) {
		this.payReceivingBank = payReceivingBank;
	}
	public PayPaymentDetailService getPayPaymentDetailService() {
		return payPaymentDetailService;
	}
	public void setPayPaymentDetailService(PayPaymentDetailService payPaymentDetailService) {
		this.payPaymentDetailService = payPaymentDetailService;
	}
	public ComLogService getComLogRemoteService() {
		return comLogRemoteService;
	}
	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}
	public Long getPaymentsAmountSum() {
		return paymentsAmountSum;
	}
	public void setPaymentsAmountSum(Long paymentsAmountSum) {
		this.paymentsAmountSum = paymentsAmountSum;
	}
	public String getObjectIds() {
		return objectIds;
	}
	public void setObjectIds(String objectIds) {
		this.objectIds = objectIds;
	}
	public String getPaymentIds() {
		return paymentIds;
	}
	public void setPaymentIds(String paymentIds) {
		this.paymentIds = paymentIds;
	}
	public Long getLiberateAmountSum() {
		return liberateAmountSum;
	}
	public void setLiberateAmountSum(Long liberateAmountSum) {
		this.liberateAmountSum = liberateAmountSum;
	}
}
