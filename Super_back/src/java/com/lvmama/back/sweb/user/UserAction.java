package com.lvmama.back.sweb.user;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.utils.json.JSONResult;
/**
 * 
 * @author yangbin
 *
 */
@SuppressWarnings("serial")
public class UserAction extends BaseAction{
	
	private UserUserProxy userUserProxy;
	private String search;
	/**
	 * ajax返回用户列表.
	 */
	@Action(value="/user/searchUser")
	public void userList(){
		JSONResult result=new JSONResult();
		HashMap<String,Object> param=new HashMap<String, Object>();
		if (search != null) {
			param.put("search", search);
		}else{
			param.put("search", "");
		}
		param.put("maxRows", "10");
		List<UserUser> list=userUserProxy.getUsers(param);		
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(UserUser user:list){
				JSONObject obj=new JSONObject();
				obj.put("id", user.getUserId());
				obj.put("text", user.getUserName());
				array.add(obj);
			}
		}
		
		JSONOutput.writeJSON(getResponse(), array);
	}
	/**
	 * @param userService the userService to set
	 */

	/**
	 * @param search the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	
	
}
