package com.lvmama.pet.visa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.visa.VisaApplicationDocument;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocumentDetails;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.visa.VisaApplicationDocumentService;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.visa.dao.VisaApplicationDocumentDAO;
import com.lvmama.pet.visa.dao.VisaApplicationDocumentDetailsDAO;

public class VisaApplicationDocumentServiceImpl implements VisaApplicationDocumentService {
	@Autowired
	private VisaApplicationDocumentDAO visaApplicationDocumentDAO;
	@Autowired
	private VisaApplicationDocumentDetailsDAO visaApplicationDocumentDetailsDAO;
	@Autowired
	private ComLogService comLogService;
	
	@Override
	public VisaApplicationDocument insert(final String country, final String visaType, final String city, final String occupation, final String operatorName) {
		if (StringUtils.isEmpty(country) 
				|| StringUtils.isEmpty(visaType)
				|| StringUtils.isEmpty(city)
				|| StringUtils.isEmpty(occupation)) {
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("country", country);
		param.put("visaType", visaType);
		param.put("city", city);
		param.put("occupation", occupation);
		if (count(param) > 0) {
			return null;
		}
		VisaApplicationDocument document = new VisaApplicationDocument();
		document.setCountry(country);
		document.setVisaType(visaType);
		document.setCity(city);
		document.setOccupation(occupation);
		document = visaApplicationDocumentDAO.insert(document);
		
		if (null != document) {
			comLogService.insert("VISA_APP_DOCUMENT_TARGET", null, document.getDocumentId(), operatorName,
					Constant.COM_LOG_ORDER_EVENT.createVisaApplicationDocument.name(),
					"创建签证材料 ", "创建签证材料", "");
		}
		return document;
	}
	
	@Override
	public void delete(final Long documentId, final String operatorName) {
		visaApplicationDocumentDetailsDAO.deleteByDocumentId(documentId);
		visaApplicationDocumentDAO.delete(documentId);
		
		comLogService.insert("VISA_APP_DOCUMENT_TARGET", null, documentId, operatorName,
				Constant.COM_LOG_ORDER_EVENT.deleteVisaApplicationDocument.name(),
				"删除签证材料", "删除签证材料", "");
	}
	
	@Override
	public Long count(final Map<String, Object> param) {
		return visaApplicationDocumentDAO.count(param);
	}
	
	@Override
	public VisaApplicationDocument queryByPrimaryKey(final Long documentId) {
		return visaApplicationDocumentDAO.queryByPrimaryKey(documentId);
	}
	
	@Override
	public List<VisaApplicationDocument> query(final Map<String, Object> param) {
		return visaApplicationDocumentDAO.query(param);
	}
	
	@Override
	public VisaApplicationDocumentDetails insert(final VisaApplicationDocumentDetails details, final String operatorName) {
		if (null != details 
				&& null != details.getDocumentId()
				&& StringUtils.isNotBlank(details.getTitle())
				&& StringUtils.isNotBlank(details.getContent())) {
			VisaApplicationDocumentDetails _details = visaApplicationDocumentDetailsDAO.insert(details);
			if (null != _details) {
				comLogService.insert("VISA_APP_DOCUMENT_TARGET", null, details.getDocumentId(), operatorName,
						Constant.COM_LOG_ORDER_EVENT.createVisaDocumentDetails.name(),
						"创建签证材料明细 ", "创建签证材料明细【" + details.getTitle() + "】", "");	
			}
			return _details;
		} else {
			return null;
		}
	}
	
	@Override
	public VisaApplicationDocumentDetails update(final VisaApplicationDocumentDetails details, final String operatorName) {
		if (null != details 
				&& null != details.getDetailsId()
				&& null != details.getDocumentId()
				&& StringUtils.isNotBlank(details.getTitle())
				&& StringUtils.isNotBlank(details.getContent())) {
			VisaApplicationDocumentDetails _detail = visaApplicationDocumentDetailsDAO.update(details);
			comLogService.insert("VISA_APP_DOCUMENT_TARGET", null, details.getDocumentId(), operatorName,
					Constant.COM_LOG_ORDER_EVENT.updateVisaDocumentDetails.name(),
					"更新签证材料明细", "更新签证材料明细【" + details.getTitle() + "】", "");	
			return _detail;
		} else {
			return null;
		}		
	}
	
	@Override
	public List<VisaApplicationDocumentDetails> queryDetailsByDocumentId(final Long documentId) {
		if (null != documentId) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("documentId", documentId);
			return visaApplicationDocumentDetailsDAO.query(param);
		} else {
			return new ArrayList<VisaApplicationDocumentDetails>(0);
		}	
	}
	
	@Override
	public VisaApplicationDocument copy(final Long documentId, final VisaApplicationDocument document, final String operatorName) {
		VisaApplicationDocument srcDocument = queryByPrimaryKey(documentId);
		if (null == srcDocument) {
			return null;
		}
		VisaApplicationDocument targetDocument = insert(document.getCountry(), document.getVisaType(), document.getCity(), document.getOccupation(), operatorName);
		if (null != targetDocument) {
			visaApplicationDocumentDetailsDAO.copy(documentId, targetDocument.getDocumentId()); 	
		}
		return targetDocument;
	}
	
	@Override
	public void deleteDetails(Long detailsId, final String operatorName) {
		if (null != detailsId) {
			VisaApplicationDocumentDetails details = visaApplicationDocumentDetailsDAO.queryByPrimaryKey(detailsId);
			if (null != details) {
				visaApplicationDocumentDetailsDAO.delete(detailsId);
				comLogService.insert("VISA_APP_DOCUMENT_TARGET", null, details.getDocumentId(), operatorName,
						Constant.COM_LOG_ORDER_EVENT.deleteVisaDocumentDetails.name(),
						"删除签证材料 ", "删除签证材料明细【" + details.getTitle() + "】", "");				
			}	
		}
	}
}
