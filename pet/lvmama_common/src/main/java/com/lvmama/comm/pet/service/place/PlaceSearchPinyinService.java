package com.lvmama.comm.pet.service.place;

import java.util.List;

import com.lvmama.comm.pet.po.place.PlaceSearchPinyin;

public interface PlaceSearchPinyinService {
	
	/**
	 * 查询拼音列表
	 * 
	 * @param placeSearchPinyin
	 * @return
	 */
	public List<PlaceSearchPinyin> queryPlacePinyinList(PlaceSearchPinyin placeSearchPinyin);
	
	/**
	 * 取出一窜汉子所对应的拼音的List依据拼音字典表
	 * 
	 * @param objectName
	 * @return
	 */
	public List<PlaceSearchPinyin> queryPinyinListByName(String objectName);
	
	/**
	 * 删除高频关键词拼音
	 * @param placeSearchPinyin
	 */
	public void deleteHFKWPlaceSearchPinyin(PlaceSearchPinyin placeSearchPinyin);

	/**
	 * 更新或添加景点拼音
	 * @param placeSearchPinyin 景点拼音
	 * 当景点拼音是景点的名字的拼音时，将会删除该景点下的所有景点名字的拼音，然后新增一条拼音；并根据place表的记录来判断是否更新place的记录;当为高频拼音时，则
	 * 根据景点拼音的标识来判断是增加还是更新
	 */
	void saveOrUpdate(PlaceSearchPinyin placeSearchPinyin);
	
	/**
	 * 更新place的高频关键字
	 * 
	 * @param placeSearchPinyin
	 */
	public void updatePlaceHFKW(PlaceSearchPinyin placeSearchPinyin);

}
