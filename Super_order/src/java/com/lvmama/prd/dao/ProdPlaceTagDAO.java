package com.lvmama.prd.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdPlaceTag;
public class ProdPlaceTagDAO extends BaseIbatisDAO {
    public void deleteProdPlaceTagTimeOut(){
    	super.delete("PROD_PLACE_TAG.deleteProdPlaceTagTimeOut");
    }
    public List<ProdPlaceTag> selectByParams(Map<String, Object> params) {
		return super.queryForList("PROD_PLACE_TAG.selectByParams", params);
	}
    
    public ProdPlaceTag selectBytagId(Long tagId){
    	return (ProdPlaceTag)super.queryForObject("PROD_PLACE_TAG.selectBytagId",tagId);
    }
    
    public Integer selectRowCount(Map<String, Object> params) {
    	return (Integer) super.queryForObject("PROD_PLACE_TAG.selectByParamsCount", params);
    }
    
    public int deleteProdPlaceTag(ProdPlaceTag prodPlaceTag) {
		return super.delete("PROD_PLACE_TAG.deleteProdPlaceTag", prodPlaceTag);
	}
	
	/** 通过标签和 目的地类型 查询 目的地的 记录数 */
	public Integer queryPlaceByTagAndPlaceTypeCount(Map<String, Object> params) {
		return  (Integer)super
				.queryForObject("COM_PLACE.queryPlaceByTagAndPlaceTypeCount",params);
	}

	/** 通过标签和 目的地类型 查询 目的地 */
	@SuppressWarnings("unchecked")
	public List<Place> queryPlaceByTagAndPlaceType(Map<String, Object> params) {
		return super
				.queryForList("COM_PLACE.queryPlaceByTagAndPlaceType",params);
	}

	@SuppressWarnings("unchecked")
	public List<ProdPlaceTag> selectPlaceTagByPlaceIdAndTagGroupId(Long placeId,
			Long tagGroupId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("placeId", placeId);
		params.put("tagGroupId", tagGroupId);
		return super.queryForList("PROD_PLACE_TAG.selectPlaceTagByPlaceIdAndTagGroupId", params);
	}

	public List<ProdPlaceTag> selectPlaceTagByProdPlaceTag(ProdPlaceTag placeTag) {
		return super.queryForList("PROD_PLACE_TAG.selectPlaceTagByPlaceIdAndTagId", placeTag);
	}

	public void insertSelective(ProdPlaceTag placeTag) {
		super.insert("PROD_PLACE_TAG.insertSelective", placeTag);
	}

	public void deleteByPlaceTagId(ProdPlaceTag placeTag) {
		super.delete("PROD_PLACE_TAG.deleteByPrimaryKey", placeTag);
	}

	/** 通过 目的地 查询 目的地关联的标签 */
	public List<ProdPlaceTag> selectPlaceTagsByPlace(Place place) {
		return super.queryForList("PROD_PLACE_TAG.selectPlaceTagsByPlace", place);
	}
    
}