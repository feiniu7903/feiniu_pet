package com.lvmama.pet.sweb.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.service.comment.CmtSpecialSubjectService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.WebUtils;
import com.lvmama.comm.vo.comment.CmtSpecialSubjectVO;

/**
 * 专题管理的列表Action
 * @author yuzhizeng
 * 
 *	http://super.lvmama.com/pet_back/commentManager/querySpecialSubList.do
 *
 *D:\eclipse4_x32\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\ROOT
 *
 *D:\eclipse4_x32\workSpace_v2\code\maven.1353657485750\pet\pet_back
 */
public class ListSpecialSubjectAction extends BackBaseAction {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 专题管理逻辑接口
	 */
	private CmtSpecialSubjectService cmtSpecialSubjectService;
	/**
	 * 专题管理列表
	 */
	private List<CmtSpecialSubjectVO> cmtSpecialSubjectList;
	
	/**
	 * 专题标识
	 */
	private Long id;
	/**
	 * 编辑的专题管理
	 */
	private CmtSpecialSubjectVO cmtSpecialSubject;
	/**
	 * 参数列表
	 */
	private Map<String, Object> params;
	
	/**
	 * 查询
	 */
	//@Action(value="/mark/coupon/queryCouponList",results={@Result(location = "/WEB-INF/pages/back/mark/couponList.jsp")})
	@Action(value="/commentManager/querySpecialSubList",results={@Result(location = "/WEB-INF/pages/back/comment/specialSubList.jsp")})
	public String search() {
		
		params = new HashMap<String, Object>();
		Long totalRecords = cmtSpecialSubjectService.count(params);
		pagination = Page.page(10, page);
		if(totalRecords == null) totalRecords = 0L;
		pagination.setTotalResultSize(totalRecords);
		
		String url = WebUtils.getUrl(getRequest());
		pagination.setUrl(url);
		params.put("_startRow", pagination.getStartRows());
		params.put("_endRow", pagination.getEndRows());
		cmtSpecialSubjectList = cmtSpecialSubjectService.query(params);
		
		/*
		 *  params接受页面的参数,设置分页，params查询LIST设置到分页中
		initialPageInfoByMap(cmtSpecialSubjectService.count(params), params);
		cmtSpecialSubjectList = cmtSpecialSubjectService.query(params);
		*/
		return SUCCESS;
	}
	 
	/**
	 * 打开新增(编辑)专题页面.
	 * @return
	 */
	@Action(value="/commentManager/editSpecialSubject",results={@Result(location = "/WEB-INF/pages/back/comment/editSpecialSub.jsp")}) 
	public String editSpecialSubject() {
		if (null != id) {
			cmtSpecialSubject = cmtSpecialSubjectService.queryByPk(id);
		} 
		if (null == cmtSpecialSubject) {
			cmtSpecialSubject = new CmtSpecialSubjectVO();
		}
		return SUCCESS;
	}
	
	public List<CmtSpecialSubjectVO> getCmtSpecialSubjectList() {
		return cmtSpecialSubjectList;
	}

	public void setCmtSpecialSubjectService(
			CmtSpecialSubjectService cmtSpecialSubjectService) {
		this.cmtSpecialSubjectService = cmtSpecialSubjectService;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public CmtSpecialSubjectService getCmtSpecialSubjectService() {
		return cmtSpecialSubjectService;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setCmtSpecialSubjectList(
			List<CmtSpecialSubjectVO> cmtSpecialSubjectList) {
		this.cmtSpecialSubjectList = cmtSpecialSubjectList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CmtSpecialSubjectVO getCmtSpecialSubject() {
		return cmtSpecialSubject;
	}

	public void setCmtSpecialSubject(CmtSpecialSubjectVO cmtSpecialSubject) {
		this.cmtSpecialSubject = cmtSpecialSubject;
	}
	
}
