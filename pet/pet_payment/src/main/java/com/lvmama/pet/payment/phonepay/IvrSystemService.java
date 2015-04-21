package com.lvmama.pet.payment.phonepay;

public interface IvrSystemService {
	/**
	 * 接收讯鸟数据
	* @Title: receiveDataFromIVRSystem
	* @Description:
	* @param
	* @return String    返回类型
	* @throws
	 */
	void receiveDataFromIVRSystem(String xml);
}
