package com.lvmama.passport.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.UpdateOrderStatusService;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.disney.model.OrderRespose;
import com.lvmama.passport.disney.service.DisneyService;
import com.lvmama.passport.utils.WebServiceConstant;
public class UpdateOrderStatusServiceImpl implements UpdateOrderStatusService{
	private static final Log log = LogFactory.getLog(UpdateOrderStatusServiceImpl.class);
	private PassCodeService passCodeService;
	private DisneyService disneyService;
	private ComLogService comLogService;
	@Override
	
	public void updateOrderStatus() {
		String supplierId = WebServiceConstant.getProperties("disney.supplierId");
		log.info("disney.supplierId:"+supplierId);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("supplierId", supplierId);
		List<PassCode> passCodeList = passCodeService.getPassCodeBysupplierId(data);
		log.info("passCodeListbysupplierId  size:"+passCodeList.size());
		for(PassCode passCode:passCodeList){
			try{
				OrderRespose res=disneyService.getOrderStatus(passCode.getExtId());
				log.info("disneyService.getOrderStatus:"+res.getStatus());
				List<PassPortCode> passPortCodeList = this.passCodeService.queryProviderByCode(passCode.getCodeId());
				PassPortCode passPortCode = passPortCodeList.get(0);
				String status=passPortCode.getStatus();
				if(!StringUtils.equals(status,"USED") && StringUtils.equals(res.getStatus(),"CANCELLED")){
					passCode.setStatus(PassportConstant.PASSCODE_DESTROY_STATUS.DESTROYED.name());
					passCode.setFailedTime(Calendar.getInstance().getTime());
					passCodeService.successExecute(passCode, null);
					addComLog(passCode, "供应商审核通过，退票成功！", "废除通关码成功");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
		//记录回调日志信息
		private void addComLog(PassCode passCode, String logContent, String logName) {
			ComLog log = new ComLog();
			log.setObjectType("PASS_CODE");
			log.setParentId(passCode.getOrderId());
			log.setObjectId(passCode.getCodeId());
			log.setOperatorName("SYSTEM");
			log.setLogType(Constant.COM_LOG_ORDER_EVENT.cancel.name());
			log.setLogName(logName);
			log.setContent(logContent);
			comLogService.addComLog(log);
		}

		public void setPassCodeService(PassCodeService passCodeService) {
			this.passCodeService = passCodeService;
		}

		public void setDisneyService(DisneyService disneyService) {
			this.disneyService = disneyService;
		}

		public void setComLogService(ComLogService comLogService) {
			this.comLogService = comLogService;
		}

	
}
