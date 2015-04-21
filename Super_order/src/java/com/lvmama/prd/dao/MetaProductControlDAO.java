package com.lvmama.prd.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.meta.MetaProductControl;
import com.lvmama.comm.bee.vo.view.ViewMetaProductControl;

public class MetaProductControlDAO extends BaseIbatisDAO{

	@SuppressWarnings("unchecked")
	public List<MetaProductControl> getListByCondition(
			Map<String, Object> condition) {
		if (condition.get("_startRow")==null) {
			condition.put("_startRow", 0);
		}
		if (condition.get("_endRow")==null) {
			condition.put("_endRow", 20);
		}
		return (List<MetaProductControl>) super.queryForList("META_PRODUCT_CONTROL.getListByCondition", condition);
	}

	public Long countByCondition(Map<String, Object> condition) {
		return (Long) super.queryForObject("META_PRODUCT_CONTROL.countByCondition", condition);
	}

	public Long save(MetaProductControl control) {
		return (Long)super.insert("META_PRODUCT_CONTROL.save", control);
	}

	public void update(MetaProductControl control) {
		super.insert("META_PRODUCT_CONTROL.update", control);
	}

	public Long getDateCrossCountByControl(
			MetaProductControl control) {
		return (Long) super.queryForObject("META_PRODUCT_CONTROL.getDateCrossCountByControl", control);
	}
	
	public Long getTotalSaleQuantityByControlId(
			MetaProductControl control) {
		return (Long) super.queryForObject("META_PRODUCT_CONTROL.getTotalSaleQuantityByControlId", control);
	}
	
	public void deleteControlByProductId(MetaProductControl control) {
		super.delete("META_PRODUCT_CONTROL.deleteControlByProductId", control);
	}

	public MetaProductControl find(Long metaProductControlId) {
		Map<String, Long> param = new HashMap<String, Long>();
		param.put("metaProductControlId", metaProductControlId);
		return (MetaProductControl) super.queryForObject("META_PRODUCT_CONTROL.find", param);
	}

	public void deleteControlByPrimaryKey(MetaProductControl control) {
		super.delete("META_PRODUCT_CONTROL.deleteControlByPrimaryKey", control);
	}

	@SuppressWarnings("unchecked")
	public MetaProductControl findByVisitTimeCondition(
			Map<String, Object> condition) {
		List<MetaProductControl> list = (List<MetaProductControl>) super.queryForList("META_PRODUCT_CONTROL.findByVisitTimeCondition", condition);
		return list.size() == 0 ? null : list.get(0);
	}

	public void updateSaleQuantity(MetaProductControl control) {
		super.update("META_PRODUCT_CONTROL.updateSaleQuantity", control);
	}

	public void updateOrderItemMetaBtQuantity(Map<String, Object> param) {
		super.update("META_PRODUCT_CONTROL.updateOrderItemMetaBtQuantity", param);
	}

	public void updateOrderItemMetaTimeBtQuantity(Map<String, Object> param) {
		super.update("META_PRODUCT_CONTROL.updateOrderItemMetaTimeBtQuantity", param);
	}

	public Long countViewControlByCondition(Map<String, Object> searchConds) {
		return (Long) super.queryForObject("META_PRODUCT_CONTROL.countViewControlByCondition", searchConds);
	}

	@SuppressWarnings("unchecked")
	public List<ViewMetaProductControl> getViewControlList(
			Map<String, Object> searchConds) {
		if (searchConds.get("_startRow")==null) {
			searchConds.put("_startRow", 0);
		}
		if (searchConds.get("_endRow")==null) {
			searchConds.put("_endRow", 20);
		}
		List<ViewMetaProductControl> list = (List<ViewMetaProductControl>) super.queryForList("META_PRODUCT_CONTROL.getViewControlList", searchConds);
		for (ViewMetaProductControl view : list) {
			view.setTotalQuantity(getTotalSaleQuantityByControlId(view));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ViewMetaProductControl> getWithoutTotalQuantityViewControlList(
			Map<String, Object> searchConds) {
		if (searchConds.get("_startRow")==null) {
			searchConds.put("_startRow", 0);
		}
		if (searchConds.get("_endRow")==null) {
			searchConds.put("_endRow", 20);
		}
		List<ViewMetaProductControl> list = (List<ViewMetaProductControl>) super.queryForList("META_PRODUCT_CONTROL.getViewControlList", searchConds);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ViewMetaProductControl> getReportViewControlList(
			Map<String, Object> searchConds) {
		searchConds.put("_startRow", 0);
		searchConds.put("_endRow", 50000);
		List<ViewMetaProductControl> list = (List<ViewMetaProductControl>) super.queryForListForReport("META_PRODUCT_CONTROL.getViewControlList", searchConds);
		for (ViewMetaProductControl view : list) {
			view.setTotalQuantity(getTotalSaleQuantityByControlId(view));
		}
		return list;
	}

}