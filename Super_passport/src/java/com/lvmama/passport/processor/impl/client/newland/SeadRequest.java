package com.lvmama.passport.processor.impl.client.newland;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 翼码发送HTTP请求
 * 
 * @author chenlinjun
 * 
 */
public class SeadRequest {
	private static final Log log = LogFactory.getLog(SeadRequest.class);

	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	/**
	 * 放送
	 * 
	 * @param xmlRequest
	 * @return
	 */
	public String send(String xmlRequest) throws Exception {
		System.setProperty("java.protocol.handler.pkgs","sun.net.www.protocols");
		URL url = null;
		StringBuilder result = new StringBuilder();
		HttpsURLConnection httpConn = null;
		BufferedReader in = null;
		OutputStreamWriter out = null;
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
			xmlRequest = utf82gb2312(xmlRequest.getBytes("UTF-8"));
			log.info("编码后的翼码请求信息" + xmlRequest);
			url = new URL(WebServiceConstant.getProperties("newland"));
			httpConn = (HttpsURLConnection) url.openConnection();
			httpConn.setSSLSocketFactory(sc.getSocketFactory());
			httpConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			httpConn.setConnectTimeout(30000);
			httpConn.setReadTimeout(45000);
			httpConn.setRequestMethod("POST");
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			httpConn.setUseCaches(false);
			httpConn.setRequestProperty("content-type", "text/xml; charset=GBK");
			out = new OutputStreamWriter(httpConn.getOutputStream(), "GBK");
			out.write(xmlRequest);
			out.flush();
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "GBK"));

			String line = "";
			while ((line = in.readLine()) != null) {
				result.append(line.trim());
			}
			log.info("Newland Apply Result:"+result.toString());
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
				if (httpConn != null)
					httpConn.disconnect();
			} catch (Exception ex) {
				log.error("翼码发送请求异常", ex);
			}
		}
		return result.toString();
	}

	/**
	 * 放送
	 * 
	 * @param xmlRequest
	 * @return
	 */
	public String sendHttpRequest(String xmlRequest) {
		URL url = null;
		StringBuilder result = new StringBuilder();
		HttpURLConnection httpConn = null;
		OutputStreamWriter out = null;
		BufferedReader in = null;
		try {
			xmlRequest = utf82gb2312(xmlRequest.getBytes("UTF-8"));
			log.info("编码后的翼码请求信息" + xmlRequest);
			url = new URL(WebServiceConstant.getProperties("yongliguolv_url"));
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setConnectTimeout(30000);
			httpConn.setReadTimeout(10000);
			httpConn.setRequestMethod("POST");
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			httpConn.setUseCaches(false);
			httpConn.setRequestProperty("content-type", "text/xml; charset=GB2312");
			out = new OutputStreamWriter(httpConn.getOutputStream(), "GB2312");
			out.write(xmlRequest);
			out.flush();
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "GBK"));
			String line = "";
			while ((line = in.readLine()) != null) {
				result.append(line.trim());
			}
			log.info("Newland Apply Result:"+result.toString());
		} catch (Exception e) {
			log.error("翼码发送请求异常", e);
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
				if (httpConn != null)
					httpConn.disconnect();
			} catch (Exception ex) {
				log.error("翼码发送请求异常", ex);
			}
		}
		return result.toString();
	}

	private String utf82gb2312(final byte[] utf8Bytes) {
		StringBuffer stringBuffer = new StringBuffer(utf8Bytes.length / 2);
		for (int i = 0; i < utf8Bytes.length; i++) {
			if (by2int(utf8Bytes[i]) <= 0x7F) {
				stringBuffer.append((char) utf8Bytes[i]);
			} else if (by2int(utf8Bytes[i]) <= 0xDF && by2int(utf8Bytes[i]) >= 0xC0) {
				int bh = by2int(utf8Bytes[i] & 0x1F);
				int bl = by2int(utf8Bytes[++i] & 0x3F);
				bl = by2int(bh << 6 | bl);
				bh = by2int(bh >> 2);
				int c = bh << 8 | bl;
				stringBuffer.append((char) c);
			} else if (by2int(utf8Bytes[i]) <= 0xEF && by2int(utf8Bytes[i]) >= 0xE0) {
				int bh = by2int(utf8Bytes[i] & 0x0F);
				int bl = by2int(utf8Bytes[++i] & 0x3F);
				int bll = by2int(utf8Bytes[++i] & 0x3F);
				bh = by2int(bh << 4 | bl >> 2);
				bl = by2int(bl << 6 | bll);
				int c = bh << 8 | bl;
				if (c == 58865) {
					c = 32;
				}
				stringBuffer.append((char) c);
			}
		}
		return stringBuffer.toString();
	}

	private int by2int(final int b) {
		return b & 0xff;
	}

}
