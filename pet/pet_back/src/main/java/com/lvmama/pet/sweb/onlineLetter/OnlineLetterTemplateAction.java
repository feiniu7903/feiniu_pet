package com.lvmama.pet.sweb.onlineLetter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.onlineLetter.LetterTemplate;
import com.lvmama.comm.pet.service.onlineLetter.OnlineLetterService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
@Results(value={
		@Result(name="index",location="/WEB-INF/pages/back/onlineLetter/templateList.jsp"),
		@Result(name="addTemplate",location="/WEB-INF/pages/back/onlineLetter/addTemplate.jsp")
		
	})
public class OnlineLetterTemplateAction extends BackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234282622975038110L;
	private static final Logger LOG = Logger.getLogger(OnlineLetterTemplateAction.class);
	
	private OnlineLetterService onlineLetterService;
	
	private LetterTemplate template=new LetterTemplate();
	
	private Date beginCreatedTime; 
	private Date endCreatedTime;
	private String keywords; 
	private boolean initial = Boolean.TRUE;
	
	@Action("/onlineLetter/template/index")
	public String execute(){
		if(null==beginCreatedTime && null==endCreatedTime && StringUtil.isEmptyString(keywords) && initial){
			return "index";
		}
		Map<String,Object> param = initialParam();
		pagination=initPage();
		pagination.buildUrl(getRequest());
		pagination.setTotalResultSize(onlineLetterService.countTemplate(param));
		if(pagination.getTotalResultSize()>0){
			param.put("skipResult", pagination.getStartRows());
			param.put("maxResult", pagination.getEndRows());
			List<LetterTemplate> list=onlineLetterService.queryTemplate(param);
			pagination.setItems(list);
		}
		return "index";
	}
	
	@Action("/onlineLetter/template/showAdd")
	public String showAdd(){
		return "addTemplate";
	}
	
	@Action("/onlineLetter/template/save")
	public void save(){
		if(!validate(template)){
			return;
		}
		try{
			template.setType(Constant.ONLINE_LETTER_TYPE.PROCLAMATION.name());
			template.setUserGroupType(Constant.ONLINE_LETTER_USER_GROUP.ALL_USER.name());
			onlineLetterService.insertTemplate(template);
			sendAjaxResultByJson("{\"errorText\":\"\",\"success\":true}");
		}catch(Exception e){
			LOG.error(e);
			sendAjaxResultByJson("{\"errorText\":\"新增失败\",\"success\":false}");
		}
	}
	
	@Action("/onlineLetter/template/update")
	public void update(){
		if(!validate(template)){
			return;
		}
		try{
			onlineLetterService.updateTemplate(template);
			sendAjaxResultByJson("{\"errorText\":\"\",\"success\":true}");
		}catch(Exception e){
			LOG.error(e);
			sendAjaxResultByJson("{\"errorText\":\"新增失败\",\"success\":false}");
		}
	}
	private Map<String,Object> initialParam(){
		Map<String,Object> parameters = new HashMap<String,Object>();
		if(null!=beginCreatedTime){
			parameters.put("beginCreatedTime", beginCreatedTime);
		}
		if(null!=endCreatedTime){
			parameters.put("endCreatedTime", endCreatedTime);
		}
		if(!StringUtil.isEmptyString(keywords)){
			parameters.put("keywords", keywords);
		}
		return parameters;
	}
	
	private boolean validate(final LetterTemplate obj){
		if(null == obj){
			sendAjaxResultByJson("{\"errorText\":\"参数不能为空\",\"success\":false}");
			return Boolean.FALSE;
		}
		if(StringUtil.isEmptyString(obj.getTitle())){
			sendAjaxResultByJson("{\"errorText\":\"标题不能为空\",\"success\":false}");
			return Boolean.FALSE;
		}
		if(obj.getTitle().length()>100){
			sendAjaxResultByJson("{\"errorText\":\"标题过长\",\"success\":false}");
			return Boolean.FALSE;
		}
		if(StringUtil.isEmptyString(obj.getContent())){
			sendAjaxResultByJson("{\"errorText\":\"模板内容不能为空\",\"success\":false}");
			return Boolean.FALSE;
		}
		if(obj.getContent().length()>2000){
			sendAjaxResultByJson("{\"errorText\":\"模板内容过长\",\"success\":false}");
			return Boolean.FALSE;
		}
		if(null==obj.getBeginTime()){
			sendAjaxResultByJson("{\"errorText\":\"有效期起始时间不能为空\",\"success\":false}");
			return Boolean.FALSE;
		}
		if(null==obj.getEndTime()){
			sendAjaxResultByJson("{\"errorText\":\"有效期结束时间不能为空\",\"success\":false}");
			return Boolean.FALSE;
		}
		if(DateUtil.getDaysBetween(obj.getBeginTime(), obj.getEndTime())>90){
			sendAjaxResultByJson("{\"errorText\":\"有效期时间段不能超过3个月(90天)\",\"success\":false}");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	public LetterTemplate getTemplate() {
		return template;
	}

	public void setTemplate(LetterTemplate template) {
		this.template = template;
	}

	public Date getBeginCreatedTime() {
		return beginCreatedTime;
	}

	public void setBeginCreatedTime(Date beginCreatedTime) {
		this.beginCreatedTime = beginCreatedTime;
	}

	public Date getEndCreatedTime() {
		return endCreatedTime;
	}

	public void setEndCreatedTime(Date endCreatedTime) {
		this.endCreatedTime = endCreatedTime;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public boolean isInitial() {
		return initial;
	}

	public void setInitial(boolean initial) {
		this.initial = initial;
	}

	public void setOnlineLetterService(OnlineLetterService onlineLetterService) {
		this.onlineLetterService = onlineLetterService;
	}
}
