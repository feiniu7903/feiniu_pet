package com.lvmama.passport.web.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPortAuthResources;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.vo.PassportConstant;

public class SuperMenuAction extends ZkBaseAction {

	private List<PassPortAuthResources> resourcesList;
	private EPlaceService eplaceService;
	private Include xcontents;
	private String currCategory = PassportConstant.MEMU_PASSPORT;
	private String currResource = "/welcome.zul";
	private List<String> memuList;
	private Iframe iframe;
	Tree tree;
	
	private boolean eplaceMemuVisible=false;
	private boolean passportMemuVisible=false;
	private boolean systemMemuVisible=false;

	protected void doBefore() throws Exception {
		String session_category = (String) super.session
				.getAttribute("CURRENT_CATEGORY");
		String session_resource = (String) super.session
				.getAttribute("CURRENT_RESOURCE");
		if (session_category != null) {
			currCategory = session_category;
		}
		if (session_resource != null) {
			currResource = session_resource;
		}
		if ("admin".equals(user.getUserId())) {
			memuList = this.eplaceService.selectCategoryByAdmin();
		} else {
			memuList = this.eplaceService.selectCategoryByUserId(user
					.getPassPortUserId());
		}
		initMemuVisible(memuList);
	}
	public void initMemuVisible(List memuList){
		if(memuList.contains("SYSTEM")){
			systemMemuVisible=true;
			 currCategory = PassportConstant.MEMU_SYSTEM;
		}
		if(memuList.contains("EPLACE")){
			eplaceMemuVisible=true;
			 currCategory = PassportConstant.MEMU_EPLACE;
		}
		if(memuList.contains("PASSPORT")){
			passportMemuVisible=true;
			 currCategory = PassportConstant.MEMU_PASSPORT;
		}
	}
	public void changeCategory(String category) {
		super.session.setAttribute("CURRENT_CATEGORY", category);
		currCategory = category;
		super.refreshComponent("refreshCategory");
		iframe.setSrc("/welcome.zul");
		this.doAfter();
	}

	public List<PassPortAuthResources> getResourceList() {
		if ("admin".equals(user.getUserId())) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<String> strList = new ArrayList<String>();
			strList.add(this.currCategory);
			map.put("category", strList);
			param.put("parentIdIsNull", "not null");
			resourcesList = eplaceService.selectByParms(map);
		} else {
			resourcesList = eplaceService.getResourcesByUserAndCategory(
					user.getPassPortUserId(), currCategory);
		}
		return resourcesList;
	}

	public void changeFunc(PassPortAuthResources res) {
		xcontents.setSrc(res.getFileName());
		currResource = res.getFileName();
		super.session.setAttribute("CURRENT_RESOURCE", res.getFileName());
	}

	public void logout() {
		super.session.removeAttribute("CURRENT_RESOURCE");
		super.session.removeAttribute("CURRENT_CATEGORY");
		super.session.removeAttribute(PassportConstant.SESSION_USER);
		super.session.removeAttribute(PassportConstant.SESSION_EPLACE_SUPPLIER);
		Executions.sendRedirect("/login.zul");
	}

	public void doAfter(){
		createTreeList();//创建菜单
	}
	
	public void createTreeList(){
		resourcesList = this.getResourceList();
		this.tree.getChildren().clear();
		Treechildren treeFirstChild = new Treechildren();
		for (PassPortAuthResources resource : resourcesList) {//生成一级菜单
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
			List<PassPortAuthResources> secondResources = new ArrayList<PassPortAuthResources>();
			if ("admin".equals(user.getUserId())) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("category", currCategory);
				secondResources = eplaceService.selectSecondItems(resource.getResourceId());
			}else{
				secondResources = eplaceService.selectSecondResourceByParentId(user.getPassPortUserId(),currCategory,resource.getResourceId());
			}			

			if(secondResources.size()!=0){
				Treechildren treeSecondChild = new Treechildren();
				
				for (PassPortAuthResources resource1 : secondResources){
					Treeitem treeSecondItem = new Treeitem();
					treeSecondItem.setValue(resource1.getFileName());
					treeSecondItem.setOpen(false);
					Treerow treeSecondRow = new Treerow();
					Treecell treeSecondCell = new Treecell();
					treeSecondCell.setHeight("30px");
					treeSecondCell.setLabel(resource1.getResourceName());
					treeSecondRow.appendChild(treeSecondCell);
					treeSecondItem.appendChild(treeSecondRow);
					treeSecondChild.appendChild(treeSecondItem);
	
					setSrc(treeSecondItem);//添加二级菜单监听
				}
				treeItem.appendChild(treeSecondChild);
			}
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
	public boolean isEplaceMemuVisible() {
		return eplaceMemuVisible;
	}
	public void setEplaceMemuVisible(boolean eplaceMemuVisible) {
		this.eplaceMemuVisible = eplaceMemuVisible;
	}
	public boolean isPassportMemuVisible() {
		return passportMemuVisible;
	}
	public void setPassportMemuVisible(boolean passportMemuVisible) {
		this.passportMemuVisible = passportMemuVisible;
	}
	public boolean isSystemMemuVisible() {
		return systemMemuVisible;
	}
	public void setSystemMemuVisible(boolean systemMemuVisible) {
		this.systemMemuVisible = systemMemuVisible;
	}
	public List<String> getMemuList() {
		return memuList;
	}
	public void setMemuList(List<String> memuList) {
		this.memuList = memuList;
	}
	public List<PassPortAuthResources> getResourcesList() {
		return resourcesList;
	}
	public void setResourcesList(List<PassPortAuthResources> resourcesList) {
		this.resourcesList = resourcesList;
	}
 
	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}
	public Include getXcontents() {
		return xcontents;
	}
	public void setXcontents(Include xcontents) {
		this.xcontents = xcontents;
	}
	public String getCurrCategory() {
		return currCategory;
	}
	public void setCurrCategory(String currCategory) {
		this.currCategory = currCategory;
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
