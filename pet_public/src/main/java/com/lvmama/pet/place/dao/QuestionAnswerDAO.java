package com.lvmama.pet.place.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlaceQa;

public class QuestionAnswerDAO  extends BaseIbatisDAO{
	
	/**
	 * 添加一个问答
	 * @param ask
	 */
	public void insertAsk(PlaceQa ask){
		super.insert("QUESTIONANSWER.insert",ask);
	}
	
	/**
	 * 更新一个问答
	 * @param ask
	 */
	public void updateAsk(PlaceQa ask){
		super.update("QUESTIONANSWER.update",ask);
	}
	
	/**
	 * 删除一个问答 根据问答ID
	 * @param placeQaId
	 */
	public void deleteAsk(Long placeQaId){
		super.delete("QUESTIONANSWER.delete",placeQaId);
	}
	
	/**
	 * 分页查询问答表
	 * @param startRows	
	 * @param endRows
	 * @param placeId 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PlaceQa> selectAsk(Map<String,Object> map){
		return super.queryForList("QUESTIONANSWER.select",map);
	}

	/**
	 * 查询问答 根据问答ID
	 * @param placeQaId
	 * @return
	 */
	public PlaceQa selectAskByAskId(Long placeQaId){
		return (PlaceQa)super.queryForObject("QUESTIONANSWER.selectQaByQaId",placeQaId);
	}
	
	/**
	 * 查询问答的总条数 根据placeId
	 * @param placeId
	 * @return
	 */
	public Long getCountByPlaceId(Long placeId){
		return (Long)super.queryForObject("QUESTIONANSWER.queryCountByPlaceId",placeId);
	}
}
