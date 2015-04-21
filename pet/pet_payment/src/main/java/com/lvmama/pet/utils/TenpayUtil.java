package com.lvmama.pet.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.callback.data.WeixinWebCallbackNotifyData;

public class TenpayUtil {

	
	private static final String JKS_CA_ALIAS = "tenpay";
	
	private static final String JKS_CA_PASSWORD = "";
	/** 超时时间,以秒为单位 */
	private static final int timeOut=30;
	
	private static final String USER_AGENT_VALUE = "Mozilla/4.0 (compatible; MSIE 6.0; Windows XP)";
	
	public static final String SunX509 = "SunX509";
	public static final String JKS = "JKS";
	public static final String PKCS12 = "PKCS12";
	public static final String TLS = "TLS";
	/**微信支付APP访问token**/
	public static final String WEIXIN_PHONE_ACCESS_TOKEN = "WEIXIN_phone_access_token";
	/**微信支付APP的appid**/
	public static final String WEIXIN_PHONE_APP_ID = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_APP_ID");
	/**微信支付APP的appsecret**/
	public static final String WEIXIN_PHONE_APP_SECRET = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_APP_SECRET");
	/**微信支付APP的appsecret**/
	public static final String WEIXIN_PHONE_ACCESS_TOKEN_URL = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_ACCESS_TOKEN_URL");
	
	 /**
	 * 生成退款请求数据
	 * @param resultMap
	 * @return
	 */
	public static String getRefundStringDate(Map<String, String> sParaTemp) {
		List<String> keys = new ArrayList<String>(sParaTemp.keySet());
        StringBuffer prestr = new StringBuffer();
		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = sParaTemp.get(key);
			if (first) {
				prestr.append(key + "=" + value);
				first = false;
			} else {
				prestr.append("&" + key + "=" + value);
			}
		}
		return prestr.toString();
	}
	
	/**
	 * app_signature生成方法： 
	 * A）参与签名的字段包括：appid、appkey、noncer、package、timestamp以及 traceid 
	 * B）对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，使用URL 键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1。
	 * 注意：所有参数名均为小写字符 
	 * C）对string1 作签名算法，字段名和字段值都采用原始值，不进行URL 转义。具体签名算法为SHA1
	 * @autor: heyuxing  2014-4-13 下午4:23:25
	 * @param resultMap    
	 * @return void
	 */
	public static String getAppSignature(SortedMap<String, String> resultMap) {
        StringBuffer prestr = new StringBuffer();
		for (Map.Entry<String, String> entry: resultMap.entrySet()) {
			prestr.append("&" + entry.getKey() + "=" + (entry.getValue()==null?"":entry.getValue()));
		}
		return DigestUtils.shaHex(prestr.substring(1));
	}
	
	 /**
	 * 生成MD5加密字符串
	 * @param String
	 * @return
	 */
	public static String getMD5String(String tenpayKey) {
		return MD5.md5(tenpayKey);
	}
	
	public static String cllTenpayHttpsRefund(String date,String refundGateway) throws IOException, CertificateException,KeyStoreException, NoSuchAlgorithmException,
	UnrecoverableKeyException, KeyManagementException {
		String url = PaymentConstant.getInstance().getProperty("TENPAY_REFUND_GATEWAY_URL");			//https退款请求路径
		String caFilePath = PaymentConstant.getInstance().getProperty("TENPAY_REFUND_CAFILEPATH");    //退款pem文件存放路径
		String jksCAPATH = PaymentConstant.getInstance().getProperty("TENPAY_REFUND_JKSPATH");      //财付通退款JKS文件
		
		String certFilePath;
		String certPasswd;
		if(Constant.PAYMENT_GATEWAY.TENPAY.name().equalsIgnoreCase(refundGateway)){
			certFilePath = PaymentConstant.getInstance().getProperty("TENPAY_REFUND_CERTFILEPATH");//证书存放路径
			certPasswd = PaymentConstant.getInstance().getProperty("TENPAY_PARTNER");      //证书密码，就是商户号
		}else if(Constant.PAYMENT_GATEWAY.WEIXIN_WEB.name().equalsIgnoreCase(refundGateway)){
			certFilePath = PaymentConstant.getInstance().getProperty("WEIXIN_WEB_REFUND_CERTFILEPATH");//证书存放路径
			certPasswd = PaymentConstant.getInstance().getProperty("WEIXIN_WEB_PARTNER");      //证书密码，就是商户号
		}else if(Constant.PAYMENT_GATEWAY.WEIXIN_IOS.name().equalsIgnoreCase(refundGateway)){
			certFilePath = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_REFUND_CERTFILEPATH");//证书存放路径
			certPasswd = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_PARTNER");      //证书密码，就是商户号
		}else if(Constant.PAYMENT_GATEWAY.WEIXIN_ANDROID.name().equalsIgnoreCase(refundGateway)){
			certFilePath = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_REFUND_CERTFILEPATH");//证书存放路径
			certPasswd = PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_PARTNER");      //证书密码，就是商户号
		}else{
			certFilePath = PaymentConstant.getInstance().getProperty("TENPAY_PHONE_REFUND_CERTFILEPATH");//客户端退款证书存放路径
			certPasswd = PaymentConstant.getInstance().getProperty("TENPAY_PHONE_PARTNER");      //客户端退款证书密码，就是商户号
		}
		
		File caFile = new File(caFilePath);
		File certFile = new File(certFilePath);
		if(!caFile.isFile()||!certFile.isFile()){
			return null;
		}
		File jksCAFile = new File(jksCAPATH);
		if (!jksCAFile.isFile()) {
			X509Certificate cert = (X509Certificate) getCertificate(caFile);

			FileOutputStream out = new FileOutputStream(jksCAFile);

			storeCACert(cert, JKS_CA_ALIAS,JKS_CA_PASSWORD, out);

			out.close();

		}

		FileInputStream trustStream = new FileInputStream(jksCAFile);
		FileInputStream keyStream = new FileInputStream(certFile);

		SSLContext sslContext = getSSLContext(trustStream,JKS_CA_PASSWORD, keyStream, certPasswd);

		//关闭流
		trustStream.close();
		keyStream.close();
		
		byte[] postData = date.getBytes("UTF-8");
		
		SSLSocketFactory sf = sslContext.getSocketFactory();

		HttpsURLConnection conn = getHttpsURLConnection(url);

		conn.setSSLSocketFactory(sf);

		// 以post方式通信
		conn.setRequestMethod("POST");

		// 设置请求默认属性
		setHttpRequest(conn);

		// Content-Type
		conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

		BufferedOutputStream connOutStream = new BufferedOutputStream(conn.getOutputStream());

		final int len = 1024; // 1KB
		doOutput(connOutStream, postData, len);

		//关闭流
		connOutStream.close();
		

//		// 获取响应返回状态码
//		int responseCode = conn.getResponseCode();
		// 获取应答输入流
		InputStream inputStream = conn.getInputStream();
		
		if(null == inputStream) {
			return null;
		}

		//获取应答内容
		String resContent =new String(InputStreamTOByte(inputStream),"UTF-8");

		//关闭流
		inputStream.close();
		
		return resContent;
	}
	
	/**
	 * 获取勾兑数据
	 * @param date 请求数据
	 * @return String
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static String cllTenpayHttpAccount(Date accountDate) throws IOException{

		StringBuffer requestSB = new StringBuffer();
		requestSB.append("spid="+PaymentConstant.getInstance().getProperty("TENPAY_PARTNER"));
		requestSB.append("&trans_time="+DateUtil.formatDate(accountDate, "yyyy-MM-dd"));
		requestSB.append("&stamp="+Long.toString(System.currentTimeMillis()/1000));
		requestSB.append("&cft_signtype=0&mchtype=0");
		requestSB.append("&key="+PaymentConstant.getInstance().getProperty("TENPAY_KEY"));
		String sign=MD5.md5(requestSB.toString());
		requestSB.append("&sign="+sign);
		return HttpsUtil.requestPostData(PaymentConstant.getInstance().getProperty("TENPAY_ACCOUNT_URL"), requestSB.toString(), "application/x-www-form-urlencoded", "GBK").getResponseString("GBK");
		
	}
	
	
	/**
	 * 获取CA证书信息
	 * @param cafile CA证书文件
	 * @return Certificate
	 * @throws CertificateException
	 * @throws IOException
	 */
	private static Certificate getCertificate(File cafile)
			throws CertificateException, IOException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		FileInputStream in = new FileInputStream(cafile);
		Certificate cert = cf.generateCertificate(in);
		in.close();
		return cert;
	}
	
	/**
	 * 存储ca证书成JKS格式
	 * @param cert
	 * @param alias
	 * @param password
	 * @param out
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	private static void storeCACert(Certificate cert, String alias,
			String password, OutputStream out) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore ks = KeyStore.getInstance("JKS");

		ks.load(null, null);

		ks.setCertificateEntry(alias, cert);

		ks.store(out, str2CharArray(password));

	}
	
	/**
	 * 字符串转换成char数组
	 * @param str
	 * @return char[]
	 */
	private static char[] str2CharArray(String str) {
		if(null == str) return null;
		
		return str.toCharArray();
	}
	
	/**
	 * 获取SSLContext
	 * @param trustFile 
	 * @param trustPasswd
	 * @param keyFile
	 * @param keyPasswd
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws UnrecoverableKeyException 
	 * @throws KeyManagementException 
	 */
	private static SSLContext getSSLContext(
			FileInputStream trustFileInputStream, String trustPasswd,
			FileInputStream keyFileInputStream, String keyPasswd)
			throws NoSuchAlgorithmException, KeyStoreException,
			CertificateException, IOException, UnrecoverableKeyException,
			KeyManagementException {

		// ca
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(SunX509);
		KeyStore trustKeyStore = KeyStore.getInstance(JKS);
		trustKeyStore.load(trustFileInputStream, str2CharArray(trustPasswd));
		tmf.init(trustKeyStore);

		final char[] kp = str2CharArray(keyPasswd);
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(SunX509);
		KeyStore ks = KeyStore.getInstance(PKCS12);
		ks.load(keyFileInputStream, kp);
		kmf.init(ks, kp);

		SecureRandom rand = new SecureRandom();
		SSLContext ctx = SSLContext.getInstance(TLS);
		ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), rand);

		return ctx;
	}
	
	/**
	 * get HttpsURLConnection
	 * @param strUrl url地址
	 * @return HttpsURLConnection
	 * @throws IOException
	 */
	private static HttpsURLConnection getHttpsURLConnection(String strUrl)
			throws IOException {
		URL url = new URL(strUrl);
		HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url
				.openConnection();
		return httpsURLConnection;
	}
	
	/**
	 * 设置http请求默认属性
	 * @param httpConnection
	 */
	private static void setHttpRequest(HttpURLConnection httpConnection) {
		
		//设置连接超时时间
		httpConnection.setConnectTimeout(timeOut * 1000);
		
		//User-Agent
		httpConnection.setRequestProperty("User-Agent", USER_AGENT_VALUE);
		
		//不使用缓存
		httpConnection.setUseCaches(false);
		
		//允许输入输出
		httpConnection.setDoInput(true);
		httpConnection.setDoOutput(true);
		
	}
	
	/**
	 * 处理输出<br/>
	 * 注意:流关闭需要自行处理
	 * @param out
	 * @param data
	 * @param len
	 * @throws IOException
	 */
	private static void doOutput(OutputStream out, byte[] data, int len)
			throws IOException {
		int dataLen = data.length;
		int off = 0;
		while (off < data.length) {
			if (len >= dataLen) {
				out.write(data, off, dataLen);
				off += dataLen;
			} else {
				out.write(data, off, len);
				off += len;
				dataLen -= len;
			}

			// 刷新缓冲区
			out.flush();
		}

	}
	
	/**
	 * InputStream转换成Byte
	 * 注意:流关闭需要自行处理
	 * @param in
	 * @return byte
	 * @throws Exception
	 */
	private static byte[] InputStreamTOByte(InputStream in) throws IOException{  
		
		int BUFFER_SIZE = 4096;  
		ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
        byte[] data = new byte[BUFFER_SIZE];  
        int count = -1;  
        
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
            outStream.write(data, 0, count);  
          
        data = null;  
        byte[] outByte = outStream.toByteArray();
        outStream.close();
        
        return outByte;  
    } 
	
	/**
	 * 
	 * 获得微信APP的accessToken
	 *
	 * @autor: heyuxing  2014-4-14 下午4:27:01
	 * @return    
	 * @return String
	 */
	public static String getWeixinPhoneAccessToken() {
		String tenpayWeixinIOSAccessToken = (String)MemcachedUtil.getInstance().get(WEIXIN_PHONE_ACCESS_TOKEN);
		if(StringUtils.isBlank(tenpayWeixinIOSAccessToken)) {
			tenpayWeixinIOSAccessToken = getRealTenpayWeixinPhoneAccessToken();
		}
		return tenpayWeixinIOSAccessToken;
	}
	
	/**
	 * 
	 * 向微信发起请求获得真实的微信IOS的accessToken
	 *
	 * @autor: heyuxing  2014-4-14 下午4:27:35
	 * @return    
	 * @return String
	 */
	private static String getRealTenpayWeixinPhoneAccessToken() {
		String tenpayWeixinIOSAccessToken = null;	
		try {
			String strReString = HttpsUtil.requestPostData(PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_ACCESS_TOKEN_URL")+"&appid="+WEIXIN_PHONE_APP_ID+"&secret="+WEIXIN_PHONE_APP_SECRET, "", "application/x-www-form-urlencoded", "UTF-8").getResponseString("UTF-8");
			JSONObject resultJson = JSONObject.fromObject(strReString);
			if(resultJson!=null && resultJson.containsKey("access_token") && resultJson.containsKey("expires_in")) {
				tenpayWeixinIOSAccessToken = resultJson.getString("access_token");
				int expires_in = resultJson.getInt("expires_in");
				if(expires_in==7200) {
					MemcachedUtil.getInstance().set(WEIXIN_PHONE_ACCESS_TOKEN, 90*60, tenpayWeixinIOSAccessToken);	//缓存设置有效时间为90分钟
				}else {
					MemcachedUtil.getInstance().set(WEIXIN_PHONE_ACCESS_TOKEN, (expires_in*2)/3, tenpayWeixinIOSAccessToken);	//缓存设置有效时间为90分钟
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tenpayWeixinIOSAccessToken;
	}
	
	/**
	 * 发送查询请求到财付通，获取订单信息
	 * @param reqMap 请求参数
	 * @return
	 */
	public static String getTenpayRes(Map<String,String> reqMap){
		String res="";
		if(Constant.PAYMENT_GATEWAY.TENPAY.name().equals(reqMap.get("type"))){
		reqMap.put("partner", PaymentConstant.getInstance().getProperty("TENPAY_PARTNER"));//商户号
		reqMap.remove("type");
		String sign = COMMUtil.getSignature(reqMap, PaymentConstant.getInstance().getProperty("TENPAY_KEY"));//对参数签名
		reqMap.put("sign", sign);//签名
		res = HttpsUtil.requestPostForm( PaymentConstant.getInstance().getProperty("TENPAY_QUERY_URL"), reqMap);
		}
		else
		{
		reqMap.put("bargainor_id", PaymentConstant.getInstance().getProperty("TENPAY_PHONE_PARTNER"));//商户号
		reqMap.remove("type");
		String sign = COMMUtil.getSignature(reqMap, PaymentConstant.getInstance().getProperty("TENPAY_PHONE_KEY"));//对参数签名
		reqMap.put("sign", sign);//签名
		res = HttpsUtil.requestPostForm( PaymentConstant.getInstance().getProperty("TENPAY_QUERY_PHONE_URL"), reqMap);
		
		}
	
		
		
		return res;
	}
	
	public static void testWeixinWebSign() {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("transport_fee", "0");
		paraMap.put("trade_state", "0");
		paraMap.put("trade_mode", "1");
		paraMap.put("sign_type", "MD5");
		paraMap.put("input_charset", "UTF-8");
		paraMap.put("fee_type", "1");
		paraMap.put("out_trade_no", "2377841");
		paraMap.put("transaction_id", "1218445401201404110545029393");
		paraMap.put("discount", "0");
		paraMap.put("bank_billno", "201404111890616");
		paraMap.put("sign", "1AF871219FF37E5CFB09C6087DDB4B20");
		paraMap.put("total_fee", "1");
		paraMap.put("time_end", "20140411152921");
		paraMap.put("partner", "1218445401");
		paraMap.put("notify_id", "bWc6e8-PK7OthWJCKH2mP0u2FShGXl7y_hV34TtEmqfwDeOGu1Miol5PsN2TBLDe6OrlafTLdmQabUGodGaD9GoYDcFjt_Qf");
		paraMap.put("bank_type", "3006");
		paraMap.put("product_fee", "1");
		WeixinWebCallbackNotifyData tenpayWeixinWebCallbackNotifyData = new WeixinWebCallbackNotifyData(paraMap);
//		System.out.println("ppppp="+TenpayUtil.getSignature(paraMap,tenpayWeixinWebCallbackNotifyData.getKey()));
//		System.out.println("ppppp="+TenpayUtil.getSignature(tenpayWeixinWebCallbackNotifyData.signatureParamMap(),tenpayWeixinWebCallbackNotifyData.getKey()));
//		System.out.println("ppppp="+tenpayWeixinWebCallbackNotifyData.getSign());
		System.out.println(paraMap);
		System.out.println("========================"+paraMap.size());
		Map<String, String> signParaMap = tenpayWeixinWebCallbackNotifyData.signatureParamMap();
		System.out.println("signParaMap========================");
		for(String key:signParaMap.keySet()) {
			if(!paraMap.containsKey(key)) {
				System.out.println(key);
			}
		}
		System.out.println("paraMap========================");
		for(String key:paraMap.keySet()) {
			if(!signParaMap.containsKey(key)) {
				System.out.println(key);
			}
		}
	}
	
	public static void main(String[] ags){
//		testWeixinWebSign();
//		net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject("{\"prepayid\":\"PREPAY_ID\",\"errcode\":0,\"errmsg\":\"Success\"}");
//		System.out.println(obj.get("prepayid"));

		
//		//签名验证测试
//		Map<String, String> map = new HashMap<String,String>();
//		map.put("transport_fee", "0");
//		map.put("trade_state", "0");
//		map.put("trade_mode", "1");
//		map.put("sign_type", "MD5");
//		map.put("input_charset", "UTF-8");
//		map.put("fee_type", "1");
//		map.put("out_trade_no", "201307111414118031321712");
//		map.put("transaction_id", "1216574701201307110313452597");
//		map.put("discount", "0");
//		map.put("bank_billno", "201307118613521");
//		map.put("sign", "8AB9A539CEBCA752A00CEFFF4316FBF6");
//		map.put("total_fee", "1");
//		map.put("time_end", "20130711141529");
//		map.put("partner", "1216574701");
//		map.put("notify_id", "WnpN1Cn0ZR9rQlVfHlbi3EwtBeK6TxU0Iva7gtW-7UL3fJM8wzQTBFOzPYbrPXApsSliFdiD0rUfGQVOsTatzyoyblo86eaC");
//		map.put("bank_type", "BOC");
//		map.put("product_fee", "1");
//		String aaa = TenpayUtil.getSignature(map,"5977d7ae78e897aa2d34cec70ccf0215");
//		String signo = "8AB9A539CEBCA752A00CEFFF4316FBF6";
//		System.out.println("--------------"+aaa);
//		System.out.println("++++++++++++++++"+signo);
//		System.out.println("++++++++++++++++"+signo.equals(aaa));
//		System.out.println("bank_billno=201307118613521&bank_type=BOC&discount=0&fee_type=1&input_charset=UTF-8&notify_id=WnpN1Cn0ZR9rQlVfHlbi3EwtBeK6TxU0Iva7gtW-7UL3fJM8wzQTBFOzPYbrPXApsSliFdiD0rUfGQVOsTatzyoyblo86eaC&out_trade_no=201307111414118031321712&partner=1216574701&product_fee=1&sign_type=MD5&time_end=20130711141529&total_fee=1&trade_mode=1&trade_state=0&transaction_id=1216574701201307110313452597&transport_fee=0".equals("bank_billno=201307118613521&bank_type=BOC&discount=0&fee_type=1&input_charset=UTF-8&notify_id=WnpN1Cn0ZR9rQlVfHlbi3EwtBeK6TxU0Iva7gtW-7UL3fJM8wzQTBFOzPYbrPXApsSliFdiD0rUfGQVOsTatzyoyblo86eaC&out_trade_no=201307111414118031321712&partner=1216574701&product_fee=1&sign_type=MD5&time_end=20130711141529&total_fee=1&trade_mode=1&trade_state=0&transaction_id=1216574701201307110313452597&transport_fee=0"));
//		
//		//退款XML解析测试
//		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <root><input_charset>UTF-8</input_charset><out_refund_no>201307111831034427541</out_refund_no><out_trade_no>201307111444360741321729</out_trade_no><partner>1216574701</partner><reccv_user_name /><recv_user_id /><refund_channel>1</refund_channel><refund_fee>1</refund_fee><refund_id>1111216574701201307117900120</refund_id><refund_status>9</refund_status><retcode>0</retcode><retmsg /><sign>4C8B0C29605E5C50E515BCF9939E09BE</sign><sign_key_index>1</sign_key_index><sign_type>MD5</sign_type><transaction_id>1216574701201307110313467003</transaction_id></root>";
//		Map<String, String> resultMap=StringDom4jUtil.parseTenpayRefundResult(xmlString);
//  
//		String resultState = resultMap.get("refund_status");
//    	if("4".equals(resultState)|| "10".equals(resultState)){
//    		System.out.println("成功");
//    	}
//    	else if("3".equals(resultState)
//    			|| "5".equals(resultState)
//    			|| "6".equals(resultState)
//    			|| "7".equals(resultState)){
//    		System.out.println("失败");
//    	}
//    	else if("1".equals(resultState)
//    			||"2".equals(resultState)
//    			||"8".equals(resultState)
//    			||"9".equals(resultState)
//    			||"11".equals(resultState)){
//    		System.out.println("处理中");
//    	}
		
		
	}
}