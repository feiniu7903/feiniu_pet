/**
 * 
 */
package com.lvmama.pet.web.ajax;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.web.place.DestBaseAction;



/**
 * 
 * 用户信息检测操作
 * 
 * @author nixanjun 2014
 *
 */
@SuppressWarnings("serial")
public class AjaxUserDestAction extends BaseAction{
	private static final Log LOG = LogFactory.getLog(DestBaseAction.class);

	private UserUser user;

	/**
	 * 检测用户是否登录， 在登录状态的情况下返回true，未登录状态返回false
	 */
	@Action("/check/login")
	public void checkLogin()
	{
		boolean flag=isLogin();
		
		sendAjaxMsg(flag?"true":"false");		
	}
	public UserUser getUser() {
		if (user==null ) {
			user = (UserUser)getSession(Constant.SESSION_FRONT_USER);
		}
		LOG.info("******************user:"+user.getUserName());
		return user;
	}
	public void setUser(UserUser user) {
		this.user = user;
	}
	public boolean isLogin(){
		if(getUser()!=null){
			return true;
		}
		return false;
	}
}
