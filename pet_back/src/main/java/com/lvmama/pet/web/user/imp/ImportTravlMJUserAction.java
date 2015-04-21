package com.lvmama.pet.web.user.imp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zul.Textbox;

import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.user.UserBatchRegister;
import com.lvmama.comm.pet.po.user.UserBatchUser;
import com.lvmama.comm.pet.service.user.UserBatchRegisterService;
import com.lvmama.comm.pet.service.user.UserBatchUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.utils.ZkMessage;

public class ImportTravlMJUserAction extends UserChannelBaseAction {
	private static final long serialVersionUID = 1L;

	private UserBatchRegisterService userBatchRegisterService;

	private List<UserBatchUser> batchUsers;
	@Autowired
	private UserUserProxy userUserProxy;
	@Autowired
	private UserClient userClient;
	@Autowired
	private UserBatchUserService userBatchUserService;
	

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
	}

	public synchronized void submit() {
		if (null == searchConds.get("channelId")) {
			ZkMessage.showError("请选择渠道!");
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
				if(!StringUtil.isNotEmptyString(temp)){
					continue;
				}
				temp = temp.trim();
				i++;
				try {
					StringTokenizer st = new StringTokenizer(" " + temp + " ",
							",");
					UserBatchUser b_user = new UserBatchUser();
					if (st.hasMoreElements()) {
						b_user.setRealName(st.nextToken().trim());
					}
					if (st.hasMoreTokens()) {
						b_user.setCityId(st.nextToken().trim());//用此字段作为密码存储
					}
					if (st.hasMoreTokens()) {
						b_user.setVisitResult(st.nextToken().trim());//用词字段作为ip存储
					}
						batchUsers.add(b_user);
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
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("batchRegisterId", userBatchId);
			batchUsers=userBatchUserService.query(params);
			UserBatchRegister userBatchRegister= userBatchRegisterService.getBatchRegisterByPk(userBatchId);
			int k=0;
			try {
				for (UserBatchUser user : batchUsers) {
					Long userId = null;
					try {
						if(user.getRealName().length()<20){
							userId = userClient.batchRegisterWithChannel(user.getRealName(), 
									user.getCityId(),
									user.getVisitResult(), 
									String.valueOf((Long) searchConds.get("channelId")));
						}

						UserBatchUser _u = userBatchUserService
								.getBatchUserByPrimaryKey(user.getBatchUserId());
						if(_u != null){
							if (null == userId) {
								_u.setRegisterStatus(Constant.REGISTER_TYPE.REGISTER_FAILURE
										.name());
							} else {
								k++;
								_u.setRegisterStatus(Constant.REGISTER_TYPE.REGISTER_SUCCESS
										.name());
								_u.setUserId(userId);
							}
							userBatchUserService.update(_u);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				userBatchRegister.setRegisterNumber(batchUsers.size());
				userBatchRegister.setSuccessNumber(k);
				userBatchRegister.setFailNumber(batchUsers.size()-k);
				userBatchRegisterService.update(userBatchRegister);
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
}