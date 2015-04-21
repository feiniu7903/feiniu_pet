package com.lvmama.passport.callback.http;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import com.lvmama.BackBaseAction;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.passport.processor.UsedCodeProcessor;

/**
 * 杭州野生动物园回调
 * @author lipengcheng
 *
 */
@ParentPackage("json-default")
public class HangzhouZoomCallBackAction extends BackBaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customerid;//驴妈妈订单号
	private String orderid;//供应商订单ID
	private String entertime;//领票时间
	private PassCodeService passCodeService;
	private UsedCodeProcessor usedCodeProcessor;
	private String result="error";
	
	/**
	 * 通过订单ID完成回调
	 * 返回ok或error
	 */
	@Action("/hangzhouzoom/callback")
	public void hangzhouzoomCallback() {
		try {
			log.info("customerid:"+customerid);
			log.info("orderid:"+orderid);
			log.info("entertime:"+entertime);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("code", orderid.trim());
			PassCode passCode = passCodeService.getPassCodeByParams(data);
			List<PassPortCode> passPortCodeList = passCodeService.queryProviderByCode(passCode.getCodeId());
			PassPortCode passPortCode = passPortCodeList.get(0);
			log.info("passPortCode:"+passPortCode);
			if (passPortCode != null) {
				// 履行对象
				Long targetId = passPortCode.getTargetId();
				Passport passport = new Passport();
				passport.setSerialno(passCode.getSerialNo());
				passport.setPortId(targetId);
				passport.setOutPortId(targetId.toString());
				passport.setDeviceId("HangzhouZoom");
				passport.setChild("0");
				passport.setAdult("0");
				passport.setUsedDate(DateUtils.parseDate(entertime, new String[]{ "yyyy/MM/dd hh:mm:ss"}));

				// 更新履行状态
				String code = usedCodeProcessor.update(passport);
				if ("SUCCESS".equals(code)) {
					result = "ok";
				}
			}

			sendAjaxMsg(result);
		} catch (Exception e) {
			log.error(" hangzhouzoomCallback Exception:"+e.getMessage());
			sendAjaxMsg(result);
		}
	}
	
	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getEntertime() {
		return entertime;
	}

	public void setEntertime(String entertime) {
		this.entertime = entertime;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
 

	public void setUsedCodeProcessor(UsedCodeProcessor usedCodeProcessor) {
		this.usedCodeProcessor = usedCodeProcessor;
	}

	public String getResult() {
		return result;
	}
	

}
