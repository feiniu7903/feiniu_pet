package com.lvmama.prd.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.meta.MetaProductControl;
import com.lvmama.comm.bee.service.meta.MetaProductControlService;
import com.lvmama.comm.bee.vo.view.ViewMetaProductControl;
import com.lvmama.prd.dao.MetaProductControlDAO;

public class MetaProductControlServiceImpl implements MetaProductControlService {

	private MetaProductControlDAO metaProductControlDAO;
	
	@Override
	public List<MetaProductControl> getProductControlListByCondition(
			Map<String, Object> condition) {
		return metaProductControlDAO.getListByCondition(condition);
	}

	@Override
	public Long countProductControlByCondition(Map<String, Object> condition) {
		return metaProductControlDAO.countByCondition(condition);
	}

	@Override
	public void saveControl(MetaProductControl control) {
		Long count = metaProductControlDAO.getDateCrossCountByControl(control);
		if (count > 0) {
			throw new RuntimeException("该有效期时间段内已经设置过预控,时间不可交叉");
		}
		if (control.getMetaProductControlId() == null) {
			metaProductControlDAO.save(control);
		} else {
			metaProductControlDAO.update(control);
		}
	}
	
	@Override
	public void saveBatchControl(MetaProductControl control) {
		Long count = metaProductControlDAO.getDateCrossCountByControl(control);
		if (count > 0) {
			throw new RuntimeException("该有效期时间段内已经设置过预控,时间不可交叉");
		}
		createDateControlList(control);
	}

	@Override
	public void deleteControlByProduct(MetaProductControl control) {
		metaProductControlDAO.deleteControlByProductId(control);
	}

	@Override
	public MetaProductControl getByPrimaryKey(Long metaProductControlId) {
		return metaProductControlDAO.find(metaProductControlId);
	}
	
	@Override
	public void deleteControl(MetaProductControl control) {
		metaProductControlDAO.deleteControlByPrimaryKey(control);
	}

	@Override
	public Long countViewControlByCondition(Map<String, Object> searchConds) {
		if (searchConds.get("roleArea") == null) {
			return 0L;
		}
		return metaProductControlDAO.countViewControlByCondition(searchConds);
	}

	@Override
	public List<ViewMetaProductControl> getViewControlList(
			Map<String, Object> searchConds) {
		if (searchConds.get("roleArea") == null) {
			return new ArrayList<ViewMetaProductControl>();
		}
		return metaProductControlDAO.getViewControlList(searchConds);
	}

	@Override
	public List<ViewMetaProductControl> getReportViewControlList(
			Map<String, Object> searchConds) {
		if (searchConds.get("roleArea") == null) {
			return new ArrayList<ViewMetaProductControl>();
		}
		return metaProductControlDAO.getReportViewControlList(searchConds);
	}

	@Override
	public List<ViewMetaProductControl> getWithoutTotalQuantityViewControlList(
			Map<String, Object> searchConds) {
		if (searchConds.get("roleArea") == null) {
			return new ArrayList<ViewMetaProductControl>();
		}
		return metaProductControlDAO.getWithoutTotalQuantityViewControlList(searchConds);
	}

	public void setMetaProductControlDAO(MetaProductControlDAO metaProductControlDAO) {
		this.metaProductControlDAO = metaProductControlDAO;
	}

	private void createDateControlList(
			MetaProductControl control) {
		Long subDay = (control.getEndDate().getTime() - control.getStartDate().getTime()) / (1000 * 60 * 60 * 24) + 1;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(control.getStartDate());
		for (int i = 0; i < subDay; i++) {
			Date date = calendar.getTime();
			MetaProductControl c = createCopyControl(control);
			c.setSaleEndDate(control.getSaleEndDate() == null ? date : control.getSaleEndDate());
			c.setStartDate(date);
			c.setEndDate(date);
			this.metaProductControlDAO.save(c);
			calendar.add(Calendar.DATE, 1);
		}
	}

	private MetaProductControl createCopyControl(MetaProductControl control) {
		MetaProductControl c = new MetaProductControl();
		c.setProductBranchId(control.getProductBranchId());
		c.setProductId(control.getProductId());
		c.setControlType(control.getControlType());
		c.setControlQuantity(control.getControlQuantity() == null ? 0L : control.getControlQuantity());
		c.setSaleStartDate(control.getSaleStartDate());
		c.setDelayAble(control.getDelayAble());
		c.setBackAble(control.getBackAble());
		return c;
	}
}
