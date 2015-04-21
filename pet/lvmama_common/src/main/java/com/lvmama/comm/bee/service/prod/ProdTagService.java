package com.lvmama.comm.bee.service.prod;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.po.prod.ProdTagGroup;

public interface ProdTagService {

	/**
	 * 添加一个标签
	 */
	public void addTag(ProdTag prodTag);

	/**
	 * 修改一个标签
	 */
	public void editTag(ProdTag prodTag);

	/**
	 * 获取某个指定标签
	 */
	public ProdTag getTagByTagId(Long tagId);
	/**
	 * 根据tagName获取指定标签
	 * @param tagName
	 * @return ProdTag
	 */
	public ProdTag getTagByTagName(String tagName);
	
	public Boolean updateByPrimaryKeySelective(ProdTag tag);
	
	/**
	 *  *******************下面是组ProdTagGroup有关的方法**********************
	 */
	
	/**
	 * 依据KEY获取组
	 */
	public ProdTagGroup selectByPrimaryKey(Long tagGroupId);
	/**
	 * 获取所有的组
	 */
	public List<ProdTagGroup> getGroups();
	
	/**
	 * 添加一个组
	 */
	public void addGroup(ProdTagGroup prodTagGroup);
 
	/**
	 * 获取所有的标签
	 */
	public List<ProdTag> getTags();

	/**
	 * 获取指定组下所有的标签
	 */
	public List<ProdTag> getTagsInGroup(Long tagGroupId);

	/**
	 * 组和标签一并获取
	 */
	public List<ProdTag> getGroupsAndTags(Map<String, Object> searchParams);
	
	/**
	 * 为查询组和标签分页而查询的总数
	 */
	public Integer getGroupsAndTagsCount(Map<String ,Object> searchParams);
	
	
}
