package com.lvmama.pet.job.sms;

/**
 * 上行短信的处理接口，任何对上行短信的处理都应该实现此接口
 * @author Brian
 */
public interface ReceiveLogic {
	/**
	 * 继续执行处理，返回此值会继续执行处理接口
	 */
	int CONTINUE_MSSSAGE = 0;
	/**
	 * 排他性执行处理，返回此值后忽略排在其后的处理接口
	 */
	int SKIP_MESSAGE = 1;
	/**
	 * 上行短信的处理
	 * @param mobile  发送手机号
	 * @param content  短信内容
	 * @return 返回CONTINUE_MSSSAGE或SKIP_MESSAGE来代表是否继续处理
	 */
	int execute(String mobile, String content);
}
