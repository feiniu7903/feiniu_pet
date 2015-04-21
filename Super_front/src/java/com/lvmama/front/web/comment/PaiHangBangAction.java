package com.lvmama.front.web.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

/**
 * 点评景点排行首页
 * 
 * @author yuzhizeng
 * 
 */
@Results({
	@Result(name = "success", location = "/WEB-INF/pages/comment/zt/paiHangBang.ftl", type = "freemarker"),
	@Result(name = "JS", location = "/WEB-INF/pages/comment/zt/search_list_19.ftl", type = "freemarker")
})
public class PaiHangBangAction extends CmtBaseAction {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = -1704046187848147609L;
	/**
	 * 日志输出器
	 */
	private final static Log LOG = LogFactory.getLog(PaiHangBangAction.class);

	// pcadmin后台推荐块：热门景点排行
	private Long blockId; // 主块ID

	private String station; // 站点

	/**
	 * 点评景点排行逻辑接口
	 */
	private CmtTitleStatistisService cmtTitleStatistisService;
	/**
	 * 最新的点评列表
	 */
	private List<CommonCmtCommentVO> lastestCommentsList;
	/**
	 * 近期口碑榜,景区标签 数据
	 */
	private Map<String, List<RecommendInfo>> map;
	/**
	 * 排行景点
	 */
	private List<CmtTitleStatisticsVO> cmtTopPlaceVOList;
	
	private List<CommonCmtCommentVO> cmtTopCommentList = new ArrayList<CommonCmtCommentVO>();
	
	/**
	 * cache周期1天
	 */
	private static final int CMT_TOP_PLACE_DAY_EXPIRY_MINUTES = 60 * 24;
	/**
	 * 近期口碑榜的cache(key)
	 */
	private static final String PCADMIN_REC_PRODUCT = "PCADMIN_REC_PRODUCT";
	/**
	 * 前3条最新点评的cache(key)
	 */
	private static final String THREE_LASTEST_CMTS = "THREE_LASTEST_CMTS";

	/**
	 * 网站首页显示前5个热门景区请求函数
	 */
	private String functionName;
	/**
	 * 网站首页目标标签
	 */
	private String targetName;
	
	@Action("/comment/paiHangBang")
	public String execute() {

		// 获取排行景点TOP10
		getTopPlaceInfoFromCache();

		// 近期口碑榜,景区标签(pcadmin后台配置)
		map = getRecProductByBlockIdAndStationFromCache();

		// 获取3条最新点评
		lastestCommentsList = getLastestCommentsFromCache();

		return "success";
	}

	/**
	 * 获取网站首页显示的热门排行景区
	 * 
	 * @return 产生JS代码
	 */
	@SuppressWarnings("unchecked")
	public String getRecommendMultiFormat() {
		getTopPlaceInfoFromCache();
		return "JS";
	}

	/**
	 * 从cache(1天)获取排行景点TOP10信息
	 */
	@SuppressWarnings("unchecked")
	private List<CmtTitleStatisticsVO> getTopPlaceInfoFromCache() {

		cmtTopPlaceVOList = (List<CmtTitleStatisticsVO>) MemcachedUtil.getInstance().get(Constant.TEN_CMT_TOP_PLACE_KEY);

		if (null != cmtTopPlaceVOList && false) {
			LOG.debug("从MemCache中获取排行景点TOP10所有信息列表");
		} else {
			LOG.debug("MemCache中获取排行景点TOP10所有信息列表不存在或已经过期，需要重新获取");

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("_startRow", 0);
			parameters.put("_endRow", 10);
			cmtTopPlaceVOList = cmtTitleStatistisService.queryHotCommentStatisticsOfPlace(parameters);
			
			
			// 获取每个景点的(点评数,精华点评和用户)
			for (int i = 0; i < cmtTopPlaceVOList.size();i++) {
				CmtTitleStatisticsVO vo = cmtTopPlaceVOList.get(i);
				vo = composeCmtTitleStatistics(vo);
				cmtTopPlaceVOList.set(i, vo);
				// 获取最新一条精华点评
				getFirstbestCmt(vo);  
			}
			MemcachedUtil.getInstance().set(Constant.TEN_CMT_TOP_PLACE_KEY,CMT_TOP_PLACE_DAY_EXPIRY_MINUTES*60, getCmtTopPlaceVOList());
		}

		return cmtTopPlaceVOList;
	}

	/**
	 * 获取该景点最新一条精华点评
	 */
	private void getFirstbestCmt(CmtTitleStatisticsVO vo) {
		Map<String, Object> bestCommentPara = new HashMap<String, Object>();
		bestCommentPara.put("_startRow", 0);
		bestCommentPara.put("_endRow", 1);
		bestCommentPara.put("placeId", vo.getPlaceId());
		bestCommentPara.put("isBest", "Y");
		bestCommentPara.put("createTime321", "true");
		List<CommonCmtCommentVO> bestCommentList = cmtCommentService.getCmtCommentList(bestCommentPara);
		
		if (!bestCommentList.isEmpty()) {
			cmtTopCommentList.add(bestCommentList.get(0));
		}
		else
		{
			cmtTopCommentList.add(null);
		}
	}

	/**
	 * 从cache(1天)获取3条最新点评
	 */
	@SuppressWarnings("unchecked")
	private List<CommonCmtCommentVO> getLastestCommentsFromCache() {

		Object cache = MemcachedUtil.getInstance().get(THREE_LASTEST_CMTS);
		if (null != cache && false) {
			LOG.debug("从MemCache中获取3条最新点评");
		} else {
			LOG.debug("MemCache中获取3条最新点评不存在或已经过期，需要重新获取");
			
			Map<String, Object> lastestCmtParams = new HashMap<String, Object>();
			lastestCmtParams.put("getPlaceCmts", "Y");   //取景点点评
			lastestCmtParams.put("_startRow", 0);
			lastestCmtParams.put("_endRow", 3);
			cache = getLastestComments(lastestCmtParams, null);
			MemcachedUtil.getInstance().set(THREE_LASTEST_CMTS, CMT_TOP_PLACE_DAY_EXPIRY_MINUTES*60, cache);
		}
		return (List<CommonCmtCommentVO>)cache;
	}

	/**
	 * 从cache(1天)获取近期口碑榜,景区标签
	 */
	@SuppressWarnings("unchecked")
	private Map<String, List<RecommendInfo>> getRecProductByBlockIdAndStationFromCache() {

		Object cache = MemcachedUtil.getInstance().get(PCADMIN_REC_PRODUCT);
		
		if (null != cache) {
			LOG.debug("从MemCache中获取近期口碑榜列表");
		} else {
			LOG.debug("MemCache中获取近期口碑榜列表不存在或已经过期，需要重新获取");
			
			cache = recommendInfoService.getRecommendInfoByParentBlockIdAndPageChannel(blockId, station);
			
			if(cache != null)
			{
				MemcachedUtil.getInstance().set(PCADMIN_REC_PRODUCT, CMT_TOP_PLACE_DAY_EXPIRY_MINUTES*60, cache);
			}
			
		}
		return (Map<String, List<RecommendInfo>>) cache;
	}

	public Map<String, List<RecommendInfo>> getMap() {
		return map;
	}



	public List<CommonCmtCommentVO> getLastestCommentsList() {
		return lastestCommentsList;
	}

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	public List<CmtTitleStatisticsVO> getCmtTopPlaceVOList() {
		return cmtTopPlaceVOList;
	}

	public void setCmtTopPlaceVOList(List<CmtTitleStatisticsVO> cmtTopPlaceVOList) {
		this.cmtTopPlaceVOList = cmtTopPlaceVOList;
	}

	public CmtTitleStatistisService getCmtTitleStatistisService() {
		return cmtTitleStatistisService;
	}

	public void setCmtTitleStatistisService(CmtTitleStatistisService cmtTitleStatistisService) {
		this.cmtTitleStatistisService = cmtTitleStatistisService;
	}

	public List<CommonCmtCommentVO> getCmtTopCommentList() {
		return cmtTopCommentList;
	}

	public void setCmtTopCommentList(List<CommonCmtCommentVO> cmtTopCommentList) {
		this.cmtTopCommentList = cmtTopCommentList;
	}
	
	
}
