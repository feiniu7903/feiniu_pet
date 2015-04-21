package com.lvmama.pet.refundment.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.pet.utils.CMBInstalmentTool;
import com.lvmama.pet.vo.CMBInstalmentRefundResponse;

/**
 * 招行分期退款返回结果类.
 * @author fengyu
 * @see com.com.lvmama.pet.vo.CMBInstalmentRefundResponse
 * @see com.lvmama.pet.utils.CMBInstalmentTool
 */
public class CMBInstalmentRefundCallbackData{
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(CMBInstalmentRefundCallbackData.class);

	private String code;
	private String errMsg;

	/**
	 * 构造函数.
	 * @param xmlStr
	 * @return
	 */
	public static CMBInstalmentRefundCallbackData initCMBInstalmentRefundResult(String xmlStr){
		Map<String, Class> alias = new HashMap<String, Class>();
		alias.put(CMBInstalmentRefundResponse.ROOT_ELEMENT, CMBInstalmentRefundResponse.class);
		CMBInstalmentRefundResponse cmbInstalmentRefundResponse = (CMBInstalmentRefundResponse) CMBInstalmentTool.fromXML(xmlStr, alias);
		String code = cmbInstalmentRefundResponse.getHead().getCode();
		String errMsg = cmbInstalmentRefundResponse.getHead().getErrMsg();
		CMBInstalmentRefundCallbackData cmbInstalmentRefundCallbackData = new CMBInstalmentRefundCallbackData();
		cmbInstalmentRefundCallbackData.setCode(code);
		cmbInstalmentRefundCallbackData.setErrMsg(errMsg);
		return cmbInstalmentRefundCallbackData;
	}

	/**
	 * 检查是否成功.
	 * @return
	 */
	public boolean isRefundSuccess(){
		/*
		 * 1为退款成功
		 * 2为已经完成退款
		 */
		return (code == null);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	
}