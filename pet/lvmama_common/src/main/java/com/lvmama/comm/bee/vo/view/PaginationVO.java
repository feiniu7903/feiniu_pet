package com.lvmama.comm.bee.vo.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 分页VO
 * @author songlianjun
 *
 */
public class PaginationVO<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6323155814867134632L;
	/**
	 * 总记录数
	 */
	private long totalRows;
	/**
	 * 分页查询返回结果List
	 */
	private List<T> resultList;
	/**
	 * 分页第一行记号
	 */
	private long beginIndex;
	/**
	 * 分页结束 行号
	 */
	private long endIndex;
	/**
	 * 每页记录数
	 */
	private long pageSize;
	/**
	 * 当前页数
	 */
	private long activePage;
	
	private Map queryParamMap = new HashMap();
	public long getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Long totalRows) {
		this.totalRows = totalRows;
	}
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	public long getBeginIndex() {
		
		return this.getActivePage()*this.getPageSize()+1;
	
	}
	public long getEndIndex() {
		
		return this.getActivePage()*this.getPageSize()+this.getPageSize();
	}
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	public long getActivePage() {
		return activePage;
	}
	public void setActivePage(long activePage) {
		this.activePage = activePage;
	}
	/**
	 * 获取总页数
	 * @return
	 */
	public long getPages(){
		return pageSize>0?totalRows/pageSize:0;
	}
	public void setQueryParamMap(Map queryParamMap) {
		this.queryParamMap = queryParamMap;
	}
	public Map getQueryParamMap() {
		return queryParamMap;
	}
	public void addQueryParam(String key,Object value){
		if(queryParamMap ==null){
			queryParamMap = new HashMap();
		}
		queryParamMap.put(key, value);
	}
	
}
