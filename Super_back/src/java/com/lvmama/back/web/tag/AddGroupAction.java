package com.lvmama.back.web.tag;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.bee.service.prod.ProdTagService;
import com.lvmama.comm.pet.po.prod.ProdTagGroup;

/**
 * 添加标签组
 * @author lipengcheng
 *
 */
public class AddGroupAction extends BaseAction {

	private static final long serialVersionUID = 1467640733168807672L;

	private ProdProductTagService prodProductTagService;
	private ProdTagService prodTagService;
	private ProdTagGroup group = new ProdTagGroup();

	/**
	 * 添加小组
	 */
	public void addGroup() {
		if (!validTagGroup()) {
			return;
		}
		prodTagService.addGroup(group);
		this.refreshParent("refreshButton");
		this.refreshParent("searchButton");
		this.closeWindow();
	}

	/**
	 * 验证小组对象的属性
	 * @return
	 */
	public Boolean validTagGroup() {
		boolean flag = true;
		if (group.getTagGroupName() == null || "".equals(group.getTagGroupName())) {
			alert("标签小组名不能为空");
			flag = false;
		} else if (group.getType() == null || "".equals(group.getType())) {
			alert("请选择一个标签类型");
			flag = false;
		}
		return flag;
	}
	
	public ProdTagGroup getGroup() {
		return group;
	}

	public void setProdTagService(ProdTagService prodTagService) {
		this.prodTagService = prodTagService;
	}
	
}
