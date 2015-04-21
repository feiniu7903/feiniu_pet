package com.lvmama.distribution.service;
/**
 * CK设备Service
 * @author gaoxin
 */
public interface DistributionForCKDeviceService {
	/**
	 * 根据辅助码查询订单
	 * @param requestXml 请求xml
	 * @return
	 */
	public String checkReservation(String requestXml) throws Exception;

	public String getPrintInfo(String requestXml) throws Exception;

	public String confirmPrint(String requestXml) throws Exception;

	public String queryProductList(String requestXml) throws Exception;

	public String createOrder(String requestXml) throws Exception;

	public String confirmPayment(String requestXml) throws Exception;

	public String queryOrder(String requestXml) throws Exception;

	public String requestPaymentChecksum(String requestXml) throws Exception;



}
