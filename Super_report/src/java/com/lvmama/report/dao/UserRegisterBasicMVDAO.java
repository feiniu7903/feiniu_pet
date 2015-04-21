package com.lvmama.report.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.UserRegisterBasicMV;

public class UserRegisterBasicMVDAO extends BaseIbatisDAO {
	
	public List<UserRegisterBasicMV> queryUserRegisterBasicMV(Map param){
		List<UserRegisterBasicMV> result = new ArrayList<UserRegisterBasicMV>();
		result = super.queryForList("USER_REGISTER_BASIC_MV.queryUserRegisterBasicMV", param);
		return result;
	}
	
	public List<UserRegisterBasicMV> queryUserOverviewRegisterBasicMV(Map param,boolean isForReportExport){
		List<UserRegisterBasicMV> result = new ArrayList<UserRegisterBasicMV>();
		result = super.queryForList("USER_REGISTER_BASIC_MV.queryUserOverviewRegisterBasicMV", param,isForReportExport);
		return result;
	}
	
	public Long countUserRegisterBasicMV(Map param){
		return (Long)super.queryForObject("USER_REGISTER_BASIC_MV.countUserRegisterBasicMV", param);
	}
	
	public Long countUserOverviewRegisterBasicMV(Map param){
		return (Long)super.queryForObject("USER_REGISTER_BASIC_MV.countUserOverviewRegisterBasicMV", param);
	}
	
	public Long sumUserUpdate(Map param){
		return (Long)super.queryForObject("USER_REGISTER_BASIC_MV.sumUserUpdate", param);
	}
	
	public Long sumPayUserUpdate(Map param){
		return (Long)super.queryForObject("USER_REGISTER_BASIC_MV.sumPayUserUpdate", param);
	}
	
	public Long sumPay2UserUpdate(Map param){
		return (Long)super.queryForObject("USER_REGISTER_BASIC_MV.sumPay2UserUpdate", param);
	}
	
	public Long allUserUpdate(Map param){
		return (Long)super.queryForObject("USER_REGISTER_BASIC_MV.allUserUpdate", param);
	}
	
	public Long allPayUserUpdate(Map param){
		return (Long)super.queryForObject("USER_REGISTER_BASIC_MV.allPayUserUpdate", param);
	}
	
	public Long allPay2UserUpdate(Map param){
		return (Long)super.queryForObject("USER_REGISTER_BASIC_MV.allPay2UserUpdate", param);
	}
	
	public Long sumSubChannel(Map param){
		return (Long)super.queryForObject("USER_REGISTER_BASIC_MV.sumSubChannel", param);
	}

}
