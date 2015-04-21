package com.lvmama.operate.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.CodeItem;

public class ComCodesetDAO extends BaseIbatisDAO{
	
	public List<CodeItem> getCodeset(String codeset) {
		List<CodeItem> queryForList = super.queryForList("COM_CODESET.selectCodeBySet",codeset); //"COM_FAX_STRATEGY_ALL"
		return queryForList;
	}
	
	public List<CodeItem> getCodesetChecked(Map map) {
		return super.queryForList("COM_CODESET.selectCodeChecked",map);
	}

	public List<CodeItem> getProvinceList() {
		return super.queryForList("COM_CODESET.selectAllProvince");
	}
	
	
	
}
