package com.lvmama.businesses.sweb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.businesses.review.util.KeyWordUtils;
import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PageElementModel;

@Results({
     	@Result(name = "input", location = "/WEB-INF/pages/web/review/initContent.jsp"),
		@Result(name = "initContent", location = "/WEB-INF/pages/web/review/initContent.jsp"),
		@Result(name = KeyWordUtils.BBSSUBJECT, type = "redirect", location = "/bbsSubject/query.do?fromId=${fromId}&&statusId=${statusId}"),
		@Result(name = KeyWordUtils.BBSCONTENT, type = "redirect", location = "/bbsContent/query.do?fromId=${fromId}&&statusId=${statusId}"),
		@Result(name = KeyWordUtils.CMTCOMMENT, type = "redirect", location = "/keyword/cmtComment.do?reviewStatus=${statusId}&fromId=${fromId}&&statusId=${statusId}"),
		@Result(name = KeyWordUtils.CMTREPLY, type = "redirect", location = "/keyword/cmtReply.do?reviewStatus=${statusId}&fromId=${fromId}&&statusId=${statusId}"),
		@Result(name = KeyWordUtils.GLARTICLE, type = "redirect", location = "/glarticle/query.do?fromId=${fromId}&&statusId=${statusId}"),
		@Result(name = KeyWordUtils.GLCOMMENT, type = "redirect", location = "/glComment/query.do?fromId=${fromId}&&statusId=${statusId}"),  
		@Result(name = KeyWordUtils.PHPCMSCONTENT, type = "redirect", location = "/phpcmsContent/query.do?fromId=${fromId}&&statusId=${statusId}"),
		@Result(name = KeyWordUtils.PHPCMSCOMMENT, type = "redirect", location = "/phpcmsComment/query.do?fromId=${fromId}&&statusId=${statusId}") })

public abstract class ContentAction extends BackBaseAction {
	/**
	 * 
	 */
	protected static final long serialVersionUID = 34563476861L;

	protected int fromId;
	protected int statusId;
	@Autowired
	protected ComLogService comLogService;
	protected String arrayStr;
	private List<PageElementModel> ReviewStatusList = Collections.emptyList();
	/**
	 * 设定页数参数
	 */
	protected String pageSize;
	protected String memcachedparam;
	/**
	 * 日期参数
	 */
	protected String datebegin ;
	protected String dateend ;
	protected  Map<String, Object> param;

	@Action("/review/initContent")
	public String execute() {
		return "initContent";
	}

	@Action("/review/query")
	public String query() {
		String str = "initContent";
		if (fromId != 0L) {
			switch (fromId) {
			case 1:
				str = KeyWordUtils.BBSSUBJECT;
				break;
			case 2:
				str = KeyWordUtils.BBSCONTENT;
				break;
			case 3:
				str = KeyWordUtils.CMTCOMMENT;
				break;
			case 4:
				str = KeyWordUtils.CMTREPLY;
				break;
			case 5:
				str = KeyWordUtils.GLARTICLE;
				break;
			case 6:
				str = KeyWordUtils.GLCOMMENT;
				break;
			case 7:
				str = KeyWordUtils.PHPCMSCONTENT;
				break;
			case 8:
				str = KeyWordUtils.PHPCMSCOMMENT;
				break;
			default:
				break;
			}
		}
		return str;
	}

	@Action("/review/pagesize")
	public void pageSizeSumit() {
		String flag = "false";
		if (StringUtils.isNotBlank(this.pageSize)
				&& StringUtils.isNotBlank(this.memcachedparam)) {
			MemcachedUtil.getInstance().set(this.memcachedparam, pageSize);
			flag = "true";
		}
		this.sendAjaxMsg(flag);
	}

	@Override
	protected <T> Page<?> initPage() {
		long pageSize = NumberUtils.toLong(getRequest().getParameter(
				"perPageRecord"));
		long pageNo = NumberUtils.toLong(getRequest().getParameter("page"));
		pagination = new Page<T>(pageSize < 1 ? 10 : pageSize, pageNo < 1 ? 1
				: pageNo);
		pageSizeSet();
		ReviewStatusList = Constant.REVIEW_STATUS.getList();
		return pagination;
	}

	public void pageSizeSet() {
		String ps = (String) MemcachedUtil.getInstance().get(
				getMemcachPageSizeKey());
		if (StringUtils.isNotBlank(ps)) {
			pagination.setPageSize(Long.valueOf(ps));
		} else {
			pagination.setPageSize(10);
		}
	}

	public abstract String getMemcachPageSizeKey();

	public List<String[]> parseArray(String array) {
		List<String[]> list = new ArrayList<String[]>();
		String[] str = array.split(",");
		for (String s : str) {
			if (s.contains(":")) {
				String[] str2 = s.split(":");
				if (str2.length == 2) {
					list.add(str2);
				}
			}
		}
		return list;
	}

	/**
	 * @return the fromIds
	 */
	public Map getFromIds() {
		Map fromIds = new HashMap<String, Object>();
		fromIds.put(1, "论坛主题列表");
		fromIds.put(2, "论坛帖子内容");
		fromIds.put(3, "点评内容");
		fromIds.put(4, "点评回复");
		fromIds.put(5, "攻略内容");
		fromIds.put(6, "攻略点评");
		fromIds.put(7, "资讯内容");
		fromIds.put(8, "资讯点评");
		return fromIds;
	}

	/**
	 * @return the statusIds
	 */
	public Map getStatusIds() {
		Map statusIds = new HashMap<String, Object>();
		statusIds.put(Constant.REVIEW_STATUS.white.getCode(),
				Constant.REVIEW_STATUS.white.getCnName());
		statusIds.put(Constant.REVIEW_STATUS.black.getCode(),
				Constant.REVIEW_STATUS.black.getCnName());
		statusIds.put(Constant.REVIEW_STATUS.red.getCode(),
				Constant.REVIEW_STATUS.red.getCnName());
		statusIds.put(Constant.REVIEW_STATUS.gray.getCode(),
				Constant.REVIEW_STATUS.gray.getCnName());
		statusIds.put(Constant.REVIEW_STATUS.frenchgrey.getCode(),
				Constant.REVIEW_STATUS.frenchgrey.getCnName());
		return statusIds;
	}
	
	public void initParam(){
		param=new HashMap<String, Object>();
		 if(0!=statusId){
			 param.put("reviewstatus", statusId);
		  }
		  if(StringUtils.isNotBlank(this.datebegin)){
			  param.put("datebegin", datebegin+"  00:00:00");
		  }
		  if(StringUtils.isNotBlank(this.dateend)){
			  param.put("dateend", dateend+" 23:59:59");
		  }
	}

	/**
	 * @return the fromId
	 */
	public int getFromId() {
		return fromId;
	}

	/**
	 * @param fromId
	 *            the fromId to set
	 */
	public void setFromId(int fromId) {
		this.fromId = fromId;
	}

	/**
	 * @param statusId
	 *            the statusId to set
	 */
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return the pageSize
	 */
	public String getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the statusId
	 */
	public int getStatusId() {
		return statusId;
	}

	/**
	 * @return the comLogService
	 */
	public ComLogService getComLogService() {
		return comLogService;
	}

	/**
	 * @param comLogService
	 *            the comLogService to set
	 */
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	/**
	 * @return the arrayStr
	 */
	public String getArrayStr() {
		return arrayStr;
	}

	/**
	 * @param arrayStr
	 *            the arrayStr to set
	 */
	public void setArrayStr(String arrayStr) {
		this.arrayStr = arrayStr;
	}

	public List<PageElementModel> getReviewStatusList() {
		return ReviewStatusList;
	}

	public void setReviewStatusList(List<PageElementModel> reviewStatusList) {
		ReviewStatusList = reviewStatusList;
	}

	/**
	 * @return the memcachedparam
	 */
	public String getMemcachedparam() {
		return memcachedparam;
	}

	/**
	 * @param memcachedparam
	 *            the memcachedparam to set
	 */
	public void setMemcachedparam(String memcachedparam) {
		this.memcachedparam = memcachedparam;
	}

	/**
	 * @return the datebegin
	 */
	public String getDatebegin() {
		return datebegin;
	}

	/**
	 * @param datebegin the datebegin to set
	 */
	public void setDatebegin(String datebegin) {
		this.datebegin = datebegin;
	}

	/**
	 * @return the dateend
	 */
	public String getDateend() {
		return dateend;
	}

	/**
	 * @param dateend the dateend to set
	 */
	public void setDateend(String dateend) {
		this.dateend = dateend;
	}

	/**
	 * @return the param
	 */
	public Map<String,Object> getParam() {
		return param;
	}

	/**
	 * @param param the param to set
	 */
	public void setParam(Map param) {
		this.param = param;
	}
	

}
