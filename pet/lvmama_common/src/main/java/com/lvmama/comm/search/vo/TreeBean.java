package com.lvmama.comm.search.vo;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public class TreeBean<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 650005155877227058L;
	private T node;
	private List<TreeBean> subNode;
	private String sort;

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public T getNode() {
		return node;
	}

	public void setNode(T node) {
		this.node = node;
	}

	public List<TreeBean> getSubNode() {
		return subNode;
	}

	public void setSubNode(List<TreeBean> subNode) {
		this.subNode = subNode;
	}

	/** 排序比较器 **/
	public static class comparatorChinaTree implements Comparator<TreeBean> {
		public int compare(TreeBean o1, TreeBean o2) {
			TreeBean s1 = (TreeBean) o1;
			TreeBean s2 = (TreeBean) o2;
			int result = (s1.sort).compareTo(s2.sort);
			return result;
		}

	}
}
