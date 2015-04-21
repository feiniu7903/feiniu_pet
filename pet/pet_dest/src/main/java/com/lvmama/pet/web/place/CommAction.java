package com.lvmama.pet.web.place; 

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComKeyDescService;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;
import com.lvmama.comm.pet.vo.PlaceStateDests;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 公共组件Action
 * 
 * @author dengcheng
 * 
 */
public class CommAction extends BaseAction {
	private static final long serialVersionUID = 8407829752982009226L;
	private PlaceService placeService;
	private PlaceStateDests placeStateDests;
	private Long recommendBlockId;
	/**
	 * 推荐信息查询
	 */
	private RecommendInfoService recommendInfoService;
	
	private ComKeyDescService comKeyDescService;
	
	private String URL_PREFIX="http://www.lvmama.com/dest/";
	

	@Override
	@Action("/newplace/commAction")
	public String execute() throws Exception {
		return super.execute();
	}

	/**
	 * 查询推荐的默认周边景区
	 */
	@SuppressWarnings("unchecked")
	public void defaultRoundPlace() {
		StringBuffer sb = new StringBuffer("{");
		sb.append("rm:[");
		String key="defaultRoundPlace_"+recommendBlockId;
		List<RecommendInfo> intoList = (List<RecommendInfo>)MemcachedUtil.getInstance().get(key);
		if(intoList==null){
			intoList=recommendInfoService.getRecommendInfoByBlockId(recommendBlockId, null);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, intoList);
			comKeyDescService.insert(key, "查询推荐的默认周边景区json");
		}
		for (RecommendInfo viewRecommendInfo : intoList) {
			sb.append("{name:'" + viewRecommendInfo.getTitle() + "'},");
		}
		if (intoList !=null && intoList.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		sb.append("}");
		outputJsonToClient("jsoncallback",sb.toString());
	}
	
	/**
	 * 查询推荐信息
	 * 新版的所有频道搜索框推荐所引用的json
	 */
	@SuppressWarnings("unchecked")
	public void getRecommendInfoJson() {
		StringBuffer sb = new StringBuffer("{");
		sb.append("\"rm\":[");
		String key="getRecommendInfoJson_"+recommendBlockId;
		List<RecommendInfo> intoList = (List<RecommendInfo>)MemcachedUtil.getInstance().get(key);
		if(intoList==null){
			intoList=recommendInfoService.getRecommendInfoByBlockId(recommendBlockId, null);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, intoList);
			comKeyDescService.insert(key, "新版的所有频道搜索框推荐所引用的json");
		}
		for (RecommendInfo viewRecommendInfo : intoList) {
			sb.append("{\"name\":\"" + viewRecommendInfo.getTitle() + "\"},");
		}
		if (intoList !=null && intoList.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		sb.append("}");
		outputJsonToClient("callback",sb.toString());
	}
	
	/**
	 * 查询推荐信息包括推荐的目的地id
	 * 此处现只用于酒店搜索结果页的城市推荐弹出框json
	 */
	@SuppressWarnings("unchecked")
	public void getRecommendInfoWithIdJson() {
		StringBuffer sb = new StringBuffer("{");
		sb.append("\"rm\":[");
		String key="getRecommendInfoWithIdJson_"+recommendBlockId;
		List<RecommendInfo> intoList = (List<RecommendInfo>)MemcachedUtil.getInstance().get(key);
		if(intoList==null){
			intoList=recommendInfoService.getRecommendInfoByBlockId(recommendBlockId, null);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, intoList);
			comKeyDescService.insert(key, "此处现只用于酒店搜索结果页的城市推荐弹出框json");
		}
		for (RecommendInfo viewRecommendInfo : intoList) {
			sb.append("{\"id\":\""+viewRecommendInfo.getRecommObjectId()+"\",\"name\":\"" + viewRecommendInfo.getTitle() + "\"},");
		}
		if (intoList !=null && intoList.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		sb.append("}");
		String callback = this.getRequest().getParameter("callback");// 必要
		getResponse().setContentType("application/json; charset=utf-8");
		
		try {
			getResponse().getWriter().println(callback + "(" + sb.toString() + ");");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 目的地主页  目的地搜索  国内目的地推荐json数据
	 */
	@SuppressWarnings("unchecked")
	public void destInternalJsonData() {
		StringBuffer sb = new StringBuffer("{");
		sb.append("\"tj\":[");
		
		String key="destInternalJsonData_"+Constant.getInternalRecommendBlockId();
		List<RecommendInfo> intoList = (List<RecommendInfo>)MemcachedUtil.getInstance().get(key);
		if(intoList==null){
			intoList=recommendInfoService.getRecommendInfoByBlockId(Constant.getInternalRecommendBlockId(), null);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, intoList);
	
			comKeyDescService.insert(key, "目的地国内推荐数据json");
		}
		for (RecommendInfo viewRecommendInfo : intoList) {
			sb.append("{\"name\":\"" + viewRecommendInfo.getTitle() + "\",\"PinYin\":\"" + viewRecommendInfo.getUrl() + "\"},");
		}
		if (intoList != null && intoList.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		sb.append("}");
		outputJsonToClient("jsoncallback", sb.toString());
	}
	/**
	 * 目的地主页  目的地搜索  国外目的地推荐json数据
	 */
	public void destAbroadJsonData() {
		Map<String,Long> recommendBlockIds=new HashMap<String, Long>();
		recommendBlockIds.put("statesRecommendBlockId", Constant.getStatesRecommendBlockId());
		recommendBlockIds.put("asiaPlaceId", Constant.getAsiaPlaceId());
		recommendBlockIds.put("europePlaceId", Constant.getEuropePlaceId());
		recommendBlockIds.put("northAmericaPlaceId", Constant.getNorthAmericaPlaceId());
		recommendBlockIds.put("southAmericaPlaceId", Constant.getSouthAmericaPlaceId());
		recommendBlockIds.put("africaPlaceId", Constant.getAfricaPlaceId());
		recommendBlockIds.put("oceaniaPlaceId", Constant.getOceaniaPlaceId());
		
		String key="destAbroadJsonData_sb";
		this.placeStateDests = (PlaceStateDests)MemcachedUtil.getInstance().get(key);
		if(placeStateDests==null){		
			placeStateDests=placeService.getDestRecommend(recommendBlockIds);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, placeStateDests);
			comKeyDescService.insert(key, "目的地国外推荐数据json");
		}
		
		StringBuffer sb = new StringBuffer("{");
		// 获取洲推荐数据
		sb.append("\"tj\":[");
		List<RecommendInfo> intoList = placeStateDests.getStateDestsRecomm();
		for (RecommendInfo viewRecommendInfo : intoList) {
			sb.append("{\"name\":\"" + viewRecommendInfo.getTitle() + "\",\"PinYin\":\"" + viewRecommendInfo.getUrl() + "\"},");
		}
		if (intoList != null && intoList.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("],");
		// 获取亚洲数据
		sb.append("\"yz\":[");
		commonAssemblyJson(sb, placeStateDests.getAsiaDests());
		sb.append("],");
		// 欧洲
		sb.append("oz:[");
		commonAssemblyJson(sb, placeStateDests.getEuropeDests());
		sb.append("],");
		// 美洲
		sb.append("mz:[");
		commonAssemblyJson(sb, placeStateDests.getAmericaDests());
		sb.append("],");
		// 非洲
		sb.append("fz:[");
		commonAssemblyJson(sb, placeStateDests.getAfricaDests());
		sb.append("],");
		// 大洋洲
		sb.append("dyz:[");
		commonAssemblyJson(sb, placeStateDests.getOceaniaDests());
		sb.append("]");
		sb.append("}");
		
		outputJsonToClient("jsoncallback", sb.toString());
	}
	
	/**
	 * 组装json数据
	 * 
	 * @param sb
	 * @param list
	 */
	private void commonAssemblyJson(StringBuffer sb, List<Place> list) {
		if (list != null && list.size() > 0) {
			for (Iterator<Place> itaf = list.iterator(); itaf.hasNext();) {
				Place p = (Place) itaf.next();
				sb.append("{\"name\":\"" + p.getName() + "\",\"PinYin\":\"" + URL_PREFIX + p.getPinYin() + "\"}");
				if (itaf.hasNext()) {
					sb.append(",");
				}
			}
		}
	}
	/**
	 * 输出json数据到客户端
	 * 
	 * @param param
	 * @param jsonMsg
	 */
	private void outputJsonToClient(String param, String jsonMsg) {
		String jsoncallback = this.getRequest().getParameter(param);// 必要
		getResponse().setContentType("application/json; charset=UTF-8");
		try {
			getResponse().getWriter().write(jsoncallback + "(" + jsonMsg + ")");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PlaceStateDests getPlaceStateDests() {
		return placeStateDests;
	}

	public void setPlaceStateDests(PlaceStateDests placeStateDests) {
		this.placeStateDests = placeStateDests;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public void setRecommendInfoService(RecommendInfoService recommendInfoService) {
		this.recommendInfoService = recommendInfoService;
	}

	public Long getRecommendBlockId() {
		return recommendBlockId;
	}

	public void setRecommendBlockId(Long recommendBlockId) {
		this.recommendBlockId = recommendBlockId;
	}

	public void setComKeyDescService(ComKeyDescService comKeyDescService) {
		this.comKeyDescService = comKeyDescService;
	}



}
