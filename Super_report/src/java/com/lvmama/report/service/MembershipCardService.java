package com.lvmama.report.service;

import java.util.List;
import java.util.Map;

import com.lvmama.report.po.MembershipCardDetailsMV;


public interface MembershipCardService {
	/**
	 * 计数
	 * @param parameters
	 * @return
	 */
	public Long count(final Map<String,Object> parameters); 
	/**
	 * 查询
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MembershipCardDetailsMV> query(final Map<String,Object> parameters); 
}
