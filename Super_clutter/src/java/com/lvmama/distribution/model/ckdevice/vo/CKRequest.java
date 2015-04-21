package com.lvmama.distribution.model.ckdevice.vo;


import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ckdevice.CkDeviceInfo;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.vo.ConstantMsg;
import com.lvmama.distribution.util.DistributionUtil;
import com.opensymphony.oscache.util.StringUtil;


public class CKRequest {
	private static final Log log = LogFactory.getLog(CKRequest.class);
	private CKHead header;
	private CKBody body;
	public CKRequest(){
		
	}
	public CKRequest(CKHead header,CKBody body){
		this.header = header;
		this.body = body;
	}
	public CKRequest(CKBody body){
		this.header = new CKHead();
		this.body = body;
	}
	public CKHead getHeader() {
		return header;
	}
	public void setHeader(CKHead header) {
		this.header = header;
	}
	public CKBody getBody() {
		
		return body;
	}
	public void setBody(CKBody body) {
		this.body = body;
	}
	public String  init(String requestXml) {
		if(StringUtil.isEmpty(requestXml)){
			return ConstantMsg.CK_MSG.MSG_ERROR.getCode();
		}
		try{
			header.init(requestXml);
			body.init(requestXml);
		} catch (Exception e) {
			log.error("response checkReservation Exception:" + requestXml , e);
			return ConstantMsg.CK_MSG.MSG_ERROR.getCode();
		}
		return ConstantMsg.CK_MSG.SUCCESS.getCode();
	}
	public String checkRequest(CkDeviceInfo deviceInfo, String keyWord) {
		String key = DistributionUtil.getPropertiesByKey("ckdevice.key");
		if(deviceInfo==null){
			return "1021";
		}
		try {
			String paramInfo = key + keyWord;
			String lvmamaSigned = MD5.encode(paramInfo);
			String reqeustSigned = header.getSigned();
			if (!lvmamaSigned.equals(reqeustSigned)) {
				log.info("lvmamaSigned: " + lvmamaSigned + " reqeustSigned=" + reqeustSigned);
				return "1015";// 签证验证未通过
			}
			return body.checkParams();
		} catch (NoSuchAlgorithmException e) {
			log.info("检查验证码错误: 错误原因" + e);
			return "1015";
		}
	}
	
	
	
}
