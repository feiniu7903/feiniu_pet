package com.lvmama.back.utils;

import java.io.Serializable;
import java.util.List;

/**
 * Pagination Encapsulated various kinds of functions to deal with page-splits
 * 
 * @author huangl
 */
public class Pagination implements Serializable{
	
	private static final long serialVersionUID = -6092334105077108415L;

	/**
	 * 当前页数
	 * */
	private int page = 1;

	/**
	 * 总记录数
	 * */
	private long totalRecords;

	/**
	 * 每页记录数
	 * */
	private int perPageRecord = 10;
	
	/**
	 * 总共页数
	 * */
	private int totalPages;
	
	/**
	 * 集合对象
	 * */
	private List records;
	
	/**
	 * 提交actonUrl
	 * */
	private String actionUrl;

	public List getRecords() {
		return records;
	}

	public void setRecords(List records) {
		this.records = records;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	
	public int getTotalPages() {
		if (totalRecords % perPageRecord == 0)
			totalPages = (int)totalRecords / perPageRecord;
		else
			totalPages = 1 + (int)totalRecords / perPageRecord;

		return totalPages;
	}
	
	public int getPerPageRecord() {
		return perPageRecord;
	}

	public void setPerPageRecord(int perPageRecord) {
		this.perPageRecord = perPageRecord;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * 每页起始行
	 * */
	public int getFirstRow() {
		return (page - 1) * perPageRecord + 1;
	}

	/**
	 * 每页结束行
	 * */
	public int getLastRow() {
		return page == getTotalPages() ?(int) getTotalRecords() : page * perPageRecord;
	}

	/**
	 * 上页页码
	 * */
	public int getPreviousPage() {
		return page > 1 ? page-1 : page;
	} 

	/**
	 * 下页页码
	 * */
	public int getNextPage() {
		return page < getTotalPages() ? page+1 : page;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
}

