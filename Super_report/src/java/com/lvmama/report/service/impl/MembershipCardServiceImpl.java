package com.lvmama.report.service.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.report.dao.MembershipCardMVDAO;
import com.lvmama.report.po.MembershipCardDetailsMV;
import com.lvmama.report.service.MembershipCardService;

public class MembershipCardServiceImpl implements MembershipCardService{
	private MembershipCardMVDAO membershipCardMVDAO;
	@Override
	public Long count(Map<String, Object> parameters) {
		return membershipCardMVDAO.count(parameters);
	}

	@Override
	public List<MembershipCardDetailsMV> query(
			Map<String, Object> parameters) {
		return membershipCardMVDAO.query(parameters);
	}

	public MembershipCardMVDAO getMembershipCardMVDAO() {
		return membershipCardMVDAO;
	}

	public void setMembershipCardMVDAO(MembershipCardMVDAO membershipCardMVDAO) {
		this.membershipCardMVDAO = membershipCardMVDAO;
	}

}
