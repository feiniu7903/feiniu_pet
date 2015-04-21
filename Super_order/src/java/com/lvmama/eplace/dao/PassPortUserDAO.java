package com.lvmama.eplace.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pass.PassPortUser;

@SuppressWarnings("unchecked")
public class PassPortUserDAO extends BaseIbatisDAO {
	public PassPortUser getPassPortUserByUserName(Map map) {
		PassPortUser passportUser = (PassPortUser) super.queryForObject(
						"PASS_PORT_USER.selectByUserIDAndPassword", map);
		return passportUser;
	}

	public PassPortUser getPassPortUserByPk(String passPortUserId) {
		Map param = new HashMap();
		param.put("passPortUserId", passPortUserId);
		param.put("_startRow", 0);
		param.put("_endRow", 1);
		PassPortUser passPortUser = new PassPortUser();
		List list = super.queryForList(
				"PASS_PORT_USER.selectFull", param);
		if (list.size() > 0) {
			passPortUser = (PassPortUser) list.get(0);
		}
		return passPortUser;
	}

	public void updatePassPortUser(PassPortUser passPortUser) {
		super.update(
				"PASS_PORT_USER.updateByPrimaryKey", passPortUser);
	}

	public Long addPassPortUser(PassPortUser passPortUser) {
		Long supplyId = (Long) super.insert(
				"PASS_PORT_USER.insert", passPortUser);
		return supplyId;
	}

	public Long findPassPortUserByMapCount(Map param) {
		Long count = 0l;
		count = (Long) super.queryForObject(
				"PASS_PORT_USER.selectFullCount", param);
		return count;
	}

	public List<PassPortUser> findPassPortUserByMap(Map param) {
		if (param.get("_startRow") == null) {
			param.put("_startRow", 0);
		}
		if (param.get("_endRow") == null) {
			param.put("_endRow", 20);
		}
		return super.queryForList(
				"PASS_PORT_USER.selectFull", param);
	}
}