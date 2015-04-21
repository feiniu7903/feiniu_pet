package com.lvmama.comm.bee.service.meta;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.meta.MetaProductControl;
import com.lvmama.comm.bee.vo.view.ViewMetaProductControl;


public interface MetaProductControlService {
	
	List<MetaProductControl> getProductControlListByCondition(Map<String, Object> condition);
	
	Long countProductControlByCondition(Map<String, Object> condition);

	void saveControl(MetaProductControl control);

	void deleteControlByProduct(MetaProductControl control);

	MetaProductControl getByPrimaryKey(Long metaProductControlId);

	void deleteControl(MetaProductControl control);

	void saveBatchControl(MetaProductControl control);

	Long countViewControlByCondition(Map<String, Object> searchConds);

	List<ViewMetaProductControl> getViewControlList(
			Map<String, Object> searchConds);

	List<ViewMetaProductControl> getWithoutTotalQuantityViewControlList(
			Map<String, Object> searchConds);

	List<ViewMetaProductControl> getReportViewControlList(
			Map<String, Object> searchConds);
}
