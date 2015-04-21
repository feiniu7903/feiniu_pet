/**
 * 
 */
package com.lvmama.vst.service;

import com.lvmama.comm.pet.client.IUserClient;
import com.lvmama.comm.pet.client.UserClient;

/**
 * @author lancey
 *
 */
public class VstUserClientImpl implements IUserClient {
	
	private UserClient userClient;

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.client.IUserClient#batchRegisterWithChannel(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Long batchRegisterWithChannel(String mobileNumber, String email,
			String realName, String gender, String smsContent,
			String mailContent, String cityId, String channel) throws Exception {
		return userClient.batchRegisterWithChannel(mobileNumber, email,
				realName, gender, smsContent, mailContent, cityId, channel);
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}

}
