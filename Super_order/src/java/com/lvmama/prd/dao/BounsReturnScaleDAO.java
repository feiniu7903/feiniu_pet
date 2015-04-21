package com.lvmama.prd.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.BounsReturnScale;

@SuppressWarnings("unchecked")
public class BounsReturnScaleDAO extends BaseIbatisDAO {

	public List<BounsReturnScale> getAll() {
		return queryForList("BOUNS_RETURN_SCALE.getAll");
	}
	
	public BounsReturnScale getByType(Map<String, String> map) {
		return (BounsReturnScale) queryForObject("BOUNS_RETURN_SCALE.getByType", map);
	}
}
