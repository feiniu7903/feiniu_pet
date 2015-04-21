package com.lvmama.passport.service.impl;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.work.PublicWorkOrderService;
import com.lvmama.comm.pet.vo.InvokeResult;
import com.lvmama.comm.pet.vo.WorkOrderCreateParam;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.service.ProcessorCallbackService;

/**
 * 废码回调实现
 * 
 * @author chenlinjun
 * @date:2010-9-25
 */
public class DestroyCodeProcessorCallbackService extends ProcessorCallbackService {
	private static Log log = LogFactory.getLog(DestroyCodeProcessorCallbackService.class);
	private PublicWorkOrderService publicWorkOrderService;
	@Override
	public boolean successExecute(PassCode passCode, Passport passport) {
		// add by zhangwengang 2013/08/30 新增废码时间 start
		passCode.setFailedTime(Calendar.getInstance().getTime());
		passCodeService.updatePassCode(passCode);
		// add by zhangwengang 2013/08/30 新增废码时间 end
		super.addComLog(passCode, "废码成功", "废除通关码成功");
		return this.passCodeService.successExecute(passCode, passport);
	}
	
	@Override
	protected boolean errorExecute(PassCode passCode, Passport passport, PassEvent passEvent) {
		// add by zhangwengang 2013/08/30 新增废码时间 start
		passCode.setFailedTime(Calendar.getInstance().getTime());
		passCodeService.updatePassCode(passCode);
		// add by zhangwengang 2013/08/30 新增废码时间 end
		passCode.setStatus(PassportConstant.PASSCODE_DESTROY_STATUS.UNDESTROYED.name());
		passCodeService.updatePassCodeBySerialNo(passCode);
		
		passEvent.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
		passCodeService.updateEventStauts(passEvent);
		super.addComLog(passCode, passport.getComLogContent(), "废除通关码失败");
		if(!publicWorkOrderService.isExistsForPassport(passCode.getOrderId(), Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EWMFMSB.getWorkOrderTypeCode(), Constant.WORK_ORDER_STATUS.UNCOMPLETED.getCode())){
			//发送废码失败工单
			WorkOrderCreateParam param = new WorkOrderCreateParam();
			param.setLimitTime(480L);
			param.setOrderId(passCode.getOrderId());
			param.setProductId(null);
			param.setUrl("/super_back/passport/list_passcode.zul");
			param.setVisitorUserName(null);
			param.setWorkOrderContent("订单号："+passCode.getOrderId());
			param.setWorkTaskContent("订单号：" + passCode.getOrderId());
			param.setWorkOrderTypeCode(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EWMFMSB.getWorkOrderTypeCode());
			InvokeResult result = publicWorkOrderService.createWorkOrder(param);
			if(result.getCode() > 0){
				ComLog log = new ComLog();
				log.setObjectType("WORK_ORDER");
				log.setParentId(null);
				log.setObjectId(passCode.getCodeId());
				log.setOperatorName("SYSTEM");
				log.setLogType("WORK_ORDER_SEND_ERROR");
				log.setLogName("WORK_ORDER_SEND_ERROR");
				log.setContent("发送废码失败工单异常,passcode:"+passCode.getCodeId()
						+",订单号:"+passCode.getOrderId()+",error："+result.getDescription());
				comLogService.addComLog(log);
			}
		}else{
			log.info("work order is exists,orderId="+passCode.getOrderId()+", work_order_type_code="+Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EWMFMSB.getWorkOrderTypeCode());
		}
		return false;
	}

	public PublicWorkOrderService getPublicWorkOrderService() {
		return publicWorkOrderService;
	}

	public void setPublicWorkOrderService(
			PublicWorkOrderService publicWorkOrderService) {
		this.publicWorkOrderService = publicWorkOrderService;
	}
}
