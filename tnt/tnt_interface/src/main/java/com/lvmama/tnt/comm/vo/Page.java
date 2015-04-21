package com.lvmama.tnt.comm.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 分页类
 * 
 * @author hupeipei
 * 
 * @param <T>
 */
public class Page<T> implements java.io.Serializable {

	/**
	 * 设置PageConfig
	 * 
	 * @param totalResultSize
	 *            记录总数
	 * @param pageSize
	 *            每页数
	 * @param currentPage
	 *            当前页
	 * @return
	 */
	public static <T> Page<T> page(long totalResultSize, long pageSize,
			long currentPage) {
		return new Page<T>(totalResultSize, pageSize, currentPage);
	}

	public static <T> Page<T> page(long pageSize, long currentPage) {
		return new Page<T>(pageSize, currentPage);
	}

	public static <T> Page<T> page(long pageSize, long currentPage, T param) {
		Page<T> page = new Page<T>(pageSize, currentPage);
		page.setParam(param);
		return page;
	}

	public static <T> Page<T> page(long currentPage, T param) {
		Page<T> page = new Page<T>(DEFAULT_PAGE_SIZE, currentPage);
		page.setParam(param);
		return page;
	}

	public static final String KEY = "page";
	public static final int MODEL_FRONT = 0;
	private static final long serialVersionUID = 8450665768936866696L;
	private static final long DEFAULT_PAGE_SIZE = 10;
	private static final long TOTAL_COUNT_UNKNOW = 0L;
	/** 当前页 */
	private long currentPage = 1;
	/** 每页数查询数量，默认10条 */
	private long pageSize = DEFAULT_PAGE_SIZE;
	/** 记录总数 */
	private long totalResultSize = 0;
	/** 记录集 */
	private List<T> items = Collections.emptyList();
	/** 总页数 */
	private long totalPageNum;

	/** 自动查记录总数标示 */
	protected boolean autoCount = true;

	/** 页面分页显示 **/
	private String pagination;

	private int model = 10;

	public int getModel() {
		return model;
	}

	public void setModel(int model) {
		this.model = model;
	}

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	public T getParam() {
		return param;
	}

	public void setParam(T param) {
		this.param = param;
	}

	/** 查询条件 **/
	private T param;

	/** 用于排序 **/
	protected List<String> orderList = new ArrayList<String>();
	protected List<String> ascOrderList = new ArrayList<String>();
	protected List<String> descOrderList = new ArrayList<String>();

	private String actionType = "do";

	private String currentPageParamName = "page";

	private String url;

	private String requestURI;

	private boolean ajaxFlag = false;

	private List<T> allItems = Collections.emptyList();

	private List<RequestKV> params = Collections.emptyList();

	public void setParams(List<RequestKV> params) {
		this.params = params;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Page() {

	}

	public Page(long totalResultSize) {
		this.totalResultSize = totalResultSize;
		this.pageSize = DEFAULT_PAGE_SIZE;
		this.currentPage = 1;
	}

	public Page(long pageSize, long currentPage) {
		if (pageSize < 1) {
			this.pageSize = DEFAULT_PAGE_SIZE;
		} else {
			this.pageSize = pageSize;
		}
		this.currentPage = currentPage;
	}

	/**
	 * @param totalResultSize
	 *            记录总数
	 * @param pageSize
	 *            每页显示数
	 * @param currentPage
	 *            当前页
	 */
	public Page(long totalResultSize, long pageSize, long currentPage) {
		this.totalResultSize = totalResultSize;
		this.pageSize = pageSize;
		if ((currentPage < 1) || (totalResultSize <= pageSize)) {
			this.currentPage = 1;
		} else {
			this.currentPage = currentPage;
		}
		totalPageNum = getTotalPages();
		if (currentPage > totalPageNum) {
			this.currentPage = 1;
		}
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(final Long currentPage) {
		if ((currentPage == null) || (currentPage < 1)) {
			this.currentPage = 1;
		}
		this.currentPage = currentPage;
	}

	/**
	 * 每页数显示数量，默认10
	 * 
	 * @return
	 */
	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalResultSize() {
		return totalResultSize;
	}

	public void setTotalResultSize(long totalResultSize) {
		this.totalResultSize = totalResultSize;
		this.totalPageNum = getTotalPages();
	}

	public List<T> getItems() {
		if ((items == null) || (items.size() == 0)) {
			if (allItems.size() > 0) {
				Long i = this.getStartRows() - 1;
				int end = this.getCurrentRowNum();
				if (i < end) {
					return allItems.subList(i.intValue(), end);
				}

			}
		}
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public long getStartRows() {
		return ((currentPage - 1) * pageSize) + 1;
	}

	public long getTotalPages() {
		if ((this.totalResultSize % this.pageSize) > 0) {
			return (this.totalResultSize / this.pageSize) + 1;
		} else {
			return this.totalResultSize / this.pageSize;
		}
	}

	public long getTotalPageNum() {
		return this.getTotalPages();
	}

	public void setTotalPageNum(long totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	/**
	 * 重置记录数量总数
	 * 
	 * @param page
	 *            每页数
	 * @param totalResultSize
	 *            当前页
	 * @return
	 */
	public void resetTotalResultSize(Page<T> pageConfig, long totalResultSize) {
		if (totalResultSize <= 0) {
			return;
		}
		pageConfig.setTotalResultSize(totalResultSize);
		long totoalNumCount = getTotalPages();
		pageConfig.setTotalPageNum(totoalNumCount);
	}

	/**
	 * 当前页的结束行
	 * 
	 * @return
	 */
	public long getEndRows() {
		long num = 0;
		if (this.currentPage == this.totalPageNum) {
			num = this.getTotalResultSize();
		} else {
			num = (this.getStartRows() + this.getPageSize()) - 1;
		}
		return num;
	}

	public boolean isAjaxFlag() {
		return ajaxFlag;
	}

	public void setAjaxFlag(boolean ajaxFlag) {
		this.ajaxFlag = ajaxFlag;
	}

	public String getCurrentPageParamName() {
		return currentPageParamName;
	}

	public void setCurrentPageParamName(String currentPageParamName) {
		this.currentPageParamName = currentPageParamName;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public void setPage(Long page) {
		this.currentPage = page;
	}

	public Long getPage() {
		return this.currentPage;
	}

	/**
	 * 是否存在下一页
	 * 
	 * @return
	 */
	public boolean hasNext() {
		long totalPage = getTotalPages();
		return (totalPage > currentPage);
	}

	public boolean hasPrev() {
		return (currentPage > 1);
	}

	public List<T> getAllItems() {
		return allItems;
	}

	public void setAllItems(List<T> allItems) {
		this.allItems = allItems;
	}

	/**
	 * 当前页的结束行 工list subList用
	 * 
	 * @return
	 */
	public int getCurrentRowNum() {
		Long num = 0L;
		if (this.currentPage == this.totalPageNum) {
			num = this.getTotalResultSize(); // 原来减1 ； subList(start,end)
												// 是不包含end ，比如0,2 截取的是0,1 ；
		} else {
			num = (this.getStartRows() - 1) + this.getPageSize();

			if (num >= allItems.size()) {
				num = allItems.size() - 1L;
			}
		}
		return num.intValue();
	}

	public List<RequestKV> getParams() {
		return params;
	}

	public String getRequestURI() {
		return requestURI;
	}

	public boolean isAutoCount() {
		return autoCount && totalResultSize == TOTAL_COUNT_UNKNOW;
	}

	public Page<T> setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
		return this;
	}

	public List<String> getOrderList() {
		return orderList;
	}

	public List<String> getAscOrderList() {
		return ascOrderList;
	}

	public List<String> getDescOrderList() {
		return descOrderList;
	}

	public Page<T> asc(String column) {
		orderList.add(column + " ASC");
		ascOrderList.add(column);
		return this;
	}

	public Page<T> desc(String column) {
		orderList.add(column + " DESC");
		descOrderList.add(column);
		return this;
	}

}
