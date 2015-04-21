package com.lvmama.front.web.buy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.JSONException;
import org.json.JSONObject;

import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UUIDGenerator;
import com.lvmama.comm.utils.ord.ProductBlackValidateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;

/**
 * @author shihui
 */
@ParentPackage("json-default")
@Results({
	@Result(name = "address", location = "/WEB-INF/pages/buy/address.ftl",type="freemarker")
})
/**
 * 配送地址操作类
 * 
 * @author shihui
 * */
public class UsrReceiversAction extends BaseAction {

	private static final long serialVersionUID = -859893604241046147L;
	private IReceiverUserService receiverUserService;
	private UsrReceivers usrReceivers = new UsrReceivers();
	/**
	 * 已存在地址列表
	 * */
	private List<UsrReceivers> usrReceiversList;
	//游玩人对象
	List<UsrReceivers> travellerList = new ArrayList<UsrReceivers>();
	
	/**
	 * 黑名单新增属性
	 * @author zenglei
	 *   2014-5-8 14:48:49
	 */
	private Long productId;
	/**
	 * 加载地址列表
	 * */
	@Action("/usrReceivers/loadAddresses")
	public String loadAddresses() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", getUserId());
		params.put("receiversType", Constant.RECEIVERS_TYPE.ADDRESS.name());
		this.usrReceiversList = receiverUserService.loadRecieverByParams(params);
		if(usrReceivers.getReceiverId() != null){
			this.usrReceivers = this.receiverUserService.getRecieverByPk(usrReceivers.getReceiverId());
		}
		return "address";
	}
	
	/**
	 * 新增地址
	 */
	@Action("/usrReceivers/saveAddress")
	public void saveAddress() {
		try {
			UUIDGenerator gen = new UUIDGenerator();
			this.usrReceivers.setReceiverId(gen.generate().toString());
			this.usrReceivers.setUserId(this.getUserId());
			this.usrReceivers.setIsValid("Y");
			this.usrReceivers.setCreatedDate(new Date());
			this.usrReceivers.setReceiversType(Constant.RECEIVERS_TYPE.ADDRESS.name());
			this.receiverUserService.insert(usrReceivers);
			returnMessage(true);
		} catch (Exception e) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
			returnMessage(false);
		}
	}

	/**
	 * 修改地址
	 */
	@Action("/usrReceivers/updateAddress")
	public void updateAddress() {
		try {
			this.receiverUserService.update(usrReceivers);
			returnMessage(true);
		} catch (Exception e) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
			returnMessage(false);
		}
	}
	
	/**
	 * 删除地址
	 */
	@Action("/usrReceivers/deleteAddress")
	public void deleteAddress() {
		try {
			this.receiverUserService.delete(this.usrReceivers.getReceiverId());
			returnMessage(true);
		} catch (Exception e) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
			returnMessage(false);
		}
	}
	
	/**
	 * 返回操作成功信息
	 */
	private void returnMessage(boolean flag) {
		try {
			if (flag) {
				this.getResponse().getWriter().write("{result:true}");
			} else {
				this.getResponse().getWriter().write("{result:false}");
			}
		} catch (Exception e) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
		}

	}
	
	/**
	 * 新增或修改紧急联系人
	 * @throws JSONException 
	 */
	@Action("/usrReceivers/saveEmReceivers")
	public void saveEmReceivers() throws JSONException {
		JSONObject result=new JSONObject();
		try {
			if(!StringUtils.isNotBlank(usrReceivers.getReceiverName())) {
				result.put("success", false);
				result.put("msg", "请输入紧急联系人姓名");
				outputJsonMsg(result.toString());
				return ;
			}
			if(!StringUtils.isNotBlank(usrReceivers.getMobileNumber())){
				result.put("success", false);
				result.put("msg", "请输入紧急联系人手机号");
				outputJsonMsg(result.toString());
				return ;
			} else {
				if(StringUtils.isNotBlank(usrReceivers.getReceiverId())){
					Pattern pattern = Pattern.compile("(1[0-9]{2,2}[\\*]{4,4}[0-9]{4,4})");
					Matcher matcher = pattern.matcher(usrReceivers.getMobileNumber());
					if (matcher.matches()) {//134****8362,替换掉****
						UsrReceivers dbUsrReceivers=receiverUserService.getRecieverByPk(usrReceivers.getReceiverId());
						usrReceivers.setMobileNumber(dbUsrReceivers.getMobileNumber());
					}
				}
				Pattern p = Pattern.compile(Constant.MOBILE_REGX);
				Matcher m = p.matcher(usrReceivers.getMobileNumber());
				boolean b = m.matches();
				if(!b) {
					result.put("success", false);
					result.put("msg", "手机号格式不正确");
					outputJsonMsg(result.toString());
					return ;
				}
			}
			Map res = this.saveUsrRecever(usrReceivers);
			result.put("msg", "成功");
			result.put("success", true);
			result.put("userRecever", res);
			outputJsonMsg(result.toString());
		} catch (Exception e) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
			result.put("success", false);
			result.put("msg", "出现未知错误");
			try {
				outputJsonMsg(result.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	
	
	
	/**
	 * 新增或修改游玩人信息
	 * @throws JSONException 
	 * @throws IOException 
	 */
	@Action("/usrReceivers/savePlayReceivers")
	public void savePlayReceivers() throws JSONException, IOException {
		JSONObject result=new JSONObject();
		List ja = new ArrayList();
		try {

			boolean flag = true;
			/*if(travellerList == null || travellerList.isEmpty()){
				result.put("success", false);
				result.put("msg","请输入游玩人名称");
				flag=false;
			}*/


			for(UsrReceivers usrReceivers:travellerList){

				/*if(!StringUtils.isNotBlank(usrReceivers.getReceiverName())){
					result.put("success", false);
					result.put("msg","请输入游玩人名称");
					flag = false;
					break;
				}*/

				//如果手机不为空
				if(StringUtils.isNotEmpty(usrReceivers.getMobileNumber())){
					if(StringUtils.isNotBlank(usrReceivers.getReceiverId())){
						Pattern pattern = Pattern.compile("(1[0-9]{2,2}[\\*]{4,4}[0-9]{4,4})");
						Matcher matcher = pattern.matcher(usrReceivers.getMobileNumber());
						if (matcher.matches()) {//134****8362,替换掉****
							UsrReceivers dbUsrReceivers=receiverUserService.getRecieverByPk(usrReceivers.getReceiverId());
							usrReceivers.setMobileNumber(dbUsrReceivers.getMobileNumber());
						}
					}
					Pattern p = Pattern.compile(Constant.MOBILE_REGX);
					Matcher m = p.matcher(usrReceivers.getMobileNumber());
					boolean b = m.matches();
					if(!b) {
						result.put("success", false);
						result.put("msg", "手机号格式不正确");
						outputJsonMsg(result.toString());
						return ;
					}
				
				}
				
				if(StringUtils.isNotEmpty(usrReceivers.getPinyin())){
                    Pattern  p_py = Pattern.compile("[a-zA-Z]*");
                    Matcher m_py = p_py.matcher(usrReceivers.getPinyin());
                    if(!m_py.matches()) {
                        result.put("success", false);
                        result.put("msg", "请输入正确的拼音");
                        outputJsonMsg(result.toString());
                        return ;
                    }
				}
				
				Map res = this.saveUsrRecever(usrReceivers);
				ja.add(res);
			}
			result.put("success", true);
			result.put("list", ja);
			outputJsonMsg(result.toString());
		} catch (Exception e) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
			result.put("success", false);
			result.put("msg", "出现未知错误");
			try {
				outputJsonMsg(result.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	
	private Map saveUsrRecever(UsrReceivers usrReceiver) throws JSONException  {
		Map jsonRecever = new TreeMap();
		if(usrReceiver != null){
			UsrReceivers user = null;
			if(StringUtils.isEmpty(usrReceiver.getReceiverId())) {
				user = new UsrReceivers();
				UUIDGenerator gen = new UUIDGenerator();
				String receiverId= gen.generate().toString();
				user.setReceiverId(receiverId);
				user.setCreatedDate(new Date());
				user.setUserId(this.getUserId());
				user.setIsValid("Y");
				user.setReceiversType(Constant.RECEIVERS_TYPE.CONTACT.name());
				user.setReceiverName(usrReceiver.getReceiverName());
				user.setCardType(usrReceiver.getCardType());
				user.setPinyin(usrReceiver.getPinyin());
				user.setMobileNumber(usrReceiver.getMobileNumber());
				user.setCardNum(usrReceiver.getCardNum());
				user.setEmail(usrReceiver.getEmail());
				receiverUserService.insert(user);
			} else {
				user=receiverUserService.getRecieverByPk(usrReceiver.getReceiverId());
				if(user != null && getUserId().equals(user.getUserId())){
					if(StringUtil.isNotEmptyString(usrReceiver.getReceiverName())){
						user.setReceiverName(usrReceiver.getReceiverName());
					}
					if(StringUtil.isNotEmptyString(usrReceiver.getCardType())){
						user.setCardType(usrReceiver.getCardType());
					}
					if(StringUtil.isNotEmptyString(usrReceiver.getMobileNumber())){
						user.setMobileNumber(usrReceiver.getMobileNumber());
					}
					if(StringUtil.isNotEmptyString(usrReceiver.getCardNum())){
						user.setCardNum(usrReceiver.getCardNum());
					}
					if(StringUtil.isNotEmptyString(usrReceiver.getEmail())){
						user.setEmail(usrReceiver.getEmail());
					}
					if(StringUtil.isNotEmptyString(usrReceiver.getPinyin())){
                        user.setPinyin(usrReceiver.getPinyin());
                    }
					receiverUserService.update(user);
				}else{
					throw new RuntimeException("user is null");
				}
			}
			jsonRecever.put("cardType", user.getCardType()==null ? "" : user.getCardType());
			jsonRecever.put("cardNum", user.getCardNum()==null ? "" : user.getCardNum());
			jsonRecever.put("receiverName", user.getReceiverName()==null?"":user.getReceiverName());
			jsonRecever.put("mobileNumber", user.getMobileNumber()==null ? "" : StringUtil.hiddenMobile( user.getMobileNumber()));
			jsonRecever.put("receiverId", user.getReceiverId());
			jsonRecever.put("email", user.getEmail() == null ? "" : StringUtil.hiddenEmail(user.getEmail()));
			jsonRecever.put("pinyin", user.getPinyin() == null ? "" : user.getPinyin());
		}
		return jsonRecever;
	}
	
	/**
	 * 保存订单联系人信息
	 * @throws JSONException 
	 */
	@Action("/usrReceivers/saveOrderReceivers")
	public void saveOrderReceivers() throws JSONException{
		JSONObject result=new JSONObject();
		try {
			if(!StringUtils.isNotBlank(usrReceivers.getReceiverName())) {
				result.put("success", false);
				result.put("msg", "请输入订单联系人姓名");
				outputJsonMsg(result.toString());
				return ;
			}
			if(!StringUtils.isNotBlank(usrReceivers.getMobileNumber())){
				result.put("success", false);
				result.put("msg", "请输入订单联系人手机号");
				outputJsonMsg(result.toString());
				return ;
			} else {
				if(StringUtils.isNotBlank(usrReceivers.getReceiverId())){
					Pattern pattern = Pattern.compile("(1[0-9]{2,2}[\\*]{4,4}[0-9]{4,4})");
					Matcher matcher = pattern.matcher(usrReceivers.getMobileNumber());
					if (matcher.matches()) {//134****8362,替换掉****
						UsrReceivers dbUsrReceivers=receiverUserService.getRecieverByPk(usrReceivers.getReceiverId());
						usrReceivers.setMobileNumber(dbUsrReceivers.getMobileNumber());
					}
				}
				Pattern p = Pattern.compile(Constant.MOBILE_REGX);
				Matcher m = p.matcher(usrReceivers.getMobileNumber());
				boolean b = m.matches();
				if(!b) {
					result.put("success", false);
					result.put("msg", "手机号格式不正确");
					outputJsonMsg(result.toString());
					return ;
				}
			}
			Map res = this.saveUsrRecever(usrReceivers);
			result.put("msg", "成功");
			result.put("success", true);
			result.put("userRecever", res);
			outputJsonMsg(result.toString());
		} catch (Exception e) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
			result.put("success", false);
			result.put("msg", "出现未知错误");
			try {
				outputJsonMsg(result.toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
			
	/**
	 * 保存取票人信息
	 * @throws JSONException 
	 */
	@Action("/usrReceivers/saveTakeReceivers")
	public void saveTakeReceivers() throws JSONException{
		JSONObject result=new JSONObject();
		try {
			if(!StringUtils.isNotBlank(usrReceivers.getReceiverName())) {
				result.put("success", false);
				result.put("msg", "请输入取票人姓名");
				outputJsonMsg(result.toString());
				return ;
			}
			if(!StringUtils.isNotBlank(usrReceivers.getMobileNumber())){
				result.put("success", false);
				result.put("msg", "请输入取票人手机号");
				outputJsonMsg(result.toString());
				return ;
			} else {
				if(StringUtils.isNotBlank(usrReceivers.getReceiverId())){
					Pattern pattern = Pattern.compile("(1[0-9]{2,2}[\\*]{4,4}[0-9]{4,4})");
					Matcher matcher = pattern.matcher(usrReceivers.getMobileNumber());
					UsrReceivers dbUsrReceivers = null;
					if (matcher.matches()) {//134****8362,替换掉****
						dbUsrReceivers=receiverUserService.getRecieverByPk(usrReceivers.getReceiverId());
						usrReceivers.setMobileNumber(dbUsrReceivers.getMobileNumber());
					}
					pattern = Pattern.compile("\\w+([-+.]\\w+)*[\\*]{4,4}@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
					matcher = pattern.matcher(usrReceivers.getEmail());
					if (matcher.matches()) {//替换掉****
						if (dbUsrReceivers == null) {
							dbUsrReceivers=receiverUserService.getRecieverByPk(usrReceivers.getReceiverId());
						}
						usrReceivers.setEmail(dbUsrReceivers.getEmail());
					}
				}
				Pattern p = Pattern.compile(Constant.MOBILE_REGX);
				Matcher m = p.matcher(usrReceivers.getMobileNumber());
				boolean b = m.matches();
				if(!b) {
					result.put("success", false);
					result.put("msg", "手机号格式不正确");
					outputJsonMsg(result.toString());
					return ;
				}
				if (!StringUtils.isEmpty(usrReceivers.getEmail())) {
					p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
					m = p.matcher(usrReceivers.getEmail());
					if(!m.matches()) {
						result.put("success", false);
						result.put("msg", "电子邮箱格式不正确");
						outputJsonMsg(result.toString());
						return ;
					}
				}
				if(!StringUtils.isEmpty(usrReceivers.getPinyin())){
                    Pattern  p_py = Pattern.compile("[a-zA-Z]*");
                    Matcher m_py = p_py.matcher(usrReceivers.getPinyin());
                    if(!m_py.matches()) {
                        result.put("success", false);
                        result.put("msg", "请输入正确的拼音");
                        outputJsonMsg(result.toString());
                        return ;
                    }
                }
			}
			//校验是否属于黑名单
			boolean flag1 = ProductBlackValidateUtil.getProductBlackValidateUtil().validateBlackByMobileAndProduct(productId, usrReceivers.getMobileNumber());
			if(flag1){
				Map res = this.saveUsrRecever(usrReceivers);
				result.put("msg", "成功");
				result.put("success", true);
				result.put("userRecever", res);
				outputJsonMsg(result.toString());
			}else{
				result.put("success", false);
				result.put("msg", "手机号近期已下太多订单");
				outputJsonMsg(result.toString());
				return ;
			}
			
		} catch (Exception e) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
			result.put("success", false);
			result.put("msg", "出现未知错误");
			try {
				outputJsonMsg(result.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	public UsrReceivers getUsrReceivers() {
		return usrReceivers;
	}

	public void setUsrReceivers(UsrReceivers usrReceivers) {
		this.usrReceivers = usrReceivers;
	}

	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}

	public List<UsrReceivers> getUsrReceiversList() {
		return usrReceiversList;
	}


	public void setUsrReceiversList(List<UsrReceivers> usrReceiversList) {
		this.usrReceiversList = usrReceiversList;
	}

	public List<UsrReceivers> getTravellerList() {
		return travellerList;
	}

	public void setTravellerList(List<UsrReceivers> travellerList) {
		this.travellerList = travellerList;
	}

	public IReceiverUserService getReceiverUserService() {
		return receiverUserService;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
