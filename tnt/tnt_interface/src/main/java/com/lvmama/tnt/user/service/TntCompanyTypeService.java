package com.lvmama.tnt.user.service;

import java.util.List;
import java.util.Map;

import com.lvmama.tnt.user.po.TntCompanyType;

/**
 * 分销商类型
 * 
 * @author gaoxin
 * 
 */
public interface TntCompanyTypeService {

	/**
	 * 
	 * @param tntCompanyType
	 * @return
	 */
	public boolean insert(TntCompanyType tntCompanyType);

	/**
	 * 
	 * @param TntUser
	 * @return
	 */
	public boolean update(TntCompanyType tntCompanyType);

	/**
	 * 查询获取，key为companyTypeId，value为companyTypeName的map
	 * 
	 * @param tntCompanyType
	 * @return
	 */
	public Map<String, String> map(TntCompanyType tntCompanyType);

	/**
	 * 返回 companyTypeId:companyTypeName的list
	 * 
	 * @param tntCompanyType
	 * @return
	 */
	public List<String> getTypeIdANdNameList(TntCompanyType tntCompanyType);

	/**
	 * 根据条件查询分销商类型
	 * 
	 * @param tntCompanyType
	 * @return
	 */
	public TntCompanyType get(TntCompanyType tntCompanyType);

	/**
	 * 根据typeId获取分销商类型
	 * 
	 * @param companyTypeId
	 * @return
	 */
	public TntCompanyType get(Long companyTypeId);

	/**
	 * 根据typeid删除分销商类型
	 * 
	 * 如果该类型有分销商的话提示不能删除
	 * 
	 * @param companyTypeId
	 * @return
	 */
	public boolean delete(Long companyTypeId);

	/**
	 * 根据条件查询条目数
	 * 
	 * @param tntCompanyType
	 * @return
	 */
	public int count(TntCompanyType tntCompanyType);
	
	public List<TntCompanyType> query(TntCompanyType tntCompanyType);
	
	public Long getPersonType();

}
