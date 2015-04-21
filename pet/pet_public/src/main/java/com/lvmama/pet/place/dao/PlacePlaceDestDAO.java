package com.lvmama.pet.place.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlacePlaceDest;

public class PlacePlaceDestDAO extends BaseIbatisDAO{
	
	/**
	 * 根据placeId计算层级关系带递归上级目的名称的
	 * 
	 * @param placeId
	 * @return
	 */
	public List<PlacePlaceDest> calculationPlaceSuperior(Long placeId){
		return (List<PlacePlaceDest>)super.queryForList("PLACE_PLACE_DEST.calculationPlaceSuperior",placeId);
	}

	/**
	 * 获得此目的地的直接父亲目的地list
	 * 
	 * @param placeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PlacePlaceDest> queryParentPlaceList(Long placeId) {
		return super.queryForList("PLACE_PLACE_DEST.selectParentPlaceList", placeId);
	}
	
	/**
	 * 获得此目的地的所有父级
	 * @param placeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> selectParentPlaceIdList(Long placeId) {
		return (List<Long>)super.queryForList("PLACE_PLACE_DEST.selectParentPlaceIdList", placeId);
	}
	
	/**
	 * 查看是否在所有的儿子节点上面已经存在
	 * 
	 * @param placeId
	 * @param parentPlaceId
	 * @return
	 */
	public boolean isExistInChildrenLevelTree(long placeId, long parentPlaceId) {
		PlacePlaceDest placePlaceDest = new PlacePlaceDest();
		placePlaceDest.setPlaceId(placeId);
		placePlaceDest.setParentPlaceId(parentPlaceId);
		Long result = (Long) super.queryForObject("PLACE_PLACE_DEST.selectHasSonsPlaceCount", placePlaceDest);
		if (result!=null) {
			return result>0;
		}
		return false;
	}
	
	/**
	 * 查看是否在所有的父亲节点上面已经存在
	 * 
	 * @param placeId
	 * @param parentPlaceId
	 * @return
	 */
	public boolean isExistInParentLevelTree(long placeId, long parentPlaceId) {
		PlacePlaceDest placePlaceDest = new PlacePlaceDest();
		placePlaceDest.setPlaceId(placeId);
		placePlaceDest.setParentPlaceId(parentPlaceId);
		Long result = (Long) super.queryForObject("PLACE_PLACE_DEST.selectHasParentsPlaceCount", placePlaceDest);
		if (result!=null) {
			return result>0;
		}
		return false;
	}

	public void saveOrUpdate(PlacePlaceDest placePlaceDest) {
		if(placePlaceDest.getPlacePlaceDestId() ==null) {
			super.insert("PLACE_PLACE_DEST.insert", placePlaceDest);
		} else {
			super.update("PLACE_PLACE_DEST.updateMaster", placePlaceDest);
		}
	}

	public void deletePlacePlaceDest(PlacePlaceDest placePlaceDest) {
		super.delete("PLACE_PLACE_DEST.deleteByPrimaryKey", placePlaceDest);
	}
	
	/**
	 * 主要层级只有一个主线
	 * 
	 * @param placePlaceDest
	 */
	public void updateMaster(PlacePlaceDest placePlaceDest) {
		super.update("PLACE_PLACE_DEST.updateMaster", placePlaceDest);
	}
	
	/**
	 * 根据当前placeId获取父类的placeIdList
	 * @param placeId
	 * @return
	 */
	public List<PlacePlaceDest> getParentPlaceIdListByCurrentPlaceId(Long placeId){
		return super.queryForList("PLACE_PLACE_DEST.getParentPlaceIdListByCurrentPlaceId",placeId);
	}
	public PlacePlaceDest selectByPrimaryKey(Long placePlaceDestId)  {
		PlacePlaceDest key = new PlacePlaceDest();
		key.setPlacePlaceDestId(placePlaceDestId);
		PlacePlaceDest record = (PlacePlaceDest) super.queryForObject("PLACE_PLACE_DEST.selectByPrimaryKey", key);
		return record;
	}
}