package com.lvmama.finance.base;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 分页类
 * 
 * @author yanggan
 * 
 * @param <T>
 */
public class Page<T> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 当前页
	 */
	protected int currpage = 1;
	/**
	 * 总页数
	 */
	protected Integer totalpages;
	/**
	 * 查询出的总记录数
	 */
	protected Integer totalrecords;
	/**
	 * 分页大小
	 */
	private Integer pagesize;
	/**
	 * 实际数据
	 */
	protected List<T> invdata = null;

	// -- 构造函数 --//
	public Page() {
	}

	public int getTotalpages() {
		if (totalrecords < 0) {
			return -1;
		}
		int count = totalrecords / pagesize;
		if (totalrecords % pagesize > 0) {
			count++;
		}
		return count;
	}

	@JsonIgnore
	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public List<T> getInvdata() {
		return invdata;
	}

	public void setInvdata(List<T> invdata) {
		this.invdata = invdata;
	}

	public Integer getTotalrecords() {
		return totalrecords;
	}

	public void setTotalrecords(Integer totalrecords) {
		this.totalrecords = totalrecords;
	}

	public int getCurrpage() {
		return currpage;
	}

	public void setCurrpage(int currpage) {
		this.currpage = currpage;
	}

	public void setTotalpages(Integer totalpages) {
		this.totalpages = totalpages;
	}

}
