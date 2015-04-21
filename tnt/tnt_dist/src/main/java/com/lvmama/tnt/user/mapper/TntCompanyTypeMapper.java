package com.lvmama.tnt.user.mapper;

import java.util.List;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntCompanyType;

public interface TntCompanyTypeMapper {

	public int insert(TntCompanyType entity);

	public List<TntCompanyType> selectList(TntCompanyType entity);

	public TntCompanyType selectOne(TntCompanyType entity);

	public List<TntCompanyType> fetchPage(Page<TntCompanyType> page);

	public int findCount(TntCompanyType entity);

	public int update(TntCompanyType entity);

	public int delete(Long companyTypeId);

	public TntCompanyType getById(Long companyTypeId);

}
