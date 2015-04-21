package com.lvmama.pet.sweb.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.comment.CmtRecommendPlace;
import com.lvmama.comm.pet.service.comment.CmtRecommendPlaceService;
import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;

/**
 * 推荐点评
 */
@Results({ 
	@Result(name = "recomment", location = "/WEB-INF/pages/back/comment/recomment.jsp")
	})
public class CmtRecommentPlaceAction extends BackBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 1973023189428137752L;
	/**
	 * 景点点评统计服务接口
	 */
	private CmtRecommendPlaceService cmtRecommendPlaceService;
	
	private CmtTitleStatistisService cmtTitleStatistisService;
	
	private PlaceService placeService;
	/**
	 * 获取当前的景点ID
	 */
	private List<CmtRecommendPlace> placeList;

	/**
	 * 查询点评推荐的所有ID
	 */
	@Action(value="/commentManager/cmtRecommentPlaceQuery")
	public String execute() {
		placeList = cmtRecommendPlaceService.queryRecommendPlace();
		return "recomment";
	}

	/**
	 * 自动查询并保存结果
	 */
	@Action(value="/commentManager/recommendSaveAu")
	public String saveAu() {
		try{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("stage", 2); //点评招募目的地类型
			parameters.put("_startRow", 0);
			parameters.put("_endRow", 10);
			List<CmtTitleStatisticsVO> commentStatisticsList = cmtTitleStatistisService.recommendQuery(parameters);
			for(int i=0;i<commentStatisticsList.size();i++){
				updatePlaceId((long)i+1,commentStatisticsList.get(i).getPlaceId(),"Y");
			}
		}catch(Exception e){
			LOG.warn("saveMu error:\r\n"+e);
		}
		return execute();
	}

	/**
	 * 手动
	 */
	@Action(value="/commentManager/recommendSaveMu")
	public void saveMu() {
		String placeArrayString = getRequest().getParameter("placeArray");
		if(StringUtil.isEmptyString(placeArrayString)){
			sendAjaxResultByJson("{\"errorText\":\"没有传入景点ID\",\"success\":false}");
			return;
		}
		String[] placeArray = placeArrayString.split(",");
		String errorString = "";
		for(String place:placeArray){
			String[] placeStr = place.split("=");
			if(null==placeService.queryPlaceByPlaceId(Long.valueOf(placeStr[1])) || placeStr.length!=2){
				errorString +=","+placeStr[1];
			}
		}
		if(!StringUtil.isEmptyString(errorString)){
			sendAjaxResultByJson("{\"errorText\":\""+errorString+"\",\"success\":false}");
			return;
		}
		for(String place:placeArray){
			String[] placeStr = place.split("=");
			updatePlaceId(Long.valueOf(placeStr[0]),Long.valueOf(placeStr[1]),"N");
		}
		sendAjaxResultByJson("{\"errorText\":\"\",\"success\":true}");
	}


	/**
	 * 根据景点ID做修改操作
	 * @param placeId 景点Id
	 * @param sequenece     序列值
	 */
	public void updatePlaceId(final Long cmtRecommendPlaceId, final Long placeId, final String action) {
		CmtRecommendPlace cmtRecommendPlace = new CmtRecommendPlace();
		cmtRecommendPlace.setCmtRecommendPlaceId(cmtRecommendPlaceId);
		cmtRecommendPlace.setPlaceId(placeId);
		cmtRecommendPlace.setAction(action);
		cmtRecommendPlaceService.updateRecommend(cmtRecommendPlace);
	}

	public List<CmtRecommendPlace> getPlaceList() {
		return placeList;
	}

	public void setPlaceList(final List<CmtRecommendPlace> placeList) {
		this.placeList = placeList;
	}

	/**
	 * @param cmtRecommendPlaceService the cmtRecommendPlaceService to set
	 */
	public void setCmtRecommendPlaceService(CmtRecommendPlaceService cmtRecommendPlaceService) {
		this.cmtRecommendPlaceService = cmtRecommendPlaceService;
	}

	public void setCmtTitleStatistisService(
			CmtTitleStatistisService cmtTitleStatistisService) {
		this.cmtTitleStatistisService = cmtTitleStatistisService;
	}

	public PlaceService getPlaceService() {
		return placeService;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

}
