package com.lvmama.finance;

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

	// -- 公共变量 --//
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	/**
	 * 当前页
	 */
	protected int page = 1;

	/**
	 * 包含实际数据的数组
	 */
	protected List<T> result = null;
	/**
	 * 查询出的总记录数
	 */
	protected Integer records;
	/**
	 * 总页数
	 */
	protected Integer total;
	/**
	 * 分页大小
	 */
	private Integer rows;

	// -- 构造函数 --//
	public Page() {
	}

	public int getPage() {
		return page;
	}

	public void setPage(final int page) {
		this.page = page;

		if (page < 1) {
			this.page = 1;
		}
	}

	public Page<T> page(final int thePage) {
		setPage(thePage);
		return this;
	}

	public int getTotal() {
		if (records < 0) {
			return -1;
		}
		int count = records / rows;
		if (records % rows > 0) {
			count++;
		}
		return count;
	}

	@JsonIgnore
	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	@JsonIgnore
	public int getStart() {
		return ((page - 1) * rows) + 1;
	}

	@JsonIgnore
	public int getEnd() {
		return page * rows;
	}

}
