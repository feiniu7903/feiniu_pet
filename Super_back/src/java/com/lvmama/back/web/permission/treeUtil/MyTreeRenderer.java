package com.lvmama.back.web.permission.treeUtil;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.SimpleTreeModel;
import org.zkoss.zul.SimpleTreeNode;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import com.lvmama.comm.pet.po.perm.PermOrganization;

/**
 * @author shihui
 * 
 *         树结构渲染类，根据数据级别关系组织树形结构
 * */
public class MyTreeRenderer implements TreeitemRenderer {

	private Long departmentId;

	public void render(Treeitem item, Object data) throws Exception {
		SimpleTreeNode treeNode = (SimpleTreeNode) data;
		MyTreeItem treeItem = (MyTreeItem) treeNode.getData();
		Treecell cell = new Treecell(treeItem.getName());
		Treerow row = null;

		// 创建节点
		if (item.getTreerow() == null) {
			row = new Treerow();
			row.setParent(item);
		} else {
			row = item.getTreerow();
			row.getChildren().clear();
		}

		item.setId(treeItem.getId() + "");
		// 设置节点的属性
		item.setAttribute("id", treeItem.getId());
		item.setAttribute("name", treeItem.getName());
		item.setAttribute("parentId", treeItem.getParentId());

		cell.setParent(row);
		//修改时，初始化用户所在的组织
		if (this.departmentId != null) {
			//只有展开后，才会加载子节点，否则，只加载当前节点
			//展开后，直接跳到render方法，不进行下面操作；加载子节点完毕后，再继续进行下面操作
			item.setOpen(true);
			setSelectedItem(item);
		}
	}

	public void setSelectedItem(Treeitem item) {
		if (this.departmentId != null) {
			if (item.getId().equals(this.departmentId.toString())) {
				item.setSelected(true);
				this.departmentId = null;
			} else {
				item.setOpen(false);
			}
		} 
	}

	/**
	 * 建树
	 * */
	public void refreshTree(Tree tree, List<PermOrganization> data, Long departmentId) {
		try {
			// 将数据填充入树所需要的列表里
			List<MyTreeItem> list = new ArrayList<MyTreeItem>();
			for (int i = 0; i < data.size(); i++) {
				PermOrganization org = data.get(i);
				list.add(new MyTreeItem(org.getOrgId(), org.getDepartmentName(), org.getParentOrgId()));
			}

			SimpleTreeNode root = new SimpleTreeNode("ROOT", new MyTreeModel(list).show());
			SimpleTreeModel model = new SimpleTreeModel(root);
			MyTreeRenderer render = new MyTreeRenderer();
			render.setDepartmentId(departmentId);
			tree.setModel(model);
			tree.setTreeitemRenderer(render);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
}
