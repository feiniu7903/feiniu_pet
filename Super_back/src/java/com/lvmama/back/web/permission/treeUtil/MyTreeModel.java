package com.lvmama.back.web.permission.treeUtil;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.SimpleTreeNode;

/**
 * @author shihui
 * */
public class MyTreeModel {

	List<MyTreeItem> list;

	/**
	 * 根据当前的id，循环取出子节点信息
	 * */
	public List<MyTreeItem> getChildren(MyTree parent) {
		Long father = parent.getTreeItem().getId();
		List<MyTreeItem> children = new ArrayList<MyTreeItem>();
		for (int i = 0; i < list.size(); i++) {
			if (father != null && father.equals(list.get(i).getParentId())) {
				children.add(list.get(i));
			}
		}
		return children;
	}

	/**
	 * 利用递归，组建所有的节点
	 * */
	public void addChild(MyTree parent) {
		List<MyTreeItem> children = getChildren(parent);
		for (MyTreeItem child : children) {
			MyTree tree = new MyTree(child);
			parent.addChild(tree);

			SimpleTreeNode treeNode = new SimpleTreeNode(child, tree
					.getSimpleChildren());
			parent.addSimpleChildren(treeNode);
			addChild(tree);
		}
	}

	public ArrayList<SimpleTreeNode> show() {
		// 默认的顶层节点
		MyTree parent = new MyTree(new MyTreeItem(new Long(0), "",
				null));
		addChild(parent);
		return parent.getSimpleChildren();
	}

	public MyTreeModel(List<MyTreeItem> list) {
		this.list = list;
	}

}
