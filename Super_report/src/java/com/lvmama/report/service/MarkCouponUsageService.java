package com.lvmama.report.service;

import java.util.List;
import java.util.Map;

import com.lvmama.report.po.CouponUsegeModel;

public interface MarkCouponUsageService {
	public List<CouponUsegeModel> query(Map<String,Object> parameters);

}
