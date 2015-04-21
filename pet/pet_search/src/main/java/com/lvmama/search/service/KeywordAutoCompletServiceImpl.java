package com.lvmama.search.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.AutoCompleteVerHotel;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.search.lucene.service.autocomplete.AutoCompleteOneService;
import com.lvmama.search.lucene.service.autocomplete.AutoCompleteService;


/**
 * 下拉补全逻辑 
 * @author HZ
 */
@Service("keywordAutoCompletService")
public class KeywordAutoCompletServiceImpl implements KeywordAutoCompletService{
	@Autowired
	protected AutoCompleteOneService autoCompleteOneService;
	
	private Log loger = LogFactory.getLog(getClass());

	/** 默认优先级别**/
	private String[] channelPriority = new String[] { "TICKET", "FREETOUR", "HOTEL", "ROUTE", "GROUP", "FREELONG", "AROUND" };
	
	/**
	 * 酒店自动补全
	 * @param keyword
	 * @return
	 */
	public List<AutoCompletePlaceObject> hotelAutoComplete(String keyword){
		List<AutoCompletePlaceObject> list =  autoCompleteOneService.getAutoCompletePlacePlaceObjectListMatched(SearchConstants.HOTEL_MERGE_COMPLETE, null, keyword,null,false);
		List<AutoCompletePlaceObject> list_city = new ArrayList<AutoCompletePlaceObject>();
		List<AutoCompletePlaceObject> list_product = new ArrayList<AutoCompletePlaceObject>();
		List<AutoCompletePlaceObject> list_landmark = new ArrayList<AutoCompletePlaceObject>();
		List<AutoCompletePlaceObject> list_hotel = new ArrayList<AutoCompletePlaceObject>();
		List<AutoCompletePlaceObject> list_scenic = new ArrayList<AutoCompletePlaceObject>();
		
		int maxSzie = list.size()>10 ? 10 : list.size();
		for (int i = 0; i < maxSzie; i++) {
			AutoCompletePlaceObject ap = list.get(i);
			if("PLACE".equals(ap.getStage())){
				list_city.add(ap);
			}else if("PRODUCT".equals(ap.getStage()) || "TAG".equals(ap.getStage()) || "TOPIC".equals(ap.getStage())){
				list_product.add(ap);
			}else if("LANDMARK".equals(ap.getStage())){
				list_landmark.add(ap);
			}else if("HOTEL".equals(ap.getStage())){
				list_hotel.add(ap);
			}else if("SCENIC".equals(ap.getStage())){
				list_scenic.add(ap);
			}
		}
		list_city.addAll(list_product);
		list_city.addAll(list_scenic);
		list_city.addAll(list_landmark);
		list_city.addAll(list_hotel);
		return list_city;
	}
	
	/**
	 * ver酒店自动补全
	 * @param keyword
	 * @return
	 */
	public List<AutoCompleteVerHotel> verHotelAutoComplete(String keyword){
		List<AutoCompleteVerHotel> list =  autoCompleteOneService.getAutoCompleteVerHotelListMatched(SearchConstants.HOTEL_MERGE_COMPLETE, null, keyword,null,false);
		if(list!=null && list.size()==0 && StringUtil.isNotEmptyString(keyword)){
			keyword=keyword.substring(0,keyword.length()-1);
			
			return this.verHotelAutoComplete(keyword);
		}
		List<AutoCompleteVerHotel> list_district = new ArrayList<AutoCompleteVerHotel>();
		List<AutoCompleteVerHotel> list_landmark = new ArrayList<AutoCompleteVerHotel>();
		List<AutoCompleteVerHotel> list_hotel = new ArrayList<AutoCompleteVerHotel>();
		List<AutoCompleteVerHotel> return_list = new ArrayList<AutoCompleteVerHotel>();
		
		int maxSzie = list.size()>10 ? 10 : list.size();
		for (int i = 0; i < maxSzie; i++) {
			AutoCompleteVerHotel ap = list.get(i);
			if("1".equals(ap.getAutocompleteMark())){
				list_district.add(ap);
			}else if("2".equals(ap.getAutocompleteMark())){
				list_landmark.add(ap);
			}else if("3".equals(ap.getAutocompleteMark())){
				list_hotel.add(ap);
		}
		}
			return_list.addAll(list_district);
			return_list.addAll(list_landmark);
			return_list.addAll(list_hotel);
		return return_list ;
	}
	
	public List<AutoCompletePlaceObject> getKeywordAutoComplet(String fromChannel,String fromDestId, String keyword) {
		List<AutoCompletePlaceObject> matchListAll = autoCompleteOneService.getAutoCompletePlacePlaceObjectListMatched(SearchConstants.ALL_CHANNEL_TYPE, Long.parseLong(fromDestId), keyword);
		List<AutoCompletePlaceObject> autoCompleteList = allListMerge(fromChannel, matchListAll,50);
		return autoCompleteList;
	}	

	/**
	 *  合并重复的下拉提示内容NAME字段
	 *  排名的规则是按matchSEQ排序 ,并且按如下规则调整
	 *  如果排名第一的有重复，则全部调整到前面,按大类线路，门票，酒店展开,子类全部合并在一起写在字段STAGE中
	 *  如果排名第一以后的有重复，则去重复，压缩到一个字段，子类全部合并在一起写在字段STAGE中 *
	 **/
	private List<AutoCompletePlaceObject>  allListMerge(String fromChannel ,List<AutoCompletePlaceObject> matchListAll ,int topNum) {
		List<AutoCompletePlaceObject> showList = new ArrayList<AutoCompletePlaceObject>();
		if (matchListAll != null && !matchListAll.isEmpty()) {
			showList = matchListAll;
			// 保证不为空，默认为MAIN
			fromChannel = StringUtil.isEmptyString(fromChannel) ? "MAIN" : fromChannel;
			fromChannel = fromChannel.replaceAll("GROUP", "ROUTE").replaceAll("FREELONG", "ROUTE");
			
			/**调整补全排序栏目优先级**/
			channelPriorityModify( fromChannel ,channelPriority);
			
			/**用户如果选择了fromChannel 并且不是首页,例如门票频道,优先把门票的补全排第一项  */			
			for (int i = 0; i < showList.size(); i++) {
				if (showList.get(i).getStage().indexOf(channelPriority[0])>=0) {
					AutoCompletePlaceObject firstObj = showList.get(i);
					showList.remove(firstObj);
					showList.add(0, firstObj);
					break;	
				}					
			}
			
			/**取前TopNum**/
			if (showList.size()>topNum) {
				showList = showList.subList(0, topNum);
			}		
			
			/**第二项开始的STAGE清空**/
			if (showList.size() >= 2) {
				for (int i = 1; i < showList.size(); i++) {
					showList.get(i).setStage("");
				}				
			}
			
			/**如果第一项STAGE是复合的,展开第一项 **/
			String firstStage = showList.get(0).getStage();
			if (!StringUtils.isEmpty(firstStage) && firstStage.indexOf("+") > 0) {
				AutoCompletePlaceObject firstObj = showList.get(0);
				showList.remove(firstObj);
				for (int k =channelPriority.length-1; k >=0; k--) {
					if (firstStage.indexOf(channelPriority[k])>=0) {
						AutoCompletePlaceObject channelObj = new AutoCompletePlaceObject(channelPriority[k], firstObj.getShortId(), firstObj.getPinyin(), firstObj.getMatchword(), firstObj.getWords(),
								firstObj.getMatchSEQ(), firstObj.getSeq());
						showList.add(0, channelObj);
					}					
				}
			}
		}
		return showList;
	}

	/**
	 *  如果用户预先选定老和新的频道,调整补全排序默认优先级   
	 **/	
	private void channelPriorityModify(String fromChannel, String[] channelPriority) {
		if (!fromChannel.isEmpty() && !fromChannel.equals("MAIN")) {
			for (int i = 0; i < channelPriority.length; i++) {
				if (channelPriority[i].indexOf(fromChannel) >= 0 && i > 0) {
					String topChannel = channelPriority[i];
					for (int j = i - 1; j >=0; j--) {
						channelPriority[j+1] = channelPriority[j];
					}
					channelPriority[0] = topChannel;
					break;
				}
			}
		}
	}	
	
	public List<AutoCompletePlaceObject> destAutoComplete( String keyword) {
		List<AutoCompletePlaceObject> matchList = new ArrayList<AutoCompletePlaceObject>();
		if (StringUtils.isNotEmpty(keyword)) {
			
			matchList = autoCompleteOneService.getAutoCompletePlacePlaceObjectListMatched(SearchConstants.DEST_AUTO_COMPLETE, null, keyword, 50);
		}
		if (StringUtil.isEmptyString(keyword)) {//刚点击搜索框时默认提示10个SEQtop的列表
			matchList = autoCompleteOneService.getAutoCompletePlacePlaceObjectListDefault(SearchConstants.DEST_AUTO_COMPLETE, 9999L, 10);
		}
		return matchList;
	}
	
}
