package com.lvmama.pet.place.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.PlaceQa;
import com.lvmama.comm.pet.service.place.QuestionAnswerService;
import com.lvmama.pet.place.dao.QuestionAnswerDAO;

public class QuestionAnswerServiceImpl implements QuestionAnswerService {

	private QuestionAnswerDAO askDAO; 
	@Override
	public Long QueryCountAskByPlaceId(Long placeId) {
		return askDAO.getCountByPlaceId(placeId);
	}
	
	@Override
	public List<PlaceQa> QueryAllAskByPlaceId(Map<String,Object> map) {
		return askDAO.selectAsk(map);
	}

	@Override
	public void AddAskBySelf(PlaceQa ask) {
		askDAO.insertAsk(ask);
	}

	@Override
	public void DelAskBySelfId(Long placeQaId) {
		askDAO.deleteAsk(placeQaId);
		
	}

	@Override
	public void UpdAskBySelf(PlaceQa ask) {
		askDAO.updateAsk(ask);
	}

	@Override
	public PlaceQa QueryQaByQaId(Long placeQaId) {
		return askDAO.selectAskByAskId(placeQaId);
	}
	
	public QuestionAnswerDAO getAskDAO() {
		return askDAO;
	}

	public void setAskDAO(QuestionAnswerDAO askDAO) {
		this.askDAO = askDAO;
	}

	
}
