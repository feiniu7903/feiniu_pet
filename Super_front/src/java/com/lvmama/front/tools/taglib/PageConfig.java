package com.lvmama.front.tools.taglib;

import java.util.ArrayList;
import java.util.List;
/**
 * 分页实体类
 * @author zhuchao
 *
 * @param <T>
 */
public  class PageConfig<T> {
	private static final int DEFAULT_PAGE_SIZE = 10;
	/**当前页*/
	private int currentPage = 1;
	/**每页数*/
	private int pageSize = DEFAULT_PAGE_SIZE;
	/**记录总数 */
	private int totalResultSize = 0;
	/**当前页的结束行行数*/
	private int currentRowNum = 0;
	/**记录集 */
	private List<T> items = new ArrayList<T>();
	/**总页数*/
	private int totalPageNum;
	
	private String url;
	
	public PageConfig(int totalResultSize)
	{
		this.totalResultSize = totalResultSize;
		this.pageSize = DEFAULT_PAGE_SIZE;
		this.currentPage = 1;
	}
	public PageConfig(int pageSize,int currentPage)
	{
		if(pageSize<1)
			this.pageSize = DEFAULT_PAGE_SIZE;
		else
			this.pageSize = pageSize;
		this.currentPage = currentPage;
	}	
	public PageConfig(int totalResultSize,int pageSize,int currentPage)
	{
		init(totalResultSize, pageSize, currentPage);
	}
	/**
	 * 初始化分页对象
	 * @param totalResultSize
	 * @param pageSize
	 * @param currentPage
	 */
	private void init(int totalResultSize, int pageSize, int currentPage) {
		this.totalResultSize = totalResultSize;
		this.pageSize = pageSize;
		if(currentPage<1) 
			this.currentPage = 1;
		else if(currentPage > this.getTotalPages()) 
			this.currentPage = this.getTotalPages();
		else 
			this.currentPage = currentPage;
		totalPageNum = getTotalPages();
	}
	
	public int getCurrentPage()
	{
		return currentPage;
	}
	public void setCurrentPage(int currentPage)
	{
		if(currentPage<1) this.currentPage = 1;
		else if(currentPage > this.getTotalPages()) this.currentPage = this.getTotalPages();
		else this.currentPage = currentPage;
	}
	
	public void setTotalResultSize(int totalResultSize)
	{
		this.totalResultSize = totalResultSize;
		this.init(this.totalResultSize, this.pageSize, this.currentPage);
	}	
	//当前页的第一条行数，第一页第一条为0开始
	public int getStartResult()
	{
		return (int)(currentPage - 1)* pageSize;
	}
	
	public int getTotalPages()
	{
		if(this.totalResultSize%this.pageSize > 0) return (int)this.totalResultSize/this.pageSize + 1;
		else return (int)this.totalResultSize/this.pageSize;
	}
	
	public static PageConfig top(int totalResultSize,int currentPage)
	{
		PageConfig pageConfig = new PageConfig(totalResultSize,DEFAULT_PAGE_SIZE,currentPage);
		return pageConfig;
	}
	
	/**
	 * 设置PageConfig
	 * @param totalResultSize 记录总数
	 * @param pageSize 每页数
	 * @param currentPage 当前页
	 * @return
	 */
	public static PageConfig page(int totalResultSize,int pageSize,int currentPage)
	{
		return new PageConfig(totalResultSize,pageSize,currentPage);
	}
	
	public static PageConfig page(int pageSize,int currentPage)
	{
		return new PageConfig(pageSize,currentPage);
	}
	/**
	 * 重置记录数量总数
	 * @param page 每页数
	 * @param totalResultSize 当前页
	 * @return
	 */
	public void resetTotalResultSize(PageConfig<T> pageConfig,int totalResultSize)
	{
		if(totalResultSize<=0)
			return;
		pageConfig.setTotalResultSize(totalResultSize);
		int totoalNumCount = getTotalPages();
		pageConfig.setTotalPageNum(totoalNumCount);
		if(this.currentPage<1) this.currentPage = 1;
		else if(this.currentPage > totoalNumCount) this.currentPage = totoalNumCount;
	}
	/**
	 * 当前页的结束行
	 * @return
	 */
	public int getCurrentRowNum() {
		int num = 0;
		if(this.currentPage==this.totalPageNum){
			num = this.getTotalResultSize();
		}else {
			num = this.getStartResult()+this.getPageSize();
		}
		return num;
	}
	
	public int getTotalPageNum() {
		return totalPageNum;
	}
	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}
	public void setCurrentRowNum(int currentRowNum) {
		this.currentRowNum = currentRowNum;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 每页数显示数量，默认10
	 * @return
	 */
	public int getPageSize()
	{
		return pageSize;
	}
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}
	public int getTotalResultSize()
	{
		return totalResultSize;
	}

	public List<T> getItems()
	{
		return items;
	}

	public void setItems(List<T> items)
	{
		this.items = items;
	}
}
