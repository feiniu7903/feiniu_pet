package com.lvmama.fenxiao.model.tuangou;

import java.io.Serializable;

public class BaiduAccessToken implements Serializable {
	private static final long serialVersionUID = 1718382499194095250L;
	private String access_token;
	private String expires_in;
	private String refresh_token;
	private String scope;
	private String session_key;
	private String session_secret;
	private String error;
	private String error_description;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	public String getSession_secret() {
		return session_secret;
	}

	public void setSession_secret(String session_secret) {
		this.session_secret = session_secret;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError_description() {
		return error_description;
	}

	public void setError_description(String error_description) {
		this.error_description = error_description;
	}

	@Override
	public String toString() {
		return "BaiduAccessToken [access_token=" + access_token + ", expires_in=" + expires_in + ", refresh_token=" + refresh_token + ", scope=" + scope + ", session_key=" + session_key + ", session_secret=" + session_secret + ", error=" + error
				+ ", error_description=" + error_description + "]";
	}
}
