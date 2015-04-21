package com.lvmama.pet.pub.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComKeJetAds;

public class ComKeJetAdsDAO extends BaseIbatisDAO {
	@SuppressWarnings("unchecked")
	public List<ComKeJetAds> queryAll() {
		return super.queryForList("COM_KEJET_ADS.queryAll");
	}

}
