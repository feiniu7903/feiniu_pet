package com.lvmama.pet.sweb.channeldata;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;
import com.lvmama.comm.utils.DateUtil;

/**
 * 首页限时特卖的数据管理
 * @author Brian
 *
 */
@Results({ 
	@Result(name = "list", location = "/WEB-INF/pages/back/channeldata/xianShiTeMai/list.jsp"),
	@Result(name = "edit", location = "/WEB-INF/pages/back/channeldata/xianShiTeMai/edit.jsp")
})
public class XianShiTeMaiAction extends BackBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -3272660146498868832L;
	/**
	 * 限时特卖的dataCode
	 */
	private static final String DATA_CODE = "XianShiTeMai";
	/**
	 * 分公司和推荐块的对应关系
	 */
	private static final Map<String, Long> FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING = new HashMap<String, Long>();
	static {
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put("SH", 10081L);
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put("CD", 10409L);
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put("BJ", 10414L);
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put("GZ", 10415L);
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put("HZ", 13097L);
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put("SZ", 13098L);
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put("CQ", 13100L);
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put("NJ", 13102L);
	}
	
	/**
	 * 推荐管理的远程服务
	 */
	private RecommendInfoService recommendInfoService;
	/**
	 * 分公司
	 */
	private String filiale = "SH";
	/**
	 * 产品类型
	 */
	private String remark;
	/**
	 * 推荐数据标识
	 */
	private Long recommendInfoId;
	/**
	 * 推荐数据列表
	 */
	private List<RecommendInfo> recommendInfos;
	private RecommendInfo recommendInfo;

	/**
	 * 查询点评推荐的所有ID
	 */
	@Action(value="/channeldata/xianShiTeMai/list")
	public String index() {
		Map<String, Object> param =new HashMap<String, Object>();
		if (StringUtils.isNotBlank(remark)) {
			param.put("remark", remark);
		}
		if (null == FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.get(filiale)) {
			param.put("parentRecommendBlockId", FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.get("SH"));
		} else {
			param.put("parentRecommendBlockId", FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.get(filiale));
		}
		param.put("dataCode", DATA_CODE);
		recommendInfos = recommendInfoService.queryRecommendInfoByParam(param);
		return "list";
	}
	
	@Action(value="/channeldata/xianShiTeMai/edit")
	public String edit() {
		recommendInfo =  recommendInfoService.getRecommendInfoById(recommendInfoId); 
		return "edit";
	}
	
	@Action(value="/channeldata/xianShiTeMai/save")
	public void save() throws Exception {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "未能找到相应的信息");
		
		if (null != this.recommendInfo && null != this.recommendInfo.getRecommendInfoId()) {
			RecommendInfo _info = this.recommendInfoService.getRecommendInfoById(recommendInfo.getRecommendInfoId());
			if (null != _info) {
				_info.setRemark(recommendInfo.getRemark());
				if ("1".equals(recommendInfo.getRemark())) {
					_info.setBakWord1("门票");
				}
				if ("2".equals(recommendInfo.getRemark())) {
					_info.setBakWord1("周边");
				}
				if ("3".equals(recommendInfo.getRemark())) {
					_info.setBakWord1("国内");
				}
				if ("4".equals(recommendInfo.getRemark())) {
					_info.setBakWord1("出境");
				}
				_info.setTitle(recommendInfo.getTitle());
				_info.setUrl(recommendInfo.getUrl());
				_info.setBakWord2(recommendInfo.getBakWord2());
				_info.setBakWord3(recommendInfo.getBakWord3());
				_info.setSeq(recommendInfo.getSeq());
				//设定时间上下线时间为空，让job去更新
				if(recommendInfo.getBakWord2()!=_info.getBakWord2()&&StringUtils.isNotBlank(_info.getBakWord4())){
					Calendar endTime  = Calendar.getInstance();
					endTime.setTime(DateUtil.toDate(_info.getBakWord4(), "yyyy-MM-dd HH:mm:ss"));
					endTime.add(Calendar.DATE, Integer.parseInt(_info.getBakWord2()) - 1);
					endTime.set(Calendar.HOUR_OF_DAY, 23);
					endTime.set(Calendar.MINUTE, 59);
					endTime.set(Calendar.SECOND, 59);
					_info.setBakWord5(DateUtil.getFormatDate(endTime.getTime(), "yyyy-MM-dd HH:mm:ss"));
				}
				recommendInfoService.updateRecommendInfo(_info);
				json.put("success", true);
				json.put("message", "操作成功");
			}
		} 
		getResponse().getWriter().print(json.toString());
	}	

	public String getFiliale() {
		return filiale;
	}

	public void setFiliale(String filiale) {
		this.filiale = filiale;
	}

	public List<RecommendInfo> getRecommendInfos() {
		return recommendInfos;
	}

	public void setRecommendInfos(List<RecommendInfo> recommendInfos) {
		this.recommendInfos = recommendInfos;
	}

	public Long getRecommendInfoId() {
		return recommendInfoId;
	}

	public void setRecommendInfoId(Long recommendInfoId) {
		this.recommendInfoId = recommendInfoId;
	}

	public RecommendInfo getRecommendInfo() {
		return recommendInfo;
	}

	public void setRecommendInfo(RecommendInfo recommendInfo) {
		this.recommendInfo = recommendInfo;
	}

	public void setRecommendInfoService(RecommendInfoService recommendInfoService) {
		this.recommendInfoService = recommendInfoService;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}	
}
