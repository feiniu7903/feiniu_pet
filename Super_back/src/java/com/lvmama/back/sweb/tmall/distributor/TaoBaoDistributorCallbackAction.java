package com.lvmama.back.sweb.tmall.distributor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.back.sweb.BaseAction;

public class TaoBaoDistributorCallbackAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(TaoBaoDistributorCallbackAction.class);
	
	private String top_appkey;
	private String top_parameters;
	private String top_session;
	private String top_sign;
	private String encode;
	
	/**
	 * TaoBao 分销Session Key 回调接口
	 * 
	 * @return
	 */
	@Action(value = "/distributor/doDistributorCallBack")
	public void doDistributorCallBack(){
		log.info("doDistributorCallBack:====>top_appkey:"+top_appkey+"top_parameters:"+top_parameters+"top_session:"+top_session+"top_sign:"+top_sign+"encode:"+encode);
		//return "";
	}
	public String getTop_appkey() {
		return top_appkey;
	}

	public void setTop_appkey(String top_appkey) {
		this.top_appkey = top_appkey;
	}

	public String getTop_parameters() {
		return top_parameters;
	}

	public void setTop_parameters(String top_parameters) {
		this.top_parameters = top_parameters;
	}

	public String getTop_session() {
		return top_session;
	}

	public void setTop_session(String top_session) {
		this.top_session = top_session;
	}

	public String getTop_sign() {
		return top_sign;
	}

	public void setTop_sign(String top_sign) {
		this.top_sign = top_sign;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

}
