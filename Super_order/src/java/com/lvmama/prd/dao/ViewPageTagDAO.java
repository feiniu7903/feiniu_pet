package com.lvmama.prd.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ViewPageTag;

public class ViewPageTagDAO extends BaseIbatisDAO{

   
    public int deleteByPrimaryKey(Long pageTagId)  {
        int rows = super.delete("VIEW_PAGE_TAG.deleteByPrimaryKey", pageTagId);
        return rows;
    }

   
    public void insert(ViewPageTag record)  {
        super.insert("VIEW_PAGE_TAG.insert", record);
    }

   
    public ViewPageTag selectByPrimaryKey(Long pageTagId)  {
        ViewPageTag record = (ViewPageTag) super.queryForObject("VIEW_PAGE_TAG.selectByPrimaryKey", pageTagId);
        return record;
    }

    public int updateByPrimaryKey(ViewPageTag record)  {
        int rows = super.update("VIEW_PAGE_TAG.updateByPrimaryKey", record);
        return rows;
    }


	public List<ViewPageTag> selectByPageId(Long pageId) {
		return super.queryForList("VIEW_PAGE_TAG.selectByPageId", pageId);
	}

}