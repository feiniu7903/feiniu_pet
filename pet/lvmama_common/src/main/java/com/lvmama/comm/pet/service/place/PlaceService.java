package com.lvmama.comm.pet.service.place;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.vo.PlaceStateDests;
import com.lvmama.comm.pet.vo.PlaceVo;

public interface PlaceService {
	/**
	 * 根据参数获取符合条件的目的地列表,参数支持:是否有效、type类型、名称、iD、使用模板、目的地类型、主题、活动状态
	 * @param placeId
	 * @param parentPlaceName
	 * @param placeName
	 * @param isValid
	 * @param template
	 * @param placeType
	 * @param topic
	 * @param isHasActivity
	 * @return
	 */
	 List<Place> queryPlaceListByParam(Map<String,Object> param);
	/**
	 * 计算符合条件的place记录数量
	 * @param placeId
	 * @param parentPlaceName
	 * @param placeName
	 * @param isValid
	 * @param template
	 * @param placeType
	 * @param topic
	 * @param isHasActivity
	 * @param stage
	 * @return
	 */
	 Long countPlaceListByParam(Map<String,Object> param);
	/**
	 * 批量保存place排序值
	 * @param placeIds
	 */
	 void batchSavePlaceSeq(String placeIds,String userName);
	/**
	 * 获取当前place及上级直属place
	 * @param param
	 * @return
	 */
	 List<PlaceVo> queryPlaceAndParent(Map<String,Object> param);
	/**
	 * 根据placeId查询目的地对象
	 * @param placeId
	 * @return
	 */
	 Place queryPlaceByPlaceId(Long placeId);
	/**
	 * 保存place
	 * @param place
	 */
	 void savePlace(Place place);
	 
	 /**
	 * 保存place
	 * @param place
	 * @param userName  操作的用户名称
	 */
	void savePlace(Place place,String userName);
	/**
	 * 根据stage获取目的地自动补全
	 * @param stage
	 * @return
	 */
	 List<Place> queryPlaceAutocomplate(String word,String stage);
	 
	 Place getPlaceByName(String name,String valid);
	/**
	 * 判断placename是否已经存在,如果返回结果为ture代表已经存在
	 * @param placeName
	 * @return
	 */
	 boolean isExistPlaceNameCheck(String placeName);
	/**
	 * 根据placeId和stage获取下级的place
	 * @param place
	 * @return
	 */
	 List<Place> getSonPlaceByParentPlaceId(long parentPlaceId, long rownum, Long stage);
	/**
	 * 根据placeId和stage获取下级的place,本接口包含点评及价格信息
	 * @param place
	 * @return
	 */
	 List<Place> getSonPlaceByPlaceIdAndStage(Place place);
	/**
	 * 根据拼音获取place
	 * @param pinYin
	 * @return
	 */
	 Place getPlaceByPinYin(String pinYin);
	 /**
	  * 通过产品id获取place（标的）
	  * @param productId
	  * @return
	  */
	 public List<Place> getPlaceByProductId(Long productId);
	/**
	 * 根据stage和父亲的placeID查询有效地儿子节点的数量
	 * 
	 * @param parentPlaceId
	 * @param stage
	 * @return
	 */
	 long selectSonPlaceCount(Long parentPlaceId, Long stage);
	/**
	 * 获取同级目的地
	 * @param id  parentPlaceId
	 * @param stage
	 * @return
	 */
	 List<Place> getPlaceBySameParentPlaceId(Long parentPlaceId, Long stage);
	 /**
	 * 获取指定数量的同级目的地
	 * @param id  parentPlaceId
	 * @param stage
	 * @param size
	 * @return
	 */ 
	 List<Place> getPlaceBySameParentPlaceId(Long parentPlaceId, Long stage, Long size);
	
	/**
	 * 获取指定数量的同级目的地 (包含价格，图片等信息)
	 * 
	 * @param parentPlaceId
	 * @param stage
	 * @param size
	 * @return
	 */
	List<Place> getPlaceInfoBySameParentPlaceId(Long parentPlaceId,String stage,Long size);
	/**
     * 获取指定数量的同级目的地 (包含价格，图片等信息)
     * 增加随机排序. 根据place_id随机
     * @param parentPlaceId
     * @param stage
     * @param size
     * @param placeId 推荐的结果中排除查询的 
     * @return
     */
    List<Place> getPlaceInfoBySameParentPlaceId(Long parentPlaceId,String stage,Long size,String placeId);
	
	/**
	 * 获取指定数量的同级目的地(火车票用)
	 * @param parentPlaceId
	 * @param stage
	 * @param size
	 * @return
	 */
	List<Place> getPlaceInfoBySameParentPlaceIdTrain(Long parentPlaceId,String stage,Long size);
	
	/**
	 * 根据条件查找所有线路产品的出发地列表
	 * @param map 条件列表
	 * @return 出发地列表
	 * <p>根据查询条件查找所有线路产品的出发地。该函数会强制添加一些条件，如:isValid,productType,stage。
	 */
	List<Place> getRouteFrom(Map<String, Object> map);

	/**
	 * 获取目的地  默认境外的推荐数据及各区域数据
	 * 
	 * @return
	 */
	 PlaceStateDests getDestRecommend(Map<String,Long> recommendBlockIds);
	 List<Place> selectSuggestPlaceByName(String name);
	 /**
	 * 获取目的地  EBK产品录入使用，对名称采取后模糊查询方式
	 * 
	 * @return
	 */
	 List<Place> selectSuggestPlaceByNameEBK(String name);
	 List<Place> getRootDest();
	 List<Place> selectDestByRootId(Long id);
	 /**
	  * 目的地页面面包屑导航
	  * @param placeId
	  * @return
	  */
	 public Map<String,Object> loadNavigation(Long placeId);
	 /**
	  * 根据销售产品ID，stage查询Place信息
	  * @param productId
	  * @param stage
	  * @return
	  */
	 public List<Place> getPlaceByProductIdAndStage(Long productId,Long stage);
	/**
	 * 获取place加上对应搜索关键字转码
	 * @param param
	 * @return
	 */
	Place queryPlaceAndComSearchTranscodeByPlaceId(Long param);
	
	/**
	 * 更新点评主题
	 * @param placeId
	 * @param cmtTitle
	 */
	void updateCmtTitle(Long placeId, String cmtTitle, String userName);

	/**
	 * 替换点评主题
	 * @param placeId
	 * @param cmtTitle
	 */
	void replaceCmtTitle(Long placeId, Long productId, String cmtTitle, String userName);
	
	/**
	 * 根据parentPlaceIds获取景点信息
	 * @param param
	 * @return
	 */
	 List<Place> getPlaceListByParentIds(Map<String,List> param);
	 
	 /**
	  * 获取所有国家纪录
	  * @return
	  */
	 List<Place> getCountryRecord();
	 
	 /**
	  * 通过placeid获取描述和交通信息
	  * @return
	  */
	 Place getDescripAndTrafficByPlaceId(Long id);
	/**
	 * @param string
	 * @return
	 */
	Place getPlaceByPinYinWithOutCLOB(String string);
	/**
	 * @param parseLong
	 * @return
	 */
	Place queryPlaceByPlaceIdWithOutCLOB(long parseLong);
	
	 /**
	  * 查询父节点的景点门票
	  * @param parentPalceId 父节点id
	  * @return
	  */
	List<Place> queryParentPlace(Long parentPalceId);
	
	/**
     * 校验是否有敏感词并更新
     * */
    boolean checkAndUpdateIsHasSensitiveWords(Long placeId);
    
    void markPlaceSensitive(Long placeId, String hasSensitiveWord);
}
