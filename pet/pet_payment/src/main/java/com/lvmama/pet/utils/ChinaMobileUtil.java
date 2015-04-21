package com.lvmama.pet.utils;

import com.lvmama.pet.utils.ChinaMobileMD5;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;

public class ChinaMobileUtil {
	public boolean MD5Verify(String srcData, String hmac, String key) {
		String _crpy = MD5Sign(srcData, key);
		return _crpy.equals(hmac);
	}

	public String sendAndRecv(String url, String buf, String characterSet) throws IOException {
		String charType = "GBK";
		if ("00".equals(characterSet)) {
			charType = "GBK";
		} else if ("01".equals(characterSet)) {
			charType = "GB2312";
		} else if ("02".equals(characterSet)) {
			charType = "UTF-8";
		}
		HttpClientParams params = new HttpClientParams();
		params.setContentCharset(charType);
		HttpClient hc = new HttpClient();
		params.setSoTimeout(120000);
		hc.setParams(params);
		PostMethod pm = new PostMethod(url);
		pm.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charType);
		String[] aParam = buf.split("&");
		if (aParam.length == 0) {
			return null;
		}
		int z = 0;
		for (int i = 0; i < aParam.length; i++) {
			z = aParam[i].indexOf('=');
			if (z != -1) {
				pm.addParameter(aParam[i].substring(0, z++), aParam[i].substring(z));
			}

		}

		String repMsg = "";
		try {
			hc.executeMethod(pm);
			repMsg = pm.getResponseBodyAsString();
		} finally {
			pm.releaseConnection();
			pm = null;
			hc = null;
		}

		return repMsg;
	}

	public String MD5Sign(String signData, String signkey) {
		ChinaMobileMD5 impl = new ChinaMobileMD5();
		String value = impl.cryptMd5(signData, "");
		String value2 = impl.cryptMd5(value, signkey);
		return value2;
	}

	public String getValue(String respMsg, String name) {
		String[] resArr = StringUtils.split(respMsg, "&");

		Map resMap = new HashMap();
		for (int i = 0; i < resArr.length; i++) {
			String data = resArr[i];
			int index = StringUtils.indexOf(data, '=');
			String nm = StringUtils.substring(data, 0, index);
			String val = StringUtils.substring(data, index + 1);
			resMap.put(nm, val);
		}
		return (String) resMap.get(name) == null ? "" : (String) resMap.get(name);
	}

	public static String getRedirectUrl(String payUrl) {
		HashMap rdUrl = new HashMap();
		if (payUrl != null) {
			String[] items = payUrl.split("[<hi:$$>]{7}");
			if (items != null) {
				for (int i = 0; i < items.length; i++) {
					String item = items[i];
					if (item != null) {
						String[] element = item.split("[<hi:=>]{6}");
						if ((element != null) && (element.length == 2)) {
							rdUrl.put(element[0], element[1]);
						}
					}
				}
			}
		}
		return rdUrl.get("url") + "?" + "sessionId=" + rdUrl.get("sessionId");
	}
}