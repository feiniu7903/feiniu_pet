package com.lvmama.order.po;

import java.util.HashSet;
import java.util.Set;

/**
 * SQL构建器原材料.
 *
 * <pre>
 * {@link com.lvmama.ord.service.po.CompositeQuery}经过加工后的结果
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public final class SQlBuilderMaterial {
	/**
	 * 要查询的表.
	 */
	private Set<String> tableSet = new HashSet<String>();
	/**
	 * 查询条件.
	 */
	private Set<String> conditionSet = new HashSet<String>();
	/**
	 * 排序.
	 */
	private Set<String> orderbySet = new HashSet<String>();
	/**
	 * 分组.
	 */
	private Set<String> groupBySet = new HashSet<String>();
	/**
	 * 起始索引（包括）.
	 */
	private Integer beginIndex;
	/**
	 * 结束索引（包括）.
	 */
	private Integer endIndex;
	/** 
	* 是否取默认的订单状态 
	*/ 
	private boolean defaultOrderStauts=true; 

	public boolean isDefaultOrderStauts() { 
	return defaultOrderStauts; 
	} 

	public void setDefaultOrderStauts(boolean defaultOrderStauts) { 
	this.defaultOrderStauts = defaultOrderStauts; 
	}
	/**
	 * getTableSet.
	 *
	 * @return 要查询的表
	 */
	public Set<String> getTableSet() {
		return tableSet;
	}

	/**
	 * setTableSet.
	 *
	 * @param tableSet
	 *            要查询的表
	 */
	public void setTableSet(final Set<String> tableSet) {
		this.tableSet = tableSet;
	}

	/**
	 * getConditionSet.
	 *
	 * @return 查询条件
	 */
	public Set<String> getConditionSet() {
		return conditionSet;
	}

	/**
	 * setConditionSet.
	 *
	 * @param conditionSet
	 *            查询条件
	 */
	public void setConditionSet(final Set<String> conditionSet) {
		this.conditionSet = conditionSet;
	}

	/**
	 * getOrderbySet.
	 *
	 * @return 排序
	 */
	public Set<String> getOrderbySet() {
		return orderbySet;
	}

	/**
	 * setOrderbySet.
	 *
	 * @param orderbySet
	 *            排序
	 */
	public void setOrderbySet(final Set<String> orderbySet) {
		this.orderbySet = orderbySet;
	}

	/**
	 * getGroupBySet.
	 *
	 * @return 分组
	 */
	public Set<String> getGroupBySet() {
		return groupBySet;
	}

	/**
	 * setGroupBySet.
	 *
	 * @param groupBySet
	 *            分组
	 */
	public void setGroupBySet(final Set<String> groupBySet) {
		this.groupBySet = groupBySet;
	}

	/**
	 * getBeginIndex.
	 *
	 * @return 起始索引（包括）
	 */
	public Integer getBeginIndex() {
		return beginIndex;
	}

	/**
	 * setBeginIndex.
	 *
	 * @param beginIndex
	 *            起始索引（包括）
	 */
	public void setBeginIndex(final Integer beginIndex) {
		this.beginIndex = beginIndex;
	}

	/**
	 * getEndIndex.
	 *
	 * @return 结束索引（包括）
	 */
	public Integer getEndIndex() {
		return endIndex;
	}

	/**
	 * setEndIndex.
	 *
	 * @param endIndex
	 *            结束索引（包括）
	 */
	public void setEndIndex(final Integer endIndex) {
		this.endIndex = endIndex;
	}
}
