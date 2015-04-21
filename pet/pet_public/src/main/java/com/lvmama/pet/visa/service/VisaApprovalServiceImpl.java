package com.lvmama.pet.visa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.pet.po.pub.ComFSFile;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocument;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocumentDetails;
import com.lvmama.comm.pet.po.visa.VisaApproval;
import com.lvmama.comm.pet.po.visa.VisaApprovalDetails;
import com.lvmama.comm.pet.service.pub.ComFSService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.visa.VisaApplicationDocumentService;
import com.lvmama.comm.pet.service.visa.VisaApprovalService;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.visa.VisaApprovalDetailsVO;
import com.lvmama.pet.visa.dao.VisaApprovalDAO;
import com.lvmama.pet.visa.dao.VisaApprovalDetailsDAO;

public class VisaApprovalServiceImpl implements VisaApprovalService {
	@Autowired
	private VisaApplicationDocumentService visaApplicationDocumentService;
	@Autowired
	private VisaApprovalDAO visaApprovalDAO;
	@Autowired
	private VisaApprovalDetailsDAO visaApprovalDetailsDAO;
	@Autowired
	private ComLogService comLogService;
	@Autowired
	private ComFSService comFSRemoteService;
	
	@Override
	public boolean createVisaApproval(final OrdOrder order,final String productName, final String country, final String visaType, final String city, final int aheadDay) {
		if (null == order 
				|| order.getPersonList().isEmpty() 
				|| StringUtils.isBlank(country)
				|| StringUtils.isBlank(visaType)
				|| StringUtils.isBlank(city)) {
			return false;
		}
		List<VisaApproval> approvalList = new ArrayList<VisaApproval>();
		List<OrdPerson> ordPersons = order.getTravellerList();
		for (OrdPerson person : ordPersons) {
			if (Constant.ORD_PERSON_TYPE.TRAVELLER.name().equals(person.getPersonType())) {
				VisaApproval approval = new VisaApproval();
				approval.setTravelGroupCode(order.getTravelGroupCode());
				approval.setOrderId(order.getOrderId());
				approval.setVisitTime(order.getVisitTime());
				approval.setPersonId(person.getPersonId());
				approval.setName(person.getName());
				approval.setCountry(country);
				approval.setVisaType(visaType);
				approval.setCity(city);
				approval.setVisaStatus(Constant.VISA_STATUS.UNVET.name());
				approval.setProductName(productName);
				approval.setAheadDay(aheadDay);
				
				approvalList.add(approval);
			}
		}
		if (!approvalList.isEmpty()) {
			visaApprovalDAO.insert(approvalList);
		}
		return true;
	}
	
	@Override
	public void deleteApprovalByOrderId(Long orderId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		List<VisaApproval> approvals = this.query(param);
		Map<String, Object> _param = new HashMap<String, Object>();
		for (VisaApproval approval : approvals) {
			visaApprovalDetailsDAO.deleteByVisaApprovalId(approval.getVisaApprovalId());
			_param.put("visaApprovalId", approval.getVisaApprovalId());
			visaApprovalDAO.delete(_param);
		}
	}
	
	@Override
	public List<VisaApproval> query(final Map<String, Object> param) {
		return visaApprovalDAO.query(param);
	}
	
	@Override
	public Long count(final Map<String, Object> param) {
		return visaApprovalDAO.count(param);
	}

	@Override
	public VisaApproval queryByPk(Long visaApprovalId) {
		return visaApprovalDAO.queryByPk(visaApprovalId);
	}

	@Override
	public void updateOccupation(Long visaApprovalId, String occupation,
			String operatorName) {
		if (null != visaApprovalId 
				&& StringUtils.isNotBlank(occupation)) {
			visaApprovalDetailsDAO.deleteByVisaApprovalId(visaApprovalId);
			
			visaApprovalDAO.updateOccupation(visaApprovalId, occupation);
			
			VisaApproval visaApproval = visaApprovalDAO.queryByPk(visaApprovalId);
			if (null != visaApproval) {
				Map<String, Object> param = new HashMap<String,Object>();
				param.put("country", visaApproval.getCountry());
				param.put("visaType", visaApproval.getVisaType());
				param.put("city", visaApproval.getCity());
				param.put("occupation", visaApproval.getOccupation());
				
				List<VisaApplicationDocument> documents = visaApplicationDocumentService.query(param);
				if (null != documents && !documents.isEmpty()) {
					List<VisaApplicationDocumentDetails> details = visaApplicationDocumentService.queryDetailsByDocumentId(documents.get(0).getDocumentId());
					List<VisaApprovalDetails> approvalDetails = new ArrayList<VisaApprovalDetails>(details.size());
					for (VisaApplicationDocumentDetails d : details) {
						VisaApprovalDetails approvalD = new VisaApprovalDetails();
						approvalD.setVisaApprovalId(visaApprovalId);
						approvalD.setTitle(d.getTitle());
						approvalD.setContent(d.getContent());
						//给个中间态
						approvalD.setApprovalStatus("O");
						
						approvalDetails.add(approvalD);
					}
					
					if (!approvalDetails.isEmpty()) {
						visaApprovalDetailsDAO.insert(approvalDetails);
					}
				}
				
			}
			
			comLogService.insert("VISA_APPROVAL_TARGET", null, visaApprovalId, operatorName,
					Constant.COM_LOG_ORDER_EVENT.updateVisaDocumentDetails.name(),
					"更新签证材料明细", "更新了适用人群", "");			
		}		
	}

	@Override
	public void updateDeposit(Long visaApprovalId, String depositType,
			String bank, Long amount, String operatorName) {
		if (null != visaApprovalId 
				&& StringUtils.isNotBlank(depositType)) {
			visaApprovalDAO.updateDeposit(visaApprovalId, depositType, bank, amount);
			
			comLogService.insert("VISA_APPROVAL_TARGET", null, visaApprovalId, operatorName,
					Constant.COM_LOG_ORDER_EVENT.updateVisaDocumentDetails.name(),
					"更新签证材料明细", "更新了保证金", "");			
		}
		
	}

	@Override
	public void updateApprovalStatus(Long visaApprovalId, String visaStatus,
			String operatorName) {
		if (null != visaApprovalId && StringUtils.isNotBlank(visaStatus)) {
			visaApprovalDAO.updateApprovalStatus(visaApprovalId, visaStatus);
			
			comLogService.insert("VISA_APPROVAL_TARGET", null, visaApprovalId, operatorName,
					Constant.COM_LOG_ORDER_EVENT.updateVisaDocumentDetails.name(),
					"更新签证材料明细", "设置资料审核状态为" + visaStatus, "");			
		}
		
	}

	@Override
	public void returnMaterial(Long visaApprovalId, String operatorName) {
		if (null != visaApprovalId) {
			visaApprovalDAO.updateReturnMaterial(visaApprovalId);
			
			comLogService.insert("VISA_APPROVAL_TARGET", null, visaApprovalId, operatorName,
					Constant.COM_LOG_ORDER_EVENT.updateVisaDocumentDetails.name(),
					"更新签证材料明细", "设置为资料可被退还", "");			
		}	
	}

	@Override
	public void returnDeposit(Long visaApprovalId, String operatorName) {
		if (null != visaApprovalId) {
			visaApprovalDAO.updateReturnDeposit(visaApprovalId);
			
			comLogService.insert("VISA_APPROVAL_TARGET", null, visaApprovalId, operatorName,
					Constant.COM_LOG_ORDER_EVENT.updateVisaDocumentDetails.name(),
					"更新签证材料明细", "设置为保证金可被退还", "");			
		}	
	}

	@Override
	public void addSendLog(Long visaApprovalId, String content, Long pid,
			String operatorName) {
		if (null != visaApprovalId && StringUtils.isNotBlank(content)) {
			StringBuffer sb = new StringBuffer(content);
			if (null != pid) {
				ComFSFile file = comFSRemoteService.selectComFSFileById(pid);
				if (file != null) {
					sb.append("&nbsp;&nbsp;&nbsp;&nbsp;关联附件:");
					sb.append("<a href=\"/pet_back/contract/downLoad.do?path=" + pid + "\">" + file.getFileName() + "</a>");	
				}
				
			}
			comLogService.insert("VISA_APPROVAL_SENDLOG_TARGET", null, visaApprovalId, operatorName,
					Constant.COM_LOG_ORDER_EVENT.UploadVisaApprovalMaterial.name(),
					"签证审核材料上传快递", sb.toString(), "");				
		}
	}
	
	@Override
	public void addFaceLog(Long visaApprovalId, String content, Long pid,
			String operatorName) {
		if (null != visaApprovalId && StringUtils.isNotBlank(content)) {
			StringBuffer sb = new StringBuffer(content);
			if (null != pid) {
				ComFSFile file = comFSRemoteService.selectComFSFileById(pid);
				if (file != null) {
					sb.append("&nbsp;&nbsp;&nbsp;&nbsp;关联附件:");
					sb.append("<a href=\"/pet_back/contract/downLoad.do?path=" + pid + "\">" + file.getFileName() + "</a>");	
				}
				
			}
			comLogService.insert("VISA_APPROVAL_FACELOG_TARGET", null, visaApprovalId, operatorName,
					Constant.COM_LOG_ORDER_EVENT.UploadVisaApprovalMaterial.name(),
					"签证审核材料上传快递 ", sb.toString(), "");				
		}
	}	

	@Override
	public VisaApprovalDetails queryDetailsByPK(Long detailsId) {
		return visaApprovalDetailsDAO.queryByPk(detailsId);
	}

	@Override
	public List<VisaApprovalDetails> queryDetailsByApprovalId(Long approvalId) {
		if (null != approvalId) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("visaApprovalId", approvalId);
			return visaApprovalDetailsDAO.query(param);
		} else {
			return null;
		}
	}

	@Override
	public void updateDetailsApprovalStatus(Long detailsId,
			String approvalStatus, String operatorName) {
		if (null != detailsId && StringUtils.isNotBlank(approvalStatus)) {
			VisaApprovalDetails details = visaApprovalDetailsDAO.queryByPk(detailsId);
			if (null != details) {
				visaApprovalDetailsDAO.updateApprovalStatus(detailsId, approvalStatus);
				
				comLogService.insert("VISA_APPROVAL_TARGET", null, details.getVisaApprovalId(),
						operatorName,Constant.COM_LOG_ORDER_EVENT.updateVisaDocumentDetails.name(),
						"更新签证材料明细",
						operatorName + "审核了" + details.getTitle() + ",新审核状态为:" + approvalStatus,"");
			}
		}
		
	}

	@Override
	public void addDetailsMemo(Long detailsId, String content,
			String operatorName) {
		if (null != detailsId && StringUtils.isNotBlank(content)) {
			visaApprovalDetailsDAO.addMemo(detailsId, operatorName + "添加备注:" + content + "<br/>");
		}	
	}

	@Override
	public void insertVisaApprovalDetails(Long visaApprovalId, String content,
			String operatorName) {
		if (null != visaApprovalId && StringUtils.isNotBlank(content)) {
			VisaApprovalDetails details = new VisaApprovalDetails();
			details.setTitle("<font color=\"red\">增补材料</font>");
			details.setContent(content);
			details.setVisaApprovalId(visaApprovalId);
			
			visaApprovalDetailsDAO.insert(details);
			
			comLogService.insert("VISA_APPROVAL_TARGET", null, visaApprovalId, operatorName,
					Constant.COM_LOG_ORDER_EVENT.addVisaDocumentDetails.name(),
					"新增增补材料", "新增增补材料", "");
		}
		
	}

	@Override
	public VisaApprovalDetailsVO queryVerticalDetailsByApprovalId(
			Long visaApprovalId) {
		if (null != visaApprovalId ) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("visaApprovalId", visaApprovalId);
 			return visaApprovalDetailsDAO.queryVerticalDetailsByApprovalId(param);
		} else {
			return null;
		}
	}
	
}
