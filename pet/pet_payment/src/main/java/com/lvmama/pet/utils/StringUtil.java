package com.lvmama.pet.utils;

import com.lvmama.pet.payment.phonepay.RequestObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 解析IVR报文工具类.
 */
public class StringUtil {
	/**
	 * 解析请求参数
	 * @param xmlRequest
	 * @return
	 */
	public static RequestObject getRequestObject(String xmlRequest) {
		XStream xstream = getRequestXstreamObject();
		RequestObject obj = (RequestObject) xstream.fromXML(xmlRequest);
		return obj;
	}
	
	/**
	 * 返回总体模板
	 * @return
	 */
	private static XStream getRequestXstreamObject() {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("Request",com.lvmama.pet.payment.phonepay.RequestObject.class);
		xStream.alias("Head",com.lvmama.pet.payment.phonepay.RequestHeadObject.class);
		/** *****定义类中属性********** */
		xStream.aliasField("Head",com.lvmama.pet.payment.phonepay.RequestObject.class,"headobj");
		xStream.aliasField("Body",com.lvmama.pet.payment.phonepay.RequestObject.class,"body");
		xStream.aliasField("Version",com.lvmama.pet.payment.phonepay.RequestHeadObject.class,"version");
		xStream.aliasField("SequenceId",com.lvmama.pet.payment.phonepay.RequestHeadObject.class,"sequenceid");
		xStream.aliasField("Signed",com.lvmama.pet.payment.phonepay.RequestHeadObject.class,"signed");
		return xStream;
	}

}
