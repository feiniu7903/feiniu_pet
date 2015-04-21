package com.lvmama.search.lucene.service.autocomplete;

import java.util.List;

import com.lvmama.comm.search.vo.AutoCompletePlaceDto;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.AutoCompleteVerHotel;
import com.lvmama.comm.search.vo.AutoCompleteVerHotelCity;

/**
 * 自动补全查询
 * 
 * @author huangzhi
 * 
 */
public interface AutoCompleteOneService {

	/**
	 * 根据频道传入参数得到相应的LIST
	 * 
	 * @param
	 * @return ArrayList<AutoCompletePlaceDto>
	 */
	public List<AutoCompletePlaceDto> getAutoCompletePlaceDtoList(int channelType, Long fromPlaceId);
	
	/**
	 * 根据关键字匹配得到一个结果LIST
	 * 
	 * @param
	 * @return ArrayList<AutoCompletePlaceObject>
	 */
	public List<AutoCompletePlaceObject> getAutoCompletePlacePlaceObjectListMatched(int channelType, Long fromPlaceId, String keyword );
	
	public List<AutoCompletePlaceObject> getAutoCompletePlacePlaceObjectListMatched(int channelType, Long fromPlaceId, String keyword , Integer topNum);

	public List<AutoCompletePlaceObject> getAutoCompletePlacePlaceObjectListMatched(int channelType, Long fromPlaceId, String keyword , Integer topNum,boolean merge);
	
	public List<AutoCompletePlaceObject> getAutoCompletePlacePlaceObjectListDefault(int channelType, Long fromPlaceId, int topNum) ;

	public List<AutoCompleteVerHotel> getAutoCompleteVerHotelListMatched(int channelType, Long fromPlaceId, String keyword , Integer topNum,boolean merge);
	
	public List<AutoCompleteVerHotelCity> getAutoCompleteVerHotelCitiesMatched(int channelType, Long fromPlaceId, String keyword , Integer topNum,boolean merge);

}
