package com.lvmama.comm.pet.service.mark;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lvmama.comm.pet.po.mark.MarkMembershipCardCode;

public interface MarkMembershipCardCodeService {
	/**
	 * 批量插入
	 */
	void insertByBatch(String cardPrefixNumber, Long number, Set<String> code,final String operatorName);
	/**
	 * 查询
	 * @param parameters
	 * @return
	 */
	List<MarkMembershipCardCode> query(final Map<String,Object> parameters);
	
	/**
	 * 计数
	 * @param parameters
	 * @return
	 */
	Long count(final Map<String,Object> parameters);
}
