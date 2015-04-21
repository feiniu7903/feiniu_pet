package com.lvmama.passport.callback.http;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.lvmama.BackBaseAction;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassPortCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.passport.processor.UsedCodeProcessor;
import com.lvmama.passport.processor.impl.client.ylgl.YongliguolvUtil;
import com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransCheck;
import com.lvmama.passport.processor.impl.client.ylgl.model.ResponseTransSended;


/**
 * 永利国旅回调驴妈妈
 * @author lipengcheng
 * @date 2011-11-8
 */

@ParentPackage("json-default")
public class YongliguolvCallBackAction extends BackBaseAction {
	private static final long serialVersionUID = 1L;
	private PassCodeService passCodeService;
	private UsedCodeProcessor usedCodeProcessor;
	private String xmlTransSended;
	private String xmlTransCheck;
	
	/**
	 * 针对交易送达报文解析
	 */
	@Action("/yongliguolv/transSended")
	public void responseTransSended() {
		log.info("Yongliguolv CallBack transSended xml:"+this.xmlTransSended);
		ResponseTransSended responseTransSended = YongliguolvUtil.getTransSendedReq(xmlTransSended);
		log.info("Yongliguolv CallBack ,orderID = :" + responseTransSended.getErDeliverReportSyncRequest().getSpSeq());
	}	
	
	
	/**
	 * 针对交易验证报文解析
	 */
	@Action("/yongliguolv/transCheck")
	public void responseTransCheck() {
		log.info("Yongliguolv CallBack transCheck xml:"+this.xmlTransCheck);
		ResponseTransCheck responseTransCheck = YongliguolvUtil.getTransCheckReq(xmlTransCheck);
		log.info("Yongliguolv CallBack ,orderID = :" + responseTransCheck.getSpSeq());
		String orderId = responseTransCheck.getSpSeq();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("serialNo", orderId.trim());
		PassCode passCode = passCodeService.getPassCodeByParams(data);
		if(passCode!=null){
			List<PassPortCode> passPortCodeList = passCodeService.queryProviderByCode(passCode.getCodeId());
			PassPortCode passPortCode = passPortCodeList.get(0);
			if (passPortCode != null) {
				// 履行对象
				Long targetId = passPortCode.getTargetId();
				Passport passport = new Passport();
				passport.setSerialno(passCode.getSerialNo());
				passport.setPortId(targetId);
				passport.setOutPortId(targetId.toString());
				passport.setDeviceId("Yongliguolv");
				passport.setChild("0");
				passport.setAdult("0");
				passport.setUsedDate(new Date());
				// 更新履行状态
				usedCodeProcessor.update(passport);
			}
		}
	}
 
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setUsedCodeProcessor(UsedCodeProcessor usedCodeProcessor) {
		this.usedCodeProcessor = usedCodeProcessor;
	}

	public String getXmlTransSended() {
		return xmlTransSended;
	}

	public void setXmlTransSended(String xmlTransSended) {
		this.xmlTransSended = xmlTransSended;
	}

	public String getXmlTransCheck() {
		return xmlTransCheck;
	}

	public void setXmlTransCheck(String xmlTransCheck) {
		this.xmlTransCheck = xmlTransCheck;
	}




}
