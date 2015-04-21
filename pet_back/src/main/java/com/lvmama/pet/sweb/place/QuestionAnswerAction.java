package com.lvmama.pet.sweb.place;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.PlaceQa;
import com.lvmama.comm.pet.service.place.QuestionAnswerService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.vo.Constant;

/**
 * 针对问答 增删改查
 * @author zhongshuangxi
 *
 */
@Results({
	@Result( name="askList",location="/WEB-INF/pages/back/place/ask/ask_list.jsp"),
	@Result( name="askEdit",location="/WEB-INF/pages/back/place/ask/ask_edit.jsp")
})

public class QuestionAnswerAction  extends BackBaseAction{
	
	/**
	 * 问答action
	 */
	private static final long serialVersionUID = 1L;
	
	private Long askId;
	private Long placeId;
	private List<PlaceQa> asklist;
	private String message;
	private ComLogService comLogService;
	
	private QuestionAnswerService askService;
	private PlaceQa ask;
	
	@Action("/QuestionAnswer/QueryAsk")
	public String query() throws Exception {
		list(placeId);
		return "askList";
	}
	@Action("/doAsk")
	public String doAsk() throws Exception {
		if(ask!=null && ask.getPlaceQaId()==null){
			try{
				if(ask.getSeq()==null){
					ask.setSeq(-1L);
				}
				askService.AddAskBySelf(ask);
				
				comLogService.insert("SCENIC_LOG_PLACE", null, ask.getPlaceId(), super.getSessionUserName(),
				        Constant.QA_LOG_PLACE.createPlaceQa.name(),"添加问答","添加[问答]", "");
				
			}catch(Exception ex){
				ex.printStackTrace();

			}
		}else{
			try{
				askService.UpdAskBySelf(ask);
				this.setMessage("修改成功");
				comLogService.insert("SCENIC_LOG_PLACE", null, ask.getPlaceId(), super.getSessionUserName(),
                        Constant.QA_LOG_PLACE.updatePlaceQa.name(),"修改问答","修改[问答]", "");
			}catch(Exception ex){
				ex.printStackTrace();
				this.setMessage("修改失败");
			}
		}
		placeId = ask.getPlaceId();
		list(ask.getPlaceId());
		return "askList";
	}
	
	@Action("/QuestionAnswer/goToAskEdit")
	public String goEditAsk() throws Exception {
		if(askId!=null){
			ask = askService.QueryQaByQaId(askId);
		}
		if(placeId!=null){
			ask = new PlaceQa();
			ask.setPlaceId(placeId);
		}
		return "askEdit";
	}
	

	
	@Action("/QuestionAnswer/delAsk")
	public String del() throws Exception {
		if(askId!=null){
			try{
				askService.DelAskBySelfId(askId);
				this.setMessage("删除成功");
				comLogService.insert("SCENIC_LOG_PLACE", null, ask.getPlaceId(), super.getSessionUserName(),
                        Constant.QA_LOG_PLACE.deletePlaceQa.name(),"删除问答","删除[问答]", "");
			}catch(Exception ex){
				ex.printStackTrace();
				this.setMessage("删除失败");
			}
		}
		list(placeId);
		return "askList";
	}
	
	@Action("/QuestionAnswer/updAsk")
	public String upd() throws Exception {
		if(ask.getSeq()==null){
			ask.setSeq(0L);
		}
		ask.setFlag(0L);
		askService.UpdAskBySelf(ask);
		list(placeId);
		return "askList";
	}
	
	public void list(Long placeId){
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("placeId",placeId);		
		Long count = askService.QueryCountAskByPlaceId(placeId);
		pagination=initPage();
		pagination.setPageSize(10);
		pagination.setTotalResultSize(count);
		if(pagination.getTotalResultSize()>0){
			param.put("startRows", pagination.getStartRows());
			param.put("endRows", pagination.getEndRows());
		}
		pagination.buildUrl(getRequest());
		asklist = askService.QueryAllAskByPlaceId(param);
	}


	public Long getAskId() {
		return askId;
	}

	public void setAskId(Long askId) {
		this.askId = askId;
	}

	public List<PlaceQa> getAsklist() {
		return asklist;
	}
	public void setAsklist(List<PlaceQa> asklist) {
		this.asklist = asklist;
	}
	public PlaceQa getAsk() {
		return ask;
	}
	public void setAsk(PlaceQa ask) {
		this.ask = ask;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    public ComLogService getComLogService() {
        return comLogService;
    }
    public void setComLogService(ComLogService comLogService) {
        this.comLogService = comLogService;
    }
	
}
