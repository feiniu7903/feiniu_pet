package com.lvmama.report.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.MembershipCardDetailsMV;


public class MembershipCardMVDAO extends BaseIbatisDAO  {
	/**
	 * 计数
	 * @param parameters
	 * @return
	 */
	public Long count(final Map<String,Object> parameters) {
		return (Long) super.queryForObject("MARK_MEMBERSHIP_CARD_MV.count", parameters);
	}
	
	/**
	 * 查询
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MembershipCardDetailsMV> query(final Map<String,Object> parameters) {
		return super.queryForList("MARK_MEMBERSHIP_CARD_MV.query", parameters);
	}
}
