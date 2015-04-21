package com.lvmama.comm.pet.vo;

import java.io.Serializable;
import java.util.Map;


/**
 * 分页查询上下文
 * 
 * @author yanggan
 * 
 */
@SuppressWarnings("rawtypes")
public class PageSearchContext implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1406114826873842286L;

	private Map context;
	
	private Map pageinfo;
	
	public Integer getPageSize() {
		return (Integer) pageinfo.get(FincConstant.PAGE_PAGESIZE);
	}
	public Integer getCurrpage() {
		return (Integer) pageinfo.get(FincConstant.PAGE_CURRPAGE);
	}
	/*
	 * 获取排序的字段
	 */
	public String getOrderby() {
		return (String) pageinfo.get(FincConstant.PAGE_ORDERBY);
	}

	/*
	 * 获取排序的规则
	 */
	public String getOrder() {
		return (String) pageinfo.get(FincConstant.PAGE_ORDER);
	}

	/*
	 * 获取分页查询开始位置
	 */
	public Integer getStart() {
		return Integer.parseInt(pageinfo.get(FincConstant.PAGE_START).toString());
	}

	/*
	 * 获取分页查询结束位置
	 */
	public Integer getEnd() {
		return Integer.parseInt(pageinfo.get(FincConstant.PAGE_END).toString());
	}

	public Map getContext() {
		return context;
	}
	public void setContext(Map context) {
		this.context = context;
	}
	public Map getPageinfo() {
		return pageinfo;
	}
	public void setPageinfo(Map pageinfo) {
		this.pageinfo = pageinfo;
	}

}
