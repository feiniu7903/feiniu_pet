/**
 * 
 */
package com.lvmama.comm.pet.client;

/**
 * 该接口只提供给vst系统使用，focus,super直接使用UserClient类
 * @author lancey
 *
 */
public interface IUserClient {

	Long batchRegisterWithChannel(String mobileNumber, String email,
			String realName, String gender, String smsContent,
			String mailContent, String cityId, String channel) throws Exception;
}
