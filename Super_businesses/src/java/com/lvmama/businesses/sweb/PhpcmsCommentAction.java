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
import com.lvmama.comm.businesses.po.review.PhpcmsComment;
import com.lvmama.comm.businesses.service.review.InfonewsService;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.vo.Constant;
@Results({
	
	@Result(name="phpcmsComment",location="/WEB-INF/pages/web/review/phpcmsComment.jsp")
})
public class PhpcmsCommentAction extends ContentAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 134532532L;
	@Autowired
	private InfonewsService infonewsService;
	@Action("/phpcmsComment/query")
	public String glarticle() {
		super.initParam();
		pagination = initPage();
		pagination.setCurrentPage(pagination.getCurrentPage());
		pagination.setTotalResultSize(infonewsService.countForPhpcmsComment(param));
		if (pagination.getTotalResultSize() > 0) {
			param.put("start", pagination.getStartRows() - 1);
			param.put("end", pagination.getPageSize());
			pagination.setAllItems(infonewsService.queryPhpcmsCommentByParam(param));
		}
		pagination.buildUrl(getRequest());

		return "phpcmsComment";
	}

	@Action("/phpcmsComment/update")
	public void update(){
		if(StringUtils.isNotBlank(arrayStr)){
			List<String[]> list=parseArray(arrayStr);
			for(String[] m:list){
				 Map<String,Object> param=new HashMap<String, Object>();
				 param.put("commentid", m[0]);
				 param.put("reviewstatus", m[1]);
				 PhpcmsComment old=infonewsService.queryForPhpcmsCommentById(Integer.valueOf(m[0]));
				 if(null!=old&&(!old.getReviewstatus().equals(m[1]))){
					 if(m[1].equals(Constant.REVIEW_STATUS.black.getCode())){
						 param.put("status", "0");//不显示
					 }
					 if(old.getReviewstatus().equals(Constant.REVIEW_STATUS.black.getCode())){
						 param.put("status", "1");
					 }
					 infonewsService.updateForPhpcmsComment(param);
					 //日志
					 ComLog comlog=new ComLog();
					 comlog.setObjectId(Long.valueOf(m[0]));
					 comlog.setObjectType(KeyWordUtils.PHPCMSCOMMENT);
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
		 
		return KeyWordUtils.BUSSINESS_PAGE_SIZE_+KeyWordUtils.PHPCMSCOMMENT;
	}

}
