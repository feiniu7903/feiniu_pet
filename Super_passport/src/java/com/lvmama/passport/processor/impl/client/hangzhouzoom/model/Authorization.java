package com.lvmama.passport.processor.impl.client.hangzhouzoom.model;
import com.lvmama.passport.utils.WebServiceConstant;

public class Authorization {
	private static Authorization authorization = new Authorization();
	/** 授权的地址 */
	private String scope = WebServiceConstant.getProperties("hanghzouzoom_scope");
	/** 授权类型 */
	private String grantType = WebServiceConstant.getProperties("hanghzouzoom_granttype");
	/** 申请应用时分配的AppKey */
	private String clientId = WebServiceConstant.getProperties("hanghzouzoom_clientid");
	/** 申请应用时分配的AppSecret */
	private String clientSecret = WebServiceConstant.getProperties("hanghzouzoom_clientsecret");

	private Authorization() {
	}

	public static Authorization getInstance() {
		return authorization;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
}
