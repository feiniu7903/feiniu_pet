package com.lvmama.comm.pet.service.place;

import java.util.List;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlacePlaceDest;
import com.lvmama.comm.pet.vo.PlaceVo;



public interface PlacePlaceDestService {
	/**
	 * 计算目的地层级关系
	 * @param place
	 * @return
	 */
	public List<PlaceVo> calculationPlaceSuperior(List<Place> place);
	/**
	 * 根据placeId获取层级关系
	 * @param place
	 * @return
	 */
	public String queryPlaceSuperior(Long placeId,String currentPlaceName);
	
	/**
	 * 获得此目的地的直接父亲目的地list
	 * 
	 * @param placeId
	 * @return
	 */
	public List<PlacePlaceDest> queryParentPlaceList(Long placeId);
	
	/**
	 * 判断在儿子的树形结构往下面推，是否存在；
	 * 
	 * @param placeId
	 * @param parentPlaceId
	 * @return
	 */
	public boolean isExistInChildrenLevelTree(long placeId, long parentPlaceId);
	
	/**
	 * 判断在父亲的树形结构往上面推，是否存在；
	 * 
	 * @param placeId
	 * @param parentPlaceId
	 * @return
	 */
	public boolean isExistInParentLevelTree(long placeId, long parentPlaceId);
	
	/**
	 * 更新或者保存
	 * 
	 * @param placePlaceDest
	 */
	public void saveOrUpdate(PlacePlaceDest placePlaceDest);
	
	public void deletePlacePlaceDest(PlacePlaceDest placePlaceDest);
	
	/**
	 * 更新主层级的关系
	 * 
	 * @param placePlaceDest
	 */
	public void updateMaster(PlacePlaceDest placePlaceDest);
	PlacePlaceDest selectByPrimaryKey(Long placePlaceDestId);
	/**
	 * 获得此目的地的所有父级
	 * @param placeId
	 * @return
	 */
	public List<Long> selectParentPlaceIdList(Long placeId);
}
