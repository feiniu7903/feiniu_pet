/**
 * 
 */
package com.lvmama.front.web.myspace;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.pet.vo.Page;

/**
 * 我的驴妈妈常用游客信息管理
 * @author liuyi
 *
 */
@Results({
	@Result(name = "success", location = "/WEB-INF/pages/myspace/sub/myUserReceivers.ftl", type = "freemarker"),
	@Result(name = "error", location = "/error.jsp", type = "redirect")
})
public class UserReceiversAction extends SpaceBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3395442927818750092L;
	
	
	/**
	 * 日志记录器
	 */
	private static final Log LOG = LogFactory.getLog(UserReceiversAction.class);
	
	
	/**
	 * 每页显示行数
	 */
	private static final Long ROW_NUMBER = 10L;
	
	/**
	 * 常用游客信息列表
	 */
	private List<UsrReceivers> receiversList;
	
	/**
	 * 游客名
	 */
	private String receiverName;
	
	/**
	 * 游客手机号
	 */
	private String receiverMobileNumber;
	
	/**
	 * 游客证件类型
	 */
	private String receiverCardType;
	
	/**
	 * 游客证件号
	 */
	private String receiverCardNum;
	
	/**
	 * 游客地址
	 */
	private String receiverAddress;
	
	/**
	 * 游客证件号
	 */
	private String receiverPostCode;
	
	/**
	 * 游客ID
	 */
	private String receiverId;
	
	/**
	 * 当前常用游客
	 */
	private UsrReceivers currentUserReceiver;
	
	/**
	 * 展现设置游客界面
	 */
	private String displaySetReceiverUserForm;
	
	/**
	 * 操作指令
	 */
	private String command;
	
	/**
	 * 分页配置信息
	 */
	private Page<UsrReceivers> pageConfig;
	
	/**
	 * 当前页码
	 */
	private Long currentPage = 1L;
	private IReceiverUserService receiverUserService;
	
	/**
	 * 游客页入口
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action("/myspace/userinfo/visitor")
	public String goToUserReceiversPage()
	{
		if(StringUtils.isNotEmpty(command))
		{
			if("delete".equalsIgnoreCase(command))
			{
				return deleteUserReceiver();
			}
			else if("set".equalsIgnoreCase(command))
			{
				return setUserReceiver();
			}
			else if("goToSet".equalsIgnoreCase(command))
			{
				return goToSetReceiverUserPage();
			}
			else
			{
				return initUserReceivers();
			}
		}
		else
		{
			return initUserReceivers();
		}
	}
	

	/**
	 * 常用游客信息页初始化
	 * @return
	 */
	private String initUserReceivers()
	{
		UserUser users = super.getUser();
		if (null == users) {
			LOG.error("Cann't get user's point log for empty userId");
			return ERROR;
		}
		listUserReceivers(users);
		return SUCCESS;
	}	

	/**
	 * 增加/更新常用游客
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action("/myspace/userReceivers/set")
	public String setUserReceiver()
	{
		UserUser users = super.getUser();
		if (null == users) {
			LOG.error("Cann't get user's point log for empty userId");
			return ERROR;
		}
		UsrReceivers userReceiver = composeUserReceiverObject();
		List<UsrReceivers> userReceiverList = new ArrayList<UsrReceivers>();
		userReceiverList.add(userReceiver);
		receiverUserService.createContact(userReceiverList, users.getUserNo());
		LOG.info("set receiver for "+users.getUserNo());
		listUserReceivers(users);
		return SUCCESS;
	}

	
	/**
	 * 删除常用游客
	 * @return
	 */
	private String deleteUserReceiver()
	{
		UserUser users = super.getUser();
		if (null == users) {
			LOG.error("Cann't get user's point log for empty userId");
			return ERROR;
		}
		UsrReceivers userReceiver = composeUserReceiverObject();
		List<UsrReceivers> userReceiverList = new ArrayList<UsrReceivers>();
		userReceiverList.add(userReceiver);
		receiverUserService.deleteContact(userReceiverList);
		LOG.debug("delete receiver for "+users.getUserNo());
		listUserReceivers(users);
		return SUCCESS;
	}
	
	/**
	 * 展示设置游客框
	 * @return
	 */
	private String goToSetReceiverUserPage()
	{
		UserUser users = super.getUser();
		if (null == users) {
			LOG.error("Cann't get user's point log for empty userId");
			return ERROR;
		}
		if(receiverId != null)
		{
			currentUserReceiver = receiverUserService.getUserReceiversByReceiverId(receiverId);
		}
		else
		{
			//do nothing
		}
		displaySetReceiverUserForm = "Y";
		listUserReceivers(users);
		return SUCCESS;
	}

	private void listUserReceivers(UserUser users)
	{
		Long count = receiverUserService.loadReceiversByPageConfigCount(users.getUserNo());
		pageConfig = new Page<UsrReceivers>(count, ROW_NUMBER, currentPage);
		this.receiversList = receiverUserService.loadReceiversByPageConfig(pageConfig.getStartRows(), pageConfig.getEndRows(), users.getUserNo());
		pageConfig.setItems(this.receiversList);
		if (pageConfig.getItems().size() > 0) {
			this.pageConfig.setUrl("http://www.lvmama.com/myspace/userinfo/visitor.do?currentPage=");
		}
	}
	
	
	
	/**
	 * 替换***
	 * @param usrReceivers 目标
	 * @param receiversList 数据源
	 * @return
	 */
	private String replaceHiddenMobile(String receiverId,String postMobile){
		String resultMobile=postMobile;
		List<UsrReceivers> receiversList = this.receiverUserService.loadUserReceiversByUserId(getUserId());
		Pattern pattern = Pattern.compile("(1[0-9]{2,2}[\\*]{4,4}[0-9]{4,4})");
		if(StringUtils.isNotBlank(receiverId)){//选择的常用联系人
			Matcher matcher = pattern.matcher(postMobile);
			if (matcher.matches()) {//134****8362,替换掉****
				for(UsrReceivers receiver:receiversList){
					if(receiver.getReceiverId().equals(receiverId)){
						resultMobile=receiver.getMobileNumber();
						break;
					}
				}
			}
		}
		return resultMobile;
	}
	
	
	
	private UsrReceivers composeUserReceiverObject()
	{
		UsrReceivers userReceiver = new UsrReceivers();
		userReceiver.setReceiverName(receiverName);
		if(receiverMobileNumber != null)
		{
			receiverMobileNumber = receiverMobileNumber.replace(" ", "");
			receiverMobileNumber=replaceHiddenMobile(receiverId,receiverMobileNumber);
		}
		userReceiver.setMobileNumber(receiverMobileNumber);
		userReceiver.setCardType(receiverCardType);
		if(receiverCardNum != null)
		{
			receiverCardNum = receiverCardNum.replace(" ", "");
		}
		userReceiver.setCardNum(receiverCardNum);
		userReceiver.setAddress(receiverAddress);
		if(receiverPostCode != null)
		{
			receiverPostCode = receiverPostCode.replace(" ", "");
		}		
		userReceiver.setPostCode(receiverPostCode);
		userReceiver.setUseOffen("true");
		if(receiverId != null)
		{
			userReceiver.setReceiverId(receiverId);
		}
		return userReceiver;
	}

	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}


	public List<UsrReceivers> getReceiversList() {
		return receiversList;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverMobileNumber() {
		return receiverMobileNumber;
	}

	public void setReceiverMobileNumber(String receiverMobileNumber) {
		this.receiverMobileNumber = receiverMobileNumber;
	}

	public String getReceiverCardType() {
		return receiverCardType;
	}

	public void setReceiverCardType(String receiverCardType) {
		this.receiverCardType = receiverCardType;
	}

	public String getReceiverCardNum() {
		return receiverCardNum;
	}

	public void setReceiverCardNum(String receiverCardNum) {
		this.receiverCardNum = receiverCardNum;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverPostCode() {
		return receiverPostCode;
	}

	public void setReceiverPostCode(String receiverPostCode) {
		this.receiverPostCode = receiverPostCode;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public UsrReceivers getCurrentUserReceiver() {
		return currentUserReceiver;
	}

	public void setCurrentUserReceiver(UsrReceivers currentUserReceiver) {
		this.currentUserReceiver = currentUserReceiver;
	}

	public String getDisplaySetReceiverUserForm() {
		return displaySetReceiverUserForm;
	}

	public void setDisplaySetReceiverUserForm(String displaySetReceiverUserForm) {
		this.displaySetReceiverUserForm = displaySetReceiverUserForm;
	}


	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}


	public Page<UsrReceivers> getPageConfig() {
		return pageConfig;
	}


	public Long getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(Long currentPage) {
		this.currentPage = currentPage;
	}

}
