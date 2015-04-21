package com.lvmama.pet.web.place;

import com.lvmama.comm.BaseAction;

public abstract class PaginationAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7157281044654066192L;
	/**
	 * 默认每页的显示数
	 */
	protected static final long DEFAULT_PAGE_SIZE = 5L;	
	protected Long totalCount = 0L;
	protected Long startRow = 0L;
	
	public Long getTotalCount() {
		return totalCount;
	}

	public Long getStartRow() {
		return startRow;
	}

	public void setStartRow(Long startRow) {
		this.startRow = startRow;
	}

	public long getTotalPage() {
		if (0 == totalCount) {
			return 0L;
		} 
		if (totalCount % DEFAULT_PAGE_SIZE == 0) {
			return totalCount / DEFAULT_PAGE_SIZE;
		} else {
			return totalCount / DEFAULT_PAGE_SIZE + 1;
		}
	}
	
	public long getCurrentPage() {
		return startRow / DEFAULT_PAGE_SIZE + 1;
	}
	
	public long getDefaultPageSize() {
		return DEFAULT_PAGE_SIZE;
	}	
}
