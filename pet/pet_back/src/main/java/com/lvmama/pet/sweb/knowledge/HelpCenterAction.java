/**
 * 
 */
package com.lvmama.pet.sweb.knowledge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.info.InfoHelpCenter;
import com.lvmama.comm.pet.po.info.InfoQuesTypeHierarchy;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.info.InfoService;
import com.lvmama.comm.vo.Constant;

/**
 * 帮助中心后台操作Action
 * @author yangbin
 */
@ParentPackage("json-default")
@Results( { @Result(name = "helpCenter", location = "/WEB-INF/pages/back/knowledge/help/helpCenter.jsp")})
public class HelpCenterAction extends BackBaseAction {

	private static final long serialVersionUID = 1624468921144678161L;
	private Log logger=LogFactory.getLog(HelpCenterAction.class);
	private InfoService infoServiceBean;
	private List<Object> rows;
	private InfoQuesTypeHierarchy resultInfoQuesTypeHierarchy;
	private InfoHelpCenter oneHelpInfoRow;
	private InfoQuesTypeHierarchy searchInfoQuesTypeHierarchy;
	private String userName;

	
	@Action("/help/goToHelpCenter")
	public String goToHelpCenter() {
		PermUser user=new PermUser();
		Object obj=super.getSession(Constant.SESSION_BACK_USER);
		BeanUtils.copyProperties(obj, user);
		this.userName=user.getUserName();
		return "helpCenter";
	}
	
	/**
	 * query business type category
	 * @return
	 */
	@Action(value="/help/queryBusinessTypeJsonList",results=@Result(type="json",name="businessTypeJsonList",params={"includeProperties","rows\\[\\d+\\]\\.typeId,rows\\[\\d+\\]\\.typeName,rows\\[\\d+\\]\\.parentTypeName"}))   
	public String queryBusinessTypeJsonList(){
		try {
			List<InfoQuesTypeHierarchy> infoQuesTypeHierarchyList = this.infoServiceBean
					.queryInfoQuesTypeHierarchyByObjectType(Constant.INFO_TYPE.INFO_QUES_HELP_CENTER
							.name());
			this.rows = new ArrayList<Object>();
			for (InfoQuesTypeHierarchy infoQuesTypeHierarchy : infoQuesTypeHierarchyList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("typeId", infoQuesTypeHierarchy.getTypeId());
				map.put("typeName", infoQuesTypeHierarchy.getTypeName());
				map.put("parentTypeName",
						infoQuesTypeHierarchy.getParentTypeName());
				this.rows.add(map);
			}
		} catch (Exception ex) {
			logger.error(ex, ex);
		}
		return "businessTypeJsonList";
	}
	
	/**
	 * query content type category
	 * @return
	 */
	@Action(value="/help/queryContentTypeJsonList",results=@Result(type="json",name="contentTypeJsonList",params={"includeProperties","rows\\[\\d+\\]\\.typeId,rows\\[\\d+\\]\\.parentTypeId,rows\\[\\d+\\]\\.typeName,rows\\[\\d+\\]\\.parentTypeName"}))   
	public String queryContentTypeJsonList() {
		try {
			List<InfoQuesTypeHierarchy> infoQuesTypeHierarchyList = this.infoServiceBean
					.queryInfoQuesTypeHierarchyByObjectType(
							Constant.INFO_TYPE.INFO_QUES_HELP_CENTER.name(),
							resultInfoQuesTypeHierarchy.getParentTypeId());
			this.rows = new ArrayList<Object>();
			for (InfoQuesTypeHierarchy child : infoQuesTypeHierarchyList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("typeId", child.getTypeId());
				map.put("parentTypeId", child.getParentTypeId());
				map.put("typeName", child.getTypeName());
				map.put("parentTypeName", child.getParentTypeName());
				this.rows.add(map);
			}
		} catch (Exception ex) {
			logger.error(ex, ex);
		}
		return "contentTypeJsonList";
	}
	
	/**
	 * insert type category to db(business type or content type)
	 * @throws IOException
	 */
	@Action("/help/saveType")   
	public void insertType() throws IOException{
		try{
			this.resultInfoQuesTypeHierarchy.setObjectType(Constant.INFO_TYPE.INFO_QUES_HELP_CENTER.name());
			infoServiceBean.saveQuestionTypeInHierarchy(this.resultInfoQuesTypeHierarchy);
			sendAjaxMsg("{result:true}");
		}catch(Exception ex){
			logger.error(ex, ex);
			sendAjaxMsg("{result:false}");
		}

	}
	/**
	 * remove type category from db(business type or content type)
	 * @throws IOException
	 */
	@Action("/help/removeType") 
	public void removeType() throws IOException{
		try{
		    this.resultInfoQuesTypeHierarchy.setObjectType(Constant.INFO_TYPE.INFO_QUES_HELP_CENTER.name());
			this.infoServiceBean.removeQuestionType(this.resultInfoQuesTypeHierarchy.getTypeId());
			sendAjaxMsg("{result:true}");
		}catch(Exception ex){
			logger.error(ex, ex);
			sendAjaxMsg("{result:false}");
		}
	}
	
	/**
	 * query help info content
	 * @return
	 */
	@Action(value="/help/queryHelpInfoJsonList",results=@Result(type="json",name="helpjsonlist",params={"includeProperties","rows\\[\\d+\\]\\.id,rows\\[\\d+\\]\\.typeId,rows\\[\\d+\\]\\.title,rows\\[\\d+\\]\\.typeName,rows\\[\\d+\\]\\.userName,rows\\[\\d+\\]\\.content"}))   
	public String queryHelpJsonList(){
		try
		{
			InfoHelpCenter infoHelpCenter = new InfoHelpCenter();
			infoHelpCenter.setTypeId(searchInfoQuesTypeHierarchy.getTypeId());
			List<InfoHelpCenter> infoHelpCenterList = infoServiceBean.queryInfoHelpList(infoHelpCenter);
			this.rows = new ArrayList<Object>();
			for(int i = 0; i < infoHelpCenterList.size(); i++){
				InfoHelpCenter infoHelpCenterResult = infoHelpCenterList.get(i);
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id", infoHelpCenterResult.getId());
				map.put("typeId", infoHelpCenterResult.getTypeId());
				map.put("title", infoHelpCenterResult.getTitle());
				map.put("typeName", infoHelpCenterResult.getTypeName());
				map.put("userName", infoHelpCenterResult.getUserName());
				map.put("content",  infoHelpCenterResult.getContent());
				this.rows.add(map);
			}
			this.userName = getSessionUserName();
		}
		catch(Exception ex)
		{
			logger.error(ex, ex);
		}
		return "helpjsonlist";
	}
	
	/**
	 * save help info
	 * @throws IOException
	 */
	@Action("/help/saveHelpInfo")   
	public void saveOrUpdateHelpInfo() throws IOException{
		try{		
			this.infoServiceBean.saveHelp(this.oneHelpInfoRow);		
			sendAjaxMsg("{result:true}");
		}catch(Exception ex){
			logger.error(ex, ex);
			sendAjaxMsg("{result:false}");
		}
	}
	
	/**
	 * remove help info
	 * @throws IOException
	 */
	@Action("/help/removeHelpInfo") 
	public void removeHelpInfo() throws IOException{
		try {
			infoServiceBean.removeHelp(oneHelpInfoRow.getId());
			sendAjaxMsg("{result:true}");
		} catch (Exception ex) {
			logger.error(ex, ex);
			sendAjaxMsg("{result:false}");
		}
	}
	
	

	public void setInfoServiceBean(InfoService infoServiceBean) {
		this.infoServiceBean = infoServiceBean;
	}

	public void setResultInfoQuesTypeHierarchy(
			InfoQuesTypeHierarchy resultInfoQuesTypeHierarchy) {
		this.resultInfoQuesTypeHierarchy = resultInfoQuesTypeHierarchy;
	}


	public InfoQuesTypeHierarchy getResultInfoQuesTypeHierarchy() {
		return resultInfoQuesTypeHierarchy;
	}


	public void setRows(List<Object> rows) {
		this.rows = rows;
	}


	public List<Object> getRows() {
		return rows;
	}
	

	public void setSearchInfoQuesTypeHierarchy(
			InfoQuesTypeHierarchy searchInfoQuesTypeHierarchy) {
		this.searchInfoQuesTypeHierarchy = searchInfoQuesTypeHierarchy;
	}


	public InfoQuesTypeHierarchy getSearchInfoQuesTypeHierarchy() {
		return searchInfoQuesTypeHierarchy;
	}


	public void setOneHelpInfoRow(InfoHelpCenter oneHelpInfoRow) {
		this.oneHelpInfoRow = oneHelpInfoRow;
	}


	public InfoHelpCenter getOneHelpInfoRow() {
		return oneHelpInfoRow;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserName() {
		return userName;
	}

}
