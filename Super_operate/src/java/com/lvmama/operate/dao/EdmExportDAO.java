package com.lvmama.operate.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.edm.EdmExport;


/**
 * @author yangbin
 *
 */
public class EdmExportDAO extends BaseIbatisDAO{

	public Long insert(EdmExport edmExport)
	{
		Long pk=(Long)super.insert("EDM_EXPORT.insert", edmExport);
		return pk;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<EdmExport> selectListByDate(Map parameters)
	{
		return super.queryForListForReport("EDM_EXPORT.selectAllByDate", parameters);
	}
	
	@SuppressWarnings("unchecked")
	public long selectCountByDate(Map parameters)
	{
		return (Long)super.queryForObject("EDM_EXPORT.selectAllByDateCount", parameters);
	}
}
