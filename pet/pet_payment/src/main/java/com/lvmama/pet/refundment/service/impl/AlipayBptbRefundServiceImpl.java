package com.lvmama.pet.refundment.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import com.alipay.util.Md5Encrypt;
import com.lvmama.comm.pet.po.money.CashDraw;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.GeneralSequenceNo;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.HttpsUtil.HttpResponseWrapper;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.utils.AlipayUtil;

public class AlipayBptbRefundServiceImpl implements BankRefundmentService {
	
	protected transient final Log log = LogFactory.getLog(getClass());

	private CashAccountService cashAccountService;

	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		BankReturnInfo returnInfo = new BankReturnInfo();
		HttpResponseWrapper httpResponseWrapper = null;
		try{
			PaymentConstant pc = PaymentConstant.getInstance();
			String targetURL = pc.getProperty("ALIPAY_URL");
			log.info("DrawCash2Alipay targetURL = " + targetURL);
			
			CashMoneyDraw cashMoneyDraw = cashAccountService.queryCashMoneyDraw(info.getObjectId());
			Map<String,Object> result = generateCSVFile(cashMoneyDraw);
			if ("true".equals(result.get("success"))) {
//				HttpClient httpclient = HttpsUtil.createHttpClient();
				Map<String, String> params = this.initParameters();
				File file = (File)result.get("targetFile");
//				MultipartEntity reqEntity = buildRequestPartArray(params, file );
//				HttpPost httppost = new HttpPost(targetURL);
//				httppost.setEntity(reqEntity);
//				HttpResponse response = httpclient.execute(httppost);
//				int statusCode = response.getStatusLine().getStatusCode();
				Map<String, File> requestFiles = new HashMap<String, File>();
				requestFiles.put("bptb_pay_file", file);
				httpResponseWrapper = HttpsUtil.requestPostUpload(targetURL, requestFiles, params);
				int statusCode = httpResponseWrapper.getStatusCode();
				if(statusCode == HttpStatus.SC_OK) {
					//HttpEntity resEntity = response.getEntity();  
					//String respTxt = EntityUtils.toString(resEntity);
					String respTxt = httpResponseWrapper.getResponseString();
					String batchNo = result.get("batch_no").toString();
					CashDraw cashDraw = createCashDraw(respTxt, cashMoneyDraw, file, batchNo);
					cashAccountService.withDrawMoney(cashMoneyDraw, cashDraw);
					returnInfo.setSerial(batchNo);
					if ( "文件上传成功!".equalsIgnoreCase(respTxt) ) {
						returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.PROCESSING.name());
						returnInfo.setCodeInfo("现金账户提现处理中");
					}else{
						returnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
						returnInfo.setCodeInfo(respTxt);
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			httpResponseWrapper.close();
		}
		return returnInfo;
	}
	

	private Map<String, Object> generateCSVFile(CashMoneyDraw cashMoneyDraw) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", "false");
		try {
			PaymentConstant pc = PaymentConstant.getInstance();
			String timestamp = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss");
			//前缀加入D 用于区分普通提现和批量付款到卡(对账时用于类型判断)
			String serialNo = Constant.ALIPAY_TRANSACTION_TYPE_PREFIX.D.name()+GeneralSequenceNo.generateSerialNo();
			String bankAccount = cashMoneyDraw.getBankAccount().trim();
			String bankAccountName = cashMoneyDraw.getBankAccountName().trim();
			String bankName = cashMoneyDraw.getBankName().trim();
			String province = cashMoneyDraw.getProvince()==null?"":cashMoneyDraw.getProvince().trim();
			String city = cashMoneyDraw.getCity()==null?"":cashMoneyDraw.getCity().trim();
			String kaiHuHang = cashMoneyDraw.getKaiHuHang()==null?"":cashMoneyDraw.getKaiHuHang().trim();
			float amount = PriceUtil.convertToYuan(cashMoneyDraw.getDrawAmount().longValue());
			String transAmt = new DecimalFormat("#################.00").format(amount);
			String flag = cashMoneyDraw.getFlag();//1：对公 2：对私
			if(StringUtils.isBlank(flag)){
				flag="2";
			}
			String memo = "";
			StringBuffer sb = new StringBuffer();
			sb.append("日期,总金额,总笔数,支付宝帐号(Email)\r\n");
			sb.append(timestamp.substring(0, 8) + "," + transAmt + ",1," + pc.getProperty("ALIPAY_SELLER_EMAIL") + "\r\n");
			sb.append("商户流水号,收款银行户名,收款银行帐号,收款开户银行,收款银行所在省份,收款银行所在市,收款支行名称,金额,对公对私标志,备注\r\n");
			sb.append(serialNo + "," + bankAccountName + "," + bankAccount + "," 
					+ bankName + "," + province + "," + city + "," + kaiHuHang + "," 
					+ transAmt + "," + flag + "," + memo + "\r\n");
			String content = sb.toString();
			
			File filePath = new File(pc.getProperty("ALIPAY_DRAW_MONEY_UPLOAD_FILE_PATH") + timestamp.substring(0, 8));
			if(!filePath.exists()) {
				filePath.mkdirs();
			}
			String fileName = "lvmama_" + timestamp + "_" + cashMoneyDraw.getMoneyDrawId() + ".csv";
			File targetFile = new File(filePath, fileName.toLowerCase());
			targetFile.createNewFile();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(targetFile), pc.getProperty("ALIPAY_CHARSET")));
			pw.print(content);
			pw.flush();
			pw.close();
			//fileDigest = Md5Encrypt.md5(content);
			log.info("generate csv file success: " + targetFile.getAbsolutePath());
			map.put("success", "true");
			map.put("batch_no", serialNo);
			map.put("targetFile", targetFile);
			return map;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	private MultipartEntity buildRequestPartArray(Map<String, String> params, File file) throws Exception {
		PaymentConstant pc = PaymentConstant.getInstance();
		//String charset = pc.getProperty("ALIPAY_CHARSET");
		
		List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        MultipartEntity entity = new MultipartEntity();
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = params.get(key);
			entity.addPart(key, new StringBody(value));
		}
		entity.addPart("bptb_pay_file", new FileBody(file));
		return entity;
	}
	
	private Map<String, String> initParameters() {
		PaymentConstant pc = PaymentConstant.getInstance();
        //基本信息参数
		String key = pc.getProperty("ALIPAY_KEY"); // 私钥值。
        Map<String,String> params = new HashMap<String,String>();
        params.put("service","bptb_pay_file");
        params.put("partner",pc.getProperty("ALIPAY_PARTNER"));
        params.put("_input_charset","GBK");
//        params.put("digest_bptb_pay_file", digest); // 文件摘要，可为空
        params.put("file_digest_type", "MD5");
//        params.put("biz_type", "d_sale"); // 业务类型，可为空
        params.put("bussiness_type", pc.getProperty("ALIPAY_BUSINESS_TYPE")); // 批量代发类型，可为空，T0/T1

        String signString = AlipayUtil.getContent(params, key);
        log.info("DrawMoney2Bank sign string: " + signString);
        String sign = Md5Encrypt.md5(signString);
        log.info("DrawMoney2Bank sign: " + sign);

        params.put("sign",sign);
        params.put("sign_type","MD5");

        return params;
	}
	
	private CashDraw createCashDraw(String info, CashMoneyDraw cashMoneyDraw, File targetFile, String batchNo) {
		CashDraw fincCashDraw = new CashDraw();
		fincCashDraw.setAlipay2bankFile(targetFile.getName());
		fincCashDraw.setAmount(cashMoneyDraw.getDrawAmount());
		fincCashDraw.setCreateTime( new Date() );
		fincCashDraw.setMoneyDrawId(cashMoneyDraw.getMoneyDrawId());
		fincCashDraw.setPaymentGateway(Constant.PAYMENT_GATEWAY.ALIPAY.name());
		fincCashDraw.setSerial(batchNo);
		fincCashDraw.setOperatorName("SYSTEM");
		if ( "文件上传成功!".equalsIgnoreCase(info) ) {
			fincCashDraw.setStatus(Constant.PAYMENT_SERIAL_STATUS.CREATE.name());
			cashMoneyDraw.setPayStatus(Constant.FINC_CASH_STATUS.ApplyPayCashSuccess.name());
		} else {
			fincCashDraw.setStatus(Constant.PAYMENT_SERIAL_STATUS.FAIL.name());
			fincCashDraw.setCallbackInfo(info);
			cashMoneyDraw.setPayStatus(Constant.FINC_CASH_STATUS.ApplyPayCashRejected.name());
		}
		return fincCashDraw;
	}
	
	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}
}
