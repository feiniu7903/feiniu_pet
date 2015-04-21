package com.lvmama.comm.pet.service.mobile;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.mobile.MobileFavorite;

/**
 * 驴途移动端 from 3.0 - 我的收藏.
 * @author qinzubo
 *
 */
public interface MobileFavoriteService {

	/**
	 * 新增我的收藏.
	 * @param mf  推荐我的收藏对象
	 * @return  持久化后的我的收藏对象
	 */
	MobileFavorite insertMobileFavorite(MobileFavorite mf);
	
	/**
	 * 更新我的收藏.
	 * @param mf  要更新的对象 
	 * @return   更新后的对象
	 */
	boolean updateMobileFavorite(MobileFavorite mf);
	
	/**
	 * 根据主键查找对象.
	 * @param id  主键
	 * @return  对象
	 */
	MobileFavorite selectMobileFavoriteById(Long id);
	
	/**
     * 查询列表 
     * 如果参数 isPaging 不为null ，表示分页查询. 
     * @param param 
     * @return
     */
	List<MobileFavorite> queryMobileFavoriteList(Map<String,Object> param);
	
	/**
	 * 符合条件的数据量 
	 * @param param
	 * @return long 
	 */
	Long countMobileFavoriteList(Map<String,Object> param);
	
	/**
	 * 删除
	 * @param id
	 * @return 删除的条数
	 */
	int deleteMobileFavoriteById(Long id);
	
	/**
	 * 删除
	 * @param id
	 * @param useId
	 * @return 删除的条数
	 */
	int deleteMobileFavorite(Long id,Long userId);

	
	/**
	 * 删除
	 * @param objectId
	 * @param useId
	 * @return 删除的条数
	 */
	int deleteByObjectIdAndUserId(Long id,Long userId);

	/**
	 * 景点 (网站首页——我的收藏)
	 * @param param
	 * @return
	 */
	List<MobileFavorite> queryMobileFavoritePlaceListForHome(
			Map<String, Object> param);

	/**
	 * 攻略(网站首页——我的收藏)
	 * @param param
	 * @return
	 */
	List<MobileFavorite> queryMobileFavoriteGuideListForHome(
			Map<String, Object> param);

	/**
	 * 产品(网站首页——我的收藏)
	 * @param param
	 * @return
	 */
	List<MobileFavorite> queryMobileFavoriteProductListForHome(
			Map<String, Object> param);
	
	

}
