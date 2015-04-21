package com.lvmama.businesses.sweb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.businesses.review.util.KeyWordUtils;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

@Results({ @Result(name = "cmtComment", location = "/WEB-INF/pages/web/review/cmtComment.jsp") })
public class CmtCommentAction extends ContentAction {
	private static final long serialVersionUID = 14564645645L;
	private CmtCommentService cmtCommentService;
	private List<CommonCmtCommentVO> CmtCommentList;
	private String reviewStatus;
	private String startDate;
	private String endDate;

	/*************************** cmtComment ***************************/
	@Action("/keyword/cmtComment")
	public String goCmtComment() {
		list_cmtComment();
		return "cmtComment";
	}

	@Action("/keyword/doCmtCommentUpdate")
	public void doUpdateCmtComment() {
		if(StringUtils.isNotBlank(arrayStr)){
			List<String[]> list=parseArray(arrayStr);
			for(String[] m:list){
				 CommonCmtCommentVO vo=cmtCommentService.getCmtCommentByKey(Long.valueOf(m[0]));
 				 if(m[1].equals(Constant.REVIEW_STATUS.black.getCode())){
 					vo.setIsHide("Y");
				 }else{
				    vo.setIsHide("N");
				 }
				 if(! m[1].equals(String.valueOf(vo.getReviewStatus()))){
				     ComLog comlog=new ComLog();
	                 comlog.setObjectId(Long.valueOf(m[0]));
	                 comlog.setObjectType(KeyWordUtils.CMTCOMMENT);
	                 comlog.setCreateTime(new Date());
	                 comlog.setContent("状态改为"+Constant.REVIEW_STATUS.getCnNameByCode(m[1]));
	                 comlog.setOperatorName(super.getSessionUserName());
	                 comLogService.addComLog(comlog);
				 }
				 vo.setReviewStatus(Long.valueOf(m[1]));
				 cmtCommentService.updateCmtCommentToReviewStatus(vo);
			}
			this.sendAjaxMsg("true");
		}
		this.sendAjaxMsg("false");
	}

	public void list_cmtComment() {
		pagination = initPage();
		Map<String, Object> param = builderParam();
		pagination.setTotalResultSize(cmtCommentService
				.getCountOfCmtParam(param));
		if (pagination.getTotalResultSize() > 0) {
			param.put("startRows", pagination.getStartRows());
			param.put("endRows", pagination.getEndRows());
			CmtCommentList = cmtCommentService.getCmtCommentByParam(param);
			pagination.setAllItems(CmtCommentList);
		}
		pagination.buildUrl(getRequest());
	}

	public Map<String, Object> builderParam() {
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isBlank(reviewStatus)) {
			param.put("reviewStatus", Constant.REVIEW_STATUS.gray.getCode());
		} else {
			param.put("reviewStatus", reviewStatus);
		}
		if (null != startDate && !"".equals(startDate)) {
			param.put("startDate", convertToDate(startDate + " 00:00:00"));
		}
		if (null != endDate && !"".equals(endDate)) {
			param.put("endDate", convertToDate(endDate + " 23:59:59"));
		}
		param.put("isAudit","AUDIT_SUCCESS");
		return param;
	}

	private Date convertToDate(String dateStr) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/************************************** getter/setter ****************************************/

	public CmtCommentService getCmtCommentService() {
		return cmtCommentService;
	}

	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}

	public List<CommonCmtCommentVO> getCmtCommentList() {
		return CmtCommentList;
	}

	public void setCmtCommentList(List<CommonCmtCommentVO> cmtCommentList) {
		CmtCommentList = cmtCommentList;
	}

	@Override
	public String getMemcachPageSizeKey() {
	    return KeyWordUtils.BUSSINESS_PAGE_SIZE_+KeyWordUtils.CMTCOMMENT;
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
