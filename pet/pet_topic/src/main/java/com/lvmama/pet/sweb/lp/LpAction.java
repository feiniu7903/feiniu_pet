package com.lvmama.pet.sweb.lp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.place.SpecialTopic;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.place.SpecialTopicService;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;
import com.lvmama.comm.utils.MemcachedUtil;
@Results({
	@Result(name = "success", location = "/WEB-INF/pages/lp/lp.ftl"),
	@Result(name = "jumpurl", type="redirect", location = "${redirectUrl}"),
	@Result(name = "error",type="dispatcher", location = "/404.jsp")
})
public class LpAction extends BaseAction{
	private static final long serialVersionUID = -1L;
	private static final Logger logger = Logger.getLogger(LpAction.class);
	public static final String RESULT_JUMPURL = "jumpurl";
	private boolean jumpedFlag;// 页面是否跳转标识
	private String idcode;
	private String redirectUrl;
	private SpecialTopic specialTopic = new SpecialTopic();
	// 页面上推荐块是固定的，不确定是哪些因素，这里用数字标识
	private List<RecommendInfo> block1ItemList = new ArrayList<RecommendInfo>();
	private List<RecommendInfo> block2ItemList = new ArrayList<RecommendInfo>();
	private List<RecommendInfo> block3ItemList = new ArrayList<RecommendInfo>();
	private List<RecommendInfo> block4ItemList = new ArrayList<RecommendInfo>();
	private List<RecommendInfo> block5ItemList = new ArrayList<RecommendInfo>();
	private List<RecommendInfo> block6ItemList = new ArrayList<RecommendInfo>();
	private List<RecommendInfo> block7ItemList = new ArrayList<RecommendInfo>();
	private List<RecommendInfo> block8ItemList = new ArrayList<RecommendInfo>();
	private List<RecommendInfo> block9ItemList = new ArrayList<RecommendInfo>();
	private List<RecommendInfo> block10ItemList = new ArrayList<RecommendInfo>();
	private List<RecommendInfo> lpPageBlockList = new ArrayList<RecommendInfo>();
	
	public static final int SPECIAL_TOPIC_REUSED_Y = 1;
	public static final int SPECIAL_TOPIC_REUSED_N = 0;
	public static final int SPECIAL_TOPIC_JUMPED_Y = 1;
	public static final int SPECIAL_TOPIC_JUMPED_N = 0;
	public String bakWord3 = "";
	private SpecialTopicService specialTopicService;
	private RecommendInfoService recommendInfoService;
	private String errorUrl;

	@Action(value = "/lp/index")
	public String execute() throws Exception {
		HttpServletResponse response = getResponse();
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		this.loadData();
		if(errorUrl!=null&&!"".equals(errorUrl))
			return errorUrl;
		if (jumpedFlag) {
			return RESULT_JUMPURL;
		}
		return SUCCESS;
	}
	/**
	 * 加载页面数据
	 */
	private void loadData() {
		try {
			if(StringUtils.isNotBlank(idcode)){
				this.specialTopic=specialTopicService.getSpecialTopicByIdcode(idcode);
				if(specialTopic==null){
					errorUrl="error";
					return;
				}
				this.checkSpecialTopic(specialTopic);
				this.jumpedFlag=false;
				if (this.jumpedFlag == false) {
					//由于recommend_info表增加了些预留字段，一条记录就能保存所有的信息了，所以之前分3个list查询合并到一个list了
					long block1Id = 303L;// 默认是本月热卖
					long block4Id = 305L;// 默认是驴妈妈推荐
					long block5Id = 306L;// 默认是门票推荐
					long block6Id = 307L;// 默认是套餐推荐
					long block7Id = 308L;// 默认是酒店推荐
					long block8Id = 309L;// 默认是拼团游推荐
					long block9Id = 310L;// 默认是旅游推荐
					long block10Id = 311L;// 默认是更多精彩
					long lpPageBlockId = 312L;
					this.block1ItemList = getRecommendInfoList(specialTopic.getId(), block1Id);
					this.block4ItemList = getRecommendInfoList(specialTopic.getId(), block4Id);
					this.block5ItemList = getRecommendInfoList(specialTopic.getId(), block5Id);
					this.block6ItemList = getRecommendInfoList(specialTopic.getId(), block6Id);
					this.block7ItemList = getRecommendInfoList(specialTopic.getId(), block7Id);
					this.block8ItemList = getRecommendInfoList(specialTopic.getId(), block8Id);
					this.block9ItemList = getRecommendInfoList(specialTopic.getId(), block9Id);
					this.block10ItemList = getRecommendInfoList(specialTopic.getId(), block10Id);
					this.lpPageBlockList = getRecommendInfoList(specialTopic.getId(), lpPageBlockId);
				}
			}else{
				errorUrl="error";
			}
			
		} catch (Exception e) {
			logger.error("LpAction>>>>>loadData occured an exception:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 检查主题的一些性质，是否重用，是否跳转
	 * 
	 * @param specialTopic
	 * @return
	 */
	public void checkSpecialTopic(SpecialTopic specialTopic) {
		if (specialTopic != null) {
			this.idcode=specialTopic.getIdcode();
			Integer reused = specialTopic.getReused();
			Integer jumped = specialTopic.getJumped();
			Date endDate = specialTopic.getEndDate();
			if (endDate != null && reused != null && reused.equals(SPECIAL_TOPIC_REUSED_Y)) {
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				calendar.setTime(endDate);
				calendar.set(Calendar.YEAR, year);
				endDate = calendar.getTime();
			}
			if (jumped != null && jumped.equals(SPECIAL_TOPIC_JUMPED_Y)) {
				if (endDate != null) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(endDate);
					calendar.set(Calendar.HOUR, 23);
					calendar.set(Calendar.MINUTE, 59);
					calendar.set(Calendar.SECOND, 59);
					endDate = calendar.getTime();
					if (endDate.before(new Date())) {
						this.jumpedFlag = true;
						this.redirectUrl = specialTopic.getJumpurl();
					}
				}
			}

		}
	}

	public String getLpFileHost(){
		return "http://pic.lvmama.com";
	}
	/**
	 * 根据主题编号和块的编号查询推荐信息
	 * 
	 * @param specialTopicId
	 * @param BlockId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RecommendInfo> getRecommendInfoList(Long specialTopicId, Long blockId) {
		List<RecommendInfo> recommendInfoList = new ArrayList<RecommendInfo>();
		if(specialTopicId==null){
			return recommendInfoList;
		}
		recommendInfoList = (List<RecommendInfo>) MemcachedUtil.getInstance().get("LpAction_" + blockId + "_" + specialTopicId.toString());
		if (null == recommendInfoList || recommendInfoList.isEmpty()) {
			recommendInfoList=recommendInfoService.getRecommendInfoByBlockId(blockId,specialTopicId.toString());
			MemcachedUtil.getInstance().set("LpAction_" + blockId + "_" + specialTopicId.toString(), MemcachedUtil.TWO_HOUR, recommendInfoList);
		}
		return recommendInfoList;
	}
	public boolean isJumpedFlag() {
		return jumpedFlag;
	}
	public void setJumpedFlag(boolean jumpedFlag) {
		this.jumpedFlag = jumpedFlag;
	}
	public String getIdcode() {
		return idcode;
	}
	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public SpecialTopic getSpecialTopic() {
		return specialTopic;
	}
	public void setSpecialTopic(SpecialTopic specialTopic) {
		this.specialTopic = specialTopic;
	}
	public List<RecommendInfo> getBlock1ItemList() {
		return block1ItemList;
	}
	public void setBlock1ItemList(List<RecommendInfo> block1ItemList) {
		this.block1ItemList = block1ItemList;
	}
	public List<RecommendInfo> getBlock2ItemList() {
		return block2ItemList;
	}
	public void setBlock2ItemList(List<RecommendInfo> block2ItemList) {
		this.block2ItemList = block2ItemList;
	}
	public List<RecommendInfo> getBlock3ItemList() {
		return block3ItemList;
	}
	public void setBlock3ItemList(List<RecommendInfo> block3ItemList) {
		this.block3ItemList = block3ItemList;
	}
	public List<RecommendInfo> getBlock4ItemList() {
		return block4ItemList;
	}
	public void setBlock4ItemList(List<RecommendInfo> block4ItemList) {
		this.block4ItemList = block4ItemList;
	}
	public List<RecommendInfo> getBlock5ItemList() {
		return block5ItemList;
	}
	public void setBlock5ItemList(List<RecommendInfo> block5ItemList) {
		this.block5ItemList = block5ItemList;
	}
	public List<RecommendInfo> getBlock6ItemList() {
		return block6ItemList;
	}
	public void setBlock6ItemList(List<RecommendInfo> block6ItemList) {
		this.block6ItemList = block6ItemList;
	}
	public List<RecommendInfo> getBlock7ItemList() {
		return block7ItemList;
	}
	public void setBlock7ItemList(List<RecommendInfo> block7ItemList) {
		this.block7ItemList = block7ItemList;
	}
	public List<RecommendInfo> getBlock8ItemList() {
		return block8ItemList;
	}
	public void setBlock8ItemList(List<RecommendInfo> block8ItemList) {
		this.block8ItemList = block8ItemList;
	}
	public List<RecommendInfo> getBlock9ItemList() {
		return block9ItemList;
	}
	public void setBlock9ItemList(List<RecommendInfo> block9ItemList) {
		this.block9ItemList = block9ItemList;
	}
	public List<RecommendInfo> getBlock10ItemList() {
		return block10ItemList;
	}
	public void setBlock10ItemList(List<RecommendInfo> block10ItemList) {
		this.block10ItemList = block10ItemList;
	}
	public List<RecommendInfo> getLpPageBlockList() {
		return lpPageBlockList;
	}
	public void setLpPageBlockList(List<RecommendInfo> lpPageBlockList) {
		this.lpPageBlockList = lpPageBlockList;
	}
	public SpecialTopicService getSpecialTopicService() {
		return specialTopicService;
	}
	public void setSpecialTopicService(SpecialTopicService specialTopicService) {
		this.specialTopicService = specialTopicService;
	}
	public RecommendInfoService getRecommendInfoService() {
		return recommendInfoService;
	}
	public void setRecommendInfoService(RecommendInfoService recommendInfoService) {
		this.recommendInfoService = recommendInfoService;
	}
	public String getBakWord3() {
		return bakWord3;
	}
	

}
