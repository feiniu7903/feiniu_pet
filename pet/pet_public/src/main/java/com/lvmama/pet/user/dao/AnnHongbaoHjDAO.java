package com.lvmama.pet.user.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.user.AnnHongbaoHj;

public class AnnHongbaoHjDAO extends BaseIbatisDAO {

	public void saveAnnHongbaoHJ(AnnHongbaoHj annHongbaoHj) {
		super.insert("ANN_HONGBAO_HJ.insert", annHongbaoHj);
	}
	
	@SuppressWarnings("unchecked")
	public List<AnnHongbaoHj> query(Map<String, Object> param){
		return super.queryForList("ANN_HONGBAO_HJ.selectByParam",param);
	}
	
	public Long selectSumMoneyByUserId(Map<String,Object> param){
		return (Long) super.queryForObject("ANN_HONGBAO_HJ.selectSumMoneyByUserId",param);
	}
	
}
