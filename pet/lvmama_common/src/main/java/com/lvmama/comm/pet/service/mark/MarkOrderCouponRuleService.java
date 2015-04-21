package com.lvmama.comm.pet.service.mark;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.vo.mark.MarkOrderCouponRule;

public interface MarkOrderCouponRuleService {

	public List<MarkOrderCouponRule> query(Map<String, Object> params);

	public Long count(Map<String, Object> params);

	public MarkOrderCouponRule queryByPk(Serializable id);
	
	public Long save(final MarkOrderCouponRule markOrderCouponRule);
	
	public int delete(final Long markOrderCouponRuleId);
}
