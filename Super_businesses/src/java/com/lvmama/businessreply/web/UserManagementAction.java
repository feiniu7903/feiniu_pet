/**
 * 
 */
package com.lvmama.businessreply.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.lvmama.businessreply.utils.ZkMessage;
import com.lvmama.businessreply.utils.ZkMsgCallBack;
import com.lvmama.businessreply.vo.BusinessConstant;
import com.lvmama.comm.pet.po.comment.CmtBusinessUser;
import com.lvmama.comm.pet.service.comment.CmtBusinessUserService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.opensymphony.oscache.util.StringUtil;

/**
 * @author liuyi
 *
 */
public class UserManagementAction extends BaseAction {

	private static Logger logger = Logger.getLogger(UserManagementAction.class);
	private List<CmtBusinessUser> businessUserList;
	private CmtBusinessUserService cmtBusinessUserService =  (CmtBusinessUserService) SpringBeanProxy.getBean("cmtBusinessUserService");
	

	/**
	 * 查询条件
	 */
	private Map<String, Object> queryOption = new HashMap<String, Object>();
	
	private Integer totalRowCount;
	
	/**
	 * 查询
	 */
	public void doQuery() {
		String userStatus = (String)(getQueryOption().get("status"));
		if(BusinessConstant.USER_STATUS_VALID.equals(userStatus))
		{
			getQueryOption().put("isValid", "Y");
		}
		else if(BusinessConstant.USER_STATUS_INVALID.equals(userStatus))
		{
			getQueryOption().put("isValid", "N");
		}
		else if(BusinessConstant.USER_STATUS_ALL.equals(userStatus))
		{
			getQueryOption().remove("isValid");
		}
		
		String userType = (String)(getQueryOption().get("userType"));
		if(null == (userType))
		{
			getQueryOption().put("userType", BusinessConstant.USER_TYPE_LVMAMA);
		}
		
		
		String placeIDs = (String)(getQueryOption().get("placeIDString"));
		if(null != placeIDs && !"".equals(placeIDs))
		{
			List<String> placeList = new ArrayList<String>();
			String[] placeIDArray = placeIDs.split(",");
			for(int i = 0; i < placeIDArray.length; i++)
			{
				if(isNumeric(placeIDArray[i]))
				{
					placeList.add(placeIDArray[i]);
				}
			}
			if(placeList.size() > 0)
			{
				getQueryOption().put("placeIDs", placeList);
			}
		}
		else
		{
			getQueryOption().remove("placeIDs");
		}
		
		getQueryOption().put("createDate321", "true");
		
	 	totalRowCount= Integer.valueOf((int)(cmtBusinessUserService.getUserCount(queryOption)));
		_totalRowCountLabel.setValue(totalRowCount.toString()); 
		_paging.setTotalSize(totalRowCount.intValue());
		queryOption.put("_startRow", _paging.getActivePage()*_paging.getPageSize()+1);
		queryOption.put("_endRow", _paging.getActivePage()*_paging.getPageSize()+_paging.getPageSize());
		
		businessUserList = cmtBusinessUserService.queryUser(getQueryOption());
	}
	
	
	
	
	
	/**
	 * 根据查询类型改变参数列表值
	 * @param type 查询类型
	 * @param value 参数值
	 */
	public void changeValue(final String type, final String value) {
            if ("userType".equals(type)) { /*用户类型  */
            	getQueryOption().remove("userType");
			
			  if (!StringUtil.isEmpty(value)) {
				getQueryOption().put(type, value);
			  }
			 return;
		   }
          else if ("status".equals(type)) { /* 用户状态  */
          	getQueryOption().remove("status");
		
		  if (!StringUtil.isEmpty(value)) {
			getQueryOption().put(type, value);
		  }
		   return;
	    }  
	}
	
	
	
	
	
	
	
	/**
	 * 启用用户
	 * @param deviceId
	 */
	public void enableUser (final String userID){
		ZkMessage.showQuestion("您确认要启用用户吗", new ZkMsgCallBack() {
			public void execute() {
				Map param = new HashMap();
				param.put("userID", userID);
				CmtBusinessUser cmtBusinessUser = cmtBusinessUserService.getCmtBusinessUser(param);
				if(cmtBusinessUser != null)
				{
					cmtBusinessUser.setIsValid("Y");
					cmtBusinessUser.setUpdateDate(new Date());
					cmtBusinessUserService.updateUser(cmtBusinessUser);
				}
				refreshComponent("search");
			}
		}, new ZkMsgCallBack() {
			public void execute() {

			}
		});
	}
	
	/**
	 * 停用用户
	 * @param deviceId
	 */
	public void disableUser (final String userID){
		ZkMessage.showQuestion("您确认要停用用户吗", new ZkMsgCallBack() {
			public void execute() {
				Map param = new HashMap();
				param.put("userID", userID);
				CmtBusinessUser cmtBusinessUser = cmtBusinessUserService.getCmtBusinessUser(param);
				if(cmtBusinessUser != null)
				{
					cmtBusinessUser.setIsValid("N");
					cmtBusinessUser.setUpdateDate(new Date());
					cmtBusinessUserService.updateUser(cmtBusinessUser);
				}
				refreshComponent("search");
			}
		}, new ZkMsgCallBack() {
			public void execute() {

			}
		});
	}
	
	
	public void setBusinessUserList(List<CmtBusinessUser> businessUserList) {
		this.businessUserList = businessUserList;
	}


	public List<CmtBusinessUser> getBusinessUserList() {
		return businessUserList;
	}

	public static boolean isNumeric(String str){
		try {
		Integer.parseInt(str);
		return true;
		} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		return false;
		}
		}

	public Map<String, Object> getQueryOption() {
		return queryOption;
	}

	public void setTotalRowCount(Integer totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

	public Integer getTotalRowCount() {
		return totalRowCount;
	}

	public void setQueryOption(Map<String, Object> queryOption) {
		this.queryOption = queryOption;
	}

}
