package com.lvmama.market.dao;

import java.util.List;
import java.util.Map;

import com.ibm.db2.jcc.b.ob;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.market.ApplyCity;
import com.lvmama.comm.bee.po.market.TaobaoProduct;
import com.lvmama.comm.bee.po.market.TaobaoProductDetail;
/**
 * 报名城市
 * @author zhushuying
 *
 */
public class ApplyCityDAO extends BaseIbatisDAO {

	@SuppressWarnings("unchecked")
	public List<ApplyCity> selectAllApplyCity() {
		return super.queryForList("APPLY_CITY.getAllApplyCity");
	}
	
	public ApplyCity selectApplyCityBy(Map<String, Object> map){
		ApplyCity applyCity=(ApplyCity) super.queryForObject("APPLY_CITY.selectApplyCityBy",map);
		return applyCity;
	}
	
	@SuppressWarnings("unchecked")
	public List<ApplyCity> getApplyCityByPage(Map<String, Object> map) {
		return super.queryForList("APPLY_CITY.getApplyCityByPage", map);
	}
	
	public Long getApplyCityPageCount(){
		return (Long)queryForObject("APPLY_CITY.getApplyCityPageCount");
	}
	

	public Long addApplyCity(ApplyCity city) {
		return (Long) super.insert("APPLY_CITY.addCity", city);
	}

	public int delApplyCity(Long cityId) {
		return super.delete("APPLY_CITY.delCityById", cityId);
	}
}