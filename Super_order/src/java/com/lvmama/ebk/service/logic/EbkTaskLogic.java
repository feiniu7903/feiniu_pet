package com.lvmama.ebk.service.logic;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkFaxTask;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.ebk.dao.EbkFaxTaskDAO;
import com.lvmama.ebk.dao.EbkTaskDAO;

public class EbkTaskLogic {
	private static final Log log=LogFactory.getLog(EbkTaskLogic.class);
	private EbkFaxTaskDAO ebkFaxTaskDAO;
	private EbkTaskDAO ebkTaskDAO;
	
	public EbkFaxTask craeteFaxTask(EbkCertificate ebkCertificate, OrdOrder ordOrder,AbstractEbkCertificateSet set) {
		EbkFaxTask faxTask = ebkFaxTaskDAO.getByEbkCertificateId(ebkCertificate.getEbkCertificateId());
		log.info("createFaxTask::::"+ebkCertificate.getEbkCertificateId()+"     "+ordOrder.getOrderId());
		if(faxTask == null) {
			faxTask = new EbkFaxTask();
			faxTask.setEbkCertificateId(ebkCertificate.getEbkCertificateId());
			faxTask.setCreateTime(new Date());
			faxTask.setSendCount(0L);
			faxTask.setSendTime(null);
			faxTask.setSendStatus(Constant.EBK_FAX_TASK_STATUS.FAX_SEND_STATUS_DEFAULT.getStatus());
			Constant.FAX_STRATEGY strategy = Constant.FAX_STRATEGY.valueOf(ebkCertificate.getFaxStrategy());
			boolean autoSend = false;
			Date planTime = null;
			if(set.hasEbkChangeToFax()) {
				strategy = Constant.FAX_STRATEGY.IMMEDIATELY;
			} else if(ebkCertificate.hasEbkCertificateTypeChange()){
				// 当订单有最晚取消时间时，如果当前时间在(最晚取消时间-1h)前，则以（最晚取消时间-1h）发送；如果修改时间是在（最晚取消时间-1h）后，则生成后立即发送
				// 当订单无最晚取消时间时，则立即发送
				// 当订单的结算单价、结算总价发生变更时，变更单生成并立即发送。 需检查在此之前是否有游玩人信息或特殊要求的变更单还未发送，若有，则合并后立即发送，若无，则变更单立即发送 
				 if(ordOrder.isHasLastCancelTime() && set.hasOrderModifyPerson()){
					 Date lastCancelTime = DateUtil.DsDay_Hour(ordOrder.getLastCancelTime(), -1);
					 if(new Date().before(lastCancelTime)){
						 //发送时间
						 planTime = lastCancelTime;
					 }else {
						 strategy = Constant.FAX_STRATEGY.IMMEDIATELY;
					}
				 }else {
					 strategy = Constant.FAX_STRATEGY.IMMEDIATELY;
				}
			}else if(ebkCertificate.hasEbkCertificateTypeCancel()){
			      strategy = Constant.FAX_STRATEGY.IMMEDIATELY;
			}
			if(!strategy.equals(Constant.FAX_STRATEGY.MANUAL_SEND)){
				autoSend = true;//自动发送
			}
			if(planTime == null){
				planTime = strategy.sendTime(ebkCertificate.getVisitTime());
			}
			faxTask.setAutoSend(Boolean.toString(autoSend));
			faxTask.setPlanTime(planTime);
			ebkFaxTaskDAO.insertEbkFaxTask(faxTask);
			log.info("createFaxTask:::: insert "+faxTask.getEbkFaxTaskId());
		}
		return faxTask;
	}
	
	public EbkFaxTask updateEbkFaxTask(EbkCertificate ec, OrdOrder ordOrder,AbstractEbkCertificateSet set){
		EbkFaxTask faxTask = ebkFaxTaskDAO.getByEbkCertificateId(ec.getEbkCertificateId());
		if(faxTask != null && !faxTask.hasFaxSended()){
			Date planTime = Constant.FAX_STRATEGY.IMMEDIATELY.sendTime(ec.getVisitTime());
			faxTask.setPlanTime(planTime);
			ebkFaxTaskDAO.updateEbkFaxTask(faxTask);
		}
		return faxTask;
	}
	
	public EbkTask craeteEbkTask(EbkCertificate ebkCertificate, OrdOrder ordOrder, OrdOrderItemMeta ooim) {
		EbkTask ebkTask = ebkTaskDAO.selectByEbkCertificateId(ebkCertificate.getEbkCertificateId());
		if(ebkTask == null) {
			ebkTask = new EbkTask();
			ebkTask.setEbkCertificateId(ebkCertificate.getEbkCertificateId());
			ebkTask.setCreateTime(new Date());
			ebkTask.setOrderId(ordOrder.getOrderId());
			ebkTask.setOrderCreateTime(ordOrder.getCreateTime());
			ebkTask.setOrderStatus(ordOrder.getOrderStatus());
			ebkTask.setPaymentStatus(ordOrder.getPaymentStatus());
			ebkTask.setResourceConfirm(ooim.getResourceConfirm());
			ebkTask.setRoomQuantity(ooim.getRoomQuantity());
			ebkTask.setTravellerName(ordOrder.getTravellerList().get(0).getName());
			ebkTaskDAO.insert(ebkTask);
		}
		return ebkTask;
	}

	public void setEbkFaxTaskDAO(EbkFaxTaskDAO ebkFaxTaskDAO) {
		this.ebkFaxTaskDAO = ebkFaxTaskDAO;
	}

	public void setEbkTaskDAO(EbkTaskDAO ebkTaskDAO) {
		this.ebkTaskDAO = ebkTaskDAO;
	}
	
}
