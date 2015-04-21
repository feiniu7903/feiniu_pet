package com.lvmama.distribution.model.lv;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.distribution.util.DistributionUtil;

/**
 * 分销报文返回体
 * @author lipengcheng
 *
 */
public class ResponseBody {
	private static final Log log = LogFactory.getLog(ResponseBody.class);
	private String xmlStr = null;
	public ResponseBody() {
	}
	
	public ResponseBody(String xmlStr) {
		this.xmlStr = xmlStr;
	}
	
	public void responseResend(){
		xmlStr = "<status>success</status>";
	}

	/**
	 * 报文生体成的统一方法
	 * 
	 * @return
	 */
	public String buildXmlStr() {
		try {
			if ("".equals(xmlStr)) {
				return xmlStr;// 此处逻辑针对于没有body部分的报文构造
			} else if (xmlStr != null) {
				xmlStr = DistributionUtil.encode(xmlStr.getBytes("utf-8"));
			} else {
				xmlStr = "noBody";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return DistributionUtil.buildXmlElement("body", xmlStr);
	}

}
