package com.lvmama.com.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.vo.ComboxItem;

public class ComCodesetDAO extends BaseIbatisDAO{
	
	public List<CodeItem> getCodeset(String codeset) {
		List<CodeItem> queryForList = super.queryForList("COM_CODESET.selectCodeBySet",codeset); //"COM_FAX_STRATEGY_ALL"
		return queryForList;
	}
	
	public List<CodeItem> selectCodeItemBySet(Map map) {
		return super.queryForList("COM_CODESET.selectCodeItemBySet",map);
	}
	
	public List<CodeItem> getCodesetChecked(Map map) {
		return super.queryForList("COM_CODESET.selectCodeChecked",map);
	}

	public List<CodeItem> getProvinceList() {
		return super.queryForList("COM_CODESET.selectAllProvince");
	}
	
	public void insertPamentGetWay(CodeItem codeItem) {
		super.insert("COM_CODESET.insertPamentGetWay", codeItem);
	}
	
}
