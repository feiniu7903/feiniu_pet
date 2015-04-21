package com.lvmama.passport.callback.http;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import com.lvmama.BackBaseAction;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.passport.processor.UsedCodeProcessor;

/**
 * 智游宝回调驴妈妈接口实现
 * @author gaoxin
 *
 */
public class ZhiyoubaoCallbackAction extends BackBaseAction{
	private static final long serialVersionUID = -3227406740860332975L;
	private PassCodeService passCodeService;
	private UsedCodeProcessor usedCodeProcessor;
	private String order_no;
	private String status;
	private String sign;
	
	@Action("/TianmuhuCallback")
	public void performCallback() {
		log.info("order_no: " + order_no);
		log.info("sign:"+sign);
		log.info("status:"+status);
		String result = "error";
			if(StringUtils.equals(status,"success")){
				if (order_no != null) {
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("serialNo", order_no.trim());
					PassCode passCode = passCodeService.getPassCodeByParams(data);
					if (passCode != null) {
						List<PassPortCode> passPortCodeList = this.passCodeService.queryProviderByCode(passCode.getCodeId());
						PassPortCode passPortCode = passPortCodeList.get(0);
						if (passPortCode != null) {
							// 履行对象
							Long targetId = passPortCode.getTargetId();
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("targetId", targetId);
							List<PassDevice> passDeviceList = this.passCodeService.searchPassDevice(params);
							Passport passport = new Passport();
							passport.setSerialno(passCode.getSerialNo());
							passport.setPortId(targetId);
							passport.setOutPortId(targetId.toString());
							if (passDeviceList != null && passDeviceList.size() > 0) {
								passport.setDeviceId(passDeviceList.get(0).getDeviceNo().toString());
							}
							passport.setChild("0");
							passport.setAdult("0");
							passport.setUsedDate(new Date());
							// 更新履行状态
							String code = usedCodeProcessor.update(passport);
							if ("SUCCESS".equals(code)) {
								result = "success";
							}
						}
					}
				}
			}else if(StringUtils.equals(status,"cancle")){
				result = "success";
			}
		log.info("result: " + result);
		sendAjaxMsg(result);
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
 
	public void setUsedCodeProcessor(UsedCodeProcessor usedCodeProcessor) {
		this.usedCodeProcessor = usedCodeProcessor;
	}
 
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setStatus(String status) {
		this.status = status;
	}
 
}
