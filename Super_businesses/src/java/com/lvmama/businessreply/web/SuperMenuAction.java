package com.lvmama.businessreply.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.api.Include;
import com.lvmama.businessreply.po.MenuResources;
import com.lvmama.businessreply.vo.BusinessConstant;

public class SuperMenuAction extends BaseAction {

	private static Logger logger = Logger.getLogger(SuperMenuAction.class);
	private List<MenuResources> resourcesList;
	private Include xcontents;
	private String currResource = "/welcome.zul";
	private List<String> memuList;
	private Iframe iframe;
	Tree tree;
	protected void doBefore() throws Exception {
		String session_resource = (String) super.session
				.getAttribute("CURRENT_RESOURCE");
		if (session_resource != null) {
			currResource = session_resource;
		}
		else
		{
			List<MenuResources> menuResources = getResourceList();
			if(menuResources != null && menuResources.size() > 0)
			{
				currResource = menuResources.get(0).getFileName();
			}
		}
	}

	/**
	 * 获得菜单资源
	 * @return
	 */
	public List<MenuResources> getResourceList() {
		if (BusinessConstant.USER_TYPE_LVMAMA.equals(user.getUserType())) { //驴妈妈用户
			resourcesList = new ArrayList<MenuResources>();
			MenuResources manageUserMenu = new MenuResources();
			manageUserMenu.setResourceId(1l);
			manageUserMenu.setResourceName("用户账号管理");
			manageUserMenu.setFileName("/userManagement.zul");
			MenuResources lvmamaReplyMenu = new MenuResources();
			lvmamaReplyMenu.setResourceId(2l);
			lvmamaReplyMenu.setResourceName("驴妈妈用户回复管理");
			lvmamaReplyMenu.setFileName("/lvmamaReply.zul");
			resourcesList.add(manageUserMenu);
			resourcesList.add(lvmamaReplyMenu);
		} else if (BusinessConstant.USER_TYPE_MERCHANT.equals(user.getUserType())){ //普通商家用户
			resourcesList = new ArrayList<MenuResources>();
			MenuResources merchantReplyMenu = new MenuResources();
			merchantReplyMenu.setResourceId(3l);
			merchantReplyMenu.setResourceName("商家用户回复管理");
			merchantReplyMenu.setFileName("/merchantReply.zul");
            resourcesList.add(merchantReplyMenu);
		}
		return resourcesList;
	}

    /**
     * 跳转菜单页面
     * @param res
     */
	public void changeFunc(MenuResources res) {
		xcontents.setSrc(res.getFileName());
		currResource = res.getFileName();
		super.session.setAttribute("CURRENT_RESOURCE", res.getFileName());
	}

	public void logout() {
		super.session.removeAttribute("CURRENT_RESOURCE");
		super.session.removeAttribute(BusinessConstant.SESSION_USER);
		Executions.sendRedirect("/login.zul");
	}

	public void doAfter(){
		createTreeList();//创建菜单
	}
	
	public void createTreeList(){
		resourcesList = this.getResourceList();
		this.tree.getChildren().clear();
		Treechildren treeFirstChild = new Treechildren();
		for (MenuResources resource : resourcesList) {//生成一级菜单
			Treeitem treeItem = new Treeitem();
			//treeItem.setImage(resource.getImage());
			treeItem.setValue(resource.getFileName());

			treeItem.setOpen(false);
			Treerow treeRow = new Treerow();
			Treecell treeCell = new Treecell();
			treeCell.setHeight("30px");
			
			treeCell.setLabel(resource.getResourceName());
			treeCell.setAttribute("ddd", "111111");
			treeRow.appendChild(treeCell);
			treeItem.appendChild(treeRow);
			treeFirstChild.appendChild(treeItem);
			setSrc(treeItem);//添加一级菜单监听
		}
		tree.appendChild(treeFirstChild);
	}
	
	
	public void setSrc(Treeitem treeItem){
		treeItem.addEventListener("onClick", new EventListener() {//添加一级菜单的事件监听
			   public void onEvent(Event event) throws Exception {
				   Treeitem treeItem = (Treeitem) event.getTarget();
				   if(treeItem.getValue()!=null&&!"".equals(treeItem.getValue())){
				   iframe.setSrc(treeItem.getValue().toString());
				   }
			   }
			  });
	}
	

	public String getCurrResource() {
		return currResource;
	}
	public List<String> getMemuList() {
		return memuList;
	}
	public void setMemuList(List<String> memuList) {
		this.memuList = memuList;
	}
	public List<MenuResources> getResourcesList() {
		return resourcesList;
	}
	public void setResourcesList(List<MenuResources> resourcesList) {
		this.resourcesList = resourcesList;
	}
	public Include getXcontents() {
		return xcontents;
	}
	public void setXcontents(Include xcontents) {
		this.xcontents = xcontents;
	}
	public Iframe getIframe() {
		return iframe;
	}
	public void setIframe(Iframe iframe) {
		this.iframe = iframe;
	}
	public Tree getTree() {
		return tree;
	}
	public void setTree(Tree tree) {
		this.tree = tree;
	}
	public void setCurrResource(String currResource) {
		this.currResource = currResource;
	}

}
