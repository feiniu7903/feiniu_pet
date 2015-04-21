package com.lvmama.pet.seo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.lvmama.comm.pet.po.prod.ProdContainerFromPlace;
import com.lvmama.comm.pet.po.seo.RecommendBlock;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.pet.prod.dao.ProdContainerFromPlaceDAO;
import com.lvmama.pet.seo.dao.RecommendBlockDAO;
import com.lvmama.pet.seo.dao.RecommendInfoDAO;

public class RecommendInfoServiceImpl implements RecommendInfoService {
	private RecommendBlockDAO recommendBlockDAO;
	private RecommendInfoDAO recommendInfoDAO;
	private ProdContainerFromPlaceDAO prodContainerFromPlaceDAO;

	@Override
	public Map<String, List<RecommendInfo>> getRecommendInfoByParentBlockIdAndPageChannel(Long parentBlockId, String pageChannel) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentRecommendBlockId", parentBlockId);
		param.put("pageChannel", pageChannel);
		List<RecommendBlock> recommendBlockList = recommendBlockDAO.queryRecommendBlockByParam(param);
		Map<String, List<RecommendInfo>> reMap = new HashMap<String, List<RecommendInfo>>();
		
		Map<String,Object> recommendInfoParam = null;
		for (RecommendBlock recommendBlock : recommendBlockList) {
			String key = recommendBlock.getPageChannel() + "_" + recommendBlock.getDataCode();
			recommendInfoParam = new HashMap<String, Object>();
			recommendInfoParam.put("recommendBlockId", recommendBlock.getRecommendBlockId());
			if(recommendBlock.getItemNumberLimit()!=null&&recommendBlock.getItemNumberLimit()>0){
				recommendInfoParam.put("endRows", recommendBlock.getItemNumberLimit());
			}
			// 查询关联了上线可售的产品
			recommendInfoParam.put("status", "true");
			reMap.put(key,this.queryRecommendInfoByRecommendBlock(recommendInfoParam,recommendBlock));
		}
		return reMap;
	}
	
	@Override
	public List<RecommendInfo> queryRecommendInfoByParam(Map<String, Object> param) {
		if(param.get("recommendBlockId")!=null && !"".equals(param.get("recommendBlockId"))){
			RecommendBlock rb=recommendBlockDAO.getRecommendBlockById((Long)param.get("recommendBlockId"));
			if(rb!=null){
				return this.queryRecommendInfoByRecommendBlock(param,rb);
			}
		}
		return recommendInfoDAO.queryRecommendInfoByParam(param);
	}
	
	@Override
	public List<RecommendInfo> queryRecommendInfoByParam(Map<String, Object> param,Long blockId) {
		RecommendBlock rb=recommendBlockDAO.getRecommendBlockById(blockId);
		return this.queryRecommendInfoByRecommendBlock(param, rb);
	}
	
	/**
	 * 根据推荐模块的类型查询
	 * @param recommendBlock
	 * @return
	 */
	private List<RecommendInfo> queryRecommendInfoByRecommendBlock(Map<String, Object> param,RecommendBlock recommendBlock) {
		List<RecommendInfo> recommendInfoList = null;
		if("3".equals(recommendBlock.getModeType())){
			//产品基本数据及价格取值
			recommendInfoList = recommendInfoDAO.queryRecommendInfoAndProductByParam(param);
		}else if("1".equals(recommendBlock.getModeType()) || "2".equals(recommendBlock.getModeType()) || "5".equals(recommendBlock.getModeType())||"6".equals(recommendBlock.getModeType())){
			if("5".equals(recommendBlock.getModeType())){
				param.put("isHotel",true);
			}
			//目的地，景区，酒店的基本数据及最低价格取值
			recommendInfoList = recommendInfoDAO.queryRecommendInfoAndPlaceByParam(param);
		}else{
			//推荐模块中的其他类型的使用推荐中的价格
			 recommendInfoList = recommendInfoDAO.queryRecommendInfoByParam(param);
		}
		if (recommendInfoList != null) {
			return recommendInfoList;
		}
		return new ArrayList<RecommendInfo>();
	}
	
	@Override
	public List<RecommendInfo> queryRecommendInfoByParamAndDataCodes(Map<String, Object> param,Long blockId) {
		RecommendBlock rb=recommendBlockDAO.getRecommendBlockById(blockId);
		return this.queryRecommendInfoByRecommendBlockAndDataCodes(param, rb);
	}
	
	/**
	 * 根据推荐模块的类型查询
	 * @param recommendBlock
	 * @return
	 */
	private List<RecommendInfo> queryRecommendInfoByRecommendBlockAndDataCodes(Map<String, Object> param,RecommendBlock recommendBlock) {
		List<RecommendInfo> recommendInfoList = null;
		if("3".equals(recommendBlock.getModeType())){
			//产品基本数据及价格取值
			recommendInfoList = recommendInfoDAO.queryRecommendInfoAndProductByDataCode(param);
		}else if("1".equals(recommendBlock.getModeType()) || "2".equals(recommendBlock.getModeType()) || "5".equals(recommendBlock.getModeType())||"6".equals(recommendBlock.getModeType())){
			if("5".equals(recommendBlock.getModeType())){
				param.put("isHotel",true);
			}
			//目的地，景区，酒店的基本数据及最低价格取值
			recommendInfoList = recommendInfoDAO.queryRecommendInfoAndPlaceByDataCode(param);
		}else{
			//推荐模块中的其他类型的使用推荐中的价格
			 recommendInfoList = recommendInfoDAO.queryRecommendInfoByDataCode(param);
		}
		if (recommendInfoList != null) {
			return recommendInfoList;
		}
		return new ArrayList<RecommendInfo>();
	}
	
	@Override
	public Long countRecommendInfoByParam(Map<String, Object> param) {
		return recommendInfoDAO.countRecommendInfoByParam(param);
	}

	public Map<String, List<RecommendInfo>> getRecommendInfoByDataCodeAndPageChannel(Long dataCode, String pageChannel) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dataCode", String.valueOf(dataCode));
		param.put("pageChannel", pageChannel);
		param.put("levels", 1);
		RecommendBlock level1Block = null;
		List<RecommendBlock> recommendBlockList = recommendBlockDAO.queryRecommendBlockByParam(param);
		if (recommendBlockList != null && recommendBlockList.size() > 0) {
			level1Block = recommendBlockList.get(0);
		} else {
			return new HashMap<String, List<RecommendInfo>>();
		}
		return this.getRecommendInfoByParentBlockIdAndPageChannel(level1Block.getRecommendBlockId(), pageChannel);
	}

	@Override
	public RecommendInfo getRecommendInfoById(Long recommendInfoId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("recommendInfoId", recommendInfoId);
		List<RecommendInfo> recommendInfoList = recommendInfoDAO.queryRecommendInfoByParam(param);
		if(recommendInfoList!=null && recommendInfoList.size()>0){
			return recommendInfoList.get(0);
		}
		return new RecommendInfo();
	}

	@Override
	public List<RecommendInfo> getRecommendInfoByBlockId(Long recommendBlockId,String objectId) {
		String key="getRecommendInfoByBlockId_"+recommendBlockId+"_"+objectId;
		List<RecommendInfo> recommendInfoList=(List<RecommendInfo>) MemcachedUtil.getInstance().get(key);
		if(recommendInfoList==null){
			RecommendBlock recommendBlock = recommendBlockDAO.getRecommendBlockById(recommendBlockId);
			if (null != recommendBlock) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("recommendBlockId", recommendBlockId);
				param.put("recommObjectId", objectId);
				if (null != recommendBlock.getItemNumberLimit()&& recommendBlock.getItemNumberLimit() >0) {// 显示条数限制
					param.put("startRows", 1);
					param.put("endRows", recommendBlock.getItemNumberLimit());
				} 
				recommendInfoList = this.queryRecommendInfoByRecommendBlock(param,recommendBlock);
			}
			MemcachedUtil.getInstance().set(key, MemcachedUtil.TWO_HOUR,recommendInfoList);
		} 
		return recommendInfoList;
	}

	@Override
	public void saveCopyRecommendInfos(Long srcBlockId, Long parentBlockId)throws Exception {
		RecommendBlock recommendBlock = (RecommendBlock) recommendBlockDAO.getRecommendBlockById(parentBlockId);
		if (recommendBlock != null) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("recommendBlockId", srcBlockId);
			List<RecommendInfo> origs = (List<RecommendInfo>) recommendInfoDAO.queryRecommendInfoByParam(param);
			for (RecommendInfo orig : origs) {
				RecommendInfo copyRecommendInfo = new RecommendInfo();
				BeanUtils.copyProperties(orig, copyRecommendInfo);
				copyRecommendInfo.setRecommendBlockId(recommendBlock.getRecommendBlockId());
				copyRecommendInfo.setDataCode(recommendBlock.getDataCode());
				if (recommendBlock.getParentRecommendBlockId() != null) {
					copyRecommendInfo.setParentRecommendBlockId(recommendBlock.getParentRecommendBlockId());
				}
				copyRecommendInfo.setSeq(-1L);
				recommendInfoDAO.insertRecommendInfo(copyRecommendInfo);
			}
		}
	}

	@Override
	public void saveRecommendInfoSeq(Long[] recommendInfoId,Long[] recommendInfoSeq) {
		RecommendInfo recommendInfo = null;
		for (int i = 0; i < recommendInfoId.length; i++) {
			recommendInfo = new RecommendInfo();
			recommendInfo.setRecommendInfoId(recommendInfoId[i]);
			recommendInfo.setSeq(recommendInfoSeq[i]);
			this.recommendInfoDAO.updateRecommendInfo(recommendInfo);
		}
	}

	@Override
	public Map<String, List<RecommendInfo>> getRecommendInfoMap(String containerCode, Long commonBlockId, Long fromPlaceId,String channelPage) {
		Map<String, List<RecommendInfo>> recommendInfoMap = new HashMap<String, List<RecommendInfo>>();
		List<ProdContainerFromPlace> fromPlaces = new ArrayList<ProdContainerFromPlace>();
		// 从外面传进来的commonBlockID做默认的一个FromPlace
		if (null != commonBlockId) {
			ProdContainerFromPlace commonContainerFromPlace = new ProdContainerFromPlace();
			commonContainerFromPlace.setBlockId(commonBlockId);
			fromPlaces.add(commonContainerFromPlace);
		}
		List<ProdContainerFromPlace> list = prodContainerFromPlaceDAO.selectValidFromPlaces(containerCode, fromPlaceId);
		if (list != null) {
			fromPlaces.addAll(list);
		}
		for (ProdContainerFromPlace fromPlace : fromPlaces) {
			recommendInfoMap.putAll(getRecommendInfoByParentBlockIdAndPageChannel(fromPlace.getBlockId(), channelPage));
		}
		return recommendInfoMap;
	}
	
	public List<ProdContainerFromPlace> getBlocksByPlaceIdWitheContainerCode(String containerCode,Long fromPlaceId ) {
		List<ProdContainerFromPlace> list = prodContainerFromPlaceDAO.selectValidFromPlaces(containerCode, fromPlaceId);
		return list;
	}
	
	@Override
	public void insertRecommendInfo(RecommendInfo recommendInfo) {
		recommendInfoDAO.insertRecommendInfo(recommendInfo);
	}

	@Override
	public void deleteRecommendInfoById(Long recommendInfoId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("recommendInfoId", recommendInfoId);
		recommendInfoDAO.deleteRecommendInfoByParam(param);
	}

	@Override
	public void updateRecommendInfo(RecommendInfo recommendInfo) {
		recommendInfoDAO.updateRecommendInfo(recommendInfo);
	}
	
	public void setRecommendInfoDAO(RecommendInfoDAO recommendInfoDAO) {
		this.recommendInfoDAO = recommendInfoDAO;
	}

	public void setRecommendBlockDAO(RecommendBlockDAO recommendBlockDAO) {
		this.recommendBlockDAO = recommendBlockDAO;
	}

	public ProdContainerFromPlaceDAO getProdContainerFromPlaceDAO() {
		return prodContainerFromPlaceDAO;
	}

	public void setProdContainerFromPlaceDAO(
			ProdContainerFromPlaceDAO prodContainerFromPlaceDAO) {
		this.prodContainerFromPlaceDAO = prodContainerFromPlaceDAO;
	}
}
