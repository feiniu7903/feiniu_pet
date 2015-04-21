package com.lvmama.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.Query;
import org.springframework.stereotype.Service;

import com.lvmama.comm.pet.service.search.ComSearchIntelligentTypeService;
import com.lvmama.comm.search.SearchConstants.SEARCH_TYPE;
import com.lvmama.comm.search.vo.ComSearchIntelligentType;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.TypeCount;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.search.lucene.query.QueryUtil;
import com.lvmama.search.lucene.service.search.NewBaseSearchService;

/**
 * 关键字直接搜索栏目判断逻辑 当一个关键字进来后，用户没有选取下拉补全的提示关键字,这个时候这个关键字要做一个逻辑运算来判断跳那个频道
 * 如果输入的是productID则根据产品ID查询
 * 
 * @param String
 * @author HZ
 **/
@Service("keywordDirectSearchService")
public class KeywordDirectSearchServiceImpl implements KeywordDirectSearchService {

	private Log loger = LogFactory.getLog(getClass());
	/**
	 * 六个栏目 TICKET("门票"), HOTEL("特色酒店"), ROUTE("度假线路"), GROUP("跟团游"),
	 * FREELONG("自由行(机票+酒店)"), FREETOUR("自由行(景点+酒店)"), AROUD("周边/当地跟团游");
	 * **/
	@Resource
	protected NewBaseSearchService newPlaceSearchService;
	@Resource
	protected SearchBusinessService searchBusinessService;
	@Resource
	protected ComSearchIntelligentTypeService comSearchIntelligentTypeService;
	protected static int MAX_SEARCH_HITS = 800;
	protected static int PAGE = 1;

	public HashMap<String, Integer> getChannelCountsMap(String fromDestId, String keyword) {

		return null;
	}

	/**
	 * 根据fromChannel , keyword , fromdestId , newChannel 以及各个栏目的统计数情况 Sum
	 * 判断跳转的栏目newChannel
	 **/
	public String decideChannel(HttpServletRequest request, HttpServletResponse response, String fromChannel, String newChannel, String fromDest, String keyword, String orikeyword) {
		HashMap<String, Object> allTypeCountMap = new HashMap<String, Object>();
		String decideChannel = "";
		/**
		 * 第一种情况：关键字属于出发点的区域范围内景点或者城市 优先级别为 : 门票>门票+酒店 > 酒店 > 线路
		 * **/
		TypeCount typeCount = searchBusinessService.getTypeCount(request, response, fromDest, keyword, orikeyword);
		List<String> channelHits = new ArrayList<String>();
		//应用智能分类
		boolean useIntelligentType;
		if (typeCount.getTicket() > 0) {
			channelHits.add(SEARCH_TYPE.TICKET.getCode());
		}
		if (typeCount.getHotel() > 0) {
			channelHits.add(SEARCH_TYPE.HOTEL.getCode());
		}
		if (typeCount.getRoute() > 0) {
			channelHits.add(SEARCH_TYPE.ROUTE.getCode());
		}
		if (typeCount.getFreetour() > 0) {
			channelHits.add(SEARCH_TYPE.FREETOUR.getCode());
		}
		if (typeCount.getFreelong() > 0) {
			channelHits.add(SEARCH_TYPE.FREELONG.getCode());
		}
		if (typeCount.getGroup() > 0) {
			channelHits.add(SEARCH_TYPE.GROUP.getCode());
		}
		if (typeCount.getAround() > 0) {
			channelHits.add(SEARCH_TYPE.AROUND.getCode());
		}
		if(channelHits.size() > 1 ){ //当存在多个分类时，查询智能分类表中对应的出发地和目的地是否存在
			String key  = "SEARCH_INTELLIGENT_TYPE_"+fromDest+"_"+keyword;
			String type = (String)MemcachedUtil.getInstance().get(key);
			if(StringUtil.isEmptyString(type)){
				//keyword同义词循环搜索
				String[] arr = keyword.split(",");
				for (String string : arr) {
					ComSearchIntelligentType csit = comSearchIntelligentTypeService.searchByNames(fromDest, string);
					if(csit!=null){
						loger.debug("database find the "+key +" searchType :"+csit.getSearchType());
						MemcachedUtil.getInstance().set(key, csit.getSearchType());
						return csit.getSearchType();
					}
				}
			}else{
				loger.debug("localCache find the "+key +" searchType :"+type);
				return type;
			}
		}
		// 保证不为空，默认为MAIN
		fromChannel = StringUtil.isEmptyString(fromChannel) ? "MAIN" : fromChannel;
		String[] channelPriority;
		if (fromDestIsZoneOfKeyword(fromDest, keyword)) {
			// 默认优先级别
			channelPriority = new String[] { "TICKET", "FREETOUR", "ROUTE", "GROUP", "FREELONG", "AROUND", "HOTEL" };
		} else {
			// 默认优先级别
			channelPriority = new String[] { "ROUTE", "GROUP", "FREETOUR", "FREELONG", "AROUND", "TICKET", "HOTEL" };

		}
		// 调整优先级
		channelPriorityModify(fromChannel, channelPriority);
		
		// 匹配自动跳转栏目
		for (int j = 0; j < channelPriority.length; j++) {
			if (channelHits.contains(channelPriority[j])) {
				decideChannel = channelPriority[j];
				break;
			}
		}
		
		/** 当所有统计数为0时候，没有搜索结果 decideChannel="" **/
		if (typeCount.getAround() == 0 && typeCount.getFreelong() == 0 && typeCount.getFreetour() == 0 && typeCount.getGroup() == 0 && typeCount.getHotel() == 0 && typeCount.getTicket() == 0) {
			decideChannel = "" ;
		}
		return decideChannel;
	}

	/**
	 * 判断keyword和出发点的关系
	 * **/
	@Override
	public boolean fromDestIsZoneOfKeyword(String fromDest, String keyword) {
		boolean find = false;
		if (StringUtils.isNotEmpty(keyword)) {
			Query query = QueryUtil.fromDestIsZoneOfKeywordQuery(fromDest, keyword);
			List<PlaceBean> placeList = newPlaceSearchService.search(1,  query);
			if (placeList.size() > 0) {
				PlaceBean bean=placeList.get(0);
				//搜桂林的时候出现桂林公园会跳景点，桂林应该针对的是线路
				if(bean!=null && "105609".equals(bean.getId())){
					find =false;
				}else {
					find = true;
				}
				
			}
		}
		return find;
	}

	/**
	 * 调整排序优先级
	 **/
	private void channelPriorityModify(String modifyChannel, String[] channelPriority) {
		if (!StringUtil.isEmptyString(modifyChannel) && !modifyChannel.equals("MAIN")) {
			for (int i = 0; i < channelPriority.length; i++) {
				if (channelPriority[i].indexOf(modifyChannel) >= 0 && i > 0) {
					String topChannel = channelPriority[i];
					for (int j = i - 1; j >= 0; j--) {
						channelPriority[j + 1] = channelPriority[j];
					}
					channelPriority[0] = topChannel;
					break;
				}
			}
		}
	}

	@Override
	public String splitLogic(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}
}
