package com.lvmama.prd.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.prod.ProdTagGroup;

public class ProdTagGroupDAO extends BaseIbatisDAO {
	
	public int deleteByPrimaryKey(Long tagGroupId) {
		ProdTagGroup key = new ProdTagGroup();
		key.setTagGroupId(tagGroupId);
		int rows = super.delete("PROD_TAG_GROUP.deleteByPrimaryKey", key);
		return rows;
	}

	public void insertSelective(ProdTagGroup record) {
		super.insert("PROD_TAG_GROUP.insertSelective", record);
	}

	public ProdTagGroup selectByPrimaryKey(Long tagGroupId) {
		ProdTagGroup key = new ProdTagGroup();
		key.setTagGroupId(tagGroupId);
		ProdTagGroup record = (ProdTagGroup) super.queryForObject("PROD_TAG_GROUP.selectByPrimaryKey", key);
		return record;
	}

	public int updateByPrimaryKeySelective(ProdTagGroup record) {
		int rows = super.update("PROD_TAG_GROUP.updateByPrimaryKeySelective", record);
		return rows;
	}

	public List<ProdTagGroup> selectByParams(Map<String, Object> params) {
		return super.queryForList("PROD_TAG_GROUP.selectByParams", params);
	}
	
	public ProdTagGroup selectByTagGroupId(Long tagGroupId) {
		return (ProdTagGroup) super.queryForObject("PROD_TAG_GROUP.selectByTagGroupId", tagGroupId);
	}
	
	public int update(ProdTagGroup prodTagGroup) {
		return super.update("PROD_TAG_GROUP.update", prodTagGroup);
	}
	
	public int deleteByTagGroupId(Long tagGroupId) {
		return super.delete("PROD_TAG_GROUP.deleteByTagGroupId", tagGroupId);
	}
	
	public Long insert(ProdTagGroup prodTagGroup) {
    	Object newKey = super.insert("PROD_TAG_GROUP.insert", prodTagGroup);
    	return (Long) newKey;
    }
}