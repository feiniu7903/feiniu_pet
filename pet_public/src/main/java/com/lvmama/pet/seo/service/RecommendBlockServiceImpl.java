package com.lvmama.pet.seo.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.seo.RecommendBlock;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.seo.RecommendBlockService;
import com.lvmama.pet.seo.dao.RecommendBlockDAO;
import com.lvmama.pet.seo.dao.RecommendInfoDAO;

public class RecommendBlockServiceImpl implements RecommendBlockService {
	private RecommendBlockDAO recommendBlockDAO;
	private RecommendInfoDAO recommendInfoDAO;
	
	@Override
	public RecommendBlock getRecommendBlockById(Long recommendBlockId) {
		return recommendBlockDAO.getRecommendBlockById(recommendBlockId);
	}

	@Override
	public List<RecommendBlock> queryRecommendBlockByParam(Map<String, Object> param) {
		return recommendBlockDAO.queryRecommendBlockByParam(param);
	}

	@Override
	public void deleteRecommendBlockAndInfo(RecommendBlock recommendBlock) {
		if (recommendBlock != null) {
			RecommendBlock recommendBlockParam =null;
			if (recommendBlock.getLevels() == 2l) {
				//删除相应推荐信息
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("recommendBlockId", recommendBlock.getRecommendBlockId());
				recommendInfoDAO.deleteRecommendInfoByParam(param);
			} else if (recommendBlock.getLevels() == 1l) {
				//最顶级删除推荐信息-删除下级推荐模块
				recommendInfoDAO.deleteRecommendInfoByBlockIdAndSon(recommendBlock.getRecommendBlockId());
				recommendBlockParam = new RecommendBlock();
				recommendBlockParam.setParentRecommendBlockId(recommendBlock.getRecommendBlockId());
				recommendBlockDAO.deleteRecommendBlockByParam( recommendBlockParam);
			}
			//删除本模块
			recommendBlockParam = new RecommendBlock();
			recommendBlockParam.setRecommendBlockId(recommendBlock.getRecommendBlockId());
			recommendBlockDAO.deleteRecommendBlockByParam(recommendBlockParam);
		}
	}

	@Override
	public RecommendBlock insertRecommendBlock(RecommendBlock recommendBlock) {
		RecommendBlock rb=new RecommendBlock();
		recommendBlockDAO.insertRecommendBlock(recommendBlock);
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("dataCode", recommendBlock.getDataCode());
		param.put("name", recommendBlock.getName());
		param.put("levels", recommendBlock.getLevels());
		param.put("pageChannel", recommendBlock.getPageChannel());
		List<RecommendBlock> blockList=queryRecommendBlockByParam(param);
		if(blockList!=null&&blockList.size()>0){
			rb=blockList.get(0);
		}
		return rb;
	}

	@Override
	public void updateRecommendBlock(RecommendBlock recommendBlock) {
		Map<String,Object> param=new HashMap<String, Object>();
    	param.put("recommendBlockId", recommendBlock.getRecommendBlockId());
        List<RecommendInfo> infos = (List<RecommendInfo>) recommendInfoDAO.queryRecommendInfoByParam(param);
        RecommendInfo recommendInfoDataCode=null;
		for (RecommendInfo recommendInfo : infos) {
			recommendInfoDataCode=new RecommendInfo();
			recommendInfoDataCode.setRecommendInfoId(recommendInfo.getRecommendInfoId());
			if (recommendBlock.getDataCode() != null) {
				recommendInfoDataCode.setDataCode(recommendBlock.getDataCode());
			}
			recommendInfoDAO.updateRecommendInfo(recommendInfoDataCode);
		}
		recommendBlockDAO.updateRecommendBlock(recommendBlock);
	}

	public void setRecommendBlockDAO(RecommendBlockDAO recommendBlockDAO) {
		this.recommendBlockDAO = recommendBlockDAO;
	}

	public void setRecommendInfoDAO(RecommendInfoDAO recommendInfoDAO) {
		this.recommendInfoDAO = recommendInfoDAO;
	}
}
