package com.lvmama.passport.processor.impl.client.zhiyoubao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.MD5;
import com.lvmama.passport.utils.HttpsUtil;

public class ZhiyoubaoClient {
	private static final Log log = LogFactory.getLog(ZhiyoubaoClient.class);
	
	public static String request(String xmlMsg,String key,String url) throws Exception {
		Map<String, String> xmlreq = new HashMap<String, String>();
		String singxml = "xmlMsg=" + xmlMsg+key;
		String sign = MD5.encode(singxml);
		xmlreq.put("xmlMsg", xmlMsg);
		xmlreq.put("sign", sign);
		log.info("Req signxml:"+singxml);
		log.info("Req URL:"+url);
		log.info("Req MD5:"+sign);
		String result = HttpsUtil.requestPostForm(url, xmlreq);
		if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
			throw new Exception(result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
		}
		return result;
	}
}