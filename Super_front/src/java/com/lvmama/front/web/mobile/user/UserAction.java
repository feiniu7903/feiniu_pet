package com.lvmama.front.web.mobile.user;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.UUIDGenerator;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;

@Results( { @Result(name = "receiver", location = "/WEB-INF/pages/mobile/myaccount/receiver.ftl", type = "freemarker"),
			@Result(name = "moreReceivers", location = "/WEB-INF/pages/mobile/myaccount/more_receivers.ftl", type = "freemarker")})
/**
 * 我的账户操作类
 */
public class UserAction extends BaseAction {
	private static final long serialVersionUID = -3280031265823092401L;
	private List<UsrReceivers> receiversList;
	private UsrReceivers usrReceiver;
	private IReceiverUserService receiverUserService;
	private Page<?> pageConfig;
	private String page = "1";
	private String recieverId;

	@Action(value = "/m/account/info", results = @Result(location = "/WEB-INF/pages/mobile/myaccount/detail.ftl"))
	public String execute() {
		Long count = receiverUserService.loadReceiversByPageConfigCount(getUserId());
		pageConfig = pageConfig.page(count, 2,Long.valueOf(this.page));
		receiversList = receiverUserService.loadReceiversByPageConfig(0,2, getUserId());
		//receiversList = (List<ViewUsrReceivers>) pageConfig.getItems();
		return SUCCESS;
	}

	@Action("/m/account/toReceiver")
	public String toReceiver() {
		this.errorMessage("");
		if(recieverId != null) {
			usrReceiver = receiverUserService.getRecieverByPk(recieverId);
		}else{
			usrReceiver = new UsrReceivers();
		}
		return "receiver";
	}
	
	/**
	 * 更多取票人
	 * @return
	 */
	@Action("/m/account/moreReceivers")
	public String moreReceivers() {
		Long count = receiverUserService.loadReceiversByPageConfigCount(getUserId());
		pageConfig = new Page(count, 8, Integer.parseInt(page));
		pageConfig.setUrl("/m/account/moreReceivers.do?");
		receiversList = receiverUserService.loadReceiversByPageConfig(pageConfig.getStartRows(),pageConfig.getStartRows() + 8, getUserId());
		return "moreReceivers";
	}

	/**
	 * 新增或修改取票人
	 */
	@Action("/m/account/addOrUpdateReceiver")
	public String addOrUpdateReceiver() {
		if(usrReceiver.getReceiverName()==null || "".equals(usrReceiver.getReceiverName())) {
			this.errorMessage("请输入取票人姓名");
			return "receiver";
		}
		if(usrReceiver.getMobileNumber()==null || "".equals(usrReceiver.getMobileNumber())) {
			this.errorMessage("请输入手机号");
			return "receiver";
		} else {
			Pattern p = Pattern.compile(Constant.MOBILE_REGX);
			Matcher m = p.matcher(usrReceiver.getMobileNumber());
			boolean b = m.matches();
			if(!b) {
				this.errorMessage("手机号格式不正确");
				return "receiver";
			}
		}
		if(usrReceiver.getReceiverId().equals("")) {
			UUIDGenerator gen = new UUIDGenerator();
			this.usrReceiver.setReceiverId(gen.generate().toString());
			this.usrReceiver.setUserId(this.getUserId());
			this.usrReceiver.setIsValid("Y");
			this.usrReceiver.setCreatedDate(new Date());
			this.usrReceiver.setReceiversType(Constant.RECEIVERS_TYPE.CONTACT
					.name());
			receiverUserService.insert(this.usrReceiver);
		}else{
			receiverUserService.update(usrReceiver);
		}
		return moreReceivers();
	}

	public List<UsrReceivers> getReceiversList() {
		return receiversList;
	}

	public void setReceiversList(List<UsrReceivers> receiversList) {
		this.receiversList = receiversList;
	}



	public UsrReceivers getUsrReceiver() {
		return usrReceiver;
	}

	public void setUsrReceiver(UsrReceivers usrReceiver) {
		this.usrReceiver = usrReceiver;
	}	

	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}

	public Page<?> getPageConfig() {
		return pageConfig;
	}

	public void setPageConfig(Page<?> pageConfig) {
		this.pageConfig = pageConfig;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setRecieverId(String recieverId) {
		this.recieverId = recieverId;
	}
}
