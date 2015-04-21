package com.lvmama.pet.mark.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.mark.MarkMembershipCard;
import com.lvmama.comm.pet.po.mark.MarkMembershipCardCode;
import com.lvmama.comm.pet.service.mark.MarkMembershipCardCodeService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.mark.dao.MarkMembershipCardCodeDAO;
import com.lvmama.pet.mark.dao.MarkMembershipCardDAO;

public class MarkMembershipCardCodeServiceImpl implements
		MarkMembershipCardCodeService {
	@Autowired
	private MarkMembershipCardDAO markMembershipCardDAO;
	@Autowired
	private MarkMembershipCardCodeDAO markMembershipCardCodeDAO;
	@Autowired
	private ComLogService comLogService;

	@Override
	public void insertByBatch(final String cardPrefixNumber,final Long number, final Set<String> codes,String operatorName) {
		Date now = new Date();
		MarkMembershipCard card = new MarkMembershipCard();
		card.setCardPrefixNumber(cardPrefixNumber);
		card.setAmount(number);
		card.setCreateTime(now);
		markMembershipCardDAO.insert(card);
		//panzy记录日志
		comLogService.insert("MARK_MEMBERSHIP_CARD", null, card.getCardId(), operatorName,
				Constant.MEMBERCARDSHIP.CREATE_MEMBERCARD.name(), "创建会员卡", LogViewUtil.logNewStr(operatorName), null);
		
		Set<MarkMembershipCardCode> cardCodes = new HashSet<MarkMembershipCardCode>(codes.size());
		for (String code : codes) {
			MarkMembershipCardCode membershipCardCode = new MarkMembershipCardCode();
			membershipCardCode.setCardCode(code);
			membershipCardCode.setCardId(card.getCardId());
			membershipCardCode.setCreateTime(now);
			membershipCardCode.setUsed("FALSE");
			cardCodes.add(membershipCardCode);
		}
		markMembershipCardCodeDAO.insertByBatch(cardCodes);
		//panzy记录日志
		comLogService.insert("MARK_MEMBERSHIP_CARD", null, card.getCardId(), operatorName,
				Constant.MEMBERCARDSHIP.CREATE_MEMBERCARD_BATCH.name(), "创建批次", LogViewUtil.logNewStr(operatorName), null);
	}

	@Override
	public List<MarkMembershipCardCode> query(final
			Map<String, Object> parameters) {
		return markMembershipCardCodeDAO.query(parameters);
	}

	@Override
	public Long count(final Map<String, Object> parameters) {
		return markMembershipCardCodeDAO.count(parameters);
	}
	
}
