package com.lvmama.comm.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pub.ComKeJetWord;
import com.lvmama.comm.pet.service.pub.ComKeJetWordService;
import com.lvmama.comm.spring.SpringBeanProxy;



/**
 * 科捷广告系统的代理
 * @author Brian
 * <p>此类是为了解决科捷广告系统过多的请求而设计。通过此类将科捷显示的html放入memcache缓存。客户端代码直接由服务器端返回，而不再向科捷请求。</p>
 *
 */
public final class KeJieAdsProxy {
	/**
	 * 日志输出器
	 */
	private final static Log LOG = LogFactory.getLog(KeJieAdsProxy.class);
	/**
	 * 账户
	 */
	private final static String PU = "4dcb4947990dd69f0001";
	
	/**
	 * 获取科捷广告系统的代码
	 * @param ap ap参数值
	 * @param ct ct参数值，代表类型
	 * @param ext_key ext_key参数值，代表地域
	 * @return
	 */
	public static void getKeJetAdsContent(final String ap, final String ct, final String ext_key) {
		if (StringUtils.isBlank(ap) || StringUtils.isBlank(ct)) {
			return;
		}
	
		String url = "http://afp8core.kejet.com/afp/door/;ap=" + ap + ";ct=" + ct + ";pu=" + PU + ";" + ((null == ext_key)? "" : "ext_key=" + ext_key) + "/?";
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("reqeust url:" + url);
		}
		
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
		httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		
		String content = null;
		
		try {
			GetMethod getMethod = new GetMethod(url);
			httpClient.executeMethod(getMethod);
			InputStream inputStream = getMethod.getResponseBodyAsStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			BufferedReader bufferReader = new BufferedReader(inputStreamReader);
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = bufferReader.readLine()) != null) {
				sb.append(line.trim());
			}
			content = sb.toString();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		
		LOG.info(url + "\t" + content);
		
		if ("if".equalsIgnoreCase(ct)) {
			//返回的就是html，不需要加工处理 
		}
		
		if ("js".equalsIgnoreCase(ct) && null != content) {
			
			int start = content.indexOf("\"");
			int end = content.lastIndexOf("\"");
			if (start != -1 && end != -1 && end > start) {
				content = content.substring(start + 1, end);
			}
			if (";".equalsIgnoreCase(content.trim())) {
				content = "<!-- no ads -->";
			}
		}
		
		if (null != content) {
			LOG.info("replace memcache: key=" + getMemcacheKey(ap, ct, ext_key) + "\t old value= " + MemcachedUtil.getInstance().get(getMemcacheKey(ap, ct, ext_key)) + "\t new value:" + content);
			MemcachedUtil.getInstance().set(getMemcacheKey(ap, ct, ext_key), 12 * 3600, content); //缓存12小时
		}			
	}
	
	/**
	 * 返回科捷广告系统的代码
	 * @param ap ap参数值
	 * @param ct ct参数值，代表类型
	 * @param ext_key ext_key参数值，代表地域
	 * @return
	 */
	public static String keJieAdsProxy (final String ap, final String ct, final String ext_key) {
		if (StringUtils.isBlank(ap) || StringUtils.isBlank(ct)) {
			return "";
		}
		String memcacheKey = getMemcacheKey(ap, ct, ext_key);
		String content = (String) MemcachedUtil.getInstance().get(memcacheKey);
		
		if (null == content) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("There is no '" + memcacheKey + "' key in memcached!");
			}
			content = "";
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("content:" + content);
		}
		return content;
	}
	
	public static String keJieAdsTextProxy (final Long id) {
		if (null == id) {
			return "";
		}
		String memcacheKey = "KEJETPROXY_TEXT_" + id;
		String content = (String) MemcachedUtil.getInstance().get(memcacheKey);
		if (null == content) {		
			ComKeJetWordService service = (ComKeJetWordService) SpringBeanProxy.getBean("comKeJetWordService");
			ComKeJetWord word = service.queryByPK(id);
			if (null != word && StringUtils.isNotEmpty(word.getCode())) {
				content = word.getCode();
			} else {
				content = "";
			}
			MemcachedUtil.getInstance().set(memcacheKey, 12 * 3600, content); //缓存12小时
		}
		return content;
	}
	
	/**
	 * 返回memcache的键
	 * @param ap
	 * @param ct
	 * @param ext_key
	 * @return
	 */
	private static String getMemcacheKey (final String ap, final String ct, final String ext_key) {
		if (StringUtils.isBlank(ap) || StringUtils.isBlank(ct)) {
			return "";
		}
		String memcacheKey = ap + "_" + ct;
		if (StringUtils.isNotBlank(ext_key)) {
			memcacheKey =  memcacheKey + "_" + ext_key;
		}
		return memcacheKey;
	}
}
