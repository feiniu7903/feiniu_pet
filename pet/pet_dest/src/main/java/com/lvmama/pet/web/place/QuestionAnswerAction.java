package com.lvmama.pet.web.place;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceQa;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.place.QuestionAnswerService;

@Results({ 
	@Result(name = "paginationOfQuestionAndAnswer", type="freemarker", location = "/WEB-INF/pages/comment/paginationOfQuestionAndAnswer.ftl")
})
public class QuestionAnswerAction extends PaginationAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -601434215790708856L;
	/**
	 * 默认每页的显示数
	 */
	private static final long DEFAULT_PAGE_SIZE = 5L;	
	private QuestionAnswerService askService;
	private PlaceService placeService;
	private List<PlaceQa> placeQAS;
	private Long placeId;
	private Place place;
	
	@Action("/newplace/getPaginationOfQuestionAndAnswer")
	public String getPaginationOfQuestionAndAnswer() {
		if (null == placeId || null == this.startRow) {
			LOG.error("景点标识出错，返回空");
			return null;
		}
		place = placeService.queryPlaceByPlaceId(placeId);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("placeId", placeId);
		parameters.put("startRows", startRow + 1);
		parameters.put("endRows", startRow + DEFAULT_PAGE_SIZE);//默认景点页获取5条点评
		
		totalCount = askService.QueryCountAskByPlaceId(placeId);
		placeQAS = askService.QueryAllAskByPlaceId(parameters);
		
		return "paginationOfQuestionAndAnswer";
	}



	public QuestionAnswerService getAskService() {
		return askService;
	}



	public void setAskService(QuestionAnswerService askService) {
		this.askService = askService;
	}



	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public List<PlaceQa> getPlaceQAS() {
		return placeQAS;
	}



	public Place getPlace() {
		return place;
	}



	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}
}
