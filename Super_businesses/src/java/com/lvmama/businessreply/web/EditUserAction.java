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
import org.zkoss.zk.ui.Executions;

import com.lvmama.businessreply.vo.BusinessConstant;
import com.lvmama.comm.pet.po.comment.CmtBusinessUser;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.comment.CmtBusinessUserService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.opensymphony.oscache.util.StringUtil;

/**
 * @author liuyi
 *
 */
public class EditUserAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(EditUserAction.class);
	private CmtBusinessUserService cmtBusinessUserService =  (CmtBusinessUserService) SpringBeanProxy.getBean("cmtBusinessUserService");;
	private CmtBusinessUser businessUser;
	private boolean newRecord;
	
	/**
	 * 判断是在新增还是修改
	 */
	protected void doBefore() throws Exception {
		Map arg = Executions.getCurrent().getArg();
		if (arg.get("provider") == null) {
			businessUser = new CmtBusinessUser();
			newRecord = true;
		} else {
			businessUser = (CmtBusinessUser) arg.get("provider");
			newRecord = false;
		}
	}
	
	
	public void doSaveUser() {
		if (businessUser.getUserID() == null || businessUser.getUserID().equals("")) {
			this.alert("请输入登录名！");
		}
		else if (businessUser.getUserName() == null || businessUser.getUserName().equals("")) {
			this.alert("请输入用户名称！");
		} 
		else if (businessUser.getPassword() == null || businessUser.getPassword().equals("")) {
			this.alert("请输入密码！");
		} 
		else if (businessUser.getUserType() == null || businessUser.getUserType().equals("")) {
			this.alert("请选择用户类型！");
		} 
		else if(businessUser.getUserType().equals(BusinessConstant.USER_TYPE_MERCHANT) && (null == businessUser.getPlaceIDListString() || "".equals(businessUser.getPlaceIDListString())))
		{
			this.alert("商家用户，请输入地点标的！");
		}
		else {
			//设置标的列表
			 if(businessUser.getUserType().equals(BusinessConstant.USER_TYPE_MERCHANT))
			 {
					List<Place> placeList = new ArrayList<Place>();
					String[] placeIDArray = businessUser.getPlaceIDListString().split(",");
					for(int i = 0; i < placeIDArray.length; i++)
					{
						if(UserManagementAction.isNumeric(placeIDArray[i]))
						{
							Place place = new Place();
							place.setPlaceId(Long.parseLong(placeIDArray[i]));
							placeList.add(place);
						}
					}
					if(placeList.size() > 0)
					{
						businessUser.setPlaceList(placeList);
					}
			 }

			Map<String, String> param = new HashMap<String, String>();
			param.put("userID", businessUser.getUserID());
			CmtBusinessUser user = cmtBusinessUserService.getCmtBusinessUser(param);
			long result = 0;
			if(newRecord == false)//update user
			{
				if(user != null)
				{
					businessUser.setUpdateDate(new Date());
					result = cmtBusinessUserService.updateUser(businessUser);
					cmtBusinessUserService.deleteUserPlaceRelation(businessUser);
					if(businessUser.getPlaceList() != null  && businessUser.getPlaceList().size() > 0)
					{
						cmtBusinessUserService.addNewUserPlaceRelation(businessUser);
					}
					logger.info("更新用户： "+businessUser.getUserID());
					logger.info(businessUser.getPlaceIDListString());
				}
				else
				{
					this.alert("用户不存在无法更新！"); 
					return;
				}
			}
			else if(newRecord == true)//add user
			{
				if(user != null)
				{
					this.alert("用户已存在无法新增！"); 
					return;
				}
				else
				{
					businessUser.setCreateDate(new Date());
					businessUser.setUpdateDate(new Date());
					result = cmtBusinessUserService.addNewUser(businessUser);
					businessUser.setCmtBusinessUserID(result);
					if(businessUser.getPlaceList() != null && businessUser.getPlaceList().size() > 0)
					{
						cmtBusinessUserService.addNewUserPlaceRelation(businessUser);
					}
					logger.info("新增用户： "+businessUser.getUserID());
				}
			}
			
			if(result > 0)
			{
				this.refreshParent("search");
				this.closeWindow();
			}
			else
			{
				this.alert("失败！");
			}
		}
	}
	
	/**
	 * 根据查询类型改变参数列表值
	 * @param type 查询类型
	 * @param value 参数值
	 */
	public void changeValue(final String type, final String value) {
            if ("userType".equals(type)) { /*用户类型  */
            	businessUser.setUserType(null);
			
			  if (!StringUtil.isEmpty(value)) {
				  businessUser.setUserType(value);
			  }
			 return;
		   }
	}
	
	
	public void setBusinessUser(CmtBusinessUser businessUser) {
		this.businessUser = businessUser;
	}


	public CmtBusinessUser getBusinessUser() {
		return businessUser;
	}
}
