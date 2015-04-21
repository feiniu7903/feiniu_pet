package com.lvmama.prd.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ViewTag;

public class ViewTagDAO extends BaseIbatisDAO{

    public int deleteByPrimaryKey(Long tagId)  {
        ViewTag key = new ViewTag();
        key.setTagId(tagId);
        int rows = super.delete("VIEW_PAGE_TAG.deleteByPrimaryKey", key);
        return rows;
    }

  
    public void insert(ViewTag record)  {
        super.insert("VIEW_PAGE_TAG.insert", record);
    }

    
    public ViewTag selectByPrimaryKey(Long tagId)  {
        ViewTag key = new ViewTag();
        key.setTagId(tagId);
        ViewTag record = (ViewTag) super.queryForObject("VIEW_PAGE_TAG.selectByPrimaryKey", key);
        return record;
    }

    
    public int updateByPrimaryKey(ViewTag record)  {
        int rows = super.update("VIEW_PAGE_TAG.updateByPrimaryKey", record);
        return rows;
    }


	public List<ViewTag> searchViewTag(String name) {
		return super.queryForList("VIEW_TAG.select", name);
	}

}