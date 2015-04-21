package com.lvmama.passport.callback.http;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import com.lvmama.BackBaseAction;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.passport.processor.UsedCodeProcessor;
import com.lvmama.passport.processor.impl.client.shouxihu.ShouxihuUtil;
import com.lvmama.passport.processor.impl.client.shouxihu.model.SXHRequestInfo;

/**
 * 瘦西湖回调驴妈妈
 * @author tang_jing
 * @date 2013-12-04
 */

public class ShouxihuCallBackAction extends BackBaseAction {
	private static final long serialVersionUID = 1L;
	private PassCodeService passCodeService;
	private UsedCodeProcessor usedCodeProcessor;
	private String requestXml;
	
	/**
	 * 针对送达报文解析
	 */
	@Action("/Shouxihu/transSend")
	public void responseTransCheck() {
		try {
			log.info("Shouxihu CallBack requestXml:" + this.requestXml);
			SXHRequestInfo response = ShouxihuUtil.callBackInfo(requestXml);
			String orderId = response.getBody().getSerialId();
			log.info("Shouxihu CallBack ,orderID = :" + orderId);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("serialNo", orderId.trim());
			PassCode passCode = passCodeService.getPassCodeByParams(data);
			if (passCode != null) {
				List<PassPortCode> passPortCodeList = passCodeService
						.queryProviderByCode(passCode.getCodeId());
				PassPortCode passPortCode = passPortCodeList.get(0);
				if (passPortCode != null) {
					String status = response.getBody().getOrderStatus();
					if (StringUtils.equals(status, "E")) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String usedDateStr = response.getBody().getTicketDate();
						Date usedDate = sdf.parse(usedDateStr);
						// 履行对象
						Long targetId = passPortCode.getTargetId();
						Passport passport = new Passport();
						passport.setSerialno(passCode.getSerialNo());
						passport.setPortId(targetId);
						passport.setOutPortId(targetId.toString());
						passport.setDeviceId("Shouxihu");
						passport.setChild("0");
						passport.setAdult("0");
						passport.setUsedDate(usedDate);
						// 更新履行状态
						usedCodeProcessor.update(passport);
					}
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
 
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setUsedCodeProcessor(UsedCodeProcessor usedCodeProcessor) {
		this.usedCodeProcessor = usedCodeProcessor;
	}

	public String getRequestXml() {
		return requestXml;
	}

	public void setRequestXml(String requestXml) {
		this.requestXml = requestXml;
	}
}
