package com.lvmama.distribution.service;

public interface DistributionService {
	
	/**
	 * 分销产品基本信息列表接口
	 * @return
	 */
	public String productInfoList(String requestXmlStr);
	
	/**
	 * 产品时间价格表接口
	 * @return
	 */
	public String productPriceList(String requestXml);
	
	/**
	 * 单个产品时间价格表接口
	 * @return
	 */
	public String getProductPrice(String requestXml);
	
	/**
	 * 单个产品信息接口
	 */
	public String getProductInfo(String requestXml);
	
	/**
	 * 创建订单接口
	 * @return
	 */
	public String createOrder(String requestXml);
	
	/**
	 * 修改订单状态接口
	 * @return
	 */
	public String updateOrderStatus(String requestXml);
	
	/**
	 * 获取单个订单信息接口
	 */
	public String getOrder(String requestXml);
	
	/**
	 * 重发二维码短信接口
	 * @return
	 */
	public String resendCode(String requestXml);
	
	/**
	 * 查询产品上下架信息化接口
	 */
	public String getProductOnLine(String requestXml);
	
	/**
	 * 获取订单的资源审核状态
	 * @return
	 */
	public String getOrderApprove(String requestXml);
	
	/**
	 *告知驴妈妈订单的审核状态已经正常更新
	 */
	public String orderApproveCallBack(String requestXml);
	
	/**
	 * 验证订单信息接口
	 */
	public String validateOrder(String requestXml);
}
