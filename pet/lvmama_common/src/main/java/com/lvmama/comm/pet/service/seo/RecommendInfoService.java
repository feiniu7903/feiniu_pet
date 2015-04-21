package com.lvmama.comm.pet.service.seo;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.prod.ProdContainerFromPlace;
import com.lvmama.comm.pet.po.seo.RecommendBlock;
import com.lvmama.comm.pet.po.seo.RecommendInfo;

public interface RecommendInfoService {
	/**
	 * 获取推荐信息ById
	 * @param recommendInfoId
	 * @return
	 */
	public RecommendInfo getRecommendInfoById(Long recommendInfoId);
	/**
	 * 更新推荐信息
	 * @param recommendInfo
	 */
	public void updateRecommendInfo(RecommendInfo recommendInfo);
	/**
	 * 添加推荐信息
	 * @param recommendInfo
	 */
	public void insertRecommendInfo(RecommendInfo recommendInfo);
	/**
	 * 删除推荐信息
	 * @param recommendInfoId
	 */
	public void deleteRecommendInfoById(Long recommendInfoId);
	/**
	 * 根据参数获取推荐信息
	 * @param param
	 * @return
	 */
	public List<RecommendInfo> queryRecommendInfoByParam(Map<String,Object> param);
	/**
	 * 根据参数获取推荐信息数量
	 * @param param
	 * @return
	 */
	public Long countRecommendInfoByParam(Map<String,Object> param);
	/**
	 * 批量保存seq
	 * @param recommendInfoId
	 * @param recommendInfoSeq
	 */
	public void saveRecommendInfoSeq(Long[] recommendInfoId,Long[] recommendInfoSeq);

	/**
     * 把某一个推荐块下的所有产品复制到另一个块下.
     */
	public void saveCopyRecommendInfos(Long srcBlockId, Long destBlockId) throws Exception;
	/**
	 * 获取推荐信息查询大类模块下面所有的
	 * @param parentBlockId
	 * @param pageChannel
	 * @return
	 */
	public Map<String, List<RecommendInfo>> getRecommendInfoByParentBlockIdAndPageChannel(Long parentBlockId, String pageChannel);
	/**
	 * 通过datCode及pageChannel获取推荐信息（目前目的地页面使用中）
	 * @param dataCode
	 * @param pageChannel
	 * @return
	 */
	public Map<String, List<RecommendInfo>> getRecommendInfoByDataCodeAndPageChannel(Long dataCode, String pageChannel);
	/**
	 * 查询推荐信息
	 * @param recommendBlockId
	 * @param objectId
	 * @return
	 */
	public List<RecommendInfo> getRecommendInfoByBlockId(Long recommendBlockId, String objectId);
	/**
	 * 根据参数获取推荐的map对象
	 * @param containerCode
	 * @param commonBlockId
	 * @param fromPlaceId
	 * @param channelPage
	 * @return
	 */
	public Map<String, List<RecommendInfo>> getRecommendInfoMap(String containerCode, Long commonBlockId, Long fromPlaceId, String channelPage);
	/**
	 * 通过容器code和出发地id获得包含的板块列表
	 * @return
	 */
	List<ProdContainerFromPlace> getBlocksByPlaceIdWitheContainerCode(String containerCode,Long fromPlaceId );
	
	
	List<RecommendInfo> queryRecommendInfoByParam(
			Map<String, Object> param, Long blockId);
	
	List<RecommendInfo> queryRecommendInfoByParamAndDataCodes(Map<String, Object> param,Long blockId);
}
