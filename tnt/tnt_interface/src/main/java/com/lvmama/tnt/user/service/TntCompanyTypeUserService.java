package com.lvmama.tnt.user.service;

import java.util.List;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntCompanyType;

public interface TntCompanyTypeUserService {

	/**
	 * 根据条件查询分销商类型
	 * 
	 * @param tntCompanyType
	 * @return
	 */
	public List<TntCompanyType> queryWithUserTotal(Page<TntCompanyType> page);

	/**
	 * 判断是否包含分销商
	 * 
	 * @param companyTypeId
	 * @return
	 */
	public boolean isContainUser(Long companyTypeId);
}
