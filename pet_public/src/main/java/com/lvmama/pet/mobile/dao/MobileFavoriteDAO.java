package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileFavorite;

/**
 * 我的收藏. 
 * @author qinzubo
 *
 */
public class MobileFavoriteDAO extends BaseIbatisDAO {

    public MobileFavoriteDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long id) {
        MobileFavorite key = new MobileFavorite();
        key.setId(id);
        int rows = super.delete("MOBILE_FAVORITE.deleteByPrimaryKey", key);
        return rows;
    }
    
    public int deleteByPrimaryKey(Long id,Long userId) {
        MobileFavorite m = new MobileFavorite();
        m.setId(id);
        m.setUserId(userId);
        int rows = super.delete("MOBILE_FAVORITE.deleteByKeyAndUserId", m);
        return rows;
    }
    
    public int deleteByObjectIdAndUserId(Long id,Long userId) {
        MobileFavorite m = new MobileFavorite();
        m.setObjectId(id);
        m.setUserId(userId);
        int rows = super.delete("MOBILE_FAVORITE.deleteByObjectIdAndUserId", m);
        return rows;
    }
    
    

    public MobileFavorite insert(MobileFavorite record) {
        super.insert("MOBILE_FAVORITE.insert", record);
        return record;
    }

    public MobileFavorite insertSelective(MobileFavorite record) {
        super.insert("MOBILE_FAVORITE.insertSelective", record);
        return record;
    }

    public MobileFavorite selectByPrimaryKey(Long id) {
        MobileFavorite key = new MobileFavorite();
        key.setId(id);
        MobileFavorite record = (MobileFavorite) super.queryForObject("MOBILE_FAVORITE.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobileFavorite record) {
        int rows = super.update("MOBILE_FAVORITE.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobileFavorite record) {
        int rows = super.update("MOBILE_FAVORITE.updateByPrimaryKey", record);
        return rows;
    }
    
    /**
     * 查询我的收藏列表 . 
     * @param params
     * @return
     */
    public List<MobileFavorite> getMobileFavoriteListByPrarms(Map<String,Object> params){
    	return (List<MobileFavorite>)super.queryForList("MOBILE_FAVORITE.queryFavoriterList", params);
    	
    }
    
    /**
     * 查询我的收藏总记录数. 
     * @param param
     * @return
     */
    public Long countMobileFavorite(Map<String,Object> param){
    	return (Long) super.queryForObject("MOBILE_FAVORITE.countFavoriterList", param);
    }

	public List<MobileFavorite> getMobileFavoritePlaceListByPrarmsForHome(
			Map<String, Object> param) {
		return (List<MobileFavorite>)super.queryForList("MOBILE_FAVORITE.queryFavoriterPlaceListForHome", param);
	}

	public List<MobileFavorite> getMobileFavoriteGuideListByPrarmsForHome(
			Map<String, Object> param) {
		return (List<MobileFavorite>)super.queryForList("MOBILE_FAVORITE.queryFavoriterGuideListForHome", param);

	}

	public List<MobileFavorite> getMobileFavoriteProductListByPrarmsForHome(
			Map<String, Object> param) {
		return (List<MobileFavorite>)super.queryForList("MOBILE_FAVORITE.queryFavoriterProductListForHome", param);

	}
}