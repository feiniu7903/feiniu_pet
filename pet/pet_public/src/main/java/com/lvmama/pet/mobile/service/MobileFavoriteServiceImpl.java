package com.lvmama.pet.mobile.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.mobile.MobileFavorite;
import com.lvmama.comm.pet.service.mobile.MobileFavoriteService;
import com.lvmama.pet.mobile.dao.MobileFavoriteDAO;

/**
 * 驴途移动端 from 3.0 我的收藏. 
 * @author qinzubo
 *
 */
public class MobileFavoriteServiceImpl implements MobileFavoriteService {
	
	@Autowired
	MobileFavoriteDAO mobileFavoriteDAO;
	
	@Override
	public MobileFavorite insertMobileFavorite(MobileFavorite record) {
		return mobileFavoriteDAO.insert(record);
	}

	@Override
	public boolean updateMobileFavorite(MobileFavorite mf) {
		int rows =  mobileFavoriteDAO.updateByPrimaryKey(mf);
		if(rows > 0) {
			return true;
		}
		return false;
	}

	@Override
	public MobileFavorite selectMobileFavoriteById(Long id) {
		return mobileFavoriteDAO.selectByPrimaryKey(id);
	}

	@Override
	public List<MobileFavorite> queryMobileFavoriteList( Map<String, Object> param) {
		return mobileFavoriteDAO.getMobileFavoriteListByPrarms(param);
	}

	@Override
	public Long countMobileFavoriteList(Map<String, Object> param) {
		return mobileFavoriteDAO.countMobileFavorite(param);
	}

	@Override
	public int deleteMobileFavoriteById(Long id) {
		return mobileFavoriteDAO.deleteByPrimaryKey(id);
	}

	
	public void setMobileFavoriteDAO(MobileFavoriteDAO mobileFavoriteDAO) {
		this.mobileFavoriteDAO = mobileFavoriteDAO;
	}

	@Override
	public int deleteMobileFavorite(Long id, Long userId) {
		return mobileFavoriteDAO.deleteByPrimaryKey(id, userId);
	}


	@Override
	public int deleteByObjectIdAndUserId(Long id, Long userId) {
		return mobileFavoriteDAO.deleteByObjectIdAndUserId(id, userId);
	}



	@Override
	public List<MobileFavorite> queryMobileFavoritePlaceListForHome(
			Map<String, Object> param) {
		return mobileFavoriteDAO.getMobileFavoritePlaceListByPrarmsForHome(param);
	}

	@Override
	public List<MobileFavorite> queryMobileFavoriteGuideListForHome(
			Map<String, Object> param) {
		return mobileFavoriteDAO.getMobileFavoriteGuideListByPrarmsForHome(param);
	}

	@Override
	public List<MobileFavorite> queryMobileFavoriteProductListForHome(
			Map<String, Object> param) {
		return mobileFavoriteDAO.getMobileFavoriteProductListByPrarmsForHome(param);
	}


}
