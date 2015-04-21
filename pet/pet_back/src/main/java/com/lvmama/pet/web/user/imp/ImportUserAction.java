package com.lvmama.pet.web.user.imp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zul.Textbox;

import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.user.UserBatchUser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.user.UserBatchRegisterService;
import com.lvmama.comm.pet.service.user.UserBatchUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.utils.ZkMessage;

public class ImportUserAction extends UserChannelBaseAction {
	private static final long serialVersionUID = -1089031695261214724L;

	private UserBatchRegisterService userBatchRegisterService;

	private List<UserBatchUser> batchUsers;
	@Autowired
	private UserUserProxy userUserProxy;
	
	@Autowired
	private UserClient userClient;
	@Autowired
	private UserBatchUserService userBatchUserService;
	@Autowired
	private MarkCouponService markCouponService;
	private SmsRemoteService smsRemoteService;
	

	@Override
	public void doBefore() {
		searchConds.put("registerType",
				Constant.IMPORT_TYPE.REGISTER_IMMEDIATELY.name());
	}

	@Override
	public void doAfter() {
		desktop.enableServerPush(true);
	}

	public void setRegisterType(String type) {
		searchConds.put("registerType", type);
		((Textbox) this.getComponent().getFellow("confirmSMS"))
				.setDisabled(!"REGISTER_NEED_CONFIRM".equalsIgnoreCase(type));
	}

	public synchronized void submit() {
		if (null == searchConds.get("channelId")) {
			ZkMessage.showError("请选择渠道!");
			return;
		}
		if (null == searchConds.get("cityId")) {
			ZkMessage.showError("请选择所属城市!");
			return;
		}
		if (!super.validate()) { return; }
		BufferedReader reader = null;
		if (null == searchConds.get("furl")) {
			ZkMessage.showError("请上传文件!");
			return;
		}

		batchUsers = new ArrayList<UserBatchUser>();

		String furl = ((Textbox) getComponent().getFellow("furl")).getValue();
		if (null == furl) {
			ZkMessage.showError("无法找到上传的文件，请重新尝试!");
			return;
		}
		try {	
			reader = new BufferedReader(new FileReader(furl));
			String temp = null;
			int i = 0;
			while ((temp = reader.readLine()) != null) {
				temp = temp.trim();
				i++;
				try {
					StringTokenizer st = new StringTokenizer(" " + temp + " ",
							",");
					UserBatchUser b_user = new UserBatchUser();
					if (st.hasMoreTokens()) {
						b_user.setMobileNumber(st.nextToken().trim());
					}
					if (st.hasMoreTokens()) {
						b_user.setEmail(st.nextToken().trim());
					}
					if (st.hasMoreElements()) {
						b_user.setRealName(st.nextToken().trim());
					}
					if (st.hasMoreElements()) {
						String gender = null;
						if (st.hasMoreTokens()) {
							gender = st.nextToken().trim();
						}
						b_user.setGender((null == gender || gender.equals("") || (!gender
								.equalsIgnoreCase("F") && !gender
								.equalsIgnoreCase("M"))) ? null : gender);
					}
					b_user.setCityId((String) searchConds.get("cityId"));
					if (!StringUtil.isEmptyString(b_user.getMobileNumber())
							|| !StringUtil.isEmptyString(b_user.getEmail())) {
						batchUsers.add(b_user);
					}
					
				} catch (Exception e) {
					ZkMessage.showError("第" + i + "行存在错误的数据格式,此行将被放弃!");
				}
			}
		} catch (java.io.FileNotFoundException fnfe) {
			ZkMessage.showError("无法找到上传的文件，请重新尝试!");
			return;
		} catch (java.io.IOException ioe) {
			ioe.printStackTrace();
			ZkMessage.showError("读取文件的时候发生异常的错误，请重新尝试!");
			return;
		}finally{
			try{if(reader!=null)reader.close();}catch(Exception e){}
		}

		if (batchUsers.isEmpty()) {
			ZkMessage.showError("需要导入的用户数为0,此次操作无效!");
			return;
		}
		synchronized(this)//批量导入用户要避免同时多次操作，需要同步保护
		{
			Long userBatchId=userBatchRegisterService.insert(searchConds, batchUsers);
			try {
				if (Constant.IMPORT_TYPE.REGISTER_IMMEDIATELY.name()
						.equalsIgnoreCase((String) searchConds.get("registerType"))) {
					immediatelyRegister(userBatchId);
				}
				if (Constant.IMPORT_TYPE.REGISTER_NEED_CONFIRM.name()
						.equalsIgnoreCase((String) searchConds.get("registerType"))) {
					needConfirmRegister();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ZkMessage.showInfo("本次操作已经执行完成!");
		super.closeWindow();
	}

	/**
	 * 把选中的渠道类型放到MAP中
	 */
	public void putChannelType(String value) {
		searchConds.put("type", value);
	}

	/**
	 * 把选中的渠道标识放到MAP中
	 */
	public void putChannelId(String value) {
		searchConds.put("channelId", value);
	}

	private void immediatelyRegister(Long userBatchId) {
		StringTokenizer couponToken = null;
		StringTokenizer oldCouponToken=null;
		try {
			//赠送优惠券
			if (null != (String) searchConds.get("coupon")) {
				String coupon = ((String) searchConds.get("coupon")).replace('，', ',');
				couponToken = new StringTokenizer(coupon, ",");
			}	
			if (null != (String) searchConds.get("oldCoupon")) {
				String oldCoupon = ((String) searchConds.get("oldCoupon")).replace('，', ',');
				oldCouponToken = new StringTokenizer(oldCoupon, ",");
			}
		} catch (Exception e) {
			
		}
		
		try {
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("batchRegisterId", userBatchId);
			List<UserBatchUser> userBatchUsers=userBatchUserService.query(params);
			for (UserBatchUser user : userBatchUsers) {
				Long userId = null;
				try {
					userId = userClient.batchRegisterWithChannel(user.getMobileNumber(), 
							user.getEmail(),
							user.getRealName(), 
							user.getGender(),
							(String) searchConds.get("smsTemplate"),
							(String) searchConds.get("mailTemplate"),
							(String) searchConds.get("cityId"), 
							String.valueOf((Long) searchConds.get("channelId")));

					UserBatchUser _u = userBatchUserService
							.getBatchUserByPrimaryKey(user.getBatchUserId());
					if(_u != null){
						if (null == userId) {
							_u.setRegisterStatus(Constant.REGISTER_TYPE.REGISTER_FAILURE
									.name());
							//在此加入是否是老会员的判定
							UserUser _user=null;
							if(_user==null){
								_user=userUserProxy.getUsersByMobOrNameOrEmailOrCard(user.getRealName());
							}
							if(_user==null){
								_user=userUserProxy.getUsersByMobOrNameOrEmailOrCard(user.getMobileNumber());
							}
							if(_user==null){
								_user=userUserProxy.getUsersByMobOrNameOrEmailOrCard(user.getEmail());
							}
							if(null!=oldCouponToken && null!=_user){
								while (oldCouponToken.hasMoreTokens()) {
									try {
										markCouponService.bindingUserAndCouponCode(_user,  markCouponService.generateSingleMarkCouponCodeByCouponId(Long.parseLong(oldCouponToken.nextToken())).getCouponCode());
									} catch (Exception e) {
										
									}
								}
								Map<String, Object> parameters = new HashMap<String, Object>();
								parameters.put("username", _user.getUserName());
								parameters.put("password", _user.getRealPass());
								parameters.put("userId", _user.getUserId());
								String customedSmsContent=(String) searchConds.get("oldSmsTemplate");
								String smsContent = StringUtil.composeMessage(customedSmsContent, parameters);;
								smsRemoteService.sendSms(smsContent, user.getMobileNumber());
							}
						} else {
							_u.setRegisterStatus(Constant.REGISTER_TYPE.REGISTER_SUCCESS
									.name());
							_u.setUserId(userId);
						}
						userBatchUserService.update(_u);
			            
						if (null != couponToken && null!=userId) {
							while (couponToken.hasMoreTokens()) {
								try {
									markCouponService.bindingUserAndCouponCode(userUserProxy.getUserUserByPk(userId),  markCouponService.generateSingleMarkCouponCodeByCouponId(Long.parseLong(couponToken.nextToken())).getCouponCode());
								} catch (Exception e) {
									
								}
							}
						}
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void needConfirmRegister() {
		/**
		 * @ TODO 处理
		 */
		// try {
		// for (ComBatchUser user : batchUsers) {
		// if (null != user.getMobileNumber()) {
		// String content = (String) searchConds.get("confirmSMS");
		// if (!ssoRemoteProxy.checkMobileInUse(user.getMobileNumber())) {
		// remoteSmsService.sendSmsInWorking(content, user.getMobileNumber());
		// user.setReply("N");
		// comBatchUserService.update(user);
		// } else {
		// user.setRegisterStatus(Constant.REGISTER_TYPE.REGISTER_FAILURE.name());
		// comBatchUserService.update(user);
		// }
		// } else {
		// user.setRegisterStatus(Constant.REGISTER_TYPE.REGISTER_FAILURE.name());
		// comBatchUserService.update(user);
		// }
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public String getCityId() {
		return null;
	}

	public void changeCity(String cityId) {
		this.searchConds.put("cityId", cityId);
	}

	public void setUserBatchRegisterService(
			UserBatchRegisterService userBatchRegisterService) {
		this.userBatchRegisterService = userBatchRegisterService;
	}

	public List<UserBatchUser> getBatchUsers() {
		return batchUsers;
	}

	public void setBatchUsers(List<UserBatchUser> batchUsers) {
		this.batchUsers = batchUsers;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setUserBatchUserService(UserBatchUserService userBatchUserService) {
		this.userBatchUserService = userBatchUserService;
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}
	
}