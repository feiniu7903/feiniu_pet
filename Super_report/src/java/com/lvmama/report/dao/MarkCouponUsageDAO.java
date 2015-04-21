package com.lvmama.report.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.CouponUsegeModel;
public class MarkCouponUsageDAO extends BaseIbatisDAO {
	@SuppressWarnings("unchecked")
	public List<CouponUsegeModel> query(final Map<String,Object> parameters){
		List<CouponUsegeModel> list=super.queryForList("MARK_COUPON_USAGE.query", parameters);
		return list;
	}
}
