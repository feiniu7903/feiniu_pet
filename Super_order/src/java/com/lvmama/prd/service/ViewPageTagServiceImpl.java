package com.lvmama.prd.service;

import java.util.List;

import com.lvmama.comm.bee.po.prod.ViewPageTag;
import com.lvmama.comm.bee.po.prod.ViewTag;
import com.lvmama.comm.bee.service.view.ViewPageTagService;
import com.lvmama.prd.dao.ViewPageTagDAO;
import com.lvmama.prd.dao.ViewTagDAO;

public class ViewPageTagServiceImpl implements ViewPageTagService {
	private ViewPageTagDAO viewPageTagDAO;
	private ViewTagDAO viewTagDAO;
	public void setViewTagDAO(ViewTagDAO viewTagDAO) {
		this.viewTagDAO = viewTagDAO;
	}

	public ViewPageTagDAO getViewPageTagDAO() {
		return viewPageTagDAO;
	}

	public void setViewPageTagDAO(ViewPageTagDAO viewPageTagDAO) {
		this.viewPageTagDAO = viewPageTagDAO;
	}

	public void deletePageTag(Long pageTagId) {
		viewPageTagDAO.deleteByPrimaryKey(pageTagId);
		
	}

	public void insertTag(ViewPageTag viewPageTag) {
		viewPageTagDAO.insert(viewPageTag);
		
	}

	public List<ViewPageTag> selectByPageId(Long pageId) {
		return (List<ViewPageTag>)viewPageTagDAO.selectByPageId(pageId);
	}

	public List<ViewTag> searchViewTags(String name) {
		return viewTagDAO.searchViewTag(name);
	}

}
