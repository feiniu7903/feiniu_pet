package com.lvmama.eplace.web.lvmama.supplyUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPortUser;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.passport.utils.ZkMessage;
import com.lvmama.passport.utils.ZkMsgCallBack;

/**
 * 通关平台用户查询.
 * 
 * @author huangli
 * 
 */
@SuppressWarnings("unchecked")
public class PassPortUserQueryAction extends ZkBaseAction {
	/**
	 * 通关平台用户查询服务.
	 */
	private EPlaceService eplaceService;
	/**
	 * 综合查询Map.
	 */
	private Map serachMap = new HashMap();
	/**
	 * 通关平台用户集合.
	 */
	private List<PassPortUser> passPortUserList;
	/**
	 * 用户类型(驴登陆用户,通过平台用户,E景通供应商用户).
	 */
	private String userType="";
	/**
	 * 用户综合查询.
	 */
	public void search() {
		if (serachMap.get("userId") != null
				&& !StringUtil
						.isEmptyString(serachMap.get("userId").toString())) {
			serachMap.put("userId", serachMap.get("userId").toString());
		}
		if (serachMap.get("name") != null
				&& !StringUtil.isEmptyString(serachMap.get("name").toString())) {
			serachMap.put("name", serachMap.get("name").toString());
		}
		serachMap.put("userType",userType);
		Map map = initialPageInfoByMap(eplaceService
				.findPassPortUserByMapCount(serachMap), serachMap);
		int skipResults = 0;
		int maxResults = 20;
		if (map.get("_startRow") != null) {
			skipResults = Integer.parseInt(map.get("_startRow").toString());
		}
		if (map.get("_endRow") != null) {
			maxResults = Integer.parseInt(map.get("_endRow").toString());
		}
		serachMap.put("_startRow", skipResults);
		serachMap.put("_endRow", maxResults);
		passPortUserList = eplaceService.findPassPortUserByMap(serachMap);
	}

	public void delPassPortUser(final Long passPortUserId)
			throws InterruptedException {
		ZkMessage.showQuestion("您确认要删除此用户信息吗", new ZkMsgCallBack() {
			public void execute() {
				PassPortUser user = eplaceService
						.getPassPortUserByPk(passPortUserId + "");
				user.setStatus("N");
				eplaceService.updatePassPortUser(user, null);
				refreshComponent("search");
			}
		}, new ZkMsgCallBack() {
			public void execute() {

			}
		});

	}

	public Map getSerachMap() {
		return serachMap;
	}

	public void setSerachMap(Map serachMap) {
		this.serachMap = serachMap;
	}
 
	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}

	public List<PassPortUser> getPassPortUserList() {
		return passPortUserList;
	}

	public void setPassPortUserList(List<PassPortUser> passPortUserList) {
		this.passPortUserList = passPortUserList;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
