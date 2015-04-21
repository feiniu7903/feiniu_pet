package com.lvmama.pet.mark.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.service.mark.MarkMembershipCardDiscountService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.mark.MarkMembershipCardDiscountDetails;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.mark.dao.MarkMembershipCardDAO;
import com.lvmama.pet.mark.dao.MarkMembershipCardDiscountDAO;

public class MarkMembershipCardDiscountServiceImpl implements
		MarkMembershipCardDiscountService {
	@Autowired
	private MarkMembershipCardDiscountDAO markMembershipCardDiscountDAO;
	@Autowired
	private MarkMembershipCardDAO markMembershipCardDAO;
	@Autowired
	private ComLogService comLogService;
	
	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	@Override
	public List<MarkMembershipCardDiscountDetails> query(
			Map<String, Object> parameters) {
		return markMembershipCardDiscountDAO.query(parameters);
	}

	@Override
	public void delete(Map<String, Object> parameters,String operatorName) {
		Long cardId = (Long) parameters.get("cardId");
		if (null == cardId) {
			cardId = markMembershipCardDiscountDAO.query(parameters).get(0).getCardId();
		} 
		markMembershipCardDiscountDAO.delete(parameters);
		
		//更新会员卡批次是否被绑定过优惠券
		if (markMembershipCardDiscountDAO.query(parameters).size() == 0) {
			markMembershipCardDAO.updateUnBindingDiscount(cardId);
		}
		
		//panzy记录日志
		comLogService.insert("MARK_MEMBERSHIP_CARD", null, cardId, operatorName,
				Constant.MEMBERCARDSHIP.DELETE_MEMBERCARD_DISCOUNT.name(), "删除优惠券", LogViewUtil.logDeleteStr(operatorName), null);
	}

	//setter and getter
	public MarkMembershipCardDiscountDAO getMarkMembershipCardDiscountDAO() {
		return markMembershipCardDiscountDAO;
	}

	public void setMarkMembershipCardDiscountDAO(
			MarkMembershipCardDiscountDAO markMembershipCardDiscountDAO) {
		this.markMembershipCardDiscountDAO = markMembershipCardDiscountDAO;
	}

	public void setMarkMembershipCardDAO(MarkMembershipCardDAO markMembershipCardDAO) {
		this.markMembershipCardDAO = markMembershipCardDAO;
	}
}
