package com.lvmama.comm.bee.service.prod;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdPlaceTag;


 public interface ProdPlaceTagService {
	 /**
	  * 删除过了有效期的目的地标签
	  */
	 public void deleteProdPlaceTagTimeOut();
	/**
	 * 删除拥有某标签的目的地所关联的该标签的时效等信息
	 */
	void delPlaceTags(List<ProdPlaceTag> prodPlaceTags);

	/**
	 * 获取拥有某标签的目的地所关联的该标签的时效信息的总记录数
	 */
	Integer getPlaceTagsTotalCount(Long tagId);
	
	/**
	 * 获取拥有某标签的目的地所关联的该标签的时效等信息
	 */
	 List<ProdPlaceTag> selectByParams(Map<String,Object> params);
	 
	/** 通过标签和 目的地类型 查询 目的地 的 记录数 */
	public Integer queryPlaceByTagAndPlaceTypeCount(Map<String, Object> params)throws Exception;

	/** 通过标签和 目的地类型 查询 目的地 */
	public List<Place> queryPlaceByTagAndPlaceType(Map<String, Object> params)throws Exception;

	/**
	 * 添加 目的地 标签 关联
	 * @param tagGroupId 标签组
	 * @param placeTags 新增标签
	 * @param alreadyAddPlaceTags 目的地关联的所有标签
	 */
	public List<ProdPlaceTag> addProgPlaceTags(Long tagGroupId,List<ProdPlaceTag> placeTags, List<ProdPlaceTag> alreadyAddPlaceTags);

	/** 删除重复的目的地标签关联 新增关联 */
	public List<ProdPlaceTag> addPlaceTagAndDeleteConflictsPlaceTag(long tagGroupId, List<ProdPlaceTag> convertPlaceTag);

	/** 通过 目的地 查询 目的地关联的标签 */
	public List<ProdPlaceTag> selectPlaceTagsByPlace(Place place);
	 
}