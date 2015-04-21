package com.lvmama.pet.prod.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdBlackList;

public class ProdBlackListDao extends BaseIbatisDAO{
	
	public void insertBlackList(List<ProdBlackList> prodBlackLists) {
		super.batchInsert("PROD_BLACK_LIST.insert", prodBlackLists);
	}

	public void updateBlackList(ProdBlackList prodBlackList) {
		super.update("PROD_BLACK_LIST.update",prodBlackList);
	}

	public void deleteBlackList(ProdBlackList prodBlackList) {
		super.delete("PROD_BLACK_LIST.delete", prodBlackList.getBlackId());
	}

	public List<ProdBlackList> queryBlackListByParam(Map<String, Object> param) {
		if(param.get("_startRow") != null && param.get("pageSize") != null){
			int startRow = Integer.parseInt(param.get("_startRow").toString())-1;
			int pageSize = Integer.parseInt(param.get("pageSize").toString());
			return super.queryForList("PROD_BLACK_LIST.queryBlackListByParam",param,startRow,pageSize);		
		}else{
			return super.queryForList("PROD_BLACK_LIST.queryBlackListByParam",param);
		}
	}
	
	public List<ProdBlackList> queryBlackListByBlack(ProdBlackList prodBlackList){
		return super.queryForList("PROD_BLACK_LIST.queryBlackListByBlack",prodBlackList);
	}
	
	public Integer selectRowCount(Map<String, Object> param){
		Integer count = 0;
		count = (Integer) super.queryForObject("PROD_BLACK_LIST.selectRowCount",param);
		return count;
	}
}
