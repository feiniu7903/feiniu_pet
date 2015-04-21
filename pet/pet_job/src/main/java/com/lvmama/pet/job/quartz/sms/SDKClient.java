package com.lvmama.pet.job.quartz.sms;

import cn.emay.sdk.client.api.Client;

/**
 * 自定义实现短信发送客服端
 * @author Brian
 *
 */
public final class SDKClient extends Client {

	/**
	 * Constructor
	 * @param softwareSerialNo 序列号
	 * @param key key值
	 * @throws Exception 未知错误
	 */
	public SDKClient(final String softwareSerialNo, final String key) throws Exception {
		super(softwareSerialNo, key);
	}

	/**
	 * Constructor
	 * @param softwareSerialNo 序列号
	 * @param key key值
	 * @param remotServerIP 远程服务器地址
	 * @throws Exception 未知错误
	 */
	public SDKClient(final String softwareSerialNo, final String key, final String remotServerIP) throws Exception {
		super(softwareSerialNo, key, null, remotServerIP);
	}

}
