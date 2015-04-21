package com.lvmama.eplace.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pass.UserRelateMenu;

@SuppressWarnings("unchecked")
public class UserRelateMenuDAO extends BaseIbatisDAO {

	public void deleteUserRelateMenu(Long passportUserId) {
		 super.delete(
				"USER_RELATE_MENU.deleteByPrimaryKey", passportUserId);
	}

	
	public Long addUserRelateMenu(UserRelateMenu userRelateMenu) {
		Long supplyId = (Long) super.insert(
				"USER_RELATE_MENU.insert", userRelateMenu);
		return supplyId;
	}

	public Long findUserRelateMenuByMapCount(Map param) {
		Long count = 0l;
		count = (Long) super.queryForObject(
				"USER_RELATE_MENU.selectFullCount", param);
		return count;
	}

	public List<UserRelateMenu> findUserRelateMenuByMap(Map param) {
		if (param.get("_startRow") == null) {
			param.put("_startRow", 0);
		}
		if (param.get("_endRow") == null) {
			param.put("_endRow", 20);
		}
		return super.queryForList(
				"USER_RELATE_MENU.selectFull", param);
	}
}