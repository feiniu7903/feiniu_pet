package com.lvmama.businesses.sweb;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.businesses.review.util.KeyWordUtils;
import com.lvmama.comm.businesses.po.review.BbsPreForumThread;
import com.lvmama.comm.businesses.service.review.BbsService;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.vo.Constant;

@Results({
	 @Result(name = "bbsSubject", location = "/WEB-INF/pages/web/review/bbsSubject.jsp")
})
public class BbsSubjectAction extends ContentAction {
	
	private static final long serialVersionUID = 43566636881L;
	@Autowired
	protected BbsService bbsService;
	@Action("/bbsSubject/query")
	public String query(){
		     super.initParam();
		    pagination=initPage();
		    pagination.setCurrentPage(pagination.getCurrentPage());
			pagination.setTotalResultSize(bbsService.countForThread(param));
			if(pagination.getTotalResultSize()>0){
				 param.put("start", pagination.getStartRows()-1);
				 param.put("end", pagination.getPageSize());
				 List<BbsPreForumThread>  list=bbsService.queryForThreadByParam(param);
				 pagination.setAllItems(list);
			}
			pagination.buildUrl(getRequest());  
 		 return "bbsSubject"; 
	}

	@Action("/bbsSubject/update")
	public void update(){
		if(StringUtils.isNotBlank(arrayStr)){
			List<String[]> list=parseArray(arrayStr);
			for(String[] m:list){
				 Map<String,Object> param=new HashMap<String, Object>();
				 param.put("tid", m[0]);
				 param.put("reviewstatus", m[1]);
				 BbsPreForumThread old=bbsService.queryForThreadByTid(Integer.valueOf(m[0]));
				 if(null!=old&&(!old.getReviewstatus().equals(m[1]))){
					 
					 if(m[1].equals(Constant.REVIEW_STATUS.black.getCode())){
						 param.put("displayorder", "-1");
					 }
					 if(old.getReviewstatus().equals(Constant.REVIEW_STATUS.black.getCode())){
						 param.put("displayorder", "0");
					 }
					 bbsService.updateForThread(param);
					 //日志
					 ComLog comlog=new ComLog();
					 comlog.setObjectId(Long.valueOf(m[0]));
					 comlog.setObjectType(KeyWordUtils.BBSSUBJECT);
					 comlog.setCreateTime(new Date());
					 comlog.setContent("状态改为"+Constant.REVIEW_STATUS.getCnNameByCode(m[1]));
					 comlog.setOperatorName(super.getSessionUserName());
					 comLogService.addComLog(comlog);
				 }
			}
			this.sendAjaxMsg("true");
		}
		this.sendAjaxMsg("false");
	}
	
	@Override
	public String getMemcachPageSizeKey() {
		return KeyWordUtils.BUSSINESS_PAGE_SIZE_+KeyWordUtils.BBSSUBJECT;
	}

}
