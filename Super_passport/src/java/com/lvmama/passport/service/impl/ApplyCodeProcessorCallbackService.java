package com.lvmama.passport.service.impl;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.work.PublicWorkOrderService;
import com.lvmama.comm.pet.vo.InvokeResult;
import com.lvmama.comm.pet.vo.WorkOrderCreateParam;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.service.ProcessorCallbackService;
/**
 * 申请回调实现
 * @author chenlinjun
 * @date:2010-9-25 
 */
public class ApplyCodeProcessorCallbackService extends ProcessorCallbackService {
	private static Log log = LogFactory.getLog(ApplyCodeProcessorCallbackService.class);
	private PublicWorkOrderService publicWorkOrderService;
	private OrderService orderServiceProxy;
	@Override
	public boolean successExecute(PassCode passCode, Passport passport) {
		passCode.setAddCode(passport.getAddCode());
		String addCodeMd5=passport.getAddCodeMd5()==null?"":passport.getAddCodeMd5();
		passCode.setAddCodeMd5(addCodeMd5);
		passCode.setUpdateTime(new Date());
		passCode.setCode(passport.getCode());
		passCode.setStatus(passport.getStatus());
		passCode.setExtId(passport.getExtId()==null?"":passport.getExtId());
		passCode.setSendSms(passport.getSendSms());
		passCode.setSendOrderid(String.valueOf(passport.isSendOrderid()));
		passCode.setStatusNo(null);
		passCode.setReapplyTime(null);
		passCode.setStatusExplanation(passport.getMessageWhenApplySuccess());
		if(passport.getCodeImage()!=null){
			passCode.setCodeImage(passport.getCodeImage());
		}
		log.info(passCode.toString());
		super.addComLog(passCode, "申码成功", "申码成功");
		//更新通关码信息
		this.passCodeService.updatePassCodeBySerialNo(passCode);
		return true;
	}
	
	@Override
	protected boolean errorExecute(PassCode passCode, Passport passport, PassEvent passEvent) {
		passCode.setStatus(passport.getStatus());
		passCode.setStatusNo(PassportConstant.PASSCODE_ERROR.APPLY.name());
		passCode.setStatusExplanation(passport.getZhErrorMessage());
		if(passport.getReapplyTime()!=null){
			passCode.setReapplyTime(passport.getReapplyTime());
		}
		passCodeService.updatePassCodeBySerialNo(passCode);
		super.addComLog(passCode, passport.getComLogContent(), "申请通关码失败");
//		if(!publicWorkOrderService.isExistsForPassport(passCode.getOrderId(), Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EWMSMSB.getWorkOrderTypeCode(), Constant.WORK_ORDER_STATUS.UNCOMPLETED.getCode())){
		// --------------------add by zhushuying
		OrdOrder ordOrder= orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		//二维码申码失败工单，当满足订单取消或是订单过了游玩日期的
		//工单完成之后，如果二维码还是申码失败的，都不再发工单
		if(ordOrder!=null && 
				!Constant.ORDER_STATUS.CANCEL.name().equals(ordOrder.getOrderStatus()) 
				&& !publicWorkOrderService.isExistsForPassport(passCode.getOrderId(), Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EWMSMSB.getWorkOrderTypeCode(), Constant.WORK_ORDER_STATUS.UNCOMPLETED.getCode())){
			if(!DateUtil.isCompareTime(ordOrder.getVisitTime(), new Date())){//-----------end by zhushuying
				//发送申码失败工单
				WorkOrderCreateParam param = new WorkOrderCreateParam();
				param.setLimitTime(480L);
				param.setOrderId(passCode.getOrderId());
				param.setProductId(null);
				param.setUrl("/super_back/passport/list_passcode.zul");
				param.setVisitorUserName(null);
				param.setWorkOrderContent("订单号："+passCode.getOrderId());
				param.setWorkTaskContent("订单号：" + passCode.getOrderId());
				param.setWorkOrderTypeCode(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EWMSMSB.getWorkOrderTypeCode());
				InvokeResult result = publicWorkOrderService.createWorkOrder(param);
				if(result.getCode() > 0){
					ComLog log = new ComLog();
					log.setObjectType("WORK_ORDER");
					log.setParentId(null);
					log.setObjectId(passCode.getCodeId());
					log.setOperatorName("SYSTEM");
					log.setLogType("WORK_ORDER_SEND_ERROR");
					log.setLogName("WORK_ORDER_SEND_ERROR");
					log.setContent("发送申码失败工单异常,passcode:"+passCode.getCodeId()
							+",订单号:"+passCode.getOrderId()+",error："+result.getDescription());
					comLogService.addComLog(log);
				}
			}
		}else{
			log.info("work order is exists,orderId="+passCode.getOrderId()+", work_order_type_code="+Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EWMSMSB.getWorkOrderTypeCode());
		}
		return true;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public PublicWorkOrderService getPublicWorkOrderService() {
		return publicWorkOrderService;
	}

	public void setPublicWorkOrderService(
			PublicWorkOrderService publicWorkOrderService) {
		this.publicWorkOrderService = publicWorkOrderService;
	}
}
