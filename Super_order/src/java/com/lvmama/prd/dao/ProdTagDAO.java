package com.lvmama.prd.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.prod.ProdTag;

public class ProdTagDAO extends BaseIbatisDAO {

	public int deleteByPrimaryKey(Long tagId) {
		ProdTag key = new ProdTag();
		key.setTagId(tagId);
		int rows = super.delete("PROD_TAG.deleteByPrimaryKey", key);
		return rows;
	}

	public void insertSelective(ProdTag record) {
		super.insert("PROD_TAG.insertSelective", record);
	}

	public ProdTag selectByPrimaryKey(Long tagId) {
		ProdTag key = new ProdTag();
		key.setTagId(tagId);
		ProdTag record = (ProdTag) super.queryForObject("PROD_TAG.selectByPrimaryKey", key);
		return record;
	}

	@SuppressWarnings("unchecked")
	public List<ProdTag> selectByTagGroupId(Long tagGroupId) {
		return super.queryForList("PROD_TAG.selectByTagGroupId", tagGroupId);
	}

	public ProdTag selectByTagId(Long tagId) {
		return (ProdTag) super.queryForObject("PROD_TAG.selectByTagId", tagId);
	}
	/**
	 * 根据名字查找ProdTag
	 * @param tagName
	 * @return ProdTag
	 */
	public ProdTag selectByTagName(String tagName) {
		return (ProdTag) super.queryForObject("PROD_TAG.selectByTagName", tagName);
	}
	@SuppressWarnings("unchecked")
	public List<ProdTag> selectByParams(Map<String, Object> params) {
		return super.queryForList("PROD_TAG.selectByParams", params);
	}

	public Integer selectByParamsCount(Map<String, Object> params) {
		return (Integer) super.queryForObject("PROD_TAG.selectByParamsCount", params);
	}

	public int update(ProdTag prodTag) {
		return super.update("PROD_TAG.update", prodTag);
	}

	public int deleteByTagId(Long tagId) {
		return super.delete("PROD_TAG.deleteByTagId", tagId);
	}

	public Long insert(ProdTag prodTag) {
		Object newKey = super.insert("PROD_TAG.insert", prodTag);
		return (Long) newKey;
	}

	public int updateByPrimaryKeySelective(ProdTag tag) {
		return (Integer) super.update("PROD_TAG.updateByPrimaryKeySelective", tag);
	}
	@SuppressWarnings("unchecked")
	public List<ProdTag> selectTagsByProductId(Long productId) {
		return super.queryForList("PROD_TAG.selectTagsByProductId", productId);
	}
}