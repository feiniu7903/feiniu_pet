package com.lvmama.comm.pet.service.place;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.PlaceQa;

public interface QuestionAnswerService {
	/**
	 * 分页查询问答表
	 * @param startRows	
	 * @param endRows
	 * @param placeId 
	 * @return
	 */
	List<PlaceQa> QueryAllAskByPlaceId(Map<String,Object> map);
	
	/**
	 * 添加问答
	 * @param ask
	 */
	void AddAskBySelf(PlaceQa ask);
	
	/**
	 * 删除问答
	 * @param placeQaId
	 */
	void DelAskBySelfId(Long placeQaId);
	
	/**
	 * 更新问答
	 * @param ask
	 */
	void UpdAskBySelf(PlaceQa ask);
	 
	/**
	 * 查询问答
	 * @param placeQaId
	 * @return
	 */
	PlaceQa QueryQaByQaId(Long placeQaId);
	
	/**
	 * 查询问答总条数 根据placeId
	 * @param placeId
	 * @return
	 */
	Long QueryCountAskByPlaceId(Long placeId);
	
}
