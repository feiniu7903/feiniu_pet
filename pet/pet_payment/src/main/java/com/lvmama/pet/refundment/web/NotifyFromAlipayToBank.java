package com.lvmama.pet.refundment.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alipay.util.Md5Encrypt;
import com.lvmama.comm.pet.po.money.CashDraw;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.HttpsUtil.HttpResponseWrapper;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.AlipayUtil;

public class NotifyFromAlipayToBank extends AbstractAlipayNotify implements NotifyDrawMoney {
	
	public NotifyFromAlipayToBank(Map map) {
		super.requestPrameterMap = map;
		init();
	}
	
	@Override
	public boolean checkSignature() {
    	PaymentConstant pc = PaymentConstant.getInstance();
		String sign = info.get("sign");
		String signString = getSignString(pc.getProperty("ALIPAY_KEY"));
		log.info("async Alipay ALIPAY_KEY = " + pc.getProperty("ALIPAY_KEY"));
		log.info("async Alipay sign = " + sign);
		log.info("async Alipay mysign = " + signString);
		if(Md5Encrypt.md5(signString).equals(sign)) {
			return true;
		}else{
			return false;
		}
	}
	
    @Override
	public boolean process() throws Exception {
    	String fileName = info.get("file_name");
    	String notifyType = info.get("notify_type");
    	
    	if(Constant.ALIPAY_NOTIFY_TYPE.bptb_unfreeze_notify.name().equalsIgnoreCase(notifyType)) {
			CashDraw fincCashDraw = cashAccountService.findCashDrawByAlipay2bankFile(fileName);
			return callbackForFailed(fincCashDraw, Constant.ALIPAY_NOTIFY_TYPE.bptb_unfreeze_notify.name(), "批次余额不足");
		}
    	
    	if(Constant.ALIPAY_NOTIFY_TYPE.bptb_result_notify.name().equalsIgnoreCase(notifyType)) {
			File resultFile = downloadResultFilie(fileName);
			if (resultFile!=null && resultFile.exists() && resultFile.length()>1024) {//此文件不应该过小，否则可能是下载失败。
				return this.processResultFile(resultFile);
			}
		}
		return false;
	}
	
    /**
     * 打款到银行结果的查询接口
     * @param alipay2BankFileName
     * @return File
     * @throws IOException
     */
    private File downloadResultFilie(String alipay2BankFileName) throws IOException {
    	PaymentConstant pc = PaymentConstant.getInstance();
		String dir = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
		String parentPathName = pc.getProperty("ALIPAY_DRAW_MONEY_DOWNLOAD_FILE_PATH") + "/" + dir;
		File parentPath = new File(parentPathName);
		if(!parentPath.exists()) {
			parentPath.mkdirs();
		}
		String time = DateUtil.getFormatDate(new Date(), "HHmmss");
		String zipFileName = "Alipay_" + alipay2BankFileName + "_" + time + ".zip";	// 下载文件
		File zipFile = new File(parentPathName, zipFileName);
		String targetURL = pc.getProperty("ALIPAY_URL");
//		HttpClient client = new HttpClient();
//		client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
//		client.getHttpConnectionManager().getParams().setSoTimeout(30000);
//		PostMethod postMethod = new PostMethod(targetURL);
//		postMethod.setRequestBody(buildRequestParameters(alipay2BankFileName));
//		client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
//		int code = client.executeMethod(postMethod);
		HttpResponseWrapper httpResponseWrapper = HttpsUtil.requestPostFormResponse(targetURL, createRequestParameters(alipay2BankFileName));
		int code = httpResponseWrapper.getStatusCode();
		if (code == HttpStatus.SC_OK) {
			//byte[] body = postMethod.getResponseBody();
			FileOutputStream fos = new FileOutputStream(zipFile);
			//InputStream is = postMethod.getResponseBodyAsStream();
			InputStream is = httpResponseWrapper.getResponseStream();
			int bytes = 0;
			byte[] buf = new byte[1024];
			while ((bytes = is.read(buf)) != -1) {
				fos.write(buf, 0, bytes);
			}
			is.close();
			fos.flush();
			fos.close();
		}
		//postMethod.releaseConnection();
		httpResponseWrapper.close();
		return zipFile;
    }
	
	private NameValuePair[] buildRequestParameters(String alipay2BankFileName) {
		PaymentConstant pc = PaymentConstant.getInstance();
		String timestamp = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
        //基本信息参数
		String alipayKey = pc.getProperty("ALIPAY_KEY"); //私钥值。
        Map<String,String> params = new HashMap<String,String>();
        params.put("service","bptb_result_file");
        params.put("partner",pc.getProperty("ALIPAY_PARTNER"));
        params.put("_input_charset","utf-8");
        params.put("file_name", alipay2BankFileName);
        params.put("date", timestamp);
        //params.put("biz_type", "d_sale");
        String content = AlipayUtil.getContent(params, alipayKey);
        String sign = Md5Encrypt.md5(content);
        params.put("sign",sign);
        params.put("sign_type","MD5");
        
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
		NameValuePair[] requestParams = new NameValuePair[keys.size()];
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			requestParams[i] = new NameValuePair(key, value);
		}
		
        return requestParams;
	}
	
	private Map<String, String> createRequestParameters(String alipay2BankFileName) {
		PaymentConstant pc = PaymentConstant.getInstance();
		String timestamp = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
        //基本信息参数
		String alipayKey = pc.getProperty("ALIPAY_KEY"); //私钥值。
        Map<String,String> params = new HashMap<String,String>();
        params.put("service","bptb_result_file");
        params.put("partner",pc.getProperty("ALIPAY_PARTNER"));
        params.put("_input_charset","utf-8");
        params.put("file_name", alipay2BankFileName);
        params.put("date", timestamp);
        //params.put("biz_type", "d_sale");
        String content = AlipayUtil.getContent(params, alipayKey);
        String sign = Md5Encrypt.md5(content);
        params.put("sign",sign);
        params.put("sign_type","MD5");
        
        return params;
	}
	
    private boolean processResultFile(File zipFile) {
    	boolean flag = true;
    	try{
	    	PaymentConstant pc = PaymentConstant.getInstance();
	    	String charset = pc.getProperty("ALIPAY_CHARSET");
	    	ZipFile zip = new ZipFile(zipFile);
	    	Enumeration enums = zip.entries();
	    	while(enums.hasMoreElements()) {
	    		ZipEntry zipEntry = (ZipEntry)enums.nextElement();
	    		if (zipEntry.getName().matches("lvmama_.*csv")) {
	    			log.info(zipEntry.getName());
		    		int size = (int)zipEntry.getSize();
		    		char[] bufs = new char[size];
		    		InputStreamReader isr = new InputStreamReader(zip.getInputStream(zipEntry), charset);
		    		isr.read(bufs);
		    		isr.close();
		    		String content = new String(bufs).trim();
		    		flag = flag && processContent(content);
	    		}
	    	}
    	}catch(Exception e){
    		log.error("PROCESS FAILED: " + zipFile.getAbsolutePath());
    		e.printStackTrace();
    		return false;
    	}
    	return flag;
    }
	
    private boolean processContent(String content) {
    	boolean flag = true;
		String[] result = content.split("\n");
		if (result.length>=3) {
			String summary = result[1];
			String[] sumArr = summary.split(",");
			Date transDate = DateUtil.toDate(sumArr[1], "yyyyMMdd");
    		for(int i=3; i<result.length; i++) {
    			String[] detail = result[i].split(",");
    			String serialNo = detail[0];
    			String amountStr = detail[7];
				Long amount = new Long(Math.round(Float.parseFloat(amountStr) * 100));
				String status = detail[9];
				String tradeNo = detail[10];
				//String refundFlag = detail[11];		//使用alipay原路退回，暂时不用。
				String memo = detail[12];
				log.info("serialNo = " + serialNo + " amount = " + amount + " status = " 
						+ status + " tradeNo = " + tradeNo + " memo = " + memo 
						+ " transDate = " + transDate);
				CashDraw fincCashDraw = cashAccountService.findCashDrawBySerial(serialNo);
				if("S".equalsIgnoreCase(status)) {
					fincCashDraw.setGatewayTradeNo(tradeNo);
					fincCashDraw.setTransTime(transDate);
					flag = flag && callbackForSuccess(fincCashDraw);
				} else {
					flag = flag && callbackForFailed(fincCashDraw, status, memo);
				}
    		}
		}
		return flag;
    }
    
	/**
	 * 生成签名窜
	 * @param params
	 * @param privateKey
	 * @return
	 */
	private String getSignString(String privateKey) {
        List<String> keys = new ArrayList<String>(info.keySet());
        Collections.sort(keys);//对参数排序，已确保拼接的签名顺序正确
        String prestr = "";
		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) info.get(key);
			if ("sign_type".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key) || value == null || value.trim().length() == 0) {
				continue;
			}
			if (first) {
				prestr = prestr + key + "=" + value;
				first = false;
			} else {
				prestr = prestr + "&" + key + "=" + value;
			}
		}
        return prestr + privateKey;
    }
	
    public static void main(String[] args) throws Exception {
    	File file = new File("E:/logs/Alipay_lvmama_20120119151516_18835.csv.zip");
    	new NotifyFromAlipayToBank(null).processResultFile(file);
    }
}
