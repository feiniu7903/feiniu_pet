package com.lvmama.front.web.product;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.ord.ProductBlackValidateUtil;
import com.lvmama.comm.vo.ViewBuyInfo;
import com.lvmama.front.web.BaseAction;

public class ProductBlackValidateAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IReceiverUserService receiverUserService;
	
	protected UsrReceivers contact = new UsrReceivers();
	private ViewBuyInfo buyInfo;
	
	@Action("/product/validateBlackByProds")
	public String validateBlackByProds(){
		JSONResult jsonObject=new JSONResult(getResponse());
		boolean flag1 = ProductBlackValidateUtil.getProductBlackValidateUtil().validateBlackByUserAndProductForProds(buyInfo.getProductId(), this.getUser().getId());
		if(!flag1){
			LOG.info("============validate blackListProds validateBlackByUserAndProductForProds ==============="+flag1);
			jsonObject.put("flag", false);
			jsonObject.put("msg", "用户己达购买指定商品上限!");
			jsonObject.output();
			return null;
		}
		if(StringUtil.isNotEmptyString(contact.getMobileNumber())){
			contact = getContacts();
			boolean flag2 = ProductBlackValidateUtil.getProductBlackValidateUtil().validateBlackByMoblieAndProductForProds(buyInfo.getProductId(), contact.getMobileNumber());
			if(!flag2){
				LOG.info("============validate blackListProds validateBlackByMoblieAndProductForProds ==============="+flag2);
				jsonObject.put("flag", false);
				jsonObject.put("msg", "该手机号码己达购买指定商品上限!");
				jsonObject.output();
				return null;
			}
		}
		jsonObject.put("flag", true);
		jsonObject.put("msg", "");
		jsonObject.output();
		return null;
	}
	
	public UsrReceivers getContacts(){
		List<UsrReceivers> receiversList = this.receiverUserService.loadUserReceiversByUserId(getUserId());
		return replaceHiddenMobile(this.contact,receiversList);
	}
	/**
	 * 替换***
	 * @param usrReceivers 目标
	 * @param receiversList 数据源
	 * @return
	 */
	private UsrReceivers replaceHiddenMobile(UsrReceivers usrReceivers,List<UsrReceivers> receiversList){
		if(null==usrReceivers){
			return null;
		}
		if(StringUtils.isNotEmpty(usrReceivers.getMobileNumber())){
			Pattern pattern = Pattern.compile("(1[0-9]{2,2}[\\*]{4,4}[0-9]{4,4})");
			if(StringUtils.isNotBlank(usrReceivers.getReceiverId())){//选择的常用联系人
				Matcher matcher = pattern.matcher(usrReceivers.getMobileNumber());
				if (matcher.matches()) {//134****8362,替换掉****
					for(UsrReceivers receiver:receiversList){
						if(receiver.getReceiverId().equals(usrReceivers.getReceiverId())){
							usrReceivers.setMobileNumber(receiver.getMobileNumber());
							break;
						}
					}
				}
			}
		}
		return usrReceivers;
	}
	
	public UsrReceivers getContact() {
		return contact;
	}

	public void setContact(UsrReceivers contact) {
		this.contact = contact;
	}

	public ViewBuyInfo getBuyInfo() {
		return buyInfo;
	}

	public void setBuyInfo(ViewBuyInfo buyInfo) {
		this.buyInfo = buyInfo;
	}

	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}

}
