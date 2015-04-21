package com.lvmama.report.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;

public class UserRegisterBasicDAO extends BaseIbatisDAO {
	public Long countUserRegisterBasic(Map param) {
		return (Long) super.queryForObject("USER_REGISTER_BASIC.countUserRegisterBasic", param);
	}
	
	public List queryUserRegisterBasic(Map param,boolean isForReportExport) {
		return super.queryForList("USER_REGISTER_BASIC.queryUserRegisterBasic", param,isForReportExport);
	}
	
	public Long countNewUser(Map param) {
		return (Long) super.queryForObject("USER_REGISTER_BASIC.countNewUser", param);
	}
}
