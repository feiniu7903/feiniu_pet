package com.lvmama.back.web.passport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

/**
 * 订单通关码监控
 * 
 * @author chenlinjun
 * 
 */
public class ListPasscodeAction extends BaseAction {
	private static final Log log = LogFactory.getLog(ListPasscodeAction.class);
	private static final long serialVersionUID = -6624749276774956154L;
	private PassCodeService passCodeService;
	private TopicMessageProducer passportMessageProducer;
	private TopicMessageProducer passportChimelongMessageProducer;
	private OrderService orderServiceProxy=(OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	protected ComLogService comLogService;
	/**
	 * 查询条件
	 */
	private Map<String, Object> queryOption = new HashMap<String, Object>();
	/**
	 * 通关码
	 */
	private List<PassCode> passCodeList;
	private PassCode passCode;
    private Long orderId;

	/**
	 * 查询
	 */
	public void doQuery() {
		if (passCodeList != null) {
			passCodeList.clear();
		}
//		if("".equals(queryOption.get("mobile"))){
//			queryOption.remove("mobile");
//		}
//		if("".equals(queryOption.get("serialNo"))){
//			queryOption.remove("serialNo");
//		}
//		if("".equals(queryOption.get("orderId"))){
//			queryOption.remove("orderId");
//		}
//		if("".equals(queryOption.get("codeId"))){
//			queryOption.remove("codeId");
//		}
//		if ("0".equals(queryOption.get("providerId"))) {
//			queryOption.remove("providerId");
//		}
		Integer totalRowCount=passCodeService.selectPassCodeRowCount(queryOption);
		_totalRowCountLabel.setValue(totalRowCount.toString()); 
		_paging.setTotalSize(totalRowCount.intValue());
		
		queryOption.put("_startRow", _paging.getActivePage()*_paging.getPageSize()+1);
		queryOption.put("_endRow", _paging.getActivePage()*_paging.getPageSize()+_paging.getPageSize());
		this.passCodeList = this.passCodeService.selectPassCodeByParams(queryOption);
	}

	public void destroyPassportCode(Long codeId) {
		PassEvent event = passCodeService.destroyCode(codeId);
		passportMessageProducer.sendMsg(MessageFactory.newPasscodeEventMessage(event.getEventId()));
		ZkMessage.showInfo("废码请求已经提交");
	}
	/**
	 * 重发短信
	 * @param codeId
	 */
	public void resend(Long codeId) {
		PassEvent event = passCodeService.resend(codeId);
		passportMessageProducer.sendMsg(MessageFactory.newPasscodeEventMessage(event.getEventId()));
		ZkMessage.showInfo("重发短信请求已经提交");
	}
	/**
	 * 重新申请码
	 */
	public void doReapply(Long codeId, boolean canReapply) {
		if (canReapply) {
			PassCode passCode=passCodeService.getPassCodeByCodeId(codeId);
			OrdOrder ordOrder = this.orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			if(ordOrder.isCanceled()){
				ZkMessage.showInfo("订单："+ordOrder.getOrderId()+"已经取消，不能做重新申码！");
				return;
			}
			boolean isSuccessCode=passCodeService.hasSuccessCode(passCode.getOrderId(),passCode.getCodeTotal());
			if(isSuccessCode){
				ZkMessage.showInfo("订单："+ordOrder.getOrderId()+"已经申请成功了，不能做重新申码！");
				return;
			}
			//查询该码号对应的服务商信息，如果重复使用申请流水号发码，则使用原来的流水号，否则生成新的
			List<PassPortCode> passport= passCodeService.searchPassPortByCodeId(passCode.getCodeId());
			PassPortCode passPortCodes = passport != null && passport.size() > 0 ? passport.get(0) : null;
			if(passport!=null){
				if(passPortCodes.isUseSameSerialNo()){
					log.info(" ListPassCodeAction Reapply old CodeId: "+codeId);
					this.passCodeService.reApplyCodeUseSameSerialNo(codeId);
					List<PassPortCode> passPortList = this.passCodeService.queryProviderByCode(codeId);
					PassPortCode passPortCode = passPortList != null && passPortList.size() > 0 ? passPortList.get(0) : null;
					String providerName = passPortCode!=null? passPortCode.getProviderName():"";
					
					if(providerName.equals("长隆")){
						passportChimelongMessageProducer.sendMsg(MessageFactory.newPasscodeApplyMessage(codeId));
					}else{
						passportMessageProducer.sendMsg(MessageFactory.newPasscodeApplyMessage(codeId));
					}
					this.addComLog(codeId);
				}else{
					Long newCodeId = passCodeService.reApplyCode(codeId);
					if (newCodeId!=null) {
						
						List<PassPortCode> passPortList = this.passCodeService.queryProviderByCode(newCodeId);
						PassPortCode passPortCode = passPortList != null && passPortList.size() > 0 ? passPortList.get(0) : null;
						String providerName = passPortCode!=null? passPortCode.getProviderName():"";
						
						if(providerName.equals("长隆")){
							passportChimelongMessageProducer.sendMsg(MessageFactory.newPasscodeApplyMessage(newCodeId));
						}else{
							passportMessageProducer.sendMsg(MessageFactory.newPasscodeApplyMessage(newCodeId));
						}
						
						this.addComLog(codeId);
					}
				}
				
			}
			ZkMessage.showInfo("重新申请已经提交");
		}else{
			ZkMessage.showInfo("该通关码不可重新申码");
		}
	}
/**
 * 添加日志
 * @param codeId
 */
	private void addComLog(Long codeId) {
		PassCode passCode=passCodeService.getPassCodeByCodeId(codeId);
		ComLog log = new ComLog();
		log.setObjectType("PASS_CODE");
		log.setParentId(passCode.getOrderId());
		log.setObjectId(passCode.getCodeId());
		log.setOperatorName(this.getSessionUserName());
		log.setLogType(Constant.COM_LOG_ORDER_EVENT.systemApprovePass.name());
		log.setLogName("重新申请通关码");
		log.setContent("重新申请通关码操作");
		comLogService.addComLog(log);
	}
	public Map<String, Object> getQueryOption() {
		return queryOption;
	}

	public List<PassCode> getPassCodeList() {
		return passCodeList;
	}

	public void setPassCodeList(List<PassCode> passCodeList) {
		this.passCodeList = passCodeList;
	}
 
	public void setQueryOption(Map<String, Object> queryOption) {
		this.queryOption = queryOption;
	}

	public PassCode getPassCode() {
		return passCode;
	}

	public void setPassCode(PassCode passCode) {
		this.passCode = passCode;
	}
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setPassportMessageProducer(
			TopicMessageProducer passportMessageProducer) {
		this.passportMessageProducer = passportMessageProducer;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setPassportChimelongMessageProducer(TopicMessageProducer passportChimelongMessageProducer) {
		this.passportChimelongMessageProducer = passportChimelongMessageProducer;
	}

}
