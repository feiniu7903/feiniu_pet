package com.lvmama.prd.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.service.prod.ProdTagService;
import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.po.prod.ProdTagGroup;
import com.lvmama.prd.dao.ProdTagDAO;
import com.lvmama.prd.dao.ProdTagGroupDAO;

public class ProdTagServiceImpl implements ProdTagService {
	
	
	private ProdTagDAO prodTagDAO;
	private ProdTagGroupDAO prodTagGroupDAO;
	
	/**
	 * 添加一个标签
	 */
	public void addTag(ProdTag prodTag) {
		prodTagDAO.insert(prodTag);
	}

	/**
	 * 修改一个标签
	 */
	public void editTag(ProdTag prodTag) {
		prodTagDAO.update(prodTag);
	}

	/**
	 * 获取某个指定标签
	 */
	public ProdTag getTagByTagId(Long tagId) {
		return prodTagDAO.selectByTagId(tagId);
	}
	
	/**
	 * 根据tagName获取指定标签
	 */
	public ProdTag getTagByTagName(String tagName) {
		return prodTagDAO.selectByTagName(tagName);
	}

	public Boolean updateByPrimaryKeySelective(ProdTag tag) {
		boolean flag = false;
		if (this.prodTagDAO.updateByPrimaryKeySelective(tag) == 1) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 为查询组和标签分页而查询的总数
	 */
	public Integer getGroupsAndTagsCount(Map<String ,Object> searchParams){
		return prodTagDAO.selectByParamsCount(searchParams);
	}
	
	/**
	 *  *******************下面是组ProdTagGroup有关的方法**********************
	 */
	
	/**
	 * 依据KEY获取组
	 */
	public ProdTagGroup selectByPrimaryKey(Long tagGroupId) {
		return prodTagGroupDAO.selectByPrimaryKey(tagGroupId);
	}

	/**
	 * 获取所有的组
	 */
	public List<ProdTagGroup> getGroups() {
		return prodTagGroupDAO.selectByParams(null);
	}
	
	/**
	 * 添加一个组
	 */
	public void addGroup(ProdTagGroup prodTagGroup) {
		prodTagGroupDAO.insert(prodTagGroup);
	}
 
	/**
	 * 获取所有的标签
	 */
	public List<ProdTag> getTags() {
		return prodTagDAO.selectByParams(null);
	}

	/**
	 * 获取指定组下所有的标签
	 */
	public List<ProdTag> getTagsInGroup(Long tagGroupId) {
		return prodTagDAO.selectByTagGroupId(tagGroupId);
	}

	/**
	 * 组和标签一并获取
	 */
	public List<ProdTag> getGroupsAndTags(Map<String, Object> searchParams) {
		Map<String, Object> params = searchParams;
		// params.put("tagGroupId", tagGroupId);
		// params.put("tagId", tagId);
		List<ProdTag> prodTags = prodTagDAO.selectByParams(params);
		for (ProdTag prodTag : prodTags) {
			if(prodTag.getTagGroupId() != null){
				ProdTagGroup prodTagGroup = prodTagGroupDAO.selectByTagGroupId(prodTag.getTagGroupId());
				prodTag.setTagGroupName(prodTagGroup.getTagGroupName());
			}
		}
		return prodTags;
	}
	
	public void setProdTagDAO(ProdTagDAO prodTagDAO) {
		this.prodTagDAO = prodTagDAO;
	}

	public void setProdTagGroupDAO(ProdTagGroupDAO prodTagGroupDAO) {
		this.prodTagGroupDAO = prodTagGroupDAO;
	}
}
