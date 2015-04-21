package com.lvmama.passport.processor.impl.client.time100;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

/**
 * 三亚民间返回对象，不能使用动态webservice
 * 
 * @author qiuguobin
 *
 */
public class Time100Client {
	private static final QName SERVICE_NAME = new QName("http://www.time100.net/", "Holiday");
	private static HolidaySoap port = null;

	private Time100Client() {
	}

	public static HolidaySoap getHolidaySoap() throws MalformedURLException {
		if (port == null) {
			URL wsdlURL = new URL("http://api.time100.net/Holiday.asmx?wsdl");
			Holiday ss = new Holiday(wsdlURL, SERVICE_NAME);
			port = ss.getHolidaySoap();
		}
		return port;
	}

	/**
	 * 下单
	 * 
	 * @param apiKEY
	 * @param apiPSW
	 * @param linkman
	 *            联系人名称
	 * @param mobile
	 *            联系人手机号码
	 * @param remark
	 *            备注
	 * @param orderDetails
	 *            JSON格式的产品信息
	 * @return
	 * @throws MalformedURLException
	 */
	public static String addOrderBestStr(String apiKEY, String apiPSW, String linkman, String mobile, String remark, String orderDetails) throws MalformedURLException {
		return getHolidaySoap().addOrderBestStr(apiKEY, apiPSW, linkman, mobile, remark, orderDetails);
	}

	/**
	 * 退单
	 * 
	 * @param apiKEY
	 * @param apiPSW
	 * @param oid
	 *            订单ID
	 * @return
	 * @throws MalformedURLException
	 */
	public static String orderCancelStr(String apiKEY, String apiPSW, String oid) throws MalformedURLException {
		return getHolidaySoap().orderCancelStr(apiKEY, apiPSW, oid);
	}

	/**
	 * 产品列表查询
	 * 
	 * @param API_KEY
	 * @param API_PSW
	 * @param Key
	 *            产品关键字
	 * @param start
	 *            开始条数
	 * @param end
	 *            结束条数
	 * @return
	 */
	public static String productListStr(String apiKEY, String apiPSW, String key, int start, int end) throws Exception {
		return getHolidaySoap().productListStr(apiKEY, apiPSW, key, start, end);
	}
}
