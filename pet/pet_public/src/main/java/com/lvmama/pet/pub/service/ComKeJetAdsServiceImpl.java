package com.lvmama.pet.pub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.pub.ComKeJetAds;
import com.lvmama.comm.pet.service.pub.ComKeJetAdsService;
import com.lvmama.pet.pub.dao.ComKeJetAdsDAO;

public class ComKeJetAdsServiceImpl implements ComKeJetAdsService {
	@Autowired
	private ComKeJetAdsDAO comKeJetAdsDAO;
	
	@Override
	public List<ComKeJetAds> queryAll() {
		return comKeJetAdsDAO.queryAll();
	}
	
}
