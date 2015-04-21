package com.lvmama.passport.processor.impl.client.hangzhouzoom.model;

public class AccessToken {
 /**
  * 杭州野生动物园授权信息
  */
	private String accessToken; // 用于调用access_token，接口获取授权后的access token
	private int expiresIn; // 有效时间

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

}
