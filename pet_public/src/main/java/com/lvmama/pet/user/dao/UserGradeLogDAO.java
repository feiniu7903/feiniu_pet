package com.lvmama.pet.user.dao;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.user.UserGradeLog;


public class UserGradeLogDAO extends BaseIbatisDAO {
	/**
	 * 查询用户的Log
	 * 
	 * @param UserId
	 *            用户Id
	 * @return 用户日志的列表
	 */
	@SuppressWarnings("unchecked")
	public final List<UserGradeLog> queryLogByUserId(final Serializable userId) {
		return super.queryForList(
				"USR_GRADE_LOG.queryUserLogs", userId);
	}

	/**
	 * 插入日志表中
	 */
	public void insert(final UserGradeLog userGradeLog) {
		super.insert("USR_GRADE_LOG.insert", userGradeLog);
	}
}
