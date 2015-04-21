package com.lvmama.passport.callback.ws.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassDeviceService;
import com.lvmama.comm.bee.service.pass.PassPortCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.passport.callback.ws.IPassportPerformCallbackService;
import com.lvmama.passport.processor.UsedCodeProcessor;

/**
 * 第三方回调驴妈妈接口实现
 * @author lipengcheng
 *
 */
public class PassportPerformCallbackService implements IPassportPerformCallbackService {

	private PassCodeService passCodeService;
	private UsedCodeProcessor usedCodeProcessor;
	private static final Log log = LogFactory.getLog(PassportPerformCallbackService.class);
	public String performCallback(String orderId,String date,String adultQuantity , String childQuantity) {
		log.info("orderId: " + orderId);
		log.info("date: " + date);
		log.info("adultQuantity: " + adultQuantity);
		log.info("childQuantity: " + childQuantity);
		String result = "0";
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("extId", orderId.trim());
		PassCode passCode = passCodeService.getPassCodeByParams(data);
		if (passCode != null) {
			List<PassPortCode> passPortCodeList = this.passCodeService.queryProviderByCode(passCode.getCodeId());
			PassPortCode passPortCode = passPortCodeList.get(0);
			if (passPortCode != null) {
				// 履行对象
				Long targetId = passPortCode.getTargetId();
				Map<String , Object> params = new HashMap<String ,Object>();
				params.put("targetId", targetId);
				List<PassDevice> passDeviceList = this.passCodeService.searchPassDevice(params);
				Passport passport = new Passport();
				passport.setSerialno(passCode.getSerialNo());
				passport.setPortId(targetId);
				passport.setOutPortId(targetId.toString());
				if(passDeviceList!=null && passDeviceList.size()>0){
					passport.setDeviceId(passDeviceList.get(0).getDeviceNo().toString());
				}
				passport.setChild(childQuantity);
				passport.setAdult(adultQuantity);
				passport.setUsedDate(DateUtil.toDate(date, "yyyyMMddHHmmss"));

				// 更新履行状态
				String code = usedCodeProcessor.update(passport);
				if ("SUCCESS".equals(code)) {
					result = "1";
				}
			}
		}
		return result;
	}
	
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setUsedCodeProcessor(UsedCodeProcessor usedCodeProcessor) {
		this.usedCodeProcessor = usedCodeProcessor;
	}
 
}
