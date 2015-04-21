package com.lvmama.passport.callback.ws;

/**
 * 常州环球恐龙城服务接口
 * 
 * @author chenlinjun
 * 
 */
public interface IDinosaurTownService {
	/**
	 * 根据时间段获取所有门票订单的数 (定时抓取数据)
	 * 
	 * @param startTime
	 *            开始日期（游玩）格式 yyyy -MM -dd
	 * @param endTime
	 *            开始日期（游玩）格式
	 * @param user
	 *            用户身份
	 * @param check
	 *            验证码 MD5加密
	 * @return
	 */
	String queryOrderList(String startTime, String endTime, String user, String check);

	/**
	 * 履行对象使用(核实订单信息)
	 * 
	 * @param reqXml
	 * @return
	 */
	String usePerFormOrder(String reqXml);
	/**
	 * 根据订单号获取订单信息 (订单信息回传)
	 * @param orderId 订单号
	 * @param user
	 * @param check
	 * @return
	 */
	String getOrder(String orderId,String user,String check);
}
