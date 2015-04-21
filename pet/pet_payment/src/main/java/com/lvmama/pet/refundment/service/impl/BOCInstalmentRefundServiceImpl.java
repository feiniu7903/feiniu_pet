package com.lvmama.pet.refundment.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.payment.post.data.BOCInstalmentRefundPostData;
import com.lvmama.pet.refundment.data.BOCInstalmentRefundCallbackData;
import com.lvmama.pet.utils.BOCInstalmentHostnameVerifier;

/**
 * 中行分期退款类.
 * @author sunruyi
 * @see com.lvmama.pet.refundment.data.BOCInstalmentRefundCallbackData
 * @see com.lvmama.pet.payment.post.data.BOCInstalmentRefundPostData
 */
public class BOCInstalmentRefundServiceImpl implements BankRefundmentService{
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(BOCInstalmentRefundServiceImpl.class);
	/**
	 * 中行分期的退款.
	 * @return 是否退款成功.
	 */
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		boolean isSuccess = false;
		BankReturnInfo refumentinfo =new BankReturnInfo();
		try {
			String refundUrl = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_REFUND_URL");
			this.initHttpsURLConnection();
			String xmlRefundRequest = this.initRefundRequest(info);
			String xmlResponse = this.sendRequest(refundUrl,xmlRefundRequest);
			if(!xmlResponse.equals("")){
				BOCInstalmentRefundCallbackData bocInstalmentRefundResult = BOCInstalmentRefundCallbackData.initBOCInstalmentRefundResult(xmlResponse);
				isSuccess = bocInstalmentRefundResult.isSuccess();
				
				refumentinfo.setCode(bocInstalmentRefundResult.getStatusCode());
				refumentinfo.setCodeInfo(bocInstalmentRefundResult.getCallbackInfo());
				if(isSuccess){
					refumentinfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());	
				}
				else{
					refumentinfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return refumentinfo;
	}
	/**
	 * 初始化退款数据.
	 * @param order 订单.
	 * @param ordPayment 支付记录.
	 * @param ordRefundment 退款单.
	 */
	private String initRefundRequest (RefundmentToBankInfo info){
		BOCInstalmentRefundPostData bocInstalmentRefundData = new BOCInstalmentRefundPostData(info);
		return bocInstalmentRefundData.initRefundXML();
	}
	/**
	 * 向中行发送退款请求.
	 * @param xmlRequest 请求的XML数据.
	 * @return 返回的XML格式数据
	 */
	private String sendRequest(String url,String xmlRequest){
		 HttpsURLConnection urlCon = null;
		 String xmlResponse = "";
	        try {
	            urlCon = (HttpsURLConnection)(new URL(url)).openConnection();
	            urlCon.setDoOutput(true);
	            urlCon.setDoInput(true);
	            urlCon.setRequestMethod("PUT");
	            urlCon.setRequestProperty("Content-Length", String.valueOf(xmlRequest.getBytes().length));
	            urlCon.setUseCaches(false);
//	            LOG.info(urlCon.getResponseCode());
	            urlCon.getOutputStream().write(xmlRequest.getBytes("utf-8"));
	            urlCon.getOutputStream().flush();
	            urlCon.getOutputStream().close();
	            BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	            	xmlResponse = xmlResponse + line;
	            }
	            xmlResponse = new String(xmlResponse.getBytes("ISO-8859-1"),"UTF-8");
	            LOG.info("BOC INSTALMENT REFUNDMENT RESPONSE XML = " + xmlResponse);
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        	if(urlCon != null){
	        		urlCon.disconnect();
	        	}
	        }
		return xmlResponse;
	}
	/**
	 * 获得KeyStore.
	 * @param keyStorePath 密钥库路径
	 * @param password 密码
	 * @return 密钥库
	 * @throws Exception
	 */
	public static KeyStore getKeyStore(String password,String keyStorePath) throws Exception{
		//实例化密钥库
		KeyStore ks = KeyStore.getInstance("JKS");
		//获得密钥库文件流
		FileInputStream is = new FileInputStream(keyStorePath);
		//加载密钥库
		ks.load(is, password.toCharArray());
		//关闭密钥库文件流
		is.close();
		return ks;
	}
	/**
	 * 获得SSLSocketFactory.
	 * @param password 密码
	 * @param keyStorePath 密钥库路径
	 * @param trustStorePath 信任库路径
	 * @return SSLSocketFactory
	 * @throws Exception
	 */
	public static SSLContext getSSLContext(String password,String keyStorePath, String trustStorePath) throws Exception{
		//实例化密钥库
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		//获得密钥库
		KeyStore keyStore = getKeyStore(password,keyStorePath);
		//初始化密钥工厂
		keyManagerFactory.init(keyStore, password.toCharArray());
		
		//实例化信任库
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		//获得信任库
		KeyStore trustStore = getKeyStore(password,trustStorePath);
		//初始化信任库
		trustManagerFactory.init(trustStore);
		//实例化SSL上下文
		SSLContext ctx = SSLContext.getInstance("TLS");
		//初始化SSL上下文
		ctx.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
		//获得SSLSocketFactory
		return ctx;
	}
	/**
	 * 初始化HttpsURLConnection.
	 * @param password 密码
	 * @param keyStorePath 密钥库路径
	 * @param trustStorePath 信任库路径
	 * @throws Exception
	 */
	private void initHttpsURLConnection() throws Exception{
		String storePassword = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_REFUND_STORE_PASSWORD");
		String keyStorePath = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_REFUND_KEY_STORE_PATH");
		String trustStorePath = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_REFUND_TRUST_STORE_PATH");
		//声明SSL上下文
		SSLContext sslContext = null;
		//实例化主机名验证接口
		HostnameVerifier hnv = new BOCInstalmentHostnameVerifier();
        try {
        	sslContext = getSSLContext(storePassword, keyStorePath, trustStorePath);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        if (sslContext != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        }
        HttpsURLConnection.setDefaultHostnameVerifier(hnv);
	}
	/**
	 * 测试方法.
	 */
	public static void main(String[] args) throws Exception {
//			PayPayment payPayment = new PayPayment(); 
//			payPayment.setGatewayTradeNo("1277326002");
//			payPayment.setCreateTime(new SimpleDateFormat("yyyyMMddhhmmss").parse("20120420162217"));
//			PayPaymentRefundment payPaymentRefundment = new PayPaymentRefundment();
//			payPaymentRefundment.setSerial("201204201277326003");
//			payPaymentRefundment.setAmount(100L);
//			BOCInstalmentRefundService BOC = new BOCInstalmentRefundServiceImpl();
//			LOG.info(BOC.refund(payPayment, payPaymentRefundment));
	}
}
