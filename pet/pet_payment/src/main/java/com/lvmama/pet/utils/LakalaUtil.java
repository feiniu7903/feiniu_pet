package com.lvmama.pet.utils;

import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.PaymentConstant;

/**
 * 拉卡拉支付.
 * 
 * <pre>
 * 用户在使用拉卡拉POS机时，拉卡拉系统首先调用驴妈妈查询接口，判断此时是否可以进行支付，
 * 如果可以，则拉卡拉系统进行扣款，扣款成功后再通知驴妈妈支付成功，如果此时网络中断，则不会再次通知！
 * </pre>
 * 
 * @author tom
 * @version Super二期 2011/03/20
 * @since Super二期
 * @see com.lvmama.common.utils.ConfigHelper
 */
public abstract class LakalaUtil {

	/**
	 * MD5.
	 */
	private static final String MD5 = "MD5";
	/**
	 * UTF8.
	 */
	private static final String UTF8 = "UTF-8";

	/**
	 * 生成拉卡拉URL.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param oughtPayYuan
	 *            需支付金额，以元为单位
	 * @return 拉卡拉URL，用于向用户发送短信
	 */
	public static String createLakalaURL(final String billNo, final String oughtPayYuan) {
		final String lakala_url = PaymentConstant.getInstance().getProperty("LAKALA_URL");
		final String merID = PaymentConstant.getInstance().getProperty("LAKALA_MER_ID");
		final String macKey = PaymentConstant.getInstance().getProperty("LAKALA_MACKEY");
		final String verCode = UtilityTool.messageEncrypt(merID + billNo + macKey, MD5, UTF8);
		return new StringBuilder(lakala_url).append("?").append("billNo=")
				.append(billNo).append("&").append("merID=").append(merID)
				.append("&").append("amount=").append(oughtPayYuan).append("&")
				.append("verCode=").append(verCode).toString();
	}
}
