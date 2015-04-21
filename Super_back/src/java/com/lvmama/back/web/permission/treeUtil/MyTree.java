package com.lvmama.back.web.permission.treeUtil;

import java.util.ArrayList;

import org.zkoss.zul.SimpleTreeNode;

/**
 * @author shihui
 * */
public class MyTree {

	private ArrayList<MyTree> children; // 子节点

	private ArrayList<SimpleTreeNode> simpleChildren;

	private MyTree parent; // 父节点

	private MyTreeItem treeItem; // 节点本身

	public MyTree(MyTreeItem treeItem) {
		this.treeItem = treeItem;
		this.children = new ArrayList<MyTree>();
		this.simpleChildren = new ArrayList<SimpleTreeNode>();
	}

	public ArrayList<MyTree> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<MyTree> children) {
		this.children = children;
	}

	public ArrayList<SimpleTreeNode> getSimpleChildren() {
		return simpleChildren;
	}

	public void setSimpleChildren(ArrayList<SimpleTreeNode> simpleChildren) {
		this.simpleChildren = simpleChildren;
	}

	public MyTree getParent() {
		return parent;
	}

	public void setParent(MyTree parent) {
		this.parent = parent;
	}

	public MyTreeItem getTreeItem() {
		return treeItem;
	}

	public void setTreeItem(MyTreeItem treeItem) {
		this.treeItem = treeItem;
	}

	public void addChild(MyTree child) {
		children.add(child);
	}

	public void addSimpleChildren(SimpleTreeNode child) {
		simpleChildren.add(child);
	}

	public boolean hasChildren() {
		return children.size() > 0;
	}
}
