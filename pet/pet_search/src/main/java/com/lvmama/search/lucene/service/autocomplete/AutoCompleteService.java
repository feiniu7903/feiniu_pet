package com.lvmama.search.lucene.service.autocomplete;

import java.util.List;

import com.lvmama.comm.search.vo.AutoCompletePlaceDto;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.ClientPlaceSearchVO;
import com.lvmama.comm.search.vo.ClientRouteSearchVO;

/**
 * 自动补全查询
 * 
 * @author huangzhi
 * 
 */
public interface AutoCompleteService {

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
	public List<AutoCompletePlaceObject> getAutoCompletePlacePlaceObjectListMatched(int channelType, Long fromPlaceId, String keyword ,int topNum);
	
	/**
	 * 根据ClientPlaceSearchVO得到一个结果LIST
	 * 
	 * @param
	 * @return ArrayList<AutoCompletePlaceObject>
	 */
	public List<AutoCompletePlaceObject> getAutoCompletePlaceObjectListMatched(int channelType, ClientPlaceSearchVO searchVo, int topNum) ;
	
	/**
	 * 根据ClientRouteSearchVO得到一个结果LIST
	 * 
	 * @param
	 * @return ArrayList<AutoCompletePlaceObject>
	 */
	public List<AutoCompletePlaceObject> getAutoCompleteRouteObjectListMatched(int channelType, ClientRouteSearchVO searchVo, int topNum) ;
	
	
	/**
	 * 空匹配，直接打印前TOPnum个SEQ最大的结果 
	 * @param
	 * @return ArrayList<AutoCompletePlaceObject>
	 */
	public List<AutoCompletePlaceObject> getAutoCompletePlacePlaceObjectListDefault(int channelType, Long fromPlaceId,int topNum);
}
