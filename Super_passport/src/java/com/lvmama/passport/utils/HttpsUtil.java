package com.lvmama.passport.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpsUtil {
	private static final Log LOG = LogFactory.getLog(HttpsUtil.class);
	private static final String CHARACTER_ENCODING = "UTF-8";
	private static final String CONTENT_TYPE = "Content-Type";
	public static final String HTTP_ERROR_PREFIX = "passport";
	private static final int CONNECTION_TIMEOUT = 10000;
	private static final int SO_TIMEOUT = 180000;
	
	/**
	 * HttpResponse包装类
	 */
	public static class HttpResponseWrapper {
		private HttpResponse httpResponse;
		private HttpClient httpClient;
		
		public HttpResponseWrapper(HttpClient httpClient, HttpResponse httpResponse) {
			this.httpClient = httpClient;
			this.httpResponse = httpResponse;
		}
		
		public HttpResponseWrapper(HttpClient httpClient) {
			this.httpClient = httpClient;
		}
		public HttpResponse getHttpResponse() {
			return httpResponse;
		}
		public void setHttpResponse(HttpResponse httpResponse) {
			this.httpResponse = httpResponse;
		}
		
		/**
		 * 获得流类型的响应
		 */
		public InputStream getResponseStream() throws IllegalStateException, IOException {
			return httpResponse.getEntity().getContent();
		}
		
		/**
		 * 获得字符串类型的响应
		 */
		public String getResponseString(String responseCharacter) throws ParseException, IOException {
			HttpEntity entity = getEntity();
			String responseStr = EntityUtils.toString(entity, responseCharacter);
			if (entity.getContentType() == null) {
				responseStr = new String(responseStr.getBytes("iso-8859-1"), responseCharacter);
			}
			EntityUtils.consume(entity);
			return responseStr;
		}
		
		public String getResponseString() throws ParseException, IOException {
			return getResponseString(CHARACTER_ENCODING);
		}
		
		/**
		 * 获得响应状态码
		 */
		public int getStatusCode() {
			return httpResponse.getStatusLine().getStatusCode();
		}
		
		/**
		 * 获得响应状态码并释放资源
		 */
		public int getStatusCodeAndClose() {
			close();
			return getStatusCode();
		}
		
		public HttpEntity getEntity() {
			return httpResponse.getEntity();
		}
		
		/**
		 * 释放资源
		 */
		public void close() {
			httpClient.getConnectionManager().shutdown();
		}
	}

	/**
	 * POST方式提交表单数据，返回响应对象
	 * @throws Exception 
	 */
	private static HttpResponseWrapper requestPostFormResponse(String url, Map<String, String> requestParas, String requestCharacter,int connectionTimeout,int soTimeout) throws Exception {
		HttpClient client = null;
		if (url.startsWith("https")) {
			client =createHttpsClient(connectionTimeout,soTimeout);
		}else{
			client =createHttpClient(connectionTimeout,soTimeout);
		}
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> formParams = initNameValuePair(requestParas);
		httpPost.setEntity(new UrlEncodedFormEntity(formParams, requestCharacter));
		HttpResponse httpResponse = client.execute(httpPost); //执行POST请求
		return new HttpResponseWrapper(client, httpResponse);
	}
	
	/**
	 * POST方式提交表单数据，不会自动重定向
	 */
	public static String requestPostForm(String url, Map<String, String> requestParas, String requestCharacter, String responseCharacter,int connectionTimeout,int soTimeout) {
		HttpResponseWrapper httpResponseWrapper = null;
		String responseStr = "";
		try {
			httpResponseWrapper = requestPostFormResponse(url, requestParas, requestCharacter,connectionTimeout,soTimeout);
			responseStr = httpResponseWrapper.getResponseString(responseCharacter);
		} catch (Exception e) {
			responseStr = HTTP_ERROR_PREFIX + e.toString();
			LOG.error("requestPostForm request Exception:" ,e);
		} finally {
			if(httpResponseWrapper != null){
				httpResponseWrapper.close();
			}
		}
		return responseStr;
	}
	/**
	 * POST方式提交表单数据，不会自动重定向
	 */
	public static String requestPostForm(String url, Map<String, String> requestParas) {
		return requestPostForm(url, requestParas, CHARACTER_ENCODING, CHARACTER_ENCODING,CONNECTION_TIMEOUT,SO_TIMEOUT);
	}

	/**
	 * POST方式提交Json数据，返回响应对象
	 * @throws Exception 
	 */
	private static HttpResponseWrapper requestPostJsonResponse(String url, String json, String requestCharacter,int connectionTimeout,int soTimeout) throws Exception {
		HttpClient client = null;
		if (url.startsWith("https")) {
			client =createHttpsClient(connectionTimeout,soTimeout);
		}else{
			client =createHttpClient(connectionTimeout,soTimeout);
		}

		String contentType = "application/json; charset=" + requestCharacter;
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader(CONTENT_TYPE, contentType);
		StringEntity se =  new StringEntity(json,requestCharacter);
		httpPost.setEntity(se);
		HttpResponse httpResponse = client.execute(httpPost); //执行POST请求
		return new HttpResponseWrapper(client, httpResponse);
	}
	/**
	 * POST方式提交Json数据
	 */
	public static String requestPostJson(String url, String json, String requestCharacter, String responseCharacter,int connectionTimeout,int soTimeout) {
		HttpResponseWrapper httpResponseWrapper = null;
		String responseStr = "";
		try {
			httpResponseWrapper = requestPostJsonResponse(url, json, requestCharacter,connectionTimeout,soTimeout);
			responseStr = httpResponseWrapper.getResponseString(responseCharacter);
		} catch (Exception e) {
			responseStr = HTTP_ERROR_PREFIX + e.toString();
			LOG.error("requestPostForm request Exception:" ,e);
		} finally {
			if(httpResponseWrapper != null){
				httpResponseWrapper.close();
			}
		}
		return responseStr;
	}

	/**
	 * POST方式提交JSON数据
	 */
	public static String requestPostJson(String url, String json) {
		return requestPostJson(url, json, CHARACTER_ENCODING, CHARACTER_ENCODING,CONNECTION_TIMEOUT,SO_TIMEOUT);
	}
	
	private static HttpResponseWrapper requestGetResponse(String url,int connectionTimeout,int soTimeout) throws Exception {
		HttpClient client = null;
		if (url.startsWith("https")) {
			client =createHttpsClient(connectionTimeout,soTimeout);
		}else{
			client =createHttpClient(connectionTimeout,soTimeout);
		}
		HttpGet httpGet = new HttpGet(url);	
		HttpResponse httpResponse = client.execute(httpGet);
		return new HttpResponseWrapper(client, httpResponse);
	}
	/**
	 * GET方式提交URL请求，会自动重定向
	 */
	public static String requestGet(String url, String responseCharacter ,int connectionTimeout,int soTimeout) {
		HttpResponseWrapper httpResponseWrapper = null;
		String responseStr = "";
		try {
			httpResponseWrapper = requestGetResponse(url,connectionTimeout,soTimeout);
			responseStr = httpResponseWrapper.getResponseString(responseCharacter);
		} catch (Exception e) {
			responseStr  = HTTP_ERROR_PREFIX + e.toString();
			LOG.error("requstGet request Exception:",e);
		} finally {
			if(httpResponseWrapper != null){
				httpResponseWrapper.close();
			}
		}
		return responseStr;
	}
	/**
	 * GET方式提交URL请求，会自动重定向
	 */
	public static String requestGet(String url) {
		return requestGet(url, CHARACTER_ENCODING ,CONNECTION_TIMEOUT,SO_TIMEOUT);
	}
	/**
	 * POST方式提交非表单数据，返回响应对象
	 * @throws Exception 
	 */
	private static HttpResponseWrapper requestPostData(String url, String data, String contentType, String requestCharacter,int connectionTimeout,int soTimeout) throws Exception {
		HttpClient client = null;
		if (url.startsWith("https")) {
			client =createHttpsClient(connectionTimeout,soTimeout);
		}else{
			client =createHttpClient(connectionTimeout,soTimeout);
		}
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader(CONTENT_TYPE, contentType);
		httpPost.setEntity(new StringEntity(data, requestCharacter));
		HttpResponse httpResponse = client.execute(httpPost);
		return new HttpResponseWrapper(client, httpResponse);
	}
	
	/**
	 * POST方式提交非表单数据，返回响应对象
	 * @throws Exception 
	 */
	private static HttpResponseWrapper requestPostData(String url, String data, String contentType, String requestCharacter) throws Exception {
		return requestPostData(url,data,contentType,requestCharacter,CONNECTION_TIMEOUT,SO_TIMEOUT);
	}
	/**
	 * POST非表单方式提交XML数据
	 */
	public static String requestPostXml(String url, String xmlData, String requestCharacter, String responseCharacter) {
		HttpResponseWrapper httpResponseWrapper = null;
		String responseStr = "";
		try {
			String contentType = "text/xml; charset=" + requestCharacter;
			httpResponseWrapper = requestPostData(url, xmlData, contentType, requestCharacter);
			responseStr = httpResponseWrapper.getResponseString(responseCharacter);
		} catch (Exception e) {
			responseStr = HTTP_ERROR_PREFIX + e.toString();
			LOG.error("requestPostXml request Exception:",e);
		} finally {
			if(httpResponseWrapper != null){
				httpResponseWrapper.close();
			}
		}
		return responseStr;
	}
	/**
	 * POST非表单方式提交XML数据
	 */
	public static String requestPostXml(String url, String xmlData) {
		return requestPostXml(url, xmlData, CHARACTER_ENCODING, CHARACTER_ENCODING);
	}
	
	private static HttpClient createHttpClient(int connectionTimeout,int soTimeout) {
		HttpClient httpClient = new DefaultHttpClient();
        HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
		HttpConnectionParams.setSoTimeout(params, soTimeout);
		return httpClient;
	}
	
	private static HttpClient createHttpClient() {
    		return createHttpClient(CONNECTION_TIMEOUT,SO_TIMEOUT);
	}
	
	private static HttpClient createHttpsClient(int connectionTimeout,int soTimeout) throws Exception  {
    	HttpClient httpClient = new DefaultHttpClient(); //创建默认的httpClient实例 
        HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
		HttpConnectionParams.setSoTimeout(params, soTimeout);
        //TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext 
        SSLContext ctx = SSLContext.getInstance("TLS"); 
        //使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用 
        ctx.init(null, new TrustManager[]{new TrustAnyTrustManager()}, null); 
        //创建SSLSocketFactory 
        SSLSocketFactory socketFactory = new SSLSocketFactory(ctx); 
        //通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上 
        httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory)); 
        return httpClient;
    }
	
    private static HttpClient createHttpsClient() throws Exception  {
		return createHttpsClient(CONNECTION_TIMEOUT,SO_TIMEOUT);
    }
    
    
    
    private static List<NameValuePair> initNameValuePair(Map<String, String> params) {
    	List<NameValuePair> formParams = new ArrayList<NameValuePair>();
    	if (params != null && params.size() > 0) {
    		// 对key进行排序
    		List<String> keys = new ArrayList<String>(params.keySet());
    		Collections.sort(keys);
	    	for (String key : keys) {
	    		LOG.info(key+" = " +params.get(key));
	    		formParams.add(new BasicNameValuePair(key, params.get(key))); 
			}
    	}
    	return formParams;
    }
	
	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}
	
	public static void main(String[] args) {
		Map<String,String> map  = new HashMap<String, String>();
		System.out.println("-----------\n"+requestPostXml("", "").substring(HTTP_ERROR_PREFIX.length()));
	}
}
