package com.lvmama.back.web.utils.insurance.pinan;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.lvmama.comm.vo.Constant;
import com.paic.internetbank.citi.AuthSSLProtocolSocketFactory;

public class PinAnOperator {
	private static final Log LOG = LogFactory.getLog(PinAnOperator.class);

	private String keystoreFile;
	private String keystorePassword;
	private String trustKeyFile;
	private String trustPassword;
	private String server;
	private int port;
	private String postURL;

	/**
	 * 请求报文
	 * 
	 * @param content
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Object request(Object content) {
		if(Constant.getInstance().isPinAnInsure()){
			HttpClient httpClient = initAuthHttps();
			UTF8PostMethod postMethod = new UTF8PostMethod(postURL);
			postMethod.setRequestBody(((EaiAhsXml) content).toXMLString());
			try {
				httpClient.executeMethod(postMethod);
				String rtnContent = postMethod.getResponseBodyAsString();
				return rtnContent;
			} catch (IOException ioe) {
				LOG.error("平安保险接口出错啦!" + ioe.getMessage());
				ioe.printStackTrace();
			} finally {
				postMethod.releaseConnection();
			}
		}
		return null;
	}

	/**
	 * 投保请求的回复报文分析
	 * 
	 * @param content
	 * @return
	 */
	public Map<String, Object> getRtnDataForRequest(Object content) {
		try {
			Map<String, Object> rtn = new HashMap<String, Object>();
			SAXBuilder sb = new SAXBuilder();
			Document document = sb.build(new ByteArrayInputStream(
					((String) content).getBytes("utf-8")));
			if (null != XPath.selectSingleNode(document, "//Header/BK_SERIAL")) {
				rtn.put("policySerial", ((Text) XPath.selectSingleNode(
						document, "//Header/BK_SERIAL/text()"))
						.getTextNormalize());
			}
			if (null != XPath.selectSingleNode(document, "//Header/PA_RSLT_CODE")) {
				rtn.put("PA_RSLT_CODE", ((Text) XPath.selectSingleNode(
						document, "//Header/PA_RSLT_CODE/text()"))
						.getTextNormalize());
			}
			if (null != XPath.selectSingleNode(document, "//Header/PA_RSLT_MESG")) {
				rtn.put("paRsltMesg", ((Text) XPath.selectSingleNode(document,
						"//Header/PA_RSLT_MESG/text()")).getTextNormalize());
			}
			if (null != XPath.selectSingleNode(document, "//Response/policyInfo/policyNo")) {
				rtn.put("policyNo", ((Text) XPath.selectSingleNode(document,
						"//Response/policyInfo/policyNo/text()"))
						.getTextNormalize());
			}
			if (null != XPath.selectSingleNode(document, "//Response/policyInfo/validateCode")) {
				rtn.put("validateCode", ((Text) XPath.selectSingleNode(
						document, "//Response/policyInfo/validateCode/text()"))
						.getTextNormalize());
			}
			return rtn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取消保单的回复报文分析
	 * 
	 * @param content
	 * @return
	 */
	public Map<String, Object> getRtnDataForCancel(Object content) {
		try {
			Map<String, Object> rtn = new HashMap<String, Object>();
			SAXBuilder sb = new SAXBuilder();
			Document document = sb.build(new ByteArrayInputStream(
					((String) content).getBytes("utf-8")));
			rtn.put("PA_RSLT_CODE", ((Text) XPath.selectSingleNode(document,
					"//Header/PA_RSLT_CODE/text()")).getTextNormalize());
			rtn.put("paRsltMesg", ((Text) XPath.selectSingleNode(document,
					"//Header/PA_RSLT_MESG/text()")).getTextNormalize());
			return rtn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 构造SSL协议的httpclient客户端
	 */
	@SuppressWarnings("deprecation")
	private synchronized HttpClient initAuthHttps() {
		Protocol authhttps = new Protocol("https",
				new AuthSSLProtocolSocketFactory(this.getClass()
						.getResource(keystoreFile), keystorePassword, this
						.getClass().getResource(trustKeyFile),
						trustPassword), 443);
		// 构造HttpClient的实例
		Protocol.registerProtocol("https", authhttps);
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
		httpClient.getHostConfiguration().setHost(server, port, authhttps);
		return httpClient;
	}

	public String getKeystoreFile() {
		return keystoreFile;
	}

	public void setKeystoreFile(String keystoreFile) {
		this.keystoreFile = keystoreFile;
	}

	public String getKeystorePassword() {
		return keystorePassword;
	}

	public void setKeystorePassword(String keystorePassword) {
		this.keystorePassword = keystorePassword;
	}

	public String getTrustKeyFile() {
		return trustKeyFile;
	}

	public void setTrustKeyFile(String trustKeyFile) {
		this.trustKeyFile = trustKeyFile;
	}

	public String getTrustPassword() {
		return trustPassword;
	}

	public void setTrustPassword(String trustPassword) {
		this.trustPassword = trustPassword;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPostURL() {
		return postURL;
	}

	public void setPostURL(String postURL) {
		this.postURL = postURL;
	}
}

/**
 * 支持utf-8字符集的post方法
 * 
 * @author Brian
 * 
 */
class UTF8PostMethod extends PostMethod {
	public UTF8PostMethod(String url) {
		super(url);
	}

	@Override
	public String getRequestCharSet() {
		return "UTF-8";
	}
}
