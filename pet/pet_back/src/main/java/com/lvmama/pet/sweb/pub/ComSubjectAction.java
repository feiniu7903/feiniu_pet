package com.lvmama.pet.sweb.pub;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pub.ComSubject;
import com.lvmama.comm.pet.service.pub.ComSubjectService;



/**
 * 公用主题Action
 *
 */
@Results( {
	@Result(name = "subjectList", location = "/WEB-INF/pages/back/subject/comSubjectList.jsp", type = "dispatcher"),
	@Result(name = "subjectEdit", location = "/WEB-INF/pages/back/subject/comSubjectEdit.jsp", type = "dispatcher")
})
public class ComSubjectAction  extends BackBaseAction {
	private static final long serialVersionUID = -3000098832309983241L;
	
	private ComSubjectService comSubjectService;
	private List<ComSubject> comSubjectList;
	private ComSubject comSubject = new ComSubject();
	private String comSubjectIdStr;
	private String subjectTypeStr;
	private String message;
	private String subjectSeqs;

	@Action("/pub/subject/subjectList")
	public String queryComSubjects(){
		comSubjectList = comSubjectService.findComSubjects(comSubject, Integer.MAX_VALUE);
		return "subjectList";
	}
	
	@Action("/pub/subject/subjectEdie")
	public String editComSubjects(){
		comSubject.setSubjectType(subjectTypeStr);
		//修改
		if(StringUtils.isNotBlank(comSubjectIdStr)){
			comSubject.setComSubjectId(Long.parseLong(comSubjectIdStr));
			comSubject = comSubjectService.getComSubjectById(comSubject);
		}else{
			//新增
			comSubject.setCreateTime(new Date());
			comSubject.setUpdateTime(new Date());
			comSubject.setUsedByCount(0L);
		}
		return "subjectEdit";
	}
	
	@Action("/pub/subject/subjectSaveOrUpdate")
	public void saveComSubjects() throws Exception{
		comSubjectService.saveOrUpdateComSubject(comSubject);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("message", "操作成功！");
		this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
 	}

	@Action("/pub/subject/updateSeq")
	public String updateSeqs() throws Exception {
		try{
			comSubjectService.updateSeqs(subjectSeqs);
			this.sendAjaxMsg("success");
		}catch(Exception ex){
			ex.printStackTrace();
			this.sendAjaxMsg("error");
		}
		return null;
	}
	
	public void setComSubjectService(ComSubjectService comSubjectService) {
		this.comSubjectService = comSubjectService;
	}

	public List<ComSubject> getComSubjectList() {
		return comSubjectList;
	}

	public void setComSubject(ComSubject comSubject) {
		this.comSubject = comSubject;
	}

	public ComSubject getComSubject() {
		return comSubject;
	}

	public String getMessage() {
		return message;
	}

	public String getSubjectSeqs() {
		return subjectSeqs;
	}

	public void setSubjectSeqs(String subjectSeqs) {
		this.subjectSeqs = subjectSeqs;
	}

	public String getComSubjectIdStr() {
		return comSubjectIdStr;
	}

	public void setComSubjectIdStr(String comSubjectIdStr) {
		this.comSubjectIdStr = comSubjectIdStr;
	}

	public String getSubjectTypeStr() {
		return subjectTypeStr;
	}

	public void setSubjectTypeStr(String subjectTypeStr) {
		this.subjectTypeStr = subjectTypeStr;
	}

}

