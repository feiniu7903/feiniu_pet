package com.lvmama.comm.pet.service.mark;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.vo.mark.MarkMembershipCardDiscountDetails;

public interface MarkMembershipCardDiscountService {
	/**
	 * 查询
	 * @param parameters
	 * @return
	 */
	List<MarkMembershipCardDiscountDetails> query(final Map<String,Object> parameters);
	
	/**
	 * 删除
	 * @param parameters
	 */
	void delete(final Map<String,Object> parameters,final String operatorName);
}
