/**
 * 
 */
package com.lvmama.sso.web.ajax;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserCooperationUserService;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.sso.web.AbstractLoginAction;


/**
 * 这个类用来获取USER相关信息，一般为无法直接访问ORACLE数据库的程序访问这个服务，目前会访问这个服务的系统为PHP CMS
 * 
 * @author liudong
 *
 */
public class SearchUserInfoAction extends AbstractLoginAction {

	
	private String userIDs;
	private String userName;
	private UserCooperationUserService userCooperationUserService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8258518014048089349L;


	@Action("/ajax/userinfo/searchUserInfo")
	public void searchUserInfo() throws IOException
	{
		if(!StringUtils.isEmpty(userIDs))
		{
			String[] userIDsArray = userIDs.split(",");
			List<String> idList = new ArrayList<String>();
			for(int i = 0; i < userIDsArray.length; i++)
			{
				idList.add(userIDsArray[i]);
			}
			List<UserUser> userList = userUserProxy.getUsersListByUserNoList(idList);
			if(userList.size() > 0){
				this.printRtn(getRequest(), getResponse(), constructUserJsonReturnData(getJsonByUserInfoUrl(userList), "200", ""));
			}else{
				this.printRtn(getRequest(), getResponse(), constructUserJsonReturnData("{}", "500", "没找到用户"));
			}
		}
		else if(!StringUtils.isEmpty(getUserName()))
		{
			
			UserUser user = userUserProxy.getUsersByMobOrNameOrEmailOrCard(getUserName());
			if(user != null){
				List<UserUser> userList = new ArrayList<UserUser>();
				userList.add(user);
				this.printRtn(getRequest(), getResponse(), constructUserJsonReturnData(getJsonByUserInfoUrl(userList), "200", ""));
			}else{
				this.printRtn(getRequest(), getResponse(), constructUserJsonReturnData("{}", "500", "没找到用户"));
			}
		}
		else
		{
			this.printRtn(getRequest(), getResponse(), constructUserJsonReturnData("{}", "500", "传入userId为空"));
		}
	}


	
	/**
	 * 输出返回码
	 * @param request request
	 * @param response response
	 * @param bean Ajax返回的基本Bean
	 * @throws IOException IOException
	 */
	private void printRtn(final HttpServletRequest request,
			final HttpServletResponse response, String json) throws IOException {
		response.setContentType("text/json; charset=UTF-8");
		if (request.getParameter("jsoncallback") == null) {
			response.getWriter().write(json); 
		} else {
			
			response.getWriter().write(getRequest().getParameter("jsoncallback")+"("+json+")"); 
		}
	}
	
	
	private String constructUserJsonReturnData(String dataJson, String statusCode, String msg)
	{
		String json = "{\"code\":\""+statusCode+"\",\"msg\":\""+msg+"\",\"data\":"+dataJson+"}";
		return json;
	}
	
	/**
	 * 输出返回码
	 * @param request userList
	 * @param response 返回的json(会员属性组合:id,avatar,point)
	 * @throws UnsupportedEncodingException
	 */
	private String getJsonByUserInfoUrl(List<UserUser> userList) throws UnsupportedEncodingException
	{
		String Ip = InternetProtocol.getRemoteAddr(getRequest());//获取Ip地址
	    if(CheckIpAddress(Ip)){
			
			String json="{\"userAvatars\":[";
			
			for (int i = 0; i < userList.size(); i++) {
				json += "{\"id\":\"" + userList.get(i).getUserId()
						+ "\",\"userName\":\"" + userList.get(i).getUserName()
						+ "\",\"createDate\":\"" + (null != userList.get(i).getCreatedDate() ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(userList.get(i).getCreatedDate()) : "")
						+ "\",\"channal\":\"" + userList.get(i).getChannel()
						+ "\",\"mobileNumber\":\"" + userList.get(i).getMobileNumber()
						+ "\",\"isMobileChecked\":\"" + userList.get(i).getIsMobileChecked()
						+ "\",\"email\":\"" + userList.get(i).getEmail()
						+ "\",\"isEmailChecked\":\"" + userList.get(i).getIsEmailChecked()
						+ "\",\"birthday\":\"" + (null != userList.get(i).getBirthday() ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(userList.get(i).getBirthday()) : "")
						+ "\",\"point\":\"" + userList.get(i).getPoint() 	 										
						+ "\",\"grade\":\"" + userList.get(i).getGrade()	 										
						+ "\",\"memberShipCard\":\"" + userList.get(i).getMemberShipCard() 										
						+ "\",\"bindingAccount\":\"" + getCooperationAccount(userList.get(i).getId()) //绑定分享账号,可以有多个										
						+ "\"}";
				if(i< userList.size()-1)
					json+=",";
			}
			json+="]}";
			return json;
		}else{
			
			return "";
		}
		
	}
	
	/**
	 * 通过UserId得到第三方绑定
	 * @return 返回绑定状态（Y or N）
	 * */
	public String getCooperationAccount(Long userId){
		Map<String, Object> parameters = new HashMap<String, Object>();
		//String bindingAccount ="";
		if(userId!=null){
			parameters.put("userId", userId);
		}
		List<UserCooperationUser> list =userCooperationUserService.getCooperationUsers(parameters);
		if(list!=null&&list.size()>0){
			return "Y";
		}
		return "N";
	}
	
	/**
     * 判断ip是否是指定网段
     * 如果匹配返回true，否则返回false
     * 目前是192.168.10.X
     * */
    public boolean CheckIpAddress(String Ip){
    	String regex =Constant.getInstance().getProperty("PHP.IP.REGEX");
    	String[] ips = regex.split(",");
    	//匹配Ip
    	for(int i=0;i<ips.length;i++){
		  if (Ip.matches(ips[i])) {
	             return true;
	         } 
    	}
       
    	return false;
    }
    
	public void setUserIDs(String userIDs) {
		this.userIDs = userIDs;
	}


	public String getUserIDs() {
		return userIDs;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public UserCooperationUserService getUserCooperationUserService() {
		return userCooperationUserService;
	}



	public void setUserCooperationUserService(
			UserCooperationUserService userCooperationUserService) {
		this.userCooperationUserService = userCooperationUserService;
	}
	

}
