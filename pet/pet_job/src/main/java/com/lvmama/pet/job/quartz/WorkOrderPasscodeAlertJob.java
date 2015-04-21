package com.lvmama.pet.job.quartz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.pet.service.work.PublicWorkOrderService;
import com.lvmama.comm.pet.vo.InvokeResult;
import com.lvmama.comm.pet.vo.WorkOrderCreateParam;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

public class WorkOrderPasscodeAlertJob implements Runnable{
	private static Log log = LogFactory.getLog(WorkOrderPasscodeAlertJob.class);
	@Autowired
	private PublicWorkOrderService publicWorkOrderService;
	private OrderService orderServiceProxy;
	@Autowired
	private PassCodeService passCodeService;	
	public void run(){
		if(Constant.getInstance().isJobRunnable()){
			log.info("Auto WorkOrderPasscodeAlertJob running.....");
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("status", Constant.PASSCODE_STATUS.NOHANDL.getCode());
			params.put("createTimeEnd", new Date(System.currentTimeMillis()-1000*600));
			List<PassCode> pcList=passCodeService.queryPassCodeByParam(params);
			if(pcList!=null && pcList.size()>0){
				for(PassCode passCode:pcList){
					try{
						createWorkOrder(passCode);
					}catch(Exception ex){
						log.error(ex);
					}
				}
			}
			log.info("Auto WorkOrderPasscodeAlertJob end.....");
		}
	}
	private void createWorkOrder(PassCode passCode){
		OrdOrder ordOrder= orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		//二维码申码未处理工单，当满足订单取消或是订单过了游玩日期的
		//工单完成之后，如果二维码还是申码未处理的，都不再发工单
		if(ordOrder!=null && 
				!Constant.ORDER_STATUS.CANCEL.name().equals(ordOrder.getOrderStatus()) 
				&& !publicWorkOrderService.isExistsForPassport(passCode.getOrderId(), Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EWMSMWCL.getWorkOrderTypeCode(), Constant.WORK_ORDER_STATUS.UNCOMPLETED.getCode())){
			if(!DateUtil.isCompareTime(ordOrder.getVisitTime(), new Date())){
				//发送申码失败工单
				WorkOrderCreateParam param = new WorkOrderCreateParam();
				param.setOrderId(passCode.getOrderId());
				param.setWorkOrderContent("订单号："+passCode.getOrderId());
				param.setWorkTaskContent("订单号：" + passCode.getOrderId());
				param.setWorkOrderTypeCode(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EWMSMWCL.getWorkOrderTypeCode());
				InvokeResult ivokeresult = publicWorkOrderService.createWorkOrder(param);
				if(ivokeresult!=null &&ivokeresult.getCode() == 0){ 
					log.info("success created work order:workOrderId="+ivokeresult.getWorkOrderId()+", workTaskId="+ivokeresult.getWorkTaskId());
				}else{
					if(ivokeresult!=null)
					{
						log.error(ivokeresult.getDescription());
					}
				}
			}
		}
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
}
