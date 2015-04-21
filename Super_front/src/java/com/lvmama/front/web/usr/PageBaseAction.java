package com.lvmama.front.web.usr;

import org.python.modules.math;

import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.front.web.BaseAction;

public class PageBaseAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer pageNo;
	private Integer pageCount;
	private Integer pageSize=10;
	
	protected void initialPageIndex(CompositeQuery compositeQuery){
		PageIndex pageIndex = new PageIndex();
		if (pageNo==null) pageNo=1;
		pageIndex.setBeginIndex((pageNo-1)*pageSize+1);
		pageIndex.setEndIndex(pageNo*pageSize);
		compositeQuery.setPageIndex(pageIndex);
	}
	
	protected void initalPageCount(Long dataSize){
		pageCount = new Integer( new Double(math.ceil((float)dataSize/(float)pageSize.intValue())).intValue());
		
	}
	
	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

}
